package org.jetbrains.kotlin.resolve.deprecation;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.KotlinCompilerVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;

/* JADX INFO: compiled from: deprecationUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u001a\u0018\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0000\u001a\f\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\u000b\u001a\f\u0010\u0010\u001a\u0004\u0018\u00010\u000f*\u00020\u000b\u001a\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u0005*\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u000f¨\u0006\u0013"}, d2 = {"computeLevelForDeprecatedSinceKotlin", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "annotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "apiVersion", "Lorg/jetbrains/kotlin/config/ApiVersion;", "createDeprecationDiagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "deprecation", "Lorg/jetbrains/kotlin/resolve/deprecation/DescriptorBasedDeprecationInfo;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "deprecatedByAnnotationReplaceWithExpression", "", "deprecatedByOverriddenMessage", "getSinceVersion", "name", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeprecationUtilKt {

    /* JADX INFO: compiled from: deprecationUtil.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[DeprecationLevelValue.values().length];
            try {
                iArr[DeprecationLevelValue.WARNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeprecationLevelValue.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DeprecationLevelValue.HIDDEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ProtoBuf.VersionRequirement.VersionKind.values().length];
            try {
                iArr2[ProtoBuf.VersionRequirement.VersionKind.COMPILER_VERSION.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ProtoBuf.VersionRequirement.VersionKind.LANGUAGE_VERSION.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[ProtoBuf.VersionRequirement.VersionKind.API_VERSION.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public static final String deprecatedByOverriddenMessage(DescriptorBasedDeprecationInfo descriptorBasedDeprecationInfo) {
        Intrinsics.checkNotNullParameter(descriptorBasedDeprecationInfo, "<this>");
        DeprecatedByOverridden deprecatedByOverridden = descriptorBasedDeprecationInfo instanceof DeprecatedByOverridden ? (DeprecatedByOverridden) descriptorBasedDeprecationInfo : null;
        if (deprecatedByOverridden != null) {
            return deprecatedByOverridden.additionalMessage$frontend();
        }
        return null;
    }

    public static final String deprecatedByAnnotationReplaceWithExpression(DescriptorBasedDeprecationInfo descriptorBasedDeprecationInfo) {
        Intrinsics.checkNotNullParameter(descriptorBasedDeprecationInfo, "<this>");
        DeprecatedByAnnotation deprecatedByAnnotation = descriptorBasedDeprecationInfo instanceof DeprecatedByAnnotation ? (DeprecatedByAnnotation) descriptorBasedDeprecationInfo : null;
        if (deprecatedByAnnotation != null) {
            return deprecatedByAnnotation.getReplaceWithValue$frontend();
        }
        return null;
    }

    public static final ApiVersion getSinceVersion(AnnotationDescriptor annotationDescriptor, String name2) {
        String value;
        Intrinsics.checkNotNullParameter(annotationDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        ConstantValue<?> constantValueArgumentValue = AnnotationUtilKt.argumentValue(annotationDescriptor, name2);
        StringValue stringValue = constantValueArgumentValue instanceof StringValue ? (StringValue) constantValueArgumentValue : null;
        if (stringValue == null || (value = stringValue.getValue()) == null) {
            return null;
        }
        if (value.length() == 0) {
            value = null;
        }
        if (value != null) {
            return ApiVersion.INSTANCE.parse(value);
        }
        return null;
    }

    public static final DeprecationLevelValue computeLevelForDeprecatedSinceKotlin(AnnotationDescriptor annotation, ApiVersion apiVersion) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(apiVersion, "apiVersion");
        ApiVersion sinceVersion = getSinceVersion(annotation, "hiddenSince");
        if (sinceVersion != null && apiVersion.compareTo(sinceVersion) >= 0) {
            return DeprecationLevelValue.HIDDEN;
        }
        ApiVersion sinceVersion2 = getSinceVersion(annotation, "errorSince");
        if (sinceVersion2 != null && apiVersion.compareTo(sinceVersion2) >= 0) {
            return DeprecationLevelValue.ERROR;
        }
        ApiVersion sinceVersion3 = getSinceVersion(annotation, "warningSince");
        if (sinceVersion3 == null || apiVersion.compareTo(sinceVersion3) < 0) {
            return null;
        }
        return DeprecationLevelValue.WARNING;
    }

    public static final Diagnostic createDeprecationDiagnostic(PsiElement element, DescriptorBasedDeprecationInfo deprecation, LanguageVersionSettings languageVersionSettings) {
        DiagnosticFactory2<PsiElement, DeclarationDescriptor, String> diagnosticFactory2;
        DiagnosticFactory3<PsiElement, TypeAliasDescriptor, DeclarationDescriptor, String> diagnosticFactory3;
        DiagnosticFactory3<PsiElement, DeclarationDescriptor, VersionRequirement.Version, Pair<String, String>> diagnosticFactory4;
        String versionString;
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(deprecation, "deprecation");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        DeclarationDescriptor original = deprecation.getTarget().getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        if (deprecation instanceof DeprecatedByVersionRequirement) {
            int i = WhenMappings.$EnumSwitchMapping$0[deprecation.getDeprecationLevel().ordinal()];
            if (i == 1) {
                diagnosticFactory4 = Errors.VERSION_REQUIREMENT_DEPRECATION;
            } else {
                if (i != 2 && i != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                diagnosticFactory4 = Errors.VERSION_REQUIREMENT_DEPRECATION_ERROR;
            }
            DeprecatedByVersionRequirement deprecatedByVersionRequirement = (DeprecatedByVersionRequirement) deprecation;
            int i2 = WhenMappings.$EnumSwitchMapping$1[deprecatedByVersionRequirement.getVersionRequirement().getKind().ordinal()];
            if (i2 == 1) {
                versionString = KotlinCompilerVersion.VERSION;
            } else if (i2 == 2) {
                versionString = languageVersionSettings.getLanguageVersion().getVersionString();
            } else {
                if (i2 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                versionString = languageVersionSettings.getApiVersion().getVersionString();
            }
            Diagnostic diagnosticOn = diagnosticFactory4.on(element, original, deprecatedByVersionRequirement.getVersionRequirement().getVersion(), TuplesKt.to(versionString, deprecation.getMessage()));
            Intrinsics.checkNotNull(diagnosticOn);
            return diagnosticOn;
        }
        if (deprecation instanceof DeprecatedTypealiasByAnnotation) {
            int i3 = WhenMappings.$EnumSwitchMapping$0[deprecation.getDeprecationLevel().ordinal()];
            if (i3 == 1) {
                diagnosticFactory3 = Errors.TYPEALIAS_EXPANSION_DEPRECATION;
            } else {
                if (i3 != 2 && i3 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                diagnosticFactory3 = Errors.TYPEALIAS_EXPANSION_DEPRECATION_ERROR;
            }
            DeprecatedTypealiasByAnnotation deprecatedTypealiasByAnnotation = (DeprecatedTypealiasByAnnotation) deprecation;
            TypeAliasDescriptor original2 = deprecatedTypealiasByAnnotation.getTypeAliasTarget().getOriginal();
            DeclarationDescriptor original3 = deprecatedTypealiasByAnnotation.getNested().getTarget().getOriginal();
            String message = deprecatedTypealiasByAnnotation.getNested().getMessage();
            Diagnostic diagnosticOn2 = diagnosticFactory3.on(element, original2, original3, message != null ? message : "");
            Intrinsics.checkNotNull(diagnosticOn2);
            return diagnosticOn2;
        }
        int i4 = WhenMappings.$EnumSwitchMapping$0[deprecation.getDeprecationLevel().ordinal()];
        if (i4 == 1) {
            diagnosticFactory2 = Errors.DEPRECATION;
        } else {
            if (i4 != 2 && i4 != 3) {
                throw new NoWhenBranchMatchedException();
            }
            diagnosticFactory2 = Errors.DEPRECATION_ERROR;
        }
        String message2 = deprecation.getMessage();
        Diagnostic diagnosticOn3 = diagnosticFactory2.on(element, original, message2 != null ? message2 : "");
        Intrinsics.checkNotNull(diagnosticOn3);
        return diagnosticOn3;
    }
}
