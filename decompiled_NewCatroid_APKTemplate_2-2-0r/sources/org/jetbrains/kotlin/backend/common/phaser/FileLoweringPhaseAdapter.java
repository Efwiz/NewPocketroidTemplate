package org.jetbrains.kotlin.backend.common.phaser;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.LoggingContext;
import org.jetbrains.kotlin.ir.declarations.IrFile;

/* JADX INFO: compiled from: PhaseBuilders.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00040\u0003B\u0019\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ3\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\r2\u0006\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u000f\u001a\u00020\u0004H\u0016¢\u0006\u0002\u0010\u0010R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/FileLoweringPhaseAdapter;", "Context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeCompilerPhase;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "lowering", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "(Lkotlin/jvm/functions/Function1;)V", "invoke", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;", "phaserState", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;", "context", "input", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrFile;)Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FileLoweringPhaseAdapter<Context extends CommonBackendContext> implements SameTypeCompilerPhase<Context, IrFile> {
    private final Function1<Context, FileLoweringPass> lowering;

    /* JADX WARN: Multi-variable type inference failed */
    public FileLoweringPhaseAdapter(Function1<? super Context, ? extends FileLoweringPass> lowering) {
        Intrinsics.checkNotNullParameter(lowering, "lowering");
        this.lowering = lowering;
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
    public /* bridge */ /* synthetic */ Object invoke(PhaseConfigurationService phaseConfigurationService, PhaserState phaserState, LoggingContext loggingContext, Object obj) {
        return invoke(phaseConfigurationService, (PhaserState<IrFile>) phaserState, (CommonBackendContext) loggingContext, (IrFile) obj);
    }

    public IrFile invoke(PhaseConfigurationService phaseConfig, PhaserState<IrFile> phaserState, Context context, IrFile input) {
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(phaserState, "phaserState");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(input, "input");
        this.lowering.invoke(context).lower(input);
        return input;
    }
}
