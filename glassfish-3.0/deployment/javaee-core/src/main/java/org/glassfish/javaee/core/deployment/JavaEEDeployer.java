/*
 * The contents of this file are subject to the terms 
 * of the Common Development and Distribution License 
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at 
 * https://glassfish.dev.java.net/public/CDDLv1.0.html or
 * glassfish/bootstrap/legal/CDDLv1.0.txt.
 * See the License for the specific language governing 
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL 
 * Header Notice in each file and include the License file 
 * at glassfish/bootstrap/legal/CDDLv1.0.txt.  
 * If applicable, add the following below the CDDL Header, 
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information: 
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2008 Sun Microsystems, Inc. All rights reserved.
 */

package org.glassfish.javaee.core.deployment;

import org.glassfish.api.deployment.*;
import org.glassfish.api.container.Container;
import org.glassfish.api.admin.ServerEnvironment;
import org.glassfish.api.deployment.archive.ReadableArchive;
import org.glassfish.internal.data.ApplicationInfo;
import org.glassfish.internal.data.ApplicationRegistry;
import org.glassfish.internal.api.JAXRPCCodeGenFacade;
import org.glassfish.loader.util.ASClassLoaderUtil;
import com.sun.enterprise.deployment.Application;
import com.sun.enterprise.deployment.BundleDescriptor;
import com.sun.enterprise.deployment.util.ApplicationVisitor;
import org.glassfish.deployment.common.DeploymentException;
import org.glassfish.deployment.common.DeploymentProperties;
import com.sun.enterprise.config.serverbeans.ServerTags;
import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.component.Habitat;

import java.io.File;
import java.io.IOException;
import java.util.jar.Manifest;
import java.util.jar.Attributes;
import java.util.List;
import java.net.URL;

/**
 * Convenient superclass for JavaEE Deployer implementations.
 *
 */
