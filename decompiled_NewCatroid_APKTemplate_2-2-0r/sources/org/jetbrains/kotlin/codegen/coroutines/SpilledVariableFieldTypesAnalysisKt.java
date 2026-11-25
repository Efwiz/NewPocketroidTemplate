package org.jetbrains.kotlin.codegen.coroutines;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.optimization.common.FastMethodAnalyzer;
import org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;

/* JADX INFO: compiled from: SpilledVariableFieldTypesAnalysis.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a-\u0010\u0006\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b0\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0000¢\u0006\u0002\u0010\u000e\u001a\f\u0010\u000f\u001a\u00020\u0010*\u00020\u0003H\u0002¨\u0006\u0011"}, d2 = {"coerceInt", "", "to", "Lorg/jetbrains/org/objectweb/asm/Type;", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "performSpilledVariableFieldTypesAnalysis", "", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "thisName", "", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;Ljava/lang/String;)[Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "isIntLike", "", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SpilledVariableFieldTypesAnalysisKt {
    public static final Frame<BasicValue>[] performSpilledVariableFieldTypesAnalysis(MethodNode methodNode, String thisName) throws AnalyzerException {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        Intrinsics.checkNotNullParameter(thisName, "thisName");
        IntLikeCoerceInterpreter intLikeCoerceInterpreter = new IntLikeCoerceInterpreter();
        new FastMethodAnalyzer(thisName, methodNode, intLikeCoerceInterpreter, false, 8, null).analyze();
        for (Map.Entry<VarInsnNode, Type> entry : intLikeCoerceInterpreter.getNeedsToBeCoerced().entrySet()) {
            VarInsnNode key = entry.getKey();
            Type value = entry.getValue();
            MethodNode methodNode2 = new MethodNode();
            coerceInt(value, new InstructionAdapter(methodNode2));
            Unit unit = Unit.INSTANCE;
            InsnList instructions = methodNode2.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
            methodNode.instructions.insert(key, instructions);
        }
        return new FastMethodAnalyzer(thisName, methodNode, new OptimizationBasicInterpreter(), false, 8, null).analyze();
    }

    private static final void coerceInt(Type type, InstructionAdapter instructionAdapter) {
        if (Intrinsics.areEqual(type, Type.BOOLEAN_TYPE)) {
            Label label = new Label();
            Label label2 = new Label();
            instructionAdapter.ifeq(label);
            instructionAdapter.iconst(1);
            instructionAdapter.goTo(label2);
            instructionAdapter.mark(label);
            instructionAdapter.iconst(0);
            instructionAdapter.mark(label2);
            return;
        }
        StackValue.coerce(Type.INT_TYPE, type, instructionAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isIntLike(Type type) {
        int sort = type.getSort();
        return sort == 1 || sort == 2 || sort == 3 || sort == 4;
    }
}
