package org.jetbrains.kotlin.cfg;

import kotlin.Metadata;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.incremental.components.EnumWhenTracker;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ControlFlowInformationProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\bJ\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\u0012\u0010\u0005\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProvider;", "", "checkDeclaration", "", "checkForLocalClassOrObjectMode", "checkFunction", "expectedReturnType", "Lorg/jetbrains/kotlin/types/KotlinType;", "Factory", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ControlFlowInformationProvider {

    /* JADX INFO: compiled from: ControlFlowInformationProvider.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J0\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProvider$Factory;", "", "createControlFlowInformationProvider", "Lorg/jetbrains/kotlin/cfg/ControlFlowInformationProvider;", "declaration", "Lorg/jetbrains/kotlin/psi/KtElement;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "diagnosticSuppressor", "Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor;", "enumWhenTracker", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Factory {
        ControlFlowInformationProvider createControlFlowInformationProvider(KtElement declaration, BindingTrace trace, LanguageVersionSettings languageVersionSettings, PlatformDiagnosticSuppressor diagnosticSuppressor, EnumWhenTracker enumWhenTracker);
    }

    void checkDeclaration();

    void checkForLocalClassOrObjectMode();

    void checkFunction(KotlinType expectedReturnType);
}
