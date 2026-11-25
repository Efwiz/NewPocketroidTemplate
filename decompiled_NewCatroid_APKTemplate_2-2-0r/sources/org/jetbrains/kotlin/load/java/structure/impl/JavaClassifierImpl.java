package org.jetbrains.kotlin.load.java.structure.impl;

import java.util.Collection;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClassifier;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: loaded from: classes5.dex */
public abstract class JavaClassifierImpl<Psi extends PsiClass> extends JavaElementImpl<Psi> implements JavaClassifier, JavaAnnotationOwnerImpl {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "psiClass";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/load/java/structure/impl/JavaClassifierImpl";
        } else if (i != 3) {
            objArr[0] = "psi";
        } else {
            objArr[0] = "fqName";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/load/java/structure/impl/JavaClassifierImpl";
        } else {
            objArr[1] = "getAnnotations";
        }
        if (i == 1) {
            objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        } else if (i != 2) {
            if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "findAnnotation";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public final boolean isFromSource() {
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected JavaClassifierImpl(JavaElementPsiSource<Psi> javaElementPsiSource) {
        super(javaElementPsiSource);
        if (javaElementPsiSource == null) {
            $$$reportNull$$$0(0);
        }
    }

    static JavaClassifierImpl<?> create(PsiClass psiClass, JavaElementSourceFactory javaElementSourceFactory) {
        if (psiClass == null) {
            $$$reportNull$$$0(1);
        }
        if (psiClass instanceof PsiTypeParameter) {
            return new JavaTypeParameterImpl((JavaElementPsiSource<PsiTypeParameter>) javaElementSourceFactory.createPsiSource((PsiTypeParameter) psiClass));
        }
        return new JavaClassImpl((JavaElementPsiSource<PsiClass>) javaElementSourceFactory.createPsiSource(psiClass));
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations */
    public Collection<JavaAnnotation> mo7252getAnnotations() {
        Collection<JavaAnnotation> annotations = JavaElementUtil.getAnnotations(this, getSourceFactory());
        if (annotations == null) {
            $$$reportNull$$$0(2);
        }
        return annotations;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation */
    public JavaAnnotation mo7251findAnnotation(FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(3);
        }
        return JavaElementUtil.findAnnotation(this, fqName, getSourceFactory());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc */
    public boolean getIsDeprecatedInJavaDoc() {
        return getPsi().isDeprecated();
    }
}
