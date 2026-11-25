package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNew;
import org.jetbrains.kotlin.js.translate.reference.CallArgumentTranslator;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.resolve.sam.SamConstructorDescriptor;

/* JADX INFO: compiled from: FunctionCallCases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\f\u0010\u0007\u001a\u00020\b*\u00020\u0006H\u0014J,\u0010\t\u001a\u00020\b*\u00020\u00062\u001d\u0010\n\u001a\u0019\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\r0\u000b¢\u0006\u0002\b\u000eH\u0082\bJ\f\u0010\u000f\u001a\u00020\b*\u00020\u0006H\u0014J\f\u0010\u0010\u001a\u00020\b*\u00020\u0006H\u0014¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/ConstructorCallCase;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallCase;", "()V", "canApply", "", "callInfo", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallInfo;", "dispatchReceiver", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "doTranslate", "getArguments", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/js/translate/reference/CallArgumentTranslator$ArgumentsInfo;", "", "Lkotlin/ExtensionFunctionType;", "extensionReceiver", "noReceivers", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstructorCallCase extends FunctionCallCase {
    public static final ConstructorCallCase INSTANCE = new ConstructorCallCase();

    private ConstructorCallCase() {
    }

    public final boolean canApply(FunctionCallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        FunctionCallInfo functionCallInfo = callInfo;
        return (CallInfoExtensionsKt.getCallableDescriptor(functionCallInfo) instanceof ConstructorDescriptor) || (CallInfoExtensionsKt.getCallableDescriptor(functionCallInfo) instanceof SamConstructorDescriptor);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression noReceivers(FunctionCallInfo functionCallInfo) {
        JsNew jsNew;
        ClassDescriptor baseDescriptorForSynthetic;
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        FunctionCallInfo functionCallInfo2 = functionCallInfo;
        CallableDescriptor callableDescriptor = CallInfoExtensionsKt.getCallableDescriptor(functionCallInfo2);
        ArrayList arrayListEmptyList = null;
        SamConstructorDescriptor samConstructorDescriptor = callableDescriptor instanceof SamConstructorDescriptor ? (SamConstructorDescriptor) callableDescriptor : null;
        if (samConstructorDescriptor != null && (baseDescriptorForSynthetic = samConstructorDescriptor.getSamInterface()) != null) {
            JsExpression jsExpressionTranslateAsTypeReference = ReferenceTranslator.translateAsTypeReference(baseDescriptorForSynthetic, functionCallInfo.getContext());
            Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsTypeReference, "translateAsTypeReference(...)");
            return new JsNew(jsExpressionTranslateAsTypeReference, functionCallInfo.getArgumentsInfo().getTranslateArguments());
        }
        JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(CallInfoExtensionsKt.getCallableDescriptor(functionCallInfo2), functionCallInfo.getContext());
        Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsValueReference, "translateAsValueReference(...)");
        ArrayList arrayList = new ArrayList();
        CallableDescriptor callableDescriptor2 = CallInfoExtensionsKt.getCallableDescriptor(functionCallInfo2);
        Intrinsics.checkNotNull(callableDescriptor2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor");
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) callableDescriptor2;
        if (!functionCallInfo.getContext().shouldBeDeferred(classConstructorDescriptor)) {
            List<DeclarationDescriptor> classOrConstructorClosure = functionCallInfo.getContext().getClassOrConstructorClosure(classConstructorDescriptor);
            ArrayList arrayList2 = arrayList;
            if (classOrConstructorClosure != null) {
                List<DeclarationDescriptor> list = classOrConstructorClosure;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList3.add(functionCallInfo.getContext().getArgumentForClosureConstructor((DeclarationDescriptor) it.next()));
                }
                arrayListEmptyList = arrayList3;
            }
            if (arrayListEmptyList == null) {
                arrayListEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(arrayList2, arrayListEmptyList);
        }
        CollectionsKt.addAll(arrayList, functionCallInfo.getArgumentsInfo().getTranslateArguments());
        if (classConstructorDescriptor.isPrimary() || AnnotationsUtils.isNativeObject(classConstructorDescriptor)) {
            jsNew = new JsNew(jsExpressionTranslateAsValueReference, arrayList);
        } else {
            jsNew = new JsInvocation(jsExpressionTranslateAsValueReference, arrayList);
        }
        if (functionCallInfo.getContext().shouldBeDeferred(classConstructorDescriptor)) {
            functionCallInfo.getContext().deferConstructorCall(classConstructorDescriptor, jsNew.getArguments());
        }
        return (JsExpression) jsNew;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression dispatchReceiver(FunctionCallInfo functionCallInfo) {
        JsNew jsNew;
        ClassDescriptor baseDescriptorForSynthetic;
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        FunctionCallInfo functionCallInfo2 = functionCallInfo;
        CallableDescriptor callableDescriptor = CallInfoExtensionsKt.getCallableDescriptor(functionCallInfo2);
        ArrayList arrayListEmptyList = null;
        SamConstructorDescriptor samConstructorDescriptor = callableDescriptor instanceof SamConstructorDescriptor ? (SamConstructorDescriptor) callableDescriptor : null;
        if (samConstructorDescriptor != null && (baseDescriptorForSynthetic = samConstructorDescriptor.getSamInterface()) != null) {
            JsExpression jsExpressionTranslateAsTypeReference = ReferenceTranslator.translateAsTypeReference(baseDescriptorForSynthetic, functionCallInfo.getContext());
            Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsTypeReference, "translateAsTypeReference(...)");
            CallArgumentTranslator.ArgumentsInfo argumentsInfo = functionCallInfo.getArgumentsInfo();
            JsExpression dispatchReceiver = functionCallInfo.getDispatchReceiver();
            Intrinsics.checkNotNull(dispatchReceiver);
            return new JsNew(jsExpressionTranslateAsTypeReference, FunctionCallCasesKt.argsWithReceiver(argumentsInfo, dispatchReceiver));
        }
        JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(CallInfoExtensionsKt.getCallableDescriptor(functionCallInfo2), functionCallInfo.getContext());
        Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsValueReference, "translateAsValueReference(...)");
        ArrayList arrayList = new ArrayList();
        CallableDescriptor callableDescriptor2 = CallInfoExtensionsKt.getCallableDescriptor(functionCallInfo2);
        Intrinsics.checkNotNull(callableDescriptor2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor");
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) callableDescriptor2;
        if (!functionCallInfo.getContext().shouldBeDeferred(classConstructorDescriptor)) {
            List<DeclarationDescriptor> classOrConstructorClosure = functionCallInfo.getContext().getClassOrConstructorClosure(classConstructorDescriptor);
            ArrayList arrayList2 = arrayList;
            if (classOrConstructorClosure != null) {
                List<DeclarationDescriptor> list = classOrConstructorClosure;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList3.add(functionCallInfo.getContext().getArgumentForClosureConstructor((DeclarationDescriptor) it.next()));
                }
                arrayListEmptyList = arrayList3;
            }
            if (arrayListEmptyList == null) {
                arrayListEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(arrayList2, arrayListEmptyList);
        }
        CallArgumentTranslator.ArgumentsInfo argumentsInfo2 = functionCallInfo.getArgumentsInfo();
        JsExpression dispatchReceiver2 = functionCallInfo.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver2);
        CollectionsKt.addAll(arrayList, FunctionCallCasesKt.argsWithReceiver(argumentsInfo2, dispatchReceiver2));
        if (classConstructorDescriptor.isPrimary() || AnnotationsUtils.isNativeObject(classConstructorDescriptor)) {
            jsNew = new JsNew(jsExpressionTranslateAsValueReference, arrayList);
        } else {
            jsNew = new JsInvocation(jsExpressionTranslateAsValueReference, arrayList);
        }
        if (functionCallInfo.getContext().shouldBeDeferred(classConstructorDescriptor)) {
            functionCallInfo.getContext().deferConstructorCall(classConstructorDescriptor, jsNew.getArguments());
        }
        return (JsExpression) jsNew;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression extensionReceiver(FunctionCallInfo functionCallInfo) {
        JsNew jsNew;
        ClassDescriptor baseDescriptorForSynthetic;
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        FunctionCallInfo functionCallInfo2 = functionCallInfo;
        CallableDescriptor callableDescriptor = CallInfoExtensionsKt.getCallableDescriptor(functionCallInfo2);
        ArrayList arrayListEmptyList = null;
        SamConstructorDescriptor samConstructorDescriptor = callableDescriptor instanceof SamConstructorDescriptor ? (SamConstructorDescriptor) callableDescriptor : null;
        if (samConstructorDescriptor != null && (baseDescriptorForSynthetic = samConstructorDescriptor.getSamInterface()) != null) {
            JsExpression jsExpressionTranslateAsTypeReference = ReferenceTranslator.translateAsTypeReference(baseDescriptorForSynthetic, functionCallInfo.getContext());
            Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsTypeReference, "translateAsTypeReference(...)");
            CallArgumentTranslator.ArgumentsInfo argumentsInfo = functionCallInfo.getArgumentsInfo();
            JsExpression extensionReceiver = functionCallInfo.getExtensionReceiver();
            Intrinsics.checkNotNull(extensionReceiver);
            return new JsNew(jsExpressionTranslateAsTypeReference, FunctionCallCasesKt.argsWithReceiver(argumentsInfo, extensionReceiver));
        }
        JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(CallInfoExtensionsKt.getCallableDescriptor(functionCallInfo2), functionCallInfo.getContext());
        Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsValueReference, "translateAsValueReference(...)");
        ArrayList arrayList = new ArrayList();
        CallableDescriptor callableDescriptor2 = CallInfoExtensionsKt.getCallableDescriptor(functionCallInfo2);
        Intrinsics.checkNotNull(callableDescriptor2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor");
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) callableDescriptor2;
        if (!functionCallInfo.getContext().shouldBeDeferred(classConstructorDescriptor)) {
            List<DeclarationDescriptor> classOrConstructorClosure = functionCallInfo.getContext().getClassOrConstructorClosure(classConstructorDescriptor);
            ArrayList arrayList2 = arrayList;
            if (classOrConstructorClosure != null) {
                List<DeclarationDescriptor> list = classOrConstructorClosure;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList3.add(functionCallInfo.getContext().getArgumentForClosureConstructor((DeclarationDescriptor) it.next()));
                }
                arrayListEmptyList = arrayList3;
            }
            if (arrayListEmptyList == null) {
                arrayListEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(arrayList2, arrayListEmptyList);
        }
        CallArgumentTranslator.ArgumentsInfo argumentsInfo2 = functionCallInfo.getArgumentsInfo();
        JsExpression extensionReceiver2 = functionCallInfo.getExtensionReceiver();
        Intrinsics.checkNotNull(extensionReceiver2);
        CollectionsKt.addAll(arrayList, FunctionCallCasesKt.argsWithReceiver(argumentsInfo2, extensionReceiver2));
        if (classConstructorDescriptor.isPrimary() || AnnotationsUtils.isNativeObject(classConstructorDescriptor)) {
            jsNew = new JsNew(jsExpressionTranslateAsValueReference, arrayList);
        } else {
            jsNew = new JsInvocation(jsExpressionTranslateAsValueReference, arrayList);
        }
        if (functionCallInfo.getContext().shouldBeDeferred(classConstructorDescriptor)) {
            functionCallInfo.getContext().deferConstructorCall(classConstructorDescriptor, jsNew.getArguments());
        }
        return (JsExpression) jsNew;
    }
}
