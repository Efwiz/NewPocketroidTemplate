package org.jetbrains.kotlin.backend.common.phaser;

import com.google.android.gms.cast.MediaTrack;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.LoggingContext;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: PhaseBuilders.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000v\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u001a²\u0002\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001j\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003`\u0004\"\b\b\u0000\u0010\u0002*\u00020\u0005\"\b\b\u0001\u0010\u0003*\u00020\u00062\u0018\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u001c\b\u0002\u0010\r\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u000f0\u000e2$\b\u0002\u0010\u0010\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\t0\u0011j\b\u0012\u0004\u0012\u0002H\u0003`\u00120\u000e2$\b\u0002\u0010\u0013\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\t0\u0011j\b\u0012\u0004\u0012\u0002H\u0003`\u00120\u000e2$\b\u0002\u0010\u0014\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\t0\u0011j\b\u0012\u0004\u0012\u0002H\u0003`\u00120\u000e26\b\u0002\u0010\u0015\u001a0\u0012,\u0012*\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\t0\u0016j\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u0002`\u00180\u000e2\b\b\u0002\u0010\u0019\u001a\u00020\u001a\u001a\u0098\u0002\u0010\u001b\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u001c0\u0001j\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u001c`\u0004\"\b\b\u0000\u0010\u0002*\u00020\u00052\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u001e0\u00112\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u001c\b\u0002\u0010\r\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u000f0\u000e2$\b\u0002\u0010\u0010\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\t0\u0011j\b\u0012\u0004\u0012\u00020\u001c`\u00120\u000e2$\b\u0002\u0010\u0013\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\t0\u0011j\b\u0012\u0004\u0012\u00020\u001c`\u00120\u000e2$\b\u0002\u0010\u0014\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\t0\u0011j\b\u0012\u0004\u0012\u00020\u001c`\u00120\u000e26\b\u0002\u0010\u0015\u001a0\u0012,\u0012*\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\t0\u0016j\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u0002H\u0002`\u00180\u000e\u001a\u0098\u0002\u0010\u001f\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020 0\u0001j\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020 `\u0004\"\b\b\u0000\u0010\u0002*\u00020\u00052\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u001e0\u00112\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u001c\b\u0002\u0010\r\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u000f0\u000e2$\b\u0002\u0010\u0010\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\t0\u0011j\b\u0012\u0004\u0012\u00020 `\u00120\u000e2$\b\u0002\u0010\u0013\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\t0\u0011j\b\u0012\u0004\u0012\u00020 `\u00120\u000e2$\b\u0002\u0010\u0014\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\t0\u0011j\b\u0012\u0004\u0012\u00020 `\u00120\u000e26\b\u0002\u0010\u0015\u001a0\u0012,\u0012*\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\t0\u0016j\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u0002H\u0002`\u00180\u000e\u001aq\u0010!\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\t0\u0001j\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\t`\u0004\"\b\b\u0000\u0010\u0002*\u00020\u00052\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u001a\u0010\r\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u000f0\u000e2\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\t0\u0011¢\u0006\u0002\b\"\u001a~\u0010#\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\t0\u0001j\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\t`\u0004\"\b\b\u0000\u0010\u0002*\u00020\u00052\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u001c\b\u0002\u0010\r\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u000f0\u000e2\b\b\u0002\u0010\u0019\u001a\u00020\u001a2\u0018\u0010$\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0%\u001aB\u0010&\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H'\u0012\u0004\u0012\u0002H(0%\"\b\b\u0000\u0010\u0002*\u00020\u0005\"\u0004\b\u0001\u0010'\"\u0004\b\u0002\u0010(2\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H(0\u0011\u001aB\u0010)\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H'\u0012\u0004\u0012\u0002H(0%\"\b\b\u0000\u0010\u0002*\u00020\u0005\"\u0004\b\u0001\u0010'\"\u0004\b\u0002\u0010(2\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H'\u0012\u0004\u0012\u0002H(0\u0011\u001a(\u0010*\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H+\u0012\u0004\u0012\u00020\t0%\"\b\b\u0000\u0010\u0002*\u00020\u0005\"\u0004\b\u0001\u0010+\u001ag\u0010,\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H+\u0012\u0004\u0012\u0002H-0%\"\b\b\u0000\u0010\u0002*\u00020\u0005\"\u0004\b\u0001\u0010+\"\u0004\b\u0002\u0010.\"\u0004\b\u0003\u0010-*\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H+\u0012\u0004\u0012\u0002H.0%2\u0018\u0010/\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u0002H-0%H\u0086\u0004¨\u00060"}, d2 = {"makeCustomPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Context", "Element", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Lorg/jetbrains/kotlin/ir/IrElement;", "op", "Lkotlin/Function2;", "", "name", "", MediaTrack.ROLE_DESCRIPTION, "prerequisite", "", "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "preconditions", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/backend/common/phaser/Checker;", "postconditions", "stickyPostconditions", "actions", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/backend/common/phaser/ActionState;", "Lorg/jetbrains/kotlin/backend/common/phaser/Action;", "nlevels", "", "makeIrFilePhase", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "lowering", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "makeIrModulePhase", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "namedOpUnitPhase", "Lkotlin/ExtensionFunctionType;", "namedUnitPhase", "lower", "Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;", "takeFromContext", "OldData", "NewData", "transform", "unitSink", "Input", "then", "Output", "Mid", "other", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PhaseBuildersKt {
    public static final <Context extends CommonBackendContext, Input, Mid, Output> CompilerPhase<Context, Input, Output> then(CompilerPhase<? super Context, Input, Mid> compilerPhase, CompilerPhase<? super Context, Mid, Output> other) {
        Intrinsics.checkNotNullParameter(compilerPhase, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return new CompositePhase(compilerPhase instanceof CompositePhase ? CollectionsKt.plus((Collection<? extends CompilerPhase<? super Context, Mid, Output>>) ((CompositePhase) compilerPhase).getPhases(), other) : CollectionsKt.listOf((Object[]) new CompilerPhase[]{compilerPhase, other}));
    }

    public static final <Context extends CommonBackendContext, Element extends IrElement> NamedCompilerPhase<Context, Element> makeCustomPhase(Function2<? super Context, ? super Element, Unit> op, String name2, String description, Set<? extends AbstractNamedCompilerPhase<? super Context, ?, ?>> prerequisite, Set<? extends Function1<? super Element, Unit>> preconditions, Set<? extends Function1<? super Element, Unit>> postconditions, Set<? extends Function1<? super Element, Unit>> stickyPostconditions, Set<? extends Function3<? super ActionState, ? super Element, ? super Context, Unit>> actions, int i) {
        Intrinsics.checkNotNullParameter(op, "op");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(prerequisite, "prerequisite");
        Intrinsics.checkNotNullParameter(preconditions, "preconditions");
        Intrinsics.checkNotNullParameter(postconditions, "postconditions");
        Intrinsics.checkNotNullParameter(stickyPostconditions, "stickyPostconditions");
        Intrinsics.checkNotNullParameter(actions, "actions");
        return new NamedCompilerPhase<>(name2, description, prerequisite, new CustomPhaseAdapter(op), preconditions, postconditions, stickyPostconditions, actions, i);
    }

    public static /* synthetic */ NamedCompilerPhase namedUnitPhase$default(String str, String str2, Set set, int i, CompilerPhase compilerPhase, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            set = SetsKt.emptySet();
        }
        if ((i2 & 8) != 0) {
            i = 1;
        }
        return namedUnitPhase(str, str2, set, i, compilerPhase);
    }

    public static final <Context extends CommonBackendContext> NamedCompilerPhase<Context, Unit> namedUnitPhase(String name2, String description, Set<? extends AbstractNamedCompilerPhase<? super Context, ?, ?>> prerequisite, int i, CompilerPhase<? super Context, Unit, Unit> lower) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(prerequisite, "prerequisite");
        Intrinsics.checkNotNullParameter(lower, "lower");
        return new NamedCompilerPhase<>(name2, description, prerequisite, lower, null, null, null, null, i, 240, null);
    }

    public static final <Context extends CommonBackendContext> NamedCompilerPhase<Context, Unit> namedOpUnitPhase(String name2, String description, Set<? extends AbstractNamedCompilerPhase<? super Context, ?, ?>> prerequisite, final Function1<? super Context, Unit> op) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(prerequisite, "prerequisite");
        Intrinsics.checkNotNullParameter(op, "op");
        return namedUnitPhase(name2, description, prerequisite, 0, new SameTypeCompilerPhase<Context, Unit>() { // from class: org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt.namedOpUnitPhase.1
            @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
            public /* bridge */ /* synthetic */ Object invoke(PhaseConfigurationService phaseConfigurationService, PhaserState phaserState, LoggingContext loggingContext, Object obj) {
                invoke(phaseConfigurationService, phaserState, (CommonBackendContext) loggingContext, (Unit) obj);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Incorrect types in method signature: (Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState<Lkotlin/Unit;>;TContext;Lkotlin/Unit;)V */
            public void invoke(PhaseConfigurationService phaseConfig, PhaserState phaserState, CommonBackendContext context, Unit input) {
                Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
                Intrinsics.checkNotNullParameter(phaserState, "phaserState");
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(input, "input");
                op.invoke(context);
            }
        });
    }

    public static final <Context extends CommonBackendContext> NamedCompilerPhase<Context, IrFile> makeIrFilePhase(Function1<? super Context, ? extends FileLoweringPass> lowering, String name2, String description, Set<? extends AbstractNamedCompilerPhase<? super Context, ?, ?>> prerequisite, Set<? extends Function1<? super IrFile, Unit>> preconditions, Set<? extends Function1<? super IrFile, Unit>> postconditions, Set<? extends Function1<? super IrFile, Unit>> stickyPostconditions, Set<? extends Function3<? super ActionState, ? super IrFile, ? super Context, Unit>> actions) {
        Intrinsics.checkNotNullParameter(lowering, "lowering");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(prerequisite, "prerequisite");
        Intrinsics.checkNotNullParameter(preconditions, "preconditions");
        Intrinsics.checkNotNullParameter(postconditions, "postconditions");
        Intrinsics.checkNotNullParameter(stickyPostconditions, "stickyPostconditions");
        Intrinsics.checkNotNullParameter(actions, "actions");
        return new NamedCompilerPhase<>(name2, description, prerequisite, new FileLoweringPhaseAdapter(lowering), preconditions, postconditions, stickyPostconditions, actions, 0);
    }

    public static final <Context extends CommonBackendContext> NamedCompilerPhase<Context, IrModuleFragment> makeIrModulePhase(Function1<? super Context, ? extends FileLoweringPass> lowering, String name2, String description, Set<? extends AbstractNamedCompilerPhase<? super Context, ?, ?>> prerequisite, Set<? extends Function1<? super IrModuleFragment, Unit>> preconditions, Set<? extends Function1<? super IrModuleFragment, Unit>> postconditions, Set<? extends Function1<? super IrModuleFragment, Unit>> stickyPostconditions, Set<? extends Function3<? super ActionState, ? super IrModuleFragment, ? super Context, Unit>> actions) {
        Intrinsics.checkNotNullParameter(lowering, "lowering");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(prerequisite, "prerequisite");
        Intrinsics.checkNotNullParameter(preconditions, "preconditions");
        Intrinsics.checkNotNullParameter(postconditions, "postconditions");
        Intrinsics.checkNotNullParameter(stickyPostconditions, "stickyPostconditions");
        Intrinsics.checkNotNullParameter(actions, "actions");
        return new NamedCompilerPhase<>(name2, description, prerequisite, new ModuleLoweringPhaseAdapter(lowering), preconditions, postconditions, stickyPostconditions, actions, 0);
    }

    public static final <Context extends CommonBackendContext, Input> CompilerPhase<Context, Input, Unit> unitSink() {
        return (CompilerPhase) new CompilerPhase<Context, Input, Unit>() { // from class: org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt.unitSink.1
            /* JADX WARN: Incorrect types in method signature: (Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState<TInput;>;TContext;TInput;)V */
            public void invoke(PhaseConfigurationService phaseConfig, PhaserState phaserState, CommonBackendContext context, Object input) {
                Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
                Intrinsics.checkNotNullParameter(phaserState, "phaserState");
                Intrinsics.checkNotNullParameter(context, "context");
            }

            @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
            public /* bridge */ /* synthetic */ Unit invoke(PhaseConfigurationService phaseConfigurationService, PhaserState phaserState, LoggingContext loggingContext, Object obj) {
                invoke(phaseConfigurationService, phaserState, (CommonBackendContext) loggingContext, obj);
                return Unit.INSTANCE;
            }
        };
    }

    public static final <Context extends CommonBackendContext, OldData, NewData> CompilerPhase<Context, OldData, NewData> takeFromContext(final Function1<? super Context, ? extends NewData> op) {
        Intrinsics.checkNotNullParameter(op, "op");
        return (CompilerPhase) new CompilerPhase<Context, OldData, NewData>() { // from class: org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt.takeFromContext.1
            /* JADX WARN: Incorrect types in method signature: (Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState<TOldData;>;TContext;TOldData;)TNewData; */
            @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
            public Object invoke(PhaseConfigurationService phaseConfig, PhaserState phaserState, CommonBackendContext context, Object input) {
                Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
                Intrinsics.checkNotNullParameter(phaserState, "phaserState");
                Intrinsics.checkNotNullParameter(context, "context");
                return op.invoke(context);
            }
        };
    }

    public static final <Context extends CommonBackendContext, OldData, NewData> CompilerPhase<Context, OldData, NewData> transform(final Function1<? super OldData, ? extends NewData> op) {
        Intrinsics.checkNotNullParameter(op, "op");
        return (CompilerPhase) new CompilerPhase<Context, OldData, NewData>() { // from class: org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt.transform.1
            /* JADX WARN: Incorrect types in method signature: (Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState<TOldData;>;TContext;TOldData;)TNewData; */
            @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
            public Object invoke(PhaseConfigurationService phaseConfig, PhaserState phaserState, CommonBackendContext context, Object input) {
                Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
                Intrinsics.checkNotNullParameter(phaserState, "phaserState");
                Intrinsics.checkNotNullParameter(context, "context");
                return op.invoke(input);
            }
        };
    }
}
