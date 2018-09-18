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

import java.util.Enumeration;
import java.util.List;

import org.glassfish.api.admin.AdminCommand;
import org.glassfish.api.admin.AdminCommandContext;
import org.glassfish.api.I18n;
import org.glassfish.api.Param;
import org.glassfish.api.ActionReport;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.annotations.Scoped;
import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.component.PerLookup;
import org.jvnet.hk2.config.types.Property;
import com.sun.enterprise.config.serverbeans.Configs;
import com.sun.enterprise.config.serverbeans.Config;
import com.sun.enterprise.util.LocalStringManagerImpl;
import com.sun.enterprise.config.serverbeans.AuthRealm;
import com.sun.enterprise.security.auth.realm.file.FileRealm;
import com.sun.enterprise.security.auth.realm.BadRealmException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;
import com.sun.enterprise.security.auth.realm.NoSuchRealmException;
import com.sun.enterprise.config.serverbeans.SecurityService;

/**
 * List File GroupsCommand
 * Usage: list-file-groups [--terse={true|false}][ --echo={true|false} ]
 *        [ --interactive={true|false} ] [--host  host] [--port port]
 *        [--secure| -s ] [--user  admin_user] [--passwordfile filename]
 *        [--help] [--name username] [--authrealmname auth_realm_name] [ target]
 *
 * @author Nandini Ektare
 */

@Service(name="list-file-groups")
@Scoped(PerLookup.class)
@I18n("list.file.group")

public class ListFileGroup implements AdminCommand {

    final private static LocalStringManagerImpl localStrings =
        new LocalStringManagerImpl(ListFileGroup.class);

    @Param(name="authrealmname", optional=true)
    String authRealmName;

    @Param(name="name", optional=true)
    String fileUserName;

    @Param(optional=true)
    String target;

    @Inject
    Configs configs;

    /**
     * Executes the command with the command parameters passed as Properties
     * where the keys are the paramter names and the values the parameter values
     *
     * @param context information
     */
    public void execute(AdminCommandContext context) {

        final ActionReport report = context.getActionReport();

        List <Config> configList = configs.getConfig();
        Config config = configList.get(0);
        SecurityService securityService = config.getSecurityService();

        // ensure we have the file authrealm
        if (authRealmName == null)
            authRealmName = securityService.getDefaultRealm();
 
        AuthRealm fileAuthRealm = null;
        for (AuthRealm authRealm : securityService.getAuthRealm()) {
            if (authRealm.getName().equals(authRealmName))
                fileAuthRealm = authRealm;
        }

        if (fileAuthRealm == null) {
            report.setMessage(localStrings.getLocalString(
                "list.file.group.filerealmnotfound",
                "File realm {0} does not exist", authRealmName));
            report.setActionExitCode(ActionReport.ExitCode.FAILURE);
            return;
        }

        try {
            // Get all users of this file realm. If a username has
            // been passed in through the --name CLI option use that
            FileRealm fr = getFileRealm(fileAuthRealm, report);

            if (fr == null) {
                // the getFileRealm method would have filled
                // in the right cause of this situation
                return;
            }
            
            Enumeration groups = null;
            if (fileUserName != null) {
                groups = fr.getGroupNames(fileUserName);
            } else {
                groups = fr.getGroupNames();
            }

            report.getTopMessagePart().setMessage(localStrings.getLocalString(
                "list.file.group.success",
                "list-file-groups successful"));
            report.getTopMessagePart().setChildrenType("file-group");
            while (groups.hasMoreElements()) {
                final ActionReport.MessagePart part =
                     report.getTopMessagePart().addChild();
                part.setMessage((String) groups.nextElement());
            }
        } catch (BadRealmException e) {
            report.setMessage(
                localStrings.getLocalString(
                    "list.file.group.realmcorrupted",
                    "Configured file realm {0} is corrupted.", authRealmName) +
                "  " + e.getLocalizedMessage());
            report.setActionExitCode(ActionReport.ExitCode.FAILURE);
            report.setFailureCause(e);
        } catch (NoSuchUserException e) {
            report.setMessage(
                localStrings.getLocalString(
                    "list.file.group.usernotfound",
                    "Specified file user {0} not found.", fileUserName) +
                "  " + e.getLocalizedMessage());
            report.setActionExitCode(ActionReport.ExitCode.FAILURE);
            report.setFailureCause(e);
        }
    }

    private FileRealm getFileRealm(AuthRealm fileAuthRealm, ActionReport report) {
        // Get FileRealm class name, match it with what is expected.
        String fileRealmClassName = fileAuthRealm.getClassname();

        // Report error if provided impl is not the one expected
        if (fileRealmClassName != null &&
            !fileRealmClassName.equals(
                "com.sun.enterprise.security.auth.realm.file.FileRealm")) {
            report.setMessage(
                localStrings.getLocalString(
                    "list.file.user.realmnotsupported",
                    "Configured file realm {0} is not supported.",
                    fileRealmClassName));
            report.setActionExitCode(ActionReport.ExitCode.FAILURE);
            return null;
        }

        // ensure we have the file associated with the authrealm
        String keyFile = null;
        for (Property fileProp : fileAuthRealm.getProperty()) {
            if (fileProp.getName().equals("file"))
                keyFile = fileProp.getValue();
        }
        if (keyFile == null) {
            report.setMessage(
                localStrings.getLocalString("list.file.user.keyfilenotfound",
                "There is no physical file associated with this file realm {0} ",
                authRealmName));
            report.setActionExitCode(ActionReport.ExitCode.FAILURE);
            return null;
        }

        // We have the right impl so let's try to remove one
        FileRealm fr = null;
        try {
            fr = new FileRealm(keyFile);
        } catch(BadRealmException e) {
            report.setMessage(
                localStrings.getLocalString(
                    "list.file.user.realmcorrupted",
                    "Configured file realm {0} is corrupted.", authRealmName) +
                "  " + e.getLocalizedMessage());
            report.setActionExitCode(ActionReport.ExitCode.FAILURE);
            report.setFailureCause(e);
        } catch(NoSuchRealmException e) {
            report.setMessage(
                localStrings.getLocalString(
                    "list.file.user.realmnotsupported",
                    "Configured file realm {0} is not supported.", authRealmName) +
                "  " + e.getLocalizedMessage());
            report.setActionExitCode(ActionReport.ExitCode.FAILURE);
            report.setFailureCause(e);
        }
        return fr;
    }
}