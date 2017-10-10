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



package com.sun.enterprise.config.serverbeans;

import org.jvnet.hk2.config.Attribute;
import org.jvnet.hk2.config.Configured;
import org.jvnet.hk2.config.Element;
import org.jvnet.hk2.config.ConfigBeanProxy;
import org.jvnet.hk2.config.types.Property;
import org.jvnet.hk2.config.types.PropertyBag;
import org.jvnet.hk2.component.Injectable;

import java.beans.PropertyVetoException;
import java.util.List;

import org.glassfish.api.admin.config.PropertiesDesc;

import org.glassfish.quality.ToDo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 *
 */

/* @XmlType(name = "", propOrder = {
    "webContainerAvailability",
    "ejbContainerAvailability",
    "jmsAvailability",
    "property"
}) */

@Configured

public interface AvailabilityService extends ConfigBeanProxy, Injectable, PropertyBag {

    /**
     * Gets the value of the availabilityEnabled property.
     *
     * This boolean flag controls whether availability is enabled for HTTP
     * session persistence. If this is "false", then session persistence is
     * disabled for all web modules in j2ee apps and stand-alone web modules.
     * If it is "true" (and providing that the global availability-enabled in
     * availability-service is also "true", then j2ee apps and stand-alone web
     * modules may be ha enabled. Finer-grained control exists at lower levels.
     * If this attribute is missing, it "inherits" the value of the global
     * availability-enabled under availability-service.
     *
     * @return possible object is
     *         {@link String }
     */
    @Attribute (defaultValue="true",dataType=Boolean.class)
    String getAvailabilityEnabled();

    /**
     * Sets the value of the availabilityEnabled property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    void setAvailabilityEnabled(String value) throws PropertyVetoException;

    /**
     * Gets the value of the haAgentHosts property.
     *
     * Comma-delimited list of server host names or IP addresses where
     * high availability store management agents are running.  
     *
     * @return possible object is
     *         {@link String }
     */
    @Attribute
    String getHaAgentHosts();

    /**
     * Sets the value of the haAgentHosts property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    void setHaAgentHosts(String value) throws PropertyVetoException;

    /**
     * Gets the value of the haAgentPort property.
     *
     * Port number where highly available store management agents
     * can be contacted
     * 
     * @return possible object is
     *         {@link String }
     */
    @Attribute
    @Max(value=65535)
    @Min(value=1)
    String getHaAgentPort();

    /**
     * Sets the value of the haAgentPort property.     
     *
     * @param value allowed object is
     *              {@link String }
     */
    void setHaAgentPort(String value) throws PropertyVetoException;

    /**
     * Gets the value of the haAgentPassword property.
     * password needed to contact highly available store management agents
     * 
     * @return possible object is
     *         {@link String }
     */
    @Attribute
    String getHaAgentPassword();

    /**
     * Sets the value of the haAgentPassword property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    void setHaAgentPassword(String value) throws PropertyVetoException;

    /**
     * Gets the value of the haStoreName property.
     * Name of the session store
     * 
     * @return possible object is
     *         {@link String }
     */
    @Attribute
    String getHaStoreName();

    /**
     * Sets the value of the haStoreName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    void setHaStoreName(String value) throws PropertyVetoException;

    /**
     * Gets the value of the autoManageHaStore property.
     * If set to true, the lifecycle of the highly available store
     * is matched with the lifecycle of the highly available cluster.
     * The store is started or stopped with the cluster. It is removed
     * when the cluster is deleted. When set to false, the store
     * lifecycle would have to manually managed by the administrator.
     *
     * @return possible object is
     *         {@link String }
     */
    @Attribute (defaultValue="false",dataType=Boolean.class)
    String getAutoManageHaStore();

    /**
     * Sets the value of the autoManageHaStore property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    void setAutoManageHaStore(String value) throws PropertyVetoException;

    /**
     * Gets the value of the storePoolName property.
     *
     * This is the jndi-name for the JDBC Connection Pool used potentially by
     * both the Web Container and the EJB Stateful Session Bean Container for
     * use in checkpointing/passivation when persistence-type = "ha".
     * See sfsb-ha-persistence-type and sfsb-persistence-type for more details.
     * It will default to "jdbc/hastore". This attribute can be over-ridden in
     * either web-container-availability (with http-session-store-pool-name)
     * and/or in ejb-container-availability (with sfsb-store-pool-name).
     * If store-pool-name is not over-ridden then both containers will share
     * the same connection pool. If either container over-rides then it may
     * have its own dedicated pool. In this case there must also be a new
     * corresponding JDBC Resource and JDBC Connection Pool defined for
     * this new pool name.
     * 
     * @return possible object is
     *         {@link String }
     */
    @Attribute
    String getStorePoolName();

    /**
     * Sets the value of the storePoolName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    void setStorePoolName(String value) throws PropertyVetoException;

    /**
     * Gets the value of the haStoreHealthcheckEnabled property.
     * 
     * Application server stops saving session state when the store service
     * does not function properly or is is not accessible for any reason.
     * When this attribute is set to true, periodic checking is done to
     * detect if the store service has become available again.
     * If healthcheck succeeds the session state saving is resumed. 
     *
     * @return possible object is
     *         {@link String }
     */
    @Attribute (defaultValue="false",dataType=Boolean.class)
    String getHaStoreHealthcheckEnabled();

    /**
     * Sets the value of the haStoreHealthcheckEnabled property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    void setHaStoreHealthcheckEnabled(String value) throws PropertyVetoException;

    /**
     * Gets the value of the haStoreHealthcheckIntervalInSeconds property.
     *
     * The periodicity at which store health is checked.
     * 
     * @return possible object is
     *         {@link String }
     */
    @Attribute (defaultValue="5")
    @Min(value=1)
    String getHaStoreHealthcheckIntervalInSeconds();

    /**
     * Sets the value of the haStoreHealthcheckIntervalInSeconds property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    void setHaStoreHealthcheckIntervalInSeconds(String value) throws PropertyVetoException;

    /**
     * Gets the value of the webContainerAvailability property.
     *
     * @return possible object is
     *         {@link WebContainerAvailability }
     */
    @Element
    WebContainerAvailability getWebContainerAvailability();

    /**
     * Sets the value of the webContainerAvailability property.
     *
     * @param value allowed object is
     *              {@link WebContainerAvailability }
     */
    void setWebContainerAvailability(WebContainerAvailability value) throws PropertyVetoException;

    /**
     * Gets the value of the ejbContainerAvailability property.
     *
     * @return possible object is
     *         {@link EjbContainerAvailability }
     */
    @Element
    EjbContainerAvailability getEjbContainerAvailability();

    /**
     * Sets the value of the ejbContainerAvailability property.
     *
     * @param value allowed object is
     *              {@link EjbContainerAvailability }
     */
    void setEjbContainerAvailability(EjbContainerAvailability value) throws PropertyVetoException;

    /**
     * Gets the value of the jmsAvailability property.
     *
     * @return possible object is
     *         {@link JmsAvailability }
     */
    @Element
    JmsAvailability getJmsAvailability();

    /**
     * Sets the value of the jmsAvailability property.
     *
     * @param value allowed object is
     *              {@link JmsAvailability }
     */
    void setJmsAvailability(JmsAvailability value) throws PropertyVetoException;
    
    /**
    	Properties as per {@link PropertyBag}
     */
    @ToDo(priority=ToDo.Priority.IMPORTANT, details="Provide PropertyDesc for legal props" )
    @PropertiesDesc(props={})
    @Element
    List<Property> getProperty();
}