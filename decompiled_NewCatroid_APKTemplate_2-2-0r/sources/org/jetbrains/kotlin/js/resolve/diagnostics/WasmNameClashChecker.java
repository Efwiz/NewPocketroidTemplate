package org.jetbrains.kotlin.js.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.js.naming.WasmNameSuggestion;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: JsNameClashChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/WasmNameClashChecker;", "Lorg/jetbrains/kotlin/js/resolve/diagnostics/AbstractNameClashChecker;", "nameSuggestion", "Lorg/jetbrains/kotlin/js/naming/WasmNameSuggestion;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "(Lorg/jetbrains/kotlin/js/naming/WasmNameSuggestion;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;)V", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmNameClashChecker extends AbstractNameClashChecker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WasmNameClashChecker(WasmNameSuggestion nameSuggestion, LanguageVersionSettings languageVersionSettings, KotlinTypeRefiner kotlinTypeRefiner) {
        super(nameSuggestion, languageVersionSettings, kotlinTypeRefiner);
        Intrinsics.checkNotNullParameter(nameSuggestion, "nameSuggestion");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
    }
}
