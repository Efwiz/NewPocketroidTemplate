package com.reandroid.arsc.pool;

import com.reandroid.arsc.array.IntegerOffsetArray;
import com.reandroid.arsc.array.OffsetArray;
import com.reandroid.arsc.array.StringArray;
import com.reandroid.arsc.array.StyleArray;
import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.chunk.Chunk;
import com.reandroid.arsc.header.StringPoolHeader;
import com.reandroid.arsc.io.BlockLoad;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.ByteItem;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.ReferenceItem;
import com.reandroid.arsc.item.StringItem;
import com.reandroid.arsc.item.StyleItem;
import com.reandroid.common.BytesOutputStream;
import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.NumbersUtil;
import com.reandroid.utils.StringsUtil;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.IterableIterator;
import com.reandroid.utils.collection.MultiMap;
import com.reandroid.xml.StyleDocument;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes10.dex */
public abstract class StringPool<T extends StringItem> extends Chunk<StringPoolHeader> implements BlockLoad, Iterable<T>, JSONConvert<JSONArray> {
    private final StringArray<T> mArrayStrings;
    private final StyleArray mArrayStyles;
    private final Object mLock;
    private final MultiMap<String, T> poolMap;
    private boolean stringLinkLocked;

    abstract StringArray<T> newInstance(OffsetArray offsetArray, IntegerItem integerItem, IntegerItem integerItem2, boolean z);

