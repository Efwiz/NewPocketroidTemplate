package org.jetbrains.kotlin.fir.resolve.providers;

import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirCachedSymbolNamesProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0014\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\nH&J\u0018\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\u0006\u0010\u0016\u001a\u00020\tH&J\u0018\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\n2\u0006\u0010\u0016\u001a\u00020\tH&J\u0010\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\nH\u0016J\u0018\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\u0006\u0010\u0016\u001a\u00020\tH\u0016J\u0018\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\n2\u0006\u0010\u0016\u001a\u00020\tH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R*\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\bX\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\n8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R*\u0010\u0013\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/FirCachedSymbolNamesProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "topLevelCallableNamesByPackage", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/name/FqName;", "", "Lorg/jetbrains/kotlin/name/Name;", "", "topLevelCallablePackageNames", "", "getTopLevelCallablePackageNames", "()Ljava/util/Set;", "topLevelCallablePackageNames$delegate", "Lkotlin/Lazy;", "topLevelClassifierNamesByPackage", "computePackageNamesWithTopLevelCallables", "computeTopLevelCallableNames", "packageFqName", "computeTopLevelClassifierNames", "getPackageNamesWithTopLevelCallables", "getTopLevelCallableNamesInPackage", "getTopLevelClassifierNamesInPackage", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirCachedSymbolNamesProvider extends FirSymbolNamesProvider {
    private final FirSession session;
    private final FirCache topLevelCallableNamesByPackage;

    /* JADX INFO: renamed from: topLevelCallablePackageNames$delegate, reason: from kotlin metadata */
    private final Lazy topLevelCallablePackageNames;
    private final FirCache topLevelClassifierNamesByPackage;

    public abstract Set<String> computePackageNamesWithTopLevelCallables();

    public abstract Set<Name> computeTopLevelCallableNames(FqName packageFqName);

    public abstract Set<String> computeTopLevelClassifierNames(FqName packageFqName);

    public FirCachedSymbolNamesProvider(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        this.topLevelClassifierNamesByPackage = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider$special$$inlined$createCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((FqName) obj, (Void) obj2);
            }

            public final Set<? extends String> invoke(FqName key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.$receiver$inlined.computeTopLevelClassifierNames(key);
            }
        });
        this.topLevelCallablePackageNames = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Set<? extends String>>() { // from class: org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider$topLevelCallablePackageNames$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends String> invoke() {
                return this.this$0.computePackageNamesWithTopLevelCallables();
            }
        });
        this.topLevelCallableNamesByPackage = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider$special$$inlined$createCache$2
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((FqName) obj, (Void) obj2);
            }

            public final Set<? extends Name> invoke(FqName key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.$receiver$inlined.computeTopLevelCallableNames(key);
            }
        });
    }

    protected final FirSession getSession() {
        return this.session;
    }

    private final Set<String> getTopLevelCallablePackageNames() {
        return (Set) this.topLevelCallablePackageNames.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
    public Set<String> getTopLevelClassifierNamesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return (Set) this.topLevelClassifierNamesByPackage.getValue(packageFqName, null);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
    public Set<String> getPackageNamesWithTopLevelCallables() {
        return getTopLevelCallablePackageNames();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
    public Set<Name> getTopLevelCallableNamesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Set<String> packageNamesWithTopLevelCallables = getPackageNamesWithTopLevelCallables();
        return (packageNamesWithTopLevelCallables == null || packageNamesWithTopLevelCallables.contains(packageFqName.asString())) ? (Set) this.topLevelCallableNamesByPackage.getValue(packageFqName, null) : SetsKt.emptySet();
    }
}
