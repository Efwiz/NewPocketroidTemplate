package org.jetbrains.kotlin.fir.resolve.providers;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH&J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0010H&J\"\u0010\u0012\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00140\u00132\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J*\u0010\u0018\u001a\u00020\u00192\u0010\u0010\u001a\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00140\u001b2\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H'J\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00132\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J&\u0010\u001e\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001b2\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H'J\u001e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u00132\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J&\u0010!\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020 0\u001b2\u0006\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H'R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "symbolNamesProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getSymbolNamesProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getClassLikeSymbolByClassId", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getPackage", "Lorg/jetbrains/kotlin/name/FqName;", "fqName", "getTopLevelCallableSymbols", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "packageFqName", "name", "Lorg/jetbrains/kotlin/name/Name;", "getTopLevelCallableSymbolsTo", "", FirebaseAnalytics.Param.DESTINATION, "", "getTopLevelFunctionSymbols", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getTopLevelFunctionSymbolsTo", "getTopLevelPropertySymbols", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "getTopLevelPropertySymbolsTo", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirSymbolProvider implements FirSessionComponent {
    private final FirSession session;

    public abstract FirClassLikeSymbol<?> getClassLikeSymbolByClassId(ClassId classId);

    public abstract FqName getPackage(FqName fqName);

    public abstract FirSymbolNamesProvider getSymbolNamesProvider();

    @FirSymbolProviderInternals
    public abstract void getTopLevelCallableSymbolsTo(List<FirCallableSymbol<?>> destination, FqName packageFqName, Name name2);

    @FirSymbolProviderInternals
    public abstract void getTopLevelFunctionSymbolsTo(List<FirNamedFunctionSymbol> destination, FqName packageFqName, Name name2);

    @FirSymbolProviderInternals
    public abstract void getTopLevelPropertySymbolsTo(List<FirPropertySymbol> destination, FqName packageFqName, Name name2);

    public FirSymbolProvider(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
    }

    public final FirSession getSession() {
        return this.session;
    }

    public List<FirCallableSymbol<?>> getTopLevelCallableSymbols(FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        List<FirCallableSymbol<?>> listCreateListBuilder = CollectionsKt.createListBuilder();
        getTopLevelCallableSymbolsTo(listCreateListBuilder, packageFqName, name2);
        return CollectionsKt.build(listCreateListBuilder);
    }

    public List<FirNamedFunctionSymbol> getTopLevelFunctionSymbols(FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        List<FirNamedFunctionSymbol> listCreateListBuilder = CollectionsKt.createListBuilder();
        getTopLevelFunctionSymbolsTo(listCreateListBuilder, packageFqName, name2);
        return CollectionsKt.build(listCreateListBuilder);
    }

    public List<FirPropertySymbol> getTopLevelPropertySymbols(FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        List<FirPropertySymbol> listCreateListBuilder = CollectionsKt.createListBuilder();
        getTopLevelPropertySymbolsTo(listCreateListBuilder, packageFqName, name2);
        return CollectionsKt.build(listCreateListBuilder);
    }
}
