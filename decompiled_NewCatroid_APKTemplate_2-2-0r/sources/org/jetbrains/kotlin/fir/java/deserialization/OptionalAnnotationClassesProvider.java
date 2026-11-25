package org.jetbrains.kotlin.fir.java.deserialization;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider;
import org.jetbrains.kotlin.fir.deserialization.FirDeserializationContext;
import org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider;
import org.jetbrains.kotlin.fir.deserialization.PackagePartsCacheData;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmFlags;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.deserialization.ClassData;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: OptionalAnnotationClassesProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 22\u00020\u0001:\u00012B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"2\u0006\u0010$\u001a\u00020\u000fH\u0014J\u000e\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0014J\u001c\u0010&\u001a\u0004\u0018\u00010'2\u0006\u0010(\u001a\u00020\u00192\b\u0010)\u001a\u0004\u0018\u00010*H\u0014J\u0012\u0010+\u001a\u0004\u0018\u00010\u000f2\u0006\u0010,\u001a\u00020\u000fH\u0016J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0014J\u0016\u00101\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010$\u001a\u00020\u000fH\u0014R-\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R9\u0010\u0016\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a0\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u001b0\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u0015\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 ¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/deserialization/OptionalAnnotationClassesProvider;", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "moduleDataProvider", "Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "kotlinScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "packagePartProvider", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "defaultDeserializationOrigin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "optionalAnnotationClassNamesByPackage", "", "Lorg/jetbrains/kotlin/name/FqName;", "", "", "getOptionalAnnotationClassNamesByPackage", "()Ljava/util/Map;", "optionalAnnotationClassNamesByPackage$delegate", "Lkotlin/Lazy;", "optionalAnnotationClassesAndPackages", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "", "getOptionalAnnotationClassesAndPackages", "()Lkotlin/Pair;", "optionalAnnotationClassesAndPackages$delegate", "getPackagePartProvider", "()Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "computePackagePartsInfos", "", "Lorg/jetbrains/kotlin/fir/deserialization/PackagePartsCacheData;", "packageFqName", "computePackageSetWithNonClassDeclarations", "extractClassMetadata", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider$ClassMetadataFindResult;", "classId", "parentContext", "Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;", "getPackage", "fqName", "isNewPlaceForBodyGeneration", "", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "knownTopLevelClassesInPackage", "Companion", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OptionalAnnotationClassesProvider extends AbstractFirDeserializedSymbolProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: renamed from: optionalAnnotationClassNamesByPackage$delegate, reason: from kotlin metadata */
    private final Lazy optionalAnnotationClassNamesByPackage;

    /* JADX INFO: renamed from: optionalAnnotationClassesAndPackages$delegate, reason: from kotlin metadata */
    private final Lazy optionalAnnotationClassesAndPackages;
    private final PackagePartProvider packagePartProvider;

    public final PackagePartProvider getPackagePartProvider() {
        return this.packagePartProvider;
    }

    public /* synthetic */ OptionalAnnotationClassesProvider(FirSession firSession, ModuleDataProvider moduleDataProvider, FirKotlinScopeProvider firKotlinScopeProvider, PackagePartProvider packagePartProvider, FirDeclarationOrigin.Library library, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, moduleDataProvider, firKotlinScopeProvider, packagePartProvider, (i & 16) != 0 ? FirDeclarationOrigin.Library.INSTANCE : library);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OptionalAnnotationClassesProvider(FirSession session, ModuleDataProvider moduleDataProvider, FirKotlinScopeProvider kotlinScopeProvider, PackagePartProvider packagePartProvider, FirDeclarationOrigin defaultDeserializationOrigin) {
        super(session, moduleDataProvider, kotlinScopeProvider, defaultDeserializationOrigin, BuiltInSerializerProtocol.INSTANCE);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(moduleDataProvider, "moduleDataProvider");
        Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        Intrinsics.checkNotNullParameter(defaultDeserializationOrigin, "defaultDeserializationOrigin");
        this.packagePartProvider = packagePartProvider;
        this.optionalAnnotationClassesAndPackages = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Pair<? extends Map<ClassId, ClassData>, ? extends Set<String>>>() { // from class: org.jetbrains.kotlin.fir.java.deserialization.OptionalAnnotationClassesProvider$optionalAnnotationClassesAndPackages$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Pair<? extends Map<ClassId, ClassData>, ? extends Set<String>> invoke() {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                for (ClassData classData : this.this$0.getPackagePartProvider().getAllOptionalAnnotationClasses()) {
                    ClassId classId = NameResolverUtilKt.getClassId(classData.getNameResolver(), classData.getClassProto().getFqName());
                    linkedHashMap.put(classId, classData);
                    String strAsString = classId.getPackageFqName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    linkedHashSet.add(strAsString);
                }
                return new Pair<>(linkedHashMap, linkedHashSet);
            }
        });
        this.optionalAnnotationClassNamesByPackage = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Map<FqName, ? extends Set<String>>>() { // from class: org.jetbrains.kotlin.fir.java.deserialization.OptionalAnnotationClassesProvider$optionalAnnotationClassNamesByPackage$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<FqName, ? extends Set<String>> invoke() {
                OptionalAnnotationClassesProvider optionalAnnotationClassesProvider = this.this$0;
                Map mapCreateMapBuilder = MapsKt.createMapBuilder();
                for (ClassId classId : ((Map) optionalAnnotationClassesProvider.getOptionalAnnotationClassesAndPackages().getFirst()).keySet()) {
                    FqName packageFqName = classId.getPackageFqName();
                    Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                    Object obj = mapCreateMapBuilder.get(packageFqName);
                    if (obj == null) {
                        obj = (Set) new LinkedHashSet();
                        mapCreateMapBuilder.put(packageFqName, obj);
                    }
                    String strAsString = classId.getShortClassName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    ((Set) obj).add(strAsString);
                }
                return MapsKt.build(mapCreateMapBuilder);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Pair<Map<ClassId, ClassData>, Set<String>> getOptionalAnnotationClassesAndPackages() {
        return (Pair) this.optionalAnnotationClassesAndPackages.getValue();
    }

    private final Map<FqName, Set<String>> getOptionalAnnotationClassNamesByPackage() {
        return (Map) this.optionalAnnotationClassNamesByPackage.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected List<PackagePartsCacheData> computePackagePartsInfos(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected Set<String> computePackageSetWithNonClassDeclarations() {
        return getOptionalAnnotationClassesAndPackages().getSecond();
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected Set<String> knownTopLevelClassesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Set<String> set = getOptionalAnnotationClassNamesByPackage().get(packageFqName);
        return set == null ? SetsKt.emptySet() : set;
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected AbstractFirDeserializedSymbolProvider.ClassMetadataFindResult extractClassMetadata(ClassId classId, FirDeserializationContext parentContext) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        ClassData classData = getOptionalAnnotationClassesAndPackages().getFirst().get(classId);
        if (classData == null) {
            return null;
        }
        return new AbstractFirDeserializedSymbolProvider.ClassMetadataFindResult.Metadata(classData.getNameResolver(), classData.getClassProto(), null, (FirModuleData) CollectionsKt.last(getModuleDataProvider().getAllModuleData()), null, null);
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected boolean isNewPlaceForBodyGeneration(ProtoBuf.Class classProto) {
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        Flags.BooleanFlagField is_compiled_in_jvm_default_mode = JvmFlags.INSTANCE.getIS_COMPILED_IN_JVM_DEFAULT_MODE();
        Object extension = classProto.getExtension(JvmProtoBuf.jvmClassFlags);
        Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
        Boolean bool = is_compiled_in_jvm_default_mode.get(((Number) extension).intValue());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FqName getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (getOptionalAnnotationClassesAndPackages().getSecond().contains(fqName.asString())) {
            return fqName;
        }
        return null;
    }

    /* JADX INFO: compiled from: OptionalAnnotationClassesProvider.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J2\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/deserialization/OptionalAnnotationClassesProvider$Companion;", "", "()V", "createIfNeeded", "Lorg/jetbrains/kotlin/fir/java/deserialization/OptionalAnnotationClassesProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "moduleDataProvider", "Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "kotlinScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "packagePartProvider", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "defaultDeserializationOrigin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ OptionalAnnotationClassesProvider createIfNeeded$default(Companion companion, FirSession firSession, ModuleDataProvider moduleDataProvider, FirKotlinScopeProvider firKotlinScopeProvider, PackagePartProvider packagePartProvider, FirDeclarationOrigin firDeclarationOrigin, int i, Object obj) {
            if ((i & 16) != 0) {
                firDeclarationOrigin = FirDeclarationOrigin.Library.INSTANCE;
            }
            return companion.createIfNeeded(firSession, moduleDataProvider, firKotlinScopeProvider, packagePartProvider, firDeclarationOrigin);
        }

        public final OptionalAnnotationClassesProvider createIfNeeded(FirSession session, ModuleDataProvider moduleDataProvider, FirKotlinScopeProvider kotlinScopeProvider, PackagePartProvider packagePartProvider, FirDeclarationOrigin defaultDeserializationOrigin) {
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(moduleDataProvider, "moduleDataProvider");
            Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
            Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
            Intrinsics.checkNotNullParameter(defaultDeserializationOrigin, "defaultDeserializationOrigin");
            if (packagePartProvider.mayHaveOptionalAnnotationClasses()) {
                return new OptionalAnnotationClassesProvider(session, moduleDataProvider, kotlinScopeProvider, packagePartProvider, defaultDeserializationOrigin);
            }
            return null;
        }
    }
}
