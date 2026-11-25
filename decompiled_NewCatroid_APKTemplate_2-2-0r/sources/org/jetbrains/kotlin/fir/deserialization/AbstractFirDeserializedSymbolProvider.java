package org.jetbrains.kotlin.fir.deserialization;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderInternals;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: AbstractFirDeserializedSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Î\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b&\u0018\u00002\u00020\u0001:\u0001XB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0016\u00101\u001a\b\u0012\u0004\u0012\u00020&0\u00162\u0006\u00102\u001a\u00020%H$J\u000e\u00103\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH$J\u001e\u00104\u001a\u0004\u0018\u0001052\u0006\u00106\u001a\u00020\u000f2\n\b\u0002\u00107\u001a\u0004\u0018\u00010\u0011H$J>\u00108\u001a$\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020;\u0018\u00010:j\u0004\u0018\u0001`<092\u0006\u00106\u001a\u00020\u000f2\n\b\u0002\u00107\u001a\u0004\u0018\u00010\u0011H\u0002J2\u0010=\u001a$\u0012\u0006\u0012\u0004\u0018\u00010.\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020;\u0018\u00010:j\u0004\u0018\u0001`>092\u0006\u00106\u001a\u00020\u000fH\u0002J\u001e\u0010?\u001a\u0004\u0018\u00010\u00102\u0006\u00106\u001a\u00020\u000f2\n\b\u0002\u00107\u001a\u0004\u0018\u00010\u0011H\u0004J\u0016\u0010@\u001a\b\u0012\u0002\b\u0003\u0018\u00010A2\u0006\u00106\u001a\u00020\u000fH\u0016J\u0016\u0010B\u001a\b\u0012\u0004\u0012\u00020&0C2\u0006\u00102\u001a\u00020%H\u0002J*\u0010D\u001a\u00020;2\u0010\u0010E\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030G0F2\u0006\u00102\u001a\u00020%2\u0006\u0010H\u001a\u000200H\u0017J&\u0010I\u001a\u00020;2\f\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00170F2\u0006\u00102\u001a\u00020%2\u0006\u0010H\u001a\u000200H\u0017J&\u0010J\u001a\u00020;2\f\u0010E\u001a\b\u0012\u0004\u0012\u00020(0F2\u0006\u00102\u001a\u00020%2\u0006\u0010H\u001a\u000200H\u0017J\u0012\u0010K\u001a\u0004\u0018\u00010.2\u0006\u00106\u001a\u00020\u000fH\u0002J\u0010\u0010L\u001a\u00020M2\u0006\u0010N\u001a\u00020OH$J\u0018\u0010P\u001a\n\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u001e2\u0006\u00102\u001a\u00020%H$J\u0016\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\u0006\u0010R\u001a\u00020\u0015H\u0002J\u0016\u0010S\u001a\b\u0012\u0004\u0012\u00020(0\u00162\u0006\u0010R\u001a\u00020\u0015H\u0002J\u0016\u0010T\u001a\b\u0012\u0004\u0012\u00020&0\u00162\u0006\u00102\u001a\u00020%H\u0002JB\u0010U\u001a\b\u0012\u0004\u0012\u0002HV0\u0016\"\f\b\u0000\u0010V*\u0006\u0012\u0002\b\u00030G*\u001c\u0012\u0004\u0012\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002HV0\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u000e2\u0006\u0010W\u001a\u00020\u0015H\u0002R$\u0010\r\u001a\u0018\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R(\u0010\u0014\u001a\u001c\u0012\u0004\u0012\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR!\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b \u0010!R(\u0010$\u001a\u001c\u0012\u0004\u0012\u00020%\u0012\n\u0012\b\u0012\u0004\u0012\u00020&0\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010'\u001a\u001c\u0012\u0004\u0012\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010)\u001a\u00020*X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R$\u0010-\u001a\u0018\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010.\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010/\u001a\u001c\u0012\u0004\u0012\u00020%\u0012\n\u0012\b\u0012\u0004\u0012\u0002000\u001e\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Y"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "moduleDataProvider", "Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "kotlinScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "defaultDeserializationOrigin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "serializerExtensionProtocol", "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;)V", "classCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;", "getDefaultDeserializationOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "functionCache", "Lorg/jetbrains/kotlin/name/CallableId;", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "", "getKotlinScopeProvider", "()Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "getModuleDataProvider", "()Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "packageNamesForNonClassDeclarations", "", "", "getPackageNamesForNonClassDeclarations", "()Ljava/util/Set;", "packageNamesForNonClassDeclarations$delegate", "Lkotlin/Lazy;", "packagePartsCache", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/deserialization/PackagePartsCacheData;", "propertyCache", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "symbolNamesProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getSymbolNamesProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "typeAliasCache", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;", "typeAliasesNamesByPackage", "Lorg/jetbrains/kotlin/name/Name;", "computePackagePartsInfos", "packageFqName", "computePackageSetWithNonClassDeclarations", "extractClassMetadata", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider$ClassMetadataFindResult;", "classId", "parentContext", "findAndDeserializeClass", "Lkotlin/Pair;", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/fir/deserialization/DeserializedClassPostProcessor;", "findAndDeserializeTypeAlias", "Lorg/jetbrains/kotlin/fir/deserialization/DeserializedTypeAliasPostProcessor;", "getClass", "getClassLikeSymbolByClassId", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getPackageParts", "", "getTopLevelCallableSymbolsTo", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "name", "getTopLevelFunctionSymbolsTo", "getTopLevelPropertySymbolsTo", "getTypeAlias", "isNewPlaceForBodyGeneration", "", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "knownTopLevelClassesInPackage", "loadFunctionsByCallableId", "callableId", "loadPropertiesByCallableId", "tryComputePackagePartInfos", "getCallables", "C", "id", "ClassMetadataFindResult", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractFirDeserializedSymbolProvider extends FirSymbolProvider {
    private final FirCache<ClassId, FirRegularClassSymbol, FirDeserializationContext> classCache;
    private final FirDeclarationOrigin defaultDeserializationOrigin;
    private final FirCache functionCache;
    private final FirKotlinScopeProvider kotlinScopeProvider;
    private final ModuleDataProvider moduleDataProvider;

    /* JADX INFO: renamed from: packageNamesForNonClassDeclarations$delegate, reason: from kotlin metadata */
    private final Lazy packageNamesForNonClassDeclarations;
    private final FirCache packagePartsCache;
    private final FirCache propertyCache;
    private final SerializerExtensionProtocol serializerExtensionProtocol;
    private final FirSymbolNamesProvider symbolNamesProvider;
    private final FirCache<ClassId, FirTypeAliasSymbol, FirDeserializationContext> typeAliasCache;
    private final FirCache typeAliasesNamesByPackage;

    protected abstract List<PackagePartsCacheData> computePackagePartsInfos(FqName packageFqName);

    protected abstract Set<String> computePackageSetWithNonClassDeclarations();

    protected abstract ClassMetadataFindResult extractClassMetadata(ClassId classId, FirDeserializationContext parentContext);

    protected abstract boolean isNewPlaceForBodyGeneration(ProtoBuf.Class classProto);

    protected abstract Set<String> knownTopLevelClassesInPackage(FqName packageFqName);

    public final ModuleDataProvider getModuleDataProvider() {
        return this.moduleDataProvider;
    }

    public final FirKotlinScopeProvider getKotlinScopeProvider() {
        return this.kotlinScopeProvider;
    }

    public final FirDeclarationOrigin getDefaultDeserializationOrigin() {
        return this.defaultDeserializationOrigin;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractFirDeserializedSymbolProvider(final FirSession session, ModuleDataProvider moduleDataProvider, FirKotlinScopeProvider kotlinScopeProvider, FirDeclarationOrigin defaultDeserializationOrigin, SerializerExtensionProtocol serializerExtensionProtocol) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(moduleDataProvider, "moduleDataProvider");
        Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
        Intrinsics.checkNotNullParameter(defaultDeserializationOrigin, "defaultDeserializationOrigin");
        Intrinsics.checkNotNullParameter(serializerExtensionProtocol, "serializerExtensionProtocol");
        this.moduleDataProvider = moduleDataProvider;
        this.kotlinScopeProvider = kotlinScopeProvider;
        this.defaultDeserializationOrigin = defaultDeserializationOrigin;
        this.serializerExtensionProtocol = serializerExtensionProtocol;
        this.packageNamesForNonClassDeclarations = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Set<? extends String>>() { // from class: org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider$packageNamesForNonClassDeclarations$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends String> invoke() {
                return this.this$0.computePackageSetWithNonClassDeclarations();
            }
        });
        this.symbolNamesProvider = new FirCachedSymbolNamesProvider(session) { // from class: org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider$symbolNamesProvider$1
            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider
            public Set<String> computeTopLevelClassifierNames(FqName packageFqName) {
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                Set<String> setKnownTopLevelClassesInPackage = this.knownTopLevelClassesInPackage(packageFqName);
                if (setKnownTopLevelClassesInPackage == null) {
                    return null;
                }
                if (!this.getPackageNamesForNonClassDeclarations().contains(packageFqName.asString())) {
                    return setKnownTopLevelClassesInPackage;
                }
                Set set = (Set) this.typeAliasesNamesByPackage.getValue(packageFqName, null);
                if (set.isEmpty()) {
                    return setKnownTopLevelClassesInPackage;
                }
                Set setCreateSetBuilder = SetsKt.createSetBuilder();
                setCreateSetBuilder.addAll(setKnownTopLevelClassesInPackage);
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    setCreateSetBuilder.add(((Name) it.next()).asString());
                }
                return SetsKt.build(setCreateSetBuilder);
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider, org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<String> getPackageNamesWithTopLevelCallables() {
                return this.getPackageNamesForNonClassDeclarations();
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider
            public Set<String> computePackageNamesWithTopLevelCallables() {
                return this.getPackageNamesForNonClassDeclarations();
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirCachedSymbolNamesProvider
            public Set<Name> computeTopLevelCallableNames(FqName packageFqName) {
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                Collection<PackagePartsCacheData> packageParts = this.getPackageParts(packageFqName);
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                for (PackagePartsCacheData packagePartsCacheData : packageParts) {
                    CollectionsKt.addAll(linkedHashSet, SetsKt.plus((Set) packagePartsCacheData.getTopLevelFunctionNameIndex().keySet(), (Iterable) packagePartsCacheData.getTopLevelPropertyNameIndex().keySet()));
                }
                return linkedHashSet;
            }
        };
        this.typeAliasesNamesByPackage = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider$special$$inlined$createCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((FqName) obj, (Void) obj2);
            }

            public final Set<Name> invoke(FqName key, Void r3) {
                Intrinsics.checkNotNullParameter(key, "key");
                Collection packageParts = this.this$0.getPackageParts(key);
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                Iterator it = packageParts.iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(linkedHashSet, ((PackagePartsCacheData) it.next()).getTypeAliasNameIndex().keySet());
                }
                return linkedHashSet;
            }
        });
        this.packagePartsCache = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider$special$$inlined$createCache$2
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((FqName) obj, (Void) obj2);
            }

            public final List<? extends PackagePartsCacheData> invoke(FqName key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.$receiver$inlined.tryComputePackagePartInfos(key);
            }
        });
        this.typeAliasCache = FirCachesFactoryKt.getFirCachesFactory(session).createCacheWithPostCompute(new Function2<ClassId, FirDeserializationContext, Pair<? extends FirTypeAliasSymbol, ? extends Function1<? super FirTypeAliasSymbol, ? extends Unit>>>() { // from class: org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider$typeAliasCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Pair<FirTypeAliasSymbol, Function1<FirTypeAliasSymbol, Unit>> invoke(ClassId classId, FirDeserializationContext firDeserializationContext) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                return this.this$0.findAndDeserializeTypeAlias(classId);
            }
        }, new Function3<ClassId, FirTypeAliasSymbol, Function1<? super FirTypeAliasSymbol, ? extends Unit>, Unit>() { // from class: org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider$typeAliasCache$2
            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(ClassId classId, FirTypeAliasSymbol firTypeAliasSymbol, Function1<? super FirTypeAliasSymbol, ? extends Unit> function1) {
                invoke2(classId, firTypeAliasSymbol, (Function1<? super FirTypeAliasSymbol, Unit>) function1);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ClassId classId, FirTypeAliasSymbol firTypeAliasSymbol, Function1<? super FirTypeAliasSymbol, Unit> function1) {
                Intrinsics.checkNotNullParameter(classId, "<anonymous parameter 0>");
                if (function1 == null || firTypeAliasSymbol == null) {
                    return;
                }
                function1.invoke(firTypeAliasSymbol);
            }
        });
        this.classCache = FirCachesFactoryKt.getFirCachesFactory(session).createCacheWithPostCompute(new Function2<ClassId, FirDeserializationContext, Pair<? extends FirRegularClassSymbol, ? extends Function1<? super FirRegularClassSymbol, ? extends Unit>>>() { // from class: org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider$classCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Pair<FirRegularClassSymbol, Function1<FirRegularClassSymbol, Unit>> invoke(ClassId classId, FirDeserializationContext firDeserializationContext) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                return this.this$0.findAndDeserializeClass(classId, firDeserializationContext);
            }
        }, new Function3<ClassId, FirRegularClassSymbol, Function1<? super FirRegularClassSymbol, ? extends Unit>, Unit>() { // from class: org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider$classCache$2
            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(ClassId classId, FirRegularClassSymbol firRegularClassSymbol, Function1<? super FirRegularClassSymbol, ? extends Unit> function1) {
                invoke2(classId, firRegularClassSymbol, (Function1<? super FirRegularClassSymbol, Unit>) function1);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ClassId classId, FirRegularClassSymbol firRegularClassSymbol, Function1<? super FirRegularClassSymbol, Unit> function1) {
                Intrinsics.checkNotNullParameter(classId, "<anonymous parameter 0>");
                if (function1 == null || firRegularClassSymbol == null) {
                    return;
                }
                function1.invoke(firRegularClassSymbol);
            }
        });
        this.functionCache = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider$special$$inlined$createCache$3
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((CallableId) obj, (Void) obj2);
            }

            public final List<? extends FirNamedFunctionSymbol> invoke(CallableId key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.$receiver$inlined.loadFunctionsByCallableId(key);
            }
        });
        this.propertyCache = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider$special$$inlined$createCache$4
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((CallableId) obj, (Void) obj2);
            }

            public final List<? extends FirPropertySymbol> invoke(CallableId key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.$receiver$inlined.loadPropertiesByCallableId(key);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Set<String> getPackageNamesForNonClassDeclarations() {
        return (Set) this.packageNamesForNonClassDeclarations.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirSymbolNamesProvider getSymbolNamesProvider() {
        return this.symbolNamesProvider;
    }

    public static /* synthetic */ ClassMetadataFindResult extractClassMetadata$default(AbstractFirDeserializedSymbolProvider abstractFirDeserializedSymbolProvider, ClassId classId, FirDeserializationContext firDeserializationContext, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: extractClassMetadata");
        }
        if ((i & 2) != 0) {
            firDeserializationContext = null;
        }
        return abstractFirDeserializedSymbolProvider.extractClassMetadata(classId, firDeserializationContext);
    }

    /* JADX INFO: compiled from: AbstractFirDeserializedSymbolProvider.kt */
    @kotlin.Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider$ClassMetadataFindResult;", "", "()V", "Metadata", "NoMetadata", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider$ClassMetadataFindResult$Metadata;", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider$ClassMetadataFindResult$NoMetadata;", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class ClassMetadataFindResult {
        public /* synthetic */ ClassMetadataFindResult(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private ClassMetadataFindResult() {
        }

        /* JADX INFO: compiled from: AbstractFirDeserializedSymbolProvider.kt */
        @kotlin.Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006¢\u0006\u0002\u0010\u0007J\u0019\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006HÆ\u0003J#\u0010\u000b\u001a\u00020\u00002\u0018\b\u0002\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R!\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider$ClassMetadataFindResult$NoMetadata;", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider$ClassMetadataFindResult;", "classPostProcessor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "", "Lorg/jetbrains/kotlin/fir/deserialization/DeserializedClassPostProcessor;", "(Lkotlin/jvm/functions/Function1;)V", "getClassPostProcessor", "()Lkotlin/jvm/functions/Function1;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class NoMetadata extends ClassMetadataFindResult {
            private final Function1<FirRegularClassSymbol, Unit> classPostProcessor;

            /* JADX WARN: Multi-variable type inference failed */
            public static /* synthetic */ NoMetadata copy$default(NoMetadata noMetadata, Function1 function1, int i, Object obj) {
                if ((i & 1) != 0) {
                    function1 = noMetadata.classPostProcessor;
                }
                return noMetadata.copy(function1);
            }

            public final Function1<FirRegularClassSymbol, Unit> component1() {
                return this.classPostProcessor;
            }

            public final NoMetadata copy(Function1<? super FirRegularClassSymbol, Unit> classPostProcessor) {
                Intrinsics.checkNotNullParameter(classPostProcessor, "classPostProcessor");
                return new NoMetadata(classPostProcessor);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                return (other instanceof NoMetadata) && Intrinsics.areEqual(this.classPostProcessor, ((NoMetadata) other).classPostProcessor);
            }

            public int hashCode() {
                return this.classPostProcessor.hashCode();
            }

            public String toString() {
                return "NoMetadata(classPostProcessor=" + this.classPostProcessor + ')';
            }

            public final Function1<FirRegularClassSymbol, Unit> getClassPostProcessor() {
                return this.classPostProcessor;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public NoMetadata(Function1<? super FirRegularClassSymbol, Unit> classPostProcessor) {
                super(null);
                Intrinsics.checkNotNullParameter(classPostProcessor, "classPostProcessor");
                this.classPostProcessor = classPostProcessor;
            }
        }

        /* JADX INFO: compiled from: AbstractFirDeserializedSymbolProvider.kt */
        @kotlin.Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u001a\u0010\f\u001a\u0016\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\rj\u0004\u0018\u0001`\u0010¢\u0006\u0002\u0010\u0011J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0005HÆ\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\tHÆ\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u000bHÆ\u0003J\u001d\u0010#\u001a\u0016\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\rj\u0004\u0018\u0001`\u0010HÆ\u0003J_\u0010$\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\u001c\b\u0002\u0010\f\u001a\u0016\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\rj\u0004\u0018\u0001`\u0010HÆ\u0001J\u0013\u0010%\u001a\u00020&2\b\u0010'\u001a\u0004\u0018\u00010(HÖ\u0003J\t\u0010)\u001a\u00020*HÖ\u0001J\t\u0010+\u001a\u00020,HÖ\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R%\u0010\f\u001a\u0016\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\rj\u0004\u0018\u0001`\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider$ClassMetadataFindResult$Metadata;", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider$ClassMetadataFindResult;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "annotationDeserializer", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "sourceElement", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "classPostProcessor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "", "Lorg/jetbrains/kotlin/fir/deserialization/DeserializedClassPostProcessor;", "(Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;Lkotlin/jvm/functions/Function1;)V", "getAnnotationDeserializer", "()Lorg/jetbrains/kotlin/fir/deserialization/AbstractAnnotationDeserializer;", "getClassPostProcessor", "()Lkotlin/jvm/functions/Function1;", "getClassProto", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "getSourceElement", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final /* data */ class Metadata extends ClassMetadataFindResult {
            private final AbstractAnnotationDeserializer annotationDeserializer;
            private final Function1<FirRegularClassSymbol, Unit> classPostProcessor;
            private final ProtoBuf.Class classProto;
            private final FirModuleData moduleData;
            private final NameResolver nameResolver;
            private final DeserializedContainerSource sourceElement;

            /* JADX WARN: Multi-variable type inference failed */
            public static /* synthetic */ Metadata copy$default(Metadata metadata, NameResolver nameResolver, ProtoBuf.Class r6, AbstractAnnotationDeserializer abstractAnnotationDeserializer, FirModuleData firModuleData, DeserializedContainerSource deserializedContainerSource, Function1 function1, int i, Object obj) {
                if ((i & 1) != 0) {
                    nameResolver = metadata.nameResolver;
                }
                if ((i & 2) != 0) {
                    r6 = metadata.classProto;
                }
                ProtoBuf.Class r12 = r6;
                if ((i & 4) != 0) {
                    abstractAnnotationDeserializer = metadata.annotationDeserializer;
                }
                AbstractAnnotationDeserializer abstractAnnotationDeserializer2 = abstractAnnotationDeserializer;
                if ((i & 8) != 0) {
                    firModuleData = metadata.moduleData;
                }
                FirModuleData firModuleData2 = firModuleData;
                if ((i & 16) != 0) {
                    deserializedContainerSource = metadata.sourceElement;
                }
                DeserializedContainerSource deserializedContainerSource2 = deserializedContainerSource;
                if ((i & 32) != 0) {
                    function1 = metadata.classPostProcessor;
                }
                return metadata.copy(nameResolver, r12, abstractAnnotationDeserializer2, firModuleData2, deserializedContainerSource2, function1);
            }

            /* JADX INFO: renamed from: component1, reason: from getter */
            public final NameResolver getNameResolver() {
                return this.nameResolver;
            }

            /* JADX INFO: renamed from: component2, reason: from getter */
            public final ProtoBuf.Class getClassProto() {
                return this.classProto;
            }

            /* JADX INFO: renamed from: component3, reason: from getter */
            public final AbstractAnnotationDeserializer getAnnotationDeserializer() {
                return this.annotationDeserializer;
            }

            /* JADX INFO: renamed from: component4, reason: from getter */
            public final FirModuleData getModuleData() {
                return this.moduleData;
            }

            /* JADX INFO: renamed from: component5, reason: from getter */
            public final DeserializedContainerSource getSourceElement() {
                return this.sourceElement;
            }

            public final Function1<FirRegularClassSymbol, Unit> component6() {
                return this.classPostProcessor;
            }

            public final Metadata copy(NameResolver nameResolver, ProtoBuf.Class classProto, AbstractAnnotationDeserializer annotationDeserializer, FirModuleData moduleData, DeserializedContainerSource sourceElement, Function1<? super FirRegularClassSymbol, Unit> classPostProcessor) {
                Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
                Intrinsics.checkNotNullParameter(classProto, "classProto");
                return new Metadata(nameResolver, classProto, annotationDeserializer, moduleData, sourceElement, classPostProcessor);
            }

            public boolean equals(Object other) {
                if (this == other) {
                    return true;
                }
                if (!(other instanceof Metadata)) {
                    return false;
                }
                Metadata metadata = (Metadata) other;
                return Intrinsics.areEqual(this.nameResolver, metadata.nameResolver) && Intrinsics.areEqual(this.classProto, metadata.classProto) && Intrinsics.areEqual(this.annotationDeserializer, metadata.annotationDeserializer) && Intrinsics.areEqual(this.moduleData, metadata.moduleData) && Intrinsics.areEqual(this.sourceElement, metadata.sourceElement) && Intrinsics.areEqual(this.classPostProcessor, metadata.classPostProcessor);
            }

            public int hashCode() {
                int iHashCode = ((this.nameResolver.hashCode() * 31) + this.classProto.hashCode()) * 31;
                AbstractAnnotationDeserializer abstractAnnotationDeserializer = this.annotationDeserializer;
                int iHashCode2 = (iHashCode + (abstractAnnotationDeserializer == null ? 0 : abstractAnnotationDeserializer.hashCode())) * 31;
                FirModuleData firModuleData = this.moduleData;
                int iHashCode3 = (iHashCode2 + (firModuleData == null ? 0 : firModuleData.hashCode())) * 31;
                DeserializedContainerSource deserializedContainerSource = this.sourceElement;
                int iHashCode4 = (iHashCode3 + (deserializedContainerSource == null ? 0 : deserializedContainerSource.hashCode())) * 31;
                Function1<FirRegularClassSymbol, Unit> function1 = this.classPostProcessor;
                return iHashCode4 + (function1 != null ? function1.hashCode() : 0);
            }

            public String toString() {
                return "Metadata(nameResolver=" + this.nameResolver + ", classProto=" + this.classProto + ", annotationDeserializer=" + this.annotationDeserializer + ", moduleData=" + this.moduleData + ", sourceElement=" + this.sourceElement + ", classPostProcessor=" + this.classPostProcessor + ')';
            }

            public final NameResolver getNameResolver() {
                return this.nameResolver;
            }

            public final ProtoBuf.Class getClassProto() {
                return this.classProto;
            }

            public final AbstractAnnotationDeserializer getAnnotationDeserializer() {
                return this.annotationDeserializer;
            }

            public final FirModuleData getModuleData() {
                return this.moduleData;
            }

            public final DeserializedContainerSource getSourceElement() {
                return this.sourceElement;
            }

            public final Function1<FirRegularClassSymbol, Unit> getClassPostProcessor() {
                return this.classPostProcessor;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public Metadata(NameResolver nameResolver, ProtoBuf.Class classProto, AbstractAnnotationDeserializer abstractAnnotationDeserializer, FirModuleData firModuleData, DeserializedContainerSource deserializedContainerSource, Function1<? super FirRegularClassSymbol, Unit> function1) {
                super(null);
                Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
                Intrinsics.checkNotNullParameter(classProto, "classProto");
                this.nameResolver = nameResolver;
                this.classProto = classProto;
                this.annotationDeserializer = abstractAnnotationDeserializer;
                this.moduleData = firModuleData;
                this.sourceElement = deserializedContainerSource;
                this.classPostProcessor = function1;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<PackagePartsCacheData> tryComputePackagePartInfos(FqName packageFqName) {
        return computePackagePartsInfos(packageFqName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:16:0x0061  */
    /* JADX WARN: Code duplicated, block: B:23:? A[RETURN, SYNTHETIC] */
    public final Pair<FirTypeAliasSymbol, Function1<FirTypeAliasSymbol, Unit>> findAndDeserializeTypeAlias(ClassId classId) {
        Pair<FirTypeAliasSymbol, Function1<FirTypeAliasSymbol, Unit>> pair;
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        for (final PackagePartsCacheData packagePartsCacheData : getPackageParts(packageFqName)) {
            List<Integer> list = packagePartsCacheData.getTypeAliasNameIndex().get(classId.getShortClassName());
            if (list == null || list.isEmpty()) {
                pair = null;
            } else {
                final ProtoBuf.TypeAlias typeAlias = packagePartsCacheData.getProto().getTypeAlias(((Number) CollectionsKt.single((List) list)).intValue());
                pair = TuplesKt.to(new FirTypeAliasSymbol(classId), new Function1<FirTypeAliasSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider$findAndDeserializeTypeAlias$1$postProcessor$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(FirTypeAliasSymbol firTypeAliasSymbol) {
                        invoke2(firTypeAliasSymbol);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(FirTypeAliasSymbol it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        FirMemberDeserializer memberDeserializer = packagePartsCacheData.getContext().getMemberDeserializer();
                        ProtoBuf.TypeAlias aliasProto = typeAlias;
                        Intrinsics.checkNotNullExpressionValue(aliasProto, "$aliasProto");
                        memberDeserializer.loadTypeAlias(aliasProto, it);
                    }
                });
            }
            if (pair != null) {
                if (pair == null) {
                    return TuplesKt.to(null, null);
                }
                return pair;
            }
        }
        pair = null;
        if (pair == null) {
            return TuplesKt.to(null, null);
        }
        return pair;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final Pair<FirRegularClassSymbol, Function1<FirRegularClassSymbol, Unit>> findAndDeserializeClass(ClassId classId, FirDeserializationContext parentContext) {
        ClassMetadataFindResult classMetadataFindResultExtractClassMetadata = extractClassMetadata(classId, parentContext);
        if (classMetadataFindResultExtractClassMetadata instanceof ClassMetadataFindResult.NoMetadata) {
            return TuplesKt.to(new FirRegularClassSymbol(classId), ((ClassMetadataFindResult.NoMetadata) classMetadataFindResultExtractClassMetadata).getClassPostProcessor());
        }
        if (!(classMetadataFindResultExtractClassMetadata instanceof ClassMetadataFindResult.Metadata)) {
            if (classMetadataFindResultExtractClassMetadata == null) {
                return TuplesKt.to(null, null);
            }
            throw new NoWhenBranchMatchedException();
        }
        ClassMetadataFindResult.Metadata metadata = (ClassMetadataFindResult.Metadata) classMetadataFindResultExtractClassMetadata;
        NameResolver nameResolver = metadata.getNameResolver();
        ProtoBuf.Class classProto = metadata.getClassProto();
        AbstractAnnotationDeserializer annotationDeserializer = metadata.getAnnotationDeserializer();
        FirModuleData moduleData = metadata.getModuleData();
        DeserializedContainerSource sourceElement = metadata.getSourceElement();
        Function1<FirRegularClassSymbol, Unit> function1Component6 = metadata.component6();
        if (moduleData == null) {
            return TuplesKt.to(null, null);
        }
        FirRegularClassSymbol firRegularClassSymbol = new FirRegularClassSymbol(classId);
        ClassDeserializationKt.deserializeClassToSymbol(classId, classProto, firRegularClassSymbol, nameResolver, getSession(), moduleData, annotationDeserializer, this.kotlinScopeProvider, this.serializerExtensionProtocol, parentContext, sourceElement, this.defaultDeserializationOrigin, new AnonymousClass1(this));
        ClassMembersKt.setNewPlaceForBodyGeneration((FirRegularClass) firRegularClassSymbol.getFir(), Boolean.valueOf(isNewPlaceForBodyGeneration(classProto)));
        return TuplesKt.to(firRegularClassSymbol, function1Component6);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider$findAndDeserializeClass$1, reason: invalid class name */
    /* JADX INFO: compiled from: AbstractFirDeserializedSymbolProvider.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function2<ClassId, FirDeserializationContext, FirRegularClassSymbol> {
        AnonymousClass1(Object obj) {
            super(2, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "getClass(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "getClass";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(AbstractFirDeserializedSymbolProvider.class);
        }

        @Override // kotlin.jvm.functions.Function2
        public final FirRegularClassSymbol invoke(ClassId p0, FirDeserializationContext firDeserializationContext) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return ((AbstractFirDeserializedSymbolProvider) this.receiver).getClass(p0, firDeserializationContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<FirNamedFunctionSymbol> loadFunctionsByCallableId(CallableId callableId) {
        ArrayList arrayListEmptyList;
        Collection<PackagePartsCacheData> packageParts = getPackageParts(callableId.getPackageName());
        ArrayList arrayList = new ArrayList();
        for (PackagePartsCacheData packagePartsCacheData : packageParts) {
            List<Integer> list = packagePartsCacheData.getTopLevelFunctionNameIndex().get(callableId.getCallableName());
            if (list == null) {
                arrayListEmptyList = CollectionsKt.emptyList();
            } else {
                List<Integer> list2 = list;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    int iIntValue = ((Number) it.next()).intValue();
                    FirMemberDeserializer memberDeserializer = packagePartsCacheData.getContext().getMemberDeserializer();
                    ProtoBuf.Function function = packagePartsCacheData.getProto().getFunction(iIntValue);
                    Intrinsics.checkNotNullExpressionValue(function, "getFunction(...)");
                    arrayList2.add(FirMemberDeserializer.loadFunction$default(memberDeserializer, function, null, null, this.defaultDeserializationOrigin, 6, null).getSymbol());
                }
                arrayListEmptyList = arrayList2;
            }
            CollectionsKt.addAll(arrayList, arrayListEmptyList);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<FirPropertySymbol> loadPropertiesByCallableId(CallableId callableId) {
        ArrayList arrayListEmptyList;
        Collection<PackagePartsCacheData> packageParts = getPackageParts(callableId.getPackageName());
        ArrayList arrayList = new ArrayList();
        for (PackagePartsCacheData packagePartsCacheData : packageParts) {
            List<Integer> list = packagePartsCacheData.getTopLevelPropertyNameIndex().get(callableId.getCallableName());
            if (list == null) {
                arrayListEmptyList = CollectionsKt.emptyList();
            } else {
                List<Integer> list2 = list;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    int iIntValue = ((Number) it.next()).intValue();
                    FirMemberDeserializer memberDeserializer = packagePartsCacheData.getContext().getMemberDeserializer();
                    ProtoBuf.Property property = packagePartsCacheData.getProto().getProperty(iIntValue);
                    Intrinsics.checkNotNullExpressionValue(property, "getProperty(...)");
                    arrayList2.add(FirMemberDeserializer.loadProperty$default(memberDeserializer, property, null, null, 6, null).getSymbol());
                }
                arrayListEmptyList = arrayList2;
            }
            CollectionsKt.addAll(arrayList, arrayListEmptyList);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<PackagePartsCacheData> getPackageParts(FqName packageFqName) {
        return (Collection) this.packagePartsCache.getValue(packageFqName, null);
    }

    public static /* synthetic */ FirRegularClassSymbol getClass$default(AbstractFirDeserializedSymbolProvider abstractFirDeserializedSymbolProvider, ClassId classId, FirDeserializationContext firDeserializationContext, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getClass");
        }
        if ((i & 2) != 0) {
            firDeserializationContext = null;
        }
        return abstractFirDeserializedSymbolProvider.getClass(classId, firDeserializationContext);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final FirRegularClassSymbol getClass(ClassId classId, FirDeserializationContext parentContext) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        ClassId outerClassId = classId.getOuterClassId();
        if (outerClassId == null && !getSymbolNamesProvider().mayHaveTopLevelClassifier(classId)) {
            return null;
        }
        if (outerClassId != null) {
            FirSymbolNamesProvider symbolNamesProvider = getSymbolNamesProvider();
            ClassId outermostClassId = classId.getOutermostClassId();
            Intrinsics.checkNotNullExpressionValue(outermostClassId, "getOutermostClassId(...)");
            if (!symbolNamesProvider.mayHaveTopLevelClassifier(outermostClassId)) {
                return null;
            }
        }
        if (parentContext == null && outerClassId != null) {
            FirRegularClassSymbol valueIfComputed = this.classCache.getValueIfComputed(classId);
            if (valueIfComputed != null) {
                return valueIfComputed;
            }
            getClass(outerClassId, null);
        }
        return this.classCache.getValue(classId, parentContext);
    }

    private final FirTypeAliasSymbol getTypeAlias(ClassId classId) {
        FqName relativeClassName = classId.getRelativeClassName();
        Intrinsics.checkNotNullExpressionValue(relativeClassName, "getRelativeClassName(...)");
        if (!FqNamesUtilKt.isOneSegmentFQN(relativeClassName)) {
            return null;
        }
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        if (getPackageNamesForNonClassDeclarations().contains(packageFqName.asString()) && ((Set) this.typeAliasesNamesByPackage.getValue(packageFqName, null)).contains(classId.getShortClassName())) {
            return this.typeAliasCache.getValue(classId, null);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelCallableSymbolsTo(List<FirCallableSymbol<?>> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        CallableId callableId = new CallableId(packageFqName, name2);
        List<FirCallableSymbol<?>> list = destination;
        CollectionsKt.addAll(list, getCallables(this.functionCache, callableId));
        CollectionsKt.addAll(list, getCallables(this.propertyCache, callableId));
    }

    private final <C extends FirCallableSymbol<?>> List<C> getCallables(FirCache firCache, CallableId callableId) {
        return !getSymbolNamesProvider().mayHaveTopLevelCallable(callableId.getPackageName(), callableId.getCallableName()) ? CollectionsKt.emptyList() : (List) firCache.getValue(callableId, null);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelFunctionSymbolsTo(List<FirNamedFunctionSymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        CollectionsKt.addAll(destination, getCallables(this.functionCache, new CallableId(packageFqName, name2)));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelPropertySymbolsTo(List<FirPropertySymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        CollectionsKt.addAll(destination, getCallables(this.propertyCache, new CallableId(packageFqName, name2)));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirClassLikeSymbol<?> getClassLikeSymbolByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        FirRegularClassSymbol class$default = getClass$default(this, classId, null, 2, null);
        return class$default != null ? class$default : getTypeAlias(classId);
    }
}
