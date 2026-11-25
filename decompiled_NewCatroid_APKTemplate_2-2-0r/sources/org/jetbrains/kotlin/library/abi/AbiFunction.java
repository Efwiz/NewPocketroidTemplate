package org.jetbrains.kotlin.library.abi;

import java.util.List;
import kotlin.Metadata;

/* JADX INFO: compiled from: LibraryAbi.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u00012\u00020\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\nR\u0012\u0010\f\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0012\u0010\r\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\nR\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0018\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0017À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/AbiFunction;", "Lorg/jetbrains/kotlin/library/abi/AbiDeclarationWithModality;", "Lorg/jetbrains/kotlin/library/abi/AbiTypeParametersContainer;", "contextReceiverParametersCount", "", "getContextReceiverParametersCount", "()I", "hasExtensionReceiverParameter", "", "getHasExtensionReceiverParameter", "()Z", "isConstructor", "isInline", "isSuspend", "returnType", "Lorg/jetbrains/kotlin/library/abi/AbiType;", "getReturnType", "()Lorg/jetbrains/kotlin/library/abi/AbiType;", "valueParameters", "", "Lorg/jetbrains/kotlin/library/abi/AbiValueParameter;", "getValueParameters", "()Ljava/util/List;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public interface AbiFunction extends AbiDeclarationWithModality, AbiTypeParametersContainer {
    int getContextReceiverParametersCount();

    boolean getHasExtensionReceiverParameter();

    /* JADX INFO: renamed from: getReturnType */
    AbiType mo7337getReturnType();

    List<AbiValueParameter> getValueParameters();

    boolean isConstructor();

    boolean isInline();

    boolean isSuspend();
}
