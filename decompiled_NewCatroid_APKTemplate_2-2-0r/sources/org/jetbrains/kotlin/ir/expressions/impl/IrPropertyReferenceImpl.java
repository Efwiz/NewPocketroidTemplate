package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.EmptyArraysForMemberAccessKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrPropertyReferenceImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001BW\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0019\"\u0004\b!\u0010\u001bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0013R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001e\u0010+\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060,X\u0094\u0004¢\u0006\n\n\u0002\u0010/\u001a\u0004\b-\u0010.R\u001e\u00100\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001010,X\u0094\u0004¢\u0006\n\n\u0002\u00104\u001a\u0004\b2\u00103¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrPropertyReferenceImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "typeArgumentsCount", "field", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "getter", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "setter", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;ILorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getEndOffset", "()I", "getField", "()Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "setField", "(Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;)V", "getGetter", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "setGetter", "(Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getSetter", "setSetter", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;)V", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "typeArguments", "", "getTypeArguments", "()[Lorg/jetbrains/kotlin/ir/types/IrType;", "[Lorg/jetbrains/kotlin/ir/types/IrType;", "valueArguments", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getValueArguments", "()[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrPropertyReferenceImpl extends IrPropertyReference {
    private final int endOffset;
    private IrFieldSymbol field;
    private IrSimpleFunctionSymbol getter;
    private IrStatementOrigin origin;
    private IrSimpleFunctionSymbol setter;
    private final int startOffset;
    private IrPropertySymbol symbol;
    private IrType type;
    private final IrType[] typeArguments;
    private final IrExpression[] valueArguments;

    public /* synthetic */ IrPropertyReferenceImpl(int i, int i2, IrType irType, IrPropertySymbol irPropertySymbol, int i3, IrFieldSymbol irFieldSymbol, IrSimpleFunctionSymbol irSimpleFunctionSymbol, IrSimpleFunctionSymbol irSimpleFunctionSymbol2, IrStatementOrigin irStatementOrigin, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irType, irPropertySymbol, i3, irFieldSymbol, irSimpleFunctionSymbol, irSimpleFunctionSymbol2, (i4 & 256) != 0 ? null : irStatementOrigin);
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

    @Override // org.jetbrains.kotlin.ir.expressions.IrCallableReference, org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression, org.jetbrains.kotlin.ir.expressions.IrDeclarationReference
    public IrPropertySymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrCallableReference
    public void setSymbol(IrPropertySymbol irPropertySymbol) {
        Intrinsics.checkNotNullParameter(irPropertySymbol, "<set-?>");
        this.symbol = irPropertySymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrPropertyReference
    public IrFieldSymbol getField() {
        return this.field;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrPropertyReference
    public void setField(IrFieldSymbol irFieldSymbol) {
        this.field = irFieldSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrPropertyReference
    public IrSimpleFunctionSymbol getGetter() {
        return this.getter;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrPropertyReference
    public void setGetter(IrSimpleFunctionSymbol irSimpleFunctionSymbol) {
        this.getter = irSimpleFunctionSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrPropertyReference
    public IrSimpleFunctionSymbol getSetter() {
        return this.setter;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrPropertyReference
    public void setSetter(IrSimpleFunctionSymbol irSimpleFunctionSymbol) {
        this.setter = irSimpleFunctionSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    public IrStatementOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    public void setOrigin(IrStatementOrigin irStatementOrigin) {
        this.origin = irStatementOrigin;
    }

    public IrPropertyReferenceImpl(int i, int i2, IrType type, IrPropertySymbol symbol, int i3, IrFieldSymbol irFieldSymbol, IrSimpleFunctionSymbol irSimpleFunctionSymbol, IrSimpleFunctionSymbol irSimpleFunctionSymbol2, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.symbol = symbol;
        this.field = irFieldSymbol;
        this.getter = irSimpleFunctionSymbol;
        this.setter = irSimpleFunctionSymbol2;
        this.origin = irStatementOrigin;
        this.typeArguments = EmptyArraysForMemberAccessKt.initializeTypeArguments(i3);
        this.valueArguments = EmptyArraysForMemberAccessKt.initializeParameterArguments(0);
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    protected IrType[] getTypeArguments() {
        return this.typeArguments;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    protected IrExpression[] getValueArguments() {
        return this.valueArguments;
    }
}
