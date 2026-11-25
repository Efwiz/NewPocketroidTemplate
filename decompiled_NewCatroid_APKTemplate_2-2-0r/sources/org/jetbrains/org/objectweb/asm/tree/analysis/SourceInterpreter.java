package org.jetbrains.org.objectweb.asm.tree.analysis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.org.objectweb.asm.Opcodes;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;

/* JADX INFO: loaded from: classes6.dex */
public class SourceInterpreter extends Interpreter<SourceValue> implements Opcodes {
    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public void returnOperation(AbstractInsnNode abstractInsnNode, SourceValue sourceValue, SourceValue sourceValue2) {
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public /* bridge */ /* synthetic */ Value naryOperation(AbstractInsnNode abstractInsnNode, List list) throws AnalyzerException {
        return naryOperation(abstractInsnNode, (List<? extends SourceValue>) list);
    }

    public SourceInterpreter() {
        super(589824);
        if (getClass() != SourceInterpreter.class) {
            throw new IllegalStateException();
        }
    }

    protected SourceInterpreter(int i) {
        super(i);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public SourceValue newValue(Type type) {
        if (type == Type.VOID_TYPE) {
            return null;
        }
        return new SourceValue(type == null ? 1 : type.getSize());
    }

    /* JADX WARN: Code duplicated, block: B:14:0x001e  */
    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public SourceValue newOperation(AbstractInsnNode abstractInsnNode) {
        int opcode = abstractInsnNode.getOpcode();
        int size = 2;
        if (opcode != 9 && opcode != 10 && opcode != 14 && opcode != 15) {
            if (opcode == 18) {
                Object obj = ((LdcInsnNode) abstractInsnNode).cst;
                if (!(obj instanceof Long) && !(obj instanceof Double)) {
                    size = 1;
                }
            } else if (opcode != 178) {
                size = 1;
            } else {
                size = Type.getType(((FieldInsnNode) abstractInsnNode).desc).getSize();
            }
        }
        return new SourceValue(size, abstractInsnNode);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public SourceValue copyOperation(AbstractInsnNode abstractInsnNode, SourceValue sourceValue) {
        return new SourceValue(sourceValue.get_size(), abstractInsnNode);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0038  */
    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public SourceValue unaryOperation(AbstractInsnNode abstractInsnNode, SourceValue sourceValue) {
        int size;
        int opcode = abstractInsnNode.getOpcode();
        if (opcode == 117 || opcode == 119 || opcode == 133 || opcode == 135 || opcode == 138 || opcode == 143) {
            size = 2;
        } else if (opcode == 180) {
            size = Type.getType(((FieldInsnNode) abstractInsnNode).desc).getSize();
        } else if (opcode == 140 || opcode == 141) {
            size = 2;
        } else {
            size = 1;
        }
        return new SourceValue(size, abstractInsnNode);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public SourceValue binaryOperation(AbstractInsnNode abstractInsnNode, SourceValue sourceValue, SourceValue sourceValue2) {
        int i;
        switch (abstractInsnNode.getOpcode()) {
            case 47:
            case 49:
            case 97:
            case 99:
            case 101:
            case 103:
            case 105:
            case 107:
            case 109:
            case 111:
            case 113:
            case 115:
            case 121:
            case 123:
            case 125:
            case 127:
            case 129:
            case 131:
                i = 2;
                break;
            default:
                i = 1;
                break;
        }
        return new SourceValue(i, abstractInsnNode);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public SourceValue ternaryOperation(AbstractInsnNode abstractInsnNode, SourceValue sourceValue, SourceValue sourceValue2, SourceValue sourceValue3) {
        return new SourceValue(1, abstractInsnNode);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public SourceValue naryOperation(AbstractInsnNode abstractInsnNode, List<? extends SourceValue> list) {
        int size;
        int opcode = abstractInsnNode.getOpcode();
        if (opcode == 197) {
            size = 1;
        } else if (opcode == 186) {
            size = Type.getReturnType(((InvokeDynamicInsnNode) abstractInsnNode).desc).getSize();
        } else {
            size = Type.getReturnType(((MethodInsnNode) abstractInsnNode).desc).getSize();
        }
        return new SourceValue(size, abstractInsnNode);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public SourceValue merge(SourceValue sourceValue, SourceValue sourceValue2) {
        if ((sourceValue.insns instanceof SmallSet) && (sourceValue2.insns instanceof SmallSet)) {
            Set<AbstractInsnNode> setUnion = ((SmallSet) sourceValue.insns).union((SmallSet) sourceValue2.insns);
            return (setUnion == sourceValue.insns && sourceValue.size == sourceValue2.size) ? sourceValue : new SourceValue(Math.min(sourceValue.size, sourceValue2.size), setUnion);
        }
        if (sourceValue.size == sourceValue2.size && containsAll(sourceValue.insns, sourceValue2.insns)) {
            return sourceValue;
        }
        HashSet hashSet = new HashSet();
        hashSet.addAll(sourceValue.insns);
        hashSet.addAll(sourceValue2.insns);
        return new SourceValue(Math.min(sourceValue.size, sourceValue2.size), hashSet);
    }

    private static <E> boolean containsAll(Set<E> set, Set<E> set2) {
        if (set.size() < set2.size()) {
            return false;
        }
        return set.containsAll(set2);
    }
}
