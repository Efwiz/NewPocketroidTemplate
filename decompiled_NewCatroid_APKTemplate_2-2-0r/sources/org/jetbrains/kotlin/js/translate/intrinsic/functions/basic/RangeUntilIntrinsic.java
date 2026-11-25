package org.jetbrains.kotlin.js.translate.intrinsic.functions.basic;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: RangeUntilIntrinsic.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/RangeUntilIntrinsic;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsicWithReceiverComputed;", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "getFunction", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "apply", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "receiver", "arguments", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "Companion", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RangeUntilIntrinsic extends FunctionIntrinsicWithReceiverComputed {
    private static final Name untilFunName;
    private final FunctionDescriptor function;

    public RangeUntilIntrinsic(FunctionDescriptor function) {
        Intrinsics.checkNotNullParameter(function, "function");
        this.function = function;
    }

    public final FunctionDescriptor getFunction() {
        return this.function;
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
    public JsExpression apply(JsExpression receiver, List<? extends JsExpression> arguments, TranslationContext context) {
        Object type;
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(context, "context");
        Iterator<T> it = context.getCurrentModule().getPackage(StandardNames.RANGES_PACKAGE_FQ_NAME).getMemberScope().getContributedFunctions(untilFunName, NoLookupLocation.FROM_BUILTINS).iterator();
        while (true) {
            type = null;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) next;
            ReceiverParameterDescriptor extensionReceiverParameter = simpleFunctionDescriptor.getExtensionReceiverParameter();
            type = extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null;
            ReceiverParameterDescriptor dispatchReceiverParameter = this.function.mo6753getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
            if (Intrinsics.areEqual(type, dispatchReceiverParameter.getType()) && simpleFunctionDescriptor.getValueParameters().size() == 1 && Intrinsics.areEqual(simpleFunctionDescriptor.getValueParameters().get(0).getType(), this.function.getValueParameters().get(0).getType())) {
                type = next;
                break;
            }
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor2 = (SimpleFunctionDescriptor) type;
        if (simpleFunctionDescriptor2 == null) {
            throw new IllegalStateException(("No 'until' function found for descriptor: " + this.function).toString());
        }
        return new JsInvocation(context.getInnerReference(simpleFunctionDescriptor2), (List<? extends JsExpression>) CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(receiver), (Iterable) arguments));
    }

    static {
        Name nameIdentifier = Name.identifier("until");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        untilFunName = nameIdentifier;
    }
}
