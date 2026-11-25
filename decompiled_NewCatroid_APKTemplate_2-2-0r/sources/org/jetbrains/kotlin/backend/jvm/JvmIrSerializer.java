package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrFile;

/* JADX INFO: compiled from: JvmIrSerializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0007\u001a\u00020\bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmIrSerializer;", "", "serializeIrFile", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "serializeTopLevelIrClass", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JvmIrSerializer {
    byte[] serializeIrFile(IrFile irFile);

    byte[] serializeTopLevelIrClass(IrClass irClass);
}
