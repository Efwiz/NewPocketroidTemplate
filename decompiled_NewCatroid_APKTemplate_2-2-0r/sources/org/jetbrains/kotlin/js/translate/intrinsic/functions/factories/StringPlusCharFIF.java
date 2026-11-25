package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.callTranslator.CallInfo;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;

/* JADX INFO: compiled from: StringPlusCharFIF.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\tB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/StringPlusCharFIF;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/FunctionIntrinsicFactory;", "()V", "getIntrinsic", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsic;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "StringPlusAnyIntrinsic", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StringPlusCharFIF implements FunctionIntrinsicFactory {
    public static final StringPlusCharFIF INSTANCE = new StringPlusCharFIF();

    /* JADX INFO: compiled from: StringPlusCharFIF.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J&\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/StringPlusCharFIF$StringPlusAnyIntrinsic;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsic;", "leftTypeNullable", "", "(Z)V", "apply", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "callInfo", "Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;", "arguments", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class StringPlusAnyIntrinsic extends FunctionIntrinsic {
        private final boolean leftTypeNullable;

        public StringPlusAnyIntrinsic(boolean z) {
            this.leftTypeNullable = z;
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic
        public JsExpression apply(CallInfo callInfo, List<? extends JsExpression> arguments, TranslationContext context) {
            JsExpression jsExpressionApply;
            Intrinsics.checkNotNullParameter(callInfo, "callInfo");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(context, "context");
            JsExpression dispatchReceiver = callInfo.getDispatchReceiver();
            if (dispatchReceiver == null) {
                dispatchReceiver = callInfo.getExtensionReceiver();
                Intrinsics.checkNotNull(dispatchReceiver);
            }
            BindingContext bindingContext = context.bindingContext();
            KtExpression argumentExpression = callInfo.getResolvedCall().getCall().getValueArguments().get(0).getArgumentExpression();
            Intrinsics.checkNotNull(argumentExpression);
            KotlinType type = bindingContext.getType(argumentExpression);
            if (type == null) {
                type = callInfo.getResolvedCall().getResultingDescriptor().getValueParameters().get(0).getType();
            }
            Intrinsics.checkNotNull(type);
            boolean z = (this.leftTypeNullable && TypeUtils.isNullableType(type)) ? false : true;
            if (KotlinBuiltIns.isChar(type)) {
                jsExpressionApply = JsAstUtils.charToString(arguments.get(0));
            } else if (KotlinBuiltIns.isStringOrNullableString(type) && z) {
                jsExpressionApply = arguments.get(0);
            } else {
                jsExpressionApply = TopLevelFIF.TO_STRING.apply(arguments.get(0), CollectionsKt.emptyList(), context);
            }
            Intrinsics.checkNotNull(jsExpressionApply);
            return new JsBinaryOperation(JsBinaryOperator.ADD, dispatchReceiver, jsExpressionApply);
        }
    }

    private StringPlusCharFIF() {
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.FunctionIntrinsicFactory
    public FunctionIntrinsic getIntrinsic(FunctionDescriptor descriptor, TranslationContext context) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        String strAsString = DescriptorUtilsKt.getFqNameUnsafe(descriptor).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (!Intrinsics.areEqual(strAsString, "kotlin.String.plus") && !Intrinsics.areEqual(strAsString, "kotlin.plus")) {
            return null;
        }
        ReceiverParameterDescriptor dispatchReceiverParameter = descriptor.mo6753getDispatchReceiverParameter();
        if (dispatchReceiverParameter == null && (dispatchReceiverParameter = descriptor.getExtensionReceiverParameter()) == null) {
            return null;
        }
        KotlinType type = dispatchReceiverParameter.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        if (KotlinBuiltIns.isStringOrNullableString(type)) {
            return new StringPlusAnyIntrinsic(TypeUtils.isNullableType(type));
        }
        return null;
    }
}
