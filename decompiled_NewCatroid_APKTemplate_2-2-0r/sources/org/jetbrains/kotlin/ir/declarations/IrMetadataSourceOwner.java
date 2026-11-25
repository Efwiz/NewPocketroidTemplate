package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.IrElement;

/* JADX INFO: compiled from: IrMetadataSourceOwner.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrMetadataSourceOwner;", "Lorg/jetbrains/kotlin/ir/IrElement;", "metadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrMetadataSourceOwner extends IrElement {
    MetadataSource getMetadata();

    void setMetadata(MetadataSource metadataSource);
}
