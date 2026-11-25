package org.jetbrains.kotlin.codegen.intrinsics;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: IntrinsicCallable.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\\\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032,\u0010\u0007\u001a(\u0012\u0004\u0012\u00020\u0001\u0012\u0013\u0012\u00110\t¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\r0\b¢\u0006\u0002\b\u000e\u001a<\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112,\u0010\u0012\u001a(\u0012\u0004\u0012\u00020\u0001\u0012\u0013\u0012\u00110\t¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\r0\b¢\u0006\u0002\b\u000e\u001a^\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00162\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00032,\u0010\u0012\u001a(\u0012\u0004\u0012\u00020\u0001\u0012\u0013\u0012\u00110\t¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\r0\b¢\u0006\u0002\b\u000e¨\u0006\u0018"}, d2 = {"createBinaryIntrinsicCallable", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicCallable;", "returnType", "Lorg/jetbrains/org/objectweb/asm/Type;", "valueParameterType", "thisType", "receiverType", "lambda", "Lkotlin/Function2;", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "Lkotlin/ParameterName;", "name", RegisterSpec.PREFIX, "", "Lkotlin/ExtensionFunctionType;", "createIntrinsicCallable", "callable", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", "invoke", "createUnaryIntrinsicCallable", "newReturnType", "needPrimitiveCheck", "", "newThisType", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IntrinsicCallableKt {
    public static /* synthetic */ IntrinsicCallable createBinaryIntrinsicCallable$default(Type type, Type type2, Type type3, Type type4, Function2 function2, int i, Object obj) {
        if ((i & 4) != 0) {
            type3 = null;
        }
        if ((i & 8) != 0) {
            type4 = null;
        }
        return createBinaryIntrinsicCallable(type, type2, type3, type4, function2);
    }

    public static final IntrinsicCallable createBinaryIntrinsicCallable(Type returnType, Type valueParameterType, Type type, Type type2, final Function2<? super IntrinsicCallable, ? super InstructionAdapter, Unit> lambda) {
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(valueParameterType, "valueParameterType");
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        AsmUtil.isPrimitive(returnType);
        return new IntrinsicCallable(returnType, type, type2, CollectionsKt.listOf(valueParameterType)) { // from class: org.jetbrains.kotlin.codegen.intrinsics.IntrinsicCallableKt.createBinaryIntrinsicCallable.2
            @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicCallable
            public void invokeIntrinsic(InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(v, "v");
                lambda.invoke(this, v);
            }
        };
    }

    public static /* synthetic */ IntrinsicCallable createUnaryIntrinsicCallable$default(CallableMethod callableMethod, Type type, boolean z, Type type2, Function2 function2, int i, Object obj) {
        if ((i & 2) != 0) {
            type = null;
        }
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            type2 = null;
        }
        return createUnaryIntrinsicCallable(callableMethod, type, z, type2, function2);
    }

    public static final IntrinsicCallable createUnaryIntrinsicCallable(CallableMethod callable, Type type, boolean z, Type type2, Function2<? super IntrinsicCallable, ? super InstructionAdapter, Unit> invoke) {
        Intrinsics.checkNotNullParameter(callable, "callable");
        Intrinsics.checkNotNullParameter(invoke, "invoke");
        if (type == null) {
            type = callable.getReturnType();
        }
        Type type3 = type;
        List<Type> valueParameterTypes = callable.getValueParameterTypes();
        if (type2 == null) {
            type2 = callable.getDispatchReceiverType();
        }
        IntrinsicCallable intrinsicCallable = new IntrinsicCallable(type3, valueParameterTypes, type2, callable.getExtensionReceiverType(), invoke);
        intrinsicCallable.getValueParameterTypes().isEmpty();
        if (z) {
            AsmUtil.isPrimitive(intrinsicCallable.getReturnType());
        }
        return intrinsicCallable;
    }

    public static final IntrinsicCallable createIntrinsicCallable(CallableMethod callable, Function2<? super IntrinsicCallable, ? super InstructionAdapter, Unit> invoke) {
        Intrinsics.checkNotNullParameter(callable, "callable");
        Intrinsics.checkNotNullParameter(invoke, "invoke");
        return new IntrinsicCallable(callable, invoke);
    }
}
