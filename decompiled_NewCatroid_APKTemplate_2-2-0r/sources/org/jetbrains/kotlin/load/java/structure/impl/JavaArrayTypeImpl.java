package org.jetbrains.kotlin.load.java.structure.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.load.java.structure.JavaArrayType;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementTypeSource;

/* JADX INFO: loaded from: classes5.dex */
public class JavaArrayTypeImpl extends JavaTypeImpl<PsiArrayType> implements JavaArrayType {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "psiArrayTypeSource";
        } else {
            objArr[0] = "org/jetbrains/kotlin/load/java/structure/impl/JavaArrayTypeImpl";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/load/java/structure/impl/JavaArrayTypeImpl";
        } else {
            objArr[1] = "getComponentType";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaArrayTypeImpl(JavaElementTypeSource<PsiArrayType> javaElementTypeSource) {
        super(javaElementTypeSource);
        if (javaElementTypeSource == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaArrayType
    public JavaTypeImpl<?> getComponentType() {
        JavaTypeImpl<?> javaTypeImplCreate = JavaTypeImpl.create((JavaElementTypeSource<? extends PsiType>) createTypeSource(getPsi().getComponentType()));
        if (javaTypeImplCreate == null) {
            $$$reportNull$$$0(1);
        }
        return javaTypeImplCreate;
    }
}
