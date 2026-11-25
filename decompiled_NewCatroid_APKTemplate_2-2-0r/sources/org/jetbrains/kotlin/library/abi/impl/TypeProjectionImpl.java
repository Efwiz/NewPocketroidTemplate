package org.jetbrains.kotlin.library.abi.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.library.abi.AbiType;
import org.jetbrains.kotlin.library.abi.AbiTypeArgument;
import org.jetbrains.kotlin.library.abi.AbiVariance;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;

/* JADX INFO: compiled from: LibraryAbiImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0001\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/TypeProjectionImpl;", "Lorg/jetbrains/kotlin/library/abi/AbiTypeArgument$TypeProjection;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/library/abi/AbiType;", "variance", "Lorg/jetbrains/kotlin/library/abi/AbiVariance;", "(Lorg/jetbrains/kotlin/library/abi/AbiType;Lorg/jetbrains/kotlin/library/abi/AbiVariance;)V", "getType", "()Lorg/jetbrains/kotlin/library/abi/AbiType;", "getVariance", "()Lorg/jetbrains/kotlin/library/abi/AbiVariance;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class TypeProjectionImpl implements AbiTypeArgument.TypeProjection {
    private final AbiType type;
    private final AbiVariance variance;

    public TypeProjectionImpl(AbiType type, AbiVariance variance) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(variance, "variance");
        this.type = type;
        this.variance = variance;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiTypeArgument.TypeProjection
    public AbiType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiTypeArgument.TypeProjection
    public AbiVariance getVariance() {
        return this.variance;
    }
}
