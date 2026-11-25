package org.jetbrains.kotlin.fir.resolve.substitution;

import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Substitutors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0096\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0016\u001a\u00020\u0005H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutorByMap;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/AbstractConeSubstitutor;", SchemaSymbols.ATTVAL_SUBSTITUTION, "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Ljava/util/Map;Lorg/jetbrains/kotlin/fir/FirSession;)V", "hashCode", "", "getHashCode", "()I", "hashCode$delegate", "Lkotlin/Lazy;", "getSubstitution", "()Ljava/util/Map;", Namer.EQUALS_METHOD_NAME, "", "other", "", "substituteType", ModuleXmlParser.TYPE, "toString", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeSubstitutorByMap extends AbstractConeSubstitutor {

    /* JADX INFO: renamed from: hashCode$delegate, reason: from kotlin metadata */
    private final Lazy hashCode;
    private final Map<FirTypeParameterSymbol, ConeKotlinType> substitution;
    private final FirSession useSiteSession;

    public final Map<FirTypeParameterSymbol, ConeKotlinType> getSubstitution() {
        return this.substitution;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ConeSubstitutorByMap(Map<FirTypeParameterSymbol, ? extends ConeKotlinType> substitution, FirSession useSiteSession) {
        super(TypeComponentsKt.getTypeContext(useSiteSession));
        Intrinsics.checkNotNullParameter(substitution, "substitution");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        this.substitution = substitution;
        this.useSiteSession = useSiteSession;
        this.hashCode = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Integer>() { // from class: org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutorByMap.hashCode.2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Integer invoke() {
                return Integer.valueOf(ConeSubstitutorByMap.this.getSubstitution().hashCode());
            }
        });
    }

    private final int getHashCode() {
        return ((Number) this.hashCode.getValue()).intValue();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.substitution.AbstractConeSubstitutor
    public ConeKotlinType substituteType(ConeKotlinType type) {
        ConeKotlinType coneKotlinTypeUpdateNullabilityIfNeeded;
        ConeKotlinType coneKotlinTypeWithCombinedAttributesFrom;
        Intrinsics.checkNotNullParameter(type, "type");
        if (!(type instanceof ConeTypeParameterType) || (coneKotlinTypeUpdateNullabilityIfNeeded = updateNullabilityIfNeeded(this.substitution.get(((ConeTypeParameterType) type).getLookupTag().getSymbol()), type)) == null || (coneKotlinTypeWithCombinedAttributesFrom = LookupTagUtilsKt.withCombinedAttributesFrom(coneKotlinTypeUpdateNullabilityIfNeeded, type)) == null) {
            return null;
        }
        return coneKotlinTypeWithCombinedAttributesFrom;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConeSubstitutorByMap)) {
            return false;
        }
        ConeSubstitutorByMap coneSubstitutorByMap = (ConeSubstitutorByMap) other;
        return getHashCode() == coneSubstitutorByMap.getHashCode() && Intrinsics.areEqual(this.substitution, coneSubstitutorByMap.substitution) && Intrinsics.areEqual(this.useSiteSession, coneSubstitutorByMap.useSiteSession);
    }

    public int hashCode() {
        return getHashCode();
    }

    public String toString() {
        return CollectionsKt.joinToString$default(this.substitution.entrySet(), " | ", "{", "}", 0, null, new Function1<Map.Entry<? extends FirTypeParameterSymbol, ? extends ConeKotlinType>, CharSequence>() { // from class: org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutorByMap.toString.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final CharSequence invoke2(Map.Entry<FirTypeParameterSymbol, ? extends ConeKotlinType> entry) {
                Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                return entry.getKey().getName() + " -> " + ConeTypeUtilsKt.renderForDebugging(entry.getValue());
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Map.Entry<? extends FirTypeParameterSymbol, ? extends ConeKotlinType> entry) {
                return invoke2((Map.Entry<FirTypeParameterSymbol, ? extends ConeKotlinType>) entry);
            }
        }, 24, null);
    }
}
