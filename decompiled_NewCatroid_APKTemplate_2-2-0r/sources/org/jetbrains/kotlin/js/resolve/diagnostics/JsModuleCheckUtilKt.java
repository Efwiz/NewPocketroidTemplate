package org.jetbrains.kotlin.js.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.js.resolve.BindingContextSlicesJsKt;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: JsModuleCheckUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a.\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n\u001a\u0010\u0010\u000b\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0002\u001a\u0010\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0007H\u0002¨\u0006\u000e"}, d2 = {"checkJsModuleUsage", "", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "diagnosticSink", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "container", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "callee", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "findRoot", "normalizeDescriptor", "descriptor", "js.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsModuleCheckUtilKt {
    public static final void checkJsModuleUsage(BindingContext bindingContext, DiagnosticSink diagnosticSink, DeclarationDescriptor container, DeclarationDescriptor callee, PsiElement reportOn) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(diagnosticSink, "diagnosticSink");
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        ModuleDescriptor containingModule = DescriptorUtils.getContainingModule(container);
        Intrinsics.checkNotNullExpressionValue(containingModule, "getContainingModule(...)");
        ModuleKind moduleKind = (ModuleKind) bindingContext.get(BindingContextSlicesJsKt.MODULE_KIND, containingModule);
        if (moduleKind == null) {
            return;
        }
        DeclarationDescriptor declarationDescriptorFindRoot = findRoot(callee);
        boolean z = (AnnotationsUtils.getModuleName(declarationDescriptorFindRoot) == null && AnnotationsUtils.getFileModuleName(bindingContext, declarationDescriptorFindRoot) == null) ? false : true;
        boolean z2 = AnnotationsUtils.isNonModule(declarationDescriptorFindRoot) || AnnotationsUtils.isFromNonModuleFile(bindingContext, declarationDescriptorFindRoot);
        if (moduleKind == ModuleKind.UMD) {
            if ((z2 || !z) && (!z2 || z)) {
                return;
            }
            diagnosticSink.report(ErrorsJs.CALL_FROM_UMD_MUST_BE_JS_MODULE_AND_JS_NON_MODULE.on(reportOn));
            return;
        }
        if (moduleKind == ModuleKind.PLAIN) {
            if (z2 || !z) {
                return;
            }
            diagnosticSink.report(ErrorsJs.CALL_TO_JS_MODULE_WITHOUT_MODULE_SYSTEM.on(reportOn, normalizeDescriptor(callee)));
            return;
        }
        if (z || !z2) {
            return;
        }
        diagnosticSink.report(ErrorsJs.CALL_TO_JS_NON_MODULE_WITH_MODULE_SYSTEM.on(reportOn, normalizeDescriptor(callee)));
    }

    private static final DeclarationDescriptor normalizeDescriptor(DeclarationDescriptor declarationDescriptor) {
        return declarationDescriptor instanceof FakeCallableDescriptorForObject ? ((FakeCallableDescriptorForObject) declarationDescriptor).getClassDescriptor() : declarationDescriptor;
    }

    private static final DeclarationDescriptor findRoot(DeclarationDescriptor declarationDescriptor) {
        return (DeclarationDescriptor) SequencesKt.last(SequencesKt.takeWhile(SequencesKt.generateSequence(declarationDescriptor, new Function1<DeclarationDescriptor, DeclarationDescriptor>() { // from class: org.jetbrains.kotlin.js.resolve.diagnostics.JsModuleCheckUtilKt.findRoot.1
            @Override // kotlin.jvm.functions.Function1
            public final DeclarationDescriptor invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getContainingDeclaration();
            }
        }), new Function1<DeclarationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.js.resolve.diagnostics.JsModuleCheckUtilKt.findRoot.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(!(it instanceof PackageFragmentDescriptor));
            }
        }));
    }
}
