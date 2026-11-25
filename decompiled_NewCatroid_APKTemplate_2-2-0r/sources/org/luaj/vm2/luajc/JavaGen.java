package org.luaj.vm2.luajc;

import org.luaj.vm2.LocVars;
import org.luaj.vm2.Lua;
import org.luaj.vm2.Prototype;
import org.luaj.vm2.Upvaldesc;

/* JADX INFO: loaded from: classes6.dex */
public class JavaGen {
    public final byte[] bytecode;
    public final String classname;
    public final JavaGen[] inners;

    public JavaGen(Prototype prototype, String str, String str2, boolean z) {
        this(new ProtoInfo(prototype, str), str, str2, z);
    }

    private JavaGen(ProtoInfo protoInfo, String str, String str2, boolean z) {
        this.classname = str;
        JavaBuilder javaBuilder = new JavaBuilder(protoInfo, str, str2);
        scanInstructions(protoInfo, str, javaBuilder);
        for (int i = 0; i < protoInfo.prototype.locvars.length; i++) {
            LocVars locVars = protoInfo.prototype.locvars[i];
            javaBuilder.setVarStartEnd(i, locVars.startpc, locVars.endpc, locVars.varname.tojstring());
        }
        this.bytecode = javaBuilder.completeClass(z);
        if (protoInfo.subprotos == null) {
            this.inners = null;
            return;
        }
        int length = protoInfo.subprotos.length;
        this.inners = new JavaGen[length];
        for (int i2 = 0; i2 < length; i2++) {
            this.inners[i2] = new JavaGen(protoInfo.subprotos[i2], protoInfo.subprotos[i2].f1060name, str2, false);
        }
    }

    private void loadLocalOrConstant(Prototype prototype, JavaBuilder javaBuilder, int i, int i2) {
        if (i2 <= 255) {
            javaBuilder.loadLocal(i, i2);
        } else {
            javaBuilder.loadConstant(prototype.k[i2 & 255]);
        }
    }

