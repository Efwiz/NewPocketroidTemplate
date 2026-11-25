package org.jetbrains.kotlin.wasm.util;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.js.resolve.diagnostics.JsCallChecker;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: jsCodeUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u0014\u0010\u0007\u001a\u00020\u0001*\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002¨\u0006\t"}, d2 = {"hasValidJsCodeBody", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/psi/KtDeclarationWithBody;", "isJsCall", "Lorg/jetbrains/kotlin/psi/KtExpression;", "wasm.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsCodeUtilsKt {
    public static final boolean hasValidJsCodeBody(PropertyDescriptor propertyDescriptor, BindingContext bindingContext) {
        KtExpression initializer;
        Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        SourceElement source = propertyDescriptor.getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        PsiElement psi = PsiSourceElementKt.getPsi(source);
        KtProperty ktProperty = psi instanceof KtProperty ? (KtProperty) psi : null;
        if (ktProperty == null || (initializer = ktProperty.getInitializer()) == null) {
            return false;
        }
        return isJsCall(initializer, bindingContext);
    }

    public static final boolean hasValidJsCodeBody(FunctionDescriptor functionDescriptor, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        SourceElement source = functionDescriptor.getSourceElement();
        Intrinsics.checkNotNullExpressionValue(source, "getSource(...)");
        PsiElement psi = PsiSourceElementKt.getPsi(source);
        KtNamedFunction ktNamedFunction = psi instanceof KtNamedFunction ? (KtNamedFunction) psi : null;
        if (ktNamedFunction == null) {
            return false;
        }
        return hasValidJsCodeBody(ktNamedFunction, bindingContext);
    }

    private static final boolean hasValidJsCodeBody(KtDeclarationWithBody ktDeclarationWithBody, BindingContext bindingContext) {
        if (!ktDeclarationWithBody.hasBody()) {
            return false;
        }
        KtExpression bodyExpression = ktDeclarationWithBody.getBodyExpression();
        Intrinsics.checkNotNull(bodyExpression);
        if (!ktDeclarationWithBody.hasBlockBody()) {
            return isJsCall(bodyExpression, bindingContext);
        }
        if (!(bodyExpression instanceof KtBlockExpression)) {
            return false;
        }
        List<KtExpression> statements = ((KtBlockExpression) bodyExpression).getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        KtExpression ktExpression = (KtExpression) CollectionsKt.singleOrNull((List) statements);
        if (ktExpression == null) {
            return false;
        }
        return isJsCall(ktExpression, bindingContext);
    }

    private static final boolean isJsCall(KtExpression ktExpression, BindingContext bindingContext) {
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktExpression, bindingContext);
        if (resolvedCall != null) {
            return JsCallChecker.INSTANCE.isJsCall(resolvedCall);
        }
        return false;
    }
}
