package org.jetbrains.kotlin.fir.resolve.providers;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProviderKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010\u001a \u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\r*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0014\u001a\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u0016*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\u001a$\u0010\u0017\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00180\r*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0014\u001a\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u001a*\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0010\u001a,\u0010\u001b\u001a\u0004\u0018\u0001H\u001c\"\u000e\b\u0000\u0010\u001c\u0018\u0001*\u0006\u0012\u0002\b\u00030\u001d*\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u001fH\u0086\b¢\u0006\u0002\u0010 \u001a\u0018\u0010!\u001a\b\u0012\u0002\b\u0003\u0018\u00010\"*\u00020\u00102\u0006\u0010#\u001a\u00020\u0004\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u001f\u0010\u0002\u001a\u00020\u0003*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\"\u001f\u0010\t\u001a\u00020\u0003*\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\n\u0010\u0006¨\u0006$"}, d2 = {"DEPENDENCIES_SYMBOL_PROVIDER_QUALIFIED_KEY", "", "dependenciesSymbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getDependenciesSymbolProvider", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "dependenciesSymbolProvider$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "symbolProvider", "getSymbolProvider", "symbolProvider$delegate", "getClassDeclaredConstructors", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassDeclaredFunctionSymbols", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getClassDeclaredMemberScope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "getClassDeclaredPropertySymbols", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "getRegularClassSymbolByClassId", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "getSymbolByTypeRef", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "(Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;Lorg/jetbrains/kotlin/fir/types/FirTypeRef;)Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "toSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "session", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirSymbolProviderKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirSymbolProviderKt.class, "providers"), "symbolProvider", "getSymbolProvider(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirSymbolProviderKt.class, "providers"), "dependenciesSymbolProvider", "getDependenciesSymbolProvider(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;"))};
    private static final ArrayMapAccessor symbolProvider$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirSymbolProvider.class), (Object) null, 2, (Object) null);
    public static final String DEPENDENCIES_SYMBOL_PROVIDER_QUALIFIED_KEY = "org.jetbrains.kotlin.fir.resolve.providers.FirDependenciesSymbolProvider";
    private static final ArrayMapAccessor dependenciesSymbolProvider$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, DEPENDENCIES_SYMBOL_PROVIDER_QUALIFIED_KEY, (Object) null, 2, (Object) null);

    /* JADX WARN: Multi-variable type inference failed */
    private static final FirScope getClassDeclaredMemberScope(FirSymbolProvider firSymbolProvider, ClassId classId) {
        FirClassLikeSymbol<?> classLikeSymbolByClassId = firSymbolProvider.getClassLikeSymbolByClassId(classId);
        FirRegularClassSymbol firRegularClassSymbol = classLikeSymbolByClassId instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) classLikeSymbolByClassId : null;
        if (firRegularClassSymbol == null) {
            return null;
        }
        return FirDeclaredMemberScopeProviderKt.declaredMemberScope(firSymbolProvider.getSession(), (FirClass) firRegularClassSymbol.getFir(), (FirResolvePhase) null);
    }

    public static final List<FirConstructorSymbol> getClassDeclaredConstructors(FirSymbolProvider firSymbolProvider, ClassId classId) {
        Intrinsics.checkNotNullParameter(firSymbolProvider, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        FirScope classDeclaredMemberScope = getClassDeclaredMemberScope(firSymbolProvider, classId);
        List<FirConstructorSymbol> declaredConstructors = classDeclaredMemberScope != null ? FirScopeKt.getDeclaredConstructors(classDeclaredMemberScope) : null;
        return declaredConstructors == null ? CollectionsKt.emptyList() : declaredConstructors;
    }

    public static final List<FirNamedFunctionSymbol> getClassDeclaredFunctionSymbols(FirSymbolProvider firSymbolProvider, ClassId classId, Name name2) {
        Intrinsics.checkNotNullParameter(firSymbolProvider, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(name2, "name");
        FirScope classDeclaredMemberScope = getClassDeclaredMemberScope(firSymbolProvider, classId);
        List<FirNamedFunctionSymbol> functions = classDeclaredMemberScope != null ? FirScopeKt.getFunctions(classDeclaredMemberScope, name2) : null;
        return functions == null ? CollectionsKt.emptyList() : functions;
    }

    public static final List<FirVariableSymbol<?>> getClassDeclaredPropertySymbols(FirSymbolProvider firSymbolProvider, ClassId classId, Name name2) {
        Intrinsics.checkNotNullParameter(firSymbolProvider, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(name2, "name");
        FirScope classDeclaredMemberScope = getClassDeclaredMemberScope(firSymbolProvider, classId);
        List<FirVariableSymbol<?>> properties = classDeclaredMemberScope != null ? FirScopeKt.getProperties(classDeclaredMemberScope, name2) : null;
        return properties == null ? CollectionsKt.emptyList() : properties;
    }

    public static final FirRegularClassSymbol getRegularClassSymbolByClassId(FirSymbolProvider firSymbolProvider, ClassId classId) {
        Intrinsics.checkNotNullParameter(firSymbolProvider, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        FirClassLikeSymbol<?> classLikeSymbolByClassId = firSymbolProvider.getClassLikeSymbolByClassId(classId);
        if (classLikeSymbolByClassId instanceof FirRegularClassSymbol) {
            return (FirRegularClassSymbol) classLikeSymbolByClassId;
        }
        return null;
    }

    public static final FirClassifierSymbol<?> toSymbol(ClassId classId, FirSession session) {
        Intrinsics.checkNotNullParameter(classId, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return getSymbolProvider(session).getClassLikeSymbolByClassId(classId);
    }

    public static final FirSymbolProvider getSymbolProvider(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirSymbolProvider) symbolProvider$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }

    public static final FirSymbolProvider getDependenciesSymbolProvider(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirSymbolProvider) dependenciesSymbolProvider$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[1]);
    }

    public static final /* synthetic */ <T extends FirBasedSymbol<?>> T getSymbolByTypeRef(FirSymbolProvider firSymbolProvider, FirTypeRef typeRef) {
        ConeClassifierLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(firSymbolProvider, "<this>");
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeSimpleKotlinType)) {
            type = null;
        }
        ConeSimpleKotlinType coneSimpleKotlinType = (ConeSimpleKotlinType) type;
        ConeSimpleKotlinType coneSimpleKotlinTypeFullyExpandedType = coneSimpleKotlinType != null ? TypeExpansionUtilsKt.fullyExpandedType(coneSimpleKotlinType, firSymbolProvider.getSession()) : null;
        ConeLookupTagBasedType coneLookupTagBasedType = coneSimpleKotlinTypeFullyExpandedType instanceof ConeLookupTagBasedType ? (ConeLookupTagBasedType) coneSimpleKotlinTypeFullyExpandedType : null;
        if (coneLookupTagBasedType == null || (lookupTag = coneLookupTagBasedType.getLookupTag()) == null) {
            return null;
        }
        FirClassifierSymbol<?> symbolByLookupTag = LookupTagUtilsKt.getSymbolByLookupTag(firSymbolProvider, lookupTag);
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        return symbolByLookupTag;
    }
}
