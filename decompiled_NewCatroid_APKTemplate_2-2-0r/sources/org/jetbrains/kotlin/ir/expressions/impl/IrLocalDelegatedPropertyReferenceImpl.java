package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.EmptyArraysForMemberAccessKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrLocalDelegatedPropertyReferenceImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u0010R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u000b\u001a\u00020\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001c\u0010\r\u001a\u0004\u0018\u00010\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0018\"\u0004\b \u0010\u001aR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001e\u0010*\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060+X\u0094\u0004¢\u0006\n\n\u0002\u0010.\u001a\u0004\b,\u0010-R\u001e\u0010/\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001000+X\u0094\u0004¢\u0006\n\n\u0002\u00103\u001a\u0004\b1\u00102¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrLocalDelegatedPropertyReferenceImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "getter", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "setter", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getDelegate", "()Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "setDelegate", "(Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;)V", "getEndOffset", "()I", "getGetter", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "setGetter", "(Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getSetter", "setSetter", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;)V", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "typeArguments", "", "getTypeArguments", "()[Lorg/jetbrains/kotlin/ir/types/IrType;", "[Lorg/jetbrains/kotlin/ir/types/IrType;", "valueArguments", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getValueArguments", "()[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrLocalDelegatedPropertyReferenceImpl extends IrLocalDelegatedPropertyReference {
    private IrVariableSymbol delegate;
    private final int endOffset;
    private IrSimpleFunctionSymbol getter;
    private IrStatementOrigin origin;
    private IrSimpleFunctionSymbol setter;
    private final int startOffset;
    private IrLocalDelegatedPropertySymbol symbol;
    private IrType type;
    private final IrType[] typeArguments;
    private final IrExpression[] valueArguments;

    public /* synthetic */ IrLocalDelegatedPropertyReferenceImpl(int i, int i2, IrType irType, IrLocalDelegatedPropertySymbol irLocalDelegatedPropertySymbol, IrVariableSymbol irVariableSymbol, IrSimpleFunctionSymbol irSimpleFunctionSymbol, IrSimpleFunctionSymbol irSimpleFunctionSymbol2, IrStatementOrigin irStatementOrigin, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irType, irLocalDelegatedPropertySymbol, irVariableSymbol, irSimpleFunctionSymbol, irSimpleFunctionSymbol2, (i3 & 128) != 0 ? null : irStatementOrigin);
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
    public IrLocalDelegatedPropertySymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrCallableReference
    public void setSymbol(IrLocalDelegatedPropertySymbol irLocalDelegatedPropertySymbol) {
        Intrinsics.checkNotNullParameter(irLocalDelegatedPropertySymbol, "<set-?>");
        this.symbol = irLocalDelegatedPropertySymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference
    public IrVariableSymbol getDelegate() {
        return this.delegate;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference
    public void setDelegate(IrVariableSymbol irVariableSymbol) {
        Intrinsics.checkNotNullParameter(irVariableSymbol, "<set-?>");
        this.delegate = irVariableSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference
    public IrSimpleFunctionSymbol getGetter() {
        return this.getter;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference
    public void setGetter(IrSimpleFunctionSymbol irSimpleFunctionSymbol) {
        Intrinsics.checkNotNullParameter(irSimpleFunctionSymbol, "<set-?>");
        this.getter = irSimpleFunctionSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference
    public IrSimpleFunctionSymbol getSetter() {
        return this.setter;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference
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

    public IrLocalDelegatedPropertyReferenceImpl(int i, int i2, IrType type, IrLocalDelegatedPropertySymbol symbol, IrVariableSymbol delegate, IrSimpleFunctionSymbol getter, IrSimpleFunctionSymbol irSimpleFunctionSymbol, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(getter, "getter");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.symbol = symbol;
        this.delegate = delegate;
        this.getter = getter;
        this.setter = irSimpleFunctionSymbol;
        this.origin = irStatementOrigin;
        this.typeArguments = EmptyArraysForMemberAccessKt.initializeTypeArguments(0);
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
