package org.jf.dexlib2.writer.builder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import org.jf.dexlib2.writer.TypeListSection;
import org.jf.util.collection.Iterables;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
class BuilderTypeListPool extends BaseBuilderPool implements TypeListSection<BuilderTypeReference, BuilderTypeList> {
    private final ConcurrentMap<List<? extends CharSequence>, BuilderTypeList> internedItems;

    public BuilderTypeListPool(DexBuilder dexBuilder) {
        super(dexBuilder);
        this.internedItems = new ConcurrentHashMap();
    }

    public BuilderTypeList internTypeList(List<? extends CharSequence> list) {
        if (list == null || list.size() == 0) {
            return BuilderTypeList.EMPTY;
        }
        BuilderTypeList builderTypeList = this.internedItems.get(list);
        if (builderTypeList != null) {
            return builderTypeList;
        }
        BuilderTypeList builderTypeList2 = new BuilderTypeList(ListUtil.copyOf(Iterables.transform(list, new Function<CharSequence, BuilderTypeReference>() { // from class: org.jf.dexlib2.writer.builder.BuilderTypeListPool.1
            @Override // java.util.function.Function
            public BuilderTypeReference apply(CharSequence charSequence) {
                return ((BuilderTypePool) BuilderTypeListPool.this.dexBuilder.typeSection).internType(charSequence.toString());
            }
        })));
        BuilderTypeList builderTypeListPutIfAbsent = this.internedItems.putIfAbsent(builderTypeList2, builderTypeList2);
        return builderTypeListPutIfAbsent == null ? builderTypeList2 : builderTypeListPutIfAbsent;
    }

    @Override // org.jf.dexlib2.writer.TypeListSection, org.jf.dexlib2.writer.NullableOffsetSection
    public int getNullableItemOffset(BuilderTypeList builderTypeList) {
        if (builderTypeList == null || builderTypeList.size() == 0) {
            return 0;
        }
        return builderTypeList.offset;
    }

    @Override // org.jf.dexlib2.writer.TypeListSection
    public Collection<? extends BuilderTypeReference> getTypes(BuilderTypeList builderTypeList) {
        return builderTypeList == null ? BuilderTypeList.EMPTY : builderTypeList.types;
    }

    @Override // org.jf.dexlib2.writer.OffsetSection
    public int getItemOffset(BuilderTypeList builderTypeList) {
        return builderTypeList.offset;
    }

    @Override // org.jf.dexlib2.writer.OffsetSection
    public Collection<? extends Map.Entry<? extends BuilderTypeList, Integer>> getItems() {
        return new BuilderMapEntryCollection<BuilderTypeList>(this.internedItems.values()) { // from class: org.jf.dexlib2.writer.builder.BuilderTypeListPool.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            public int getValue(BuilderTypeList builderTypeList) {
                return builderTypeList.offset;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
            public int setValue(BuilderTypeList builderTypeList, int i) {
                int i2 = builderTypeList.offset;
                builderTypeList.offset = i;
                return i2;
            }
        };
    }
}
