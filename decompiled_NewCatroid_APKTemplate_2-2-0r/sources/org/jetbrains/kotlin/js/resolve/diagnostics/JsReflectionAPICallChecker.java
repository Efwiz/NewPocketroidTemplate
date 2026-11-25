package org.jetbrains.kotlin.js.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.resolve.calls.checkers.AbstractReflectionApiCallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: JsReflectionAPICallChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0014J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u0010H\u0014R\u0014\u0010\u0007\u001a\u00020\b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\t¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsReflectionAPICallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/AbstractReflectionApiCallChecker;", "reflectionTypes", "Lorg/jetbrains/kotlin/builtins/ReflectionTypes;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/builtins/ReflectionTypes;Lorg/jetbrains/kotlin/storage/StorageManager;)V", "isWholeReflectionApiAvailable", "", "()Z", "isAllowedReflectionApi", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "containingClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "report", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsReflectionAPICallChecker extends AbstractReflectionApiCallChecker {
    @Override // org.jetbrains.kotlin.resolve.calls.checkers.AbstractReflectionApiCallChecker
    protected boolean isWholeReflectionApiAvailable() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsReflectionAPICallChecker(ReflectionTypes reflectionTypes, StorageManager storageManager) {
        super(reflectionTypes, storageManager);
        Intrinsics.checkNotNullParameter(reflectionTypes, "reflectionTypes");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.AbstractReflectionApiCallChecker
    protected boolean isAllowedReflectionApi(CallableDescriptor descriptor, ClassDescriptor containingClass, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(context, "context");
        return super.isAllowedReflectionApi(descriptor, containingClass, context) || JsReflectionAPICallCheckerKt.ADDITIONAL_ALLOWED_CLASSES.contains(DescriptorUtilsKt.getFqNameSafe(containingClass)) || Intrinsics.areEqual(descriptor.getShortName().asString(), "findAssociatedObject");
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.AbstractReflectionApiCallChecker
    protected void report(PsiElement element, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(context, "context");
        context.getTrace().report(Errors.UNSUPPORTED.on(element, "This reflection API is not supported yet in JavaScript"));
    }
}
