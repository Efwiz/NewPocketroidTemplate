package org.jetbrains.kotlin.backend.common;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrScript;

/* JADX INFO: compiled from: Lower.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/ScriptLoweringPass;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "irScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ScriptLoweringPass extends FileLoweringPass {
    void lower(IrScript irScript);

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    default void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        LowerKt.runOnFilePostfix(this, irFile);
    }
}
