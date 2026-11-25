package org.jetbrains.kotlin.config;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CommonConfigurationKeys.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0004\"(\u0010\u0007\u001a\u00020\u0002*\u00020\b2\u0006\u0010\u0006\u001a\u00020\u00028F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"areExpectActualClassesStable", "", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getAreExpectActualClassesStable", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)Z", "isLibraryToSourceAnalysisEnabled", "value", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getLanguageVersionSettings", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "setLanguageVersionSettings", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "config"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CommonConfigurationKeysKt {
    public static final LanguageVersionSettings getLanguageVersionSettings(CompilerConfiguration compilerConfiguration) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Object obj = compilerConfiguration.get(CommonConfigurationKeys.LANGUAGE_VERSION_SETTINGS, LanguageVersionSettingsImpl.DEFAULT);
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        return (LanguageVersionSettings) obj;
    }

    public static final void setLanguageVersionSettings(CompilerConfiguration compilerConfiguration, LanguageVersionSettings value) {
        Intrinsics.checkNotNullParameter(compilerConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        compilerConfiguration.put(CommonConfigurationKeys.LANGUAGE_VERSION_SETTINGS, value);
    }

    public static final boolean isLibraryToSourceAnalysisEnabled(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "<this>");
        return ((Boolean) languageVersionSettings.getFlag(AnalysisFlags.getLibraryToSourceAnalysis())).booleanValue();
    }

    public static final boolean getAreExpectActualClassesStable(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "<this>");
        return ((Boolean) languageVersionSettings.getFlag(AnalysisFlags.getMuteExpectActualClassesWarning())).booleanValue() || languageVersionSettings.supportsFeature(LanguageFeature.ExpectActualClasses);
    }
}
