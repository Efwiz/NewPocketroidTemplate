package com.android.tools.r8.internal;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;

/* JADX INFO: renamed from: com.android.tools.r8.internal.d, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1806d implements WildcardType, Serializable {
    private final Type a;
    private final Type b;

    public C1806d(Type[] typeArr, Type[] typeArr2) {
        AbstractC1575a.a(typeArr2.length <= 1);
        AbstractC1575a.a(typeArr.length == 1);
        if (typeArr2.length != 1) {
            AbstractC1575a.a(typeArr[0]);
            AbstractC1879e.b(typeArr[0]);
            this.b = null;
            this.a = AbstractC1879e.a(typeArr[0]);
            return;
        }
        AbstractC1575a.a(typeArr2[0]);
        AbstractC1879e.b(typeArr2[0]);
        AbstractC1575a.a(typeArr[0] == Object.class);
        this.b = AbstractC1879e.a(typeArr2[0]);
        this.a = Object.class;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof WildcardType) && AbstractC1879e.a(this, (WildcardType) obj);
    }

    @Override // java.lang.reflect.WildcardType
    public final Type[] getLowerBounds() {
        Type type = this.b;
        return type != null ? new Type[]{type} : AbstractC1879e.a;
    }

    @Override // java.lang.reflect.WildcardType
    public final Type[] getUpperBounds() {
        return new Type[]{this.a};
    }

    public final int hashCode() {
        Type type = this.b;
        return (type != null ? type.hashCode() + 31 : 1) ^ (this.a.hashCode() + 31);
    }

    public final String toString() {
        if (this.b != null) {
            return PsiWildcardType.SUPER_PREFIX + AbstractC1879e.d(this.b);
        }
        return this.a == Object.class ? TypeDescription.Generic.OfWildcardType.SYMBOL : PsiWildcardType.EXTENDS_PREFIX + AbstractC1879e.d(this.a);
    }
}
