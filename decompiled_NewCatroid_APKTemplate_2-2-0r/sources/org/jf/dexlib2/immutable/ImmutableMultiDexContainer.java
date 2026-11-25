package org.jf.dexlib2.immutable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jf.dexlib2.iface.MultiDexContainer;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableMultiDexContainer implements MultiDexContainer<ImmutableDexFile> {
    private final Map<String, ImmutableDexEntry> entries;

    public ImmutableMultiDexContainer(Map<String, ImmutableDexFile> map) {
        HashMap map2 = new HashMap();
        for (Map.Entry<String, ImmutableDexFile> entry : map.entrySet()) {
            ImmutableDexEntry immutableDexEntry = new ImmutableDexEntry(entry.getKey(), entry.getValue());
            map2.put(immutableDexEntry.getEntryName(), immutableDexEntry);
        }
        this.entries = map2;
    }

    @Override // org.jf.dexlib2.iface.MultiDexContainer
    public List<String> getDexEntryNames() {
        return ListUtil.copyOf((Collection) this.entries.keySet());
    }

    @Override // org.jf.dexlib2.iface.MultiDexContainer
    public ImmutableDexEntry getEntry(String str) {
        return this.entries.get(str);
    }

    public class ImmutableDexEntry implements MultiDexContainer.DexEntry<ImmutableDexFile> {
        private final ImmutableDexFile dexFile;
        private final String entryName;

        protected ImmutableDexEntry(String str, ImmutableDexFile immutableDexFile) {
            this.entryName = str;
            this.dexFile = immutableDexFile;
        }

        @Override // org.jf.dexlib2.iface.MultiDexContainer.DexEntry
        public String getEntryName() {
            return this.entryName;
        }

        @Override // org.jf.dexlib2.iface.MultiDexContainer.DexEntry
        public ImmutableDexFile getDexFile() {
            return this.dexFile;
        }

        @Override // org.jf.dexlib2.iface.MultiDexContainer.DexEntry
        public MultiDexContainer<? extends ImmutableDexFile> getContainer() {
            return ImmutableMultiDexContainer.this;
        }
    }
}
