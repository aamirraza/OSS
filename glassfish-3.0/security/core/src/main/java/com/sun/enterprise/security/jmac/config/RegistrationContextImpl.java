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

package com.sun.enterprise.security.jmac.config;

import javax.security.auth.message.config.AuthConfigFactory.RegistrationContext;

/*
 * Class used by GFAuthConfigFactory and EntryInfo.
 *
 * This class will not be used outside of its package.
 */
final class RegistrationContextImpl implements RegistrationContext {
    private final String messageLayer;
    private final String appContext;
    private final String description;
    private final boolean isPersistent;

    RegistrationContextImpl(String messageLayer, String appContext,
        String description, boolean persistent) {
        
        this.messageLayer = messageLayer;
        this.appContext = appContext;
        this.description = description;
        this.isPersistent = persistent;
    }

    // helper method to create impl class
    RegistrationContextImpl(RegistrationContext ctx) {
        this.messageLayer = ctx.getMessageLayer();
        this.appContext = ctx.getAppContext();
        this.description = ctx.getDescription();
        this.isPersistent = ctx.isPersistent();
    }
    
    public String getMessageLayer() {
        return messageLayer;
    }

    public String getAppContext() {
        return appContext;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPersistent() {
        return isPersistent;
    }
 
    public boolean equals(Object o) {
        if (o == null || !(o instanceof RegistrationContext)) {
            return false;
        }
        RegistrationContext target = (RegistrationContext) o;
        return ( EntryInfo.matchStrings(
            messageLayer, target.getMessageLayer()) &&
            EntryInfo.matchStrings(appContext, target.getAppContext()) &&
            EntryInfo.matchStrings(description, target.getDescription()) );
    }
}