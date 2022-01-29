/*
 * Copyright (c) 1997, 2009, Oracle and/or its affiliates. All rights reserved.
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

import android.sun.security.util.ObjectIdentifier;

import java.util.*;
import java.io.IOException;

import java.security.cert.CertificateException;

/**
 * This class defines the mapping from OID & name to classes and vice
 * versa.  Used by CertificateExtensions & PKCS10 to get the java
 * classes associated with a particular OID/name.
 *
 * @author Amit Kapoor
 * @author Hemma Prafullchandra
 * @author Andreas Sterbenz
 *
 */
public class OIDMap {

    private OIDMap() {
        // empty
    }

    // "user-friendly" names
    private static final String ROOT = X509CertImpl.NAME + "." +
                                 android.sun.security.x509.X509CertInfo.NAME + "." +
                                 X509CertInfo.EXTENSIONS;
    private static final String AUTH_KEY_IDENTIFIER = ROOT + "." +
                                          AuthorityKeyIdentifierExtension.NAME;
    private static final String SUB_KEY_IDENTIFIER  = ROOT + "." +
                                          SubjectKeyIdentifierExtension.NAME;
    private static final String KEY_USAGE           = ROOT + "." +
                                          KeyUsageExtension.NAME;
    private static final String PRIVATE_KEY_USAGE   = ROOT + "." +
                                          PrivateKeyUsageExtension.NAME;
    private static final String POLICY_MAPPINGS     = ROOT + "." +
                                          PolicyMappingsExtension.NAME;
    private static final String SUB_ALT_NAME        = ROOT + "." +
                                          SubjectAlternativeNameExtension.NAME;
    private static final String ISSUER_ALT_NAME     = ROOT + "." +
                                          IssuerAlternativeNameExtension.NAME;
    private static final String BASIC_CONSTRAINTS   = ROOT + "." +
                                          BasicConstraintsExtension.NAME;
    private static final String NAME_CONSTRAINTS    = ROOT + "." +
                                          NameConstraintsExtension.NAME;
    private static final String POLICY_CONSTRAINTS  = ROOT + "." +
                                          PolicyConstraintsExtension.NAME;
    private static final String CRL_NUMBER  = ROOT + "." +
                                              CRLNumberExtension.NAME;
    private static final String CRL_REASON  = ROOT + "." +
                                              CRLReasonCodeExtension.NAME;
    private static final String NETSCAPE_CERT  = ROOT + "." +
                                              NetscapeCertTypeExtension.NAME;
    private static final String CERT_POLICIES = ROOT + "." +
                                             CertificatePoliciesExtension.NAME;
    private static final String EXT_KEY_USAGE       = ROOT + "." +
                                          ExtendedKeyUsageExtension.NAME;
    private static final String INHIBIT_ANY_POLICY  = ROOT + "." +
                                          InhibitAnyPolicyExtension.NAME;
    private static final String CRL_DIST_POINTS = ROOT + "." +
                                        CRLDistributionPointsExtension.NAME;

    private static final String CERT_ISSUER = ROOT + "." +
                                        CertificateIssuerExtension.NAME;
    private static final String SUBJECT_INFO_ACCESS = ROOT + "." +
                                          SubjectInfoAccessExtension.NAME;
    private static final String AUTH_INFO_ACCESS = ROOT + "." +
                                          AuthorityInfoAccessExtension.NAME;
    private static final String ISSUING_DIST_POINT = ROOT + "." +
                                        IssuingDistributionPointExtension.NAME;
    private static final String DELTA_CRL_INDICATOR = ROOT + "." +
                                        DeltaCRLIndicatorExtension.NAME;
    private static final String FRESHEST_CRL = ROOT + "." +
                                        FreshestCRLExtension.NAME;
    private static final String OCSPNOCHECK = ROOT + "." +
                                        OCSPNoCheckExtension.NAME;

    private static final int NetscapeCertType_data[] =
        { 2, 16, 840, 1, 113730, 1, 1 };

    /** Map ObjectIdentifier(oid) -> OIDInfo(info) */
    private final static Map<android.sun.security.util.ObjectIdentifier,OIDInfo> oidMap;

