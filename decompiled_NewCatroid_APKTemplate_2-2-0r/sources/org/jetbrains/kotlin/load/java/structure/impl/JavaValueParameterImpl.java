package org.jetbrains.kotlin.load.java.structure.impl;

import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsParameterImpl;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: loaded from: classes5.dex */
public class JavaValueParameterImpl extends JavaElementImpl<PsiParameter> implements JavaValueParameter, JavaAnnotationOwnerImpl, JavaModifierListOwnerImpl {
    /* JADX WARN: Code duplicated, block: B:21:0x002f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 4) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/load/java/structure/impl/JavaValueParameterImpl";
        } else if (i == 3) {
            objArr[0] = "fqName";
        } else if (i != 4) {
            objArr[0] = "psiParameterSource";
        } else {
            objArr[0] = "org/jetbrains/kotlin/load/java/structure/impl/JavaValueParameterImpl";
        }
        if (i == 1) {
            objArr[1] = "getVisibility";
        } else if (i == 2) {
            objArr[1] = "getAnnotations";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/load/java/structure/impl/JavaValueParameterImpl";
        } else {
            objArr[1] = "getType";
        }
        if (i != 1 && i != 2) {
            if (i == 3) {
                objArr[2] = "findAnnotation";
            } else if (i != 4) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isAbstract */
    public boolean mo7260isAbstract() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isFinal */
    public boolean mo7264isFinal() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    public boolean isFromSource() {
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isStatic */
    public boolean mo7267isStatic() {
        return false;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.JavaElementImpl, org.jetbrains.kotlin.load.java.structure.impl.JavaModifierListOwnerImpl
    public /* bridge */ /* synthetic */ PsiModifierListOwner getPsi() {
        return (PsiModifierListOwner) super.getPsi();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaValueParameterImpl(JavaElementPsiSource<PsiParameter> javaElementPsiSource) {
        super(javaElementPsiSource);
        if (javaElementPsiSource == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.JavaAnnotationOwnerImpl
    public PsiAnnotationOwner getAnnotationOwnerPsi() {
        return ((PsiParameter) getPsi()).mo6095getModifierList();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    public Visibility getVisibility() {
        Visibilities.Local local = Visibilities.Local.INSTANCE;
        if (local == null) {
            $$$reportNull$$$0(1);
        }
        return local;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        Collection<JavaAnnotation> regularAndExternalAnnotations = JavaElementUtil.getRegularAndExternalAnnotations(this, getSourceFactory());
        if (regularAndExternalAnnotations == null) {
            $$$reportNull$$$0(2);
        }
        return regularAndExternalAnnotations;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    public JavaAnnotation mo7251findAnnotation(FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(3);
        }
        return JavaElementUtil.findAnnotation(this, fqName, getSourceFactory());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    public Name getName() {
        String str;
        PsiParameter psiParameter = (PsiParameter) getPsi();
        if (((psiParameter instanceof ClsParameterImpl) && ((ClsParameterImpl) psiParameter).isAutoGeneratedName()) || (str = psiParameter.getName()) == null) {
            return null;
        }
        return Name.identifier(str);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    public JavaType getType() {
        JavaTypeImpl<?> javaTypeImplCreate = JavaTypeImpl.create(((PsiParameter) getPsi()).getType(), createVariableReturnTypeSource(this.psiElementSource));
        if (javaTypeImplCreate == null) {
            $$$reportNull$$$0(4);
        }
        return javaTypeImplCreate;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaValueParameter
    /* JADX INFO: renamed from: isVararg */
    public boolean getIsVararg() {
        return ((PsiParameter) getPsi()).getMyVarArgs();
    }
}
