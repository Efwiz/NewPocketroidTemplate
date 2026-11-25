package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrGetEnumValueImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetEnumValueImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;)V", "getEndOffset", "()I", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;)V", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrGetEnumValueImpl extends IrGetEnumValue {
    private final int endOffset;
    private final int startOffset;
    private IrEnumEntrySymbol symbol;
    private IrType type;

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
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

    @Override // org.jetbrains.kotlin.ir.expressions.IrDeclarationReference
    public IrEnumEntrySymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrGetEnumValue
    public void setSymbol(IrEnumEntrySymbol irEnumEntrySymbol) {
        Intrinsics.checkNotNullParameter(irEnumEntrySymbol, "<set-?>");
        this.symbol = irEnumEntrySymbol;
    }

    public IrGetEnumValueImpl(int i, int i2, IrType type, IrEnumEntrySymbol symbol) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.symbol = symbol;
    }
}
