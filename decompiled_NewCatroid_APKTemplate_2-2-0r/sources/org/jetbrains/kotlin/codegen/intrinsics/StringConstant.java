package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: Trim.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J$\u0010\u0006\u001a\u00020\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/StringConstant;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicCallable;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicWithSpecialReceiver;", "text", "", "(Ljava/lang/String;)V", "invokeMethodWithArguments", "Lorg/jetbrains/kotlin/codegen/StackValue;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "receiver", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class StringConstant extends IntrinsicCallable implements IntrinsicWithSpecialReceiver {
    private final String text;

    /* JADX WARN: Illegal instructions before constructor call */
    public StringConstant(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        Type JAVA_STRING_TYPE = AsmTypes.JAVA_STRING_TYPE;
        Intrinsics.checkNotNullExpressionValue(JAVA_STRING_TYPE, "JAVA_STRING_TYPE");
        super(JAVA_STRING_TYPE, CollectionsKt.emptyList(), null, null, null, 16, null);
        this.text = text;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public StackValue invokeMethodWithArguments(ResolvedCall<?> resolvedCall, StackValue receiver, ExpressionCodegen codegen) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        StackValue stackValueConstant = StackValue.constant(this.text, AsmTypes.JAVA_STRING_TYPE);
        Intrinsics.checkNotNullExpressionValue(stackValueConstant, "constant(...)");
        return stackValueConstant;
    }
}
