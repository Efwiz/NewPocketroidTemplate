package org.jetbrains.kotlin.fir.session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider;
import org.jetbrains.kotlin.fir.deserialization.ClassDeserializationKt;
import org.jetbrains.kotlin.fir.deserialization.FirConstDeserializer;
import org.jetbrains.kotlin.fir.deserialization.FirDeserializationContext;
import org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider;
import org.jetbrains.kotlin.fir.deserialization.PackagePartsCacheData;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.library.MetadataLibrary;
import org.jetbrains.kotlin.library.metadata.KlibMetadataClassDataFinder;
import org.jetbrains.kotlin.library.metadata.KlibMetadataDeserializationUtilsKt;
import org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf;
import org.jetbrains.kotlin.library.metadata.KlibMetadataSerializerProtocol;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;
import org.jetbrains.kotlin.resolve.CompilerDeserializationConfiguration;
import org.jetbrains.kotlin.serialization.deserialization.ClassData;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: MetadataLibraryBasedSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000²\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B'\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0016\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u001c2\u0006\u0010&\u001a\u00020!H\u0014J\u000e\u0010'\u001a\b\u0012\u0004\u0012\u00020\u00120 H\u0014J\u001f\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010*\u001a\u00028\u00002\u0006\u0010&\u001a\u00020!H$¢\u0006\u0002\u0010+J\u001c\u0010,\u001a\u0004\u0018\u00010-2\u0006\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u000101H\u0014J1\u00102\u001a\u0002032\u0006\u0010&\u001a\u00020!2\u001e\u00104\u001a\u001a\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020305H\u0082\bJ\u0012\u00107\u001a\u0004\u0018\u00010!2\u0006\u00108\u001a\u00020!H\u0016J%\u00109\u001a\u00020\u00132\u0006\u0010*\u001a\u00028\u00002\u0006\u0010:\u001a\u00020\u00122\u0006\u0010;\u001a\u00020\u0012H\u0002¢\u0006\u0002\u0010<J\u0010\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020@H\u0014J\u0016\u0010A\u001a\b\u0012\u0004\u0012\u00020\u00120 2\u0006\u0010&\u001a\u00020!H\u0014J\u0017\u0010B\u001a\u0004\u0018\u00010C2\u0006\u0010D\u001a\u00028\u0000H$¢\u0006\u0002\u0010ER\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R2\u0010\u000f\u001a&\u0012\u0004\u0012\u00028\u0000\u0012\u001c\u0012\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u0011\u0012\u0004\u0012\u00020\u00130\u00100\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u0017X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R$\u0010\u001a\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001c0\u001bX¤\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0018\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 X¤\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/fir/session/MetadataLibraryBasedSymbolProvider;", "L", "Lorg/jetbrains/kotlin/library/MetadataLibrary;", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "moduleDataProvider", "Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "kotlinScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "defaultDeserializationOrigin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "annotationDeserializer", "Lorg/jetbrains/kotlin/fir/session/KlibBasedAnnotationDeserializer;", "cachedFragments", "", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "constDeserializer", "Lorg/jetbrains/kotlin/fir/deserialization/FirConstDeserializer;", "deserializationConfiguration", "Lorg/jetbrains/kotlin/resolve/CompilerDeserializationConfiguration;", "getDeserializationConfiguration", "()Lorg/jetbrains/kotlin/resolve/CompilerDeserializationConfiguration;", "fragmentNamesInLibraries", "", "", "getFragmentNamesInLibraries", "()Ljava/util/Map;", "knownPackagesInLibraries", "", "Lorg/jetbrains/kotlin/name/FqName;", "getKnownPackagesInLibraries", "()Ljava/util/Set;", "computePackagePartsInfos", "Lorg/jetbrains/kotlin/fir/deserialization/PackagePartsCacheData;", "packageFqName", "computePackageSetWithNonClassDeclarations", "createDeserializedContainerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "resolvedLibrary", "(Lorg/jetbrains/kotlin/library/MetadataLibrary;Lorg/jetbrains/kotlin/name/FqName;)Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "extractClassMetadata", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider$ClassMetadataFindResult;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "parentContext", "Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;", "forEachFragmentInPackage", "", "f", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "getPackage", "fqName", "getPackageFragment", "packageStringName", "packageMetadataPart", "(Lorg/jetbrains/kotlin/library/MetadataLibrary;Ljava/lang/String;Ljava/lang/String;)Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "isNewPlaceForBodyGeneration", "", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "knownTopLevelClassesInPackage", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "library", "(Lorg/jetbrains/kotlin/library/MetadataLibrary;)Lorg/jetbrains/kotlin/fir/FirModuleData;", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class MetadataLibraryBasedSymbolProvider<L extends MetadataLibrary> extends AbstractFirDeserializedSymbolProvider {
    private final KlibBasedAnnotationDeserializer annotationDeserializer;
    private final Map<L, Map<Pair<String, String>, ProtoBuf.PackageFragment>> cachedFragments;
    private final FirConstDeserializer constDeserializer;
    private final CompilerDeserializationConfiguration deserializationConfiguration;

    protected abstract DeserializedContainerSource createDeserializedContainerSource(L resolvedLibrary, FqName packageFqName);

    protected abstract Map<String, List<L>> getFragmentNamesInLibraries();

    protected abstract Set<FqName> getKnownPackagesInLibraries();

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected boolean isNewPlaceForBodyGeneration(ProtoBuf.Class classProto) {
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        return false;
    }

    protected abstract FirModuleData moduleData(L library);

    public /* synthetic */ MetadataLibraryBasedSymbolProvider(FirSession firSession, ModuleDataProvider moduleDataProvider, FirKotlinScopeProvider firKotlinScopeProvider, FirDeclarationOrigin.Library library, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, moduleDataProvider, firKotlinScopeProvider, (i & 8) != 0 ? FirDeclarationOrigin.Library.INSTANCE : library);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MetadataLibraryBasedSymbolProvider(FirSession session, ModuleDataProvider moduleDataProvider, FirKotlinScopeProvider kotlinScopeProvider, FirDeclarationOrigin defaultDeserializationOrigin) {
        super(session, moduleDataProvider, kotlinScopeProvider, defaultDeserializationOrigin, KlibMetadataSerializerProtocol.INSTANCE);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(moduleDataProvider, "moduleDataProvider");
        Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
        Intrinsics.checkNotNullParameter(defaultDeserializationOrigin, "defaultDeserializationOrigin");
        this.annotationDeserializer = new KlibBasedAnnotationDeserializer(session);
        this.constDeserializer = new FirConstDeserializer(session, KlibMetadataSerializerProtocol.INSTANCE);
        this.deserializationConfiguration = new CompilerDeserializationConfiguration(FirLanguageSettingsComponentKt.getLanguageVersionSettings(session));
        this.cachedFragments = new LinkedHashMap();
    }

    protected final CompilerDeserializationConfiguration getDeserializationConfiguration() {
        return this.deserializationConfiguration;
    }

    private final ProtoBuf.PackageFragment getPackageFragment(L resolvedLibrary, String packageStringName, String packageMetadataPart) throws InvalidProtocolBufferException {
        Map<L, Map<Pair<String, String>, ProtoBuf.PackageFragment>> map = this.cachedFragments;
        LinkedHashMap linkedHashMap = map.get(resolvedLibrary);
        if (linkedHashMap == null) {
            linkedHashMap = new LinkedHashMap();
            map.put(resolvedLibrary, linkedHashMap);
        }
        Map<Pair<String, String>, ProtoBuf.PackageFragment> map2 = linkedHashMap;
        Pair<String, String> pair = TuplesKt.to(packageStringName, packageMetadataPart);
        ProtoBuf.PackageFragment packageFragment = map2.get(pair);
        if (packageFragment == null) {
            packageFragment = KlibMetadataDeserializationUtilsKt.parsePackageFragment(resolvedLibrary.packageMetadata(packageStringName, packageMetadataPart));
            map2.put(pair, packageFragment);
        }
        return packageFragment;
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected List<PackagePartsCacheData> computePackagePartsInfos(FqName packageFqName) throws InvalidProtocolBufferException {
        String strAsString;
        PackagePartsCacheData packagePartsCacheData;
        FqName packageFqName2 = packageFqName;
        Intrinsics.checkNotNullParameter(packageFqName2, "packageFqName");
        if (packageFqName.isRoot()) {
            strAsString = "";
        } else {
            strAsString = packageFqName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        }
        String str = strAsString;
        List<L> list = getFragmentNamesInLibraries().get(str);
        if (list == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (L l : list) {
            Set<String> setPackageMetadataParts = l.packageMetadataParts(str);
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it = setPackageMetadataParts.iterator();
            while (it.hasNext()) {
                ProtoBuf.PackageFragment packageFragment = this.getPackageFragment(l, str, (String) it.next());
                FirModuleData firModuleDataModuleData = this.moduleData(l);
                if (firModuleDataModuleData == null) {
                    packagePartsCacheData = null;
                } else {
                    ProtoBuf.Package r8 = packageFragment.getPackage();
                    ProtoBuf.StringTable strings = packageFragment.getStrings();
                    Intrinsics.checkNotNullExpressionValue(strings, "getStrings(...)");
                    ProtoBuf.QualifiedNameTable qualifiedNames = packageFragment.getQualifiedNames();
                    Intrinsics.checkNotNullExpressionValue(qualifiedNames, "getQualifiedNames(...)");
                    NameResolverImpl nameResolverImpl = new NameResolverImpl(strings, qualifiedNames);
                    Intrinsics.checkNotNull(r8);
                    packagePartsCacheData = new PackagePartsCacheData(r8, FirDeserializationContext.INSTANCE.createForPackage(packageFqName, r8, nameResolverImpl, firModuleDataModuleData, this.annotationDeserializer, this.constDeserializer, this.createDeserializedContainerSource(l, packageFqName2)));
                }
                if (packagePartsCacheData != null) {
                    arrayList2.add(packagePartsCacheData);
                }
                this = this;
                packageFqName2 = packageFqName;
            }
            CollectionsKt.addAll(arrayList, arrayList2);
            packageFqName2 = packageFqName;
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected Set<String> computePackageSetWithNonClassDeclarations() {
        return getFragmentNamesInLibraries().keySet();
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected Set<String> knownTopLevelClassesInPackage(FqName packageFqName) throws InvalidProtocolBufferException {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        String strAsString = packageFqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        List<L> list = getFragmentNamesInLibraries().get(strAsString);
        if (list != null) {
            for (L l : list) {
                Iterator<String> it = l.packageMetadataParts(strAsString).iterator();
                while (it.hasNext()) {
                    ProtoBuf.PackageFragment packageFragment = getPackageFragment(l, strAsString, it.next());
                    ProtoBuf.StringTable strings = packageFragment.getStrings();
                    Intrinsics.checkNotNullExpressionValue(strings, "getStrings(...)");
                    ProtoBuf.QualifiedNameTable qualifiedNames = packageFragment.getQualifiedNames();
                    Intrinsics.checkNotNullExpressionValue(qualifiedNames, "getQualifiedNames(...)");
                    NameResolverImpl nameResolverImpl = new NameResolverImpl(strings, qualifiedNames);
                    List<Integer> listEmptyList = (List) packageFragment.getExtension(KlibMetadataProtoBuf.className);
                    if (listEmptyList == null) {
                        listEmptyList = CollectionsKt.emptyList();
                    }
                    for (Integer num : listEmptyList) {
                        Intrinsics.checkNotNull(num);
                        String strAsString2 = NameResolverUtilKt.getClassId(nameResolverImpl, num.intValue()).getShortClassName().asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                        setCreateSetBuilder.add(strAsString2);
                    }
                }
            }
        }
        return SetsKt.build(setCreateSetBuilder);
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected AbstractFirDeserializedSymbolProvider.ClassMetadataFindResult extractClassMetadata(final ClassId classId, final FirDeserializationContext parentContext) throws InvalidProtocolBufferException {
        final ProtoBuf.Class classProto;
        Intrinsics.checkNotNullParameter(classId, "classId");
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        String strAsString = packageFqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        List<L> list = getFragmentNamesInLibraries().get(strAsString);
        if (list != null) {
            for (final L l : list) {
                Iterator<String> it = l.packageMetadataParts(strAsString).iterator();
                while (it.hasNext()) {
                    ProtoBuf.PackageFragment packageFragment = getPackageFragment(l, strAsString, it.next());
                    ProtoBuf.StringTable strings = packageFragment.getStrings();
                    Intrinsics.checkNotNullExpressionValue(strings, "getStrings(...)");
                    ProtoBuf.QualifiedNameTable qualifiedNames = packageFragment.getQualifiedNames();
                    Intrinsics.checkNotNullExpressionValue(qualifiedNames, "getQualifiedNames(...)");
                    final NameResolverImpl nameResolverImpl = new NameResolverImpl(strings, qualifiedNames);
                    ClassData classDataFindClassData = new KlibMetadataClassDataFinder(packageFragment, nameResolverImpl, null, 4, null).findClassData(classId);
                    if (classDataFindClassData != null && (classProto = classDataFindClassData.getClassProto()) != null) {
                        final FirModuleData firModuleDataModuleData = moduleData(l);
                        if (firModuleDataModuleData == null) {
                            return null;
                        }
                        return new AbstractFirDeserializedSymbolProvider.ClassMetadataFindResult.NoMetadata(new Function1<FirRegularClassSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.session.MetadataLibraryBasedSymbolProvider$extractClassMetadata$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Incorrect types in method signature: (Lorg/jetbrains/kotlin/fir/session/MetadataLibraryBasedSymbolProvider<TL;>;TL;Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;)V */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(FirRegularClassSymbol firRegularClassSymbol) {
                                invoke2(firRegularClassSymbol);
                                return Unit.INSTANCE;
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(FirRegularClassSymbol symbol) {
                                Intrinsics.checkNotNullParameter(symbol, "symbol");
                                MetadataLibraryBasedSymbolProvider<L> metadataLibraryBasedSymbolProvider = this.this$0;
                                MetadataLibrary metadataLibrary = l;
                                FqName packageFqName2 = classId.getPackageFqName();
                                Intrinsics.checkNotNullExpressionValue(packageFqName2, "getPackageFqName(...)");
                                ClassDeserializationKt.deserializeClassToSymbol(classId, classProto, symbol, nameResolverImpl, this.this$0.getSession(), firModuleDataModuleData, ((MetadataLibraryBasedSymbolProvider) this.this$0).annotationDeserializer, this.this$0.getKotlinScopeProvider(), KlibMetadataSerializerProtocol.INSTANCE, parentContext, metadataLibraryBasedSymbolProvider.createDeserializedContainerSource(metadataLibrary, packageFqName2), this.this$0.getDefaultDeserializationOrigin(), new AnonymousClass1(this.this$0));
                                ClassMembersKt.setNewPlaceForBodyGeneration((FirRegularClass) symbol.getFir(), Boolean.valueOf(this.this$0.isNewPlaceForBodyGeneration(classProto)));
                            }

                            /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.session.MetadataLibraryBasedSymbolProvider$extractClassMetadata$1$1$1, reason: invalid class name */
                            /* JADX INFO: compiled from: MetadataLibraryBasedSymbolProvider.kt */
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
                                    return Reflection.getOrCreateKotlinClass(MetadataLibraryBasedSymbolProvider.class);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final FirRegularClassSymbol invoke(ClassId p0, FirDeserializationContext firDeserializationContext) {
                                    Intrinsics.checkNotNullParameter(p0, "p0");
                                    return ((MetadataLibraryBasedSymbolProvider) this.receiver).getClass(p0, firDeserializationContext);
                                }
                            }
                        });
                    }
                }
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FqName getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (getKnownPackagesInLibraries().contains(fqName)) {
            return fqName;
        }
        return null;
    }
}
