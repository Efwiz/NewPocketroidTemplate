package org.jetbrains.kotlin.wasm.resolve.diagnostics;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: WasmImportAnnotationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u001e\u0010\r\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016²\u0006\n\u0010\u0017\u001a\u00020\u0018X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/wasm/resolve/diagnostics/WasmImportAnnotationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "wasmImportFqName", "Lorg/jetbrains/kotlin/name/FqName;", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkSignatureIsPrimitive", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "isParameterTypeSupported", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "isReturnTypeSupported", "wasm.frontend", "valueParameterDeclaration", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmImportAnnotationChecker implements DeclarationChecker {
    public static final WasmImportAnnotationChecker INSTANCE = new WasmImportAnnotationChecker();
    private static final FqName wasmImportFqName = new FqName("kotlin.wasm.WasmImport");

    private WasmImportAnnotationChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = descriptor.getAnnotations().mo6752findAnnotation(wasmImportFqName);
        if (annotationDescriptorMo6752findAnnotation == null) {
            return;
        }
        BindingTrace trace = context.getTrace();
        KtDeclaration psi = PsiSourceElementKt.getPsi(annotationDescriptorMo6752findAnnotation.getSource());
        if (psi == null) {
            psi = declaration;
        }
        if (!DescriptorUtils.isTopLevelDeclaration(descriptor)) {
            trace.report(ErrorsWasm.NESTED_WASM_IMPORT.on(psi));
        }
        if (descriptor instanceof FunctionDescriptor) {
            if (!DescriptorUtilsKt.isEffectivelyExternal((MemberDescriptor) descriptor)) {
                trace.report(ErrorsWasm.WASM_IMPORT_ON_NON_EXTERNAL_DECLARATION.on(psi));
            }
            checkSignatureIsPrimitive((FunctionDescriptor) descriptor, trace, declaration);
        }
    }

    private final boolean isParameterTypeSupported(KotlinType type) {
        return TypeUtilsKt.isPrimitiveNumberType(type) || TypeUtilsKt.isBoolean(type);
    }

    private final boolean isReturnTypeSupported(KotlinType type) {
        return isParameterTypeSupported(type) || TypeUtilsKt.isUnit(type);
    }

    public final void checkSignatureIsPrimitive(FunctionDescriptor descriptor, BindingTrace trace, KtDeclaration declaration) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        for (ValueParameterDescriptor valueParameterDescriptor : descriptor.getValueParameters()) {
            Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "next(...)");
            final ValueParameterDescriptor valueParameterDescriptor2 = valueParameterDescriptor;
            Lazy lazy = LazyKt.lazy(new Function0<PsiElement>() { // from class: org.jetbrains.kotlin.wasm.resolve.diagnostics.WasmImportAnnotationChecker$checkSignatureIsPrimitive$valueParameterDeclaration$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final PsiElement invoke() {
                    PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(valueParameterDescriptor2);
                    Intrinsics.checkNotNull(psiElementDescriptorToDeclaration);
                    return psiElementDescriptorToDeclaration;
                }
            });
            if (valueParameterDescriptor2.declaresDefaultValue()) {
                trace.report(ErrorsWasm.WASM_IMPORT_EXPORT_PARAMETER_DEFAULT_VALUE.on(checkSignatureIsPrimitive$lambda$0(lazy)));
            }
            if (ArgumentsUtilsKt.isVararg(valueParameterDescriptor2)) {
                trace.report(ErrorsWasm.WASM_IMPORT_EXPORT_VARARG_PARAMETER.on(checkSignatureIsPrimitive$lambda$0(lazy)));
            }
            KotlinType type = valueParameterDescriptor2.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            if (!isParameterTypeSupported(type)) {
                trace.report(ErrorsWasm.WASM_IMPORT_EXPORT_UNSUPPORTED_PARAMETER_TYPE.on(checkSignatureIsPrimitive$lambda$0(lazy), valueParameterDescriptor2.getType()));
            }
        }
        KotlinType returnType = descriptor.getReturnType();
        if (returnType == null || isReturnTypeSupported(returnType)) {
            return;
        }
        trace.report(ErrorsWasm.WASM_IMPORT_EXPORT_UNSUPPORTED_RETURN_TYPE.on(declaration, returnType));
    }

    private static final PsiElement checkSignatureIsPrimitive$lambda$0(Lazy<? extends PsiElement> lazy) {
        return lazy.getValue();
    }
}
