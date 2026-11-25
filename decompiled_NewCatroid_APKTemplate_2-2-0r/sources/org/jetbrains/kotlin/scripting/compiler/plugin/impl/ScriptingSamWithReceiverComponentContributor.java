package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.extensions.AnnotationBasedExtension;
import org.jetbrains.kotlin.extensions.StorageComponentContainerContributor;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.jvm.JvmPlatformKt;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.resolve.sam.SamWithReceiverResolver;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: compilationContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001:\u0001\u0010B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ScriptingSamWithReceiverComponentContributor;", "Lorg/jetbrains/kotlin/extensions/StorageComponentContainerContributor;", "annotations", "", "", "(Ljava/util/List;)V", "getAnnotations", "()Ljava/util/List;", "registerModuleComponents", "", "container", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "Extension", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptingSamWithReceiverComponentContributor implements StorageComponentContainerContributor {
    private final List<String> annotations;

    public ScriptingSamWithReceiverComponentContributor(List<String> annotations) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        this.annotations = annotations;
    }

    public final List<String> getAnnotations() {
        return this.annotations;
    }

    /* JADX INFO: compiled from: compilationContext.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ScriptingSamWithReceiverComponentContributor$Extension;", "Lorg/jetbrains/kotlin/resolve/sam/SamWithReceiverResolver;", "Lorg/jetbrains/kotlin/extensions/AnnotationBasedExtension;", "annotations", "", "", "(Ljava/util/List;)V", "getAnnotationFqNames", "modifierListOwner", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "shouldConvertFirstSamParameterToReceiver", "", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Extension implements SamWithReceiverResolver, AnnotationBasedExtension {
        private final List<String> annotations;

        public Extension(List<String> annotations) {
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            this.annotations = annotations;
        }

        @Override // org.jetbrains.kotlin.extensions.AnnotationBasedExtension
        public List<String> getAnnotationFqNames(KtModifierListOwner modifierListOwner) {
            return this.annotations;
        }

        @Override // org.jetbrains.kotlin.resolve.sam.SamWithReceiverResolver
        public boolean shouldConvertFirstSamParameterToReceiver(FunctionDescriptor function) {
            Intrinsics.checkNotNullParameter(function, "function");
            DeclarationDescriptor containingDeclaration = function.getContainingDeclaration();
            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
            if (classDescriptor != null) {
                return hasSpecialAnnotation(classDescriptor, null);
            }
            return false;
        }
    }

    @Override // org.jetbrains.kotlin.extensions.StorageComponentContainerContributor
    public void registerModuleComponents(StorageComponentContainer container, TargetPlatform platform, ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(platform, "platform");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        if (JvmPlatformKt.isJvm(platform)) {
            DslKt.useInstance(container, new Extension(this.annotations));
        }
    }
}