    /** Map String(friendly name) -> OIDInfo(info) */
    private final static Map<String,OIDInfo> nameMap;

    static {
        oidMap = new HashMap<android.sun.security.util.ObjectIdentifier,OIDInfo>();
        nameMap = new HashMap<String,OIDInfo>();
        addInternal(SUB_KEY_IDENTIFIER, android.sun.security.x509.PKIXExtensions.SubjectKey_Id,
                    "SubjectKeyIdentifierExtension");
        addInternal(KEY_USAGE, android.sun.security.x509.PKIXExtensions.KeyUsage_Id,
                    "KeyUsageExtension");
        addInternal(PRIVATE_KEY_USAGE, android.sun.security.x509.PKIXExtensions.PrivateKeyUsage_Id,
                    "PrivateKeyUsageExtension");
        addInternal(SUB_ALT_NAME, android.sun.security.x509.PKIXExtensions.SubjectAlternativeName_Id,
                    "SubjectAlternativeNameExtension");
        addInternal(ISSUER_ALT_NAME, android.sun.security.x509.PKIXExtensions.IssuerAlternativeName_Id,
                    "IssuerAlternativeNameExtension");
        addInternal(BASIC_CONSTRAINTS, android.sun.security.x509.PKIXExtensions.BasicConstraints_Id,
                    "BasicConstraintsExtension");
        addInternal(CRL_NUMBER, android.sun.security.x509.PKIXExtensions.CRLNumber_Id,
                    "CRLNumberExtension");
        addInternal(CRL_REASON, android.sun.security.x509.PKIXExtensions.ReasonCode_Id,
                    "CRLReasonCodeExtension");
        addInternal(NAME_CONSTRAINTS, android.sun.security.x509.PKIXExtensions.NameConstraints_Id,
                    "NameConstraintsExtension");
        addInternal(POLICY_MAPPINGS, android.sun.security.x509.PKIXExtensions.PolicyMappings_Id,
                    "PolicyMappingsExtension");
        addInternal(AUTH_KEY_IDENTIFIER, android.sun.security.x509.PKIXExtensions.AuthorityKey_Id,
                    "AuthorityKeyIdentifierExtension");
        addInternal(POLICY_CONSTRAINTS, android.sun.security.x509.PKIXExtensions.PolicyConstraints_Id,
                    "PolicyConstraintsExtension");
        addInternal(NETSCAPE_CERT, android.sun.security.util.ObjectIdentifier.newInternal
                    (new int[] {2,16,840,1,113730,1,1}),
                    "NetscapeCertTypeExtension");
        addInternal(CERT_POLICIES, android.sun.security.x509.PKIXExtensions.CertificatePolicies_Id,
                    "CertificatePoliciesExtension");
        addInternal(EXT_KEY_USAGE, android.sun.security.x509.PKIXExtensions.ExtendedKeyUsage_Id,
                    "ExtendedKeyUsageExtension");
        addInternal(INHIBIT_ANY_POLICY, android.sun.security.x509.PKIXExtensions.InhibitAnyPolicy_Id,
                    "InhibitAnyPolicyExtension");
        addInternal(CRL_DIST_POINTS, android.sun.security.x509.PKIXExtensions.CRLDistributionPoints_Id,
                    "CRLDistributionPointsExtension");
        addInternal(CERT_ISSUER, android.sun.security.x509.PKIXExtensions.CertificateIssuer_Id,
                    "CertificateIssuerExtension");
        addInternal(SUBJECT_INFO_ACCESS, android.sun.security.x509.PKIXExtensions.SubjectInfoAccess_Id,
                    "SubjectInfoAccessExtension");
        addInternal(AUTH_INFO_ACCESS, android.sun.security.x509.PKIXExtensions.AuthInfoAccess_Id,
                    "AuthorityInfoAccessExtension");
        addInternal(ISSUING_DIST_POINT,
                    android.sun.security.x509.PKIXExtensions.IssuingDistributionPoint_Id,
                    "IssuingDistributionPointExtension");
        addInternal(DELTA_CRL_INDICATOR, android.sun.security.x509.PKIXExtensions.DeltaCRLIndicator_Id,
                    "DeltaCRLIndicatorExtension");
        addInternal(FRESHEST_CRL, android.sun.security.x509.PKIXExtensions.FreshestCRL_Id,
                    "FreshestCRLExtension");
        addInternal(OCSPNOCHECK, PKIXExtensions.OCSPNoCheck_Id,
                    "OCSPNoCheckExtension");
    }

