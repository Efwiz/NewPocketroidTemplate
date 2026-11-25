package org.jetbrains.kotlin.ir.expressions.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: IrTryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0010\u0018\u00002\u00020\u0001B?\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\b\u0010\f\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\rB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u000eR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001c\u0010\f\u001a\u0004\u0018\u00010\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u001a\u0010\u0007\u001a\u00020\bX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0015\"\u0004\b\u001a\u0010\u0017R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrTryImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "tryResult", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "catches", "", "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "finallyExpression", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Ljava/util/List;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "(IILorg/jetbrains/kotlin/ir/types/IrType;)V", "", "getCatches", "()Ljava/util/List;", "getEndOffset", "()I", "getFinallyExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setFinallyExpression", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getStartOffset", "getTryResult", "setTryResult", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrTryImpl extends IrTry {
    private final List<IrCatch> catches;
    private final int endOffset;
    private IrExpression finallyExpression;
    private final int startOffset;
    public IrExpression tryResult;
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

    public IrTryImpl(int i, int i2, IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.catches = new SmartList();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrTryImpl(int i, int i2, IrType type, IrExpression tryResult, List<? extends IrCatch> catches, IrExpression irExpression) {
        this(i, i2, type);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(tryResult, "tryResult");
        Intrinsics.checkNotNullParameter(catches, "catches");
        setTryResult(tryResult);
        getCatches().addAll(catches);
        setFinallyExpression(irExpression);
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrTry
    public IrExpression getTryResult() {
        IrExpression irExpression = this.tryResult;
        if (irExpression != null) {
            return irExpression;
        }
        Intrinsics.throwUninitializedPropertyAccessException("tryResult");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrTry
    public void setTryResult(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<set-?>");
        this.tryResult = irExpression;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrTry
    public List<IrCatch> getCatches() {
        return this.catches;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrTry
    public IrExpression getFinallyExpression() {
        return this.finallyExpression;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrTry
    public void setFinallyExpression(IrExpression irExpression) {
        this.finallyExpression = irExpression;
    }
}
