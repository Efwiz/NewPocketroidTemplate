package org.jetbrains.kotlin.fir.java.deserialization;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider;
import org.jetbrains.kotlin.fir.deserialization.FirDeserializationContext;
import org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider;
import org.jetbrains.kotlin.fir.deserialization.PackagePartsCacheData;
import org.jetbrains.kotlin.fir.java.FirJavaFacade;
import org.jetbrains.kotlin.fir.resolve.transformers.PublishedApiEffectiveVisibilityKt;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.load.kotlin.JvmPackagePartSource;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinderKt;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.PackagePartProvider;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmFlags;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.serialization.deserialization.IncompatibleVersionErrorData;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerAbiStability;
import org.jetbrains.kotlin.utils.MetadataHelpersKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JvmClassFileBasedSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\u0006\u0010\u001f\u001a\u00020 H\u0014J\u000e\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"H\u0014J\u001c\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010)H\u0014J\u0012\u0010*\u001a\u0004\u0018\u00010 2\u0006\u0010+\u001a\u00020 H\u0016J\u0010\u0010,\u001a\u00020\u001a2\u0006\u0010-\u001a\u00020.H\u0014J\u0018\u0010/\u001a\n\u0012\u0004\u0012\u00020#\u0018\u00010\"2\u0006\u0010\u001f\u001a\u00020 H\u0014J\u0018\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0002J\u0010\u00106\u001a\u0004\u0018\u000107*\u0004\u0018\u00010#H\u0002R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u0014*\u00020\u00168BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0018\u0010\u0019\u001a\u00020\u001a*\u00020\u00168BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001b¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/deserialization/JvmClassFileBasedSymbolProvider;", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "moduleDataProvider", "Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "kotlinScopeProvider", "Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "packagePartProvider", "Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;", "kotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "javaFacade", "Lorg/jetbrains/kotlin/fir/java/FirJavaFacade;", "defaultDeserializationOrigin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;Lorg/jetbrains/kotlin/load/kotlin/PackagePartProvider;Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;Lorg/jetbrains/kotlin/fir/java/FirJavaFacade;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "annotationsLoader", "Lorg/jetbrains/kotlin/fir/java/deserialization/AnnotationsLoader;", "incompatibility", "Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "getIncompatibility", "(Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;)Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "isPreReleaseInvisible", "", "(Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;)Z", "computePackagePartsInfos", "", "Lorg/jetbrains/kotlin/fir/deserialization/PackagePartsCacheData;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "computePackageSetWithNonClassDeclarations", "", "", "extractClassMetadata", "Lorg/jetbrains/kotlin/fir/deserialization/AbstractFirDeserializedSymbolProvider$ClassMetadataFindResult;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "parentContext", "Lorg/jetbrains/kotlin/fir/deserialization/FirDeserializationContext;", "getPackage", "fqName", "isNewPlaceForBodyGeneration", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "knownTopLevelClassesInPackage", "loadAnnotationsFromClassFile", "", "kotlinClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result$KotlinClass;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "toPath", "Ljava/nio/file/Path;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmClassFileBasedSymbolProvider extends AbstractFirDeserializedSymbolProvider {
    private final AnnotationsLoader annotationsLoader;
    private final FirJavaFacade javaFacade;
    private final KotlinClassFinder kotlinClassFinder;
    private final PackagePartProvider packagePartProvider;

    public /* synthetic */ JvmClassFileBasedSymbolProvider(FirSession firSession, ModuleDataProvider moduleDataProvider, FirKotlinScopeProvider firKotlinScopeProvider, PackagePartProvider packagePartProvider, KotlinClassFinder kotlinClassFinder, FirJavaFacade firJavaFacade, FirDeclarationOrigin firDeclarationOrigin, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, moduleDataProvider, firKotlinScopeProvider, packagePartProvider, kotlinClassFinder, firJavaFacade, (i & 64) != 0 ? FirDeclarationOrigin.Library.INSTANCE : firDeclarationOrigin);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmClassFileBasedSymbolProvider(FirSession session, ModuleDataProvider moduleDataProvider, FirKotlinScopeProvider kotlinScopeProvider, PackagePartProvider packagePartProvider, KotlinClassFinder kotlinClassFinder, FirJavaFacade javaFacade, FirDeclarationOrigin defaultDeserializationOrigin) {
        super(session, moduleDataProvider, kotlinScopeProvider, defaultDeserializationOrigin, BuiltInSerializerProtocol.INSTANCE);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(moduleDataProvider, "moduleDataProvider");
        Intrinsics.checkNotNullParameter(kotlinScopeProvider, "kotlinScopeProvider");
        Intrinsics.checkNotNullParameter(packagePartProvider, "packagePartProvider");
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
        Intrinsics.checkNotNullParameter(javaFacade, "javaFacade");
        Intrinsics.checkNotNullParameter(defaultDeserializationOrigin, "defaultDeserializationOrigin");
        this.packagePartProvider = packagePartProvider;
        this.kotlinClassFinder = kotlinClassFinder;
        this.javaFacade = javaFacade;
        this.annotationsLoader = new AnnotationsLoader(session, kotlinClassFinder);
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected List<PackagePartsCacheData> computePackagePartsInfos(FqName packageFqName) {
        KotlinJvmBinaryClass kotlinJvmBinaryClassFindKotlinClass;
        KotlinClassHeader classHeader;
        String[] data;
        String[] strings;
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        PackagePartProvider packagePartProvider = this.packagePartProvider;
        String strAsString = packageFqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        List<String> listFindPackageParts = packagePartProvider.findPackageParts(strAsString);
        ArrayList arrayList = new ArrayList();
        for (String str : listFindPackageParts) {
            PackagePartsCacheData packagePartsCacheData = null;
            if (!ProviderUtilsKt.getKotlinBuiltins().contains(str)) {
                ClassId classId = ClassId.topLevel(JvmClassName.byInternalName(str).getFqNameForTopLevelClassMaybeWithDollars());
                Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
                if (this.javaFacade.hasTopLevelClassOf(classId)) {
                    JvmMetadataVersion metadataVersion = MetadataHelpersKt.toMetadataVersion(FirLanguageSettingsComponentKt.getLanguageVersionSettings(getSession()).getLanguageVersion());
                    KotlinClassFinder.Result resultFindKotlinClassOrContent = this.kotlinClassFinder.findKotlinClassOrContent(classId, metadataVersion);
                    KotlinClassFinder.Result.KotlinClass kotlinClass = resultFindKotlinClassOrContent instanceof KotlinClassFinder.Result.KotlinClass ? (KotlinClassFinder.Result.KotlinClass) resultFindKotlinClassOrContent : null;
                    if (kotlinClass != null) {
                        KotlinJvmBinaryClass kotlinJvmBinaryClass = kotlinClass.getKotlinJvmBinaryClass();
                        byte[] byteContent = kotlinClass.getByteContent();
                        String multifileClassName = kotlinJvmBinaryClass.getClassHeader().getMultifileClassName();
                        if (multifileClassName == null || multifileClassName.length() <= 0) {
                            multifileClassName = null;
                        }
                        FqName fqNameForTopLevelClassMaybeWithDollars = multifileClassName != null ? JvmClassName.byInternalName(multifileClassName).getFqNameForTopLevelClassMaybeWithDollars() : null;
                        if (fqNameForTopLevelClassMaybeWithDollars != null) {
                            KotlinClassFinder kotlinClassFinder = this.kotlinClassFinder;
                            ClassId classId2 = ClassId.topLevel(fqNameForTopLevelClassMaybeWithDollars);
                            Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
                            kotlinJvmBinaryClassFindKotlinClass = KotlinClassFinderKt.findKotlinClass(kotlinClassFinder, classId2, metadataVersion);
                        } else {
                            kotlinJvmBinaryClassFindKotlinClass = null;
                        }
                        FirModuleData moduleData = getModuleDataProvider().getModuleData(toPath(kotlinJvmBinaryClass.getContainingLibrary()));
                        if (moduleData != null && (((data = (classHeader = kotlinJvmBinaryClass.getClassHeader()).getData()) != null || (data = classHeader.getIncompatibleData()) != null) && (strings = classHeader.getStrings()) != null)) {
                            Pair<JvmNameResolver, ProtoBuf.Package> packageDataFrom = JvmProtoBufUtil.readPackageDataFrom(data, strings);
                            JvmNameResolver jvmNameResolverComponent1 = packageDataFrom.component1();
                            ProtoBuf.Package packageComponent2 = packageDataFrom.component2();
                            JvmNameResolver jvmNameResolver = jvmNameResolverComponent1;
                            JvmPackagePartSource jvmPackagePartSource = new JvmPackagePartSource(kotlinJvmBinaryClass, packageComponent2, jvmNameResolver, getIncompatibility(kotlinJvmBinaryClass), isPreReleaseInvisible(kotlinJvmBinaryClass), (DeserializedContainerAbiStability) null, 32, (DefaultConstructorMarker) null);
                            FirDeserializationContext.Companion companion = FirDeserializationContext.INSTANCE;
                            JvmBinaryAnnotationDeserializer jvmBinaryAnnotationDeserializer = new JvmBinaryAnnotationDeserializer(getSession(), kotlinJvmBinaryClass, this.kotlinClassFinder, byteContent);
                            FirSession session = getSession();
                            if (kotlinJvmBinaryClassFindKotlinClass != null) {
                                kotlinJvmBinaryClass = kotlinJvmBinaryClassFindKotlinClass;
                            }
                            packagePartsCacheData = new PackagePartsCacheData(packageComponent2, companion.createForPackage(packageFqName, packageComponent2, jvmNameResolver, moduleData, jvmBinaryAnnotationDeserializer, new FirJvmConstDeserializer(session, kotlinJvmBinaryClass, BuiltInSerializerProtocol.INSTANCE), jvmPackagePartSource));
                        }
                    }
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
        return this.packagePartProvider.computePackageSetWithNonClassDeclarations();
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    protected Set<String> knownTopLevelClassesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return this.javaFacade.knownClassNamesInPackage(packageFqName);
    }

    private final IncompatibleVersionErrorData<JvmMetadataVersion> getIncompatibility(KotlinJvmBinaryClass kotlinJvmBinaryClass) {
        JvmMetadataVersion metadataVersion = MetadataHelpersKt.toMetadataVersion(FirLanguageSettingsComponentKt.getLanguageVersionSettings(getSession()).getLanguageVersion());
        if (kotlinJvmBinaryClass.getClassHeader().getMetadataVersion().isCompatible(metadataVersion)) {
            return null;
        }
        return new IncompatibleVersionErrorData<>(kotlinJvmBinaryClass.getClassHeader().getMetadataVersion(), JvmMetadataVersion.INSTANCE, metadataVersion, metadataVersion.lastSupportedVersionWithThisLanguageVersion(kotlinJvmBinaryClass.getClassHeader().getMetadataVersion().getIsStrictSemantics()), kotlinJvmBinaryClass.getLocation(), kotlinJvmBinaryClass.getClassId());
    }

    private final boolean isPreReleaseInvisible(KotlinJvmBinaryClass kotlinJvmBinaryClass) {
        return kotlinJvmBinaryClass.getClassHeader().isPreRelease();
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x004e, code lost:
    
        if (((r14 != null ? (org.jetbrains.kotlin.fir.declarations.FirRegularClass) r14.getFir() : null) instanceof org.jetbrains.kotlin.fir.java.declarations.FirJavaClass) == false) goto L29;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider.ClassMetadataFindResult extractClassMetadata(final org.jetbrains.kotlin.name.ClassId r13, org.jetbrains.kotlin.fir.deserialization.FirDeserializationContext r14) {
        /*
            Method dump skipped, instruction units count: 268
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.java.deserialization.JvmClassFileBasedSymbolProvider.extractClassMetadata(org.jetbrains.kotlin.name.ClassId, org.jetbrains.kotlin.fir.deserialization.FirDeserializationContext):org.jetbrains.kotlin.fir.deserialization.AbstractFirDeserializedSymbolProvider$ClassMetadataFindResult");
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
        return this.javaFacade.getPackage(fqName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void loadAnnotationsFromClassFile(KotlinClassFinder.Result.KotlinClass kotlinClass, FirRegularClassSymbol symbol) {
        final ArrayList arrayList = new ArrayList();
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        kotlinClass.getKotlinJvmBinaryClass().loadClassAnnotations(new KotlinJvmBinaryClass.AnnotationVisitor() { // from class: org.jetbrains.kotlin.fir.java.deserialization.JvmClassFileBasedSymbolProvider.loadAnnotationsFromClassFile.1
            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationVisitor
            public void visitEnd() {
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationVisitor
            public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(ClassId classId, SourceElement source) {
                Intrinsics.checkNotNullParameter(classId, "classId");
                Intrinsics.checkNotNullParameter(source, "source");
                if (Intrinsics.areEqual(classId, StandardClassIds.Annotations.INSTANCE.getPublishedApi())) {
                    booleanRef.element = true;
                }
                return this.annotationsLoader.loadAnnotationIfNotSpecial$java(classId, arrayList);
            }
        }, kotlinClass.getByteContent());
        FirRegularClass firRegularClass = (FirRegularClass) symbol.getFir();
        firRegularClass.replaceAnnotations(MutableOrEmptyList.m6756boximpl(FirBuilderDslKt.toMutableOrEmpty(arrayList)));
        firRegularClass.replaceDeprecationsProvider(DeprecationUtilsKt.getDeprecationsProvider(symbol.getFir(), getSession()));
        PublishedApiEffectiveVisibilityKt.setLazyPublishedVisibility(firRegularClass, booleanRef.element, (FirProperty) null, getSession());
    }

    private final Path toPath(String str) {
        if (str != null) {
            return Paths.get(str, new String[0]).normalize();
        }
        return null;
    }
}
