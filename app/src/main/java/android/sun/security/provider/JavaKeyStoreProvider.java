/*
 * Copyright (c) 2021, Muntashir Al-Islam. All rights reserved.
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
 */

package android.sun.security.provider;

import android.sun.security.pkcs12.PKCS12KeyStore;
import android.sun.security.provider.JavaKeyStore.JKS;
import android.sun.security.provider.JavaKeyStore.CaseExactJKS;

import java.security.Provider;
import java.security.Security;

public class JavaKeyStoreProvider extends Provider {
    public JavaKeyStoreProvider() {
        super("JKS", 1.0D, "Java KeyStore");
        this.put("KeyStore.JKS", JKS.class.getName());
        this.put("KeyStore.CaseExactJKS", CaseExactJKS.class.getName());
        this.put("KeyStore.PKCS12", PKCS12KeyStore.class.getName());
        Security.setProperty("keystore.type", "jks");
    }
}
