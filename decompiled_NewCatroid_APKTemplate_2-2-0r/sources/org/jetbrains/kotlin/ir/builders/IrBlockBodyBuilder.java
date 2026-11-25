package org.jetbrains.kotlin.ir.builders;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;

/* JADX INFO: compiled from: IrBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\nJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014J%\u0010\u0010\u001a\u00020\u00022\u0017\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\r0\u0012¢\u0006\u0002\b\u0013H\u0086\bø\u0001\u0000J\b\u0010\u0014\u001a\u00020\u0002H\u0016R\u000e\u0010\u000b\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/builders/IrBlockBodyBuilder;", "Lorg/jetbrains/kotlin/ir/builders/IrStatementsBuilder;", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "context", "Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;", "scope", "Lorg/jetbrains/kotlin/ir/builders/Scope;", "startOffset", "", "endOffset", "(Lorg/jetbrains/kotlin/ir/builders/IrGeneratorContext;Lorg/jetbrains/kotlin/ir/builders/Scope;II)V", "irBlockBody", "addStatement", "", "irStatement", "Lorg/jetbrains/kotlin/ir/IrStatement;", "blockBody", "body", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "doBuild", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrBlockBodyBuilder extends IrStatementsBuilder<IrBlockBody> {
    private final IrBlockBody irBlockBody;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrBlockBodyBuilder(IrGeneratorContext context, Scope scope, int i, int i2) {
        super(context, scope, i, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.irBlockBody = context.getIrFactory().createBlockBody(i, i2);
    }

    public final IrBlockBody blockBody(Function1<? super IrBlockBodyBuilder, Unit> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        body.invoke(this);
        return getIrBlockBody();
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrStatementsBuilder
    protected void addStatement(IrStatement irStatement) {
        Intrinsics.checkNotNullParameter(irStatement, "irStatement");
        this.irBlockBody.getStatements().add(irStatement);
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrStatementsBuilder
    /* JADX INFO: renamed from: doBuild, reason: from getter */
    public IrBlockBody getIrBlockBody() {
        return this.irBlockBody;
    }
}
