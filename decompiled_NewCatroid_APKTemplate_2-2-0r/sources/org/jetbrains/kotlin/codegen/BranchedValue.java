package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsnsKt;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: BranchedValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB)\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ \u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J \u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\"\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u00052\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/BranchedValue;", "Lorg/jetbrains/kotlin/codegen/StackValue;", "arg1", "arg2", "operandType", "Lorg/jetbrains/org/objectweb/asm/Type;", "opcode", "", "(Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/org/objectweb/asm/Type;I)V", "getArg1", "()Lorg/jetbrains/kotlin/codegen/StackValue;", "getArg2", "getOpcode", "()I", "getOperandType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "condJump", "", "jumpLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "jumpIfFalse", "", "loopJump", "patchOpcode", "putSelector", ModuleXmlParser.TYPE, "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class BranchedValue extends StackValue {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    private static final BranchedValue FALSE;
    private static final BranchedValue TRUE;
    private static final HashMap<Integer, Integer> negatedOperations;
    private final StackValue arg1;
    private final StackValue arg2;
    private final int opcode;
    private final Type operandType;

    protected int patchOpcode(int opcode, InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(v, "v");
        return opcode;
    }

    public /* synthetic */ BranchedValue(StackValue stackValue, StackValue stackValue2, Type type, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(stackValue, (i2 & 2) != 0 ? null : stackValue2, type, i);
    }

    public final StackValue getArg1() {
        return this.arg1;
    }

    public final StackValue getArg2() {
        return this.arg2;
    }

    public final Type getOperandType() {
        return this.operandType;
    }

    public final int getOpcode() {
        return this.opcode;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BranchedValue(StackValue arg1, StackValue stackValue, Type operandType, int i) {
        super(Type.BOOLEAN_TYPE);
        Intrinsics.checkNotNullParameter(arg1, "arg1");
        Intrinsics.checkNotNullParameter(operandType, "operandType");
        this.arg1 = arg1;
        this.arg2 = stackValue;
        this.operandType = operandType;
        this.opcode = i;
    }

    @Override // org.jetbrains.kotlin.codegen.StackValue
    public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(v, "v");
        Label label = new Label();
        condJump(label, v, true);
        Label label2 = new Label();
        v.iconst(1);
        v.visitJumpInsn(167, label2);
        v.visitLabel(label);
        v.iconst(0);
        v.visitLabel(label2);
        coerceTo(type, kotlinType, v);
    }

    public void condJump(Label jumpLabel, InstructionAdapter v, boolean jumpIfFalse) {
        int iIntValue;
        Intrinsics.checkNotNullParameter(jumpLabel, "jumpLabel");
        Intrinsics.checkNotNullParameter(v, "v");
        StackValue stackValue = this.arg1;
        if (stackValue instanceof CondJump) {
            ((CondJump) stackValue).condJump(jumpLabel, v, jumpIfFalse);
        } else {
            stackValue.put(this.operandType, v);
        }
        StackValue stackValue2 = this.arg2;
        if (stackValue2 != null) {
            stackValue2.put(this.operandType, v);
        }
        if (jumpIfFalse) {
            iIntValue = this.opcode;
        } else {
            Integer num = negatedOperations.get(Integer.valueOf(this.opcode));
            Intrinsics.checkNotNull(num);
            iIntValue = num.intValue();
        }
        v.visitJumpInsn(patchOpcode(iIntValue, v), jumpLabel);
    }

    public void loopJump(Label jumpLabel, InstructionAdapter v, boolean jumpIfFalse) {
        Intrinsics.checkNotNullParameter(jumpLabel, "jumpLabel");
        Intrinsics.checkNotNullParameter(v, "v");
        condJump(jumpLabel, v, jumpIfFalse);
    }

    /* JADX INFO: compiled from: BranchedValue.kt */
    @Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011J&\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0018J&\u0010\u001a\u001a\u00020\u001d2\u0006\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\u00182\u0006\u0010$\u001a\u00020\u0018J&\u0010%\u001a\u00020\u001d2\u0006\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\"J\u0018\u0010&\u001a\u00020\u001d2\u0006\u0010'\u001a\u00020\u000b2\u0006\u0010(\u001a\u00020\u000bH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R-\u0010\t\u001a\u001e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\nj\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b`\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/codegen/BranchedValue$Companion;", "", "()V", "FALSE", "Lorg/jetbrains/kotlin/codegen/BranchedValue;", "getFALSE", "()Lorg/jetbrains/kotlin/codegen/BranchedValue;", "TRUE", "getTRUE", "negatedOperations", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "getNegatedOperations", "()Ljava/util/HashMap;", "booleanConstant", "value", "", "cmp", "opToken", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "operandType", "Lorg/jetbrains/org/objectweb/asm/Type;", "left", "Lorg/jetbrains/kotlin/codegen/StackValue;", "right", "condJump", "Lorg/jetbrains/kotlin/codegen/CondJump;", "condition", "", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/Label;", "jumpIfFalse", "iv", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "createInvertValue", "argument", "loopJump", "registerOperations", "op", "negatedOp", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final HashMap<Integer, Integer> getNegatedOperations() {
            return BranchedValue.negatedOperations;
        }

        public final BranchedValue getTRUE() {
            return BranchedValue.TRUE;
        }

        public final BranchedValue getFALSE() {
            return BranchedValue.FALSE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void registerOperations(int op, int negatedOp) {
            getNegatedOperations().put(Integer.valueOf(op), Integer.valueOf(negatedOp));
            getNegatedOperations().put(Integer.valueOf(negatedOp), Integer.valueOf(op));
        }

        public final BranchedValue booleanConstant(boolean value) {
            return value ? getTRUE() : getFALSE();
        }

        public final StackValue createInvertValue(StackValue argument) {
            Intrinsics.checkNotNullParameter(argument, "argument");
            return new Invert(condJump(argument));
        }

        public final void condJump(StackValue condition, Label label, boolean jumpIfFalse, InstructionAdapter iv) {
            Intrinsics.checkNotNullParameter(condition, "condition");
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(iv, "iv");
            condJump(condition).condJump(label, iv, jumpIfFalse);
        }

        public final void loopJump(StackValue condition, Label label, boolean jumpIfFalse, InstructionAdapter iv) {
            Intrinsics.checkNotNullParameter(condition, "condition");
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(iv, "iv");
            condJump(condition).loopJump(label, iv, jumpIfFalse);
        }

        public final CondJump condJump(StackValue condition) {
            Intrinsics.checkNotNullParameter(condition, "condition");
            BranchedValue branchedValue = condition instanceof BranchedValue ? (BranchedValue) condition : null;
            if (branchedValue == null) {
                Type BOOLEAN_TYPE = Type.BOOLEAN_TYPE;
                Intrinsics.checkNotNullExpressionValue(BOOLEAN_TYPE, "BOOLEAN_TYPE");
                branchedValue = new BranchedValue(condition, null, BOOLEAN_TYPE, 153);
            }
            return new CondJump(branchedValue, 153);
        }

        public final BranchedValue cmp(IElementType opToken, Type operandType, StackValue left, StackValue right) {
            Intrinsics.checkNotNullParameter(opToken, "opToken");
            Intrinsics.checkNotNullParameter(operandType, "operandType");
            Intrinsics.checkNotNullParameter(left, "left");
            Intrinsics.checkNotNullParameter(right, "right");
            if (operandType.getSort() == 10) {
                return new ObjectCompare(opToken, operandType, left, right);
            }
            return new NumberCompare(opToken, operandType, left, right);
        }
    }

    static {
        Companion companion = new Companion(null);
        INSTANCE = companion;
        negatedOperations = new HashMap<>();
        final StackValue stackValueNone = StackValue.none();
        final Type type = Type.BOOLEAN_TYPE;
        TRUE = new BranchedValue(stackValueNone, type) { // from class: org.jetbrains.kotlin.codegen.BranchedValue$Companion$TRUE$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(stackValueNone, null, type, 153);
                Intrinsics.checkNotNull(stackValueNone);
                Intrinsics.checkNotNull(type);
            }

            @Override // org.jetbrains.kotlin.codegen.BranchedValue
            public void condJump(Label jumpLabel, InstructionAdapter v, boolean jumpIfFalse) {
                Intrinsics.checkNotNullParameter(jumpLabel, "jumpLabel");
                Intrinsics.checkNotNullParameter(v, "v");
                v.nop();
                if (jumpIfFalse) {
                    return;
                }
                v.goTo(jumpLabel);
            }

            @Override // org.jetbrains.kotlin.codegen.BranchedValue
            public void loopJump(Label jumpLabel, InstructionAdapter v, boolean jumpIfFalse) {
                Intrinsics.checkNotNullParameter(jumpLabel, "jumpLabel");
                Intrinsics.checkNotNullParameter(v, "v");
                v.nop();
                if (!jumpIfFalse) {
                    PseudoInsnsKt.fakeAlwaysTrueIfeq(v, jumpLabel);
                } else {
                    PseudoInsnsKt.fakeAlwaysFalseIfeq(v, jumpLabel);
                }
            }

            @Override // org.jetbrains.kotlin.codegen.BranchedValue, org.jetbrains.kotlin.codegen.StackValue
            public void putSelector(Type type2, KotlinType kotlinType, InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(type2, "type");
                Intrinsics.checkNotNullParameter(v, "v");
                v.iconst(1);
                coerceTo(type2, kotlinType, v);
            }
        };
        final StackValue stackValueNone2 = StackValue.none();
        final Type type2 = Type.BOOLEAN_TYPE;
        FALSE = new BranchedValue(stackValueNone2, type2) { // from class: org.jetbrains.kotlin.codegen.BranchedValue$Companion$FALSE$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(stackValueNone2, null, type2, 153);
                Intrinsics.checkNotNull(stackValueNone2);
                Intrinsics.checkNotNull(type2);
            }

            @Override // org.jetbrains.kotlin.codegen.BranchedValue
            public void condJump(Label jumpLabel, InstructionAdapter v, boolean jumpIfFalse) {
                Intrinsics.checkNotNullParameter(jumpLabel, "jumpLabel");
                Intrinsics.checkNotNullParameter(v, "v");
                v.nop();
                if (jumpIfFalse) {
                    v.goTo(jumpLabel);
                }
            }

            @Override // org.jetbrains.kotlin.codegen.BranchedValue
            public void loopJump(Label jumpLabel, InstructionAdapter v, boolean jumpIfFalse) {
                Intrinsics.checkNotNullParameter(jumpLabel, "jumpLabel");
                Intrinsics.checkNotNullParameter(v, "v");
                v.nop();
                if (jumpIfFalse) {
                    PseudoInsnsKt.fakeAlwaysTrueIfeq(v, jumpLabel);
                } else {
                    PseudoInsnsKt.fakeAlwaysFalseIfeq(v, jumpLabel);
                }
            }

            @Override // org.jetbrains.kotlin.codegen.BranchedValue, org.jetbrains.kotlin.codegen.StackValue
            public void putSelector(Type type3, KotlinType kotlinType, InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(type3, "type");
                Intrinsics.checkNotNullParameter(v, "v");
                v.iconst(0);
                coerceTo(type3, kotlinType, v);
            }
        };
        companion.registerOperations(154, 153);
        companion.registerOperations(158, 157);
        companion.registerOperations(155, 156);
        companion.registerOperations(156, 155);
        companion.registerOperations(157, 158);
        companion.registerOperations(166, 165);
        companion.registerOperations(198, 199);
    }
}
