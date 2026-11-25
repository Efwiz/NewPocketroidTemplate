package com.reandroid.arsc.model;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.item.SpecString;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResConfig;
import com.reandroid.arsc.value.ResValue;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.arsc.value.attribute.AttributeBag;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.FilterIterator;
import com.reandroid.utils.collection.IterableIterator;
import com.reandroid.utils.collection.SingleIterator;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes10.dex */
public class ResourceEntry implements Iterable<Entry> {
    private final PackageBlock packageBlock;
    private final int resourceId;

    public ResourceEntry(PackageBlock packageBlock, int i) {
        this.resourceId = i;
        this.packageBlock = packageBlock;
    }

    public Iterator<String> getStringValues() {
        return getStringValues(iterator());
    }

    Iterator<String> getStringValues(Iterator<Entry> it) {
        return new IterableIterator<Entry, String>(it) { // from class: com.reandroid.arsc.model.ResourceEntry.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<String> iterator(Entry entry) {
                return ResourceEntry.this.getStringValues(entry);
            }
        };
    }

    Iterator<String> getStringValues(Entry entry) {
        ResValue resValue = entry.getResValue();
        if (resValue == null) {
            return EmptyIterator.of();
        }
        ValueType valueType = resValue.getValueType();
        if (valueType == ValueType.STRING) {
            return SingleIterator.of(resValue.getValueAsString());
        }
        if (!valueType.isReference()) {
            return EmptyIterator.of();
        }
        TableBlock tableBlock = getPackageBlock().getTableBlock();
        if (tableBlock == null) {
            return EmptyIterator.of();
        }
        return getStringValues(tableBlock.resolveReference(resValue.getData()).iterator());
    }

    public ResourceEntry previous() {
        int resourceId = getResourceId();
        int i = 65535 & resourceId;
        if (i == 0) {
            return null;
        }
        return new ResourceEntry(getPackageBlock(), (resourceId & (-65536)) | (i - 1));
    }

    public ResourceEntry next() {
        SpecTypePair specTypePair;
        int resourceId = getResourceId();
        int i = resourceId & 65535;
        if (i == 65535 || (specTypePair = getPackageBlock().getSpecTypePair((resourceId >> 16) & 255)) == null) {
            return null;
        }
        return specTypePair.getResource(i + 1);
    }

    public ResourceEntry getLast() {
        SpecTypePair specTypePair = getPackageBlock().getSpecTypePair((getResourceId() >> 16) & 255);
        if (specTypePair != null) {
            return specTypePair.getResource(specTypePair.getHighestEntryId());
        }
        return null;
    }

    public ResourceEntry resolveReference() {
        HashSet hashSet = new HashSet();
        hashSet.add(0);
        hashSet.add(Integer.valueOf(getResourceId()));
        ResourceEntry resourceEntryResolveReference = resolveReference(hashSet);
        return resourceEntryResolveReference != null ? resourceEntryResolveReference : this;
    }

    private ResourceEntry resolveReference(Set<Integer> set) {
        ResValue resValue;
        ValueType valueType;
        ResourceEntry resourceEntryResolveReference;
        Entry entry = get();
        if (entry == null || (resValue = entry.getResValue()) == null || (valueType = resValue.getValueType()) == null || !valueType.isReference()) {
            return this;
        }
        int data = resValue.getData();
        if (data == 0 || set.contains(Integer.valueOf(data))) {
            return null;
        }
        set.add(Integer.valueOf(data));
        ResourceEntry resourceEntry = getResourceEntry(data);
        return (resourceEntry == null || (resourceEntryResolveReference = resourceEntry.resolveReference(set)) == null) ? resourceEntry : resourceEntryResolveReference;
    }

    private ResourceEntry getResourceEntry(int i) {
        PackageBlock packageBlock = getPackageBlock();
        TableBlock tableBlock = packageBlock.getTableBlock();
        if (tableBlock == null) {
            return null;
        }
        return tableBlock.getResource(packageBlock, i);
    }

    public Entry getOrCreate(String str) {
        return getOrCreate(ResConfig.parse(str));
    }

    public Entry getOrCreate(ResConfig resConfig) {
        int resourceId = getResourceId();
        Entry orCreateEntry = getPackageBlock().getOrCreateEntry((byte) ((resourceId >> 16) & 255), (short) (resourceId & 65535), resConfig);
        String name2 = getName();
        if (name2 != null && orCreateEntry.getName() == null) {
            orCreateEntry.setName(name2, true);
        }
        return orCreateEntry;
    }

    public Entry get(String str) {
        return get(ResConfig.parse(str));
    }

    public Entry get(ResConfig resConfig) {
        for (Entry entry : this) {
            if (resConfig.equals(entry.getResConfig())) {
                return entry;
            }
        }
        return null;
    }

    public int getConfigsCount() {
        return CollectionUtil.count(iterator(true));
    }

    public Entry getEqualsOrMoreSpecific(ResConfig resConfig) {
        Entry entry = null;
        for (Entry entry2 : this) {
            if (resConfig.equals(entry2.getResConfig())) {
                return entry2;
            }
            if (entry == null && entry2.getResConfig().isEqualOrMoreSpecificThan(resConfig)) {
                entry = entry2;
            }
        }
        return entry;
    }

    public Entry get() {
        Entry entry = null;
        for (Entry entry2 : this) {
            if (entry2.isDefault()) {
                return entry2;
            }
            if (entry == null) {
                entry = entry2;
            }
        }
        return entry;
    }

    public Entry any() {
        Iterator<Entry> it = iterator(true);
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }

    public boolean isEmpty() {
        return CollectionUtil.isEmpty(iterator(true));
    }

    public boolean isDefined() {
        return iterator(true).hasNext();
    }

    public boolean isDeclared() {
        return getName() != null;
    }

    public PackageBlock getPackageBlock() {
        return this.packageBlock;
    }

    public boolean isContext(Block block) {
        if (block == null) {
            return false;
        }
        if (block instanceof TableBlock) {
            PackageBlock packageBlock = getPackageBlock();
            return packageBlock != null && block == packageBlock.getTableBlock();
        }
        if (block instanceof PackageBlock) {
            return isContext((PackageBlock) block);
        }
        return isContext((PackageBlock) block.getParentInstance(PackageBlock.class));
    }

    public boolean isContext(PackageBlock packageBlock) {
        PackageBlock packageBlock2;
        if (packageBlock == null || (packageBlock2 = getPackageBlock()) == null) {
            return false;
        }
        return packageBlock2 == packageBlock || packageBlock2.getTableBlock() == packageBlock.getTableBlock();
    }

    public int getResourceId() {
        return this.resourceId;
    }

    public String getPackageName() {
        return getPackageBlock().getName();
    }

    public String getType() {
        return getPackageBlock().typeNameOf((getResourceId() >> 16) & 255);
    }

    public void setName(String str) {
        SpecString name2 = null;
        boolean z = false;
        for (Entry entry : this) {
            if (name2 != null) {
                entry.setSpecReference(name2);
            } else {
                name2 = entry.setName(str);
                z = true;
            }
        }
        if (z) {
            return;
        }
        Iterator<Entry> it = iterator(false);
        if (it.hasNext()) {
            it.next().setName(str, true);
        }
    }

    public String getName() {
        Iterator<Entry> it = iterator(false);
        while (it.hasNext()) {
            String name2 = it.next().getName();
            if (name2 != null) {
                return name2;
            }
        }
        return null;
    }

    @Override // java.lang.Iterable
    public Iterator<Entry> iterator() {
        return iterator(true);
    }

    public Iterator<Entry> iterator(boolean z) {
        return getPackageBlock().getEntries(getResourceId(), z);
    }

    public Iterator<Entry> iterator(Predicate<? super Entry> predicate) {
        return new FilterIterator(getPackageBlock().getEntries(getResourceId()), predicate);
    }

    public Iterator<ResConfig> getConfigs() {
        return new ComputeIterator(iterator(false), new Function() { // from class: com.reandroid.arsc.model.ResourceEntry$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Entry) obj).getResConfig();
            }
        });
    }

    public String getHexId() {
        return HexUtil.toHex8(getResourceId());
    }

    public ResourceName toResourceName() {
        String name2 = getName();
        if (name2 == null) {
            return null;
        }
        return new ResourceName(getPackageName(), getType(), name2);
    }

    public String buildReference() {
        return buildReference(getPackageBlock(), null);
    }

    public String buildReference(PackageBlock packageBlock) {
        return buildReference(packageBlock, null);
    }

    public String buildReference(PackageBlock packageBlock, ValueType valueType) {
        String packageName;
        StringBuilder sb = new StringBuilder();
        if (valueType != null) {
            if (valueType == ValueType.REFERENCE) {
                sb.append('@');
            } else {
                sb.append(MangleConstant.Q_MARK);
            }
        }
        PackageBlock packageBlock2 = getPackageBlock();
        if (packageBlock != packageBlock2 && !packageBlock2.isEmpty() && (packageName = getPackageName()) != null) {
            sb.append(packageName);
            sb.append(':');
        }
        sb.append(getType());
        sb.append('/');
        sb.append(getName());
        return sb.toString();
    }

    public String decodeAttributeData(int i) {
        AttributeBag attributeBagCreate;
        Entry entry = get();
        if (entry == null || (attributeBagCreate = AttributeBag.create(entry.getResValueMapArray())) == null) {
            return null;
        }
        return attributeBagCreate.decodeAttributeValue(i);
    }

    public boolean serializePublicXml(XmlSerializer xmlSerializer) throws IOException {
        String name2 = getName();
        if (name2 == null) {
            return false;
        }
        xmlSerializer.text("\n  ");
        xmlSerializer.startTag(null, PackageBlock.TAG_public);
        xmlSerializer.attribute(null, "id", getHexId());
        xmlSerializer.attribute(null, ModuleXmlParser.TYPE, getType());
        xmlSerializer.attribute(null, "name", name2);
        xmlSerializer.endTag(null, PackageBlock.TAG_public);
        return true;
    }

    public int hashCode() {
        return getResourceId();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ResourceEntry) && getResourceId() == ((ResourceEntry) obj).getResourceId();
    }

    public String toString() {
        String packageName = getPackageName();
        return packageName == null ? getHexId() + " @" + getType() + "/" + getName() : getHexId() + " @" + packageName + ":" + getType() + "/" + getName();
    }
}
