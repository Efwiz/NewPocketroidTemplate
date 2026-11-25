package org.jetbrains.kotlin.backend.common.phaser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: compiled from: FlexiblePhaseConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010#\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001Bw\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0004\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000e¢\u0006\u0002\u0010\u0011J \u0010\u001b\u001a\u00020\u001c2\u0016\u0010\u001d\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001ej\u0002`\u001fH\u0016J \u0010 \u001a\u00020\u000e2\u0016\u0010\u001d\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001ej\u0002`\u001fH\u0016J \u0010!\u001a\u00020\u000e2\u0016\u0010\u001d\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001ej\u0002`\u001fH\u0016J \u0010\"\u001a\u00020\u000e2\u0016\u0010\u001d\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001ej\u0002`\u001fH\u0016J \u0010#\u001a\u00020\u000e2\u0016\u0010\u001d\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001ej\u0002`\u001fH\u0016J \u0010$\u001a\u00020\u000e2\u0016\u0010\u001d\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001ej\u0002`\u001fH\u0016J \u0010%\u001a\u00020\u000e2\u0016\u0010\u001d\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001ej\u0002`\u001fH\u0016R\u0014\u0010\u000f\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0010\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0016\u0010\u000b\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0013R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/FlexiblePhaseConfig;", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;", CompilerOptions.DISABLED, "", "", "verbose", "toDumpStateBefore", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseSet;", "toDumpStateAfter", "toValidateStateBefore", "toValidateStateAfter", "dumpToDirectory", "dumpOnlyFqName", "needProfiling", "", "checkConditions", "checkStickyConditions", "(Ljava/util/Set;Ljava/util/Set;Lorg/jetbrains/kotlin/backend/common/phaser/PhaseSet;Lorg/jetbrains/kotlin/backend/common/phaser/PhaseSet;Lorg/jetbrains/kotlin/backend/common/phaser/PhaseSet;Lorg/jetbrains/kotlin/backend/common/phaser/PhaseSet;Ljava/lang/String;Ljava/lang/String;ZZZ)V", "getCheckConditions", "()Z", "getCheckStickyConditions", "disabledMut", "", "getDumpOnlyFqName", "()Ljava/lang/String;", "getDumpToDirectory", "getNeedProfiling", "disable", "", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/common/phaser/AnyNamedPhase;", "isEnabled", "isVerbose", "shouldDumpStateAfter", "shouldDumpStateBefore", "shouldValidateStateAfter", "shouldValidateStateBefore", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FlexiblePhaseConfig implements PhaseConfigurationService {
    private final boolean checkConditions;
    private final boolean checkStickyConditions;
    private final Set<String> disabledMut;
    private final String dumpOnlyFqName;
    private final String dumpToDirectory;
    private final boolean needProfiling;
    private final PhaseSet toDumpStateAfter;
    private final PhaseSet toDumpStateBefore;
    private final PhaseSet toValidateStateAfter;
    private final PhaseSet toValidateStateBefore;
    private final Set<String> verbose;

    public FlexiblePhaseConfig(Set<String> disabled, Set<String> verbose, PhaseSet toDumpStateBefore, PhaseSet toDumpStateAfter, PhaseSet toValidateStateBefore, PhaseSet toValidateStateAfter, String str, String str2, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(disabled, "disabled");
        Intrinsics.checkNotNullParameter(verbose, "verbose");
        Intrinsics.checkNotNullParameter(toDumpStateBefore, "toDumpStateBefore");
        Intrinsics.checkNotNullParameter(toDumpStateAfter, "toDumpStateAfter");
        Intrinsics.checkNotNullParameter(toValidateStateBefore, "toValidateStateBefore");
        Intrinsics.checkNotNullParameter(toValidateStateAfter, "toValidateStateAfter");
        this.verbose = verbose;
        this.toDumpStateBefore = toDumpStateBefore;
        this.toDumpStateAfter = toDumpStateAfter;
        this.toValidateStateBefore = toValidateStateBefore;
        this.toValidateStateAfter = toValidateStateAfter;
        this.dumpToDirectory = str;
        this.dumpOnlyFqName = str2;
        this.needProfiling = z;
        this.checkConditions = z2;
        this.checkStickyConditions = z3;
        this.disabledMut = CollectionsKt.toMutableSet(disabled);
    }

    public /* synthetic */ FlexiblePhaseConfig(Set set, Set set2, PhaseSet phaseSet, PhaseSet phaseSet2, PhaseSet phaseSet3, PhaseSet phaseSet4, String str, String str2, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(set, set2, phaseSet, phaseSet2, phaseSet3, phaseSet4, (i & 64) != 0 ? null : str, (i & 128) != 0 ? null : str2, (i & 256) != 0 ? false : z, (i & 512) != 0 ? false : z2, (i & 1024) != 0 ? false : z3);
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService
    public String getDumpToDirectory() {
        return this.dumpToDirectory;
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService
    public String getDumpOnlyFqName() {
        return this.dumpOnlyFqName;
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService
    public boolean getNeedProfiling() {
        return this.needProfiling;
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService
    public boolean getCheckConditions() {
        return this.checkConditions;
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService
    public boolean getCheckStickyConditions() {
        return this.checkStickyConditions;
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService
    public boolean isEnabled(AbstractNamedCompilerPhase<?, ?, ?> phase) {
        Intrinsics.checkNotNullParameter(phase, "phase");
        return !this.disabledMut.contains(phase.getName());
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService
    public boolean isVerbose(AbstractNamedCompilerPhase<?, ?, ?> phase) {
        Intrinsics.checkNotNullParameter(phase, "phase");
        return this.verbose.contains(phase.getName());
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService
    public void disable(AbstractNamedCompilerPhase<?, ?, ?> phase) {
        Intrinsics.checkNotNullParameter(phase, "phase");
        this.disabledMut.add(phase.getName());
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService
    public boolean shouldDumpStateBefore(AbstractNamedCompilerPhase<?, ?, ?> phase) {
        Intrinsics.checkNotNullParameter(phase, "phase");
        return this.toDumpStateBefore.contains(phase);
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService
    public boolean shouldDumpStateAfter(AbstractNamedCompilerPhase<?, ?, ?> phase) {
        Intrinsics.checkNotNullParameter(phase, "phase");
        return this.toDumpStateAfter.contains(phase);
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService
    public boolean shouldValidateStateBefore(AbstractNamedCompilerPhase<?, ?, ?> phase) {
        Intrinsics.checkNotNullParameter(phase, "phase");
        return this.toValidateStateBefore.contains(phase);
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService
    public boolean shouldValidateStateAfter(AbstractNamedCompilerPhase<?, ?, ?> phase) {
        Intrinsics.checkNotNullParameter(phase, "phase");
        return this.toValidateStateAfter.contains(phase);
    }
}
