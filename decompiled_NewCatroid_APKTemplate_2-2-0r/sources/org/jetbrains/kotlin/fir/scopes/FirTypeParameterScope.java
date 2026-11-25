package org.jetbrains.kotlin.fir.scopes;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirTypeParameterScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u0016J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u0016J.\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00052\u001c\u0010\u0010\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u000e0\u0011H\u0016R$\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FirTypeParameterScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "()V", "typeParameters", "", "Lorg/jetbrains/kotlin/name/Name;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "getTypeParameters", "()Ljava/util/Map;", "getCallableNames", "", "getClassifierNames", "processClassifiersByNameWithSubstitution", "", "name", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirTypeParameterScope extends FirContainingNamesAwareScope {
    public abstract Map<Name, List<FirTypeParameter>> getTypeParameters();

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        List<FirTypeParameter> list = getTypeParameters().get(name2);
        if (list == null) {
            return;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            processor.invoke(((FirTypeParameter) it.next()).getSymbol(), ConeSubstitutor.Empty.INSTANCE);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return SetsKt.emptySet();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return getTypeParameters().keySet();
    }
}
