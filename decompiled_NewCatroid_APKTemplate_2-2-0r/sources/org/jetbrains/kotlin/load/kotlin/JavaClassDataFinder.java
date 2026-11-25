package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.serialization.deserialization.ClassData;
import org.jetbrains.kotlin.serialization.deserialization.ClassDataFinder;
import org.jetbrains.kotlin.utils.DeserializationHelpersKt;

/* JADX INFO: compiled from: JavaClassDataFinder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/JavaClassDataFinder;", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassDataFinder;", "kotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "deserializedDescriptorResolver", "Lorg/jetbrains/kotlin/load/kotlin/DeserializedDescriptorResolver;", "(Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;Lorg/jetbrains/kotlin/load/kotlin/DeserializedDescriptorResolver;)V", "getKotlinClassFinder$descriptors_jvm", "()Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "findClassData", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassDataFinder implements ClassDataFinder {
    private final DeserializedDescriptorResolver deserializedDescriptorResolver;
    private final KotlinClassFinder kotlinClassFinder;

    public JavaClassDataFinder(KotlinClassFinder kotlinClassFinder, DeserializedDescriptorResolver deserializedDescriptorResolver) {
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
        Intrinsics.checkNotNullParameter(deserializedDescriptorResolver, "deserializedDescriptorResolver");
        this.kotlinClassFinder = kotlinClassFinder;
        this.deserializedDescriptorResolver = deserializedDescriptorResolver;
    }

    /* JADX INFO: renamed from: getKotlinClassFinder$descriptors_jvm, reason: from getter */
    public final KotlinClassFinder getKotlinClassFinder() {
        return this.kotlinClassFinder;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ClassDataFinder
    public ClassData findClassData(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        KotlinJvmBinaryClass kotlinJvmBinaryClassFindKotlinClass = KotlinClassFinderKt.findKotlinClass(this.kotlinClassFinder, classId, DeserializationHelpersKt.jvmMetadataVersionOrDefault(this.deserializedDescriptorResolver.getComponents().getConfiguration()));
        if (kotlinJvmBinaryClassFindKotlinClass == null) {
            return null;
        }
        Intrinsics.areEqual(kotlinJvmBinaryClassFindKotlinClass.getClassId(), classId);
        return this.deserializedDescriptorResolver.readClassData$descriptors_jvm(kotlinJvmBinaryClassFindKotlinClass);
    }
}
