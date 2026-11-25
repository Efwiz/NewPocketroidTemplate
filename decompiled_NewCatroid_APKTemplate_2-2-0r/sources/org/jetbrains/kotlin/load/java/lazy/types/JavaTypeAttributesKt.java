package org.jetbrains.kotlin.load.java.lazy.types;

import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.TypeUsage;

/* JADX INFO: compiled from: JavaTypeAttributes.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a*\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00042\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¨\u0006\b"}, d2 = {"toAttributes", "Lorg/jetbrains/kotlin/load/java/lazy/types/JavaTypeAttributes;", "Lorg/jetbrains/kotlin/types/TypeUsage;", "isForAnnotationParameter", "", "isRaw", "upperBoundForTypeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "descriptors.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaTypeAttributesKt {
    public static /* synthetic */ JavaTypeAttributes toAttributes$default(TypeUsage typeUsage, boolean z, boolean z2, TypeParameterDescriptor typeParameterDescriptor, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = false;
        }
        if ((i & 4) != 0) {
            typeParameterDescriptor = null;
        }
        return toAttributes(typeUsage, z, z2, typeParameterDescriptor);
    }

    public static final JavaTypeAttributes toAttributes(TypeUsage typeUsage, boolean z, boolean z2, TypeParameterDescriptor typeParameterDescriptor) {
        Intrinsics.checkNotNullParameter(typeUsage, "<this>");
        return new JavaTypeAttributes(typeUsage, null, z2, z, typeParameterDescriptor != null ? SetsKt.setOf(typeParameterDescriptor) : null, null, 34, null);
    }
}
