package org.jetbrains.kotlin.analysis.decompiler.psi;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.stub.file.DirectoryBasedClassFinder;
import org.jetbrains.kotlin.analysis.decompiler.stub.file.DirectoryBasedDataFinder;
import org.jetbrains.kotlin.builtins.DefaultBuiltIns;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.contracts.ContractDeserializerImpl;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.impl.MutablePackageFragmentDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.load.kotlin.BinaryClassAnnotationAndConstantLoaderImpl;
import org.jetbrains.kotlin.load.kotlin.BinaryClassAnnotationAndConstantLoaderImplKt;
import org.jetbrains.kotlin.load.kotlin.JavaFlexibleTypeDeserializer;
import org.jetbrains.kotlin.load.kotlin.JvmPackagePartSource;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinderKt;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverImpl;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.serialization.deserialization.IncompatibleVersionErrorData;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerAbiStability;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPackageMemberScope;

/* JADX INFO: compiled from: DeserializerForClassfileDecompiler.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\u0005H\u0016R\u0014\u0010\t\u001a\u00020\n8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u0010X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/DeserializerForClassfileDecompiler;", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/DeserializerForDecompilerBase;", "packageDirectory", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "directoryPackageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "(Lcom/intellij/openapi/vfs/VirtualFile;Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;)V", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "classFinder", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/DirectoryBasedClassFinder;", "deserializationComponents", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "getDeserializationComponents", "()Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "resolveDeclarationsInFacade", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "facadeFqName", "Companion", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeserializerForClassfileDecompiler extends DeserializerForDecompilerBase {
    private static final Logger LOG;
    private final DirectoryBasedClassFinder classFinder;
    private final DeserializationComponents deserializationComponents;
    private final JvmMetadataVersion jvmMetadataVersion;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeserializerForClassfileDecompiler(VirtualFile packageDirectory, FqName directoryPackageFqName, JvmMetadataVersion jvmMetadataVersion) {
        super(directoryPackageFqName);
        Intrinsics.checkNotNullParameter(packageDirectory, "packageDirectory");
        Intrinsics.checkNotNullParameter(directoryPackageFqName, "directoryPackageFqName");
        Intrinsics.checkNotNullParameter(jvmMetadataVersion, "jvmMetadataVersion");
        this.jvmMetadataVersion = jvmMetadataVersion;
        DirectoryBasedClassFinder directoryBasedClassFinder = new DirectoryBasedClassFinder(packageDirectory, directoryPackageFqName);
        this.classFinder = directoryBasedClassFinder;
        Logger logger = LOG;
        DirectoryBasedDataFinder directoryBasedDataFinder = new DirectoryBasedDataFinder(directoryBasedClassFinder, logger, jvmMetadataVersion);
        NotFoundClasses notFoundClasses = new NotFoundClasses(getStorageManager(), getModuleDescriptor());
        BinaryClassAnnotationAndConstantLoaderImpl binaryClassAnnotationAndConstantLoaderImplCreateBinaryClassAnnotationAndConstantLoader = BinaryClassAnnotationAndConstantLoaderImplKt.createBinaryClassAnnotationAndConstantLoader(getModuleDescriptor(), notFoundClasses, getStorageManager(), directoryBasedClassFinder, jvmMetadataVersion);
        DeserializationConfiguration deserializationConfiguration = new DeserializationConfiguration() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.DeserializerForClassfileDecompiler$configuration$1
            private final boolean readDeserializedContracts = true;
            private final boolean preserveDeclarationsOrdering = true;

            @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration
            public boolean getReadDeserializedContracts() {
                return this.readDeserializedContracts;
            }

            @Override // org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration
            public boolean getPreserveDeclarationsOrdering() {
                return this.preserveDeclarationsOrdering;
            }
        };
        this.deserializationComponents = new DeserializationComponents(getStorageManager(), getModuleDescriptor(), deserializationConfiguration, directoryBasedDataFinder, binaryClassAnnotationAndConstantLoaderImplCreateBinaryClassAnnotationAndConstantLoader, getPackageFragmentProvider(), new ResolveEverythingToKotlinAnyLocalClassifierResolver(getBuiltIns()), new LoggingErrorReporter(logger), LookupTracker.DO_NOTHING.INSTANCE, JavaFlexibleTypeDeserializer.INSTANCE, CollectionsKt.emptyList(), notFoundClasses, new ContractDeserializerImpl(deserializationConfiguration, getStorageManager()), null, null, JvmProtoBufUtil.INSTANCE.getEXTENSION_REGISTRY(), null, new SamConversionResolverImpl(getStorageManager(), CollectionsKt.emptyList()), null, getEnumEntriesDeserializationSupport(), 352256, null);
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.psi.DeserializerForDecompilerBase
    protected KotlinBuiltIns getBuiltIns() {
        return DefaultBuiltIns.INSTANCE.getInstance();
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.psi.DeserializerForDecompilerBase
    protected DeserializationComponents getDeserializationComponents() {
        return this.deserializationComponents;
    }

    @Override // org.jetbrains.kotlin.analysis.decompiler.psi.ResolverForDecompiler
    public List<DeclarationDescriptor> resolveDeclarationsInFacade(FqName facadeFqName) {
        FqName fqNameParent;
        Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
        FqName fqNameParent2 = facadeFqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent2, "parent(...)");
        Intrinsics.areEqual(fqNameParent2, getDirectoryPackageFqName());
        DirectoryBasedClassFinder directoryBasedClassFinder = this.classFinder;
        ClassId classId = ClassId.topLevel(facadeFqName);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        KotlinJvmBinaryClass kotlinJvmBinaryClassFindKotlinClass = KotlinClassFinderKt.findKotlinClass(directoryBasedClassFinder, classId, this.jvmMetadataVersion);
        KotlinClassHeader classHeader = kotlinJvmBinaryClassFindKotlinClass != null ? kotlinJvmBinaryClassFindKotlinClass.getClassHeader() : null;
        String[] data = classHeader != null ? classHeader.getData() : null;
        String[] strings = classHeader != null ? classHeader.getStrings() : null;
        if (data == null || strings == null) {
            LOG.error("Could not read annotation data for " + facadeFqName + " from " + (kotlinJvmBinaryClassFindKotlinClass != null ? kotlinJvmBinaryClassFindKotlinClass.getClassId() : null));
            return CollectionsKt.emptyList();
        }
        Pair<JvmNameResolver, ProtoBuf.Package> packageDataFrom = JvmProtoBufUtil.readPackageDataFrom(data, strings);
        JvmNameResolver jvmNameResolverComponent1 = packageDataFrom.component1();
        ProtoBuf.Package packageComponent2 = packageDataFrom.component2();
        String packageName = classHeader.getPackageName();
        if (packageName != null) {
            fqNameParent = new FqName(packageName);
        } else {
            fqNameParent = facadeFqName.parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        }
        MutablePackageFragmentDescriptor mutablePackageFragmentDescriptorCreateDummyPackageFragment = createDummyPackageFragment(fqNameParent);
        JvmNameResolver jvmNameResolver = jvmNameResolverComponent1;
        return CollectionsKt.toList(ResolutionScope.DefaultImpls.getContributedDescriptors$default(new DeserializedPackageMemberScope(mutablePackageFragmentDescriptorCreateDummyPackageFragment, packageComponent2, jvmNameResolver, classHeader.getMetadataVersion(), new JvmPackagePartSource(kotlinJvmBinaryClassFindKotlinClass, packageComponent2, (NameResolver) jvmNameResolver, (IncompatibleVersionErrorData) null, false, (DeserializedContainerAbiStability) null, 56, (DefaultConstructorMarker) null), getDeserializationComponents(), "scope of dummyPackageFragment " + mutablePackageFragmentDescriptorCreateDummyPackageFragment.getFqName() + " in module " + getModuleDescriptor() + " @DeserializerForClassfileDecompiler", new Function0<Collection<? extends Name>>() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.DeserializerForClassfileDecompiler$resolveDeclarationsInFacade$membersScope$1
            @Override // kotlin.jvm.functions.Function0
            public final Collection<? extends Name> invoke() {
                return CollectionsKt.emptyList();
            }
        }), null, null, 3, null));
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) DeserializerForClassfileDecompiler.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
    }
}
