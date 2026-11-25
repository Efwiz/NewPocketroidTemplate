package org.jetbrains.kotlin.load.java.structure.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;

/* JADX INFO: loaded from: classes5.dex */
public class JavaFieldImpl extends JavaMemberImpl<PsiField> implements JavaField {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "psiFieldSource";
        } else {
            objArr[0] = "org/jetbrains/kotlin/load/java/structure/impl/JavaFieldImpl";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/load/java/structure/impl/JavaFieldImpl";
        } else {
            objArr[1] = "getType";
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
    public JavaFieldImpl(JavaElementPsiSource<PsiField> javaElementPsiSource) {
        super(javaElementPsiSource);
        if (javaElementPsiSource == null) {
            $$$reportNull$$$0(0);
        }
    }

    public JavaFieldImpl(PsiField psiField) {
        this((JavaElementPsiSource<PsiField>) JavaElementSourceFactory.getInstance(psiField.getProject()).createPsiSource(psiField));
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    /* JADX INFO: renamed from: isEnumEntry */
    public boolean getIsEnumEntry() {
        return getPsi() instanceof PsiEnumConstant;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    public JavaType getType() {
        JavaTypeImpl<?> javaTypeImplCreate = JavaTypeImpl.create(((PsiField) this.psiElementSource.getPsi()).getType(), createVariableReturnTypeSource(this.psiElementSource));
        if (javaTypeImplCreate == null) {
            $$$reportNull$$$0(1);
        }
        return javaTypeImplCreate;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    public Object getInitializerValue() {
        return ((PsiField) getPsi()).computeConstantValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaField
    /* JADX INFO: renamed from: getHasConstantNotNullInitializer */
    public boolean mo7273getHasConstantNotNullInitializer() {
        return PsiUtil.isCompileTimeConstant((PsiVariable) getPsi());
    }
}
