package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ConeClassLookupTagWithFixedSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0015\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/ConeClassLookupTagWithFixedSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeClassLookupTagWithFixedSymbol extends ConeClassLikeLookupTag {
    private final ClassId classId;
    private final FirClassSymbol<?> symbol;

    @Override // org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag
    public ClassId getClassId() {
        return this.classId;
    }

    public final FirClassSymbol<?> getSymbol() {
        return this.symbol;
    }

    public ConeClassLookupTagWithFixedSymbol(ClassId classId, FirClassSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.classId = classId;
        this.symbol = symbol;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.ConeClassLookupTagWithFixedSymbol");
        return Intrinsics.areEqual(this.symbol, ((ConeClassLookupTagWithFixedSymbol) other).symbol);
    }

    public int hashCode() {
        return this.symbol.hashCode();
    }
}
