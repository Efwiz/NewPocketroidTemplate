package org.jetbrains.kotlin.psi;

import com.reandroid.arsc.container.SpecTypePair;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes6.dex */
public final class KtStubbedPsiUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 2 || i == 4) {
            objArr[0] = "declarationClass";
        } else if (i == 6) {
            objArr[0] = SpecTypePair.NAME_types;
        } else if (i != 7) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "factory";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtStubbedPsiUtil";
        if (i == 3 || i == 4) {
            objArr[2] = "getPsiOrStubParent";
        } else if (i == 5 || i == 6 || i == 7) {
            objArr[2] = "getStubOrPsiChild";
        } else {
            objArr[2] = "getContainingDeclaration";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static KtDeclaration getContainingDeclaration(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        return (KtDeclaration) getPsiOrStubParent(psiElement, KtDeclaration.class, true);
    }

    public static <T extends KtDeclaration> T getContainingDeclaration(PsiElement psiElement, Class<T> cls) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        if (cls == null) {
            $$$reportNull$$$0(2);
        }
        return (T) getPsiOrStubParent(psiElement, cls, true);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement] */
    public static <T extends KtElement> T getPsiOrStubParent(PsiElement psiElement, Class<T> cls, boolean z) {
        ?? stub;
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        if (cls == null) {
            $$$reportNull$$$0(4);
        }
        if (!z && cls.isInstance(psiElement)) {
            return (T) psiElement;
        }
        if ((psiElement instanceof KtElementImplStub) && (stub = ((KtElementImplStub) psiElement).getStub()) != 0) {
            return (T) stub.getParentStubOfType(cls);
        }
        return (T) PsiTreeUtil.getParentOfType(psiElement, cls, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T extends KtElement> T getStubOrPsiChild(KtElementImplStub<?> ktElementImplStub, TokenSet tokenSet, ArrayFactory<T> arrayFactory) {
        if (ktElementImplStub == null) {
            $$$reportNull$$$0(5);
        }
        if (tokenSet == null) {
            $$$reportNull$$$0(6);
        }
        if (arrayFactory == 0) {
            $$$reportNull$$$0(7);
        }
        KtElement[] ktElementArr = (KtElement[]) ktElementImplStub.getStubOrPsiChildren(tokenSet, arrayFactory);
        if (ktElementArr.length == 0) {
            return null;
        }
        return (T) ktElementArr[0];
    }

    private KtStubbedPsiUtil() {
    }
}
