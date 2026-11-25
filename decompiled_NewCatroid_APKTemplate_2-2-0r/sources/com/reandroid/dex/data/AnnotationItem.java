package com.reandroid.dex.data;

import com.reandroid.arsc.item.ByteItem;
import com.reandroid.dex.base.CountedList;
import com.reandroid.dex.base.Ule128Item;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.common.AnnotationVisibility;
import com.reandroid.dex.common.SectionTool;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.id.TypeId;
import com.reandroid.dex.key.DataKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.ModifiableKeyItem;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.reference.Ule128IdItemReference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliRegion;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliAnnotationElement;
import com.reandroid.dex.smali.model.SmaliAnnotationItem;
import com.reandroid.dex.value.DexValueBlock;
import com.reandroid.dex.value.DexValueType;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.IterableIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Predicate;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: loaded from: classes11.dex */
public class AnnotationItem extends DataItem implements Comparable<AnnotationItem>, Iterable<AnnotationElement>, ModifiableKeyItem, SmaliRegion {
    private final CountedList<AnnotationElement> annotationElements;
    private final DataKey<AnnotationItem> mItemKey;
    private final boolean mValueEntry;
    private final Ule128IdItemReference<TypeId> typeId;
    private final ByteItem visibility;

    public AnnotationItem(boolean z) {
        super(z ? 3 : 4);
        this.mValueEntry = z;
        ByteItem byteItem = z ? null : new ByteItem();
        this.visibility = byteItem;
        Ule128IdItemReference<TypeId> ule128IdItemReference = new Ule128IdItemReference<>(SectionType.TYPE_ID, UsageMarker.USAGE_ANNOTATION);
        this.typeId = ule128IdItemReference;
        Ule128Item ule128Item = new Ule128Item();
        CountedList<AnnotationElement> countedList = new CountedList<>(ule128Item, AnnotationElement.CREATOR);
        this.annotationElements = countedList;
        int i = 0;
        if (!z) {
            addChild(0, byteItem);
            i = 1;
        }
        this.mItemKey = new DataKey<>(this);
        addChild(i, ule128IdItemReference);
        addChild(i + 1, ule128Item);
        addChild(i + 2, countedList);
    }

    public AnnotationItem() {
        this(false);
    }

    @Override // com.reandroid.dex.common.SectionItem
    public SectionType<AnnotationItem> getSectionType() {
        return SectionType.ANNOTATION_ITEM;
    }

    public boolean removeIf(Predicate<AnnotationElement> predicate) {
        return this.annotationElements.removeIf(predicate);
    }

    public void remove(AnnotationElement annotationElement) {
        this.annotationElements.remove(annotationElement);
    }

    public AnnotationElement getOrCreateElement(String str) {
        AnnotationElement element = getElement(str);
        if (element != null) {
            return element;
        }
        AnnotationElement annotationElementCreateNewElement = createNewElement();
        annotationElementCreateNewElement.setName(str);
        annotationElementCreateNewElement.getOrCreateValue(DexValueType.NULL);
        return annotationElementCreateNewElement;
    }

    public AnnotationElement createNewElement() {
        return this.annotationElements.createNext();
    }

