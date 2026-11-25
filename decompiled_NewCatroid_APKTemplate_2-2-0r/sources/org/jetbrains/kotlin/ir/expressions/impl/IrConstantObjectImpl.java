package org.jetbrains.kotlin.ir.expressions.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: IrConstantObjectImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b¢\u0006\u0002\u0010\rJ\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\tH\u0016J\b\u0010#\u001a\u00020\u0003H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u001a\u0010\f\u001a\u00020\u000bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\"\u0010\u0019\u001a\u0010\u0012\f\u0012\n \u001b*\u0004\u0018\u00010\u000b0\u000b0\u001aX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\"\u0010\u001e\u001a\u0010\u0012\f\u0012\n \u001b*\u0004\u0018\u00010\t0\t0\u001aX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001d¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstantObjectImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantObject;", "startOffset", "", "endOffset", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "initValueArguments", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstantValue;", "initTypeArguments", "Lorg/jetbrains/kotlin/ir/types/IrType;", ModuleXmlParser.TYPE, "(IILorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getConstructor", "()Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "setConstructor", "(Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;)V", "getEndOffset", "()I", "getStartOffset", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "typeArguments", "Lorg/jetbrains/kotlin/utils/SmartList;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getTypeArguments", "()Lorg/jetbrains/kotlin/utils/SmartList;", "valueArguments", "getValueArguments", "contentEquals", "", "other", "contentHashCode", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrConstantObjectImpl extends IrConstantObject {
    private IrConstructorSymbol constructor;
    private final int endOffset;
    private final int startOffset;
    private IrType type;
    private final SmartList<IrType> typeArguments;
    private final SmartList<IrConstantValue> valueArguments;

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstantObject
    public IrConstructorSymbol getConstructor() {
        return this.constructor;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstantObject
    public void setConstructor(IrConstructorSymbol irConstructorSymbol) {
        Intrinsics.checkNotNullParameter(irConstructorSymbol, "<set-?>");
        this.constructor = irConstructorSymbol;
    }

    public /* synthetic */ IrConstantObjectImpl(int i, int i2, IrConstructorSymbol irConstructorSymbol, List list, List list2, IrType irType, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, irConstructorSymbol, list, list2, (i3 & 32) != 0 ? IrUtilsKt.getConstructedClassType(irConstructorSymbol.getOwner()) : irType);
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

    public IrConstantObjectImpl(int i, int i2, IrConstructorSymbol constructor, List<? extends IrConstantValue> initValueArguments, List<? extends IrType> initTypeArguments, IrType type) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(initValueArguments, "initValueArguments");
        Intrinsics.checkNotNullParameter(initTypeArguments, "initTypeArguments");
        Intrinsics.checkNotNullParameter(type, "type");
        this.startOffset = i;
        this.endOffset = i2;
        this.constructor = constructor;
        this.type = type;
        this.valueArguments = new SmartList<>((Collection) initValueArguments);
        this.typeArguments = new SmartList<>((Collection) initTypeArguments);
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstantObject
    public SmartList<IrConstantValue> getValueArguments() {
        return this.valueArguments;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConstantObject
    public SmartList<IrType> getTypeArguments() {
        return this.typeArguments;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x00ab  */
    /* JADX WARN: Code duplicated, block: B:31:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:37:0x00d5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:39:? A[LOOP:0: B:29:0x00af->B:39:?, LOOP_END, SYNTHETIC] */
    @Override // org.jetbrains.kotlin.ir.expressions.IrConstantValue
    public boolean contentEquals(IrConstantValue other) {
        Iterable indices;
        Iterator it;
        int iNextInt;
        Intrinsics.checkNotNullParameter(other, "other");
        if ((other instanceof IrConstantObject) && Intrinsics.areEqual(other.getType(), getType())) {
            IrConstantObject irConstantObject = (IrConstantObject) other;
            if (Intrinsics.areEqual(irConstantObject.getConstructor(), getConstructor()) && getValueArguments().size() == irConstantObject.getValueArguments().size() && getTypeArguments().size() == irConstantObject.getTypeArguments().size()) {
                Iterable indices2 = CollectionsKt.getIndices(getValueArguments());
                if ((indices2 instanceof Collection) && ((Collection) indices2).isEmpty()) {
                    indices = CollectionsKt.getIndices(getTypeArguments());
                    if (indices instanceof Collection) {
                        it = indices.iterator();
                        while (it.hasNext()) {
                            iNextInt = ((IntIterator) it).nextInt();
                            if (!Intrinsics.areEqual(getTypeArguments().get(iNextInt), irConstantObject.getTypeArguments().get(iNextInt))) {
                            }
                        }
                    } else {
                        it = indices.iterator();
                        while (it.hasNext()) {
                            iNextInt = ((IntIterator) it).nextInt();
                            if (!Intrinsics.areEqual(getTypeArguments().get(iNextInt), irConstantObject.getTypeArguments().get(iNextInt))) {
                            }
                        }
                    }
                    return true;
                }
                Iterator it2 = indices2.iterator();
                while (it2.hasNext()) {
                    int iNextInt2 = ((IntIterator) it2).nextInt();
                    if (!getValueArguments().get(iNextInt2).contentEquals(irConstantObject.getValueArguments().get(iNextInt2))) {
                    }
                }
                indices = CollectionsKt.getIndices(getTypeArguments());
                if ((indices instanceof Collection) || !((Collection) indices).isEmpty()) {
                    it = indices.iterator();
                    while (it.hasNext()) {
                        iNextInt = ((IntIterator) it).nextInt();
                        if (!Intrinsics.areEqual(getTypeArguments().get(iNextInt), irConstantObject.getTypeArguments().get(iNextInt))) {
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
        int iHashCode = (getType().hashCode() * 31) + getConstructor().hashCode();
        Iterator<IrConstantValue> it = getValueArguments().iterator();
        while (it.hasNext()) {
            iHashCode = (iHashCode * 31) + it.next().contentHashCode();
        }
        Iterator<IrType> it2 = getTypeArguments().iterator();
        while (it2.hasNext()) {
            iHashCode = (iHashCode * 31) + it2.next().hashCode();
        }
        return iHashCode;
    }
}
