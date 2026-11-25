package org.jetbrains.kotlin.ir.builders;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IrBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\r¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/IrBuilder;", "Lorg/jetbrains/kotlin/ir/builders/IrGenerator;", "context", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "startOffset", "", "endOffset", "(Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;II)V", "getContext", "()Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "getEndOffset", "()I", "setEndOffset", "(I)V", "getStartOffset", "setStartOffset", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrBuilder implements IrGenerator {
    private final IrGeneratorContext context;
    private int endOffset;
    private int startOffset;

    public IrBuilder(IrGeneratorContext context, int i, int i2) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.startOffset = i;
        this.endOffset = i2;
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGenerator
    public IrGeneratorContext getContext() {
        return this.context;
    }

    public final int getStartOffset() {
        return this.startOffset;
    }

    public final void setStartOffset(int i) {
        this.startOffset = i;
    }

    public final int getEndOffset() {
        return this.endOffset;
    }

    public final void setEndOffset(int i) {
        this.endOffset = i;
    }
}
