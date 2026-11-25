package org.jetbrains.kotlin.backend.jvm.intrinsics;

import com.android.dx.rop.code.RegisterSpec;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: CompareTo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J \u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/CompareTo;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "genInvoke", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "toCallable", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicFunction;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompareTo extends IntrinsicMethod {
    public static final CompareTo INSTANCE = new CompareTo();

    private CompareTo() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void genInvoke(Type type, InstructionAdapter v) {
        if (Intrinsics.areEqual(type, Type.CHAR_TYPE) ? true : Intrinsics.areEqual(type, Type.BYTE_TYPE) ? true : Intrinsics.areEqual(type, Type.SHORT_TYPE) ? true : Intrinsics.areEqual(type, Type.INT_TYPE)) {
            v.invokestatic("kotlin/jvm/internal/Intrinsics", "compare", "(II)I", false);
            return;
        }
        if (Intrinsics.areEqual(type, Type.LONG_TYPE)) {
            v.invokestatic("kotlin/jvm/internal/Intrinsics", "compare", "(JJ)I", false);
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

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public IntrinsicFunction toCallable(IrFunctionAccessExpression expression, JvmMethodSignature signature, ClassCodegen classCodegen) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        IrFunction owner = expression.getSymbol().getOwner();
        IrValueParameter dispatchReceiverParameter = owner.getDispatchReceiverParameter();
        if (dispatchReceiverParameter == null) {
            dispatchReceiverParameter = owner.getExtensionReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
        }
        Type typeMapType$default = IrTypeMapper.mapType$default(classCodegen.getTypeMapper(), dispatchReceiverParameter.getType(), null, null, false, 14, null);
        List<JvmMethodParameterSignature> valueParameters = signature.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        final Type typeComparisonOperandType = AsmUtil.comparisonOperandType(typeMapType$default, ((JvmMethodParameterSignature) CollectionsKt.single((List) valueParameters)).getAsmType());
        return IntrinsicFunction.INSTANCE.create(expression, signature, classCodegen, CollectionsKt.listOf((Object[]) new Type[]{typeComparisonOperandType, typeComparisonOperandType}), new Function2<IntrinsicFunction, InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.intrinsics.CompareTo.toCallable.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(IntrinsicFunction intrinsicFunction, InstructionAdapter instructionAdapter) {
                invoke2(intrinsicFunction, instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IntrinsicFunction create, InstructionAdapter it) {
                Intrinsics.checkNotNullParameter(create, "$this$create");
                Intrinsics.checkNotNullParameter(it, "it");
                CompareTo.INSTANCE.genInvoke(typeComparisonOperandType, it);
            }
        });
    }
}
