package org.jetbrains.kotlin.fir.symbols;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ConeTypeParameterLookupTag.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\t\u0010\u000e\u001a\u00020\u0004HÆ\u0003J\u0013\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u0004HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/ConeTypeParameterLookupTag;", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassifierLookupTagWithFixedSymbol;", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "typeParameterSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;)V", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", NativeSymbol.TYPE_NAME, "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "getTypeParameterSymbol", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ConeTypeParameterLookupTag extends ConeClassifierLookupTagWithFixedSymbol implements TypeParameterMarker {
    private final FirTypeParameterSymbol typeParameterSymbol;

    public static /* synthetic */ ConeTypeParameterLookupTag copy$default(ConeTypeParameterLookupTag coneTypeParameterLookupTag, FirTypeParameterSymbol firTypeParameterSymbol, int i, Object obj) {
        if ((i & 1) != 0) {
            firTypeParameterSymbol = coneTypeParameterLookupTag.typeParameterSymbol;
        }
        return coneTypeParameterLookupTag.copy(firTypeParameterSymbol);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final FirTypeParameterSymbol getTypeParameterSymbol() {
        return this.typeParameterSymbol;
    }

    public final ConeTypeParameterLookupTag copy(FirTypeParameterSymbol typeParameterSymbol) {
        Intrinsics.checkNotNullParameter(typeParameterSymbol, "typeParameterSymbol");
        return new ConeTypeParameterLookupTag(typeParameterSymbol);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ConeTypeParameterLookupTag) && Intrinsics.areEqual(this.typeParameterSymbol, ((ConeTypeParameterLookupTag) other).typeParameterSymbol);
    }

    public int hashCode() {
        return this.typeParameterSymbol.hashCode();
    }

    @Override // org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag
    public String toString() {
        return "ConeTypeParameterLookupTag(typeParameterSymbol=" + this.typeParameterSymbol + ')';
    }

    public final FirTypeParameterSymbol getTypeParameterSymbol() {
        return this.typeParameterSymbol;
    }

    public ConeTypeParameterLookupTag(FirTypeParameterSymbol typeParameterSymbol) {
        Intrinsics.checkNotNullParameter(typeParameterSymbol, "typeParameterSymbol");
        this.typeParameterSymbol = typeParameterSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag
    public Name getName() {
        return this.typeParameterSymbol.getName();
    }

    @Override // org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTagWithFixedSymbol
    public FirTypeParameterSymbol getSymbol() {
        return this.typeParameterSymbol;
    }
}
