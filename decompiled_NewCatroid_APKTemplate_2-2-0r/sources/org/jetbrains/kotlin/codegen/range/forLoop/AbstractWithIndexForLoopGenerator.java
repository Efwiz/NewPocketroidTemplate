package org.jetbrains.kotlin.codegen.range.forLoop;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: AbstractWithIndexForLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001:B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u000e\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\u0010\u0010*\u001a\u00020\u001f2\u0006\u0010+\u001a\u00020\u0011H\u0016J\b\u0010,\u001a\u00020\u001fH\u0016J\b\u0010-\u001a\u00020\u001fH$J\b\u0010.\u001a\u00020\u001fH\u0016J\b\u0010/\u001a\u00020\u001fH\u0016J\u0010\u00100\u001a\u00020\u001f2\u0006\u0010+\u001a\u00020\u0011H\u0016J\u0010\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\u0004J\u0010\u00105\u001a\u00020\u001f2\u0006\u0010+\u001a\u00020\u0011H$J\u0016\u00106\u001a\u00020\u001f2\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0004J\u000e\u00108\u001a\u0004\u0018\u00010\u0015*\u000209H\u0002R\u0014\u0010\f\u001a\u00020\rX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\u001a\u001a\u0004\u0018\u00010\u0015X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R*\u0010\u001c\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u001dj\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e` X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u000e\u0010#\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0014\u0010&\u001a\u00020'X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractWithIndexForLoopGenerator;", "Lorg/jetbrains/kotlin/codegen/range/forLoop/ForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "loopParameter", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "rangeCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;)V", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "bodyEnd", "Lorg/jetbrains/org/objectweb/asm/Label;", "getCodegen", "()Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "elementLoopComponent", "Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractWithIndexForLoopGenerator$LoopComponent;", "getElementLoopComponent", "()Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractWithIndexForLoopGenerator$LoopComponent;", "getForExpression", "()Lorg/jetbrains/kotlin/psi/KtForExpression;", "indexLoopComponent", "getIndexLoopComponent", "leaveTasks", "Ljava/util/ArrayList;", "Lkotlin/Function0;", "", "Lkotlin/collections/ArrayList;", "getLoopParameter", "()Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "loopParameterStartLabel", "getRangeCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "getV", "()Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "afterBody", "loopExit", "afterLoop", "assignLoopParametersNextValues", "beforeBody", "body", "checkEmptyLoop", "createLoopTempVariable", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "incrementAndCheckPostCondition", "scheduleLeaveTask", "task", "resolveLoopComponent", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclarationEntry;", "LoopComponent", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractWithIndexForLoopGenerator implements ForLoopGenerator {
    private final BindingContext bindingContext;
    private final Label bodyEnd;
    private final ExpressionCodegen codegen;
    private final LoopComponent elementLoopComponent;
    private final KtForExpression forExpression;
    private final LoopComponent indexLoopComponent;
    private final ArrayList<Function0<Unit>> leaveTasks;
    private final KtDestructuringDeclaration loopParameter;
    private final Label loopParameterStartLabel;
    private final ResolvedCall<? extends CallableDescriptor> rangeCall;
    private final InstructionAdapter v;

    protected abstract void assignLoopParametersNextValues();

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkEmptyLoop(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
    }

    protected abstract void incrementAndCheckPostCondition(Label loopExit);

    public AbstractWithIndexForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, KtDestructuringDeclaration loopParameter, ResolvedCall<? extends CallableDescriptor> rangeCall) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        Intrinsics.checkNotNullParameter(loopParameter, "loopParameter");
        Intrinsics.checkNotNullParameter(rangeCall, "rangeCall");
        this.codegen = codegen;
        this.forExpression = forExpression;
        this.loopParameter = loopParameter;
        this.rangeCall = rangeCall;
        BindingContext bindingContext = codegen.getBindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        this.bindingContext = bindingContext;
        InstructionAdapter instructionAdapter = codegen.v;
        Intrinsics.checkNotNull(instructionAdapter);
        this.v = instructionAdapter;
        this.loopParameterStartLabel = new Label();
        this.bodyEnd = new Label();
        this.leaveTasks = new ArrayList<>();
        List<KtDestructuringDeclarationEntry> entries = loopParameter.getEntries();
        Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
        KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry = (KtDestructuringDeclarationEntry) CollectionsKt.getOrNull(entries, 0);
        this.indexLoopComponent = ktDestructuringDeclarationEntry != null ? resolveLoopComponent(ktDestructuringDeclarationEntry) : null;
        List<KtDestructuringDeclarationEntry> entries2 = loopParameter.getEntries();
        Intrinsics.checkNotNullExpressionValue(entries2, "getEntries(...)");
        KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry2 = (KtDestructuringDeclarationEntry) CollectionsKt.getOrNull(entries2, 1);
        this.elementLoopComponent = ktDestructuringDeclarationEntry2 != null ? resolveLoopComponent(ktDestructuringDeclarationEntry2) : null;
    }

    protected final ExpressionCodegen getCodegen() {
        return this.codegen;
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public final KtForExpression getForExpression() {
        return this.forExpression;
    }

    protected final KtDestructuringDeclaration getLoopParameter() {
        return this.loopParameter;
    }

    protected final ResolvedCall<? extends CallableDescriptor> getRangeCall() {
        return this.rangeCall;
    }

    protected final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    protected final InstructionAdapter getV() {
        return this.v;
    }

    /* JADX INFO: compiled from: AbstractWithIndexForLoopGenerator.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractWithIndexForLoopGenerator$LoopComponent;", "", "parameterVar", "", "parameterType", "Lorg/jetbrains/org/objectweb/asm/Type;", "componentType", "(ILorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/org/objectweb/asm/Type;)V", "getComponentType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "getParameterType", "getParameterVar", "()I", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final class LoopComponent {
        private final Type componentType;
        private final Type parameterType;
        private final int parameterVar;

        public LoopComponent(int i, Type parameterType, Type componentType) {
            Intrinsics.checkNotNullParameter(parameterType, "parameterType");
            Intrinsics.checkNotNullParameter(componentType, "componentType");
            this.parameterVar = i;
            this.parameterType = parameterType;
            this.componentType = componentType;
        }

        public final Type getComponentType() {
            return this.componentType;
        }

        public final Type getParameterType() {
            return this.parameterType;
        }

        public final int getParameterVar() {
            return this.parameterVar;
        }
    }

    protected final LoopComponent getIndexLoopComponent() {
        return this.indexLoopComponent;
    }

    protected final LoopComponent getElementLoopComponent() {
        return this.elementLoopComponent;
    }

    private final LoopComponent resolveLoopComponent(KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry) {
        ResolvedCall resolvedCall;
        final VariableDescriptor variableDescriptor = (VariableDescriptor) this.bindingContext.get(BindingContext.VARIABLE, ktDestructuringDeclarationEntry);
        if (variableDescriptor == null || variableDescriptor.getShortName().isSpecial() || (resolvedCall = (ResolvedCall) this.bindingContext.get(BindingContext.COMPONENT_RESOLVED_CALL, ktDestructuringDeclarationEntry)) == null) {
            return null;
        }
        ExpressionCodegen expressionCodegen = this.codegen;
        KotlinType returnType = ((FunctionDescriptor) resolvedCall.getResultingDescriptor()).getReturnType();
        if (returnType == null) {
            return null;
        }
        Type typeAsmType = expressionCodegen.asmType(returnType);
        Intrinsics.checkNotNullExpressionValue(typeAsmType, "asmType(...)");
        final Type typeAsmType2 = this.codegen.asmType(variableDescriptor.getType());
        Intrinsics.checkNotNullExpressionValue(typeAsmType2, "asmType(...)");
        final int iEnter = this.codegen.myFrameMap.enter(variableDescriptor, typeAsmType2);
        scheduleLeaveTask(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.range.forLoop.AbstractWithIndexForLoopGenerator.resolveLoopComponent.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                AbstractWithIndexForLoopGenerator.this.getCodegen().myFrameMap.leaveTemp(typeAsmType2);
                AbstractWithIndexForLoopGenerator.this.getV().visitLocalVariable(variableDescriptor.getShortName().asString(), typeAsmType2.getDescriptor(), null, AbstractWithIndexForLoopGenerator.this.loopParameterStartLabel, AbstractWithIndexForLoopGenerator.this.bodyEnd, iEnter);
            }
        });
        return new LoopComponent(iEnter, typeAsmType2, typeAsmType);
    }

    protected final void scheduleLeaveTask(Function0<Unit> task) {
        Intrinsics.checkNotNullParameter(task, "task");
        this.leaveTasks.add(task);
    }

    protected final int createLoopTempVariable(final Type type) {
        Intrinsics.checkNotNullParameter(type, "type");
        int iEnterTemp = this.codegen.myFrameMap.enterTemp(type);
        scheduleLeaveTask(new Function0<Unit>() { // from class: org.jetbrains.kotlin.codegen.range.forLoop.AbstractWithIndexForLoopGenerator.createLoopTempVariable.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                AbstractWithIndexForLoopGenerator.this.getCodegen().myFrameMap.leaveTemp(type);
            }
        });
        return iEnterTemp;
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void beforeBody() {
        assignLoopParametersNextValues();
        this.v.mark(this.loopParameterStartLabel);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void body() {
        this.codegen.generateLoopBody(this.forExpression.getBody());
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void afterBody(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        this.codegen.markStartLineNumber(this.forExpression);
        incrementAndCheckPostCondition(loopExit);
        this.v.mark(this.bodyEnd);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void afterLoop() {
        Iterator it = CollectionsKt.asReversedMutable(this.leaveTasks).iterator();
        while (it.hasNext()) {
            ((Function0) it.next()).invoke();
        }
    }
}
