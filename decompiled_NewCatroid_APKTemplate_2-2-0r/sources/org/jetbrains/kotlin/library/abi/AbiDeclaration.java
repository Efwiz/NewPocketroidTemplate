package org.jetbrains.kotlin.library.abi;

import kotlin.Metadata;

/* JADX INFO: compiled from: LibraryAbi.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bw\u0018\u00002\u00020\u0001J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t\u0082\u0001\u0003\r\u000e\u000fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "", "qualifiedName", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "getQualifiedName", "()Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "signatures", "Lorg/jetbrains/kotlin/library/abi/AbiSignatures;", "getSignatures", "()Lorg/jetbrains/kotlin/library/abi/AbiSignatures;", "hasAnnotation", "", "annotationClassName", "Lorg/jetbrains/kotlin/library/abi/AbiDeclarationWithModality;", "Lorg/jetbrains/kotlin/library/abi/AbiEnumEntry;", "Lorg/jetbrains/kotlin/library/abi/AbiTypeParametersContainer;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public interface AbiDeclaration {
    AbiQualifiedName getQualifiedName();

    AbiSignatures getSignatures();

    boolean hasAnnotation(AbiQualifiedName annotationClassName);
}
