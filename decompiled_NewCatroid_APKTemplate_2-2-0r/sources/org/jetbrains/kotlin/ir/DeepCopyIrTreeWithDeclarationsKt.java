package org.jetbrains.kotlin.ir;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.NullDescriptorsRemapper;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: DeepCopyIrTreeWithDeclarations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0019\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u0001¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"deepCopyWithVariables", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/IrElement;", "(Lorg/jetbrains/kotlin/ir/IrElement;)Lorg/jetbrains/kotlin/ir/IrElement;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeepCopyIrTreeWithDeclarationsKt {
    public static final <T extends IrElement> T deepCopyWithVariables(T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(NullDescriptorsRemapper.INSTANCE);
        IrVisitorsKt.acceptVoid(t, deepCopySymbolRemapper);
        DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
        T t2 = (T) t.transform(new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), null);
        Intrinsics.checkNotNull(t2, "null cannot be cast to non-null type T of org.jetbrains.kotlin.ir.DeepCopyIrTreeWithDeclarationsKt.deepCopyWithVariables");
        return t2;
    }
}
