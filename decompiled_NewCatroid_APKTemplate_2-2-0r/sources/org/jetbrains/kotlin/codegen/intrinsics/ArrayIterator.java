package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: ArrayIterator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/ArrayIterator;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "()V", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "method", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayIterator extends IntrinsicMethod {
    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    protected Callable toCallable(final CallableMethod method) {
        Intrinsics.checkNotNullParameter(method, "method");
        return IntrinsicCallableKt.createUnaryIntrinsicCallable$default(method, null, false, null, new Function2<IntrinsicCallable, InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.intrinsics.ArrayIterator.toCallable.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(IntrinsicCallable intrinsicCallable, InstructionAdapter instructionAdapter) {
                invoke2(intrinsicCallable, instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IntrinsicCallable createUnaryIntrinsicCallable, InstructionAdapter it) {
                String str;
                Intrinsics.checkNotNullParameter(createUnaryIntrinsicCallable, "$this$createUnaryIntrinsicCallable");
                Intrinsics.checkNotNullParameter(it, "it");
                String str2 = "(" + method.getOwner().getDescriptor() + ')' + createUnaryIntrinsicCallable.getReturnType().getDescriptor();
                if (AsmUtil.isPrimitive(method.getOwner().getElementType())) {
                    str = "kotlin/jvm/internal/ArrayIteratorsKt";
                } else {
                    str = "kotlin/jvm/internal/ArrayIteratorKt";
                }
                it.invokestatic(str, "iterator", str2, false);
            }
        }, 14, null);
    }
}
