package org.jetbrains.kotlin.backend.jvm.ir;

import com.reandroid.archive.PathTree;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrArrayBuilder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\u001a1\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\u0002\b\tH\u0086\bø\u0001\u0000\u001a\"\u0010\n\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\f\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\r"}, d2 = {"irArray", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;", "arrayType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "block", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/backend/jvm/ir/IrArrayBuilder;", "", "Lkotlin/ExtensionFunctionType;", "irArrayOf", PathTree.NAME_elements, "", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrArrayBuilderKt {
    public static final IrExpression irArray(JvmIrBuilder jvmIrBuilder, IrType arrayType, Function1<? super IrArrayBuilder, Unit> block) {
        Intrinsics.checkNotNullParameter(jvmIrBuilder, "<this>");
        Intrinsics.checkNotNullParameter(arrayType, "arrayType");
        Intrinsics.checkNotNullParameter(block, "block");
        IrArrayBuilder irArrayBuilder = new IrArrayBuilder(jvmIrBuilder, arrayType);
        block.invoke(irArrayBuilder);
        return irArrayBuilder.build();
    }

    public static /* synthetic */ IrExpression irArrayOf$default(JvmIrBuilder jvmIrBuilder, IrType irType, List list, int i, Object obj) {
        if ((i & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        return irArrayOf(jvmIrBuilder, irType, list);
    }

    public static final IrExpression irArrayOf(JvmIrBuilder jvmIrBuilder, IrType arrayType, List<? extends IrExpression> elements) {
        Intrinsics.checkNotNullParameter(jvmIrBuilder, "<this>");
        Intrinsics.checkNotNullParameter(arrayType, "arrayType");
        Intrinsics.checkNotNullParameter(elements, "elements");
        IrArrayBuilder irArrayBuilder = new IrArrayBuilder(jvmIrBuilder, arrayType);
        Iterator<T> it = elements.iterator();
        while (it.hasNext()) {
            irArrayBuilder.unaryPlus((IrExpression) it.next());
        }
        return irArrayBuilder.build();
    }
}
