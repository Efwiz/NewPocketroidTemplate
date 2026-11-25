package org.jetbrains.kotlin.ir.types;

import kotlin.Metadata;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;

/* JADX INFO: compiled from: IrType.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001J\u0013\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H¦\u0002J\b\u0010\u0006\u001a\u00020\u0007H&\u0082\u0001\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "Lorg/jetbrains/kotlin/ir/types/IrStarProjection;", "Lorg/jetbrains/kotlin/ir/types/IrTypeProjection;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrTypeArgument extends TypeArgumentMarker {
    boolean equals(Object other);

    int hashCode();
}
