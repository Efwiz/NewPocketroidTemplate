package com.reandroid.arsc.model;

import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.item.TypeString;
import com.reandroid.arsc.pool.TypeStringPool;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.FilterIterator;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes10.dex */
public class ResourceType implements Comparable<ResourceType>, Iterable<ResourceEntry> {

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final int f230id;
    private final PackageBlock packageBlock;

    public ResourceType(PackageBlock packageBlock, int i) {
        this.packageBlock = packageBlock;
        this.f230id = i;
    }

    public ResourceType(SpecTypePair specTypePair) {
        this(specTypePair.getPackageBlock(), specTypePair.getId());
    }

    public ResourceEntry get(int i) {
        int id2;
        if (i == 0 || (i >>> 24) != getPackageId() || (id2 = getId()) == 0 || ((i >> 16) & 255) != id2 || (65535 & i) > size()) {
            return null;
        }
        return new ResourceEntry(getPackageBlock(), i);
    }

    public int getIdentifier(String str) {
        return getPackageBlock().getSpecStringPool().resolveResourceId(getId(), str);
    }

    @Override // java.lang.Iterable
    public Iterator<ResourceEntry> iterator() {
        return FilterIterator.of(getResources(), new Predicate() { // from class: com.reandroid.arsc.model.ResourceType$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((ResourceEntry) obj).isDefined();
            }
        });
    }

    public int size() {
        SpecTypePair specTypePair = getSpecTypePair();
        if (specTypePair != null) {
            return specTypePair.getHighestEntryCount();
        }
        return 0;
    }

    public boolean isEmpty() {
        return size() == 0 || !iterator().hasNext();
    }

    private Iterator<ResourceEntry> getResources() {
        SpecTypePair specTypePair = getSpecTypePair();
        if (specTypePair != null) {
            return specTypePair.getResources();
        }
        return EmptyIterator.of();
    }

    private SpecTypePair getSpecTypePair() {
        return getPackageBlock().getSpecTypePair(getId());
    }

    public String getName() {
        return getPackageBlock().typeNameOf(getId());
    }

    public void setName(String str) {
        TypeStringPool typeStringPool = getPackageBlock().getTypeStringPool();
        int id2 = getId();
        TypeString byId = typeStringPool.getById(id2);
        if (byId == null) {
            typeStringPool.getOrCreate(id2, str);
        } else {
            byId.set(str);
        }
    }

    public PackageBlock getPackageBlock() {
        return this.packageBlock;
    }

    public int getId() {
        return this.f230id;
    }

    public int getPackageId() {
        return getPackageBlock().getId();
    }

    @Override // java.lang.Comparable
    public int compareTo(ResourceType resourceType) {
        if (resourceType == null) {
            return -1;
        }
        if (resourceType == this) {
            return 0;
        }
        int iCompare = CompareUtil.compare(getPackageId(), resourceType.getPackageId());
        return iCompare == 0 ? CompareUtil.compare(getId(), resourceType.getId()) : iCompare;
    }

    public int hashCode() {
        return (getPackageId() << 24) | (getId() << 16);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ResourceType) && getPackageBlock() == ((ResourceType) obj).getPackageBlock();
    }

    public String toString() {
        return getName();
    }
}
