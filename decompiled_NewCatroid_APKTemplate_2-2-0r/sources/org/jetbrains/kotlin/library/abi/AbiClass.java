package org.jetbrains.kotlin.library.abi;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: LibraryAbi.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0006R\u0012\u0010\b\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0018\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0012À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiClass;", "Lorg/jetbrains/kotlin/library/abi/AbiDeclarationWithModality;", "Lorg/jetbrains/kotlin/library/abi/AbiDeclarationContainer;", "Lorg/jetbrains/kotlin/library/abi/AbiTypeParametersContainer;", "isFunction", "", "()Z", "isInner", "isValue", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/library/abi/AbiClassKind;", "getKind", "()Lorg/jetbrains/kotlin/library/abi/AbiClassKind;", "superTypes", "", "Lorg/jetbrains/kotlin/library/abi/AbiType;", "getSuperTypes", "()Ljava/util/List;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public interface AbiClass extends AbiDeclarationContainer, AbiDeclarationWithModality, AbiTypeParametersContainer {
    AbiClassKind getKind();

    List<AbiType> getSuperTypes();

    boolean isFunction();

    boolean isInner();

    boolean isValue();
}
