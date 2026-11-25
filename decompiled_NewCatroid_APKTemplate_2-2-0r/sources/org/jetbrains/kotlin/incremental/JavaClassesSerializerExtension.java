package org.jetbrains.kotlin.incremental;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.java.JavaClassProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.serialization.MutableVersionRequirementTable;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase;
import org.jetbrains.kotlin.serialization.SerializerExtension;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: JavaClassesSerializerExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J \u0010\u0015\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u00172\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J*\u0010\u0018\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00192\u0006\u0010\u000f\u001a\u00020\u001a2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J*\u0010\u001b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u001c2\u0006\u0010\u000f\u001a\u00020\u001d2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0012\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!*\u00020#H\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/incremental/JavaClassesSerializerExtension;", "Lorg/jetbrains/kotlin/serialization/KotlinSerializerExtensionBase;", "()V", "customClassMembersProducer", "Lorg/jetbrains/kotlin/serialization/SerializerExtension$ClassMembersProducer;", "getCustomClassMembersProducer", "()Lorg/jetbrains/kotlin/serialization/SerializerExtension$ClassMembersProducer;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "serializeClass", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Builder;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;", "childSerializer", "Lorg/jetbrains/kotlin/serialization/DescriptorSerializer;", "serializeConstructor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor$Builder;", "serializeFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;", "serializeProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;", "shouldUseNormalizedVisibility", "", "getSortedCallableDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassesSerializerExtension extends KotlinSerializerExtensionBase {
    private final SerializerExtension.ClassMembersProducer customClassMembersProducer;

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public boolean shouldUseNormalizedVisibility() {
        return true;
    }

    public JavaClassesSerializerExtension() {
        super(BuiltInSerializerProtocol.INSTANCE);
        this.customClassMembersProducer = new SerializerExtension.ClassMembersProducer() { // from class: org.jetbrains.kotlin.incremental.JavaClassesSerializerExtension$customClassMembersProducer$1
            @Override // org.jetbrains.kotlin.serialization.SerializerExtension.ClassMembersProducer
            public ArrayList<CallableMemberDescriptor> getCallableMembers(ClassDescriptor classDescriptor) {
                Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
                ArrayList<CallableMemberDescriptor> arrayList = new ArrayList<>();
                JavaClassesSerializerExtension javaClassesSerializerExtension = this.this$0;
                MemberScope unsubstitutedMemberScope = classDescriptor.getUnsubstitutedMemberScope();
                Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
                arrayList.addAll(javaClassesSerializerExtension.getSortedCallableDescriptors(unsubstitutedMemberScope));
                MemberScope staticScope = classDescriptor.getStaticScope();
                Intrinsics.checkNotNullExpressionValue(staticScope, "getStaticScope(...)");
                arrayList.addAll(javaClassesSerializerExtension.getSortedCallableDescriptors(staticScope));
                return arrayList;
            }
        };
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public BinaryVersion getMetadataVersion() {
        return JvmMetadataVersion.INVALID_VERSION;
    }

    @Override // org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase, org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeClass(ClassDescriptor descriptor, ProtoBuf.Class.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        super.serializeClass(descriptor, proto, versionRequirementTable, childSerializer);
        if (Intrinsics.areEqual(descriptor.getVisibilityImpl(), JavaDescriptorVisibilities.PACKAGE_VISIBILITY)) {
            proto.setExtension(JavaClassProtoBuf.isPackagePrivateClass, true);
        }
    }

    @Override // org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase, org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeConstructor(ConstructorDescriptor descriptor, ProtoBuf.Constructor.Builder proto, DescriptorSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        super.serializeConstructor(descriptor, proto, childSerializer);
        if (Intrinsics.areEqual(descriptor.getVisibilityImpl(), JavaDescriptorVisibilities.PACKAGE_VISIBILITY)) {
            proto.setExtension(JavaClassProtoBuf.isPackagePrivateConstructor, true);
        }
    }

    @Override // org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase, org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeFunction(FunctionDescriptor descriptor, ProtoBuf.Function.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        super.serializeFunction(descriptor, proto, versionRequirementTable, childSerializer);
        if (Intrinsics.areEqual(descriptor.getVisibilityImpl(), JavaDescriptorVisibilities.PACKAGE_VISIBILITY)) {
            proto.setExtension(JavaClassProtoBuf.isPackagePrivateMethod, true);
        }
        if (descriptor.mo6753getDispatchReceiverParameter() == null) {
            proto.setExtension(JavaClassProtoBuf.isStaticMethod, true);
        }
    }

    @Override // org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase, org.jetbrains.kotlin.serialization.SerializerExtension
    public void serializeProperty(PropertyDescriptor descriptor, ProtoBuf.Property.Builder proto, MutableVersionRequirementTable versionRequirementTable, DescriptorSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        super.serializeProperty(descriptor, proto, versionRequirementTable, childSerializer);
        if (Intrinsics.areEqual(descriptor.getVisibilityImpl(), JavaDescriptorVisibilities.PACKAGE_VISIBILITY)) {
            proto.setExtension(JavaClassProtoBuf.isPackagePrivateField, true);
        }
        if (descriptor.mo6753getDispatchReceiverParameter() == null) {
            proto.setExtension(JavaClassProtoBuf.isStaticField, true);
        }
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public SerializerExtension.ClassMembersProducer getCustomClassMembersProducer() {
        return this.customClassMembersProducer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<CallableMemberDescriptor> getSortedCallableDescriptors(MemberScope memberScope) {
        Collection<DeclarationDescriptor> allDescriptors = DescriptorUtils.getAllDescriptors(memberScope);
        Intrinsics.checkNotNullExpressionValue(allDescriptors, "getAllDescriptors(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : allDescriptors) {
            if (obj instanceof CallableMemberDescriptor) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (((CallableMemberDescriptor) obj2).getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                arrayList2.add(obj2);
            }
        }
        return DescriptorSerializer.INSTANCE.sort(arrayList2);
    }
}
