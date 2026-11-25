package org.jetbrains.kotlin.fir.resolve.providers.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProviderWithoutCallables;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderInternals;
import org.jetbrains.kotlin.fir.scopes.FirScopeProvider;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: FirCloneableSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\u000f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0016J*\u0010\u0016\u001a\u00020\u00172\u0010\u0010\u0018\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001a0\u00192\u0006\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u001dH\u0017J&\u0010\u001e\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00192\u0006\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u001dH\u0017J&\u0010 \u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020!0\u00192\u0006\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u001dH\u0017R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirCloneableSymbolProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "scopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;)V", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "symbolNamesProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getSymbolNamesProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getClassLikeSymbolByClassId", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getPackage", "Lorg/jetbrains/kotlin/name/FqName;", "fqName", "getTopLevelCallableSymbolsTo", "", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "packageFqName", "name", "Lorg/jetbrains/kotlin/name/Name;", "getTopLevelFunctionSymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getTopLevelPropertySymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCloneableSymbolProvider extends FirSymbolProvider {
    private final FirRegularClass klass;
    private final FirSymbolNamesProvider symbolNamesProvider;

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FqName getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelCallableSymbolsTo(List<FirCallableSymbol<?>> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelFunctionSymbolsTo(List<FirNamedFunctionSymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelPropertySymbolsTo(List<FirPropertySymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirCloneableSymbolProvider(FirSession session, FirModuleData moduleData, FirScopeProvider scopeProvider) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(scopeProvider, "scopeProvider");
        FirRegularClassBuilder firRegularClassBuilder = new FirRegularClassBuilder();
        firRegularClassBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
        firRegularClassBuilder.setOrigin(FirDeclarationOrigin.Library.INSTANCE);
        firRegularClassBuilder.setModuleData(moduleData);
        firRegularClassBuilder.setStatus(new FirResolvedDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.ABSTRACT, EffectiveVisibility.Public.INSTANCE));
        firRegularClassBuilder.setClassKind(ClassKind.INTERFACE);
        FirRegularClassSymbol firRegularClassSymbol = new FirRegularClassSymbol(StandardClassIds.INSTANCE.getCloneable());
        firRegularClassBuilder.setSymbol(firRegularClassSymbol);
        List<FirTypeRef> superTypeRefs = firRegularClassBuilder.getSuperTypeRefs();
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(session.getBuiltinTypes().getAnyType().getType());
        superTypeRefs.add(firResolvedTypeRefBuilder.mo6809build());
        List<FirDeclaration> declarations = firRegularClassBuilder.getDeclarations();
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        firSimpleFunctionBuilder.setModuleData(moduleData);
        firSimpleFunctionBuilder.setResolvePhase(FirResolvePhase.INSTANCE.getANALYZED_DEPENDENCIES());
        firSimpleFunctionBuilder.setOrigin(FirDeclarationOrigin.Library.INSTANCE);
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder2 = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder2.setType(session.getBuiltinTypes().getAnyType().getType());
        firSimpleFunctionBuilder.setReturnTypeRef(firResolvedTypeRefBuilder2.mo6809build());
        firSimpleFunctionBuilder.setStatus(new FirResolvedDeclarationStatusImpl(Visibilities.Protected.INSTANCE, Modality.OPEN, EffectiveVisibilityUtilsKt.toEffectiveVisibility$default((Visibility) Visibilities.Protected.INSTANCE, (FirClassLikeSymbol) firRegularClassSymbol, false, false, 6, (Object) null)));
        firSimpleFunctionBuilder.setName(StandardClassIds.Callables.INSTANCE.getClone().getCallableName());
        firSimpleFunctionBuilder.setSymbol(new FirNamedFunctionSymbol(StandardClassIds.Callables.INSTANCE.getClone()));
        firSimpleFunctionBuilder.setDispatchReceiverType(TypeConstructionUtilsKt.constructType$default((FirClassLikeSymbol) firRegularClassBuilder.getSymbol(), new ConeTypeProjection[0], false, (ConeAttributes) null, 4, (Object) null));
        declarations.add(firSimpleFunctionBuilder.mo6809build());
        firRegularClassBuilder.setScopeProvider(scopeProvider);
        Name shortClassName = StandardClassIds.INSTANCE.getCloneable().getShortClassName();
        Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
        firRegularClassBuilder.setName(shortClassName);
        this.klass = firRegularClassBuilder.mo6809build();
        this.symbolNamesProvider = new FirSymbolNamesProviderWithoutCallables() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirCloneableSymbolProvider$symbolNamesProvider$1
            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<String> getTopLevelClassifierNamesInPackage(FqName packageFqName) {
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                if (Intrinsics.areEqual(packageFqName, StandardClassIds.INSTANCE.getCloneable().getPackageFqName())) {
                    return SetsKt.setOf(StandardClassIds.INSTANCE.getCloneable().getShortClassName().asString());
                }
                return SetsKt.emptySet();
            }
        };
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirSymbolNamesProvider getSymbolNamesProvider() {
        return this.symbolNamesProvider;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirClassLikeSymbol<?> getClassLikeSymbolByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        if (Intrinsics.areEqual(classId, StandardClassIds.INSTANCE.getCloneable())) {
            return this.klass.getSymbol();
        }
        return null;
    }
}
