package org.jetbrains.kotlin.backend.jvm.intrinsics;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: Clone.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/Clone;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "CLONEABLE_TYPE", "Lorg/jetbrains/org/objectweb/asm/Type;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "toCallable", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicFunction;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Clone extends IntrinsicMethod {
    public static final Clone INSTANCE = new Clone();
    private static final Type CLONEABLE_TYPE = Type.getObjectType("java/lang/Cloneable");

    private Clone() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public IntrinsicFunction toCallable(IrFunctionAccessExpression expression, JvmMethodSignature signature, ClassCodegen classCodegen) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        boolean z = (expression instanceof IrCall) && ((IrCall) expression).getSuperQualifierSymbol() != null;
        final int i = z ? 183 : 182;
        IntrinsicMethod.Companion companion = IntrinsicMethod.INSTANCE;
        Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
        Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
        JvmMethodSignature jvmMethodSignatureNewReturnType = companion.newReturnType(signature, OBJECT_TYPE);
        List<Type> listArgTypes = IntrinsicFunctionKt.argTypes(expression, classCodegen);
        if (z || Intrinsics.areEqual(listArgTypes.get(0), CLONEABLE_TYPE)) {
            listArgTypes = CollectionsKt.listOf(AsmTypes.OBJECT_TYPE);
        }
        return IntrinsicFunction.INSTANCE.create(expression, jvmMethodSignatureNewReturnType, classCodegen, listArgTypes, new Function2<IntrinsicFunction, InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.intrinsics.Clone.toCallable.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(IntrinsicFunction intrinsicFunction, InstructionAdapter instructionAdapter) {
                invoke2(intrinsicFunction, instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IntrinsicFunction create, InstructionAdapter mv) {
                Intrinsics.checkNotNullParameter(create, "$this$create");
                Intrinsics.checkNotNullParameter(mv, "mv");
                mv.visitMethodInsn(i, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, "clone", "()Ljava/lang/Object;", false);
            }
        });
    }
}
