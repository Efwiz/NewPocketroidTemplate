package org.jetbrains.kotlin.backend.common.phaser;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.backend.common.LoggingContext;

/* JADX INFO: compiled from: CompilerPhase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u0002*\u0004\b\u0001\u0010\u0003*\u0004\b\u0002\u0010\u00042\u00020\u0005J2\u0010\r\u001a\"\u0012\u001e\u0012\u001c\u0012\u0004\u0012\u00020\u0010\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00110\u000f0\u000e2\b\b\u0002\u0010\u0012\u001a\u00020\u0010H\u0016J3\u0010\u0013\u001a\u00028\u00022\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00010\u00172\u0006\u0010\u0018\u001a\u00028\u00002\u0006\u0010\u0019\u001a\u00028\u0001H&¢\u0006\u0002\u0010\u001aR0\u0010\u0006\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00020\t0\bj\b\u0012\u0004\u0012\u00028\u0002`\n0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;", "Context", "Lorg/jetbrains/kotlin/backend/common/LoggingContext;", "Input", "Output", "", "stickyPostconditions", "", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/backend/common/phaser/Checker;", "getStickyPostconditions", "()Ljava/util/Set;", "getNamedSubphases", "", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "startDepth", "invoke", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;", "phaserState", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;", "context", "input", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;Lorg/jetbrains/kotlin/backend/common/LoggingContext;Ljava/lang/Object;)Ljava/lang/Object;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CompilerPhase<Context extends LoggingContext, Input, Output> {
    Output invoke(PhaseConfigurationService phaseConfig, PhaserState<Input> phaserState, Context context, Input input);

    static /* synthetic */ List getNamedSubphases$default(CompilerPhase compilerPhase, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getNamedSubphases");
        }
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return compilerPhase.getNamedSubphases(i);
    }

    default List<Pair<Integer, AbstractNamedCompilerPhase<Context, ?, ?>>> getNamedSubphases(int startDepth) {
        return CollectionsKt.emptyList();
    }

    default Set<Function1<Output, Unit>> getStickyPostconditions() {
        return SetsKt.emptySet();
    }
}
