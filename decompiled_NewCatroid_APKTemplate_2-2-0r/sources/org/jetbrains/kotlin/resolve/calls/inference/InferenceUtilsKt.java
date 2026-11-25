package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;

/* JADX INFO: compiled from: InferenceUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u001a&\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t\u001a\u0012\u0010\f\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011\u001a\u0012\u0010\u0012\u001a\u00020\u0006*\u00020\u00042\u0006\u0010\u0013\u001a\u00020\n\u001a\u0012\u0010\u0014\u001a\u00020\u0006*\u00020\u00042\u0006\u0010\u0015\u001a\u00020\n\u001a\u0012\u0010\u0016\u001a\u00020\u0017*\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a¨\u0006\u001b"}, d2 = {"buildAbstractResultingSubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "transformTypeVariablesToErrorTypes", "", "buildCurrentSubstitutor", "additionalBindings", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "buildNotFixedVariablesToNonSubtypableTypesSubstitutor", "extractTypeForGivenRecursiveTypeParameter", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", ModuleXmlParser.TYPE, "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "hasRecursiveTypeParametersWithGivenSelfType", "selfTypeConstructor", "isRecursiveTypeParameter", "typeConstructor", "registerTypeVariableIfNotPresent", "", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "resolution.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InferenceUtilsKt {
    public static final TypeSubstitutorMarker buildCurrentSubstitutor(ConstraintStorage constraintStorage, TypeSystemInferenceExtensionContext context, Map<TypeConstructorMarker, ? extends StubTypeMarker> additionalBindings) {
        Intrinsics.checkNotNullParameter(constraintStorage, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(additionalBindings, "additionalBindings");
        Set<Map.Entry<TypeConstructorMarker, KotlinTypeMarker>> setEntrySet = constraintStorage.getFixedTypeVariables().entrySet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Pair pair = TuplesKt.to(entry.getKey(), entry.getValue());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return context.typeSubstitutorByTypeConstructor(MapsKt.plus(linkedHashMap, additionalBindings));
    }

    public static /* synthetic */ TypeSubstitutorMarker buildAbstractResultingSubstitutor$default(ConstraintStorage constraintStorage, TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return buildAbstractResultingSubstitutor(constraintStorage, typeSystemInferenceExtensionContext, z);
    }

    public static final TypeSubstitutorMarker buildAbstractResultingSubstitutor(ConstraintStorage constraintStorage, TypeSystemInferenceExtensionContext context, boolean z) {
        LinkedHashMap linkedHashMap;
        Intrinsics.checkNotNullParameter(constraintStorage, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (constraintStorage.getAllTypeVariables().isEmpty()) {
            return context.createEmptySubstitutor();
        }
        Set<Map.Entry<TypeConstructorMarker, KotlinTypeMarker>> setEntrySet = constraintStorage.getFixedTypeVariables().entrySet();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Pair pair = TuplesKt.to(entry.getKey(), entry.getValue());
            linkedHashMap2.put(pair.getFirst(), pair.getSecond());
        }
        if (z) {
            Set<Map.Entry<TypeConstructorMarker, VariableWithConstraints>> setEntrySet2 = constraintStorage.getNotFixedTypeVariables().entrySet();
            linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet2, 10)), 16));
            Iterator<T> it2 = setEntrySet2.iterator();
            while (it2.hasNext()) {
                Map.Entry entry2 = (Map.Entry) it2.next();
                Pair pair2 = TuplesKt.to((TypeConstructorMarker) entry2.getKey(), context.createUninferredType(context.freshTypeConstructor(((VariableWithConstraints) entry2.getValue()).getTypeVariable())));
                linkedHashMap.put(pair2.getFirst(), pair2.getSecond());
            }
        } else {
            Set<Map.Entry<TypeConstructorMarker, VariableWithConstraints>> setEntrySet3 = constraintStorage.getNotFixedTypeVariables().entrySet();
            linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet3, 10)), 16));
            Iterator<T> it3 = setEntrySet3.iterator();
            while (it3.hasNext()) {
                Map.Entry entry3 = (Map.Entry) it3.next();
                Pair pair3 = TuplesKt.to((TypeConstructorMarker) entry3.getKey(), MarkerExtensionsKt.defaultType(((VariableWithConstraints) entry3.getValue()).getTypeVariable(), context));
                linkedHashMap.put(pair3.getFirst(), pair3.getSecond());
            }
        }
        return context.typeSubstitutorByTypeConstructor(MapsKt.plus(linkedHashMap2, linkedHashMap));
    }

    public static final TypeSubstitutorMarker buildNotFixedVariablesToNonSubtypableTypesSubstitutor(ConstraintStorage constraintStorage, TypeSystemInferenceExtensionContext context) {
        Intrinsics.checkNotNullParameter(constraintStorage, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Map<TypeConstructorMarker, VariableWithConstraints> notFixedTypeVariables = constraintStorage.getNotFixedTypeVariables();
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(notFixedTypeVariables.size()));
        Iterator<T> it = notFixedTypeVariables.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(entry.getKey(), context.createStubTypeForTypeVariablesInSubtyping(((VariableWithConstraints) entry.getValue()).getTypeVariable()));
        }
        return context.typeSubstitutorByTypeConstructor(linkedHashMap);
    }

    public static final boolean hasRecursiveTypeParametersWithGivenSelfType(TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext, TypeConstructorMarker selfTypeConstructor) {
        Intrinsics.checkNotNullParameter(typeSystemInferenceExtensionContext, "<this>");
        Intrinsics.checkNotNullParameter(selfTypeConstructor, "selfTypeConstructor");
        List<TypeParameterMarker> parameters = typeSystemInferenceExtensionContext.getParameters(selfTypeConstructor);
        if ((parameters instanceof Collection) && parameters.isEmpty()) {
            return false;
        }
        Iterator<T> it = parameters.iterator();
        while (it.hasNext()) {
            if (typeSystemInferenceExtensionContext.hasRecursiveBounds((TypeParameterMarker) it.next(), selfTypeConstructor)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isRecursiveTypeParameter(TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext, TypeConstructorMarker typeConstructor) {
        Intrinsics.checkNotNullParameter(typeSystemInferenceExtensionContext, "<this>");
        Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
        TypeParameterMarker typeParameterClassifier = typeSystemInferenceExtensionContext.getTypeParameterClassifier(typeConstructor);
        return typeParameterClassifier != null && TypeSystemContext.hasRecursiveBounds$default(typeSystemInferenceExtensionContext, typeParameterClassifier, null, 1, null);
    }

    public static final KotlinTypeMarker extractTypeForGivenRecursiveTypeParameter(TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext, KotlinTypeMarker type, TypeParameterMarker typeParameter) {
        TypeParameterMarker typeParameter2;
        Intrinsics.checkNotNullParameter(typeSystemInferenceExtensionContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        for (TypeArgumentMarker typeArgumentMarker : typeSystemInferenceExtensionContext.getArguments(type)) {
            if (!typeSystemInferenceExtensionContext.isStarProjection(typeArgumentMarker)) {
                TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemInferenceExtensionContext.typeConstructor(typeSystemInferenceExtensionContext.getType(typeArgumentMarker));
                if (typeConstructorMarkerTypeConstructor instanceof TypeVariableTypeConstructorMarker) {
                    TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker = (TypeVariableTypeConstructorMarker) typeConstructorMarkerTypeConstructor;
                    if (Intrinsics.areEqual(typeSystemInferenceExtensionContext.getTypeParameter(typeVariableTypeConstructorMarker), typeParameter) && (typeParameter2 = typeSystemInferenceExtensionContext.getTypeParameter(typeVariableTypeConstructorMarker)) != null && typeSystemInferenceExtensionContext.hasRecursiveBounds(typeParameter2, typeSystemInferenceExtensionContext.typeConstructor(type))) {
                        return type;
                    }
                }
                KotlinTypeMarker kotlinTypeMarkerExtractTypeForGivenRecursiveTypeParameter = extractTypeForGivenRecursiveTypeParameter(typeSystemInferenceExtensionContext, typeSystemInferenceExtensionContext.getType(typeArgumentMarker), typeParameter);
                if (kotlinTypeMarkerExtractTypeForGivenRecursiveTypeParameter != null) {
                    return kotlinTypeMarkerExtractTypeForGivenRecursiveTypeParameter;
                }
            }
        }
        return null;
    }

    public static final void registerTypeVariableIfNotPresent(NewConstraintSystemImpl newConstraintSystemImpl, TypeVariableMarker typeVariable) {
        Intrinsics.checkNotNullParameter(newConstraintSystemImpl, "<this>");
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        NewConstraintSystemImpl builder = newConstraintSystemImpl.getBuilder();
        if (builder.currentStorage().getAllTypeVariables().keySet().contains(MarkerExtensionsKt.freshTypeConstructor(typeVariable, newConstraintSystemImpl))) {
            return;
        }
        builder.registerVariable(typeVariable);
    }
}
