package org.jetbrains.kotlin.resolve.calls.components;

import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: PostponedArgumentsAnalyzerContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0014\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007H&J\u001c\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\t0\u0007H&J\b\u0010\u000e\u001a\u00020\u000bH&J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H&J\u0016\u0010\u0016\u001a\u00020\u00032\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\r0\u0018H&J\b\u0010\u0019\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001aÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzerContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "addOtherSystem", "", "otherSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "bindingStubsForPostponedVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "buildCurrentSubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "additionalBindings", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "buildNotFixedVariablesToStubTypesSubstitutor", "canBeProper", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "hasUpperOrEqualUnitConstraint", "removePostponedTypeVariablesFromConstraints", "postponedTypeVariables", "", "resolveForkPointsConstraints", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PostponedArgumentsAnalyzerContext extends TypeSystemInferenceExtensionContext {
    void addOtherSystem(ConstraintStorage otherSystem);

    Map<TypeVariableMarker, StubTypeMarker> bindingStubsForPostponedVariables();

    TypeSubstitutorMarker buildCurrentSubstitutor(Map<TypeConstructorMarker, ? extends StubTypeMarker> additionalBindings);

    TypeSubstitutorMarker buildNotFixedVariablesToStubTypesSubstitutor();

    boolean canBeProper(KotlinTypeMarker type);

    ConstraintSystemBuilder getBuilder();

    boolean hasUpperOrEqualUnitConstraint(KotlinTypeMarker type);

    void removePostponedTypeVariablesFromConstraints(Set<? extends TypeConstructorMarker> postponedTypeVariables);

    void resolveForkPointsConstraints();
}
