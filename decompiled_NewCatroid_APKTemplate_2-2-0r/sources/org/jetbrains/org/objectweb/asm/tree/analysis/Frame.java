package org.jetbrains.org.objectweb.asm.tree.analysis;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.IincInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: loaded from: classes6.dex */
public class Frame<V extends Value> {
    private int numLocals;
    private int numStack;
    private V returnValue;
    private V[] values;

    public void initJumpTarget(int i, LabelNode labelNode) {
    }

    public Frame(int i, int i2) {
        this.values = (V[]) new Value[i2 + i];
        this.numLocals = i;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Frame(Frame<? extends V> frame) {
        int i = frame.numLocals;
        this(i, frame.values.length - i);
        init(frame);
    }

    public Frame<V> init(Frame<? extends V> frame) {
        this.returnValue = frame.returnValue;
        V[] vArr = frame.values;
        V[] vArr2 = this.values;
        System.arraycopy(vArr, 0, vArr2, 0, vArr2.length);
        this.numStack = frame.numStack;
        return this;
    }

    public void setReturn(V v) {
        this.returnValue = v;
    }

    public int getLocals() {
        return this.numLocals;
    }

    public int getMaxStackSize() {
        return this.values.length - this.numLocals;
    }

    public V getLocal(int i) {
        if (i >= this.numLocals) {
            throw new IndexOutOfBoundsException("Trying to get an inexistant local variable " + i);
        }
        return this.values[i];
    }

    public void setLocal(int i, V v) {
        if (i >= this.numLocals) {
            throw new IndexOutOfBoundsException("Trying to set an inexistant local variable " + i);
        }
        this.values[i] = v;
    }

    public int getStackSize() {
        return this.numStack;
    }

    public V getStack(int i) {
        return this.values[this.numLocals + i];
    }

    public void setStack(int i, V v) {
        this.values[this.numLocals + i] = v;
    }

    public void clearStack() {
        this.numStack = 0;
    }

    public V pop() {
        int i = this.numStack;
        if (i == 0) {
            throw new IndexOutOfBoundsException("Cannot pop operand off an empty stack.");
        }
        V[] vArr = this.values;
        int i2 = this.numLocals;
        int i3 = i - 1;
        this.numStack = i3;
        return vArr[i2 + i3];
    }

    public void push(V v) {
        int i = this.numLocals;
        int i2 = this.numStack;
        int i3 = i + i2;
        V[] vArr = this.values;
        if (i3 >= vArr.length) {
            throw new IndexOutOfBoundsException("Insufficient maximum stack size.");
        }
        this.numStack = i2 + 1;
        vArr[i + i2] = v;
    }

    public void execute(AbstractInsnNode abstractInsnNode, Interpreter<V> interpreter) throws AnalyzerException {
        int i;
        Value local;
        int opcode = abstractInsnNode.getOpcode();
        switch (opcode) {
            case 0:
                return;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                push(interpreter.newOperation(abstractInsnNode));
                return;
            default:
                switch (opcode) {
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                        push(interpreter.copyOperation(abstractInsnNode, getLocal(((VarInsnNode) abstractInsnNode).var)));
                        return;
                    default:
                        switch (opcode) {
                            case 46:
                            case 47:
                            case 48:
                            case 49:
                            case 50:
                            case 51:
                            case 52:
                            case 53:
                                break;
                            case 54:
                            case 55:
                            case 56:
                            case 57:
                            case 58:
                                Value valueCopyOperation = interpreter.copyOperation(abstractInsnNode, pop());
                                int i2 = ((VarInsnNode) abstractInsnNode).var;
                                setLocal(i2, valueCopyOperation);
                                if (valueCopyOperation.get_size() == 2) {
                                    int i3 = i2 + 1;
                                    setLocal(i3, interpreter.newEmptyValue(i3));
                                }
                                if (i2 <= 0 || (local = getLocal((i = i2 - 1))) == null || local.get_size() != 2) {
                                    return;
                                }
                                setLocal(i, interpreter.newEmptyValue(i));
                                return;
                            default:
                                switch (opcode) {
                                    case 79:
                                    case 80:
                                    case 81:
                                    case 82:
                                    case 83:
                                    case 84:
                                    case 85:
                                    case 86:
                                        interpreter.ternaryOperation(abstractInsnNode, pop(), pop(), pop());
                                        return;
                                    case 87:
                                        if (pop().get_size() == 2) {
                                            throw new AnalyzerException(abstractInsnNode, "Illegal use of POP");
                                        }
                                        return;
                                    case 88:
                                        if (pop().get_size() == 1 && pop().get_size() != 1) {
                                            throw new AnalyzerException(abstractInsnNode, "Illegal use of POP2");
                                        }
                                        return;
                                    case 89:
                                        Value valuePop = pop();
                                        if (valuePop.get_size() != 1) {
                                            throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP");
                                        }
                                        push(valuePop);
                                        push(interpreter.copyOperation(abstractInsnNode, valuePop));
                                        return;
                                    case 90:
                                        Value valuePop2 = pop();
                                        Value valuePop3 = pop();
                                        if (valuePop2.get_size() != 1 || valuePop3.get_size() != 1) {
                                            throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP_X1");
                                        }
                                        push(interpreter.copyOperation(abstractInsnNode, valuePop2));
                                        push(valuePop3);
                                        push(valuePop2);
                                        return;
                                    case 91:
                                        Value valuePop4 = pop();
                                        if (valuePop4.get_size() != 1 || !executeDupX2(abstractInsnNode, valuePop4, interpreter)) {
                                            throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP_X2");
                                        }
                                        return;
                                    case 92:
                                        Value valuePop5 = pop();
                                        if (valuePop5.get_size() == 1) {
                                            Value valuePop6 = pop();
                                            if (valuePop6.get_size() == 1) {
                                                push(valuePop6);
                                                push(valuePop5);
                                                push(interpreter.copyOperation(abstractInsnNode, valuePop6));
                                                push(interpreter.copyOperation(abstractInsnNode, valuePop5));
                                                return;
                                            }
                                            throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP2");
                                        }
                                        push(valuePop5);
                                        push(interpreter.copyOperation(abstractInsnNode, valuePop5));
                                        return;
                                    case 93:
                                        Value valuePop7 = pop();
                                        if (valuePop7.get_size() == 1) {
                                            Value valuePop8 = pop();
                                            if (valuePop8.get_size() == 1) {
                                                Value valuePop9 = pop();
                                                if (valuePop9.get_size() == 1) {
                                                    push(interpreter.copyOperation(abstractInsnNode, valuePop8));
                                                    push(interpreter.copyOperation(abstractInsnNode, valuePop7));
                                                    push(valuePop9);
                                                    push(valuePop8);
                                                    push(valuePop7);
                                                    return;
                                                }
                                            }
                                        } else {
                                            Value valuePop10 = pop();
                                            if (valuePop10.get_size() == 1) {
                                                push(interpreter.copyOperation(abstractInsnNode, valuePop7));
                                                push(valuePop10);
                                                push(valuePop7);
                                                return;
                                            }
                                        }
                                        throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP2_X1");
                                    case 94:
                                        Value valuePop11 = pop();
                                        if (valuePop11.get_size() == 1) {
                                            Value valuePop12 = pop();
                                            if (valuePop12.get_size() == 1) {
                                                Value valuePop13 = pop();
                                                if (valuePop13.get_size() == 1) {
                                                    Value valuePop14 = pop();
                                                    if (valuePop14.get_size() == 1) {
                                                        push(interpreter.copyOperation(abstractInsnNode, valuePop12));
                                                        push(interpreter.copyOperation(abstractInsnNode, valuePop11));
                                                        push(valuePop14);
                                                        push(valuePop13);
                                                        push(valuePop12);
                                                        push(valuePop11);
                                                        return;
                                                    }
                                                } else {
                                                    push(interpreter.copyOperation(abstractInsnNode, valuePop12));
                                                    push(interpreter.copyOperation(abstractInsnNode, valuePop11));
                                                    push(valuePop13);
                                                    push(valuePop12);
                                                    push(valuePop11);
                                                    return;
                                                }
                                            }
                                        } else if (executeDupX2(abstractInsnNode, valuePop11, interpreter)) {
                                            return;
                                        }
                                        throw new AnalyzerException(abstractInsnNode, "Illegal use of DUP2_X2");
                                    case 95:
                                        Value valuePop15 = pop();
                                        Value valuePop16 = pop();
                                        if (valuePop16.get_size() != 1 || valuePop15.get_size() != 1) {
                                            throw new AnalyzerException(abstractInsnNode, "Illegal use of SWAP");
                                        }
                                        push(interpreter.copyOperation(abstractInsnNode, valuePop15));
                                        push(interpreter.copyOperation(abstractInsnNode, valuePop16));
                                        return;
                                    case 96:
                                    case 97:
                                    case 98:
                                    case 99:
                                    case 100:
                                    case 101:
                                    case 102:
                                    case 103:
                                    case 104:
                                    case 105:
                                    case 106:
                                    case 107:
                                    case 108:
                                    case 109:
                                    case 110:
                                    case 111:
                                    case 112:
                                    case 113:
                                    case 114:
                                    case 115:
                                    case 120:
                                    case 121:
                                    case 122:
                                    case 123:
                                    case 124:
                                    case 125:
                                    case 126:
                                    case 127:
                                    case 128:
                                    case 129:
                                    case 130:
                                    case 131:
                                    case 148:
                                    case 149:
                                    case 150:
                                    case 151:
                                    case 152:
                                        break;
                                    case 116:
                                    case 117:
                                    case 118:
                                    case 119:
                                        push(interpreter.unaryOperation(abstractInsnNode, pop()));
                                        return;
                                    case 132:
                                        int i4 = ((IincInsnNode) abstractInsnNode).var;
                                        setLocal(i4, interpreter.unaryOperation(abstractInsnNode, getLocal(i4)));
                                        return;
                                    case 133:
                                    case 134:
                                    case 135:
                                    case 136:
                                    case 137:
                                    case 138:
                                    case 139:
                                    case 140:
                                    case 141:
                                    case 142:
                                    case 143:
                                    case 144:
                                    case 145:
                                    case 146:
                                    case 147:
                                        push(interpreter.unaryOperation(abstractInsnNode, pop()));
                                        return;
                                    case 153:
                                    case 154:
                                    case 155:
                                    case 156:
                                    case 157:
                                    case 158:
                                        interpreter.unaryOperation(abstractInsnNode, pop());
                                        return;
                                    case 159:
                                    case 160:
                                    case 161:
                                    case 162:
                                    case 163:
                                    case 164:
                                    case 165:
                                    case 166:
                                    case 181:
                                        interpreter.binaryOperation(abstractInsnNode, pop(), pop());
                                        return;
                                    case 167:
                                    case 169:
                                        return;
                                    case 168:
                                        push(interpreter.newOperation(abstractInsnNode));
                                        return;
                                    case 170:
                                    case 171:
                                        interpreter.unaryOperation(abstractInsnNode, pop());
                                        return;
                                    case 172:
                                    case 173:
                                    case 174:
                                    case 175:
                                    case 176:
                                        Value valuePop17 = pop();
                                        interpreter.unaryOperation(abstractInsnNode, valuePop17);
                                        interpreter.returnOperation(abstractInsnNode, valuePop17, this.returnValue);
                                        return;
                                    case 177:
                                        if (this.returnValue != null) {
                                            throw new AnalyzerException(abstractInsnNode, "Incompatible return type");
                                        }
                                        return;
                                    case 178:
                                        push(interpreter.newOperation(abstractInsnNode));
                                        return;
                                    case 179:
                                        interpreter.unaryOperation(abstractInsnNode, pop());
                                        return;
                                    case 180:
                                        push(interpreter.unaryOperation(abstractInsnNode, pop()));
                                        return;
                                    case 182:
                                    case 183:
                                    case 184:
                                    case 185:
                                        executeInvokeInsn(abstractInsnNode, ((MethodInsnNode) abstractInsnNode).desc, interpreter);
                                        return;
                                    case 186:
                                        executeInvokeInsn(abstractInsnNode, ((InvokeDynamicInsnNode) abstractInsnNode).desc, interpreter);
                                        return;
                                    case 187:
                                        push(interpreter.newOperation(abstractInsnNode));
                                        return;
                                    case 188:
                                    case 189:
                                    case 190:
                                        push(interpreter.unaryOperation(abstractInsnNode, pop()));
                                        return;
                                    case 191:
                                        interpreter.unaryOperation(abstractInsnNode, pop());
                                        return;
                                    case 192:
                                    case 193:
                                        push(interpreter.unaryOperation(abstractInsnNode, pop()));
                                        return;
                                    case 194:
                                    case 195:
                                        interpreter.unaryOperation(abstractInsnNode, pop());
                                        return;
                                    default:
                                        switch (opcode) {
                                            case 197:
                                                ArrayList arrayList = new ArrayList();
                                                for (int i5 = ((MultiANewArrayInsnNode) abstractInsnNode).dims; i5 > 0; i5--) {
                                                    arrayList.add(0, pop());
                                                }
                                                push(interpreter.naryOperation(abstractInsnNode, arrayList));
                                                return;
                                            case 198:
                                            case 199:
                                                interpreter.unaryOperation(abstractInsnNode, pop());
                                                return;
                                            default:
                                                throw new AnalyzerException(abstractInsnNode, "Illegal opcode " + abstractInsnNode.getOpcode());
                                        }
                                }
                                break;
                        }
                        push(interpreter.binaryOperation(abstractInsnNode, pop(), pop()));
                        return;
                }
        }
    }

    private boolean executeDupX2(AbstractInsnNode abstractInsnNode, V v, Interpreter<V> interpreter) throws AnalyzerException {
        Value valuePop = pop();
        if (valuePop.get_size() == 1) {
            Value valuePop2 = pop();
            if (valuePop2.get_size() != 1) {
                return false;
            }
            push(interpreter.copyOperation(abstractInsnNode, v));
            push(valuePop2);
            push(valuePop);
            push(v);
            return true;
        }
        push(interpreter.copyOperation(abstractInsnNode, v));
        push(valuePop);
        push(v);
        return true;
    }

    private void executeInvokeInsn(AbstractInsnNode abstractInsnNode, String str, Interpreter<V> interpreter) throws AnalyzerException {
        ArrayList arrayList = new ArrayList();
        for (int length = Type.getArgumentTypes(str).length; length > 0; length--) {
            arrayList.add(0, pop());
        }
        if (abstractInsnNode.getOpcode() != 184 && abstractInsnNode.getOpcode() != 186) {
            arrayList.add(0, pop());
        }
        if (Type.getReturnType(str) == Type.VOID_TYPE) {
            interpreter.naryOperation(abstractInsnNode, arrayList);
        } else {
            push(interpreter.naryOperation(abstractInsnNode, arrayList));
        }
    }

    public boolean merge(Frame<? extends V> frame, Interpreter<V> interpreter) throws AnalyzerException {
        if (this.numStack != frame.numStack) {
            throw new AnalyzerException(null, "Incompatible stack heights");
        }
        boolean z = false;
        for (int i = 0; i < this.numLocals + this.numStack; i++) {
            Value valueMerge = interpreter.merge(this.values[i], frame.values[i]);
            if (!valueMerge.equals(this.values[i])) {
                ((V[]) this.values)[i] = valueMerge;
                z = true;
            }
        }
        return z;
    }

    public boolean merge(Frame<? extends V> frame, boolean[] zArr) {
        boolean z = false;
        for (int i = 0; i < this.numLocals; i++) {
            if (!zArr[i] && !this.values[i].equals(frame.values[i])) {
                this.values[i] = frame.values[i];
                z = true;
            }
        }
        return z;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getLocals(); i++) {
            sb.append(getLocal(i));
        }
        sb.append(StringUtil.SPACE);
        for (int i2 = 0; i2 < getStackSize(); i2++) {
            sb.append(getStack(i2).toString());
        }
        return sb.toString();
    }
}
