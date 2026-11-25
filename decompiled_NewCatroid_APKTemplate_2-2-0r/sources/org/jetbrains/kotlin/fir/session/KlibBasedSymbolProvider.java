package org.jetbrains.kotlin.fir.session;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.library.KotlinLibrary;
import org.jetbrains.kotlin.library.metadata.KlibDeserializedContainerSource;
import org.jetbrains.kotlin.library.metadata.KlibMetadataDeserializationUtilsKt;
import org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: KlibBasedSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B5\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0018\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00022\u0006\u0010#\u001a\u00020\u0018H\u0014J\u0012\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020\u0002H\u0014R-\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00110\u000f8TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R!\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u00178TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u0015\u001a\u0004\b\u0019\u0010\u001aR'\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u001d0\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u0015\u001a\u0004\b\u001e\u0010\u0013R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/KlibBasedSymbolProvider;", "Lorg/jetbrains/kotlin/fir/session/MetadataLibraryBasedSymbolProvider;", "Lorg/jetbrains/kotlin/library/KotlinLibrary;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "moduleDataProvider", "Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "kotlinScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "resolvedLibraries", "", "defaultDeserializationOrigin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;Ljava/util/Collection;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "fragmentNamesInLibraries", "", "", "", "getFragmentNamesInLibraries", "()Ljava/util/Map;", "fragmentNamesInLibraries$delegate", "Lkotlin/Lazy;", "knownPackagesInLibraries", "", "Lorg/jetbrains/kotlin/name/FqName;", "getKnownPackagesInLibraries", "()Ljava/util/Set;", "knownPackagesInLibraries$delegate", "moduleHeaders", "Lorg/jetbrains/kotlin/library/metadata/KlibMetadataProtoBuf$Header;", "getModuleHeaders", "moduleHeaders$delegate", "createDeserializedContainerSource", "Lorg/jetbrains/kotlin/library/metadata/KlibDeserializedContainerSource;", "resolvedLibrary", "packageFqName", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "library", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KlibBasedSymbolProvider extends MetadataLibraryBasedSymbolProvider<KotlinLibrary> {

    /* JADX INFO: renamed from: fragmentNamesInLibraries$delegate, reason: from kotlin metadata */
    private final Lazy fragmentNamesInLibraries;

    /* JADX INFO: renamed from: knownPackagesInLibraries$delegate, reason: from kotlin metadata */
    private final Lazy knownPackagesInLibraries;

    /* JADX INFO: renamed from: moduleHeaders$delegate, reason: from kotlin metadata */
    private final Lazy moduleHeaders;
    private final Collection<KotlinLibrary> resolvedLibraries;

    public /* synthetic */ KlibBasedSymbolProvider(FirSession firSession, ModuleDataProvider moduleDataProvider, FirKotlinScopeProvider firKotlinScopeProvider, Collection collection, FirDeclarationOrigin.Library library, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, moduleDataProvider, firKotlinScopeProvider, collection, (i & 16) != 0 ? FirDeclarationOrigin.Library.INSTANCE : library);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public KlibBasedSymbolProvider(FirSession session, ModuleDataProvider moduleDataProvider, FirKotlinScopeProvider kotlinScopeProvider, Collection<? extends KotlinLibrary> resolvedLibraries, FirDeclarationOrigin defaultDeserializationOrigin) {
        super(session, moduleDataProvider, kotlinScopeProvider, defaultDeserializationOrigin);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(moduleDataProvider, "moduleDataProvider");
        Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
        Intrinsics.checkNotNullParameter(resolvedLibraries, "resolvedLibraries");
        Intrinsics.checkNotNullParameter(defaultDeserializationOrigin, "defaultDeserializationOrigin");
        this.resolvedLibraries = resolvedLibraries;
        this.moduleHeaders = LazyKt.lazy(new Function0<Map<KotlinLibrary, ? extends KlibMetadataProtoBuf.Header>>() { // from class: org.jetbrains.kotlin.fir.session.KlibBasedSymbolProvider$moduleHeaders$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<KotlinLibrary, ? extends KlibMetadataProtoBuf.Header> invoke() {
                Collection<KotlinLibrary> collection = this.this$0.resolvedLibraries;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(collection, 10)), 16));
                for (KotlinLibrary kotlinLibrary : collection) {
                    Pair pair = TuplesKt.to(kotlinLibrary, KlibMetadataDeserializationUtilsKt.parseModuleHeader(kotlinLibrary.getModuleHeaderData()));
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                return linkedHashMap;
            }
        });
        this.fragmentNamesInLibraries = LazyKt.lazy(new Function0<Map<String, ? extends SmartList<KotlinLibrary>>>() { // from class: org.jetbrains.kotlin.fir.session.KlibBasedSymbolProvider$fragmentNamesInLibraries$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<String, ? extends SmartList<KotlinLibrary>> invoke() {
                KlibBasedSymbolProvider klibBasedSymbolProvider = this.this$0;
                Map mapCreateMapBuilder = MapsKt.createMapBuilder();
                for (Map.Entry entry : klibBasedSymbolProvider.getModuleHeaders().entrySet()) {
                    KotlinLibrary kotlinLibrary = (KotlinLibrary) entry.getKey();
                    for (String str : ((KlibMetadataProtoBuf.Header) entry.getValue()).getPackageFragmentNameList()) {
                        Intrinsics.checkNotNull(str);
                        Object smartList = mapCreateMapBuilder.get(str);
                        if (smartList == null) {
                            smartList = new SmartList();
                            mapCreateMapBuilder.put(str, smartList);
                        }
                        ((SmartList) smartList).add(kotlinLibrary);
                    }
                }
                return MapsKt.build(mapCreateMapBuilder);
            }
        });
        this.knownPackagesInLibraries = LazyKt.lazy(new Function0<Set<? extends FqName>>() { // from class: org.jetbrains.kotlin.fir.session.KlibBasedSymbolProvider$knownPackagesInLibraries$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends FqName> invoke() {
                KlibBasedSymbolProvider klibBasedSymbolProvider = this.this$0;
                Set setCreateSetBuilder = SetsKt.createSetBuilder();
                Iterator it = klibBasedSymbolProvider.getModuleHeaders().entrySet().iterator();
                while (it.hasNext()) {
                    Iterator<String> it2 = ((KlibMetadataProtoBuf.Header) ((Map.Entry) it.next()).getValue()).getPackageFragmentNameList().iterator();
                    while (it2.hasNext()) {
                        FqName fqName = new FqName(it2.next());
                        while (!fqName.isRoot()) {
                            setCreateSetBuilder.add(fqName);
                            fqName = fqName.parent();
                            Intrinsics.checkNotNullExpressionValue(fqName, "parent(...)");
                        }
                    }
                }
                return SetsKt.build(setCreateSetBuilder);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Map<KotlinLibrary, KlibMetadataProtoBuf.Header> getModuleHeaders() {
        return (Map) this.moduleHeaders.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.session.MetadataLibraryBasedSymbolProvider
    protected Map<String, List<KotlinLibrary>> getFragmentNamesInLibraries() {
        return (Map) this.fragmentNamesInLibraries.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.session.MetadataLibraryBasedSymbolProvider
    protected Set<FqName> getKnownPackagesInLibraries() {
        return (Set) this.knownPackagesInLibraries.getValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.fir.session.MetadataLibraryBasedSymbolProvider
    public FirModuleData moduleData(KotlinLibrary library) {
        Intrinsics.checkNotNullParameter(library, "library");
        return getModuleDataProvider().getModuleData(Paths.get(library.getLibraryFile().getPath(), new String[0]));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.fir.session.MetadataLibraryBasedSymbolProvider
    public KlibDeserializedContainerSource createDeserializedContainerSource(KotlinLibrary resolvedLibrary, FqName packageFqName) {
        Intrinsics.checkNotNullParameter(resolvedLibrary, "resolvedLibrary");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        KlibMetadataProtoBuf.Header header = getModuleHeaders().get(resolvedLibrary);
        Intrinsics.checkNotNull(header);
        return new KlibDeserializedContainerSource(resolvedLibrary, header, getDeserializationConfiguration(), packageFqName);
    }
}
