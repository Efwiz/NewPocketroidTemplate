package org.jetbrains.kotlin.resolve.calls.components;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableFromCallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.model.ResolutionPart;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCallAtomsKt;
import org.jetbrains.kotlin.resolve.calls.model.SimpleTypeArgument;
import org.jetbrains.kotlin.resolve.calls.model.TypeArgument;
import org.jetbrains.kotlin.resolve.checkers.EmptyIntersectionTypeInfo;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: ResolutionParts.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u001a\u0010\b\u001a\u00020\t*\u00020\u00052\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/CheckIncompatibleTypeVariableUpperBounds;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionPart;", "()V", "process", "", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "workIndex", "", "wasPreviouslyDiscriminated", "", "upperTypes", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckIncompatibleTypeVariableUpperBounds extends ResolutionPart {
    public static final CheckIncompatibleTypeVariableUpperBounds INSTANCE = new CheckIncompatibleTypeVariableUpperBounds();

    private CheckIncompatibleTypeVariableUpperBounds() {
    }

    private final boolean wasPreviouslyDiscriminated(ResolutionCandidate resolutionCandidate, List<? extends KotlinTypeMarker> list) {
        KotlinResolutionStatelessCallbacks statelessCallbacks = resolutionCandidate.getCallComponents().getStatelessCallbacks();
        Intrinsics.checkNotNull(list, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.types.KotlinType>");
        return statelessCallbacks.isOldIntersectionIsEmpty(list);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolutionPart
    public void process(ResolutionCandidate resolutionCandidate, int i) {
        EmptyIntersectionTypeInfo emptyIntersectionTypeKind;
        TypeParameterDescriptor originalTypeParameter;
        Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
        ConstraintSystemCompletionContext constraintSystemCompletionContextAsConstraintSystemCompleterContext = resolutionCandidate.getSystem().asConstraintSystemCompleterContext();
        NewConstraintSystem system = resolutionCandidate.getSystem();
        for (VariableWithConstraints variableWithConstraints : system.getBuilder().currentStorage().getNotFixedTypeVariables().values()) {
            List<KotlinTypeMarker> listExtractUpperTypesToCheckIntersectionEmptiness = constraintSystemCompletionContextAsConstraintSystemCompleterContext.extractUpperTypesToCheckIntersectionEmptiness(variableWithConstraints.getConstraints());
            if (listExtractUpperTypesToCheckIntersectionEmptiness.size() > 1) {
                List<Constraint> constraints = variableWithConstraints.getConstraints();
                if (!(constraints instanceof Collection) || !constraints.isEmpty()) {
                    Iterator<T> it = constraints.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (((Constraint) it.next()).getKind().isLower()) {
                            }
                        }
                    }
                }
                if (INSTANCE.wasPreviouslyDiscriminated(resolutionCandidate, listExtractUpperTypesToCheckIntersectionEmptiness)) {
                    ResolvedCallAtomsKt.markCandidateForCompatibilityResolve(resolutionCandidate, false);
                } else {
                    TypeVariableMarker typeVariable = variableWithConstraints.getTypeVariable();
                    TypeArgument typeArgument = null;
                    TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor = typeVariable instanceof TypeVariableFromCallableDescriptor ? (TypeVariableFromCallableDescriptor) typeVariable : null;
                    if (typeVariableFromCallableDescriptor != null && (originalTypeParameter = typeVariableFromCallableDescriptor.getOriginalTypeParameter()) != null) {
                        typeArgument = resolutionCandidate.getResolvedCall().getTypeArgumentMappingByOriginal().getTypeArgument(originalTypeParameter);
                    }
                    if (!(typeArgument instanceof SimpleTypeArgument) && (emptyIntersectionTypeKind = system.getEmptyIntersectionTypeKind(listExtractUpperTypesToCheckIntersectionEmptiness)) != null) {
                        constraintSystemCompletionContextAsConstraintSystemCompleterContext.addError((ConstraintSystemError) ((Function4) (resolutionCandidate.getCallComponents().getLanguageVersionSettings().supportsFeature(LanguageFeature.ForbidInferringTypeVariablesIntoEmptyIntersection) ? CheckIncompatibleTypeVariableUpperBounds$process$1$errorFactory$1.INSTANCE : CheckIncompatibleTypeVariableUpperBounds$process$1$errorFactory$2.INSTANCE)).invoke(listExtractUpperTypesToCheckIntersectionEmptiness, ArraysKt.toList(emptyIntersectionTypeKind.getCasingTypes()), variableWithConstraints.getTypeVariable(), emptyIntersectionTypeKind.getKind()));
                    }
                }
            }
        }
    }
}
