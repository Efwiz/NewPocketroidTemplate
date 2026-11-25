package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: ConstraintSystemBuilder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H&J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H&J \u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H&J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0019\u001a\u00020\u001aH&J,\u0010\u0018\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u001b\u001a\u00020\u001a2\u0018\u0010\u001c\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001e0\u001d0\u0003H&J\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00032\u0006\u0010 \u001a\u00020\u000eH&J\u0010\u0010!\u001a\u00020\b2\u0006\u0010\"\u001a\u00020#H&J\u0010\u0010$\u001a\u00020\b2\u0006\u0010 \u001a\u00020\u000eH&J\u0010\u0010%\u001a\u00020\b2\u0006\u0010 \u001a\u00020\u000eH&J\b\u0010&\u001a\u00020\fH&J\u0010\u0010'\u001a\u00020\f2\u0006\u0010(\u001a\u00020#H&J2\u0010)\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001a2\u0018\u0010\u001c\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001e0\u001d0\u00032\u0006\u0010*\u001a\u00020\u000eH&J\u0018\u0010)\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010*\u001a\u00020\u000eH&J\u0010\u0010+\u001a\u00020\f2\u0006\u0010(\u001a\u00020#H&J\b\u0010,\u001a\u00020\fH&J\u0010\u0010-\u001a\u00020\f2\u0006\u0010.\u001a\u00020/H&J\u0010\u00100\u001a\u00020\f2\u0006\u0010(\u001a\u00020#H&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u00061À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemOperation;", "", "errors", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "getErrors", "()Ljava/util/List;", "hasContradiction", "", "getHasContradiction", "()Z", "addEqualityConstraint", "", "a", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "addOtherSystem", "otherSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "addSubtypeConstraint", "lowerType", "upperType", "getBuiltFunctionalExpectedTypeForPostponedArgument", "expectedTypeVariable", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "topLevelVariable", "pathToExpectedType", "Lkotlin/Pair;", "", "getProperSuperTypeConstructors", ModuleXmlParser.TYPE, "isPostponedTypeVariable", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "isProperType", "isTypeVariable", "markCouldBeResolvedWithUnrestrictedBuilderInference", "markPostponedVariable", "variable", "putBuiltFunctionalExpectedTypeForPostponedArgument", "builtFunctionalType", "registerVariable", "removePostponedVariables", "substituteFixedVariables", "substitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "unmarkPostponedVariable", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ConstraintSystemOperation {
    void addEqualityConstraint(KotlinTypeMarker a, KotlinTypeMarker b, ConstraintPosition position);

    void addOtherSystem(ConstraintStorage otherSystem);

    void addSubtypeConstraint(KotlinTypeMarker lowerType, KotlinTypeMarker upperType, ConstraintPosition position);

    KotlinTypeMarker getBuiltFunctionalExpectedTypeForPostponedArgument(TypeConstructorMarker expectedTypeVariable);

    KotlinTypeMarker getBuiltFunctionalExpectedTypeForPostponedArgument(TypeConstructorMarker topLevelVariable, List<? extends Pair<? extends TypeConstructorMarker, Integer>> pathToExpectedType);

    List<ConstraintSystemError> getErrors();

    boolean getHasContradiction();

    List<TypeConstructorMarker> getProperSuperTypeConstructors(KotlinTypeMarker type);

    boolean isPostponedTypeVariable(TypeVariableMarker typeVariable);

    boolean isProperType(KotlinTypeMarker type);

    boolean isTypeVariable(KotlinTypeMarker type);

    void markCouldBeResolvedWithUnrestrictedBuilderInference();

    void markPostponedVariable(TypeVariableMarker variable);

    void putBuiltFunctionalExpectedTypeForPostponedArgument(TypeConstructorMarker topLevelVariable, List<? extends Pair<? extends TypeConstructorMarker, Integer>> pathToExpectedType, KotlinTypeMarker builtFunctionalType);

    void putBuiltFunctionalExpectedTypeForPostponedArgument(TypeConstructorMarker expectedTypeVariable, KotlinTypeMarker builtFunctionalType);

    void registerVariable(TypeVariableMarker variable);

    void removePostponedVariables();

    void substituteFixedVariables(TypeSubstitutorMarker substitutor);

    void unmarkPostponedVariable(TypeVariableMarker variable);
}
