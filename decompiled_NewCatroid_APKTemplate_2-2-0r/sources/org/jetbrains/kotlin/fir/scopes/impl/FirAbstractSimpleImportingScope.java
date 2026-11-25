package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirResolvedImport;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirAbstractSimpleImportingScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\tH\u0014J.\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\t2\u001c\u0010\u0014\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0016\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00130\u0015H\u0016J$\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\t2\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00130\u0019H\u0016J(\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\t2\u0016\u0010\u0014\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001c\u0012\u0004\u0012\u00020\u00130\u0019H\u0016R$\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirAbstractSimpleImportingScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirAbstractImportingScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "simpleImports", "", "Lorg/jetbrains/kotlin/name/Name;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;", "getSimpleImports", "()Ljava/util/Map;", "isExcluded", "", PsiKeyword.IMPORT, "name", "processClassifiersByNameWithSubstitution", "", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processFunctionsByName", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAbstractSimpleImportingScope extends FirAbstractImportingScope {
    public abstract Map<Name, List<FirResolvedImport>> getSimpleImports();

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirAbstractImportingScope
    protected boolean isExcluded(FirResolvedImport firResolvedImport, Name name2) {
        Intrinsics.checkNotNullParameter(firResolvedImport, "import");
        Intrinsics.checkNotNullParameter(name2, "name");
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirAbstractSimpleImportingScope(FirSession session, ScopeSession scopeSession) {
        super(session, scopeSession, true);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, final Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        List<FirResolvedImport> list = getSimpleImports().get(name2);
        if (list == null) {
            return;
        }
        processClassifiersFromImportsByName(null, list, new Function1<FirClassLikeSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirAbstractSimpleImportingScope.processClassifiersByNameWithSubstitution.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirClassLikeSymbol<?> firClassLikeSymbol) {
                invoke2(firClassLikeSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirClassLikeSymbol<?> symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                processor.invoke(symbol, ConeSubstitutor.Empty.INSTANCE);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        List<FirResolvedImport> list = getSimpleImports().get(name2);
        if (list == null) {
            return;
        }
        processFunctionsByName(null, list, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        List<FirResolvedImport> list = getSimpleImports().get(name2);
        if (list == null) {
            return;
        }
        processPropertiesByName(null, list, processor);
    }
}
