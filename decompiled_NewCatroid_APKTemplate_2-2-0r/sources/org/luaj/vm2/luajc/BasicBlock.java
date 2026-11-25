package org.luaj.vm2.luajc;

import com.beust.jcommander.Parameters;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Vector;
import org.luaj.vm2.Lua;
import org.luaj.vm2.Prototype;

/* JADX INFO: loaded from: classes6.dex */
public class BasicBlock {
    boolean islive;
    BasicBlock[] next;
    int pc0;
    int pc1;
    BasicBlock[] prev;

    private static final class AllocAndXRefVisitor extends BranchVisitor {
        private final BasicBlock[] blocks;
        private final int[] nnext;
        private final int[] nprev;

        private AllocAndXRefVisitor(boolean[] zArr, int[] iArr, int[] iArr2, BasicBlock[] basicBlockArr) {
            super(zArr);
            this.nnext = iArr;
            this.nprev = iArr2;
            this.blocks = basicBlockArr;
        }

        @Override // org.luaj.vm2.luajc.BasicBlock.BranchVisitor
        public void visitBranch(int i, int i2) {
            if (this.blocks[i].next == null) {
                this.blocks[i].next = new BasicBlock[this.nnext[i]];
            }
            if (this.blocks[i2].prev == null) {
                this.blocks[i2].prev = new BasicBlock[this.nprev[i2]];
            }
            BasicBlock[] basicBlockArr = this.blocks[i].next;
            int[] iArr = this.nnext;
            int i3 = iArr[i] - 1;
            iArr[i] = i3;
            BasicBlock[] basicBlockArr2 = this.blocks;
            basicBlockArr[i3] = basicBlockArr2[i2];
            BasicBlock[] basicBlockArr3 = basicBlockArr2[i2].prev;
            int[] iArr2 = this.nprev;
            int i4 = iArr2[i2] - 1;
            iArr2[i2] = i4;
            basicBlockArr3[i4] = this.blocks[i];
        }
    }

    public static abstract class BranchVisitor {
        final boolean[] isbeg;

        public BranchVisitor(boolean[] zArr) {
            this.isbeg = zArr;
        }

        public void visitBranch(int i, int i2) {
        }

        public void visitReturn(int i) {
        }
    }

    private static final class CountPrevNextVistor extends BranchVisitor {
        private final int[] nnext;
        private final int[] nprev;

        private CountPrevNextVistor(boolean[] zArr, int[] iArr, int[] iArr2) {
            super(zArr);
            this.nnext = iArr;
            this.nprev = iArr2;
        }

        @Override // org.luaj.vm2.luajc.BasicBlock.BranchVisitor
        public void visitBranch(int i, int i2) {
            int[] iArr = this.nnext;
            iArr[i] = iArr[i] + 1;
            int[] iArr2 = this.nprev;
            iArr2[i2] = iArr2[i2] + 1;
        }
    }

    private static final class MarkAndMergeVisitor extends BranchVisitor {
        private final boolean[] isend;

        private MarkAndMergeVisitor(boolean[] zArr, boolean[] zArr2) {
            super(zArr);
            this.isend = zArr2;
        }

        @Override // org.luaj.vm2.luajc.BasicBlock.BranchVisitor
        public void visitBranch(int i, int i2) {
            this.isend[i] = true;
            this.isbeg[i2] = true;
        }

        @Override // org.luaj.vm2.luajc.BasicBlock.BranchVisitor
        public void visitReturn(int i) {
            this.isend[i] = true;
        }
    }

    public BasicBlock(Prototype prototype, int i) {
        this.pc1 = i;
        this.pc0 = i;
    }

    public static BasicBlock[] findBasicBlocks(Prototype prototype) {
        int i;
        int length = prototype.code.length;
        boolean[] zArr = new boolean[length];
        boolean[] zArr2 = new boolean[length];
        int i2 = 0;
        zArr[0] = true;
        MarkAndMergeVisitor markAndMergeVisitor = new MarkAndMergeVisitor(zArr, zArr2);
        visitBranches(prototype, markAndMergeVisitor);
        visitBranches(prototype, markAndMergeVisitor);
        BasicBlock[] basicBlockArr = new BasicBlock[length];
        while (i2 < length) {
            zArr[i2] = true;
            BasicBlock basicBlock = new BasicBlock(prototype, i2);
            basicBlockArr[i2] = basicBlock;
            while (!zArr2[i2] && (i = i2 + 1) < length && !zArr[i]) {
                basicBlock.pc1 = i;
                basicBlockArr[i] = basicBlock;
                i2 = i;
            }
            i2++;
        }
        int[] iArr = new int[length];
        int[] iArr2 = new int[length];
        visitBranches(prototype, new CountPrevNextVistor(zArr, iArr, iArr2));
        visitBranches(prototype, new AllocAndXRefVisitor(zArr, iArr, iArr2, basicBlockArr));
        return basicBlockArr;
    }

