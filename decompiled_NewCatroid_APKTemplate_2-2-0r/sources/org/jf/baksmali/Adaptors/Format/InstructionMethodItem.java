package org.jf.baksmali.Adaptors.Format;

import java.io.IOException;
import org.jf.baksmali.Adaptors.MethodDefinition;
import org.jf.baksmali.Adaptors.MethodItem;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.VerificationError;
import org.jf.dexlib2.iface.instruction.DualReferenceInstruction;
import org.jf.dexlib2.iface.instruction.FieldOffsetInstruction;
import org.jf.dexlib2.iface.instruction.FiveRegisterInstruction;
import org.jf.dexlib2.iface.instruction.InlineIndexInstruction;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction;
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.instruction.RegisterRangeInstruction;
import org.jf.dexlib2.iface.instruction.ThreeRegisterInstruction;
import org.jf.dexlib2.iface.instruction.TwoRegisterInstruction;
import org.jf.dexlib2.iface.instruction.VtableIndexInstruction;
import org.jf.dexlib2.iface.instruction.WideLiteralInstruction;
import org.jf.dexlib2.iface.instruction.formats.Instruction20bc;
import org.jf.dexlib2.iface.instruction.formats.Instruction31t;
import org.jf.dexlib2.iface.instruction.formats.UnknownInstruction;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.util.ExceptionWithContext;
import org.jf.util.NumberUtils;

/* JADX INFO: loaded from: classes6.dex */
public class InstructionMethodItem<T extends Instruction> extends MethodItem {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected T instruction;
    protected final MethodDefinition methodDef;

    /* JADX INFO: Access modifiers changed from: private */
    interface Writable {
        void write() throws IOException;
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public double getSortOrder() {
        return 100.0d;
    }

    public InstructionMethodItem(MethodDefinition methodDefinition, int i, T t) {
        super(i);
        this.methodDef = methodDefinition;
        this.instruction = t;
    }

    public T getInstruction() {
        return this.instruction;
    }

    public void setInstruction(T t) {
        this.instruction = t;
    }

    private boolean isAllowedOdex(Opcode opcode) {
        if (this.methodDef.classDef.options.allowOdex) {
            return true;
        }
        if (this.methodDef.classDef.options.apiLevel >= 14) {
            return false;
        }
        return opcode.isVolatileFieldAccessor() || opcode == Opcode.THROW_VERIFICATION_ERROR;
    }

