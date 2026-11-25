package org.jetbrains.kotlin.com.intellij.psi.util;

import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.model.ModelBranch;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiCacheKey<T, H extends PsiElement> extends Key<SoftReference<Pair<Long, T>>> {
    private final Function<? super H, ? extends T> myFunction;

    /* JADX WARN: Code duplicated, block: B:11:0x0021  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "function";
        } else if (i == 2 || i == 3) {
            objArr[0] = "h";
        } else if (i == 4) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i != 6) {
            objArr[0] = "name";
        } else {
            objArr[0] = "function";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiCacheKey";
        if (i == 2) {
            objArr[2] = "getValue";
        } else if (i == 3) {
            objArr[2] = "getCachedValueOrNull";
        } else if (i == 4) {
            objArr[2] = "getModificationCount";
        } else if (i == 5 || i == 6) {
            objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private PsiCacheKey(String str, Function<? super H, ? extends T> function) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (function == null) {
            $$$reportNull$$$0(1);
        }
        this.myFunction = function;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T getValue(H h) {
        if (h == 0) {
            $$$reportNull$$$0(2);
        }
        T cachedValueOrNull = getCachedValueOrNull(h);
        if (cachedValueOrNull != null) {
            return cachedValueOrNull;
        }
        T tFun = this.myFunction.fun(h);
        h.putUserData(this, new SoftReference(new Pair(Long.valueOf(getModificationCount(h)), tFun)));
        return tFun;
    }

    public T getCachedValueOrNull(H h) {
        if (h == null) {
            $$$reportNull$$$0(3);
        }
        Pair pair = (Pair) SoftReference.dereference((SoftReference) h.getUserData(this));
        if (pair == null || ((Long) pair.getFirst()).longValue() != getModificationCount(h)) {
            return null;
        }
        return (T) pair.getSecond();
    }

    private static long getModificationCount(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(4);
        }
        PsiFile containingFile = psiElement.getContainingFile();
        long modificationStamp = (containingFile == null || containingFile.isPhysical()) ? 0L : containingFile.getModificationStamp();
        ModelBranch psiBranch = containingFile == null ? null : ModelBranch.getPsiBranch(containingFile);
        if (psiBranch != null) {
            modificationStamp += psiBranch.getBranchedPsiModificationCount();
        }
        if (containingFile != null) {
            psiElement = containingFile;
        }
        return modificationStamp + psiElement.getManager().getModificationTracker().getModificationCount();
    }

    public static <T, H extends PsiElement> PsiCacheKey<T, H> create(String str, Function<? super H, ? extends T> function) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        if (function == null) {
            $$$reportNull$$$0(6);
        }
        return new PsiCacheKey<>(str, function);
    }
}
