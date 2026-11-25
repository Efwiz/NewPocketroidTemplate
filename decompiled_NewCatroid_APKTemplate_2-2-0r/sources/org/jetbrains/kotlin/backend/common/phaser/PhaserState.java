package org.jetbrains.kotlin.backend.common.phaser;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: compiled from: CompilerPhase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B_\u0012\u001e\b\u0002\u0010\u0003\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005j\u0002`\u00060\u0004\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012$\b\u0002\u0010\n\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00028\u0000`\r0\u0004¢\u0006\u0002\u0010\u000eJ\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000R'\u0010\u0003\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005j\u0002`\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\t\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0012\"\u0004\b\u0016\u0010\u0014R-\u0010\n\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00028\u0000`\r0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/PhaserState;", "Data", "", "alreadyDone", "", "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/common/phaser/AnyNamedPhase;", ConfigConstants.CONFIG_KEY_DEPTH, "", "phaseCount", "stickyPostconditions", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/backend/common/phaser/Checker;", "(Ljava/util/Set;IILjava/util/Set;)V", "getAlreadyDone", "()Ljava/util/Set;", "getDepth", "()I", "setDepth", "(I)V", "getPhaseCount", "setPhaseCount", "getStickyPostconditions", "copyOf", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PhaserState<Data> {
    private final Set<AbstractNamedCompilerPhase<?, ?, ?>> alreadyDone;
    private int depth;
    private int phaseCount;
    private final Set<Function1<Data, Unit>> stickyPostconditions;

    public PhaserState() {
        this(null, 0, 0, null, 15, null);
    }

    public PhaserState(Set<AbstractNamedCompilerPhase<?, ?, ?>> alreadyDone, int i, int i2, Set<Function1<Data, Unit>> stickyPostconditions) {
        Intrinsics.checkNotNullParameter(alreadyDone, "alreadyDone");
        Intrinsics.checkNotNullParameter(stickyPostconditions, "stickyPostconditions");
        this.alreadyDone = alreadyDone;
        this.depth = i;
        this.phaseCount = i2;
        this.stickyPostconditions = stickyPostconditions;
    }

    public /* synthetic */ PhaserState(LinkedHashSet linkedHashSet, int i, int i2, LinkedHashSet linkedHashSet2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? new LinkedHashSet() : linkedHashSet, (i3 & 2) != 0 ? 0 : i, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) != 0 ? new LinkedHashSet() : linkedHashSet2);
    }

    public final Set<AbstractNamedCompilerPhase<?, ?, ?>> getAlreadyDone() {
        return this.alreadyDone;
    }

    public final int getDepth() {
        return this.depth;
    }

    public final void setDepth(int i) {
        this.depth = i;
    }

    public final int getPhaseCount() {
        return this.phaseCount;
    }

    public final void setPhaseCount(int i) {
        this.phaseCount = i;
    }

    public final Set<Function1<Data, Unit>> getStickyPostconditions() {
        return this.stickyPostconditions;
    }

    public final PhaserState<Data> copyOf() {
        return new PhaserState<>(CollectionsKt.toMutableSet(this.alreadyDone), this.depth, this.phaseCount, this.stickyPostconditions);
    }
}
