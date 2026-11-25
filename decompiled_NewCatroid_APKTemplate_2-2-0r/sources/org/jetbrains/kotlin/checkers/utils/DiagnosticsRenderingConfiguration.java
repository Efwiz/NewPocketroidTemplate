package org.jetbrains.kotlin.checkers.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;

/* JADX INFO: compiled from: CheckerTestUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J5\u0010\u0015\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00052\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/checkers/utils/DiagnosticsRenderingConfiguration;", "", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "", "withNewInference", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "skipDebugInfoDiagnostics", "(Ljava/lang/String;ZLorg/jetbrains/kotlin/config/LanguageVersionSettings;Z)V", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getPlatform", "()Ljava/lang/String;", "getSkipDebugInfoDiagnostics", "()Z", "getWithNewInference", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DiagnosticsRenderingConfiguration {
    private final LanguageVersionSettings languageVersionSettings;
    private final String platform;
    private final boolean skipDebugInfoDiagnostics;
    private final boolean withNewInference;

    public static /* synthetic */ DiagnosticsRenderingConfiguration copy$default(DiagnosticsRenderingConfiguration diagnosticsRenderingConfiguration, String str, boolean z, LanguageVersionSettings languageVersionSettings, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = diagnosticsRenderingConfiguration.platform;
        }
        if ((i & 2) != 0) {
            z = diagnosticsRenderingConfiguration.withNewInference;
        }
        if ((i & 4) != 0) {
            languageVersionSettings = diagnosticsRenderingConfiguration.languageVersionSettings;
        }
        if ((i & 8) != 0) {
            z2 = diagnosticsRenderingConfiguration.skipDebugInfoDiagnostics;
        }
        return diagnosticsRenderingConfiguration.copy(str, z, languageVersionSettings, z2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getPlatform() {
        return this.platform;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getWithNewInference() {
        return this.withNewInference;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getSkipDebugInfoDiagnostics() {
        return this.skipDebugInfoDiagnostics;
    }

    public final DiagnosticsRenderingConfiguration copy(String platform, boolean withNewInference, LanguageVersionSettings languageVersionSettings, boolean skipDebugInfoDiagnostics) {
        return new DiagnosticsRenderingConfiguration(platform, withNewInference, languageVersionSettings, skipDebugInfoDiagnostics);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DiagnosticsRenderingConfiguration)) {
            return false;
        }
        DiagnosticsRenderingConfiguration diagnosticsRenderingConfiguration = (DiagnosticsRenderingConfiguration) other;
        return Intrinsics.areEqual(this.platform, diagnosticsRenderingConfiguration.platform) && this.withNewInference == diagnosticsRenderingConfiguration.withNewInference && Intrinsics.areEqual(this.languageVersionSettings, diagnosticsRenderingConfiguration.languageVersionSettings) && this.skipDebugInfoDiagnostics == diagnosticsRenderingConfiguration.skipDebugInfoDiagnostics;
    }

    public int hashCode() {
        String str = this.platform;
        int iHashCode = (((str == null ? 0 : str.hashCode()) * 31) + Boolean.hashCode(this.withNewInference)) * 31;
        LanguageVersionSettings languageVersionSettings = this.languageVersionSettings;
        return ((iHashCode + (languageVersionSettings != null ? languageVersionSettings.hashCode() : 0)) * 31) + Boolean.hashCode(this.skipDebugInfoDiagnostics);
    }

    public String toString() {
        return "DiagnosticsRenderingConfiguration(platform=" + this.platform + ", withNewInference=" + this.withNewInference + ", languageVersionSettings=" + this.languageVersionSettings + ", skipDebugInfoDiagnostics=" + this.skipDebugInfoDiagnostics + ')';
    }

    public DiagnosticsRenderingConfiguration(String str, boolean z, LanguageVersionSettings languageVersionSettings, boolean z2) {
        this.platform = str;
        this.withNewInference = z;
        this.languageVersionSettings = languageVersionSettings;
        this.skipDebugInfoDiagnostics = z2;
    }

    public /* synthetic */ DiagnosticsRenderingConfiguration(String str, boolean z, LanguageVersionSettings languageVersionSettings, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, z, languageVersionSettings, (i & 8) != 0 ? false : z2);
    }

    public final String getPlatform() {
        return this.platform;
    }

    public final boolean getWithNewInference() {
        return this.withNewInference;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final boolean getSkipDebugInfoDiagnostics() {
        return this.skipDebugInfoDiagnostics;
    }
}
