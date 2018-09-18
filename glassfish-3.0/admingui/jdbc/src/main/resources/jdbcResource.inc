
<!--
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 
 Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 
 The contents of this file are subject to the terms of either the GNU
 General Public License Version 2 only ("GPL") or the Common Development
 and Distribution License("CDDL") (collectively, the "License").  You
 may not use this file except in compliance with the License. You can obtain
 a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 language governing permissions and limitations under the License.
 
 When distributing the software, include this License Header Notice in each
 file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 Sun designates this particular file as subject to the "Classpath" exception
 as provided by Sun in the GPL Version 2 section of the License file that
 accompanied this code.  If applicable, add the following below the License
 Header, with the fields enclosed by brackets [] replaced by your own
 identifying information: "Portions Copyrighted [year]
 [name of copyright owner]"
 
 Contributor(s):
 
 If you wish your version of this file to be governed by only the CDDL or
 only the GPL Version 2, indicate your decision by adding "[Contributor]
 elects to include this software in this distribution under the [CDDL or GPL
 Version 2] license."  If you don't indicate a single choice of license, a
 recipient has the option to distribute your version of this file under
 either the CDDL, the GPL Version 2 or to extend the choice of license to
 its licensees as provided above.  However, if you add GPL Version 2 code
 and therefore, elected the GPL Version 2 license, then the option applies
 only if the new code is made subject to such option by the copyright
 holder.
-->
<!-- jdbc/jdbcResource.inc -->
<!-- used by jdbcResourceEdit.jsf and jdbcResourceNew.jsf -->
 

<sun:propertySheet id="propertySheet">
    <!-- Text Field section -->
    <sun:propertySheetSection id="propertSectionTextField">
        <sun:property id="nameNew"  rendered="#{!edit}" labelAlign="left" noWrap="#{true}" overlapLabel="#{false}" label="$resource{i18n.common.jndiName}">
            <sun:textField id="name" styleClass="required"  required="#{true}" columns="$int{30}" text="#{pageSession.valueMap['Name']}" />
        </sun:property>

        <sun:property id="name"  rendered="#{edit}" labelAlign="left" noWrap="#{true}" overlapLabel="#{false}" label="$resource{i18n.common.jndiName}">
            <sun:staticText id="name" text="#{pageSession.Name}"  rendered="#{Edit}" />
        </sun:property>

        <sun:property id="poolNameProp"  labelAlign="left" noWrap="#{true}" overlapLabel="#{false}" label="$resource{i18n.common.poolName}"  helpText="$resource{i18njdbc.jdbcResource.poolHelp}">
            <sun:dropDown id="PoolName" selected="#{pageSession.valueMap['PoolName']}" labels="$pageSession{jdbcList}"  values="$pageSession{jdbcList}" >
            <!beforeCreate
                getChildrenByType(parentObjectNameStr="amx:pp=/domain,type=resources" type="jdbc-connection-pool" result="#{pageSession.jdbcList}");
            />
           </sun:dropDown>
        </sun:property>   

        <sun:property id="descProp" labelAlign="left" noWrap="#{true}" overlapLabel="#{false}" label="$resource{i18n.common.description}">
            <sun:textField id="desc" columns="$int{55}" text="#{pageSession.valueMap['Description']}" />      
        </sun:property>

        <sun:property id="statusProp"  labelAlign="left" noWrap="#{true}" overlapLabel="#{false}" label="$resource{i18n.common.status}">
            <sun:checkbox label="$resource{i18n.common.Enabled}" selected="#{pageSession.valueMap['Enabled']}" selectedValue="true" visible="#{false}" />
            <sun:checkbox label="$resource{i18n.common.Enabled}" immediate="#{true}" selected="#{pageSession.valueMap2['Enabled']}" selectedValue="true" />
        </sun:property>
        "<br /><br />
    </sun:propertySheetSection>
</sun:propertySheet>    

