package org.jetbrains.kotlin.js.translate.utils;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.inline.util.InvocationUtilsKt;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.reference.CallExpressionTranslator;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: inlineUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a&\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r\u001a*\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\f\u001a\u00020\r\u001a&\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r\u001a\u0012\u0010\u0012\u001a\u00020\u0001*\u00020\r2\u0006\u0010\n\u001a\u00020\u000b\"\u0019\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u0004\u0018\u00010\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0013"}, d2 = {"name", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getName", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "setInlineCallMetadata", "", "expression", "psiElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "nameRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "aliasedName", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineUtils {
    public static final void setInlineCallMetadata(JsExpression expression, final KtElement psiElement, final CallableDescriptor descriptor, TranslationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(psiElement, "psiElement");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        CallExpressionTranslator.shouldBeInlined(descriptor);
        JsName innerNameForDescriptor = context.getInnerNameForDescriptor(descriptor);
        Intrinsics.checkNotNullExpressionValue(innerNameForDescriptor, "getInnerNameForDescriptor(...)");
        final Set of = SetsKt.setOf((Object[]) new JsName[]{aliasedName(context, descriptor), innerNameForDescriptor});
        new RecursiveJsVisitor() { // from class: org.jetbrains.kotlin.js.translate.utils.InlineUtils$setInlineCallMetadata$visitor$1
            @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
            public void visitInvocation(JsInvocation invocation) {
                DeclarationDescriptor descriptor2;
                Intrinsics.checkNotNullParameter(invocation, "invocation");
                super.visitInvocation(invocation);
                JsInvocation jsInvocation = invocation;
                if (!CollectionsKt.contains(of, InlineUtils.getName(jsInvocation))) {
                    JsName name2 = InlineUtils.getName(jsInvocation);
                    if (!Intrinsics.areEqual((name2 == null || (descriptor2 = MetadataProperties.getDescriptor(name2)) == null) ? null : descriptor2.getOriginal(), descriptor.getOriginal())) {
                        return;
                    }
                }
                MetadataProperties.setDescriptor(invocation, descriptor);
                MetadataProperties.setInline(invocation, (Boolean) true);
                MetadataProperties.setPsiElement(invocation, psiElement);
            }
        }.accept(expression);
        context.addInlineCall(descriptor);
    }

    public static final void setInlineCallMetadata(JsExpression expression, KtElement psiElement, ResolvedCall<?> resolvedCall, TranslationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(psiElement, "psiElement");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor functionDescriptor = PsiUtils.getFunctionDescriptor(resolvedCall);
        Intrinsics.checkNotNullExpressionValue(functionDescriptor, "getFunctionDescriptor(...)");
        setInlineCallMetadata(expression, psiElement, functionDescriptor, context);
    }

    public static final void setInlineCallMetadata(JsNameRef nameRef, KtElement psiElement, CallableDescriptor descriptor, TranslationContext context) {
        Intrinsics.checkNotNullParameter(nameRef, "nameRef");
        Intrinsics.checkNotNullParameter(psiElement, "psiElement");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (MetadataProperties.isInline(nameRef) != null) {
            return;
        }
        MetadataProperties.setDescriptor(nameRef, descriptor);
        MetadataProperties.setInline(nameRef, (Boolean) true);
        MetadataProperties.setPsiElement(nameRef, psiElement);
        context.addInlineCall(descriptor);
    }

    public static final JsName aliasedName(TranslationContext translationContext, CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        CallableDescriptor callableDescriptor = descriptor;
        JsExpression aliasForDescriptor = translationContext.getAliasForDescriptor(callableDescriptor);
        JsNameRef jsNameRef = aliasForDescriptor instanceof JsNameRef ? (JsNameRef) aliasForDescriptor : null;
        JsName name2 = jsNameRef != null ? jsNameRef.getName() : null;
        if (name2 != null) {
            return name2;
        }
        JsName nameForDescriptor = translationContext.getNameForDescriptor(callableDescriptor);
        Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
        return nameForDescriptor;
    }

    public static final JsName getName(JsExpression jsExpression) {
        if (jsExpression instanceof JsInvocation) {
            JsInvocation jsInvocation = (JsInvocation) jsExpression;
            JsExpression qualifier = jsInvocation.getQualifier();
            Intrinsics.checkNotNullExpressionValue(qualifier, "getQualifier(...)");
            return InvocationUtilsKt.isCallInvocation(jsInvocation) ? getName(((JsNameRef) qualifier).getQualifier()) : getName(qualifier);
        }
        if (jsExpression instanceof JsNameRef) {
            return ((JsNameRef) jsExpression).getName();
        }
        return null;
    }
}
