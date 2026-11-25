package org.jetbrains.kotlin.ir.backend.js.lower.coroutines;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;

/* JADX INFO: compiled from: StateMachineBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J5\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002H\u00160\u00182\u0006\u0010\u0019\u001a\u0002H\u0016H\u0016¢\u0006\u0002\u0010\u001aR\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR$\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u000e8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/IrDispatchPoint;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "target", "Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;", "(Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;)V", "endOffset", "", "getEndOffset", "()I", "startOffset", "getStartOffset", "getTarget", "()Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;", "value", "Lorg/jetbrains/kotlin/ir/types/IrType;", ModuleXmlParser.TYPE, "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "data", "(Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrDispatchPoint extends IrExpression {
    private final SuspendState target;

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return -1;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return -1;
    }

    public IrDispatchPoint(SuspendState target) {
        Intrinsics.checkNotNullParameter(target, "target");
        this.target = target;
    }

    public final SuspendState getTarget() {
        return this.target;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrExpression
    public IrType getType() {
        return this.target.getEntryBlock().getType();
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrExpression
    public void setType(IrType value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.target.getEntryBlock().setType(value);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.IrElement
    public <R, D> R accept(IrElementVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitExpression2(this, data);
    }
}
