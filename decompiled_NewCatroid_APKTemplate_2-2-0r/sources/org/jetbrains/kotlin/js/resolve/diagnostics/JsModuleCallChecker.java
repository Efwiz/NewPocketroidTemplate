package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.psi.KtTypeArgumentList;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.scopes.receivers.ClassValueReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: JsModuleCallChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J$\u0010\u000b\u001a\u00020\u00042\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsModuleCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "checkReifieidTypeParameters", "call", "extractModuleCallee", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsModuleCallChecker implements CallChecker {
    public static final JsModuleCallChecker INSTANCE = new JsModuleCallChecker();

    private JsModuleCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        checkReifieidTypeParameters(resolvedCall, reportOn, context);
        DeclarationDescriptor declarationDescriptorExtractModuleCallee = extractModuleCallee(resolvedCall);
        if (declarationDescriptorExtractModuleCallee == null) {
            return;
        }
        BindingContext context2 = context.getTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
        JsModuleCheckUtilKt.checkJsModuleUsage(context2, context.getTrace(), context.getScope().getOwnerDescriptor(), declarationDescriptorExtractModuleCallee, reportOn);
    }

    private final DeclarationDescriptor extractModuleCallee(ResolvedCall<?> call) {
        ReceiverValue receiverValueMo7304getDispatchReceiver;
        CallableDescriptor resultingDescriptor = call.getResultingDescriptor();
        if (DescriptorUtils.isTopLevelDeclaration(resultingDescriptor) || (receiverValueMo7304getDispatchReceiver = call.getDispatchReceiver()) == null) {
            return resultingDescriptor;
        }
        if (receiverValueMo7304getDispatchReceiver instanceof ClassValueReceiver) {
            return ((ClassValueReceiver) receiverValueMo7304getDispatchReceiver).getClassQualifier().getDescriptor();
        }
        return null;
    }

    private final void checkReifieidTypeParameters(ResolvedCall<?> call, PsiElement reportOn, CallCheckerContext context) {
        LinkedHashMap linkedHashMapEmptyMap;
        DeclarationDescriptor ownerDescriptor = context.getScope().getOwnerDescriptor();
        List<TypeParameterDescriptor> typeParameters = call.get$suspendPropertyDescriptor().getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeParameterDescriptor) it.next()).getOriginal());
        }
        Iterable iterableWithIndex = CollectionsKt.withIndex(arrayList);
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : iterableWithIndex) {
            if (((TypeParameterDescriptor) ((IndexedValue) obj).component2()).isReified()) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = arrayList2;
        KtTypeArgumentList typeArgumentList = call.getCall().getTypeArgumentList();
        if (typeArgumentList != null) {
            ArrayList<IndexedValue> arrayList4 = arrayList3;
            linkedHashMapEmptyMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList4, 10)), 16));
            for (IndexedValue indexedValue : arrayList4) {
                int index = indexedValue.getIndex();
                TypeParameterDescriptor original = ((TypeParameterDescriptor) indexedValue.component2()).getOriginal();
                List<KtTypeProjection> arguments = typeArgumentList.getArguments();
                Intrinsics.checkNotNullExpressionValue(arguments, "getArguments(...)");
                KtTypeProjection ktTypeProjection = (KtTypeProjection) CollectionsKt.getOrNull(arguments, index);
                Pair pair = TuplesKt.to(original, ktTypeProjection != null ? ktTypeProjection.getTypeReference() : null);
                linkedHashMapEmptyMap.put(pair.getFirst(), pair.getSecond());
            }
        } else {
            linkedHashMapEmptyMap = null;
        }
        if (linkedHashMapEmptyMap == null) {
            linkedHashMapEmptyMap = MapsKt.emptyMap();
        }
        ArrayList arrayList5 = arrayList3;
        ArrayList<TypeParameterDescriptor> arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
        Iterator it2 = arrayList5.iterator();
        while (it2.hasNext()) {
            arrayList6.add(((TypeParameterDescriptor) ((IndexedValue) it2.next()).component2()).getOriginal());
        }
        for (TypeParameterDescriptor typeParameterDescriptor : arrayList6) {
            KtTypeReference ktTypeReference = (KtTypeReference) linkedHashMapEmptyMap.get(typeParameterDescriptor);
            KtTypeReference ktTypeReference2 = ktTypeReference == null ? reportOn : ktTypeReference;
            KotlinType kotlinType = call.getTypeArguments().get(typeParameterDescriptor);
            if (kotlinType != null) {
                ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
                ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
                if (classDescriptor != null) {
                    BindingContext context2 = context.getTrace().getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                    JsModuleCheckUtilKt.checkJsModuleUsage(context2, context.getTrace(), ownerDescriptor, classDescriptor, ktTypeReference2);
                }
            }
        }
    }
}
