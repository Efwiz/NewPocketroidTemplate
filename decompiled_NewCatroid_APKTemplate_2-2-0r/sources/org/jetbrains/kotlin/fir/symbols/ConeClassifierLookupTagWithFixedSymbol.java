package org.jetbrains.kotlin.fir.symbols;

import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ConeClassifierLookupTagWithFixedSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/ConeClassifierLookupTagWithFixedSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassifierLookupTag;", "()V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeClassifierLookupTagWithFixedSymbol extends ConeClassifierLookupTag {
    public abstract FirClassifierSymbol<?> getSymbol();
}
