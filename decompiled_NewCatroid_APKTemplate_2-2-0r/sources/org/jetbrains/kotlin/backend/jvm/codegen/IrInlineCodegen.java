package org.jetbrains.kotlin.backend.jvm.codegen;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.MethodDescription;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrCallableMethod;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.JvmKotlinType;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.ValueKind;
import org.jetbrains.kotlin.codegen.inline.CapturedParamDesc;
import org.jetbrains.kotlin.codegen.inline.InlineArgumentsInPlaceKt;
import org.jetbrains.kotlin.codegen.inline.InlineCodegen;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline;
import org.jetbrains.kotlin.codegen.inline.TypeParameterMappings;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: IrInlineCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003BI\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u0011¢\u0006\u0002\u0010\u0012J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0016H\u0016J\b\u0010\u0018\u001a\u00020\u0014H\u0002J\u0018\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0004\u001a\u00020\u0002H\u0016J(\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u0014H\u0016J0\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(2\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010)\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020\u0016H\u0014J\b\u0010,\u001a\u00020\u0014H\u0014R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/IrInlineCodegen;", "Lorg/jetbrains/kotlin/codegen/inline/InlineCodegen;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/IrInlineCallGenerator;", "codegen", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "typeParameterMappings", "Lorg/jetbrains/kotlin/codegen/inline/TypeParameterMappings;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "sourceCompiler", "Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;", "reifiedTypeInliner", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner;", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;Lorg/jetbrains/kotlin/codegen/inline/TypeParameterMappings;Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner;)V", "inlineArgumentsInPlace", "", "afterCallEnd", "", "beforeCallStart", "canInlineArgumentsInPlace", "genCycleStub", "text", "", "genInlineCall", "callableMethod", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrCallableMethod;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "isInsideIfCondition", "genValueAndPut", "irValueParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "argumentExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "parameterType", "Lorg/jetbrains/org/objectweb/asm/Type;", "blockInfo", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "generateAssertField", "isInlinedToInlineFunInKotlinRuntime", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrInlineCodegen extends InlineCodegen<ExpressionCodegen> implements IrInlineCallGenerator {
    private final IrFunction function;
    private final boolean inlineArgumentsInPlace;

    /* JADX INFO: compiled from: IrInlineCodegen.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ValueKind.values().length];
            try {
                iArr[ValueKind.METHOD_HANDLE_IN_DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ValueKind.DEFAULT_MASK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ValueKind.DEFAULT_PARAMETER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ValueKind.DEFAULT_INLINE_PARAMETER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrInlineCodegen(ExpressionCodegen codegen, GenerationState state, IrFunction function, JvmMethodSignature signature, TypeParameterMappings<IrType> typeParameterMappings, SourceCompilerForInline sourceCompiler, ReifiedTypeInliner<IrType> reifiedTypeInliner) {
        super(codegen, state, signature, typeParameterMappings, sourceCompiler, reifiedTypeInliner);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(typeParameterMappings, "typeParameterMappings");
        Intrinsics.checkNotNullParameter(sourceCompiler, "sourceCompiler");
        Intrinsics.checkNotNullParameter(reifiedTypeInliner, "reifiedTypeInliner");
        this.function = function;
        this.inlineArgumentsInPlace = canInlineArgumentsInPlace();
    }

    private final boolean canInlineArgumentsInPlace() {
        boolean z;
        if (!JvmIrInlineUtilsKt.isInlineOnly(this.function)) {
            return false;
        }
        int size = this.function.getValueParameters().size();
        if (this.function.getDispatchReceiverParameter() != null) {
            size++;
        }
        if (this.function.getExtensionReceiverParameter() != null) {
            size++;
        }
        if (size == 0) {
            return false;
        }
        List<IrValueParameter> valueParameters = this.function.getValueParameters();
        if ((valueParameters instanceof Collection) && valueParameters.isEmpty()) {
            z = false;
        } else {
            Iterator<T> it = valueParameters.iterator();
            while (it.hasNext()) {
                if (JvmIrInlineUtilsKt.isInlineParameter((IrValueParameter) it.next())) {
                    z = true;
                }
            }
            z = false;
        }
        if (z) {
            return false;
        }
        return InlineArgumentsInPlaceKt.canInlineArgumentsInPlace(getSourceCompiler().compileInlineFunction(getJvmSignature()).getNode());
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.IrCallGenerator
    public void beforeCallStart() {
        if (this.inlineArgumentsInPlace) {
            InlineArgumentsInPlaceKt.addInplaceCallStartMarker(getCodegen().getVisitor());
        }
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.IrCallGenerator
    public void afterCallEnd() {
        if (this.inlineArgumentsInPlace) {
            InlineArgumentsInPlaceKt.addInplaceCallEndMarker(getCodegen().getVisitor());
        }
    }

    @Override // org.jetbrains.kotlin.codegen.inline.InlineCodegen
    protected void generateAssertField() {
        PromisedValue promisedValue;
        IrExpression irExpressionGenerateAssertFieldIfNeeded = getCodegen().getClassCodegen().generateAssertFieldIfNeeded(Intrinsics.areEqual(getSourceCompiler().getInlineCallSiteInfo().getMethod().getName(), MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME));
        if (irExpressionGenerateAssertFieldIfNeeded == null || (promisedValue = (PromisedValue) irExpressionGenerateAssertFieldIfNeeded.accept(getCodegen(), new BlockInfo(null, 1, null))) == null) {
            return;
        }
        promisedValue.discard();
    }

    @Override // org.jetbrains.kotlin.codegen.inline.InlineCodegen
    protected boolean isInlinedToInlineFunInKotlinRuntime() {
        IrFunction irFunction = getCodegen().getIrFunction();
        return irFunction.getIsInline() && IrUtilsKt.getPackageFragment((IrDeclaration) irFunction).getPackageFqName().startsWith(StandardNames.BUILT_INS_PACKAGE_NAME);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.IrCallGenerator
    public void genValueAndPut(IrValueParameter irValueParameter, IrExpression argumentExpression, Type parameterType, ExpressionCodegen codegen, BlockInfo blockInfo) {
        ValueKind valueKind;
        StackValue stackValueConstant;
        Intrinsics.checkNotNullParameter(irValueParameter, "irValueParameter");
        Intrinsics.checkNotNullParameter(argumentExpression, "argumentExpression");
        Intrinsics.checkNotNullParameter(parameterType, "parameterType");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(blockInfo, "blockInfo");
        IrFunctionReference irFunctionReferenceUnwrapInlineLambda = JvmIrInlineUtilsKt.unwrapInlineLambda(argumentExpression);
        if (irFunctionReferenceUnwrapInlineLambda != null) {
            IrExpressionLambdaImpl irExpressionLambdaImpl = new IrExpressionLambdaImpl(codegen, irFunctionReferenceUnwrapInlineLambda);
            rememberClosure(parameterType, irValueParameter.getIndex(), irExpressionLambdaImpl);
            irExpressionLambdaImpl.generateLambdaBody(getSourceCompiler());
            int i = 0;
            for (Object obj : IrUtilsKt.getArgumentsWithIr(irExpressionLambdaImpl.getReference())) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                IrExpression irExpression = (IrExpression) ((Pair) obj).component2();
                CapturedParamDesc capturedParamDesc = irExpressionLambdaImpl.getCapturedVars().get(i);
                putCapturedToLocalVal(codegen.genOrGetLocal$backend_jvm_codegen(irExpression, capturedParamDesc.getType(), irExpression.getType(), new BlockInfo(null, 1, null)), capturedParamDesc, IrBasedDescriptorsKt.toIrBasedKotlinType$default(irExpression.getType(), null, 1, null));
                i = i2;
            }
            return;
        }
        boolean zIsInlineParameter = JvmIrInlineUtilsKt.isInlineParameter(irValueParameter);
        if (Intrinsics.areEqual(irValueParameter.getOrigin(), IrDeclarationOrigin.MASK_FOR_DEFAULT_FUNCTION.INSTANCE)) {
            valueKind = ValueKind.DEFAULT_MASK;
        } else if (Intrinsics.areEqual(irValueParameter.getOrigin(), IrDeclarationOrigin.METHOD_HANDLER_IN_DEFAULT_FUNCTION.INSTANCE)) {
            valueKind = ValueKind.METHOD_HANDLE_IN_DEFAULT;
        } else if ((argumentExpression instanceof IrContainerExpression) && Intrinsics.areEqual(((IrContainerExpression) argumentExpression).getOrigin(), IrStatementOrigin.DEFAULT_VALUE.INSTANCE)) {
            if (zIsInlineParameter) {
                valueKind = ValueKind.DEFAULT_INLINE_PARAMETER;
            } else {
                valueKind = ValueKind.DEFAULT_PARAMETER;
            }
        } else if (zIsInlineParameter && IrTypeUtilsKt.isSuspendFunction(irValueParameter.getType())) {
            if (CoroutineCodegenKt.isReadOfInlineLambda(argumentExpression)) {
                valueKind = ValueKind.READ_OF_INLINE_LAMBDA_FOR_INLINE_SUSPEND_PARAMETER;
            } else {
                valueKind = ValueKind.READ_OF_OBJECT_FOR_INLINE_SUSPEND_PARAMETER;
            }
        } else {
            valueKind = ValueKind.GENERAL;
        }
        int i3 = WhenMappings.$EnumSwitchMapping$0[valueKind.ordinal()];
        if (i3 == 1) {
            stackValueConstant = StackValue.constant(null, AsmTypes.OBJECT_TYPE);
        } else if (i3 == 2) {
            stackValueConstant = StackValue.constant(((IrConst) argumentExpression).getValue(), Type.INT_TYPE);
        } else if (i3 == 3 || i3 == 4) {
            stackValueConstant = StackValue.createDefaultValue(parameterType);
        } else {
            if (this.inlineArgumentsInPlace) {
                InlineArgumentsInPlaceKt.addInplaceArgumentStartMarker(codegen.getVisitor());
            }
            if (irValueParameter.getIndex() >= 0) {
                stackValueConstant = codegen.genOrGetLocal$backend_jvm_codegen(argumentExpression, parameterType, irValueParameter.getType(), blockInfo);
            } else {
                stackValueConstant = codegen.genToStackValue(argumentExpression, parameterType, irValueParameter.getType(), blockInfo);
            }
            if (this.inlineArgumentsInPlace) {
                InlineArgumentsInPlaceKt.addInplaceArgumentEndMarker(codegen.getVisitor());
            }
        }
        JvmKotlinType jvmKotlinType = new JvmKotlinType(parameterType, IrBasedDescriptorsKt.toIrBasedKotlinType$default(irValueParameter.getType(), null, 1, null));
        Intrinsics.checkNotNull(stackValueConstant);
        putArgumentToLocalVal(jvmKotlinType, stackValueConstant, irValueParameter.getIndex(), valueKind);
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.IrInlineCallGenerator
    public void genInlineCall(IrCallableMethod callableMethod, ExpressionCodegen codegen, IrFunctionAccessExpression expression, boolean isInsideIfCondition) {
        Intrinsics.checkNotNullParameter(callableMethod, "callableMethod");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(expression, "expression");
        performInline(isInsideIfCondition, JvmIrInlineUtilsKt.isInlineOnly(this.function));
    }

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.IrInlineCallGenerator
    public void genCycleStub(String text, ExpressionCodegen codegen) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        generateStub(text, codegen);
    }
}
