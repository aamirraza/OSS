/*
 * The contents of this file are subject to the terms 
 * of the Common Development and Distribution License 
 * (the "License").  You may not use this file except 
 * in compliance with the License.
 * 
 * You can obtain a copy of the license at 
 * glassfish/bootstrap/legal/CDDLv1.0.txt or 
 * https://glassfish.dev.java.net/public/CDDLv1.0.html. 
 * See the License for the specific language governing 
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL 
 * HEADER in each file and include the License file at 
 * glassfish/bootstrap/legal/CDDLv1.0.txt.  If applicable, 
 * add the following below this CDDL HEADER, with the 
 * fields enclosed by brackets "[]" replaced with your 
 * own identifying information: Portions Copyright [yyyy] 
 * [name of copyright owner]
 */

/*
 * @(#)BodyPart.java	1.6 05/08/29
 *
 * Copyright 1997-2005 Sun Microsystems, Inc. All Rights Reserved.
 */

package javax.mail;

/**
 * This class models a Part that is contained within a Multipart.
 * This is an abstract class. Subclasses provide actual implementations.<p>
 *
 * BodyPart implements the Part interface. Thus, it contains a set of
 * attributes and a "content".
 *
 * @author John Mani
 * @author Bill Shannon
 */

public abstract class BodyPart implements Part {

    /**
     * The <code>Multipart</code> object containing this <code>BodyPart</code>,
     * if known.
     * @since	JavaMail 1.1
     */
    protected Multipart parent;

    /**
     * Return the containing <code>Multipart</code> object,
     * or <code>null</code> if not known.
     */
    public Multipart getParent() {
	return parent;
    }

    /**
     * Set the parent of this <code>BodyPart</code> to be the specified
     * <code>Multipart</code>.  Normally called by <code>Multipart</code>'s
     * <code>addBodyPart</code> method.  <code>parent</code> may be
     * <code>null</code> if the <code>BodyPart</code> is being removed
     * from its containing <code>Multipart</code>.
     * @since	JavaMail 1.1
     */
    void setParent(Multipart parent) {
	this.parent = parent;
    }
}
