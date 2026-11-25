package org.jetbrains.kotlin.backend.jvm.intrinsics;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMappingKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: IntrinsicFunction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0000¨\u0006\u0006"}, d2 = {"argTypes", "", "Lorg/jetbrains/org/objectweb/asm/Type;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "backend.jvm.codegen"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IntrinsicFunctionKt {
    public static final List<Type> argTypes(IrFunctionAccessExpression irFunctionAccessExpression, ClassCodegen classCodegen) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        IrFunction owner = irFunctionAccessExpression.getSymbol().getOwner();
        JvmMethodSignature jvmMethodSignatureMapSignatureSkipGeneric = classCodegen.getMethodSignatureMapper().mapSignatureSkipGeneric(owner);
        ArrayList arrayList = new ArrayList();
        if (irFunctionAccessExpression.getDispatchReceiver() != null) {
            arrayList.add(IrTypeMappingKt.mapClass(classCodegen.getTypeMapper(), IrUtilsKt.getParentAsClass(owner)));
        }
        Type[] argumentTypes = jvmMethodSignatureMapSignatureSkipGeneric.getAsmMethod().getArgumentTypes();
        Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
        CollectionsKt.addAll(arrayList, argumentTypes);
        return arrayList;
    }
}
