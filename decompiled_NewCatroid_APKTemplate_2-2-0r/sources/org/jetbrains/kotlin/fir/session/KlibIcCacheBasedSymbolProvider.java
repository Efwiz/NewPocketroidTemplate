package org.jetbrains.kotlin.fir.session;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.deserialization.SingleModuleDataProvider;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.library.metadata.KlibDeserializedContainerSource;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: KlibIcCacheBasedSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B/\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u0017H\u0014J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0002H\u0014R-\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00100\u000e8TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\t\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00168TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u0014\u001a\u0004\b\u0018\u0010\u0019¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/session/KlibIcCacheBasedSymbolProvider;", "Lorg/jetbrains/kotlin/fir/session/MetadataLibraryBasedSymbolProvider;", "Lorg/jetbrains/kotlin/fir/session/KlibIcData;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "moduleDataProvider", "Lorg/jetbrains/kotlin/fir/deserialization/SingleModuleDataProvider;", "kotlinScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "icData", "defaultDeserializationOrigin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/deserialization/SingleModuleDataProvider;Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;Lorg/jetbrains/kotlin/fir/session/KlibIcData;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "fragmentNamesInLibraries", "", "", "", "getFragmentNamesInLibraries", "()Ljava/util/Map;", "fragmentNamesInLibraries$delegate", "Lkotlin/Lazy;", "knownPackagesInLibraries", "", "Lorg/jetbrains/kotlin/name/FqName;", "getKnownPackagesInLibraries", "()Ljava/util/Set;", "knownPackagesInLibraries$delegate", "createDeserializedContainerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "resolvedLibrary", "packageFqName", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "library", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KlibIcCacheBasedSymbolProvider extends MetadataLibraryBasedSymbolProvider<KlibIcData> {

    /* JADX INFO: renamed from: fragmentNamesInLibraries$delegate, reason: from kotlin metadata */
    private final Lazy fragmentNamesInLibraries;
    private final KlibIcData icData;

    /* JADX INFO: renamed from: knownPackagesInLibraries$delegate, reason: from kotlin metadata */
    private final Lazy knownPackagesInLibraries;

    public /* synthetic */ KlibIcCacheBasedSymbolProvider(FirSession firSession, SingleModuleDataProvider singleModuleDataProvider, FirKotlinScopeProvider firKotlinScopeProvider, KlibIcData klibIcData, FirDeclarationOrigin.Precompiled precompiled, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, singleModuleDataProvider, firKotlinScopeProvider, klibIcData, (i & 16) != 0 ? FirDeclarationOrigin.Precompiled.INSTANCE : precompiled);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KlibIcCacheBasedSymbolProvider(FirSession session, SingleModuleDataProvider moduleDataProvider, FirKotlinScopeProvider kotlinScopeProvider, KlibIcData icData, FirDeclarationOrigin defaultDeserializationOrigin) {
        super(session, moduleDataProvider, kotlinScopeProvider, defaultDeserializationOrigin);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(moduleDataProvider, "moduleDataProvider");
        Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
        Intrinsics.checkNotNullParameter(icData, "icData");
        Intrinsics.checkNotNullParameter(defaultDeserializationOrigin, "defaultDeserializationOrigin");
        this.icData = icData;
        this.fragmentNamesInLibraries = LazyKt.lazy(new Function0<Map<String, ? extends SmartList<KlibIcData>>>() { // from class: org.jetbrains.kotlin.fir.session.KlibIcCacheBasedSymbolProvider$fragmentNamesInLibraries$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<String, ? extends SmartList<KlibIcData>> invoke() {
                KlibIcCacheBasedSymbolProvider klibIcCacheBasedSymbolProvider = this.this$0;
                Map mapCreateMapBuilder = MapsKt.createMapBuilder();
                for (String str : klibIcCacheBasedSymbolProvider.icData.getPackageFragmentNameList()) {
                    Object smartList = mapCreateMapBuilder.get(str);
                    if (smartList == null) {
                        smartList = new SmartList();
                        mapCreateMapBuilder.put(str, smartList);
                    }
                    ((SmartList) smartList).add(klibIcCacheBasedSymbolProvider.icData);
                }
                return MapsKt.build(mapCreateMapBuilder);
            }
        });
        this.knownPackagesInLibraries = LazyKt.lazy(new Function0<Set<? extends FqName>>() { // from class: org.jetbrains.kotlin.fir.session.KlibIcCacheBasedSymbolProvider$knownPackagesInLibraries$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends FqName> invoke() {
                KlibIcCacheBasedSymbolProvider klibIcCacheBasedSymbolProvider = this.this$0;
                Set setCreateSetBuilder = SetsKt.createSetBuilder();
                Iterator<String> it = klibIcCacheBasedSymbolProvider.icData.getPackageFragmentNameList().iterator();
                while (it.hasNext()) {
                    FqName fqName = new FqName(it.next());
                    while (!fqName.isRoot()) {
                        setCreateSetBuilder.add(fqName);
                        fqName = fqName.parent();
                        Intrinsics.checkNotNullExpressionValue(fqName, "parent(...)");
                    }
                }
                return SetsKt.build(setCreateSetBuilder);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.fir.session.MetadataLibraryBasedSymbolProvider
    public FirModuleData moduleData(KlibIcData library) {
        Intrinsics.checkNotNullParameter(library, "library");
        return (FirModuleData) CollectionsKt.single(getModuleDataProvider().getAllModuleData());
    }

    @Override // org.jetbrains.kotlin.fir.session.MetadataLibraryBasedSymbolProvider
    protected Map<String, List<KlibIcData>> getFragmentNamesInLibraries() {
        return (Map) this.fragmentNamesInLibraries.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.session.MetadataLibraryBasedSymbolProvider
    protected Set<FqName> getKnownPackagesInLibraries() {
        return (Set) this.knownPackagesInLibraries.getValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.fir.session.MetadataLibraryBasedSymbolProvider
    public DeserializedContainerSource createDeserializedContainerSource(KlibIcData resolvedLibrary, FqName packageFqName) {
        Intrinsics.checkNotNullParameter(resolvedLibrary, "resolvedLibrary");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return new KlibDeserializedContainerSource(false, "Package '" + packageFqName + '\'', false);
    }
}
