package org.jetbrains.kotlin.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.types.AbbreviatedType;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: UpperBoundChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = UpperBoundChecker.class)
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J>\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0004J`\u0010\u0012\u001a\u00020\u00132\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u00172\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2 \b\u0002\u0010\u0018\u001a\u001a\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u001a0\u0019H\u0016J(\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u0006H\u0002J&\u0010\u001f\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010 \u001a\u00020!J \u0010\"\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\b2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/resolve/UpperBoundChecker;", "", "typeChecker", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeChecker;", "(Lorg/jetbrains/kotlin/types/checker/KotlinTypeChecker;)V", "checkBound", "", "bound", "Lorg/jetbrains/kotlin/types/KotlinType;", "argumentType", "argumentReference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "typeAliasUsageElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "upperBoundViolatedReporter", "Lorg/jetbrains/kotlin/resolve/UpperBoundViolatedReporter;", "checkBounds", "", "typeParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "diagnosticForTypeAliases", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory3;", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "checkBoundsForAbbreviatedSupertype", ModuleXmlParser.TYPE, "typeReference", "reportWarning", "checkBoundsInSupertype", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "checkBoundsOfExpandedTypeAlias", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class UpperBoundChecker {
    private final KotlinTypeChecker typeChecker;

    public void checkBoundsOfExpandedTypeAlias(KotlinType type, KtExpression expression, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(trace, "trace");
    }

    public UpperBoundChecker(KotlinTypeChecker typeChecker) {
        Intrinsics.checkNotNullParameter(typeChecker, "typeChecker");
        this.typeChecker = typeChecker;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void checkBounds$default(UpperBoundChecker upperBoundChecker, KtTypeReference ktTypeReference, KotlinType kotlinType, TypeParameterDescriptor typeParameterDescriptor, TypeSubstitutor typeSubstitutor, BindingTrace bindingTrace, KtElement ktElement, DiagnosticFactory3 diagnosticFactory3, int i, Object obj) {
        DiagnosticFactory3 diagnosticFactory4;
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: checkBounds");
        }
        KtElement ktElement2 = (i & 32) != 0 ? null : ktElement;
        if ((i & 64) != 0) {
            DiagnosticFactory3<KtElement, KotlinType, KotlinType, ClassifierDescriptor> UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION = Errors.UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION;
            Intrinsics.checkNotNullExpressionValue(UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION, "UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION");
            diagnosticFactory4 = UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION;
        } else {
            diagnosticFactory4 = diagnosticFactory3;
        }
        upperBoundChecker.checkBounds(ktTypeReference, kotlinType, typeParameterDescriptor, typeSubstitutor, bindingTrace, ktElement2, diagnosticFactory4);
    }

    public void checkBounds(KtTypeReference argumentReference, KotlinType argumentType, TypeParameterDescriptor typeParameterDescriptor, TypeSubstitutor substitutor, BindingTrace trace, KtElement typeAliasUsageElement, DiagnosticFactory3<KtElement, KotlinType, KotlinType, ClassifierDescriptor> diagnosticForTypeAliases) {
        Intrinsics.checkNotNullParameter(argumentType, "argumentType");
        Intrinsics.checkNotNullParameter(typeParameterDescriptor, "typeParameterDescriptor");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(diagnosticForTypeAliases, "diagnosticForTypeAliases");
        if (typeParameterDescriptor.getUpperBounds().isEmpty()) {
            return;
        }
        UpperBoundViolatedReporter upperBoundViolatedReporter = new UpperBoundViolatedReporter(trace, argumentType, typeParameterDescriptor, null, diagnosticForTypeAliases, 8, null);
        for (KotlinType kotlinType : typeParameterDescriptor.getUpperBounds()) {
            Intrinsics.checkNotNull(kotlinType);
            checkBound(kotlinType, argumentType, argumentReference, substitutor, typeAliasUsageElement, upperBoundViolatedReporter);
        }
    }

    public final void checkBoundsInSupertype(KtTypeReference typeReference, KotlinType type, BindingTrace trace, LanguageVersionSettings languageVersionSettings) {
        KtTypeElement typeElement;
        Intrinsics.checkNotNullParameter(typeReference, "typeReference");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (KotlinTypeKt.isError(type) || (typeElement = typeReference.getTypeElement()) == null) {
            return;
        }
        List<TypeParameterDescriptor> parameters = type.getConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<TypeProjection> arguments = type.getArguments();
        parameters.size();
        arguments.size();
        List<KtTypeReference> typeArgumentsAsTypes = typeElement.getTypeArgumentsAsTypes();
        Intrinsics.checkNotNullExpressionValue(typeArgumentsAsTypes, "getTypeArgumentsAsTypes(...)");
        String str = "get(...)";
        if (FlexibleTypesKt.isFlexible(type) && !DynamicTypesKt.isDynamic(type)) {
            typeArgumentsAsTypes.size();
            FlexibleType flexibleTypeAsFlexibleType = FlexibleTypesKt.asFlexibleType(type);
            KtTypeReference ktTypeReference = typeArgumentsAsTypes.get(0);
            Intrinsics.checkNotNullExpressionValue(ktTypeReference, "get(...)");
            checkBoundsInSupertype(ktTypeReference, flexibleTypeAsFlexibleType.getLowerBound(), trace, languageVersionSettings);
            KtTypeReference ktTypeReference2 = typeArgumentsAsTypes.get(1);
            Intrinsics.checkNotNullExpressionValue(ktTypeReference2, "get(...)");
            checkBoundsInSupertype(ktTypeReference2, flexibleTypeAsFlexibleType.getUpperBound(), trace, languageVersionSettings);
            return;
        }
        if (type instanceof AbbreviatedType) {
            checkBoundsForAbbreviatedSupertype(type, trace, typeReference, (typeArgumentsAsTypes.size() == arguments.size() || languageVersionSettings.supportsFeature(LanguageFeature.ReportMissingUpperBoundsViolatedErrorOnAbbreviationAtSupertypes)) ? false : true);
            return;
        }
        if (typeArgumentsAsTypes.size() != arguments.size()) {
            return;
        }
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(type);
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        int size = typeArgumentsAsTypes.size();
        int i = 0;
        while (i < size) {
            KtTypeReference ktTypeReference3 = typeArgumentsAsTypes.get(i);
            if (ktTypeReference3 != null) {
                KotlinType type2 = arguments.get(i).getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                checkBoundsInSupertype(ktTypeReference3, type2, trace, languageVersionSettings);
                KotlinType type3 = arguments.get(i).getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                TypeParameterDescriptor typeParameterDescriptor = parameters.get(i);
                Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, str);
                checkBounds$default(this, ktTypeReference3, type3, typeParameterDescriptor, typeSubstitutorCreate, trace, null, null, 96, null);
            }
            i++;
            str = str;
            size = size;
            typeSubstitutorCreate = typeSubstitutorCreate;
        }
    }

    private final void checkBoundsForAbbreviatedSupertype(KotlinType type, BindingTrace trace, KtTypeReference typeReference, boolean reportWarning) {
        DiagnosticFactory3<KtElement, KotlinType, KotlinType, ClassifierDescriptor> diagnosticFactory3;
        List<TypeParameterDescriptor> parameters = type.getConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        List<TypeProjection> arguments = type.getArguments();
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(type);
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        if (reportWarning) {
            diagnosticFactory3 = Errors.UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION_WARNING;
        } else {
            diagnosticFactory3 = Errors.UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION;
        }
        DiagnosticFactory3<KtElement, KotlinType, KotlinType, ClassifierDescriptor> diagnosticFactory4 = diagnosticFactory3;
        int size = arguments.size();
        for (int i = 0; i < size; i++) {
            if (!arguments.get(i).isStarProjection()) {
                KotlinType type2 = arguments.get(i).getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                checkBoundsForAbbreviatedSupertype(type2, trace, typeReference, reportWarning);
                TypeParameterDescriptor typeParameterDescriptor = parameters.get(i);
                Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "get(...)");
                Intrinsics.checkNotNull(diagnosticFactory4);
                checkBounds(null, type2, typeParameterDescriptor, typeSubstitutorCreate, trace, typeReference, diagnosticFactory4);
            }
        }
    }

    public static /* synthetic */ boolean checkBound$default(UpperBoundChecker upperBoundChecker, KotlinType kotlinType, KotlinType kotlinType2, KtTypeReference ktTypeReference, TypeSubstitutor typeSubstitutor, KtElement ktElement, UpperBoundViolatedReporter upperBoundViolatedReporter, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: checkBound");
        }
        if ((i & 16) != 0) {
            ktElement = null;
        }
        return upperBoundChecker.checkBound(kotlinType, kotlinType2, ktTypeReference, typeSubstitutor, ktElement, upperBoundViolatedReporter);
    }

    protected final boolean checkBound(KotlinType bound, KotlinType argumentType, KtTypeReference argumentReference, TypeSubstitutor substitutor, KtElement typeAliasUsageElement, UpperBoundViolatedReporter upperBoundViolatedReporter) {
        Intrinsics.checkNotNullParameter(bound, "bound");
        Intrinsics.checkNotNullParameter(argumentType, "argumentType");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        Intrinsics.checkNotNullParameter(upperBoundViolatedReporter, "upperBoundViolatedReporter");
        KotlinType kotlinTypeSafeSubstitute = substitutor.safeSubstitute(bound, Variance.INVARIANT);
        Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeSubstitute, "safeSubstitute(...)");
        if (this.typeChecker.isSubtypeOf(argumentType, kotlinTypeSafeSubstitute)) {
            return true;
        }
        if (argumentReference != null) {
            upperBoundViolatedReporter.report(argumentReference, kotlinTypeSafeSubstitute);
            return false;
        }
        if (typeAliasUsageElement == null || TypeUtilsKt.containsTypeAliasParameters(kotlinTypeSafeSubstitute) || TypeUtilsKt.containsTypeAliasParameters(argumentType)) {
            return false;
        }
        upperBoundViolatedReporter.reportForTypeAliasExpansion(typeAliasUsageElement, kotlinTypeSafeSubstitute);
        return false;
    }
}
