package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: KotlinClassFinder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u001c\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\t"}, d2 = {"findKotlinClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "javaClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "deserialization.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinClassFinderKt {
    public static final KotlinJvmBinaryClass findKotlinClass(KotlinClassFinder kotlinClassFinder, ClassId classId, JvmMetadataVersion jvmMetadataVersion) {
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(jvmMetadataVersion, "jvmMetadataVersion");
        KotlinClassFinder.Result resultFindKotlinClassOrContent = kotlinClassFinder.findKotlinClassOrContent(classId, jvmMetadataVersion);
        if (resultFindKotlinClassOrContent != null) {
            return resultFindKotlinClassOrContent.toKotlinJvmBinaryClass();
        }
        return null;
    }

    public static final KotlinJvmBinaryClass findKotlinClass(KotlinClassFinder kotlinClassFinder, JavaClass javaClass, JvmMetadataVersion jvmMetadataVersion) {
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "<this>");
        Intrinsics.checkNotNullParameter(javaClass, "javaClass");
        Intrinsics.checkNotNullParameter(jvmMetadataVersion, "jvmMetadataVersion");
        KotlinClassFinder.Result resultFindKotlinClassOrContent = kotlinClassFinder.findKotlinClassOrContent(javaClass, jvmMetadataVersion);
        if (resultFindKotlinClassOrContent != null) {
            return resultFindKotlinClassOrContent.toKotlinJvmBinaryClass();
        }
        return null;
    }
}
