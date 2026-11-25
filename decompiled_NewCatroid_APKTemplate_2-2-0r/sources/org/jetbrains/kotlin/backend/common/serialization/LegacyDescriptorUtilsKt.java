package org.jetbrains.kotlin.backend.common.serialization;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.library.metadata.DeserializedSourceFile;
import org.jetbrains.kotlin.library.metadata.KlibMetadataDeserializedPackageFragment;
import org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf;
import org.jetbrains.kotlin.library.metadata.KlibModuleOriginKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.multiplatform.OptionalAnnotationUtil;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassConstructorDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPropertyDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedSimpleFunctionDescriptor;

/* JADX INFO: compiled from: LegacyDescriptorUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002\u001a\f\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\u0002\u001a\r\u0010\u0010\u001a\u00020\u0011*\u00020\u0002H\u0086\u0010\u001a\n\u0010\u0012\u001a\u00020\t*\u00020\u000b\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006\"\u0018\u0010\u0007\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0003¨\u0006\u0013"}, d2 = {"isExpectMember", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Z", "isForwardDeclarationModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)Z", "isSerializableExpectClass", "sourceByIndex", "Lorg/jetbrains/kotlin/descriptors/SourceFile;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "index", "", "extractSerializedKdocString", "", "findPackage", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "findSourceFile", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LegacyDescriptorUtilsKt {
    public static final boolean isExpectMember(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return (declarationDescriptor instanceof MemberDescriptor) && ((MemberDescriptor) declarationDescriptor).get$isExpect();
    }

    public static final boolean isSerializableExpectClass(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return (declarationDescriptor instanceof ClassDescriptor) && OptionalAnnotationUtil.shouldGenerateExpectClass((ClassDescriptor) declarationDescriptor);
    }

    public static final PackageFragmentDescriptor findPackage(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        while (!(declarationDescriptor instanceof PackageFragmentDescriptor)) {
            declarationDescriptor = declarationDescriptor.getDeclarationDescriptor();
            Intrinsics.checkNotNull(declarationDescriptor);
        }
        return (PackageFragmentDescriptor) declarationDescriptor;
    }

    public static final boolean isForwardDeclarationModule(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        return Intrinsics.areEqual(moduleDescriptor.getShortName(), Name.special("<forward declarations>"));
    }

    private static final SourceFile sourceByIndex(CallableMemberDescriptor callableMemberDescriptor, int i) {
        CallableMemberDescriptor callableMemberDescriptor2 = callableMemberDescriptor;
        PackageFragmentDescriptor packageFragmentDescriptorFindPackage = findPackage(callableMemberDescriptor2);
        Intrinsics.checkNotNull(packageFragmentDescriptorFindPackage, "null cannot be cast to non-null type org.jetbrains.kotlin.library.metadata.KlibMetadataDeserializedPackageFragment");
        String str = ((KlibMetadataDeserializedPackageFragment) packageFragmentDescriptorFindPackage).getProto().getStrings().getStringList().get(i);
        Intrinsics.checkNotNull(str);
        return new DeserializedSourceFile(str, KlibModuleOriginKt.getKotlinLibrary(DescriptorUtilsKt.getModule(callableMemberDescriptor2)));
    }

    public static final SourceFile findSourceFile(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        SourceFile containingFile = callableMemberDescriptor.getSourceElement().getContainingFile();
        Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
        if (!Intrinsics.areEqual(containingFile, SourceFile.NO_SOURCE_FILE)) {
            return containingFile;
        }
        if (callableMemberDescriptor instanceof DeserializedSimpleFunctionDescriptor) {
            DeserializedSimpleFunctionDescriptor deserializedSimpleFunctionDescriptor = (DeserializedSimpleFunctionDescriptor) callableMemberDescriptor;
            if (deserializedSimpleFunctionDescriptor.getProto().hasExtension(KlibMetadataProtoBuf.functionFile)) {
                Object extension = deserializedSimpleFunctionDescriptor.getProto().getExtension(KlibMetadataProtoBuf.functionFile);
                Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
                return sourceByIndex(callableMemberDescriptor, ((Number) extension).intValue());
            }
        }
        if (callableMemberDescriptor instanceof DeserializedPropertyDescriptor) {
            DeserializedPropertyDescriptor deserializedPropertyDescriptor = (DeserializedPropertyDescriptor) callableMemberDescriptor;
            if (deserializedPropertyDescriptor.getProto().hasExtension(KlibMetadataProtoBuf.propertyFile)) {
                Object extension2 = deserializedPropertyDescriptor.getProto().getExtension(KlibMetadataProtoBuf.propertyFile);
                Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
                return sourceByIndex(callableMemberDescriptor, ((Number) extension2).intValue());
            }
        }
        throw new NotImplementedError(null, 1, null);
    }

    public static final String extractSerializedKdocString(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        if (declarationDescriptor instanceof DeserializedClassDescriptor) {
            return (String) ((DeserializedClassDescriptor) declarationDescriptor).getClassProto().getExtension(KlibMetadataProtoBuf.classKdoc);
        }
        if (declarationDescriptor instanceof DeserializedSimpleFunctionDescriptor) {
            return (String) ((DeserializedSimpleFunctionDescriptor) declarationDescriptor).getProto().getExtension(KlibMetadataProtoBuf.functionKdoc);
        }
        if (declarationDescriptor instanceof DeserializedPropertyDescriptor) {
            return (String) ((DeserializedPropertyDescriptor) declarationDescriptor).getProto().getExtension(KlibMetadataProtoBuf.propertyKdoc);
        }
        if (declarationDescriptor instanceof DeserializedClassConstructorDescriptor) {
            return (String) ((DeserializedClassConstructorDescriptor) declarationDescriptor).getProto().getExtension(KlibMetadataProtoBuf.constructorKdoc);
        }
        return null;
    }
}
