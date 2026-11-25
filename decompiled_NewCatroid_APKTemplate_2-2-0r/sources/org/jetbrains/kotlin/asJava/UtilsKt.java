package org.jetbrains.kotlin.asJava;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.elements.KtLightElementBase;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.DelegatingBindingTrace;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"computeExpression", "", "expression", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "light-classes"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    private static final Object computeExpression$evalConstantValue(ConstantValue<?> constantValue) {
        if (constantValue instanceof ArrayValue) {
            List<? extends ConstantValue<?>> value = ((ArrayValue) constantValue).getValue();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(value, 10));
            Iterator<T> it = value.iterator();
            while (it.hasNext()) {
                arrayList.add(computeExpression$evalConstantValue((ConstantValue) it.next()));
            }
            ArrayList arrayList2 = arrayList;
            Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) arrayList2);
            return objSingleOrNull == null ? arrayList2 : objSingleOrNull;
        }
        return constantValue.getValue();
    }

    public static final Object computeExpression(PsiElement expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!(expression instanceof KtLightElementBase)) {
            return null;
        }
        KtElement kotlinOrigin = ((KtLightElementBase) expression).getKotlinOrigin();
        final KtExpression ktExpression = kotlinOrigin instanceof KtExpression ? (KtExpression) kotlinOrigin : null;
        if (ktExpression == null) {
            return null;
        }
        LightClassGenerationSupport.Companion companion = LightClassGenerationSupport.INSTANCE;
        Project project = ktExpression.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        final LightClassGenerationSupport companion2 = companion.getInstance(project);
        final ConstantExpressionEvaluator constantExpressionEvaluatorCreateConstantEvaluator = companion2.createConstantEvaluator(ktExpression);
        CompileTimeConstant compileTimeConstant = (CompileTimeConstant) ApplicationManager.getApplication().runReadAction(new Computable() { // from class: org.jetbrains.kotlin.asJava.UtilsKt$computeExpression$$inlined$runReadAction$1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final T compute() {
                return (T) ConstantExpressionEvaluator.evaluateExpression$default(constantExpressionEvaluatorCreateConstantEvaluator, ktExpression, new DelegatingBindingTrace(companion2.analyze(ktExpression), "Evaluating annotation argument", false, null, false, null, 60, null), null, 4, null);
            }
        });
        if (compileTimeConstant == null || compileTimeConstant.isError()) {
            return null;
        }
        SimpleType NO_EXPECTED_TYPE = TypeUtils.NO_EXPECTED_TYPE;
        Intrinsics.checkNotNullExpressionValue(NO_EXPECTED_TYPE, "NO_EXPECTED_TYPE");
        return computeExpression$evalConstantValue(compileTimeConstant.toConstantValue(NO_EXPECTED_TYPE));
    }
}
