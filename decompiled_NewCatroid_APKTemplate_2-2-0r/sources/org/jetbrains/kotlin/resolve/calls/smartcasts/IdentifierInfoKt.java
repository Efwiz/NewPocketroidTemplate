package org.jetbrains.kotlin.resolve.calls.smartcasts;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.google.common.collect.Multimap;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPostfixExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ContextReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: IdentifierInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0014\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0002\u001a(\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u001a*\u0010\u0013\u001a\u00020\n2\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u0012H\u0000\u001a\u0012\u0010\u0016\u001a\u00020\n2\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0002\u001a\"\u0010\u0016\u001a\u00020\n2\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0018\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002\u001a*\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\n2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020\n2\u0006\u0010 \u001a\u00020!H\u0002¨\u0006\""}, d2 = {"findReceiverByLabelOrGetDefault", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "descriptorOfThisReceiver", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "default", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "labelName", "", "getIdForImplicitReceiver", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "receiverValue", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "getIdForSimpleNameExpression", "simpleNameExpression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "containingDeclarationOrModule", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getIdForStableIdentifier", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getIdForThisReceiver", "postfix", "argumentInfo", "op", "Lorg/jetbrains/kotlin/lexer/KtToken;", SchemaSymbols.ATTVAL_QUALIFIED, "receiverInfo", "receiverType", "Lorg/jetbrains/kotlin/types/KotlinType;", "selectorInfo", "safe", "", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IdentifierInfoKt {
    public static final IdentifierInfo getIdForStableIdentifier(KtExpression ktExpression, BindingContext bindingContext, DeclarationDescriptor containingDeclarationOrModule, LanguageVersionSettings languageVersionSettings) {
        KtExpression ktExpressionDeparenthesize;
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(containingDeclarationOrModule, "containingDeclarationOrModule");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (ktExpression != null && ktExpression != (ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(ktExpression))) {
            return getIdForStableIdentifier(ktExpressionDeparenthesize, bindingContext, containingDeclarationOrModule, languageVersionSettings);
        }
        if (ktExpression instanceof KtQualifiedExpression) {
            KtQualifiedExpression ktQualifiedExpression = (KtQualifiedExpression) ktExpression;
            KtExpression receiverExpression = ktQualifiedExpression.getReceiverExpression();
            KtExpression selectorExpression = ktQualifiedExpression.getSelectorExpression();
            return qualified(getIdForStableIdentifier(receiverExpression, bindingContext, containingDeclarationOrModule, languageVersionSettings), bindingContext.getType(receiverExpression), getIdForStableIdentifier(selectorExpression, bindingContext, containingDeclarationOrModule, languageVersionSettings), ktQualifiedExpression.getOperationSign() == KtTokens.SAFE_ACCESS);
        }
        if (ktExpression instanceof KtBinaryExpressionWithTypeRHS) {
            KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS = (KtBinaryExpressionWithTypeRHS) ktExpression;
            KtExpression left = ktBinaryExpressionWithTypeRHS.getLeft();
            Intrinsics.checkNotNullExpressionValue(left, "getLeft(...)");
            KtTypeReference right = ktBinaryExpressionWithTypeRHS.getRight();
            IElementType referencedNameElementType = ktBinaryExpressionWithTypeRHS.getOperationReference().getReferencedNameElementType();
            if (Intrinsics.areEqual(referencedNameElementType, KtTokens.IS_KEYWORD) || Intrinsics.areEqual(referencedNameElementType, KtTokens.AS_KEYWORD)) {
                return IdentifierInfo.NO.INSTANCE;
            }
            return new IdentifierInfo.SafeCast(getIdForStableIdentifier(left, bindingContext, containingDeclarationOrModule, languageVersionSettings), bindingContext.getType(left), (KotlinType) bindingContext.get(BindingContext.TYPE, right));
        }
        if (ktExpression instanceof KtSimpleNameExpression) {
            return getIdForSimpleNameExpression((KtSimpleNameExpression) ktExpression, bindingContext, containingDeclarationOrModule, languageVersionSettings);
        }
        if (ktExpression instanceof KtThisExpression) {
            KtThisExpression ktThisExpression = (KtThisExpression) ktExpression;
            DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) bindingContext.get(BindingContext.REFERENCE_TARGET, ktThisExpression.getInstanceReference());
            String labelName = ktThisExpression.getLabelName();
            if (labelName == null) {
                return getIdForThisReceiver(declarationDescriptor);
            }
            return getIdForThisReceiver(declarationDescriptor, bindingContext, labelName);
        }
        if (ktExpression instanceof KtPostfixExpression) {
            KtPostfixExpression ktPostfixExpression = (KtPostfixExpression) ktExpression;
            IElementType referencedNameElementType2 = ktPostfixExpression.getOperationReference().getReferencedNameElementType();
            if (referencedNameElementType2 == KtTokens.PLUSPLUS || referencedNameElementType2 == KtTokens.MINUSMINUS) {
                return postfix(getIdForStableIdentifier(ktPostfixExpression.getBaseExpression(), bindingContext, containingDeclarationOrModule, languageVersionSettings), (KtToken) referencedNameElementType2);
            }
            return IdentifierInfo.NO.INSTANCE;
        }
        return IdentifierInfo.NO.INSTANCE;
    }

    private static final IdentifierInfo getIdForSimpleNameExpression(KtSimpleNameExpression ktSimpleNameExpression, BindingContext bindingContext, DeclarationDescriptor declarationDescriptor, LanguageVersionSettings languageVersionSettings) {
        DeclarationDescriptor declarationDescriptor2 = (DeclarationDescriptor) bindingContext.get(BindingContext.REFERENCE_TARGET, ktSimpleNameExpression);
        if (declarationDescriptor2 instanceof VariableDescriptor) {
            KtSimpleNameExpression ktSimpleNameExpression2 = ktSimpleNameExpression;
            ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktSimpleNameExpression2, bindingContext);
            VariableDescriptor variableDescriptor = (VariableDescriptor) declarationDescriptor2;
            IdentifierInfo.Variable variable = new IdentifierInfo.Variable(variableDescriptor, DataFlowValueKindUtilsKt.variableKind(variableDescriptor, DescriptorUtils.getContainingModuleOrNull(declarationDescriptor), bindingContext, ktSimpleNameExpression2, languageVersionSettings), (DataFlowValue) bindingContext.get(BindingContext.BOUND_INITIALIZER_VALUE, declarationDescriptor2));
            ReceiverValue dispatchReceiver = resolvedCall != null ? resolvedCall.getDispatchReceiver() : null;
            if (dispatchReceiver == null) {
                return variable;
            }
            IdentifierInfo idForImplicitReceiver = getIdForImplicitReceiver(dispatchReceiver);
            if (idForImplicitReceiver == null) {
                return variable;
            }
            Call call = resolvedCall.getCall();
            Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
            return qualified(idForImplicitReceiver, dispatchReceiver.getType(), variable, CallUtilKt.isSafeCall(call));
        }
        if (!(declarationDescriptor2 instanceof ClassDescriptor)) {
            return declarationDescriptor2 instanceof PackageViewDescriptor ? new IdentifierInfo.PackageOrClass(declarationDescriptor2) : IdentifierInfo.NO.INSTANCE;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) declarationDescriptor2;
        if (classDescriptor.getKind() == ClassKind.ENUM_ENTRY && languageVersionSettings.supportsFeature(LanguageFeature.SoundSmartcastForEnumEntries)) {
            return new IdentifierInfo.EnumEntry(classDescriptor);
        }
        return new IdentifierInfo.PackageOrClass(declarationDescriptor2);
    }

    private static final IdentifierInfo getIdForImplicitReceiver(ReceiverValue receiverValue) {
        if (receiverValue instanceof ContextReceiver) {
            return new IdentifierInfo.Receiver(receiverValue);
        }
        if (receiverValue instanceof ImplicitReceiver) {
            return getIdForThisReceiver(((ImplicitReceiver) receiverValue).getDeclarationDescriptor());
        }
        return null;
    }

    private static final IdentifierInfo getIdForThisReceiver(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor instanceof CallableDescriptor) {
            ReceiverParameterDescriptor extensionReceiverParameter = ((CallableDescriptor) declarationDescriptor).getExtensionReceiverParameter();
            if (extensionReceiverParameter == null) {
                throw new IllegalStateException(("'This' refers to the callable member without a receiver parameter: " + declarationDescriptor).toString());
            }
            ReceiverValue value = extensionReceiverParameter.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return new IdentifierInfo.Receiver(value);
        }
        if (!(declarationDescriptor instanceof ClassDescriptor)) {
            return IdentifierInfo.NO.INSTANCE;
        }
        ReceiverValue value2 = ((ClassDescriptor) declarationDescriptor).getThisAsReceiverParameter().getValue();
        Intrinsics.checkNotNullExpressionValue(value2, "getValue(...)");
        return new IdentifierInfo.Receiver(value2);
    }

    private static final IdentifierInfo getIdForThisReceiver(DeclarationDescriptor declarationDescriptor, BindingContext bindingContext, String str) {
        if (declarationDescriptor instanceof CallableDescriptor) {
            ReceiverValue value = findReceiverByLabelOrGetDefault(declarationDescriptor, ((CallableDescriptor) declarationDescriptor).getExtensionReceiverParameter(), bindingContext, str).getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return new IdentifierInfo.Receiver(value);
        }
        if (declarationDescriptor instanceof ClassDescriptor) {
            ReceiverValue value2 = findReceiverByLabelOrGetDefault(declarationDescriptor, ((ClassDescriptor) declarationDescriptor).getThisAsReceiverParameter(), bindingContext, str).getValue();
            Intrinsics.checkNotNullExpressionValue(value2, "getValue(...)");
            return new IdentifierInfo.Receiver(value2);
        }
        return IdentifierInfo.NO.INSTANCE;
    }

    private static final ReceiverParameterDescriptor findReceiverByLabelOrGetDefault(DeclarationDescriptor declarationDescriptor, ReceiverParameterDescriptor receiverParameterDescriptor, BindingContext bindingContext, String str) {
        PropertyDescriptor propertyDescriptor;
        Collection collection;
        ReceiverParameterDescriptor receiverParameterDescriptor2;
        WritableSlice<DeclarationDescriptor, Multimap<String, ReceiverParameterDescriptor>> writableSlice = BindingContext.DESCRIPTOR_TO_CONTEXT_RECEIVER_MAP;
        if (declarationDescriptor instanceof PropertyAccessorDescriptor) {
            PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) declarationDescriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
            propertyDescriptor = correspondingProperty;
        } else {
            propertyDescriptor = declarationDescriptor;
        }
        Multimap multimap = (Multimap) bindingContext.get(writableSlice, propertyDescriptor);
        if (multimap != null && (collection = multimap.get(str)) != null && (receiverParameterDescriptor2 = (ReceiverParameterDescriptor) CollectionsKt.singleOrNull(collection)) != null) {
            return receiverParameterDescriptor2;
        }
        if (receiverParameterDescriptor != null) {
            return receiverParameterDescriptor;
        }
        throw new IllegalStateException(("'This' refers to the callable member without a receiver parameter: " + declarationDescriptor).toString());
    }

    private static final IdentifierInfo postfix(IdentifierInfo identifierInfo, KtToken ktToken) {
        return Intrinsics.areEqual(identifierInfo, IdentifierInfo.NO.INSTANCE) ? IdentifierInfo.NO.INSTANCE : new IdentifierInfo.PostfixIdentifierInfo(identifierInfo, ktToken);
    }

    private static final IdentifierInfo qualified(IdentifierInfo identifierInfo, KotlinType kotlinType, IdentifierInfo identifierInfo2, boolean z) {
        if (Intrinsics.areEqual(identifierInfo, IdentifierInfo.NO.INSTANCE)) {
            return IdentifierInfo.NO.INSTANCE;
        }
        return identifierInfo instanceof IdentifierInfo.PackageOrClass ? identifierInfo2 : new IdentifierInfo.Qualified(identifierInfo, identifierInfo2, z, kotlinType);
    }
}
