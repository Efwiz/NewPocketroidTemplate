package org.jetbrains.kotlin.backend.jvm.mapping;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;
import org.jetbrains.org.objectweb.asm.util.Printer;

/* JADX INFO: compiled from: IrCallableMethod.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\b\u0010\u001e\u001a\u00020\u001fH\u0016R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00030\u001b¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/mapping/IrCallableMethod;", "", "owner", "Lorg/jetbrains/org/objectweb/asm/Type;", "invokeOpcode", "", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "isInterfaceMethod", "", "returnType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/org/objectweb/asm/Type;ILorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;ZLorg/jetbrains/kotlin/ir/types/IrType;)V", "asmMethod", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "getAsmMethod", "()Lorg/jetbrains/org/objectweb/asm/commons/Method;", "getInvokeOpcode", "()I", "()Z", "getOwner", "()Lorg/jetbrains/org/objectweb/asm/Type;", "getReturnType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "getSignature", "()Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "valueParameterTypes", "", "getValueParameterTypes", "()Ljava/util/List;", "toString", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrCallableMethod {
    private final Method asmMethod;
    private final int invokeOpcode;
    private final boolean isInterfaceMethod;
    private final Type owner;
    private final IrType returnType;
    private final JvmMethodSignature signature;
    private final List<Type> valueParameterTypes;

    public IrCallableMethod(Type owner, int i, JvmMethodSignature signature, boolean z, IrType returnType) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        this.owner = owner;
        this.invokeOpcode = i;
        this.signature = signature;
        this.isInterfaceMethod = z;
        this.returnType = returnType;
        Method asmMethod = signature.getAsmMethod();
        Intrinsics.checkNotNullExpressionValue(asmMethod, "getAsmMethod(...)");
        this.asmMethod = asmMethod;
        List<JvmMethodParameterSignature> valueParameters = signature.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : valueParameters) {
            if (((JvmMethodParameterSignature) obj).getKind() != JvmMethodParameterKind.RECEIVER) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((JvmMethodParameterSignature) it.next()).getAsmType());
        }
        this.valueParameterTypes = arrayList3;
    }

    public final Type getOwner() {
        return this.owner;
    }

    public final int getInvokeOpcode() {
        return this.invokeOpcode;
    }

    public final JvmMethodSignature getSignature() {
        return this.signature;
    }

    /* JADX INFO: renamed from: isInterfaceMethod, reason: from getter */
    public final boolean getIsInterfaceMethod() {
        return this.isInterfaceMethod;
    }

    public final IrType getReturnType() {
        return this.returnType;
    }

    public final Method getAsmMethod() {
        return this.asmMethod;
    }

    public final List<Type> getValueParameterTypes() {
        return this.valueParameterTypes;
    }

    public String toString() {
        return Printer.OPCODES[this.invokeOpcode] + StringUtil.SPACE + this.owner + '.' + this.asmMethod + (this.isInterfaceMethod ? " (itf)" : "");
    }
}
