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
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirSymbolNamesProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\fH\u0016J\u0018\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/FirCompositeSymbolNamesProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "providers", "", "(Ljava/util/List;)V", "mayHaveSyntheticFunctionTypes", "", "getMayHaveSyntheticFunctionTypes", "()Z", "getProviders", "()Ljava/util/List;", "getPackageNamesWithTopLevelCallables", "", "", "getTopLevelCallableNamesInPackage", "Lorg/jetbrains/kotlin/name/Name;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getTopLevelClassifierNamesInPackage", "mayHaveSyntheticFunctionType", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Companion"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirCompositeSymbolNamesProvider extends FirSymbolNamesProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean mayHaveSyntheticFunctionTypes;
    private final List<FirSymbolNamesProvider> providers;

    /* JADX WARN: Multi-variable type inference failed */
    public FirCompositeSymbolNamesProvider(List<? extends FirSymbolNamesProvider> providers) {
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

    public final List<FirSymbolNamesProvider> getProviders() {
        return this.providers;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
    public Set<String> getTopLevelClassifierNamesInPackage(FqName packageFqName) {
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

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
    public Set<String> getPackageNamesWithTopLevelCallables() {
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

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
    public Set<Name> getTopLevelCallableNamesInPackage(FqName packageFqName) {
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

    /* JADX INFO: compiled from: FirSymbolNamesProvider.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006J\u0014\u0010\u0007\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\b0\u0006¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/FirCompositeSymbolNamesProvider$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "providers", "", "fromSymbolProviders", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirSymbolNamesProvider create(List<? extends FirSymbolNamesProvider> providers) {
            Intrinsics.checkNotNullParameter(providers, "providers");
            int size = providers.size();
            if (size == 0) {
                return FirEmptySymbolNamesProvider.INSTANCE;
            }
            if (size == 1) {
                return (FirSymbolNamesProvider) CollectionsKt.single((List) providers);
            }
            return new FirCompositeSymbolNamesProvider(providers);
        }

        public final FirSymbolNamesProvider fromSymbolProviders(List<? extends FirSymbolProvider> providers) {
            Intrinsics.checkNotNullParameter(providers, "providers");
            List<? extends FirSymbolProvider> list = providers;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((FirSymbolProvider) it.next()).getSymbolNamesProvider());
            }
            return create(arrayList);
        }
    }
}
