package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.translate.callTranslator.CallInfo;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ThrowableConstructorIntrinsicFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\tB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/ThrowableConstructorIntrinsicFactory;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/FunctionIntrinsicFactory;", "()V", "getIntrinsic", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsic;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "Intrinsic", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ThrowableConstructorIntrinsicFactory implements FunctionIntrinsicFactory {
    public static final ThrowableConstructorIntrinsicFactory INSTANCE = new ThrowableConstructorIntrinsicFactory();

    private ThrowableConstructorIntrinsicFactory() {
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.FunctionIntrinsicFactory
    public FunctionIntrinsic getIntrinsic(FunctionDescriptor descriptor, TranslationContext context) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!(descriptor instanceof ConstructorDescriptor)) {
            return null;
        }
        SimpleType defaultType = ((ConstructorDescriptor) descriptor).getConstructedClass().getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        if (TypeUtilsKt.isNotNullThrowable(defaultType)) {
            return Intrinsic.INSTANCE;
        }
        return null;
    }

    /* JADX INFO: compiled from: ThrowableConstructorIntrinsicFactory.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/ThrowableConstructorIntrinsicFactory$Intrinsic;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsic;", "()V", "apply", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "callInfo", "Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;", "arguments", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Intrinsic extends FunctionIntrinsic {
        public static final Intrinsic INSTANCE = new Intrinsic();

        private Intrinsic() {
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic
        public JsExpression apply(CallInfo callInfo, List<? extends JsExpression> arguments, TranslationContext context) {
            FqName fqNameSafe;
            boolean z;
            Intrinsics.checkNotNullParameter(callInfo, "callInfo");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(context, "context");
            CallableDescriptor resultingDescriptor = callInfo.getResolvedCall().getResultingDescriptor();
            List mutableList = CollectionsKt.toMutableList((Collection) arguments);
            List<ValueParameterDescriptor> valueParameters = resultingDescriptor.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            List<ValueParameterDescriptor> list = valueParameters;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    ClassifierDescriptor this$0 = ((ValueParameterDescriptor) it.next()).getType().getConstructor().getThis$0();
                    if (this$0 != null) {
                        Intrinsics.checkNotNull(this$0);
                        fqNameSafe = DescriptorUtilsKt.getFqNameSafe(this$0);
                    } else {
                        fqNameSafe = null;
                    }
                    if (Intrinsics.areEqual(fqNameSafe, StandardNames.FqNames.throwable)) {
                        z = true;
                        break;
                    }
                }
            } else {
                z = false;
                break;
            }
            if (resultingDescriptor.getValueParameters().size() == 1 && z) {
                JsExpression undefinedExpression = Namer.getUndefinedExpression();
                Intrinsics.checkNotNullExpressionValue(undefinedExpression, "getUndefinedExpression(...)");
                mutableList.add(0, undefinedExpression);
            }
            return new JsInvocation(JsAstUtils.pureFqn("newThrowable", Namer.kotlinObject()), (List<? extends JsExpression>) mutableList);
        }
    }
}
