package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirNestedClassifierScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\fH\u0016J\u0016\u0010\r\u001a\b\u0012\u0002\b\u0003\u0018\u00010\n2\u0006\u0010\u000e\u001a\u00020\tH\u0014J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u001e\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirNestedClassifierScopeImpl;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirNestedClassifierScope;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;Lorg/jetbrains/kotlin/fir/FirSession;)V", "classIndex", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getClassifierNames", "", "getNestedClassSymbol", "name", "isEmpty", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNestedClassifierScopeImpl extends FirNestedClassifierScope {
    private final Map<Name, FirClassLikeSymbol<?>> classIndex;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirNestedClassifierScopeImpl(FirClass klass, FirSession useSiteSession) {
        super(klass, useSiteSession);
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (FirDeclaration firDeclaration : klass.getDeclarations()) {
            if (firDeclaration instanceof FirRegularClass) {
                FirRegularClass firRegularClass = (FirRegularClass) firDeclaration;
                linkedHashMap.put(firRegularClass.getName(), firRegularClass.getSymbol());
            } else if (firDeclaration instanceof FirTypeAlias) {
                FirTypeAlias firTypeAlias = (FirTypeAlias) firDeclaration;
                linkedHashMap.put(firTypeAlias.getName(), firTypeAlias.getSymbol());
            }
        }
        this.classIndex = linkedHashMap;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScope
    protected FirClassLikeSymbol<?> getNestedClassSymbol(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.classIndex.get(name2);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScope
    public boolean isEmpty() {
        return this.classIndex.isEmpty();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return this.classIndex.keySet();
    }
}
