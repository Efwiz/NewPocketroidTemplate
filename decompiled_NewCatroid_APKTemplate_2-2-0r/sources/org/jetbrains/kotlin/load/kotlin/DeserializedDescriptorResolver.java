package org.jetbrains.kotlin.load.kotlin;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.javax.inject.Inject;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.serialization.deserialization.ClassData;
import org.jetbrains.kotlin.serialization.deserialization.DeserializationComponents;
import org.jetbrains.kotlin.serialization.deserialization.IncompatibleVersionErrorData;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerAbiStability;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPackageMemberScope;
import org.jetbrains.kotlin.utils.DeserializationHelpersKt;

/* JADX INFO: compiled from: DeserializedDescriptorResolver.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 62\u00020\u0001:\u00016B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0013J0\u0010\"\u001a\u0004\u0018\u0001H#\"\b\b\u0000\u0010#*\u00020\u00012\u0006\u0010$\u001a\u00020\u00132\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H#0&H\u0082\b¢\u0006\u0002\u0010'J\u0017\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010!\u001a\u00020\u0013H\u0000¢\u0006\u0002\b*J+\u0010+\u001a\n\u0012\u0004\u0012\u00020-\u0018\u00010,2\u0006\u0010!\u001a\u00020\u00132\f\u0010.\u001a\b\u0012\u0004\u0012\u0002000/H\u0002¢\u0006\u0002\u00101J\u0010\u00102\u001a\u0004\u0018\u0001032\u0006\u0010!\u001a\u00020\u0013J\u0010\u0010\u0007\u001a\u0002042\u0006\u0010\u0003\u001a\u000205H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\u00020\u0012*\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R \u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\u0017*\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0018\u0010\u001a\u001a\u00020\u000e*\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0018\u0010\u001c\u001a\u00020\u000e*\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001b¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/DeserializedDescriptorResolver;", "", "()V", "components", "Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "getComponents", "()Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;", "setComponents", "(Lorg/jetbrains/kotlin/serialization/deserialization/DeserializationComponents;)V", "ownMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "getOwnMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "skipMetadataVersionCheck", "", "getSkipMetadataVersionCheck", "()Z", "abiStability", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "getAbiStability", "(Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;)Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "incompatibility", "Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "getIncompatibility", "(Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;)Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "isCompiledWith13M1", "(Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;)Z", "isPreReleaseInvisible", "createKotlinPackagePartScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "kotlinClass", "parseProto", ExifInterface.GPS_DIRECTION_TRUE, "klass", "block", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "readClassData", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "readClassData$descriptors_jvm", "readData", "", "", "expectedKinds", "", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind;", "(Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;Ljava/util/Set;)[Ljava/lang/String;", "resolveClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "", "Lorg/jetbrains/kotlin/load/kotlin/DeserializationComponentsForJava;", "Companion", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeserializedDescriptorResolver {
    public DeserializationComponents components;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Set<KotlinClassHeader.Kind> KOTLIN_CLASS = SetsKt.setOf(KotlinClassHeader.Kind.CLASS);
    private static final Set<KotlinClassHeader.Kind> KOTLIN_FILE_FACADE_OR_MULTIFILE_CLASS_PART = SetsKt.setOf((Object[]) new KotlinClassHeader.Kind[]{KotlinClassHeader.Kind.FILE_FACADE, KotlinClassHeader.Kind.MULTIFILE_CLASS_PART});
    private static final JvmMetadataVersion KOTLIN_1_1_EAP_METADATA_VERSION = new JvmMetadataVersion(1, 1, 2);
    private static final JvmMetadataVersion KOTLIN_1_3_M1_METADATA_VERSION = new JvmMetadataVersion(1, 1, 11);
    private static final JvmMetadataVersion KOTLIN_1_3_RC_METADATA_VERSION = new JvmMetadataVersion(1, 1, 13);

    public final DeserializationComponents getComponents() {
        DeserializationComponents deserializationComponents = this.components;
        if (deserializationComponents != null) {
            return deserializationComponents;
        }
        Intrinsics.throwUninitializedPropertyAccessException("components");
        return null;
    }

    public final void setComponents(DeserializationComponents deserializationComponents) {
        Intrinsics.checkNotNullParameter(deserializationComponents, "<set-?>");
        this.components = deserializationComponents;
    }

    private final JvmMetadataVersion getOwnMetadataVersion() {
        return DeserializationHelpersKt.jvmMetadataVersionOrDefault(getComponents().getConfiguration());
    }

    @Inject
    public final void setComponents(DeserializationComponentsForJava components) {
        Intrinsics.checkNotNullParameter(components, "components");
        setComponents(components.getComponents());
    }

    private final boolean getSkipMetadataVersionCheck() {
        return getComponents().getConfiguration().getSkipMetadataVersionCheck();
    }

    public final ClassDescriptor resolveClass(KotlinJvmBinaryClass kotlinClass) {
        Intrinsics.checkNotNullParameter(kotlinClass, "kotlinClass");
        ClassData classData$descriptors_jvm = readClassData$descriptors_jvm(kotlinClass);
        if (classData$descriptors_jvm == null) {
            return null;
        }
        return getComponents().getClassDeserializer().deserializeClass(kotlinClass.getClassId(), classData$descriptors_jvm);
    }

    public final ClassData readClassData$descriptors_jvm(KotlinJvmBinaryClass kotlinClass) {
        Pair<JvmNameResolver, ProtoBuf.Class> classDataFrom;
        Intrinsics.checkNotNullParameter(kotlinClass, "kotlinClass");
        String[] data = readData(kotlinClass, KOTLIN_CLASS);
        if (data == null) {
            return null;
        }
        String[] strings = kotlinClass.getClassHeader().getStrings();
        try {
            if (strings == null) {
                return null;
            }
            try {
                classDataFrom = JvmProtoBufUtil.readClassDataFrom(data, strings);
            } catch (InvalidProtocolBufferException e) {
                throw new IllegalStateException("Could not read data from " + kotlinClass.getLocation(), e);
            }
        } catch (Throwable th) {
            if (getSkipMetadataVersionCheck() || kotlinClass.getClassHeader().getMetadataVersion().isCompatible(getOwnMetadataVersion())) {
                throw th;
            }
            classDataFrom = null;
        }
        if (classDataFrom == null) {
            return null;
        }
        return new ClassData(classDataFrom.component1(), classDataFrom.component2(), kotlinClass.getClassHeader().getMetadataVersion(), new KotlinJvmBinarySourceElement(kotlinClass, getIncompatibility(kotlinClass), isPreReleaseInvisible(kotlinClass), getAbiStability(kotlinClass)));
    }

    public final MemberScope createKotlinPackagePartScope(PackageFragmentDescriptor descriptor, KotlinJvmBinaryClass kotlinClass) {
        String[] strings;
        Pair<JvmNameResolver, ProtoBuf.Package> packageDataFrom;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(kotlinClass, "kotlinClass");
        String[] data = readData(kotlinClass, KOTLIN_FILE_FACADE_OR_MULTIFILE_CLASS_PART);
        if (data == null || (strings = kotlinClass.getClassHeader().getStrings()) == null) {
            return null;
        }
        try {
            try {
                packageDataFrom = JvmProtoBufUtil.readPackageDataFrom(data, strings);
            } catch (InvalidProtocolBufferException e) {
                throw new IllegalStateException("Could not read data from " + kotlinClass.getLocation(), e);
            }
        } catch (Throwable th) {
            if (getSkipMetadataVersionCheck() || kotlinClass.getClassHeader().getMetadataVersion().isCompatible(getOwnMetadataVersion())) {
                throw th;
            }
            packageDataFrom = null;
        }
        if (packageDataFrom == null) {
            return null;
        }
        JvmNameResolver jvmNameResolverComponent1 = packageDataFrom.component1();
        ProtoBuf.Package packageComponent2 = packageDataFrom.component2();
        JvmNameResolver jvmNameResolver = jvmNameResolverComponent1;
        JvmPackagePartSource jvmPackagePartSource = new JvmPackagePartSource(kotlinClass, packageComponent2, jvmNameResolver, getIncompatibility(kotlinClass), isPreReleaseInvisible(kotlinClass), getAbiStability(kotlinClass));
        return new DeserializedPackageMemberScope(descriptor, packageComponent2, jvmNameResolver, kotlinClass.getClassHeader().getMetadataVersion(), jvmPackagePartSource, getComponents(), "scope for " + jvmPackagePartSource + " in " + descriptor, new Function0<Collection<? extends Name>>() { // from class: org.jetbrains.kotlin.load.kotlin.DeserializedDescriptorResolver.createKotlinPackagePartScope.2
            @Override // kotlin.jvm.functions.Function0
            public final Collection<? extends Name> invoke() {
                return CollectionsKt.emptyList();
            }
        });
    }

    private final IncompatibleVersionErrorData<JvmMetadataVersion> getIncompatibility(KotlinJvmBinaryClass kotlinJvmBinaryClass) {
        if (getSkipMetadataVersionCheck() || kotlinJvmBinaryClass.getClassHeader().getMetadataVersion().isCompatible(getOwnMetadataVersion())) {
            return null;
        }
        return new IncompatibleVersionErrorData<>(kotlinJvmBinaryClass.getClassHeader().getMetadataVersion(), JvmMetadataVersion.INSTANCE, getOwnMetadataVersion(), getOwnMetadataVersion().lastSupportedVersionWithThisLanguageVersion(kotlinJvmBinaryClass.getClassHeader().getMetadataVersion().getIsStrictSemantics()), kotlinJvmBinaryClass.getLocation(), kotlinJvmBinaryClass.getClassId());
    }

    private final boolean isPreReleaseInvisible(KotlinJvmBinaryClass kotlinJvmBinaryClass) {
        return (getComponents().getConfiguration().getReportErrorsOnPreReleaseDependencies() && (kotlinJvmBinaryClass.getClassHeader().isPreRelease() || Intrinsics.areEqual(kotlinJvmBinaryClass.getClassHeader().getMetadataVersion(), KOTLIN_1_1_EAP_METADATA_VERSION))) || isCompiledWith13M1(kotlinJvmBinaryClass);
    }

    private final boolean isCompiledWith13M1(KotlinJvmBinaryClass kotlinJvmBinaryClass) {
        return !getComponents().getConfiguration().getSkipPrereleaseCheck() && kotlinJvmBinaryClass.getClassHeader().isPreRelease() && Intrinsics.areEqual(kotlinJvmBinaryClass.getClassHeader().getMetadataVersion(), KOTLIN_1_3_M1_METADATA_VERSION);
    }

    private final DeserializedContainerAbiStability getAbiStability(KotlinJvmBinaryClass kotlinJvmBinaryClass) {
        if (getComponents().getConfiguration().getAllowUnstableDependencies()) {
            return DeserializedContainerAbiStability.STABLE;
        }
        if (kotlinJvmBinaryClass.getClassHeader().isUnstableFirBinary()) {
            return DeserializedContainerAbiStability.FIR_UNSTABLE;
        }
        return kotlinJvmBinaryClass.getClassHeader().isUnstableJvmIrBinary() ? DeserializedContainerAbiStability.IR_UNSTABLE : DeserializedContainerAbiStability.STABLE;
    }

    private final String[] readData(KotlinJvmBinaryClass kotlinClass, Set<? extends KotlinClassHeader.Kind> expectedKinds) {
        KotlinClassHeader classHeader = kotlinClass.getClassHeader();
        String[] data = classHeader.getData();
        if (data == null) {
            data = classHeader.getIncompatibleData();
        }
        if (data == null || !expectedKinds.contains(classHeader.getKind())) {
            return null;
        }
        return data;
    }

    /* JADX INFO: compiled from: DeserializedDescriptorResolver.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/DeserializedDescriptorResolver$Companion;", "", "()V", "KOTLIN_1_1_EAP_METADATA_VERSION", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "KOTLIN_1_3_M1_METADATA_VERSION", "KOTLIN_1_3_RC_METADATA_VERSION", "getKOTLIN_1_3_RC_METADATA_VERSION$descriptors_jvm", "()Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "KOTLIN_CLASS", "", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind;", "getKOTLIN_CLASS$descriptors_jvm", "()Ljava/util/Set;", "KOTLIN_FILE_FACADE_OR_MULTIFILE_CLASS_PART", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Set<KotlinClassHeader.Kind> getKOTLIN_CLASS$descriptors_jvm() {
            return DeserializedDescriptorResolver.KOTLIN_CLASS;
        }

        public final JvmMetadataVersion getKOTLIN_1_3_RC_METADATA_VERSION$descriptors_jvm() {
            return DeserializedDescriptorResolver.KOTLIN_1_3_RC_METADATA_VERSION;
        }
    }
}
