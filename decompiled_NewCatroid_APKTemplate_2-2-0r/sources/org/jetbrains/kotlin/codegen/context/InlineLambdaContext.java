package org.jetbrains.kotlin.codegen.context;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonCoroutineCodegenUtilKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: InlineLambdaContext.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b¢\u0006\u0002\u0010\rJ\f\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0007H\u0016J\b\u0010\u0010\u001a\u00020\u000bH\u0002R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000eR\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/context/InlineLambdaContext;", "Lorg/jetbrains/kotlin/codegen/context/MethodContext;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "contextKind", "Lorg/jetbrains/kotlin/codegen/OwnerKind;", "parentContext", "Lorg/jetbrains/kotlin/codegen/context/CodegenContext;", "closure", "Lorg/jetbrains/kotlin/codegen/binding/MutableClosure;", "isCrossInline", "", "isPropertyReference", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/codegen/OwnerKind;Lorg/jetbrains/kotlin/codegen/context/CodegenContext;Lorg/jetbrains/kotlin/codegen/binding/MutableClosure;ZZ)V", "()Z", "getFirstCrossInlineOrNonInlineContext", "isSuspendIntrinsicParameter", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineLambdaContext extends MethodContext {
    private final boolean isCrossInline;
    private final boolean isPropertyReference;

    /* JADX INFO: renamed from: isCrossInline, reason: from getter */
    public final boolean getIsCrossInline() {
        return this.isCrossInline;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InlineLambdaContext(FunctionDescriptor functionDescriptor, OwnerKind contextKind, CodegenContext<?> parentContext, MutableClosure mutableClosure, boolean z, boolean z2) {
        super(functionDescriptor, contextKind, parentContext, mutableClosure, false);
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Intrinsics.checkNotNullParameter(contextKind, "contextKind");
        Intrinsics.checkNotNullParameter(parentContext, "parentContext");
        this.isCrossInline = z;
        this.isPropertyReference = z2;
    }

    @Override // org.jetbrains.kotlin.codegen.context.CodegenContext
    public CodegenContext<?> getFirstCrossInlineOrNonInlineContext() {
        ClassContext classContext;
        if (this.isCrossInline && !isSuspendIntrinsicParameter()) {
            return this;
        }
        if (this.isPropertyReference) {
            CodegenContext parentContext = getParentContext();
            classContext = parentContext instanceof AnonymousClassContext ? (AnonymousClassContext) parentContext : null;
        } else {
            CodegenContext parentContext2 = getParentContext();
            classContext = parentContext2 instanceof ClosureContext ? (ClosureContext) parentContext2 : null;
        }
        if (classContext == null) {
            throw new AssertionError("Parent of inlining lambda body should be " + (this.isPropertyReference ? "ClosureContext" : "AnonymousClassContext") + ", but: " + getParentContext());
        }
        CodegenContext parentContext3 = classContext.getParentContext();
        if (parentContext3 == null) {
            throw new AssertionError("Parent context of lambda class context should exist: " + getContextDescriptor());
        }
        CodegenContext<?> firstCrossInlineOrNonInlineContext = parentContext3.getFirstCrossInlineOrNonInlineContext();
        Intrinsics.checkNotNullExpressionValue(firstCrossInlineOrNonInlineContext, "getFirstCrossInlineOrNonInlineContext(...)");
        return firstCrossInlineOrNonInlineContext;
    }

    private final boolean isSuspendIntrinsicParameter() {
        if (!(getContextDescriptor() instanceof AnonymousFunctionDescriptor)) {
            return false;
        }
        SourceElement source = getContextDescriptor().getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        PsiElement psi = PsiSourceElementKt.getPsi(source);
        ResolvedCall parentResolvedCall$default = CallUtilKt.getParentResolvedCall$default(psi instanceof KtElement ? (KtElement) psi : null, getState().getBindingContext(), false, 2, null);
        if (parentResolvedCall$default == null) {
            return false;
        }
        CallableDescriptor resultingDescriptor = parentResolvedCall$default.getResultingDescriptor();
        FunctionDescriptor functionDescriptor = resultingDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) resultingDescriptor : null;
        if (functionDescriptor == null) {
            return false;
        }
        if (!CommonCoroutineCodegenUtilKt.isBuiltInSuspendCoroutineUninterceptedOrReturn(functionDescriptor)) {
            String strAsString = StandardNames.COROUTINES_PACKAGE_FQ_NAME.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            if (!DescriptorUtilKt.isTopLevelInPackage(functionDescriptor, "suspendCoroutine", strAsString)) {
                return false;
            }
        }
        return true;
    }
}
