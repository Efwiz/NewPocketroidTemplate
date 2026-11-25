package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrClassReferenceImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0006¢\u0006\u0002\u0010\nR\u001a\u0010\t\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000e¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrClassReferenceImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "classType", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getClassType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setClassType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getEndOffset", "()I", "getStartOffset", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;)V", "getType", "setType", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrClassReferenceImpl extends IrClassReference {
    private IrType classType;
    private final int endOffset;
    private final int startOffset;
    private IrClassifierSymbol symbol;
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
    public IrClassifierSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrClassReference
    public void setSymbol(IrClassifierSymbol irClassifierSymbol) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<set-?>");
        this.symbol = irClassifierSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrClassReference
    public IrType getClassType() {
        return this.classType;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrClassReference
    public void setClassType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.classType = irType;
    }

    public IrClassReferenceImpl(int i, int i2, IrType type, IrClassifierSymbol symbol, IrType classType) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(classType, "classType");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.symbol = symbol;
        this.classType = classType;
    }
}
