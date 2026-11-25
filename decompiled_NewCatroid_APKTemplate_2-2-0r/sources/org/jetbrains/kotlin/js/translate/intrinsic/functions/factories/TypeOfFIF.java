package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.callTranslator.CallInfo;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: TypeOfFIF.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\tB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TypeOfFIF;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/FunctionIntrinsicFactory;", "()V", "getIntrinsic", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsic;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "Intrinsic", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeOfFIF implements FunctionIntrinsicFactory {
    public static final TypeOfFIF INSTANCE = new TypeOfFIF();

    private TypeOfFIF() {
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.FunctionIntrinsicFactory
    public FunctionIntrinsic getIntrinsic(FunctionDescriptor descriptor, TranslationContext context) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsic intrinsic = Intrinsic.INSTANCE;
        if (!Intrinsics.areEqual(DescriptorUtilsKt.getFqNameUnsafe(descriptor).asString(), "kotlin.reflect.typeOf")) {
            intrinsic = null;
        }
        return intrinsic;
    }

    /* JADX INFO: compiled from: TypeOfFIF.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/TypeOfFIF$Intrinsic;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsic;", "()V", "apply", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "callInfo", "Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;", "arguments", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Intrinsic extends FunctionIntrinsic {
        public static final Intrinsic INSTANCE = new Intrinsic();

        private Intrinsic() {
        }

        @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic
        public JsExpression apply(CallInfo callInfo, List<? extends JsExpression> arguments, TranslationContext context) {
            Intrinsics.checkNotNullParameter(callInfo, "callInfo");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(context, "context");
            KotlinType kotlinType = (KotlinType) CollectionsKt.single(callInfo.getResolvedCall().getTypeArguments().values());
            Intrinsics.checkNotNull(kotlinType);
            return TypeOfFIFKt.createKType(context, kotlinType);
        }
    }
}
