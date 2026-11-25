package org.jetbrains.kotlin.backend.common.phaser;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.LoggingContext;

/* JADX INFO: compiled from: PhaseBuilders.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\u0004\b\u0001\u0010\u00032\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0004B\u001f\u0012\u0018\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ3\u0010\t\u001a\u00028\u00012\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00010\r2\u0006\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u000f\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0010R \u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/CustomPhaseAdapter;", "Context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Element", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeCompilerPhase;", "op", "Lkotlin/Function2;", "", "(Lkotlin/jvm/functions/Function2;)V", "invoke", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;", "phaserState", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;", "context", "input", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Ljava/lang/Object;)Ljava/lang/Object;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CustomPhaseAdapter<Context extends CommonBackendContext, Element> implements SameTypeCompilerPhase<Context, Element> {
    private final Function2<Context, Element, Unit> op;

    /* JADX WARN: Multi-variable type inference failed */
    public CustomPhaseAdapter(Function2<? super Context, ? super Element, Unit> op) {
        Intrinsics.checkNotNullParameter(op, "op");
        this.op = op;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
    public /* bridge */ /* synthetic */ Object invoke(PhaseConfigurationService phaseConfigurationService, PhaserState phaserState, LoggingContext loggingContext, Object obj) {
        return invoke(phaseConfigurationService, (PhaserState<Object>) phaserState, (CommonBackendContext) loggingContext, obj);
    }

    public Element invoke(PhaseConfigurationService phaseConfig, PhaserState<Element> phaserState, Context context, Element input) {
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(phaserState, "phaserState");
        Intrinsics.checkNotNullParameter(context, "context");
        this.op.invoke(context, input);
        return input;
    }
}
