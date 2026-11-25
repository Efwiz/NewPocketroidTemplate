package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: ArraySet.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/ArraySet;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "()V", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "method", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArraySet extends IntrinsicMethod {
    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    protected Callable toCallable(CallableMethod method) {
        Intrinsics.checkNotNullParameter(method, "method");
        Type dispatchReceiverType = method.getDispatchReceiverType();
        Intrinsics.checkNotNull(dispatchReceiverType);
        Type typeCorrectElementType = AsmUtil.correctElementType(dispatchReceiverType);
        Intrinsics.checkNotNullExpressionValue(typeCorrectElementType, "correctElementType(...)");
        return new IntrinsicCallable(Type.VOID_TYPE, CollectionsKt.listOf((Object[]) new Type[]{Type.INT_TYPE, typeCorrectElementType}), method.getDispatchReceiverType(), method.getExtensionReceiverType()) { // from class: org.jetbrains.kotlin.codegen.intrinsics.ArraySet.toCallable.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(type, list, type, type, null, 16, null);
                Intrinsics.checkNotNull(type);
            }

            @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicCallable
            public void invokeIntrinsic(InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(v, "v");
                v.astore(this.$type);
            }
        };
    }
}
