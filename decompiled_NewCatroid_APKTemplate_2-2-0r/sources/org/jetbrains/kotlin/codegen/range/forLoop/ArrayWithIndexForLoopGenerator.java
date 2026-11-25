package org.jetbrains.kotlin.codegen.range.forLoop;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ArrayWithIndexForLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u000e\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\b\u0010\u0017\u001a\u00020\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u0018H\u0016J\u0010\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u001cH\u0014R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0014\u001a\n \u0015*\u0004\u0018\u00010\r0\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/ArrayWithIndexForLoopGenerator;", "Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractWithIndexForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "loopParameter", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "rangeCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)V", "arrayElementType", "Lorg/jetbrains/org/objectweb/asm/Type;", "arrayKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "arrayLengthVar", "", "arrayType", "arrayVar", "indexType", JvmProtoBufUtil.PLATFORM_TYPE_ID, "indexVar", "assignLoopParametersNextValues", "", "beforeLoop", "checkPreCondition", "loopExit", "Lorg/jetbrains/org/objectweb/asm/Label;", "incrementAndCheckPostCondition", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayWithIndexForLoopGenerator extends AbstractWithIndexForLoopGenerator {
    private final Type arrayElementType;
    private final KotlinType arrayKotlinType;
    private int arrayLengthVar;
    private final Type arrayType;
    private int arrayVar;
    private Type indexType;
    private int indexVar;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ArrayWithIndexForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, KtDestructuringDeclaration loopParameter, ResolvedCall<? extends CallableDescriptor> rangeCall) {
        super(codegen, forExpression, loopParameter, rangeCall);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        Intrinsics.checkNotNullParameter(loopParameter, "loopParameter");
        Intrinsics.checkNotNullParameter(rangeCall, "rangeCall");
        KotlinType expectedReceiverType = ExpressionCodegen.getExpectedReceiverType(rangeCall);
        Intrinsics.checkNotNullExpressionValue(expectedReceiverType, "getExpectedReceiverType(...)");
        this.arrayKotlinType = expectedReceiverType;
        Type typeAsmType = codegen.asmType(expectedReceiverType);
        Intrinsics.checkNotNullExpressionValue(typeAsmType, "asmType(...)");
        this.arrayType = typeAsmType;
        Type typeCorrectElementType = AsmUtil.correctElementType(typeAsmType);
        Intrinsics.checkNotNullExpressionValue(typeCorrectElementType, "correctElementType(...)");
        this.arrayElementType = typeCorrectElementType;
        this.arrayVar = -1;
        this.arrayLengthVar = -1;
        this.indexVar = -1;
        this.indexType = Type.INT_TYPE;
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void beforeLoop() {
        int iCreateLoopTempVariable;
        Type parameterType;
        this.arrayVar = createLoopTempVariable(this.arrayType);
        Type INT_TYPE = Type.INT_TYPE;
        Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
        this.arrayLengthVar = createLoopTempVariable(INT_TYPE);
        AbstractWithIndexForLoopGenerator.LoopComponent indexLoopComponent = getIndexLoopComponent();
        if (indexLoopComponent != null) {
            iCreateLoopTempVariable = indexLoopComponent.getParameterVar();
        } else {
            Type INT_TYPE2 = Type.INT_TYPE;
            Intrinsics.checkNotNullExpressionValue(INT_TYPE2, "INT_TYPE");
            iCreateLoopTempVariable = createLoopTempVariable(INT_TYPE2);
        }
        this.indexVar = iCreateLoopTempVariable;
        AbstractWithIndexForLoopGenerator.LoopComponent indexLoopComponent2 = getIndexLoopComponent();
        if (indexLoopComponent2 == null || (parameterType = indexLoopComponent2.getParameterType()) == null) {
            parameterType = Type.INT_TYPE;
        }
        this.indexType = parameterType;
        StackValue.Local local = StackValue.local(this.arrayVar, this.arrayType);
        Intrinsics.checkNotNullExpressionValue(local, "local(...)");
        local.store(CodegenUtilKt.generateCallReceiver(getCodegen(), getRangeCall()), getV());
        local.put(this.arrayType, this.arrayKotlinType, getV());
        getV().arraylength();
        getV().store(this.arrayLengthVar, Type.INT_TYPE);
        StackValue.local(this.indexVar, this.indexType).store(StackValue.constant(0), getV());
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkPreCondition(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        StackValue.local(this.indexVar, this.indexType).put(Type.INT_TYPE, getV());
        getV().load(this.arrayLengthVar, Type.INT_TYPE);
        getV().ificmpge(loopExit);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractWithIndexForLoopGenerator
    protected void assignLoopParametersNextValues() {
        if (getElementLoopComponent() != null) {
            getV().load(this.arrayVar, AsmTypes.OBJECT_TYPE);
            getV().load(this.indexVar, Type.INT_TYPE);
            getV().aload(this.arrayElementType);
            StackValue.local(getElementLoopComponent().getParameterVar(), getElementLoopComponent().getParameterType()).store(StackValue.onStack(this.arrayElementType), getV());
        }
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractWithIndexForLoopGenerator
    protected void incrementAndCheckPostCondition(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        getV().iinc(this.indexVar, 1);
    }
}
