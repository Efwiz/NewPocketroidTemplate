package org.jetbrains.kotlin.resolve.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtInitializerList;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ClassValueReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: EnumCompanionInEnumConstructorCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u000fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u0004\u0018\u00010\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/EnumCompanionInEnumConstructorCallChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "()V", "resolvedDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "getResolvedDescriptor", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;)Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "Visitor", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EnumCompanionInEnumConstructorCallChecker implements DeclarationChecker {
    public static final EnumCompanionInEnumConstructorCallChecker INSTANCE = new EnumCompanionInEnumConstructorCallChecker();

    private EnumCompanionInEnumConstructorCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        ClassDescriptor classDescriptorMo6346getCompanionObjectDescriptor;
        KtInitializerList initializerList;
        List<KtSuperTypeListEntry> initializers;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if ((declaration instanceof KtEnumEntry) && (descriptor instanceof ClassDescriptor)) {
            ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
            if (classDescriptor.getKind() != ClassKind.ENUM_ENTRY) {
                return;
            }
            DeclarationDescriptor declarationDescriptor = classDescriptor.getContainingModule();
            Object obj = null;
            ClassDescriptor classDescriptor2 = declarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) declarationDescriptor : null;
            if (classDescriptor2 == null || (classDescriptorMo6346getCompanionObjectDescriptor = classDescriptor2.mo6346getCompanionObjectDescriptor()) == null || (initializerList = ((KtEnumEntry) declaration).getInitializerList()) == null || (initializers = initializerList.getInitializers()) == null) {
                return;
            }
            for (Object obj2 : initializers) {
                if (obj2 instanceof KtSuperTypeCallEntry) {
                    obj = obj2;
                    break;
                }
            }
            KtSuperTypeCallEntry ktSuperTypeCallEntry = (KtSuperTypeCallEntry) obj;
            if (ktSuperTypeCallEntry == null) {
                return;
            }
            BindingTrace trace = context.getTrace();
            BindingContext context2 = trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
            ktSuperTypeCallEntry.acceptChildren(new Visitor(classDescriptor2, classDescriptorMo6346getCompanionObjectDescriptor, context2, trace, context.getLanguageVersionSettings().supportsFeature(LanguageFeature.ProhibitAccessToEnumCompanionMembersInEnumConstructorCall)));
        }
    }

    /* JADX INFO: compiled from: EnumCompanionInEnumConstructorCallChecker.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/EnumCompanionInEnumConstructorCallChecker$Visitor;", "Lorg/jetbrains/kotlin/psi/KtVisitorVoid;", "enumDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "companionDescriptor", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "reporter", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "reportError", "", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;Z)V", "getCompanionDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "getEnumDescriptor", "getReportError", "()Z", "getReporter", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "analyzeExpression", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "visitElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "visitExpression", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Visitor extends KtVisitorVoid {
        private final ClassDescriptor companionDescriptor;
        private final BindingContext context;
        private final ClassDescriptor enumDescriptor;
        private final boolean reportError;
        private final DiagnosticSink reporter;

        public final ClassDescriptor getEnumDescriptor() {
            return this.enumDescriptor;
        }

        public final ClassDescriptor getCompanionDescriptor() {
            return this.companionDescriptor;
        }

        public final BindingContext getContext() {
            return this.context;
        }

        public final DiagnosticSink getReporter() {
            return this.reporter;
        }

        public final boolean getReportError() {
            return this.reportError;
        }

        public Visitor(ClassDescriptor enumDescriptor, ClassDescriptor companionDescriptor, BindingContext context, DiagnosticSink reporter, boolean z) {
            Intrinsics.checkNotNullParameter(enumDescriptor, "enumDescriptor");
            Intrinsics.checkNotNullParameter(companionDescriptor, "companionDescriptor");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(reporter, "reporter");
            this.enumDescriptor = enumDescriptor;
            this.companionDescriptor = companionDescriptor;
            this.context = context;
            this.reporter = reporter;
            this.reportError = z;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
        public void visitElement(PsiElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            element.acceptChildren(this);
        }

        @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
        public void visitExpression(KtExpression expression) {
            KtValueArgumentList valueArgumentList;
            Intrinsics.checkNotNullParameter(expression, "expression");
            if (analyzeExpression(expression)) {
                expression.acceptChildren(this);
            } else {
                if (!(expression instanceof KtCallExpression) || (valueArgumentList = ((KtCallExpression) expression).getValueArgumentList()) == null) {
                    return;
                }
                valueArgumentList.acceptChildren(this);
            }
        }

        private final boolean analyzeExpression(KtExpression expression) {
            ResolvedCall<? extends CallableDescriptor> resolvedCall;
            DiagnosticFactory1<KtExpression, ClassDescriptor> diagnosticFactory1;
            if ((expression.getLightParent() instanceof KtCallExpression) || (resolvedCall = CallUtilKt.getResolvedCall(expression, this.context)) == null) {
                return true;
            }
            DeclarationDescriptor resolvedDescriptor = EnumCompanionInEnumConstructorCallChecker.INSTANCE.getResolvedDescriptor(resolvedCall.getDispatchReceiver());
            DeclarationDescriptor resolvedDescriptor2 = EnumCompanionInEnumConstructorCallChecker.INSTANCE.getResolvedDescriptor(resolvedCall.getExtensionReceiver());
            boolean zAreEqual = Intrinsics.areEqual(resolvedDescriptor, this.companionDescriptor);
            boolean zAreEqual2 = Intrinsics.areEqual(resolvedDescriptor2, this.companionDescriptor);
            if (!zAreEqual && !zAreEqual2) {
                return true;
            }
            KtExpression selectorExpression = null;
            KtQualifiedExpression ktQualifiedExpression = expression instanceof KtQualifiedExpression ? (KtQualifiedExpression) expression : null;
            KtExpression receiverExpression = ktQualifiedExpression != null ? ktQualifiedExpression.getReceiverExpression() : null;
            if (receiverExpression instanceof KtSimpleNameExpression) {
                selectorExpression = receiverExpression;
            } else if (receiverExpression instanceof KtQualifiedExpression) {
                selectorExpression = ((KtQualifiedExpression) receiverExpression).getSelectorExpression();
            }
            if (selectorExpression != null) {
                expression = selectorExpression;
            }
            if (this.reportError) {
                diagnosticFactory1 = Errors.UNINITIALIZED_ENUM_COMPANION;
            } else {
                diagnosticFactory1 = Errors.UNINITIALIZED_ENUM_COMPANION_WARNING;
            }
            this.reporter.report(diagnosticFactory1.on(expression, this.enumDescriptor));
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DeclarationDescriptor getResolvedDescriptor(ReceiverValue receiverValue) {
        if ((receiverValue instanceof ClassValueReceiver) || (receiverValue instanceof ImplicitClassReceiver)) {
            return receiverValue.getType().unwrap().getConstructor().getThis$0();
        }
        return null;
    }
}
