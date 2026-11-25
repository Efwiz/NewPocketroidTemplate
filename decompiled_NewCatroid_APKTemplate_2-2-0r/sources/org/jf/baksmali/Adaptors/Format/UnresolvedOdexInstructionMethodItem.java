package org.jf.baksmali.Adaptors.Format;

import java.io.IOException;
import org.jf.baksmali.Adaptors.MethodDefinition;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.analysis.UnresolvedOdexInstruction;

/* JADX INFO: loaded from: classes6.dex */
public class UnresolvedOdexInstructionMethodItem extends InstructionMethodItem<UnresolvedOdexInstruction> {
    public UnresolvedOdexInstructionMethodItem(MethodDefinition methodDefinition, int i, UnresolvedOdexInstruction unresolvedOdexInstruction) {
        super(methodDefinition, i, unresolvedOdexInstruction);
    }

    @Override // org.jf.baksmali.Adaptors.Format.InstructionMethodItem, org.jf.baksmali.Adaptors.MethodItem
    public boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        writeThrowTo(baksmaliWriter);
        return true;
    }

    private void writeThrowTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write("#Replaced unresolvable odex instruction with a throw\n");
        baksmaliWriter.write("throw ");
        writeRegister(baksmaliWriter, ((UnresolvedOdexInstruction) this.instruction).objectRegisterNum);
    }
}
