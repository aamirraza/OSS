/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2009 Sun Microsystems, Inc. All rights reserved.
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
package org.glassfish.kernel.embedded;

import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.component.Habitat;
import org.glassfish.api.embedded.Ports;
import org.glassfish.api.embedded.Port;

import java.io.IOException;
import java.util.*;

import com.sun.grizzly.config.dom.NetworkConfig;
import com.sun.grizzly.config.dom.NetworkListener;

/**
 * @author Jerome Dochez
 */
@Service
public class PortsImpl implements Ports {


    @Inject
    NetworkConfig network;

    @Inject
    Habitat habitat;

    final Map<Integer, Port> ports = new HashMap<Integer, Port>();

    public Port createPort(int number) throws IOException {
        return createPort(Integer.valueOf(number));
    }

    private Port createPort(Integer portNumber) throws IOException {

        for (NetworkListener nl : network.getNetworkListeners().getNetworkListener()) {
            if (nl.getPort().equals(portNumber.toString())) {
                throw new IOException("Port " + portNumber + " is already configured");
            }
        }
        for (Integer pn : ports.keySet()) {
            if (pn.equals(portNumber)) {
                throw new IOException("Port " + portNumber + " is alredy open");
            }
        }
        PortImpl port = habitat.getComponent(PortImpl.class);
        port.bind(portNumber);
        ports.put(portNumber, port);
        return port;    }

    public Collection<Port> getPorts() {
        return ports.values();
    }

    public void remove(Port port) {
        ports.remove(port.getPortNumber());
    }
}
