package org.jetbrains.kotlin.library.abi;

import java.util.List;
import kotlin.Metadata;

/* JADX INFO: compiled from: LibraryAbi.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bw\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\u0007\bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiTypeParametersContainer;", "Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "typeParameters", "", "Lorg/jetbrains/kotlin/library/abi/AbiTypeParameter;", "getTypeParameters", "()Ljava/util/List;", "Lorg/jetbrains/kotlin/library/abi/AbiClass;", "Lorg/jetbrains/kotlin/library/abi/AbiFunction;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public interface AbiTypeParametersContainer extends AbiDeclaration {
    List<AbiTypeParameter> getTypeParameters();
}
