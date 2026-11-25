package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.callTranslator.CallInfo;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;

/* JADX INFO: compiled from: CallTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u00022\u00020\u0003J\u001f\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\u0007\u001a\u00020\bH\u0016¢\u0006\u0002\u0010\tJ\u0011\u0010\n\u001a\u00020\u000b*\u00028\u0000H\u0016¢\u0006\u0002\u0010\fJ\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e*\u00028\u0000H&¢\u0006\u0002\u0010\u000fJ\u0011\u0010\u0010\u001a\u00020\u0011*\u00028\u0000H&¢\u0006\u0002\u0010\u0012J\u001b\u0010\u0013\u001a\u0004\u0018\u00010\u0005*\u00028\u00002\u0006\u0010\u0007\u001a\u00020\bH\u0002¢\u0006\u0002\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0014À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/DelegateIntrinsic;", "I", "Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;", "", "intrinsic", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "callInfo", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "(Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "canBeApply", "", "(Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;)Z", "getArgs", "", "(Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;)Ljava/util/List;", "getDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getIntrinsic", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DelegateIntrinsic<I extends CallInfo> {
    default boolean canBeApply(I i) {
        Intrinsics.checkNotNullParameter(i, "<this>");
        return true;
    }

    List<JsExpression> getArgs(I i);

    CallableDescriptor getDescriptor(I i);

    default JsExpression intrinsic(I callInfo, TranslationContext context) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        Intrinsics.checkNotNullParameter(context, "context");
        if (canBeApply(callInfo)) {
            return getIntrinsic(callInfo, context);
        }
        return null;
    }

    private default JsExpression getIntrinsic(I i, TranslationContext translationContext) {
        FunctionIntrinsic functionIntrinsic;
        CallableDescriptor descriptor = getDescriptor(i);
        if (!(descriptor instanceof FunctionDescriptor) || (functionIntrinsic = translationContext.intrinsics().getFunctionIntrinsic((FunctionDescriptor) descriptor, translationContext)) == null) {
            return null;
        }
        return functionIntrinsic.apply(i, getArgs(i), translationContext);
    }
}
