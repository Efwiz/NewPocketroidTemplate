package org.jetbrains.kotlin.protobuf;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: loaded from: classes4.dex */
public class LazyStringArrayList extends AbstractList<String> implements RandomAccess, LazyStringList {
    public static final LazyStringList EMPTY = new LazyStringArrayList().getUnmodifiableView();
    private final List<Object> list;

    public LazyStringArrayList() {
        this.list = new ArrayList();
    }

    public LazyStringArrayList(LazyStringList lazyStringList) {
        this.list = new ArrayList(lazyStringList.size());
        addAll(lazyStringList);
    }

    public LazyStringArrayList(List<String> list) {
        this.list = new ArrayList(list);
    }

    @Override // java.util.AbstractList, java.util.List
    public String get(int i) {
        Object obj = this.list.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof ByteString) {
            ByteString byteString = (ByteString) obj;
            String stringUtf8 = byteString.toStringUtf8();
            if (byteString.isValidUtf8()) {
                this.list.set(i, stringUtf8);
            }
            return stringUtf8;
        }
        byte[] bArr = (byte[]) obj;
        String stringUtf9 = Internal.toStringUtf8(bArr);
        if (Internal.isValidUtf8(bArr)) {
            this.list.set(i, stringUtf9);
        }
        return stringUtf9;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.list.size();
    }

    @Override // java.util.AbstractList, java.util.List
    public String set(int i, String str) {
        return asString(this.list.set(i, str));
    }

    @Override // java.util.AbstractList, java.util.List
    public void add(int i, String str) {
        this.list.add(i, str);
        this.modCount++;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    @Override // java.util.AbstractList, java.util.List
    public boolean addAll(int i, Collection<? extends String> collection) {
        if (collection instanceof LazyStringList) {
            collection = ((LazyStringList) collection).getUnderlyingElements();
        }
        boolean zAddAll = this.list.addAll(i, collection);
        this.modCount++;
        return zAddAll;
    }

    @Override // org.jetbrains.kotlin.protobuf.LazyStringList
    public boolean addAllByteString(Collection<? extends ByteString> collection) {
        boolean zAddAll = this.list.addAll(collection);
        this.modCount++;
        return zAddAll;
    }

    @Override // org.jetbrains.kotlin.protobuf.LazyStringList
    public boolean addAllByteArray(Collection<byte[]> collection) {
        boolean zAddAll = this.list.addAll(collection);
        this.modCount++;
        return zAddAll;
    }

    @Override // java.util.AbstractList, java.util.List
    public String remove(int i) {
        Object objRemove = this.list.remove(i);
        this.modCount++;
        return asString(objRemove);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        this.list.clear();
        this.modCount++;
    }

    @Override // org.jetbrains.kotlin.protobuf.LazyStringList
    public void add(ByteString byteString) {
        this.list.add(byteString);
        this.modCount++;
    }

    @Override // org.jetbrains.kotlin.protobuf.LazyStringList
    public void add(byte[] bArr) {
        this.list.add(bArr);
        this.modCount++;
    }

    @Override // org.jetbrains.kotlin.protobuf.LazyStringList
    public ByteString getByteString(int i) {
        Object obj = this.list.get(i);
        ByteString byteStringAsByteString = asByteString(obj);
        if (byteStringAsByteString != obj) {
            this.list.set(i, byteStringAsByteString);
        }
        return byteStringAsByteString;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.protobuf.LazyStringList
    public byte[] getByteArray(int i) {
        Object obj = this.list.get(i);
        byte[] bArrAsByteArray = asByteArray(obj);
        if (bArrAsByteArray != obj) {
            this.list.set(i, bArrAsByteArray);
        }
        return bArrAsByteArray;
    }

    @Override // org.jetbrains.kotlin.protobuf.LazyStringList
    public void set(int i, ByteString byteString) {
        this.list.set(i, byteString);
    }

    @Override // org.jetbrains.kotlin.protobuf.LazyStringList
    public void set(int i, byte[] bArr) {
        this.list.set(i, bArr);
    }

    private static String asString(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof ByteString) {
            return ((ByteString) obj).toStringUtf8();
        }
        return Internal.toStringUtf8((byte[]) obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ByteString asByteString(Object obj) {
        if (obj instanceof ByteString) {
            return (ByteString) obj;
        }
        if (obj instanceof String) {
            return ByteString.copyFromUtf8((String) obj);
        }
        return ByteString.copyFrom((byte[]) obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] asByteArray(Object obj) {
        if (obj instanceof byte[]) {
            return (byte[]) obj;
        }
        if (obj instanceof String) {
            return Internal.toByteArray((String) obj);
        }
        return ((ByteString) obj).toByteArray();
    }

    @Override // org.jetbrains.kotlin.protobuf.LazyStringList
    public List<?> getUnderlyingElements() {
        return Collections.unmodifiableList(this.list);
    }

    @Override // org.jetbrains.kotlin.protobuf.LazyStringList
    public void mergeFrom(LazyStringList lazyStringList) {
        for (Object obj : lazyStringList.getUnderlyingElements()) {
            if (obj instanceof byte[]) {
                byte[] bArr = (byte[]) obj;
                this.list.add(Arrays.copyOf(bArr, bArr.length));
            } else {
                this.list.add(obj);
            }
        }
    }

    private static class ByteArrayListView extends AbstractList<byte[]> implements RandomAccess {
        private final List<Object> list;

        ByteArrayListView(List<Object> list) {
            this.list = list;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.AbstractList, java.util.List
        public byte[] get(int i) {
            Object obj = this.list.get(i);
            byte[] bArrAsByteArray = LazyStringArrayList.asByteArray(obj);
            if (bArrAsByteArray != obj) {
                this.list.set(i, bArrAsByteArray);
            }
            return bArrAsByteArray;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.list.size();
        }

        @Override // java.util.AbstractList, java.util.List
        public byte[] set(int i, byte[] bArr) {
            Object obj = this.list.set(i, bArr);
            this.modCount++;
            return LazyStringArrayList.asByteArray(obj);
        }

        @Override // java.util.AbstractList, java.util.List
        public void add(int i, byte[] bArr) {
            this.list.add(i, bArr);
            this.modCount++;
        }

        @Override // java.util.AbstractList, java.util.List
        public byte[] remove(int i) {
            Object objRemove = this.list.remove(i);
            this.modCount++;
            return LazyStringArrayList.asByteArray(objRemove);
        }
    }

    @Override // org.jetbrains.kotlin.protobuf.LazyStringList
    public List<byte[]> asByteArrayList() {
        return new ByteArrayListView(this.list);
    }

    private static class ByteStringListView extends AbstractList<ByteString> implements RandomAccess {
        private final List<Object> list;

        ByteStringListView(List<Object> list) {
            this.list = list;
        }

        @Override // java.util.AbstractList, java.util.List
        public ByteString get(int i) {
            Object obj = this.list.get(i);
            ByteString byteStringAsByteString = LazyStringArrayList.asByteString(obj);
            if (byteStringAsByteString != obj) {
                this.list.set(i, byteStringAsByteString);
            }
            return byteStringAsByteString;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.list.size();
        }

        @Override // java.util.AbstractList, java.util.List
        public ByteString set(int i, ByteString byteString) {
            Object obj = this.list.set(i, byteString);
            this.modCount++;
            return LazyStringArrayList.asByteString(obj);
        }

        @Override // java.util.AbstractList, java.util.List
        public void add(int i, ByteString byteString) {
            this.list.add(i, byteString);
            this.modCount++;
        }

        @Override // java.util.AbstractList, java.util.List
        public ByteString remove(int i) {
            Object objRemove = this.list.remove(i);
            this.modCount++;
            return LazyStringArrayList.asByteString(objRemove);
        }
    }

    @Override // org.jetbrains.kotlin.protobuf.ProtocolStringList
    public List<ByteString> asByteStringList() {
        return new ByteStringListView(this.list);
    }

    @Override // org.jetbrains.kotlin.protobuf.LazyStringList
    public LazyStringList getUnmodifiableView() {
        return new UnmodifiableLazyStringList(this);
    }
}
