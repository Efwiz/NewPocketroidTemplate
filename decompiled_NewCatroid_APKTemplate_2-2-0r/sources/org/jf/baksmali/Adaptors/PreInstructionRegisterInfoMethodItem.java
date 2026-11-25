package org.jf.baksmali.Adaptors;

import java.io.IOException;
import java.util.BitSet;
import java.util.Iterator;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.analysis.AnalyzedInstruction;
import org.jf.dexlib2.analysis.MethodAnalyzer;
import org.jf.dexlib2.analysis.RegisterType;
import org.jf.dexlib2.iface.instruction.FiveRegisterInstruction;
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction;
import org.jf.dexlib2.iface.instruction.RegisterRangeInstruction;
import org.jf.dexlib2.iface.instruction.ThreeRegisterInstruction;
import org.jf.dexlib2.iface.instruction.TwoRegisterInstruction;

/* JADX INFO: loaded from: classes6.dex */
public class PreInstructionRegisterInfoMethodItem extends MethodItem {
    private final AnalyzedInstruction analyzedInstruction;
    private final MethodAnalyzer methodAnalyzer;
    private final RegisterFormatter registerFormatter;
    private final int registerInfo;

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public double getSortOrder() {
        return 99.9d;
    }

    public PreInstructionRegisterInfoMethodItem(int i, MethodAnalyzer methodAnalyzer, RegisterFormatter registerFormatter, AnalyzedInstruction analyzedInstruction, int i2) {
        super(i2);
        this.registerInfo = i;
        this.methodAnalyzer = methodAnalyzer;
        this.registerFormatter = registerFormatter;
        this.analyzedInstruction = analyzedInstruction;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0056 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:26:0x0058  */
    /* JADX WARN: Code duplicated, block: B:28:0x0065 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:29:0x0067  */
    /* JADX WARN: Code duplicated, block: B:30:0x006b  */
    @Override // org.jf.baksmali.Adaptors.MethodItem
    public boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        BitSet bitSet;
        int registerCount = this.analyzedInstruction.getRegisterCount();
        BitSet bitSet2 = new BitSet(registerCount);
        int i = this.registerInfo;
        BitSet bitSet3 = null;
        if ((i & 1) != 0 || (i & 2) != 0) {
            bitSet2.set(0, registerCount);
        } else {
            if ((i & 8) != 0) {
                addArgsRegs(bitSet2);
            }
            int i2 = this.registerInfo;
            if ((i2 & 32) != 0) {
                if (this.analyzedInstruction.isBeginningInstruction()) {
                    addParamRegs(bitSet2, registerCount);
                }
                bitSet = new BitSet(registerCount);
                addMergeRegs(bitSet, registerCount);
            } else if ((i2 & 64) != 0 && this.analyzedInstruction.isBeginningInstruction()) {
                addParamRegs(bitSet2, registerCount);
            }
            if ((this.registerInfo & 64) != 0) {
                if (bitSet == null) {
                    bitSet = new BitSet(registerCount);
                    addMergeRegs(bitSet, registerCount);
                }
                bitSet3 = bitSet;
                bitSet2.or(bitSet3);
            } else if (bitSet != null) {
                bitSet2.or(bitSet);
            } else {
                bitSet3 = bitSet;
            }
            return writeRegisterInfo(baksmaliWriter, bitSet2, bitSet3);
        }
        bitSet = null;
        if ((this.registerInfo & 64) != 0) {
            if (bitSet == null) {
                bitSet = new BitSet(registerCount);
                addMergeRegs(bitSet, registerCount);
            }
            bitSet3 = bitSet;
            bitSet2.or(bitSet3);
        } else if (bitSet != null) {
            bitSet2.or(bitSet);
        } else {
            bitSet3 = bitSet;
        }
        return writeRegisterInfo(baksmaliWriter, bitSet2, bitSet3);
    }

