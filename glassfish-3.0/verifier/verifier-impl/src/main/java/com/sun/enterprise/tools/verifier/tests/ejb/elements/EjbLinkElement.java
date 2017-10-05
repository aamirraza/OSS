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
package com.sun.enterprise.tools.verifier.tests.ejb.elements;

import com.sun.enterprise.tools.verifier.tests.ejb.EjbTest;
import com.sun.enterprise.tools.verifier.tests.ejb.EjbCheck;
import java.util.*;
import java.util.logging.Level;

import com.sun.enterprise.deployment.*;
import com.sun.enterprise.tools.verifier.*;
import com.sun.enterprise.tools.verifier.tests.*;

/** 
 * The value of the ejb-link element must be the ejb-name of an enterprise
 * bean in the same ejb-jar file.
 */
public class EjbLinkElement extends EjbTest implements EjbCheck { 

    /**
     * The referenced bean must be an enterprise bean in the same ear file.
     *
     * @param descriptor the Enterprise Java Bean deployment descriptor
     * @return <code>Result</code> the results for this assertion
     */
    public Result check(EjbDescriptor descriptor) {

	Result result = getInitializedResult();
	ComponentNameConstructor compName = getVerifierContext().getComponentNameConstructor();

	boolean resolved = false;
	boolean oneFailed = false;
	int na = 0;

	// The value of the ejb-link element must be the ejb-name of an enterprise
	// bean in the same J2EE Application archive.
        /*
	if (Verifier.getEarFile() == null) {
	    // this ejb-jar is not part of an .ear file
	    result.addNaDetails(smh.getLocalString
				  ("tests.componentNameConstructor",
				   "For [ {0} ]",
				   new Object[] {compName.toString()}));
	    result.notApplicable
		(smh.getLocalString(getClass().getName() + ".no_ear",
                "This ejb jar [ {0} ] is not part of a J2EE Archive.",
                 new Object[] {descriptor.getName()}));
	    return result;
	}
         */

//	String applicationName = null;
	// The value of the ejb-link element must be the ejb-name of an enterprise
	// bean in the same application .ear file.
	if (!descriptor.getEjbReferenceDescriptors().isEmpty()) {
	    for (Iterator itr = descriptor.getEjbReferenceDescriptors().iterator(); 
		 itr.hasNext();) {                                                     
		EjbReferenceDescriptor nextEjbReference = (EjbReferenceDescriptor) itr.next();
		if (nextEjbReference.isLinked()) {
		    String ejb_link = nextEjbReference.getLinkName();
		    ejb_link = ejb_link.substring(ejb_link.indexOf("#") + 1);

		    try {
                    
//                applicationName = application.getName();
//                File tmpFile = new File(System.getProperty("java.io.tmpdir"));
//                tmpFile = new File(tmpFile, Verifier.TMPFILENAME + ".tmp");
                Set ejbBundles = descriptor.getApplication().getEjbBundleDescriptors();
                Iterator ejbBundlesIterator = ejbBundles.iterator();
                EjbBundleDescriptor ejbBundle = null;
 
			while (ejbBundlesIterator.hasNext()) {
			    ejbBundle = (EjbBundleDescriptor)ejbBundlesIterator.next();
//                            if (Verifier.getEarFile() != null){
//                                archivist.extractEntry(ejbBundle.getModuleDescriptor().getArchiveUri(), tmpFile);
//                            }
			    for (Iterator itr2 = ejbBundle.getEjbs().iterator(); itr2.hasNext();) {

				EjbDescriptor ejbDescriptor = (EjbDescriptor) itr2.next();
				if (ejbDescriptor.getName().equals(ejb_link)) {
				    resolved = true;
				    logger.log(Level.FINE, getClass().getName() + ".passed",
                            new Object[] {ejb_link,ejbDescriptor.getName()});
				    addGoodDetails(result, compName);
				    result.addGoodDetails
					(smh.getLocalString
					 (getClass().getName() + ".passed",
					  "Valid referenced bean [ {0} ].",
					  new Object[] {ejb_link}));
				    break;
				}
			    }
    
			} 
		    } catch(Exception e) {
			addErrorDetails(result, compName);
			result.addErrorDetails(smh.getLocalString
					       (getClass().getName() + ".failedException1",
						"Exception occured while opening or saving the J2EE archive.",
						new Object[] {}));
                logger.log(Level.FINE, "com.sun.enterprise.tools.verifier.testsprint",
                        new Object[] {"[" + getClass() + "] Error: " + e.getMessage()});

                        if (!oneFailed) {
                            oneFailed = true;
                        }
		    }
    
		    // before you go onto the next ejb-link, tell me whether you
		    // resolved the last ejb-link okay
		    if (!resolved) {
                        if (!oneFailed) {
                            oneFailed = true;
                        }
			addErrorDetails(result, compName);
			result.addErrorDetails(smh.getLocalString
					       (getClass().getName() + ".failed",
						"Error: No EJB matching [ {0} ] found within [ {1} ] jar file.",
						new Object[] {ejb_link, descriptor.getName()}));
		    } else {
			// clear the resolved flag for the next ejb-link 
			resolved =false;
		    }
    
		} else {
		    // Cannot get the link name of an ejb reference referring 
		    // to an external bean, The value of the ejb-link element 
		    // must be the ejb-name of an enterprise bean in the same 
		    // ejb-ear file.
		    addNaDetails(result, compName);
		    result.addNaDetails
			(smh.getLocalString
			 (getClass().getName() + ".notApplicable1",
			  "Warning:  Cannot verify the existence of an ejb reference [ {0} ] to external bean within different .ear file.",
			  new Object[] {nextEjbReference.getName()}));
		    na++;
		}
	    }

	    if (oneFailed) {
		result.setStatus(Result.FAILED);
	    } else if (na == descriptor.getEjbReferenceDescriptors().size()) {
		result.setStatus(Result.NOT_APPLICABLE);
	    } else {
		result.setStatus(Result.PASSED);
	    }
//            File tmpFile = new File(System.getProperty("java.io.tmpdir"));
//            tmpFile = new File(tmpFile, Verifier.TMPFILENAME + ".tmp");
//	    tmpFile.delete();
	    return result;

	} else {
	    addNaDetails(result, compName);
	    result.notApplicable(smh.getLocalString
				 (getClass().getName() + ".notApplicable",
				  "There are no ejb references to other beans within this bean [ {0} ]",  
				  new Object[] {descriptor.getName()}));
	}

	return result;
    }
}