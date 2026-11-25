package org.eclipse.jgit.transport;

import com.beust.jcommander.Parameters;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.eclipse.jgit.lib.Repository;

/* JADX INFO: loaded from: classes13.dex */
public class HMACSHA1NonceGenerator implements NonceGenerator {
    private static final String HEX = "0123456789ABCDEF";
    private Mac mac;

    public HMACSHA1NonceGenerator(String str) throws IllegalStateException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(StandardCharsets.ISO_8859_1), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            this.mac = mac;
            mac.init(secretKeySpec);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // org.eclipse.jgit.transport.NonceGenerator
    public synchronized String createNonce(Repository repository, long j) throws IllegalStateException {
        return String.valueOf(Long.toString(j)) + Parameters.DEFAULT_OPTION_PREFIXES + toHex(this.mac.doFinal((String.valueOf(repository.getIdentifier()) + ":" + String.valueOf(j)).getBytes(StandardCharsets.UTF_8)));
    }

    @Override // org.eclipse.jgit.transport.NonceGenerator
    public PushCertificate.NonceStatus verify(String str, String str2, Repository repository, boolean z, int i) {
        if (str.isEmpty()) {
            return PushCertificate.NonceStatus.MISSING;
        }
        if (str2.isEmpty()) {
            return PushCertificate.NonceStatus.UNSOLICITED;
        }
        if (str.equals(str2)) {
            return PushCertificate.NonceStatus.OK;
        }
        if (!z) {
            return PushCertificate.NonceStatus.BAD;
        }
        int iIndexOf = str2.indexOf(45);
        int iIndexOf2 = str.indexOf(45);
        if (iIndexOf == -1 || iIndexOf2 == -1) {
            return PushCertificate.NonceStatus.BAD;
        }
        String strSubstring = str.substring(0, iIndexOf2);
        String strSubstring2 = str2.substring(0, iIndexOf);
        try {
            long j = Long.parseLong(strSubstring);
            long j2 = Long.parseLong(strSubstring2);
            if (!createNonce(repository, j).equals(str)) {
                return PushCertificate.NonceStatus.BAD;
            }
            if (Math.abs(j2 - j) <= i) {
                return PushCertificate.NonceStatus.OK;
            }
            return PushCertificate.NonceStatus.SLOP;
        } catch (IllegalArgumentException unused) {
            return PushCertificate.NonceStatus.BAD;
        }
    }

    private static String toHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(HEX.charAt((b & 240) >> 4));
            sb.append(HEX.charAt(b & 15));
        }
        return sb.toString();
    }
}
