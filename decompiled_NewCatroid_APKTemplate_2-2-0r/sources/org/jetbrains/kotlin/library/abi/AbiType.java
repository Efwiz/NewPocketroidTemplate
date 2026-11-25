package org.jetbrains.kotlin.library.abi;

import java.util.List;
import kotlin.Metadata;

/* JADX INFO: compiled from: LibraryAbi.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bw\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0003\u0005\u0006\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiType;", "", "Dynamic", "Error", "Simple", "Lorg/jetbrains/kotlin/library/abi/AbiType$Dynamic;", "Lorg/jetbrains/kotlin/library/abi/AbiType$Error;", "Lorg/jetbrains/kotlin/library/abi/AbiType$Simple;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public interface AbiType {

    /* JADX INFO: compiled from: LibraryAbi.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0002À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiType$Dynamic;", "Lorg/jetbrains/kotlin/library/abi/AbiType;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Dynamic extends AbiType {
    }

    /* JADX INFO: compiled from: LibraryAbi.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0002À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiType$Error;", "Lorg/jetbrains/kotlin/library/abi/AbiType;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Error extends AbiType {
    }

    /* JADX INFO: compiled from: LibraryAbi.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiType$Simple;", "Lorg/jetbrains/kotlin/library/abi/AbiType;", "arguments", "", "Lorg/jetbrains/kotlin/library/abi/AbiTypeArgument;", "getArguments", "()Ljava/util/List;", "classifierReference", "Lorg/jetbrains/kotlin/library/abi/AbiClassifierReference;", "getClassifierReference", "()Lorg/jetbrains/kotlin/library/abi/AbiClassifierReference;", "nullability", "Lorg/jetbrains/kotlin/library/abi/AbiTypeNullability;", "getNullability", "()Lorg/jetbrains/kotlin/library/abi/AbiTypeNullability;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Simple extends AbiType {
        List<AbiTypeArgument> getArguments();

        AbiClassifierReference getClassifierReference();

        AbiTypeNullability getNullability();
    }
}
