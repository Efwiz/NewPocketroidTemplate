package com.reandroid.arsc.model;

import com.reandroid.arsc.chunk.PackageBlock;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class ResourcePackage implements Iterable<ResourceType> {
    private final PackageBlock packageBlock;

    public ResourcePackage(PackageBlock packageBlock) {
        this.packageBlock = packageBlock;
    }

    @Override // java.lang.Iterable
    public Iterator<ResourceType> iterator() {
        return getPackageBlock().getTypes();
    }

    public PackageBlock getPackageBlock() {
        return this.packageBlock;
    }

    public int getId() {
        return getPackageBlock().getId();
    }

    public String getName() {
        return getPackageBlock().getName();
    }

    public int hashCode() {
        return getId();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ResourcePackage) && getId() == ((ResourcePackage) obj).getId();
    }

    public String toString() {
        return getName();
    }
}
