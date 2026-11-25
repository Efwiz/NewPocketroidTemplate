package org.jetbrains.kotlin.com.intellij.psi.stub;

import org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiMethodImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.StubbedSpine;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaStubImplUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "member", "org/jetbrains/kotlin/com/intellij/psi/stub/JavaStubImplUtil", "isMemberDeprecated"));
    }

    public static int getMethodStubIndex(PsiMethod psiMethod) {
        if (!(psiMethod instanceof PsiMethodImpl)) {
            return -1;
        }
        StubbedSpine stubbedSpine = ((PsiFileImpl) psiMethod.getContainingFile()).getStubbedSpine();
        int i = 0;
        for (int i2 = 0; i2 < stubbedSpine.getStubCount(); i2++) {
            if (stubbedSpine.getStubType(i2) == JavaElementType.METHOD) {
                if (stubbedSpine.getStubPsi(i2) == psiMethod) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T extends PsiMember & PsiJavaDocumentedElement> boolean isMemberDeprecated(T t, PsiMemberStub<?> psiMemberStub) {
        if (t == 0) {
            $$$reportNull$$$0(0);
        }
        if (psiMemberStub != null) {
            return psiMemberStub.isDeprecated() || (psiMemberStub.hasDeprecatedAnnotation() && PsiImplUtil.isDeprecatedByAnnotation(t));
        }
        return PsiImplUtil.isDeprecatedByDocTag(t) || PsiImplUtil.isDeprecatedByAnnotation(t);
    }
}
