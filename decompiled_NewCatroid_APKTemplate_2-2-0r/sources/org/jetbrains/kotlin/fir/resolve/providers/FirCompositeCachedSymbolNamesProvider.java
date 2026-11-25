package org.jetbrains.kotlin.fir.resolve.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.providers.impl.FirSyntheticFunctionInterfaceProviderBase;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirCachedSymbolNamesProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fH\u0016J\u0018\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/FirCompositeCachedSymbolNamesProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirCachedSymbolNamesProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "providers", "", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "(Lorg/jetbrains/kotlin/fir/FirSession;Ljava/util/List;)V", "mayHaveSyntheticFunctionTypes", "", "getMayHaveSyntheticFunctionTypes", "()Z", "getProviders", "()Ljava/util/List;", "computePackageNamesWithTopLevelCallables", "", "", "computeTopLevelCallableNames", "Lorg/jetbrains/kotlin/name/Name;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "computeTopLevelClassifierNames", "mayHaveSyntheticFunctionType", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Companion"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirCompositeCachedSymbolNamesProvider extends FirCachedSymbolNamesProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean mayHaveSyntheticFunctionTypes;
    private final List<FirSymbolNamesProvider> providers;

    public final List<FirSymbolNamesProvider> getProviders() {
        return this.providers;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FirCompositeCachedSymbolNamesProvider(FirSession session, List<? extends FirSymbolNamesProvider> providers) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(providers, "providers");
        this.providers = providers;
        List<? extends FirSymbolNamesProvider> list = providers;
        boolean z = false;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (((FirSymbolNamesProvider) it.next()).getMayHaveSyntheticFunctionTypes()) {
                    z = true;
                    break;
                }
            }
        }
        this.mayHaveSyntheticFunctionTypes = z;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider
    public Set<String> computeTopLevelClassifierNames(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        List<FirSymbolNamesProvider> list = this.providers;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            Set<String> topLevelClassifierNamesInPackage = ((FirSymbolNamesProvider) it.next()).getTopLevelClassifierNamesInPackage(packageFqName);
            if (topLevelClassifierNamesInPackage == null) {
                return null;
            }
            CollectionsKt.addAll(linkedHashSet, topLevelClassifierNamesInPackage);
        }
        return linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider
    public Set<String> computePackageNamesWithTopLevelCallables() {
        List<FirSymbolNamesProvider> list = this.providers;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            Set<String> packageNamesWithTopLevelCallables = ((FirSymbolNamesProvider) it.next()).getPackageNamesWithTopLevelCallables();
            if (packageNamesWithTopLevelCallables == null) {
                return null;
            }
            CollectionsKt.addAll(linkedHashSet, packageNamesWithTopLevelCallables);
        }
        return linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider
    public Set<Name> computeTopLevelCallableNames(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        List<FirSymbolNamesProvider> list = this.providers;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            Set<Name> topLevelCallableNamesInPackage = ((FirSymbolNamesProvider) it.next()).getTopLevelCallableNamesInPackage(packageFqName);
            if (topLevelCallableNamesInPackage == null) {
                return null;
            }
            CollectionsKt.addAll(linkedHashSet, topLevelCallableNamesInPackage);
        }
        return linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
    public boolean getMayHaveSyntheticFunctionTypes() {
        return this.mayHaveSyntheticFunctionTypes;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
    public boolean mayHaveSyntheticFunctionType(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        if (!FirSyntheticFunctionInterfaceProviderBase.INSTANCE.mayBeSyntheticFunctionClassName(classId)) {
            return false;
        }
        List<FirSymbolNamesProvider> list = this.providers;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (((FirSymbolNamesProvider) it.next()).mayHaveSyntheticFunctionType(classId)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: compiled from: FirCachedSymbolNamesProvider.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\bJ\u001c\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\n0\b¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/FirCompositeCachedSymbolNamesProvider$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "providers", "", "fromSymbolProviders", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirSymbolNamesProvider create(FirSession session, List<? extends FirSymbolNamesProvider> providers) {
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(providers, "providers");
            int size = providers.size();
            if (size == 0) {
                return FirEmptySymbolNamesProvider.INSTANCE;
            }
            if (size == 1) {
                FirSymbolNamesProvider firSymbolNamesProvider = (FirSymbolNamesProvider) CollectionsKt.single((List) providers);
                return firSymbolNamesProvider instanceof FirCachedSymbolNamesProvider ? (FirCachedSymbolNamesProvider) firSymbolNamesProvider : new FirDelegatingCachedSymbolNamesProvider(session, firSymbolNamesProvider);
            }
            return new FirCompositeCachedSymbolNamesProvider(session, providers);
        }

        public final FirSymbolNamesProvider fromSymbolProviders(FirSession session, List<? extends FirSymbolProvider> providers) {
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(providers, "providers");
            List<? extends FirSymbolProvider> list = providers;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((FirSymbolProvider) it.next()).getSymbolNamesProvider());
            }
            return create(session, arrayList);
        }
    }
}
