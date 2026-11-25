package org.jetbrains.kotlin.resolve.calls.smartcasts;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtIfExpression;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;

/* JADX INFO: compiled from: DataFlowValueFactoryImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J$\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0010H\u0016J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001c\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0010H\u0016J*\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u0012H\u0016J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0007\u001a\u00020\bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactoryImpl;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "createDataFlowValue", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "containingDeclarationOrModule", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "resolutionContext", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "receiverValue", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "createDataFlowValueForProperty", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "variableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "usageContainingModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "createDataFlowValueForStableReceiver", "receiver", "isComplexExpression", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataFlowValueFactoryImpl implements DataFlowValueFactory {
    private final LanguageVersionSettings languageVersionSettings;

    public DataFlowValueFactoryImpl(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.languageVersionSettings = languageVersionSettings;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory
    public DataFlowValue createDataFlowValue(ReceiverValue receiverValue, ResolutionContext<?> resolutionContext) {
        Intrinsics.checkNotNullParameter(receiverValue, "receiverValue");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        BindingContext context = resolutionContext.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        return createDataFlowValue(receiverValue, context, resolutionContext.scope.getOwnerDescriptor());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory
    public DataFlowValue createDataFlowValue(ReceiverValue receiverValue, BindingContext bindingContext, DeclarationDescriptor containingDeclarationOrModule) {
        Intrinsics.checkNotNullParameter(receiverValue, "receiverValue");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(containingDeclarationOrModule, "containingDeclarationOrModule");
        if (receiverValue instanceof TransientReceiver ? true : receiverValue instanceof ImplicitReceiver) {
            return createDataFlowValueForStableReceiver(receiverValue);
        }
        if (receiverValue instanceof ExpressionReceiver) {
            KtExpression expression = ((ExpressionReceiver) receiverValue).getExpression();
            KotlinType type = receiverValue.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            return createDataFlowValue(expression, type, bindingContext, containingDeclarationOrModule);
        }
        throw new UnsupportedOperationException("Unsupported receiver value: " + receiverValue.getClass().getName());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory
    public DataFlowValue createDataFlowValueForStableReceiver(ReceiverValue receiver) {
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        IdentifierInfo.Receiver receiver2 = new IdentifierInfo.Receiver(receiver);
        KotlinType type = receiver.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return new DataFlowValue(receiver2, type, null, 4, null);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory
    public DataFlowValue createDataFlowValueForProperty(KtProperty property, VariableDescriptor variableDescriptor, BindingContext bindingContext, ModuleDescriptor usageContainingModule) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        IdentifierInfo.Variable variable = new IdentifierInfo.Variable(variableDescriptor, DataFlowValueKindUtilsKt.variableKind(variableDescriptor, usageContainingModule, bindingContext, property, this.languageVersionSettings), (DataFlowValue) bindingContext.get(BindingContext.BOUND_INITIALIZER_VALUE, variableDescriptor));
        KotlinType type = variableDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return new DataFlowValue(variable, type, null, 4, null);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory
    public DataFlowValue createDataFlowValue(KtExpression expression, KotlinType type, ResolutionContext<?> resolutionContext) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        BindingContext context = resolutionContext.trace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        return createDataFlowValue(expression, type, context, resolutionContext.scope.getOwnerDescriptor());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory
    public DataFlowValue createDataFlowValue(KtExpression expression, KotlinType type, BindingContext bindingContext, DeclarationDescriptor containingDeclarationOrModule) {
        DataFlowValue dataFlowValue;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(containingDeclarationOrModule, "containingDeclarationOrModule");
        if ((expression instanceof KtConstantExpression) && ((KtConstantExpression) expression).getNode().getElementType() == KtNodeTypes.NULL) {
            return DataFlowValue.INSTANCE.nullValue(DescriptorUtilsKt.getBuiltIns(containingDeclarationOrModule));
        }
        if (KotlinTypeKt.isError(type)) {
            return DataFlowValue.ERROR;
        }
        if (KotlinBuiltIns.isNullableNothing(type)) {
            return DataFlowValue.INSTANCE.nullValue(DescriptorUtilsKt.getBuiltIns(containingDeclarationOrModule));
        }
        if (ExpressionTypingUtils.isExclExclExpression(KtPsiUtil.deparenthesize(expression))) {
            dataFlowValue = new DataFlowValue(new IdentifierInfo.Expression(expression, false, 2, null), type, Nullability.NOT_NULL);
        } else if (isComplexExpression(expression)) {
            dataFlowValue = new DataFlowValue(new IdentifierInfo.Expression(expression, true), type, null, 4, null);
        } else {
            IdentifierInfo.Expression idForStableIdentifier = IdentifierInfoKt.getIdForStableIdentifier(expression, bindingContext, containingDeclarationOrModule, this.languageVersionSettings);
            if (idForStableIdentifier == IdentifierInfo.NO.INSTANCE) {
                idForStableIdentifier = new IdentifierInfo.Expression(expression, false, 2, null);
            }
            return new DataFlowValue(idForStableIdentifier, type, null, 4, null);
        }
        return dataFlowValue;
    }

    private final boolean isComplexExpression(KtExpression expression) {
        KtExpression ktExpressionDeparenthesize;
        if (expression instanceof KtBlockExpression ? true : expression instanceof KtIfExpression ? true : expression instanceof KtWhenExpression) {
            return true;
        }
        if (expression instanceof KtBinaryExpression) {
            if (((KtBinaryExpression) expression).getOperationToken() == KtTokens.ELVIS) {
                return true;
            }
        } else if ((expression instanceof KtParenthesizedExpression) && (ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(expression)) != null && isComplexExpression(ktExpressionDeparenthesize)) {
            return true;
        }
        return false;
    }
}
