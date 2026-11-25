package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirNameAwareCompositeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirDeclaredMemberScopeProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u001dB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J2\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00122\u000e\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u00142\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0002J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0010\u001a\u00020\u0007H\u0002J:\u0010\u0019\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00122\u000e\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u00142\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bJ\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0010\u001a\u00020\u0007R \u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\n\u001a\u0018\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirDeclaredMemberScopeProvider;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "declaredMemberCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirDeclaredMemberScopeProvider$DeclaredMemberScopeContext;", "nestedClassifierCache", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirNestedClassifierScope;", "", "getUseSiteSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "createDeclaredMemberScope", "klass", "useLazyNestedClassifierScope", "", "existingNames", "", "Lorg/jetbrains/kotlin/name/Name;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "createNestedClassifierScope", "declaredMemberScope", "memberRequiredPhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "nestedClassifierScope", "DeclaredMemberScopeContext", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDeclaredMemberScopeProvider implements FirSessionComponent {
    private final FirCache<FirClass, FirContainingNamesAwareScope, DeclaredMemberScopeContext> declaredMemberCache;
    private final FirCache nestedClassifierCache;
    private final FirSession useSiteSession;

    public FirDeclaredMemberScopeProvider(FirSession useSiteSession) {
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        this.useSiteSession = useSiteSession;
        this.declaredMemberCache = FirCachesFactoryKt.getFirCachesFactory(useSiteSession).createCache(new Function2<FirClass, DeclaredMemberScopeContext, FirContainingNamesAwareScope>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProvider$declaredMemberCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final FirContainingNamesAwareScope invoke(FirClass klass, FirDeclaredMemberScopeProvider.DeclaredMemberScopeContext context) {
                Intrinsics.checkNotNullParameter(klass, "klass");
                Intrinsics.checkNotNullParameter(context, "context");
                return this.this$0.createDeclaredMemberScope(klass, context.getUseLazyNestedClassifierScope(), context.getExistingNames(), context.getSymbolProvider());
            }
        });
        this.nestedClassifierCache = FirCachesFactoryKt.getFirCachesFactory(useSiteSession).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProvider$nestedClassifierCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final FirNestedClassifierScope invoke(FirClass klass, Void r2) {
                Intrinsics.checkNotNullParameter(klass, "klass");
                return this.this$0.createNestedClassifierScope(klass);
            }
        });
    }

    public final FirSession getUseSiteSession() {
        return this.useSiteSession;
    }

    public final FirContainingNamesAwareScope declaredMemberScope(FirClass klass, boolean useLazyNestedClassifierScope, List<Name> existingNames, FirSymbolProvider symbolProvider, FirResolvePhase memberRequiredPhase) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        if (memberRequiredPhase != null) {
            FirLazyDeclarationResolverKt.lazyResolveToPhaseWithCallableMembers(klass, memberRequiredPhase);
        }
        return this.declaredMemberCache.getValue(klass, new DeclaredMemberScopeContext(useLazyNestedClassifierScope, existingNames, symbolProvider));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FirDeclaredMemberScopeProvider.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\u0011\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\bHÆ\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bHÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00032\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0019\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirDeclaredMemberScopeProvider$DeclaredMemberScopeContext;", "", "useLazyNestedClassifierScope", "", "existingNames", "", "Lorg/jetbrains/kotlin/name/Name;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "(ZLjava/util/List;Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;)V", "getExistingNames", "()Ljava/util/List;", "getSymbolProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "getUseLazyNestedClassifierScope", "()Z", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final /* data */ class DeclaredMemberScopeContext {
        private final List<Name> existingNames;
        private final FirSymbolProvider symbolProvider;
        private final boolean useLazyNestedClassifierScope;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ DeclaredMemberScopeContext copy$default(DeclaredMemberScopeContext declaredMemberScopeContext, boolean z, List list, FirSymbolProvider firSymbolProvider, int i, Object obj) {
            if ((i & 1) != 0) {
                z = declaredMemberScopeContext.useLazyNestedClassifierScope;
            }
            if ((i & 2) != 0) {
                list = declaredMemberScopeContext.existingNames;
            }
            if ((i & 4) != 0) {
                firSymbolProvider = declaredMemberScopeContext.symbolProvider;
            }
            return declaredMemberScopeContext.copy(z, list, firSymbolProvider);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final boolean getUseLazyNestedClassifierScope() {
            return this.useLazyNestedClassifierScope;
        }

        public final List<Name> component2() {
            return this.existingNames;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final FirSymbolProvider getSymbolProvider() {
            return this.symbolProvider;
        }

        public final DeclaredMemberScopeContext copy(boolean useLazyNestedClassifierScope, List<Name> existingNames, FirSymbolProvider symbolProvider) {
            return new DeclaredMemberScopeContext(useLazyNestedClassifierScope, existingNames, symbolProvider);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DeclaredMemberScopeContext)) {
                return false;
            }
            DeclaredMemberScopeContext declaredMemberScopeContext = (DeclaredMemberScopeContext) other;
            return this.useLazyNestedClassifierScope == declaredMemberScopeContext.useLazyNestedClassifierScope && Intrinsics.areEqual(this.existingNames, declaredMemberScopeContext.existingNames) && Intrinsics.areEqual(this.symbolProvider, declaredMemberScopeContext.symbolProvider);
        }

        public int hashCode() {
            int iHashCode = Boolean.hashCode(this.useLazyNestedClassifierScope) * 31;
            List<Name> list = this.existingNames;
            int iHashCode2 = (iHashCode + (list == null ? 0 : list.hashCode())) * 31;
            FirSymbolProvider firSymbolProvider = this.symbolProvider;
            return iHashCode2 + (firSymbolProvider != null ? firSymbolProvider.hashCode() : 0);
        }

        public String toString() {
            return "DeclaredMemberScopeContext(useLazyNestedClassifierScope=" + this.useLazyNestedClassifierScope + ", existingNames=" + this.existingNames + ", symbolProvider=" + this.symbolProvider + ')';
        }

        public DeclaredMemberScopeContext(boolean z, List<Name> list, FirSymbolProvider firSymbolProvider) {
            this.useLazyNestedClassifierScope = z;
            this.existingNames = list;
            this.symbolProvider = firSymbolProvider;
        }

        public final boolean getUseLazyNestedClassifierScope() {
            return this.useLazyNestedClassifierScope;
        }

        public final List<Name> getExistingNames() {
            return this.existingNames;
        }

        public final FirSymbolProvider getSymbolProvider() {
            return this.symbolProvider;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirContainingNamesAwareScope createDeclaredMemberScope(FirClass klass, boolean useLazyNestedClassifierScope, List<Name> existingNames, FirSymbolProvider symbolProvider) {
        FirDeclarationOrigin origin = klass.getOrigin();
        if (origin.getGenerated()) {
            FirGeneratedClassDeclaredMemberScope firGeneratedClassDeclaredMemberScopeCreate = FirGeneratedClassDeclaredMemberScope.INSTANCE.create(this.useSiteSession, klass.getSymbol(), null, true);
            return firGeneratedClassDeclaredMemberScopeCreate != null ? firGeneratedClassDeclaredMemberScopeCreate : FirTypeScope.Empty.INSTANCE;
        }
        FirClassDeclaredMemberScopeImpl firClassDeclaredMemberScopeImpl = new FirClassDeclaredMemberScopeImpl(this.useSiteSession, klass, useLazyNestedClassifierScope, existingNames, symbolProvider);
        FirGeneratedClassDeclaredMemberScope firGeneratedClassDeclaredMemberScopeCreate2 = origin.getFromSource() || origin.getGenerated() ? FirGeneratedClassDeclaredMemberScope.INSTANCE.create(this.useSiteSession, klass.getSymbol(), firClassDeclaredMemberScopeImpl, false) : null;
        if (firGeneratedClassDeclaredMemberScopeCreate2 != null) {
            return new FirNameAwareCompositeScope(CollectionsKt.listOf((Object[]) new FirClassDeclaredMemberScope[]{firClassDeclaredMemberScopeImpl, firGeneratedClassDeclaredMemberScopeCreate2}));
        }
        return firClassDeclaredMemberScopeImpl;
    }

    public final FirNestedClassifierScope nestedClassifierScope(FirClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return (FirNestedClassifierScope) this.nestedClassifierCache.getValue(klass, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirNestedClassifierScope createNestedClassifierScope(FirClass klass) {
        FirNestedClassifierScopeImpl firCompositeNestedClassifierScope;
        FirDeclarationOrigin origin = klass.getOrigin();
        if (origin.getGenerated()) {
            firCompositeNestedClassifierScope = FirGeneratedClassNestedClassifierScope.INSTANCE.create(this.useSiteSession, klass.getSymbol(), null);
        } else {
            FirNestedClassifierScopeImpl firNestedClassifierScopeImpl = new FirNestedClassifierScopeImpl(klass, this.useSiteSession);
            FirGeneratedClassNestedClassifierScope firGeneratedClassNestedClassifierScopeCreate = origin.getFromSource() ? FirGeneratedClassNestedClassifierScope.INSTANCE.create(this.useSiteSession, klass.getSymbol(), firNestedClassifierScopeImpl) : null;
            if (firGeneratedClassNestedClassifierScopeCreate != null) {
                firCompositeNestedClassifierScope = new FirCompositeNestedClassifierScope(CollectionsKt.listOf((Object[]) new FirNestedClassifierScope[]{firNestedClassifierScopeImpl, firGeneratedClassNestedClassifierScopeCreate}), klass, this.useSiteSession);
            } else {
                firCompositeNestedClassifierScope = firNestedClassifierScopeImpl;
            }
        }
        if (firCompositeNestedClassifierScope == null || firCompositeNestedClassifierScope.isEmpty()) {
            return null;
        }
        return firCompositeNestedClassifierScope;
    }
}
