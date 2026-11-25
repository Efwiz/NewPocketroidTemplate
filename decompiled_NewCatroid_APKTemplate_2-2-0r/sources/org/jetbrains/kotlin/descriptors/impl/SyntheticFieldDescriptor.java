package org.jetbrains.kotlin.descriptors.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: SyntheticFieldDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u001f\b\u0002\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\tJ\b\u0010\f\u001a\u0004\u0018\u00010\rJ\n\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/impl/SyntheticFieldDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/LocalVariableDescriptor;", "accessorDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;", "sourceElement", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "(Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;Lorg/jetbrains/kotlin/descriptors/SourceElement;)V", "propertyDescriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/descriptors/PropertyAccessorDescriptor;Lorg/jetbrains/kotlin/descriptors/SourceElement;)V", "getPropertyDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getDispatchReceiverForBackend", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "getDispatchReceiverParameter", "", "getDispatchReceiverParameterForBackend", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SyntheticFieldDescriptor extends LocalVariableDescriptor {
    public static final Name NAME;
    private final PropertyDescriptor propertyDescriptor;

    public Void getDispatchReceiverParameter() {
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.VariableDescriptorImpl, org.jetbrains.kotlin.descriptors.CallableDescriptor
    /* JADX INFO: renamed from: getDispatchReceiverParameter, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ ReceiverParameterDescriptor mo6753getDispatchReceiverParameter() {
        return (ReceiverParameterDescriptor) getDispatchReceiverParameter();
    }

    public final PropertyDescriptor getPropertyDescriptor() {
        return this.propertyDescriptor;
    }

    private SyntheticFieldDescriptor(PropertyDescriptor propertyDescriptor, PropertyAccessorDescriptor propertyAccessorDescriptor, SourceElement sourceElement) {
        super(propertyAccessorDescriptor, Annotations.INSTANCE.getEMPTY(), NAME, propertyDescriptor.getType(), propertyDescriptor.isVar(), false, false, sourceElement);
        this.propertyDescriptor = propertyDescriptor;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SyntheticFieldDescriptor(PropertyAccessorDescriptor accessorDescriptor, SourceElement sourceElement) {
        Intrinsics.checkNotNullParameter(accessorDescriptor, "accessorDescriptor");
        Intrinsics.checkNotNullParameter(sourceElement, "sourceElement");
        PropertyDescriptor correspondingProperty = accessorDescriptor.getCorrespondingProperty();
        Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
        this(correspondingProperty, accessorDescriptor, sourceElement);
    }

    public final ReceiverValue getDispatchReceiverForBackend() {
        ReceiverParameterDescriptor dispatchReceiverParameterForBackend = getDispatchReceiverParameterForBackend();
        if (dispatchReceiverParameterForBackend != null) {
            return dispatchReceiverParameterForBackend.getValue();
        }
        return null;
    }

    public final ReceiverParameterDescriptor getDispatchReceiverParameterForBackend() {
        return this.propertyDescriptor.mo6753getDispatchReceiverParameter();
    }

    static {
        Name nameIdentifier = Name.identifier("field");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        NAME = nameIdentifier;
    }
}
