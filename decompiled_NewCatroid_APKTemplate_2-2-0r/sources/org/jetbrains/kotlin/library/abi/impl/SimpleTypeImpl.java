package org.jetbrains.kotlin.library.abi.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.abi.AbiClassifierReference;
import org.jetbrains.kotlin.library.abi.AbiType;
import org.jetbrains.kotlin.library.abi.AbiTypeArgument;
import org.jetbrains.kotlin.library.abi.AbiTypeNullability;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;

/* JADX INFO: compiled from: LibraryAbiImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0001\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/SimpleTypeImpl;", "Lorg/jetbrains/kotlin/library/abi/AbiType$Simple;", "classifierReference", "Lorg/jetbrains/kotlin/library/abi/AbiClassifierReference;", "arguments", "", "Lorg/jetbrains/kotlin/library/abi/AbiTypeArgument;", "nullability", "Lorg/jetbrains/kotlin/library/abi/AbiTypeNullability;", "(Lorg/jetbrains/kotlin/library/abi/AbiClassifierReference;Ljava/util/List;Lorg/jetbrains/kotlin/library/abi/AbiTypeNullability;)V", "getArguments", "()Ljava/util/List;", "getClassifierReference", "()Lorg/jetbrains/kotlin/library/abi/AbiClassifierReference;", "getNullability", "()Lorg/jetbrains/kotlin/library/abi/AbiTypeNullability;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class SimpleTypeImpl implements AbiType.Simple {
    private final List<AbiTypeArgument> arguments;
    private final AbiClassifierReference classifierReference;
    private final AbiTypeNullability nullability;

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleTypeImpl(AbiClassifierReference classifierReference, List<? extends AbiTypeArgument> arguments, AbiTypeNullability nullability) {
        Intrinsics.checkNotNullParameter(classifierReference, "classifierReference");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(nullability, "nullability");
        this.classifierReference = classifierReference;
        this.arguments = arguments;
        this.nullability = nullability;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiType.Simple
    public AbiClassifierReference getClassifierReference() {
        return this.classifierReference;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiType.Simple
    public List<AbiTypeArgument> getArguments() {
        return this.arguments;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiType.Simple
    public AbiTypeNullability getNullability() {
        return this.nullability;
    }
}
