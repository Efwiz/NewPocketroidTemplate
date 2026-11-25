package org.jetbrains.kotlin.js.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.naming.JsNameSuggestion;

/* JADX INFO: compiled from: JsNameCharsChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsNameCharsChecker;", "Lorg/jetbrains/kotlin/js/resolve/diagnostics/AbstractNameCharsChecker;", "suggestion", "Lorg/jetbrains/kotlin/js/naming/JsNameSuggestion;", "(Lorg/jetbrains/kotlin/js/naming/JsNameSuggestion;)V", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsNameCharsChecker extends AbstractNameCharsChecker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsNameCharsChecker(JsNameSuggestion suggestion) {
        super(suggestion);
        Intrinsics.checkNotNullParameter(suggestion, "suggestion");
    }
}
