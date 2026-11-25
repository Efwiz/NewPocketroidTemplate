package org.jetbrains.kotlin.resolve.calls.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzerContext;
import org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.model.CallableReferenceKotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallArgument;
import org.jetbrains.kotlin.resolve.calls.model.LHSResult;
import org.jetbrains.kotlin.resolve.calls.model.SubKotlinCallArgument;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeApproximator;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;

/* JADX INFO: compiled from: DescriptorRelatedInferenceUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0005\u001a\u00020\u0006*\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\t\u001a\u001c\u0010\n\u001a\u00020\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0006\u001a\u0012\u0010\u0010\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u000b\u001a\u0012\u0010\u0010\u001a\u00020\u0001*\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0001\u001a&\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u000b2\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0006\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0018"}, d2 = {"returnTypeOrNothing", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getReturnTypeOrNothing", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Lorg/jetbrains/kotlin/types/UnwrappedType;", "addSubsystemFromArgument", "", "Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzerContext;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "buildResultingSubstitutor", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/NewTypeSubstitutor;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "transformTypeVariablesToErrorTypes", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", ModuleXmlParser.TYPE, "substituteAndApproximateTypes", "typeApproximator", "Lorg/jetbrains/kotlin/types/TypeApproximator;", "positionDependentApproximation", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DescriptorRelatedInferenceUtilsKt {
    public static /* synthetic */ NewTypeSubstitutor buildResultingSubstitutor$default(ConstraintStorage constraintStorage, TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return buildResultingSubstitutor(constraintStorage, typeSystemInferenceExtensionContext, z);
    }

    public static final NewTypeSubstitutor buildResultingSubstitutor(ConstraintStorage constraintStorage, TypeSystemInferenceExtensionContext context, boolean z) {
        Intrinsics.checkNotNullParameter(constraintStorage, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        TypeSubstitutorMarker typeSubstitutorMarkerBuildAbstractResultingSubstitutor = InferenceUtilsKt.buildAbstractResultingSubstitutor(constraintStorage, context, z);
        Intrinsics.checkNotNull(typeSubstitutorMarkerBuildAbstractResultingSubstitutor, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.components.NewTypeSubstitutor");
        return (NewTypeSubstitutor) typeSubstitutorMarkerBuildAbstractResultingSubstitutor;
    }

    public static final UnwrappedType getReturnTypeOrNothing(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        KotlinType returnType = callableDescriptor.getReturnType();
        if (returnType == null) {
            SimpleType nothingType = DescriptorUtilsKt.getBuiltIns(callableDescriptor).getNothingType();
            Intrinsics.checkNotNullExpressionValue(nothingType, "getNothingType(...)");
            return nothingType;
        }
        return returnType.unwrap();
    }

    public static final UnwrappedType substitute(TypeSubstitutor typeSubstitutor, UnwrappedType type) {
        Intrinsics.checkNotNullParameter(typeSubstitutor, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return typeSubstitutor.safeSubstitute(type, Variance.INVARIANT).unwrap();
    }

    public static final CallableDescriptor substitute(CallableDescriptor callableDescriptor, final NewTypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        if (substitutor.isEmpty()) {
            return callableDescriptor;
        }
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(new TypeSubstitution() { // from class: org.jetbrains.kotlin.resolve.calls.inference.DescriptorRelatedInferenceUtilsKt$substitute$wrappedSubstitution$1
            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            /* JADX INFO: renamed from: get */
            public TypeProjection mo7475get(KotlinType key) {
                Intrinsics.checkNotNullParameter(key, "key");
                return null;
            }

            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            public UnwrappedType prepareTopLevelType(KotlinType topLevelType, Variance position) {
                Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
                Intrinsics.checkNotNullParameter(position, "position");
                return substitutor.safeSubstitute(topLevelType.unwrap());
            }
        });
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        CallableDescriptor callableDescriptorSubstitute = callableDescriptor.substitute(typeSubstitutorCreate);
        Intrinsics.checkNotNullExpressionValue(callableDescriptorSubstitute, "substitute(...)");
        return callableDescriptorSubstitute;
    }

    public static /* synthetic */ CallableDescriptor substituteAndApproximateTypes$default(CallableDescriptor callableDescriptor, NewTypeSubstitutor newTypeSubstitutor, TypeApproximator typeApproximator, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return substituteAndApproximateTypes(callableDescriptor, newTypeSubstitutor, typeApproximator, z);
    }

    public static final CallableDescriptor substituteAndApproximateTypes(CallableDescriptor callableDescriptor, final NewTypeSubstitutor substitutor, final TypeApproximator typeApproximator, final boolean z) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        if (substitutor.isEmpty()) {
            return callableDescriptor;
        }
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(new TypeSubstitution() { // from class: org.jetbrains.kotlin.resolve.calls.inference.DescriptorRelatedInferenceUtilsKt$substituteAndApproximateTypes$wrappedSubstitution$1
            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            /* JADX INFO: renamed from: get */
            public TypeProjection mo7475get(KotlinType key) {
                Intrinsics.checkNotNullParameter(key, "key");
                return null;
            }

            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            public UnwrappedType prepareTopLevelType(KotlinType topLevelType, Variance position) {
                Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
                Intrinsics.checkNotNullParameter(position, "position");
                UnwrappedType unwrappedTypeSafeSubstitute = substitutor.safeSubstitute(topLevelType.unwrap());
                TypeApproximator typeApproximator2 = typeApproximator;
                boolean z2 = z;
                if (typeApproximator2 == null) {
                    return unwrappedTypeSafeSubstitute;
                }
                UnwrappedType unwrappedTypeApproximateTo = typeApproximator2.approximateTo(unwrappedTypeSafeSubstitute, TypeApproximatorConfiguration.FinalApproximationAfterResolutionAndInference.INSTANCE, (position == Variance.IN_VARIANCE && z2) ? false : true);
                return unwrappedTypeApproximateTo == null ? unwrappedTypeSafeSubstitute : unwrappedTypeApproximateTo;
            }
        });
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        CallableDescriptor callableDescriptorSubstitute = callableDescriptor.substitute(typeSubstitutorCreate);
        return callableDescriptorSubstitute == null ? callableDescriptor : callableDescriptorSubstitute;
    }

    public static final boolean addSubsystemFromArgument(PostponedArgumentsAnalyzerContext postponedArgumentsAnalyzerContext, KotlinCallArgument kotlinCallArgument) {
        Intrinsics.checkNotNullParameter(postponedArgumentsAnalyzerContext, "<this>");
        if (kotlinCallArgument instanceof SubKotlinCallArgument) {
            postponedArgumentsAnalyzerContext.addOtherSystem(((SubKotlinCallArgument) kotlinCallArgument).getCallResult().getConstraintSystem().getBuilder().currentStorage());
            return true;
        }
        if (!(kotlinCallArgument instanceof CallableReferenceKotlinCallArgument)) {
            return false;
        }
        LHSResult lhsResult = ((CallableReferenceKotlinCallArgument) kotlinCallArgument).getLhsResult();
        LHSResult.Expression expression = lhsResult instanceof LHSResult.Expression ? (LHSResult.Expression) lhsResult : null;
        return addSubsystemFromArgument(postponedArgumentsAnalyzerContext, expression != null ? expression.getLshCallArgument() : null);
    }
}
