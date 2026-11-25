package org.jetbrains.kotlin.js.translate.reference;

import kotlin.Metadata;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: CallableReferenceTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class CallableReferenceTranslator$translateForProperty$setter$1 extends FunctionReference implements Function4<TranslationContext, ResolvedCall<? extends VariableDescriptor>, JsExpression, JsExpression, JsExpression> {
    CallableReferenceTranslator$translateForProperty$setter$1(Object obj) {
        super(4, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "translateSet(Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "translateSet";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(CallTranslator.class);
    }

    @Override // kotlin.jvm.functions.Function4
    public final JsExpression invoke(TranslationContext p0, ResolvedCall<? extends VariableDescriptor> p1, JsExpression p2, JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        Intrinsics.checkNotNullParameter(p2, "p2");
        return ((CallTranslator) this.receiver).translateSet(p0, p1, p2, jsExpression);
    }
}
