package org.jetbrains.kotlin.cfg;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtWhenCondition;
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.CompileTimeConstantUtils;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: WhenChecker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/cfg/WhenOnBooleanExhaustivenessChecker;", "Lorg/jetbrains/kotlin/cfg/WhenExhaustivenessChecker;", "()V", "getMissingCases", "", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "expression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "subjectDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "nullable", "", "isApplicable", "subjectType", "Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class WhenOnBooleanExhaustivenessChecker implements WhenExhaustivenessChecker {
    public static final WhenOnBooleanExhaustivenessChecker INSTANCE = new WhenOnBooleanExhaustivenessChecker();

    private WhenOnBooleanExhaustivenessChecker() {
    }

    @Override // org.jetbrains.kotlin.cfg.WhenExhaustivenessChecker
    public List<WhenMissingCase> getMissingCases(KtWhenExpression expression, BindingContext context, ClassDescriptor subjectDescriptor, boolean nullable) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Iterator<KtWhenEntry> it = expression.getEntries().iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            KtWhenCondition[] conditions = it.next().getConditions();
            Intrinsics.checkNotNullExpressionValue(conditions, "getConditions(...)");
            for (KtWhenCondition ktWhenCondition : conditions) {
                if (ktWhenCondition instanceof KtWhenConditionWithExpression) {
                    KtExpression expression2 = ((KtWhenConditionWithExpression) ktWhenCondition).getExpression();
                    if (CompileTimeConstantUtils.canBeReducedToBooleanConstant(expression2, context, true)) {
                        z = true;
                    }
                    if (CompileTimeConstantUtils.canBeReducedToBooleanConstant(expression2, context, false)) {
                        z2 = true;
                    }
                }
            }
        }
        return CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) (!z ? CollectionsKt.listOf(WhenMissingCase.BooleanIsMissing.TrueIsMissing.INSTANCE) : CollectionsKt.emptyList()), (Iterable) (!z2 ? CollectionsKt.listOf(WhenMissingCase.BooleanIsMissing.FalseIsMissing.INSTANCE) : CollectionsKt.emptyList())), (Iterable) WhenOnNullableExhaustivenessChecker.INSTANCE.getMissingCases(expression, context, nullable));
    }

    @Override // org.jetbrains.kotlin.cfg.WhenExhaustivenessChecker
    public boolean isApplicable(KotlinType subjectType) {
        Intrinsics.checkNotNullParameter(subjectType, "subjectType");
        return KotlinBuiltIns.isBoolean(TypeUtils.makeNotNullable(subjectType));
    }
}
