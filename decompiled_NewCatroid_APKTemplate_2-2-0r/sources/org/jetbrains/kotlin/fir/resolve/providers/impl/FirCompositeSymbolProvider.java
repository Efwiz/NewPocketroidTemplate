package org.jetbrains.kotlin.fir.resolve.providers.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirCompositeSymbolNamesProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderInternals;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirCompositeSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\r\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\"\u0010\u0014\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u00052\u0006\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J*\u0010\u0019\u001a\u00020\u001a2\u0010\u0010\u001b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00150\u001c2\u0006\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0017J&\u0010\u001d\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001c2\u0006\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0017J&\u0010\u001f\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020 0\u001c2\u0006\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0017R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirCompositeSymbolProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "providers", "", "(Lorg/jetbrains/kotlin/fir/FirSession;Ljava/util/List;)V", "getProviders", "()Ljava/util/List;", "symbolNamesProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getSymbolNamesProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getClassLikeSymbolByClassId", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getPackage", "Lorg/jetbrains/kotlin/name/FqName;", "fqName", "getTopLevelCallableSymbols", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "packageFqName", "name", "Lorg/jetbrains/kotlin/name/Name;", "getTopLevelCallableSymbolsTo", "", FirebaseAnalytics.Param.DESTINATION, "", "getTopLevelFunctionSymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getTopLevelPropertySymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCompositeSymbolProvider extends FirSymbolProvider {
    private final List<FirSymbolProvider> providers;
    private final FirSymbolNamesProvider symbolNamesProvider;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FirCompositeSymbolProvider(FirSession session, List<? extends FirSymbolProvider> providers) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(providers, "providers");
        this.providers = providers;
        this.symbolNamesProvider = FirCompositeSymbolNamesProvider.INSTANCE.fromSymbolProviders(providers);
    }

    public final List<FirSymbolProvider> getProviders() {
        return this.providers;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirSymbolNamesProvider getSymbolNamesProvider() {
        return this.symbolNamesProvider;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public List<FirCallableSymbol<?>> getTopLevelCallableSymbols(FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        List<FirSymbolProvider> list = this.providers;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((FirSymbolProvider) it.next()).getTopLevelCallableSymbols(packageFqName, name2));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelCallableSymbolsTo(List<FirCallableSymbol<?>> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        CollectionsKt.addAll(destination, getTopLevelCallableSymbols(packageFqName, name2));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelFunctionSymbolsTo(List<FirNamedFunctionSymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        Iterator<T> it = this.providers.iterator();
        while (it.hasNext()) {
            ((FirSymbolProvider) it.next()).getTopLevelFunctionSymbolsTo(destination, packageFqName, name2);
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelPropertySymbolsTo(List<FirPropertySymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        Iterator<T> it = this.providers.iterator();
        while (it.hasNext()) {
            ((FirSymbolProvider) it.next()).getTopLevelPropertySymbolsTo(destination, packageFqName, name2);
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FqName getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Iterator<T> it = this.providers.iterator();
        while (it.hasNext()) {
            FqName fqName2 = ((FirSymbolProvider) it.next()).getPackage(fqName);
            if (fqName2 != null) {
                return fqName2;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirClassLikeSymbol<?> getClassLikeSymbolByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Iterator<T> it = this.providers.iterator();
        while (it.hasNext()) {
            FirClassLikeSymbol<?> classLikeSymbolByClassId = ((FirSymbolProvider) it.next()).getClassLikeSymbolByClassId(classId);
            if (classLikeSymbolByClassId != null) {
                return classLikeSymbolByClassId;
            }
        }
        return null;
    }
}
