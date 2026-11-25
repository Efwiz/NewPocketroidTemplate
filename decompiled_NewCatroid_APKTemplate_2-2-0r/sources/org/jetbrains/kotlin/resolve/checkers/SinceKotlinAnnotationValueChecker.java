package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.AnnotationsKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: KotlinVersionStringAnnotationValueChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/SinceKotlinAnnotationValueChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/KotlinVersionStringAnnotationValueChecker;", "()V", "extraCheck", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "annotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "version", "", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SinceKotlinAnnotationValueChecker extends KotlinVersionStringAnnotationValueChecker {
    public static final SinceKotlinAnnotationValueChecker INSTANCE = new SinceKotlinAnnotationValueChecker();

    private SinceKotlinAnnotationValueChecker() {
        super(AnnotationsKt.getSINCE_KOTLIN_FQ_NAME());
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.KotlinVersionStringAnnotationValueChecker
    public void extraCheck(KtDeclaration declaration, AnnotationDescriptor annotation, String version, DiagnosticSink diagnosticHolder, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        ApiVersion apiVersion = ApiVersion.INSTANCE.parse(version);
        ApiVersion apiVersion2 = languageVersionSettings.getApiVersion();
        if (apiVersion == null || apiVersion.compareTo(apiVersion2) <= 0) {
            return;
        }
        DiagnosticFactory1<PsiElement, String> diagnosticFactory1 = Errors.NEWER_VERSION_IN_SINCE_KOTLIN;
        KtDeclaration psi = PsiSourceElementKt.getPsi(annotation.getSource());
        if (psi == null) {
            psi = declaration;
        }
        diagnosticHolder.report(diagnosticFactory1.on(psi, apiVersion2.getVersionString()));
    }
}
