package org.jetbrains.kotlin.descriptors.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FieldDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotatedImpl;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;

/* JADX INFO: compiled from: FieldDescriptorImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/FieldDescriptorImpl;", "Lorg/jetbrains/kotlin/descriptors/FieldDescriptor;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotatedImpl;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "correspondingProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;)V", "getCorrespondingProperty", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FieldDescriptorImpl extends AnnotatedImpl implements FieldDescriptor {
    private final PropertyDescriptor correspondingProperty;

    @Override // org.jetbrains.kotlin.descriptors.FieldDescriptor
    public PropertyDescriptor getCorrespondingProperty() {
        return this.correspondingProperty;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FieldDescriptorImpl(Annotations annotations, PropertyDescriptor correspondingProperty) {
        super(annotations);
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(correspondingProperty, "correspondingProperty");
        this.correspondingProperty = correspondingProperty;
    }
}
