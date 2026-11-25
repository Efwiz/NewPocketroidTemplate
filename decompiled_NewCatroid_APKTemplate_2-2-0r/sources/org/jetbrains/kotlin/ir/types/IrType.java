package org.jetbrains.kotlin.ir.types;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: IrType.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0013\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H¦\u0002J\b\u0010\b\u001a\u00020\tH&¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrType implements IrAnnotationContainer, KotlinTypeMarker {
    public abstract boolean equals(Object other);

    public abstract int hashCode();
}
