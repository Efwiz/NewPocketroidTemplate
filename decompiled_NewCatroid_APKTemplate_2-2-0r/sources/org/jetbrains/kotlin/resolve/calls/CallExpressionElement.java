package org.jetbrains.kotlin.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;

/* JADX INFO: compiled from: CallExpressionUnroller.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00102\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\f8F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000e¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/CallExpressionElement;", "", SchemaSymbols.ATTVAL_QUALIFIED, "Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "(Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;)V", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "getNode", "()Lcom/intellij/lang/ASTNode;", "getQualified", "()Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "receiver", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getReceiver", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "safe", "", "getSafe", "()Z", "selector", "getSelector", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CallExpressionElement {
    private final KtQualifiedExpression qualified;

    public static /* synthetic */ CallExpressionElement copy$default(CallExpressionElement callExpressionElement, KtQualifiedExpression ktQualifiedExpression, int i, Object obj) {
        if ((i & 1) != 0) {
            ktQualifiedExpression = callExpressionElement.qualified;
        }
        return callExpressionElement.copy(ktQualifiedExpression);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final KtQualifiedExpression getQualified() {
        return this.qualified;
    }

    public final CallExpressionElement copy(KtQualifiedExpression qualified) {
        Intrinsics.checkNotNullParameter(qualified, "qualified");
        return new CallExpressionElement(qualified);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof CallExpressionElement) && Intrinsics.areEqual(this.qualified, ((CallExpressionElement) other).qualified);
    }

    public int hashCode() {
        return this.qualified.hashCode();
    }

    public String toString() {
        return "CallExpressionElement(qualified=" + this.qualified + ')';
    }

    public CallExpressionElement(KtQualifiedExpression qualified) {
        Intrinsics.checkNotNullParameter(qualified, "qualified");
        this.qualified = qualified;
    }

    public final KtQualifiedExpression getQualified() {
        return this.qualified;
    }

    public final KtExpression getReceiver() {
        return this.qualified.getReceiverExpression();
    }

    public final KtExpression getSelector() {
        return this.qualified.getSelectorExpression();
    }

    public final boolean getSafe() {
        return Intrinsics.areEqual(this.qualified.getOperationSign(), KtTokens.SAFE_ACCESS);
    }

    public final ASTNode getNode() {
        return this.qualified.getOperationTokenNode();
    }
}
