package org.jetbrains.kotlin.wasm.resolve;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.ContainerKt;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.js.naming.WasmNameSuggestion;
import org.jetbrains.kotlin.js.resolve.ExtensionFunctionToExternalIsInlinable;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsDefinedExternallyCallChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsExportAnnotationChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsExportDeclarationChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsExternalChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsExternalFileChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsModuleCallChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsModuleChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsModuleClassLiteralChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsNameChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsNativeRttiChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsQualifierChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsReflectionAPICallChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsReifiedNativeChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsRuntimeAnnotationChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.WasmNameCharsChecker;
import org.jetbrains.kotlin.js.resolve.diagnostics.WasmNameClashChecker;
import org.jetbrains.kotlin.resolve.PlatformConfiguratorBase;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.LateinitIntrinsicApplicabilityChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker;
import org.jetbrains.kotlin.wasm.analyze.WasmDiagnosticSuppressor;
import org.jetbrains.kotlin.wasm.resolve.diagnostics.WasmExternalDeclarationChecker;
import org.jetbrains.kotlin.wasm.resolve.diagnostics.WasmExternalInheritanceChecker;
import org.jetbrains.kotlin.wasm.resolve.diagnostics.WasmImportAnnotationChecker;
import org.jetbrains.kotlin.wasm.resolve.diagnostics.WasmJsCallChecker;
import org.jetbrains.kotlin.wasm.resolve.diagnostics.WasmJsExportChecker;
import org.jetbrains.kotlin.wasm.resolve.diagnostics.WasmJsFunAnnotationChecker;
import org.jetbrains.kotlin.wasm.resolve.diagnostics.WasmJsInteropTypesChecker;

/* JADX INFO: compiled from: WasmPlatformConfigurator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/resolve/WasmJsPlatformConfigurator;", "Lorg/jetbrains/kotlin/resolve/PlatformConfiguratorBase;", "()V", "configureModuleComponents", "", "container", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "configureModuleDependentCheckers", "wasm.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmJsPlatformConfigurator extends PlatformConfiguratorBase {
    public static final WasmJsPlatformConfigurator INSTANCE = new WasmJsPlatformConfigurator();

    private WasmJsPlatformConfigurator() {
        super(null, CollectionsKt.listOf((Object[]) new DeclarationChecker[]{JsNameChecker.INSTANCE, JsModuleChecker.INSTANCE, JsExternalFileChecker.INSTANCE, JsExternalChecker.INSTANCE, WasmExternalInheritanceChecker.INSTANCE, JsRuntimeAnnotationChecker.INSTANCE, JsExportAnnotationChecker.INSTANCE, JsExportDeclarationChecker.INSTANCE, WasmExternalDeclarationChecker.INSTANCE, WasmImportAnnotationChecker.INSTANCE, WasmJsFunAnnotationChecker.INSTANCE, WasmJsInteropTypesChecker.INSTANCE, WasmJsExportChecker.INSTANCE}), CollectionsKt.listOf((Object[]) new CallChecker[]{JsModuleCallChecker.INSTANCE, JsDefinedExternallyCallChecker.INSTANCE, new LateinitIntrinsicApplicabilityChecker(true)}), null, null, null, null, null, null, null, null, null, null, null, null, 32761, null);
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformConfigurator
    public void configureModuleComponents(StorageComponentContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
        DslKt.useInstance(container, new WasmNameSuggestion());
        ContainerKt.registerSingleton(container, WasmJsCallChecker.class);
        ContainerKt.registerSingleton(container, WasmNameClashChecker.class);
        ContainerKt.registerSingleton(container, WasmNameCharsChecker.class);
        DslKt.useInstance(container, JsModuleClassLiteralChecker.INSTANCE);
        ContainerKt.registerSingleton(container, JsReflectionAPICallChecker.class);
        ContainerKt.registerSingleton(container, JsNativeRttiChecker.class);
        ContainerKt.registerSingleton(container, JsReifiedNativeChecker.class);
        DslKt.useInstance(container, ExtensionFunctionToExternalIsInlinable.INSTANCE);
        DslKt.useInstance(container, JsQualifierChecker.INSTANCE);
        DslKt.useInstance(container, WasmDiagnosticSuppressor.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformConfiguratorBase, org.jetbrains.kotlin.resolve.PlatformConfigurator
    public void configureModuleDependentCheckers(StorageComponentContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
        super.configureModuleDependentCheckers(container);
        ContainerKt.registerSingleton(container, ExpectedActualDeclarationChecker.class);
    }
}
