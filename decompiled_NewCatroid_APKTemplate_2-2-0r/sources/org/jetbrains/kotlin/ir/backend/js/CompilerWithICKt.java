package org.jetbrains.kotlin.ir.backend.js;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.backend.common.phaser.PhaserState;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: compilerWithIC.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a,\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"lowerPreservingTags", "", "modules", "", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;", "controller", "Lorg/jetbrains/kotlin/ir/backend/js/WholeWorldStageController;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompilerWithICKt {
    public static final void lowerPreservingTags(Iterable<? extends IrModuleFragment> modules, JsIrBackendContext context, PhaseConfig phaseConfig, WholeWorldStageController controller) {
        Intrinsics.checkNotNullParameter(modules, "modules");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(controller, "controller");
        int i = 0;
        controller.setCurrentStage(0);
        PhaserState phaserState = new PhaserState(null, 0, 0, null, 15, null);
        for (Object obj : JsLoweringPhasesKt.getLoweringList()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            controller.setCurrentStage(i2);
            ((Lowering) obj).getModulePhase().invoke(phaseConfig, phaserState, context, modules);
            i = i2;
        }
        controller.setCurrentStage(JsLoweringPhasesKt.getLoweringList().size() + 1);
    }
}
