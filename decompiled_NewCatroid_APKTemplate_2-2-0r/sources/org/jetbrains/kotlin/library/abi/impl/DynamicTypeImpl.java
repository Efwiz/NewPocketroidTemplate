package org.jetbrains.kotlin.library.abi.impl;

import kotlin.Metadata;
import org.jetbrains.kotlin.library.abi.AbiType;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;

/* JADX INFO: compiled from: LibraryAbiImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÁ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/DynamicTypeImpl;", "Lorg/jetbrains/kotlin/library/abi/AbiType$Dynamic;", "()V", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class DynamicTypeImpl implements AbiType.Dynamic {
    public static final DynamicTypeImpl INSTANCE = new DynamicTypeImpl();

    private DynamicTypeImpl() {
    }
}
