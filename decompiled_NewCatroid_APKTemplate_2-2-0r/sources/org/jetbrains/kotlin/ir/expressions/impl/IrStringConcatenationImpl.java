package org.jetbrains.kotlin.ir.expressions.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrStringConcatenationImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\n\u0018\u00002\u00020\u0001B-\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u000bR\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrStringConcatenationImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "arguments", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Ljava/util/Collection;)V", "(IILorg/jetbrains/kotlin/ir/types/IrType;)V", "", "getArguments", "()Ljava/util/List;", "getEndOffset", "()I", "getStartOffset", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrStringConcatenationImpl extends IrStringConcatenation {
    private final List<IrExpression> arguments;
    private final int endOffset;
    private final int startOffset;
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

    public IrStringConcatenationImpl(int i, int i2, IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.arguments = new ArrayList(2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrStringConcatenationImpl(int i, int i2, IrType type, Collection<? extends IrExpression> arguments) {
        this(i, i2, type);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        getArguments().addAll(arguments);
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrStringConcatenation
    public List<IrExpression> getArguments() {
        return this.arguments;
    }
}
