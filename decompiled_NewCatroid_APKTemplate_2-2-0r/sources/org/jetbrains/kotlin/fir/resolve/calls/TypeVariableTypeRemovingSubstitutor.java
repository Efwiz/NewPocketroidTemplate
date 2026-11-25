package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnknownLambdaParameterTypeDiagnostic;
import org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeContext;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableType;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;

/* JADX INFO: compiled from: TypeVariableTypeRemovingSubstitutor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0012\u0010\t\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/TypeVariableTypeRemovingSubstitutor;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/AbstractConeSubstitutor;", "typeContext", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "(Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;)V", "convertTypeVariableType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariableType;", "substituteType", "toString", "", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TypeVariableTypeRemovingSubstitutor extends AbstractConeSubstitutor {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TypeVariableTypeRemovingSubstitutor(ConeTypeContext typeContext) {
        super(typeContext);
        Intrinsics.checkNotNullParameter(typeContext, "typeContext");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor
    public ConeKotlinType substituteType(ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        if (type instanceof ConeTypeVariableType) {
            return convertTypeVariableType((ConeTypeVariableType) type);
        }
        return null;
    }

    private final ConeKotlinType convertTypeVariableType(ConeTypeVariableType type) {
        TypeParameterMarker originalTypeParameter = type.getLookupTag().getOriginalTypeParameter();
        if (originalTypeParameter != null) {
            if (!(originalTypeParameter instanceof ConeTypeParameterLookupTag)) {
                throw new IllegalStateException("Check failed.".toString());
            }
            return new ConeTypeParameterTypeImpl((ConeTypeParameterLookupTag) originalTypeParameter, ConeTypeUtilsKt.isNullable(type), type.getAttributes());
        }
        return new ConeErrorType(new ConeUnknownLambdaParameterTypeDiagnostic(), false, null, null, 14, null);
    }

    public String toString() {
        return "{<Type variable> -> <Error type>}";
    }
}
