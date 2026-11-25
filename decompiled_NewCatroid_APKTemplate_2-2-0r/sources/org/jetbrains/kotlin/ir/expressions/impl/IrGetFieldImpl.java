package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrGetFieldImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\u0018\u00002\u00020\u0001BI\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fB=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u0010R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrGetFieldImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "startOffset", "", "endOffset", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "receiver", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "superQualifierSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "(IILorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "(IILorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "getEndOffset", "()I", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getStartOffset", "getSuperQualifierSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "setSuperQualifierSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;)V", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrGetFieldImpl extends IrGetField {
    private final int endOffset;
    private IrStatementOrigin origin;
    private final int startOffset;
    private IrClassSymbol superQualifierSymbol;
    private IrFieldSymbol symbol;
    private IrType type;

    public /* synthetic */ IrGetFieldImpl(int i, int i2, IrFieldSymbol irFieldSymbol, IrType irType, IrStatementOrigin irStatementOrigin, IrClassSymbol irClassSymbol, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irFieldSymbol, irType, (i3 & 16) != 0 ? null : irStatementOrigin, (i3 & 32) != 0 ? null : irClassSymbol);
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrDeclarationReference
    public IrFieldSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression
    public void setSymbol(IrFieldSymbol irFieldSymbol) {
        Intrinsics.checkNotNullParameter(irFieldSymbol, "<set-?>");
        this.symbol = irFieldSymbol;
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

    @Override // org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression
    public IrStatementOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression
    public void setOrigin(IrStatementOrigin irStatementOrigin) {
        this.origin = irStatementOrigin;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression
    public IrClassSymbol getSuperQualifierSymbol() {
        return this.superQualifierSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression
    public void setSuperQualifierSymbol(IrClassSymbol irClassSymbol) {
        this.superQualifierSymbol = irClassSymbol;
    }

    public IrGetFieldImpl(int i, int i2, IrFieldSymbol symbol, IrType type, IrStatementOrigin irStatementOrigin, IrClassSymbol irClassSymbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(type, "type");
        this.startOffset = i;
        this.endOffset = i2;
        this.symbol = symbol;
        this.type = type;
        this.origin = irStatementOrigin;
        this.superQualifierSymbol = irClassSymbol;
    }

    public /* synthetic */ IrGetFieldImpl(int i, int i2, IrFieldSymbol irFieldSymbol, IrType irType, IrExpression irExpression, IrStatementOrigin irStatementOrigin, IrClassSymbol irClassSymbol, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irFieldSymbol, irType, irExpression, (i3 & 32) != 0 ? null : irStatementOrigin, (i3 & 64) != 0 ? null : irClassSymbol);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrGetFieldImpl(int i, int i2, IrFieldSymbol symbol, IrType type, IrExpression irExpression, IrStatementOrigin irStatementOrigin, IrClassSymbol irClassSymbol) {
        this(i, i2, symbol, type, irStatementOrigin, irClassSymbol);
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(type, "type");
        setReceiver(irExpression);
    }
}
