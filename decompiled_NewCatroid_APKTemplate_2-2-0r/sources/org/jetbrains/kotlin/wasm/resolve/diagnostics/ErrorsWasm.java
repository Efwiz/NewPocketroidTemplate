package org.jetbrains.kotlin.wasm.resolve.diagnostics;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.diagnostics.PositioningStrategies;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: loaded from: classes6.dex */
public interface ErrorsWasm {
    public static final DiagnosticFactory1<KtElement, KotlinType> NON_EXTERNAL_TYPE_EXTENDS_EXTERNAL_TYPE = DiagnosticFactory1.create(Severity.ERROR, PositioningStrategies.DECLARATION_SIGNATURE_OR_DEFAULT);
    public static final DiagnosticFactory2<PsiElement, String, KotlinType> WRONG_JS_INTEROP_TYPE = DiagnosticFactory2.create(Severity.ERROR, PositioningStrategies.DECLARATION_SIGNATURE_OR_DEFAULT);
    public static final DiagnosticFactory0<PsiElement> NESTED_WASM_EXPORT = DiagnosticFactory0.create(Severity.ERROR);
    public static final DiagnosticFactory0<PsiElement> WASM_EXPORT_ON_EXTERNAL_DECLARATION = DiagnosticFactory0.create(Severity.ERROR);
    public static final DiagnosticFactory0<PsiElement> JS_AND_WASM_EXPORTS_ON_SAME_DECLARATION = DiagnosticFactory0.create(Severity.ERROR);
    public static final DiagnosticFactory0<PsiElement> WASI_EXTERNAL_NOT_TOP_LEVEL_FUNCTION = DiagnosticFactory0.create(Severity.ERROR);
    public static final DiagnosticFactory0<PsiElement> WASI_EXTERNAL_FUNCTION_WITHOUT_IMPORT = DiagnosticFactory0.create(Severity.ERROR);
    public static final DiagnosticFactory0<PsiElement> NESTED_WASM_IMPORT = DiagnosticFactory0.create(Severity.ERROR);
    public static final DiagnosticFactory0<PsiElement> WASM_IMPORT_ON_NON_EXTERNAL_DECLARATION = DiagnosticFactory0.create(Severity.ERROR);
    public static final DiagnosticFactory0<PsiElement> WASM_IMPORT_EXPORT_PARAMETER_DEFAULT_VALUE = DiagnosticFactory0.create(Severity.ERROR);
    public static final DiagnosticFactory0<PsiElement> WASM_IMPORT_EXPORT_VARARG_PARAMETER = DiagnosticFactory0.create(Severity.ERROR);
    public static final DiagnosticFactory1<PsiElement, KotlinType> WASM_IMPORT_EXPORT_UNSUPPORTED_PARAMETER_TYPE = DiagnosticFactory1.create(Severity.ERROR);
    public static final DiagnosticFactory1<PsiElement, KotlinType> WASM_IMPORT_EXPORT_UNSUPPORTED_RETURN_TYPE = DiagnosticFactory1.create(Severity.ERROR);
    public static final DiagnosticFactory0<PsiElement> WRONG_JS_FUN_TARGET = DiagnosticFactory0.create(Severity.ERROR);
    public static final DiagnosticFactory0<PsiElement> JSCODE_WRONG_CONTEXT = DiagnosticFactory0.create(Severity.ERROR);
    public static final DiagnosticFactory1<PsiElement, String> JSCODE_UNSUPPORTED_FUNCTION_KIND = DiagnosticFactory1.create(Severity.ERROR);
    public static final DiagnosticFactory0<PsiElement> JSCODE_INVALID_PARAMETER_NAME = DiagnosticFactory0.create(Severity.ERROR);
    public static final Object _initializer = new Object() { // from class: org.jetbrains.kotlin.wasm.resolve.diagnostics.ErrorsWasm.1
        {
            Errors.Initializer.initializeFactoryNames(ErrorsWasm.class);
        }
    };
}