    /**
     * Add attributes to the table. For internal use in the static
     * initializer.
     */
    private static void addInternal(String name, android.sun.security.util.ObjectIdentifier oid,
            String className) {
        OIDInfo info = new OIDInfo(name, oid, className);
        oidMap.put(oid, info);
        nameMap.put(name, info);
    }

    /**
     * Inner class encapsulating the mapping info and Class loading.
     */
    private static class OIDInfo {

        final android.sun.security.util.ObjectIdentifier oid;
        final String name;
        final String className;
        private volatile Class clazz;

        OIDInfo(String name, ObjectIdentifier oid, String className) {
            this.name = name;
            this.oid = oid;
            this.className = className;
        }

        OIDInfo(String name, android.sun.security.util.ObjectIdentifier oid, Class clazz) {
            this.name = name;
            this.oid = oid;
            this.className = clazz.getName();
            this.clazz = clazz;
        }

        /**
         * Return the Class object associated with this attribute.
         */
        Class getClazz() throws CertificateException {
            try {
                Class c = clazz;
                if (c == null) {
                    c = Class.forName(className);
                    clazz = c;
                }
                return c;
            } catch (ClassNotFoundException e) {
                throw (CertificateException)new CertificateException
                                ("Could not load class: " + e).initCause(e);
            }
        }
    }

    /**
     * Add a name to lookup table.
     *
     * @param name the name of the attr
     * @param oid the string representation of the object identifier for
     *         the class.
     * @param clazz the Class object associated with this attribute
     * @exception CertificateException on errors.
     */
    public static void addAttribute(String name, String oid, Class clazz)
            throws CertificateException {
        android.sun.security.util.ObjectIdentifier objId;
        try {
            objId = new android.sun.security.util.ObjectIdentifier(oid);
        } catch (IOException ioe) {
            throw new CertificateException
                                ("Invalid Object identifier: " + oid);
        }
        OIDInfo info = new OIDInfo(name, objId, clazz);
        if (oidMap.put(objId, info) != null) {
            throw new CertificateException
                                ("Object identifier already exists: " + oid);
        }
        if (nameMap.put(name, info) != null) {
            throw new CertificateException("Name already exists: " + name);
        }
    }

    /**
     * Return user friendly name associated with the OID.
     *
     * @param oid the name of the object identifier to be returned.
     * @return the user friendly name or null if no name
     * is registered for this oid.
     */
    public static String getName(android.sun.security.util.ObjectIdentifier oid) {
        OIDInfo info = oidMap.get(oid);
        return (info == null) ? null : info.name;
    }

    /**
     * Return Object identifier for user friendly name.
     *
     * @param name the user friendly name.
     * @return the Object Identifier or null if no oid
     * is registered for this name.
     */
    public static android.sun.security.util.ObjectIdentifier getOID(String name) {
        OIDInfo info = nameMap.get(name);
        return (info == null) ? null : info.oid;
    }

    /**
     * Return the java class object associated with the user friendly name.
     *
     * @param name the user friendly name.
     * @exception CertificateException if class cannot be instantiated.
     */
    public static Class getClass(String name) throws CertificateException {
        OIDInfo info = nameMap.get(name);
        return (info == null) ? null : info.getClazz();
    }

    /**
     * Return the java class object associated with the object identifier.
     *
     * @param oid the name of the object identifier to be returned.
     * @exception CertificateException if class cannot be instatiated.
     */
    public static Class getClass(android.sun.security.util.ObjectIdentifier oid)
            throws CertificateException {
        OIDInfo info = oidMap.get(oid);
        return (info == null) ? null : info.getClazz();
    }

}
