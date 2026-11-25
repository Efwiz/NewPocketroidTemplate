package org.jetbrains.kotlin.backend.common.phaser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CompilerPhase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005j\u0002`\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\u0019\u0010\u0015\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005j\u0002`\u0006HÆ\u0003J\t\u0010\u0016\u001a\u00020\bHÆ\u0003J\t\u0010\u0017\u001a\u00020\nHÆ\u0003JA\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0018\b\u0002\u0010\u0004\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005j\u0002`\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nHÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\bHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR!\u0010\u0004\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0005j\u0002`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/ActionState;", "", "config", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/common/phaser/AnyNamedPhase;", "phaseCount", "", "beforeOrAfter", "Lorg/jetbrains/kotlin/backend/common/phaser/BeforeOrAfter;", "(Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;ILorg/jetbrains/kotlin/backend/common/phaser/BeforeOrAfter;)V", "getBeforeOrAfter", "()Lorg/jetbrains/kotlin/backend/common/phaser/BeforeOrAfter;", "getConfig", "()Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;", "getPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "getPhaseCount", "()I", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ActionState {
    private final BeforeOrAfter beforeOrAfter;
    private final PhaseConfigurationService config;
    private final AbstractNamedCompilerPhase<?, ?, ?> phase;
    private final int phaseCount;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ActionState copy$default(ActionState actionState, PhaseConfigurationService phaseConfigurationService, AbstractNamedCompilerPhase abstractNamedCompilerPhase, int i, BeforeOrAfter beforeOrAfter, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            phaseConfigurationService = actionState.config;
        }
        if ((i2 & 2) != 0) {
            abstractNamedCompilerPhase = actionState.phase;
        }
        if ((i2 & 4) != 0) {
            i = actionState.phaseCount;
        }
        if ((i2 & 8) != 0) {
            beforeOrAfter = actionState.beforeOrAfter;
        }
        return actionState.copy(phaseConfigurationService, abstractNamedCompilerPhase, i, beforeOrAfter);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final PhaseConfigurationService getConfig() {
        return this.config;
    }

    public final AbstractNamedCompilerPhase<?, ?, ?> component2() {
        return this.phase;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getPhaseCount() {
        return this.phaseCount;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final BeforeOrAfter getBeforeOrAfter() {
        return this.beforeOrAfter;
    }

    public final ActionState copy(PhaseConfigurationService config, AbstractNamedCompilerPhase<?, ?, ?> phase, int phaseCount, BeforeOrAfter beforeOrAfter) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(phase, "phase");
        Intrinsics.checkNotNullParameter(beforeOrAfter, "beforeOrAfter");
        return new ActionState(config, phase, phaseCount, beforeOrAfter);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ActionState)) {
            return false;
        }
        ActionState actionState = (ActionState) other;
        return Intrinsics.areEqual(this.config, actionState.config) && Intrinsics.areEqual(this.phase, actionState.phase) && this.phaseCount == actionState.phaseCount && this.beforeOrAfter == actionState.beforeOrAfter;
    }

    public int hashCode() {
        return (((((this.config.hashCode() * 31) + this.phase.hashCode()) * 31) + Integer.hashCode(this.phaseCount)) * 31) + this.beforeOrAfter.hashCode();
    }

    public String toString() {
        return "ActionState(config=" + this.config + ", phase=" + this.phase + ", phaseCount=" + this.phaseCount + ", beforeOrAfter=" + this.beforeOrAfter + ')';
    }

    public ActionState(PhaseConfigurationService config, AbstractNamedCompilerPhase<?, ?, ?> phase, int i, BeforeOrAfter beforeOrAfter) {
        Intrinsics.checkNotNullParameter(config, "config");
        Intrinsics.checkNotNullParameter(phase, "phase");
        Intrinsics.checkNotNullParameter(beforeOrAfter, "beforeOrAfter");
        this.config = config;
        this.phase = phase;
        this.phaseCount = i;
        this.beforeOrAfter = beforeOrAfter;
    }

    public final PhaseConfigurationService getConfig() {
        return this.config;
    }

    public final AbstractNamedCompilerPhase<?, ?, ?> getPhase() {
        return this.phase;
    }

    public final int getPhaseCount() {
        return this.phaseCount;
    }

    public final BeforeOrAfter getBeforeOrAfter() {
        return this.beforeOrAfter;
    }
}
