package org.jetbrains.kotlin.fir.resolve;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousFunctionSymbol;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirSpecialTowerDataContexts.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0005J\u000e\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\bJ\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\f\u001a\u00020\u0005J\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000e\u001a\u00020\bJ\u000e\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0000J\u0016\u0010\u0013\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0006J\u0016\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0006R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/FirSpecialTowerDataContexts;", "", "()V", "towerDataContextForAnonymousFunctions", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirAnonymousFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;", "towerDataContextForCallableReferences", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", LineReader.CLEAR, "", "dropAnonymousFunctionContext", NativeSymbol.TYPE_NAME, "dropCallableReferenceContext", "access", "getAnonymousFunctionContext", "getCallableReferenceContext", "putAll", "contexts", "storeAnonymousFunctionContext", "context", "storeCallableReferenceContext", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSpecialTowerDataContexts {
    private final Map<FirAnonymousFunctionSymbol, FirTowerDataContext> towerDataContextForAnonymousFunctions = new LinkedHashMap();
    private final Map<FirCallableReferenceAccess, FirTowerDataContext> towerDataContextForCallableReferences = new LinkedHashMap();

    public final FirTowerDataContext getAnonymousFunctionContext(FirAnonymousFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return this.towerDataContextForAnonymousFunctions.get(symbol);
    }

    public final FirTowerDataContext getCallableReferenceContext(FirCallableReferenceAccess access) {
        Intrinsics.checkNotNullParameter(access, "access");
        return this.towerDataContextForCallableReferences.get(access);
    }

    public final void storeAnonymousFunctionContext(FirAnonymousFunctionSymbol symbol, FirTowerDataContext context) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(context, "context");
        this.towerDataContextForAnonymousFunctions.put(symbol, context);
    }

    public final void dropAnonymousFunctionContext(FirAnonymousFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.towerDataContextForAnonymousFunctions.remove(symbol);
    }

    public final void storeCallableReferenceContext(FirCallableReferenceAccess access, FirTowerDataContext context) {
        Intrinsics.checkNotNullParameter(access, "access");
        Intrinsics.checkNotNullParameter(context, "context");
        this.towerDataContextForCallableReferences.put(access, context);
    }

    public final void dropCallableReferenceContext(FirCallableReferenceAccess access) {
        Intrinsics.checkNotNullParameter(access, "access");
        this.towerDataContextForCallableReferences.remove(access);
    }

    public final void putAll(FirSpecialTowerDataContexts contexts) {
        Intrinsics.checkNotNullParameter(contexts, "contexts");
        this.towerDataContextForCallableReferences.putAll(contexts.towerDataContextForCallableReferences);
        this.towerDataContextForAnonymousFunctions.putAll(contexts.towerDataContextForAnonymousFunctions);
    }

    public final void clear() {
        this.towerDataContextForAnonymousFunctions.clear();
        this.towerDataContextForCallableReferences.clear();
    }
}
