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
package com.sun.enterprise.security.cli;

import com.sun.enterprise.util.SystemPropertyConstants;
import org.glassfish.api.admin.AdminCommand;
import org.glassfish.api.admin.AdminCommandContext;
import org.glassfish.api.I18n;
import org.glassfish.api.Param;
import org.glassfish.api.ActionReport;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.annotations.Scoped;
import org.jvnet.hk2.component.PerLookup;
import com.sun.enterprise.util.LocalStringManagerImpl;
import com.sun.enterprise.security.store.PasswordAdapter;
import com.sun.enterprise.security.store.IdentityManager;
/**
 * Create Password Alias Command
 *
 * Usage: create-password-alias [--terse=false] [--echo=false] 
 *        [--interactive=true] [--host localhost] [--port 4848|4849] 
 *        [--secure | -s] [--user admin_user] [--passwordfile file_name] aliasname
 *  
 * Result of the command is that:
 * <domain-dir>/<domain-name>/config/domain-passwords file gets appended with 
 * the entry of the form: aliasname=<password encrypted with masterpassword>
 *
 * A user can use this aliased password now in setting passwords in domin.xml. 
 * Benefit is it is in NON-CLEAR-TEXT
 *
 * domain.xml example entry is:
 * <provider-config class-name="com.sun.xml.wss.provider.ClientSecurityAuthModule" 
 *                  provider-id="XWS_ClientProvider" provider-type="client">
 *      <property name="password" value="${ALIAS=myalias}/>
 * </provider-config> 
 *
 * @author Nandini Ektare
 */

@Service(name="create-password-alias")
@Scoped(PerLookup.class)
@I18n("create.password.alias")
public class CreatePasswordAlias implements AdminCommand {
    
    final private static LocalStringManagerImpl localStrings = 
        new LocalStringManagerImpl(CreatePasswordAlias.class);    

    @Param(name="aliasname", primary=true)
    String aliasName;
    
    @Param(name="aliaspassword", password=true)
    String aliasPassword;
    
    /**
     * Executes the command with the command parameters passed as Properties
     * where the keys are paramter names and the values the parameter values
     *
     * @param context information
     */
    public void execute(AdminCommandContext context) {
        final ActionReport report = context.getActionReport();
        
        try {
            String mp = System.getProperty(
                        SystemPropertyConstants.TRUSTSTORE_PASSWORD_PROPERTY);
            if (mp == null) 
                mp = System.getProperty(
                        SystemPropertyConstants.KEYSTORE_PASSWORD_PROPERTY);

            if (mp == null)
                mp = IdentityManager.getMasterPassword();

            if (mp == null) // nothing works. try with defaults
                mp = "changeit";
            
            PasswordAdapter pa = new PasswordAdapter(mp.toCharArray());
            if (pa.getPasswordForAlias(aliasName) != null) {
                report.setMessage(localStrings.getLocalString(
                    "create.password.alias.alreadyexists",
                    "Password alias with the specified name already exists. " +
                    "Please use the update-password-alias command to change it",
                    aliasName));
                report.setActionExitCode(ActionReport.ExitCode.FAILURE);
                return;
            }

            pa.setPasswordForAlias(aliasName,aliasPassword.getBytes());

        } catch (Exception ex) {
            ex.printStackTrace();
            report.setMessage(localStrings.getLocalString(
                "create.password.alias.fail", 
                "Creation of Password Alias {0} failed", aliasName)+
                              "  " + ex.getLocalizedMessage());
            report.setActionExitCode(ActionReport.ExitCode.FAILURE);
            report.setFailureCause(ex);
            return;            
        }
        report.setActionExitCode(ActionReport.ExitCode.SUCCESS);
        report.setMessage(localStrings.getLocalString(
            "create.password.alias.success",
            "Encrypted password for the alias name {0} stored successfully",
            aliasName));        
    }       
}