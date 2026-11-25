package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.bridges.BridgesKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.load.java.BuiltinMethodsWithSpecialGenericSignature;
import org.jetbrains.kotlin.load.java.SpecialBuiltinMembers;
import org.jetbrains.kotlin.load.java.SpecialGenericSignatures;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;

/* JADX INFO: compiled from: builtinSpecialBridges.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a4\u0010\u0006\u001a\u0004\u0018\u00010\u0002\"\u0004\b\u0000\u0010\u00072\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00070\nH\u0002\u001a\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010\u001a-\u0010\u0011\u001a\u0004\u0018\u0001H\u0007\"\u0004\b\u0000\u0010\u0007*\u00020\u00122\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00070\nH\u0002¢\u0006\u0002\u0010\u0013¨\u0006\u0014"}, d2 = {"findAllReachableDeclarations", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "functionDescriptor", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "findSuperImplementationForStubDelegation", "Signature", "function", "signatureByDescriptor", "Lkotlin/Function1;", "isValueArgumentForCallToMethodWithTypeCheckBarrier", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getSpecialBridgeSignatureIfExists", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BuiltinSpecialBridgesKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final <Signature> FunctionDescriptor findSuperImplementationForStubDelegation(FunctionDescriptor functionDescriptor, GenerationState generationState, Function1<? super FunctionDescriptor, ? extends Signature> function1) {
        DescriptorBasedFunctionHandleForJvm descriptorBasedFunctionHandleForJvm = (DescriptorBasedFunctionHandleForJvm) BridgesKt.findConcreteSuperDeclaration(new DescriptorBasedFunctionHandleForJvm(functionDescriptor, generationState));
        if (descriptorBasedFunctionHandleForJvm == null || !descriptorBasedFunctionHandleForJvm.getMayBeUsedAsSuperImplementation() || Intrinsics.areEqual(function1.invoke(functionDescriptor), function1.invoke(descriptorBasedFunctionHandleForJvm.getDescriptor()))) {
            return null;
        }
        return descriptorBasedFunctionHandleForJvm.getDescriptor();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set<FunctionDescriptor> findAllReachableDeclarations(FunctionDescriptor functionDescriptor, GenerationState generationState) {
        Set setFindAllReachableDeclarations = BridgesKt.findAllReachableDeclarations(new DescriptorBasedFunctionHandleForJvm(functionDescriptor, generationState));
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setFindAllReachableDeclarations, 10));
        Iterator it = setFindAllReachableDeclarations.iterator();
        while (it.hasNext()) {
            arrayList.add(((DescriptorBasedFunctionHandleForJvm) it.next()).getDescriptor());
        }
        return CollectionsKt.toMutableSet(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <Signature> Signature getSpecialBridgeSignatureIfExists(CallableMemberDescriptor callableMemberDescriptor, Function1<? super FunctionDescriptor, ? extends Signature> function1) {
        if (!(callableMemberDescriptor instanceof FunctionDescriptor)) {
            return null;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) callableMemberDescriptor;
        if ((functionDescriptor.getDeclarationDescriptor() instanceof JavaClassDescriptor) || DescriptorUtils.isInterface(functionDescriptor.getDeclarationDescriptor())) {
            return null;
        }
        FunctionDescriptor original = functionDescriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) SpecialBuiltinMembers.getOverriddenBuiltinReflectingJvmDescriptor(original);
        if (functionDescriptor2 == null) {
            return null;
        }
        Signature signatureInvoke = function1.invoke(functionDescriptor2);
        if (Intrinsics.areEqual(function1.invoke(original), signatureInvoke)) {
            return null;
        }
        return signatureInvoke;
    }

    public static final boolean isValueArgumentForCallToMethodWithTypeCheckBarrier(KtElement element, BindingContext bindingContext) {
        KtExpression argumentExpression;
        SpecialGenericSignatures.SpecialSignatureInfo specialSignatureInfo;
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Call parentCall = CallUtilKt.getParentCall(element, bindingContext, true);
        if (parentCall == null) {
            return false;
        }
        List<? extends ValueArgument> valueArguments = parentCall.getValueArguments();
        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
        ValueArgument valueArgument = (ValueArgument) CollectionsKt.singleOrNull((List) valueArguments);
        if (valueArgument == null || (argumentExpression = valueArgument.getArgumentExpression()) == null || KtPsiUtil.deparenthesize(argumentExpression) != element) {
            return false;
        }
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(parentCall, bindingContext);
        CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) (resolvedCall != null ? resolvedCall.get$suspendPropertyDescriptor() : null);
        if (callableMemberDescriptor == null || (specialSignatureInfo = BuiltinMethodsWithSpecialGenericSignature.getSpecialSignatureInfo(callableMemberDescriptor)) == null) {
            return false;
        }
        return specialSignatureInfo.getIsObjectReplacedWithTypeParameter();
    }
}
