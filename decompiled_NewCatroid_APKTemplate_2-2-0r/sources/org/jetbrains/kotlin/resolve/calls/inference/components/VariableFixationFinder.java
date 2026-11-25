package org.jetbrains.kotlin.resolve.calls.inference.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.calls.inference.InferenceUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.DeclaredUpperBoundConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.IncorporationConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: VariableFixationFinder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001:\u0003234B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J<\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u00112\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u0016\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u0012J\u0014\u0010\u001c\u001a\u00020\b*\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u0012H\u0002J:\u0010\u001e\u001a\u0004\u0018\u00010\r*\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00140\u00112\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u001c\u0010 \u001a\u00020!*\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u00122\u0006\u0010\"\u001a\u00020#H\u0002J\u0014\u0010$\u001a\u00020\b*\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u0012H\u0002J\u0014\u0010%\u001a\u00020\b*\u00020\u000f2\u0006\u0010\u000e\u001a\u00020&H\u0002J\u0014\u0010'\u001a\u00020\b*\u00020\u000f2\u0006\u0010(\u001a\u00020\u0018H\u0002J\u0014\u0010)\u001a\u00020\b*\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u0012H\u0002J\u0014\u0010*\u001a\u00020\b*\u00020\u000f2\u0006\u0010+\u001a\u00020&H\u0002J\u0014\u0010,\u001a\u00020\b*\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u0012H\u0002J\u0014\u0010-\u001a\u00020\b*\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u0012H\u0002J\u0014\u0010.\u001a\u00020\b*\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u0012H\u0002J\u0014\u0010/\u001a\u00020\b*\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u0012H\u0002J\u0014\u00100\u001a\u00020\b*\u00020\u000f2\u0006\u00101\u001a\u00020\u0012H\u0002R\u0014\u0010\u0007\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder;", "", "trivialConstraintTypeInferenceOracle", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/TrivialConstraintTypeInferenceOracle;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "inferenceCompatibilityModeEnabled", "", "getInferenceCompatibilityModeEnabled", "()Z", "isTypeInferenceForSelfTypesSupported", "findFirstVariableForFixation", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder$VariableForFixation;", "c", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder$Context;", "allTypeVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "postponedKtPrimitives", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedResolvedAtomMarker;", "completionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "topLevelType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "isTypeVariableHasProperConstraint", "context", "typeVariable", "areAllProperConstraintsSelfTypeBased", "variable", "findTypeVariableForFixation", "postponedArguments", "getTypeVariableReadiness", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder$TypeVariableFixationReadiness;", "dependencyProvider", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/TypeVariableDependencyInformationProvider;", "hasDependencyToOtherTypeVariables", "isProperArgumentConstraint", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "isProperType", ModuleXmlParser.TYPE, "isReified", "isSelfTypeConstraint", "constraint", "variableHasLowerNonNothingProperConstraint", "variableHasOnlyIncorporatedConstraintsFromDeclaredUpperBound", "variableHasProperArgumentConstraints", "variableHasTrivialOrNonProperConstraints", "variableHasUnprocessedConstraintsInForks", "variableConstructor", "Context", "TypeVariableFixationReadiness", "VariableForFixation", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VariableFixationFinder {
    private final LanguageVersionSettings languageVersionSettings;
    private final TrivialConstraintTypeInferenceOracle trivialConstraintTypeInferenceOracle;

    /* JADX INFO: compiled from: VariableFixationFinder.kt */
    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\bH&RD\u0010\u0002\u001a4\u00120\u0012.\u0012\u0004\u0012\u00020\u0005\u0012$\u0012\"\u0012\u001a\u0012\u0018\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u00040\u0007j\u0002`\n0\u0006j\u0002`\u000b0\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001e\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00150\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0013R\u0018\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\rø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001cÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder$Context;", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "constraintsFromAllForkPoints", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/IncorporationConstraintPosition;", "", "", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/Constraint;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ForkPointBranchDescription;", "Lorg/jetbrains/kotlin/resolve/calls/inference/ForkPointData;", "getConstraintsFromAllForkPoints", "()Ljava/util/List;", "fixedTypeVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getFixedTypeVariables", "()Ljava/util/Map;", "notFixedTypeVariables", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/VariableWithConstraints;", "getNotFixedTypeVariables", "postponedTypeVariables", "getPostponedTypeVariables", "isReified", "", "variable", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface Context extends TypeSystemInferenceExtensionContext {
        List<Pair<IncorporationConstraintPosition, List<Set<Pair<TypeVariableMarker, Constraint>>>>> getConstraintsFromAllForkPoints();

        Map<TypeConstructorMarker, KotlinTypeMarker> getFixedTypeVariables();

        Map<TypeConstructorMarker, VariableWithConstraints> getNotFixedTypeVariables();

        List<TypeVariableMarker> getPostponedTypeVariables();

        boolean isReified(TypeVariableMarker variable);
    }

    /* JADX INFO: compiled from: VariableFixationFinder.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\r\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder$TypeVariableFixationReadiness;", "", "(Ljava/lang/String;I)V", "FORBIDDEN", "WITHOUT_PROPER_ARGUMENT_CONSTRAINT", "READY_FOR_FIXATION_DECLARED_UPPER_BOUND_WITH_SELF_TYPES", "WITH_COMPLEX_DEPENDENCY", "WITH_TRIVIAL_OR_NON_PROPER_CONSTRAINTS", "RELATED_TO_ANY_OUTPUT_TYPE", "FROM_INCORPORATION_OF_DECLARED_UPPER_BOUND", "READY_FOR_FIXATION_UPPER", "READY_FOR_FIXATION_LOWER", "READY_FOR_FIXATION", "READY_FOR_FIXATION_REIFIED", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum TypeVariableFixationReadiness {
        FORBIDDEN,
        WITHOUT_PROPER_ARGUMENT_CONSTRAINT,
        READY_FOR_FIXATION_DECLARED_UPPER_BOUND_WITH_SELF_TYPES,
        WITH_COMPLEX_DEPENDENCY,
        WITH_TRIVIAL_OR_NON_PROPER_CONSTRAINTS,
        RELATED_TO_ANY_OUTPUT_TYPE,
        FROM_INCORPORATION_OF_DECLARED_UPPER_BOUND,
        READY_FOR_FIXATION_UPPER,
        READY_FOR_FIXATION_LOWER,
        READY_FOR_FIXATION,
        READY_FOR_FIXATION_REIFIED;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<TypeVariableFixationReadiness> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: VariableFixationFinder.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TypeVariableFixationReadiness.values().length];
            try {
                iArr[TypeVariableFixationReadiness.FORBIDDEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeVariableFixationReadiness.WITHOUT_PROPER_ARGUMENT_CONSTRAINT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeVariableFixationReadiness.WITH_TRIVIAL_OR_NON_PROPER_CONSTRAINTS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public VariableFixationFinder(TrivialConstraintTypeInferenceOracle trivialConstraintTypeInferenceOracle, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(trivialConstraintTypeInferenceOracle, "trivialConstraintTypeInferenceOracle");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.trivialConstraintTypeInferenceOracle = trivialConstraintTypeInferenceOracle;
        this.languageVersionSettings = languageVersionSettings;
    }

    /* JADX INFO: compiled from: VariableFixationFinder.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00052\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/VariableFixationFinder$VariableForFixation;", "", "variable", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "hasProperConstraint", "", "hasOnlyTrivialProperConstraint", "(Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;ZZ)V", "getHasOnlyTrivialProperConstraint", "()Z", "getHasProperConstraint", "getVariable", "()Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class VariableForFixation {
        private final boolean hasOnlyTrivialProperConstraint;
        private final boolean hasProperConstraint;
        private final TypeConstructorMarker variable;

        public static /* synthetic */ VariableForFixation copy$default(VariableForFixation variableForFixation, TypeConstructorMarker typeConstructorMarker, boolean z, boolean z2, int i, Object obj) {
            if ((i & 1) != 0) {
                typeConstructorMarker = variableForFixation.variable;
            }
            if ((i & 2) != 0) {
                z = variableForFixation.hasProperConstraint;
            }
            if ((i & 4) != 0) {
                z2 = variableForFixation.hasOnlyTrivialProperConstraint;
            }
            return variableForFixation.copy(typeConstructorMarker, z, z2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final TypeConstructorMarker getVariable() {
            return this.variable;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getHasProperConstraint() {
            return this.hasProperConstraint;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final boolean getHasOnlyTrivialProperConstraint() {
            return this.hasOnlyTrivialProperConstraint;
        }

        public final VariableForFixation copy(TypeConstructorMarker variable, boolean hasProperConstraint, boolean hasOnlyTrivialProperConstraint) {
            Intrinsics.checkNotNullParameter(variable, "variable");
            return new VariableForFixation(variable, hasProperConstraint, hasOnlyTrivialProperConstraint);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof VariableForFixation)) {
                return false;
            }
            VariableForFixation variableForFixation = (VariableForFixation) other;
            return Intrinsics.areEqual(this.variable, variableForFixation.variable) && this.hasProperConstraint == variableForFixation.hasProperConstraint && this.hasOnlyTrivialProperConstraint == variableForFixation.hasOnlyTrivialProperConstraint;
        }

        public int hashCode() {
            return (((this.variable.hashCode() * 31) + Boolean.hashCode(this.hasProperConstraint)) * 31) + Boolean.hashCode(this.hasOnlyTrivialProperConstraint);
        }

        public String toString() {
            return "VariableForFixation(variable=" + this.variable + ", hasProperConstraint=" + this.hasProperConstraint + ", hasOnlyTrivialProperConstraint=" + this.hasOnlyTrivialProperConstraint + ')';
        }

        public VariableForFixation(TypeConstructorMarker variable, boolean z, boolean z2) {
            Intrinsics.checkNotNullParameter(variable, "variable");
            this.variable = variable;
            this.hasProperConstraint = z;
            this.hasOnlyTrivialProperConstraint = z2;
        }

        public /* synthetic */ VariableForFixation(TypeConstructorMarker typeConstructorMarker, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(typeConstructorMarker, z, (i & 4) != 0 ? false : z2);
        }

        public final TypeConstructorMarker getVariable() {
            return this.variable;
        }

        public final boolean getHasProperConstraint() {
            return this.hasProperConstraint;
        }

        public final boolean getHasOnlyTrivialProperConstraint() {
            return this.hasOnlyTrivialProperConstraint;
        }
    }

    public final VariableForFixation findFirstVariableForFixation(Context c, List<? extends TypeConstructorMarker> allTypeVariables, List<? extends PostponedResolvedAtomMarker> postponedKtPrimitives, ConstraintSystemCompletionMode completionMode, KotlinTypeMarker topLevelType) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(allTypeVariables, "allTypeVariables");
        Intrinsics.checkNotNullParameter(postponedKtPrimitives, "postponedKtPrimitives");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        Intrinsics.checkNotNullParameter(topLevelType, "topLevelType");
        return findTypeVariableForFixation(c, allTypeVariables, postponedKtPrimitives, completionMode, topLevelType);
    }

    private final boolean getInferenceCompatibilityModeEnabled() {
        return this.languageVersionSettings.supportsFeature(LanguageFeature.InferenceCompatibility);
    }

    private final boolean isTypeInferenceForSelfTypesSupported() {
        return this.languageVersionSettings.supportsFeature(LanguageFeature.TypeInferenceOnCallsWithSelfTypes);
    }

    private final TypeVariableFixationReadiness getTypeVariableReadiness(Context context, TypeConstructorMarker typeConstructorMarker, TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider) {
        if (!context.getNotFixedTypeVariables().containsKey(typeConstructorMarker) || typeVariableDependencyInformationProvider.isVariableRelatedToTopLevelType(typeConstructorMarker) || variableHasUnprocessedConstraintsInForks(context, typeConstructorMarker)) {
            return TypeVariableFixationReadiness.FORBIDDEN;
        }
        if (isTypeInferenceForSelfTypesSupported() && areAllProperConstraintsSelfTypeBased(context, typeConstructorMarker)) {
            return TypeVariableFixationReadiness.READY_FOR_FIXATION_DECLARED_UPPER_BOUND_WITH_SELF_TYPES;
        }
        if (!variableHasProperArgumentConstraints(context, typeConstructorMarker)) {
            return TypeVariableFixationReadiness.WITHOUT_PROPER_ARGUMENT_CONSTRAINT;
        }
        if (hasDependencyToOtherTypeVariables(context, typeConstructorMarker)) {
            return TypeVariableFixationReadiness.WITH_COMPLEX_DEPENDENCY;
        }
        if (variableHasTrivialOrNonProperConstraints(context, typeConstructorMarker)) {
            return TypeVariableFixationReadiness.WITH_TRIVIAL_OR_NON_PROPER_CONSTRAINTS;
        }
        if (typeVariableDependencyInformationProvider.isVariableRelatedToAnyOutputType(typeConstructorMarker)) {
            return TypeVariableFixationReadiness.RELATED_TO_ANY_OUTPUT_TYPE;
        }
        if (variableHasOnlyIncorporatedConstraintsFromDeclaredUpperBound(context, typeConstructorMarker)) {
            return TypeVariableFixationReadiness.FROM_INCORPORATION_OF_DECLARED_UPPER_BOUND;
        }
        if (isReified(context, typeConstructorMarker)) {
            return TypeVariableFixationReadiness.READY_FOR_FIXATION_REIFIED;
        }
        if (getInferenceCompatibilityModeEnabled()) {
            return variableHasLowerNonNothingProperConstraint(context, typeConstructorMarker) ? TypeVariableFixationReadiness.READY_FOR_FIXATION_LOWER : TypeVariableFixationReadiness.READY_FOR_FIXATION_UPPER;
        }
        return TypeVariableFixationReadiness.READY_FOR_FIXATION;
    }

    private final boolean variableHasUnprocessedConstraintsInForks(Context context, TypeConstructorMarker typeConstructorMarker) {
        if (context.getConstraintsFromAllForkPoints().isEmpty()) {
            return false;
        }
        Iterator<Pair<IncorporationConstraintPosition, List<Set<Pair<TypeVariableMarker, Constraint>>>>> it = context.getConstraintsFromAllForkPoints().iterator();
        while (it.hasNext()) {
            Iterator<Set<Pair<TypeVariableMarker, Constraint>>> it2 = it.next().component2().iterator();
            while (it2.hasNext()) {
                for (Pair<TypeVariableMarker, Constraint> pair : it2.next()) {
                    TypeVariableMarker typeVariableMarkerComponent1 = pair.component1();
                    Constraint constraintComponent2 = pair.component2();
                    if (Intrinsics.areEqual(context.freshTypeConstructor(typeVariableMarkerComponent1), typeConstructorMarker) || VariableFixationFinderKt.containsTypeVariable(context, constraintComponent2.getType(), typeConstructorMarker)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final boolean isTypeVariableHasProperConstraint(Context context, TypeConstructorMarker typeVariable) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        int i = WhenMappings.$EnumSwitchMapping$0[getTypeVariableReadiness(context, typeVariable, new TypeVariableDependencyInformationProvider(context.getNotFixedTypeVariables(), CollectionsKt.emptyList(), null, context)).ordinal()];
        return (i == 1 || i == 2) ? false : true;
    }

    private final boolean variableHasTrivialOrNonProperConstraints(Context context, TypeConstructorMarker typeConstructorMarker) {
        List<Constraint> constraints;
        VariableWithConstraints variableWithConstraints = context.getNotFixedTypeVariables().get(typeConstructorMarker);
        if (variableWithConstraints == null || (constraints = variableWithConstraints.getConstraints()) == null) {
            return false;
        }
        List<Constraint> list = constraints;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            for (Constraint constraint : list) {
                boolean zIsProperArgumentConstraint = isProperArgumentConstraint(context, constraint);
                if (!zIsProperArgumentConstraint || !this.trivialConstraintTypeInferenceOracle.isNotInterestingConstraint(constraint)) {
                    if (zIsProperArgumentConstraint) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private final boolean variableHasOnlyIncorporatedConstraintsFromDeclaredUpperBound(Context context, TypeConstructorMarker typeConstructorMarker) {
        List<Constraint> constraints;
        VariableWithConstraints variableWithConstraints = context.getNotFixedTypeVariables().get(typeConstructorMarker);
        if (variableWithConstraints == null || (constraints = variableWithConstraints.getConstraints()) == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : constraints) {
            if (isProperArgumentConstraint(context, (Constraint) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                if (!((Constraint) it.next()).getPosition().isFromDeclaredUpperBound()) {
                    return false;
                }
            }
        }
        return true;
    }

    private final VariableForFixation findTypeVariableForFixation(Context context, List<? extends TypeConstructorMarker> list, List<? extends PostponedResolvedAtomMarker> list2, ConstraintSystemCompletionMode constraintSystemCompletionMode, KotlinTypeMarker kotlinTypeMarker) {
        Object next;
        if (list.isEmpty()) {
            return null;
        }
        Map<TypeConstructorMarker, VariableWithConstraints> notFixedTypeVariables = context.getNotFixedTypeVariables();
        if (constraintSystemCompletionMode != ConstraintSystemCompletionMode.PARTIAL) {
            kotlinTypeMarker = null;
        }
        TypeVariableDependencyInformationProvider typeVariableDependencyInformationProvider = new TypeVariableDependencyInformationProvider(notFixedTypeVariables, list2, kotlinTypeMarker, context);
        Iterator<T> it = list.iterator();
        if (it.hasNext()) {
            next = it.next();
            if (it.hasNext()) {
                TypeVariableFixationReadiness typeVariableReadiness = getTypeVariableReadiness(context, (TypeConstructorMarker) next, typeVariableDependencyInformationProvider);
                do {
                    Object next2 = it.next();
                    TypeVariableFixationReadiness typeVariableReadiness2 = getTypeVariableReadiness(context, (TypeConstructorMarker) next2, typeVariableDependencyInformationProvider);
                    if (typeVariableReadiness.compareTo(typeVariableReadiness2) < 0) {
                        next = next2;
                        typeVariableReadiness = typeVariableReadiness2;
                    }
                } while (it.hasNext());
            }
        } else {
            next = null;
        }
        TypeConstructorMarker typeConstructorMarker = (TypeConstructorMarker) next;
        if (typeConstructorMarker == null) {
            return null;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[getTypeVariableReadiness(context, typeConstructorMarker, typeVariableDependencyInformationProvider).ordinal()];
        if (i == 1) {
            return null;
        }
        if (i == 2) {
            return new VariableForFixation(typeConstructorMarker, false, false, 4, null);
        }
        if (i == 3) {
            return new VariableForFixation(typeConstructorMarker, true, true);
        }
        return new VariableForFixation(typeConstructorMarker, true, false, 4, null);
    }

    private final boolean hasDependencyToOtherTypeVariables(final Context context, final TypeConstructorMarker typeConstructorMarker) {
        List<Constraint> constraints;
        VariableWithConstraints variableWithConstraints = context.getNotFixedTypeVariables().get(typeConstructorMarker);
        if (variableWithConstraints != null && (constraints = variableWithConstraints.getConstraints()) != null) {
            for (Constraint constraint : constraints) {
                Function1<KotlinTypeMarker, Boolean> function1 = new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinder$hasDependencyToOtherTypeVariables$dependencyPresenceCondition$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(KotlinTypeMarker type) {
                        Intrinsics.checkNotNullParameter(type, "type");
                        return Boolean.valueOf(!Intrinsics.areEqual(context.typeConstructor(type), typeConstructorMarker) && context.getNotFixedTypeVariables().containsKey(context.typeConstructor(type)));
                    }
                };
                if (context.argumentsCount(context.lowerBoundIfFlexible(constraint.getType())) != 0 && context.contains(constraint.getType(), function1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean variableHasProperArgumentConstraints(final Context context, TypeConstructorMarker typeConstructorMarker) {
        List<Constraint> constraints;
        boolean z;
        VariableWithConstraints variableWithConstraints = context.getNotFixedTypeVariables().get(typeConstructorMarker);
        if (variableWithConstraints == null || (constraints = variableWithConstraints.getConstraints()) == null) {
            return false;
        }
        List<Constraint> list = constraints;
        boolean z2 = list instanceof Collection;
        if (!z2 || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (context.contains(((Constraint) it.next()).getType(), new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinder$variableHasProperArgumentConstraints$areThereConstraintsWithUninferredTypeParameter$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(KotlinTypeMarker it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return Boolean.valueOf(context.isUninferredParameter(it2));
                    }
                })) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        if (z2 && list.isEmpty()) {
            return false;
        }
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            if (isProperArgumentConstraint(context, (Constraint) it2.next())) {
                return !z;
            }
        }
        return false;
    }

    private final boolean isProperArgumentConstraint(Context context, Constraint constraint) {
        return (!isProperType(context, constraint.getType()) || (constraint.getPosition().getInitialConstraint().getPosition() instanceof DeclaredUpperBoundConstraintPosition) || constraint.getIsNullabilityConstraint()) ? false : true;
    }

    private final boolean isProperType(final Context context, KotlinTypeMarker kotlinTypeMarker) {
        Context context2 = context;
        if (!context.contains(kotlinTypeMarker, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinder$isProperType$1$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinTypeMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(context.getNotFixedTypeVariables().containsKey(context.typeConstructor(it)));
            }
        })) {
            Set<KotlinTypeMarker> setExtractProjectionsForAllCapturedTypes = VariableFixationFinderKt.extractProjectionsForAllCapturedTypes(context2, kotlinTypeMarker);
            if (!(setExtractProjectionsForAllCapturedTypes instanceof Collection) || !setExtractProjectionsForAllCapturedTypes.isEmpty()) {
                Iterator<T> it = setExtractProjectionsForAllCapturedTypes.iterator();
                while (it.hasNext()) {
                    if (context.contains((KotlinTypeMarker) it.next(), new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinder$isProperType$1$1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(KotlinTypeMarker it2) {
                            Intrinsics.checkNotNullParameter(it2, "it");
                            return Boolean.valueOf(context.getNotFixedTypeVariables().containsKey(context.typeConstructor(it2)));
                        }
                    })) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    private final boolean isReified(Context context, TypeConstructorMarker typeConstructorMarker) {
        TypeVariableMarker typeVariable;
        VariableWithConstraints variableWithConstraints = context.getNotFixedTypeVariables().get(typeConstructorMarker);
        if (variableWithConstraints == null || (typeVariable = variableWithConstraints.getTypeVariable()) == null) {
            return false;
        }
        return context.isReified(typeVariable);
    }

    private final boolean variableHasLowerNonNothingProperConstraint(Context context, TypeConstructorMarker typeConstructorMarker) {
        List<Constraint> constraints;
        VariableWithConstraints variableWithConstraints = context.getNotFixedTypeVariables().get(typeConstructorMarker);
        if (variableWithConstraints == null || (constraints = variableWithConstraints.getConstraints()) == null) {
            return false;
        }
        List<Constraint> list = constraints;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (Constraint constraint : list) {
            if (constraint.getKind().isLower() && isProperArgumentConstraint(context, constraint) && !context.isNothingConstructor(context.typeConstructor(constraint.getType()))) {
                return true;
            }
        }
        return false;
    }

    private final boolean isSelfTypeConstraint(Context context, Constraint constraint) {
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = context.typeConstructor(constraint.getType());
        if (constraint.getPosition().getFrom() instanceof DeclaredUpperBoundConstraintPosition) {
            Context context2 = context;
            if (InferenceUtilsKt.hasRecursiveTypeParametersWithGivenSelfType(context2, typeConstructorMarkerTypeConstructor) || InferenceUtilsKt.isRecursiveTypeParameter(context2, typeConstructorMarkerTypeConstructor)) {
                return true;
            }
        }
        return false;
    }

    private final boolean areAllProperConstraintsSelfTypeBased(Context context, TypeConstructorMarker typeConstructorMarker) {
        List<Constraint> constraints;
        VariableWithConstraints variableWithConstraints = context.getNotFixedTypeVariables().get(typeConstructorMarker);
        if (variableWithConstraints == null || (constraints = variableWithConstraints.getConstraints()) == null) {
            return false;
        }
        if (constraints.isEmpty()) {
            constraints = null;
        }
        if (constraints == null) {
            return false;
        }
        boolean z = false;
        boolean z2 = false;
        for (Constraint constraint : constraints) {
            if (isSelfTypeConstraint(context, constraint)) {
                z = true;
            }
            if (isProperArgumentConstraint(context, constraint)) {
                z2 = true;
            }
            if (z && z2) {
                break;
            }
        }
        return z && !z2;
    }
}
