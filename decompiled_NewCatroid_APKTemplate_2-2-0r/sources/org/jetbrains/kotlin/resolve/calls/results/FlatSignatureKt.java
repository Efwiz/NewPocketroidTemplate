package org.jetbrains.kotlin.resolve.calls.results;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;

/* JADX INFO: compiled from: FlatSignature.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001aF\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u0001\u001aV\u0010\f\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0016\u0010\r\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000eH\u0002¨\u0006\u0011"}, d2 = {"isSignatureNotLessSpecific", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/resolve/calls/results/SimpleConstraintSystem;", "specific", "Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;", "general", "callbacks", "Lorg/jetbrains/kotlin/resolve/calls/results/SpecificityComparisonCallbacks;", "specificityComparator", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeSpecificityComparator;", "useOriginalSamTypes", "isValueParameterTypeNotLessSpecific", "typeKindSelector", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/calls/results/TypeWithConversion;", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "resolution.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FlatSignatureKt {
    private static final <T> boolean isValueParameterTypeNotLessSpecific(SimpleConstraintSystem simpleConstraintSystem, FlatSignature<? extends T> flatSignature, FlatSignature<? extends T> flatSignature2, SpecificityComparisonCallbacks specificityComparisonCallbacks, TypeSpecificityComparator typeSpecificityComparator, Function1<? super TypeWithConversion, ? extends KotlinTypeMarker> function1) {
        KotlinTypeMarker kotlinTypeMarkerInvoke;
        KotlinTypeMarker kotlinTypeMarkerCaptureFromExpression;
        Collection<TypeParameterMarker> typeParameters = flatSignature2.getTypeParameters();
        TypeSubstitutorMarker typeSubstitutorMarkerRegisterTypeVariables = simpleConstraintSystem.registerTypeVariables(typeParameters);
        int contextReceiverCount = flatSignature.getContextReceiverCount();
        int contextReceiverCount2 = flatSignature2.getContextReceiverCount();
        List<TypeWithConversion> valueParameterTypes = flatSignature.getValueParameterTypes();
        List<TypeWithConversion> valueParameterTypes2 = flatSignature2.getValueParameterTypes();
        if (contextReceiverCount != contextReceiverCount2) {
            valueParameterTypes = CollectionsKt.drop(valueParameterTypes, contextReceiverCount);
            valueParameterTypes2 = CollectionsKt.drop(valueParameterTypes2, contextReceiverCount2);
        }
        int size = valueParameterTypes.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            KotlinTypeMarker kotlinTypeMarkerInvoke2 = function1.invoke(valueParameterTypes.get(i));
            if (kotlinTypeMarkerInvoke2 != null && (kotlinTypeMarkerInvoke = function1.invoke(valueParameterTypes2.get(i))) != null) {
                if (typeSpecificityComparator.isDefinitelyLessSpecific(kotlinTypeMarkerInvoke2, kotlinTypeMarkerInvoke)) {
                    return z;
                }
                if (typeParameters.isEmpty() || !MarkerExtensionsKt.dependsOnTypeParameters(kotlinTypeMarkerInvoke, simpleConstraintSystem.getContext(), typeParameters)) {
                    if (!AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) simpleConstraintSystem.getContext(), kotlinTypeMarkerInvoke2, kotlinTypeMarkerInvoke, false, 8, (Object) null) && !specificityComparisonCallbacks.isNonSubtypeNotLessSpecific(kotlinTypeMarkerInvoke2, kotlinTypeMarkerInvoke)) {
                        return false;
                    }
                    z = false;
                } else {
                    KotlinTypeMarker kotlinTypeMarkerSafeSubstitute = MarkerExtensionsKt.safeSubstitute(typeSubstitutorMarkerRegisterTypeVariables, simpleConstraintSystem.getContext(), kotlinTypeMarkerInvoke);
                    KotlinTypeMarker kotlinTypeMarkerPrepareType$default = AbstractTypeChecker.prepareType$default(AbstractTypeChecker.INSTANCE, simpleConstraintSystem.getContext(), kotlinTypeMarkerInvoke2, false, 4, null);
                    if (simpleConstraintSystem.getCaptureFromArgument() && (kotlinTypeMarkerCaptureFromExpression = simpleConstraintSystem.getContext().captureFromExpression(kotlinTypeMarkerPrepareType$default)) != null) {
                        kotlinTypeMarkerPrepareType$default = kotlinTypeMarkerCaptureFromExpression;
                    }
                    simpleConstraintSystem.addSubtypeConstraint(kotlinTypeMarkerPrepareType$default, kotlinTypeMarkerSafeSubstitute);
                }
            }
        }
        return true;
    }

    public static /* synthetic */ boolean isSignatureNotLessSpecific$default(SimpleConstraintSystem simpleConstraintSystem, FlatSignature flatSignature, FlatSignature flatSignature2, SpecificityComparisonCallbacks specificityComparisonCallbacks, TypeSpecificityComparator typeSpecificityComparator, boolean z, int i, Object obj) {
        if ((i & 16) != 0) {
            z = false;
        }
        return isSignatureNotLessSpecific(simpleConstraintSystem, flatSignature, flatSignature2, specificityComparisonCallbacks, typeSpecificityComparator, z);
    }

    public static final <T> boolean isSignatureNotLessSpecific(SimpleConstraintSystem simpleConstraintSystem, FlatSignature<? extends T> specific, FlatSignature<? extends T> general, SpecificityComparisonCallbacks callbacks, TypeSpecificityComparator specificityComparator, boolean z) {
        Intrinsics.checkNotNullParameter(simpleConstraintSystem, "<this>");
        Intrinsics.checkNotNullParameter(specific, "specific");
        Intrinsics.checkNotNullParameter(general, "general");
        Intrinsics.checkNotNullParameter(callbacks, "callbacks");
        Intrinsics.checkNotNullParameter(specificityComparator, "specificityComparator");
        if (specific.getHasExtensionReceiver() != general.getHasExtensionReceiver() || specific.getContextReceiverCount() > general.getContextReceiverCount() || specific.getValueParameterTypes().size() - specific.getContextReceiverCount() != general.getValueParameterTypes().size() - general.getContextReceiverCount() || !isValueParameterTypeNotLessSpecific(simpleConstraintSystem, specific, general, callbacks, specificityComparator, new Function1<TypeWithConversion, KotlinTypeMarker>() { // from class: org.jetbrains.kotlin.resolve.calls.results.FlatSignatureKt.isSignatureNotLessSpecific.1
            @Override // kotlin.jvm.functions.Function1
            public final KotlinTypeMarker invoke(TypeWithConversion typeWithConversion) {
                if (typeWithConversion != null) {
                    return typeWithConversion.getResultType();
                }
                return null;
            }
        })) {
            return false;
        }
        if (!z || isValueParameterTypeNotLessSpecific(simpleConstraintSystem, specific, general, callbacks, specificityComparator, new Function1<TypeWithConversion, KotlinTypeMarker>() { // from class: org.jetbrains.kotlin.resolve.calls.results.FlatSignatureKt.isSignatureNotLessSpecific.2
            @Override // kotlin.jvm.functions.Function1
            public final KotlinTypeMarker invoke(TypeWithConversion typeWithConversion) {
                if (typeWithConversion != null) {
                    return typeWithConversion.getOriginalTypeIfWasConverted();
                }
                return null;
            }
        })) {
            return !simpleConstraintSystem.hasContradiction();
        }
        return false;
    }
}
