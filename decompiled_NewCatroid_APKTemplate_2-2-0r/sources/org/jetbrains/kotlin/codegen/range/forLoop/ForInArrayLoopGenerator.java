package org.jetbrains.kotlin.codegen.range.forLoop;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ForInArrayLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\b\u0010\u0010\u001a\u00020\u0011H\u0014J\b\u0010\u0012\u001a\u00020\u0011H\u0016J\u0010\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\u0010\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/ForInArrayLoopGenerator;", "Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "canCacheArrayLength", "", "shouldAlwaysStoreArrayInNewVar", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;ZZ)V", "arrayLengthVar", "", "arrayVar", "indexVar", "loopRangeType", "Lorg/jetbrains/kotlin/types/KotlinType;", "assignToLoopParameter", "", "beforeLoop", "checkEmptyLoop", "loopExit", "Lorg/jetbrains/org/objectweb/asm/Label;", "checkPostConditionAndIncrement", "checkPreCondition", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ForInArrayLoopGenerator extends AbstractForLoopGenerator {
    private int arrayLengthVar;
    private int arrayVar;
    private final boolean canCacheArrayLength;
    private int indexVar;
    private final KotlinType loopRangeType;
    private final boolean shouldAlwaysStoreArrayInNewVar;

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkEmptyLoop(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ForInArrayLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, boolean z, boolean z2) {
        super(codegen, forExpression);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        this.canCacheArrayLength = z;
        this.shouldAlwaysStoreArrayInNewVar = z2;
        BindingContext bindingContext = getBindingContext();
        KtExpression loopRange = forExpression.getLoopRange();
        Intrinsics.checkNotNull(loopRange);
        KotlinType type = bindingContext.getType(loopRange);
        Intrinsics.checkNotNull(type);
        this.loopRangeType = type;
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator, org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void beforeLoop() {
        super.beforeLoop();
        Type INT_TYPE = Type.INT_TYPE;
        Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
        this.indexVar = createLoopTempVariable(INT_TYPE);
        StackValue stackValueGen = getCodegen().gen(getForExpression().getLoopRange());
        Type typeAsmType = getCodegen().asmType(this.loopRangeType);
        Intrinsics.checkNotNullExpressionValue(typeAsmType, "asmType(...)");
        if (!this.shouldAlwaysStoreArrayInNewVar && (stackValueGen instanceof StackValue.Local) && Intrinsics.areEqual(stackValueGen.type, typeAsmType)) {
            this.arrayVar = ((StackValue.Local) stackValueGen).index;
        } else {
            Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
            Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
            this.arrayVar = createLoopTempVariable(OBJECT_TYPE);
            stackValueGen.put(typeAsmType, this.loopRangeType, getV());
            getV().store(this.arrayVar, AsmTypes.OBJECT_TYPE);
        }
        if (this.canCacheArrayLength) {
            Type INT_TYPE2 = Type.INT_TYPE;
            Intrinsics.checkNotNullExpressionValue(INT_TYPE2, "INT_TYPE");
            this.arrayLengthVar = createLoopTempVariable(INT_TYPE2);
            getV().load(this.arrayVar, AsmTypes.OBJECT_TYPE);
            getV().arraylength();
            getV().store(this.arrayLengthVar, Type.INT_TYPE);
        }
        getV().iconst(0);
        getV().store(this.indexVar, Type.INT_TYPE);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkPreCondition(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        getV().load(this.indexVar, Type.INT_TYPE);
        if (this.canCacheArrayLength) {
            getV().load(this.arrayLengthVar, Type.INT_TYPE);
        } else {
            getV().load(this.arrayVar, AsmTypes.OBJECT_TYPE);
            getV().arraylength();
        }
        getV().ificmpge(loopExit);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator
    protected void assignToLoopParameter() {
        Type typeBoxType = KotlinBuiltIns.isArray(this.loopRangeType) ? DescriptorAsmUtil.boxType(getAsmElementType(), getElementType(), getCodegen().getState().getTypeMapper()) : getAsmElementType();
        Intrinsics.checkNotNull(typeBoxType);
        getV().load(this.arrayVar, AsmTypes.OBJECT_TYPE);
        getV().load(this.indexVar, Type.INT_TYPE);
        getV().aload(typeBoxType);
        StackValue.local(getLoopParameterVar(), getLoopParameterType(), getLoopParameterKotlinType()).store(StackValue.onStack(typeBoxType, getElementType()), getV());
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator
    protected void checkPostConditionAndIncrement(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        getV().iinc(this.indexVar, 1);
    }
}
