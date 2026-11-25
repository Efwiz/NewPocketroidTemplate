package org.jf.baksmali.Adaptors.Format;

import java.io.IOException;
import java.util.List;
import org.jf.baksmali.Adaptors.MethodDefinition;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.iface.instruction.formats.ArrayPayload;

/* JADX INFO: loaded from: classes6.dex */
public class ArrayDataMethodItem extends InstructionMethodItem<ArrayPayload> {
    public ArrayDataMethodItem(MethodDefinition methodDefinition, int i, ArrayPayload arrayPayload) {
        super(methodDefinition, i, arrayPayload);
    }

    @Override // org.jf.baksmali.Adaptors.Format.InstructionMethodItem, org.jf.baksmali.Adaptors.MethodItem
    public boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        String str;
        int elementWidth = ((ArrayPayload) this.instruction).getElementWidth();
        baksmaliWriter.write(".array-data ");
        baksmaliWriter.writeSignedIntAsDec(((ArrayPayload) this.instruction).getElementWidth());
        baksmaliWriter.write(10);
        baksmaliWriter.indent(4);
        List<Number> arrayElements = ((ArrayPayload) this.instruction).getArrayElements();
        if (elementWidth == 1) {
            str = "t";
        } else if (elementWidth != 2) {
            str = "";
        } else {
            str = "s";
        }
        for (Number number : arrayElements) {
            baksmaliWriter.writeSignedIntOrLongTo(number.longValue());
            baksmaliWriter.write(str);
            if (elementWidth == 8) {
                writeCommentIfLikelyDouble(baksmaliWriter, number.longValue());
            } else if (elementWidth == 4) {
                int iIntValue = number.intValue();
                if (!writeCommentIfResourceId(baksmaliWriter, iIntValue)) {
                    writeCommentIfLikelyFloat(baksmaliWriter, iIntValue);
                }
            }
            baksmaliWriter.write("\n");
        }
        baksmaliWriter.deindent(4);
        baksmaliWriter.write(".end array-data");
        return true;
    }
}
