package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallResolverUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: SelfCallInNestedObjectConstructorChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/SelfCallInNestedObjectConstructorChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "Visitor", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SelfCallInNestedObjectConstructorChecker implements CallChecker {
    public static final SelfCallInNestedObjectConstructorChecker INSTANCE = new SelfCallInNestedObjectConstructorChecker();

    private SelfCallInNestedObjectConstructorChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor callableDescriptor = resolvedCall.get$suspendPropertyDescriptor();
        Call call = resolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        if ((callableDescriptor instanceof ConstructorDescriptor) && CallResolverUtilKt.isSuperOrDelegatingConstructorCall(call)) {
            DeclarationDescriptor declarationDescriptor = context.getResolutionContext().scope.getOwnerDescriptor().getContainingModule();
            ClassDescriptor classDescriptor = declarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) declarationDescriptor : null;
            if (classDescriptor != null && classDescriptor.getClassKind() == ClassKind.OBJECT) {
                DeclarationDescriptor declarationDescriptor2 = classDescriptor.getContainingModule();
                ClassDescriptor classDescriptor2 = declarationDescriptor2 instanceof ClassDescriptor ? (ClassDescriptor) declarationDescriptor2 : null;
                if (classDescriptor2 != null && Intrinsics.areEqual(((ConstructorDescriptor) callableDescriptor).getConstructedClass(), classDescriptor2)) {
                    Visitor visitor = new Visitor(classDescriptor2, context.getTrace(), context.getLanguageVersionSettings());
                    KtValueArgumentList valueArgumentList = resolvedCall.getCall().getValueArgumentList();
                    if (valueArgumentList != null) {
                        valueArgumentList.accept(visitor);
                    }
                }
            }
        }
    }

    /* JADX INFO: compiled from: SelfCallInNestedObjectConstructorChecker.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u001a\u0010\u0013\u001a\u00020\u00102\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0012H\u0002J\u0010\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u0012H\u0016J\u0010\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u001bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/SelfCallInNestedObjectConstructorChecker$Visitor;", "Lorg/jetbrains/kotlin/psi/KtVisitorVoid;", "containingClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "getContainingClass", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "checkArgument", "", "argumentExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "checkReceiver", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "argument", "visitExpression", "expression", "visitKtElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Visitor extends KtVisitorVoid {
        private final ClassDescriptor containingClass;
        private final LanguageVersionSettings languageVersionSettings;
        private final BindingTrace trace;

        public final ClassDescriptor getContainingClass() {
            return this.containingClass;
        }

        public final BindingTrace getTrace() {
            return this.trace;
        }

        public final LanguageVersionSettings getLanguageVersionSettings() {
            return this.languageVersionSettings;
        }

        public Visitor(ClassDescriptor containingClass, BindingTrace trace, LanguageVersionSettings languageVersionSettings) {
            Intrinsics.checkNotNullParameter(containingClass, "containingClass");
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            this.containingClass = containingClass;
            this.trace = trace;
            this.languageVersionSettings = languageVersionSettings;
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitKtElement(KtElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            element.acceptChildren(this, null);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitExpression(KtExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            checkArgument(expression);
            expression.acceptChildren(this, null);
        }

        private final void checkArgument(KtExpression argumentExpression) {
            BindingContext context = this.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            Call call = CallUtilKt.getCall(argumentExpression, context);
            if (call == null) {
                return;
            }
            BindingContext context2 = this.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
            ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(call, context2);
            if (resolvedCall == null) {
                return;
            }
            checkReceiver(resolvedCall.getDispatchReceiver(), argumentExpression);
        }

        private final void checkReceiver(ReceiverValue receiver, KtExpression argument) {
            KotlinType type;
            if (receiver == null || (type = receiver.getType()) == null) {
                return;
            }
            ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
            ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            if (classDescriptor != null && DescriptorUtils.isSubclass(classDescriptor, this.containingClass)) {
                this.trace.report(Errors.SELF_CALL_IN_NESTED_OBJECT_CONSTRUCTOR.on(this.languageVersionSettings, argument));
            }
        }
    }
}
