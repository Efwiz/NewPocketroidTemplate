package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: irValidation.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"validateIr", "", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "module", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrValidationKt {
    public static final void validateIr(JvmBackendContext context, IrModuleFragment module) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(module, "module");
        if (context.getConfig().getShouldValidateIr()) {
            DumperVerifierKt.validationCallback(context, module, true);
        }
    }
}
