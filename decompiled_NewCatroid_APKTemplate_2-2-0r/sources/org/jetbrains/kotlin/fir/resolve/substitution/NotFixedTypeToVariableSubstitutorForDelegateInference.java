package org.jetbrains.kotlin.fir.resolve.substitution;

import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.types.ConeTypeContext;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: Substitutors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\f\u001a\u00020\u0005H\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/substitution/NotFixedTypeToVariableSubstitutorForDelegateInference;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/AbstractConeSubstitutor;", "bindings", "", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "typeContext", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "(Ljava/util/Map;Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;)V", "getBindings", "()Ljava/util/Map;", "substituteType", ModuleXmlParser.TYPE, "toString", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NotFixedTypeToVariableSubstitutorForDelegateInference extends AbstractConeSubstitutor {
    private final Map<TypeVariableMarker, ConeKotlinType> bindings;

    public final Map<TypeVariableMarker, ConeKotlinType> getBindings() {
        return this.bindings;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public NotFixedTypeToVariableSubstitutorForDelegateInference(Map<TypeVariableMarker, ? extends ConeKotlinType> bindings, ConeTypeContext typeContext) {
        super(typeContext);
        Intrinsics.checkNotNullParameter(bindings, "bindings");
        Intrinsics.checkNotNullParameter(typeContext, "typeContext");
        this.bindings = bindings;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor
    public ConeKotlinType substituteType(ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (!(type instanceof ConeStubType)) {
            return null;
        }
        ConeStubType coneStubType = (ConeStubType) type;
        if (coneStubType.getConstructor().isTypeVariableInSubtyping()) {
            return null;
        }
        return updateNullabilityIfNeeded(this.bindings.get(coneStubType.getConstructor().getVariable()), type);
    }

    public String toString() {
        return CollectionsKt.joinToString$default(this.bindings.entrySet(), " | ", "{", "}", 0, null, new Function1<Map.Entry<? extends TypeVariableMarker, ? extends ConeKotlinType>, CharSequence>() { // from class: org.jetbrains.kotlin.fir.resolve.substitution.NotFixedTypeToVariableSubstitutorForDelegateInference.toString.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(Map.Entry<? extends TypeVariableMarker, ? extends ConeKotlinType> entry) {
                Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                TypeVariableMarker key = entry.getKey();
                ConeKotlinType value = entry.getValue();
                if (!(key instanceof ConeTypeVariable)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                return "TV(" + ((ConeTypeVariable) key).getTypeConstructor().getDebugName() + ") -> " + ConeTypeUtilsKt.renderForDebugging(value);
            }
        }, 24, null);
    }
}
