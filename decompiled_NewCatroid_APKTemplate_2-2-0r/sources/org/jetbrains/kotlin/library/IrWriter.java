package org.jetbrains.kotlin.library;

import kotlin.Metadata;

/* JADX INFO: compiled from: KotlinLibraryWriter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&¨\u0006\tÀ\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/library/IrWriter;", "", "addDataFlowGraph", "", "dataFlowGraph", "", "addIr", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/library/SerializedIrModule;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface IrWriter {
    void addDataFlowGraph(byte[] dataFlowGraph);

    void addIr(SerializedIrModule ir);
}
