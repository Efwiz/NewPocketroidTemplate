package org.jetbrains.kotlin.codegen.optimization.fixStack;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsn;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;
import org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: FixStackAnalyzer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u0000 '2\u00020\u0001:\u0002'(B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0006\u0010\u001b\u001a\u00020\u001cJ\u0016\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u001e2\u0006\u0010 \u001a\u00020\u0013J\u000e\u0010!\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\u0013J\u000e\u0010\"\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\u0013J\u001a\u0010#\u001a\f\u0018\u00010$R\u00060\fR\u00020\u00002\u0006\u0010 \u001a\u00020\u0013H\u0002J\u0016\u0010%\u001a\n\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u001e2\u0006\u0010 \u001a\u00020\u0013J\b\u0010&\u001a\u00020\u001cH\u0002R\u0012\u0010\u000b\u001a\u00060\fR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR6\u0010\u000f\u001a*\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u0010j\u0014\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u0012`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0015\u001a\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackAnalyzer;", "", "owner", "", "method", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "context", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackContext;", "skipBreakContinueGotoEdges", "", "(Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackContext;Z)V", "analyzer", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackAnalyzer$InternalAnalyzer;", "getContext", "()Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackContext;", "loopEntryPointMarkers", "Ljava/util/HashMap;", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "Lorg/jetbrains/kotlin/utils/SmartList;", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "Lkotlin/collections/HashMap;", "maxExtraStackSize", "", "getMaxExtraStackSize", "()I", "getMethod", "()Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "analyze", "", "getActualStack", "", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackValue;", FirebaseAnalytics.Param.LOCATION, "getActualStackSize", "getExpectedStackSize", "getFrame", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackAnalyzer$InternalAnalyzer$FixStackFrame;", "getStackToSpill", "recordLoopEntryPointMarkers", "Companion", "InternalAnalyzer", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FixStackAnalyzer {
    public static final int DEAD_CODE_STACK_SIZE = -1;
    private final InternalAnalyzer analyzer;
    private final FixStackContext context;
    private final HashMap<LabelNode, SmartList<AbstractInsnNode>> loopEntryPointMarkers;
    private final MethodNode method;
    private final boolean skipBreakContinueGotoEdges;

    public FixStackAnalyzer(String owner, MethodNode method, FixStackContext context, boolean z) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(context, "context");
        this.method = method;
        this.context = context;
        this.skipBreakContinueGotoEdges = z;
        this.loopEntryPointMarkers = new HashMap<>();
        this.analyzer = new InternalAnalyzer(this, owner);
    }

    public /* synthetic */ FixStackAnalyzer(String str, MethodNode methodNode, FixStackContext fixStackContext, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, methodNode, fixStackContext, (i & 8) != 0 ? true : z);
    }

    public final MethodNode getMethod() {
        return this.method;
    }

    public final FixStackContext getContext() {
        return this.context;
    }

    public final int getMaxExtraStackSize() {
        return this.analyzer.getMaxExtraStackSize();
    }

    public final List<FixStackValue> getStackToSpill(AbstractInsnNode location) {
        Intrinsics.checkNotNullParameter(location, "location");
        return this.analyzer.getSpilledStacks().get(location);
    }

    public final List<FixStackValue> getActualStack(AbstractInsnNode location) {
        Intrinsics.checkNotNullParameter(location, "location");
        InternalAnalyzer.FixStackFrame frame = getFrame(location);
        if (frame != null) {
            return frame.getStackContent();
        }
        return null;
    }

    public final int getActualStackSize(AbstractInsnNode location) {
        Intrinsics.checkNotNullParameter(location, "location");
        InternalAnalyzer.FixStackFrame frame = getFrame(location);
        if (frame != null) {
            return frame.getStackSizeWithExtra();
        }
        return -1;
    }

    public final int getExpectedStackSize(AbstractInsnNode location) {
        Intrinsics.checkNotNullParameter(location, "location");
        SmartList<AbstractInsnNode> smartList = this.loopEntryPointMarkers.get(location);
        Iterator it = (smartList != null ? smartList : CollectionsKt.listOf(location)).iterator();
        while (it.hasNext()) {
            InternalAnalyzer.FixStackFrame frame = getFrame((AbstractInsnNode) it.next());
            if (frame != null) {
                return frame.getStackSizeWithExtra();
            }
        }
        return -1;
    }

    private final InternalAnalyzer.FixStackFrame getFrame(AbstractInsnNode location) {
        Frame<FixStackValue> frame = this.analyzer.getFrame(location);
        if (frame instanceof InternalAnalyzer.FixStackFrame) {
            return (InternalAnalyzer.FixStackFrame) frame;
        }
        return null;
    }

    public final void analyze() {
        recordLoopEntryPointMarkers();
        this.analyzer.analyze();
    }

    private final void recordLoopEntryPointMarkers() {
        for (AbstractInsnNode abstractInsnNode : this.context.getFakeAlwaysFalseIfeqMarkers()) {
            AbstractInsnNode next = abstractInsnNode.getNext();
            if (next instanceof JumpInsnNode) {
                HashMap<LabelNode, SmartList<AbstractInsnNode>> map = this.loopEntryPointMarkers;
                LabelNode label = ((JumpInsnNode) next).label;
                Intrinsics.checkNotNullExpressionValue(label, "label");
                SmartList<AbstractInsnNode> smartList = map.get(label);
                if (smartList == null) {
                    smartList = new SmartList<>();
                    map.put(label, smartList);
                }
                smartList.add(abstractInsnNode);
            }
        }
    }

    /* JADX INFO: compiled from: FixStackAnalyzer.kt */
    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001$B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\rH\u0002J\u001e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0007H\u0014J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u0007H\u0014J\u001c\u0010\u001c\u001a\u00020\u001d*\n0\u001eR\u00060\u0000R\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\rH\u0002J\u001c\u0010 \u001a\u00020\u001d*\n0\u001eR\u00060\u0000R\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\rH\u0002J\u001c\u0010!\u001a\u00020\u001d*\n0\u001eR\u00060\u0000R\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\rH\u0002J\u001c\u0010\"\u001a\u00020\u001d*\n0\u001eR\u00060\u0000R\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\rH\u0002J\u001c\u0010#\u001a\u00020\u001d*\n0\u001eR\u00060\u0000R\u00020\u001f2\u0006\u0010\u001a\u001a\u00020\rH\u0002R\u001e\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0007@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR9\u0010\u000b\u001a*\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u000e0\fj\u0014\u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u000e`\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackAnalyzer$InternalAnalyzer;", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FastStackAnalyzer;", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackValue;", "owner", "", "(Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackAnalyzer;Ljava/lang/String;)V", "<set-?>", "", "maxExtraStackSize", "getMaxExtraStackSize", "()I", "spilledStacks", "Ljava/util/HashMap;", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "", "Lkotlin/collections/HashMap;", "getSpilledStacks", "()Ljava/util/HashMap;", "indexOf", "node", "newFrame", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "nLocals", "nStack", "visitControlFlowEdge", "", "insn", "successor", "executeAfterInlineCallMarker", "", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackAnalyzer$InternalAnalyzer$FixStackFrame;", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackAnalyzer;", "executeBeforeInlineCallMarker", "executeRestoreStackInTryCatch", "executeSaveStackBeforeTry", "saveStackAndClear", "FixStackFrame", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class InternalAnalyzer extends FastStackAnalyzer<FixStackValue> {
        private int maxExtraStackSize;
        private final HashMap<AbstractInsnNode, List<FixStackValue>> spilledStacks;
        final /* synthetic */ FixStackAnalyzer this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public InternalAnalyzer(FixStackAnalyzer fixStackAnalyzer, String owner) {
            super(owner, fixStackAnalyzer.getMethod(), new FixStackInterpreter());
            Intrinsics.checkNotNullParameter(owner, "owner");
            this.this$0 = fixStackAnalyzer;
            this.spilledStacks = new HashMap<>();
        }

        public final HashMap<AbstractInsnNode, List<FixStackValue>> getSpilledStacks() {
            return this.spilledStacks;
        }

        public final int getMaxExtraStackSize() {
            return this.maxExtraStackSize;
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.FastStackAnalyzer
        protected boolean visitControlFlowEdge(int insn, int successor) {
            if (!this.this$0.skipBreakContinueGotoEdges) {
                return true;
            }
            AbstractInsnNode abstractInsnNode = getInsnsArray()[insn];
            return ((abstractInsnNode instanceof JumpInsnNode) && this.this$0.getContext().getBreakContinueGotoNodes().contains(abstractInsnNode)) ? false : true;
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.FastStackAnalyzer
        protected Frame<FixStackValue> newFrame(int nLocals, int nStack) {
            return new FixStackFrame(nLocals, nStack);
        }

        private final int indexOf(AbstractInsnNode node) {
            return getMethod().instructions.indexOf(node);
        }

        /* JADX INFO: compiled from: FixStackAnalyzer.kt */
        @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u001e\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u001e\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u0014H\u0016J\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00020\u0016J\u001e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u000e\u0010\u0018\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u0001H\u0016J&\u0010\u0019\u001a\u00020\u001a2\u000e\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00020\u0014H\u0016J\b\u0010\u001c\u001a\u00020\u0002H\u0016J\u0010\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u0002H\u0016J\u0014\u0010\u001f\u001a\u00020\u000f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00020!J\u0018\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u0002H\u0016R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackAnalyzer$InternalAnalyzer$FixStackFrame;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackValue;", "nLocals", "", "nStack", "(Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackAnalyzer$InternalAnalyzer;II)V", "extraStack", "Lorg/jetbrains/kotlin/com/intellij/util/containers/Stack;", "getExtraStack", "()Lcom/intellij/util/containers/Stack;", "stackSizeWithExtra", "getStackSizeWithExtra", "()I", "clearStack", "", "execute", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "interpreter", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Interpreter;", "getStackContent", "", ConfigConstants.CONFIG_INIT_SECTION, "src", "merge", "", TypedValues.AttributesType.S_FRAME, "pop", "push", "value", "pushAll", CompoundEntry.NAME_values, "", "setStack", ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
        public final class FixStackFrame extends Frame<FixStackValue> {
            private final Stack<FixStackValue> extraStack;

            public FixStackFrame(int i, int i2) {
                super(i, i2);
                this.extraStack = new Stack<>();
            }

            public final Stack<FixStackValue> getExtraStack() {
                return this.extraStack;
            }

            @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Frame
            public Frame<FixStackValue> init(Frame<? extends FixStackValue> src) {
                Intrinsics.checkNotNullParameter(src, "src");
                this.extraStack.clear();
                this.extraStack.addAll(((FixStackFrame) src).extraStack);
                Frame<FixStackValue> frameInit = super.init(src);
                Intrinsics.checkNotNullExpressionValue(frameInit, "init(...)");
                return frameInit;
            }

            @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Frame
            public void clearStack() {
                this.extraStack.clear();
                super.clearStack();
            }

            @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Frame
            public void execute(AbstractInsnNode insn, Interpreter<FixStackValue> interpreter) throws AnalyzerException {
                Intrinsics.checkNotNullParameter(insn, "insn");
                Intrinsics.checkNotNullParameter(interpreter, "interpreter");
                if (PseudoInsn.SAVE_STACK_BEFORE_TRY.isa(insn)) {
                    InternalAnalyzer.this.executeSaveStackBeforeTry(this, insn);
                } else if (PseudoInsn.RESTORE_STACK_IN_TRY_CATCH.isa(insn)) {
                    InternalAnalyzer.this.executeRestoreStackInTryCatch(this, insn);
                } else if (InlineCodegenUtilsKt.isBeforeInlineMarker(insn)) {
                    InternalAnalyzer.this.executeBeforeInlineCallMarker(this, insn);
                } else if (InlineCodegenUtilsKt.isAfterInlineMarker(insn)) {
                    InternalAnalyzer.this.executeAfterInlineCallMarker(this, insn);
                } else if (insn.getOpcode() == 177) {
                    return;
                }
                super.execute(insn, interpreter);
            }

            public final int getStackSizeWithExtra() {
                return super.getStackSize() + this.extraStack.size();
            }

            public final List<FixStackValue> getStackContent() {
                ArrayList arrayList = new ArrayList();
                int stackSize = super.getStackSize();
                for (int i = 0; i < stackSize; i++) {
                    arrayList.add(super.getStack(i));
                }
                arrayList.addAll(this.extraStack);
                return arrayList;
            }

            @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Frame
            public void push(FixStackValue value) {
                Intrinsics.checkNotNullParameter(value, "value");
                if (super.getStackSize() < getMaxStackSize()) {
                    super.push(value);
                    return;
                }
                this.extraStack.add(value);
                InternalAnalyzer internalAnalyzer = InternalAnalyzer.this;
                internalAnalyzer.maxExtraStackSize = Math.max(internalAnalyzer.getMaxExtraStackSize(), this.extraStack.size());
            }

            public final void pushAll(Collection<? extends FixStackValue> values) {
                Intrinsics.checkNotNullParameter(values, "values");
                Iterator<T> it = values.iterator();
                while (it.hasNext()) {
                    push((FixStackValue) it.next());
                }
            }

            @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Frame
            public FixStackValue pop() {
                if (!this.extraStack.isEmpty()) {
                    FixStackValue fixStackValuePop = this.extraStack.pop();
                    Intrinsics.checkNotNull(fixStackValuePop);
                    return fixStackValuePop;
                }
                Value valuePop = super.pop();
                Intrinsics.checkNotNull(valuePop);
                return (FixStackValue) valuePop;
            }

            @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Frame
            public void setStack(int i, FixStackValue value) {
                Intrinsics.checkNotNullParameter(value, "value");
                if (i < super.getMaxStackSize()) {
                    super.setStack(i, value);
                } else {
                    this.extraStack.set(i - getMaxStackSize(), value);
                }
            }

            @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Frame
            public boolean merge(Frame<? extends FixStackValue> frame, Interpreter<FixStackValue> interpreter) {
                Intrinsics.checkNotNullParameter(frame, "frame");
                Intrinsics.checkNotNullParameter(interpreter, "interpreter");
                throw new UnsupportedOperationException("Stack normalization should not merge frames");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void executeBeforeInlineCallMarker(FixStackFrame fixStackFrame, AbstractInsnNode abstractInsnNode) {
            saveStackAndClear(fixStackFrame, abstractInsnNode);
        }

        private final void saveStackAndClear(FixStackFrame fixStackFrame, AbstractInsnNode abstractInsnNode) {
            this.spilledStacks.put(abstractInsnNode, fixStackFrame.getStackContent());
            fixStackFrame.clearStack();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void executeAfterInlineCallMarker(FixStackFrame fixStackFrame, AbstractInsnNode abstractInsnNode) {
            AbstractInsnNode abstractInsnNode2 = this.this$0.getContext().getOpeningInlineMethodMarker().get(abstractInsnNode);
            if (fixStackFrame.getStackSize() > 0) {
                FixStackValue fixStackValuePop = fixStackFrame.pop();
                fixStackFrame.clearStack();
                List<FixStackValue> list = this.spilledStacks.get(abstractInsnNode2);
                Intrinsics.checkNotNull(list);
                fixStackFrame.pushAll(list);
                fixStackFrame.push(fixStackValuePop);
                return;
            }
            List<FixStackValue> list2 = this.spilledStacks.get(abstractInsnNode2);
            Intrinsics.checkNotNull(list2);
            fixStackFrame.pushAll(list2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void executeRestoreStackInTryCatch(FixStackFrame fixStackFrame, AbstractInsnNode abstractInsnNode) {
            AbstractInsnNode abstractInsnNode2 = this.this$0.getContext().getSaveStackMarkerForRestoreMarker().get(abstractInsnNode);
            HashMap<AbstractInsnNode, List<FixStackValue>> map = this.spilledStacks;
            Intrinsics.checkNotNull(abstractInsnNode2);
            List<FixStackValue> list = map.get(abstractInsnNode2);
            if (list == null) {
                throw new AssertionError(indexOf(abstractInsnNode) + ": Restore stack is unavailable for " + indexOf(abstractInsnNode2));
            }
            fixStackFrame.pushAll(list);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void executeSaveStackBeforeTry(FixStackFrame fixStackFrame, AbstractInsnNode abstractInsnNode) {
            saveStackAndClear(fixStackFrame, abstractInsnNode);
        }
    }
}
