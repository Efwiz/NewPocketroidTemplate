package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve;

import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaCodeReferenceElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class ResolveClassUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "ref";
        } else {
            objArr[0] = "containingFile";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/ResolveClassUtil";
        objArr[2] = "resolveClass";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static PsiClass resolveClass(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, PsiFile psiFile) {
        PsiExpression qualifier;
        PsiClass psiClassResolveClassInType;
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(0);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(1);
        }
        if ((psiJavaCodeReferenceElement instanceof PsiJavaCodeReferenceElementImpl) && ((PsiJavaCodeReferenceElementImpl) psiJavaCodeReferenceElement).getKindEnum(psiFile) == PsiJavaCodeReferenceElementImpl.Kind.CLASS_IN_QUALIFIED_NEW_KIND) {
            PsiNewExpression psiNewExpression = (PsiNewExpression) PsiTreeUtil.getContextOfType(psiJavaCodeReferenceElement, PsiNewExpression.class);
            if (psiNewExpression != null && (qualifier = psiNewExpression.getQualifier()) != null) {
                PsiType type = qualifier.getType();
                if ((type instanceof PsiClassType) && (psiClassResolveClassInType = PsiUtil.resolveClassInType(type)) != null) {
                    return psiClassResolveClassInType.findInnerClassByName(psiJavaCodeReferenceElement.getText(), true);
                }
            }
        } else {
            PsiElement referenceNameElement = psiJavaCodeReferenceElement.getReferenceNameElement();
            if (referenceNameElement instanceof PsiIdentifier) {
                ClassResolverProcessor classResolverProcessor = new ClassResolverProcessor(referenceNameElement.getText(), psiJavaCodeReferenceElement, psiFile);
                PsiScopesUtil.resolveAndWalk(classResolverProcessor, psiJavaCodeReferenceElement, null);
                if (classResolverProcessor.getResult().length == 1) {
                    return (PsiClass) classResolverProcessor.getResult()[0].getElement();
                }
            }
        }
        return null;
    }
}
