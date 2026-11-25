package com.android.tools.r8.internal;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2977sE extends r implements Serializable {
    private final MessageDigest a;
    private final int b;
    private final boolean c;
    private final String d;

    C2977sE() {
        MessageDigest messageDigestA = a(McElieceCCA2KeyGenParameterSpec.SHA256);
        this.a = messageDigestA;
        this.b = messageDigestA.getDigestLength();
        this.d = (String) AbstractC3123uM.a("Hashing.sha256()");
        this.c = a(messageDigestA);
    }

    private static boolean a(MessageDigest messageDigest) {
        try {
            messageDigest.clone();
            return true;
        } catch (CloneNotSupportedException unused) {
            return false;
        }
    }

    public final String toString() {
        return this.d;
    }

    private static MessageDigest a(String str) {
        try {
            return MessageDigest.getInstance(str);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC3384xk
    public final InterfaceC3524zk a() {
        int i = 0;
        if (this.c) {
            try {
                return new C2906rE((MessageDigest) this.a.clone(), this.b, i);
            } catch (CloneNotSupportedException unused) {
            }
        }
        return new C2906rE(a(this.a.getAlgorithm()), this.b, i);
    }
}
