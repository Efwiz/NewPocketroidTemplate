package org.jetbrains.kotlin.backend.common.phaser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: PhaseConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010#\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BÇ\u0002\b\u0017\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003\u0012$\b\u0002\u0010\u0004\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\u0005\u0012\u001e\b\u0002\u0010\t\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n\u0012\u001e\b\u0002\u0010\u000b\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n\u0012\u001e\b\u0002\u0010\f\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n\u0012\u001e\b\u0002\u0010\r\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0006\u0012\u001e\b\u0002\u0010\u0010\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n\u0012\u001e\b\u0002\u0010\u0011\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n\u0012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00060\n\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0014¢\u0006\u0002\u0010\u0017Bµ\u0002\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003\u0012$\b\u0002\u0010\u0004\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\u0005\u0012\u001e\b\u0002\u0010\t\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n\u0012\u001e\b\u0002\u0010\u000b\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n\u0012\u001e\b\u0002\u0010\f\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n\u0012\u001e\b\u0002\u0010\r\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0006\u0012\u001e\b\u0002\u0010\u0010\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n\u0012\u001e\b\u0002\u0010\u0011\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0014¢\u0006\u0002\u0010\u0018J \u0010(\u001a\u00020)2\u0016\u0010*\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\bH\u0016J\u001e\u0010+\u001a\u00020)2\u0016\u0010*\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\bJ \u0010,\u001a\u00020\u00142\u0016\u0010*\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\bH\u0016J \u0010-\u001a\u00020\u00142\u0016\u0010*\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\bH\u0016J\u000e\u0010.\u001a\u00020\u00062\u0006\u0010/\u001a\u00020\u0006J\u0006\u00100\u001a\u00020)J \u00101\u001a\u00020\u00142\u0016\u0010*\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\bH\u0016J \u00102\u001a\u00020\u00142\u0016\u0010*\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\bH\u0016J \u00103\u001a\u00020\u00142\u0016\u0010*\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\bH\u0016J \u00104\u001a\u00020\u00142\u0016\u0010*\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\bH\u0016J&\u00105\u001a\u00020)2\u0016\u0010*\u001a\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b2\u0006\u00106\u001a\u00020\u0014J\u0006\u00107\u001a\u000208R\u0014\u0010\u0015\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u0016\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u001a\u0010\u0002\u001a\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001dR'\u0010\u001f\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n8F¢\u0006\u0006\u001a\u0004\b \u0010!R$\u0010\"\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0#X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\t\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001aR*\u0010\u0004\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R'\u0010\r\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n¢\u0006\b\n\u0000\u001a\u0004\b%\u0010!R'\u0010\f\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n¢\u0006\b\n\u0000\u001a\u0004\b&\u0010!R$\u0010\u0011\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0010\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R'\u0010\u000b\u001a\u0018\u0012\u0014\u0012\u0012\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0007j\u0002`\b0\n¢\u0006\b\n\u0000\u001a\u0004\b'\u0010!¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigurationService;", "compoundPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;", "phases", "", "", "Lorg/jetbrains/kotlin/backend/common/phaser/AbstractNamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/common/phaser/AnyNamedPhase;", "initiallyEnabled", "", "verbose", "toDumpStateBefore", "toDumpStateAfter", "dumpToDirectory", "dumpOnlyFqName", "toValidateStateBefore", "toValidateStateAfter", "namesOfElementsExcludedFromDumping", "needProfiling", "", "checkConditions", "checkStickyConditions", "(Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;Ljava/util/Map;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;ZZZ)V", "(Lorg/jetbrains/kotlin/backend/common/phaser/CompilerPhase;Ljava/util/Map;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/util/Set;Ljava/lang/String;Ljava/lang/String;Ljava/util/Set;Ljava/util/Set;ZZZ)V", "getCheckConditions", "()Z", "getCheckStickyConditions", "getDumpOnlyFqName", "()Ljava/lang/String;", "getDumpToDirectory", CompilerOptions.ENABLED, "getEnabled", "()Ljava/util/Set;", "enabledMut", "", "getNeedProfiling", "getToDumpStateAfter", "getToDumpStateBefore", "getVerbose", "disable", "", TypedValues.CycleType.S_WAVE_PHASE, CommonCompilerArguments.ENABLE, "isEnabled", "isVerbose", "known", "name", SchemaSymbols.ATTVAL_LIST, "shouldDumpStateAfter", "shouldDumpStateBefore", "shouldValidateStateAfter", "shouldValidateStateBefore", PsiKeyword.SWITCH, "onOff", "toBuilder", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfigBuilder;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PhaseConfig implements PhaseConfigurationService {
    private final boolean checkConditions;
    private final boolean checkStickyConditions;
    private final CompilerPhase<?, ?, ?> compoundPhase;
    private final String dumpOnlyFqName;
    private final String dumpToDirectory;
    private final Set<AbstractNamedCompilerPhase<?, ?, ?>> enabledMut;
    private final Set<AbstractNamedCompilerPhase<?, ?, ?>> initiallyEnabled;
    private final boolean needProfiling;
    private final Map<String, AbstractNamedCompilerPhase<?, ?, ?>> phases;
    private final Set<AbstractNamedCompilerPhase<?, ?, ?>> toDumpStateAfter;
    private final Set<AbstractNamedCompilerPhase<?, ?, ?>> toDumpStateBefore;
    private final Set<AbstractNamedCompilerPhase<?, ?, ?>> toValidateStateAfter;
    private final Set<AbstractNamedCompilerPhase<?, ?, ?>> toValidateStateBefore;
    private final Set<AbstractNamedCompilerPhase<?, ?, ?>> verbose;

    /* JADX WARN: Multi-variable type inference failed */
    public PhaseConfig(CompilerPhase<?, ?, ?> compoundPhase, Map<String, ? extends AbstractNamedCompilerPhase<?, ?, ?>> phases, Set<? extends AbstractNamedCompilerPhase<?, ?, ?>> initiallyEnabled, Set<? extends AbstractNamedCompilerPhase<?, ?, ?>> verbose, Set<? extends AbstractNamedCompilerPhase<?, ?, ?>> toDumpStateBefore, Set<? extends AbstractNamedCompilerPhase<?, ?, ?>> toDumpStateAfter, String str, String str2, Set<? extends AbstractNamedCompilerPhase<?, ?, ?>> toValidateStateBefore, Set<? extends AbstractNamedCompilerPhase<?, ?, ?>> toValidateStateAfter, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(compoundPhase, "compoundPhase");
        Intrinsics.checkNotNullParameter(phases, "phases");
        Intrinsics.checkNotNullParameter(initiallyEnabled, "initiallyEnabled");
        Intrinsics.checkNotNullParameter(verbose, "verbose");
        Intrinsics.checkNotNullParameter(toDumpStateBefore, "toDumpStateBefore");
        Intrinsics.checkNotNullParameter(toDumpStateAfter, "toDumpStateAfter");
        Intrinsics.checkNotNullParameter(toValidateStateBefore, "toValidateStateBefore");
        Intrinsics.checkNotNullParameter(toValidateStateAfter, "toValidateStateAfter");
        this.compoundPhase = compoundPhase;
        this.phases = phases;
        this.initiallyEnabled = initiallyEnabled;
        this.verbose = verbose;
        this.toDumpStateBefore = toDumpStateBefore;
        this.toDumpStateAfter = toDumpStateAfter;
        this.dumpToDirectory = str;
        this.dumpOnlyFqName = str2;
        this.toValidateStateBefore = toValidateStateBefore;
        this.toValidateStateAfter = toValidateStateAfter;
        this.needProfiling = z;
        this.checkConditions = z2;
        this.checkStickyConditions = z3;
        this.enabledMut = CollectionsKt.toMutableSet(initiallyEnabled);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ PhaseConfig(CompilerPhase compilerPhase, Map map, Set set, Set set2, Set set3, Set set4, String str, String str2, Set set5, Set set6, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        Map phaseMap = (i & 2) != 0 ? PhaseConfigKt.toPhaseMap(compilerPhase) : map;
        this(compilerPhase, phaseMap, (i & 4) != 0 ? CollectionsKt.toSet(phaseMap.values()) : set, (i & 8) != 0 ? SetsKt.emptySet() : set2, (i & 16) != 0 ? SetsKt.emptySet() : set3, (i & 32) != 0 ? SetsKt.emptySet() : set4, (i & 64) != 0 ? null : str, (i & 128) == 0 ? str2 : null, (i & 256) != 0 ? SetsKt.emptySet() : set5, (i & 512) != 0 ? SetsKt.emptySet() : set6, (i & 1024) != 0 ? false : z, (i & 2048) != 0 ? false : z2, (i & 4096) == 0 ? z3 : false);
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

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ PhaseConfig(CompilerPhase compilerPhase, Map map, Set set, Set set2, Set set3, Set set4, String str, String str2, Set set5, Set set6, Set set7, boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        Map phaseMap = (i & 2) != 0 ? PhaseConfigKt.toPhaseMap(compilerPhase) : map;
        this(compilerPhase, phaseMap, (i & 4) != 0 ? CollectionsKt.toSet(phaseMap.values()) : set, (i & 8) != 0 ? SetsKt.emptySet() : set2, (i & 16) != 0 ? SetsKt.emptySet() : set3, (i & 32) != 0 ? SetsKt.emptySet() : set4, (i & 64) != 0 ? null : str, (i & 128) == 0 ? str2 : null, (i & 256) != 0 ? SetsKt.emptySet() : set5, (i & 512) != 0 ? SetsKt.emptySet() : set6, (i & 1024) != 0 ? SetsKt.emptySet() : set7, (i & 2048) != 0 ? false : z, (i & 4096) != 0 ? false : z2, (i & 8192) == 0 ? z3 : false);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Provided for binary compatibility")
    public /* synthetic */ PhaseConfig(CompilerPhase compoundPhase, Map phases, Set initiallyEnabled, Set verbose, Set toDumpStateBefore, Set toDumpStateAfter, String str, String str2, Set toValidateStateBefore, Set toValidateStateAfter, Set namesOfElementsExcludedFromDumping, boolean z, boolean z2, boolean z3) {
        this(compoundPhase, phases, initiallyEnabled, verbose, toDumpStateBefore, toDumpStateAfter, str, str2, toValidateStateBefore, toValidateStateAfter, z, z2, z3);
        Intrinsics.checkNotNullParameter(compoundPhase, "compoundPhase");
        Intrinsics.checkNotNullParameter(phases, "phases");
        Intrinsics.checkNotNullParameter(initiallyEnabled, "initiallyEnabled");
        Intrinsics.checkNotNullParameter(verbose, "verbose");
        Intrinsics.checkNotNullParameter(toDumpStateBefore, "toDumpStateBefore");
        Intrinsics.checkNotNullParameter(toDumpStateAfter, "toDumpStateAfter");
        Intrinsics.checkNotNullParameter(toValidateStateBefore, "toValidateStateBefore");
        Intrinsics.checkNotNullParameter(toValidateStateAfter, "toValidateStateAfter");
        Intrinsics.checkNotNullParameter(namesOfElementsExcludedFromDumping, "namesOfElementsExcludedFromDumping");
    }

    public final PhaseConfigBuilder toBuilder() {
        PhaseConfigBuilder phaseConfigBuilder = new PhaseConfigBuilder(this.compoundPhase);
        phaseConfigBuilder.getEnabled().addAll(this.initiallyEnabled);
        phaseConfigBuilder.getVerbose().addAll(this.verbose);
        phaseConfigBuilder.getToDumpStateBefore().addAll(this.toDumpStateBefore);
        phaseConfigBuilder.getToDumpStateAfter().addAll(this.toDumpStateAfter);
        phaseConfigBuilder.setDumpToDirectory(getDumpToDirectory());
        phaseConfigBuilder.setDumpOnlyFqName(getDumpOnlyFqName());
        phaseConfigBuilder.getToValidateStateBefore().addAll(this.toValidateStateBefore);
        phaseConfigBuilder.getToValidateStateAfter().addAll(this.toValidateStateAfter);
        phaseConfigBuilder.setNeedProfiling(getNeedProfiling());
        phaseConfigBuilder.setCheckConditions(getCheckConditions());
        phaseConfigBuilder.setCheckStickyConditions(getCheckStickyConditions());
        return phaseConfigBuilder;
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService
    public boolean isEnabled(AbstractNamedCompilerPhase<?, ?, ?> phase) {
        Intrinsics.checkNotNullParameter(phase, "phase");
        return getEnabled().contains(phase);
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService
    public boolean isVerbose(AbstractNamedCompilerPhase<?, ?, ?> phase) {
        Intrinsics.checkNotNullParameter(phase, "phase");
        return this.verbose.contains(phase);
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

    public final Set<AbstractNamedCompilerPhase<?, ?, ?>> getEnabled() {
        return this.enabledMut;
    }

    public final String known(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (this.phases.get(name2) != null) {
            return name2;
        }
        throw new IllegalStateException(("Unknown phase: " + name2 + ". Use -Xlist-phases to see the list of phases.").toString());
    }

    public final void list() {
        for (Pair pair : CompilerPhase.getNamedSubphases$default(this.compoundPhase, 0, 1, null)) {
            int iIntValue = ((Number) pair.component1()).intValue();
            AbstractNamedCompilerPhase abstractNamedCompilerPhase = (AbstractNamedCompilerPhase) pair.component2();
            String str = "";
            String str2 = !getEnabled().contains(abstractNamedCompilerPhase) ? " (Disabled)" : "";
            if (this.verbose.contains(abstractNamedCompilerPhase)) {
                str = " (Verbose)";
            }
            String str3 = String.format("%1$-50s %2$-50s %3$-10s", Arrays.copyOf(new Object[]{StringsKt.repeat("    ", iIntValue) + abstractNamedCompilerPhase.getName(), abstractNamedCompilerPhase.getDescription(), str2.concat(str)}, 3));
            Intrinsics.checkNotNullExpressionValue(str3, "format(...)");
            System.out.println((Object) str3);
        }
    }

    public final void enable(AbstractNamedCompilerPhase<?, ?, ?> phase) {
        Intrinsics.checkNotNullParameter(phase, "phase");
        this.enabledMut.add(phase);
    }

    @Override // org.jetbrains.kotlin.backend.common.phaser.PhaseConfigurationService
    public void disable(AbstractNamedCompilerPhase<?, ?, ?> phase) {
        Intrinsics.checkNotNullParameter(phase, "phase");
        this.enabledMut.remove(phase);
    }

    /* JADX INFO: renamed from: switch, reason: not valid java name */
    public final void m6113switch(AbstractNamedCompilerPhase<?, ?, ?> phase, boolean onOff) {
        Intrinsics.checkNotNullParameter(phase, "phase");
        if (onOff) {
            enable(phase);
        } else {
            disable(phase);
        }
    }
}
