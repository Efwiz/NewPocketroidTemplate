package com.reandroid.dex.data;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IndirectInteger;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.DataKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.ModifiableKeyItem;
import com.reandroid.dex.reference.DataItemIndirectReference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.IterableIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
public class AnnotationsDirectory extends DataItem implements ModifiableKeyItem {
    private static final Creator<DirectoryEntry<FieldDef, AnnotationSet>> CREATOR_FIELDS = new Creator<DirectoryEntry<FieldDef, AnnotationSet>>() { // from class: com.reandroid.dex.data.AnnotationsDirectory.4
        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
        public DirectoryEntry<FieldDef, AnnotationSet>[] newArrayInstance(int i) {
            return new DirectoryEntry[i];
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
        public DirectoryEntry<FieldDef, AnnotationSet> newInstance() {
            return new DirectoryEntry<>(SectionType.ANNOTATION_SET);
        }
    };
    private static final Creator<DirectoryEntry<MethodDef, AnnotationSet>> CREATOR_METHODS = new Creator<DirectoryEntry<MethodDef, AnnotationSet>>() { // from class: com.reandroid.dex.data.AnnotationsDirectory.5
        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
        public DirectoryEntry<MethodDef, AnnotationSet>[] newArrayInstance(int i) {
            return new DirectoryEntry[i];
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
        public DirectoryEntry<MethodDef, AnnotationSet> newInstance() {
            return new DirectoryEntry<>(SectionType.ANNOTATION_SET);
        }
    };
    private static final Creator<DirectoryEntry<MethodDef, AnnotationGroup>> CREATOR_PARAMS = new Creator<DirectoryEntry<MethodDef, AnnotationGroup>>() { // from class: com.reandroid.dex.data.AnnotationsDirectory.6
        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
        public DirectoryEntry<MethodDef, AnnotationGroup>[] newArrayInstance(int i) {
            return new DirectoryEntry[i];
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
        public DirectoryEntry<MethodDef, AnnotationGroup> newInstance() {
            return new DirectoryEntry<>(SectionType.ANNOTATION_GROUP);
        }
    };
    private final DirectoryMap<FieldDef, AnnotationSet> fieldsAnnotationMap;
    private final Header header;
    private final DataKey<AnnotationsDirectory> mKey;
    private final DirectoryMap<MethodDef, AnnotationSet> methodsAnnotationMap;
    private final DirectoryMap<MethodDef, AnnotationGroup> parametersAnnotationMap;

    public AnnotationsDirectory() {
        super(4);
        Header header = new Header();
        this.header = header;
        DirectoryMap<FieldDef, AnnotationSet> directoryMap = new DirectoryMap<>(header.fieldCount, CREATOR_FIELDS);
        this.fieldsAnnotationMap = directoryMap;
        DirectoryMap<MethodDef, AnnotationSet> directoryMap2 = new DirectoryMap<>(header.methodCount, CREATOR_METHODS);
        this.methodsAnnotationMap = directoryMap2;
        DirectoryMap<MethodDef, AnnotationGroup> directoryMap3 = new DirectoryMap<>(header.parameterCount, CREATOR_PARAMS);
        this.parametersAnnotationMap = directoryMap3;
        this.mKey = new DataKey<>(this);
        addChild(0, header);
        addChild(1, directoryMap);
        addChild(2, directoryMap2);
        addChild(3, directoryMap3);
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public DataKey<AnnotationsDirectory> getKey() {
        return this.mKey;
    }

    @Override // com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        merge((AnnotationsDirectory) ((DataKey) key).getItem());
    }

    @Override // com.reandroid.dex.common.SectionItem
    public SectionType<AnnotationsDirectory> getSectionType() {
        return SectionType.ANNOTATION_DIRECTORY;
    }

    public AnnotationSet getOrCreateClassAnnotations() {
        return (AnnotationSet) this.header.classAnnotation.getOrCreate();
    }

    public AnnotationSet getClassAnnotations() {
        return (AnnotationSet) this.header.classAnnotation.getItem();
    }

    public void setClassAnnotations(AnnotationSet annotationSet) {
        this.header.classAnnotation.setItem(annotationSet);
    }

    public boolean isEmpty() {
        return getClassAnnotations() == null || this.fieldsAnnotationMap.isEmpty() || this.methodsAnnotationMap.isEmpty() || this.parametersAnnotationMap.isEmpty();
    }

    public int countField() {
        return this.fieldsAnnotationMap.getCount();
    }

    public int countMethod() {
        return this.methodsAnnotationMap.getCount() + this.parametersAnnotationMap.getCount();
    }

    public void sortFields() {
        this.fieldsAnnotationMap.sort();
    }

    public void sortMethods() {
        this.methodsAnnotationMap.sort();
        this.parametersAnnotationMap.sort();
    }

    public void link(Def<?> def) {
        if (def instanceof FieldDef) {
            linkField((FieldDef) def);
        } else if (def instanceof MethodDef) {
            linkMethod((MethodDef) def);
        }
    }

    public void linkField(FieldDef fieldDef) {
        this.fieldsAnnotationMap.link(fieldDef);
    }

    public void linkMethod(MethodDef methodDef) {
        this.methodsAnnotationMap.link(methodDef);
        this.parametersAnnotationMap.link(methodDef);
    }

    public void remove(Def<?> def) {
        if (def instanceof FieldDef) {
            removeField((FieldDef) def);
        } else if (def instanceof MethodDef) {
            removeMethod((MethodDef) def);
        }
    }

    public void removeField(FieldDef fieldDef) {
        this.fieldsAnnotationMap.remove(fieldDef);
    }

    public void removeMethod(MethodDef methodDef) {
        this.methodsAnnotationMap.remove(methodDef);
        this.parametersAnnotationMap.remove(methodDef);
    }

    public void addAnnotation(Def<?> def, AnnotationSet annotationSet) {
        if (def instanceof FieldDef) {
            addFieldAnnotation((FieldDef) def, annotationSet);
        } else if (def instanceof MethodDef) {
            addMethodAnnotation((MethodDef) def, annotationSet);
        }
    }

    public void addFieldAnnotation(FieldDef fieldDef, AnnotationSet annotationSet) {
        this.fieldsAnnotationMap.add(fieldDef, ensureSameContext(annotationSet));
    }

    public void addMethodAnnotation(MethodDef methodDef, AnnotationSet annotationSet) {
        this.methodsAnnotationMap.add(methodDef, ensureSameContext(annotationSet));
    }

    private AnnotationSet ensureSameContext(AnnotationSet annotationSet) {
        if (isSameContext(annotationSet)) {
            return annotationSet;
        }
        AnnotationSet annotationSet2 = (AnnotationSet) getOrCreateSection(SectionType.ANNOTATION_SET).createItem();
        annotationSet2.merge(annotationSet);
        return annotationSet2;
    }

    public Iterator<AnnotationSet> getAnnotations(Def<?> def) {
        if (def.getClass() == FieldDef.class) {
            return getFieldsAnnotation((FieldDef) def);
        }
        if (def.getClass() == MethodDef.class) {
            return getMethodAnnotation((MethodDef) def);
        }
        throw new IllegalArgumentException("Unknown class type: " + def.getClass());
    }

    public Iterator<AnnotationSet> getFieldsAnnotation(FieldDef fieldDef) {
        return this.fieldsAnnotationMap.getValues(fieldDef);
    }

    public Iterator<AnnotationSet> getFieldsAnnotation(int i) {
        return this.fieldsAnnotationMap.getValues(i);
    }

    public Iterator<AnnotationSet> getMethodAnnotation(int i) {
        return this.methodsAnnotationMap.getValues(i);
    }

    public Iterator<AnnotationSet> getMethodAnnotation(MethodDef methodDef) {
        return this.methodsAnnotationMap.getValues(methodDef);
    }

    public Iterator<AnnotationGroup> getParameterAnnotation(MethodDef methodDef) {
        return this.parametersAnnotationMap.getValues(methodDef);
    }

    public Iterator<DirectoryEntry<MethodDef, AnnotationGroup>> getParameterEntries(MethodDef methodDef) {
        return this.parametersAnnotationMap.getEntries(methodDef);
    }

    public Iterator<AnnotationGroup> getParameterAnnotation(int i) {
        return this.parametersAnnotationMap.getValues(i);
    }

    public Iterator<AnnotationSet> getParameterAnnotation(MethodDef methodDef, final int i) {
        return ComputeIterator.of(getParameterAnnotation(methodDef), new Function() { // from class: com.reandroid.dex.data.AnnotationsDirectory$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AnnotationGroup) obj).getItem(i);
            }
        });
    }

    public Iterator<AnnotationSet> getParameterAnnotation(int i, final int i2) {
        return ComputeIterator.of(getParameterAnnotation(i), new Function() { // from class: com.reandroid.dex.data.AnnotationsDirectory$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AnnotationGroup) obj).getItem(i2);
            }
        });
    }

    public AnnotationSet getOrCreateParameterAnnotation(MethodDef methodDef, int i) {
        return getOrCreateParameterAnnotationGroup(methodDef).getOrCreateAt(i);
    }

    private AnnotationGroup getOrCreateParameterAnnotationGroup(MethodDef methodDef) {
        Iterator<VALUE> values = this.parametersAnnotationMap.getValues(methodDef);
        if (values.hasNext()) {
            return (AnnotationGroup) values.next();
        }
        AnnotationGroup annotationGroup = (AnnotationGroup) getOrCreateSection(SectionType.ANNOTATION_GROUP).createItem();
        this.parametersAnnotationMap.add(methodDef, annotationGroup);
        return annotationGroup;
    }

    public AnnotationSet createNewParameterAnnotation(MethodDef methodDef, int i) {
        return getEmptyParameterAnnotationGroup(methodDef, i).getOrCreateAt(i);
    }

    private AnnotationGroup getEmptyParameterAnnotationGroup(MethodDef methodDef, int i) {
        Iterator<VALUE> values = this.parametersAnnotationMap.getValues(methodDef);
        while (values.hasNext()) {
            AnnotationGroup annotationGroup = (AnnotationGroup) values.next();
            if (annotationGroup.getItem(i) == null) {
                return annotationGroup;
            }
        }
        AnnotationGroup annotationGroup2 = (AnnotationGroup) getOrCreateSection(SectionType.ANNOTATION_GROUP).createItem();
        this.parametersAnnotationMap.add(methodDef, annotationGroup2);
        return annotationGroup2;
    }

    public void replaceKeys(Key key, Key key2) {
        AnnotationSet classAnnotations = getClassAnnotations();
        if (classAnnotations != null) {
            classAnnotations.replaceKeys(key, key2);
        }
        Iterator<VALUE> values = this.fieldsAnnotationMap.getValues();
        while (values.hasNext()) {
            ((AnnotationSet) values.next()).replaceKeys(key, key2);
        }
        Iterator<VALUE> values2 = this.methodsAnnotationMap.getValues();
        while (values2.hasNext()) {
            ((AnnotationSet) values2.next()).replaceKeys(key, key2);
        }
        Iterator<VALUE> values3 = this.parametersAnnotationMap.getValues();
        while (values3.hasNext()) {
            ((AnnotationGroup) values3.next()).replaceKeys(key, key2);
        }
    }

    @Override // com.reandroid.dex.common.SectionItem, com.reandroid.dex.common.EditableItem
    public void editInternal(Block block) {
        this.header.editInternal(block);
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        Iterator<IdItem> itUsedIds;
        AnnotationSet classAnnotations = getClassAnnotations();
        if (classAnnotations == null) {
            itUsedIds = EmptyIterator.of();
        } else {
            itUsedIds = classAnnotations.usedIds();
        }
        return CombiningIterator.four(itUsedIds, new IterableIterator<AnnotationSet, IdItem>(this.fieldsAnnotationMap.getValues()) { // from class: com.reandroid.dex.data.AnnotationsDirectory.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IdItem> iterator(AnnotationSet annotationSet) {
                return annotationSet.usedIds();
            }
        }, new IterableIterator<AnnotationSet, IdItem>(this.methodsAnnotationMap.getValues()) { // from class: com.reandroid.dex.data.AnnotationsDirectory.2
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IdItem> iterator(AnnotationSet annotationSet) {
                return annotationSet.usedIds();
            }
        }, new IterableIterator<AnnotationGroup, IdItem>(this.parametersAnnotationMap.getValues()) { // from class: com.reandroid.dex.data.AnnotationsDirectory.3
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IdItem> iterator(AnnotationGroup annotationGroup) {
                return annotationGroup.usedIds();
            }
        });
    }

