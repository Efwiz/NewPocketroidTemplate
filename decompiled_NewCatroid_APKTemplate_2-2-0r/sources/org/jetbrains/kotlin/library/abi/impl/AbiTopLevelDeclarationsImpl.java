package org.jetbrains.kotlin.library.abi.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.abi.AbiDeclaration;
import org.jetbrains.kotlin.library.abi.AbiTopLevelDeclarations;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;

/* JADX INFO: compiled from: LibraryAbiImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0001\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiTopLevelDeclarationsImpl;", "Lorg/jetbrains/kotlin/library/abi/AbiTopLevelDeclarations;", "declarations", "", "Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "(Ljava/util/List;)V", "getDeclarations", "()Ljava/util/List;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class AbiTopLevelDeclarationsImpl implements AbiTopLevelDeclarations {
    private final List<AbiDeclaration> declarations;

    /* JADX WARN: Multi-variable type inference failed */
    public AbiTopLevelDeclarationsImpl(List<? extends AbiDeclaration> declarations) {
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        this.declarations = declarations;
    }

    @Override // org.jetbrains.kotlin.library.abi.AbiDeclarationContainer
    public List<AbiDeclaration> getDeclarations() {
        return this.declarations;
    }
}
