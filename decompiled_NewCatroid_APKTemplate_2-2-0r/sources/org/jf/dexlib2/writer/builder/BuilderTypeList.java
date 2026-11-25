package org.jf.dexlib2.writer.builder;

import java.util.AbstractList;
import java.util.List;
import org.jf.util.collection.EmptyList;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderTypeList extends AbstractList<BuilderTypeReference> {
    static final BuilderTypeList EMPTY = new BuilderTypeList(EmptyList.of());
    int offset = 0;
    final List<? extends BuilderTypeReference> types;

    public BuilderTypeList(List<? extends BuilderTypeReference> list) {
        this.types = list;
    }

    @Override // java.util.AbstractList, java.util.List
    public BuilderTypeReference get(int i) {
        return this.types.get(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.types.size();
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int i) {
        this.offset = i;
    }
}
