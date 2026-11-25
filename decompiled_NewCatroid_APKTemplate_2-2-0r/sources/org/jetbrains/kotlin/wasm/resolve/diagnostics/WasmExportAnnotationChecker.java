package org.jetbrains.kotlin.wasm.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.js.resolve.diagnostics.SourceLocationUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.wasm.util.JsCodeUtilsKt;

/* JADX INFO: compiled from: WasmInteropTypesChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/wasm/resolve/diagnostics/WasmExportAnnotationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "checkJsInterop", "", "(Z)V", "getCheckJsInterop", "()Z", "jsExportFqName", "Lorg/jetbrains/kotlin/name/FqName;", "wasmExportFqName", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "wasm.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class WasmExportAnnotationChecker implements DeclarationChecker {
    private final boolean checkJsInterop;
    private final FqName wasmExportFqName = new FqName("kotlin.wasm.WasmExport");
    private final FqName jsExportFqName = new FqName("kotlin.js.JsExport");

    public WasmExportAnnotationChecker(boolean z) {
        this.checkJsInterop = z;
    }

    public final boolean getCheckJsInterop() {
        return this.checkJsInterop;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((descriptor instanceof FunctionDescriptor) && descriptor.getAnnotations().hasAnnotation(this.wasmExportFqName)) {
            BindingTrace trace = context.getTrace();
            BindingContext bindingContext = trace.getBindingContext();
            Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
            if (this.checkJsInterop && descriptor.getAnnotations().hasAnnotation(this.jsExportFqName)) {
                KtDeclaration ktDeclarationFindPsi = SourceLocationUtilsKt.findPsi(descriptor);
                if (ktDeclarationFindPsi == null) {
                    ktDeclarationFindPsi = declaration;
                }
                trace.report(ErrorsWasm.JS_AND_WASM_EXPORTS_ON_SAME_DECLARATION.on(ktDeclarationFindPsi));
            }
            if (DescriptorUtilsKt.isEffectivelyExternal((MemberDescriptor) descriptor) || (this.checkJsInterop && JsCodeUtilsKt.hasValidJsCodeBody((FunctionDescriptor) descriptor, bindingContext))) {
                KtDeclaration ktDeclarationFindPsi2 = SourceLocationUtilsKt.findPsi(descriptor);
                if (ktDeclarationFindPsi2 == null) {
                    ktDeclarationFindPsi2 = declaration;
                }
                trace.report(ErrorsWasm.WASM_EXPORT_ON_EXTERNAL_DECLARATION.on(ktDeclarationFindPsi2));
            }
            if (!DescriptorUtils.isTopLevelDeclaration(descriptor)) {
                KtDeclaration ktDeclarationFindPsi3 = SourceLocationUtilsKt.findPsi(descriptor);
                if (ktDeclarationFindPsi3 == null) {
                    ktDeclarationFindPsi3 = declaration;
                }
                trace.report(ErrorsWasm.NESTED_WASM_EXPORT.on(ktDeclarationFindPsi3));
            }
            WasmImportAnnotationChecker.INSTANCE.checkSignatureIsPrimitive((FunctionDescriptor) descriptor, trace, declaration);
        }
    }
}
