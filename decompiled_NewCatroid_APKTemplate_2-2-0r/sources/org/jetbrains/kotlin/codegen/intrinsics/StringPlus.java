package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.StringConcatGenerator;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: StringPlus.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/StringPlus;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "()V", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "method", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StringPlus extends IntrinsicMethod {
    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    protected Callable toCallable(CallableMethod method) {
        Intrinsics.checkNotNullParameter(method, "method");
        return new IntrinsicCallable(method) { // from class: org.jetbrains.kotlin.codegen.intrinsics.StringPlus.toCallable.1
            private StringConcatGenerator generator;

            @Override // org.jetbrains.kotlin.codegen.Callable
            public StackValue invokeMethodWithArguments(ResolvedCall<?> resolvedCall, StackValue receiver, ExpressionCodegen codegen) {
                Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
                Intrinsics.checkNotNullParameter(receiver, "receiver");
                Intrinsics.checkNotNullParameter(codegen, "codegen");
                StringConcatGenerator.Companion companion = StringConcatGenerator.INSTANCE;
                GenerationState state = codegen.getState();
                Intrinsics.checkNotNullExpressionValue(state, "getState(...)");
                InstructionAdapter v = codegen.v;
                Intrinsics.checkNotNullExpressionValue(v, "v");
                this.generator = companion.create(state, v);
                return super.invokeMethodWithArguments(resolvedCall, receiver, codegen);
            }

            @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicCallable, org.jetbrains.kotlin.codegen.Callable
            public void genInvokeInstruction(InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(v, "v");
                StringConcatGenerator stringConcatGenerator = this.generator;
                StringConcatGenerator stringConcatGenerator2 = null;
                if (stringConcatGenerator == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("generator");
                    stringConcatGenerator = null;
                }
                if (!stringConcatGenerator.getMode().isDynamic()) {
                    v.invokestatic("kotlin/jvm/internal/Intrinsics", "stringPlus", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;", false);
                    return;
                }
                StringConcatGenerator stringConcatGenerator3 = this.generator;
                if (stringConcatGenerator3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("generator");
                    stringConcatGenerator3 = null;
                }
                Type JAVA_STRING_TYPE = AsmTypes.JAVA_STRING_TYPE;
                Intrinsics.checkNotNullExpressionValue(JAVA_STRING_TYPE, "JAVA_STRING_TYPE");
                stringConcatGenerator3.invokeAppend(JAVA_STRING_TYPE);
                StringConcatGenerator stringConcatGenerator4 = this.generator;
                if (stringConcatGenerator4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("generator");
                    stringConcatGenerator4 = null;
                }
                Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
                Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
                stringConcatGenerator4.invokeAppend(OBJECT_TYPE);
                StringConcatGenerator stringConcatGenerator5 = this.generator;
                if (stringConcatGenerator5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("generator");
                } else {
                    stringConcatGenerator2 = stringConcatGenerator5;
                }
                stringConcatGenerator2.genToString();
            }
        };
    }
}
