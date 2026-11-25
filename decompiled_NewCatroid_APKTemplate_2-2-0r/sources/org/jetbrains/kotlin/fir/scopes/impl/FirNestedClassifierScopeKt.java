package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;

/* JADX INFO: compiled from: FirNestedClassifierScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0003¨\u0006\u0004"}, d2 = {"toConeType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirNestedClassifierScopeKt {
    public static final ConeKotlinType toConeType(FirTypeParameterRef firTypeParameterRef) {
        Intrinsics.checkNotNullParameter(firTypeParameterRef, "<this>");
        return toConeType(firTypeParameterRef.getSymbol());
    }

    public static final ConeKotlinType toConeType(FirTypeParameterSymbol firTypeParameterSymbol) {
        Intrinsics.checkNotNullParameter(firTypeParameterSymbol, "<this>");
        return new ConeTypeParameterTypeImpl(new ConeTypeParameterLookupTag(firTypeParameterSymbol), false, null, 4, null);
    }
}
