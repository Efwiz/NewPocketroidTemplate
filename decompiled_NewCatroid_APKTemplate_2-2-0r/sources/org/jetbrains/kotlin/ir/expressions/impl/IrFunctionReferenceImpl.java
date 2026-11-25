package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.EmptyArraysForMemberAccessKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrFunctionReferenceImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 *2\u00020\u0001:\u0001*BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0010R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0016\"\u0004\b\u001b\u0010\u0018R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001e\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060!X\u0094\u0004¢\u0006\n\n\u0002\u0010$\u001a\u0004\b\"\u0010#R\u001e\u0010%\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010&0!X\u0094\u0004¢\u0006\n\n\u0002\u0010)\u001a\u0004\b'\u0010(¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrFunctionReferenceImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "typeArgumentsCount", "valueArgumentsCount", "reflectionTarget", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;IILorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getEndOffset", "()I", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getReflectionTarget", "()Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "setReflectionTarget", "(Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;)V", "getStartOffset", "getSymbol", "setSymbol", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "typeArguments", "", "getTypeArguments", "()[Lorg/jetbrains/kotlin/ir/types/IrType;", "[Lorg/jetbrains/kotlin/ir/types/IrType;", "valueArguments", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getValueArguments", "()[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Companion", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrFunctionReferenceImpl extends IrFunctionReference {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int endOffset;
    private IrStatementOrigin origin;
    private IrFunctionSymbol reflectionTarget;
    private final int startOffset;
    private IrFunctionSymbol symbol;
    private IrType type;
    private final IrType[] typeArguments;
    private final IrExpression[] valueArguments;

    public /* synthetic */ IrFunctionReferenceImpl(int i, int i2, IrType irType, IrFunctionSymbol irFunctionSymbol, int i3, int i4, IrFunctionSymbol irFunctionSymbol2, IrStatementOrigin irStatementOrigin, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irType, irFunctionSymbol, i3, i4, (i5 & 64) != 0 ? irFunctionSymbol : irFunctionSymbol2, (i5 & 128) != 0 ? null : irStatementOrigin);
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
    public IrFunctionSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrCallableReference
    public void setSymbol(IrFunctionSymbol irFunctionSymbol) {
        Intrinsics.checkNotNullParameter(irFunctionSymbol, "<set-?>");
        this.symbol = irFunctionSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrFunctionReference
    public IrFunctionSymbol getReflectionTarget() {
        return this.reflectionTarget;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrFunctionReference
    public void setReflectionTarget(IrFunctionSymbol irFunctionSymbol) {
        this.reflectionTarget = irFunctionSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    public IrStatementOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    public void setOrigin(IrStatementOrigin irStatementOrigin) {
        this.origin = irStatementOrigin;
    }

    public IrFunctionReferenceImpl(int i, int i2, IrType type, IrFunctionSymbol symbol, int i3, int i4, IrFunctionSymbol irFunctionSymbol, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.symbol = symbol;
        this.reflectionTarget = irFunctionSymbol;
        this.origin = irStatementOrigin;
        this.typeArguments = EmptyArraysForMemberAccessKt.initializeTypeArguments(i3);
        this.valueArguments = EmptyArraysForMemberAccessKt.initializeParameterArguments(i4);
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    protected IrType[] getTypeArguments() {
        return this.typeArguments;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression
    protected IrExpression[] getValueArguments() {
        return this.valueArguments;
    }

    /* JADX INFO: compiled from: IrFunctionReferenceImpl.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JF\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00062\b\u0010\r\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0007JD\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00062\b\u0010\r\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrFunctionReferenceImpl$Companion;", "", "()V", "fromSymbolDescriptor", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrFunctionReferenceImpl;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "typeArgumentsCount", "reflectionTarget", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "fromSymbolOwner", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @ObsoleteDescriptorBasedAPI
        public final IrFunctionReferenceImpl fromSymbolDescriptor(int startOffset, int endOffset, IrType type, IrFunctionSymbol symbol, int typeArgumentsCount, IrFunctionSymbol reflectionTarget, IrStatementOrigin origin) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return new IrFunctionReferenceImpl(startOffset, endOffset, type, symbol, typeArgumentsCount, symbol.getDescriptor().getValueParameters().size() + symbol.getDescriptor().getContextReceiverParameters().size(), reflectionTarget, origin);
        }

        public final IrFunctionReferenceImpl fromSymbolOwner(int startOffset, int endOffset, IrType type, IrFunctionSymbol symbol, int typeArgumentsCount, IrFunctionSymbol reflectionTarget, IrStatementOrigin origin) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            return new IrFunctionReferenceImpl(startOffset, endOffset, type, symbol, typeArgumentsCount, symbol.getOwner().getValueParameters().size(), reflectionTarget, origin);
        }
    }
}
