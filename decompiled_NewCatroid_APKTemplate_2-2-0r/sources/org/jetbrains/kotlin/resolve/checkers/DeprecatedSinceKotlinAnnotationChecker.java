package org.jetbrains.kotlin.resolve.checkers;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.RequireKotlinConstants;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: DeprecatedSinceKotlinAnnotationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u0002J.\u0010\u0010\u001a\u00020\u0004*\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/DeprecatedSinceKotlinAnnotationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "lessOrNull", "", "a", "Lorg/jetbrains/kotlin/config/ApiVersion;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "checkVersion", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "parsedVersion", "name", "", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeprecatedSinceKotlinAnnotationChecker implements DeclarationChecker {
    public static final DeprecatedSinceKotlinAnnotationChecker INSTANCE = new DeprecatedSinceKotlinAnnotationChecker();

    private DeprecatedSinceKotlinAnnotationChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = descriptor.getAnnotations().mo6752findAnnotation(StandardNames.FqNames.deprecatedSinceKotlin);
        if (annotationDescriptorMo6752findAnnotation == null) {
            return;
        }
        PsiElement psi = PsiSourceElementKt.getPsi(annotationDescriptorMo6752findAnnotation.getSource());
        KtAnnotationEntry ktAnnotationEntry = psi instanceof KtAnnotationEntry ? (KtAnnotationEntry) psi : null;
        if (ktAnnotationEntry == null) {
            return;
        }
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation2 = descriptor.getAnnotations().mo6752findAnnotation(StandardNames.FqNames.deprecated);
        KtTypeReference typeReference = ktAnnotationEntry.getTypeReference();
        if (typeReference == null) {
            return;
        }
        FqName fqNameFqNameOrNull = DescriptorUtilsKt.fqNameOrNull(descriptor);
        if (fqNameFqNameOrNull != null && !FqNamesUtilKt.isSubpackageOf(fqNameFqNameOrNull, StandardNames.BUILT_INS_PACKAGE_FQ_NAME)) {
            context.getTrace().report(Errors.DEPRECATED_SINCE_KOTLIN_OUTSIDE_KOTLIN_SUBPACKAGE.on(typeReference));
            return;
        }
        if (annotationDescriptorMo6752findAnnotation2 == null) {
            context.getTrace().report(Errors.DEPRECATED_SINCE_KOTLIN_WITHOUT_DEPRECATED.on(typeReference));
            return;
        }
        if (AnnotationUtilKt.argumentValue(annotationDescriptorMo6752findAnnotation2, FirebaseAnalytics.Param.LEVEL) != null) {
            context.getTrace().report(Errors.DEPRECATED_SINCE_KOTLIN_WITH_DEPRECATED_LEVEL.on(typeReference));
            return;
        }
        if (annotationDescriptorMo6752findAnnotation.getAllValueArguments().isEmpty()) {
            context.getTrace().report(Errors.DEPRECATED_SINCE_KOTLIN_WITHOUT_ARGUMENTS.on(typeReference));
            return;
        }
        ApiVersion apiVersionCheck$getCheckedSinceVersion = check$getCheckedSinceVersion(annotationDescriptorMo6752findAnnotation, context, typeReference, "warningSince");
        ApiVersion apiVersionCheck$getCheckedSinceVersion2 = check$getCheckedSinceVersion(annotationDescriptorMo6752findAnnotation, context, typeReference, "errorSince");
        ApiVersion apiVersionCheck$getCheckedSinceVersion3 = check$getCheckedSinceVersion(annotationDescriptorMo6752findAnnotation, context, typeReference, "hiddenSince");
        if (lessOrNull(apiVersionCheck$getCheckedSinceVersion, apiVersionCheck$getCheckedSinceVersion2) && lessOrNull(apiVersionCheck$getCheckedSinceVersion2, apiVersionCheck$getCheckedSinceVersion3) && lessOrNull(apiVersionCheck$getCheckedSinceVersion, apiVersionCheck$getCheckedSinceVersion3)) {
            return;
        }
        context.getTrace().report(Errors.DEPRECATED_SINCE_KOTLIN_WITH_UNORDERED_VERSIONS.on(typeReference));
    }

    private static final ApiVersion check$getCheckedSinceVersion(AnnotationDescriptor annotationDescriptor, DeclarationCheckerContext declarationCheckerContext, KtTypeReference ktTypeReference, String str) {
        ApiVersion sinceVersion = DeprecationUtilKt.getSinceVersion(annotationDescriptor, str);
        INSTANCE.checkVersion(annotationDescriptor, sinceVersion, str, declarationCheckerContext, ktTypeReference);
        return sinceVersion;
    }

    private final void checkVersion(AnnotationDescriptor annotationDescriptor, ApiVersion apiVersion, String str, DeclarationCheckerContext declarationCheckerContext, PsiElement psiElement) {
        ConstantValue<?> constantValueArgumentValue = AnnotationUtilKt.argumentValue(annotationDescriptor, str);
        StringValue stringValue = constantValueArgumentValue instanceof StringValue ? (StringValue) constantValueArgumentValue : null;
        String value = stringValue != null ? stringValue.getValue() : null;
        if (value != null) {
            if (apiVersion != null) {
                if (RequireKotlinConstants.INSTANCE.getVERSION_REGEX().matches(value)) {
                    return;
                }
            }
            BindingTrace trace = declarationCheckerContext.getTrace();
            DiagnosticFactory1<PsiElement, FqName> diagnosticFactory1 = Errors.ILLEGAL_KOTLIN_VERSION_STRING_VALUE;
            FqName fqName = annotationDescriptor.getFqName();
            if (fqName == null) {
                return;
            }
            Diagnostic diagnosticOn = diagnosticFactory1.on(psiElement, fqName);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
            DiagnosticUtilsKt.reportDiagnosticOnce(trace, diagnosticOn);
        }
    }

    private final boolean lessOrNull(ApiVersion a, ApiVersion b) {
        return a == null || b == null || a.compareTo(b) <= 0;
    }
}
