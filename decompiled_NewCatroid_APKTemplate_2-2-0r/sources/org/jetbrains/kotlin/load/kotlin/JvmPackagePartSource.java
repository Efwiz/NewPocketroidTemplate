package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoBufUtilKt;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.serialization.deserialization.IncompatibleVersionErrorData;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerAbiStability;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: JvmPackagePartSource.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002BE\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010BY\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0010\b\u0002\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0015J\b\u0010.\u001a\u00020/H\u0016J\b\u00100\u001a\u00020%H\u0016R\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u0011\u001a\u00020\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001dR\u001c\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010!R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010$\u001a\u00020%¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010'R\u0011\u0010*\u001a\u00020+8F¢\u0006\u0006\u001a\u0004\b,\u0010-¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/JvmPackagePartSource;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "Lorg/jetbrains/kotlin/load/kotlin/FacadeClassSource;", "kotlinClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "packageProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "incompatibility", "Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "isPreReleaseInvisible", "", "abiStability", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "(Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;ZLorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;)V", "className", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "facadeClassName", "knownJvmBinaryClass", "(Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;ZLorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;)V", "getAbiStability", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getClassName", "()Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "getFacadeClassName", "getIncompatibility", "()Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "()Z", "getKnownJvmBinaryClass", "()Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "moduleName", "", "getModuleName", "()Ljava/lang/String;", "presentableString", "getPresentableString", Namer.METADATA_SIMPLE_NAME, "Lorg/jetbrains/kotlin/name/Name;", "getSimpleName", "()Lorg/jetbrains/kotlin/name/Name;", "getContainingFile", "Lorg/jetbrains/kotlin/descriptors/SourceFile;", "toString", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmPackagePartSource implements FacadeClassSource, DeserializedContainerSource {
    private final DeserializedContainerAbiStability abiStability;
    private final JvmClassName className;
    private final JvmClassName facadeClassName;
    private final IncompatibleVersionErrorData<JvmMetadataVersion> incompatibility;
    private final boolean isPreReleaseInvisible;
    private final KotlinJvmBinaryClass knownJvmBinaryClass;
    private final String moduleName;

    public JvmPackagePartSource(JvmClassName className, JvmClassName jvmClassName, ProtoBuf.Package packageProto, NameResolver nameResolver, IncompatibleVersionErrorData<JvmMetadataVersion> incompatibleVersionErrorData, boolean z, DeserializedContainerAbiStability abiStability, KotlinJvmBinaryClass kotlinJvmBinaryClass) {
        String string;
        Intrinsics.checkNotNullParameter(className, "className");
        Intrinsics.checkNotNullParameter(packageProto, "packageProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(abiStability, "abiStability");
        this.className = className;
        this.facadeClassName = jvmClassName;
        this.incompatibility = incompatibleVersionErrorData;
        this.isPreReleaseInvisible = z;
        this.abiStability = abiStability;
        this.knownJvmBinaryClass = kotlinJvmBinaryClass;
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> packageModuleName = JvmProtoBuf.packageModuleName;
        Intrinsics.checkNotNullExpressionValue(packageModuleName, "packageModuleName");
        Integer num = (Integer) ProtoBufUtilKt.getExtensionOrNull(packageProto, packageModuleName);
        this.moduleName = (num == null || (string = nameResolver.getString(num.intValue())) == null) ? "main" : string;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.FacadeClassSource
    public JvmClassName getClassName() {
        return this.className;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.FacadeClassSource
    public JvmClassName getFacadeClassName() {
        return this.facadeClassName;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
    /* JADX INFO: renamed from: getIncompatibility */
    public IncompatibleVersionErrorData<JvmMetadataVersion> mo6784getIncompatibility() {
        return this.incompatibility;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
    /* JADX INFO: renamed from: isPreReleaseInvisible, reason: from getter */
    public boolean getIsPreReleaseInvisible() {
        return this.isPreReleaseInvisible;
    }

    public /* synthetic */ JvmPackagePartSource(JvmClassName jvmClassName, JvmClassName jvmClassName2, ProtoBuf.Package r15, NameResolver nameResolver, IncompatibleVersionErrorData incompatibleVersionErrorData, boolean z, DeserializedContainerAbiStability deserializedContainerAbiStability, KotlinJvmBinaryClass kotlinJvmBinaryClass, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(jvmClassName, jvmClassName2, r15, nameResolver, (IncompatibleVersionErrorData<JvmMetadataVersion>) ((i & 16) != 0 ? null : incompatibleVersionErrorData), (i & 32) != 0 ? false : z, (i & 64) != 0 ? DeserializedContainerAbiStability.STABLE : deserializedContainerAbiStability, (i & 128) != 0 ? null : kotlinJvmBinaryClass);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
    public DeserializedContainerAbiStability getAbiStability() {
        return this.abiStability;
    }

    public final KotlinJvmBinaryClass getKnownJvmBinaryClass() {
        return this.knownJvmBinaryClass;
    }

    public /* synthetic */ JvmPackagePartSource(KotlinJvmBinaryClass kotlinJvmBinaryClass, ProtoBuf.Package r9, NameResolver nameResolver, IncompatibleVersionErrorData incompatibleVersionErrorData, boolean z, DeserializedContainerAbiStability deserializedContainerAbiStability, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinJvmBinaryClass, r9, nameResolver, (i & 8) != 0 ? null : incompatibleVersionErrorData, (i & 16) != 0 ? false : z, (i & 32) != 0 ? DeserializedContainerAbiStability.STABLE : deserializedContainerAbiStability);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public JvmPackagePartSource(KotlinJvmBinaryClass kotlinClass, ProtoBuf.Package packageProto, NameResolver nameResolver, IncompatibleVersionErrorData<JvmMetadataVersion> incompatibleVersionErrorData, boolean z, DeserializedContainerAbiStability abiStability) {
        Intrinsics.checkNotNullParameter(kotlinClass, "kotlinClass");
        Intrinsics.checkNotNullParameter(packageProto, "packageProto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(abiStability, "abiStability");
        JvmClassName jvmClassNameByClassId = JvmClassName.byClassId(kotlinClass.getClassId());
        Intrinsics.checkNotNullExpressionValue(jvmClassNameByClassId, "byClassId(...)");
        String multifileClassName = kotlinClass.getClassHeader().getMultifileClassName();
        JvmClassName jvmClassNameByInternalName = null;
        if (multifileClassName != null && multifileClassName.length() > 0) {
            jvmClassNameByInternalName = JvmClassName.byInternalName(multifileClassName);
        }
        this(jvmClassNameByClassId, jvmClassNameByInternalName, packageProto, nameResolver, incompatibleVersionErrorData, z, abiStability, kotlinClass);
    }

    public final String getModuleName() {
        return this.moduleName;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
    public String getPresentableString() {
        return "Class '" + getClassId().asSingleFqName().asString() + '\'';
    }

    public final Name getSimpleName() {
        String internalName = getClassName().getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        Name nameIdentifier = Name.identifier(StringsKt.substringAfterLast$default(internalName, '/', (String) null, 2, (Object) null));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    public final ClassId getClassId() {
        return new ClassId(getClassName().getPackageFqName(), getSimpleName());
    }

    public String toString() {
        return getClass().getSimpleName() + ": " + getClassName();
    }

    @Override // org.jetbrains.kotlin.descriptors.SourceElement
    public SourceFile getContainingFile() {
        SourceFile NO_SOURCE_FILE = SourceFile.NO_SOURCE_FILE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE_FILE, "NO_SOURCE_FILE");
        return NO_SOURCE_FILE;
    }
}
