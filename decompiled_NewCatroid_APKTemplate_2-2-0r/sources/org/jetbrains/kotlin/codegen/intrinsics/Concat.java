package org.jetbrains.kotlin.codegen.intrinsics;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.FrameMap;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.StringConcatGenerator;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: Concat.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J>\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u0010J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/Concat;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "()V", "generateImpl", "Lorg/jetbrains/org/objectweb/asm/Type;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "returnType", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "arguments", "", "Lorg/jetbrains/kotlin/psi/KtExpression;", "receiver", "Lorg/jetbrains/kotlin/codegen/StackValue;", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "method", "Lorg/jetbrains/kotlin/codegen/CallableMethod;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Concat extends IntrinsicMethod {
    /* JADX WARN: Code duplicated, block: B:7:0x0053  */
    public final Type generateImpl(ExpressionCodegen codegen, InstructionAdapter v, Type returnType, PsiElement element, List<? extends KtExpression> arguments, StackValue receiver) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        StringConcatGenerator.Companion companion = StringConcatGenerator.INSTANCE;
        GenerationState state = codegen.getState();
        Intrinsics.checkNotNullExpressionValue(state, "getState(...)");
        StringConcatGenerator stringConcatGeneratorCreate = companion.create(state, v);
        if (element instanceof KtBinaryExpression) {
            KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) element;
            if (Intrinsics.areEqual(ktBinaryExpression.getOperationReference().getReferencedNameElementType(), KtTokens.PLUS)) {
                StringConcatGenerator.genStringBuilderConstructorIfNeded$default(stringConcatGeneratorCreate, false, 1, null);
                codegen.invokeAppend(stringConcatGeneratorCreate, ktBinaryExpression.getLeft());
                codegen.invokeAppend(stringConcatGeneratorCreate, ktBinaryExpression.getRight());
            } else {
                receiver.put(AsmTypes.JAVA_STRING_TYPE, v);
                stringConcatGeneratorCreate.genStringBuilderConstructorIfNeded(true);
                DescriptorAsmUtil.genInvokeAppendMethod(stringConcatGeneratorCreate, returnType, null, null, StackValue.onStack(AsmTypes.JAVA_STRING_TYPE));
                codegen.invokeAppend(stringConcatGeneratorCreate, arguments.get(0));
            }
        } else {
            receiver.put(AsmTypes.JAVA_STRING_TYPE, v);
            stringConcatGeneratorCreate.genStringBuilderConstructorIfNeded(true);
            DescriptorAsmUtil.genInvokeAppendMethod(stringConcatGeneratorCreate, returnType, null, null, StackValue.onStack(AsmTypes.JAVA_STRING_TYPE));
            codegen.invokeAppend(stringConcatGeneratorCreate, arguments.get(0));
        }
        stringConcatGeneratorCreate.genToString();
        Type JAVA_STRING_TYPE = AsmTypes.JAVA_STRING_TYPE;
        Intrinsics.checkNotNullExpressionValue(JAVA_STRING_TYPE, "JAVA_STRING_TYPE");
        return JAVA_STRING_TYPE;
    }

    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    protected Callable toCallable(CallableMethod method) {
        Intrinsics.checkNotNullParameter(method, "method");
        return new IntrinsicCallable(method) { // from class: org.jetbrains.kotlin.codegen.intrinsics.Concat.toCallable.1
            public StringConcatGenerator generator;

            public final StringConcatGenerator getGenerator() {
                StringConcatGenerator stringConcatGenerator = this.generator;
                if (stringConcatGenerator != null) {
                    return stringConcatGenerator;
                }
                Intrinsics.throwUninitializedPropertyAccessException("generator");
                return null;
            }

            public final void setGenerator(StringConcatGenerator stringConcatGenerator) {
                Intrinsics.checkNotNullParameter(stringConcatGenerator, "<set-?>");
                this.generator = stringConcatGenerator;
            }

            @Override // org.jetbrains.kotlin.codegen.Callable
            public StackValue invokeMethodWithArguments(final ResolvedCall<?> resolvedCall, final StackValue receiver, final ExpressionCodegen codegen) {
                Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
                Intrinsics.checkNotNullParameter(receiver, "receiver");
                Intrinsics.checkNotNullParameter(codegen, "codegen");
                if (resolvedCall.getCall().getCallElement().getLightParent() instanceof KtCallableReferenceExpression) {
                    return super.invokeMethodWithArguments(resolvedCall, receiver, codegen);
                }
                Type returnType = getReturnType();
                final Concat concat = this;
                StackValue stackValueOperation = StackValue.operation(returnType, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.intrinsics.Concat$toCallable$1$invokeMethodWithArguments$1
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
                        List<? extends ValueArgument> valueArguments = resolvedCall.getCall().getValueArguments();
                        Intrinsics.checkNotNullExpressionValue(valueArguments, "getValueArguments(...)");
                        List<? extends ValueArgument> list = valueArguments;
                        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                        Iterator<T> it = list.iterator();
                        while (it.hasNext()) {
                            KtExpression argumentExpression = ((ValueArgument) it.next()).getArgumentExpression();
                            Intrinsics.checkNotNull(argumentExpression);
                            arrayList.add(argumentExpression);
                        }
                        Concat concat2 = concat;
                        ExpressionCodegen expressionCodegen = codegen;
                        Intrinsics.checkNotNull(instructionAdapter);
                        Type returnType2 = getReturnType();
                        KtElement callElement = resolvedCall.getCall().getCallElement();
                        StackValue stackValueReceiver = StackValue.receiver(resolvedCall, receiver, codegen, this);
                        Intrinsics.checkNotNullExpressionValue(stackValueReceiver, "receiver(...)");
                        StackValue.coerce(concat2.generateImpl(expressionCodegen, instructionAdapter, returnType2, callElement, arrayList, stackValueReceiver), getReturnType(), instructionAdapter);
                    }
                });
                Intrinsics.checkNotNullExpressionValue(stackValueOperation, "operation(...)");
                return stackValueOperation;
            }

            @Override // org.jetbrains.kotlin.codegen.Callable
            public void afterReceiverGeneration(InstructionAdapter v, FrameMap frameMap, GenerationState state) {
                Intrinsics.checkNotNullParameter(v, "v");
                Intrinsics.checkNotNullParameter(frameMap, "frameMap");
                Intrinsics.checkNotNullParameter(state, "state");
                setGenerator(StringConcatGenerator.INSTANCE.create(state, v));
                if (!getGenerator().getMode().isDynamic()) {
                    Type JAVA_STRING_TYPE = AsmTypes.JAVA_STRING_TYPE;
                    Intrinsics.checkNotNullExpressionValue(JAVA_STRING_TYPE, "JAVA_STRING_TYPE");
                    CodegenUtilKt.generateNewInstanceDupAndPlaceBeforeStackTop(v, frameMap, JAVA_STRING_TYPE, "java/lang/StringBuilder");
                    v.invokespecial("java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
                    return;
                }
                StringConcatGenerator generator = getGenerator();
                Type JAVA_STRING_TYPE2 = AsmTypes.JAVA_STRING_TYPE;
                Intrinsics.checkNotNullExpressionValue(JAVA_STRING_TYPE2, "JAVA_STRING_TYPE");
                generator.invokeAppend(JAVA_STRING_TYPE2);
            }

            @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicCallable
            public void invokeIntrinsic(InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(v, "v");
                StringConcatGenerator generator = getGenerator();
                Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
                Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
                generator.invokeAppend(OBJECT_TYPE);
                getGenerator().genToString();
            }
        };
    }
}
