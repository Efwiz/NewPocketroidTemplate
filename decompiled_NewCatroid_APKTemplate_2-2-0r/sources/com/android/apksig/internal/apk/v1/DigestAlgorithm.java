package com.android.apksig.internal.apk.v1;

import java.util.Comparator;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public enum DigestAlgorithm {
    SHA1(McElieceCCA2KeyGenParameterSpec.SHA1),
    SHA256(McElieceCCA2KeyGenParameterSpec.SHA256);

    public static Comparator<DigestAlgorithm> BY_STRENGTH_COMPARATOR = new StrengthComparator(null);
    private final String mJcaMessageDigestAlgorithm;

    DigestAlgorithm(String jcaMessageDigestAlgoritm) {
        this.mJcaMessageDigestAlgorithm = jcaMessageDigestAlgoritm;
    }

    String getJcaMessageDigestAlgorithm() {
        return this.mJcaMessageDigestAlgorithm;
    }

    private static class StrengthComparator implements Comparator<DigestAlgorithm> {
        private StrengthComparator() {
        }

        /* synthetic */ StrengthComparator(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // java.util.Comparator
        public int compare(DigestAlgorithm a1, DigestAlgorithm a2) {
            int i = AnonymousClass1.$SwitchMap$com$android$apksig$internal$apk$v1$DigestAlgorithm[a1.ordinal()];
            if (i == 1) {
                int i2 = AnonymousClass1.$SwitchMap$com$android$apksig$internal$apk$v1$DigestAlgorithm[a2.ordinal()];
                if (i2 == 1) {
                    return 0;
                }
                if (i2 == 2) {
                    return -1;
                }
                throw new RuntimeException("Unsupported algorithm: " + a2);
            }
            if (i == 2) {
                int i3 = AnonymousClass1.$SwitchMap$com$android$apksig$internal$apk$v1$DigestAlgorithm[a2.ordinal()];
                if (i3 == 1) {
                    return 1;
                }
                if (i3 == 2) {
                    return 0;
                }
                throw new RuntimeException("Unsupported algorithm: " + a2);
            }
            throw new RuntimeException("Unsupported algorithm: " + a1);
        }
    }

    /* JADX INFO: renamed from: com.android.apksig.internal.apk.v1.DigestAlgorithm$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$apksig$internal$apk$v1$DigestAlgorithm;

        static {
            int[] iArr = new int[DigestAlgorithm.values().length];
            $SwitchMap$com$android$apksig$internal$apk$v1$DigestAlgorithm = iArr;
            try {
                iArr[DigestAlgorithm.SHA1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$apk$v1$DigestAlgorithm[DigestAlgorithm.SHA256.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
