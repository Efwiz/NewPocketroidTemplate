package org.jf.dexlib2.immutable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.extra.DexMarker;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.DexFile;
import org.jf.util.ImmutableUtils;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableDexFile implements DexFile {
    protected final Set<? extends ImmutableClassDef> classes;
    private List<DexMarker> markerList;
    private final Opcodes opcodes;

    public ImmutableDexFile(Opcodes opcodes, Collection<? extends ClassDef> collection) {
        this.classes = ImmutableClassDef.immutableSetOf(collection);
        this.opcodes = opcodes;
    }

    public ImmutableDexFile(Opcodes opcodes, Set<? extends ImmutableClassDef> set) {
        this.classes = ImmutableUtils.nullToEmptySet(set);
        this.opcodes = opcodes;
    }

    public static ImmutableDexFile of(DexFile dexFile) {
        if (dexFile instanceof ImmutableDexFile) {
            return (ImmutableDexFile) dexFile;
        }
        ImmutableDexFile immutableDexFile = new ImmutableDexFile(dexFile.getOpcodes(), dexFile.getClasses());
        immutableDexFile.setMarkerList(dexFile.getMarkers());
        return immutableDexFile;
    }

    public void setMarkerList(List<DexMarker> list) {
        this.markerList = list;
    }

    @Override // org.jf.dexlib2.iface.DexFile
    public List<DexMarker> getMarkers() {
        if (this.markerList == null) {
            this.markerList = new ArrayList();
        }
        return this.markerList;
    }

    @Override // org.jf.dexlib2.iface.DexFile
    public Set<? extends ImmutableClassDef> getClasses() {
        return this.classes;
    }

    @Override // org.jf.dexlib2.iface.DexFile
    public Opcodes getOpcodes() {
        return this.opcodes;
    }
}
