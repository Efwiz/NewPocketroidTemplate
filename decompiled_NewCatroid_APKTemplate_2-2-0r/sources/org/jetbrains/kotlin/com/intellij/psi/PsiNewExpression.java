package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiNewExpression extends PsiCallExpression, PsiConstructorCall {
    PsiAnonymousClass getAnonymousClass();

    PsiExpression[] getArrayDimensions();

    PsiArrayInitializerExpression getArrayInitializer();

    PsiJavaCodeReferenceElement getClassOrAnonymousClassReference();

    PsiJavaCodeReferenceElement getClassReference();

    PsiType getOwner(PsiAnnotation psiAnnotation);

    PsiExpression getQualifier();

    default boolean isArrayCreation() {
        return (getArrayDimensions().length == 0 && getArrayInitializer() == null) ? false : true;
    }
}
