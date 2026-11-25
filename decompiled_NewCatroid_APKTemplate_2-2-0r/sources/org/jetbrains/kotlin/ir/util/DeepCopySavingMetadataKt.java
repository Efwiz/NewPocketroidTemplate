package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: DeepCopySavingMetadata.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a/\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u00012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"deepCopySavingMetadata", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/IrElement;", "initialParent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "symbolRemapper", "Lorg/jetbrains/kotlin/ir/util/DeepCopySymbolRemapper;", "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;Lorg/jetbrains/kotlin/ir/util/DeepCopySymbolRemapper;)Lorg/jetbrains/kotlin/ir/IrElement;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeepCopySavingMetadataKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrElement deepCopySavingMetadata$default(IrElement irElement, IrDeclarationParent irDeclarationParent, DeepCopySymbolRemapper deepCopySymbolRemapper, int i, Object obj) {
        DescriptorsRemapper descriptorsRemapper = null;
        Object[] objArr = 0;
        if ((i & 1) != 0) {
            irDeclarationParent = null;
        }
        if ((i & 2) != 0) {
            deepCopySymbolRemapper = new DeepCopySymbolRemapper(descriptorsRemapper, 1, objArr == true ? 1 : 0);
        }
        return deepCopySavingMetadata(irElement, irDeclarationParent, deepCopySymbolRemapper);
    }

    public static final <T extends IrElement> T deepCopySavingMetadata(T t, IrDeclarationParent irDeclarationParent, DeepCopySymbolRemapper symbolRemapper) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(symbolRemapper, "symbolRemapper");
        IrVisitorsKt.acceptVoid(t, symbolRemapper);
        DeepCopySymbolRemapper deepCopySymbolRemapper = symbolRemapper;
        T t2 = (T) PatchDeclarationParentsKt.patchDeclarationParents(t.transform(new DeepCopySavingMetadata(deepCopySymbolRemapper, new DeepCopyTypeRemapper(deepCopySymbolRemapper), SymbolRenamer.DEFAULT.INSTANCE), null), irDeclarationParent);
        Intrinsics.checkNotNull(t2, "null cannot be cast to non-null type T of org.jetbrains.kotlin.ir.util.DeepCopySavingMetadataKt.deepCopySavingMetadata");
        return t2;
    }
}
