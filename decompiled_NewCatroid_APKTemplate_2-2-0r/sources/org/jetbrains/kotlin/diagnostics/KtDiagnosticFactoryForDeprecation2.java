package org.jetbrains.kotlin.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;

/* JADX INFO: compiled from: KtDiagnosticFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00040\u0003B5\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation2;", "A", "B", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "featureForError", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "warningFactory", "errorFactory", "(Lorg/jetbrains/kotlin/config/LanguageFeature;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;)V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDiagnosticFactoryForDeprecation2<A, B> extends KtDiagnosticFactoryForDeprecation<KtDiagnosticFactory2<A, B>> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDiagnosticFactoryForDeprecation2(LanguageFeature featureForError, KtDiagnosticFactory2<A, B> warningFactory, KtDiagnosticFactory2<A, B> errorFactory) {
        super(featureForError, warningFactory, errorFactory, null);
        Intrinsics.checkNotNullParameter(featureForError, "featureForError");
        Intrinsics.checkNotNullParameter(warningFactory, "warningFactory");
        Intrinsics.checkNotNullParameter(errorFactory, "errorFactory");
    }
}
