package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement;

/* JADX INFO: compiled from: resolveUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¨\u0006\u0005"}, d2 = {"hasBackingField", "", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ResolveUtilKt {
    public static final boolean hasBackingField(PropertyDescriptor propertyDescriptor, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
        if (propertyDescriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
            if (propertyDescriptor.getExtensionReceiverParameter() == null) {
                if ((propertyDescriptor.getSourceElement() instanceof KotlinSourceElement) && bindingContext != null) {
                    Boolean bool = (Boolean) bindingContext.get(BindingContext.BACKING_FIELD_REQUIRED, propertyDescriptor);
                    if (bool == null) {
                        bool = false;
                    }
                    return bool.booleanValue();
                }
                if (propertyDescriptor.mo6754getCompileTimeInitializer() != null || propertyDescriptor.getGetter() == null) {
                    return true;
                }
            }
        } else {
            Collection<? extends PropertyDescriptor> overriddenDescriptors = propertyDescriptor.getOverriddenDescriptors();
            Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
            Collection<? extends PropertyDescriptor> collection = overriddenDescriptors;
            if (!collection.isEmpty()) {
                for (PropertyDescriptor propertyDescriptor2 : collection) {
                    Intrinsics.checkNotNull(propertyDescriptor2);
                    if (hasBackingField(propertyDescriptor2, bindingContext)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
