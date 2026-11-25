package org.jetbrains.kotlin.resolve.calls.components;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.incremental.UtilsKt;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.model.LambdaKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.LambdaWithTypeVariableAsExpectedTypeAtom;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtomsKt;
import org.jetbrains.kotlin.resolve.calls.model.SamConversionDescription;
import org.jetbrains.kotlin.resolve.calls.model.SimpleKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.SubKotlinCallArgument;
import org.jetbrains.kotlin.resolve.sam.SamConversionOracle;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverImplKt;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverKt;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: SamTypeConversions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u0004H\u0016J*\u0010\u000e\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u0011\u001a\u00020\u00042\u000e\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u00132\u0006\u0010\u0015\u001a\u00020\nH\u0002J\u0016\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\nH\u0002¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/SamTypeConversions;", "Lorg/jetbrains/kotlin/resolve/calls/components/ParameterTypeConversion;", "()V", "conversionDefinitelyNotNeeded", "", "candidate", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "expectedParameterType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "conversionIsNeededAfterSubtypingCheck", "conversionIsNeededBeforeSubtypingCheck", "areSuspendOnlySamConversionsSupported", "convertParameterType", "parameter", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "hasNonAnalyzedLambdaAsReturnType", "subResolvedAtoms", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", ModuleXmlParser.TYPE, "isJavaParameterCanBeConverted", "needCompatibilityResolveForSAM", "typeToConvert", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SamTypeConversions implements ParameterTypeConversion {
    public static final SamTypeConversions INSTANCE = new SamTypeConversions();

    private SamTypeConversions() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.ParameterTypeConversion
    public boolean conversionDefinitelyNotNeeded(ResolutionCandidate candidate, KotlinCallArgument argument, UnwrappedType expectedParameterType) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(expectedParameterType, "expectedParameterType");
        KotlinCallComponents callComponents = candidate.getCallComponents();
        if (!callComponents.getLanguageVersionSettings().supportsFeature(LanguageFeature.SamConversionPerArgument)) {
            return true;
        }
        UnwrappedType unwrappedType = expectedParameterType;
        if (TypeUtilsKt.isNothing(unwrappedType) || FunctionTypesKt.isFunctionType(unwrappedType)) {
            return true;
        }
        SamConversionOracle samConversionOracle = callComponents.getSamConversionOracle();
        if (!callComponents.getLanguageVersionSettings().supportsFeature(LanguageFeature.SamConversionForKotlinFunctions) && !samConversionOracle.shouldRunSamConversionForFunction(candidate.getResolvedCall().get_candidateDescriptor())) {
            return true;
        }
        ClassifierDescriptor this$0 = expectedParameterType.getConstructor().getThis$0();
        return (this$0 instanceof ClassDescriptor) && ((ClassDescriptor) this$0).isDefinitelyNotSamInterface();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.ParameterTypeConversion
    public boolean conversionIsNeededBeforeSubtypingCheck(KotlinCallArgument argument, boolean areSuspendOnlySamConversionsSupported) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        if (argument instanceof SubKotlinCallArgument) {
            SubKotlinCallArgument subKotlinCallArgument = (SubKotlinCallArgument) argument;
            UnwrappedType stableType = ArgumentsUtilsKt.getStableType(subKotlinCallArgument.getReceiver());
            UnwrappedType unwrappedType = stableType;
            if (FunctionTypesKt.isFunctionType(unwrappedType) || (areSuspendOnlySamConversionsSupported && FunctionTypesKt.isFunctionOrKFunctionTypeWithAnySuspendability(unwrappedType))) {
                return true;
            }
            return hasNonAnalyzedLambdaAsReturnType(subKotlinCallArgument.getCallResult().getSubResolvedAtoms(), stableType);
        }
        if (argument instanceof SimpleKotlinCallArgument) {
            UnwrappedType stableType2 = ArgumentsUtilsKt.getStableType(((SimpleKotlinCallArgument) argument).getReceiver());
            if (FunctionTypesKt.isFunctionType(stableType2)) {
                return true;
            }
            if (areSuspendOnlySamConversionsSupported && FunctionTypesKt.isFunctionOrKFunctionTypeWithAnySuspendability(stableType2)) {
                return true;
            }
        } else {
            if (argument instanceof LambdaKotlinCallArgument ? true : argument instanceof CallableReferenceKotlinCallArgument) {
                return true;
            }
        }
        return false;
    }

    private final boolean hasNonAnalyzedLambdaAsReturnType(List<? extends ResolvedAtom> subResolvedAtoms, UnwrappedType type) {
        if (subResolvedAtoms == null) {
            return false;
        }
        for (ResolvedAtom resolvedAtom : subResolvedAtoms) {
            if (((resolvedAtom instanceof LambdaWithTypeVariableAsExpectedTypeAtom) && Intrinsics.areEqual(((LambdaWithTypeVariableAsExpectedTypeAtom) resolvedAtom).getExpectedType().getConstructor(), type.getConstructor())) || INSTANCE.hasNonAnalyzedLambdaAsReturnType(resolvedAtom.getSubResolvedAtoms(), type)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.ParameterTypeConversion
    public boolean conversionIsNeededAfterSubtypingCheck(KotlinCallArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        return (argument instanceof SimpleKotlinCallArgument) && FunctionTypesKt.isFunctionTypeOrSubtype(ArgumentsUtilsKt.getStableType(((SimpleKotlinCallArgument) argument).getReceiver()));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.ParameterTypeConversion
    public UnwrappedType convertParameterType(ResolutionCandidate candidate, KotlinCallArgument argument, ParameterDescriptor parameter, UnwrappedType expectedParameterType) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(expectedParameterType, "expectedParameterType");
        KotlinCallComponents callComponents = candidate.getCallComponents();
        ParameterDescriptor original = parameter.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        UnwrappedType expectedType = ArgumentsUtilsKt.getExpectedType(argument, original, callComponents.getLanguageVersionSettings());
        UnwrappedType functionTypeForPossibleSamType = SamConversionResolverImplKt.getFunctionTypeForPossibleSamType(callComponents.getSamConversionResolver(), expectedParameterType, callComponents.getSamConversionOracle());
        if (functionTypeForPossibleSamType == null) {
            return null;
        }
        UnwrappedType functionTypeForPossibleSamType2 = Intrinsics.areEqual(expectedParameterType.getConstructor(), expectedType.getConstructor()) ? SamConversionResolverImplKt.getFunctionTypeForPossibleSamType(callComponents.getSamConversionResolver(), expectedType, callComponents.getSamConversionOracle()) : functionTypeForPossibleSamType;
        Intrinsics.areEqual(functionTypeForPossibleSamType.getConstructor(), functionTypeForPossibleSamType2 != null ? functionTypeForPossibleSamType2.getConstructor() : null);
        MutableResolvedCallAtom resolvedCall = candidate.getResolvedCall();
        Intrinsics.checkNotNull(functionTypeForPossibleSamType2);
        resolvedCall.registerArgumentWithSamConversion(argument, new SamConversionDescription(functionTypeForPossibleSamType2, functionTypeForPossibleSamType, expectedParameterType));
        if (needCompatibilityResolveForSAM(candidate, expectedParameterType)) {
            ResolvedCallAtomsKt.markCandidateForCompatibilityResolve$default(candidate, false, 1, null);
        }
        ClassifierDescriptor this$0 = expectedType.getConstructor().getThis$0();
        if (this$0 instanceof ClassDescriptor) {
            UtilsKt.record(callComponents.getLookupTracker(), candidate.getScopeTower().getLocation(), (ClassDescriptor) this$0, SamConversionResolverKt.getSAM_LOOKUP_NAME());
        }
        return functionTypeForPossibleSamType;
    }

    private final boolean needCompatibilityResolveForSAM(ResolutionCandidate candidate, UnwrappedType typeToConvert) {
        ClassifierDescriptor this$0 = typeToConvert.getConstructor().getThis$0();
        if ((this$0 instanceof ClassDescriptor) && ((ClassDescriptor) this$0).isFun()) {
            return false;
        }
        return !candidate.getCallComponents().getSamConversionOracle().isJavaApplicableCandidate(candidate.getResolvedCall().get_candidateDescriptor());
    }

    public final boolean isJavaParameterCanBeConverted(ResolutionCandidate candidate, UnwrappedType expectedParameterType) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(expectedParameterType, "expectedParameterType");
        KotlinCallComponents callComponents = candidate.getCallComponents();
        if (!callComponents.getSamConversionOracle().isJavaApplicableCandidate(candidate.getResolvedCall().get_candidateDescriptor())) {
            return false;
        }
        ClassifierDescriptor this$0 = expectedParameterType.getConstructor().getThis$0();
        return (((this$0 instanceof ClassDescriptor) && ((ClassDescriptor) this$0).isDefinitelyNotSamInterface()) || SamConversionResolverImplKt.getFunctionTypeForPossibleSamType(callComponents.getSamConversionResolver(), expectedParameterType, callComponents.getSamConversionOracle()) == null) ? false : true;
    }
}
