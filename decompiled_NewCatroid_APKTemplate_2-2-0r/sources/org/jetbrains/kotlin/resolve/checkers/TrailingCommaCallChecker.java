package org.jetbrains.kotlin.resolve.checkers;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: TrailingCommaChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/TrailingCommaCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TrailingCommaCallChecker implements CallChecker {
    public static final TrailingCommaCallChecker INSTANCE = new TrailingCommaCallChecker();

    private TrailingCommaCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        KtElement callElement = resolvedCall.getCall().getCallElement();
        Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
        if (callElement instanceof KtArrayAccessExpression) {
            TrailingCommaChecker.INSTANCE.check(((KtArrayAccessExpression) callElement).getTrailingComma(), context.getTrace(), context.getLanguageVersionSettings());
            return;
        }
        if (callElement instanceof KtCollectionLiteralExpression) {
            TrailingCommaChecker.INSTANCE.check(((KtCollectionLiteralExpression) callElement).getTrailingComma(), context.getTrace(), context.getLanguageVersionSettings());
            return;
        }
        if (callElement instanceof KtWhenExpression) {
            KtWhenExpression ktWhenExpression = (KtWhenExpression) callElement;
            if (ktWhenExpression.getSubjectExpression() != null) {
                List<KtWhenEntry> entries = ktWhenExpression.getEntries();
                Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
                Iterator<T> it = entries.iterator();
                while (it.hasNext()) {
                    TrailingCommaChecker.INSTANCE.check(((KtWhenEntry) it.next()).getTrailingComma(), context.getTrace(), context.getLanguageVersionSettings());
                }
                return;
            }
            return;
        }
        Call call = resolvedCall.getCall();
        TrailingCommaChecker trailingCommaChecker = TrailingCommaChecker.INSTANCE;
        KtValueArgumentList valueArgumentList = call.getValueArgumentList();
        trailingCommaChecker.check(valueArgumentList != null ? valueArgumentList.getTrailingComma() : null, context.getTrace(), context.getLanguageVersionSettings());
    }
}
