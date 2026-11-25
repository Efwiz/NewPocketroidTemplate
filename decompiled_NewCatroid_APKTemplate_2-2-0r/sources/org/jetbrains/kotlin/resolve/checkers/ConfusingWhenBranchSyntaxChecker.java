package org.jetbrains.kotlin.resolve.checkers;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtIsExpression;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtWhenCondition;
import org.jetbrains.kotlin.psi.KtWhenConditionInRange;
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.BindingTrace;

/* JADX INFO: compiled from: ConfusingWhenBranchSyntaxChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ \u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\"\u0010\u0011\u001a\u00020\u00072\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002R\u0013\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ConfusingWhenBranchSyntaxChecker;", "", "()V", "prohibitedTokens", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "Lorg/jetbrains/annotations/NotNull;", "check", "", "whenExpression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "checkCondition", "condition", "Lorg/jetbrains/kotlin/psi/KtWhenCondition;", "checkConditionExpression", "rawExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConfusingWhenBranchSyntaxChecker {
    public static final ConfusingWhenBranchSyntaxChecker INSTANCE = new ConfusingWhenBranchSyntaxChecker();
    private static final TokenSet prohibitedTokens;

    private ConfusingWhenBranchSyntaxChecker() {
    }

    static {
        TokenSet tokenSetCreate = TokenSet.create(KtTokens.IN_KEYWORD, KtTokens.NOT_IN, KtTokens.LT, KtTokens.LTEQ, KtTokens.GT, KtTokens.GTEQ, KtTokens.EQEQ, KtTokens.EXCLEQ, KtTokens.EQEQEQ, KtTokens.EXCLEQEQEQ, KtTokens.ANDAND, KtTokens.OROR);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
        prohibitedTokens = tokenSetCreate;
    }

    public final void check(KtWhenExpression whenExpression, LanguageVersionSettings languageVersionSettings, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(trace, "trace");
        if (whenExpression.getSubjectExpression() == null && whenExpression.getSubjectVariable() == null) {
            return;
        }
        Iterator<KtWhenEntry> it = whenExpression.getEntries().iterator();
        while (it.hasNext()) {
            KtWhenCondition[] conditions = it.next().getConditions();
            Intrinsics.checkNotNullExpressionValue(conditions, "getConditions(...)");
            for (KtWhenCondition ktWhenCondition : conditions) {
                Intrinsics.checkNotNull(ktWhenCondition);
                checkCondition(ktWhenCondition, languageVersionSettings, trace);
            }
        }
    }

    private final void checkCondition(KtWhenCondition condition, LanguageVersionSettings languageVersionSettings, BindingTrace trace) {
        if (condition instanceof KtWhenConditionWithExpression) {
            checkConditionExpression(((KtWhenConditionWithExpression) condition).getExpression(), languageVersionSettings, trace);
        } else if (condition instanceof KtWhenConditionInRange) {
            checkConditionExpression(((KtWhenConditionInRange) condition).getRangeExpression(), languageVersionSettings, trace);
        }
    }

    private final void checkConditionExpression(KtExpression rawExpression, LanguageVersionSettings languageVersionSettings, BindingTrace trace) {
        boolean zContains;
        if (rawExpression == null || (rawExpression instanceof KtParenthesizedExpression)) {
            return;
        }
        KtExpression ktExpressionSafeDeparenthesize = KtPsiUtil.safeDeparenthesize(rawExpression);
        Intrinsics.checkNotNullExpressionValue(ktExpressionSafeDeparenthesize, "safeDeparenthesize(...)");
        if (ktExpressionSafeDeparenthesize instanceof KtIsExpression) {
            zContains = true;
        } else {
            zContains = ktExpressionSafeDeparenthesize instanceof KtBinaryExpression ? prohibitedTokens.contains(((KtBinaryExpression) ktExpressionSafeDeparenthesize).getOperationToken()) : false;
        }
        if (zContains) {
            trace.report(Errors.CONFUSING_BRANCH_CONDITION.on(languageVersionSettings, rawExpression));
        }
    }
}
