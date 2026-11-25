package org.jetbrains.kotlin.resolve.calls.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;

/* JADX INFO: compiled from: CapturingInClosureChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"findDestructuredVariable", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "variable", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "variableParent", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CapturingInClosureCheckerKt {
    public static final ValueParameterDescriptor findDestructuredVariable(VariableDescriptor variable, DeclarationDescriptor variableParent) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(variableParent, "variableParent");
        Object obj = null;
        if (!(variable instanceof LocalVariableDescriptor) || !(variableParent instanceof AnonymousFunctionDescriptor)) {
            return null;
        }
        List<ValueParameterDescriptor> valueParameters = ((AnonymousFunctionDescriptor) variableParent).getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        for (Object obj2 : valueParameters) {
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) obj2;
            if ((valueParameterDescriptor instanceof ValueParameterDescriptorImpl.WithDestructuringDeclaration) && ((ValueParameterDescriptorImpl.WithDestructuringDeclaration) valueParameterDescriptor).getDestructuringVariables().contains(variable)) {
                obj = obj2;
                break;
            }
        }
        return (ValueParameterDescriptor) obj;
    }
}
