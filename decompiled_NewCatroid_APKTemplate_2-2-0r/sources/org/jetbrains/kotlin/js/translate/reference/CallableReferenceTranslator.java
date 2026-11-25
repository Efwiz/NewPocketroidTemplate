package org.jetbrains.kotlin.js.translate.reference;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.DelegatingResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.VarargValueArgument;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.util.CallMaker;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;

/* JADX INFO: compiled from: CallableReferenceTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004H\u0002J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0016\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\rJ*\u0010\u0011\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004H\u0002J*\u0010\u0013\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004H\u0002Jr\u0010\u0014\u001a\u00020\u00042\u000e\u0010\u0015\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\u00162\u0006\u0010\u000f\u001a\u00020\u00172\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0018\u001a\u00020\t2.\u0010\u0019\u001a*\u0012\u0004\u0012\u00020\r\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\u0016\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0004\u0012\u00020\u00040\u001aH\u0002J&\u0010\u001b\u001a\u00020\u0004*\u00020\r2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J8\u0010\u001e\u001a\u00020\u0004*\u00020\r2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u00042\b\u0010 \u001a\u0004\u0018\u00010\u0004H\u0002¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/reference/CallableReferenceTranslator;", "", "()V", "bindIfNecessary", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "function", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "receiver", "isSetterVisible", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "translate", "expression", "Lorg/jetbrains/kotlin/psi/KtCallableReferenceExpression;", "translateForFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "translateForProperty", "translateForPropertyAccessor", "call", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "isSetter", "translator", "Lkotlin/Function4;", "wrapFunctionCallableRef", "name", "", "wrapPropertyCallableRef", "getter", "setter", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallableReferenceTranslator {
    public static final CallableReferenceTranslator INSTANCE = new CallableReferenceTranslator();

    private CallableReferenceTranslator() {
    }

    public final JsExpression translate(KtCallableReferenceExpression expression, TranslationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        KtSimpleNameExpression callableReference = expression.getCallableReference();
        Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
        BindingContext bindingContext = context.bindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "bindingContext(...)");
        ResolvedCall<? extends CallableDescriptor> resolvedCallWithAssert = CallUtilKt.getResolvedCallWithAssert(callableReference, bindingContext);
        CallableDescriptor resultingDescriptor = resolvedCallWithAssert.getResultingDescriptor();
        ReceiverValue extensionReceiver = resolvedCallWithAssert.getExtensionReceiver();
        ReceiverValue dispatchReceiver = resolvedCallWithAssert.getDispatchReceiver();
        if (dispatchReceiver != null) {
            extensionReceiver = dispatchReceiver;
        }
        JsExpression jsExpressionTranslateAsExpression = null;
        if (extensionReceiver != null && !(extensionReceiver instanceof TransientReceiver)) {
            if (extensionReceiver instanceof ImplicitClassReceiver ? true : extensionReceiver instanceof ExtensionReceiver) {
                jsExpressionTranslateAsExpression = context.getDispatchReceiver(JsDescriptorUtils.getReceiverParameterForReceiver(extensionReceiver));
            } else {
                if (!(extensionReceiver instanceof ExpressionReceiver)) {
                    throw new UnsupportedOperationException("Unsupported receiver value: " + extensionReceiver);
                }
                jsExpressionTranslateAsExpression = Translation.translateAsExpression(((ExpressionReceiver) extensionReceiver).getExpression(), context);
            }
        }
        if (resultingDescriptor instanceof PropertyDescriptor) {
            return translateForProperty((PropertyDescriptor) resultingDescriptor, context, expression, jsExpressionTranslateAsExpression);
        }
        if (resultingDescriptor instanceof FunctionDescriptor) {
            return translateForFunction((FunctionDescriptor) resultingDescriptor, context, expression, jsExpressionTranslateAsExpression);
        }
        throw new IllegalArgumentException("Expected property or function: " + resultingDescriptor + ", expression=" + expression.getText());
    }

    private final JsExpression translateForFunction(final FunctionDescriptor descriptor, TranslationContext context, KtCallableReferenceExpression expression, final JsExpression receiver) {
        KtSimpleNameExpression callableReference = expression.getCallableReference();
        Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
        BindingContext bindingContext = context.bindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "bindingContext(...)");
        final ResolvedCall<? extends FunctionDescriptor> functionResolvedCallWithAssert = CallUtilKt.getFunctionResolvedCallWithAssert(callableReference, bindingContext);
        Object obj = context.bindingContext().get(BindingContext.FUNCTION, expression);
        Intrinsics.checkNotNull(obj);
        final SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) obj;
        final int i = (receiver != null || (descriptor.mo6753getDispatchReceiverParameter() == null && descriptor.getExtensionReceiverParameter() == null)) ? 0 : 1;
        final int size = simpleFunctionDescriptor.getValueParameters().size() - i;
        Project project = expression.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        KtCallExpression ktCallExpressionConstructFakeFunctionCall = CodegenUtil.constructFakeFunctionCall(project, size);
        final List<KtValueArgument> valueArguments = ktCallExpressionConstructFakeFunctionCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        JsNameRef jsNameRefMakeRef = null;
        final Call callMakeCall = CallMaker.makeCall(ktCallExpressionConstructFakeFunctionCall, null, null, ktCallExpressionConstructFakeFunctionCall, valueArguments);
        Intrinsics.checkNotNullExpressionValue(callMakeCall, "makeCall(...)");
        DelegatingResolvedCall<FunctionDescriptor> delegatingResolvedCall = new DelegatingResolvedCall<FunctionDescriptor>(functionResolvedCallWithAssert, descriptor, size, simpleFunctionDescriptor, i, valueArguments, callMakeCall, receiver) { // from class: org.jetbrains.kotlin.js.translate.reference.CallableReferenceTranslator$translateForFunction$fakeResolvedCall$1
            final /* synthetic */ FunctionDescriptor $descriptor;
            final /* synthetic */ Call $fakeCall;
            final /* synthetic */ JsExpression $receiver;
            private final List<ResolvedValueArgument> valueArgumentList;
            private final Map<ValueParameterDescriptor, ResolvedValueArgument> valueArgumentMap;

            {
                this.$descriptor = descriptor;
                this.$fakeCall = callMakeCall;
                this.$receiver = receiver;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                int i2 = 0;
                for (ValueParameterDescriptor valueParameterDescriptor : descriptor.getValueParameters()) {
                    if (valueParameterDescriptor.getVarargElementType() != null) {
                        if (i2 == size) {
                            Intrinsics.checkNotNull(valueParameterDescriptor);
                            ResolvedValueArgument varargValueArgument = ArgumentsUtilsKt.hasDefaultValue(valueParameterDescriptor) ? DefaultValueArgument.DEFAULT : new VarargValueArgument();
                            Intrinsics.checkNotNull(varargValueArgument);
                            linkedHashMap.put(valueParameterDescriptor, varargValueArgument);
                        } else if (Intrinsics.areEqual(simpleFunctionDescriptor.getValueParameters().get(i + i2).getType(), valueParameterDescriptor.getVarargElementType())) {
                            Intrinsics.checkNotNull(valueParameterDescriptor);
                            linkedHashMap.put(valueParameterDescriptor, new VarargValueArgument(valueArguments.subList(i2, size)));
                            i2 = size;
                        }
                    }
                    if (i2 < size) {
                        Intrinsics.checkNotNull(valueParameterDescriptor);
                        linkedHashMap.put(valueParameterDescriptor, new ExpressionValueArgument(valueArguments.get(i2)));
                        i2++;
                    } else {
                        Intrinsics.checkNotNull(valueParameterDescriptor);
                        ArgumentsUtilsKt.hasDefaultValue(valueParameterDescriptor);
                        DefaultValueArgument DEFAULT = DefaultValueArgument.DEFAULT;
                        Intrinsics.checkNotNullExpressionValue(DEFAULT, "DEFAULT");
                        linkedHashMap.put(valueParameterDescriptor, DEFAULT);
                    }
                }
                this.valueArgumentMap = linkedHashMap;
                this.valueArgumentList = CollectionsKt.toList(linkedHashMap.values());
            }

            public final Map<ValueParameterDescriptor, ResolvedValueArgument> getValueArgumentMap() {
                return this.valueArgumentMap;
            }

            public final List<ResolvedValueArgument> getValueArgumentList() {
                return this.valueArgumentList;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.DelegatingResolvedCall, org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            /* JADX INFO: renamed from: getCall, reason: from getter */
            public Call get$fakeCall() {
                return this.$fakeCall;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.DelegatingResolvedCall, org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public List<ResolvedValueArgument> getValueArgumentsByIndex() {
                return this.valueArgumentList;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.DelegatingResolvedCall, org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public Map<ValueParameterDescriptor, ResolvedValueArgument> getValueArguments() {
                return this.valueArgumentMap;
            }

            @Override // org.jetbrains.kotlin.resolve.calls.model.DelegatingResolvedCall, org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public ExplicitReceiverKind getExplicitReceiverKind() {
                if (this.$receiver != null) {
                    return DescriptorUtilsKt.isExtension(this.$descriptor) ? ExplicitReceiverKind.EXTENSION_RECEIVER : ExplicitReceiverKind.DISPATCH_RECEIVER;
                }
                ExplicitReceiverKind explicitReceiverKind = super.getExplicitReceiverKind();
                Intrinsics.checkNotNullExpressionValue(explicitReceiverKind, "getExplicitReceiverKind(...)");
                return explicitReceiverKind;
            }
        };
        JsFunction jsFunction = new JsFunction(context.scope(), new JsBlock(), "");
        jsFunction.setSource(expression);
        if (descriptor.mo6753getDispatchReceiverParameter() != null || descriptor.getExtensionReceiverParameter() != null || receiver != null) {
            JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(Namer.getReceiverParameterName());
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
            List<JsParameter> parameters = jsFunction.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            parameters.add(new JsParameter(jsNameDeclareTemporaryName));
            jsNameRefMakeRef = jsNameDeclareTemporaryName.makeRef();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int i2 = 0;
        for (ValueArgument valueArgument : callMakeCall.getValueArguments()) {
            int i3 = i2 + 1;
            JsName jsNameDeclareTemporaryName2 = JsScope.declareTemporaryName(simpleFunctionDescriptor.getValueParameters().get(i2).getShortName().asString());
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName2, "declareTemporaryName(...)");
            List<JsParameter> parameters2 = jsFunction.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
            parameters2.add(new JsParameter(jsNameDeclareTemporaryName2));
            JsNameRef jsNameRefMakeRef2 = jsNameDeclareTemporaryName2.makeRef();
            Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef2, "makeRef(...)");
            MetadataProperties.setType(jsNameRefMakeRef2, context.getCurrentModule().getBuiltIns().getAnyType());
            KtExpression argumentExpression = valueArgument.getArgumentExpression();
            Intrinsics.checkNotNull(argumentExpression);
            linkedHashMap.put(argumentExpression, jsNameRefMakeRef2);
            i2 = i3;
        }
        TranslationContext translationContextInner = context.innerBlock(jsFunction.getBody()).innerContextWithAliasesForExpressions(linkedHashMap).inner(descriptor);
        Intrinsics.checkNotNullExpressionValue(translationContextInner, "inner(...)");
        ValueParameterDescriptor continuationParameterDescriptor = translationContextInner.getContinuationParameterDescriptor();
        if (continuationParameterDescriptor != null) {
            List<JsParameter> parameters3 = jsFunction.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters3, "getParameters(...)");
            parameters3.add(new JsParameter(context.getNameForDescriptor(continuationParameterDescriptor)));
            translationContextInner = translationContextInner.innerContextWithDescriptorsAliased(MapsKt.mapOf(TuplesKt.to(continuationParameterDescriptor, JsAstUtils.stateMachineReceiver())));
            Intrinsics.checkNotNullExpressionValue(translationContextInner, "innerContextWithDescriptorsAliased(...)");
        }
        if (descriptor.isSuspend()) {
            UtilsKt.fillCoroutineMetadata(jsFunction, translationContextInner, simpleFunctionDescriptor, false);
        }
        JsExpression jsExpressionTranslate = CallTranslator.translate(translationContextInner, delegatingResolvedCall, jsNameRefMakeRef);
        List<JsStatement> statements = jsFunction.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        statements.add(new JsReturn(TranslationUtils.coerce(context, jsExpressionTranslate, context.getCurrentModule().getBuiltIns().getAnyType())));
        return wrapFunctionCallableRef(context, receiver, expression.getCallableReference().getReferencedName(), bindIfNecessary(jsFunction, receiver));
    }

    private final JsExpression translateForProperty(final PropertyDescriptor descriptor, TranslationContext context, KtCallableReferenceExpression expression, final JsExpression receiver) {
        KtSimpleNameExpression callableReference = expression.getCallableReference();
        Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
        BindingContext bindingContext = context.bindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "bindingContext(...)");
        final ResolvedCall<? extends PropertyDescriptor> propertyResolvedCallWithAssert = CallUtilKt.getPropertyResolvedCallWithAssert(callableReference, bindingContext);
        DelegatingResolvedCall<PropertyDescriptor> delegatingResolvedCall = new DelegatingResolvedCall<PropertyDescriptor>(propertyResolvedCallWithAssert) { // from class: org.jetbrains.kotlin.js.translate.reference.CallableReferenceTranslator$translateForProperty$call$1
            @Override // org.jetbrains.kotlin.resolve.calls.model.DelegatingResolvedCall, org.jetbrains.kotlin.resolve.calls.model.ResolvedCall
            public ExplicitReceiverKind getExplicitReceiverKind() {
                if (receiver != null) {
                    return DescriptorUtilsKt.isExtension(descriptor) ? ExplicitReceiverKind.EXTENSION_RECEIVER : ExplicitReceiverKind.DISPATCH_RECEIVER;
                }
                ExplicitReceiverKind explicitReceiverKind = super.getExplicitReceiverKind();
                Intrinsics.checkNotNullExpressionValue(explicitReceiverKind, "getExplicitReceiverKind(...)");
                return explicitReceiverKind;
            }
        };
        KtCallableReferenceExpression ktCallableReferenceExpression = expression;
        return wrapPropertyCallableRef(context, receiver, descriptor, expression.getCallableReference().getReferencedName(), translateForPropertyAccessor(delegatingResolvedCall, ktCallableReferenceExpression, descriptor, context, receiver, false, new Function4<TranslationContext, ResolvedCall<? extends PropertyDescriptor>, JsExpression, JsExpression, JsExpression>() { // from class: org.jetbrains.kotlin.js.translate.reference.CallableReferenceTranslator$translateForProperty$getter$1
            @Override // kotlin.jvm.functions.Function4
            public final JsExpression invoke(TranslationContext translationContext, ResolvedCall<? extends PropertyDescriptor> resolvedCall, JsExpression jsExpression, JsExpression jsExpression2) {
                Intrinsics.checkNotNullParameter(translationContext, "translationContext");
                Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
                Intrinsics.checkNotNullParameter(jsExpression, "<anonymous parameter 2>");
                return CallTranslator.INSTANCE.translateGet(translationContext, resolvedCall, jsExpression2);
            }
        }), isSetterVisible(descriptor, context) ? translateForPropertyAccessor(delegatingResolvedCall, ktCallableReferenceExpression, descriptor, context, receiver, true, new CallableReferenceTranslator$translateForProperty$setter$1(CallTranslator.INSTANCE)) : null);
    }

    private final boolean isSetterVisible(PropertyDescriptor descriptor, TranslationContext context) {
        PropertySetterDescriptor setter = descriptor.getSetter();
        if (setter == null) {
            return false;
        }
        if (!Intrinsics.areEqual(setter.getVisibility(), DescriptorVisibilities.PRIVATE)) {
            return true;
        }
        ClassDescriptor classDescriptor = context.getClassDescriptor();
        if (classDescriptor == null) {
            return false;
        }
        Sequence sequenceFilter = SequencesKt.filter(SequencesKt.generateSequence(classDescriptor, new Function1<DeclarationDescriptor, DeclarationDescriptor>() { // from class: org.jetbrains.kotlin.js.translate.reference.CallableReferenceTranslator$isSetterVisible$outerClasses$1
            @Override // kotlin.jvm.functions.Function1
            public final DeclarationDescriptor invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getContainingModule();
            }
        }), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.js.translate.reference.CallableReferenceTranslator$isSetterVisible$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof ClassDescriptor);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return SequencesKt.contains(sequenceFilter, descriptor.getContainingModule());
    }

    private final JsExpression translateForPropertyAccessor(ResolvedCall<? extends PropertyDescriptor> call, KtExpression expression, PropertyDescriptor descriptor, TranslationContext context, JsExpression receiver, boolean isSetter, Function4<? super TranslationContext, ? super ResolvedCall<? extends PropertyDescriptor>, ? super JsExpression, ? super JsExpression, ? extends JsExpression> translator) {
        JsNameRef jsNameRefMakeRef;
        SourceInfoAwareJsNode sourceInfoAwareJsNode;
        JsFunction jsFunction = new JsFunction(context.scope(), new JsBlock(), "");
        jsFunction.setSource(expression);
        TranslationContext translationContextInnerBlock = context.innerBlock(jsFunction.getBody());
        Intrinsics.checkNotNullExpressionValue(translationContextInnerBlock, "innerBlock(...)");
        if (descriptor.mo6753getDispatchReceiverParameter() == null && descriptor.getExtensionReceiverParameter() == null) {
            jsNameRefMakeRef = null;
        } else {
            JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(Namer.getReceiverParameterName());
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
            List<JsParameter> parameters = jsFunction.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            parameters.add(new JsParameter(jsNameDeclareTemporaryName));
            jsNameRefMakeRef = jsNameDeclareTemporaryName.makeRef();
        }
        if (isSetter) {
            JsName jsNameDeclareTemporaryName2 = JsScope.declareTemporaryName("value");
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName2, "declareTemporaryName(...)");
            List<JsParameter> parameters2 = jsFunction.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters2, "getParameters(...)");
            parameters2.add(new JsParameter(jsNameDeclareTemporaryName2));
            SourceInfoAwareJsNode sourceInfoAwareJsNodeMakeRef = jsNameDeclareTemporaryName2.makeRef();
            Intrinsics.checkNotNull(sourceInfoAwareJsNodeMakeRef);
            sourceInfoAwareJsNode = (JsExpression) sourceInfoAwareJsNodeMakeRef;
        } else {
            sourceInfoAwareJsNode = (JsExpression) new JsNullLiteral();
        }
        JsExpression jsExpressionInvoke = translator.invoke(translationContextInnerBlock, call, sourceInfoAwareJsNode, jsNameRefMakeRef);
        List<JsStatement> statements = jsFunction.getBody().getStatements();
        Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
        statements.add(isSetter ? jsExpressionInvoke.makeStmt() : new JsReturn(jsExpressionInvoke));
        JsBlock body = jsFunction.getBody();
        PsiElement finalElement = UtilsKt.getFinalElement(expression);
        body.setSource(finalElement instanceof LeafPsiElement ? (LeafPsiElement) finalElement : null);
        return bindIfNecessary(jsFunction, receiver);
    }

    private final JsExpression bindIfNecessary(JsFunction function, JsExpression receiver) {
        if (receiver != null) {
            return new JsInvocation(new JsNameRef("bind", function), new JsNullLiteral(), receiver);
        }
        return function;
    }

    private final JsExpression wrapPropertyCallableRef(TranslationContext translationContext, JsExpression jsExpression, PropertyDescriptor propertyDescriptor, String str, JsExpression jsExpression2, JsExpression jsExpression3) {
        int i = ((propertyDescriptor.getContainingModule() instanceof ClassDescriptor) || propertyDescriptor.getExtensionReceiverParameter() != null) ? 1 : 0;
        if (jsExpression != null) {
            i--;
        }
        JsStringLiteral jsStringLiteral = new JsStringLiteral(str);
        JsIntLiteral jsIntLiteral = new JsIntLiteral(i);
        JsExpression referenceToIntrinsic = translationContext.getReferenceToIntrinsic(Namer.PROPERTY_CALLABLE_REF);
        Intrinsics.checkNotNullExpressionValue(referenceToIntrinsic, "getReferenceToIntrinsic(...)");
        JsInvocation jsInvocation = new JsInvocation(referenceToIntrinsic, jsStringLiteral, jsIntLiteral, jsExpression2);
        if (jsExpression3 != null) {
            List<JsExpression> arguments = jsInvocation.getArguments();
            Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
            arguments.add(jsExpression3);
        }
        MetadataProperties.setCallableReferenceReceiver(jsInvocation, jsExpression);
        return jsInvocation;
    }

    private final JsExpression wrapFunctionCallableRef(TranslationContext translationContext, JsExpression jsExpression, String str, JsExpression jsExpression2) {
        JsStringLiteral jsStringLiteral = new JsStringLiteral(str);
        JsExpression referenceToIntrinsic = translationContext.getReferenceToIntrinsic(Namer.FUNCTION_CALLABLE_REF);
        Intrinsics.checkNotNullExpressionValue(referenceToIntrinsic, "getReferenceToIntrinsic(...)");
        MetadataProperties.setSideEffects(referenceToIntrinsic, SideEffectKind.PURE);
        JsInvocation jsInvocation = new JsInvocation(referenceToIntrinsic, jsStringLiteral, jsExpression2);
        MetadataProperties.setCallableReference(jsInvocation, true);
        MetadataProperties.setSideEffects(jsInvocation, SideEffectKind.PURE);
        MetadataProperties.setCallableReferenceReceiver(jsInvocation, jsExpression);
        return jsInvocation;
    }
}
