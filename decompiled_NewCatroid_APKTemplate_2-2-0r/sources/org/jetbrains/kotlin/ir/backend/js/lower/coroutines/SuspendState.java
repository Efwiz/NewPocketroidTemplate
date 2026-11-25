package org.jetbrains.kotlin.ir.backend.js.lower.coroutines;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: StateMachineBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010#\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00000\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/SuspendState;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "entryBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "getEntryBlock", "()Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "id", "", "getId", "()I", "setId", "(I)V", "successors", "", "getSuccessors", "()Ljava/util/Set;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SuspendState {
    private final IrContainerExpression entryBlock;
    private int id;
    private final Set<SuspendState> successors;

    public SuspendState(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.entryBlock = JsIrBuilder.buildComposite$default(JsIrBuilder.INSTANCE, type, null, 2, null);
        this.successors = new LinkedHashSet();
        this.id = -1;
    }

    public final IrContainerExpression getEntryBlock() {
        return this.entryBlock;
    }

    public final Set<SuspendState> getSuccessors() {
        return this.successors;
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int i) {
        this.id = i;
    }
}
