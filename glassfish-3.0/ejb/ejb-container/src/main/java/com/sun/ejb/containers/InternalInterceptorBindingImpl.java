/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.ejb.containers;

import com.sun.enterprise.container.common.spi.util.ComponentEnvManager;
import com.sun.enterprise.deployment.util.ModuleDescriptor;
import com.sun.enterprise.deployment.*;
import com.sun.ejb.EjbInvocation;
import org.glassfish.api.invocation.ComponentInvocation;
import java.util.Collection;

import org.glassfish.api.invocation.InvocationManager;
import com.sun.enterprise.container.common.spi.ManagedBeanManager;


import org.jvnet.hk2.component.Habitat;


public class InternalInterceptorBindingImpl  {

    private Habitat habitat;

    public InternalInterceptorBindingImpl(Habitat h) {
        habitat = h;
    }

    public void registerInterceptor(Object systemInterceptor) {

        InvocationManager invManager = habitat.getByContract(InvocationManager.class);

        ComponentInvocation currentInv = invManager.getCurrentInvocation();

        if(currentInv == null) {
            throw new IllegalStateException("no current invocation");
        } else if (currentInv.getInvocationType() !=
                       ComponentInvocation.ComponentInvocationType.SERVLET_INVOCATION) {
            throw new IllegalStateException
                        ("Illegal invocation type : " +  currentInv.getInvocationType() +
                         ".  This operation is only available from a web app context");
        }

        ComponentEnvManager compEnvManager = habitat.getByContract(ComponentEnvManager.class);

        JndiNameEnvironment env = compEnvManager.getCurrentJndiNameEnvironment();

        BundleDescriptor webBundle = (BundleDescriptor) env;

        ModuleDescriptor moduleDesc = webBundle.getModuleDescriptor();

        // Register interceptor for EJB components
        if( EjbContainerUtilImpl.isInitialized() ) {

            Collection<EjbBundleDescriptor> ejbBundles =
                    moduleDesc.getDescriptor().getExtensionsDescriptors(EjbBundleDescriptor.class);

            if( ejbBundles.size() == 1) {

                EjbBundleDescriptor ejbBundle = ejbBundles.iterator().next();
                for(EjbDescriptor ejb : ejbBundle.getEjbs()) {
                    BaseContainer container =
                        EjbContainerUtilImpl.getInstance().getContainer(ejb.getUniqueId());
                    container.registerSystemInterceptor(systemInterceptor);

                }
            }

        }

        // Register interceptor for any managed beans
        // TODO Handle 299-enabled case
        ManagedBeanManager managedBeanManager = habitat.getByContract(ManagedBeanManager.class);
        managedBeanManager.registerRuntimeInterceptor(systemInterceptor, webBundle);


    }

}
