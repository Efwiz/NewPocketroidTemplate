package org.jetbrains.kotlin.wasm.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.resolve.diagnostics.SourceLocationUtilsKt;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.wasm.util.JsCodeUtilsKt;

/* JADX INFO: compiled from: WasmJsInteropTypesChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/resolve/diagnostics/WasmJsInteropTypesChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "wasm.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmJsInteropTypesChecker implements DeclarationChecker {
    public static final WasmJsInteropTypesChecker INSTANCE = new WasmJsInteropTypesChecker();

    private WasmJsInteropTypesChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (descriptor instanceof MemberDescriptor) {
            BindingTrace trace = context.getTrace();
            BindingContext context2 = trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
            if (check$isExternalJsInteropDeclaration(descriptor) || check$isJsCodeDeclaration(descriptor, context2) || AnnotationsUtils.isExportedObject(descriptor, context2)) {
                if (descriptor instanceof ClassDescriptor) {
                    for (TypeParameterDescriptor typeParameterDescriptor : ((ClassDescriptor) descriptor).getDeclaredTypeParameters()) {
                        Intrinsics.checkNotNull(typeParameterDescriptor);
                        check$checkJsInteropTypeParameter(typeParameterDescriptor, declaration, trace);
                    }
                    return;
                }
                if (descriptor instanceof PropertyDescriptor) {
                    PropertyDescriptor propertyDescriptor = (PropertyDescriptor) descriptor;
                    for (TypeParameterDescriptor typeParameterDescriptor2 : propertyDescriptor.getTypeParameters()) {
                        Intrinsics.checkNotNull(typeParameterDescriptor2);
                        check$checkJsInteropTypeParameter(typeParameterDescriptor2, declaration, trace);
                    }
                    KotlinType type = propertyDescriptor.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    check$checkJsInteropType$default(type, trace, "external property", declaration, false, 8, null);
                    return;
                }
                if (descriptor instanceof FunctionDescriptor) {
                    FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
                    for (TypeParameterDescriptor typeParameterDescriptor3 : functionDescriptor.getTypeParameters()) {
                        Intrinsics.checkNotNull(typeParameterDescriptor3);
                        check$checkJsInteropTypeParameter(typeParameterDescriptor3, declaration, trace);
                    }
                    for (ValueParameterDescriptor valueParameterDescriptor : functionDescriptor.getValueParameters()) {
                        KotlinType varargElementType = valueParameterDescriptor.getVarargElementType();
                        if (varargElementType == null) {
                            varargElementType = valueParameterDescriptor.getType();
                            Intrinsics.checkNotNullExpressionValue(varargElementType, "getType(...)");
                        }
                        Intrinsics.checkNotNull(valueParameterDescriptor);
                        KtDeclaration ktDeclarationFindPsi = SourceLocationUtilsKt.findPsi(valueParameterDescriptor);
                        if (ktDeclarationFindPsi == null) {
                            ktDeclarationFindPsi = declaration;
                        }
                        check$checkJsInteropType$default(varargElementType, trace, "external function parameter", ktDeclarationFindPsi, false, 8, null);
                    }
                    KotlinType returnType = functionDescriptor.getReturnType();
                    if (returnType != null) {
                        check$checkJsInteropType(returnType, trace, "external function return", declaration, true);
                    }
                }
            }
        }
    }

    private static final boolean check$isExternalJsInteropDeclaration(DeclarationDescriptor declarationDescriptor) {
        return DescriptorUtilsKt.isEffectivelyExternal((MemberDescriptor) declarationDescriptor) && !declarationDescriptor.getAnnotations().hasAnnotation(new FqName("kotlin.wasm.WasmImport"));
    }

    private static final boolean check$isJsCodeDeclaration(DeclarationDescriptor declarationDescriptor, BindingContext bindingContext) {
        return ((declarationDescriptor instanceof FunctionDescriptor) && JsCodeUtilsKt.hasValidJsCodeBody((FunctionDescriptor) declarationDescriptor, bindingContext)) || ((declarationDescriptor instanceof PropertyDescriptor) && JsCodeUtilsKt.hasValidJsCodeBody((PropertyDescriptor) declarationDescriptor, bindingContext));
    }

    static /* synthetic */ void check$checkJsInteropType$default(KotlinType kotlinType, BindingTrace bindingTrace, String str, PsiElement psiElement, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = false;
        }
        check$checkJsInteropType(kotlinType, bindingTrace, str, psiElement, z);
    }

    private static final void check$checkJsInteropType(KotlinType kotlinType, BindingTrace bindingTrace, String str, PsiElement psiElement, boolean z) {
        if (WasmJsInteropTypesCheckerKt.isTypeSupportedInJsInterop(kotlinType, z)) {
            return;
        }
        bindingTrace.report(ErrorsWasm.WRONG_JS_INTEROP_TYPE.on(psiElement, str, kotlinType));
    }

    private static final void check$checkJsInteropTypeParameter(TypeParameterDescriptor typeParameterDescriptor, KtDeclaration ktDeclaration, BindingTrace bindingTrace) {
        for (KotlinType kotlinType : typeParameterDescriptor.getUpperBounds()) {
            Intrinsics.checkNotNull(kotlinType);
            if (!WasmJsInteropTypesCheckerKt.isTypeSupportedInJsInterop(kotlinType, false)) {
                KtDeclaration ktDeclarationFindPsi = SourceLocationUtilsKt.findPsi(typeParameterDescriptor);
                if (ktDeclarationFindPsi == null) {
                    ktDeclarationFindPsi = ktDeclaration;
                }
                bindingTrace.report(ErrorsWasm.WRONG_JS_INTEROP_TYPE.on(ktDeclarationFindPsi, "type parameter upper bound", kotlinType));
            }
        }
    }
}
