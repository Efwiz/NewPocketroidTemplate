package org.jetbrains.kotlin.codegen.optimization;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.codegen.optimization.common.FastMethodAnalyzer;
import org.jetbrains.kotlin.codegen.optimization.common.InstructionLivenessAnalyzer;
import org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter;
import org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue;
import org.jetbrains.kotlin.codegen.optimization.fixStack.StackTransformationUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TypeInsnNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;

/* JADX INFO: compiled from: RedundantCheckCastElimination.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J/\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b2\u0006\u0010\t\u001a\u00020\nH\u0002¢\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\u0002J\u0018\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006H\u0002J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/RedundantCheckCastEliminationMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "()V", "getTypeAdjustmentForALoadInstructions", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "Lorg/jetbrains/org/objectweb/asm/Type;", "insns", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "([Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;)Ljava/util/Map;", "isMultiArrayType", "", ModuleXmlParser.TYPE, "isTrivialSubtype", "superType", "subType", "transform", "", "internalClassName", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RedundantCheckCastEliminationMethodTransformer extends MethodTransformer {
    /* JADX WARN: Code duplicated, block: B:25:0x008c  */
    /* JADX WARN: Code duplicated, block: B:27:0x0095  */
    /* JADX WARN: Code duplicated, block: B:45:0x0098 A[SYNTHETIC] */
    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) throws AnalyzerException {
        Frame frame;
        BasicValue basicValue;
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        AbstractInsnNode[] array = methodNode.instructions.toArray();
        Intrinsics.checkNotNull(array);
        for (AbstractInsnNode abstractInsnNode : array) {
            if (abstractInsnNode.getOpcode() == 192) {
                for (AbstractInsnNode abstractInsnNode2 : array) {
                    ReifiedTypeInliner.Companion companion = ReifiedTypeInliner.INSTANCE;
                    Intrinsics.checkNotNull(abstractInsnNode2);
                    if (companion.isOperationReifiedMarker(abstractInsnNode2)) {
                        return;
                    }
                }
                final Map<AbstractInsnNode, Type> typeAdjustmentForALoadInstructions = getTypeAdjustmentForALoadInstructions(array, methodNode);
                OptimizationBasicInterpreter optimizationBasicInterpreter = new OptimizationBasicInterpreter() { // from class: org.jetbrains.kotlin.codegen.optimization.RedundantCheckCastEliminationMethodTransformer$transform$interpreter$1
                    @Override // org.jetbrains.kotlin.codegen.optimization.common.OptimizationBasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
                    public BasicValue copyOperation(AbstractInsnNode insn, BasicValue value) throws AnalyzerException {
                        Intrinsics.checkNotNullParameter(insn, "insn");
                        Type type = typeAdjustmentForALoadInstructions.get(insn);
                        if (type != null) {
                            StrictBasicValue strictBasicValueNewValue = newValue(type);
                            if (strictBasicValueNewValue != null) {
                                return strictBasicValueNewValue;
                            }
                            throw new AssertionError("Local variable type can't be VOID: " + type);
                        }
                        BasicValue basicValueCopyOperation = super.copyOperation(insn, value);
                        Intrinsics.checkNotNullExpressionValue(basicValueCopyOperation, "copyOperation(...)");
                        return basicValueCopyOperation;
                    }
                };
                ArrayList arrayList = new ArrayList();
                Frame[] frameArrAnalyze = new FastMethodAnalyzer(internalClassName, methodNode, optimizationBasicInterpreter, true).analyze();
                int length = array.length;
                for (int i = 0; i < length; i++) {
                    AbstractInsnNode abstractInsnNode3 = array[i];
                    if (abstractInsnNode3.getOpcode() == 192 && (frame = frameArrAnalyze[i]) != null && (basicValue = (BasicValue) StackTransformationUtilsKt.top(frame)) != null) {
                        Intrinsics.checkNotNull(abstractInsnNode3, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.TypeInsnNode");
                        TypeInsnNode typeInsnNode = (TypeInsnNode) abstractInsnNode3;
                        Type objectType = Type.getObjectType(typeInsnNode.desc);
                        if (basicValue != StrictBasicValue.NULL_VALUE) {
                            Intrinsics.checkNotNull(objectType);
                            Type type = basicValue.getType();
                            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                            if (isTrivialSubtype(objectType, type)) {
                                Intrinsics.checkNotNull(objectType);
                                if (!isMultiArrayType(objectType)) {
                                    arrayList.add(typeInsnNode);
                                }
                            }
                        } else {
                            Intrinsics.checkNotNull(objectType);
                            if (!isMultiArrayType(objectType)) {
                                arrayList.add(typeInsnNode);
                            }
                        }
                    }
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    methodNode.instructions.remove((TypeInsnNode) it.next());
                }
                return;
            }
        }
    }

    private final Map<AbstractInsnNode, Type> getTypeAdjustmentForALoadInstructions(AbstractInsnNode[] insns, MethodNode methodNode) {
        boolean[] zArrAnalyze = new InstructionLivenessAnalyzer(methodNode, false).analyze();
        HashMap map = new HashMap();
        for (LocalVariableNode localVariableNode : methodNode.localVariables) {
            int iIndexOf = methodNode.instructions.indexOf(localVariableNode.end);
            for (int iIndexOf2 = methodNode.instructions.indexOf(localVariableNode.start); iIndexOf2 < iIndexOf; iIndexOf2++) {
                AbstractInsnNode abstractInsnNode = insns[iIndexOf2];
                if (abstractInsnNode.getOpcode() == 25) {
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                    if (((VarInsnNode) abstractInsnNode).var == localVariableNode.index && !zArrAnalyze[iIndexOf2]) {
                        Type type = Type.getType(localVariableNode.desc);
                        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                        map.put(abstractInsnNode, type);
                    }
                }
            }
        }
        return map;
    }

    private final boolean isTrivialSubtype(Type superType, Type subType) {
        return Intrinsics.areEqual(superType, subType);
    }

    private final boolean isMultiArrayType(Type type) {
        return type.getSort() == 9 && type.getDimensions() != 1;
    }
}
