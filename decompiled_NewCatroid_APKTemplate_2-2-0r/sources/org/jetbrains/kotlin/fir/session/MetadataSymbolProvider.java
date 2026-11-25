package org.jetbrains.kotlin.fir.session;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider;
import org.jetbrains.kotlin.fir.deserialization.FirConstDeserializer;
import org.jetbrains.kotlin.fir.deserialization.FirDeserializationContext;
import org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider;
import org.jetbrains.kotlin.fir.deserialization.PackagePartsCacheData;
import org.jetbrains.kotlin.fir.java.deserialization.ProviderUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.load.kotlin.PackageAndMetadataPartProvider;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsBinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.ClassData;
import org.jetbrains.kotlin.serialization.deserialization.KotlinMetadataFinder;
import org.jetbrains.kotlin.serialization.deserialization.MetadataClassDataFinder;
import org.jetbrains.kotlin.serialization.deserialization.MetadataUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: MetadataSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0016\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0006\u0010\u001e\u001a\u00020\u0017H\u0014J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0014J\u001c\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%H\u0014J\u0018\u0010&\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u00182\u0006\u0010\u001e\u001a\u00020\u0017H\u0002J\u0012\u0010'\u001a\u0004\u0018\u00010\u00172\u0006\u0010(\u001a\u00020\u0017H\u0016J\u0010\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0014J\u0018\u0010-\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u00182\u0006\u0010\u001e\u001a\u00020\u0017H\u0014R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0015\u001a\u001e\u0012\u0004\u0012\u00020\u0017\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u001a0\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/fir/session/MetadataSymbolProvider;", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "moduleDataProvider", "Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "kotlinScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "packageAndMetadataPartProvider", "Lorg/jetbrains/kotlin/load/kotlin/PackageAndMetadataPartProvider;", "kotlinClassFinder", "Lorg/jetbrains/kotlin/serialization/deserialization/KotlinMetadataFinder;", "defaultDeserializationOrigin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;Lorg/jetbrains/kotlin/load/kotlin/PackageAndMetadataPartProvider;Lorg/jetbrains/kotlin/serialization/deserialization/KotlinMetadataFinder;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "annotationDeserializer", "Lorg/jetbrains/kotlin/fir/session/MetadataBasedAnnotationDeserializer;", "classDataFinder", "Lorg/jetbrains/kotlin/serialization/deserialization/MetadataClassDataFinder;", "constDeserializer", "Lorg/jetbrains/kotlin/fir/deserialization/FirConstDeserializer;", "metadataTopLevelClassesInPackageCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/name/FqName;", "", "", "", "computePackagePartsInfos", "", "Lorg/jetbrains/kotlin/fir/deserialization/PackagePartsCacheData;", "packageFqName", "computePackageSetWithNonClassDeclarations", "extractClassMetadata", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider$ClassMetadataFindResult;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "parentContext", "Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;", "findMetadataTopLevelClassesInPackage", "getPackage", "fqName", "isNewPlaceForBodyGeneration", "", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "knownTopLevelClassesInPackage", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MetadataSymbolProvider extends AbstractFirDeserializedSymbolProvider {
    private final MetadataBasedAnnotationDeserializer annotationDeserializer;
    private final MetadataClassDataFinder classDataFinder;
    private final FirConstDeserializer constDeserializer;
    private final KotlinMetadataFinder kotlinClassFinder;
    private final FirCache metadataTopLevelClassesInPackageCache;
    private final PackageAndMetadataPartProvider packageAndMetadataPartProvider;

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected boolean isNewPlaceForBodyGeneration(ProtoBuf.Class classProto) {
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        return false;
    }

    public /* synthetic */ MetadataSymbolProvider(FirSession firSession, ModuleDataProvider moduleDataProvider, FirKotlinScopeProvider firKotlinScopeProvider, PackageAndMetadataPartProvider packageAndMetadataPartProvider, KotlinMetadataFinder kotlinMetadataFinder, FirDeclarationOrigin.Library library, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, moduleDataProvider, firKotlinScopeProvider, packageAndMetadataPartProvider, kotlinMetadataFinder, (i & 32) != 0 ? FirDeclarationOrigin.Library.INSTANCE : library);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MetadataSymbolProvider(FirSession session, ModuleDataProvider moduleDataProvider, FirKotlinScopeProvider kotlinScopeProvider, PackageAndMetadataPartProvider packageAndMetadataPartProvider, KotlinMetadataFinder kotlinClassFinder, FirDeclarationOrigin defaultDeserializationOrigin) {
        super(session, moduleDataProvider, kotlinScopeProvider, defaultDeserializationOrigin, BuiltInSerializerProtocol.INSTANCE);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(moduleDataProvider, "moduleDataProvider");
        Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
        Intrinsics.checkNotNullParameter(packageAndMetadataPartProvider, "packageAndMetadataPartProvider");
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
        Intrinsics.checkNotNullParameter(defaultDeserializationOrigin, "defaultDeserializationOrigin");
        this.packageAndMetadataPartProvider = packageAndMetadataPartProvider;
        this.kotlinClassFinder = kotlinClassFinder;
        this.classDataFinder = new MetadataClassDataFinder(kotlinClassFinder);
        this.annotationDeserializer = new MetadataBasedAnnotationDeserializer(session);
        this.constDeserializer = new FirConstDeserializer(session, BuiltInSerializerProtocol.INSTANCE);
        this.metadataTopLevelClassesInPackageCache = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.session.MetadataSymbolProvider$special$$inlined$createCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((FqName) obj, (Void) obj2);
            }

            public final Set<? extends String> invoke(FqName key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.$receiver$inlined.findMetadataTopLevelClassesInPackage(key);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected List<PackagePartsCacheData> computePackagePartsInfos(FqName packageFqName) throws IOException {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        PackageAndMetadataPartProvider packageAndMetadataPartProvider = this.packageAndMetadataPartProvider;
        String strAsString = packageFqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        List<String> listFindMetadataPackageParts = packageAndMetadataPartProvider.findMetadataPackageParts(strAsString);
        ArrayList arrayList = new ArrayList();
        for (String str : listFindMetadataPackageParts) {
            PackagePartsCacheData packagePartsCacheData = null;
            if (!ProviderUtilsKt.getKotlinBuiltins().contains(str)) {
                InputStream inputStreamFindMetadata = this.kotlinClassFinder.findMetadata(new ClassId(packageFqName, Name.identifier(str)));
                if (inputStreamFindMetadata != null) {
                    Triple<ProtoBuf.PackageFragment, NameResolverImpl, BuiltInsBinaryVersion> proto = MetadataUtilKt.readProto(inputStreamFindMetadata);
                    ProtoBuf.PackageFragment packageFragmentComponent1 = proto.component1();
                    NameResolverImpl nameResolverImplComponent2 = proto.component2();
                    FirDeserializationContext.Companion companion = FirDeserializationContext.INSTANCE;
                    ProtoBuf.Package r6 = packageFragmentComponent1.getPackage();
                    Intrinsics.checkNotNullExpressionValue(r6, "getPackage(...)");
                    FirDeserializationContext firDeserializationContextCreateForPackage = companion.createForPackage(packageFqName, r6, nameResolverImplComponent2, (FirModuleData) CollectionsKt.last(getModuleDataProvider().getAllModuleData()), this.annotationDeserializer, this.constDeserializer, null);
                    ProtoBuf.Package r3 = packageFragmentComponent1.getPackage();
                    Intrinsics.checkNotNullExpressionValue(r3, "getPackage(...)");
                    packagePartsCacheData = new PackagePartsCacheData(r3, firDeserializationContextCreateForPackage);
                }
            }
            if (packagePartsCacheData != null) {
                arrayList.add(packagePartsCacheData);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected Set<String> computePackageSetWithNonClassDeclarations() {
        return this.packageAndMetadataPartProvider.computePackageSetWithNonClassDeclarations();
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected Set<String> knownTopLevelClassesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return (Set) this.metadataTopLevelClassesInPackageCache.getValue(packageFqName, null);
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected AbstractFirDeserializedSymbolProvider.ClassMetadataFindResult extractClassMetadata(ClassId classId, FirDeserializationContext parentContext) throws IOException {
        Intrinsics.checkNotNullParameter(classId, "classId");
        ClassData classDataFindClassData = this.classDataFinder.findClassData(classId);
        if (classDataFindClassData == null) {
            return null;
        }
        return new AbstractFirDeserializedSymbolProvider.ClassMetadataFindResult.Metadata(classDataFindClassData.getNameResolver(), classDataFindClassData.getClassProto(), this.annotationDeserializer, (FirModuleData) CollectionsKt.last(getModuleDataProvider().getAllModuleData()), null, null);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FqName getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Set set = (Set) this.metadataTopLevelClassesInPackageCache.getValue(fqName, null);
        boolean z = false;
        if (set != null && (!set.isEmpty())) {
            z = true;
        }
        if (z) {
            return fqName;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Set<String> findMetadataTopLevelClassesInPackage(FqName packageFqName) {
        return this.kotlinClassFinder.findMetadataTopLevelClassesInPackage(packageFqName);
    }
}
