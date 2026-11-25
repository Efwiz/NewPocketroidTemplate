package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaPropertyDescriptor;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;

/* JADX INFO: compiled from: jvmConstants.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/ConstantsChecker;", "Lorg/jetbrains/kotlin/psi/KtVisitorVoid;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "(Lorg/jetbrains/kotlin/resolve/BindingContext;)V", "containsKotlinConstVals", "", "getContainsKotlinConstVals", "()Z", "setContainsKotlinConstVals", "(Z)V", "visitKtElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "visitSimpleNameExpression", "expression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ConstantsChecker extends KtVisitorVoid {
    private final BindingContext bindingContext;
    private boolean containsKotlinConstVals;

    public ConstantsChecker(BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        this.bindingContext = bindingContext;
    }

    public final boolean getContainsKotlinConstVals() {
        return this.containsKotlinConstVals;
    }

    public final void setContainsKotlinConstVals(boolean z) {
        this.containsKotlinConstVals = z;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitSimpleNameExpression(KtSimpleNameExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(expression, this.bindingContext);
        if (resolvedCall != null) {
            CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
            if ((resultingDescriptor instanceof PropertyDescriptor) && !(resultingDescriptor instanceof JavaPropertyDescriptor) && ((PropertyDescriptor) resultingDescriptor).isConst()) {
                this.containsKotlinConstVals = true;
            }
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitKtElement(KtElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (this.containsKotlinConstVals) {
            return;
        }
        element.acceptChildren(this);
    }
}
