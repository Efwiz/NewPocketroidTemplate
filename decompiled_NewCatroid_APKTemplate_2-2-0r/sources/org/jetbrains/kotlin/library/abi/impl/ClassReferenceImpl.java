package org.jetbrains.kotlin.library.abi.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.abi.AbiClassifierReference;
import org.jetbrains.kotlin.library.abi.AbiQualifiedName;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;

/* JADX INFO: compiled from: LibraryAbiImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0001\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/ClassReferenceImpl;", "Lorg/jetbrains/kotlin/library/abi/AbiClassifierReference$ClassReference;", "className", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "(Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;)V", "getClassName", "()Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class ClassReferenceImpl implements AbiClassifierReference.ClassReference {
    private final AbiQualifiedName className;

    public ClassReferenceImpl(AbiQualifiedName className) {
        Intrinsics.checkNotNullParameter(className, "className");
        this.className = className;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiClassifierReference.ClassReference
    public AbiQualifiedName getClassName() {
        return this.className;
    }
}
