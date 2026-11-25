package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: Callable.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J \u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H&J$\u0010'\u001a\u00020(2\n\u0010)\u001a\u0006\u0012\u0002\b\u00030*2\u0006\u0010+\u001a\u00020(2\u0006\u0010,\u001a\u00020-H\u0016J\b\u0010.\u001a\u00020/H&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005R\u0014\u0010\f\u001a\u0004\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\tR\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\tR\u0012\u0010\u0010\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\tR\u0018\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0005R\u0012\u0010\u0018\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\tR\u0018\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00070\u001bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u00060À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/Callable;", "", "dispatchReceiverKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getDispatchReceiverKotlinType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "dispatchReceiverType", "Lorg/jetbrains/org/objectweb/asm/Type;", "getDispatchReceiverType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "extensionReceiverKotlinType", "getExtensionReceiverKotlinType", "extensionReceiverType", "getExtensionReceiverType", "generateCalleeType", "getGenerateCalleeType", "owner", "getOwner", "parameterTypes", "", "getParameterTypes", "()[Lorg/jetbrains/org/objectweb/asm/Type;", "returnKotlinType", "getReturnKotlinType", "returnType", "getReturnType", "valueParameterTypes", "", "getValueParameterTypes", "()Ljava/util/List;", "afterReceiverGeneration", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "frameMap", "Lorg/jetbrains/kotlin/codegen/FrameMap;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "genInvokeInstruction", "invokeMethodWithArguments", "Lorg/jetbrains/kotlin/codegen/StackValue;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "receiver", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "isStaticCall", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Callable {
    default void afterReceiverGeneration(InstructionAdapter v, FrameMap frameMap, GenerationState state) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(frameMap, "frameMap");
        Intrinsics.checkNotNullParameter(state, "state");
    }

    void genInvokeInstruction(InstructionAdapter v);

    KotlinType getDispatchReceiverKotlinType();

    Type getDispatchReceiverType();

    KotlinType getExtensionReceiverKotlinType();

    Type getExtensionReceiverType();

    Type getGenerateCalleeType();

    Type getOwner();

    Type[] getParameterTypes();

    KotlinType getReturnKotlinType();

    Type getReturnType();

    List<Type> getValueParameterTypes();

    boolean isStaticCall();

    default StackValue invokeMethodWithArguments(final ResolvedCall<?> resolvedCall, final StackValue receiver, final ExpressionCodegen codegen) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Type returnType = getReturnType();
        KotlinType returnKotlinType = getReturnKotlinType();
        if (returnKotlinType == null) {
            returnKotlinType = resolvedCall.getResultingDescriptor().getOriginal().getReturnType();
        }
        StackValue stackValueFunctionCall = StackValue.functionCall(returnType, returnKotlinType, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.Callable.invokeMethodWithArguments.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                codegen.invokeMethodWithArguments(this, resolvedCall, receiver);
            }
        });
        Intrinsics.checkNotNullExpressionValue(stackValueFunctionCall, "functionCall(...)");
        return stackValueFunctionCall;
    }
}
