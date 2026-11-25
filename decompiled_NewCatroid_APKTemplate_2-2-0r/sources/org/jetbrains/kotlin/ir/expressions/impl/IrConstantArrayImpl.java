package org.jetbrains.kotlin.ir.expressions.impl;

import com.reandroid.archive.PathTree;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: IrConstantObjectImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\tH\u0016J\b\u0010\u001a\u001a\u00020\u0003H\u0016R\"\u0010\u000b\u001a\u0010\u0012\f\u0012\n \r*\u0004\u0018\u00010\t0\t0\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstantArrayImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantArray;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "initElements", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "(IILorg/jetbrains/kotlin/ir/types/IrType;Ljava/util/List;)V", PathTree.NAME_elements, "Lorg/jetbrains/kotlin/utils/SmartList;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getElements", "()Lorg/jetbrains/kotlin/utils/SmartList;", "getEndOffset", "()I", "getStartOffset", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "contentEquals", "", "other", "contentHashCode", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrConstantArrayImpl extends IrConstantArray {
    private final SmartList<IrConstantValue> elements;
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

    public IrConstantArrayImpl(int i, int i2, IrType type, List<? extends IrConstantValue> initElements) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(initElements, "initElements");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.elements = new SmartList<>((Collection) initElements);
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstantArray
    public SmartList<IrConstantValue> getElements() {
        return this.elements;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstantValue
    public boolean contentEquals(IrConstantValue other) {
        Intrinsics.checkNotNullParameter(other, "other");
        if ((other instanceof IrConstantArray) && Intrinsics.areEqual(other.getType(), getType())) {
            IrConstantArray irConstantArray = (IrConstantArray) other;
            if (getElements().size() == irConstantArray.getElements().size()) {
                Iterable indices = CollectionsKt.getIndices(getElements());
                if (!(indices instanceof Collection) || !((Collection) indices).isEmpty()) {
                    Iterator it = indices.iterator();
                    while (it.hasNext()) {
                        int iNextInt = ((IntIterator) it).nextInt();
                        if (!getElements().get(iNextInt).contentEquals(irConstantArray.getElements().get(iNextInt))) {
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstantValue
    public int contentHashCode() {
        int iHashCode = getType().hashCode();
        Iterator<IrConstantValue> it = getElements().iterator();
        while (it.hasNext()) {
            iHashCode = (iHashCode * 31) + it.next().contentHashCode();
        }
        return iHashCode;
    }
}
