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
package com.sun.enterprise.deployment.node.runtime;

import com.sun.enterprise.deployment.ResourcePrincipal;
import com.sun.enterprise.deployment.node.DeploymentDescriptorNode;
import com.sun.enterprise.deployment.node.XMLElement;
import com.sun.enterprise.deployment.runtime.MdbConnectionFactoryDescriptor;
import com.sun.enterprise.deployment.xml.RuntimeTagNames;
import org.w3c.dom.Node;

import java.util.Map;

public class MDBConnectionFactoryNode extends DeploymentDescriptorNode {
    
    protected MdbConnectionFactoryDescriptor descriptor;
    
    public MDBConnectionFactoryNode() {
	registerElementHandler(new XMLElement(RuntimeTagNames.DEFAULT_RESOURCE_PRINCIPAL), DefaultResourcePrincipalNode.class);
    }
    
    
   /**
    * @return the descriptor instance to associate with this XMLNode
    */    
    public Object getDescriptor() {
	if (descriptor==null) {
	    descriptor = new MdbConnectionFactoryDescriptor();
	} 
	return descriptor;
    }
    
    /**
     * all sub-implementation of this class can use a dispatch table to map xml element to
     * method name on the descriptor class for setting the element value. 
     *  
     * @return the map with the element name as a key, the setter method as a value
     */    
    protected Map getDispatchTable() {  
	Map dispatchTable = super.getDispatchTable();
	dispatchTable.put(RuntimeTagNames.JNDI_NAME, "setJndiName");
	return dispatchTable;
    }
    
    /**
     * Adds  a new DOL descriptor instance to the descriptor instance associated with 
     * this XMLNode
     *
     * @param descriptor the new descriptor
     */
    public void addDescriptor(Object newDescriptor) {
	if (newDescriptor instanceof ResourcePrincipal) {
	    descriptor.setDefaultResourcePrincipal((ResourcePrincipal) newDescriptor);
	} else super.addDescriptor(newDescriptor);
    }
    
    /**
     * write the descriptor class to a DOM tree and return it
     *
     * @param parent node for the DOM tree
     * @param node name for the descriptor
     * @param the descriptor to write
     * @return the DOM tree top node
     */    
    public Node writeDescriptor(Node parent, String nodeName, MdbConnectionFactoryDescriptor mcf) {    
	Node mcfNode = super.writeDescriptor(parent, nodeName, mcf);
	appendTextChild(mcfNode, RuntimeTagNames.JNDI_NAME, mcf.getJndiName());
        if (mcf.getDefaultResourcePrincipal()!=null) {
            DefaultResourcePrincipalNode subNode = new DefaultResourcePrincipalNode();
            subNode.writeDescriptor(mcfNode, RuntimeTagNames.DEFAULT_RESOURCE_PRINCIPAL, mcf.getDefaultResourcePrincipal());
        }
	return mcfNode;
    }
}
