package org.jetbrains.kotlin.load.java.structure.impl;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.load.java.structure.JavaConstructor;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;

/* JADX INFO: loaded from: classes5.dex */
public class JavaConstructorImpl extends JavaMemberImpl<PsiMethod> implements JavaConstructor {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/load/java/structure/impl/JavaConstructorImpl";
        } else {
            objArr[0] = "psiConstructorSource";
        }
        if (i == 1) {
            objArr[1] = "getValueParameters";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/load/java/structure/impl/JavaConstructorImpl";
        } else {
            objArr[1] = "getTypeParameters";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaConstructorImpl(JavaElementPsiSource<PsiMethod> javaElementPsiSource) {
        super(javaElementPsiSource);
        if (javaElementPsiSource == null) {
            $$$reportNull$$$0(0);
        }
    }

    public JavaConstructorImpl(PsiMethod psiMethod) {
        this((JavaElementPsiSource<PsiMethod>) JavaElementSourceFactory.getInstance(psiMethod.getProject()).createPsiSource(psiMethod));
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaConstructor
    public List<JavaValueParameter> getValueParameters() {
        List<JavaValueParameter> listValueParameters = JavaElementCollectionFromPsiArrayUtil.valueParameters(((PsiMethod) getPsi()).getParameterList().getParameters(), getSourceFactory());
        if (listValueParameters == null) {
            $$$reportNull$$$0(1);
        }
        return listValueParameters;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameters */
    public List<JavaTypeParameter> mo7258getTypeParameters() {
        List<JavaTypeParameter> listTypeParameters = JavaElementCollectionFromPsiArrayUtil.typeParameters(((PsiMethod) getPsi()).getTypeParameters(), getSourceFactory());
        if (listTypeParameters == null) {
            $$$reportNull$$$0(2);
        }
        return listTypeParameters;
    }
}
