package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrGetValueImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u0001B+\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tB1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\n\u001a\u00020\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetValueImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "startOffset", "", "endOffset", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "(IILorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getEndOffset", "()I", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;)V", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrGetValueImpl extends IrGetValue {
    private final int endOffset;
    private IrStatementOrigin origin;
    private final int startOffset;
    private IrValueSymbol symbol;
    private IrType type;

    public /* synthetic */ IrGetValueImpl(int i, int i2, IrType irType, IrValueSymbol irValueSymbol, IrStatementOrigin irStatementOrigin, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irType, irValueSymbol, (i3 & 16) != 0 ? null : irStatementOrigin);
    }

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
    public IrValueSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression
    public void setSymbol(IrValueSymbol irValueSymbol) {
        Intrinsics.checkNotNullParameter(irValueSymbol, "<set-?>");
        this.symbol = irValueSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression
    public IrStatementOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression
    public void setOrigin(IrStatementOrigin irStatementOrigin) {
        this.origin = irStatementOrigin;
    }

    public IrGetValueImpl(int i, int i2, IrType type, IrValueSymbol symbol, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.symbol = symbol;
        this.origin = irStatementOrigin;
    }

    public /* synthetic */ IrGetValueImpl(int i, int i2, IrValueSymbol irValueSymbol, IrStatementOrigin irStatementOrigin, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irValueSymbol, (i3 & 8) != 0 ? null : irStatementOrigin);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrGetValueImpl(int i, int i2, IrValueSymbol symbol, IrStatementOrigin irStatementOrigin) {
        this(i, i2, symbol.getOwner().getType(), symbol, irStatementOrigin);
        Intrinsics.checkNotNullParameter(symbol, "symbol");
    }
}
