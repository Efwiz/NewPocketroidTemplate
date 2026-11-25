package org.jetbrains.kotlin.resolve.jvm.kotlinSignature;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;

/* JADX INFO: compiled from: signaturePropagationUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0000\u001a\f\u0010\u0004\u001a\u00020\u0001*\u00020\u0003H\u0000\u001a\f\u0010\u0005\u001a\u00020\u0001*\u00020\u0003H\u0002¨\u0006\u0006"}, d2 = {"containsAnyNotTrivialSignature", "", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "containsVarargs", "hasNotTrivialSignature", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SignaturePropagationUtilKt {
    public static final boolean containsVarargs(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (((ValueParameterDescriptor) it.next()).getVarargElementType() != null) {
                return true;
            }
        }
        return false;
    }

    public static final boolean containsAnyNotTrivialSignature(Collection<? extends FunctionDescriptor> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Collection<? extends FunctionDescriptor> collection2 = collection;
        if (collection2.isEmpty()) {
            return false;
        }
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            if (hasNotTrivialSignature((FunctionDescriptor) it.next())) {
                return true;
            }
        }
        return false;
    }

    private static final boolean hasNotTrivialSignature(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor.getExtensionReceiverParameter() == null && !functionDescriptor.hasStableParameterNames()) {
            return containsVarargs(functionDescriptor);
        }
        return true;
    }
}
