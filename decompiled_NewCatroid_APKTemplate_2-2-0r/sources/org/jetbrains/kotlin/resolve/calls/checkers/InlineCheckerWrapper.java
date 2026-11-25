package org.jetbrains.kotlin.resolve.calls.checkers;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;

/* JADX INFO: compiled from: InlineCheckerWrapper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0011H\u0002R\"\u0010\u0003\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/InlineCheckerWrapper;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "checkersCache", "Ljava/lang/ref/WeakReference;", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "getChecker", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineCheckerWrapper implements CallChecker {
    private WeakReference<Map<DeclarationDescriptor, CallChecker>> checkersCache;

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.isAnnotationContext()) {
            return;
        }
        for (DeclarationDescriptor ownerDescriptor = context.getScope().getOwnerDescriptor(); ownerDescriptor != null; ownerDescriptor = ownerDescriptor.getContainingDeclaration()) {
            if (InlineUtil.isInline(ownerDescriptor)) {
                getChecker((FunctionDescriptor) ownerDescriptor).check(resolvedCall, reportOn, context);
            }
        }
    }

    private final CallChecker getChecker(FunctionDescriptor descriptor) {
        WeakReference<Map<DeclarationDescriptor, CallChecker>> weakReference = this.checkersCache;
        HashMap map = weakReference != null ? weakReference.get() : null;
        if (map == null) {
            map = new HashMap();
        }
        WeakReference<Map<DeclarationDescriptor, CallChecker>> weakReference2 = this.checkersCache;
        if (weakReference2 == null) {
            weakReference2 = new WeakReference<>(map);
        }
        this.checkersCache = weakReference2;
        InlineChecker inlineChecker = map.get(descriptor);
        if (inlineChecker == null) {
            inlineChecker = new InlineChecker(descriptor);
            map.put(descriptor, inlineChecker);
        }
        return inlineChecker;
    }
}
