package org.jetbrains.kotlin.backend.common.phaser;

import com.google.android.gms.cast.MediaTrack;
import java.util.Collection;
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
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u0002*\u0004\b\u0001\u0010\u00032\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00030\u0004Bÿ\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u001a\b\u0002\u0010\b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00040\t\u0012\u0018\u0010\n\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00010\u000b\u0012$\b\u0002\u0010\f\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u000e0\rj\b\u0012\u0004\u0012\u00028\u0001`\u000f0\t\u0012$\b\u0002\u0010\u0010\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u000e0\rj\b\u0012\u0004\u0012\u00028\u0001`\u000f0\t\u0012$\b\u0002\u0010\u0011\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u000e0\rj\b\u0012\u0004\u0012\u00028\u0001`\u000f0\t\u00126\b\u0002\u0010\u0012\u001a0\u0012,\u0012*\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u000e0\u0013j\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0000`\u00150\t\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0017¢\u0006\u0002\u0010\u0018J\u001c\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00010\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00010\u001cH\u0016J0\u0010\u001e\u001a\"\u0012\u001e\u0012\u001c\u0012\u0004\u0012\u00020\u0017\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00040 0\u001f2\u0006\u0010!\u001a\u00020\u0017H\u0016J3\u0010\"\u001a\u00028\u00012\u0006\u0010#\u001a\u00020$2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00010\u001c2\u0006\u0010%\u001a\u00028\u00002\u0006\u0010&\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010'J3\u0010(\u001a\u00028\u00012\u0006\u0010#\u001a\u00020$2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00010\u001c2\u0006\u0010%\u001a\u00028\u00002\u0006\u0010&\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010'J;\u0010)\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020$2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00010\u001c2\u0006\u0010%\u001a\u00028\u00002\u0006\u0010&\u001a\u00028\u00012\u0006\u0010*\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010+J3\u0010,\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020$2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00010\u001c2\u0006\u0010%\u001a\u00028\u00002\u0006\u0010&\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010-R<\u0010\u0012\u001a0\u0012,\u0012*\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u000e0\u0013j\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u0000`\u00150\tX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\n\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R0\u0010\u0011\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\u000e0\rj\b\u0012\u0004\u0012\u00028\u0001`\u000f0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Context", "Lorg/jetbrains/kotlin/backend/common/LoggingContext;", "Data", "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "name", "", MediaTrack.ROLE_DESCRIPTION, "prerequisite", "", "lower", "Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;", "preconditions", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/backend/common/phaser/Checker;", "postconditions", "stickyPostconditions", "actions", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/backend/common/phaser/ActionState;", "Lorg/jetbrains/kotlin/backend/common/phaser/Action;", "nlevels", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;I)V", "getStickyPostconditions", "()Ljava/util/Set;", "changePhaserStateType", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;", "phaserState", "getNamedSubphases", "", "Lkotlin/Pair;", "startDepth", "outputIfNotEnabled", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;", "context", "input", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;Lorg/jetbrains/kotlin/backend/common/LoggingContext;Ljava/lang/Object;)Ljava/lang/Object;", "phaseBody", "runAfter", "output", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;Lorg/jetbrains/kotlin/backend/common/LoggingContext;Ljava/lang/Object;Ljava/lang/Object;)V", "runBefore", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;Lorg/jetbrains/kotlin/backend/common/LoggingContext;Ljava/lang/Object;)V", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NamedCompilerPhase<Context extends LoggingContext, Data> extends AbstractNamedCompilerPhase<Context, Data, Data> {
    private final Set<Function3<ActionState, Data, Context, Unit>> actions;
    private final CompilerPhase<Context, Data, Data> lower;
    private final Set<Function1<Data, Unit>> stickyPostconditions;

    @Override // org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase
    public PhaserState<Data> changePhaserStateType(PhaserState<Data> phaserState) {
        Intrinsics.checkNotNullParameter(phaserState, "phaserState");
        return phaserState;
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase
    public Data outputIfNotEnabled(PhaseConfigurationService phaseConfig, PhaserState<Data> phaserState, Context context, Data input) {
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(phaserState, "phaserState");
        Intrinsics.checkNotNullParameter(context, "context");
        return input;
    }

    public /* synthetic */ NamedCompilerPhase(String str, String str2, Set set, CompilerPhase compilerPhase, Set set2, Set set3, Set set4, Set set5, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i2 & 4) != 0 ? SetsKt.emptySet() : set, compilerPhase, (i2 & 16) != 0 ? SetsKt.emptySet() : set2, (i2 & 32) != 0 ? SetsKt.emptySet() : set3, (i2 & 64) != 0 ? SetsKt.emptySet() : set4, (i2 & 128) != 0 ? SetsKt.emptySet() : set5, (i2 & 256) != 0 ? 0 : i);
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
    public Set<Function1<Data, Unit>> getStickyPostconditions() {
        return this.stickyPostconditions;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public NamedCompilerPhase(String name2, String description, Set<? extends AbstractNamedCompilerPhase<?, ?, ?>> prerequisite, CompilerPhase<? super Context, Data, Data> lower, Set<? extends Function1<? super Data, Unit>> preconditions, Set<? extends Function1<? super Data, Unit>> postconditions, Set<? extends Function1<? super Data, Unit>> stickyPostconditions, Set<? extends Function3<? super ActionState, ? super Data, ? super Context, Unit>> actions, int i) {
        super(name2, description, prerequisite, preconditions, postconditions, i);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        Intrinsics.checkNotNullParameter(prerequisite, "prerequisite");
        Intrinsics.checkNotNullParameter(lower, "lower");
        Intrinsics.checkNotNullParameter(preconditions, "preconditions");
        Intrinsics.checkNotNullParameter(postconditions, "postconditions");
        Intrinsics.checkNotNullParameter(stickyPostconditions, "stickyPostconditions");
        Intrinsics.checkNotNullParameter(actions, "actions");
        this.lower = lower;
        this.stickyPostconditions = stickyPostconditions;
        this.actions = actions;
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase
    public Data phaseBody(PhaseConfigurationService phaseConfig, PhaserState<Data> phaserState, Context context, Data input) {
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(phaserState, "phaserState");
        Intrinsics.checkNotNullParameter(context, "context");
        return this.lower.invoke(phaseConfig, phaserState, context, input);
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase
    public void runBefore(PhaseConfigurationService phaseConfig, PhaserState<Data> phaserState, Context context, Data input) {
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(phaserState, "phaserState");
        Intrinsics.checkNotNullParameter(context, "context");
        ActionState actionState = new ActionState(phaseConfig, this, phaserState.getPhaseCount(), BeforeOrAfter.BEFORE);
        Iterator<Function3<ActionState, Data, Context, Unit>> it = this.actions.iterator();
        while (it.hasNext()) {
            it.next().invoke(actionState, input, context);
        }
        if (phaseConfig.getCheckConditions()) {
            Iterator<Function1<Data, Unit>> it2 = getPreconditions().iterator();
            while (it2.hasNext()) {
                it2.next().invoke(input);
            }
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.AbstractNamedCompilerPhase
    public void runAfter(PhaseConfigurationService phaseConfig, PhaserState<Data> phaserState, Context context, Data input, Data output) {
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(phaserState, "phaserState");
        Intrinsics.checkNotNullParameter(context, "context");
        ActionState actionState = new ActionState(phaseConfig, this, phaserState.getPhaseCount(), BeforeOrAfter.AFTER);
        Iterator<Function3<ActionState, Data, Context, Unit>> it = this.actions.iterator();
        while (it.hasNext()) {
            it.next().invoke(actionState, output, context);
        }
        if (phaseConfig.getCheckConditions()) {
            Iterator<Function1<Data, Unit>> it2 = getPostconditions().iterator();
            while (it2.hasNext()) {
                it2.next().invoke(output);
            }
            Iterator<Function1<Data, Unit>> it3 = getStickyPostconditions().iterator();
            while (it3.hasNext()) {
                it3.next().invoke(output);
            }
            if (phaseConfig.getCheckStickyConditions()) {
                Iterator<Function1<Data, Unit>> it4 = phaserState.getStickyPostconditions().iterator();
                while (it4.hasNext()) {
                    it4.next().invoke(output);
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
    public List<Pair<Integer, AbstractNamedCompilerPhase<Context, ?, ?>>> getNamedSubphases(int startDepth) {
        return CollectionsKt.plus((Collection) CollectionsKt.listOf(TuplesKt.to(Integer.valueOf(startDepth), this)), (Iterable) this.lower.getNamedSubphases(startDepth + getNlevels()));
    }
}
