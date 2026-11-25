package org.bouncycastle.util;

import net.bytebuddy.asm.Advice;
import org.bouncycastle.crypto.digests.SHA512tDigest;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: loaded from: classes13.dex */
public class Fingerprint {
    private static char[] encodingTable = {ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL, 'e', 'f'};
    private final byte[] fingerprint;

    public Fingerprint(byte[] bArr) {
        this.fingerprint = calculateFingerprint(bArr);
    }

    public static byte[] calculateFingerprint(byte[] bArr) {
        SHA512tDigest sHA512tDigest = new SHA512tDigest(160);
        sHA512tDigest.update(bArr, 0, bArr.length);
        byte[] bArr2 = new byte[sHA512tDigest.getDigestSize()];
        sHA512tDigest.doFinal(bArr2, 0);
        return bArr2;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Fingerprint) {
            return Arrays.areEqual(((Fingerprint) obj).fingerprint, this.fingerprint);
        }
        return false;
    }

    public byte[] getFingerprint() {
        return Arrays.clone(this.fingerprint);
    }

    public int hashCode() {
        return Arrays.hashCode(this.fingerprint);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i != this.fingerprint.length; i++) {
            if (i > 0) {
                stringBuffer.append(":");
            }
            stringBuffer.append(encodingTable[(this.fingerprint[i] >>> 4) & 15]);
            stringBuffer.append(encodingTable[this.fingerprint[i] & 15]);
        }
        return stringBuffer.toString();
    }
}
