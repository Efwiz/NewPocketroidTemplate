package org.jetbrains.kotlin.backend.common.phaser;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: compiled from: PhaseConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010*\u001a\u00020+R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001a\u0010\u0002\u001a\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0013R'\u0010\u0017\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0019j\u0002`\u001a0\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\b\"\u0004\b\u001f\u0010\nR'\u0010 \u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0019j\u0002`\u001a0\u0018¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001cR'\u0010\"\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0019j\u0002`\u001a0\u0018¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001cR'\u0010$\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0019j\u0002`\u001a0\u0018¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001cR'\u0010&\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0019j\u0002`\u001a0\u0018¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001cR'\u0010(\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0019j\u0002`\u001a0\u0018¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001c¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigBuilder;", "", "compoundPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;", "(Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;)V", "checkConditions", "", "getCheckConditions", "()Z", "setCheckConditions", "(Z)V", "checkStickyConditions", "getCheckStickyConditions", "setCheckStickyConditions", "dumpOnlyFqName", "", "getDumpOnlyFqName", "()Ljava/lang/String;", "setDumpOnlyFqName", "(Ljava/lang/String;)V", "dumpToDirectory", "getDumpToDirectory", "setDumpToDirectory", CompilerOptions.ENABLED, "", "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/common/phaser/AnyNamedPhase;", "getEnabled", "()Ljava/util/Set;", "needProfiling", "getNeedProfiling", "setNeedProfiling", "toDumpStateAfter", "getToDumpStateAfter", "toDumpStateBefore", "getToDumpStateBefore", "toValidateStateAfter", "getToValidateStateAfter", "toValidateStateBefore", "getToValidateStateBefore", "verbose", "getVerbose", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PhaseConfigBuilder {
    private boolean checkConditions;
    private boolean checkStickyConditions;
    private final CompilerPhase<?, ?, ?> compoundPhase;
    private String dumpOnlyFqName;
    private String dumpToDirectory;
    private final Set<AbstractNamedCompilerPhase<?, ?, ?>> enabled;
    private boolean needProfiling;
    private final Set<AbstractNamedCompilerPhase<?, ?, ?>> toDumpStateAfter;
    private final Set<AbstractNamedCompilerPhase<?, ?, ?>> toDumpStateBefore;
    private final Set<AbstractNamedCompilerPhase<?, ?, ?>> toValidateStateAfter;
    private final Set<AbstractNamedCompilerPhase<?, ?, ?>> toValidateStateBefore;
    private final Set<AbstractNamedCompilerPhase<?, ?, ?>> verbose;

    public PhaseConfigBuilder(CompilerPhase<?, ?, ?> compoundPhase) {
        Intrinsics.checkNotNullParameter(compoundPhase, "compoundPhase");
        this.compoundPhase = compoundPhase;
        this.enabled = new LinkedHashSet();
        this.verbose = new LinkedHashSet();
        this.toDumpStateBefore = new LinkedHashSet();
        this.toDumpStateAfter = new LinkedHashSet();
        this.toValidateStateBefore = new LinkedHashSet();
        this.toValidateStateAfter = new LinkedHashSet();
    }

    public final Set<AbstractNamedCompilerPhase<?, ?, ?>> getEnabled() {
        return this.enabled;
    }

    public final Set<AbstractNamedCompilerPhase<?, ?, ?>> getVerbose() {
        return this.verbose;
    }

    public final Set<AbstractNamedCompilerPhase<?, ?, ?>> getToDumpStateBefore() {
        return this.toDumpStateBefore;
    }

    public final Set<AbstractNamedCompilerPhase<?, ?, ?>> getToDumpStateAfter() {
        return this.toDumpStateAfter;
    }

    public final String getDumpToDirectory() {
        return this.dumpToDirectory;
    }

    public final void setDumpToDirectory(String str) {
        this.dumpToDirectory = str;
    }

    public final String getDumpOnlyFqName() {
        return this.dumpOnlyFqName;
    }

    public final void setDumpOnlyFqName(String str) {
        this.dumpOnlyFqName = str;
    }

    public final Set<AbstractNamedCompilerPhase<?, ?, ?>> getToValidateStateBefore() {
        return this.toValidateStateBefore;
    }

    public final Set<AbstractNamedCompilerPhase<?, ?, ?>> getToValidateStateAfter() {
        return this.toValidateStateAfter;
    }

    public final boolean getNeedProfiling() {
        return this.needProfiling;
    }

    public final void setNeedProfiling(boolean z) {
        this.needProfiling = z;
    }

    public final boolean getCheckConditions() {
        return this.checkConditions;
    }

    public final void setCheckConditions(boolean z) {
        this.checkConditions = z;
    }

    public final boolean getCheckStickyConditions() {
        return this.checkStickyConditions;
    }

    public final void setCheckStickyConditions(boolean z) {
        this.checkStickyConditions = z;
    }

    public final PhaseConfig build() {
        CompilerPhase<?, ?, ?> compilerPhase = this.compoundPhase;
        return new PhaseConfig(compilerPhase, PhaseConfigKt.toPhaseMap(compilerPhase), this.enabled, this.verbose, this.toDumpStateBefore, this.toDumpStateAfter, this.dumpToDirectory, this.dumpOnlyFqName, this.toValidateStateBefore, this.toValidateStateAfter, this.needProfiling, this.checkConditions, this.checkStickyConditions);
    }
}
