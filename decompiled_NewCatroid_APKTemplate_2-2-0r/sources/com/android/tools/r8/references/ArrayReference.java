package com.android.tools.r8.references;

import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.utils.C3970u0;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ArrayReference implements TypeReference {
    static final /* synthetic */ boolean d = true;
    private final int a;
    private final TypeReference b;
    private final String c;

    private ArrayReference(int i, TypeReference typeReference, String str) {
        if (!d && i <= 0) {
            throw new AssertionError();
        }
        this.a = i;
        this.b = typeReference;
        this.c = str;
    }

    static ArrayReference a(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '[') {
                if (i <= 0) {
                    break;
                }
                return new ArrayReference(i, Reference.typeFromDescriptor(str.substring(i)), str);
            }
        }
        throw new C3405y10("Invalid array type descriptor: " + str);
    }

    @Override // com.android.tools.r8.references.TypeReference
    public ArrayReference asArray() {
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ArrayReference)) {
            return false;
        }
        ArrayReference arrayReference = (ArrayReference) obj;
        return this.a == arrayReference.a && this.b.equals(arrayReference.b);
    }

    public TypeReference getBaseType() {
        return this.b;
    }

    @Override // com.android.tools.r8.references.TypeReference
    public String getDescriptor() {
        return this.c;
    }

    public int getDimensions() {
        return this.a;
    }

    public TypeReference getMemberType() {
        return Reference.arrayFromDescriptor(this.c.substring(1));
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.a), this.b);
    }

    @Override // com.android.tools.r8.references.TypeReference
    public boolean isArray() {
        return true;
    }

    static ArrayReference a(TypeReference typeReference, int i) {
        String descriptor = typeReference.getDescriptor();
        int i2 = C3970u0.c;
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < i; i3++) {
            sb.append('[');
        }
        sb.append(descriptor);
        return new ArrayReference(i, typeReference, sb.toString());
    }
}
