package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBodyKind;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrSyntheticBodyImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\t¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrSyntheticBodyImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBody;", "startOffset", "", "endOffset", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBodyKind;", "(IILorg/jetbrains/kotlin/ir/expressions/IrSyntheticBodyKind;)V", "getEndOffset", "()I", "getKind", "()Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBodyKind;", "setKind", "(Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBodyKind;)V", "getStartOffset", "toString", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrSyntheticBodyImpl extends IrSyntheticBody {
    private final int endOffset;
    private IrSyntheticBodyKind kind;
    private final int startOffset;

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrSyntheticBody
    public IrSyntheticBodyKind getKind() {
        return this.kind;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrSyntheticBody
    public void setKind(IrSyntheticBodyKind irSyntheticBodyKind) {
        Intrinsics.checkNotNullParameter(irSyntheticBodyKind, "<set-?>");
        this.kind = irSyntheticBodyKind;
    }

    public IrSyntheticBodyImpl(int i, int i2, IrSyntheticBodyKind kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.startOffset = i;
        this.endOffset = i2;
        this.kind = kind;
    }

    public String toString() {
        return "IrSyntheticBodyImpl(" + getKind() + ')';
    }
}
