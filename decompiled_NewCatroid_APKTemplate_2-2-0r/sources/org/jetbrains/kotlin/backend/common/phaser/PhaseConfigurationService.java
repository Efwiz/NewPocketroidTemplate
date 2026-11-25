package org.jetbrains.kotlin.backend.common.phaser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;

/* JADX INFO: compiled from: PhaseConfigurationService.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J \u0010\u0010\u001a\u00020\u00112\u0016\u0010\u0012\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0013j\u0002`\u0014H&J \u0010\u0015\u001a\u00020\u00032\u0016\u0010\u0012\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0013j\u0002`\u0014H&J \u0010\u0016\u001a\u00020\u00032\u0016\u0010\u0012\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0013j\u0002`\u0014H&J \u0010\u0017\u001a\u00020\u00032\u0016\u0010\u0012\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0013j\u0002`\u0014H&J \u0010\u0018\u001a\u00020\u00032\u0016\u0010\u0012\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0013j\u0002`\u0014H&J \u0010\u0019\u001a\u00020\u00032\u0016\u0010\u0012\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0013j\u0002`\u0014H&J \u0010\u001a\u001a\u00020\u00032\u0016\u0010\u0012\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0013j\u0002`\u0014H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0014\u0010\b\u001a\u0004\u0018\u00010\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u0004\u0018\u00010\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0012\u0010\u000e\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;", "", "checkConditions", "", "getCheckConditions", "()Z", "checkStickyConditions", "getCheckStickyConditions", "dumpOnlyFqName", "", "getDumpOnlyFqName", "()Ljava/lang/String;", "dumpToDirectory", "getDumpToDirectory", "needProfiling", "getNeedProfiling", "disable", "", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/common/phaser/AnyNamedPhase;", "isEnabled", "isVerbose", "shouldDumpStateAfter", "shouldDumpStateBefore", "shouldValidateStateAfter", "shouldValidateStateBefore", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PhaseConfigurationService {
    void disable(AbstractNamedCompilerPhase<?, ?, ?> phase);

    boolean getCheckConditions();

    boolean getCheckStickyConditions();

    String getDumpOnlyFqName();

    String getDumpToDirectory();

    boolean getNeedProfiling();

    boolean isEnabled(AbstractNamedCompilerPhase<?, ?, ?> phase);

    boolean isVerbose(AbstractNamedCompilerPhase<?, ?, ?> phase);

    boolean shouldDumpStateAfter(AbstractNamedCompilerPhase<?, ?, ?> phase);

    boolean shouldDumpStateBefore(AbstractNamedCompilerPhase<?, ?, ?> phase);

    boolean shouldValidateStateAfter(AbstractNamedCompilerPhase<?, ?, ?> phase);

    boolean shouldValidateStateBefore(AbstractNamedCompilerPhase<?, ?, ?> phase);
}
