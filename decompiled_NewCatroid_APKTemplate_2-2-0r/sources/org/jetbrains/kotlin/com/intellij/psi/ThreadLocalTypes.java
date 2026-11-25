package org.jetbrains.kotlin.com.intellij.psi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.util.RecursionManager;

/* JADX INFO: loaded from: classes3.dex */
public final class ThreadLocalTypes {
    private static final RecursionGuard<ThreadLocalTypes> ourGuard = RecursionManager.createGuard("ThreadLocalTypes");
    private final Map<PsiElement, PsiType> myMap = new HashMap();
    private final boolean myProhibitCaching;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 2 || i == 3) {
            objArr[0] = "action";
        } else {
            objArr[0] = "psi";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/ThreadLocalTypes";
        if (i == 1) {
            objArr[2] = "hasBindingFor";
        } else if (i == 2 || i == 3) {
            objArr[2] = "performWithTypes";
        } else if (i != 4) {
            objArr[2] = "getElementType";
        } else {
            objArr[2] = "forceType";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private ThreadLocalTypes(boolean z) {
        this.myProhibitCaching = z;
    }

    public static PsiType getElementType(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        List<? extends ThreadLocalTypes> listCurrentStack = ourGuard.currentStack();
        for (int size = listCurrentStack.size() - 1; size >= 0; size--) {
            ThreadLocalTypes threadLocalTypes = listCurrentStack.get(size);
            PsiType psiType = threadLocalTypes.myMap.get(psiElement);
            if (psiType != null) {
                if (threadLocalTypes.myProhibitCaching) {
                    ourGuard.prohibitResultCaching(threadLocalTypes);
                }
                return psiType;
            }
        }
        return null;
    }

    public static boolean hasBindingFor(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        List<? extends ThreadLocalTypes> listCurrentStack = ourGuard.currentStack();
        for (int size = listCurrentStack.size() - 1; size >= 0; size--) {
            ThreadLocalTypes threadLocalTypes = listCurrentStack.get(size);
            if (threadLocalTypes.myMap.containsKey(psiElement)) {
                if (threadLocalTypes.myProhibitCaching) {
                    ourGuard.prohibitResultCaching(threadLocalTypes);
                }
                return true;
            }
        }
        return false;
    }

    public static <T> T performWithTypes(Function<? super ThreadLocalTypes, ? extends T> function) {
        if (function == null) {
            $$$reportNull$$$0(2);
        }
        return (T) performWithTypes(function, true);
    }

    public static <T> T performWithTypes(final Function<? super ThreadLocalTypes, ? extends T> function, boolean z) {
        if (function == null) {
            $$$reportNull$$$0(3);
        }
        final ThreadLocalTypes threadLocalTypes = new ThreadLocalTypes(z);
        return (T) ourGuard.doPreventingRecursion(threadLocalTypes, false, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.ThreadLocalTypes$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final Object compute() {
                return function.apply(threadLocalTypes);
            }
        });
    }

    public void forceType(PsiElement psiElement, PsiType psiType) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        this.myMap.put(psiElement, psiType);
    }
}
