package org.jetbrains.kotlin.analysis.decompiler.psi;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.DefaultBuiltIns;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.impl.MutablePackageFragmentDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverImpl;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;
import org.jetbrains.kotlin.serialization.deserialization.AnnotationAndConstantLoaderImpl;
import org.jetbrains.kotlin.serialization.deserialization.ContractDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration;
import org.jetbrains.kotlin.serialization.deserialization.FlexibleTypeDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.ProtoBasedClassDataFinder;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPackageMemberScope;

/* JADX INFO: compiled from: KotlinMetadataDeserializerForDecompiler.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u001a\u001a\u00020\u0003H\u0016R\u0014\u0010\u000f\u001a\u00020\u00108TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u0014X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/KotlinMetadataDeserializerForDecompiler;", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/DeserializerForDecompilerBase;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "serializerProtocol", "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "flexibleTypeDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;Lorg/jetbrains/kotlin/serialization/deserialization/FlexibleTypeDeserializer;)V", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "deserializationComponents", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "getDeserializationComponents", "()Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "resolveDeclarationsInFacade", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "facadeFqName", "Companion", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinMetadataDeserializerForDecompiler extends DeserializerForDecompilerBase {
    private static final Logger LOG;
    private final DeserializationComponents deserializationComponents;
    private final BinaryVersion metadataVersion;
    private final NameResolver nameResolver;
    private final ProtoBuf.PackageFragment proto;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinMetadataDeserializerForDecompiler(FqName packageFqName, ProtoBuf.PackageFragment proto, NameResolver nameResolver, BinaryVersion metadataVersion, SerializerExtensionProtocol serializerProtocol, FlexibleTypeDeserializer flexibleTypeDeserializer) {
        super(packageFqName);
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(serializerProtocol, "serializerProtocol");
        Intrinsics.checkNotNullParameter(flexibleTypeDeserializer, "flexibleTypeDeserializer");
        this.proto = proto;
        this.nameResolver = nameResolver;
        this.metadataVersion = metadataVersion;
        NotFoundClasses notFoundClasses = new NotFoundClasses(getStorageManager(), getModuleDescriptor());
        this.deserializationComponents = new DeserializationComponents(getStorageManager(), getModuleDescriptor(), DeserializationConfiguration.Default.INSTANCE, new ProtoBasedClassDataFinder(proto, nameResolver, metadataVersion, null, 8, null), new AnnotationAndConstantLoaderImpl(getModuleDescriptor(), notFoundClasses, serializerProtocol), getPackageFragmentProvider(), new ResolveEverythingToKotlinAnyLocalClassifierResolver(getBuiltIns()), new LoggingErrorReporter(LOG), LookupTracker.DO_NOTHING.INSTANCE, flexibleTypeDeserializer, CollectionsKt.emptyList(), notFoundClasses, ContractDeserializer.INSTANCE.getDEFAULT(), null, null, serializerProtocol.getExtensionRegistry(), null, new SamConversionResolverImpl(getStorageManager(), CollectionsKt.emptyList()), null, getEnumEntriesDeserializationSupport(), 352256, null);
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
        Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
        Intrinsics.areEqual(facadeFqName, getDirectoryPackageFqName());
        MutablePackageFragmentDescriptor mutablePackageFragmentDescriptorCreateDummyPackageFragment = createDummyPackageFragment(facadeFqName);
        ProtoBuf.Package r2 = this.proto.getPackage();
        Intrinsics.checkNotNullExpressionValue(r2, "getPackage(...)");
        return CollectionsKt.toList(ResolutionScope.DefaultImpls.getContributedDescriptors$default(new DeserializedPackageMemberScope(mutablePackageFragmentDescriptorCreateDummyPackageFragment, r2, this.nameResolver, this.metadataVersion, null, getDeserializationComponents(), "scope of dummyPackageFragment " + mutablePackageFragmentDescriptorCreateDummyPackageFragment.getFqName() + " in module " + getDeserializationComponents().getModuleDescriptor() + " @KotlinMetadataDeserializerForDecompiler", new Function0<Collection<? extends Name>>() { // from class: org.jetbrains.kotlin.analysis.decompiler.psi.KotlinMetadataDeserializerForDecompiler$resolveDeclarationsInFacade$membersScope$1
            @Override // kotlin.jvm.functions.Function0
            public final Collection<? extends Name> invoke() {
                return CollectionsKt.emptyList();
            }
        }), null, null, 3, null));
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) KotlinMetadataDeserializerForDecompiler.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
    }
}
