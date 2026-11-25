package org.jetbrains.kotlin.cfg;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtWhenCondition;
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: WhenChecker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/cfg/WhenOnNullableExhaustivenessChecker;", "", "()V", "getMissingCases", "", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "expression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "nullable", "", "getNullCaseIfMissing", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class WhenOnNullableExhaustivenessChecker {
    public static final WhenOnNullableExhaustivenessChecker INSTANCE = new WhenOnNullableExhaustivenessChecker();

    private WhenOnNullableExhaustivenessChecker() {
    }

    public final List<WhenMissingCase> getMissingCases(KtWhenExpression expression, BindingContext context, boolean nullable) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        return nullable ? getNullCaseIfMissing(expression, context) : CollectionsKt.emptyList();
    }

    private final List<WhenMissingCase> getNullCaseIfMissing(KtWhenExpression expression, BindingContext context) {
        KtExpression expression2;
        KotlinType type;
        Iterator<KtWhenEntry> it = expression.getEntries().iterator();
        while (it.hasNext()) {
            KtWhenCondition[] conditions = it.next().getConditions();
            Intrinsics.checkNotNullExpressionValue(conditions, "getConditions(...)");
            for (KtWhenCondition ktWhenCondition : conditions) {
                if ((ktWhenCondition instanceof KtWhenConditionWithExpression) && (expression2 = ((KtWhenConditionWithExpression) ktWhenCondition).getExpression()) != null && (type = context.getType(expression2)) != null && KotlinBuiltIns.isNullableNothing(type)) {
                    return CollectionsKt.emptyList();
                }
            }
        }
        return CollectionsKt.listOf(WhenMissingCase.NullIsMissing.INSTANCE);
    }
}
