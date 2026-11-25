package org.jetbrains.kotlin.js.translate.intrinsic;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.FunctionIntrinsics;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;
import org.jetbrains.kotlin.js.translate.intrinsic.objects.ObjectIntrinsic;
import org.jetbrains.kotlin.js.translate.intrinsic.objects.ObjectIntrinsics;
import org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsic;
import org.jetbrains.kotlin.js.translate.intrinsic.operation.BinaryOperationIntrinsics;
import org.jetbrains.kotlin.psi.KtBinaryExpression;

/* JADX INFO: compiled from: Intrinsics.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eJ\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/Intrinsics;", "", "()V", "binaryOperationIntrinsics", "Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/BinaryOperationIntrinsics;", "functionIntrinsics", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/FunctionIntrinsics;", "objectIntrinsics", "Lorg/jetbrains/kotlin/js/translate/intrinsic/objects/ObjectIntrinsics;", "getBinaryOperationIntrinsic", "Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/BinaryOperationIntrinsic;", "expression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "getFunctionIntrinsic", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsic;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getObjectIntrinsic", "Lorg/jetbrains/kotlin/js/translate/intrinsic/objects/ObjectIntrinsic;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Intrinsics {
    private final FunctionIntrinsics functionIntrinsics = new FunctionIntrinsics();
    private final BinaryOperationIntrinsics binaryOperationIntrinsics = new BinaryOperationIntrinsics();
    private final ObjectIntrinsics objectIntrinsics = new ObjectIntrinsics();

    public final BinaryOperationIntrinsic getBinaryOperationIntrinsic(KtBinaryExpression expression, TranslationContext context) {
        kotlin.jvm.internal.Intrinsics.checkNotNullParameter(expression, "expression");
        kotlin.jvm.internal.Intrinsics.checkNotNullParameter(context, "context");
        return this.binaryOperationIntrinsics.getIntrinsic(expression, context);
    }

    public final FunctionIntrinsic getFunctionIntrinsic(FunctionDescriptor descriptor, TranslationContext context) {
        kotlin.jvm.internal.Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        kotlin.jvm.internal.Intrinsics.checkNotNullParameter(context, "context");
        return this.functionIntrinsics.getIntrinsic(descriptor, context);
    }

    public final ObjectIntrinsic getObjectIntrinsic(ClassDescriptor classDescriptor) {
        kotlin.jvm.internal.Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        return this.objectIntrinsics.getIntrinsic(classDescriptor);
    }
}
