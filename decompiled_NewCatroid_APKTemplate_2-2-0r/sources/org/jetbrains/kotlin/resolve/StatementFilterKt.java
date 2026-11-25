package org.jetbrains.kotlin.resolve;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;

/* JADX INFO: compiled from: StatementFilter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0007"}, d2 = {"filterStatements", "", "Lorg/jetbrains/kotlin/psi/KtExpression;", "Lorg/jetbrains/kotlin/resolve/StatementFilter;", "block", "Lorg/jetbrains/kotlin/psi/KtBlockExpression;", "getLastStatementInABlock", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class StatementFilterKt {
    public static final List<KtExpression> filterStatements(StatementFilter statementFilter, KtBlockExpression block) {
        Intrinsics.checkNotNullParameter(statementFilter, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        if (statementFilter.getFilter() == null || (block instanceof KtPsiUtil.KtExpressionWrapper)) {
            List<KtExpression> statements = block.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            return statements;
        }
        List<KtExpression> statements2 = block.getStatements();
        Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : statements2) {
            KtExpression ktExpression = (KtExpression) obj;
            Function1<KtExpression, Boolean> filter = statementFilter.getFilter();
            Intrinsics.checkNotNull(filter);
            Intrinsics.checkNotNull(ktExpression);
            if (filter.invoke(ktExpression).booleanValue()) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final KtExpression getLastStatementInABlock(StatementFilter statementFilter, KtBlockExpression block) {
        Intrinsics.checkNotNullParameter(statementFilter, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        return (KtExpression) CollectionsKt.lastOrNull((List) filterStatements(statementFilter, block));
    }
}
