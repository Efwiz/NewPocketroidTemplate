package org.jetbrains.kotlin.codegen.range.forLoop;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: CharSequenceWithIndexForLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u000e\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\b\u0010\u0016\u001a\u00020\u0017H\u0014J\b\u0010\u0018\u001a\u00020\u0017H\u0016J\u0010\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0017H\u0002J\u0010\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0012\u001a\n \u0013*\u0004\u0018\u00010\u000f0\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/CharSequenceWithIndexForLoopGenerator;", "Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractWithIndexForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "loopParameter", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "rangeCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "canCacheLength", "", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;Z)V", "charSeqType", "Lorg/jetbrains/org/objectweb/asm/Type;", "charSeqVar", "", "indexType", JvmProtoBufUtil.PLATFORM_TYPE_ID, "indexVar", "lengthVar", "assignLoopParametersNextValues", "", "beforeLoop", "checkPreCondition", "loopExit", "Lorg/jetbrains/org/objectweb/asm/Label;", "evalCharSeqLengthOnStack", "incrementAndCheckPostCondition", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CharSequenceWithIndexForLoopGenerator extends AbstractWithIndexForLoopGenerator {
    private final boolean canCacheLength;
    private final Type charSeqType;
    private int charSeqVar;
    private Type indexType;
    private int indexVar;
    private int lengthVar;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CharSequenceWithIndexForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, KtDestructuringDeclaration loopParameter, ResolvedCall<? extends CallableDescriptor> rangeCall, boolean z) {
        super(codegen, forExpression, loopParameter, rangeCall);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        Intrinsics.checkNotNullParameter(loopParameter, "loopParameter");
        Intrinsics.checkNotNullParameter(rangeCall, "rangeCall");
        this.canCacheLength = z;
        Type typeAsmType = codegen.asmType(ExpressionCodegen.getExpectedReceiverType(rangeCall));
        Intrinsics.checkNotNullExpressionValue(typeAsmType, "asmType(...)");
        this.charSeqType = typeAsmType;
        this.charSeqVar = -1;
        this.lengthVar = -1;
        this.indexVar = -1;
        this.indexType = Type.INT_TYPE;
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void beforeLoop() {
        int iCreateLoopTempVariable;
        Type parameterType;
        int iCreateLoopTempVariable2 = createLoopTempVariable(this.charSeqType);
        this.charSeqVar = iCreateLoopTempVariable2;
        StackValue.Local local = StackValue.local(iCreateLoopTempVariable2, this.charSeqType);
        Intrinsics.checkNotNullExpressionValue(local, "local(...)");
        local.store(CodegenUtilKt.generateCallReceiver(getCodegen(), getRangeCall()), getV());
        if (this.canCacheLength) {
            Type INT_TYPE = Type.INT_TYPE;
            Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
            this.lengthVar = createLoopTempVariable(INT_TYPE);
            evalCharSeqLengthOnStack();
            getV().store(this.lengthVar, Type.INT_TYPE);
        }
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
        StackValue.local(this.indexVar, parameterType).store(StackValue.constant(0), getV());
    }

    private final void evalCharSeqLengthOnStack() {
        getV().load(this.charSeqVar, this.charSeqType);
        getV().invokeinterface("java/lang/CharSequence", "length", "()I");
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkPreCondition(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        getV().load(this.indexVar, Type.INT_TYPE);
        if (this.canCacheLength) {
            getV().load(this.lengthVar, Type.INT_TYPE);
        } else {
            evalCharSeqLengthOnStack();
        }
        getV().ificmpge(loopExit);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractWithIndexForLoopGenerator
    protected void assignLoopParametersNextValues() {
        getV().load(this.charSeqVar, this.charSeqType);
        getV().load(this.indexVar, Type.INT_TYPE);
        getV().invokeinterface("java/lang/CharSequence", "charAt", "(I)C");
        if (getElementLoopComponent() != null) {
            StackValue.local(getElementLoopComponent().getParameterVar(), getElementLoopComponent().getParameterType()).store(StackValue.onStack(Type.CHAR_TYPE), getV());
        } else {
            getV().pop();
        }
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractWithIndexForLoopGenerator
    protected void incrementAndCheckPostCondition(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        getV().iinc(this.indexVar, 1);
    }
}
