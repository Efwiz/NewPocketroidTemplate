package org.bouncycastle.jcajce.spec;

import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.util.Arrays;

/* JADX INFO: loaded from: classes13.dex */
public class SM2ParameterSpec implements AlgorithmParameterSpec {

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private byte[] f504id;

    public SM2ParameterSpec(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("id string cannot be null");
        }
        this.f504id = Arrays.clone(bArr);
    }

    public byte[] getID() {
        return Arrays.clone(this.f504id);
    }
}
