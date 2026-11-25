package org.jetbrains.kotlin.serialization.deserialization.descriptors;

import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationWithTarget;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: DeserializedAnnotations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0000\b\u0016\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0005¢\u0006\u0002\u0010\bJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0011H\u0096\u0002R!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedAnnotations;", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "compute", "Lkotlin/Function0;", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lkotlin/jvm/functions/Function0;)V", "annotations", "getAnnotations", "()Ljava/util/List;", "annotations$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "isEmpty", "", "iterator", "", "deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DeserializedAnnotations implements Annotations {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DeserializedAnnotations.class), "annotations", "getAnnotations()Ljava/util/List;"))};

    /* JADX INFO: renamed from: annotations$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue annotations;

    public DeserializedAnnotations(StorageManager storageManager, Function0<? extends List<? extends AnnotationDescriptor>> compute) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(compute, "compute");
        this.annotations = storageManager.createLazyValue(compute);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    /* JADX INFO: renamed from: findAnnotation */
    public AnnotationDescriptor mo6752findAnnotation(FqName fqName) {
        return Annotations.DefaultImpls.findAnnotation(this, fqName);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    @Deprecated(message = "This method should only be used in frontend where we split annotations according to their use-site targets.")
    public List<AnnotationWithTarget> getUseSiteTargetedAnnotations() {
        return Annotations.DefaultImpls.getUseSiteTargetedAnnotations(this);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    public boolean hasAnnotation(FqName fqName) {
        return Annotations.DefaultImpls.hasAnnotation(this, fqName);
    }

    private final List<AnnotationDescriptor> getAnnotations() {
        return (List) StorageKt.getValue(this.annotations, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.Annotations
    public boolean isEmpty() {
        return getAnnotations().isEmpty();
    }

    @Override // java.lang.Iterable
    public Iterator<AnnotationDescriptor> iterator() {
        return getAnnotations().iterator();
    }
}
