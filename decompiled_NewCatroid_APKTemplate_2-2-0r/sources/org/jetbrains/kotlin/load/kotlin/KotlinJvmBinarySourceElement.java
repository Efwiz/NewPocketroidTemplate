package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.serialization.deserialization.IncompatibleVersionErrorData;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerAbiStability;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: KotlinJvmBinarySourceElement.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0014H\u0016R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinarySourceElement;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "binaryClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "incompatibility", "Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "isPreReleaseInvisible", "", "abiStability", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "(Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;ZLorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;)V", "getAbiStability", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "getBinaryClass", "()Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "getIncompatibility", "()Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "()Z", "presentableString", "", "getPresentableString", "()Ljava/lang/String;", "getContainingFile", "Lorg/jetbrains/kotlin/descriptors/SourceFile;", "toString", "deserialization.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinJvmBinarySourceElement implements DeserializedContainerSource {
    private final DeserializedContainerAbiStability abiStability;
    private final KotlinJvmBinaryClass binaryClass;
    private final IncompatibleVersionErrorData<JvmMetadataVersion> incompatibility;
    private final boolean isPreReleaseInvisible;

    public KotlinJvmBinarySourceElement(KotlinJvmBinaryClass binaryClass, IncompatibleVersionErrorData<JvmMetadataVersion> incompatibleVersionErrorData, boolean z, DeserializedContainerAbiStability abiStability) {
        Intrinsics.checkNotNullParameter(binaryClass, "binaryClass");
        Intrinsics.checkNotNullParameter(abiStability, "abiStability");
        this.binaryClass = binaryClass;
        this.incompatibility = incompatibleVersionErrorData;
        this.isPreReleaseInvisible = z;
        this.abiStability = abiStability;
    }

    public final KotlinJvmBinaryClass getBinaryClass() {
        return this.binaryClass;
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

    public /* synthetic */ KotlinJvmBinarySourceElement(KotlinJvmBinaryClass kotlinJvmBinaryClass, IncompatibleVersionErrorData incompatibleVersionErrorData, boolean z, DeserializedContainerAbiStability deserializedContainerAbiStability, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinJvmBinaryClass, (i & 2) != 0 ? null : incompatibleVersionErrorData, (i & 4) != 0 ? false : z, (i & 8) != 0 ? DeserializedContainerAbiStability.STABLE : deserializedContainerAbiStability);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
    public DeserializedContainerAbiStability getAbiStability() {
        return this.abiStability;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource
    public String getPresentableString() {
        return "Class '" + this.binaryClass.getClassId().asSingleFqName().asString() + '\'';
    }

    @Override // org.jetbrains.kotlin.descriptors.SourceElement
    public SourceFile getContainingFile() {
        SourceFile NO_SOURCE_FILE = SourceFile.NO_SOURCE_FILE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE_FILE, "NO_SOURCE_FILE");
        return NO_SOURCE_FILE;
    }

    public String toString() {
        return getClass().getSimpleName() + ": " + this.binaryClass;
    }
}