    @Override // com.reandroid.dex.common.SectionItemContainer
    protected void onPreRefresh() {
        this.header.refresh();
        super.onPreRefresh();
    }

    public void merge(AnnotationsDirectory annotationsDirectory) {
        if (annotationsDirectory == this) {
            return;
        }
        this.header.merge(annotationsDirectory.header);
        this.fieldsAnnotationMap.merge(annotationsDirectory.fieldsAnnotationMap);
        this.methodsAnnotationMap.merge(annotationsDirectory.methodsAnnotationMap);
        this.parametersAnnotationMap.merge(annotationsDirectory.parametersAnnotationMap);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AnnotationsDirectory annotationsDirectory = (AnnotationsDirectory) obj;
        return Objects.equals(this.header, annotationsDirectory.header) && Objects.equals(this.fieldsAnnotationMap, annotationsDirectory.fieldsAnnotationMap) && Objects.equals(this.methodsAnnotationMap, annotationsDirectory.methodsAnnotationMap) && Objects.equals(this.parametersAnnotationMap, annotationsDirectory.parametersAnnotationMap);
    }

    public int hashCode() {
        return ((((((this.header.hashCode() + 31) * 31) + this.fieldsAnnotationMap.hashCode()) * 31) + this.methodsAnnotationMap.hashCode()) * 31) + this.parametersAnnotationMap.hashCode();
    }

