package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.psi.KtExpression;

/* JADX INFO: compiled from: StatementFilter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0016\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005¢\u0006\u0002\u0010\u0002R\"\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/StatementFilter;", "", "()V", "filter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "", "getFilter", "()Lkotlin/jvm/functions/Function1;", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class StatementFilter {
    public static final StatementFilter NONE = new StatementFilter() { // from class: org.jetbrains.kotlin.resolve.StatementFilter$Companion$NONE$1
        public String toString() {
            return "NONE";
        }
    };

    public Function1<KtExpression, Boolean> getFilter() {
        return null;
    }
}
