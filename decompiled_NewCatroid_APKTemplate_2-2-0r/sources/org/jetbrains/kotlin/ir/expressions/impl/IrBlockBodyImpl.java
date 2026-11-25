package org.jetbrains.kotlin.ir.expressions.impl;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.impl.IrFactoryImpl;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;

/* JADX INFO: compiled from: IrBlockBodyImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0003\u0018\u00002\u00020\u0001B%\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\tR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000bR\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrBlockBodyImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "startOffset", "", "endOffset", "statements", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "(IILjava/util/List;)V", "(II)V", "getEndOffset", "()I", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getStartOffset", "", "getStatements", "()Ljava/util/List;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrBlockBodyImpl extends IrBlockBody {
    private final int endOffset;
    private final int startOffset;
    private final List<IrStatement> statements;

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    public IrBlockBodyImpl(int i, int i2) {
        this.startOffset = i;
        this.endOffset = i2;
        this.statements = new ArrayList(2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrBlockBodyImpl(int i, int i2, List<? extends IrStatement> statements) {
        this(i, i2);
        Intrinsics.checkNotNullParameter(statements, "statements");
        getStatements().addAll(statements);
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrStatementContainer
    public List<IrStatement> getStatements() {
        return this.statements;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrBlockBody
    public IrFactory getFactory() {
        return IrFactoryImpl.INSTANCE;
    }
}
