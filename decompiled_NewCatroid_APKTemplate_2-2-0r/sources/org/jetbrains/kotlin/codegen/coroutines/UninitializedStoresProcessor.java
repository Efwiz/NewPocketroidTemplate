package org.jetbrains.kotlin.codegen.coroutines;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.method.MethodDescription;
import org.jetbrains.kotlin.codegen.coroutines.UninitializedStoresProcessor.UninitializedNewValueFrame;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.common.FastMethodAnalyzer;
import org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter;
import org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.fixStack.StackTransformationUtilsKt;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TypeInsnNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;
import org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: processUninitializedStores.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0003\u0010\u0011\u0012B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u001c\u0010\t\u001a\u0004\u0018\u00010\n*\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\f\u0010\u000f\u001a\u00020\u0006*\u00020\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/UninitializedStoresProcessor;", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;)V", "isInSpecialMethod", "", "run", "", "getUninitializedValueForConstructorCall", "Lorg/jetbrains/kotlin/codegen/coroutines/UninitializedStoresProcessor$UninitializedNewValue;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "isConstructorCall", "UninitializedNewValue", "UninitializedNewValueFrame", "UninitializedNewValueMarkerInterpreter", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UninitializedStoresProcessor {
    private final boolean isInSpecialMethod;
    private final MethodNode methodNode;

    public UninitializedStoresProcessor(MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        this.methodNode = methodNode;
        this.isInSpecialMethod = Intrinsics.areEqual(methodNode.f1016name, "<init>") || Intrinsics.areEqual(methodNode.f1016name, MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0052  */
    public final void run() {
        UninitializedNewValueMarkerInterpreter uninitializedNewValueMarkerInterpreter;
        char c;
        AbstractInsnNode[] array = this.methodNode.instructions.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        for (AbstractInsnNode abstractInsnNode : array) {
            char c2 = 187;
            if (abstractInsnNode.getOpcode() == 187) {
                InsnList instructions = this.methodNode.instructions;
                Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                final UninitializedNewValueMarkerInterpreter uninitializedNewValueMarkerInterpreter2 = new UninitializedNewValueMarkerInterpreter(instructions);
                final MethodNode methodNode = this.methodNode;
                Frame<BasicValue>[] frameArrAnalyze = new FastMethodAnalyzer<BasicValue>(uninitializedNewValueMarkerInterpreter2, this, methodNode) { // from class: org.jetbrains.kotlin.codegen.coroutines.UninitializedStoresProcessor$run$analyzer$1
                    final /* synthetic */ UninitializedStoresProcessor this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super("fake", methodNode, uninitializedNewValueMarkerInterpreter2, true);
                        this.this$0 = this;
                    }

                    @Override // org.jetbrains.kotlin.codegen.optimization.common.FastMethodAnalyzer
                    protected Frame<BasicValue> newFrame(int nLocals, int nStack) {
                        return this.this$0.new UninitializedNewValueFrame(nLocals, nStack);
                    }
                }.analyze();
                uninitializedNewValueMarkerInterpreter2.analyzePopInstructions(frameArrAnalyze);
                AbstractInsnNode[] array2 = this.methodNode.instructions.toArray();
                Intrinsics.checkNotNullExpressionValue(array2, "toArray(...)");
                int length = array2.length;
                int i = 0;
                while (i < length) {
                    AbstractInsnNode abstractInsnNode2 = array2[i];
                    Frame<BasicValue> frame = frameArrAnalyze[i];
                    if (frame == null) {
                        uninitializedNewValueMarkerInterpreter = uninitializedNewValueMarkerInterpreter2;
                        c = c2;
                    } else {
                        Intrinsics.checkNotNull(abstractInsnNode2);
                        UninitializedNewValue uninitializedValueForConstructorCall = getUninitializedValueForConstructorCall(frame, abstractInsnNode2);
                        if (uninitializedValueForConstructorCall == null) {
                            uninitializedNewValueMarkerInterpreter = uninitializedNewValueMarkerInterpreter2;
                            c = c2;
                        } else {
                            TypeInsnNode newInsn = uninitializedValueForConstructorCall.getNewInsn();
                            Set<AbstractInsnNode> set = uninitializedNewValueMarkerInterpreter2.getUninitializedValuesToRemovableUsages().get(newInsn);
                            Intrinsics.checkNotNull(set);
                            Set<AbstractInsnNode> set2 = set;
                            Set<AbstractInsnNode> set3 = set2;
                            set3.isEmpty();
                            if (set2.size() != 1) {
                                InsnList insnList = this.methodNode.instructions;
                                Intrinsics.checkNotNull(insnList);
                                UtilKt.removeAll(insnList, set3);
                                insnList.remove(newInsn);
                                int length2 = Type.getArgumentTypes(((MethodInsnNode) abstractInsnNode2).desc).length;
                                ArrayList arrayList = new ArrayList();
                                int size = this.methodNode.maxLocals;
                                int i2 = 0;
                                while (i2 < length2) {
                                    Type type = ((BasicValue) frame.getStack((frame.getStackSize() - 1) - i2)).getType();
                                    this.methodNode.instructions.insertBefore(abstractInsnNode2, new VarInsnNode(type.getOpcode(54), size));
                                    size += type.getSize();
                                    arrayList.add(type);
                                    i2++;
                                    uninitializedNewValueMarkerInterpreter2 = uninitializedNewValueMarkerInterpreter2;
                                }
                                uninitializedNewValueMarkerInterpreter = uninitializedNewValueMarkerInterpreter2;
                                MethodNode methodNode2 = this.methodNode;
                                methodNode2.maxLocals = Math.max(methodNode2.maxLocals, size);
                                InsnList insnList2 = this.methodNode.instructions;
                                String str = newInsn.desc;
                                c = 187;
                                insnList2.insertBefore(abstractInsnNode2, UtilKt.insnListOf(new TypeInsnNode(187, str), new InsnNode(89)));
                                for (Type type2 : CollectionsKt.reversed(arrayList)) {
                                    size -= type2.getSize();
                                    this.methodNode.instructions.insertBefore(abstractInsnNode2, new VarInsnNode(type2.getOpcode(21), size));
                                }
                            } else {
                                uninitializedNewValueMarkerInterpreter = uninitializedNewValueMarkerInterpreter2;
                                c = c2;
                            }
                        }
                    }
                    i++;
                    c2 = c;
                    uninitializedNewValueMarkerInterpreter2 = uninitializedNewValueMarkerInterpreter;
                }
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: processUninitializedStores.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\fH\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/UninitializedStoresProcessor$UninitializedNewValueFrame;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "nLocals", "", "nStack", "(Lorg/jetbrains/kotlin/codegen/coroutines/UninitializedStoresProcessor;II)V", "execute", "", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "interpreter", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Interpreter;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class UninitializedNewValueFrame extends Frame<BasicValue> {
        public UninitializedNewValueFrame(int i, int i2) {
            super(i, i2);
        }

        @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Frame
        public void execute(AbstractInsnNode insn, Interpreter<BasicValue> interpreter) throws AnalyzerException {
            Intrinsics.checkNotNullParameter(insn, "insn");
            boolean z = UninitializedStoresProcessor.this.getUninitializedValueForConstructorCall(this, insn) != null;
            super.execute(insn, interpreter);
            if (z) {
                BasicValue basicValuePop = pop();
                Intrinsics.checkNotNull(basicValuePop, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.coroutines.UninitializedStoresProcessor.UninitializedNewValue");
                push(new StrictBasicValue(((UninitializedNewValue) basicValuePop).getType()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final UninitializedNewValue getUninitializedValueForConstructorCall(Frame<BasicValue> frame, AbstractInsnNode abstractInsnNode) {
        if (!isConstructorCall(abstractInsnNode)) {
            return null;
        }
        abstractInsnNode.getOpcode();
        Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.MethodInsnNode");
        int length = Type.getArgumentTypes(((MethodInsnNode) abstractInsnNode).desc).length;
        Value valuePeek = StackTransformationUtilsKt.peek(frame, length + 1);
        UninitializedNewValue uninitializedNewValue = valuePeek instanceof UninitializedNewValue ? (UninitializedNewValue) valuePeek : null;
        if (uninitializedNewValue == null) {
            if (this.isInSpecialMethod) {
                return null;
            }
            throw new IllegalStateException("Expected value generated with NEW".toString());
        }
        boolean z = StackTransformationUtilsKt.peek(frame, length) instanceof UninitializedNewValue;
        return uninitializedNewValue;
    }

    /* JADX INFO: compiled from: processUninitializedStores.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000b\u001a\u00020\u0005H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/UninitializedStoresProcessor$UninitializedNewValue;", "Lorg/jetbrains/kotlin/codegen/optimization/common/StrictBasicValue;", "newInsn", "Lorg/jetbrains/org/objectweb/asm/tree/TypeInsnNode;", "internalName", "", "(Lorg/jetbrains/org/objectweb/asm/tree/TypeInsnNode;Ljava/lang/String;)V", "getInternalName", "()Ljava/lang/String;", "getNewInsn", "()Lorg/jetbrains/org/objectweb/asm/tree/TypeInsnNode;", "toString", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class UninitializedNewValue extends StrictBasicValue {
        private final String internalName;
        private final TypeInsnNode newInsn;

        public final TypeInsnNode getNewInsn() {
            return this.newInsn;
        }

        public final String getInternalName() {
            return this.internalName;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public UninitializedNewValue(TypeInsnNode newInsn, String internalName) {
            super(Type.getObjectType(internalName));
            Intrinsics.checkNotNullParameter(newInsn, "newInsn");
            Intrinsics.checkNotNullParameter(internalName, "internalName");
            this.newInsn = newInsn;
            this.internalName = internalName;
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
        public String toString() {
            return "UninitializedNewValue(internalName='" + this.internalName + "')";
        }
    }

    private final boolean isConstructorCall(AbstractInsnNode abstractInsnNode) {
        return (abstractInsnNode instanceof MethodInsnNode) && Intrinsics.areEqual(((MethodInsnNode) abstractInsnNode).f1015name, "<init>");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: processUninitializedStores.kt */
    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00072\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002J\u001e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00072\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002J!\u0010\u0017\u001a\u00020\u00112\u0014\u0010\u0018\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u00140\u0019¢\u0006\u0002\u0010\u001aJ\u0018\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0007H\u0002J\u001c\u0010\u001f\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0012\u001a\u00020\u00072\b\u0010 \u001a\u0004\u0018\u00010\u0015H\u0016J\u0018\u0010!\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020\u00152\u0006\u0010#\u001a\u00020\u0015H\u0016J\u0012\u0010$\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0012\u001a\u00020\u0007H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R9\u0010\u0005\u001a*\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\b0\u0006j\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\b`\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\u00020\r*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/UninitializedStoresProcessor$UninitializedNewValueMarkerInterpreter;", "Lorg/jetbrains/kotlin/codegen/optimization/common/OptimizationBasicInterpreter;", "instructions", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "(Lorg/jetbrains/org/objectweb/asm/tree/InsnList;)V", "uninitializedValuesToRemovableUsages", "Ljava/util/HashMap;", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "", "Lkotlin/collections/HashMap;", "getUninitializedValuesToRemovableUsages", "()Ljava/util/HashMap;", "debugText", "", "getDebugText", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)Ljava/lang/String;", "analyzePop", "", "insn", TypedValues.AttributesType.S_FRAME, "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "analyzePop2", "analyzePopInstructions", "frames", "", "([Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;)V", "checkUninitializedObjectCopy", "newInsn", "Lorg/jetbrains/org/objectweb/asm/tree/TypeInsnNode;", "usageInsn", "copyOperation", "value", "merge", RegisterSpec.PREFIX, "w", "newOperation", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class UninitializedNewValueMarkerInterpreter extends OptimizationBasicInterpreter {
        private final InsnList instructions;
        private final HashMap<AbstractInsnNode, Set<AbstractInsnNode>> uninitializedValuesToRemovableUsages;

        public UninitializedNewValueMarkerInterpreter(InsnList instructions) {
            Intrinsics.checkNotNullParameter(instructions, "instructions");
            this.instructions = instructions;
            this.uninitializedValuesToRemovableUsages = new HashMap<>();
        }

        public final HashMap<AbstractInsnNode, Set<AbstractInsnNode>> getUninitializedValuesToRemovableUsages() {
            return this.uninitializedValuesToRemovableUsages;
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
        public BasicValue newOperation(AbstractInsnNode insn) {
            Intrinsics.checkNotNullParameter(insn, "insn");
            if (insn.getOpcode() == 187) {
                HashMap<AbstractInsnNode, Set<AbstractInsnNode>> map = this.uninitializedValuesToRemovableUsages;
                if (map.get(insn) == null) {
                    map.put(insn, new LinkedHashSet());
                }
                TypeInsnNode typeInsnNode = (TypeInsnNode) insn;
                String desc = typeInsnNode.desc;
                Intrinsics.checkNotNullExpressionValue(desc, "desc");
                return new UninitializedNewValue(typeInsnNode, desc);
            }
            return super.newOperation(insn);
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
        public BasicValue copyOperation(AbstractInsnNode insn, BasicValue value) {
            Intrinsics.checkNotNullParameter(insn, "insn");
            if (value instanceof UninitializedNewValue) {
                UninitializedNewValue uninitializedNewValue = (UninitializedNewValue) value;
                checkUninitializedObjectCopy(uninitializedNewValue.getNewInsn(), insn);
                Set<AbstractInsnNode> set = this.uninitializedValuesToRemovableUsages.get(uninitializedNewValue.getNewInsn());
                Intrinsics.checkNotNull(set);
                set.add(insn);
                return value;
            }
            return super.copyOperation(insn, value);
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
        public BasicValue merge(BasicValue v, BasicValue w) {
            Intrinsics.checkNotNullParameter(v, "v");
            Intrinsics.checkNotNullParameter(w, "w");
            if (v == w) {
                return v;
            }
            if (v == StrictBasicValue.UNINITIALIZED_VALUE || w == StrictBasicValue.UNINITIALIZED_VALUE) {
                return StrictBasicValue.UNINITIALIZED_VALUE;
            }
            boolean z = v instanceof UninitializedNewValue;
            if (z || (w instanceof UninitializedNewValue)) {
                UninitializedNewValue uninitializedNewValue = z ? (UninitializedNewValue) v : null;
                TypeInsnNode newInsn = uninitializedNewValue != null ? uninitializedNewValue.getNewInsn() : null;
                UninitializedNewValue uninitializedNewValue2 = w instanceof UninitializedNewValue ? (UninitializedNewValue) w : null;
                return newInsn != (uninitializedNewValue2 != null ? uninitializedNewValue2.getNewInsn() : null) ? StrictBasicValue.UNINITIALIZED_VALUE : v;
            }
            BasicValue basicValueMerge = super.merge(v, w);
            Intrinsics.checkNotNullExpressionValue(basicValueMerge, "merge(...)");
            return basicValueMerge;
        }

        private final void checkUninitializedObjectCopy(TypeInsnNode newInsn, AbstractInsnNode usageInsn) {
            int opcode = usageInsn.getOpcode();
            if (opcode != 25 && opcode != 58 && opcode != 89) {
                throw new IllegalStateException(("Unexpected copy instruction for " + getDebugText(newInsn) + ": " + getDebugText(usageInsn)).toString());
            }
        }

        private final String getDebugText(AbstractInsnNode abstractInsnNode) {
            return this.instructions.indexOf(abstractInsnNode) + ": " + InlineCodegenUtilsKt.getInsnText(abstractInsnNode);
        }

        public final void analyzePopInstructions(Frame<BasicValue>[] frames) {
            Intrinsics.checkNotNullParameter(frames, "frames");
            AbstractInsnNode[] array = this.instructions.toArray();
            int length = frames.length;
            for (int i = 0; i < length; i++) {
                Frame<BasicValue> frame = frames[i];
                if (frame != null) {
                    AbstractInsnNode abstractInsnNode = array[i];
                    int opcode = abstractInsnNode.getOpcode();
                    if (opcode == 87) {
                        Intrinsics.checkNotNull(abstractInsnNode);
                        analyzePop(abstractInsnNode, frame);
                    } else if (opcode == 88) {
                        Intrinsics.checkNotNull(abstractInsnNode);
                        analyzePop2(abstractInsnNode, frame);
                    }
                }
            }
        }

        private final void analyzePop(AbstractInsnNode insn, Frame<BasicValue> frame) {
            BasicValue basicValue = (BasicValue) StackTransformationUtilsKt.top(frame);
            if (basicValue == null) {
                throw new IllegalStateException(("Stack underflow on POP: " + getDebugText(insn)).toString());
            }
            if (basicValue instanceof UninitializedNewValue) {
                Set<AbstractInsnNode> set = this.uninitializedValuesToRemovableUsages.get(((UninitializedNewValue) basicValue).getNewInsn());
                Intrinsics.checkNotNull(set);
                set.add(insn);
            }
        }

        private final void analyzePop2(AbstractInsnNode insn, Frame<BasicValue> frame) {
            List<BasicValue> listPeekWords = StackTransformationUtilsKt.peekWords(frame, 2);
            if (listPeekWords == null) {
                throw new IllegalStateException(("Stack underflow on POP2: " + getDebugText(insn)).toString());
            }
            for (BasicValue basicValue : listPeekWords) {
                if (basicValue instanceof UninitializedNewValue) {
                    throw new IllegalStateException(("Unexpected POP2 instruction for " + getDebugText(((UninitializedNewValue) basicValue).getNewInsn()) + ": " + getDebugText(insn)).toString());
                }
            }
        }
    }
}
