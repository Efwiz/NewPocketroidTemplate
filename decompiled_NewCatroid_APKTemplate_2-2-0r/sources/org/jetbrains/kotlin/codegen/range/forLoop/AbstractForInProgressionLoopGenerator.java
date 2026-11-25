package org.jetbrains.kotlin.codegen.range.forLoop;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.range.comparison.ComparisonGenerator;
import org.jetbrains.kotlin.codegen.range.comparison.ComparisonGeneratorKt;
import org.jetbrains.kotlin.codegen.range.comparison.SignedIntegerComparisonGenerator;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.RangeUtilKt;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: AbstractForInProgressionLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u001a\u001a\u00020\u001bH\u0014J\b\u0010\u001c\u001a\u00020\u001bH\u0016J\u0010\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0014J\b\u0010!\u001a\u00020\u001bH$R\u0014\u0010\u0007\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\nR\u001a\u0010\u0013\u001a\u00020\u0014X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractForInProgressionLoopGenerator;", "Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractForInProgressionOrRangeLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;)V", "asmLoopRangeType", "Lorg/jetbrains/org/objectweb/asm/Type;", "getAsmLoopRangeType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "elementComparisonGenerator", "Lorg/jetbrains/kotlin/codegen/range/comparison/ComparisonGenerator;", "incrementComparisonGenerator", "Lorg/jetbrains/kotlin/codegen/range/comparison/SignedIntegerComparisonGenerator;", "incrementKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "incrementType", "getIncrementType", "incrementVar", "", "getIncrementVar", "()I", "setIncrementVar", "(I)V", "rangeElementKotlinType", "assignToLoopParameter", "", "beforeLoop", "checkEmptyLoop", "loopExit", "Lorg/jetbrains/org/objectweb/asm/Label;", "checkPostConditionAndIncrement", "storeProgressionParametersToLocalVars", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractForInProgressionLoopGenerator extends AbstractForInProgressionOrRangeLoopGenerator {
    private final Type asmLoopRangeType;
    private final ComparisonGenerator elementComparisonGenerator;
    private final SignedIntegerComparisonGenerator incrementComparisonGenerator;
    private final KotlinType incrementKotlinType;
    private final Type incrementType;
    private int incrementVar;
    private final KotlinType rangeElementKotlinType;

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator
    protected void assignToLoopParameter() {
    }

    protected abstract void storeProgressionParametersToLocalVars();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractForInProgressionLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        super(codegen, forExpression);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        this.incrementVar = -1;
        Type typeAsmType = codegen.asmType(getRangeKotlinType());
        Intrinsics.checkNotNullExpressionValue(typeAsmType, "asmType(...)");
        this.asmLoopRangeType = typeAsmType;
        KotlinType rangeOrProgressionElementType$default = RangeUtilKt.getRangeOrProgressionElementType$default(getRangeKotlinType(), null, 2, null);
        if (rangeOrProgressionElementType$default == null) {
            throw new AssertionError("Unexpected loop range type: " + getRangeKotlinType());
        }
        this.rangeElementKotlinType = rangeOrProgressionElementType$default;
        MemberScope memberScope = getRangeKotlinType().getMemberScope();
        Name nameIdentifier = Name.identifier("step");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        Collection<? extends PropertyDescriptor> contributedVariables = memberScope.getContributedVariables(nameIdentifier, NoLookupLocation.FROM_BACKEND);
        contributedVariables.size();
        KotlinType type = ((PropertyDescriptor) CollectionsKt.single(contributedVariables)).getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        this.incrementKotlinType = type;
        Type typeAsmType2 = codegen.asmType(type);
        Intrinsics.checkNotNullExpressionValue(typeAsmType2, "asmType(...)");
        this.incrementType = typeAsmType2;
        ComparisonGenerator comparisonGeneratorForKotlinType = ComparisonGeneratorKt.getComparisonGeneratorForKotlinType(type);
        SignedIntegerComparisonGenerator signedIntegerComparisonGenerator = comparisonGeneratorForKotlinType instanceof SignedIntegerComparisonGenerator ? (SignedIntegerComparisonGenerator) comparisonGeneratorForKotlinType : null;
        if (signedIntegerComparisonGenerator == null) {
            throw new AssertionError("Unexpected increment type: " + type);
        }
        this.incrementComparisonGenerator = signedIntegerComparisonGenerator;
        this.elementComparisonGenerator = ComparisonGeneratorKt.getComparisonGeneratorForKotlinType(rangeOrProgressionElementType$default);
    }

    protected final int getIncrementVar() {
        return this.incrementVar;
    }

    protected final void setIncrementVar(int i) {
        this.incrementVar = i;
    }

    protected final Type getAsmLoopRangeType() {
        return this.asmLoopRangeType;
    }

    protected final Type getIncrementType() {
        return this.incrementType;
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForInProgressionOrRangeLoopGenerator, org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator, org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void beforeLoop() {
        super.beforeLoop();
        this.incrementVar = createLoopTempVariable(getAsmElementType());
        storeProgressionParametersToLocalVars();
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkEmptyLoop(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        loopParameter().put(getAsmElementType(), getElementType(), getV());
        getV().load(getEndVar(), getAsmElementType());
        getV().load(this.incrementVar, this.incrementType);
        Label label = new Label();
        Label label2 = new Label();
        this.incrementComparisonGenerator.jumpIfLessThanZero(getV(), label);
        this.elementComparisonGenerator.jumpIfGreater(getV(), loopExit);
        getV().goTo(label2);
        getV().mark(label);
        this.elementComparisonGenerator.jumpIfLess(getV(), loopExit);
        getV().mark(label2);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator
    protected void checkPostConditionAndIncrement(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        checkPostCondition(loopExit);
        StackValue stackValueLoopParameter = loopParameter();
        stackValueLoopParameter.put(getAsmElementType(), getElementType(), getV());
        getV().load(this.incrementVar, getAsmElementType());
        getV().add(getAsmElementType());
        if (getAsmElementType() == Type.BYTE_TYPE || getAsmElementType() == Type.SHORT_TYPE || getAsmElementType() == Type.CHAR_TYPE) {
            StackValue.coerce(Type.INT_TYPE, getAsmElementType(), getV());
        }
        stackValueLoopParameter.store(StackValue.onStack(getAsmElementType(), getElementType()), getV());
    }
}
