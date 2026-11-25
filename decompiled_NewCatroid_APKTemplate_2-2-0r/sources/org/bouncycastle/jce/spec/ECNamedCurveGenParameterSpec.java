package org.bouncycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes13.dex */
public class ECNamedCurveGenParameterSpec implements AlgorithmParameterSpec {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f505name;

    public ECNamedCurveGenParameterSpec(String str) {
        this.f505name = str;
    }

    public String getName() {
        return this.f505name;
    }
}
