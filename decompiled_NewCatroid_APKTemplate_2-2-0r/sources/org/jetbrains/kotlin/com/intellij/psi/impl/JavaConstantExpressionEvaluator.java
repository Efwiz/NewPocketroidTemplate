package org.jetbrains.kotlin.com.intellij.psi.impl;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Factory;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiConstantEvaluationHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrefixExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaConstantExpressionEvaluator extends JavaRecursiveElementWalkingVisitor {
    private final ConstantExpressionVisitor myConstantExpressionVisitor;
    private final Factory<ConcurrentMap<PsiElement, Object>> myMapFactory;
    private final Project myProject;
    private static final Key<CachedValue<ConcurrentMap<PsiElement, Object>>> CONSTANT_VALUE_WO_OVERFLOW_MAP_KEY = Key.create("CONSTANT_VALUE_WO_OVERFLOW_MAP_KEY");
    private static final Key<CachedValue<ConcurrentMap<PsiElement, Object>>> CONSTANT_VALUE_WITH_OVERFLOW_MAP_KEY = Key.create("CONSTANT_VALUE_WITH_OVERFLOW_MAP_KEY");
    private static final Object NO_VALUE = ObjectUtils.NULL;
    private static final CachedValueProvider<ConcurrentMap<PsiElement, Object>> PROVIDER = new CachedValueProvider() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.JavaConstantExpressionEvaluator$$ExternalSyntheticLambda1
        @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
        public final CachedValueProvider.Result compute() {
            return CachedValueProvider.Result.create(CollectionFactory.createConcurrentWeakMap(), PsiModificationTracker.MODIFICATION_COUNT);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 5 ? 3 : 2];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i != 5) {
            objArr[0] = "project";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/JavaConstantExpressionEvaluator";
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/JavaConstantExpressionEvaluator";
        } else {
            objArr[1] = "map";
        }
        if (i == 1) {
            objArr[2] = "elementFinished";
        } else if (i == 2) {
            objArr[2] = "visitElement";
        } else if (i == 3) {
            objArr[2] = "getCached";
        } else if (i == 4) {
            objArr[2] = ResponseCacheMiddleware.CACHE;
        } else if (i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 5) {
            throw new IllegalStateException(str2);
        }
    }

    private JavaConstantExpressionEvaluator(Set<PsiVariable> set, final boolean z, Project project, final PsiConstantEvaluationHelper.AuxEvaluator auxEvaluator) {
        Factory<ConcurrentMap<PsiElement, Object>> factory;
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (auxEvaluator == null) {
            factory = new Factory<ConcurrentMap<PsiElement, Object>>() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.JavaConstantExpressionEvaluator.1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Factory
                public ConcurrentMap<PsiElement, Object> create() {
                    return (ConcurrentMap) CachedValuesManager.getManager(JavaConstantExpressionEvaluator.this.myProject).getCachedValue(JavaConstantExpressionEvaluator.this.myProject, z ? JavaConstantExpressionEvaluator.CONSTANT_VALUE_WITH_OVERFLOW_MAP_KEY : JavaConstantExpressionEvaluator.CONSTANT_VALUE_WO_OVERFLOW_MAP_KEY, JavaConstantExpressionEvaluator.PROVIDER, false);
                }
            };
        } else {
            factory = new Factory() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.JavaConstantExpressionEvaluator$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Factory
                public final Object create() {
                    return auxEvaluator.getCacheMap(z);
                }
            };
        }
        this.myMapFactory = factory;
        this.myProject = project;
        this.myConstantExpressionVisitor = new ConstantExpressionVisitor(set, z, auxEvaluator);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor
    protected void elementFinished(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement instanceof PsiExpression) {
            PsiExpression psiExpression = (PsiExpression) psiElement;
            Object cached = getCached(psiExpression);
            if (cached == null) {
                cache(psiExpression, this.myConstantExpressionVisitor.handle(psiElement));
                return;
            }
            ConstantExpressionVisitor constantExpressionVisitor = this.myConstantExpressionVisitor;
            if (cached == NO_VALUE) {
                cached = null;
            }
            constantExpressionVisitor.store(psiElement, cached);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
    public void visitElement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (!(psiElement instanceof PsiExpression)) {
            super.visitElement(psiElement);
            return;
        }
        Object cached = getCached((PsiExpression) psiElement);
        if (cached == null) {
            super.visitElement(psiElement);
            return;
        }
        ConstantExpressionVisitor constantExpressionVisitor = this.myConstantExpressionVisitor;
        if (cached == NO_VALUE) {
            cached = null;
        }
        constantExpressionVisitor.store(psiElement, cached);
    }

    private Object getCached(PsiExpression psiExpression) {
        if (psiExpression == null) {
            $$$reportNull$$$0(3);
        }
        return map().get(psiExpression);
    }

    private void cache(PsiExpression psiExpression, Object obj) {
        if (psiExpression == null) {
            $$$reportNull$$$0(4);
        }
        ConcurrentMap<PsiElement, Object> map = map();
        if (obj == null) {
            obj = NO_VALUE;
        }
        ConcurrencyUtil.cacheOrGet(map, psiExpression, obj);
    }

    private ConcurrentMap<PsiElement, Object> map() {
        ConcurrentMap<PsiElement, Object> concurrentMapCreate = this.myMapFactory.create();
        if (concurrentMapCreate == null) {
            $$$reportNull$$$0(5);
        }
        return concurrentMapCreate;
    }

    public static Object computeConstantExpression(PsiExpression psiExpression, Set<PsiVariable> set, boolean z) {
        return computeConstantExpression(psiExpression, set, z, null);
    }

    public static Object computeConstantExpression(PsiExpression psiExpression, Set<PsiVariable> set, boolean z, PsiConstantEvaluationHelper.AuxEvaluator auxEvaluator) {
        if (psiExpression == null) {
            return null;
        }
        if (psiExpression instanceof PsiLiteralExpression) {
            return ((PsiLiteralExpression) psiExpression).getValue();
        }
        JavaConstantExpressionEvaluator javaConstantExpressionEvaluator = new JavaConstantExpressionEvaluator(set, z, psiExpression.getProject(), auxEvaluator);
        if (psiExpression instanceof PsiCompiledElement) {
            if (psiExpression instanceof PsiPrefixExpression) {
                PsiExpression operand = ((PsiPrefixExpression) psiExpression).getOperand();
                if (operand == null) {
                    return null;
                }
                javaConstantExpressionEvaluator.myConstantExpressionVisitor.store(operand, javaConstantExpressionEvaluator.myConstantExpressionVisitor.handle(operand));
            }
            return javaConstantExpressionEvaluator.myConstantExpressionVisitor.handle(psiExpression);
        }
        psiExpression.accept(javaConstantExpressionEvaluator);
        Object cached = javaConstantExpressionEvaluator.getCached(psiExpression);
        if (cached == NO_VALUE) {
            return null;
        }
        return cached;
    }

    public static Object computeConstantExpression(PsiExpression psiExpression, boolean z) {
        return computeConstantExpression(psiExpression, null, z);
    }
}