    public boolean containsName(String str) {
        Iterator<AnnotationElement> it = iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().getName())) {
                return true;
            }
        }
        return false;
    }

    public DexValueBlock<?> getElementValue(String str) {
        AnnotationElement element = getElement(str);
        if (element != null) {
            return element.getValue();
        }
        return null;
    }

    public AnnotationElement getElement(String str) {
        for (AnnotationElement annotationElement : this) {
            if (str.equals(annotationElement.getName())) {
                return annotationElement;
            }
        }
        return null;
    }

    public String[] getNames() {
        CountedList<AnnotationElement> countedList = this.annotationElements;
        int size = countedList.size();
        if (size == 0) {
            return null;
        }
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = countedList.get(i).getName();
        }
        return strArr;
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public DataKey<AnnotationItem> getKey() {
        return this.mItemKey;
    }

    @Override // com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        merge((AnnotationItem) ((DataKey) key).getItem());
    }

    @Override // java.lang.Iterable
    public Iterator<AnnotationElement> iterator() {
        return this.annotationElements.iterator();
    }

    public Iterator<AnnotationElement> clonedIterator() {
        return this.annotationElements.clonedIterator();
    }

    public int getElementsCount() {
        return this.annotationElements.size();
    }

    public boolean isEmpty() {
        return this.annotationElements.size() == 0;
    }

    public AnnotationElement getElement(int i) {
        return this.annotationElements.get(i);
    }

    public boolean isValueEntry() {
        return this.mValueEntry;
    }

    public AnnotationVisibility getVisibility() {
        if (isValueEntry()) {
            return null;
        }
        return AnnotationVisibility.valueOf(this.visibility.unsignedInt());
    }

    public void setVisibility(AnnotationVisibility annotationVisibility) {
        setVisibility(annotationVisibility != null ? annotationVisibility.getValue() : 0);
    }

    public void setVisibility(int i) {
        ByteItem byteItem = this.visibility;
        if (byteItem != null) {
            byteItem.set((byte) i);
        }
    }

    public int getVisibilityValue() {
        ByteItem byteItem = this.visibility;
        if (byteItem == null) {
            return -1;
        }
        return byteItem.unsignedInt();
    }

    public String getTypeName() {
        TypeId typeId = getTypeId();
        if (typeId != null) {
            return typeId.getName();
        }
        return null;
    }

    public TypeKey getTypeKey() {
        return (TypeKey) this.typeId.getKey();
    }

    public TypeId getTypeId() {
        return (TypeId) this.typeId.getItem();
    }

    public void setType(String str) {
        setType(TypeKey.create(str));
    }

    public void setType(TypeKey typeKey) {
        this.typeId.setItem(typeKey);
    }

    public void replaceKeys(Key key, Key key2) {
        Iterator<AnnotationElement> it = iterator();
        while (it.hasNext()) {
            it.next().replaceKeys(key, key2);
        }
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        if (getTypeKey().getTypeName().startsWith("Ldalvik/annotation/")) {
            return EmptyIterator.of();
        }
        return CombiningIterator.singleOne(getTypeId(), new IterableIterator<AnnotationElement, IdItem>(iterator()) { // from class: com.reandroid.dex.data.AnnotationItem.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IdItem> iterator(AnnotationElement annotationElement) {
                return annotationElement.usedIds();
            }
        });
    }

    public void merge(AnnotationItem annotationItem) {
        if (annotationItem == this) {
            return;
        }
        setVisibility(annotationItem.getVisibilityValue());
        setType(annotationItem.getTypeKey());
        this.annotationElements.ensureCapacity(annotationItem.getElementsCount());
        Iterator<AnnotationElement> it = annotationItem.iterator();
        while (it.hasNext()) {
            createNewElement().merge(it.next());
        }
    }

    public void fromSmali(SmaliAnnotationItem smaliAnnotationItem) {
        setType(smaliAnnotationItem.getType());
        setVisibility(smaliAnnotationItem.getVisibility());
        Iterator<SmaliAnnotationElement> it = smaliAnnotationItem.iterator();
        while (it.hasNext()) {
            createNewElement().fromSmali(it.next());
        }
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getSmaliDirective().append(smaliWriter);
        smaliWriter.appendOptional(getVisibility());
        getTypeId().append(smaliWriter);
        smaliWriter.indentPlus();
        smaliWriter.appendAllWithDoubleNewLine(iterator());
        smaliWriter.indentMinus();
        getSmaliDirective().appendEnd(smaliWriter);
    }

    @Override // com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        if (isValueEntry()) {
            return SmaliDirective.SUB_ANNOTATION;
        }
        return SmaliDirective.ANNOTATION;
    }

    @Override // java.lang.Comparable
    public int compareTo(AnnotationItem annotationItem) {
        if (annotationItem == null) {
            return -1;
        }
        if (annotationItem == this) {
            return 0;
        }
        int iCompareIdx = SectionTool.compareIdx(getTypeId(), annotationItem.getTypeId());
        return iCompareIdx != 0 ? iCompareIdx : CompareUtil.compare(getVisibilityValue(), annotationItem.getVisibilityValue());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AnnotationItem annotationItem = (AnnotationItem) obj;
        if (ObjectsUtil.equals(getTypeName(), annotationItem.getTypeName()) && getVisibilityValue() == annotationItem.getVisibilityValue()) {
            return this.annotationElements.equals(annotationItem.annotationElements);
        }
        return false;
    }

    public int hashCode() {
        return ObjectsUtil.hash(getVisibility(), getTypeName(), this.annotationElements);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT);
        sb.append(getTypeName());
        boolean z = false;
        for (AnnotationElement annotationElement : this) {
            if (z) {
                sb.append(", ");
            } else {
                sb.append('(');
            }
            sb.append(annotationElement);
            z = true;
        }
        if (z) {
            sb.append(')');
        }
        return sb.toString();
    }
}
