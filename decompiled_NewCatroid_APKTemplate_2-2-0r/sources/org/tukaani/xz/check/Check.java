package org.tukaani.xz.check;

import java.security.NoSuchAlgorithmException;
import org.tukaani.xz.UnsupportedOptionsException;

/* JADX INFO: loaded from: classes6.dex */
public abstract class Check {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    String f1078name;
    int size;

    public static Check getInstance(int i) throws UnsupportedOptionsException {
        if (i == 0) {
            return new None();
        }
        if (i == 1) {
            return new CRC32();
        }
        if (i == 4) {
            return new CRC64();
        }
        if (i == 10) {
            try {
                return new SHA256();
            } catch (NoSuchAlgorithmException unused) {
            }
        }
        throw new UnsupportedOptionsException("Unsupported Check ID " + i);
    }

    public abstract byte[] finish();

    public String getName() {
        return this.f1078name;
    }

    public int getSize() {
        return this.size;
    }

    public void update(byte[] bArr) {
        update(bArr, 0, bArr.length);
    }

    public abstract void update(byte[] bArr, int i, int i2);
}
