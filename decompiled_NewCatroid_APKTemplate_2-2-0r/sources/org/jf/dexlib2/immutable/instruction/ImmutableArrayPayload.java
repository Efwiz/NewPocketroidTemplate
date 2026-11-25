package org.jf.dexlib2.immutable.instruction;

import java.util.Collection;
import java.util.List;
import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.ArrayPayload;
import org.jf.dexlib2.util.Preconditions;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableArrayPayload extends ImmutableInstruction implements ArrayPayload {
    public static final Opcode OPCODE = Opcode.ARRAY_PAYLOAD;
    protected final List<Number> arrayElements;
    protected final int elementWidth;

    public ImmutableArrayPayload(int i, List<Number> list) {
        super(OPCODE);
        this.elementWidth = Preconditions.checkArrayPayloadElementWidth(i);
        this.arrayElements = Preconditions.checkArrayPayloadElements(i, list == null ? ListUtil.of() : ListUtil.copyOf((Collection) list));
    }

    public static ImmutableArrayPayload of(ArrayPayload arrayPayload) {
        if (arrayPayload instanceof ImmutableArrayPayload) {
            return (ImmutableArrayPayload) arrayPayload;
        }
        return new ImmutableArrayPayload(arrayPayload.getElementWidth(), arrayPayload.getArrayElements());
    }

    @Override // org.jf.dexlib2.iface.instruction.formats.ArrayPayload
    public int getElementWidth() {
        return this.elementWidth;
    }

    @Override // org.jf.dexlib2.iface.instruction.formats.ArrayPayload
    public List<Number> getArrayElements() {
        return this.arrayElements;
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction, org.jf.dexlib2.iface.instruction.Instruction
    public int getCodeUnits() {
        return (((this.elementWidth * this.arrayElements.size()) + 1) / 2) + 4;
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    public Format getFormat() {
        return OPCODE.format;
    }
}
