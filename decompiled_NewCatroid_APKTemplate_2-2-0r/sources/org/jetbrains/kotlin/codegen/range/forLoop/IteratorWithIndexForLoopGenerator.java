package org.jetbrains.kotlin.codegen.range.forLoop;

import com.reandroid.dex.sections.Marker;
import java.util.Iterator;
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
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: IteratorWithIndexForLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u000e\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\b\u0010\u0014\u001a\u00020\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\u0010\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0019H\u0014R\u0016\u0010\u000e\u001a\n \u000f*\u0004\u0018\u00010\f0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n \u000f*\u0004\u0018\u00010\f0\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/IteratorWithIndexForLoopGenerator;", "Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractWithIndexForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "loopParameter", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "rangeCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "iteratorOwnerType", "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;Lorg/jetbrains/org/objectweb/asm/Type;)V", "asmTypeForIterator", JvmProtoBufUtil.PLATFORM_TYPE_ID, "indexType", "indexVar", "", "iteratorVar", "assignLoopParametersNextValues", "", "beforeLoop", "checkPreCondition", "loopExit", "Lorg/jetbrains/org/objectweb/asm/Label;", "incrementAndCheckPostCondition", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IteratorWithIndexForLoopGenerator extends AbstractWithIndexForLoopGenerator {
    private final Type asmTypeForIterator;
    private Type indexType;
    private int indexVar;
    private final Type iteratorOwnerType;
    private int iteratorVar;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IteratorWithIndexForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, KtDestructuringDeclaration loopParameter, ResolvedCall<? extends CallableDescriptor> rangeCall, Type iteratorOwnerType) {
        super(codegen, forExpression, loopParameter, rangeCall);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        Intrinsics.checkNotNullParameter(loopParameter, "loopParameter");
        Intrinsics.checkNotNullParameter(rangeCall, "rangeCall");
        Intrinsics.checkNotNullParameter(iteratorOwnerType, "iteratorOwnerType");
        this.iteratorOwnerType = iteratorOwnerType;
        this.iteratorVar = -1;
        this.indexVar = -1;
        this.indexType = Type.INT_TYPE;
        this.asmTypeForIterator = Type.getType((Class<?>) Iterator.class);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void beforeLoop() {
        int iCreateLoopTempVariable;
        Type parameterType;
        AbstractWithIndexForLoopGenerator.LoopComponent indexLoopComponent = getIndexLoopComponent();
        if (indexLoopComponent != null) {
            iCreateLoopTempVariable = indexLoopComponent.getParameterVar();
        } else {
            Type INT_TYPE = Type.INT_TYPE;
            Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
            iCreateLoopTempVariable = createLoopTempVariable(INT_TYPE);
        }
        this.indexVar = iCreateLoopTempVariable;
        AbstractWithIndexForLoopGenerator.LoopComponent indexLoopComponent2 = getIndexLoopComponent();
        if (indexLoopComponent2 == null || (parameterType = indexLoopComponent2.getParameterType()) == null) {
            parameterType = Type.INT_TYPE;
        }
        this.indexType = parameterType;
        StackValue.local(this.indexVar, parameterType).store(StackValue.constant(0), getV());
        Type asmTypeForIterator = this.asmTypeForIterator;
        Intrinsics.checkNotNullExpressionValue(asmTypeForIterator, "asmTypeForIterator");
        this.iteratorVar = createLoopTempVariable(asmTypeForIterator);
        CodegenUtilKt.generateCallReceiver(getCodegen(), getRangeCall()).put(this.iteratorOwnerType, getV());
        getV().invokeinterface(this.iteratorOwnerType.getInternalName(), "iterator", "()Ljava/util/Iterator;");
        StackValue.local(this.iteratorVar, this.asmTypeForIterator).store(StackValue.onStack(this.asmTypeForIterator), getV());
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkPreCondition(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        getV().load(this.iteratorVar, this.asmTypeForIterator);
        getV().invokeinterface("java/util/Iterator", "hasNext", "()Z");
        getV().ifeq(loopExit);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractWithIndexForLoopGenerator
    protected void assignLoopParametersNextValues() {
        getV().load(this.iteratorVar, this.asmTypeForIterator);
        getV().invokeinterface("java/util/Iterator", ES6Iterator.NEXT_METHOD, "()Ljava/lang/Object;");
        if (getElementLoopComponent() != null) {
            StackValue.local(getElementLoopComponent().getParameterVar(), getElementLoopComponent().getParameterType()).store(StackValue.onStack(AsmTypes.OBJECT_TYPE), getV());
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
