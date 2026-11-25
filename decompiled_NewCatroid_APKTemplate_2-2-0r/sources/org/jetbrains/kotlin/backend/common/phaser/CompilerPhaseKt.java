package org.jetbrains.kotlin.backend.common.phaser;

import java.util.LinkedHashSet;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.LoggingContext;

/* JADX INFO: compiled from: CompilerPhase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000R\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\"\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00030\u0001\u001a=\u0010\u0004\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u00012\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00050\nH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001aO\u0010\f\u001a\u0002H\u0002\"\b\b\u0000\u0010\r*\u00020\u000e\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0002*\u0014\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u0002H\r2\u0006\u0010\u0013\u001a\u0002H\u0003¢\u0006\u0002\u0010\u0014\u001a\u0099\u0001\u0010\u0015\u001a*\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00190\u0016j\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\r`\u001a\"\u0004\b\u0000\u0010\u0018\"\u0004\b\u0001\u0010\r**\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00190\u0016j\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\r`\u001a2.\u0010\u001b\u001a*\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00190\u0016j\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\r`\u001aH\u0086\u0006*F\u0010\u001c\u001a\u0004\b\u0000\u0010\u0018\u001a\u0004\b\u0001\u0010\r\"\u001a\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00190\u00162\u001a\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u00020\u00190\u0016*\"\u0010\u001d\"\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001e2\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001e*(\u0010\u001f\u001a\u0004\b\u0000\u0010\u0018\"\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u00020\u00190 2\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u00020\u00190 *.\u0010!\u001a\u0004\b\u0000\u0010\r\u001a\u0004\b\u0001\u0010\u0018\"\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u00180\"2\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\u00180\"\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006#"}, d2 = {"changePhaserStateType", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;", "Output", "Input", "downlevel", "R", "D", "nlevels", "", "block", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;ILkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "invokeToplevel", "Context", "Lorg/jetbrains/kotlin/backend/common/LoggingContext;", "Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;", "context", "input", "(Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;Lorg/jetbrains/kotlin/backend/common/LoggingContext;Ljava/lang/Object;)Ljava/lang/Object;", "plus", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/backend/common/phaser/ActionState;", "Data", "", "Lorg/jetbrains/kotlin/backend/common/phaser/Action;", "other", "Action", "AnyNamedPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "Checker", "Lkotlin/Function1;", "SameTypeNamedCompilerPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompilerPhaseKt {
    public static final <Input, Output> PhaserState<Output> changePhaserStateType(PhaserState<Input> phaserState) {
        Intrinsics.checkNotNullParameter(phaserState, "<this>");
        return new PhaserState<>(phaserState.getAlreadyDone(), phaserState.getDepth(), phaserState.getPhaseCount(), new LinkedHashSet());
    }

    public static final <R, D> R downlevel(PhaserState<D> phaserState, int i, Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter(phaserState, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        phaserState.setDepth(phaserState.getDepth() + i);
        R rInvoke = block.invoke();
        phaserState.setDepth(phaserState.getDepth() - i);
        return rInvoke;
    }

    public static final <Context extends LoggingContext, Input, Output> Output invokeToplevel(CompilerPhase<? super Context, Input, Output> compilerPhase, PhaseConfig phaseConfig, Context context, Input input) {
        Intrinsics.checkNotNullParameter(compilerPhase, "<this>");
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(context, "context");
        return compilerPhase.invoke(phaseConfig, new PhaserState<>(null, 0, 0, null, 15, null), context, input);
    }

    public static final <Data, Context> Function3<ActionState, Data, Context, Unit> plus(final Function3<? super ActionState, ? super Data, ? super Context, Unit> function3, final Function3<? super ActionState, ? super Data, ? super Context, Unit> other) {
        Intrinsics.checkNotNullParameter(function3, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return new Function3<ActionState, Data, Context, Unit>() { // from class: org.jetbrains.kotlin.backend.common.phaser.CompilerPhaseKt.plus.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(3);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(ActionState actionState, Object obj, Object obj2) {
                invoke2(actionState, obj, obj2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ActionState phaseState, Data data, Context context) {
                Intrinsics.checkNotNullParameter(phaseState, "phaseState");
                function3.invoke(phaseState, data, context);
                other.invoke(phaseState, data, context);
            }
        };
    }
}
