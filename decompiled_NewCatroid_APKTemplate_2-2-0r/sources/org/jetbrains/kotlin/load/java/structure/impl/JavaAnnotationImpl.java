package org.jetbrains.kotlin.load.java.structure.impl;

import java.util.Collection;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.codeInsight.ExternalAnnotationsManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: loaded from: classes5.dex */
public class JavaAnnotationImpl extends JavaElementImpl<PsiAnnotation> implements JavaAnnotation {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/load/java/structure/impl/JavaAnnotationImpl";
        } else if (i == 2) {
            objArr[0] = "psiClass";
        } else if (i != 3) {
            objArr[0] = "psiAnnotation";
        } else {
            objArr[0] = "fqName";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/load/java/structure/impl/JavaAnnotationImpl";
        } else {
            objArr[1] = "getArguments";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "computeClassId";
            } else if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "isResolvedTo";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    /* JADX INFO: renamed from: isFreshlySupportedTypeUseAnnotation */
    public boolean getIsFreshlySupportedTypeUseAnnotation() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaAnnotationImpl(JavaElementPsiSource<PsiAnnotation> javaElementPsiSource) {
        super(javaElementPsiSource);
        if (javaElementPsiSource == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    public Collection<JavaAnnotationArgument> getArguments() {
        Collection<JavaAnnotationArgument> collectionNamedAnnotationArguments = JavaElementCollectionFromPsiArrayUtil.namedAnnotationArguments(getPsi().getParameterList().getAttributes(), getSourceFactory());
        if (collectionNamedAnnotationArguments == null) {
            $$$reportNull$$$0(1);
        }
        return collectionNamedAnnotationArguments;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    public ClassId getClassId() {
        PsiClass psiClassResolvePsi = resolvePsi();
        if (psiClassResolvePsi != null) {
            return computeClassId(psiClassResolvePsi);
        }
        String qualifiedName = getPsi().getQualifiedName();
        if (qualifiedName != null) {
            return ClassId.topLevel(new FqName(qualifiedName));
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    public JavaClass resolve() {
        PsiClass psiClassResolvePsi = resolvePsi();
        if (psiClassResolvePsi == null) {
            return null;
        }
        return new JavaClassImpl((JavaElementPsiSource<PsiClass>) createPsiSource(psiClassResolvePsi));
    }

    private static ClassId computeClassId(PsiClass psiClass) {
        if (psiClass == null) {
            $$$reportNull$$$0(2);
        }
        PsiClass psiClassMo6092getContainingClass = psiClass.mo6092getContainingClass();
        if (psiClassMo6092getContainingClass != null) {
            ClassId classIdComputeClassId = computeClassId(psiClassMo6092getContainingClass);
            String name2 = psiClass.getName();
            if (classIdComputeClassId == null || name2 == null) {
                return null;
            }
            return classIdComputeClassId.createNestedClassId(Name.identifier(name2));
        }
        String qualifiedName = psiClass.getQualifiedName();
        if (qualifiedName == null) {
            return null;
        }
        return ClassId.topLevel(new FqName(qualifiedName));
    }

    private PsiClass resolvePsi() {
        PsiJavaCodeReferenceElement nameReferenceElement = getPsi().getNameReferenceElement();
        if (nameReferenceElement == null) {
            return null;
        }
        PsiElement psiElementResolve = nameReferenceElement.resolve();
        if (psiElementResolve instanceof PsiClass) {
            return (PsiClass) psiElementResolve;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    public boolean isIdeExternalAnnotation() {
        PsiAnnotation psi = getPsi();
        return ExternalAnnotationsManager.getInstance(psi.getProject()).isExternalAnnotation(psi);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotation
    public boolean isResolvedTo(FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(3);
        }
        PsiJavaCodeReferenceElement nameReferenceElement = getPsi().getNameReferenceElement();
        if (nameReferenceElement == null || !Objects.equals(nameReferenceElement.getReferenceName(), fqName.shortNameOrSpecial().asString())) {
            return false;
        }
        return getPsi().hasQualifiedName(fqName.asString());
    }
}
