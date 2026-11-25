package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirResolvedImport;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirAbstractStarImportingScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\u0018\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u000eH\u0014J.\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0018\u001a\u00020\u000e2\u001c\u0010\u001b\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001d\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001a0\u001cH\u0016J$\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u0018\u001a\u00020\u000e2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u001a0 H\u0016J(\u0010\"\u001a\u00020\u001a2\u0006\u0010\u0018\u001a\u00020\u000e2\u0016\u0010\u001b\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030#\u0012\u0004\u0012\u00020\u001a0 H\u0016R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirAbstractStarImportingScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirAbstractImportingScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "lookupInFir", "", "excludedImportNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;ZLjava/util/Set;)V", "absentClassifierNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getExcludedImportNames", "()Ljava/util/Set;", "starImports", "", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;", "getStarImports", "()Ljava/util/List;", "isExcluded", PsiKeyword.IMPORT, "name", "processClassifiersByNameWithSubstitution", "", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processFunctionsByName", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAbstractStarImportingScope extends FirAbstractImportingScope {
    private final Set<Name> absentClassifierNames;
    private final Set<FqName> excludedImportNames;

    public abstract List<FirResolvedImport> getStarImports();

    public final Set<FqName> getExcludedImportNames() {
        return this.excludedImportNames;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirAbstractStarImportingScope(FirSession session, ScopeSession scopeSession, boolean z, Set<FqName> excludedImportNames) {
        super(session, scopeSession, z);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(excludedImportNames, "excludedImportNames");
        this.excludedImportNames = excludedImportNames;
        this.absentClassifierNames = new LinkedHashSet();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirAbstractImportingScope
    protected boolean isExcluded(FirResolvedImport firResolvedImport, Name name2) {
        Intrinsics.checkNotNullParameter(firResolvedImport, "import");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (this.excludedImportNames.isEmpty()) {
            return false;
        }
        Set<FqName> set = this.excludedImportNames;
        FqName importedFqName = firResolvedImport.getImportedFqName();
        Intrinsics.checkNotNull(importedFqName);
        return set.contains(importedFqName.child(name2));
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, final Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (!name2.isSpecial()) {
            String identifier = name2.getIdentifier();
            Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
            if (identifier.length() == 0) {
                return;
            }
        }
        if (getStarImports().isEmpty() || this.absentClassifierNames.contains(name2)) {
            return;
        }
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        processClassifiersFromImportsByName(name2, getStarImports(), new Function1<FirClassLikeSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirAbstractStarImportingScope.processClassifiersByNameWithSubstitution.1
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
                booleanRef.element = true;
                processor.invoke(symbol, ConeSubstitutor.Empty.INSTANCE);
            }
        });
        if (booleanRef.element) {
            return;
        }
        this.absentClassifierNames.add(name2);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        processFunctionsByName(name2, getStarImports(), processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        processPropertiesByName(name2, getStarImports(), processor);
    }
}
