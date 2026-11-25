package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassOrPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoBufUtilKt;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor;

/* JADX INFO: compiled from: moduleName.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"getJvmModuleNameForDeserializedDescriptor", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ModuleNameKt {
    public static final String getJvmModuleNameForDeserializedDescriptor(DeclarationDescriptor descriptor) {
        String string;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ClassOrPackageFragmentDescriptor classOrPackageFragmentDescriptor = (ClassOrPackageFragmentDescriptor) DescriptorUtils.getParentOfType(descriptor, ClassOrPackageFragmentDescriptor.class, false);
        if (classOrPackageFragmentDescriptor instanceof DeserializedClassDescriptor) {
            DeserializedClassDescriptor deserializedClassDescriptor = (DeserializedClassDescriptor) classOrPackageFragmentDescriptor;
            ProtoBuf.Class classProto = deserializedClassDescriptor.getClassProto();
            NameResolver nameResolver = deserializedClassDescriptor.getC().getNameResolver();
            GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, Integer> classModuleName = JvmProtoBuf.classModuleName;
            Intrinsics.checkNotNullExpressionValue(classModuleName, "classModuleName");
            Integer num = (Integer) ProtoBufUtilKt.getExtensionOrNull(classProto, classModuleName);
            return (num == null || (string = nameResolver.getString(num.intValue())) == null) ? "main" : string;
        }
        if (!(descriptor instanceof DeserializedMemberDescriptor)) {
            return null;
        }
        DeserializedContainerSource containerSource = ((DeserializedMemberDescriptor) descriptor).getContainerSource();
        if (containerSource instanceof JvmPackagePartSource) {
            return ((JvmPackagePartSource) containerSource).getModuleName();
        }
        return null;
    }
}
