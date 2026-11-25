package org.jetbrains.kotlin.fir.serialization;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactory;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.extensions.FirDeclarationsForMetadataProviderExtension;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirProvidedDeclarationsForMetadataService.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001:\u0001\u001fB\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u001c\u0010\u0012\u001a\u00020\r2\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00110\u00052\u0006\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u000eH\u0002J\"\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00052\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\u0014\u001a\u00020\u000eH\u0016J\"\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00052\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\u0014\u001a\u00020\u000eH\u0016J&\u0010\u001c\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001d0\u00052\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\u0014\u001a\u00020\u000eH\u0016J\u001e\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00110\u00052\u0006\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u000eH\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\n\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u0005\u0012\u0004\u0012\u00020\u000e0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirProvidedDeclarationsForMetadataServiceImpl;", "Lorg/jetbrains/kotlin/fir/serialization/FirProvidedDeclarationsForMetadataService;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "extensionDeclarationProviders", "", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationsForMetadataProviderExtension;", "(Lorg/jetbrains/kotlin/fir/FirSession;Ljava/util/List;)V", "cachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "membersCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "Lorg/jetbrains/kotlin/fir/serialization/FirProvidedDeclarationsForMetadataServiceImpl$ClassDeclarations;", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "topLevelsCache", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "computeMemberDeclarations", NativeSymbol.TYPE_NAME, "scopeSession", "computeTopLevelDeclarations", "packageFqName", "getProvidedCallables", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "owner", "getProvidedConstructors", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "getProvidedNestedClassifiers", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getProvidedTopLevelDeclarations", "ClassDeclarations", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FirProvidedDeclarationsForMetadataServiceImpl extends FirProvidedDeclarationsForMetadataService {
    private final FirCachesFactory cachesFactory;
    private final List<FirDeclarationsForMetadataProviderExtension> extensionDeclarationProviders;
    private final FirCache<FirClassSymbol<?>, ClassDeclarations, ScopeSession> membersCache;
    private final FirCache<FqName, List<FirDeclaration>, ScopeSession> topLevelsCache;

    /* JADX WARN: Multi-variable type inference failed */
    public FirProvidedDeclarationsForMetadataServiceImpl(FirSession session, List<? extends FirDeclarationsForMetadataProviderExtension> extensionDeclarationProviders) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(extensionDeclarationProviders, "extensionDeclarationProviders");
        this.extensionDeclarationProviders = extensionDeclarationProviders;
        FirCachesFactory firCachesFactory = FirCachesFactoryKt.getFirCachesFactory(session);
        this.cachesFactory = firCachesFactory;
        this.topLevelsCache = firCachesFactory.createCache(new FirProvidedDeclarationsForMetadataServiceImpl$topLevelsCache$1(this));
        this.membersCache = firCachesFactory.createCache(new FirProvidedDeclarationsForMetadataServiceImpl$membersCache$1(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<FirDeclaration> computeTopLevelDeclarations(FqName packageFqName, ScopeSession scopeSession) {
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        Iterator<FirDeclarationsForMetadataProviderExtension> it = this.extensionDeclarationProviders.iterator();
        while (it.hasNext()) {
            Iterator<FirDeclaration> it2 = it.next().provideTopLevelDeclarations(packageFqName, scopeSession).iterator();
            while (it2.hasNext()) {
                listCreateListBuilder.add(it2.next());
            }
        }
        return CollectionsKt.build(listCreateListBuilder);
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirProvidedDeclarationsForMetadataService
    public List<FirDeclaration> getProvidedTopLevelDeclarations(FqName packageFqName, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        return this.topLevelsCache.getValue(packageFqName, scopeSession);
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirProvidedDeclarationsForMetadataService
    public List<FirConstructor> getProvidedConstructors(FirClassSymbol<?> owner, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        return this.membersCache.getValue(owner, scopeSession).getProvidedConstructors();
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirProvidedDeclarationsForMetadataService
    public List<FirCallableDeclaration> getProvidedCallables(FirClassSymbol<?> owner, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        return this.membersCache.getValue(owner, scopeSession).getProvidedCallables();
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirProvidedDeclarationsForMetadataService
    public List<FirClassLikeSymbol<?>> getProvidedNestedClassifiers(FirClassSymbol<?> owner, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        return this.membersCache.getValue(owner, scopeSession).getProvidedNestedClasses();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FirProvidedDeclarationsForMetadataService.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B3\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u0010\u0010\u0007\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0\u0003¢\u0006\u0002\u0010\tJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0003J\u0013\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0\u0003HÆ\u0003J=\u0010\u0011\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u0012\b\u0002\u0010\u0007\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u001b\u0010\u0007\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000b¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirProvidedDeclarationsForMetadataServiceImpl$ClassDeclarations;", "", "providedCallables", "", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "providedConstructors", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "providedNestedClasses", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getProvidedCallables", "()Ljava/util/List;", "getProvidedConstructors", "getProvidedNestedClasses", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final /* data */ class ClassDeclarations {
        private final List<FirCallableDeclaration> providedCallables;
        private final List<FirConstructor> providedConstructors;
        private final List<FirClassLikeSymbol<?>> providedNestedClasses;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ClassDeclarations copy$default(ClassDeclarations classDeclarations, List list, List list2, List list3, int i, Object obj) {
            if ((i & 1) != 0) {
                list = classDeclarations.providedCallables;
            }
            if ((i & 2) != 0) {
                list2 = classDeclarations.providedConstructors;
            }
            if ((i & 4) != 0) {
                list3 = classDeclarations.providedNestedClasses;
            }
            return classDeclarations.copy(list, list2, list3);
        }

        public final List<FirCallableDeclaration> component1() {
            return this.providedCallables;
        }

        public final List<FirConstructor> component2() {
            return this.providedConstructors;
        }

        public final List<FirClassLikeSymbol<?>> component3() {
            return this.providedNestedClasses;
        }

        public final ClassDeclarations copy(List<? extends FirCallableDeclaration> providedCallables, List<? extends FirConstructor> providedConstructors, List<? extends FirClassLikeSymbol<?>> providedNestedClasses) {
            Intrinsics.checkNotNullParameter(providedCallables, "providedCallables");
            Intrinsics.checkNotNullParameter(providedConstructors, "providedConstructors");
            Intrinsics.checkNotNullParameter(providedNestedClasses, "providedNestedClasses");
            return new ClassDeclarations(providedCallables, providedConstructors, providedNestedClasses);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ClassDeclarations)) {
                return false;
            }
            ClassDeclarations classDeclarations = (ClassDeclarations) other;
            return Intrinsics.areEqual(this.providedCallables, classDeclarations.providedCallables) && Intrinsics.areEqual(this.providedConstructors, classDeclarations.providedConstructors) && Intrinsics.areEqual(this.providedNestedClasses, classDeclarations.providedNestedClasses);
        }

        public int hashCode() {
            return (((this.providedCallables.hashCode() * 31) + this.providedConstructors.hashCode()) * 31) + this.providedNestedClasses.hashCode();
        }

        public String toString() {
            return "ClassDeclarations(providedCallables=" + this.providedCallables + ", providedConstructors=" + this.providedConstructors + ", providedNestedClasses=" + this.providedNestedClasses + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ClassDeclarations(List<? extends FirCallableDeclaration> providedCallables, List<? extends FirConstructor> providedConstructors, List<? extends FirClassLikeSymbol<?>> providedNestedClasses) {
            Intrinsics.checkNotNullParameter(providedCallables, "providedCallables");
            Intrinsics.checkNotNullParameter(providedConstructors, "providedConstructors");
            Intrinsics.checkNotNullParameter(providedNestedClasses, "providedNestedClasses");
            this.providedCallables = providedCallables;
            this.providedConstructors = providedConstructors;
            this.providedNestedClasses = providedNestedClasses;
        }

        public final List<FirCallableDeclaration> getProvidedCallables() {
            return this.providedCallables;
        }

        public final List<FirConstructor> getProvidedConstructors() {
            return this.providedConstructors;
        }

        public final List<FirClassLikeSymbol<?>> getProvidedNestedClasses() {
            return this.providedNestedClasses;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final ClassDeclarations computeMemberDeclarations(FirClassSymbol<?> symbol, ScopeSession scopeSession) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator<FirDeclarationsForMetadataProviderExtension> it = this.extensionDeclarationProviders.iterator();
        while (it.hasNext()) {
            for (FirDeclaration firDeclaration : it.next().provideDeclarationsForClass((FirClass) symbol.getFir(), scopeSession)) {
                if (firDeclaration instanceof FirConstructor) {
                    arrayList2.add(firDeclaration);
                } else if (firDeclaration instanceof FirCallableDeclaration) {
                    arrayList.add(firDeclaration);
                } else {
                    if (!(firDeclaration instanceof FirClassLikeDeclaration)) {
                        throw new IllegalStateException(("Unsupported declaration type in: " + symbol + StringUtil.SPACE + UtilsKt.render(firDeclaration)).toString());
                    }
                    arrayList3.add(((FirClassLikeDeclaration) firDeclaration).getSymbol());
                }
            }
        }
        return new ClassDeclarations(arrayList, arrayList2, arrayList3);
    }
}
