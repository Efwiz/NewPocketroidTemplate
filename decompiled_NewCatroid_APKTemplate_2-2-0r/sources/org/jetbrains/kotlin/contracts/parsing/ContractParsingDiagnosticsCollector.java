package org.jetbrains.kotlin.contracts.parsing;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: ContractParsingDiagnosticsCollector.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0010J\b\u0010\u0002\u001a\u00020\u0003H&J\u0018\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\n\u001a\u00020\u0003H&J\b\u0010\u000b\u001a\u00020\fH&J\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingDiagnosticsCollector;", "", "addFallbackErrorIfNecessary", "", "badDescription", CrashHianalyticsData.MESSAGE, "", "reportOn", "Lorg/jetbrains/kotlin/psi/KtElement;", "contractNotAllowed", "flushDiagnostics", "hasErrors", "", "unsupportedFeature", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "EMPTY", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ContractParsingDiagnosticsCollector {
    void addFallbackErrorIfNecessary();

    void badDescription(String message, KtElement reportOn);

    void contractNotAllowed(String message);

    void flushDiagnostics();

    boolean hasErrors();

    void unsupportedFeature(LanguageVersionSettings languageVersionSettings);

    /* JADX INFO: compiled from: ContractParsingDiagnosticsCollector.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\u000b\u001a\u00020\u0004H\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingDiagnosticsCollector$EMPTY;", "Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingDiagnosticsCollector;", "()V", "addFallbackErrorIfNecessary", "", "badDescription", CrashHianalyticsData.MESSAGE, "", "reportOn", "Lorg/jetbrains/kotlin/psi/KtElement;", "contractNotAllowed", "flushDiagnostics", "hasErrors", "", "unsupportedFeature", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EMPTY implements ContractParsingDiagnosticsCollector {
        public static final EMPTY INSTANCE = new EMPTY();

        @Override // org.jetbrains.kotlin.contracts.parsing.ContractParsingDiagnosticsCollector
        public void addFallbackErrorIfNecessary() {
        }

        @Override // org.jetbrains.kotlin.contracts.parsing.ContractParsingDiagnosticsCollector
        public void badDescription(String message, KtElement reportOn) {
            Intrinsics.checkNotNullParameter(message, "message");
            Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        }

        @Override // org.jetbrains.kotlin.contracts.parsing.ContractParsingDiagnosticsCollector
        public void contractNotAllowed(String message) {
            Intrinsics.checkNotNullParameter(message, "message");
        }

        @Override // org.jetbrains.kotlin.contracts.parsing.ContractParsingDiagnosticsCollector
        public void flushDiagnostics() {
        }

        @Override // org.jetbrains.kotlin.contracts.parsing.ContractParsingDiagnosticsCollector
        public boolean hasErrors() {
            return false;
        }

        @Override // org.jetbrains.kotlin.contracts.parsing.ContractParsingDiagnosticsCollector
        public void unsupportedFeature(LanguageVersionSettings languageVersionSettings) {
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        }

        private EMPTY() {
        }
    }
}
