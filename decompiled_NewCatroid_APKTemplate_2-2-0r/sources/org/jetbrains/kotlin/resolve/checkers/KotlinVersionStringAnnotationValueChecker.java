package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.RequireKotlinConstants;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: KotlinVersionStringAnnotationValueChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J0\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/KotlinVersionStringAnnotationValueChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "annotationFqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/name/FqName;)V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "extraCheck", "annotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "version", "", "diagnosticHolder", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinVersionStringAnnotationValueChecker implements DeclarationChecker {
    private final FqName annotationFqName;

    public void extraCheck(KtDeclaration declaration, AnnotationDescriptor annotation, String version, DiagnosticSink diagnosticHolder, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(diagnosticHolder, "diagnosticHolder");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
    }

    public KotlinVersionStringAnnotationValueChecker(FqName annotationFqName) {
        Intrinsics.checkNotNullParameter(annotationFqName, "annotationFqName");
        this.annotationFqName = annotationFqName;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = descriptor.getAnnotations().mo6752findAnnotation(this.annotationFqName);
        if (annotationDescriptorMo6752findAnnotation == null) {
            return;
        }
        ConstantValue constantValue = (ConstantValue) CollectionsKt.singleOrNull(annotationDescriptorMo6752findAnnotation.getAllValueArguments().values());
        Object value = constantValue != null ? constantValue.getValue() : null;
        String str = value instanceof String ? (String) value : null;
        if (str == null) {
            return;
        }
        if (!RequireKotlinConstants.INSTANCE.getVERSION_REGEX().matches(str)) {
            BindingTrace trace = context.getTrace();
            DiagnosticFactory1<PsiElement, FqName> diagnosticFactory1 = Errors.ILLEGAL_KOTLIN_VERSION_STRING_VALUE;
            KtDeclaration psi = PsiSourceElementKt.getPsi(annotationDescriptorMo6752findAnnotation.getSource());
            if (psi == null) {
                psi = declaration;
            }
            trace.report(diagnosticFactory1.on(psi, this.annotationFqName));
            return;
        }
        extraCheck(declaration, annotationDescriptorMo6752findAnnotation, str, context.getTrace(), context.getLanguageVersionSettings());
    }
}
