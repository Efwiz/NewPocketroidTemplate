package org.jetbrains.kotlin.load.java.structure.impl;

import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.load.java.structure.JavaPrimitiveType;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementTypeSource;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;

/* JADX INFO: loaded from: classes5.dex */
public class JavaPrimitiveTypeImpl extends JavaTypeImpl<PsiPrimitiveType> implements JavaPrimitiveType {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "psiPrimitiveTypeSource", "org/jetbrains/kotlin/load/java/structure/impl/JavaPrimitiveTypeImpl", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaPrimitiveTypeImpl(JavaElementTypeSource<PsiPrimitiveType> javaElementTypeSource) {
        super(javaElementTypeSource);
        if (javaElementTypeSource == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaPrimitiveType
    public PrimitiveType getType() {
        String canonicalText = getPsi().getCanonicalText();
        if (PsiKeyword.VOID.equals(canonicalText)) {
            return null;
        }
        return JvmPrimitiveType.get(canonicalText).getPrimitiveType();
    }
}
