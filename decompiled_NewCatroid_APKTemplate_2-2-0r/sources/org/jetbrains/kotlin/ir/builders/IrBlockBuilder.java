package org.jetbrains.kotlin.ir.builders;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BG\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001aH\u0014J%\u0010\u001f\u001a\u00020\u00022\u0017\u0010 \u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u001d0!¢\u0006\u0002\b\"H\u0086\bø\u0001\u0000J\b\u0010#\u001a\u00020\u0002H\u0016R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0011R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001e\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\u001a0\u0019j\b\u0012\u0004\u0012\u00020\u001a`\u001bX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "Lorg/jetbrains/kotlin/ir/builders/IrStatementsBuilder;", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "context", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "scope", "Lorg/jetbrains/kotlin/ir/builders/Scope;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "resultType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "isTransparent", "", "(Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;Lorg/jetbrains/kotlin/ir/builders/Scope;IILorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;Lorg/jetbrains/kotlin/ir/types/IrType;Z)V", "()Z", "getOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "getResultType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setResultType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "statements", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/ir/IrStatement;", "Lkotlin/collections/ArrayList;", "addStatement", "", "irStatement", "block", "body", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "doBuild", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrBlockBuilder extends IrStatementsBuilder<IrContainerExpression> {
    private final boolean isTransparent;
    private final IrStatementOrigin origin;
    private IrType resultType;
    private final ArrayList<IrStatement> statements;

    public /* synthetic */ IrBlockBuilder(IrGeneratorContext irGeneratorContext, Scope scope, int i, int i2, IrStatementOrigin irStatementOrigin, IrType irType, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(irGeneratorContext, scope, i, i2, (i3 & 16) != 0 ? null : irStatementOrigin, (i3 & 32) != 0 ? null : irType, (i3 & 64) != 0 ? false : z);
    }

    public final IrStatementOrigin getOrigin() {
        return this.origin;
    }

    public final IrType getResultType() {
        return this.resultType;
    }

    public final void setResultType(IrType irType) {
        this.resultType = irType;
    }

    /* JADX INFO: renamed from: isTransparent, reason: from getter */
    public final boolean getIsTransparent() {
        return this.isTransparent;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBlockBuilder(IrGeneratorContext context, Scope scope, int i, int i2, IrStatementOrigin irStatementOrigin, IrType irType, boolean z) {
        super(context, scope, i, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.origin = irStatementOrigin;
        this.resultType = irType;
        this.isTransparent = z;
        this.statements = new ArrayList<>();
    }

    public final IrContainerExpression block(Function1<? super IrBlockBuilder, Unit> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        body.invoke(this);
        return doBuild();
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrStatementsBuilder
    protected void addStatement(IrStatement irStatement) {
        Intrinsics.checkNotNullParameter(irStatement, "irStatement");
        this.statements.add(irStatement);
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrStatementsBuilder
    public IrContainerExpression doBuild() {
        IrType type = this.resultType;
        if (type == null) {
            Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) this.statements);
            IrExpression irExpression = objLastOrNull instanceof IrExpression ? (IrExpression) objLastOrNull : null;
            type = irExpression != null ? irExpression.getType() : null;
            if (type == null) {
                type = getContext().getIrBuiltIns().getUnitType();
            }
        }
        IrBlockImpl irCompositeImpl = this.isTransparent ? new IrCompositeImpl(getStartOffset(), getEndOffset(), type, this.origin) : new IrBlockImpl(getStartOffset(), getEndOffset(), type, this.origin);
        irCompositeImpl.getStatements().addAll(this.statements);
        return irCompositeImpl;
    }
}
