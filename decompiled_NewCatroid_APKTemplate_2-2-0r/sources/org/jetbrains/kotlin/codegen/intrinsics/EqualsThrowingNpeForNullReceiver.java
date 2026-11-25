package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: Equals.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/EqualsThrowingNpeForNullReceiver;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "lhsType", "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/org/objectweb/asm/Type;)V", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "method", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EqualsThrowingNpeForNullReceiver extends IntrinsicMethod {
    private final Type lhsType;

    public EqualsThrowingNpeForNullReceiver(Type lhsType) {
        Intrinsics.checkNotNullParameter(lhsType, "lhsType");
        this.lhsType = lhsType;
    }

    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    protected Callable toCallable(CallableMethod method) {
        Intrinsics.checkNotNullParameter(method, "method");
        Type returnType = method.getReturnType();
        Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
        Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
        return IntrinsicCallableKt.createBinaryIntrinsicCallable(returnType, OBJECT_TYPE, nullOrObject(method.getDispatchReceiverType()), nullOrObject(method.getExtensionReceiverType()), new Function2<IntrinsicCallable, InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.intrinsics.EqualsThrowingNpeForNullReceiver.toCallable.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(IntrinsicCallable intrinsicCallable, InstructionAdapter instructionAdapter) {
                invoke2(intrinsicCallable, instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IntrinsicCallable createBinaryIntrinsicCallable, InstructionAdapter it) {
                Intrinsics.checkNotNullParameter(createBinaryIntrinsicCallable, "$this$createBinaryIntrinsicCallable");
                Intrinsics.checkNotNullParameter(it, "it");
                it.invokevirtual(EqualsThrowingNpeForNullReceiver.this.lhsType.getInternalName(), Namer.EQUALS_METHOD_NAME, EqualsKt.getEqualsMethodDescriptor(), false);
            }
        });
    }
}
