package org.jetbrains.kotlin.fir.analysis.checkers.context;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.SessionHolder;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;

/* JADX INFO: compiled from: AbstractCheckerContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\b&\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\nR\u0014\u0010\t\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0014\u0010\b\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/context/AbstractCheckerContext;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "sessionHolder", "Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "allInfosSuppressed", "", "allWarningsSuppressed", "allErrorsSuppressed", "(Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;ZZZ)V", "getAllErrorsSuppressed", "()Z", "getAllInfosSuppressed", "getAllWarningsSuppressed", "getReturnTypeCalculator", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "getSessionHolder", "()Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractCheckerContext extends CheckerContext {
    private final boolean allErrorsSuppressed;
    private final boolean allInfosSuppressed;
    private final boolean allWarningsSuppressed;
    private final ReturnTypeCalculator returnTypeCalculator;
    private final SessionHolder sessionHolder;

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public SessionHolder getSessionHolder() {
        return this.sessionHolder;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public ReturnTypeCalculator getReturnTypeCalculator() {
        return this.returnTypeCalculator;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public boolean getAllInfosSuppressed() {
        return this.allInfosSuppressed;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public boolean getAllWarningsSuppressed() {
        return this.allWarningsSuppressed;
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
    public boolean getAllErrorsSuppressed() {
        return this.allErrorsSuppressed;
    }

    public AbstractCheckerContext(SessionHolder sessionHolder, ReturnTypeCalculator returnTypeCalculator, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(sessionHolder, "sessionHolder");
        Intrinsics.checkNotNullParameter(returnTypeCalculator, "returnTypeCalculator");
        this.sessionHolder = sessionHolder;
        this.returnTypeCalculator = returnTypeCalculator;
        this.allInfosSuppressed = z;
        this.allWarningsSuppressed = z2;
        this.allErrorsSuppressed = z3;
    }
}
