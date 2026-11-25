package org.jetbrains.kotlin.codegen.range.forLoop;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: ForInCharSequenceLoopGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\b\u0010\u0010\u001a\u00020\u0011H\u0014J\b\u0010\u0012\u001a\u00020\u0011H\u0016J\u0010\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\u0010\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u001c\u0010\u0018\u001a\u00020\u0011*\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001bH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/ForInCharSequenceLoopGenerator;", "Lorg/jetbrains/kotlin/codegen/range/forLoop/AbstractForLoopGenerator;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "forExpression", "Lorg/jetbrains/kotlin/psi/KtForExpression;", "canCacheLength", "", "charSequenceClassType", "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtForExpression;ZLorg/jetbrains/org/objectweb/asm/Type;)V", "charSequenceLengthVar", "", "charSequenceType", "charSequenceVar", "indexVar", "assignToLoopParameter", "", "beforeLoop", "checkEmptyLoop", "loopExit", "Lorg/jetbrains/org/objectweb/asm/Label;", "checkPostConditionAndIncrement", "checkPreCondition", "invokeCharSequenceMethod", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "name", "", "desc", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ForInCharSequenceLoopGenerator extends AbstractForLoopGenerator {
    private static final Type CHAR_SEQUENCE_TYPE;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean canCacheLength;
    private final Type charSequenceClassType;
    private int charSequenceLengthVar;
    private final Type charSequenceType;
    private int charSequenceVar;
    private int indexVar;

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkEmptyLoop(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ForInCharSequenceLoopGenerator(ExpressionCodegen codegen, KtForExpression forExpression, boolean z, Type type) {
        super(codegen, forExpression);
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(forExpression, "forExpression");
        this.canCacheLength = z;
        this.charSequenceClassType = type;
        this.charSequenceType = type == null ? CHAR_SEQUENCE_TYPE : type;
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator, org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void beforeLoop() {
        super.beforeLoop();
        Type INT_TYPE = Type.INT_TYPE;
        Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
        this.indexVar = createLoopTempVariable(INT_TYPE);
        StackValue stackValueGen = getCodegen().gen(getForExpression().getLoopRange());
        BindingContext bindingContext = getBindingContext();
        KtExpression loopRange = getForExpression().getLoopRange();
        Intrinsics.checkNotNull(loopRange);
        KotlinType type = bindingContext.getType(loopRange);
        Intrinsics.checkNotNull(type);
        Type typeAsmType = getCodegen().asmType(type);
        Intrinsics.checkNotNullExpressionValue(typeAsmType, "asmType(...)");
        this.charSequenceVar = createLoopTempVariable(this.charSequenceType);
        stackValueGen.put(typeAsmType, type, getV());
        getV().store(this.charSequenceVar, this.charSequenceType);
        if (this.canCacheLength) {
            Type INT_TYPE2 = Type.INT_TYPE;
            Intrinsics.checkNotNullExpressionValue(INT_TYPE2, "INT_TYPE");
            this.charSequenceLengthVar = createLoopTempVariable(INT_TYPE2);
            getV().load(this.charSequenceVar, this.charSequenceType);
            invokeCharSequenceMethod(getV(), "length", "()I");
            getV().store(this.charSequenceLengthVar, Type.INT_TYPE);
        }
        getV().iconst(0);
        getV().store(this.indexVar, Type.INT_TYPE);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.ForLoopGenerator
    public void checkPreCondition(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        getV().load(this.indexVar, Type.INT_TYPE);
        if (this.canCacheLength) {
            getV().load(this.charSequenceLengthVar, Type.INT_TYPE);
        } else {
            getV().load(this.charSequenceVar, this.charSequenceType);
            invokeCharSequenceMethod(getV(), "length", "()I");
        }
        getV().ificmpge(loopExit);
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator
    protected void assignToLoopParameter() {
        getV().load(this.charSequenceVar, this.charSequenceType);
        getV().load(this.indexVar, Type.INT_TYPE);
        invokeCharSequenceMethod(getV(), "charAt", "(I)C");
        StackValue.onStack(Type.CHAR_TYPE).put(getLoopParameterType(), getLoopParameterKotlinType(), getCodegen().v);
        getV().store(getLoopParameterVar(), getLoopParameterType());
    }

    @Override // org.jetbrains.kotlin.codegen.range.forLoop.AbstractForLoopGenerator
    protected void checkPostConditionAndIncrement(Label loopExit) {
        Intrinsics.checkNotNullParameter(loopExit, "loopExit");
        getV().iinc(this.indexVar, 1);
    }

    private final void invokeCharSequenceMethod(InstructionAdapter instructionAdapter, String str, String str2) {
        Type type = this.charSequenceClassType;
        if (type != null) {
            instructionAdapter.invokevirtual(type.getInternalName(), str, str2, false);
        } else {
            instructionAdapter.invokeinterface(CHAR_SEQUENCE_TYPE.getInternalName(), str, str2);
        }
    }

    /* JADX INFO: compiled from: ForInCharSequenceLoopGenerator.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/range/forLoop/ForInCharSequenceLoopGenerator$Companion;", "", "()V", "CHAR_SEQUENCE_TYPE", "Lorg/jetbrains/org/objectweb/asm/Type;", "getCHAR_SEQUENCE_TYPE", "()Lorg/jetbrains/org/objectweb/asm/Type;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Type getCHAR_SEQUENCE_TYPE() {
            return ForInCharSequenceLoopGenerator.CHAR_SEQUENCE_TYPE;
        }
    }

    static {
        Type objectType = Type.getObjectType("java/lang/CharSequence");
        Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(...)");
        CHAR_SEQUENCE_TYPE = objectType;
    }
}
