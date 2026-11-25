package org.jetbrains.kotlin.ir.builders;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IrBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b&\u0018\u00002\u00020\u00012\u00020\u0002B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\nR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilder;", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorWithScope;", "context", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "scope", "Lorg/jetbrains/kotlin/ir/builders/Scope;", "startOffset", "", "endOffset", "(Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;Lorg/jetbrains/kotlin/ir/builders/Scope;II)V", "getScope", "()Lorg/jetbrains/kotlin/ir/builders/Scope;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrBuilderWithScope extends IrBuilder implements IrGeneratorWithScope {
    private final Scope scope;

    @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorWithScope
    public Scope getScope() {
        return this.scope;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBuilderWithScope(IrGeneratorContext context, Scope scope, int i, int i2) {
        super(context, i, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.scope = scope;
    }
}
