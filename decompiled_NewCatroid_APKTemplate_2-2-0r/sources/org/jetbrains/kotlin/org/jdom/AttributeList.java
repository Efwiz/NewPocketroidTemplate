package org.jetbrains.kotlin.org.jdom;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import org.jetbrains.kotlin.org.jdom.internal.ArrayCopy;

/* JADX INFO: loaded from: classes4.dex */
final class AttributeList extends AbstractList<Attribute> implements RandomAccess {
    private static final Comparator<Attribute> ATTRIBUTE_NATURAL = new Comparator<Attribute>() { // from class: org.jetbrains.kotlin.org.jdom.AttributeList.1
        @Override // java.util.Comparator
        public int compare(Attribute attribute, Attribute attribute2) {
            int iCompareTo = attribute.getNamespacePrefix().compareTo(attribute2.getNamespacePrefix());
            return iCompareTo != 0 ? iCompareTo : attribute.getName().compareTo(attribute2.getName());
        }
    };
    private Attribute[] attributeData;
    private final Element parent;
    private int size;

    AttributeList(Element element) {
        this.parent = element;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(Attribute attribute) {
        if (attribute.getParent() != null) {
            throw new IllegalAddException("The attribute already has an existing parent \"" + attribute.getParent().getQualifiedName() + "\"");
        }
        if (Verifier.checkNamespaceCollision(attribute, this.parent) != null) {
            Element element = this.parent;
            throw new IllegalAddException(element, attribute, Verifier.checkNamespaceCollision(attribute, element));
        }
        int iIndexOfDuplicate = indexOfDuplicate(attribute);
        if (iIndexOfDuplicate < 0) {
            attribute.setParent(this.parent);
            ensureCapacity(this.size + 1);
            Attribute[] attributeArr = this.attributeData;
            int i = this.size;
            this.size = i + 1;
            attributeArr[i] = attribute;
            this.modCount++;
        } else {
            this.attributeData[iIndexOfDuplicate].setParent(null);
            this.attributeData[iIndexOfDuplicate] = attribute;
            attribute.setParent(this.parent);
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public void add(int i, Attribute attribute) {
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException("Index: " + i + " Size: " + size());
        }
        if (attribute.getParent() != null) {
            throw new IllegalAddException("The attribute already has an existing parent \"" + attribute.getParent().getQualifiedName() + "\"");
        }
        if (indexOfDuplicate(attribute) >= 0) {
            throw new IllegalAddException("Cannot add duplicate attribute");
        }
        String strCheckNamespaceCollision = Verifier.checkNamespaceCollision(attribute, this.parent);
        if (strCheckNamespaceCollision != null) {
            throw new IllegalAddException(this.parent, attribute, strCheckNamespaceCollision);
        }
        attribute.setParent(this.parent);
        ensureCapacity(this.size + 1);
        int i2 = this.size;
        if (i == i2) {
            Attribute[] attributeArr = this.attributeData;
            this.size = i2 + 1;
            attributeArr[i2] = attribute;
        } else {
            Attribute[] attributeArr2 = this.attributeData;
            System.arraycopy(attributeArr2, i, attributeArr2, i + 1, i2 - i);
            this.attributeData[i] = attribute;
            this.size++;
        }
        this.modCount++;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends Attribute> collection) {
        return addAll(size(), collection);
    }

    @Override // java.util.AbstractList, java.util.List
    public boolean addAll(int i, Collection<? extends Attribute> collection) {
        if (i < 0 || i > this.size) {
            throw new IndexOutOfBoundsException("Index: " + i + " Size: " + size());
        }
        if (collection == null) {
            throw new NullPointerException("Can not add a null Collection to AttributeList");
        }
        int size = collection.size();
        int i2 = 0;
        if (size == 0) {
            return false;
        }
        if (size == 1) {
            add(i, collection.iterator().next());
            return true;
        }
        ensureCapacity(size() + size);
        int i3 = this.modCount;
        try {
            Iterator<? extends Attribute> it = collection.iterator();
            while (it.hasNext()) {
                add(i + i2, it.next());
                i2++;
            }
            return true;
        } catch (Throwable th) {
            while (true) {
                i2--;
                if (i2 >= 0) {
                    remove(i + i2);
                } else {
                    this.modCount = i3;
                    throw th;
                }
            }
        }
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        if (this.attributeData != null) {
            while (true) {
                int i = this.size;
                if (i <= 0) {
                    break;
                }
                int i2 = i - 1;
                this.size = i2;
                this.attributeData[i2].setParent(null);
                this.attributeData[this.size] = null;
            }
        }
        this.modCount++;
    }

    private void ensureCapacity(int i) {
        Attribute[] attributeArr = this.attributeData;
        if (attributeArr == null) {
            this.attributeData = new Attribute[Math.max(i, 4)];
        } else {
            if (i < attributeArr.length) {
                return;
            }
            this.attributeData = (Attribute[]) ArrayCopy.copyOf(attributeArr, ((i + 4) >>> 1) << 1);
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public Attribute get(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + i + " Size: " + size());
        }
        return this.attributeData[i];
    }

    Attribute get(String str, Namespace namespace) {
        int iIndexOf = indexOf(str, namespace);
        if (iIndexOf < 0) {
            return null;
        }
        return this.attributeData[iIndexOf];
    }

    int indexOf(String str, Namespace namespace) {
        if (this.attributeData == null) {
            return -1;
        }
        if (namespace == null) {
            return indexOf(str, Namespace.NO_NAMESPACE);
        }
        String uri = namespace.getURI();
        for (int i = 0; i < this.size; i++) {
            Attribute attribute = this.attributeData[i];
            if (uri.equals(attribute.getNamespaceURI()) && str.equals(attribute.getName())) {
                return i;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractList, java.util.List
    public Attribute remove(int i) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + i + " Size: " + size());
        }
        Attribute attribute = this.attributeData[i];
        attribute.setParent(null);
        Attribute[] attributeArr = this.attributeData;
        System.arraycopy(attributeArr, i + 1, attributeArr, i, (this.size - i) - 1);
        Attribute[] attributeArr2 = this.attributeData;
        int i2 = this.size - 1;
        this.size = i2;
        attributeArr2[i2] = null;
        this.modCount++;
        return attribute;
    }

    @Override // java.util.AbstractList, java.util.List
    public Attribute set(int i, Attribute attribute) {
        if (i < 0 || i >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + i + " Size: " + size());
        }
        if (attribute.getParent() != null) {
            throw new IllegalAddException("The attribute already has an existing parent \"" + attribute.getParent().getQualifiedName() + "\"");
        }
        int iIndexOfDuplicate = indexOfDuplicate(attribute);
        if (iIndexOfDuplicate >= 0 && iIndexOfDuplicate != i) {
            throw new IllegalAddException("Cannot set duplicate attribute");
        }
        String strCheckNamespaceCollision = Verifier.checkNamespaceCollision(attribute, this.parent, i);
        if (strCheckNamespaceCollision != null) {
            throw new IllegalAddException(this.parent, attribute, strCheckNamespaceCollision);
        }
        Attribute attribute2 = this.attributeData[i];
        attribute2.setParent(null);
        this.attributeData[i] = attribute;
        attribute.setParent(this.parent);
        return attribute2;
    }

    private int indexOfDuplicate(Attribute attribute) {
        return indexOf(attribute.getName(), attribute.getNamespace());
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<Attribute> iterator() {
        return new ALIterator();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        return super.toString();
    }

    private final int binarySearch(int[] iArr, int i, int i2, Comparator<? super Attribute> comparator) {
        int i3 = i - 1;
        Attribute attribute = this.attributeData[i2];
        int i4 = 0;
        while (i4 <= i3) {
            int i5 = (i4 + i3) >>> 1;
            int iCompare = comparator.compare(attribute, this.attributeData[iArr[i5]]);
            if (iCompare == 0) {
                while (iCompare == 0 && i5 < i3) {
                    int i6 = i5 + 1;
                    if (comparator.compare(attribute, this.attributeData[iArr[i6]]) != 0) {
                        break;
                    }
                    i5 = i6;
                }
                return i5 + 1;
            }
            if (iCompare < 0) {
                i3 = i5 - 1;
            } else {
                i4 = i5 + 1;
            }
        }
        return i4;
    }

    private void sortInPlace(int[] iArr) {
        int[] iArrCopyOf = ArrayCopy.copyOf(iArr, iArr.length);
        Arrays.sort(iArrCopyOf);
        int length = iArrCopyOf.length;
        Attribute[] attributeArr = new Attribute[length];
        for (int i = 0; i < length; i++) {
            attributeArr[i] = this.attributeData[iArr[i]];
        }
        for (int i2 = 0; i2 < iArr.length; i2++) {
            this.attributeData[iArrCopyOf[i2]] = attributeArr[i2];
        }
    }

    @Override // java.util.List
    public void sort(Comparator<? super Attribute> comparator) {
        if (comparator == null) {
            comparator = ATTRIBUTE_NATURAL;
        }
        int i = this.size;
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            int iBinarySearch = binarySearch(iArr, i2, i2, comparator);
            if (iBinarySearch < i2) {
                System.arraycopy(iArr, iBinarySearch, iArr, iBinarySearch + 1, i2 - iBinarySearch);
            }
            iArr[iBinarySearch] = i2;
        }
        sortInPlace(iArr);
    }

    private final class ALIterator implements Iterator<Attribute> {
        private boolean canremove;
        private int cursor;
        private int expect;

        private ALIterator() {
            this.expect = -1;
            this.cursor = 0;
            this.canremove = false;
            this.expect = AttributeList.this.modCount;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.cursor < AttributeList.this.size;
        }

        @Override // java.util.Iterator
        public Attribute next() {
            if (AttributeList.this.modCount == this.expect) {
                if (this.cursor >= AttributeList.this.size) {
                    throw new NoSuchElementException("Iterated beyond the end of the ContentList.");
                }
                this.canremove = true;
                Attribute[] attributeArr = AttributeList.this.attributeData;
                int i = this.cursor;
                this.cursor = i + 1;
                return attributeArr[i];
            }
            throw new ConcurrentModificationException("ContentList was modified outside of this Iterator");
        }

        @Override // java.util.Iterator
        public void remove() {
            if (AttributeList.this.modCount != this.expect) {
                throw new ConcurrentModificationException("ContentList was modified outside of this Iterator");
            }
            if (!this.canremove) {
                throw new IllegalStateException("Can only remove() content after a call to next()");
            }
            AttributeList attributeList = AttributeList.this;
            int i = this.cursor - 1;
            this.cursor = i;
            attributeList.remove(i);
            this.expect = AttributeList.this.modCount;
            this.canremove = false;
        }
    }
}