public abstract class   JavaEEDeployer<T extends Container, U extends ApplicationContainer>
        implements Deployer<T, U> {

    @Inject
    protected ServerEnvironment env;

    @Inject
    protected ApplicationRegistry appRegistry;

    @Inject
    protected Habitat habitat;

    @Inject(name="application_undeploy", optional=true)
    protected ApplicationVisitor undeploymentVisitor=null;

    private static final String APPLICATION_TYPE = "Application-Type";

    /**
     * Returns the meta data assocated with this Deployer
     *
     * @return the meta data for this Deployer
     */
    public MetaData getMetaData() {
        return new MetaData(false, null, null);
    }

     
    /**
     * Returns the classpath associated with this module
     * Can be used to compile generated cmp classes,
     * rmi stubs etc.
     *
     * @return the classpath for this module
     */
    protected String getModuleClassPath(DeploymentContext ctx) {
        // get the base module classpath
        // this includes the system classpath and deploy time lib libraries
        StringBuilder classpath = new StringBuilder
            (ASClassLoaderUtil.getModuleClassPath(habitat, ctx));

        try {
            // add the module dir
            classpath.append(ctx.getSourceDir().toURI().getPath());
            classpath.append(File.pathSeparator);

            // add the stubs dir
            classpath.append(ctx.getScratchDir("ejb").toURI().getPath());
            classpath.append(File.pathSeparator);

            // add the ear lib libraries if it's ear
            Application app = ctx.getModuleMetaData(Application.class); 
            if (!app.isVirtual()) {
                ReadableArchive parentArchive = 
                    ctx.getSource().getParentArchive();

                String compatProp = ctx.getAppProps().getProperty(
                    DeploymentProperties.COMPATIBILITY);

                List<URL> earLibURLs = 
                    ASClassLoaderUtil.getAppLibDirLibrariesAsList(new File(
                        parentArchive.getURI()), app.getLibraryDirectory(), 
                        compatProp);  

                for (URL url : earLibURLs) { 
                    classpath.append(url.toURI().getPath());
                    classpath.append(File.pathSeparator);
                }
            }
        } catch (Exception e) {
            // log a warning
        }

        return classpath.toString();
    }

    /*
     * Gets the common instance classpath, which is composed of the
     * pathnames of domain_root/lib/classes and
     * domain_root/lib/[*.jar|*.zip] (in this
     * order), separated by the path-separator character.
     * @return The instance classpath
     */
    protected String getCommonClassPath() {
        StringBuffer sb = new StringBuffer();

        File libDir = env.getLibPath();
        String libDirPath = libDir.getAbsolutePath();

        // Append domain_root/lib/classes
        sb.append(libDirPath + File.separator + "classes");
        sb.append(File.pathSeparator);

        // Append domain_root/lib/[*.jar|*.zip]
        String[] files = libDir.list();
        if (files != null) {
            for (int i=0; i<files.length; i++) {
                if (files[i].endsWith(".jar") || files[i].endsWith(".zip")) {
                    sb.append(libDirPath + File.separator + files[i]);
                    sb.append(File.pathSeparator);
                }
            }
        }
        return sb.toString();
    }

    /**
     * Loads the meta date associated with the application.
     *
     * @param type type of metadata that this deployer has declared providing.
     * @param dc deployment context
     */
    public <V> V loadMetaData(Class<V> type, DeploymentContext dc) {
        return null;
    }

    /**
     * Prepares the application bits for running in the application server.
     * For certain cases, this is generating non portable
     * artifacts and other application specific tasks.
     * Failure to prepare should throw an exception which will cause the overall
     * deployment to fail.
     *
     * @param dc deployment context
     * @return true if the prepare phase was successful
     *
     */
    public boolean prepare(DeploymentContext dc) {
        try {
            prepareScratchDirs(dc);
/*
            // TODO: Need to revisit this when we have a good plan for system 
            // applications in v3
            // currently the object type is always user for applications 
            // under <applications> element
            // if we need to use this code again, also need to move to a 
            // place which only gets done once per application
            if (dc.getAppProps().getProperty(ServerTags.OBJECT_TYPE)==null) {
                String objectType = getObjectType(dc);
                if (objectType != null) {
                    dc.getAppProps().setProperty(ServerTags.OBJECT_TYPE,
                        objectType);
                }
            }
*/

             //In jaxrpc it was required to run
             //Wscompile to generate the artifacts for clients too.
             //service-ref element can be in client in web.xml,  application-client.xml, sun-ejb-jar.xml
            BundleDescriptor bundleDesc = dc.getModuleMetaData(BundleDescriptor.class);
            if (bundleDesc.hasWebServiceClients())     {
                JAXRPCCodeGenFacade jaxrpcCodeGenFacade = habitat.getByContract(JAXRPCCodeGenFacade.class);
                if (jaxrpcCodeGenFacade != null) {
                    jaxrpcCodeGenFacade.run(habitat,dc,getModuleClassPath(dc)) ;
                }
            }
            generateArtifacts(dc);
            return true;
        } catch (Exception ex) {
            // re-throw all the exceptions as runtime exceptions
            throw new RuntimeException(ex.getMessage(),ex);
        }
    }

   /**
     * Loads a previously prepared application in its execution environment and
     * return a ContractProvider instance that will identify this environment in
     * future communications with the application's container runtime.
     * @param container in which the application will reside
     * @param context of the deployment
     * @return an ApplicationContainer instance identifying the running application
     */
    public U load(T container, DeploymentContext context) {
        // reset classloader on DOL object before loading so we have a
        // valid classloader set on DOL
        Application app = context.getModuleMetaData(Application.class);
        if (app != null) {
            app.setClassLoader(context.getClassLoader());
        }

        return null;
    }

    protected void generateArtifacts(DeploymentContext dc)
        throws DeploymentException {
    }

    /**
     * Clean any files and artifacts that were created during the execution
     * of the prepare method.
     *
     * @param context deployment context
     */
    public void clean(DeploymentContext context) {
        if (undeploymentVisitor!=null) {

            String appName = context.getCommandParameters(OpsParams.class).name();
            Application app = getApplicationFromApplicationInfo(appName);
            if (app != null) {
                context.addModuleMetaData(app);
                   undeploymentVisitor.accept(app);
            }
        }
    }

    protected void prepareScratchDirs(DeploymentContext context)
        throws IOException {
        context.getScratchDir("ejb").mkdirs();
        context.getScratchDir("xml").mkdirs();
        context.getScratchDir("jsp").mkdirs();
    }

    // get the object type from the application manifest file if
    // it is present. Application can be user application or system
    // appliction.
    protected String getObjectType(DeploymentContext context) {
        try{
            Manifest manifest = context.getSource().getManifest();
            if(manifest==null)  return null;
            Attributes attrs = manifest.getMainAttributes();
            return attrs.getValue(APPLICATION_TYPE);
        }catch(IOException e){
            // by default resource-type will be assigned "user".
            return null;
        }
    }

    protected Application getApplicationFromApplicationInfo(
        String appName) {
        ApplicationInfo appInfo = appRegistry.get(appName);
        if (appInfo == null) {
            return null;
        }
        return appInfo.getMetaData(Application.class);
    }

    abstract protected String getModuleType();
}