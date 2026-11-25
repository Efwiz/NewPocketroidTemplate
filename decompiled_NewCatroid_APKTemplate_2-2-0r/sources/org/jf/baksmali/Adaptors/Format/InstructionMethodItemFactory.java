package org.jf.baksmali.Adaptors.Format;

import org.jf.baksmali.Adaptors.MethodDefinition;
import org.jf.dexlib2.Format;
import org.jf.dexlib2.analysis.UnresolvedOdexInstruction;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.OffsetInstruction;
import org.jf.dexlib2.iface.instruction.formats.ArrayPayload;
import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload;
import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload;

/* JADX INFO: loaded from: classes6.dex */
public class InstructionMethodItemFactory {
    private InstructionMethodItemFactory() {
    }

    public static InstructionMethodItem makeInstructionFormatMethodItem(MethodDefinition methodDefinition, int i, Instruction instruction) {
        if (instruction instanceof OffsetInstruction) {
            return new OffsetInstructionFormatMethodItem(methodDefinition.classDef.options, methodDefinition, i, (OffsetInstruction) instruction);
        }
        if (instruction instanceof UnresolvedOdexInstruction) {
            return new UnresolvedOdexInstructionMethodItem(methodDefinition, i, (UnresolvedOdexInstruction) instruction);
        }
        int i2 = AnonymousClass1.$SwitchMap$org$jf$dexlib2$Format[instruction.getOpcode().format.ordinal()];
        if (i2 == 1) {
            return new ArrayDataMethodItem(methodDefinition, i, (ArrayPayload) instruction);
        }
        if (i2 == 2) {
            return new PackedSwitchMethodItem(methodDefinition, i, (PackedSwitchPayload) instruction);
        }
        if (i2 == 3) {
            return new SparseSwitchMethodItem(methodDefinition, i, (SparseSwitchPayload) instruction);
        }
        return new InstructionMethodItem(methodDefinition, i, instruction);
    }

    /* JADX INFO: renamed from: org.jf.baksmali.Adaptors.Format.InstructionMethodItemFactory$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jf$dexlib2$Format;

        static {
            int[] iArr = new int[Format.values().length];
            $SwitchMap$org$jf$dexlib2$Format = iArr;
            try {
                iArr[Format.ArrayPayload.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.PackedSwitchPayload.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jf$dexlib2$Format[Format.SparseSwitchPayload.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }
}