    public String toString() {
        return this.header + ", fields=" + this.fieldsAnnotationMap + ", methods=" + this.methodsAnnotationMap + ", parameters=" + this.parametersAnnotationMap;
    }

    static class Header extends SectionItem implements BlockRefresh {
        final DataItemIndirectReference<AnnotationSet> classAnnotation;
        final IndirectInteger fieldCount;
        final IndirectInteger methodCount;
        final IndirectInteger parameterCount;

        @Override // com.reandroid.dex.common.SectionItem, com.reandroid.dex.common.EditableItem
        public void editInternal(Block block) {
        }

        public Header() {
            super(16);
            this.classAnnotation = new DataItemIndirectReference<>(SectionType.ANNOTATION_SET, this, 0, UsageMarker.USAGE_ANNOTATION);
            this.fieldCount = new IndirectInteger(this, 4);
            this.methodCount = new IndirectInteger(this, 8);
            this.parameterCount = new IndirectInteger(this, 12);
        }

        public boolean isEmpty() {
            return this.classAnnotation.get() == 0 && this.fieldCount.get() == 0 && this.methodCount.get() == 0 && this.parameterCount.get() == 0;
        }

        @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
        public void onReadBytes(BlockReader blockReader) throws IOException {
            super.onReadBytes(blockReader);
            cacheItems();
        }

        private void cacheItems() {
            this.classAnnotation.pullItem();
        }

        @Override // com.reandroid.arsc.base.BlockRefresh
        public void refresh() {
            this.classAnnotation.refresh();
        }

        public void merge(Header header) {
            this.classAnnotation.setItem(header.classAnnotation.getKey());
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return Objects.equals(this.classAnnotation.getItem(), ((Header) obj).classAnnotation.getItem());
        }

        public int hashCode() {
            DataItem item = this.classAnnotation.getItem();
            if (item != null) {
                return 31 + item.hashCode();
            }
            return 31;
        }

        public String toString() {
            return "class=" + this.classAnnotation + ", fields=" + this.fieldCount + ", methods=" + this.methodCount + ", parameters=" + this.parameterCount;
        }
    }
}
