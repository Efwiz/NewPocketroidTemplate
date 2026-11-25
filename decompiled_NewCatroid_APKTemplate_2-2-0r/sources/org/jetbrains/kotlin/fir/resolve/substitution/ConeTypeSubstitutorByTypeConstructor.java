package org.jetbrains.kotlin.fir.resolve.substitution;

import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralTypeImplKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.types.ConeTypeContext;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContextHelpersKt;

/* JADX INFO: compiled from: Substitutors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B)\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0012\u0010\f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\r\u001a\u00020\u0006H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeTypeSubstitutorByTypeConstructor;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/AbstractConeSubstitutor;", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "map", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "typeContext", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "approximateIntegerLiterals", "", "(Ljava/util/Map;Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;Z)V", "substituteType", ModuleXmlParser.TYPE, "toString", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeTypeSubstitutorByTypeConstructor extends AbstractConeSubstitutor implements TypeSubstitutorMarker {
    private final boolean approximateIntegerLiterals;
    private final Map<TypeConstructorMarker, ConeKotlinType> map;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ConeTypeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends ConeKotlinType> map, ConeTypeContext typeContext, boolean z) {
        super(typeContext);
        Intrinsics.checkNotNullParameter(map, "map");
        Intrinsics.checkNotNullParameter(typeContext, "typeContext");
        this.map = map;
        this.approximateIntegerLiterals = z;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor
    public ConeKotlinType substituteType(ConeKotlinType type) {
        ConeKotlinType coneKotlinTypeApproximateIntegerLiteralType$default;
        Intrinsics.checkNotNullParameter(type, "type");
        if ((!(type instanceof ConeLookupTagBasedType) && !(type instanceof ConeStubType)) || (coneKotlinTypeApproximateIntegerLiteralType$default = this.map.get(TypeSystemContextHelpersKt.typeConstructor(type, getTypeContext()))) == null) {
            return null;
        }
        if (this.approximateIntegerLiterals) {
            coneKotlinTypeApproximateIntegerLiteralType$default = ConeIntegerLiteralTypeImplKt.approximateIntegerLiteralType$default(coneKotlinTypeApproximateIntegerLiteralType$default, null, 1, null);
        }
        ConeKotlinType coneKotlinTypeUpdateNullabilityIfNeeded = updateNullabilityIfNeeded(coneKotlinTypeApproximateIntegerLiteralType$default, type);
        if (coneKotlinTypeUpdateNullabilityIfNeeded != null) {
            return LookupTagUtilsKt.withCombinedAttributesFrom(coneKotlinTypeUpdateNullabilityIfNeeded, type);
        }
        return null;
    }

    public String toString() {
        return CollectionsKt.joinToString$default(this.map.entrySet(), " | ", "{", "}", 0, null, new Function1<Map.Entry<? extends TypeConstructorMarker, ? extends ConeKotlinType>, CharSequence>() { // from class: org.jetbrains.kotlin.fir.resolve.substitution.ConeTypeSubstitutorByTypeConstructor.toString.1
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(Map.Entry<? extends TypeConstructorMarker, ? extends ConeKotlinType> entry) {
                Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                return entry.getKey() + " -> " + ConeTypeUtilsKt.renderForDebugging(entry.getValue());
            }
        }, 24, null);
    }
}
