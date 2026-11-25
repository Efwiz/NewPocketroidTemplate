package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;

/* JADX INFO: compiled from: CompilerDeserializationConfiguration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0010\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\bR\u0011\u0010\u000e\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\bR\u0011\u0010\u0010\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\bR\u0011\u0010\u0012\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\bR\u0011\u0010\u0014\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/CompilerDeserializationConfiguration;", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationConfiguration;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "allowUnstableDependencies", "", "getAllowUnstableDependencies", "()Z", "isJvmPackageNameSupported", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "readDeserializedContracts", "getReadDeserializedContracts", "reportErrorsOnPreReleaseDependencies", "getReportErrorsOnPreReleaseDependencies", "skipMetadataVersionCheck", "getSkipMetadataVersionCheck", "skipPrereleaseCheck", "getSkipPrereleaseCheck", "typeAliasesAllowed", "getTypeAliasesAllowed", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class CompilerDeserializationConfiguration implements DeserializationConfiguration {
    private final boolean allowUnstableDependencies;
    private final boolean isJvmPackageNameSupported;
    private final LanguageVersionSettings languageVersionSettings;
    private final boolean readDeserializedContracts;
    private final boolean reportErrorsOnPreReleaseDependencies;
    private final boolean skipMetadataVersionCheck;
    private final boolean skipPrereleaseCheck;
    private final boolean typeAliasesAllowed;

    public CompilerDeserializationConfiguration(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.languageVersionSettings = languageVersionSettings;
        this.skipMetadataVersionCheck = ((Boolean) languageVersionSettings.getFlag(AnalysisFlags.getSkipMetadataVersionCheck())).booleanValue();
        boolean zBooleanValue = ((Boolean) languageVersionSettings.getFlag(AnalysisFlags.getSkipPrereleaseCheck())).booleanValue();
        this.skipPrereleaseCheck = zBooleanValue;
        this.reportErrorsOnPreReleaseDependencies = (zBooleanValue || languageVersionSettings.isPreRelease()) ? false : true;
        this.allowUnstableDependencies = ((Boolean) languageVersionSettings.getFlag(AnalysisFlags.getAllowUnstableDependencies())).booleanValue();
        this.typeAliasesAllowed = languageVersionSettings.supportsFeature(LanguageFeature.TypeAliases);
        this.isJvmPackageNameSupported = languageVersionSettings.supportsFeature(LanguageFeature.JvmPackageName);
        this.readDeserializedContracts = languageVersionSettings.supportsFeature(LanguageFeature.ReadDeserializedContracts);
    }

    protected final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration
    public final boolean getSkipMetadataVersionCheck() {
        return this.skipMetadataVersionCheck;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration
    public final boolean getSkipPrereleaseCheck() {
        return this.skipPrereleaseCheck;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration
    public final boolean getReportErrorsOnPreReleaseDependencies() {
        return this.reportErrorsOnPreReleaseDependencies;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration
    public final boolean getAllowUnstableDependencies() {
        return this.allowUnstableDependencies;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration
    public final boolean getTypeAliasesAllowed() {
        return this.typeAliasesAllowed;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration
    /* JADX INFO: renamed from: isJvmPackageNameSupported, reason: from getter */
    public final boolean getIsJvmPackageNameSupported() {
        return this.isJvmPackageNameSupported;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration
    public final boolean getReadDeserializedContracts() {
        return this.readDeserializedContracts;
    }
}
