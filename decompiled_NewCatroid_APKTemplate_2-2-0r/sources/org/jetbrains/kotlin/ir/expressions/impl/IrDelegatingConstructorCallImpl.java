package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.EmptyArraysForMemberAccessKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrDelegatingConstructorCallImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 +2\u00020\u0001:\u0001+B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003¢\u0006\u0002\u0010\u000bR\u001a\u0010\f\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000eR\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001e\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\"X\u0094\u0004¢\u0006\n\n\u0002\u0010%\u001a\u0004\b#\u0010$R\u001e\u0010&\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010'0\"X\u0094\u0004¢\u0006\n\n\u0002\u0010*\u001a\u0004\b(\u0010)¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrDelegatingConstructorCallImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "typeArgumentsCount", "valueArgumentsCount", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;II)V", "contextReceiversCount", "getContextReceiversCount", "()I", "setContextReceiversCount", "(I)V", "getEndOffset", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;)V", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "typeArguments", "", "getTypeArguments", "()[Lorg/jetbrains/kotlin/ir/types/IrType;", "[Lorg/jetbrains/kotlin/ir/types/IrType;", "valueArguments", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getValueArguments", "()[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Companion", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrDelegatingConstructorCallImpl extends IrDelegatingConstructorCall {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private int contextReceiversCount;
    private final int endOffset;
    private IrStatementOrigin origin;
    private final int startOffset;
    private IrConstructorSymbol symbol;
    private IrType type;
    private final IrType[] typeArguments;
    private final IrExpression[] valueArguments;

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
    public IrConstructorSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall
    public void setSymbol(IrConstructorSymbol irConstructorSymbol) {
        Intrinsics.checkNotNullParameter(irConstructorSymbol, "<set-?>");
        this.symbol = irConstructorSymbol;
    }

    public IrDelegatingConstructorCallImpl(int i, int i2, IrType type, IrConstructorSymbol symbol, int i3, int i4) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.symbol = symbol;
        this.typeArguments = EmptyArraysForMemberAccessKt.initializeTypeArguments(i3);
        this.valueArguments = EmptyArraysForMemberAccessKt.initializeParameterArguments(i4);
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    public IrStatementOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    public void setOrigin(IrStatementOrigin irStatementOrigin) {
        this.origin = irStatementOrigin;
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

    /* JADX INFO: compiled from: IrDelegatingConstructorCallImpl.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J<\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00062\b\b\u0002\u0010\r\u001a\u00020\u0006H\u0007J:\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00062\b\b\u0002\u0010\r\u001a\u00020\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrDelegatingConstructorCallImpl$Companion;", "", "()V", "fromSymbolDescriptor", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrDelegatingConstructorCallImpl;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "typeArgumentsCount", "valueArgumentsCount", "fromSymbolOwner", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ IrDelegatingConstructorCallImpl fromSymbolDescriptor$default(Companion companion, int i, int i2, IrType irType, IrConstructorSymbol irConstructorSymbol, int i3, int i4, int i5, Object obj) {
            if ((i5 & 16) != 0) {
                i3 = IrExpressionsKt.getTypeParametersCount(irConstructorSymbol.getDescriptor());
            }
            int i6 = i3;
            if ((i5 & 32) != 0) {
                i4 = irConstructorSymbol.getDescriptor().getContextReceiverParameters().size() + irConstructorSymbol.getDescriptor().getValueParameters().size();
            }
            return companion.fromSymbolDescriptor(i, i2, irType, irConstructorSymbol, i6, i4);
        }

        @ObsoleteDescriptorBasedAPI
        public final IrDelegatingConstructorCallImpl fromSymbolDescriptor(int startOffset, int endOffset, IrType type, IrConstructorSymbol symbol, int typeArgumentsCount, int valueArgumentsCount) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return new IrDelegatingConstructorCallImpl(startOffset, endOffset, type, symbol, typeArgumentsCount, valueArgumentsCount);
        }

        public static /* synthetic */ IrDelegatingConstructorCallImpl fromSymbolOwner$default(Companion companion, int i, int i2, IrType irType, IrConstructorSymbol irConstructorSymbol, int i3, int i4, int i5, Object obj) {
            if ((i5 & 16) != 0) {
                i3 = IrUtilsKt.getAllTypeParameters(irConstructorSymbol.getOwner()).size();
            }
            int i6 = i3;
            if ((i5 & 32) != 0) {
                i4 = irConstructorSymbol.getOwner().getValueParameters().size();
            }
            return companion.fromSymbolOwner(i, i2, irType, irConstructorSymbol, i6, i4);
        }

        public final IrDelegatingConstructorCallImpl fromSymbolOwner(int startOffset, int endOffset, IrType type, IrConstructorSymbol symbol, int typeArgumentsCount, int valueArgumentsCount) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return new IrDelegatingConstructorCallImpl(startOffset, endOffset, type, symbol, typeArgumentsCount, valueArgumentsCount);
        }
    }
}
