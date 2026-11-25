package org.jetbrains.kotlin.js.translate.intrinsic.functions;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.AsDynamicFIF;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.CoroutineContextFIF;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ExceptionPropertyIntrinsicFactory;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.FunctionIntrinsicFactory;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.LongOperationFIF;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.NumberAndCharConversionFIF;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveBinaryOperationFIF;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.PrimitiveUnaryOperationFIF;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.StringPlusCharFIF;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.SuspendCoroutineUninterceptedOrReturnFIF;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ThrowableConstructorIntrinsicFactory;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.TopLevelFIF;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.TypeOfFIF;

/* JADX INFO: compiled from: FunctionIntrinsics.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\n\u001a\u0004\u0018\u00010\t2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/FunctionIntrinsics;", "", "()V", "factories", "", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/FunctionIntrinsicFactory;", "intrinsicCache", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsic;", "getIntrinsic", "descriptor", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionIntrinsics {
    private final Map<FunctionDescriptor, FunctionIntrinsic> intrinsicCache = new LinkedHashMap();
    private final List<FunctionIntrinsicFactory> factories = CollectionsKt.listOf((Object[]) new FunctionIntrinsicFactory[]{LongOperationFIF.INSTANCE, PrimitiveUnaryOperationFIF.INSTANCE, StringPlusCharFIF.INSTANCE, PrimitiveBinaryOperationFIF.INSTANCE, ArrayFIF.INSTANCE, TopLevelFIF.INSTANCE, NumberAndCharConversionFIF.INSTANCE, ThrowableConstructorIntrinsicFactory.INSTANCE, ExceptionPropertyIntrinsicFactory.INSTANCE, AsDynamicFIF.INSTANCE, CoroutineContextFIF.INSTANCE, SuspendCoroutineUninterceptedOrReturnFIF.INSTANCE, TypeOfFIF.INSTANCE});

    public final FunctionIntrinsic getIntrinsic(FunctionDescriptor descriptor, TranslationContext context) {
        FunctionIntrinsic intrinsic;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (this.intrinsicCache.containsKey(descriptor)) {
            return this.intrinsicCache.get(descriptor);
        }
        Iterator<T> it = this.factories.iterator();
        while (it.hasNext()) {
            intrinsic = ((FunctionIntrinsicFactory) it.next()).getIntrinsic(descriptor, context);
            if (intrinsic != null) {
                this.intrinsicCache.put(descriptor, intrinsic);
                return intrinsic;
            }
        }
        intrinsic = null;
        this.intrinsicCache.put(descriptor, intrinsic);
        return intrinsic;
    }
}
