package org.jetbrains.kotlin.backend.common.lower.loops;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: HeaderProcessor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/LoopReplacement;", "", "newLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "replacementExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrLoop;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getNewLoop", "()Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "getReplacementExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LoopReplacement {
    private final IrLoop newLoop;
    private final IrExpression replacementExpression;

    public static /* synthetic */ LoopReplacement copy$default(LoopReplacement loopReplacement, IrLoop irLoop, IrExpression irExpression, int i, Object obj) {
        if ((i & 1) != 0) {
            irLoop = loopReplacement.newLoop;
        }
        if ((i & 2) != 0) {
            irExpression = loopReplacement.replacementExpression;
        }
        return loopReplacement.copy(irLoop, irExpression);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final IrLoop getNewLoop() {
        return this.newLoop;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final IrExpression getReplacementExpression() {
        return this.replacementExpression;
    }

    public final LoopReplacement copy(IrLoop newLoop, IrExpression replacementExpression) {
        Intrinsics.checkNotNullParameter(newLoop, "newLoop");
        Intrinsics.checkNotNullParameter(replacementExpression, "replacementExpression");
        return new LoopReplacement(newLoop, replacementExpression);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LoopReplacement)) {
            return false;
        }
        LoopReplacement loopReplacement = (LoopReplacement) other;
        return Intrinsics.areEqual(this.newLoop, loopReplacement.newLoop) && Intrinsics.areEqual(this.replacementExpression, loopReplacement.replacementExpression);
    }

    public int hashCode() {
        return (this.newLoop.hashCode() * 31) + this.replacementExpression.hashCode();
    }

    public String toString() {
        return "LoopReplacement(newLoop=" + this.newLoop + ", replacementExpression=" + this.replacementExpression + ')';
    }

    public LoopReplacement(IrLoop newLoop, IrExpression replacementExpression) {
        Intrinsics.checkNotNullParameter(newLoop, "newLoop");
        Intrinsics.checkNotNullParameter(replacementExpression, "replacementExpression");
        this.newLoop = newLoop;
        this.replacementExpression = replacementExpression;
    }

    public final IrLoop getNewLoop() {
        return this.newLoop;
    }

    public final IrExpression getReplacementExpression() {
        return this.replacementExpression;
    }
}
