package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.load.java.descriptors.UtilKt;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaPackageFragment;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor;

/* JADX INFO: compiled from: KotlinJvmBinaryPackageSourceElement.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bJ\b\u0010\t\u001a\u00020\nH\u0016J\u0006\u0010\u000b\u001a\u00020\u0006J\b\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryPackageSourceElement;", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "packageFragment", "Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageFragment;", "(Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaPackageFragment;)V", "getContainingBinaryClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "descriptor", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedMemberDescriptor;", "getContainingFile", "Lorg/jetbrains/kotlin/descriptors/SourceFile;", "getRepresentativeBinaryClass", "toString", "", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinJvmBinaryPackageSourceElement implements SourceElement {
    private final LazyJavaPackageFragment packageFragment;

    public KotlinJvmBinaryPackageSourceElement(LazyJavaPackageFragment packageFragment) {
        Intrinsics.checkNotNullParameter(packageFragment, "packageFragment");
        this.packageFragment = packageFragment;
    }

    public String toString() {
        return this.packageFragment + ": " + this.packageFragment.getBinaryClasses$descriptors_jvm().keySet();
    }

    @Override // org.jetbrains.kotlin.descriptors.SourceElement
    public SourceFile getContainingFile() {
        SourceFile NO_SOURCE_FILE = SourceFile.NO_SOURCE_FILE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE_FILE, "NO_SOURCE_FILE");
        return NO_SOURCE_FILE;
    }

    public final KotlinJvmBinaryClass getRepresentativeBinaryClass() {
        return (KotlinJvmBinaryClass) CollectionsKt.first(this.packageFragment.getBinaryClasses$descriptors_jvm().values());
    }

    public final KotlinJvmBinaryClass getContainingBinaryClass(DeserializedMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        JvmClassName implClassNameForDeserialized = UtilKt.getImplClassNameForDeserialized(descriptor);
        if (implClassNameForDeserialized == null) {
            return null;
        }
        return this.packageFragment.getBinaryClasses$descriptors_jvm().get(implClassNameForDeserialized.getInternalName());
    }
}
