package org.jetbrains.kotlin.library.abi;

import kotlin.Metadata;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: LibraryAbi.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiProperty;", "Lorg/jetbrains/kotlin/library/abi/AbiDeclarationWithModality;", "getter", "Lorg/jetbrains/kotlin/library/abi/AbiFunction;", "getGetter", "()Lorg/jetbrains/kotlin/library/abi/AbiFunction;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/library/abi/AbiPropertyKind;", "getKind", "()Lorg/jetbrains/kotlin/library/abi/AbiPropertyKind;", "setter", "getSetter", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public interface AbiProperty extends AbiDeclarationWithModality {
    AbiFunction getGetter();

    AbiPropertyKind getKind();

    AbiFunction getSetter();
}