    /* JADX WARN: Code duplicated, block: B:42:0x00f2  */
    @Override // org.jf.baksmali.Adaptors.MethodItem
    public boolean writeTo(final BaksmaliWriter baksmaliWriter) throws IOException {
        String verificationErrorName;
        Writable writable;
        boolean z;
        Writable writable2;
        Opcode opcode = this.instruction.getOpcode();
        T t = this.instruction;
        Writable writable3 = null;
        if (t instanceof Instruction20bc) {
            int verificationError = ((Instruction20bc) t).getVerificationError();
            verificationErrorName = VerificationError.getVerificationErrorName(verificationError);
            if (verificationErrorName == null) {
                baksmaliWriter.write("#was invalid verification error type: ");
                baksmaliWriter.writeSignedIntAsDec(verificationError);
                baksmaliWriter.write("\n");
                verificationErrorName = "generic-error";
            }
        } else {
            verificationErrorName = null;
        }
        T t2 = this.instruction;
        if (t2 instanceof ReferenceInstruction) {
            final Reference reference = ((ReferenceInstruction) t2).getReference();
            try {
                reference.validateReference();
                writable2 = new Writable() { // from class: org.jf.baksmali.Adaptors.Format.InstructionMethodItem$$ExternalSyntheticLambda0
                    @Override // org.jf.baksmali.Adaptors.Format.InstructionMethodItem.Writable
                    public final void write() {
                        baksmaliWriter.writeReference(reference);
                    }
                };
                z = false;
            } catch (Reference.InvalidReferenceException e) {
                baksmaliWriter.write("#");
                baksmaliWriter.write(e.getMessage());
                baksmaliWriter.write("\n");
                writable2 = new Writable() { // from class: org.jf.baksmali.Adaptors.Format.InstructionMethodItem$$ExternalSyntheticLambda1
                    @Override // org.jf.baksmali.Adaptors.Format.InstructionMethodItem.Writable
                    public final void write() {
                        baksmaliWriter.write(e.getInvalidReferenceRepresentation());
                    }
                };
                z = true;
            }
            T t3 = this.instruction;
            if (t3 instanceof DualReferenceInstruction) {
                try {
                    final Reference reference2 = ((DualReferenceInstruction) t3).getReference2();
                    reference2.validateReference();
                    writable = new Writable() { // from class: org.jf.baksmali.Adaptors.Format.InstructionMethodItem$$ExternalSyntheticLambda2
                        @Override // org.jf.baksmali.Adaptors.Format.InstructionMethodItem.Writable
                        public final void write() {
                            baksmaliWriter.writeReference(reference2);
                        }
                    };
                } catch (Reference.InvalidReferenceException e2) {
                    baksmaliWriter.write("#");
                    baksmaliWriter.write(e2.getMessage());
                    baksmaliWriter.write("\n");
                    Writable writable4 = new Writable() { // from class: org.jf.baksmali.Adaptors.Format.InstructionMethodItem$$ExternalSyntheticLambda3
                        @Override // org.jf.baksmali.Adaptors.Format.InstructionMethodItem.Writable
                        public final void write() {
                            baksmaliWriter.write(e2.getInvalidReferenceRepresentation());
                        }
                    };
                    writable = null;
                    z = true;
                    writable3 = writable4;
                }
            } else {
                writable = null;
            }
            writable3 = writable2;
        } else {
            writable = null;
            z = false;
        }
        if (this.instruction instanceof Instruction31t) {
            int i = AnonymousClass1.$SwitchMap$org$jf$dexlib2$Opcode[this.instruction.getOpcode().ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        try {
                            this.methodDef.findPayloadOffset(this.codeAddress + ((Instruction31t) this.instruction).getCodeOffset(), Opcode.ARRAY_PAYLOAD);
                        } catch (MethodDefinition.InvalidSwitchPayload unused) {
                            baksmaliWriter.write("#invalid payload reference\n");
                            z = true;
                        }
                    } else {
                        throw new ExceptionWithContext("Invalid 31t opcode: %s", this.instruction.getOpcode());
                    }
                } else if (this.methodDef.getSparseSwitchBaseAddress(this.codeAddress + ((Instruction31t) this.instruction).getCodeOffset()) == -1) {
                    baksmaliWriter.write("#invalid payload reference\n");
                    z = true;
                }
            } else if (this.methodDef.getPackedSwitchBaseAddress(this.codeAddress + ((Instruction31t) this.instruction).getCodeOffset()) == -1) {
                baksmaliWriter.write("#invalid payload reference\n");
                z = true;
            }
        }
        if (opcode.odexOnly() && !isAllowedOdex(opcode)) {
            baksmaliWriter.write("#disallowed odex opcode\n");
            z = true;
        }
        if (z) {
            baksmaliWriter.write("#");
        }
        switch (AnonymousClass1.$SwitchMap$org$jf$dexlib2$Format[this.instruction.getOpcode().format.ordinal()]) {
            case 1:
            case 2:
            case 3:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeTargetLabel(baksmaliWriter);
                break;
            case 4:
                if (this.instruction instanceof UnknownInstruction) {
                    baksmaliWriter.write("#unknown opcode: 0x");
                    baksmaliWriter.writeUnsignedLongAsHex(((UnknownInstruction) this.instruction).getOriginalOpcode());
                    baksmaliWriter.write(10);
                }
                writeOpcode(baksmaliWriter);
                break;
            case 5:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeFirstRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeLiteral(baksmaliWriter);
                break;
            case 6:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeFirstRegister(baksmaliWriter);
                break;
            case 7:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeFirstRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeSecondRegister(baksmaliWriter);
                break;
            case 8:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                baksmaliWriter.write(verificationErrorName);
                baksmaliWriter.write(", ");
                writable3.write();
                break;
            case 9:
            case 10:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeFirstRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writable3.write();
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeFirstRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeLiteral(baksmaliWriter);
                if (this.instruction.getOpcode().setsWideRegister()) {
                    writeCommentIfLikelyDouble(baksmaliWriter);
                } else if (!writeCommentIfResourceId(baksmaliWriter)) {
                    writeCommentIfLikelyFloat(baksmaliWriter);
                }
                break;
            case 16:
            case 17:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeFirstRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeTargetLabel(baksmaliWriter);
                break;
            case 18:
            case 19:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeFirstRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeSecondRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeLiteral(baksmaliWriter);
                break;
            case 20:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeFirstRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeSecondRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writable3.write();
                break;
            case 21:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeFirstRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeSecondRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeFieldOffset(baksmaliWriter);
                break;
            case 22:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeFirstRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeSecondRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeTargetLabel(baksmaliWriter);
                break;
            case 23:
            case 24:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeFirstRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeSecondRegister(baksmaliWriter);
                break;
            case 25:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeFirstRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeSecondRegister(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeThirdRegister(baksmaliWriter);
                break;
            case 26:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeInvokeRegisters(baksmaliWriter);
                baksmaliWriter.write(", ");
                writable3.write();
                break;
            case 27:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeInvokeRegisters(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeInlineIndex(baksmaliWriter);
                break;
            case 28:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeInvokeRegisters(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeVtableIndex(baksmaliWriter);
                break;
            case 29:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeInvokeRangeRegisters(baksmaliWriter);
                baksmaliWriter.write(", ");
                writable3.write();
                break;
            case 30:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeInvokeRangeRegisters(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeInlineIndex(baksmaliWriter);
                break;
            case 31:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeInvokeRangeRegisters(baksmaliWriter);
                baksmaliWriter.write(", ");
                writeVtableIndex(baksmaliWriter);
                break;
            case 32:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeInvokeRegisters(baksmaliWriter);
                baksmaliWriter.write(", ");
                writable3.write();
                baksmaliWriter.write(", ");
                writable.write();
                break;
            case 33:
                writeOpcode(baksmaliWriter);
                baksmaliWriter.write(32);
                writeInvokeRangeRegisters(baksmaliWriter);
                baksmaliWriter.write(", ");
                writable3.write();
                baksmaliWriter.write(", ");
                writable.write();
                break;
            default:
                return false;
        }
        if (z) {
            baksmaliWriter.write("\nnop");
        }
        return true;
    }

    /* JADX INFO: renamed from: org.jf.baksmali.Adaptors.Format.InstructionMethodItem$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jf$dexlib2$Format;
        static final /* synthetic */ int[] $SwitchMap$org$jf$dexlib2$Opcode;

        static {
            int[] iArr = new int[Format.values().length];
            $SwitchMap$org$jf$dexlib2$Format = iArr;
            try {
                iArr[Format.Format10t.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format20t.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format30t.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format10x.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format11n.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format11x.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format12x.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format20bc.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21c.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31c.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21ih.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21lh.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21s.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31i.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format51l.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21t.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31t.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22b.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22s.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22c.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22cs.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22t.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22x.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format32x.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format23x.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35c.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35mi.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format35ms.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rc.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rmi.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format3rms.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format45cc.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format4rcc.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            int[] iArr2 = new int[Opcode.values().length];
            $SwitchMap$org$jf$dexlib2$Opcode = iArr2;
            try {
                iArr2[Opcode.PACKED_SWITCH.ordinal()] = 1;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.SPARSE_SWITCH.ordinal()] = 2;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Opcode[Opcode.FILL_ARRAY_DATA.ordinal()] = 3;
            } catch (NoSuchFieldError unused36) {
            }
        }
    }

    protected void writeOpcode(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(this.instruction.getOpcode().f1021name);
    }

    protected void writeTargetLabel(BaksmaliWriter baksmaliWriter) throws IOException {
        throw new RuntimeException();
    }

    protected void writeRegister(BaksmaliWriter baksmaliWriter, int i) throws IOException {
        this.methodDef.registerFormatter.writeTo(baksmaliWriter, i);
    }

    protected void writeFirstRegister(BaksmaliWriter baksmaliWriter) throws IOException {
        writeRegister(baksmaliWriter, ((OneRegisterInstruction) this.instruction).getRegisterA());
    }

    protected void writeSecondRegister(BaksmaliWriter baksmaliWriter) throws IOException {
        writeRegister(baksmaliWriter, ((TwoRegisterInstruction) this.instruction).getRegisterB());
    }

    protected void writeThirdRegister(BaksmaliWriter baksmaliWriter) throws IOException {
        writeRegister(baksmaliWriter, ((ThreeRegisterInstruction) this.instruction).getRegisterC());
    }

    protected void writeInvokeRegisters(BaksmaliWriter baksmaliWriter) throws IOException {
        FiveRegisterInstruction fiveRegisterInstruction = (FiveRegisterInstruction) this.instruction;
        int registerCount = fiveRegisterInstruction.getRegisterCount();
        baksmaliWriter.write(123);
        if (registerCount == 1) {
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterC());
        } else if (registerCount == 2) {
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterC());
            baksmaliWriter.write(", ");
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterD());
        } else if (registerCount == 3) {
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterC());
            baksmaliWriter.write(", ");
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterD());
            baksmaliWriter.write(", ");
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterE());
        } else if (registerCount == 4) {
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterC());
            baksmaliWriter.write(", ");
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterD());
            baksmaliWriter.write(", ");
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterE());
            baksmaliWriter.write(", ");
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterF());
        } else if (registerCount == 5) {
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterC());
            baksmaliWriter.write(", ");
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterD());
            baksmaliWriter.write(", ");
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterE());
            baksmaliWriter.write(", ");
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterF());
            baksmaliWriter.write(", ");
            writeRegister(baksmaliWriter, fiveRegisterInstruction.getRegisterG());
        }
        baksmaliWriter.write(125);
    }

    protected void writeInvokeRangeRegisters(BaksmaliWriter baksmaliWriter) throws IOException {
        RegisterRangeInstruction registerRangeInstruction = (RegisterRangeInstruction) this.instruction;
        int registerCount = registerRangeInstruction.getRegisterCount();
        if (registerCount == 0) {
            baksmaliWriter.write("{}");
        } else {
            int startRegister = registerRangeInstruction.getStartRegister();
            this.methodDef.registerFormatter.writeRegisterRange(baksmaliWriter, startRegister, (registerCount + startRegister) - 1);
        }
    }

    protected void writeLiteral(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.writeSignedIntOrLongTo(((WideLiteralInstruction) this.instruction).getWideLiteral());
    }

    protected void writeCommentIfLikelyFloat(BaksmaliWriter baksmaliWriter) throws IOException {
        writeCommentIfLikelyFloat(baksmaliWriter, ((NarrowLiteralInstruction) this.instruction).getNarrowLiteral());
    }

    protected void writeCommentIfLikelyFloat(BaksmaliWriter baksmaliWriter, int i) throws IOException {
        if (NumberUtils.isLikelyFloat(i)) {
            baksmaliWriter.write("    # ");
            float fIntBitsToFloat = Float.intBitsToFloat(i);
            if (fIntBitsToFloat == Float.POSITIVE_INFINITY) {
                baksmaliWriter.write("Float.POSITIVE_INFINITY");
                return;
            }
            if (fIntBitsToFloat == Float.NEGATIVE_INFINITY) {
                baksmaliWriter.write("Float.NEGATIVE_INFINITY");
                return;
            }
            if (Float.isNaN(fIntBitsToFloat)) {
                baksmaliWriter.write("Float.NaN");
                return;
            }
            if (fIntBitsToFloat == Float.MAX_VALUE) {
                baksmaliWriter.write("Float.MAX_VALUE");
                return;
            }
            if (fIntBitsToFloat == 3.1415927f) {
                baksmaliWriter.write("(float)Math.PI");
            } else if (fIntBitsToFloat == 2.7182817f) {
                baksmaliWriter.write("(float)Math.E");
            } else {
                baksmaliWriter.write(Float.toString(fIntBitsToFloat));
                baksmaliWriter.write(102);
            }
        }
    }

    protected void writeCommentIfLikelyDouble(BaksmaliWriter baksmaliWriter) throws IOException {
        writeCommentIfLikelyDouble(baksmaliWriter, ((WideLiteralInstruction) this.instruction).getWideLiteral());
    }

    protected void writeCommentIfLikelyDouble(BaksmaliWriter baksmaliWriter, long j) throws IOException {
        if (NumberUtils.isLikelyDouble(j)) {
            baksmaliWriter.write("    # ");
            double dLongBitsToDouble = Double.longBitsToDouble(j);
            if (dLongBitsToDouble == Double.POSITIVE_INFINITY) {
                baksmaliWriter.write("Double.POSITIVE_INFINITY");
                return;
            }
            if (dLongBitsToDouble == Double.NEGATIVE_INFINITY) {
                baksmaliWriter.write("Double.NEGATIVE_INFINITY");
                return;
            }
            if (Double.isNaN(dLongBitsToDouble)) {
                baksmaliWriter.write("Double.NaN");
                return;
            }
            if (dLongBitsToDouble == Double.MAX_VALUE) {
                baksmaliWriter.write("Double.MAX_VALUE");
                return;
            }
            if (dLongBitsToDouble == 3.141592653589793d) {
                baksmaliWriter.write("Math.PI");
            } else if (dLongBitsToDouble == 2.718281828459045d) {
                baksmaliWriter.write("Math.E");
            } else {
                baksmaliWriter.write(Double.toString(dLongBitsToDouble));
            }
        }
    }

    protected boolean writeCommentIfResourceId(BaksmaliWriter baksmaliWriter) throws IOException {
        return writeCommentIfResourceId(baksmaliWriter, ((NarrowLiteralInstruction) this.instruction).getNarrowLiteral());
    }

    protected boolean writeCommentIfResourceId(BaksmaliWriter baksmaliWriter, int i) throws IOException {
        String comment = this.methodDef.classDef.options.getCommentProvider().getComment(i);
        if (comment == null) {
            return false;
        }
        baksmaliWriter.write("    # ");
        baksmaliWriter.write(comment);
        return true;
    }

    protected void writeFieldOffset(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write("field@0x");
        baksmaliWriter.writeUnsignedLongAsHex(((FieldOffsetInstruction) this.instruction).getFieldOffset());
    }

    protected void writeInlineIndex(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write("inline@");
        baksmaliWriter.writeSignedIntAsDec(((InlineIndexInstruction) this.instruction).getInlineIndex());
    }

    protected void writeVtableIndex(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write("vtable@");
        baksmaliWriter.writeSignedIntAsDec(((VtableIndexInstruction) this.instruction).getVtableIndex());
    }
}
