package com.android.tools.r8.references;

import com.android.tools.r8.utils.C3970u0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ClassReference implements TypeReference {
    private final String a;

    private ClassReference(String str) {
        this.a = str;
    }

    static ClassReference a(String str) {
        return new ClassReference(str);
    }

    @Override // com.android.tools.r8.references.TypeReference
    public ClassReference asClass() {
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ClassReference) {
            return this.a.equals(((ClassReference) obj).a);
        }
        return false;
    }

    public String getBinaryName() {
        return C3970u0.f(this.a);
    }

    @Override // com.android.tools.r8.references.TypeReference
    public String getDescriptor() {
        return this.a;
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @Override // com.android.tools.r8.references.TypeReference
    public boolean isClass() {
        return true;
    }

    public String toString() {
        return getDescriptor();
    }
}
