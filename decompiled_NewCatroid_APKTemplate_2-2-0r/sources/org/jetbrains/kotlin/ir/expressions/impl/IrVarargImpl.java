package org.jetbrains.kotlin.ir.expressions.impl;

import com.reandroid.archive.PathTree;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: IrVarargImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\f\u0018\u00002\u00020\u0001B5\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bB%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\fR\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0007\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0014\"\u0004\b\u0018\u0010\u0016¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrVarargImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "varargElementType", PathTree.NAME_elements, "", "Lorg/jetbrains/kotlin/ir/expressions/IrVarargElement;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/types/IrType;Ljava/util/List;)V", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "", "getElements", "()Ljava/util/List;", "getEndOffset", "()I", "getStartOffset", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getVarargElementType", "setVarargElementType", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrVarargImpl extends IrVararg {
    private final List<IrVarargElement> elements;
    private final int endOffset;
    private final int startOffset;
    private IrType type;
    private IrType varargElementType;

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

    @Override // org.jetbrains.kotlin.ir.expressions.IrVararg
    public IrType getVarargElementType() {
        return this.varargElementType;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrVararg
    public void setVarargElementType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.varargElementType = irType;
    }

    public IrVarargImpl(int i, int i2, IrType type, IrType varargElementType) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(varargElementType, "varargElementType");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.varargElementType = varargElementType;
        this.elements = new SmartList();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IrVarargImpl(int i, int i2, IrType type, IrType varargElementType, List<? extends IrVarargElement> elements) {
        this(i, i2, type, varargElementType);
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(varargElementType, "varargElementType");
        Intrinsics.checkNotNullParameter(elements, "elements");
        getElements().addAll(elements);
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrVararg
    public List<IrVarargElement> getElements() {
        return this.elements;
    }
}
