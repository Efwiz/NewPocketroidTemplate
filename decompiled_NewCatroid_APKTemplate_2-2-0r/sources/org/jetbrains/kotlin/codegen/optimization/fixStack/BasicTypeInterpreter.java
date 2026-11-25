package org.jetbrains.kotlin.codegen.optimization.fixStack;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.value.CompoundEntry;
import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Handle;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.IntInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.jetbrains.org.objectweb.asm.tree.TypeInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: BasicTypeInterpreter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\r\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0015\u0010\u0005\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0007J\u0015\u0010\u0006\u001a\u00028\u00002\u0006\u0010\b\u001a\u00020\tH$¢\u0006\u0002\u0010\nJ'\u0010\u000b\u001a\u0004\u0018\u00018\u00002\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u000f\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0010J\r\u0010\u0011\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0012J\r\u0010\u0013\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0012J\r\u0010\u0014\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0012J\r\u0010\u0015\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0012J\r\u0010\u0016\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0012J\u0015\u0010\u0017\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0019H$¢\u0006\u0002\u0010\u001aJ\r\u0010\u001b\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0012J\r\u0010\u001c\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0012J\u0015\u0010\u001d\u001a\u00028\u00002\u0006\u0010\b\u001a\u00020\tH$¢\u0006\u0002\u0010\nJ%\u0010\u001e\u001a\u0004\u0018\u00018\u00002\u0006\u0010\f\u001a\u00020\r2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00000 H\u0016¢\u0006\u0002\u0010!J\u0017\u0010\"\u001a\u0004\u0018\u00018\u00002\u0006\u0010\f\u001a\u00020\rH\u0016¢\u0006\u0002\u0010#J\u0019\u0010$\u001a\u0004\u0018\u00018\u00002\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016¢\u0006\u0002\u0010\nJ\r\u0010%\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0012J\u0015\u0010&\u001a\u00028\u00002\u0006\u0010\b\u001a\u00020\tH$¢\u0006\u0002\u0010\nJ)\u0010'\u001a\u00020(2\u0006\u0010\f\u001a\u00020\r2\b\u0010)\u001a\u0004\u0018\u00018\u00002\b\u0010*\u001a\u0004\u0018\u00018\u0000H\u0016¢\u0006\u0002\u0010+J\r\u0010,\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0012J/\u0010-\u001a\u0004\u0018\u00018\u00002\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u000f\u001a\u00028\u00002\u0006\u0010.\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010/J\u0015\u00100\u001a\u00028\u00002\u0006\u00101\u001a\u00020\tH$¢\u0006\u0002\u0010\nJ\u001f\u00102\u001a\u0004\u0018\u00018\u00002\u0006\u0010\f\u001a\u00020\r2\u0006\u0010)\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00103J\r\u00104\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0012¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/fixStack/BasicTypeInterpreter;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Interpreter;", "()V", "aaLoadValue", "arrayValue", "(Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;)Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/org/objectweb/asm/Type;)Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", "binaryOperation", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "value1", "value2", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;)Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", "booleanValue", "()Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", "byteValue", "charValue", "doubleValue", "floatValue", "handleValue", "handle", "Lorg/jetbrains/org/objectweb/asm/Handle;", "(Lorg/jetbrains/org/objectweb/asm/Handle;)Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", "intValue", "longValue", "methodValue", "naryOperation", CompoundEntry.NAME_values, "", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Ljava/util/List;)Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", "newOperation", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", "newValue", "nullValue", "objectValue", "returnOperation", "", "value", "expected", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;)V", "shortValue", "ternaryOperation", "value3", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;)Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", "typeConstValue", "typeConst", "unaryOperation", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;)Lorg/jetbrains/org/objectweb/asm/tree/analysis/Value;", "uninitializedValue", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class BasicTypeInterpreter<V extends Value> extends Interpreter<V> {
    protected abstract V aaLoadValue(V arrayValue);

    protected abstract V arrayValue(Type type);

    protected abstract V booleanValue();

    protected abstract V byteValue();

    protected abstract V charValue();

    protected abstract V doubleValue();

    protected abstract V floatValue();

    protected abstract V handleValue(Handle handle);

    protected abstract V intValue();

    protected abstract V longValue();

    protected abstract V methodValue(Type type);

    protected abstract V nullValue();

    protected abstract V objectValue(Type type);

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public void returnOperation(AbstractInsnNode insn, V value, V expected) {
        Intrinsics.checkNotNullParameter(insn, "insn");
    }

    protected abstract V shortValue();

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public V ternaryOperation(AbstractInsnNode insn, V value1, V value2, V value3) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value1, "value1");
        Intrinsics.checkNotNullParameter(value2, "value2");
        Intrinsics.checkNotNullParameter(value3, "value3");
        return null;
    }

    protected abstract V typeConstValue(Type typeConst);

    protected abstract V uninitializedValue();

    public BasicTypeInterpreter() {
        super(589824);
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public V newValue(Type type) {
        if (type == null) {
            return (V) uninitializedValue();
        }
        switch (type.getSort()) {
            case 0:
                return null;
            case 1:
                return (V) booleanValue();
            case 2:
                return (V) charValue();
            case 3:
                return (V) byteValue();
            case 4:
                return (V) shortValue();
            case 5:
                return (V) intValue();
            case 6:
                return (V) floatValue();
            case 7:
                return (V) longValue();
            case 8:
                return (V) doubleValue();
            case 9:
                return (V) arrayValue(type);
            case 10:
                return (V) objectValue(type);
            case 11:
                return (V) methodValue(type);
            default:
                throw new AssertionError("Unexpected type: " + type);
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public V newOperation(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        int opcode = insn.getOpcode();
        if (opcode == 178) {
            return (V) newValue(Type.getType(((FieldInsnNode) insn).desc));
        }
        if (opcode != 187) {
            switch (opcode) {
                case 1:
                    return (V) nullValue();
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    return (V) intValue();
                case 9:
                case 10:
                    return (V) longValue();
                case 11:
                case 12:
                case 13:
                    return (V) floatValue();
                case 14:
                case 15:
                    return (V) doubleValue();
                case 16:
                case 17:
                    return (V) intValue();
                case 18:
                    Object obj = ((LdcInsnNode) insn).cst;
                    if (obj instanceof Integer) {
                        return (V) intValue();
                    }
                    if (obj instanceof Float) {
                        return (V) floatValue();
                    }
                    if (obj instanceof Long) {
                        return (V) longValue();
                    }
                    if (obj instanceof Double) {
                        return (V) doubleValue();
                    }
                    if (obj instanceof String) {
                        Type JAVA_STRING_TYPE = AsmTypes.JAVA_STRING_TYPE;
                        Intrinsics.checkNotNullExpressionValue(JAVA_STRING_TYPE, "JAVA_STRING_TYPE");
                        return (V) objectValue(JAVA_STRING_TYPE);
                    }
                    if (obj instanceof Handle) {
                        return (V) handleValue((Handle) obj);
                    }
                    if (obj instanceof Type) {
                        return (V) typeConstValue((Type) obj);
                    }
                    throw new IllegalArgumentException("Illegal LDC constant " + obj);
                default:
                    throw new IllegalArgumentException("Unexpected instruction: " + InlineCodegenUtilsKt.getInsnOpcodeText(insn));
            }
        }
        return (V) newValue(Type.getObjectType(((TypeInsnNode) insn).desc));
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0049  */
    /* JADX WARN: Code duplicated, block: B:14:0x004e  */
    /* JADX WARN: Code duplicated, block: B:15:0x0053  */
    /* JADX WARN: Code duplicated, block: B:16:0x0058  */
    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public V binaryOperation(AbstractInsnNode insn, V value1, V value2) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value1, "value1");
        Intrinsics.checkNotNullParameter(value2, "value2");
        int opcode = insn.getOpcode();
        if (opcode != 181) {
            switch (opcode) {
                case 46:
                case 51:
                case 52:
                case 53:
                    return (V) intValue();
                case 47:
                    return (V) longValue();
                case 48:
                    return (V) floatValue();
                case 49:
                    return (V) doubleValue();
                case 50:
                    return (V) aaLoadValue(value1);
                default:
                    switch (opcode) {
                        case 96:
                        case 100:
                        case 104:
                        case 108:
                        case 112:
                            return (V) intValue();
                        case 97:
                        case 101:
                        case 105:
                        case 109:
                        case 113:
                            return (V) longValue();
                        case 98:
                        case 102:
                        case 106:
                        case 110:
                        case 114:
                            return (V) floatValue();
                        case 99:
                        case 103:
                        case 107:
                        case 111:
                        case 115:
                            return (V) doubleValue();
                        default:
                            switch (opcode) {
                                case 120:
                                case 122:
                                case 124:
                                case 126:
                                case 128:
                                case 130:
                                    return (V) intValue();
                                case 121:
                                case 123:
                                case 125:
                                case 127:
                                case 129:
                                case 131:
                                    return (V) longValue();
                                default:
                                    switch (opcode) {
                                        case 148:
                                        case 149:
                                        case 150:
                                        case 151:
                                        case 152:
                                            return (V) intValue();
                                        default:
                                            switch (opcode) {
                                                case 159:
                                                case 160:
                                                case 161:
                                                case 162:
                                                case 163:
                                                case 164:
                                                case 165:
                                                case 166:
                                                    break;
                                                default:
                                                    throw new IllegalArgumentException("Unexpected instruction: " + InlineCodegenUtilsKt.getInsnOpcodeText(insn));
                                            }
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }
        return null;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public V naryOperation(AbstractInsnNode insn, List<? extends V> values) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(values, "values");
        int opcode = insn.getOpcode();
        if (opcode == 186) {
            return (V) newValue(Type.getReturnType(((InvokeDynamicInsnNode) insn).desc));
        }
        if (opcode == 197) {
            return (V) newValue(Type.getType(((MultiANewArrayInsnNode) insn).desc));
        }
        return (V) newValue(Type.getReturnType(((MethodInsnNode) insn).desc));
    }

    /* JADX WARN: Code duplicated, block: B:33:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:34:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:35:0x00f5  */
    /* JADX WARN: Code duplicated, block: B:36:0x00fa  */
    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public V unaryOperation(AbstractInsnNode insn, V value) throws AnalyzerException {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value, "value");
        int opcode = insn.getOpcode();
        if (opcode == 179) {
            return null;
        }
        if (opcode == 180) {
            return (V) newValue(Type.getType(((FieldInsnNode) insn).desc));
        }
        if (opcode == 198 || opcode == 199) {
            return null;
        }
        switch (opcode) {
            case 116:
                return (V) intValue();
            case 117:
                return (V) longValue();
            case 118:
                return (V) floatValue();
            case 119:
                return (V) doubleValue();
            default:
                switch (opcode) {
                    case 132:
                    case 136:
                    case 139:
                    case 142:
                    case 145:
                    case 146:
                    case 147:
                        return (V) intValue();
                    case 133:
                    case 140:
                    case 143:
                        return (V) longValue();
                    case 134:
                    case 137:
                    case 144:
                        return (V) floatValue();
                    case 135:
                    case 138:
                    case 141:
                        return (V) doubleValue();
                    default:
                        switch (opcode) {
                            case 153:
                            case 154:
                            case 155:
                            case 156:
                            case 157:
                            case 158:
                                return null;
                            default:
                                switch (opcode) {
                                    case 170:
                                    case 171:
                                    case 172:
                                    case 173:
                                    case 174:
                                    case 175:
                                    case 176:
                                        return null;
                                    default:
                                        switch (opcode) {
                                            case 188:
                                                switch (((IntInsnNode) insn).operand) {
                                                    case 4:
                                                        return (V) newValue(Type.getType("[Z"));
                                                    case 5:
                                                        return (V) newValue(Type.getType("[C"));
                                                    case 6:
                                                        return (V) newValue(Type.getType("[F"));
                                                    case 7:
                                                        return (V) newValue(Type.getType("[D"));
                                                    case 8:
                                                        return (V) newValue(Type.getType("[B"));
                                                    case 9:
                                                        return (V) newValue(Type.getType("[S"));
                                                    case 10:
                                                        return (V) newValue(Type.getType("[I"));
                                                    case 11:
                                                        return (V) newValue(Type.getType("[J"));
                                                    default:
                                                        throw new AnalyzerException(insn, "Invalid array type");
                                                }
                                            case 189:
                                                return (V) newValue(Type.getType("[" + Type.getObjectType(((TypeInsnNode) insn).desc)));
                                            case 190:
                                                return (V) intValue();
                                            case 191:
                                            case 194:
                                            case 195:
                                                return null;
                                            case 192:
                                                return (V) newValue(Type.getObjectType(((TypeInsnNode) insn).desc));
                                            case 193:
                                                return (V) intValue();
                                            default:
                                                throw new IllegalArgumentException("Unexpected instruction: " + InlineCodegenUtilsKt.getInsnOpcodeText(insn));
                                        }
                                }
                        }
                }
        }
    }
}
