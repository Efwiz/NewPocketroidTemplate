package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirNestedClassifierScopeWithSubstitution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000fH\u0016J.\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0013\u001a\u00020\u000f2\u001c\u0010\u0016\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0018\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00150\u0017H\u0016J\u001c\u0010\u0019\u001a\u00020\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u00150\u001aH\u0016J$\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u0013\u001a\u00020\u000f2\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00150\u001aH\u0016J(\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u0013\u001a\u00020\u000f2\u0016\u0010\u0016\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001f\u0012\u0004\u0012\u00020\u00150\u001aH\u0016R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirNestedClassifierScopeWithSubstitution;", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "originalScope", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "(Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;)V", "getOriginalScope", "()Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "scopeOwnerLookupNames", "", "", "getScopeOwnerLookupNames", "()Ljava/util/List;", "getCallableNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getClassifierNames", "mayContainName", "", "name", "processClassifiersByNameWithSubstitution", "", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "processDeclaredConstructors", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "processFunctionsByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirNestedClassifierScopeWithSubstitution extends FirContainingNamesAwareScope {
    private final FirContainingNamesAwareScope originalScope;
    private final ConeSubstitutor substitutor;

    public final FirContainingNamesAwareScope getOriginalScope() {
        return this.originalScope;
    }

    public FirNestedClassifierScopeWithSubstitution(FirContainingNamesAwareScope originalScope, ConeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(originalScope, "originalScope");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        this.originalScope = originalScope;
        this.substitutor = substitutor;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.originalScope.processFunctionsByName(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.originalScope.processPropertiesByName(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processDeclaredConstructors(Function1<? super FirConstructorSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.originalScope.processDeclaredConstructors(processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public boolean mayContainName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.originalScope.mayContainName(name2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        FirClassifierSymbol<?> singleClassifier = FirScopeKt.getSingleClassifier(this.originalScope, name2);
        FirRegularClassSymbol firRegularClassSymbol = singleClassifier instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) singleClassifier : null;
        if (firRegularClassSymbol == null) {
            return;
        }
        ConeSubstitutor.Empty empty = ((FirMemberDeclaration) firRegularClassSymbol.getFir()).getStatus().isInner() ? this.substitutor : null;
        if (empty == null) {
            empty = ConeSubstitutor.Empty.INSTANCE;
        }
        processor.invoke(firRegularClassSymbol, empty);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return this.originalScope.getCallableNames();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return this.originalScope.getClassifierNames();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public List<String> getScopeOwnerLookupNames() {
        return this.originalScope.getScopeOwnerLookupNames();
    }
}
