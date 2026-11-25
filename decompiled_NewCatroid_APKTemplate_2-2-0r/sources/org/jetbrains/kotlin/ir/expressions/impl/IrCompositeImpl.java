package org.jetbrains.kotlin.ir.expressions.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrCompositeImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B7\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\fB)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\rR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000fR\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrCompositeImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "statements", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;Ljava/util/List;)V", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getEndOffset", "()I", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)V", "getStartOffset", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrCompositeImpl extends IrComposite {
    private final int endOffset;
    private IrStatementOrigin origin;
    private final int startOffset;
    private IrType type;

    public /* synthetic */ IrCompositeImpl(int i, int i2, IrType irType, IrStatementOrigin irStatementOrigin, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irType, (i3 & 8) != 0 ? null : irStatementOrigin);
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

    @Override // org.jetbrains.kotlin.ir.expressions.IrContainerExpression
    public IrStatementOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrContainerExpression
    public void setOrigin(IrStatementOrigin irStatementOrigin) {
        this.origin = irStatementOrigin;
    }

    public IrCompositeImpl(int i, int i2, IrType type, IrStatementOrigin irStatementOrigin) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.origin = irStatementOrigin;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrCompositeImpl(int i, int i2, IrType type, IrStatementOrigin irStatementOrigin, List<? extends IrStatement> statements) {
        this(i, i2, type, irStatementOrigin);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(statements, "statements");
        getStatements().addAll(statements);
    }
}
