package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirScriptSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirScriptSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/name/FqName;)V", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirScriptSymbol extends FirBasedSymbol<FirScript> {
    private final FqName fqName;

    public FirScriptSymbol(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        this.fqName = fqName;
    }

    public final FqName getFqName() {
        return this.fqName;
    }
}
