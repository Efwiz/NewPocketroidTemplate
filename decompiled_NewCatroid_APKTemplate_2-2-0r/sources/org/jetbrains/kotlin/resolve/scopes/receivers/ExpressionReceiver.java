package org.jetbrains.kotlin.resolve.scopes.receivers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.psi.KtConstructorDelegationReferenceExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ExpressionReceiver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/ExpressionReceiver;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ExpressionReceiver extends ReceiverValue {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    KtExpression getExpression();

    /* JADX INFO: compiled from: ExpressionReceiver.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0003\u000e\u000f\u0010B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0014\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/ExpressionReceiver$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ExpressionReceiver;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "isContextClassReceiverReference", "", "Lorg/jetbrains/kotlin/psi/KtReferenceExpression;", "ExpressionReceiverImpl", "SuperExpressionReceiver", "ThisExpressionClassReceiver", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        /* JADX INFO: compiled from: ExpressionReceiver.kt */
        @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\b\u0012\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0010\u0010\f\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u0006H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/ExpressionReceiver$Companion$ExpressionReceiverImpl;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/AbstractReceiverValue;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ExpressionReceiver;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "original", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "(Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;)V", "getExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "replaceType", "newType", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private static class ExpressionReceiverImpl extends AbstractReceiverValue implements ExpressionReceiver {
            private final KtExpression expression;

            @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver
            public KtExpression getExpression() {
                return this.expression;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public ExpressionReceiverImpl(KtExpression expression, KotlinType type, ReceiverValue receiverValue) {
                super(type, receiverValue);
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(type, "type");
                this.expression = expression;
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
            /* JADX INFO: renamed from: replaceType */
            public ExpressionReceiverImpl mo7466replaceType(KotlinType newType) {
                Intrinsics.checkNotNullParameter(newType, "newType");
                return new ExpressionReceiverImpl(getExpression(), newType, getOriginal());
            }

            public String toString() {
                return getType() + " {" + getExpression() + ": " + getExpression().getText() + '}';
            }
        }

        /* JADX INFO: compiled from: ExpressionReceiver.kt */
        @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B'\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\bH\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/ExpressionReceiver$Companion$ThisExpressionClassReceiver;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ExpressionReceiver$Companion$ExpressionReceiverImpl;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ThisClassReceiver;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "original", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;)V", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "replaceType", "newType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private static final class ThisExpressionClassReceiver extends ExpressionReceiverImpl implements ThisClassReceiver {
            private final ClassDescriptor classDescriptor;

            @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ThisClassReceiver
            public ClassDescriptor getClassDescriptor() {
                return this.classDescriptor;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public ThisExpressionClassReceiver(ClassDescriptor classDescriptor, KtExpression expression, KotlinType type, ReceiverValue receiverValue) {
                super(expression, type, receiverValue);
                Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(type, "type");
                this.classDescriptor = classDescriptor;
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver.Companion.ExpressionReceiverImpl, org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
            /* JADX INFO: renamed from: replaceType */
            public ThisExpressionClassReceiver mo7466replaceType(KotlinType newType) {
                Intrinsics.checkNotNullParameter(newType, "newType");
                return new ThisExpressionClassReceiver(getClassDescriptor(), getExpression(), newType, getOriginal());
            }
        }

        /* JADX INFO: compiled from: ExpressionReceiver.kt */
        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B'\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u0010\u0010\r\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u0004H\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/ExpressionReceiver$Companion$SuperExpressionReceiver;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ExpressionReceiver$Companion$ExpressionReceiverImpl;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/SuperCallReceiverValue;", "thisType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", ModuleXmlParser.TYPE, "original", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;)V", "getThisType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "replaceType", "newType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private static final class SuperExpressionReceiver extends ExpressionReceiverImpl implements SuperCallReceiverValue {
            private final KotlinType thisType;

            @Override // org.jetbrains.kotlin.resolve.scopes.receivers.SuperCallReceiverValue
            public KotlinType getThisType() {
                return this.thisType;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public SuperExpressionReceiver(KotlinType thisType, KtExpression expression, KotlinType type, ReceiverValue receiverValue) {
                super(expression, type, receiverValue);
                Intrinsics.checkNotNullParameter(thisType, "thisType");
                Intrinsics.checkNotNullParameter(expression, "expression");
                Intrinsics.checkNotNullParameter(type, "type");
                this.thisType = thisType;
            }

            @Override // org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver.Companion.ExpressionReceiverImpl, org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue
            /* JADX INFO: renamed from: replaceType */
            public SuperExpressionReceiver mo7466replaceType(KotlinType newType) {
                Intrinsics.checkNotNullParameter(newType, "newType");
                return new SuperExpressionReceiver(getThisType(), getExpression(), newType, getOriginal());
            }
        }

        public final ExpressionReceiver create(KtExpression expression, KotlinType type, BindingContext bindingContext) {
            KtReferenceExpression instanceReference;
            KotlinType kotlinType;
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            if (expression instanceof KtThisExpression) {
                instanceReference = ((KtThisExpression) expression).getInstanceReference();
            } else {
                instanceReference = expression instanceof KtConstructorDelegationReferenceExpression ? (KtReferenceExpression) expression : null;
            }
            if (instanceReference != null) {
                DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) bindingContext.get(BindingContext.REFERENCE_TARGET, instanceReference);
                if ((declarationDescriptor instanceof ClassDescriptor) && !isContextClassReceiverReference(instanceReference, bindingContext)) {
                    ClassDescriptor original = ((ClassDescriptor) declarationDescriptor).getOriginal();
                    Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                    return new ThisExpressionClassReceiver(original, expression, type, null);
                }
            } else if ((expression instanceof KtSuperExpression) && (kotlinType = (KotlinType) bindingContext.get(BindingContext.THIS_TYPE_FOR_SUPER_EXPRESSION, expression)) != null) {
                return new SuperExpressionReceiver(kotlinType, expression, type, null);
            }
            return new ExpressionReceiverImpl(expression, type, null);
        }

        private final boolean isContextClassReceiverReference(KtReferenceExpression ktReferenceExpression, BindingContext bindingContext) {
            ReceiverParameterDescriptor receiverParameterDescriptor = (ReceiverParameterDescriptor) bindingContext.get(BindingContext.THIS_REFERENCE_TARGET, ktReferenceExpression);
            return (receiverParameterDescriptor != null ? receiverParameterDescriptor.getValue() : null) instanceof ContextClassReceiver;
        }
    }
}
