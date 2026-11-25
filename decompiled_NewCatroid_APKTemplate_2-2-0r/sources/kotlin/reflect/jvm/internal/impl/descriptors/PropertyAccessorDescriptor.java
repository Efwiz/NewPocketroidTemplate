package kotlin.reflect.jvm.internal.impl.descriptors;

/* JADX INFO: loaded from: classes12.dex */
public interface PropertyAccessorDescriptor extends VariableAccessorDescriptor {
    PropertyDescriptor getCorrespondingProperty();

    boolean isDefault();
}