    private void loadVarargResults(JavaBuilder javaBuilder, int i, int i2, int i3) {
        if (i3 <= i2) {
            javaBuilder.loadVarresult();
            javaBuilder.subargs((i2 + 1) - i3);
        } else if (i3 == i2) {
            javaBuilder.loadVarresult();
        } else {
            javaBuilder.newVarargsVarresult(i, i2, i3 - i2);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:101:0x01c5  */
    /* JADX WARN: Code duplicated, block: B:103:0x01c8  */
    /* JADX WARN: Code duplicated, block: B:104:0x01cc  */
    /* JADX WARN: Code duplicated, block: B:106:0x01d1 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:107:0x01d3  */
    /* JADX WARN: Code duplicated, block: B:109:0x01d6  */
    /* JADX WARN: Code duplicated, block: B:111:0x01d9  */
    /* JADX WARN: Code duplicated, block: B:113:0x01dd  */
    /* JADX WARN: Code duplicated, block: B:115:0x01ea A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:116:0x01ec  */
    /* JADX WARN: Code duplicated, block: B:129:0x021f A[PHI: r15
      0x021f: PHI (r15v2 int) = (r15v1 int), (r15v3 int) binds: [B:127:0x021c, B:124:0x020f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:199:0x01e0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:26:0x006a  */
    /* JADX WARN: Code duplicated, block: B:51:0x00ce  */
    /* JADX WARN: Code duplicated, block: B:64:0x0154  */
    private void scanInstructions(ProtoInfo protoInfo, String str, JavaBuilder javaBuilder) {
        int i;
        int i2;
        boolean z;
        int i3;
        int i4;
        Prototype prototype = protoInfo.prototype;
        int i5 = -1;
        for (int i6 = 0; i6 < protoInfo.blocklist.length; i6++) {
            BasicBlock basicBlock = protoInfo.blocklist[i6];
            for (int i7 = 0; i7 < prototype.maxstacksize; i7++) {
                int i8 = basicBlock.pc0;
                if (protoInfo.isUpvalueCreate(i8, i7) && protoInfo.vars[i7][i8].isPhiVar()) {
                    javaBuilder.convertToUpvalue(i8, i7);
                }
            }
            int i9 = basicBlock.pc0;
            while (i9 <= basicBlock.pc1) {
                int i10 = prototype.code[i9];
                int i11 = i9 < prototype.lineinfo.length ? prototype.lineinfo[i9] : -1;
                int iGET_OPCODE = Lua.GET_OPCODE(i10);
                int iGETARG_A = Lua.GETARG_A(i10);
                int iGETARG_B = Lua.GETARG_B(i10);
                int iGETARG_Bx = Lua.GETARG_Bx(i10);
                int iGETARG_sBx = Lua.GETARG_sBx(i10);
                int iGETARG_C = Lua.GETARG_C(i10);
                int i12 = 3;
                switch (iGET_OPCODE) {
                    case 0:
                        javaBuilder.loadLocal(i9, iGETARG_B);
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        break;
                    case 1:
                        javaBuilder.loadConstant(prototype.k[iGETARG_Bx]);
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        break;
                    case 2:
                    default:
                        break;
                    case 3:
                        javaBuilder.loadBoolean(iGETARG_B != 0);
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        if (iGETARG_C != 0) {
                            javaBuilder.addBranch(i9, 1, i9 + 2);
                        }
                        break;
                    case 4:
                        javaBuilder.loadNil();
                        while (iGETARG_B >= 0) {
                            if (iGETARG_B > 0) {
                                javaBuilder.dup();
                            }
                            javaBuilder.storeLocal(i9, iGETARG_A);
                            iGETARG_A++;
                            iGETARG_B--;
                        }
                        break;
                    case 5:
                        javaBuilder.loadUpvalue(iGETARG_B);
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        break;
                    case 6:
                        javaBuilder.loadUpvalue(iGETARG_B);
                        loadLocalOrConstant(prototype, javaBuilder, i9, iGETARG_C);
                        javaBuilder.getTable();
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        break;
                    case 7:
                        javaBuilder.loadLocal(i9, iGETARG_B);
                        loadLocalOrConstant(prototype, javaBuilder, i9, iGETARG_C);
                        javaBuilder.getTable();
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        break;
                    case 8:
                        javaBuilder.loadUpvalue(iGETARG_A);
                        loadLocalOrConstant(prototype, javaBuilder, i9, iGETARG_B);
                        loadLocalOrConstant(prototype, javaBuilder, i9, iGETARG_C);
                        javaBuilder.setTable();
                        break;
                    case 9:
                        javaBuilder.storeUpvalue(i9, iGETARG_B, iGETARG_A);
                        break;
                    case 10:
                        javaBuilder.loadLocal(i9, iGETARG_A);
                        loadLocalOrConstant(prototype, javaBuilder, i9, iGETARG_B);
                        loadLocalOrConstant(prototype, javaBuilder, i9, iGETARG_C);
                        javaBuilder.setTable();
                        break;
                    case 11:
                        javaBuilder.newTable(iGETARG_B, iGETARG_C);
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        break;
                    case 12:
                        javaBuilder.loadLocal(i9, iGETARG_B);
                        javaBuilder.dup();
                        javaBuilder.storeLocal(i9, iGETARG_A + 1);
                        loadLocalOrConstant(prototype, javaBuilder, i9, iGETARG_C);
                        javaBuilder.getTable();
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        break;
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                        loadLocalOrConstant(prototype, javaBuilder, i9, iGETARG_B);
                        loadLocalOrConstant(prototype, javaBuilder, i9, iGETARG_C);
                        javaBuilder.binaryop(iGET_OPCODE);
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        break;
                    case 19:
                    case 20:
                    case 21:
                        javaBuilder.loadLocal(i9, iGETARG_B);
                        javaBuilder.unaryop(iGET_OPCODE);
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        break;
                    case 22:
                        for (int i13 = iGETARG_B; i13 <= iGETARG_C; i13++) {
                            javaBuilder.loadLocal(i9, i13);
                        }
                        if (iGETARG_C > iGETARG_B + 1) {
                            javaBuilder.tobuffer();
                            while (true) {
                                iGETARG_C--;
                                if (iGETARG_C >= iGETARG_B) {
                                    javaBuilder.concatbuffer();
                                } else {
                                    javaBuilder.tovalue();
                                }
                            }
                        } else {
                            javaBuilder.concatvalue();
                        }
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        break;
                    case 23:
                        if (iGETARG_A > 0) {
                            for (int i14 = iGETARG_A - 1; i14 < protoInfo.openups.length; i14++) {
                                javaBuilder.closeUpvalue(i9, i14);
                            }
                        }
                        javaBuilder.addBranch(i9, 1, i9 + 1 + iGETARG_sBx);
                        break;
                    case 24:
                    case 25:
                    case 26:
                        i = 2;
                        loadLocalOrConstant(prototype, javaBuilder, i9, iGETARG_B);
                        loadLocalOrConstant(prototype, javaBuilder, i9, iGETARG_C);
                        javaBuilder.compareop(iGET_OPCODE);
                        if (iGETARG_A == 0) {
                            i12 = i;
                        }
                        i2 = i9 + 2;
                        javaBuilder.addBranch(i9, i12, i2);
                        break;
                    case 27:
                        i = 2;
                        javaBuilder.loadLocal(i9, iGETARG_A);
                        javaBuilder.toBoolean();
                        if (iGETARG_C == 0) {
                            i12 = i;
                        }
                        i2 = i9 + 2;
                        javaBuilder.addBranch(i9, i12, i2);
                        break;
                    case 28:
                        javaBuilder.loadLocal(i9, iGETARG_B);
                        javaBuilder.toBoolean();
                        javaBuilder.addBranch(i9, iGETARG_C == 0 ? 2 : 3, i9 + 2);
                        javaBuilder.loadLocal(i9, iGETARG_B);
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        break;
                    case 29:
                        javaBuilder.loadLocal(i9, iGETARG_A);
                        int i15 = iGETARG_B - 1;
                        if (i15 != -1) {
                            if (i15 == 0 || i15 == 1 || i15 == 2 || i15 == 3) {
                                for (int i16 = 1; i16 < iGETARG_B; i16++) {
                                    javaBuilder.loadLocal(i9, iGETARG_A + i16);
                                }
                            } else {
                                javaBuilder.newVarargs(i9, iGETARG_A + 1, i15);
                            }
                            if (i15 >= 0 || iGETARG_C < 1 || iGETARG_C > 2) {
                                z = true;
                            } else {
                                z = false;
                            }
                            if (z) {
                                javaBuilder.invoke(i15);
                            } else {
                                javaBuilder.call(i15);
                            }
                            if (iGETARG_C != 0) {
                                javaBuilder.storeVarresult();
                                i5 = iGETARG_A;
                            } else if (iGETARG_C != 1) {
                                javaBuilder.pop();
                            } else if (iGETARG_C != 2) {
                                i3 = 1;
                                while (i3 < iGETARG_C) {
                                    i4 = i3 + 1;
                                    if (i4 < iGETARG_C) {
                                        javaBuilder.dup();
                                    }
                                    javaBuilder.arg(i3);
                                    javaBuilder.storeLocal(i9, (i3 + iGETARG_A) - 1);
                                    i3 = i4;
                                }
                            } else {
                                if (z) {
                                    javaBuilder.arg(1);
                                }
                                javaBuilder.storeLocal(i9, iGETARG_A);
                            }
                        } else {
                            loadVarargResults(javaBuilder, i9, iGETARG_A + 1, i5);
                        }
                        i15 = -1;
                        if (i15 >= 0) {
                            z = true;
                        } else {
                            z = true;
                        }
                        if (z) {
                            javaBuilder.invoke(i15);
                        } else {
                            javaBuilder.call(i15);
                        }
                        if (iGETARG_C != 0) {
                            javaBuilder.storeVarresult();
                            i5 = iGETARG_A;
                        } else if (iGETARG_C != 1) {
                            javaBuilder.pop();
                        } else if (iGETARG_C != 2) {
                            i3 = 1;
                            while (i3 < iGETARG_C) {
                                i4 = i3 + 1;
                                if (i4 < iGETARG_C) {
                                    javaBuilder.dup();
                                }
                                javaBuilder.arg(i3);
                                javaBuilder.storeLocal(i9, (i3 + iGETARG_A) - 1);
                                i3 = i4;
                            }
                        } else {
                            if (z) {
                                javaBuilder.arg(1);
                            }
                            javaBuilder.storeLocal(i9, iGETARG_A);
                        }
                        break;
                    case 30:
                        javaBuilder.loadLocal(i9, iGETARG_A);
                        if (iGETARG_B == 0) {
                            loadVarargResults(javaBuilder, i9, iGETARG_A + 1, i5);
                        } else if (iGETARG_B != 1) {
                            int i17 = iGETARG_A + 1;
                            if (iGETARG_B != 2) {
                                javaBuilder.newVarargs(i9, i17, iGETARG_B - 1);
                            } else {
                                javaBuilder.loadLocal(i9, i17);
                            }
                        } else {
                            javaBuilder.loadNone();
                        }
                        javaBuilder.newTailcallVarargs();
                        javaBuilder.areturn();
                        break;
                    case 31:
                        if (iGETARG_C == 1) {
                            javaBuilder.loadNone();
                        } else if (iGETARG_B == 0) {
                            loadVarargResults(javaBuilder, i9, iGETARG_A, i5);
                        } else if (iGETARG_B == 1) {
                            javaBuilder.loadNone();
                        } else if (iGETARG_B != 2) {
                            javaBuilder.newVarargs(i9, iGETARG_A, iGETARG_B - 1);
                        } else {
                            javaBuilder.loadLocal(i9, iGETARG_A);
                        }
                        javaBuilder.areturn();
                        break;
                    case 32:
                        javaBuilder.loadLocal(i9, iGETARG_A);
                        int i18 = iGETARG_A + 2;
                        javaBuilder.loadLocal(i9, i18);
                        javaBuilder.binaryop(13);
                        javaBuilder.dup();
                        javaBuilder.dup();
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        javaBuilder.storeLocal(i9, iGETARG_A + 3);
                        javaBuilder.loadLocal(i9, iGETARG_A + 1);
                        javaBuilder.loadLocal(i9, i18);
                        javaBuilder.testForLoop();
                        i2 = i9 + 1 + iGETARG_sBx;
                        i12 = 2;
                        javaBuilder.addBranch(i9, i12, i2);
                        break;
                    case 33:
                        javaBuilder.loadLocal(i9, iGETARG_A);
                        javaBuilder.loadLocal(i9, iGETARG_A + 2);
                        javaBuilder.binaryop(14);
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        javaBuilder.addBranch(i9, 1, i9 + 1 + iGETARG_sBx);
                        break;
                    case 34:
                        javaBuilder.loadLocal(i9, iGETARG_A);
                        javaBuilder.loadLocal(i9, iGETARG_A + 1);
                        int i19 = iGETARG_A + 2;
                        javaBuilder.loadLocal(i9, i19);
                        javaBuilder.invoke(2);
                        for (int i20 = 1; i20 <= iGETARG_C; i20++) {
                            if (i20 < iGETARG_C) {
                                javaBuilder.dup();
                            }
                            javaBuilder.arg(i20);
                            javaBuilder.storeLocal(i9, i19 + i20);
                        }
                        break;
                    case 35:
                        javaBuilder.loadLocal(i9, iGETARG_A + 1);
                        javaBuilder.dup();
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        javaBuilder.isNil();
                        i2 = i9 + 1 + iGETARG_sBx;
                        javaBuilder.addBranch(i9, i12, i2);
                        break;
                    case 36:
                        int i21 = ((iGETARG_C - 1) * 50) + 1;
                        javaBuilder.loadLocal(i9, iGETARG_A);
                        int i22 = iGETARG_A + 1;
                        if (iGETARG_B == 0) {
                            int i23 = i5 - i22;
                            if (i23 > 0) {
                                javaBuilder.setlistStack(i9, i22, i21, i23);
                                i21 += i23;
                            }
                            javaBuilder.setlistVarargs(i21, i5);
                        } else {
                            javaBuilder.setlistStack(i9, i22, i21, iGETARG_B);
                            javaBuilder.pop();
                        }
                        break;
                    case 37:
                        Prototype prototype2 = prototype.p[iGETARG_Bx];
                        int length = prototype2.upvalues.length;
                        String str2 = protoInfo.subprotos[iGETARG_Bx].f1060name;
                        javaBuilder.closureCreate(str2);
                        if (length > 0) {
                            javaBuilder.dup();
                        }
                        javaBuilder.storeLocal(i9, iGETARG_A);
                        int i24 = 0;
                        while (i24 < length) {
                            int i25 = i24 + 1;
                            if (i25 < length) {
                                javaBuilder.dup();
                            }
                            Upvaldesc upvaldesc = prototype2.upvalues[i24];
                            boolean z2 = upvaldesc.instack;
                            short s = upvaldesc.idx;
                            if (z2) {
                                javaBuilder.closureInitUpvalueFromLocal(str2, i24, i9, s);
                            } else {
                                javaBuilder.closureInitUpvalueFromUpvalue(str2, i24, s);
                            }
                            i24 = i25;
                        }
                        break;
                    case 38:
                        if (iGETARG_B == 0) {
                            javaBuilder.loadVarargs();
                            javaBuilder.storeVarresult();
                            i5 = iGETARG_A;
                        } else {
                            for (int i26 = 1; i26 < iGETARG_B; i26++) {
                                javaBuilder.loadVarargs(i26);
                                javaBuilder.storeLocal(i9, iGETARG_A);
                                iGETARG_A++;
                            }
                        }
                        break;
                }
                javaBuilder.onEndOfLuaInstruction(i9, i11);
                i9++;
            }
        }
    }
}
