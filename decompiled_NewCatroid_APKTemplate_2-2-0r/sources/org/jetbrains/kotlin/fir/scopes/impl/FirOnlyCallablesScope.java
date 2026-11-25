package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirOnlyCallablesScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J$\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\f0\u0010H\u0016J(\u0010\u0012\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0016\u0010\u000f\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0013\u0012\u0004\u0012\u00020\f0\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirOnlyCallablesScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Lorg/jetbrains/kotlin/fir/scopes/FirScope;)V", "getDelegate", "()Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "scopeOwnerLookupNames", "", "", "getScopeOwnerLookupNames", "()Ljava/util/List;", "processFunctionsByName", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "processor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirOnlyCallablesScope extends FirScope {
    private final FirScope delegate;

    public FirOnlyCallablesScope(FirScope delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    public final FirScope getDelegate() {
        return this.delegate;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.delegate.processFunctionsByName(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.delegate.processPropertiesByName(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public List<String> getScopeOwnerLookupNames() {
        return this.delegate.getScopeOwnerLookupNames();
    }
}
