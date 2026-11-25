package org.jetbrains.kotlin.backend.common.phaser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: FlexiblePhaseConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\n\u000bB\u0007\b\u0004¢\u0006\u0002\u0010\u0002J!\u0010\u0003\u001a\u00020\u00042\u0016\u0010\u0005\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006j\u0002`\u0007H¦\u0002J\u0011\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H¦\u0002\u0082\u0001\u0002\f\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/PhaseSet;", "", "()V", "contains", "", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/common/phaser/AnyNamedPhase;", "plus", "phaseSet", "ALL", "Enum", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseSet$ALL;", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseSet$Enum;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PhaseSet {
    public /* synthetic */ PhaseSet(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract boolean contains(AbstractNamedCompilerPhase<?, ?, ?> phase);

    public abstract PhaseSet plus(PhaseSet phaseSet);

    private PhaseSet() {
    }

    /* JADX INFO: compiled from: FlexiblePhaseConfig.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J!\u0010\b\u001a\u00020\t2\u0016\u0010\n\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u000bj\u0002`\fH\u0096\u0002J\u0011\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u0001H\u0096\u0002R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/PhaseSet$Enum;", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseSet;", "phases", "", "", "(Ljava/util/Set;)V", "getPhases", "()Ljava/util/Set;", "contains", "", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/common/phaser/AnyNamedPhase;", "plus", "phaseSet", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Enum extends PhaseSet {
        private final Set<String> phases;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Enum(Set<String> phases) {
            super(null);
            Intrinsics.checkNotNullParameter(phases, "phases");
            this.phases = phases;
        }

        public final Set<String> getPhases() {
            return this.phases;
        }

        @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseSet
        public boolean contains(AbstractNamedCompilerPhase<?, ?, ?> phase) {
            Intrinsics.checkNotNullParameter(phase, "phase");
            return this.phases.contains(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(phase.getName()));
        }

        @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseSet
        public PhaseSet plus(PhaseSet phaseSet) {
            Intrinsics.checkNotNullParameter(phaseSet, "phaseSet");
            if (Intrinsics.areEqual(phaseSet, ALL.INSTANCE)) {
                return ALL.INSTANCE;
            }
            if (phaseSet instanceof Enum) {
                return new Enum(SetsKt.plus((Set) this.phases, (Iterable) ((Enum) phaseSet).phases));
            }
            throw new NoWhenBranchMatchedException();
        }
    }

    /* JADX INFO: compiled from: FlexiblePhaseConfig.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J!\u0010\u0003\u001a\u00020\u00042\u0016\u0010\u0005\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006j\u0002`\u0007H\u0096\u0002J\u0011\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u0001H\u0096\u0002¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/PhaseSet$ALL;", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseSet;", "()V", "contains", "", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/common/phaser/AnyNamedPhase;", "plus", "phaseSet", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ALL extends PhaseSet {
        public static final ALL INSTANCE = new ALL();

        @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseSet
        public boolean contains(AbstractNamedCompilerPhase<?, ?, ?> phase) {
            Intrinsics.checkNotNullParameter(phase, "phase");
            return true;
        }

        private ALL() {
            super(null);
        }

        @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseSet
        public PhaseSet plus(PhaseSet phaseSet) {
            Intrinsics.checkNotNullParameter(phaseSet, "phaseSet");
            return INSTANCE;
        }
    }
}
