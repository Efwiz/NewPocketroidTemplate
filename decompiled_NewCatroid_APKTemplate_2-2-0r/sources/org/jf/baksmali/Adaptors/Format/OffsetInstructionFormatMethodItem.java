package org.jf.baksmali.Adaptors.Format;

import java.io.IOException;
import org.jf.baksmali.Adaptors.LabelMethodItem;
import org.jf.baksmali.Adaptors.MethodDefinition;
import org.jf.baksmali.BaksmaliOptions;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.OffsetInstruction;

/* JADX INFO: loaded from: classes6.dex */
public class OffsetInstructionFormatMethodItem extends InstructionMethodItem<OffsetInstruction> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected LabelMethodItem label;

    public OffsetInstructionFormatMethodItem(BaksmaliOptions baksmaliOptions, MethodDefinition methodDefinition, int i, OffsetInstruction offsetInstruction) {
        super(methodDefinition, i, offsetInstruction);
        this.label = new LabelMethodItem(baksmaliOptions, i + offsetInstruction.getCodeOffset(), getLabelPrefix());
        this.label = methodDefinition.getLabelCache().internLabel(this.label);
    }

    @Override // org.jf.baksmali.Adaptors.Format.InstructionMethodItem
    protected void writeTargetLabel(BaksmaliWriter baksmaliWriter) throws IOException {
        this.label.writeTo(baksmaliWriter);
    }

    public LabelMethodItem getLabel() {
        return this.label;
    }

    /* JADX INFO: renamed from: org.jf.baksmali.Adaptors.Format.OffsetInstructionFormatMethodItem$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jf$dexlib2$Format;

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
                $SwitchMap$org$jf$dexlib2$Format[Format.Format21t.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format22t.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.Format31t.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private String getLabelPrefix() {
        Opcode opcode = ((OffsetInstruction) this.instruction).getOpcode();
        switch (AnonymousClass1.$SwitchMap$org$jf$dexlib2$Format[opcode.format.ordinal()]) {
            case 1:
            case 2:
            case 3:
                return "goto_";
            case 4:
            case 5:
                return "cond_";
            case 6:
                if (opcode == Opcode.FILL_ARRAY_DATA) {
                    return "array_";
                }
                if (opcode == Opcode.PACKED_SWITCH) {
                    return "pswitch_data_";
                }
                return "sswitch_data_";
            default:
                return null;
        }
    }
}
