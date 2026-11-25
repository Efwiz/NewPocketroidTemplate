package org.jetbrains.kotlin.backend.common;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.builders.Scope;

/* JADX INFO: compiled from: IrElementTransformerVoidWithContext.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/ScopeWithIr;", "", "scope", "Lorg/jetbrains/kotlin/ir/builders/Scope;", "irElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "(Lorg/jetbrains/kotlin/ir/builders/Scope;Lorg/jetbrains/kotlin/ir/IrElement;)V", "getIrElement", "()Lorg/jetbrains/kotlin/ir/IrElement;", "getScope", "()Lorg/jetbrains/kotlin/ir/builders/Scope;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ScopeWithIr {
    private final IrElement irElement;
    private final Scope scope;

    public ScopeWithIr(Scope scope, IrElement irElement) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        this.scope = scope;
        this.irElement = irElement;
    }

    public final IrElement getIrElement() {
        return this.irElement;
    }

    public final Scope getScope() {
        return this.scope;
    }
}
