package org.jetbrains.kotlin.types.expressions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: FunctionsTypingVisitor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a(\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006¨\u0006\b"}, d2 = {"createFunctionType", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "suspendFunction", "", "shouldUseVarargType", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FunctionsTypingVisitorKt {
    public static /* synthetic */ KotlinType createFunctionType$default(SimpleFunctionDescriptor simpleFunctionDescriptor, KotlinBuiltIns kotlinBuiltIns, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        return createFunctionType(simpleFunctionDescriptor, kotlinBuiltIns, z, z2);
    }

    public static final KotlinType createFunctionType(SimpleFunctionDescriptor simpleFunctionDescriptor, KotlinBuiltIns builtIns, boolean z, boolean z2) {
        ArrayList arrayList;
        Intrinsics.checkNotNullParameter(simpleFunctionDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Annotations empty = Annotations.INSTANCE.getEMPTY();
        ReceiverParameterDescriptor extensionReceiverParameter = simpleFunctionDescriptor.getExtensionReceiverParameter();
        KotlinType type = extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
        List<ReceiverParameterDescriptor> contextReceiverParameters = simpleFunctionDescriptor.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        List<ReceiverParameterDescriptor> list = contextReceiverParameters;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            KotlinType type2 = ((ReceiverParameterDescriptor) it.next()).getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            arrayList2.add(type2);
        }
        ArrayList arrayList3 = arrayList2;
        if (z2) {
            List<ValueParameterDescriptor> valueParameters = simpleFunctionDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            List<ValueParameterDescriptor> list2 = valueParameters;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (ValueParameterDescriptor valueParameterDescriptor : list2) {
                KotlinType varargElementType = valueParameterDescriptor.getVarargElementType();
                if (varargElementType == null) {
                    varargElementType = valueParameterDescriptor.getType();
                    Intrinsics.checkNotNullExpressionValue(varargElementType, "getType(...)");
                }
                arrayList4.add(varargElementType);
            }
            arrayList = arrayList4;
        } else {
            List<ValueParameterDescriptor> valueParameters2 = simpleFunctionDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
            List<ValueParameterDescriptor> list3 = valueParameters2;
            ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
            Iterator<T> it2 = list3.iterator();
            while (it2.hasNext()) {
                KotlinType type3 = ((ValueParameterDescriptor) it2.next()).getType();
                Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                arrayList5.add(type3);
            }
            arrayList = arrayList5;
        }
        ArrayList arrayList6 = arrayList;
        KotlinType returnType = simpleFunctionDescriptor.getReturnType();
        if (returnType == null) {
            return null;
        }
        return FunctionTypesKt.createFunctionType(builtIns, empty, type, arrayList3, arrayList6, null, returnType, z);
    }
}
