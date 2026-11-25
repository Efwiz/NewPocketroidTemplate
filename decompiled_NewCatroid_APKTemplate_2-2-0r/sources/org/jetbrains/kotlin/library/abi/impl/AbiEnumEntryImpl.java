package org.jetbrains.kotlin.library.abi.impl;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.abi.AbiEnumEntry;
import org.jetbrains.kotlin.library.abi.AbiQualifiedName;
import org.jetbrains.kotlin.library.abi.AbiSignatures;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;

/* JADX INFO: compiled from: LibraryAbiImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0001\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0003H\u0016R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiEnumEntryImpl;", "Lorg/jetbrains/kotlin/library/abi/AbiEnumEntry;", "qualifiedName", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "signatures", "Lorg/jetbrains/kotlin/library/abi/AbiSignatures;", "annotations", "", "(Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;Lorg/jetbrains/kotlin/library/abi/AbiSignatures;Ljava/util/Set;)V", "getQualifiedName", "()Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "getSignatures", "()Lorg/jetbrains/kotlin/library/abi/AbiSignatures;", "hasAnnotation", "", "annotationClassName", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class AbiEnumEntryImpl implements AbiEnumEntry {
    private final Set<AbiQualifiedName> annotations;
    private final AbiQualifiedName qualifiedName;
    private final AbiSignatures signatures;

    public AbiEnumEntryImpl(AbiQualifiedName qualifiedName, AbiSignatures signatures, Set<AbiQualifiedName> annotations) {
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        this.qualifiedName = qualifiedName;
        this.signatures = signatures;
        this.annotations = annotations;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclaration
    public AbiQualifiedName getQualifiedName() {
        return this.qualifiedName;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclaration
    public AbiSignatures getSignatures() {
        return this.signatures;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclaration
    public boolean hasAnnotation(AbiQualifiedName annotationClassName) {
        Intrinsics.checkNotNullParameter(annotationClassName, "annotationClassName");
        return this.annotations.contains(annotationClassName);
    }
}