    public static BasicBlock[] findLiveBlocks(BasicBlock[] basicBlockArr) {
        Vector vector = new Vector();
        int i = 0;
        vector.addElement(basicBlockArr[0]);
        while (!vector.isEmpty()) {
            BasicBlock basicBlock = (BasicBlock) vector.elementAt(0);
            vector.removeElementAt(0);
            if (!basicBlock.islive) {
                basicBlock.islive = true;
                BasicBlock[] basicBlockArr2 = basicBlock.next;
                int length = basicBlockArr2 != null ? basicBlockArr2.length : 0;
                for (int i2 = 0; i2 < length; i2++) {
                    BasicBlock basicBlock2 = basicBlock.next[i2];
                    if (!basicBlock2.islive) {
                        vector.addElement(basicBlock2);
                    }
                }
            }
        }
        Vector vector2 = new Vector();
        while (i < basicBlockArr.length) {
            BasicBlock basicBlock3 = basicBlockArr[i];
            if (basicBlock3.islive) {
                vector2.addElement(basicBlock3);
            }
            i = basicBlockArr[i].pc1 + 1;
        }
        BasicBlock[] basicBlockArr3 = new BasicBlock[vector2.size()];
        vector2.copyInto(basicBlockArr3);
        return basicBlockArr3;
    }

    private String str(BasicBlock[] basicBlockArr, int i) {
        if (basicBlockArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer("(");
        int length = basicBlockArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (i2 > 0) {
                stringBuffer.append(",");
            }
            stringBuffer.append(String.valueOf((i == 1 ? basicBlockArr[i2].pc1 : basicBlockArr[i2].pc0) + 1));
        }
        stringBuffer.append(Constant.AFTER_QUTO);
        return stringBuffer.toString();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:17:0x0052  */
    /* JADX WARN: Code duplicated, block: B:19:0x005d  */
    /* JADX WARN: Code duplicated, block: B:22:0x0070  */
    public static void visitBranches(Prototype prototype, BranchVisitor branchVisitor) {
        int iGETARG_sBx;
        int[] iArr = prototype.code;
        int length = iArr.length;
        int i = 0;
        while (i < length) {
            int i2 = iArr[i];
            int iGET_OPCODE = Lua.GET_OPCODE(i2);
            if (iGET_OPCODE == 3) {
                if (Lua.GETARG_C(i2) == 0) {
                    iGETARG_sBx = i + 1;
                    if (iGETARG_sBx < length) {
                    }
                } else {
                    if (Lua.GET_OPCODE(iArr[i + 1]) == 23) {
                        throw new IllegalArgumentException(new StringBuffer("OP_LOADBOOL followed by jump at ").append(i).toString());
                    }
                    iGETARG_sBx = i + 2;
                }
                branchVisitor.visitBranch(i, iGETARG_sBx);
            } else if (iGET_OPCODE != 35) {
                switch (iGET_OPCODE) {
                    case 23:
                        iGETARG_sBx = Lua.GETARG_sBx(i2) + i + 1;
                        branchVisitor.visitBranch(i, iGETARG_sBx);
                        break;
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                        int i3 = i + 1;
                        if (Lua.GET_OPCODE(iArr[i3]) != 23) {
                            throw new IllegalArgumentException(new StringBuffer("test not followed by jump at ").append(i).toString());
                        }
                        branchVisitor.visitBranch(i3, Lua.GETARG_sBx(iArr[i3]) + i3 + 1);
                        branchVisitor.visitBranch(i3, i + 2);
                        i = i3;
                        break;
                        break;
                    default:
                        switch (iGET_OPCODE) {
                            case 30:
                            case 31:
                                branchVisitor.visitReturn(i);
                                continue;
                            case 32:
                                branchVisitor.visitBranch(i, Lua.GETARG_sBx(i2) + i + 1);
                                iGETARG_sBx = i + 1;
                                break;
                            case 33:
                                iGETARG_sBx = Lua.GETARG_sBx(i2) + i + 1;
                                break;
                            default:
                                iGETARG_sBx = i + 1;
                                if (iGETARG_sBx < length || !branchVisitor.isbeg[iGETARG_sBx]) {
                                }
                                break;
                        }
                        branchVisitor.visitBranch(i, iGETARG_sBx);
                        break;
                }
            } else {
                branchVisitor.visitBranch(i, Lua.GETARG_sBx(i2) + i + 1);
                iGETARG_sBx = i + 1;
                branchVisitor.visitBranch(i, iGETARG_sBx);
            }
            i++;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(new StringBuffer().append(this.pc0 + 1).append(Parameters.DEFAULT_OPTION_PREFIXES).append(this.pc1 + 1).append(this.prev != null ? new StringBuffer("  prv: ").append(str(this.prev, 1)).toString() : "").append(this.next != null ? new StringBuffer("  nxt: ").append(str(this.next, 0)).toString() : "").append("\n").toString());
        return stringBuffer.toString();
    }
}
