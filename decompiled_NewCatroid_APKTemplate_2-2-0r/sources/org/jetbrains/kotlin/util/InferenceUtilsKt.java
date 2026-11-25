package org.jetbrains.kotlin.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableTypeConstructor;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;

/* JADX INFO: compiled from: InferenceUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"buildNotFixedVariablesToPossibleResultType", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InferenceUtilsKt {
    public static final TypeSubstitutorMarker buildNotFixedVariablesToPossibleResultType(NewConstraintSystem newConstraintSystem, KotlinResolutionCallbacks resolutionCallbacks) {
        Intrinsics.checkNotNullParameter(newConstraintSystem, "<this>");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        ConstraintSystemCompletionContext constraintSystemCompletionContextAsConstraintSystemCompleterContext = newConstraintSystem.asConstraintSystemCompleterContext();
        Map<TypeConstructorMarker, VariableWithConstraints> notFixedTypeVariables = newConstraintSystem.getBuilder().currentStorage().getNotFixedTypeVariables();
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(notFixedTypeVariables.size()));
        Iterator<T> it = notFixedTypeVariables.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object key2 = entry.getKey();
            Intrinsics.checkNotNull(key2, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableTypeConstructor");
            TypeVariableTypeConstructor typeVariableTypeConstructor = (TypeVariableTypeConstructor) key2;
            SimpleType simpleTypeFindResultType = resolutionCallbacks.findResultType(newConstraintSystem, typeVariableTypeConstructor);
            if (simpleTypeFindResultType == null) {
                simpleTypeFindResultType = TypeVariableKt.typeForTypeVariable(typeVariableTypeConstructor);
            }
            linkedHashMap.put(key, simpleTypeFindResultType);
        }
        return constraintSystemCompletionContextAsConstraintSystemCompleterContext.typeSubstitutorByTypeConstructor(linkedHashMap);
    }
}
