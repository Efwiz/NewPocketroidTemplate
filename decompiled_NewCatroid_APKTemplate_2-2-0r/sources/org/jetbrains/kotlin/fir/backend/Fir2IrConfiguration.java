package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.constant.EvaluatedConstTracker;
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.incremental.components.InlineConstTracker;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Fir2IrConfiguration.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010\u000e\u001a\u00020\u0007¢\u0006\u0002\u0010\u000fJ\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0007HÆ\u0003J\t\u0010 \u001a\u00020\tHÆ\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u000bHÆ\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\rHÆ\u0003J\t\u0010#\u001a\u00020\u0007HÆ\u0003JS\u0010$\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\b\b\u0002\u0010\u000e\u001a\u00020\u0007HÆ\u0001J\u0013\u0010%\u001a\u00020\u00072\b\u0010&\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010'\u001a\u00020(HÖ\u0001J\t\u0010)\u001a\u00020*HÖ\u0001R\u0011\u0010\u000e\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0011¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrConfiguration;", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "diagnosticReporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "linkViaSignatures", "", "evaluatedConstTracker", "Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "inlineConstTracker", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "expectActualTracker", "Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "allowNonCachedDeclarations", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;ZLorg/jetbrains/kotlin/constant/EvaluatedConstTracker;Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;Z)V", "getAllowNonCachedDeclarations", "()Z", "getDiagnosticReporter", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticReporter;", "getEvaluatedConstTracker", "()Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "getExpectActualTracker", "()Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "getInlineConstTracker", "()Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLinkViaSignatures", "component1", "component2", "component3", "component4", "component5", "component6", "component7", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Fir2IrConfiguration {
    private final boolean allowNonCachedDeclarations;
    private final DiagnosticReporter diagnosticReporter;
    private final EvaluatedConstTracker evaluatedConstTracker;
    private final ExpectActualTracker expectActualTracker;
    private final InlineConstTracker inlineConstTracker;
    private final LanguageVersionSettings languageVersionSettings;
    private final boolean linkViaSignatures;

    public static /* synthetic */ Fir2IrConfiguration copy$default(Fir2IrConfiguration fir2IrConfiguration, LanguageVersionSettings languageVersionSettings, DiagnosticReporter diagnosticReporter, boolean z, EvaluatedConstTracker evaluatedConstTracker, InlineConstTracker inlineConstTracker, ExpectActualTracker expectActualTracker, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            languageVersionSettings = fir2IrConfiguration.languageVersionSettings;
        }
        if ((i & 2) != 0) {
            diagnosticReporter = fir2IrConfiguration.diagnosticReporter;
        }
        DiagnosticReporter diagnosticReporter2 = diagnosticReporter;
        if ((i & 4) != 0) {
            z = fir2IrConfiguration.linkViaSignatures;
        }
        boolean z3 = z;
        if ((i & 8) != 0) {
            evaluatedConstTracker = fir2IrConfiguration.evaluatedConstTracker;
        }
        EvaluatedConstTracker evaluatedConstTracker2 = evaluatedConstTracker;
        if ((i & 16) != 0) {
            inlineConstTracker = fir2IrConfiguration.inlineConstTracker;
        }
        InlineConstTracker inlineConstTracker2 = inlineConstTracker;
        if ((i & 32) != 0) {
            expectActualTracker = fir2IrConfiguration.expectActualTracker;
        }
        ExpectActualTracker expectActualTracker2 = expectActualTracker;
        if ((i & 64) != 0) {
            z2 = fir2IrConfiguration.allowNonCachedDeclarations;
        }
        return fir2IrConfiguration.copy(languageVersionSettings, diagnosticReporter2, z3, evaluatedConstTracker2, inlineConstTracker2, expectActualTracker2, z2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final DiagnosticReporter getDiagnosticReporter() {
        return this.diagnosticReporter;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getLinkViaSignatures() {
        return this.linkViaSignatures;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final EvaluatedConstTracker getEvaluatedConstTracker() {
        return this.evaluatedConstTracker;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final InlineConstTracker getInlineConstTracker() {
        return this.inlineConstTracker;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final ExpectActualTracker getExpectActualTracker() {
        return this.expectActualTracker;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final boolean getAllowNonCachedDeclarations() {
        return this.allowNonCachedDeclarations;
    }

    public final Fir2IrConfiguration copy(LanguageVersionSettings languageVersionSettings, DiagnosticReporter diagnosticReporter, boolean linkViaSignatures, EvaluatedConstTracker evaluatedConstTracker, InlineConstTracker inlineConstTracker, ExpectActualTracker expectActualTracker, boolean allowNonCachedDeclarations) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(diagnosticReporter, "diagnosticReporter");
        Intrinsics.checkNotNullParameter(evaluatedConstTracker, "evaluatedConstTracker");
        return new Fir2IrConfiguration(languageVersionSettings, diagnosticReporter, linkViaSignatures, evaluatedConstTracker, inlineConstTracker, expectActualTracker, allowNonCachedDeclarations);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Fir2IrConfiguration)) {
            return false;
        }
        Fir2IrConfiguration fir2IrConfiguration = (Fir2IrConfiguration) other;
        return Intrinsics.areEqual(this.languageVersionSettings, fir2IrConfiguration.languageVersionSettings) && Intrinsics.areEqual(this.diagnosticReporter, fir2IrConfiguration.diagnosticReporter) && this.linkViaSignatures == fir2IrConfiguration.linkViaSignatures && Intrinsics.areEqual(this.evaluatedConstTracker, fir2IrConfiguration.evaluatedConstTracker) && Intrinsics.areEqual(this.inlineConstTracker, fir2IrConfiguration.inlineConstTracker) && Intrinsics.areEqual(this.expectActualTracker, fir2IrConfiguration.expectActualTracker) && this.allowNonCachedDeclarations == fir2IrConfiguration.allowNonCachedDeclarations;
    }

    public int hashCode() {
        int iHashCode = ((((((this.languageVersionSettings.hashCode() * 31) + this.diagnosticReporter.hashCode()) * 31) + Boolean.hashCode(this.linkViaSignatures)) * 31) + this.evaluatedConstTracker.hashCode()) * 31;
        InlineConstTracker inlineConstTracker = this.inlineConstTracker;
        int iHashCode2 = (iHashCode + (inlineConstTracker == null ? 0 : inlineConstTracker.hashCode())) * 31;
        ExpectActualTracker expectActualTracker = this.expectActualTracker;
        return ((iHashCode2 + (expectActualTracker != null ? expectActualTracker.hashCode() : 0)) * 31) + Boolean.hashCode(this.allowNonCachedDeclarations);
    }

    public String toString() {
        return "Fir2IrConfiguration(languageVersionSettings=" + this.languageVersionSettings + ", diagnosticReporter=" + this.diagnosticReporter + ", linkViaSignatures=" + this.linkViaSignatures + ", evaluatedConstTracker=" + this.evaluatedConstTracker + ", inlineConstTracker=" + this.inlineConstTracker + ", expectActualTracker=" + this.expectActualTracker + ", allowNonCachedDeclarations=" + this.allowNonCachedDeclarations + ')';
    }

    public Fir2IrConfiguration(LanguageVersionSettings languageVersionSettings, DiagnosticReporter diagnosticReporter, boolean z, EvaluatedConstTracker evaluatedConstTracker, InlineConstTracker inlineConstTracker, ExpectActualTracker expectActualTracker, boolean z2) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(diagnosticReporter, "diagnosticReporter");
        Intrinsics.checkNotNullParameter(evaluatedConstTracker, "evaluatedConstTracker");
        this.languageVersionSettings = languageVersionSettings;
        this.diagnosticReporter = diagnosticReporter;
        this.linkViaSignatures = z;
        this.evaluatedConstTracker = evaluatedConstTracker;
        this.inlineConstTracker = inlineConstTracker;
        this.expectActualTracker = expectActualTracker;
        this.allowNonCachedDeclarations = z2;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final DiagnosticReporter getDiagnosticReporter() {
        return this.diagnosticReporter;
    }

    public final boolean getLinkViaSignatures() {
        return this.linkViaSignatures;
    }

    public final EvaluatedConstTracker getEvaluatedConstTracker() {
        return this.evaluatedConstTracker;
    }

    public final InlineConstTracker getInlineConstTracker() {
        return this.inlineConstTracker;
    }

    public final ExpectActualTracker getExpectActualTracker() {
        return this.expectActualTracker;
    }

    public final boolean getAllowNonCachedDeclarations() {
        return this.allowNonCachedDeclarations;
    }
}
