package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.EmptyArraysForMemberAccessKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrCallImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 12\u00020\u0001:\u00011BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012R\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001e\u0010'\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060(X\u0094\u0004¢\u0006\n\n\u0002\u0010+\u001a\u0004\b)\u0010*R\u001e\u0010,\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010-0(X\u0094\u0004¢\u0006\n\n\u0002\u00100\u001a\u0004\b.\u0010/¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrCallImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "typeArgumentsCount", "valueArgumentsCount", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "superQualifierSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;IILorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "contextReceiversCount", "getContextReceiversCount", "()I", "setContextReceiversCount", "(I)V", "getEndOffset", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getStartOffset", "getSuperQualifierSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "setSuperQualifierSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;)V", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "typeArguments", "", "getTypeArguments", "()[Lorg/jetbrains/kotlin/ir/types/IrType;", "[Lorg/jetbrains/kotlin/ir/types/IrType;", "valueArguments", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getValueArguments", "()[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Companion", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrCallImpl extends IrCall {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private int contextReceiversCount;
    private final int endOffset;
    private IrStatementOrigin origin;
    private final int startOffset;
    private IrClassSymbol superQualifierSymbol;
    private IrSimpleFunctionSymbol symbol;
    private IrType type;
    private final IrType[] typeArguments;
    private final IrExpression[] valueArguments;

    public /* synthetic */ IrCallImpl(int i, int i2, IrType irType, IrSimpleFunctionSymbol irSimpleFunctionSymbol, int i3, int i4, IrStatementOrigin irStatementOrigin, IrClassSymbol irClassSymbol, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irType, irSimpleFunctionSymbol, i3, i4, (i5 & 64) != 0 ? null : irStatementOrigin, (i5 & 128) != 0 ? null : irClassSymbol);
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

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression, org.jetbrains.kotlin.ir.expressions.IrDeclarationReference
    public IrSimpleFunctionSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrCall
    public void setSymbol(IrSimpleFunctionSymbol irSimpleFunctionSymbol) {
        Intrinsics.checkNotNullParameter(irSimpleFunctionSymbol, "<set-?>");
        this.symbol = irSimpleFunctionSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    public IrStatementOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    public void setOrigin(IrStatementOrigin irStatementOrigin) {
        this.origin = irStatementOrigin;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrCall
    public IrClassSymbol getSuperQualifierSymbol() {
        return this.superQualifierSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrCall
    public void setSuperQualifierSymbol(IrClassSymbol irClassSymbol) {
        this.superQualifierSymbol = irClassSymbol;
    }

    public IrCallImpl(int i, int i2, IrType type, IrSimpleFunctionSymbol symbol, int i3, int i4, IrStatementOrigin irStatementOrigin, IrClassSymbol irClassSymbol) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.symbol = symbol;
        this.origin = irStatementOrigin;
        this.superQualifierSymbol = irClassSymbol;
        this.typeArguments = EmptyArraysForMemberAccessKt.initializeTypeArguments(i3);
        this.valueArguments = EmptyArraysForMemberAccessKt.initializeParameterArguments(i4);
        if (getSymbol() instanceof IrConstructorSymbol) {
            throw new AssertionError("Should be IrConstructorCall: " + RenderIrElementKt.render$default(this, (DumpIrTreeOptions) null, 1, (Object) null));
        }
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    protected IrType[] getTypeArguments() {
        return this.typeArguments;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    protected IrExpression[] getValueArguments() {
        return this.valueArguments;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression
    public int getContextReceiversCount() {
        return this.contextReceiversCount;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression
    public void setContextReceiversCount(int i) {
        this.contextReceiversCount = i;
    }

    /* JADX INFO: compiled from: IrCallImpl.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JT\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00062\b\b\u0002\u0010\r\u001a\u00020\u00062\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0007J\u001e\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bJR\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00062\b\b\u0002\u0010\r\u001a\u00020\u00062\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrCallImpl$Companion;", "", "()V", "fromSymbolDescriptor", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrCallImpl;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "typeArgumentsCount", "valueArgumentsCount", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "superQualifierSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "fromSymbolOwner", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @ObsoleteDescriptorBasedAPI
        public final IrCallImpl fromSymbolDescriptor(int startOffset, int endOffset, IrType type, IrSimpleFunctionSymbol symbol, int typeArgumentsCount, int valueArgumentsCount, IrStatementOrigin origin, IrClassSymbol superQualifierSymbol) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return new IrCallImpl(startOffset, endOffset, type, symbol, typeArgumentsCount, valueArgumentsCount, origin, superQualifierSymbol);
        }

        public final IrCallImpl fromSymbolOwner(int startOffset, int endOffset, IrType type, IrSimpleFunctionSymbol symbol, int typeArgumentsCount, int valueArgumentsCount, IrStatementOrigin origin, IrClassSymbol superQualifierSymbol) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return new IrCallImpl(startOffset, endOffset, type, symbol, typeArgumentsCount, valueArgumentsCount, origin, superQualifierSymbol);
        }

        public final IrCallImpl fromSymbolOwner(int startOffset, int endOffset, IrSimpleFunctionSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return new IrCallImpl(startOffset, endOffset, symbol.getOwner().getReturnType(), symbol, symbol.getOwner().getTypeParameters().size(), symbol.getOwner().getValueParameters().size(), null, null);
        }
    }
}
