package org.jetbrains.kotlin.js.resolve;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.ContainerKt;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.js.analyze.JsNativeDiagnosticSuppressor;
import org.jetbrains.kotlin.js.naming.JsNameSuggestion;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsBuiltinNameClashChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsCallChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsDefinedExternallyCallChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsDynamicCallChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsDynamicDeclarationChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsExportAnnotationChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsExportDeclarationChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsExternalArgumentCallChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsExternalChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsExternalFileChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsExternalInheritorOnlyChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsIdentifierChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsInheritanceChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsModuleCallChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsModuleChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsModuleClassLiteralChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsMultipleInheritanceChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsNameCharsChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsNameChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsNameClashChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsNativeRttiChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsQualifierChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsReflectionAPICallChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsReifiedNativeChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsRuntimeAnnotationChecker;
import org.jetbrains.kotlin.resolve.PlatformConfiguratorBase;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.LateinitIntrinsicApplicabilityChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker;
import org.jetbrains.kotlin.types.DynamicTypesAllowed;

/* JADX INFO: compiled from: JsPlatformConfigurator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/JsPlatformConfigurator;", "Lorg/jetbrains/kotlin/resolve/PlatformConfiguratorBase;", "()V", "configureModuleComponents", "", "container", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "configureModuleDependentCheckers", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsPlatformConfigurator extends PlatformConfiguratorBase {
    public static final JsPlatformConfigurator INSTANCE = new JsPlatformConfigurator();

    private JsPlatformConfigurator() {
        super(new DynamicTypesAllowed(), CollectionsKt.listOf((Object[]) new DeclarationChecker[]{new NativeInvokeChecker(), new NativeGetterChecker(), new NativeSetterChecker(), JsNameChecker.INSTANCE, JsModuleChecker.INSTANCE, JsExternalFileChecker.INSTANCE, JsExternalChecker.INSTANCE, JsInheritanceChecker.INSTANCE, JsMultipleInheritanceChecker.INSTANCE, JsExternalInheritorOnlyChecker.INSTANCE, JsRuntimeAnnotationChecker.INSTANCE, JsDynamicDeclarationChecker.INSTANCE, JsExportAnnotationChecker.INSTANCE, JsExportDeclarationChecker.INSTANCE}), CollectionsKt.listOf((Object[]) new CallChecker[]{JsModuleCallChecker.INSTANCE, JsDynamicCallChecker.INSTANCE, JsDefinedExternallyCallChecker.INSTANCE, new LateinitIntrinsicApplicabilityChecker(true), JsExternalArgumentCallChecker.INSTANCE}), null, null, null, null, null, null, null, null, null, null, null, null, 32760, null);
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformConfigurator
    public void configureModuleComponents(StorageComponentContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
        DslKt.useInstance(container, new JsNameSuggestion());
        ContainerKt.registerSingleton(container, JsCallChecker.class);
        ContainerKt.registerSingleton(container, JsTypeSpecificityComparator.class);
        ContainerKt.registerSingleton(container, JsNameClashChecker.class);
        ContainerKt.registerSingleton(container, JsIdentifierChecker.class);
        ContainerKt.registerSingleton(container, JsNameCharsChecker.class);
        ContainerKt.registerSingleton(container, JsBuiltinNameClashChecker.class);
        DslKt.useInstance(container, JsModuleClassLiteralChecker.INSTANCE);
        ContainerKt.registerSingleton(container, JsReflectionAPICallChecker.class);
        ContainerKt.registerSingleton(container, JsNativeRttiChecker.class);
        ContainerKt.registerSingleton(container, JsReifiedNativeChecker.class);
        DslKt.useInstance(container, ExtensionFunctionToExternalIsInlinable.INSTANCE);
        DslKt.useInstance(container, JsQualifierChecker.INSTANCE);
        DslKt.useInstance(container, JsNativeDiagnosticSuppressor.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformConfiguratorBase, org.jetbrains.kotlin.resolve.PlatformConfigurator
    public void configureModuleDependentCheckers(StorageComponentContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
        super.configureModuleDependentCheckers(container);
        ContainerKt.registerSingleton(container, ExpectedActualDeclarationChecker.class);
    }
}
