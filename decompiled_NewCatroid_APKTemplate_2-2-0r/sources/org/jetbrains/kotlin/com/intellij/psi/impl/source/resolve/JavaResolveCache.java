package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.impl.AnyPsiChangeListener;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.PsiPolyExpressionUtil;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;

/* JADX INFO: loaded from: classes3.dex */
public class JavaResolveCache {
    private static final Object NULL = Key.create("NULL");
    private final AtomicReference<Map<PsiVariable, Object>> myVarToConstValueMapNonPhysical;
    private final AtomicReference<Map<PsiVariable, Object>> myVarToConstValueMapPhysical;

    @FunctionalInterface
    public interface ConstValueComputer {
        Object execute(PsiVariable psiVariable, Set<PsiVariable> set);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "expr";
        } else if (i == 2) {
            objArr[0] = "f";
        } else if (i == 3) {
            objArr[0] = "variable";
        } else if (i != 4) {
            objArr[0] = "project";
        } else {
            objArr[0] = "computer";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/JavaResolveCache";
        if (i == 1 || i == 2) {
            objArr[2] = "getType";
        } else if (i == 3 || i == 4) {
            objArr[2] = "computeConstantValueWithCaching";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static JavaResolveCache getInstance(Project project) {
        return (JavaResolveCache) project.getService(JavaResolveCache.class);
    }

    public JavaResolveCache(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myVarToConstValueMapPhysical = new AtomicReference<>();
        this.myVarToConstValueMapNonPhysical = new AtomicReference<>();
        project.getMessageBus().connect().subscribe(PsiManagerImpl.ANY_PSI_CHANGE_TOPIC, new AnyPsiChangeListener() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveCache.1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.AnyPsiChangeListener
            public void beforePsiChanged(boolean z) {
                JavaResolveCache.this.clearCaches(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearCaches(boolean z) {
        if (z) {
            this.myVarToConstValueMapPhysical.set(null);
        }
        this.myVarToConstValueMapNonPhysical.set(null);
    }

    public <T extends PsiExpression> PsiType getType(T t, final Function<? super T, ? extends PsiType> function) {
        if (t == null) {
            $$$reportNull$$$0(1);
        }
        if (function == null) {
            $$$reportNull$$$0(2);
        }
        if (MethodCandidateInfo.isOverloadCheck() && PsiPolyExpressionUtil.isPolyExpression(t)) {
            return function.fun(t);
        }
        return (PsiType) CachedValuesManager.getProjectPsiDependentCache(t, new java.util.function.Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveCache$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return JavaResolveCache.lambda$getType$0(function, (PsiExpression) obj);
            }
        });
    }

    static /* synthetic */ PsiType lambda$getType$0(Function function, PsiExpression psiExpression) {
        return (PsiType) function.fun(psiExpression);
    }

    public Object computeConstantValueWithCaching(PsiVariable psiVariable, ConstValueComputer constValueComputer, Set<PsiVariable> set) {
        if (psiVariable == null) {
            $$$reportNull$$$0(3);
        }
        if (constValueComputer == null) {
            $$$reportNull$$$0(4);
        }
        AtomicReference<Map<PsiVariable, Object>> atomicReference = psiVariable.getIsPhysical() ? this.myVarToConstValueMapPhysical : this.myVarToConstValueMapNonPhysical;
        Map<PsiVariable, Object> map = atomicReference.get();
        if (map == null) {
            map = (Map) ConcurrencyUtil.cacheOrGet(atomicReference, CollectionFactory.createConcurrentWeakMap());
        }
        Object obj = map.get(psiVariable);
        Object obj2 = NULL;
        if (obj == obj2) {
            return null;
        }
        if (obj != null) {
            return obj;
        }
        Object objExecute = constValueComputer.execute(psiVariable, set);
        if (objExecute != null) {
            obj2 = objExecute;
        }
        map.put(psiVariable, obj2);
        return objExecute;
    }
}
