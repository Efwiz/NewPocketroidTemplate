package org.jetbrains.kotlin.fir.scopes.impl;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolvedImport;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirAbstractImportingScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0018\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H$JÅ\u0001\u0010\u0019\u001a\u00020\u001a\"\b\b\u0000\u0010\u001b*\u00020\u001c\"\u0010\b\u0001\u0010\u001d*\n\u0012\u0006\b\u0001\u0012\u0002H\u001b0\u001e2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00160 2\u0014\b\u0004\u0010!\u001a\u000e\u0012\u0004\u0012\u0002H\u001d\u0012\u0004\u0012\u00020\u001a0\"2\u001f\b\u0004\u0010#\u001a\u0019\u0012\u0004\u0012\u0002H\u001d\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u0002H\u001d0$¢\u0006\u0002\b%2/\u0010&\u001a+\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0018\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u001d\u0012\u0004\u0012\u00020\u001a0\"\u0012\u0004\u0012\u00020\u001a0'¢\u0006\u0002\b%2\u001e\u0010(\u001a\u001a\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001d0 0$H\u0082\bJ8\u0010*\u001a\u00020\u001a2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00160 2\u0016\u0010!\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r\u0012\u0004\u0012\u00020\u001a0\"H\u0004J4\u0010+\u001a\u00020\u001a2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00160 2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020\u001a0\"H\u0004J8\u0010-\u001a\u00020\u001a2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00160 2\u0016\u0010!\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030.\u0012\u0004\u0012\u00020\u001a0\"H\u0004J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u0011*\u0006\u0012\u0002\b\u00030\fH\u0002R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\"\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f*\u0006\u0012\u0002\b\u00030\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirAbstractImportingScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirAbstractProviderBasedScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "lookupInFir", "", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Z)V", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "fullyExpandedSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getFullyExpandedSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "getStaticsScope", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "isExcluded", PsiKeyword.IMPORT, "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;", "name", "Lorg/jetbrains/kotlin/name/Name;", "processCallablesFromImportsByName", "", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "imports", "", "processor", "Lkotlin/Function1;", "buildImportedCopy", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "processCallablesByName", "Lkotlin/Function3;", "getTopLevelCallableSymbols", "Lorg/jetbrains/kotlin/name/FqName;", "processClassifiersFromImportsByName", "processFunctionsByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAbstractImportingScope extends FirAbstractProviderBasedScope {
    private final ScopeSession scopeSession;

    protected abstract boolean isExcluded(FirResolvedImport firResolvedImport, Name name2);

    protected final ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirAbstractImportingScope(FirSession session, ScopeSession scopeSession, boolean z) {
        super(session, z);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.scopeSession = scopeSession;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirClassSymbol<?> getFullyExpandedSymbol(FirClassLikeSymbol<?> firClassLikeSymbol) {
        ConeClassLikeLookupTag lookupTag;
        FirClassLikeSymbol<?> symbol;
        if (!(firClassLikeSymbol instanceof FirTypeAliasSymbol)) {
            if (firClassLikeSymbol instanceof FirClassSymbol) {
                return (FirClassSymbol) firClassLikeSymbol;
            }
            throw new NoWhenBranchMatchedException();
        }
        ConeClassLikeType expandedConeType = FirDeclarationUtilKt.getExpandedConeType((FirTypeAlias) ((FirTypeAliasSymbol) firClassLikeSymbol).getFir());
        if (expandedConeType == null || (lookupTag = expandedConeType.getLookupTag()) == null || (symbol = LookupTagUtilsKt.toSymbol(lookupTag, getSession())) == null) {
            return null;
        }
        return getFullyExpandedSymbol(symbol);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirContainingNamesAwareScope getStaticsScope(FirClassSymbol<?> firClassSymbol) {
        if (((FirClass) firClassSymbol.getFir()).getClassKind() == ClassKind.OBJECT) {
            return FirKotlinScopeProviderKt.unsubstitutedScope(firClassSymbol, getSession(), this.scopeSession, false, FirResolvePhase.STATUS);
        }
        return ((FirClass) firClassSymbol.getFir()).getScopeProvider().getStaticScope((FirClass) firClassSymbol.getFir(), getSession(), this.scopeSession);
    }

    public final FirContainingNamesAwareScope getStaticsScope(ClassId classId) {
        FirClassSymbol<?> fullyExpandedSymbol;
        Intrinsics.checkNotNullParameter(classId, "classId");
        FirClassLikeSymbol<?> classLikeSymbolByClassId = getProvider().getClassLikeSymbolByClassId(classId);
        if (classLikeSymbolByClassId == null || (fullyExpandedSymbol = getFullyExpandedSymbol(classLikeSymbolByClassId)) == null) {
            return null;
        }
        return getStaticsScope(fullyExpandedSymbol);
    }

    protected final void processClassifiersFromImportsByName(Name name2, List<? extends FirResolvedImport> imports, Function1<? super FirClassLikeSymbol<?>, Unit> processor) {
        Name importedName;
        ClassId classIdCreateNestedClassId;
        Intrinsics.checkNotNullParameter(imports, "imports");
        Intrinsics.checkNotNullParameter(processor, "processor");
        for (FirResolvedImport firResolvedImport : imports) {
            if (name2 == null) {
                importedName = firResolvedImport.getImportedName();
                if (importedName == null) {
                }
            } else {
                importedName = name2;
            }
            if (!isExcluded(firResolvedImport, importedName)) {
                ClassId resolvedParentClassId = firResolvedImport.getResolvedParentClassId();
                if (resolvedParentClassId == null || (classIdCreateNestedClassId = resolvedParentClassId.createNestedClassId(importedName)) == null) {
                    classIdCreateNestedClassId = ClassId.topLevel(firResolvedImport.getPackageFqName().child(importedName));
                }
                Intrinsics.checkNotNull(classIdCreateNestedClassId);
                FirClassLikeSymbol<?> classLikeSymbolByClassId = getProvider().getClassLikeSymbolByClassId(classIdCreateNestedClassId);
                if (classLikeSymbolByClassId != null) {
                    processor.invoke(classLikeSymbolByClassId);
                }
            }
        }
    }

    protected final void processFunctionsByName(Name name2, List<? extends FirResolvedImport> imports, final Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Name importedName;
        Intrinsics.checkNotNullParameter(imports, "imports");
        Intrinsics.checkNotNullParameter(processor, "processor");
        FirSymbolProvider provider = getProvider();
        for (FirResolvedImport firResolvedImport : imports) {
            if (name2 == null) {
                importedName = firResolvedImport.getImportedName();
                if (importedName == null) {
                }
            } else {
                importedName = name2;
            }
            if (!isExcluded(firResolvedImport, importedName)) {
                final ClassId resolvedParentClassId = firResolvedImport.getResolvedParentClassId();
                if (resolvedParentClassId != null) {
                    FirClassLikeSymbol<?> classLikeSymbolByClassId = getProvider().getClassLikeSymbolByClassId(resolvedParentClassId);
                    final FirClassSymbol<?> fullyExpandedSymbol = classLikeSymbolByClassId != null ? getFullyExpandedSymbol(classLikeSymbolByClassId) : null;
                    FirContainingNamesAwareScope staticsScope = fullyExpandedSymbol != null ? getStaticsScope(fullyExpandedSymbol) : null;
                    if (staticsScope != null) {
                        staticsScope.processFunctionsByName(importedName, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirAbstractImportingScope$processFunctionsByName$$inlined$processCallablesFromImportsByName$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                                invoke(firNamedFunctionSymbol);
                                return Unit.INSTANCE;
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            public final void invoke(FirNamedFunctionSymbol it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                if (it.getRawStatus().isStatic() || fullyExpandedSymbol.getClassKind() == ClassKind.OBJECT) {
                                    processor.invoke(FirAbstractImportingScopeKt.buildImportedVersion((FirSimpleFunction) it.getFir(), resolvedParentClassId).getSymbol());
                                    return;
                                }
                                processor.invoke(it);
                            }
                        });
                    }
                }
                if (!importedName.isSpecial()) {
                    String identifier = importedName.getIdentifier();
                    Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
                    if (identifier.length() > 0) {
                    }
                }
                Iterator<FirNamedFunctionSymbol> it = provider.getTopLevelFunctionSymbols(firResolvedImport.getPackageFqName(), importedName).iterator();
                while (it.hasNext()) {
                    processor.invoke(it.next());
                }
            }
        }
    }

    protected final void processPropertiesByName(Name name2, List<? extends FirResolvedImport> imports, final Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Name importedName;
        Intrinsics.checkNotNullParameter(imports, "imports");
        Intrinsics.checkNotNullParameter(processor, "processor");
        FirSymbolProvider provider = getProvider();
        for (FirResolvedImport firResolvedImport : imports) {
            if (name2 == null) {
                importedName = firResolvedImport.getImportedName();
                if (importedName == null) {
                }
            } else {
                importedName = name2;
            }
            if (!isExcluded(firResolvedImport, importedName)) {
                final ClassId resolvedParentClassId = firResolvedImport.getResolvedParentClassId();
                if (resolvedParentClassId != null) {
                    FirClassLikeSymbol<?> classLikeSymbolByClassId = getProvider().getClassLikeSymbolByClassId(resolvedParentClassId);
                    final FirClassSymbol<?> fullyExpandedSymbol = classLikeSymbolByClassId != null ? getFullyExpandedSymbol(classLikeSymbolByClassId) : null;
                    FirContainingNamesAwareScope staticsScope = fullyExpandedSymbol != null ? getStaticsScope(fullyExpandedSymbol) : null;
                    if (staticsScope != null) {
                        staticsScope.processPropertiesByName(importedName, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirAbstractImportingScope$processPropertiesByName$$inlined$processCallablesFromImportsByName$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                                invoke(firVariableSymbol);
                                return Unit.INSTANCE;
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            public final void invoke(FirVariableSymbol<?> it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                if (it.getRawStatus().isStatic() || fullyExpandedSymbol.getClassKind() == ClassKind.OBJECT) {
                                    processor.invoke(FirAbstractImportingScopeKt.buildImportedVersion((FirVariable) it.getFir(), resolvedParentClassId).getSymbol());
                                    return;
                                }
                                processor.invoke(it);
                            }
                        });
                    }
                }
                if (!importedName.isSpecial()) {
                    String identifier = importedName.getIdentifier();
                    Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
                    if (identifier.length() > 0) {
                    }
                }
                Iterator<FirPropertySymbol> it = provider.getTopLevelPropertySymbols(firResolvedImport.getPackageFqName(), importedName).iterator();
                while (it.hasNext()) {
                    processor.invoke(it.next());
                }
            }
        }
    }
}
