package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: CoroutineCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u0012\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t*\u00020\u000bH\u0002¨\u0006\f"}, d2 = {"reportSuspensionPointInsideMonitor", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "stackTraceElement", "", "allValueParameterTypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CoroutineCodegenKt {
    public static final void reportSuspensionPointInsideMonitor(KtElement element, GenerationState state, String stackTraceElement) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(stackTraceElement, "stackTraceElement");
        state.getDiagnostics().report(ErrorsJvm.SUSPENSION_POINT_INSIDE_MONITOR.on(element, stackTraceElement));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final List<KotlinType> allValueParameterTypes(FunctionDescriptor functionDescriptor) {
        ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter();
        List listListOfNotNull = CollectionsKt.listOfNotNull(extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null);
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            KotlinType type = ((ValueParameterDescriptor) it.next()).getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            arrayList.add(type);
        }
        return CollectionsKt.plus((Collection) listListOfNotNull, (Iterable) arrayList);
    }
}
