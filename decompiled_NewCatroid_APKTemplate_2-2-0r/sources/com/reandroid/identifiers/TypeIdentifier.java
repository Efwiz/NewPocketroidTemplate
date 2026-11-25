package com.reandroid.identifiers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public class TypeIdentifier extends IdentifierMap<ResourceIdentifier> {
    private final Map<Object, ResourceIdentifier> tagMap;

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ Collection getItems() {
        return super.getItems();
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ int getMaxId() {
        return super.getMaxId();
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ boolean hasDuplicates() {
        return super.hasDuplicates();
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ List list() {
        return super.list();
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ List listDuplicates() {
        return super.listDuplicates();
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ List listNames() {
        return super.listNames();
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ void reloadNameMap() {
        super.reloadNameMap();
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    @Override // com.reandroid.identifiers.IdentifierMap, com.reandroid.identifiers.Identifier
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public TypeIdentifier(int i, String str) {
        super(i, str);
        this.tagMap = new HashMap();
    }

    public TypeIdentifier() {
        this(0, null);
    }

    public List<ResourceIdentifier> ensureUniqueResourceNames() {
        ArrayList arrayList = new ArrayList();
        for (ResourceIdentifier resourceIdentifier : listDuplicates()) {
            resourceIdentifier.setName(resourceIdentifier.generateUniqueName());
            arrayList.add(resourceIdentifier);
        }
        if (arrayList.size() > 0) {
            reloadNameMap();
        }
        return arrayList;
    }

    public void write(XmlSerializer xmlSerializer) throws IOException {
        Iterator it = list().iterator();
        while (it.hasNext()) {
            ((ResourceIdentifier) it.next()).write(xmlSerializer);
        }
    }

    public PackageIdentifier getPackageIdentifier() {
        return (PackageIdentifier) getParent();
    }

    public void setPackageIdentifier(PackageIdentifier packageIdentifier) {
        setParent(packageIdentifier);
    }

    public String getPackageName() {
        PackageIdentifier packageIdentifier = getPackageIdentifier();
        if (packageIdentifier != null) {
            return packageIdentifier.getName();
        }
        return null;
    }

    public int getPackageId() {
        PackageIdentifier packageIdentifier = getPackageIdentifier();
        if (packageIdentifier != null) {
            return packageIdentifier.getId();
        }
        return 0;
    }

    public int renameSpecs() {
        Iterator it = getItems().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (((ResourceIdentifier) it.next()).renameSpec()) {
                i++;
            }
        }
        if (i != 0) {
            reloadNameMap();
        }
        return i;
    }

    public int renameDuplicateSpecs() {
        Iterator it = listDuplicates().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (((ResourceIdentifier) it.next()).renameSpecGenerated()) {
                i++;
            }
        }
        if (i != 0) {
            reloadNameMap();
        }
        return i;
    }

    public int renameBadSpecs() {
        Iterator it = getItems().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (((ResourceIdentifier) it.next()).renameBadSpec()) {
                i++;
            }
        }
        if (i != 0) {
            reloadNameMap();
        }
        return i;
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public ResourceIdentifier getByTag(Object obj) {
        ResourceIdentifier resourceIdentifier = this.tagMap.get(obj);
        return resourceIdentifier != null ? resourceIdentifier : (ResourceIdentifier) super.getByTag(obj);
    }

    @Override // com.reandroid.identifiers.IdentifierMap
    public void clear() {
        this.tagMap.clear();
        super.clear();
    }

    @Override // com.reandroid.identifiers.Identifier
    long getUniqueId() {
        return (getPackageId() << 8) | getId();
    }

    void addTag(Object obj, ResourceIdentifier resourceIdentifier) {
        if (obj != null) {
            this.tagMap.put(obj, resourceIdentifier);
        }
    }

    void removeTag(Object obj) {
        this.tagMap.remove(obj);
    }
}
