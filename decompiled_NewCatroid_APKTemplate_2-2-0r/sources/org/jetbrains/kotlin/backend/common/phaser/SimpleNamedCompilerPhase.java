package org.jetbrains.kotlin.backend.common.phaser;

import com.google.android.gms.cast.MediaTrack;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.LoggingContext;

/* JADX INFO: compiled from: CompilerPhase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u0002*\u0004\b\u0001\u0010\u0003*\u0004\b\u0002\u0010\u00042\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00040\u0005B\u008f\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u001a\b\u0002\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\n\u0012$\b\u0002\u0010\u000b\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\r0\fj\b\u0012\u0004\u0012\u00028\u0001`\u000e0\n\u0012$\b\u0002\u0010\u000f\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00020\r0\fj\b\u0012\u0004\u0012\u00028\u0002`\u000e0\n\u00126\b\u0002\u0010\u0010\u001a0\u0012,\u0012*\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\r0\u0011j\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0000`\u00130\n\u0012N\b\u0002\u0010\u0014\u001aH\u0012D\u0012B\u0012\u0004\u0012\u00020\u0012\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0015\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\r0\u0011j\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0015\u0012\u0004\u0012\u00028\u0000`\u00130\n\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0017¢\u0006\u0002\u0010\u0018J\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00010\u001aH\u0016J0\u0010\u001c\u001a\"\u0012\u001e\u0012\u001c\u0012\u0004\u0012\u00020\u0017\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00050\u00150\u001d2\u0006\u0010\u001e\u001a\u00020\u0017H\u0016J\u001d\u0010\u001f\u001a\u00028\u00022\u0006\u0010 \u001a\u00028\u00002\u0006\u0010!\u001a\u00028\u0001H&¢\u0006\u0002\u0010\"J1\u0010\u001f\u001a\u00028\u00022\u0006\u0010#\u001a\u00020$2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00010\u001a2\u0006\u0010 \u001a\u00028\u00002\u0006\u0010!\u001a\u00028\u0001¢\u0006\u0002\u0010%J;\u0010&\u001a\u00020\r2\u0006\u0010#\u001a\u00020$2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00020\u001a2\u0006\u0010 \u001a\u00028\u00002\u0006\u0010!\u001a\u00028\u00012\u0006\u0010'\u001a\u00028\u0002H\u0016¢\u0006\u0002\u0010(J3\u0010)\u001a\u00020\r2\u0006\u0010#\u001a\u00020$2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00010\u001a2\u0006\u0010 \u001a\u00028\u00002\u0006\u0010!\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010*RT\u0010\u0014\u001aH\u0012D\u0012B\u0012\u0004\u0012\u00020\u0012\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0015\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\r0\u0011j\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0015\u0012\u0004\u0012\u00028\u0000`\u00130\nX\u0082\u0004¢\u0006\u0002\n\u0000R<\u0010\u0010\u001a0\u0012,\u0012*\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\r0\u0011j\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0000`\u00130\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/SimpleNamedCompilerPhase;", "Context", "Lorg/jetbrains/kotlin/backend/common/LoggingContext;", "Input", "Output", "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "name", "", MediaTrack.ROLE_DESCRIPTION, "prerequisite", "", "preconditions", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/backend/common/phaser/Checker;", "postconditions", "preactions", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/backend/common/phaser/ActionState;", "Lorg/jetbrains/kotlin/backend/common/phaser/Action;", "postactions", "Lkotlin/Pair;", "nlevels", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;I)V", "changePhaserStateType", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;", "phaserState", "getNamedSubphases", "", "startDepth", "phaseBody", "context", "input", "(Lorg/jetbrains/kotlin/backend/common/LoggingContext;Ljava/lang/Object;)Ljava/lang/Object;", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;Lorg/jetbrains/kotlin/backend/common/LoggingContext;Ljava/lang/Object;)Ljava/lang/Object;", "runAfter", "output", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;Lorg/jetbrains/kotlin/backend/common/LoggingContext;Ljava/lang/Object;Ljava/lang/Object;)V", "runBefore", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;Lorg/jetbrains/kotlin/backend/common/LoggingContext;Ljava/lang/Object;)V", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SimpleNamedCompilerPhase<Context extends LoggingContext, Input, Output> extends AbstractNamedCompilerPhase<Context, Input, Output> {
    private final Set<Function3<ActionState, Pair<? extends Input, ? extends Output>, Context, Unit>> postactions;
    private final Set<Function3<ActionState, Input, Context, Unit>> preactions;

    public abstract Output phaseBody(Context context, Input input);

    public /* synthetic */ SimpleNamedCompilerPhase(String str, String str2, Set set, Set set2, Set set3, Set set4, Set set5, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i2 & 4) != 0 ? SetsKt.emptySet() : set, (i2 & 8) != 0 ? SetsKt.emptySet() : set2, (i2 & 16) != 0 ? SetsKt.emptySet() : set3, (i2 & 32) != 0 ? SetsKt.emptySet() : set4, (i2 & 64) != 0 ? SetsKt.emptySet() : set5, (i2 & 128) != 0 ? 0 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SimpleNamedCompilerPhase(String name2, String description, Set<? extends AbstractNamedCompilerPhase<?, ?, ?>> prerequisite, Set<? extends Function1<? super Input, Unit>> preconditions, Set<? extends Function1<? super Output, Unit>> postconditions, Set<? extends Function3<? super ActionState, ? super Input, ? super Context, Unit>> preactions, Set<? extends Function3<? super ActionState, ? super Pair<? extends Input, ? extends Output>, ? super Context, Unit>> postactions, int i) {
        super(name2, description, prerequisite, preconditions, postconditions, i);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(prerequisite, "prerequisite");
        Intrinsics.checkNotNullParameter(preconditions, "preconditions");
        Intrinsics.checkNotNullParameter(postconditions, "postconditions");
        Intrinsics.checkNotNullParameter(preactions, "preactions");
        Intrinsics.checkNotNullParameter(postactions, "postactions");
        this.preactions = preactions;
        this.postactions = postactions;
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase
    public final Output phaseBody(PhaseConfigurationService phaseConfig, PhaserState<Input> phaserState, Context context, Input input) {
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(phaserState, "phaserState");
        Intrinsics.checkNotNullParameter(context, "context");
        return phaseBody(context, input);
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase
    public PhaserState<Output> changePhaserStateType(PhaserState<Input> phaserState) {
        Intrinsics.checkNotNullParameter(phaserState, "phaserState");
        return CompilerPhaseKt.changePhaserStateType(phaserState);
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase
    public void runBefore(PhaseConfigurationService phaseConfig, PhaserState<Input> phaserState, Context context, Input input) {
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(phaserState, "phaserState");
        Intrinsics.checkNotNullParameter(context, "context");
        ActionState actionState = new ActionState(phaseConfig, this, phaserState.getPhaseCount(), BeforeOrAfter.BEFORE);
        Iterator<Function3<ActionState, Input, Context, Unit>> it = this.preactions.iterator();
        while (it.hasNext()) {
            it.next().invoke(actionState, input, context);
        }
        if (phaseConfig.getCheckConditions()) {
            Iterator<Function1<Input, Unit>> it2 = getPreconditions().iterator();
            while (it2.hasNext()) {
                it2.next().invoke(input);
            }
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase
    public void runAfter(PhaseConfigurationService phaseConfig, PhaserState<Output> phaserState, Context context, Input input, Output output) {
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(phaserState, "phaserState");
        Intrinsics.checkNotNullParameter(context, "context");
        ActionState actionState = new ActionState(phaseConfig, this, phaserState.getPhaseCount(), BeforeOrAfter.AFTER);
        Iterator<Function3<ActionState, Pair<? extends Input, ? extends Output>, Context, Unit>> it = this.postactions.iterator();
        while (it.hasNext()) {
            it.next().invoke(actionState, TuplesKt.to(input, output), context);
        }
        if (phaseConfig.getCheckConditions()) {
            Iterator<Function1<Output, Unit>> it2 = getPostconditions().iterator();
            while (it2.hasNext()) {
                it2.next().invoke(output);
            }
            Iterator<Function1<Output, Unit>> it3 = getStickyPostconditions().iterator();
            while (it3.hasNext()) {
                it3.next().invoke(output);
            }
            if (phaseConfig.getCheckStickyConditions()) {
                Iterator<Function1<Output, Unit>> it4 = phaserState.getStickyPostconditions().iterator();
                while (it4.hasNext()) {
                    it4.next().invoke(output);
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
    public List<Pair<Integer, AbstractNamedCompilerPhase<Context, ?, ?>>> getNamedSubphases(int startDepth) {
        return CollectionsKt.listOf(TuplesKt.to(Integer.valueOf(startDepth), this));
    }
}
