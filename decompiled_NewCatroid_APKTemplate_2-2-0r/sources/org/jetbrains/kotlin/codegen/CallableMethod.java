package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.state.StaticTypeMapperForOldBackend;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;
import org.jetbrains.org.objectweb.asm.util.Printer;

/* JADX INFO: compiled from: CallableMethod.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0089\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u000e\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u000e\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u0012\u0006\u0010\u0016\u001a\u00020\u0014¢\u0006\u0002\u0010\u0017J\u000e\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<J\u0010\u0010=\u001a\u00020:2\u0006\u0010;\u001a\u00020<H\u0016J\u0006\u0010>\u001a\u00020\u0007J\f\u0010?\u001a\b\u0012\u0004\u0012\u00020@06J\b\u0010A\u001a\u00020\u0014H\u0016J\b\u0010B\u001a\u00020\u001eH\u0016R\u000e\u0010\u0016\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0018\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001d\u001a\u00020\u001e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\u00020\u001e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010 R\u0016\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0016\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010$R\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010&R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010&R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010,R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010&R\u001a\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00030/8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u0010$R\u0014\u00103\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u0010&R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u00105\u001a\b\u0012\u0004\u0012\u00020\u0003068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b7\u00108¨\u0006C"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CallableMethod;", "Lorg/jetbrains/kotlin/codegen/Callable;", "owner", "Lorg/jetbrains/org/objectweb/asm/Type;", "defaultImplOwner", "computeDefaultMethod", "Lkotlin/Function0;", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "invokeOpcode", "", "dispatchReceiverType", "dispatchReceiverKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "extensionReceiverType", "extensionReceiverKotlinType", "generateCalleeType", "returnKotlinType", "isInterfaceMethod", "", "isDefaultMethodInInterface", "boxInlineClassBeforeInvoke", "(Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/org/objectweb/asm/Type;Lkotlin/jvm/functions/Function0;Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;ILorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/types/KotlinType;ZZZ)V", "defaultImplMethod", "getDefaultImplMethod", "()Lorg/jetbrains/org/objectweb/asm/commons/Method;", "defaultImplMethod$delegate", "Lkotlin/Lazy;", "defaultImplMethodName", "", "getDefaultImplMethodName", "()Ljava/lang/String;", "defaultMethodDesc", "getDefaultMethodDesc", "getDispatchReceiverKotlinType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getDispatchReceiverType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "getExtensionReceiverKotlinType", "getExtensionReceiverType", "getGenerateCalleeType", "getInvokeOpcode", "()I", "()Z", "getOwner", "parameterTypes", "", "getParameterTypes", "()[Lorg/jetbrains/org/objectweb/asm/Type;", "getReturnKotlinType", "returnType", "getReturnType", "valueParameterTypes", "", "getValueParameterTypes", "()Ljava/util/List;", "genInvokeDefaultInstruction", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "genInvokeInstruction", "getAsmMethod", "getValueParameters", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodParameterSignature;", "isStaticCall", "toString", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallableMethod implements Callable {
    private final boolean boxInlineClassBeforeInvoke;

    /* JADX INFO: renamed from: defaultImplMethod$delegate, reason: from kotlin metadata */
    private final Lazy defaultImplMethod;
    private final Type defaultImplOwner;
    private final KotlinType dispatchReceiverKotlinType;
    private final Type dispatchReceiverType;
    private final KotlinType extensionReceiverKotlinType;
    private final Type extensionReceiverType;
    private final Type generateCalleeType;
    private final int invokeOpcode;
    private final boolean isDefaultMethodInInterface;
    private final boolean isInterfaceMethod;
    private final Type owner;
    private final KotlinType returnKotlinType;
    private final JvmMethodSignature signature;

    public CallableMethod(Type owner, Type type, Function0<? extends Method> computeDefaultMethod, JvmMethodSignature signature, int i, Type type2, KotlinType kotlinType, Type type3, KotlinType kotlinType2, Type type4, KotlinType kotlinType3, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(computeDefaultMethod, "computeDefaultMethod");
        Intrinsics.checkNotNullParameter(signature, "signature");
        this.owner = owner;
        this.defaultImplOwner = type;
        this.signature = signature;
        this.invokeOpcode = i;
        this.dispatchReceiverType = type2;
        this.dispatchReceiverKotlinType = kotlinType;
        this.extensionReceiverType = type3;
        this.extensionReceiverKotlinType = kotlinType2;
        this.generateCalleeType = type4;
        this.returnKotlinType = kotlinType3;
        this.isInterfaceMethod = z;
        this.isDefaultMethodInInterface = z2;
        this.boxInlineClassBeforeInvoke = z3;
        this.defaultImplMethod = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) computeDefaultMethod);
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public Type getOwner() {
        return this.owner;
    }

    public final int getInvokeOpcode() {
        return this.invokeOpcode;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public Type getDispatchReceiverType() {
        return this.dispatchReceiverType;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public KotlinType getDispatchReceiverKotlinType() {
        return this.dispatchReceiverKotlinType;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public Type getExtensionReceiverType() {
        return this.extensionReceiverType;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public KotlinType getExtensionReceiverKotlinType() {
        return this.extensionReceiverKotlinType;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public Type getGenerateCalleeType() {
        return this.generateCalleeType;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public KotlinType getReturnKotlinType() {
        return this.returnKotlinType;
    }

    /* JADX INFO: renamed from: isInterfaceMethod, reason: from getter */
    public final boolean getIsInterfaceMethod() {
        return this.isInterfaceMethod;
    }

    private final Method getDefaultImplMethod() {
        return (Method) this.defaultImplMethod.getValue();
    }

    private final String getDefaultImplMethodName() {
        String name2 = getDefaultImplMethod().getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }

    private final String getDefaultMethodDesc() {
        String descriptor = getDefaultImplMethod().getDescriptor();
        Intrinsics.checkNotNullExpressionValue(descriptor, "getDescriptor(...)");
        return descriptor;
    }

    public final List<JvmMethodParameterSignature> getValueParameters() {
        List<JvmMethodParameterSignature> valueParameters = this.signature.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        return valueParameters;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public List<Type> getValueParameterTypes() {
        List<JvmMethodParameterSignature> valueParameters = this.signature.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : valueParameters) {
            if (((JvmMethodParameterSignature) obj).getKind() == JvmMethodParameterKind.VALUE) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((JvmMethodParameterSignature) it.next()).getAsmType());
        }
        return arrayList3;
    }

    public final Method getAsmMethod() {
        Method asmMethod = this.signature.getAsmMethod();
        Intrinsics.checkNotNullExpressionValue(asmMethod, "getAsmMethod(...)");
        return asmMethod;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public Type[] getParameterTypes() {
        Type[] argumentTypes = getAsmMethod().getArgumentTypes();
        Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
        return argumentTypes;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public void genInvokeInstruction(InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(v, "v");
        if (this.boxInlineClassBeforeInvoke) {
            KotlinType dispatchReceiverKotlinType = getDispatchReceiverKotlinType();
            Intrinsics.checkNotNull(dispatchReceiverKotlinType);
            StackValue.boxInlineClass(dispatchReceiverKotlinType, v, StaticTypeMapperForOldBackend.INSTANCE);
        }
        v.visitMethodInsn(this.invokeOpcode, getOwner().getInternalName(), getAsmMethod().getName(), getAsmMethod().getDescriptor(), this.isInterfaceMethod);
    }

    public final void genInvokeDefaultInstruction(InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(v, "v");
        if (this.defaultImplOwner == null) {
            throw new IllegalStateException();
        }
        if (Intrinsics.areEqual("<init>", getAsmMethod().getName())) {
            v.visitMethodInsn(183, this.defaultImplOwner.getInternalName(), "<init>", getDefaultMethodDesc(), false);
        } else {
            v.visitMethodInsn(184, this.defaultImplOwner.getInternalName(), getDefaultImplMethodName(), getDefaultMethodDesc(), this.isDefaultMethodInInterface);
            StackValue.coerce(Type.getReturnType(getDefaultMethodDesc()), Type.getReturnType(this.signature.getAsmMethod().getDescriptor()), v);
        }
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public Type getReturnType() {
        Type returnType = this.signature.getReturnType();
        Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
        return returnType;
    }

    @Override // org.jetbrains.kotlin.codegen.Callable
    public boolean isStaticCall() {
        return this.invokeOpcode == 184;
    }

    public String toString() {
        return Printer.OPCODES[this.invokeOpcode] + StringUtil.SPACE + getOwner() + '.' + this.signature;
    }
}