    private void addArgsRegs(BitSet bitSet) {
        if (this.analyzedInstruction.getInstruction() instanceof RegisterRangeInstruction) {
            RegisterRangeInstruction registerRangeInstruction = (RegisterRangeInstruction) this.analyzedInstruction.getInstruction();
            bitSet.set(registerRangeInstruction.getStartRegister(), registerRangeInstruction.getStartRegister() + registerRangeInstruction.getRegisterCount());
            return;
        }
        if (this.analyzedInstruction.getInstruction() instanceof FiveRegisterInstruction) {
            FiveRegisterInstruction fiveRegisterInstruction = (FiveRegisterInstruction) this.analyzedInstruction.getInstruction();
            int registerCount = fiveRegisterInstruction.getRegisterCount();
            if (registerCount != 1) {
                if (registerCount != 2) {
                    if (registerCount != 3) {
                        if (registerCount != 4) {
                            if (registerCount != 5) {
                                return;
                            } else {
                                bitSet.set(fiveRegisterInstruction.getRegisterG());
                            }
                        }
                        bitSet.set(fiveRegisterInstruction.getRegisterF());
                    }
                    bitSet.set(fiveRegisterInstruction.getRegisterE());
                }
                bitSet.set(fiveRegisterInstruction.getRegisterD());
            }
            bitSet.set(fiveRegisterInstruction.getRegisterC());
            return;
        }
        if (this.analyzedInstruction.getInstruction() instanceof ThreeRegisterInstruction) {
            ThreeRegisterInstruction threeRegisterInstruction = (ThreeRegisterInstruction) this.analyzedInstruction.getInstruction();
            bitSet.set(threeRegisterInstruction.getRegisterA());
            bitSet.set(threeRegisterInstruction.getRegisterB());
            bitSet.set(threeRegisterInstruction.getRegisterC());
            return;
        }
        if (this.analyzedInstruction.getInstruction() instanceof TwoRegisterInstruction) {
            TwoRegisterInstruction twoRegisterInstruction = (TwoRegisterInstruction) this.analyzedInstruction.getInstruction();
            bitSet.set(twoRegisterInstruction.getRegisterA());
            bitSet.set(twoRegisterInstruction.getRegisterB());
        } else if (this.analyzedInstruction.getInstruction() instanceof OneRegisterInstruction) {
            bitSet.set(((OneRegisterInstruction) this.analyzedInstruction.getInstruction()).getRegisterA());
        }
    }

    private void addMergeRegs(BitSet bitSet, int i) {
        if (this.analyzedInstruction.getPredecessorCount() <= 1) {
            return;
        }
        for (int i2 = 0; i2 < i; i2++) {
            RegisterType preInstructionRegisterType = this.analyzedInstruction.getPreInstructionRegisterType(i2);
            Iterator<AnalyzedInstruction> it = this.analyzedInstruction.getPredecessors().iterator();
            while (it.hasNext()) {
                RegisterType predecessorRegisterType = this.analyzedInstruction.getPredecessorRegisterType(it.next(), i2);
                if (predecessorRegisterType.category != 0 && !predecessorRegisterType.equals(preInstructionRegisterType)) {
                    bitSet.set(i2);
                }
            }
        }
    }

    private void addParamRegs(BitSet bitSet, int i) {
        bitSet.set(i - this.methodAnalyzer.getParamRegisterCount(), i);
    }

    private void writeFullMerge(BaksmaliWriter baksmaliWriter, int i) throws IOException {
        this.registerFormatter.writeTo(baksmaliWriter, i);
        baksmaliWriter.write(61);
        this.analyzedInstruction.getPreInstructionRegisterType(i).writeTo(baksmaliWriter);
        baksmaliWriter.write(":merge{");
        boolean z = true;
        for (AnalyzedInstruction analyzedInstruction : this.analyzedInstruction.getPredecessors()) {
            RegisterType predecessorRegisterType = this.analyzedInstruction.getPredecessorRegisterType(analyzedInstruction, i);
            if (!z) {
                baksmaliWriter.write(44);
            }
            if (analyzedInstruction.getInstructionIndex() == -1) {
                baksmaliWriter.write("Start:");
            } else {
                baksmaliWriter.write(PsiLiteralUtil.HEX_PREFIX);
                baksmaliWriter.writeUnsignedLongAsHex(this.methodAnalyzer.getInstructionAddress(analyzedInstruction));
                baksmaliWriter.write(58);
            }
            predecessorRegisterType.writeTo(baksmaliWriter);
            z = false;
        }
        baksmaliWriter.write(125);
    }

    private boolean writeRegisterInfo(BaksmaliWriter baksmaliWriter, BitSet bitSet, BitSet bitSet2) throws IOException {
        int iNextSetBit = bitSet.nextSetBit(0);
        if (iNextSetBit < 0) {
            return false;
        }
        baksmaliWriter.write(35);
        boolean z = false;
        boolean z2 = true;
        while (iNextSetBit >= 0) {
            if (bitSet2 != null && bitSet2.get(iNextSetBit)) {
                if (!z2) {
                    baksmaliWriter.write(10);
                    baksmaliWriter.write(35);
                }
                writeFullMerge(baksmaliWriter, iNextSetBit);
                z = true;
            } else {
                if (z) {
                    baksmaliWriter.write(10);
                    baksmaliWriter.write(35);
                    z = false;
                }
                RegisterType preInstructionRegisterType = this.analyzedInstruction.getPreInstructionRegisterType(iNextSetBit);
                this.registerFormatter.writeTo(baksmaliWriter, iNextSetBit);
                baksmaliWriter.write(61);
                preInstructionRegisterType.writeTo(baksmaliWriter);
                baksmaliWriter.write(59);
            }
            iNextSetBit = bitSet.nextSetBit(iNextSetBit + 1);
            z2 = false;
        }
        return true;
    }
}
