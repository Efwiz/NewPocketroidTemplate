package org.jetbrains.kotlin.serialization.deserialization.descriptors;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: DeserializedAnnotations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/NonEmptyDeserializedAnnotations;", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedAnnotations;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "compute", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lkotlin/jvm/functions/Function0;)V", "isEmpty", "", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NonEmptyDeserializedAnnotations extends DeserializedAnnotations {
    @Override // org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedAnnotations, org.jetbrains.kotlin.descriptors.annotations.Annotations
    public boolean isEmpty() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NonEmptyDeserializedAnnotations(StorageManager storageManager, Function0<? extends List<? extends AnnotationDescriptor>> compute) {
        super(storageManager, compute);
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(compute, "compute");
    }
}
