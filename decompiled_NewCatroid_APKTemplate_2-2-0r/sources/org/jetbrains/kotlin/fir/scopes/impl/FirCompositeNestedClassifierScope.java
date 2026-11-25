package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: FirNestedClassifierScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0014J\b\u0010\u0011\u001a\u00020\u0012H\u0016J.\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\r2\u001c\u0010\u0015\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0017\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00140\u0016H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirCompositeNestedClassifierScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirNestedClassifierScope;", "scopes", "", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Ljava/util/List;Lorg/jetbrains/kotlin/fir/declarations/FirClass;Lorg/jetbrains/kotlin/fir/FirSession;)V", "getScopes", "()Ljava/util/List;", "getClassifierNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getNestedClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "name", "isEmpty", "", "processClassifiersByNameWithSubstitution", "", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCompositeNestedClassifierScope extends FirNestedClassifierScope {
    private final List<FirNestedClassifierScope> scopes;

    public final List<FirNestedClassifierScope> getScopes() {
        return this.scopes;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FirCompositeNestedClassifierScope(List<? extends FirNestedClassifierScope> scopes, FirClass klass, FirSession useSiteSession) {
        super(klass, useSiteSession);
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        this.scopes = scopes;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScope
    public FirRegularClassSymbol getNestedClassSymbol(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScope, org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        Iterator<T> it = this.scopes.iterator();
        while (it.hasNext()) {
            ((FirNestedClassifierScope) it.next()).processClassifiersByNameWithSubstitution(name2, processor);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScope
    public boolean isEmpty() {
        List<FirNestedClassifierScope> list = this.scopes;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (!((FirNestedClassifierScope) it.next()).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        List<FirNestedClassifierScope> list = this.scopes;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(linkedHashSet, ((FirNestedClassifierScope) it.next()).getClassifierNames());
        }
        return linkedHashSet;
    }
}
