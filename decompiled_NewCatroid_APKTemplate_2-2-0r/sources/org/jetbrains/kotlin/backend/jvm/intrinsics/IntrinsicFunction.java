package org.jetbrains.kotlin.backend.jvm.intrinsics;

import com.android.dx.rop.code.RegisterSpec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: IntrinsicFunction.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 '2\u00020\u0001:\u0001'B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ(\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0002\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0002J\u0010\u0010!\u001a\u00020\u00192\u0006\u0010\"\u001a\u00020#H&J(\u0010$\u001a\u00020%2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0002\u001a\u00020\u0003H\u0016J\u0018\u0010&\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 H\u0002R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\t*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicFunction;", "", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "argsTypes", "", "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;Ljava/util/List;)V", "getArgsTypes", "()Ljava/util/List;", "getClassCodegen", "()Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "getSignature", "()Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "typeArguments", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getTypeArguments", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;)Ljava/util/List;", "genArg", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "index", "", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "genInvokeInstruction", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "invoke", "Lorg/jetbrains/kotlin/codegen/StackValue;", "loadArguments", "Companion", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IntrinsicFunction {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<Type> argsTypes;
    private final ClassCodegen classCodegen;
    private final IrFunctionAccessExpression expression;
    private final JvmMethodSignature signature;

    public abstract void genInvokeInstruction(InstructionAdapter v);

    public IntrinsicFunction(IrFunctionAccessExpression expression, JvmMethodSignature signature, ClassCodegen classCodegen, List<Type> argsTypes) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        Intrinsics.checkNotNullParameter(argsTypes, "argsTypes");
        this.expression = expression;
        this.signature = signature;
        this.classCodegen = classCodegen;
        this.argsTypes = argsTypes;
    }

    public final IrFunctionAccessExpression getExpression() {
        return this.expression;
    }

    public final JvmMethodSignature getSignature() {
        return this.signature;
    }

    public final ClassCodegen getClassCodegen() {
        return this.classCodegen;
    }

    public final List<Type> getArgsTypes() {
        return this.argsTypes;
    }

    public StackValue invoke(InstructionAdapter v, ExpressionCodegen codegen, BlockInfo data, IrFunctionAccessExpression expression) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(expression, "expression");
        loadArguments(codegen, data);
        codegen.markLineNumber(expression, true);
        genInvokeInstruction(v);
        StackValue stackValueOnStack = StackValue.onStack(this.signature.getReturnType());
        Intrinsics.checkNotNullExpressionValue(stackValueOnStack, "onStack(...)");
        return stackValueOnStack;
    }

    private final void loadArguments(ExpressionCodegen codegen, BlockInfo data) {
        int i;
        IrExpression dispatchReceiver = this.expression.getDispatchReceiver();
        int i2 = 0;
        if (dispatchReceiver != null) {
            genArg(dispatchReceiver, codegen, 0, data);
            i = 1;
        } else {
            i = 0;
        }
        IrExpression extensionReceiver = this.expression.getExtensionReceiver();
        if (extensionReceiver != null) {
            genArg(extensionReceiver, codegen, i, data);
            i++;
        }
        for (IrValueParameter irValueParameter : this.expression.getSymbol().getOwner().getValueParameters()) {
            int i3 = i2 + 1;
            IrExpression valueArgument = this.expression.getValueArgument(i2);
            if (valueArgument != null) {
                genArg(valueArgument, codegen, i2 + i, data);
            } else if (AdditionalIrUtilsKt.isVararg(irValueParameter)) {
                final Type typeMapType$default = IrTypeMapper.mapType$default(codegen.getTypeMapper(), IrTypeUtilsKt.substitute(irValueParameter.getType(), this.expression.getSymbol().getOwner().getTypeParameters(), getTypeArguments(this.expression)), null, null, false, 14, null);
                StackValue.operation(typeMapType$default, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicFunction.loadArguments.3
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
                        instructionAdapter.aconst(0);
                        instructionAdapter.newarray(AsmUtil.correctElementType(typeMapType$default));
                    }
                }).put(typeMapType$default, codegen.getMv());
            } else {
                throw new IllegalStateException(("Unknown parameter " + irValueParameter.getName() + " in: " + DumpIrTreeKt.dump$default(this.expression, null, 1, null)).toString());
            }
            i2 = i3;
        }
    }

    private final void genArg(IrExpression expression, ExpressionCodegen codegen, int index, BlockInfo data) {
        codegen.gen(expression, this.argsTypes.get(index), expression.getType(), data);
    }

    private final List<IrType> getTypeArguments(IrFunctionAccessExpression irFunctionAccessExpression) {
        IntRange intRangeUntil = RangesKt.until(0, irFunctionAccessExpression.getTypeArgumentsCount());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
        Iterator<Integer> it = intRangeUntil.iterator();
        while (it.hasNext()) {
            IrType typeArgument = irFunctionAccessExpression.getTypeArgument(((IntIterator) it).nextInt());
            Intrinsics.checkNotNull(typeArgument);
            arrayList.add(typeArgument);
        }
        return arrayList;
    }

    /* JADX INFO: compiled from: IntrinsicFunction.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JM\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u001d\u0010\u000e\u001a\u0019\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000f¢\u0006\u0002\b\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicFunction$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicFunction;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "argsTypes", "", "Lorg/jetbrains/org/objectweb/asm/Type;", "invokeInstruction", "Lkotlin/Function2;", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "", "Lkotlin/ExtensionFunctionType;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ IntrinsicFunction create$default(Companion companion, IrFunctionAccessExpression irFunctionAccessExpression, JvmMethodSignature jvmMethodSignature, ClassCodegen classCodegen, List list, Function2 function2, int i, Object obj) {
            if ((i & 8) != 0) {
                list = IntrinsicFunctionKt.argTypes(irFunctionAccessExpression, classCodegen);
            }
            return companion.create(irFunctionAccessExpression, jvmMethodSignature, classCodegen, list, function2);
        }

        public final IntrinsicFunction create(final IrFunctionAccessExpression expression, final JvmMethodSignature signature, final ClassCodegen classCodegen, final List<Type> argsTypes, final Function2<? super IntrinsicFunction, ? super InstructionAdapter, Unit> invokeInstruction) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(signature, "signature");
            Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
            Intrinsics.checkNotNullParameter(argsTypes, "argsTypes");
            Intrinsics.checkNotNullParameter(invokeInstruction, "invokeInstruction");
            return new IntrinsicFunction(expression, signature, classCodegen, argsTypes) { // from class: org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicFunction$Companion$create$1
                @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicFunction
                public void genInvokeInstruction(InstructionAdapter v) {
                    Intrinsics.checkNotNullParameter(v, "v");
                    invokeInstruction.invoke(this, v);
                }
            };
        }
    }
}
