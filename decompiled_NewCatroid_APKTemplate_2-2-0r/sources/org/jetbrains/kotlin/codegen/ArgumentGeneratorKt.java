package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: ArgumentGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001¨\u0006\u0003"}, d2 = {"getFunctionWithDefaultArguments", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "functionDescriptor", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ArgumentGeneratorKt {
    public static final FunctionDescriptor getFunctionWithDefaultArguments(FunctionDescriptor functionDescriptor) {
        Object next;
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        if (!(functionDescriptor.getContainingDeclaration() instanceof ClassDescriptor) || functionDescriptor.getOverriddenDescriptors().isEmpty()) {
            return functionDescriptor;
        }
        Iterator it = DescriptorUtilsKt.overriddenTreeUniqueAsSequence(functionDescriptor, true).iterator();
        loop0: while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) next;
            if (functionDescriptor2.getKind() == CallableMemberDescriptor.Kind.DECLARATION && functionDescriptor2.getOverriddenDescriptors().isEmpty()) {
                List<ValueParameterDescriptor> valueParameters = functionDescriptor2.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                List<ValueParameterDescriptor> list = valueParameters;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    for (ValueParameterDescriptor valueParameterDescriptor : list) {
                        Intrinsics.checkNotNull(valueParameterDescriptor);
                        if (ArgumentsUtilsKt.hasDefaultValue(valueParameterDescriptor)) {
                            break loop0;
                        }
                    }
                }
            }
        }
        FunctionDescriptor functionDescriptor3 = (FunctionDescriptor) next;
        return functionDescriptor3 == null ? functionDescriptor : functionDescriptor3;
    }
}
