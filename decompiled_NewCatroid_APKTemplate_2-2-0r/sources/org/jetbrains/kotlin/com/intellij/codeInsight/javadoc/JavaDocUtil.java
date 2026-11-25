package org.jetbrains.kotlin.com.intellij.codeInsight.javadoc;

import java.util.regex.Pattern;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaDocUtil {
    private static final Logger LOG = Logger.getInstance((Class<?>) JavaDocUtil.class);
    private static final Pattern ourTypePattern = Pattern.compile("[ ]+[^ ^\\[^\\]]");

    private JavaDocUtil() {
    }

    public static PsiClass resolveClassInTagValue(PsiDocTagValue psiDocTagValue) {
        PsiElement firstChild;
        if (psiDocTagValue != null && (firstChild = psiDocTagValue.getFirstChild()) != null) {
            PsiElement firstChild2 = firstChild.getFirstChild();
            if (firstChild2 instanceof PsiJavaCodeReferenceElement) {
                PsiElement psiElementResolve = ((PsiJavaCodeReferenceElement) firstChild2).resolve();
                if (psiElementResolve instanceof PsiClass) {
                    return (PsiClass) psiElementResolve;
                }
            }
        }
        return null;
    }

    public static boolean isInsidePackageInfo(PsiDocComment psiDocComment) {
        return psiDocComment != null && psiDocComment.getOwner() == null && (psiDocComment.getLightParent() instanceof PsiJavaFile);
    }
}
