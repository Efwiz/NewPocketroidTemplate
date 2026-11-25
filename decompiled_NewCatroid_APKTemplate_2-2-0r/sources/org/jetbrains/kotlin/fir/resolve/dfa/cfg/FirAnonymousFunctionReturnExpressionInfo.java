package org.jetbrains.kotlin.fir.resolve.dfa.cfg;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ControlFlowGraphBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\t¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/cfg/FirAnonymousFunctionReturnExpressionInfo;", "", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "isExplicit", "", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Z)V", "getExpression", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "()Z", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class FirAnonymousFunctionReturnExpressionInfo {
    private final FirExpression expression;
    private final boolean isExplicit;

    public static /* synthetic */ FirAnonymousFunctionReturnExpressionInfo copy$default(FirAnonymousFunctionReturnExpressionInfo firAnonymousFunctionReturnExpressionInfo, FirExpression firExpression, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            firExpression = firAnonymousFunctionReturnExpressionInfo.expression;
        }
        if ((i & 2) != 0) {
            z = firAnonymousFunctionReturnExpressionInfo.isExplicit;
        }
        return firAnonymousFunctionReturnExpressionInfo.copy(firExpression, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FirExpression getExpression() {
        return this.expression;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIsExplicit() {
        return this.isExplicit;
    }

    public final FirAnonymousFunctionReturnExpressionInfo copy(FirExpression expression, boolean isExplicit) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new FirAnonymousFunctionReturnExpressionInfo(expression, isExplicit);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FirAnonymousFunctionReturnExpressionInfo)) {
            return false;
        }
        FirAnonymousFunctionReturnExpressionInfo firAnonymousFunctionReturnExpressionInfo = (FirAnonymousFunctionReturnExpressionInfo) other;
        return Intrinsics.areEqual(this.expression, firAnonymousFunctionReturnExpressionInfo.expression) && this.isExplicit == firAnonymousFunctionReturnExpressionInfo.isExplicit;
    }

    public int hashCode() {
        return (this.expression.hashCode() * 31) + Boolean.hashCode(this.isExplicit);
    }

    public String toString() {
        return "FirAnonymousFunctionReturnExpressionInfo(expression=" + this.expression + ", isExplicit=" + this.isExplicit + ')';
    }

    public FirAnonymousFunctionReturnExpressionInfo(FirExpression expression, boolean z) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.expression = expression;
        this.isExplicit = z;
    }

    public final FirExpression getExpression() {
        return this.expression;
    }

    public final boolean isExplicit() {
        return this.isExplicit;
    }
}
