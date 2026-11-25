package org.jetbrains.kotlin.codegen.range.forLoop;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Label;

/* JADX INFO: compiled from: AbstractForInProgressionOrRangeLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0004J\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\r\u001a\u00020\u000eH\u0004J\u0014\u0010\u0019\u001a\u00020\u001a*\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001aH\u0004R\u001a\u0010\u0007\u001a\u00020\bX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u0010X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractForInProgressionOrRangeLoopGenerator;", "Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;)V", "endVar", "", "getEndVar", "()I", "setEndVar", "(I)V", "loopParameter", "Lorg/jetbrains/kotlin/codegen/StackValue;", "rangeKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getRangeKotlinType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "beforeLoop", "", "checkPostCondition", "loopExit", "Lorg/jetbrains/org/objectweb/asm/Label;", "checkPreCondition", "getPropertyGetterName", "", "propertyName", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractForInProgressionOrRangeLoopGenerator extends AbstractForLoopGenerator {
    private int endVar;
    private StackValue loopParameter;
    private final KotlinType rangeKotlinType;

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkPreCondition(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractForInProgressionOrRangeLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        super(codegen, forExpression);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        this.endVar = -1;
        BindingContext bindingContext = getBindingContext();
        KtExpression loopRange = forExpression.getLoopRange();
        Intrinsics.checkNotNull(loopRange);
        KotlinType type = bindingContext.getType(loopRange);
        Intrinsics.checkNotNull(type);
        this.rangeKotlinType = type;
        if (getAsmElementType().getSort() == 5 || getAsmElementType().getSort() == 3 || getAsmElementType().getSort() == 4 || getAsmElementType().getSort() == 2) {
            return;
        }
        getAsmElementType().getSort();
    }

    protected final int getEndVar() {
        return this.endVar;
    }

    protected final void setEndVar(int i) {
        this.endVar = i;
    }

    protected final KotlinType getRangeKotlinType() {
        return this.rangeKotlinType;
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator, org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void beforeLoop() {
        super.beforeLoop();
        this.endVar = createLoopTempVariable(getAsmElementType());
    }

    protected final void checkPostCondition(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        loopParameter().put(getAsmElementType(), getElementType(), getV());
        getV().load(this.endVar, getAsmElementType());
        if (getAsmElementType().getSort() == 7) {
            getV().lcmp();
            getV().ifeq(loopExit);
        } else {
            getV().ificmpeq(loopExit);
        }
    }

    protected final StackValue loopParameter() {
        StackValue stackValue = this.loopParameter;
        if (stackValue != null) {
            return stackValue;
        }
        StackValue.Local local = StackValue.local(getLoopParameterVar(), getLoopParameterType());
        StackValue.Local local2 = local;
        this.loopParameter = local2;
        Intrinsics.checkNotNullExpressionValue(local, "also(...)");
        return local2;
    }

    protected final String getPropertyGetterName(KotlinType kotlinType, String propertyName) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Intrinsics.checkNotNullParameter(propertyName, "propertyName");
        MemberScope memberScope = kotlinType.getMemberScope();
        Name nameIdentifier = Name.identifier(propertyName);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) CollectionsKt.singleOrNull(memberScope.getContributedVariables(nameIdentifier, NoLookupLocation.FROM_BACKEND));
        if (propertyDescriptor == null) {
            throw new AssertionError("No '" + propertyName + "' in member scope of type " + kotlinType);
        }
        PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
        if (getter == null) {
            throw new AssertionError("Property has no getter: " + propertyDescriptor);
        }
        return getCodegen().getTypeMapper().mapFunctionName(getter, OwnerKind.IMPLEMENTATION);
    }
}
