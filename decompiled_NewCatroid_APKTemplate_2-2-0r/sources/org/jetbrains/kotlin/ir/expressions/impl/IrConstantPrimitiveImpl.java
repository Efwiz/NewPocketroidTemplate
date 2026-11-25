package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrConstantObjectImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0003H\u0016R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u001a\u0010\u000b\u001a\u00020\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstantPrimitiveImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantPrimitive;", "startOffset", "", "endOffset", "value", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "(IILorg/jetbrains/kotlin/ir/expressions/IrConst;)V", "getEndOffset", "()I", "getStartOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getValue", "()Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "setValue", "(Lorg/jetbrains/kotlin/ir/expressions/IrConst;)V", "contentEquals", "", "other", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "contentHashCode", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrConstantPrimitiveImpl extends IrConstantPrimitive {
    private final int endOffset;
    private final int startOffset;
    private IrType type;
    private IrConst<?> value;

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive
    public IrConst<?> getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive
    public void setValue(IrConst<?> irConst) {
        Intrinsics.checkNotNullParameter(irConst, "<set-?>");
        this.value = irConst;
    }

    public IrConstantPrimitiveImpl(int i, int i2, IrConst<?> value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.startOffset = i;
        this.endOffset = i2;
        this.value = value;
        this.type = getValue().getType();
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstantValue
    public boolean contentEquals(IrConstantValue other) {
        Intrinsics.checkNotNullParameter(other, "other");
        if ((other instanceof IrConstantPrimitive) && Intrinsics.areEqual(getType(), other.getType())) {
            IrConstantPrimitive irConstantPrimitive = (IrConstantPrimitive) other;
            if (Intrinsics.areEqual(getValue().getType(), irConstantPrimitive.getValue().getType()) && Intrinsics.areEqual(getValue().getKind(), irConstantPrimitive.getValue().getKind()) && Intrinsics.areEqual(getValue().getValue(), irConstantPrimitive.getValue().getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstantValue
    public int contentHashCode() {
        int iHashCode = ((((getType().hashCode() * 31) + getValue().getType().hashCode()) * 31) + getValue().getKind().hashCode()) * 31;
        Object value = getValue().getValue();
        return iHashCode + (value != null ? value.hashCode() : 0);
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrExpression
    public IrType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrExpression
    public void setType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.type = irType;
    }
}
