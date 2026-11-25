package org.jetbrains.kotlin.codegen.intrinsics;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: CompareTo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/CompareTo;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "()V", "genInvoke", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "method", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompareTo extends IntrinsicMethod {
    /* JADX INFO: Access modifiers changed from: private */
    public final void genInvoke(Type type, InstructionAdapter v) {
        if (Intrinsics.areEqual(type, Type.INT_TYPE) ? true : Intrinsics.areEqual(type, Type.CHAR_TYPE)) {
            v.invokestatic("kotlin/jvm/internal/Intrinsics", "compare", "(II)I", false);
            return;
        }
        if (Intrinsics.areEqual(type, Type.LONG_TYPE)) {
            v.lcmp();
            return;
        }
        if (Intrinsics.areEqual(type, Type.FLOAT_TYPE)) {
            v.invokestatic("java/lang/Float", "compare", "(FF)I", false);
        } else if (Intrinsics.areEqual(type, Type.DOUBLE_TYPE)) {
            v.invokestatic("java/lang/Double", "compare", "(DD)I", false);
        } else {
            if (!Intrinsics.areEqual(type, Type.BOOLEAN_TYPE)) {
                throw new UnsupportedOperationException();
            }
            v.invokestatic("java/lang/Boolean", "compare", "(ZZ)I", false);
        }
    }

    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    protected Callable toCallable(CallableMethod method) {
        Intrinsics.checkNotNullParameter(method, "method");
        Type dispatchReceiverType = method.getDispatchReceiverType();
        if (dispatchReceiverType == null) {
            dispatchReceiverType = method.getExtensionReceiverType();
        }
        final Type typeComparisonOperandType = AsmUtil.comparisonOperandType(dispatchReceiverType, (Type) ArraysKt.single(method.getParameterTypes()));
        Type returnType = method.getReturnType();
        Intrinsics.checkNotNull(typeComparisonOperandType);
        return IntrinsicCallableKt.createBinaryIntrinsicCallable(returnType, typeComparisonOperandType, typeComparisonOperandType, null, new Function2<IntrinsicCallable, InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.intrinsics.CompareTo.toCallable.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                CompareTo.this.genInvoke(typeComparisonOperandType, it);
            }
        });
    }
}
