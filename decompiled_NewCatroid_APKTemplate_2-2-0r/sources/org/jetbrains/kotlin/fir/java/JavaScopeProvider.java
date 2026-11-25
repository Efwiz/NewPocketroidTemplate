package org.jetbrains.kotlin.fir.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaClass;
import org.jetbrains.kotlin.fir.java.scopes.JavaAnnotationSyntheticPropertiesScope;
import org.jetbrains.kotlin.fir.java.scopes.JavaClassMembersEnhancementScope;
import org.jetbrains.kotlin.fir.java.scopes.JavaClassStaticEnhancementScope;
import org.jetbrains.kotlin.fir.java.scopes.JavaClassStaticUseSiteScope;
import org.jetbrains.kotlin.fir.java.scopes.JavaClassUseSiteMemberScope;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeSessionKey;
import org.jetbrains.kotlin.fir.resolve.SupertypeSupplier;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScopeProvider;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirNameAwareOnlyCallablesScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirStaticScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.utils.DFS;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JavaScopeProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J*\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J(\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\nH\u0002J*\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0007\u001a\u00020\u00172\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J\"\u0010\u0018\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0016J\"\u0010\u001b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0016J0\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\b0\u001eH\u0002J*\u0010\u001f\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\u001a\u0010 \u001a\b\u0012\u0004\u0012\u00020\b0!*\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0017\u0010\"\u001a\u0004\u0018\u00010\b*\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0082\u0010¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/JavaScopeProvider;", "Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "()V", "buildDeclaredMemberScope", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "buildJavaEnhancementScope", "Lorg/jetbrains/kotlin/fir/java/scopes/JavaClassMembersEnhancementScope;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "memberRequiredPhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "buildSyntheticScopeForAnnotations", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "session", "enhancementScope", "buildUseSiteMemberScopeWithJavaTypes", "Lorg/jetbrains/kotlin/fir/java/scopes/JavaClassUseSiteMemberScope;", "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaClass;", "getNestedClassifierScope", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getStaticMemberScopeForCallables", "Lorg/jetbrains/kotlin/fir/java/scopes/JavaClassStaticEnhancementScope;", "visitedClasses", "", "getUseSiteMemberScope", "findClosestJavaSuperTypes", "", "findJavaSuperClass", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaScopeProvider extends FirScopeProvider {
    public static final JavaScopeProvider INSTANCE = new JavaScopeProvider();

    private JavaScopeProvider() {
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScopeProvider
    public FirTypeScope getUseSiteMemberScope(FirClass klass, FirSession useSiteSession, ScopeSession scopeSession, FirResolvePhase memberRequiredPhase) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        FirClassSymbol<? extends FirClass> symbol = klass.getSymbol();
        Intrinsics.checkNotNull(symbol, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol");
        FirRegularClassSymbol firRegularClassSymbol = (FirRegularClassSymbol) symbol;
        JavaClassMembersEnhancementScope javaClassMembersEnhancementScopeBuildJavaEnhancementScope = buildJavaEnhancementScope(useSiteSession, firRegularClassSymbol, scopeSession, memberRequiredPhase);
        if (klass.getClassKind() == ClassKind.ANNOTATION_CLASS) {
            return buildSyntheticScopeForAnnotations(useSiteSession, firRegularClassSymbol, scopeSession, javaClassMembersEnhancementScopeBuildJavaEnhancementScope);
        }
        return javaClassMembersEnhancementScopeBuildJavaEnhancementScope;
    }

    private final FirTypeScope buildSyntheticScopeForAnnotations(FirSession session, FirRegularClassSymbol symbol, ScopeSession scopeSession, JavaClassMembersEnhancementScope enhancementScope) {
        ScopeSessionKey<?, ?> scopeSessionKey = JavaScopeProviderKt.JAVA_SYNTHETIC_FOR_ANNOTATIONS;
        HashMap<Object, HashMap<ScopeSessionKey<?, ?>, Object>> mapScopes = scopeSession.scopes();
        HashMap<ScopeSessionKey<?, ?>, Object> map = mapScopes.get(symbol);
        if (map == null) {
            map = new HashMap<>();
            mapScopes.put(symbol, map);
        }
        HashMap<ScopeSessionKey<?, ?>, Object> map2 = map;
        Object javaAnnotationSyntheticPropertiesScope = map2.get(scopeSessionKey);
        if (javaAnnotationSyntheticPropertiesScope == null) {
            javaAnnotationSyntheticPropertiesScope = new JavaAnnotationSyntheticPropertiesScope(session, symbol, enhancementScope);
            map2.put(scopeSessionKey, javaAnnotationSyntheticPropertiesScope);
        }
        return (JavaAnnotationSyntheticPropertiesScope) javaAnnotationSyntheticPropertiesScope;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final JavaClassMembersEnhancementScope buildJavaEnhancementScope(FirSession useSiteSession, FirRegularClassSymbol symbol, ScopeSession scopeSession, FirResolvePhase memberRequiredPhase) {
        ScopeSessionKey<?, ?> scopeSessionKey = JavaScopeProviderKt.JAVA_ENHANCEMENT;
        HashMap<Object, HashMap<ScopeSessionKey<?, ?>, Object>> mapScopes = scopeSession.scopes();
        HashMap<ScopeSessionKey<?, ?>, Object> map = mapScopes.get(symbol);
        if (map == null) {
            map = new HashMap<>();
            mapScopes.put(symbol, map);
        }
        HashMap<ScopeSessionKey<?, ?>, Object> map2 = map;
        Object obj = map2.get(scopeSessionKey);
        if (obj == null) {
            FirRegularClass firRegularClass = (FirRegularClass) symbol.getFir();
            if (!(firRegularClass instanceof FirJavaClass)) {
                throw new IllegalArgumentException((FirDeclarationUtilKt.getClassId(firRegularClass) + " is expected to be FirJavaClass, but " + Reflection.getOrCreateKotlinClass(firRegularClass.getClass()) + " found").toString());
            }
            JavaClassMembersEnhancementScope javaClassMembersEnhancementScope = new JavaClassMembersEnhancementScope(useSiteSession, symbol, INSTANCE.buildUseSiteMemberScopeWithJavaTypes((FirJavaClass) firRegularClass, useSiteSession, scopeSession, memberRequiredPhase));
            map2.put(scopeSessionKey, javaClassMembersEnhancementScope);
            obj = javaClassMembersEnhancementScope;
        }
        return (JavaClassMembersEnhancementScope) obj;
    }

    private final FirContainingNamesAwareScope buildDeclaredMemberScope(FirSession useSiteSession, FirRegularClass regularClass) {
        if (regularClass instanceof FirJavaClass) {
            return FirDeclaredMemberScopeProviderKt.declaredMemberScopeWithLazyNestedScope(useSiteSession, regularClass, ((FirJavaClass) regularClass).getExistingNestedClassifierNames$java(), FirSymbolProviderKt.getSymbolProvider(useSiteSession));
        }
        return FirDeclaredMemberScopeProviderKt.declaredMemberScope(useSiteSession, regularClass, (FirResolvePhase) null);
    }

    private final JavaClassUseSiteMemberScope buildUseSiteMemberScopeWithJavaTypes(FirJavaClass regularClass, FirSession useSiteSession, ScopeSession scopeSession, FirResolvePhase memberRequiredPhase) {
        List listLookupSuperTypes$default;
        FirRegularClassSymbol symbol = regularClass.getSymbol();
        ScopeSessionKey<?, ?> scopeSessionKey = JavaScopeProviderKt.JAVA_USE_SITE;
        HashMap<Object, HashMap<ScopeSessionKey<?, ?>, Object>> mapScopes = scopeSession.scopes();
        HashMap<ScopeSessionKey<?, ?>, Object> map = mapScopes.get(symbol);
        if (map == null) {
            map = new HashMap<>();
            mapScopes.put(symbol, map);
        }
        HashMap<ScopeSessionKey<?, ?>, Object> map2 = map;
        Object javaClassUseSiteMemberScope = map2.get(scopeSessionKey);
        if (javaClassUseSiteMemberScope == null) {
            FirContainingNamesAwareScope firContainingNamesAwareScopeBuildDeclaredMemberScope = INSTANCE.buildDeclaredMemberScope(useSiteSession, regularClass);
            FirJavaClass firJavaClass = regularClass;
            if (SupertypeUtilsKt.isThereLoopInSupertypes(firJavaClass, useSiteSession)) {
                listLookupSuperTypes$default = CollectionsKt.listOf(TypeConstructionUtilsKt.constructClassLikeType$default(StandardClassIds.INSTANCE.getAny(), new ConeTypeProjection[0], false, null, 4, null));
            } else {
                listLookupSuperTypes$default = SupertypeUtilsKt.lookupSuperTypes$default((FirClass) firJavaClass, true, false, useSiteSession, true, (SupertypeSupplier) null, 32, (Object) null);
            }
            ArrayList arrayList = new ArrayList();
            Iterator it = listLookupSuperTypes$default.iterator();
            while (it.hasNext()) {
                FirTypeScope firTypeScopeScopeForSupertype = FirKotlinScopeProviderKt.scopeForSupertype((ConeClassLikeType) it.next(), useSiteSession, scopeSession, firJavaClass, memberRequiredPhase);
                if (firTypeScopeScopeForSupertype != null) {
                    arrayList.add(firTypeScopeScopeForSupertype);
                }
            }
            javaClassUseSiteMemberScope = new JavaClassUseSiteMemberScope(regularClass, useSiteSession, arrayList, firContainingNamesAwareScopeBuildDeclaredMemberScope);
            map2.put(scopeSessionKey, javaClassUseSiteMemberScope);
        }
        return (JavaClassUseSiteMemberScope) javaClassUseSiteMemberScope;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScopeProvider
    public FirContainingNamesAwareScope getStaticMemberScopeForCallables(FirClass klass, FirSession useSiteSession, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        JavaClassStaticEnhancementScope staticMemberScopeForCallables = getStaticMemberScopeForCallables(klass, useSiteSession, scopeSession, new HashSet());
        if (staticMemberScopeForCallables == null) {
            return null;
        }
        return new FirNameAwareOnlyCallablesScope(new FirStaticScope(staticMemberScopeForCallables));
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0067  */
    private final JavaClassStaticEnhancementScope getStaticMemberScopeForCallables(FirClass klass, FirSession useSiteSession, ScopeSession scopeSession, Set<FirRegularClass> visitedClasses) {
        FirTypeScope.Empty empty;
        if (!(klass instanceof FirJavaClass) || !visitedClasses.add(klass)) {
            return null;
        }
        FirJavaClass firJavaClass = (FirJavaClass) klass;
        FirRegularClassSymbol symbol = firJavaClass.getSymbol();
        ScopeSessionKey<?, ?> scopeSessionKey = JavaScopeProviderKt.JAVA_ENHANCEMENT_FOR_STATIC;
        HashMap<Object, HashMap<ScopeSessionKey<?, ?>, Object>> mapScopes = scopeSession.scopes();
        HashMap<ScopeSessionKey<?, ?>, Object> map = mapScopes.get(symbol);
        if (map == null) {
            map = new HashMap<>();
            mapScopes.put(symbol, map);
        }
        HashMap<ScopeSessionKey<?, ?>, Object> map2 = map;
        Object obj = map2.get(scopeSessionKey);
        if (obj == null) {
            JavaScopeProvider javaScopeProvider = INSTANCE;
            FirRegularClass firRegularClass = (FirRegularClass) klass;
            FirContainingNamesAwareScope firContainingNamesAwareScopeBuildDeclaredMemberScope = javaScopeProvider.buildDeclaredMemberScope(useSiteSession, firRegularClass);
            FirRegularClass firRegularClassFindJavaSuperClass = javaScopeProvider.findJavaSuperClass(firRegularClass, useSiteSession);
            if (firRegularClassFindJavaSuperClass != null) {
                FirScopeProvider scopeProvider = firRegularClassFindJavaSuperClass.getScopeProvider();
                JavaScopeProvider javaScopeProvider2 = scopeProvider instanceof JavaScopeProvider ? (JavaScopeProvider) scopeProvider : null;
                JavaClassStaticEnhancementScope staticMemberScopeForCallables = javaScopeProvider2 != null ? javaScopeProvider2.getStaticMemberScopeForCallables(firRegularClassFindJavaSuperClass, useSiteSession, scopeSession, visitedClasses) : null;
                if (staticMemberScopeForCallables != null) {
                    empty = staticMemberScopeForCallables;
                } else {
                    empty = FirTypeScope.Empty.INSTANCE;
                }
            } else {
                empty = FirTypeScope.Empty.INSTANCE;
            }
            Collection<FirRegularClass> collectionFindClosestJavaSuperTypes = javaScopeProvider.findClosestJavaSuperTypes(firRegularClass, useSiteSession);
            ArrayList arrayList = new ArrayList();
            for (FirRegularClass firRegularClass2 : collectionFindClosestJavaSuperTypes) {
                FirScopeProvider scopeProvider2 = firRegularClass2.getScopeProvider();
                JavaScopeProvider javaScopeProvider3 = scopeProvider2 instanceof JavaScopeProvider ? (JavaScopeProvider) scopeProvider2 : null;
                JavaClassStaticEnhancementScope staticMemberScopeForCallables2 = javaScopeProvider3 != null ? javaScopeProvider3.getStaticMemberScopeForCallables(firRegularClass2, useSiteSession, scopeSession, visitedClasses) : null;
                if (staticMemberScopeForCallables2 != null) {
                    arrayList.add(staticMemberScopeForCallables2);
                }
            }
            JavaClassStaticEnhancementScope javaClassStaticEnhancementScope = new JavaClassStaticEnhancementScope(useSiteSession, firJavaClass.getSymbol(), new JavaClassStaticUseSiteScope(useSiteSession, firContainingNamesAwareScopeBuildDeclaredMemberScope, empty, arrayList, firJavaClass.getJavaTypeParameterStack()));
            map2.put(scopeSessionKey, javaClassStaticEnhancementScope);
            obj = javaClassStaticEnhancementScope;
        }
        JavaClassStaticEnhancementScope javaClassStaticEnhancementScope2 = (JavaClassStaticEnhancementScope) obj;
        visitedClasses.remove(klass);
        return javaClassStaticEnhancementScope2;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0022  */
    /* JADX WARN: Multi-variable type inference failed */
    private final FirRegularClass findJavaSuperClass(FirRegularClass firRegularClass, FirSession firSession) {
        FirRegularClass firRegularClass2;
        do {
            Iterator<T> it = FirDeclarationUtilKt.getSuperConeTypes(firRegularClass).iterator();
            while (true) {
                if (!it.hasNext()) {
                    firRegularClass = null;
                    break;
                }
                ConeClassLikeType coneClassLikeType = (ConeClassLikeType) it.next();
                if (ConeBuiltinTypeUtilsKt.isAny(coneClassLikeType)) {
                    firRegularClass2 = null;
                } else {
                    FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), firSession);
                    FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
                    firRegularClass2 = firClassLikeDeclaration instanceof FirRegularClass ? (FirRegularClass) firClassLikeDeclaration : null;
                    if (firRegularClass2 == null || firRegularClass2.getClassKind() != ClassKind.CLASS) {
                        firRegularClass2 = null;
                    }
                }
                if (firRegularClass2 != null) {
                    firRegularClass = firRegularClass2;
                    break;
                }
            }
            if (firRegularClass == null) {
                return null;
            }
        } while (!(firRegularClass.getOrigin() instanceof FirDeclarationOrigin.Java));
        return firRegularClass;
    }

    private final Collection<FirRegularClass> findClosestJavaSuperTypes(final FirRegularClass firRegularClass, final FirSession firSession) {
        final ArrayList arrayList = new ArrayList();
        DFS.dfs(CollectionsKt.listOf(firRegularClass), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.fir.java.JavaScopeProvider$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return JavaScopeProvider.findClosestJavaSuperTypes$lambda$12(firSession, (FirRegularClass) obj);
            }
        }, new DFS.AbstractNodeHandler<FirRegularClass, Unit>() { // from class: org.jetbrains.kotlin.fir.java.JavaScopeProvider.findClosestJavaSuperTypes.2
            /* JADX INFO: renamed from: result, reason: collision with other method in class */
            public void m7050result() {
            }

            @Override // org.jetbrains.kotlin.utils.DFS.NodeHandler
            public /* bridge */ /* synthetic */ Object result() {
                m7050result();
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
            public boolean beforeChildren(FirRegularClass current) {
                if (firRegularClass == current || !(current instanceof FirJavaClass)) {
                    return true;
                }
                arrayList.add(current);
                return false;
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final Iterable findClosestJavaSuperTypes$lambda$12(FirSession useSiteSession, FirRegularClass firRegularClass) {
        Intrinsics.checkNotNullParameter(useSiteSession, "$useSiteSession");
        Intrinsics.checkNotNull(firRegularClass);
        List<ConeClassLikeType> superConeTypes = FirDeclarationUtilKt.getSuperConeTypes(firRegularClass);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = superConeTypes.iterator();
        while (it.hasNext()) {
            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(((ConeClassLikeType) it.next()).getLookupTag(), useSiteSession);
            FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
            FirRegularClass firRegularClass2 = firClassLikeDeclaration instanceof FirRegularClass ? (FirRegularClass) firClassLikeDeclaration : null;
            if (firRegularClass2 != null) {
                arrayList.add(firRegularClass2);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScopeProvider
    public FirContainingNamesAwareScope getNestedClassifierScope(FirClass klass, FirSession useSiteSession, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        return FirDeclaredMemberScopeProviderKt.lazyNestedClassifierScope(FirDeclarationUtilKt.getClassId(klass), ((FirJavaClass) klass).getExistingNestedClassifierNames$java(), FirSymbolProviderKt.getSymbolProvider(useSiteSession));
    }
}
