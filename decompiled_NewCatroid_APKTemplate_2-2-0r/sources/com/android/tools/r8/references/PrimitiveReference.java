package com.android.tools.r8.references;

import com.android.tools.r8.internal.C3405y10;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class PrimitiveReference implements TypeReference {
    static final PrimitiveReference a = new a();
    static final PrimitiveReference b = new b();
    static final PrimitiveReference c = new c();
    static final PrimitiveReference d = new d();
    static final PrimitiveReference e = new e();
    static final PrimitiveReference f = new f();
    static final PrimitiveReference g = new g();
    static final PrimitiveReference h = new h();
    static final /* synthetic */ boolean i = true;

    private PrimitiveReference() {
    }

    static PrimitiveReference a(String str) {
        if (!i && str.length() != 1) {
            throw new AssertionError();
        }
        char cCharAt = str.charAt(0);
        if (cCharAt == 'F') {
            return f;
        }
        if (cCharAt == 'S') {
            return d;
        }
        if (cCharAt == 'Z') {
            return a;
        }
        if (cCharAt == 'I') {
            return e;
        }
        if (cCharAt == 'J') {
            return g;
        }
        switch (cCharAt) {
            case 'B':
                return b;
            case 'C':
                return c;
            case 'D':
                return h;
            default:
                throw new C3405y10("Invalid primitive descriptor: " + str);
        }
    }

    @Override // com.android.tools.r8.references.TypeReference
    public PrimitiveReference asPrimitive() {
        return this;
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override // com.android.tools.r8.references.TypeReference
    public abstract String getDescriptor();

    public int hashCode() {
        return System.identityHashCode(this);
    }

    @Override // com.android.tools.r8.references.TypeReference
    public boolean isPrimitive() {
        return true;
    }
}
