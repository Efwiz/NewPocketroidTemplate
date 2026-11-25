package org.jetbrains.kotlin.codegen.range.forLoop;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.RangeUtilKt;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: AbstractForLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u0010H\u0016J\b\u00103\u001a\u000201H\u0016J\b\u00104\u001a\u000201H$J\b\u00105\u001a\u000201H\u0016J\b\u00106\u001a\u000201H\u0016J\b\u00107\u001a\u000201H\u0016J\u0010\u00108\u001a\u0002012\u0006\u00102\u001a\u00020\u0010H$J\u0010\u00109\u001a\u00020'2\u0006\u0010:\u001a\u00020\bH\u0004J\u0010\u0010;\u001a\u0002012\u0006\u0010<\u001a\u00020=H\u0002J0\u0010>\u001a\u0002012\u0006\u0010?\u001a\u00020\b2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\b2\u0006\u0010C\u001a\u00020\b2\u0006\u0010D\u001a\u00020'H\u0004J\u0010\u0010E\u001a\u0002012\u0006\u0010F\u001a\u00020\u001bH\u0002R\u0014\u0010\u0007\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u0014X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001e\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u00020\u001b0\u001aj\b\u0012\u0004\u0012\u00020\u001b`\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001d\u001a\u00020\u0014X\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0016\"\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\"\u001a\u00020\bX\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\n\"\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020'X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u0014\u0010,\u001a\u00020-X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/¨\u0006G"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractForLoopGenerator;", "Lorg/jetbrains/kotlin/codegen/range/forLoop/ForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;)V", "asmElementType", "Lorg/jetbrains/org/objectweb/asm/Type;", "getAsmElementType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "bodyEnd", "Lorg/jetbrains/org/objectweb/asm/Label;", "getCodegen", "()Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "elementType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getElementType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getForExpression", "()Lorg/jetbrains/kotlin/psi/KtForExpression;", "leaveVariableTasks", "Ljava/util/ArrayList;", "Ljava/lang/Runnable;", "Lkotlin/collections/ArrayList;", "loopParameterKotlinType", "getLoopParameterKotlinType", "setLoopParameterKotlinType", "(Lorg/jetbrains/kotlin/types/KotlinType;)V", "loopParameterStartLabel", "loopParameterType", "getLoopParameterType", "setLoopParameterType", "(Lorg/jetbrains/org/objectweb/asm/Type;)V", "loopParameterVar", "", "getLoopParameterVar", "()I", "setLoopParameterVar", "(I)V", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "getV", "()Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "afterBody", "", "loopExit", "afterLoop", "assignToLoopParameter", "beforeBody", "beforeLoop", "body", "checkPostConditionAndIncrement", "createLoopTempVariable", ModuleXmlParser.TYPE, "generateDestructuringDeclaration", "destructuringDeclaration", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "generateRangeOrProgressionProperty", "loopRangeType", "getterName", "", "getterReturnType", "varType", "varToStore", "scheduleLeaveVariable", "runnable", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractForLoopGenerator implements ForLoopGenerator {
    private final Type asmElementType;
    private final BindingContext bindingContext;
    private final Label bodyEnd;
    private final ExpressionCodegen codegen;
    private final KotlinType elementType;
    private final KtForExpression forExpression;
    private final ArrayList<Runnable> leaveVariableTasks;
    protected KotlinType loopParameterKotlinType;
    private final Label loopParameterStartLabel;
    protected Type loopParameterType;
    private int loopParameterVar;
    private final InstructionAdapter v;

    protected abstract void assignToLoopParameter();

    protected abstract void checkPostConditionAndIncrement(Label loopExit);

    public AbstractForLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        this.codegen = codegen;
        this.forExpression = forExpression;
        BindingContext bindingContext = codegen.getBindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        this.bindingContext = bindingContext;
        InstructionAdapter instructionAdapter = codegen.v;
        Intrinsics.checkNotNull(instructionAdapter);
        this.v = instructionAdapter;
        this.loopParameterStartLabel = new Label();
        this.bodyEnd = new Label();
        this.leaveVariableTasks = new ArrayList<>();
        KotlinType elementType = RangeUtilKt.getElementType(bindingContext, forExpression);
        this.elementType = elementType;
        Type typeAsmType = codegen.asmType(elementType);
        Intrinsics.checkNotNullExpressionValue(typeAsmType, "asmType(...)");
        this.asmElementType = typeAsmType;
        this.loopParameterVar = -1;
    }

    protected final ExpressionCodegen getCodegen() {
        return this.codegen;
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public final KtForExpression getForExpression() {
        return this.forExpression;
    }

    protected final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    protected final InstructionAdapter getV() {
        return this.v;
    }

    protected final KotlinType getElementType() {
        return this.elementType;
    }

    protected final Type getAsmElementType() {
        return this.asmElementType;
    }

    protected final int getLoopParameterVar() {
        return this.loopParameterVar;
    }

    protected final void setLoopParameterVar(int i) {
        this.loopParameterVar = i;
    }

    protected final Type getLoopParameterType() {
        Type type = this.loopParameterType;
        if (type != null) {
            return type;
        }
        Intrinsics.throwUninitializedPropertyAccessException("loopParameterType");
        return null;
    }

    protected final void setLoopParameterType(Type type) {
        Intrinsics.checkNotNullParameter(type, "<set-?>");
        this.loopParameterType = type;
    }

    protected final KotlinType getLoopParameterKotlinType() {
        KotlinType kotlinType = this.loopParameterKotlinType;
        if (kotlinType != null) {
            return kotlinType;
        }
        Intrinsics.throwUninitializedPropertyAccessException("loopParameterKotlinType");
        return null;
    }

    protected final void setLoopParameterKotlinType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<set-?>");
        this.loopParameterKotlinType = kotlinType;
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void beforeLoop() {
        KtParameter loopParameter = this.forExpression.getLoopParameter();
        if (loopParameter == null) {
            return;
        }
        if (loopParameter.getDestructuringDeclaration() != null) {
            setLoopParameterType(this.asmElementType);
            setLoopParameterKotlinType(this.elementType);
            this.loopParameterVar = createLoopTempVariable(this.asmElementType);
            return;
        }
        final VariableDescriptor variableDescriptor = (VariableDescriptor) this.bindingContext.get(BindingContext.VALUE_PARAMETER, loopParameter);
        Intrinsics.checkNotNull(variableDescriptor);
        KotlinType type = variableDescriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        setLoopParameterKotlinType(type);
        Type typeAsmType = this.codegen.asmType(getLoopParameterKotlinType());
        Intrinsics.checkNotNullExpressionValue(typeAsmType, "asmType(...)");
        setLoopParameterType(typeAsmType);
        this.loopParameterVar = this.codegen.myFrameMap.enter(variableDescriptor, getLoopParameterType());
        scheduleLeaveVariable(new Runnable() { // from class: org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AbstractForLoopGenerator.beforeLoop$lambda$0(this.f$0, variableDescriptor);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void beforeLoop$lambda$0(AbstractForLoopGenerator this$0, VariableDescriptor variableDescriptor) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.codegen.myFrameMap.leave(variableDescriptor);
        this$0.v.visitLocalVariable(variableDescriptor.getShortName().asString(), this$0.getLoopParameterType().getDescriptor(), null, this$0.loopParameterStartLabel, this$0.bodyEnd, this$0.loopParameterVar);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void beforeBody() {
        assignToLoopParameter();
        this.v.mark(this.loopParameterStartLabel);
        KtDestructuringDeclaration destructuringDeclaration = this.forExpression.getDestructuringDeclaration();
        if (destructuringDeclaration != null) {
            generateDestructuringDeclaration(destructuringDeclaration);
        }
    }

    private final void generateDestructuringDeclaration(KtDestructuringDeclaration destructuringDeclaration) {
        final Label label = new Label();
        List<KtDestructuringDeclarationEntry> entries = destructuringDeclaration.getEntries();
        Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
        List<KtDestructuringDeclarationEntry> list = entries;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(this.codegen.getVariableDescriptorNotNull((KtDestructuringDeclarationEntry) it.next()));
        }
        for (final VariableDescriptor variableDescriptor : CodegenUtilKt.filterOutDescriptorsWithSpecialNames(arrayList)) {
            ExpressionCodegen expressionCodegen = this.codegen;
            KotlinType returnType = variableDescriptor.getReturnType();
            Intrinsics.checkNotNull(returnType);
            final Type typeAsmType = expressionCodegen.asmType(returnType);
            Intrinsics.checkNotNullExpressionValue(typeAsmType, "asmType(...)");
            final int iEnter = this.codegen.myFrameMap.enter(variableDescriptor, typeAsmType);
            scheduleLeaveVariable(new Runnable() { // from class: org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AbstractForLoopGenerator.generateDestructuringDeclaration$lambda$2(this.f$0, variableDescriptor, typeAsmType, label, iEnter);
                }
            });
        }
        this.codegen.initializeDestructuringDeclarationVariables(destructuringDeclaration, new TransientReceiver(this.elementType), StackValue.local(this.loopParameterVar, this.asmElementType));
        this.v.visitLabel(label);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void generateDestructuringDeclaration$lambda$2(AbstractForLoopGenerator this$0, VariableDescriptor componentDescriptor, Type componentAsmType, Label destructuringStartLabel, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(componentDescriptor, "$componentDescriptor");
        Intrinsics.checkNotNullParameter(componentAsmType, "$componentAsmType");
        Intrinsics.checkNotNullParameter(destructuringStartLabel, "$destructuringStartLabel");
        this$0.codegen.myFrameMap.leave(componentDescriptor);
        this$0.v.visitLocalVariable(componentDescriptor.getShortName().asString(), componentAsmType.getDescriptor(), null, destructuringStartLabel, this$0.bodyEnd, i);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void body() {
        this.codegen.generateLoopBody(this.forExpression.getBody());
    }

    private final void scheduleLeaveVariable(Runnable runnable) {
        this.leaveVariableTasks.add(runnable);
    }

    protected final int createLoopTempVariable(final Type type) {
        Intrinsics.checkNotNullParameter(type, "type");
        int iEnterTemp = this.codegen.myFrameMap.enterTemp(type);
        scheduleLeaveVariable(new Runnable() { // from class: org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AbstractForLoopGenerator.createLoopTempVariable$lambda$3(this.f$0, type);
            }
        });
        return iEnterTemp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createLoopTempVariable$lambda$3(AbstractForLoopGenerator this$0, Type type) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(type, "$type");
        this$0.codegen.myFrameMap.leaveTemp(type);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void afterBody(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        this.codegen.markStartLineNumber(this.forExpression);
        checkPostConditionAndIncrement(loopExit);
        this.v.mark(this.bodyEnd);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void afterLoop() {
        Iterator it = CollectionsKt.asReversedMutable(this.leaveVariableTasks).iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
    }

    protected final void generateRangeOrProgressionProperty(Type loopRangeType, String getterName, Type getterReturnType, Type varType, int varToStore) {
        Intrinsics.checkNotNullParameter(loopRangeType, "loopRangeType");
        Intrinsics.checkNotNullParameter(getterName, "getterName");
        Intrinsics.checkNotNullParameter(getterReturnType, "getterReturnType");
        Intrinsics.checkNotNullParameter(varType, "varType");
        this.v.invokevirtual(loopRangeType.getInternalName(), getterName, "()" + getterReturnType.getDescriptor(), false);
        StackValue.local(varToStore, varType).store(StackValue.onStack(getterReturnType), this.v);
    }
}
