package org.jetbrains.kotlin.load.java.structure.impl;

import java.util.List;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: loaded from: classes5.dex */
public class JavaMethodImpl extends JavaMemberImpl<PsiMethod> implements JavaMethod {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/load/java/structure/impl/JavaMethodImpl";
        } else {
            objArr[0] = "psiMethodSource";
        }
        if (i == 1) {
            objArr[1] = "getName";
        } else if (i == 2) {
            objArr[1] = "getTypeParameters";
        } else if (i == 3) {
            objArr[1] = "getValueParameters";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/load/java/structure/impl/JavaMethodImpl";
        } else {
            objArr[1] = "getReturnType";
        }
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaMethodImpl(JavaElementPsiSource<PsiMethod> javaElementPsiSource) {
        super(javaElementPsiSource);
        if (javaElementPsiSource == null) {
            $$$reportNull$$$0(0);
        }
    }

    public JavaMethodImpl(PsiMethod psiMethod) {
        this((JavaElementPsiSource<PsiMethod>) JavaElementSourceFactory.getInstance(psiMethod.getProject()).createPsiSource(psiMethod));
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.JavaMemberImpl, org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        Name nameIdentifier = Name.identifier(((PsiMethod) getPsi()).getMyName());
        if (nameIdentifier == null) {
            $$$reportNull$$$0(1);
        }
        return nameIdentifier;
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

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMethod
    public List<JavaValueParameter> getValueParameters() {
        List<JavaValueParameter> listValueParameters = JavaElementCollectionFromPsiArrayUtil.valueParameters(((PsiMethod) getPsi()).getParameterList().getParameters(), getSourceFactory());
        if (listValueParameters == null) {
            $$$reportNull$$$0(3);
        }
        return listValueParameters;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMethod
    public JavaAnnotationArgument getAnnotationParameterDefaultValue() {
        PsiAnnotationMemberValue defaultValue;
        PsiMethod psiMethod = (PsiMethod) getPsi();
        if (!(psiMethod instanceof PsiAnnotationMethod) || (defaultValue = ((PsiAnnotationMethod) psiMethod).getDefaultValue()) == null) {
            return null;
        }
        return JavaAnnotationArgumentImpl.INSTANCE.create(defaultValue, null, getSourceFactory());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMethod
    public boolean getHasAnnotationParameterDefaultValue() {
        PsiMethod psiMethod = (PsiMethod) getPsi();
        return (psiMethod instanceof PsiAnnotationMethod) && ((PsiAnnotationMethod) psiMethod).getDefaultValue() != null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaMethod
    public JavaType getReturnType() {
        JavaTypeImpl<?> javaTypeImplCreate = JavaTypeImpl.create((PsiType) Objects.requireNonNull(((PsiMethod) getPsi()).getReturnType()), createMethodReturnTypeSource(this.psiElementSource));
        if (javaTypeImplCreate == null) {
            $$$reportNull$$$0(4);
        }
        return javaTypeImplCreate;
    }
}
