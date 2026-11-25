package org.jf.dexlib2.iface;

import java.util.List;
import java.util.Set;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.extra.DexMarker;

/* JADX INFO: loaded from: classes6.dex */
public interface DexFile {
    Set<? extends ClassDef> getClasses();

    List<DexMarker> getMarkers();

    Opcodes getOpcodes();
}
