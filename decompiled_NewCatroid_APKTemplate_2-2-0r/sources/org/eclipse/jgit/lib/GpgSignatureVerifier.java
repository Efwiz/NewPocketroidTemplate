package org.eclipse.jgit.lib;

import java.io.IOException;
import java.util.Date;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.revwalk.RevObject;

/* JADX INFO: loaded from: classes13.dex */
public interface GpgSignatureVerifier {

    public interface SignatureVerification {
        Date getCreationDate();

        String getKeyFingerprint();

        String getKeyUser();

        String getMessage();

        String getSigner();

        TrustLevel getTrustLevel();

        boolean getVerified();

        boolean isExpired();
    }

    public enum TrustLevel {
        UNKNOWN,
        NEVER,
        MARGINAL,
        FULL,
        ULTIMATE;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static TrustLevel[] valuesCustom() {
            TrustLevel[] trustLevelArrValuesCustom = values();
            int length = trustLevelArrValuesCustom.length;
            TrustLevel[] trustLevelArr = new TrustLevel[length];
            System.arraycopy(trustLevelArrValuesCustom, 0, trustLevelArr, 0, length);
            return trustLevelArr;
        }
    }

    void clear();

    String getName();

    SignatureVerification verify(byte[] bArr, byte[] bArr2) throws IOException;

    @Nullable
    SignatureVerification verifySignature(RevObject revObject, GpgConfig gpgConfig) throws IOException;
}
