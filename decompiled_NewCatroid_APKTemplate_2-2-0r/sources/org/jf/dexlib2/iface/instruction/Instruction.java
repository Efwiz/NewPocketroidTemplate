package org.jf.dexlib2.iface.instruction;

import org.jf.dexlib2.Opcode;

/* JADX INFO: loaded from: classes6.dex */
public interface Instruction {
    int getCodeUnits();

    Opcode getOpcode();
}
