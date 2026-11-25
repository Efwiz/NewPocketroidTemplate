package org.jetbrains.kotlin.codegen.intrinsics;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: IntrinsicCallable.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0011\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\b\u0016\u0018\u00002\u00020\u0001B?\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012.\b\u0002\u0010\u0004\u001a(\u0012\u0004\u0012\u00020\u0000\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005¢\u0006\u0002\b\u000b¢\u0006\u0002\u0010\fB_\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u000e\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u000e\u0012.\b\u0002\u0010\u0004\u001a(\u0012\u0004\u0012\u00020\u0000\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005¢\u0006\u0002\b\u000b¢\u0006\u0002\u0010\u0013J\u0006\u0010*\u001a\u00020\u000eJ\u0010\u0010+\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\u0006H\u0016J\u0010\u0010,\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\u0006H\u0016J\b\u0010-\u001a\u00020.H\u0016R\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\u001a\u001a\u0004\u0018\u00010\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0017R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019R\u0016\u0010\u001d\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0019R4\u0010\u0004\u001a(\u0012\u0004\u0012\u00020\u0000\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0004\u0012\u00020\n0\u0005¢\u0006\u0002\b\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010\u0019R\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000e0\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0016\u0010%\u001a\u0004\u0018\u00010\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010\u0017R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u0019R\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicCallable;", "Lorg/jetbrains/kotlin/codegen/Callable;", "callable", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", "invoke", "Lkotlin/Function2;", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "Lkotlin/ParameterName;", "name", RegisterSpec.PREFIX, "", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/codegen/CallableMethod;Lkotlin/jvm/functions/Function2;)V", "returnType", "Lorg/jetbrains/org/objectweb/asm/Type;", "valueParameterTypes", "", "dispatchReceiverType", "extensionReceiverType", "(Lorg/jetbrains/org/objectweb/asm/Type;Ljava/util/List;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/org/objectweb/asm/Type;Lkotlin/jvm/functions/Function2;)V", "dispatchReceiverKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getDispatchReceiverKotlinType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "extensionReceiverKotlinType", "getExtensionReceiverKotlinType", "getExtensionReceiverType", "generateCalleeType", "getGenerateCalleeType", "owner", "getOwner", "parameterTypes", "", "getParameterTypes", "()[Lorg/jetbrains/org/objectweb/asm/Type;", "returnKotlinType", "getReturnKotlinType", "getReturnType", "getValueParameterTypes", "()Ljava/util/List;", "calcReceiverType", "genInvokeInstruction", "invokeIntrinsic", "isStaticCall", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IntrinsicCallable implements Callable {
    private final Type dispatchReceiverType;
    private final Type extensionReceiverType;
    private final Function2<IntrinsicCallable, InstructionAdapter, Unit> invoke;
    private final Type returnType;
    private final List<Type> valueParameterTypes;

    @Override // org.jetbrains.kotlin.codegen.Callable
    public KotlinType getDispatchReceiverKotlinType() {
        return null;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public KotlinType getExtensionReceiverKotlinType() {
        return null;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public Type getGenerateCalleeType() {
        return null;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public KotlinType getReturnKotlinType() {
        return null;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public boolean isStaticCall() {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public IntrinsicCallable(Type returnType, List<Type> valueParameterTypes, Type type, Type type2, Function2<? super IntrinsicCallable, ? super InstructionAdapter, Unit> invoke) {
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(valueParameterTypes, "valueParameterTypes");
        Intrinsics.checkNotNullParameter(invoke, "invoke");
        this.returnType = returnType;
        this.valueParameterTypes = valueParameterTypes;
        this.dispatchReceiverType = type;
        this.extensionReceiverType = type2;
        this.invoke = invoke;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public Type getReturnType() {
        return this.returnType;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public List<Type> getValueParameterTypes() {
        return this.valueParameterTypes;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public Type getDispatchReceiverType() {
        return this.dispatchReceiverType;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public Type getExtensionReceiverType() {
        return this.extensionReceiverType;
    }

    public /* synthetic */ IntrinsicCallable(Type type, List list, Type type2, Type type3, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(type, list, type2, type3, (i & 16) != 0 ? new Function2<IntrinsicCallable, InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.intrinsics.IntrinsicCallable.1
            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(IntrinsicCallable intrinsicCallable, InstructionAdapter instructionAdapter) {
                invoke2(intrinsicCallable, instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IntrinsicCallable intrinsicCallable, InstructionAdapter it) {
                Intrinsics.checkNotNullParameter(intrinsicCallable, "$this$null");
                Intrinsics.checkNotNullParameter(it, "it");
                throw new UnsupportedOperationException();
            }
        } : anonymousClass1);
    }

    public /* synthetic */ IntrinsicCallable(CallableMethod callableMethod, AnonymousClass2 anonymousClass2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(callableMethod, (i & 2) != 0 ? new Function2<IntrinsicCallable, InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.intrinsics.IntrinsicCallable.2
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IntrinsicCallable intrinsicCallable, InstructionAdapter it) {
                Intrinsics.checkNotNullParameter(intrinsicCallable, "$this$null");
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(IntrinsicCallable intrinsicCallable, InstructionAdapter instructionAdapter) {
                invoke2(intrinsicCallable, instructionAdapter);
                return Unit.INSTANCE;
            }
        } : anonymousClass2);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IntrinsicCallable(CallableMethod callable, Function2<? super IntrinsicCallable, ? super InstructionAdapter, Unit> invoke) {
        this(callable.getReturnType(), callable.getValueParameterTypes(), callable.getDispatchReceiverType(), callable.getExtensionReceiverType(), invoke);
        Intrinsics.checkNotNullParameter(callable, "callable");
        Intrinsics.checkNotNullParameter(invoke, "invoke");
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public void genInvokeInstruction(InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(v, "v");
        invokeIntrinsic(v);
    }

    public void invokeIntrinsic(InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(v, "v");
        this.invoke.invoke(this, v);
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public Type[] getParameterTypes() {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public Type getOwner() {
        throw new UnsupportedOperationException();
    }

    public final Type calcReceiverType() {
        Type extensionReceiverType = getExtensionReceiverType();
        if (extensionReceiverType != null) {
            return extensionReceiverType;
        }
        Type dispatchReceiverType = getDispatchReceiverType();
        Intrinsics.checkNotNull(dispatchReceiverType);
        return dispatchReceiverType;
    }
}
