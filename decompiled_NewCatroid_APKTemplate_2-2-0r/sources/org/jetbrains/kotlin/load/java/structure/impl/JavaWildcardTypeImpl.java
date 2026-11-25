package org.jetbrains.kotlin.load.java.structure.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiWildcardType;
import org.jetbrains.kotlin.load.java.structure.JavaWildcardType;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementTypeSource;

/* JADX INFO: loaded from: classes5.dex */
public class JavaWildcardTypeImpl extends JavaTypeImpl<PsiWildcardType> implements JavaWildcardType {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "psiWildcardTypeSource", "org/jetbrains/kotlin/load/java/structure/impl/JavaWildcardTypeImpl", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaWildcardTypeImpl(JavaElementTypeSource<PsiWildcardType> javaElementTypeSource) {
        super(javaElementTypeSource);
        if (javaElementTypeSource == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaWildcardType
    public JavaTypeImpl<?> getBound() {
        PsiType bound = getPsi().getBound();
        if (bound == null) {
            return null;
        }
        return create((JavaElementTypeSource<? extends PsiType>) createTypeSource(bound));
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaWildcardType
    /* JADX INFO: renamed from: isExtends */
    public boolean getIsExtends() {
        return getPsi().isExtends();
    }
}
