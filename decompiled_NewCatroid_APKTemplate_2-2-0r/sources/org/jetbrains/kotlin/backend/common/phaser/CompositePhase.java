package org.jetbrains.kotlin.backend.common.phaser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.LoggingContext;

/* JADX INFO: compiled from: PhaseBuilders.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\u0004\b\u0001\u0010\u0003*\u0004\b\u0002\u0010\u00042\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00040\u0005B)\u0012\"\u0010\u0006\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00050\u0007¢\u0006\u0002\u0010\tJ0\u0010\u0013\u001a\"\u0012\u001e\u0012\u001c\u0012\u0004\u0012\u00020\u0015\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00160\u00140\u00072\u0006\u0010\u0017\u001a\u00020\u0015H\u0016J3\u0010\u0018\u001a\u00028\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00010\u001c2\u0006\u0010\u001d\u001a\u00028\u00002\u0006\u0010\u001e\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u001fR-\u0010\u0006\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR4\u0010\f\u001a\"\u0012\u001e\u0012\u001c\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0004\u0012\u00020\u000f0\u000ej\n\u0012\u0006\u0012\u0004\u0018\u00010\b`\u00100\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/CompositePhase;", "Context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "Input", "Output", "Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;", "phases", "", "", "(Ljava/util/List;)V", "getPhases", "()Ljava/util/List;", "stickyPostconditions", "", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/backend/common/phaser/Checker;", "getStickyPostconditions", "()Ljava/util/Set;", "getNamedSubphases", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "startDepth", "invoke", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;", "phaserState", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;", "context", "input", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Ljava/lang/Object;)Ljava/lang/Object;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CompositePhase<Context extends CommonBackendContext, Input, Output> implements CompilerPhase<Context, Input, Output> {
    private final List<CompilerPhase<Context, Object, Object>> phases;

    /* JADX WARN: Multi-variable type inference failed */
    public CompositePhase(List<? extends CompilerPhase<? super Context, Object, Object>> phases) {
        Intrinsics.checkNotNullParameter(phases, "phases");
        this.phases = phases;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
    public /* bridge */ /* synthetic */ Object invoke(PhaseConfigurationService phaseConfigurationService, PhaserState phaserState, LoggingContext loggingContext, Object obj) {
        return invoke(phaseConfigurationService, (PhaserState<Object>) phaserState, (CommonBackendContext) loggingContext, obj);
    }

    public final List<CompilerPhase<Context, Object, Object>> getPhases() {
        return this.phases;
    }

    public Output invoke(PhaseConfigurationService phaseConfig, PhaserState<Input> phaserState, Context context, Input input) {
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(phaserState, "phaserState");
        Intrinsics.checkNotNullParameter(context, "context");
        Context context2 = context;
        Input input2 = (Output) ((CompilerPhase) CollectionsKt.first((List) this.phases)).invoke(phaseConfig, phaserState, context2, input);
        List<CompilerPhase<Context, Object, Object>> list = this.phases;
        for (Pair pair : CollectionsKt.zip(list, CollectionsKt.drop(list, 1))) {
            CompilerPhase compilerPhase = (CompilerPhase) pair.component1();
            CompilerPhase compilerPhase2 = (CompilerPhase) pair.component2();
            if (!(compilerPhase2 instanceof SameTypeCompilerPhase)) {
                phaserState = CompilerPhaseKt.changePhaserStateType(phaserState);
            }
            phaserState.getStickyPostconditions().addAll(compilerPhase.getStickyPostconditions());
            input2 = (Output) compilerPhase2.invoke(phaseConfig, phaserState, context2, input2);
        }
        return (Output) input2;
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
    public List<Pair<Integer, AbstractNamedCompilerPhase<Context, ?, ?>>> getNamedSubphases(int startDepth) {
        List<CompilerPhase<Context, Object, Object>> list = this.phases;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((CompilerPhase) it.next()).getNamedSubphases(startDepth));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.CompilerPhase
    public Set<Function1<Object, Unit>> getStickyPostconditions() {
        return ((CompilerPhase) CollectionsKt.last((List) this.phases)).getStickyPostconditions();
    }
}
