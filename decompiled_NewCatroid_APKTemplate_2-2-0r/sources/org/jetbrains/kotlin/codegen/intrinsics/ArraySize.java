package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: ArraySize.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J6\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u00042\f\u0010\u0006\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004H\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/ArraySize;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicPropertyGetter;", "()V", CompilerOptions.GENERATE, "Lorg/jetbrains/kotlin/codegen/StackValue;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "returnType", "Lorg/jetbrains/org/objectweb/asm/Type;", "receiver", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArraySize extends IntrinsicPropertyGetter {
    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicPropertyGetter
    public StackValue generate(ResolvedCall<?> resolvedCall, ExpressionCodegen codegen, Type returnType, final StackValue receiver) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        return StackValue.operation(returnType, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.intrinsics.ArraySize.generate.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(InstructionAdapter instructionAdapter) {
                invoke2(instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(InstructionAdapter instructionAdapter) {
                receiver.put(instructionAdapter);
                instructionAdapter.arraylength();
            }
        });
    }
}
