package org.jetbrains.kotlin.com.intellij.psi.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportList;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;

/* JADX INFO: loaded from: classes3.dex */
public final class ImportsUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "ref", "org/jetbrains/kotlin/com/intellij/psi/util/ImportsUtil", "expand"));
    }

    private ImportsUtil() {
    }

    public static List<PsiJavaCodeReferenceElement> collectReferencesThrough(PsiFile psiFile, final PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, final PsiImportStaticStatement psiImportStaticStatement) {
        final ArrayList arrayList = new ArrayList();
        psiFile.accept(new JavaRecursiveElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.ImportsUtil.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
            public void visitReferenceElement(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement2) {
                PsiJavaCodeReferenceElement psiJavaCodeReferenceElement3 = psiJavaCodeReferenceElement;
                if ((psiJavaCodeReferenceElement3 == null || psiJavaCodeReferenceElement3 != psiJavaCodeReferenceElement2) && psiJavaCodeReferenceElement2.advancedResolve(true).getCurrentFileResolveScope() == psiImportStaticStatement) {
                    arrayList.add(psiJavaCodeReferenceElement2);
                }
                super.visitElement(psiJavaCodeReferenceElement2);
            }
        });
        return arrayList;
    }

    public static void replaceAllAndDeleteImport(List<PsiJavaCodeReferenceElement> list, PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, PsiImportStaticStatement psiImportStaticStatement) {
        if (psiJavaCodeReferenceElement != null) {
            list.add(psiJavaCodeReferenceElement);
        }
        list.sort(new Comparator() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.ImportsUtil$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ImportsUtil.lambda$replaceAllAndDeleteImport$0((PsiJavaCodeReferenceElement) obj, (PsiJavaCodeReferenceElement) obj2);
            }
        });
        Iterator<PsiJavaCodeReferenceElement> it = list.iterator();
        while (it.hasNext()) {
            expand(it.next(), psiImportStaticStatement);
        }
        psiImportStaticStatement.delete();
    }

    static /* synthetic */ int lambda$replaceAllAndDeleteImport$0(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, PsiJavaCodeReferenceElement psiJavaCodeReferenceElement2) {
        return psiJavaCodeReferenceElement2.getTextOffset() - psiJavaCodeReferenceElement.getTextOffset();
    }

    public static void expand(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement, PsiImportStaticStatement psiImportStaticStatement) {
        if (psiJavaCodeReferenceElement == null) {
            $$$reportNull$$$0(0);
        }
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiJavaCodeReferenceElement.getProject());
        PsiClass psiClassResolveTargetClass = psiImportStaticStatement.resolveTargetClass();
        if (psiJavaCodeReferenceElement instanceof PsiReferenceExpression) {
            ((PsiReferenceExpression) psiJavaCodeReferenceElement).setQualifierExpression(elementFactory.createReferenceExpression(psiClassResolveTargetClass));
        } else if (psiJavaCodeReferenceElement instanceof PsiImportStaticReferenceElement) {
            psiJavaCodeReferenceElement.replace((PsiElement) Objects.requireNonNull(elementFactory.createImportStaticStatement(psiClassResolveTargetClass, psiJavaCodeReferenceElement.getText()).getImportReference()));
        } else {
            psiJavaCodeReferenceElement.replace(elementFactory.createReferenceFromText(psiClassResolveTargetClass.getQualifiedName() + "." + psiJavaCodeReferenceElement.getText(), psiJavaCodeReferenceElement));
        }
    }

    public static boolean hasStaticImportOn(PsiElement psiElement, PsiMember psiMember, boolean z) {
        PsiImportList importList;
        if ((psiElement.getContainingFile() instanceof PsiJavaFile) && (importList = ((PsiJavaFile) psiElement.getContainingFile()).getImportList()) != null) {
            for (PsiImportStaticStatement psiImportStaticStatement : importList.getImportStaticStatements()) {
                PsiClass psiClassMo6092getContainingClass = psiMember.mo6092getContainingClass();
                String referenceName = psiImportStaticStatement.getReferenceName();
                if (psiClassMo6092getContainingClass != null && psiImportStaticStatement.resolveTargetClass() == psiClassMo6092getContainingClass) {
                    if (!psiImportStaticStatement.isOnDemand() && Comparing.strEqual(referenceName, psiMember.getName())) {
                        return !(psiMember instanceof PsiMethod) || psiClassMo6092getContainingClass.findMethodsByName(referenceName, false).length > 0;
                    }
                    if (z && psiImportStaticStatement.isOnDemand()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
