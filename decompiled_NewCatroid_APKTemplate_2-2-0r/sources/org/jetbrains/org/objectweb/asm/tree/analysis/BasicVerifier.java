package org.jetbrains.org.objectweb.asm.tree.analysis;

import java.util.List;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;

/* JADX INFO: loaded from: classes6.dex */
public class BasicVerifier extends BasicInterpreter {
    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public /* bridge */ /* synthetic */ Value naryOperation(AbstractInsnNode abstractInsnNode, List list) throws AnalyzerException {
        return naryOperation(abstractInsnNode, (List<? extends BasicValue>) list);
    }

    public BasicVerifier() {
        super(589824);
        if (getClass() != BasicVerifier.class) {
            throw new IllegalStateException();
        }
    }

    protected BasicVerifier(int i) {
        super(i);
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0033  */
    /* JADX WARN: Code duplicated, block: B:20:0x0036  */
    /* JADX WARN: Code duplicated, block: B:21:0x0039  */
    /* JADX WARN: Code duplicated, block: B:22:0x003c  */
    /* JADX WARN: Code duplicated, block: B:25:0x0044 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:26:0x0045  */
    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue copyOperation(AbstractInsnNode abstractInsnNode, BasicValue basicValue) throws AnalyzerException {
        BasicValue basicValue2;
        int opcode = abstractInsnNode.getOpcode();
        switch (opcode) {
            case 21:
                basicValue2 = BasicValue.INT_VALUE;
                if (basicValue2.equals(basicValue)) {
                    return basicValue;
                }
                throw new AnalyzerException(abstractInsnNode, null, basicValue2, basicValue);
            case 22:
                basicValue2 = BasicValue.LONG_VALUE;
                if (basicValue2.equals(basicValue)) {
                    return basicValue;
                }
                throw new AnalyzerException(abstractInsnNode, null, basicValue2, basicValue);
            case 23:
                basicValue2 = BasicValue.FLOAT_VALUE;
                if (basicValue2.equals(basicValue)) {
                    return basicValue;
                }
                throw new AnalyzerException(abstractInsnNode, null, basicValue2, basicValue);
            case 24:
                basicValue2 = BasicValue.DOUBLE_VALUE;
                if (basicValue2.equals(basicValue)) {
                    return basicValue;
                }
                throw new AnalyzerException(abstractInsnNode, null, basicValue2, basicValue);
            case 25:
                if (basicValue.isReference()) {
                    return basicValue;
                }
                throw new AnalyzerException(abstractInsnNode, null, "an object reference", basicValue);
            default:
                switch (opcode) {
                    case 54:
                        basicValue2 = BasicValue.INT_VALUE;
                        break;
                    case 55:
                        basicValue2 = BasicValue.LONG_VALUE;
                        break;
                    case 56:
                        basicValue2 = BasicValue.FLOAT_VALUE;
                        break;
                    case 57:
                        basicValue2 = BasicValue.DOUBLE_VALUE;
                        break;
                    case 58:
                        if (basicValue.isReference() || BasicValue.RETURNADDRESS_VALUE.equals(basicValue)) {
                            return basicValue;
                        }
                        throw new AnalyzerException(abstractInsnNode, null, "an object reference or a return address", basicValue);
                    default:
                        return basicValue;
                }
                if (basicValue2.equals(basicValue)) {
                    return basicValue;
                }
                throw new AnalyzerException(abstractInsnNode, null, basicValue2, basicValue);
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x003d  */
    /* JADX WARN: Code duplicated, block: B:24:0x0040  */
    /* JADX WARN: Code duplicated, block: B:25:0x0043  */
    /* JADX WARN: Code duplicated, block: B:26:0x0046  */
    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue unaryOperation(AbstractInsnNode abstractInsnNode, BasicValue basicValue) throws AnalyzerException {
        BasicValue basicValueNewValue;
        int opcode = abstractInsnNode.getOpcode();
        if (opcode == 179) {
            basicValueNewValue = newValue(Type.getType(((FieldInsnNode) abstractInsnNode).desc));
        } else if (opcode == 180) {
            basicValueNewValue = newValue(Type.getObjectType(((FieldInsnNode) abstractInsnNode).owner));
        } else {
            if (opcode != 198 && opcode != 199) {
                switch (opcode) {
                    case 116:
                        basicValueNewValue = BasicValue.INT_VALUE;
                        break;
                    case 117:
                        basicValueNewValue = BasicValue.LONG_VALUE;
                        break;
                    case 118:
                        basicValueNewValue = BasicValue.FLOAT_VALUE;
                        break;
                    case 119:
                        basicValueNewValue = BasicValue.DOUBLE_VALUE;
                        break;
                    default:
                        switch (opcode) {
                            case 132:
                            case 133:
                            case 134:
                            case 135:
                            case 145:
                            case 146:
                            case 147:
                                basicValueNewValue = BasicValue.INT_VALUE;
                                break;
                            case 136:
                            case 137:
                            case 138:
                                basicValueNewValue = BasicValue.LONG_VALUE;
                                break;
                            case 139:
                            case 140:
                            case 141:
                                basicValueNewValue = BasicValue.FLOAT_VALUE;
                                break;
                            case 142:
                            case 143:
                            case 144:
                                basicValueNewValue = BasicValue.DOUBLE_VALUE;
                                break;
                            default:
                                switch (opcode) {
                                    default:
                                        switch (opcode) {
                                            case 170:
                                            case 171:
                                            case 172:
                                                break;
                                            case 173:
                                                basicValueNewValue = BasicValue.LONG_VALUE;
                                                break;
                                            case 174:
                                                basicValueNewValue = BasicValue.FLOAT_VALUE;
                                                break;
                                            case 175:
                                                basicValueNewValue = BasicValue.DOUBLE_VALUE;
                                                break;
                                            case 176:
                                                break;
                                            default:
                                                switch (opcode) {
                                                    case 188:
                                                    case 189:
                                                        break;
                                                    case 190:
                                                        if (!isArrayValue(basicValue)) {
                                                            throw new AnalyzerException(abstractInsnNode, null, "an array reference", basicValue);
                                                        }
                                                        return super.unaryOperation(abstractInsnNode, basicValue);
                                                    case 191:
                                                    case 192:
                                                    case 193:
                                                    case 194:
                                                    case 195:
                                                        break;
                                                    default:
                                                        throw new AssertionError();
                                                }
                                                break;
                                        }
                                    case 153:
                                    case 154:
                                    case 155:
                                    case 156:
                                    case 157:
                                    case 158:
                                        basicValueNewValue = BasicValue.INT_VALUE;
                                        break;
                                }
                                break;
                        }
                        break;
                }
            }
            if (!basicValue.isReference()) {
                throw new AnalyzerException(abstractInsnNode, null, "an object reference", basicValue);
            }
            return super.unaryOperation(abstractInsnNode, basicValue);
        }
        if (!isSubTypeOf(basicValue, basicValueNewValue)) {
            throw new AnalyzerException(abstractInsnNode, null, basicValueNewValue, basicValue);
        }
        return super.unaryOperation(abstractInsnNode, basicValue);
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0029  */
    /* JADX WARN: Code duplicated, block: B:14:0x002f  */
    /* JADX WARN: Code duplicated, block: B:15:0x0035  */
    /* JADX WARN: Code duplicated, block: B:16:0x003b  */
    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue binaryOperation(AbstractInsnNode abstractInsnNode, BasicValue basicValue, BasicValue basicValue2) throws AnalyzerException {
        BasicValue basicValueNewValue;
        BasicValue basicValueNewValue2;
        int opcode = abstractInsnNode.getOpcode();
        if (opcode != 181) {
            switch (opcode) {
                case 46:
                    basicValueNewValue2 = newValue(Type.getType("[I"));
                    basicValueNewValue = BasicValue.INT_VALUE;
                    break;
                case 47:
                    basicValueNewValue2 = newValue(Type.getType("[J"));
                    basicValueNewValue = BasicValue.INT_VALUE;
                    break;
                case 48:
                    basicValueNewValue2 = newValue(Type.getType("[F"));
                    basicValueNewValue = BasicValue.INT_VALUE;
                    break;
                case 49:
                    basicValueNewValue2 = newValue(Type.getType("[D"));
                    basicValueNewValue = BasicValue.INT_VALUE;
                    break;
                case 50:
                    basicValueNewValue2 = newValue(Type.getType("[Ljava/lang/Object;"));
                    basicValueNewValue = BasicValue.INT_VALUE;
                    break;
                case 51:
                    if (isSubTypeOf(basicValue, newValue(Type.getType("[Z")))) {
                        basicValueNewValue2 = newValue(Type.getType("[Z"));
                    } else {
                        basicValueNewValue2 = newValue(Type.getType("[B"));
                    }
                    basicValueNewValue = BasicValue.INT_VALUE;
                    break;
                case 52:
                    basicValueNewValue2 = newValue(Type.getType("[C"));
                    basicValueNewValue = BasicValue.INT_VALUE;
                    break;
                case 53:
                    basicValueNewValue2 = newValue(Type.getType("[S"));
                    basicValueNewValue = BasicValue.INT_VALUE;
                    break;
                default:
                    switch (opcode) {
                        case 96:
                        case 100:
                        case 104:
                        case 108:
                        case 112:
                            basicValueNewValue2 = BasicValue.INT_VALUE;
                            basicValueNewValue = BasicValue.INT_VALUE;
                            break;
                        case 97:
                        case 101:
                        case 105:
                        case 109:
                        case 113:
                            basicValueNewValue2 = BasicValue.LONG_VALUE;
                            basicValueNewValue = BasicValue.LONG_VALUE;
                            break;
                        case 98:
                        case 102:
                        case 106:
                        case 110:
                        case 114:
                            basicValueNewValue2 = BasicValue.FLOAT_VALUE;
                            basicValueNewValue = BasicValue.FLOAT_VALUE;
                            break;
                        case 99:
                        case 103:
                        case 107:
                        case 111:
                        case 115:
                            basicValueNewValue2 = BasicValue.DOUBLE_VALUE;
                            basicValueNewValue = BasicValue.DOUBLE_VALUE;
                            break;
                        default:
                            switch (opcode) {
                                case 120:
                                case 122:
                                case 124:
                                case 126:
                                case 128:
                                case 130:
                                    basicValueNewValue2 = BasicValue.INT_VALUE;
                                    basicValueNewValue = BasicValue.INT_VALUE;
                                    break;
                                case 121:
                                case 123:
                                case 125:
                                    basicValueNewValue2 = BasicValue.LONG_VALUE;
                                    basicValueNewValue = BasicValue.INT_VALUE;
                                    break;
                                case 127:
                                case 129:
                                case 131:
                                    basicValueNewValue2 = BasicValue.LONG_VALUE;
                                    basicValueNewValue = BasicValue.LONG_VALUE;
                                    break;
                                default:
                                    switch (opcode) {
                                        case 148:
                                            basicValueNewValue2 = BasicValue.LONG_VALUE;
                                            basicValueNewValue = BasicValue.LONG_VALUE;
                                            break;
                                        case 149:
                                        case 150:
                                            basicValueNewValue2 = BasicValue.FLOAT_VALUE;
                                            basicValueNewValue = BasicValue.FLOAT_VALUE;
                                            break;
                                        case 151:
                                        case 152:
                                            basicValueNewValue2 = BasicValue.DOUBLE_VALUE;
                                            basicValueNewValue = BasicValue.DOUBLE_VALUE;
                                            break;
                                        default:
                                            switch (opcode) {
                                                case 159:
                                                case 160:
                                                case 161:
                                                case 162:
                                                case 163:
                                                case 164:
                                                    basicValueNewValue2 = BasicValue.INT_VALUE;
                                                    basicValueNewValue = BasicValue.INT_VALUE;
                                                    break;
                                                case 165:
                                                case 166:
                                                    basicValueNewValue2 = BasicValue.REFERENCE_VALUE;
                                                    basicValueNewValue = BasicValue.REFERENCE_VALUE;
                                                    break;
                                                default:
                                                    throw new AssertionError();
                                            }
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            FieldInsnNode fieldInsnNode = (FieldInsnNode) abstractInsnNode;
            BasicValue basicValueNewValue3 = newValue(Type.getObjectType(fieldInsnNode.owner));
            basicValueNewValue = newValue(Type.getType(fieldInsnNode.desc));
            basicValueNewValue2 = basicValueNewValue3;
        }
        if (!isSubTypeOf(basicValue, basicValueNewValue2)) {
            throw new AnalyzerException(abstractInsnNode, "First argument", basicValueNewValue2, basicValue);
        }
        if (!isSubTypeOf(basicValue2, basicValueNewValue)) {
            throw new AnalyzerException(abstractInsnNode, "Second argument", basicValueNewValue, basicValue2);
        }
        if (abstractInsnNode.getOpcode() == 50) {
            return getElementValue(basicValue);
        }
        return super.binaryOperation(abstractInsnNode, basicValue, basicValue2);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue ternaryOperation(AbstractInsnNode abstractInsnNode, BasicValue basicValue, BasicValue basicValue2, BasicValue basicValue3) throws AnalyzerException {
        BasicValue basicValueNewValue;
        BasicValue basicValue4;
        switch (abstractInsnNode.getOpcode()) {
            case 79:
                basicValueNewValue = newValue(Type.getType("[I"));
                basicValue4 = BasicValue.INT_VALUE;
                break;
            case 80:
                basicValueNewValue = newValue(Type.getType("[J"));
                basicValue4 = BasicValue.LONG_VALUE;
                break;
            case 81:
                basicValueNewValue = newValue(Type.getType("[F"));
                basicValue4 = BasicValue.FLOAT_VALUE;
                break;
            case 82:
                basicValueNewValue = newValue(Type.getType("[D"));
                basicValue4 = BasicValue.DOUBLE_VALUE;
                break;
            case 83:
                basicValue4 = BasicValue.REFERENCE_VALUE;
                basicValueNewValue = basicValue;
                break;
            case 84:
                if (isSubTypeOf(basicValue, newValue(Type.getType("[Z")))) {
                    basicValueNewValue = newValue(Type.getType("[Z"));
                } else {
                    basicValueNewValue = newValue(Type.getType("[B"));
                }
                basicValue4 = BasicValue.INT_VALUE;
                break;
            case 85:
                basicValueNewValue = newValue(Type.getType("[C"));
                basicValue4 = BasicValue.INT_VALUE;
                break;
            case 86:
                basicValueNewValue = newValue(Type.getType("[S"));
                basicValue4 = BasicValue.INT_VALUE;
                break;
            default:
                throw new AssertionError();
        }
        if (!isSubTypeOf(basicValue, basicValueNewValue)) {
            throw new AnalyzerException(abstractInsnNode, "First argument", "a " + basicValueNewValue + " array reference", basicValue);
        }
        if (!BasicValue.INT_VALUE.equals(basicValue2)) {
            throw new AnalyzerException(abstractInsnNode, "Second argument", BasicValue.INT_VALUE, basicValue2);
        }
        if (isSubTypeOf(basicValue3, basicValue4)) {
            return null;
        }
        throw new AnalyzerException(abstractInsnNode, "Third argument", basicValue4, basicValue3);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public BasicValue naryOperation(AbstractInsnNode abstractInsnNode, List<? extends BasicValue> list) throws AnalyzerException {
        int i;
        int opcode = abstractInsnNode.getOpcode();
        if (opcode == 197) {
            for (BasicValue basicValue : list) {
                if (!BasicValue.INT_VALUE.equals(basicValue)) {
                    throw new AnalyzerException(abstractInsnNode, null, BasicValue.INT_VALUE, basicValue);
                }
            }
        } else {
            int i2 = 0;
            if (opcode == 184 || opcode == 186) {
                i = 0;
            } else {
                Type objectType = Type.getObjectType(((MethodInsnNode) abstractInsnNode).owner);
                if (!isSubTypeOf(list.get(0), newValue(objectType))) {
                    throw new AnalyzerException(abstractInsnNode, "Method owner", newValue(objectType), list.get(0));
                }
                i = 1;
            }
            Type[] argumentTypes = Type.getArgumentTypes(opcode == 186 ? ((InvokeDynamicInsnNode) abstractInsnNode).desc : ((MethodInsnNode) abstractInsnNode).desc);
            while (i < list.size()) {
                int i3 = i2 + 1;
                BasicValue basicValueNewValue = newValue(argumentTypes[i2]);
                int i4 = i + 1;
                BasicValue basicValue2 = list.get(i);
                if (!isSubTypeOf(basicValue2, basicValueNewValue)) {
                    throw new AnalyzerException(abstractInsnNode, "Argument " + i3, basicValueNewValue, basicValue2);
                }
                i2 = i3;
                i = i4;
            }
        }
        return super.naryOperation(abstractInsnNode, list);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.BasicInterpreter, org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public void returnOperation(AbstractInsnNode abstractInsnNode, BasicValue basicValue, BasicValue basicValue2) throws AnalyzerException {
        if (!isSubTypeOf(basicValue, basicValue2)) {
            throw new AnalyzerException(abstractInsnNode, "Incompatible return type", basicValue2, basicValue);
        }
    }

    protected boolean isArrayValue(BasicValue basicValue) {
        return basicValue.isReference();
    }

    protected BasicValue getElementValue(BasicValue basicValue) throws AnalyzerException {
        return BasicValue.REFERENCE_VALUE;
    }

    protected boolean isSubTypeOf(BasicValue basicValue, BasicValue basicValue2) {
        return basicValue.equals(basicValue2);
    }
}
