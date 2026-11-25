package com.android.tools.r8.internal;

import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface YB {
    static String a(int i) {
        if (i == 18) {
            return "LDC";
        }
        if (i == 170) {
            return "TABLESWITCH";
        }
        if (i == 171) {
            return "LOOKUPSWITCH";
        }
        switch (i) {
            case 1:
                return "ACONST_NULL";
            case 2:
                return "ICONST_M1";
            case 3:
                return "ICONST_0";
            case 4:
                return "ICONST_1";
            case 5:
                return "ICONST_2";
            case 6:
                return "ICONST_3";
            case 7:
                return "ICONST_4";
            case 8:
                return "ICONST_5";
            case 9:
                return "LCONST_0";
            case 10:
                return "LCONST_1";
            case 11:
                return "FCONST_0";
            case 12:
                return "FCONST_1";
            case 13:
                return "FCONST_2";
            case 14:
                return "DCONST_0";
            case 15:
                return "DCONST_1";
            default:
                switch (i) {
                    case 46:
                        return "IALOAD";
                    case 47:
                        return "LALOAD";
                    case 48:
                        return "FALOAD";
                    case 49:
                        return "DALOAD";
                    case 50:
                        return "AALOAD";
                    case 51:
                        return "BALOAD";
                    case 52:
                        return "CALOAD";
                    case 53:
                        return "SALOAD";
                    default:
                        switch (i) {
                            case 79:
                                return "IASTORE";
                            case 80:
                                return "LASTORE";
                            case 81:
                                return "FASTORE";
                            case 82:
                                return "DASTORE";
                            case 83:
                                return "AASTORE";
                            case 84:
                                return "BASTORE";
                            case 85:
                                return "CASTORE";
                            case 86:
                                return "SASTORE";
                            default:
                                switch (i) {
                                    case 96:
                                        return "IADD";
                                    case 97:
                                        return "LADD";
                                    case 98:
                                        return "FADD";
                                    case 99:
                                        return "DADD";
                                    case 100:
                                        return "ISUB";
                                    case 101:
                                        return "LSUB";
                                    case 102:
                                        return "FSUB";
                                    case 103:
                                        return "DSUB";
                                    case 104:
                                        return "IMUL";
                                    case 105:
                                        return "LMUL";
                                    case 106:
                                        return "FMUL";
                                    case 107:
                                        return "DMUL";
                                    case 108:
                                        return "IDIV";
                                    case 109:
                                        return "LDIV";
                                    case 110:
                                        return "FDIV";
                                    case 111:
                                        return "DDIV";
                                    case 112:
                                        return "IREM";
                                    case 113:
                                        return "LREM";
                                    case 114:
                                        return "FREM";
                                    case 115:
                                        return "DREM";
                                    case 116:
                                        return "INEG";
                                    case 117:
                                        return "LNEG";
                                    case 118:
                                        return "FNEG";
                                    case 119:
                                        return "DNEG";
                                    case 120:
                                        return "ISHL";
                                    case 121:
                                        return "LSHL";
                                    case 122:
                                        return "ISHR";
                                    case 123:
                                        return "LSHR";
                                    case 124:
                                        return "IUSHR";
                                    case 125:
                                        return "LUSHR";
                                    case 126:
                                        return "IAND";
                                    case 127:
                                        return "LAND";
                                    case 128:
                                        return PBRFloatAttribute.IORAlias;
                                    case 129:
                                        return "LOR";
                                    case 130:
                                        return "IXOR";
                                    case 131:
                                        return "LXOR";
                                    default:
                                        switch (i) {
                                            case 133:
                                                return "I2L";
                                            case 134:
                                                return "I2F";
                                            case 135:
                                                return "I2D";
                                            case 136:
                                                return "L2I";
                                            case 137:
                                                return "L2F";
                                            case 138:
                                                return "L2D";
                                            case 139:
                                                return "F2I";
                                            case 140:
                                                return "F2L";
                                            case 141:
                                                return "F2D";
                                            case 142:
                                                return "D2I";
                                            case 143:
                                                return "D2L";
                                            case 144:
                                                return "D2F";
                                            case 145:
                                                return "I2B";
                                            case 146:
                                                return "I2C";
                                            case 147:
                                                return "I2S";
                                            case 148:
                                                return "LCMP";
                                            case 149:
                                                return "FCMPL";
                                            case 150:
                                                return "FCMPG";
                                            case 151:
                                                return "DCMPL";
                                            case 152:
                                                return "DCMPG";
                                            case 153:
                                                return "IFEQ";
                                            case 154:
                                                return "IFNE";
                                            case 155:
                                                return "IFLT";
                                            case 156:
                                                return "IFGE";
                                            case 157:
                                                return "IFGT";
                                            case 158:
                                                return "IFLE";
                                            case 159:
                                                return "IF_ICMPEQ";
                                            case 160:
                                                return "IF_ICMPNE";
                                            case 161:
                                                return "IF_ICMPLT";
                                            case 162:
                                                return "IF_ICMPGE";
                                            case 163:
                                                return "IF_ICMPGT";
                                            case 164:
                                                return "IF_ICMPLE";
                                            case 165:
                                                return "IF_ACMPEQ";
                                            case 166:
                                                return "IF_ACMPNE";
                                            case 167:
                                                return "GOTO";
                                            default:
                                                switch (i) {
                                                    case 176:
                                                        return "ARETURN";
                                                    case 177:
                                                        return "RETURN";
                                                    case 178:
                                                        return "GETSTATIC";
                                                    case 179:
                                                        return "PUTSTATIC";
                                                    case 180:
                                                        return "GETFIELD";
                                                    case 181:
                                                        return "PUTFIELD";
                                                    case 182:
                                                        return "INVOKEVIRTUAL";
                                                    case 183:
                                                        return "INVOKESPECIAL";
                                                    case 184:
                                                        return "INVOKESTATIC";
                                                    case 185:
                                                        return "INVOKEINTERFACE";
                                                    case 186:
                                                        return "INVOKEDYNAMIC";
                                                    case 187:
                                                        return "NEW";
                                                    case 188:
                                                        return "NEWARRAY";
                                                    default:
                                                        switch (i) {
                                                            case 190:
                                                                return "ARRAYLENGTH";
                                                            case 191:
                                                                return "ATHROW";
                                                            case 192:
                                                                return "CHECKCAST";
                                                            case 193:
                                                                return "INSTANCEOF";
                                                            case 194:
                                                                return "MONITORENTER";
                                                            case 195:
                                                                return "MONITOREXIT";
                                                            default:
                                                                switch (i) {
                                                                    case 197:
                                                                        return "MULTIANEWARRAY";
                                                                    case 198:
                                                                        return "IFNULL";
                                                                    case 199:
                                                                        return "IFNONNULL";
                                                                    case 200:
                                                                        return "ICONST";
                                                                    case 201:
                                                                        return "LCONST";
                                                                    case 202:
                                                                        return "FCONST";
                                                                    case 203:
                                                                        return "DCONST";
                                                                    case 204:
                                                                        return "INVOKESTATIC_ITF";
                                                                    case 205:
                                                                        return "INVOKEDIRECT";
                                                                    case 206:
                                                                        return "INVOKEDIRECT_ITF";
                                                                    case 207:
                                                                        return "INVOKESUPER";
                                                                    case 208:
                                                                        return "INVOKESUPER_ITF";
                                                                    case 209:
                                                                        return "DEBUGPOS";
                                                                    case 210:
                                                                        return "PHI";
                                                                    case 211:
                                                                        return "FALLTHROUGH";
                                                                    case 212:
                                                                        return "MOVEEXCEPTION";
                                                                    case 213:
                                                                        return "DEBUGLOCALWRITE";
                                                                    default:
                                                                        throw new C3405y10("Unexpected LIR opcode: " + i);
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
    }
}
