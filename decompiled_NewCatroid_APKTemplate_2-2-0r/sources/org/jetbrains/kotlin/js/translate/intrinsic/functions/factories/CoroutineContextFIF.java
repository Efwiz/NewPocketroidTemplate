package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt;
import org.jetbrains.kotlin.js.translate.callTranslator.CallInfo;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.checkers.CoroutineCallCheckerKt;

/* JADX INFO: compiled from: CoroutineContextFIF.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\tB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/CoroutineContextFIF;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/FunctionIntrinsicFactory;", "()V", "getIntrinsic", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsic;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "Intrinsic", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CoroutineContextFIF implements FunctionIntrinsicFactory {
    public static final CoroutineContextFIF INSTANCE = new CoroutineContextFIF();

    private CoroutineContextFIF() {
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.FunctionIntrinsicFactory
    public FunctionIntrinsic getIntrinsic(FunctionDescriptor descriptor, TranslationContext context) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (CoroutineCallCheckerKt.isBuiltInCoroutineContext(descriptor)) {
            return Intrinsic.INSTANCE;
        }
        return null;
    }

    /* JADX INFO: compiled from: CoroutineContextFIF.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/CoroutineContextFIF$Intrinsic;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsic;", "()V", "apply", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "callInfo", "Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;", "arguments", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Intrinsic extends FunctionIntrinsic {
        public static final Intrinsic INSTANCE = new Intrinsic();

        private Intrinsic() {
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic
        public JsExpression apply(CallInfo callInfo, List<? extends JsExpression> arguments, TranslationContext context) {
            Intrinsics.checkNotNullParameter(callInfo, "callInfo");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(context, "context");
            ValueParameterDescriptor enclosingContinuationParameter = TranslationUtils.getEnclosingContinuationParameter(context);
            Intrinsics.checkNotNullExpressionValue(enclosingContinuationParameter, "getEnclosingContinuationParameter(...)");
            ClassifierDescriptor this$0 = enclosingContinuationParameter.getType().getConstructor().getThis$0();
            ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            if (classDescriptor == null) {
                throw new IllegalStateException("Continuation is not a class".toString());
            }
            PropertyDescriptor propertyByName = DescriptorUtils.getPropertyByName(classDescriptor.getUnsubstitutedMemberScope(), Name.identifier("context"));
            Intrinsics.checkNotNullExpressionValue(propertyByName, "getPropertyByName(...)");
            JsName nameForDescriptor = context.getNameForDescriptor(propertyByName);
            DeclarationDescriptor declarationDescriptor = context.getDeclarationDescriptor();
            JsNameRef jsNameRef = new JsNameRef(nameForDescriptor, (declarationDescriptor == null || !DescriptorUtilsKt.isCoroutineLambda(declarationDescriptor)) ? TranslationUtils.translateContinuationArgument(context) : JsAstUtils.stateMachineReceiver());
            MetadataProperties.setInline(jsNameRef, (Boolean) false);
            return jsNameRef;
        }
    }
}
