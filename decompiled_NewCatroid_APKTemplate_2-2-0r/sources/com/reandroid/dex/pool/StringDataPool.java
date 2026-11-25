package com.reandroid.dex.pool;

import com.reandroid.dex.data.StringData;
import com.reandroid.dex.id.StringId;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.sections.Section;
import com.reandroid.dex.sections.SectionList;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.sections.StringDataSection;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.EmptyIterator;
import java.util.Iterator;
import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
public class StringDataPool extends DexSectionPool<StringData> {
    @Override // com.reandroid.dex.pool.DexSectionPool
    public int clearDuplicates() {
        return 0;
    }

    @Override // com.reandroid.dex.pool.DexSectionPool
    boolean isKeyItemsCreate() {
        return false;
    }

    @Override // com.reandroid.dex.pool.DexSectionPool
    public void load() {
    }

    public StringDataPool(StringDataSection stringDataSection) {
        super(stringDataSection);
    }

    @Override // com.reandroid.utils.collection.MultiMap
    public int size() {
        DexSectionPool<StringId> loadedIdPool = getLoadedIdPool();
        if (loadedIdPool != null) {
            return loadedIdPool.size();
        }
        return 0;
    }

    @Override // com.reandroid.utils.collection.MultiMap
    public StringData get(Key key) {
        return getStringData(key);
    }

    @Override // com.reandroid.utils.collection.MultiMap
    public Iterator<StringData> getAll(Key key) {
        DexSectionPool<StringId> loadedIdPool = getLoadedIdPool();
        if (loadedIdPool == null) {
            return EmptyIterator.of();
        }
        return ComputeIterator.of(loadedIdPool.getAll(key), new Function() { // from class: com.reandroid.dex.pool.StringDataPool$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((StringId) obj).getStringData();
            }
        });
    }

    @Override // com.reandroid.utils.collection.MultiMap
    public boolean updateKey(Key key, Key key2, StringData stringData) {
        DexSectionPool<StringId> loadedIdPool = getLoadedIdPool();
        if (loadedIdPool != null) {
            return loadedIdPool.updateKey(key, key2, loadedIdPool.get(key));
        }
        return false;
    }

    @Override // com.reandroid.dex.pool.DexSectionPool
    public boolean contains(Key key) {
        DexSectionPool<StringId> idPool = getIdPool();
        if (idPool != null) {
            return idPool.contains(key);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.dex.pool.DexSectionPool
    public StringDataSection getSection() {
        return (StringDataSection) super.getSection();
    }

    private StringData getStringData(Key key) {
        StringId stringId;
        DexSectionPool<StringId> idPool = getIdPool();
        if (idPool == null || (stringId = idPool.get(key)) == null) {
            return null;
        }
        return stringId.getStringData();
    }

    private DexSectionPool<StringId> getIdPool() {
        Section<StringId> stringIdSection = getStringIdSection();
        if (stringIdSection != null) {
            return stringIdSection.getPool();
        }
        return null;
    }

    private DexSectionPool<StringId> getLoadedIdPool() {
        Section<StringId> stringIdSection = getStringIdSection();
        if (stringIdSection != null) {
            return stringIdSection.getLoadedPool();
        }
        return null;
    }

    private Section<StringId> getStringIdSection() {
        SectionList sectionList = getSection().getSectionList();
        if (sectionList != null) {
            return sectionList.getSection(SectionType.STRING_ID);
        }
        return null;
    }
}
