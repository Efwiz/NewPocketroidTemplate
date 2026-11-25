package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.PropertyDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PropertyGetterDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.PropertySetterDescriptorImpl;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorFactory;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: AccessorForPropertyDescriptor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0016\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0002&'BA\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010BM\b\u0014\u0012\u0006\u0010\u0011\u001a\u00020\u0003\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0013\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0017B]\b\u0002\u0012\u0006\u0010\u0018\u001a\u00020\u0003\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0013\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\u0019\u001a\u00020\n\u0012\u0006\u0010\u001a\u001a\u00020\f\u0012\u0006\u0010\u001b\u001a\u00020\f\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u001cR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0019\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010\u0018\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u001a\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010#R\u0011\u0010\u001b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010#R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/codegen/AccessorForPropertyDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/PropertyDescriptorImpl;", "Lorg/jetbrains/kotlin/codegen/AccessorForCallableDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "property", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "superCallTarget", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "nameSuffix", "", "getterAccessorRequired", "", "setterAccessorRequired", "accessorKind", "Lorg/jetbrains/kotlin/codegen/AccessorKind;", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Ljava/lang/String;ZZLorg/jetbrains/kotlin/codegen/AccessorKind;)V", "original", "propertyType", "Lorg/jetbrains/kotlin/types/KotlinType;", "receiverType", "dispatchReceiverParameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Ljava/lang/String;Lorg/jetbrains/kotlin/codegen/AccessorKind;)V", "calleeDescriptor", "accessorSuffix", "isWithSyntheticGetterAccessor", "isWithSyntheticSetterAccessor", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Ljava/lang/String;ZZLorg/jetbrains/kotlin/codegen/AccessorKind;)V", "getAccessorKind", "()Lorg/jetbrains/kotlin/codegen/AccessorKind;", "getAccessorSuffix", "()Ljava/lang/String;", "getCalleeDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "()Z", "getSuperCallTarget", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Getter", "Setter", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class AccessorForPropertyDescriptor extends PropertyDescriptorImpl implements AccessorForCallableDescriptor<PropertyDescriptor> {
    private final AccessorKind accessorKind;
    private final String accessorSuffix;
    private final PropertyDescriptor calleeDescriptor;
    private final boolean isWithSyntheticGetterAccessor;
    private final boolean isWithSyntheticSetterAccessor;
    private final ClassDescriptor superCallTarget;

    @Override // org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor
    public PropertyDescriptor getCalleeDescriptor() {
        return this.calleeDescriptor;
    }

    @Override // org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor
    public ClassDescriptor getSuperCallTarget() {
        return this.superCallTarget;
    }

    public final String getAccessorSuffix() {
        return this.accessorSuffix;
    }

    /* JADX INFO: renamed from: isWithSyntheticGetterAccessor, reason: from getter */
    public final boolean getIsWithSyntheticGetterAccessor() {
        return this.isWithSyntheticGetterAccessor;
    }

    /* JADX INFO: renamed from: isWithSyntheticSetterAccessor, reason: from getter */
    public final boolean getIsWithSyntheticSetterAccessor() {
        return this.isWithSyntheticSetterAccessor;
    }

    @Override // org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor
    public final AccessorKind getAccessorKind() {
        return this.accessorKind;
    }

    private AccessorForPropertyDescriptor(PropertyDescriptor propertyDescriptor, KotlinType kotlinType, KotlinType kotlinType2, ReceiverParameterDescriptor receiverParameterDescriptor, DeclarationDescriptor declarationDescriptor, ClassDescriptor classDescriptor, String str, boolean z, boolean z2, AccessorKind accessorKind) {
        super(declarationDescriptor, null, Annotations.INSTANCE.getEMPTY(), Modality.FINAL, DescriptorVisibilities.LOCAL, propertyDescriptor.isVar(), Name.identifier("access$" + str), CallableMemberDescriptor.Kind.DECLARATION, SourceElement.NO_SOURCE, false, false, false, false, false, false);
        this.calleeDescriptor = propertyDescriptor;
        this.superCallTarget = classDescriptor;
        this.accessorSuffix = str;
        this.isWithSyntheticGetterAccessor = z;
        this.isWithSyntheticSetterAccessor = z2;
        this.accessorKind = accessorKind;
        setType(kotlinType, CollectionsKt.emptyList(), receiverParameterDescriptor, DescriptorFactory.createExtensionReceiverParameterForCallable(this, kotlinType2, Annotations.INSTANCE.getEMPTY()), getOriginal().getContextReceiverParameters());
        initialize((PropertyGetterDescriptorImpl) (z ? new Getter(this, accessorKind) : getCalleeDescriptor().getGetter()), z2 ? new Setter(this, accessorKind) : getCalleeDescriptor().getSetter());
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AccessorForPropertyDescriptor(PropertyDescriptor property, DeclarationDescriptor containingDeclaration, ClassDescriptor classDescriptor, String nameSuffix, boolean z, boolean z2, AccessorKind accessorKind) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(nameSuffix, "nameSuffix");
        Intrinsics.checkNotNullParameter(accessorKind, "accessorKind");
        KotlinType type = property.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        this(property, type, DescriptorUtils.getReceiverParameterType(property.getExtensionReceiverParameter()), CodegenUtilKt.isJvmStaticInObjectOrClassOrInterface(property) ? null : property.mo6753getDispatchReceiverParameter(), containingDeclaration, classDescriptor, nameSuffix, z, z2, accessorKind);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    protected AccessorForPropertyDescriptor(PropertyDescriptor original, KotlinType propertyType, KotlinType kotlinType, ReceiverParameterDescriptor receiverParameterDescriptor, DeclarationDescriptor containingDeclaration, ClassDescriptor classDescriptor, String nameSuffix, AccessorKind accessorKind) {
        this(original, propertyType, kotlinType, receiverParameterDescriptor, containingDeclaration, classDescriptor, nameSuffix, true, true, accessorKind);
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(propertyType, "propertyType");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(nameSuffix, "nameSuffix");
        Intrinsics.checkNotNullParameter(accessorKind, "accessorKind");
    }

    /* JADX INFO: compiled from: AccessorForPropertyDescriptor.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/AccessorForPropertyDescriptor$Getter;", "Lorg/jetbrains/kotlin/descriptors/impl/PropertyGetterDescriptorImpl;", "Lorg/jetbrains/kotlin/codegen/AccessorForCallableDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "property", "Lorg/jetbrains/kotlin/codegen/AccessorForPropertyDescriptor;", "accessorKind", "Lorg/jetbrains/kotlin/codegen/AccessorKind;", "(Lorg/jetbrains/kotlin/codegen/AccessorForPropertyDescriptor;Lorg/jetbrains/kotlin/codegen/AccessorKind;)V", "getAccessorKind", "()Lorg/jetbrains/kotlin/codegen/AccessorKind;", "calleeDescriptor", "getCalleeDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "superCallTarget", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getSuperCallTarget", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Getter extends PropertyGetterDescriptorImpl implements AccessorForCallableDescriptor<PropertyGetterDescriptor> {
        private final AccessorKind accessorKind;

        @Override // org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor
        public AccessorKind getAccessorKind() {
            return this.accessorKind;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Getter(AccessorForPropertyDescriptor property, AccessorKind accessorKind) {
            super(property, Annotations.INSTANCE.getEMPTY(), Modality.FINAL, DescriptorVisibilities.LOCAL, false, false, false, CallableMemberDescriptor.Kind.DECLARATION, null, SourceElement.NO_SOURCE);
            Intrinsics.checkNotNullParameter(property, "property");
            Intrinsics.checkNotNullParameter(accessorKind, "accessorKind");
            this.accessorKind = accessorKind;
            initialize(property.getType());
        }

        @Override // org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor
        public PropertyGetterDescriptor getCalleeDescriptor() {
            PropertyDescriptor correspondingProperty = getCorrespondingProperty();
            Intrinsics.checkNotNull(correspondingProperty, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.AccessorForPropertyDescriptor");
            PropertyGetterDescriptor getter = ((AccessorForPropertyDescriptor) correspondingProperty).getCalleeDescriptor().getGetter();
            Intrinsics.checkNotNull(getter);
            return getter;
        }

        @Override // org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor
        public ClassDescriptor getSuperCallTarget() {
            PropertyDescriptor correspondingProperty = getCorrespondingProperty();
            Intrinsics.checkNotNull(correspondingProperty, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.AccessorForPropertyDescriptor");
            return ((AccessorForPropertyDescriptor) correspondingProperty).getSuperCallTarget();
        }
    }

    /* JADX INFO: compiled from: AccessorForPropertyDescriptor.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/AccessorForPropertyDescriptor$Setter;", "Lorg/jetbrains/kotlin/descriptors/impl/PropertySetterDescriptorImpl;", "Lorg/jetbrains/kotlin/codegen/AccessorForCallableDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "property", "Lorg/jetbrains/kotlin/codegen/AccessorForPropertyDescriptor;", "accessorKind", "Lorg/jetbrains/kotlin/codegen/AccessorKind;", "(Lorg/jetbrains/kotlin/codegen/AccessorForPropertyDescriptor;Lorg/jetbrains/kotlin/codegen/AccessorKind;)V", "getAccessorKind", "()Lorg/jetbrains/kotlin/codegen/AccessorKind;", "calleeDescriptor", "getCalleeDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "superCallTarget", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getSuperCallTarget", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Setter extends PropertySetterDescriptorImpl implements AccessorForCallableDescriptor<PropertySetterDescriptor> {
        private final AccessorKind accessorKind;

        @Override // org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor
        public AccessorKind getAccessorKind() {
            return this.accessorKind;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Setter(AccessorForPropertyDescriptor property, AccessorKind accessorKind) {
            super(property, Annotations.INSTANCE.getEMPTY(), Modality.FINAL, DescriptorVisibilities.LOCAL, false, false, false, CallableMemberDescriptor.Kind.DECLARATION, null, SourceElement.NO_SOURCE);
            Intrinsics.checkNotNullParameter(property, "property");
            Intrinsics.checkNotNullParameter(accessorKind, "accessorKind");
            this.accessorKind = accessorKind;
            initializeDefault();
        }

        @Override // org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor
        public PropertySetterDescriptor getCalleeDescriptor() {
            PropertyDescriptor correspondingProperty = getCorrespondingProperty();
            Intrinsics.checkNotNull(correspondingProperty, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.AccessorForPropertyDescriptor");
            PropertySetterDescriptor setter = ((AccessorForPropertyDescriptor) correspondingProperty).getCalleeDescriptor().getSetter();
            Intrinsics.checkNotNull(setter);
            return setter;
        }

        @Override // org.jetbrains.kotlin.codegen.AccessorForCallableDescriptor
        public ClassDescriptor getSuperCallTarget() {
            PropertyDescriptor correspondingProperty = getCorrespondingProperty();
            Intrinsics.checkNotNull(correspondingProperty, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.AccessorForPropertyDescriptor");
            return ((AccessorForPropertyDescriptor) correspondingProperty).getSuperCallTarget();
        }
    }
}
