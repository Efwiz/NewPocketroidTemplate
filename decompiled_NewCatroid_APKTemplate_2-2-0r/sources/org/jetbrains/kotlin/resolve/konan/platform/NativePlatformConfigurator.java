package org.jetbrains.kotlin.resolve.konan.platform;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.ContainerKt;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.resolve.PlatformConfiguratorBase;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.LateinitIntrinsicApplicabilityChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker;
import org.jetbrains.kotlin.resolve.jvm.checkers.SuperCallWithDefaultArgumentsChecker;
import org.jetbrains.kotlin.resolve.konan.diagnostics.NativeForwardDeclarationRttiChecker;
import org.jetbrains.kotlin.resolve.konan.diagnostics.NativeHiddenFromObjCInheritanceChecker;
import org.jetbrains.kotlin.resolve.konan.diagnostics.NativeIdentifierChecker;
import org.jetbrains.kotlin.resolve.konan.diagnostics.NativeObjCNameChecker;
import org.jetbrains.kotlin.resolve.konan.diagnostics.NativeObjCNameOverridesChecker;
import org.jetbrains.kotlin.resolve.konan.diagnostics.NativeObjCRefinementAnnotationChecker;
import org.jetbrains.kotlin.resolve.konan.diagnostics.NativeObjCRefinementChecker;
import org.jetbrains.kotlin.resolve.konan.diagnostics.NativeObjCRefinementOverridesChecker;
import org.jetbrains.kotlin.resolve.konan.diagnostics.NativePlatformSpecificCastChecker;
import org.jetbrains.kotlin.resolve.konan.diagnostics.NativeReifiedForwardDeclarationChecker;
import org.jetbrains.kotlin.resolve.konan.diagnostics.NativeSharedImmutableChecker;
import org.jetbrains.kotlin.resolve.konan.diagnostics.NativeThreadLocalChecker;
import org.jetbrains.kotlin.resolve.konan.diagnostics.NativeThrowsChecker;

/* JADX INFO: compiled from: NativePlatformConfigurator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/platform/NativePlatformConfigurator;", "Lorg/jetbrains/kotlin/resolve/PlatformConfiguratorBase;", "()V", "configureModuleComponents", "", "container", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "configureModuleDependentCheckers", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativePlatformConfigurator extends PlatformConfiguratorBase {
    public static final NativePlatformConfigurator INSTANCE = new NativePlatformConfigurator();

    private NativePlatformConfigurator() {
        super(null, CollectionsKt.listOf((Object[]) new DeclarationChecker[]{NativeThrowsChecker.INSTANCE, NativeSharedImmutableChecker.INSTANCE, NativeThreadLocalChecker.INSTANCE, NativeObjCNameChecker.INSTANCE, NativeObjCNameOverridesChecker.INSTANCE, NativeObjCRefinementChecker.INSTANCE, NativeObjCRefinementAnnotationChecker.INSTANCE, NativeObjCRefinementOverridesChecker.INSTANCE, NativeHiddenFromObjCInheritanceChecker.INSTANCE}), CollectionsKt.listOf((Object[]) new CallChecker[]{new SuperCallWithDefaultArgumentsChecker(), new LateinitIntrinsicApplicabilityChecker(true), new NativeReifiedForwardDeclarationChecker()}), null, null, null, null, null, null, null, null, NativePlatformSpecificCastChecker.INSTANCE, null, null, null, 30713, null);
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformConfigurator
    public void configureModuleComponents(StorageComponentContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
        DslKt.useInstance(container, NativeInliningRule.INSTANCE);
        ContainerKt.registerSingleton(container, NativeIdentifierChecker.class);
        ContainerKt.registerSingleton(container, NativeForwardDeclarationRttiChecker.class);
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformConfiguratorBase, org.jetbrains.kotlin.resolve.PlatformConfigurator
    public void configureModuleDependentCheckers(StorageComponentContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
        super.configureModuleDependentCheckers(container);
        ContainerKt.registerSingleton(container, ExpectedActualDeclarationChecker.class);
    }
}
