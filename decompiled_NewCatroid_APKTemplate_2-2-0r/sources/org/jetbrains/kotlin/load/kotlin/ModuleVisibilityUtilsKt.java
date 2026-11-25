package org.jetbrains.kotlin.load.kotlin;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaPackageFragment;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedTypeAliasDescriptor;

/* JADX INFO: compiled from: moduleVisibilityUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0002\u001a\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0006\u001a\u00020\u00022\b\u0010\t\u001a\u0004\u0018\u00010\n\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u000b"}, d2 = {"toSourceElement", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getToSourceElement", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getSourceElement", "descriptor", "isContainedByCompiledPartOfOurModule", "", "friendPath", "Ljava/io/File;", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ModuleVisibilityUtilsKt {
    public static final boolean isContainedByCompiledPartOfOurModule(DeclarationDescriptor descriptor, File file) {
        KotlinJvmBinaryClass representativeBinaryClass;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (file == null || !(((PackageFragmentDescriptor) DescriptorUtils.getParentOfType(descriptor, PackageFragmentDescriptor.class, false)) instanceof LazyJavaPackageFragment)) {
            return false;
        }
        SourceElement sourceElement = getSourceElement(descriptor);
        if (sourceElement instanceof KotlinJvmBinarySourceElement) {
            representativeBinaryClass = ((KotlinJvmBinarySourceElement) sourceElement).getBinaryClass();
        } else if (!(sourceElement instanceof KotlinJvmBinaryPackageSourceElement)) {
            representativeBinaryClass = null;
        } else if (descriptor instanceof DeserializedMemberDescriptor) {
            KotlinJvmBinaryPackageSourceElement kotlinJvmBinaryPackageSourceElement = (KotlinJvmBinaryPackageSourceElement) sourceElement;
            representativeBinaryClass = kotlinJvmBinaryPackageSourceElement.getContainingBinaryClass((DeserializedMemberDescriptor) descriptor);
            if (representativeBinaryClass == null) {
                representativeBinaryClass = kotlinJvmBinaryPackageSourceElement.getRepresentativeBinaryClass();
            }
        } else {
            representativeBinaryClass = ((KotlinJvmBinaryPackageSourceElement) sourceElement).getRepresentativeBinaryClass();
        }
        if (!(representativeBinaryClass instanceof VirtualFileKotlinClass)) {
            return false;
        }
        VirtualFile file2 = ((VirtualFileKotlinClass) representativeBinaryClass).getFile();
        String protocol = file2.getFileSystem().getProtocol();
        int iHashCode = protocol.hashCode();
        if (iHashCode == 104987) {
            if (!protocol.equals("jar")) {
                return false;
            }
            VirtualFile virtualFileForJar = VfsUtilCore.getVirtualFileForJar(file2);
            File fileVirtualToIoFile = virtualFileForJar != null ? VfsUtilCore.virtualToIoFile(virtualFileForJar) : null;
            return fileVirtualToIoFile != null && Intrinsics.areEqual(fileVirtualToIoFile.toPath(), file.toPath());
        }
        if (iHashCode != 3143036 || !protocol.equals("file")) {
            return false;
        }
        File fileVirtualToIoFile2 = VfsUtilCore.virtualToIoFile(file2);
        Intrinsics.checkNotNullExpressionValue(fileVirtualToIoFile2, "virtualToIoFile(...)");
        return fileVirtualToIoFile2.toPath().startsWith(file.toPath());
    }

    public static final SourceElement getSourceElement(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor instanceof CallableMemberDescriptor) {
            CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) descriptor;
            if (callableMemberDescriptor.getSource() == SourceElement.NO_SOURCE) {
                DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                return getSourceElement(containingDeclaration);
            }
        }
        if (descriptor instanceof DeserializedTypeAliasDescriptor) {
            DeclarationDescriptor containingDeclaration2 = ((DeserializedTypeAliasDescriptor) descriptor).getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration2, "getContainingDeclaration(...)");
            return getSourceElement(containingDeclaration2);
        }
        return getToSourceElement(descriptor);
    }

    public static final SourceElement getToSourceElement(DeclarationDescriptor declarationDescriptor) {
        SourceElement source;
        String str;
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        if (declarationDescriptor instanceof DeclarationDescriptorWithSource) {
            source = ((DeclarationDescriptorWithSource) declarationDescriptor).getSource();
            str = "getSource(...)";
        } else {
            source = SourceElement.NO_SOURCE;
            str = "NO_SOURCE";
        }
        Intrinsics.checkNotNullExpressionValue(source, str);
        return source;
    }
}