    StringPool(boolean z, boolean z2) {
        super(new StringPoolHeader(), 4);
        this.mLock = new Object();
        IntegerOffsetArray integerOffsetArray = new IntegerOffsetArray();
        IntegerOffsetArray integerOffsetArray2 = new IntegerOffsetArray();
        StringPoolHeader headerBlock = getHeaderBlock();
        StringArray<T> stringArrayNewInstance = newInstance(integerOffsetArray, headerBlock.getCountStrings(), headerBlock.getStartStrings(), z);
        this.mArrayStrings = stringArrayNewInstance;
        StyleArray styleArray = new StyleArray(integerOffsetArray2, headerBlock.getCountStyles(), headerBlock.getStartStyles());
        this.mArrayStyles = styleArray;
        addChild(integerOffsetArray);
        addChild(integerOffsetArray2);
        addChild(stringArrayNewInstance);
        addChild(styleArray);
        setUtf8(z, false);
        headerBlock.getFlagUtf8().setBlockLoad(this);
        this.stringLinkLocked = z2;
        MultiMap<String, T> multiMap = new MultiMap<>();
        this.poolMap = multiMap;
        multiMap.setFavouriteObjectsSorter(new Comparator() { // from class: com.reandroid.arsc.pool.StringPool$$ExternalSyntheticLambda6
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return StringPool.lambda$new$0((StringItem) obj, (StringItem) obj2);
            }
        });
    }

    static /* synthetic */ int lambda$new$0(StringItem stringItem, StringItem stringItem2) {
        int iCompareTo = stringItem.compareTo(stringItem2);
        return iCompareTo == 0 ? CompareUtil.compareUnsigned(stringItem.getIndex(), stringItem2.getIndex()) : iCompareTo;
    }

    StringPool(boolean z) {
        this(z, true);
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return this.mArrayStrings.iterator();
    }

    public final T get(int i) {
        return (T) this.mArrayStrings.get(i);
    }

    public int size() {
        return this.mArrayStrings.getCount();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        getStyleArray().clear();
        getStringsArray().clear();
        this.poolMap.clear();
    }

    @Deprecated
    public void destroy() {
        clear();
    }

    public void sort() {
        ensureStringLinkUnlockedInternal();
        getStringsArray().sort();
    }

    public boolean isStringLinkLocked() {
        return this.stringLinkLocked;
    }

    public void ensureStringLinkUnlockedInternal() {
        synchronized (this.mLock) {
            if (this.stringLinkLocked) {
                this.stringLinkLocked = false;
                linkStrings();
                reloadPoolMap();
            }
        }
    }

    void linkStrings() {
        linkStyleStrings();
    }

    private void linkStyleStrings() {
        StyleArray styleArray = getStyleArray();
        int size = styleArray.size();
        for (int i = 0; i < size; i++) {
            ((StyleItem) styleArray.get(i)).linkStringsInternal();
        }
    }

    public void linkStylesInternal() {
        StyleArray styleArray = getStyleArray();
        StringArray<T> stringsArray = getStringsArray();
        int iMin = NumbersUtil.min(stringsArray.size(), styleArray.size());
        for (int i = 0; i < iMin; i++) {
            ((StringItem) stringsArray.get(i)).linkStyleItemInternal((StyleItem) styleArray.get(i));
        }
    }

    public void removeString(T t) {
        getStringsArray().remove(t);
    }

    public Iterator<String> getStrings() {
        return ComputeIterator.of(iterator(), new StringPool$$ExternalSyntheticLambda2());
    }

    public void addStrings(Collection<String> collection) {
        if (collection == null || collection.size() == 0) {
            return;
        }
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            createNewString(it.next());
        }
    }

    private void reloadPoolMap() {
        if (this.poolMap.size() == 0) {
            this.poolMap.clear();
            this.poolMap.setInitialSize(size());
            this.poolMap.putAll(new StringPool$$ExternalSyntheticLambda2(), iterator());
        }
    }

    public void compressDuplicates() {
        ensureStringLinkUnlockedInternal();
        this.poolMap.findDuplicates(CompareUtil.getComparableComparator(), new Consumer() { // from class: com.reandroid.arsc.pool.StringPool$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                StringPool.lambda$compressDuplicates$1((List) obj);
            }
        });
    }

    static /* synthetic */ void lambda$compressDuplicates$1(List list) {
        StringItem stringItem = (StringItem) list.get(0);
        for (int i = 1; i < list.size(); i++) {
            stringItem.transferReferences((StringItem) list.get(i));
        }
    }

    public boolean removeUnusedStrings() {
        return getStringsArray().removeIf(getUnusedStringsFilter());
    }

    public List<T> listUnusedStrings() {
        return getStringsArray().subListIf(getUnusedStringsFilter());
    }

    private Predicate<T> getUnusedStringsFilter() {
        return new Predicate() { // from class: com.reandroid.arsc.pool.StringPool$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return StringPool.lambda$getUnusedStringsFilter$2((StringItem) obj);
            }
        };
    }

    static /* synthetic */ boolean lambda$getUnusedStringsFilter$2(StringItem stringItem) {
        return !stringItem.hasReference();
    }

    public StyleArray getStyleArray() {
        return this.mArrayStyles;
    }

    public StringArray<T> getStringsArray() {
        return this.mArrayStrings;
    }

    public T removeReference(ReferenceItem referenceItem) {
        T t;
        if (referenceItem == null || (t = (T) get(referenceItem.get())) == null) {
            return null;
        }
        t.removeReference(referenceItem);
        return t;
    }

    public boolean contains(String str) {
        return this.poolMap.containsKey(str);
    }

    public void onStringChanged(String str, T t) {
        if (this.stringLinkLocked) {
            return;
        }
        this.poolMap.updateKey(str, t.getXml(), t);
    }

    public void onStringRemoved(T t) {
        if (this.stringLinkLocked) {
            return;
        }
        this.poolMap.remove(t.getXml(), t);
    }

    public final T getLast() {
        return this.mArrayStrings.getLast();
    }

    public <E extends Block> Iterator<E> getUsers(final Class<E> cls, String str) {
        return new IterableIterator<T, E>(getAll(str)) { // from class: com.reandroid.arsc.pool.StringPool.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<E> iterator(T t) {
                return t.getUsers(cls);
            }
        };
    }

    public final Iterator<T> getAll(String str) {
        ensureStringLinkUnlockedInternal();
        return this.poolMap.getAll(str);
    }

    public final T get(String str, Predicate<? super T> predicate) {
        ensureStringLinkUnlockedInternal();
        return this.poolMap.get(str, predicate);
    }

    public final T getString(String str) {
        return (T) CollectionUtil.getFirst(getAll(str));
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public T getOrCreate(final String str) {
        ensureStringLinkUnlockedInternal();
        if (str == null) {
            str = StringsUtil.EMPTY;
        }
        T t = (T) get(str, new Predicate() { // from class: com.reandroid.arsc.pool.StringPool$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return str.equals(((StringItem) obj).getXml());
            }
        });
        if (t != null) {
            return t;
        }
        T t2 = (T) createNewString(str);
        this.poolMap.put(str, t2);
        return t2;
    }

    public T getOrCreate(StyleDocument styleDocument) {
        ensureStringLinkUnlockedInternal();
        T t = (T) get(styleDocument.getXml(), new Predicate() { // from class: com.reandroid.arsc.pool.StringPool$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((StringItem) obj).hasStyle();
            }
        });
        if (t != null) {
            return t;
        }
        T t2 = (T) createNewString();
        t2.set(styleDocument);
        return t2;
    }

    public T getOrCreate(JSONObject jSONObject) {
        ensureStringLinkUnlockedInternal();
        if (jSONObject.optJSONObject(StringItem.NAME_style) != null) {
            T t = (T) createNewString();
            t.set(jSONObject);
            StyleDocument styleDocument = t.getStyleDocument();
            if (styleDocument != null) {
                T t2 = (T) getString(styleDocument.getXml());
                if (t2 == null) {
                    return t;
                }
                if (t.hasReference()) {
                    t.set(null);
                    t.setNull(true);
                }
                return t2;
            }
        }
        return (T) getOrCreate(jSONObject.getString(StringItem.NAME_string));
    }

    public T createNewString(String str) {
        T t = (T) createNewString();
        t.set(str);
        return t;
    }

    public T createNewString() {
        return this.mArrayStrings.createNext();
    }

    @Deprecated
    public final int countStrings() {
        return this.mArrayStrings.size();
    }

    public final int countStyles() {
        return this.mArrayStyles.size();
    }

    public boolean isUtf8() {
        return getHeaderBlock().isUtf8();
    }

    public void setUtf8(boolean z) {
        setUtf8(z, true);
    }

    private void setUtf8(boolean z, boolean z2) {
        StringPoolHeader headerBlock = getHeaderBlock();
        if (z == headerBlock.isUtf8()) {
            return;
        }
        ByteItem flagUtf8 = headerBlock.getFlagUtf8();
        if (z) {
            flagUtf8.set((byte) 1);
        } else {
            flagUtf8.set((byte) 0);
        }
        if (z2) {
            this.mArrayStrings.setUtf8(z);
        }
    }

    public void setFlagSorted(boolean z) {
        getHeaderBlock().setSorted(z);
    }

    @Override // com.reandroid.arsc.chunk.Chunk
    protected void onChunkRefreshed() {
        this.mArrayStrings.refreshCountAndStart();
        this.mArrayStyles.refreshCountAndStart();
    }

    @Override // com.reandroid.arsc.chunk.Chunk
    public void onChunkLoaded() {
        linkStylesInternal();
        reloadPoolMap();
    }

    @Override // com.reandroid.arsc.io.BlockLoad
    public void onBlockLoaded(BlockReader blockReader, Block block) throws IOException {
        StringPoolHeader headerBlock = getHeaderBlock();
        if (block == headerBlock.getFlagUtf8()) {
            this.mArrayStrings.setUtf8(headerBlock.isUtf8());
        }
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public byte[] getBytes() {
        BytesOutputStream bytesOutputStream = new BytesOutputStream(getHeaderBlock().getChunkSize());
        try {
            writeBytes(bytesOutputStream);
            bytesOutputStream.close();
        } catch (IOException unused) {
        }
        return bytesOutputStream.toByteArray();
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONArray toJson() {
        return getStringsArray().toJson();
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONArray jSONArray) {
        if (jSONArray == null) {
            return;
        }
        getStringsArray().fromJson(jSONArray);
        refresh();
    }

    boolean containsInternal(final T t) {
        return this.poolMap.containsValue(t.getXml(), new Predicate() { // from class: com.reandroid.arsc.pool.StringPool$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return StringPool.lambda$containsInternal$4(t, (StringItem) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$containsInternal$4(StringItem stringItem, StringItem stringItem2) {
        return stringItem2.compareTo(stringItem) == 0;
    }

    public void merge(StringPool<T> stringPool) {
        if (stringPool == null || stringPool == this || stringPool.size() == 0) {
            return;
        }
        ensureStringLinkUnlockedInternal();
        for (T t : stringPool) {
            if (!containsInternal(t)) {
                createNewString().merge(t);
            }
        }
    }
}
