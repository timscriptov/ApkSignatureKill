/*
 * Copyright (c) 1997, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package android.sun.security.x509;

import android.sun.security.util.DerValue;

import java.io.IOException;

/**
 * Represent the CertificatePolicyMap ASN.1 object.
 *
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 */
public class CertificatePolicyMap {
    private android.sun.security.x509.CertificatePolicyId issuerDomain;
    private android.sun.security.x509.CertificatePolicyId subjectDomain;

    /**
     * Create a CertificatePolicyMap with the passed CertificatePolicyId's.
     *
     * @param issuer the CertificatePolicyId for the issuer CA.
     * @param subject the CertificatePolicyId for the subject CA.
     */
    public CertificatePolicyMap(android.sun.security.x509.CertificatePolicyId issuer,
                                android.sun.security.x509.CertificatePolicyId subject) {
        this.issuerDomain = issuer;
        this.subjectDomain = subject;
    }

    /**
     * Create the CertificatePolicyMap from the DER encoded value.
     *
     * @param val the DER encoded value of the same.
     */
    public CertificatePolicyMap(android.sun.security.util.DerValue val) throws IOException {
        if (val.tag != DerValue.tag_Sequence) {
            throw new IOException("Invalid encoding for CertificatePolicyMap");
        }
        issuerDomain = new android.sun.security.x509.CertificatePolicyId(val.data.getDerValue());
        subjectDomain = new android.sun.security.x509.CertificatePolicyId(val.data.getDerValue());
    }

    /**
     * Return the issuer CA part of the policy map.
     */
    public android.sun.security.x509.CertificatePolicyId getIssuerIdentifier() {
        return (issuerDomain);
    }

    /**
     * Return the subject CA part of the policy map.
     */
    public CertificatePolicyId getSubjectIdentifier() {
        return (subjectDomain);
    }

    /**
     * Returns a printable representation of the CertificatePolicyId.
     */
    public String toString() {
        String s = "CertificatePolicyMap: [\n"
                 + "IssuerDomain:" + issuerDomain.toString()
                 + "SubjectDomain:" + subjectDomain.toString()
                 + "]\n";

        return (s);
    }

    /**
     * Write the CertificatePolicyMap to the DerOutputStream.
     *
     * @param out the DerOutputStream to write the object to.
     * @exception IOException on errors.
     */
    public void encode(android.sun.security.util.DerOutputStream out) throws IOException {
        android.sun.security.util.DerOutputStream tmp = new android.sun.security.util.DerOutputStream();

        issuerDomain.encode(tmp);
        subjectDomain.encode(tmp);
        out.write(android.sun.security.util.DerValue.tag_Sequence,tmp);
    }
}
