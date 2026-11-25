package org.jetbrains.kotlin.wasm.resolve.diagnostics;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.diagnostics.rendering.CommonRenderers;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticFactoryToRendererMap;
import org.jetbrains.kotlin.diagnostics.rendering.Renderers;

/* JADX INFO: compiled from: DefaultErrorMessagesWasm.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001b\u0010\u0000\u001a\u00020\u00018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0006"}, d2 = {"DIAGNOSTIC_FACTORY_TO_RENDERER", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticFactoryToRendererMap;", "getDIAGNOSTIC_FACTORY_TO_RENDERER", "()Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticFactoryToRendererMap;", "DIAGNOSTIC_FACTORY_TO_RENDERER$delegate", "Lkotlin/Lazy;", "wasm.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DefaultErrorMessagesWasmKt {
    private static final Lazy DIAGNOSTIC_FACTORY_TO_RENDERER$delegate = LazyKt.lazy(new Function0<DiagnosticFactoryToRendererMap>() { // from class: org.jetbrains.kotlin.wasm.resolve.diagnostics.DefaultErrorMessagesWasmKt$DIAGNOSTIC_FACTORY_TO_RENDERER$2
        @Override // kotlin.jvm.functions.Function0
        public final DiagnosticFactoryToRendererMap invoke() {
            DiagnosticFactoryToRendererMap diagnosticFactoryToRendererMap = new DiagnosticFactoryToRendererMap("Wasm");
            diagnosticFactoryToRendererMap.put(ErrorsWasm.NON_EXTERNAL_TYPE_EXTENDS_EXTERNAL_TYPE, "Non-external type extends external type {0}", Renderers.RENDER_TYPE);
            diagnosticFactoryToRendererMap.put(ErrorsWasm.WRONG_JS_INTEROP_TYPE, "Type {1} cannot be used in {0}. Only external, primitive, string and function types are supported in Kotlin/Wasm JS interop.", CommonRenderers.STRING, Renderers.RENDER_TYPE);
            diagnosticFactoryToRendererMap.put(ErrorsWasm.NESTED_WASM_EXPORT, "Only top-level functions can be exported with @WasmExport");
            diagnosticFactoryToRendererMap.put(ErrorsWasm.WASM_EXPORT_ON_EXTERNAL_DECLARATION, "Functions annotated with @WasmExport must not be external");
            diagnosticFactoryToRendererMap.put(ErrorsWasm.JS_AND_WASM_EXPORTS_ON_SAME_DECLARATION, "Cannot use @WasmExport and @JsExport for same function");
            diagnosticFactoryToRendererMap.put(ErrorsWasm.WASI_EXTERNAL_NOT_TOP_LEVEL_FUNCTION, "Only top-level functions can be external");
            diagnosticFactoryToRendererMap.put(ErrorsWasm.WASI_EXTERNAL_FUNCTION_WITHOUT_IMPORT, "External functions should be annotated with @WasmImport");
            diagnosticFactoryToRendererMap.put(ErrorsWasm.NESTED_WASM_IMPORT, "Only top-level functions can be imported with @WasmImport");
            diagnosticFactoryToRendererMap.put(ErrorsWasm.WASM_IMPORT_ON_NON_EXTERNAL_DECLARATION, "Functions annotated with @WasmImport must be external");
            diagnosticFactoryToRendererMap.put(ErrorsWasm.WASM_IMPORT_EXPORT_PARAMETER_DEFAULT_VALUE, "Default parameter values are not supported with @WasmImport and @WasmExport");
            diagnosticFactoryToRendererMap.put(ErrorsWasm.WASM_IMPORT_EXPORT_VARARG_PARAMETER, "Vararg parameters are not supported with @WasmImport and @WasmExport");
            diagnosticFactoryToRendererMap.put(ErrorsWasm.WASM_IMPORT_EXPORT_UNSUPPORTED_PARAMETER_TYPE, "Unsupported @WasmImport and @WasmExport parameter type {0}", Renderers.RENDER_TYPE);
            diagnosticFactoryToRendererMap.put(ErrorsWasm.WASM_IMPORT_EXPORT_UNSUPPORTED_RETURN_TYPE, "Unsupported @WasmImport and @WasmExport return type {0}", Renderers.RENDER_TYPE);
            diagnosticFactoryToRendererMap.put(ErrorsWasm.WRONG_JS_FUN_TARGET, "Only top-level external functions can be implemented using @JsFun");
            diagnosticFactoryToRendererMap.put(ErrorsWasm.JSCODE_WRONG_CONTEXT, "Calls to js(code) should be a single expression inside a top-level function body or a property initializer in Kotlin/Wasm");
            diagnosticFactoryToRendererMap.put(ErrorsWasm.JSCODE_UNSUPPORTED_FUNCTION_KIND, "Calls to js(code) are not supported in {0} in Kotlin/Wasm", CommonRenderers.STRING);
            diagnosticFactoryToRendererMap.put(ErrorsWasm.JSCODE_INVALID_PARAMETER_NAME, "Parameters passed to js(code) should have a valid JavaScript name");
            return diagnosticFactoryToRendererMap;
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public static final DiagnosticFactoryToRendererMap getDIAGNOSTIC_FACTORY_TO_RENDERER() {
        return (DiagnosticFactoryToRendererMap) DIAGNOSTIC_FACTORY_TO_RENDERER$delegate.getValue();
    }
}
