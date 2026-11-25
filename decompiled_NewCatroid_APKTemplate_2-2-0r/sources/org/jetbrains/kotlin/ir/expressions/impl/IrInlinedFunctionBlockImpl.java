package org.jetbrains.kotlin.ir.expressions.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrBlockImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0016\u0018\u00002\u00020\u0001BG\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e¢\u0006\u0002\u0010\u0010B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\u0011R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0013R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrInlinedFunctionBlockImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrInlinedFunctionBlock;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "inlineCall", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "inlinedElement", "Lorg/jetbrains/kotlin/ir/IrElement;", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "statements", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;Ljava/util/List;)V", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getEndOffset", "()I", "getInlineCall", "()Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "setInlineCall", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;)V", "getInlinedElement", "()Lorg/jetbrains/kotlin/ir/IrElement;", "setInlinedElement", "(Lorg/jetbrains/kotlin/ir/IrElement;)V", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getStartOffset", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrInlinedFunctionBlockImpl extends IrInlinedFunctionBlock {
    private final int endOffset;
    private IrFunctionAccessExpression inlineCall;
    private IrElement inlinedElement;
    private IrStatementOrigin origin;
    private final int startOffset;
    private IrType type;

    public /* synthetic */ IrInlinedFunctionBlockImpl(int i, int i2, IrType irType, IrFunctionAccessExpression irFunctionAccessExpression, IrElement irElement, IrStatementOrigin irStatementOrigin, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irType, irFunctionAccessExpression, irElement, (i3 & 32) != 0 ? null : irStatementOrigin);
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

    @Override // org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock
    public IrFunctionAccessExpression getInlineCall() {
        return this.inlineCall;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock
    public void setInlineCall(IrFunctionAccessExpression irFunctionAccessExpression) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<set-?>");
        this.inlineCall = irFunctionAccessExpression;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock
    public IrElement getInlinedElement() {
        return this.inlinedElement;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock
    public void setInlinedElement(IrElement irElement) {
        Intrinsics.checkNotNullParameter(irElement, "<set-?>");
        this.inlinedElement = irElement;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrContainerExpression
    public IrStatementOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrContainerExpression
    public void setOrigin(IrStatementOrigin irStatementOrigin) {
        this.origin = irStatementOrigin;
    }

    public IrInlinedFunctionBlockImpl(int i, int i2, IrType type, IrFunctionAccessExpression inlineCall, IrElement inlinedElement, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(inlineCall, "inlineCall");
        Intrinsics.checkNotNullParameter(inlinedElement, "inlinedElement");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.inlineCall = inlineCall;
        this.inlinedElement = inlinedElement;
        this.origin = irStatementOrigin;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrInlinedFunctionBlockImpl(int i, int i2, IrType type, IrFunctionAccessExpression inlineCall, IrElement inlinedElement, IrStatementOrigin irStatementOrigin, List<? extends IrStatement> statements) {
        this(i, i2, type, inlineCall, inlinedElement, irStatementOrigin);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(inlineCall, "inlineCall");
        Intrinsics.checkNotNullParameter(inlinedElement, "inlinedElement");
        Intrinsics.checkNotNullParameter(statements, "statements");
        getStatements().addAll(statements);
    }
}
