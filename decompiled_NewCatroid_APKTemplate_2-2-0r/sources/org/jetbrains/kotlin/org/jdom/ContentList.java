package org.jetbrains.kotlin.org.jdom;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import org.jetbrains.kotlin.org.jdom.internal.ArrayCopy;

/* JADX INFO: loaded from: classes4.dex */
final class ContentList extends AbstractList<Content> implements RandomAccess {
    private final Parent parent;
    private int size;
    private Content[] elementData = null;
    private transient int sizeModCount = Integer.MIN_VALUE;
    private transient int dataModiCount = Integer.MIN_VALUE;

    ContentList(Parent parent) {
        this.parent = parent;
    }

    private final void setModCount(int i, int i2) {
        this.sizeModCount = i;
        this.dataModiCount = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getModCount() {
        return this.sizeModCount;
    }

    private final void incModCount() {
        this.dataModiCount++;
        this.sizeModCount++;
    }

    private final void incDataModOnly() {
        this.dataModiCount++;
    }

    private final int getDataModCount() {
        return this.dataModiCount;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkIndex(int i, boolean z) {
        int i2 = z ? this.size - 1 : this.size;
        if (i < 0 || i > i2) {
            throw new IndexOutOfBoundsException("Index: " + i + " Size: " + this.size);
        }
    }

    private final void checkPreConditions(Content content, int i, boolean z) {
        if (content == null) {
            throw new NullPointerException("Cannot add null object");
        }
        checkIndex(i, z);
        if (content.getParent() != null) {
            Parent parent = content.getParent();
            if (parent instanceof Document) {
                throw new IllegalAddException((Element) content, "The Content already has an existing parent document");
            }
            throw new IllegalAddException("The Content already has an existing parent \"" + ((Element) parent).getQualifiedName() + "\"");
        }
        Parent parent2 = this.parent;
        if (content == parent2) {
            throw new IllegalAddException("The Element cannot be added to itself");
        }
        if ((parent2 instanceof Element) && (content instanceof Element) && ((Element) content).isAncestor((Element) parent2)) {
            throw new IllegalAddException("The Element cannot be added as a descendent of itself");
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public void add(int i, Content content) {
        checkPreConditions(content, i, false);
        this.parent.canContainContent(content, i, false);
        content.setParent(this.parent);
        ensureCapacity(this.size + 1);
        int i2 = this.size;
        if (i == i2) {
            Content[] contentArr = this.elementData;
            this.size = i2 + 1;
            contentArr[i2] = content;
        } else {
            Content[] contentArr2 = this.elementData;
            System.arraycopy(contentArr2, i, contentArr2, i + 1, i2 - i);
            this.elementData[i] = content;
            this.size++;
        }
        incModCount();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends Content> collection) {
        return addAll(this.size, collection);
    }

    @Override // java.util.AbstractList, java.util.List
    public boolean addAll(int i, Collection<? extends Content> collection) {
        if (collection == null) {
            throw new NullPointerException("Can not add a null collection to the ContentList");
        }
        int i2 = 0;
        checkIndex(i, false);
        if (collection.isEmpty()) {
            return false;
        }
        int size = collection.size();
        if (size == 1) {
            add(i, collection.iterator().next());
            return true;
        }
        ensureCapacity(size() + size);
        int modCount = getModCount();
        int dataModCount = getDataModCount();
        try {
            Iterator<? extends Content> it = collection.iterator();
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
                    setModCount(modCount, dataModCount);
                    throw th;
                }
            }
        }
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        if (this.elementData != null) {
            for (int i = 0; i < this.size; i++) {
                removeParent(this.elementData[i]);
            }
            this.elementData = null;
            this.size = 0;
        }
        incModCount();
    }

    void ensureCapacity(int i) {
        Content[] contentArr = this.elementData;
        if (contentArr == null) {
            this.elementData = new Content[Math.max(i, 4)];
        } else {
            if (i < contentArr.length) {
                return;
            }
            int i2 = ((this.size * 3) / 2) + 1;
            if (i2 >= i) {
                i = i2;
            }
            this.elementData = (Content[]) ArrayCopy.copyOf(contentArr, i);
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public Content get(int i) {
        checkIndex(i, true);
        return this.elementData[i];
    }

    int indexOfFirstElement() {
        if (this.elementData == null) {
            return -1;
        }
        for (int i = 0; i < this.size; i++) {
            if (this.elementData[i] instanceof Element) {
                return i;
            }
        }
        return -1;
    }

    int indexOfDocType() {
        if (this.elementData == null) {
            return -1;
        }
        for (int i = 0; i < this.size; i++) {
            if (this.elementData[i] instanceof DocType) {
                return i;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractList, java.util.List
    public Content remove(int i) {
        checkIndex(i, true);
        Content content = this.elementData[i];
        removeParent(content);
        Content[] contentArr = this.elementData;
        System.arraycopy(contentArr, i + 1, contentArr, i, (this.size - i) - 1);
        Content[] contentArr2 = this.elementData;
        int i2 = this.size - 1;
        this.size = i2;
        contentArr2[i2] = null;
        incModCount();
        return content;
    }

    private static void removeParent(Content content) {
        content.setParent(null);
    }

    @Override // java.util.AbstractList, java.util.List
    public Content set(int i, Content content) {
        checkPreConditions(content, i, true);
        this.parent.canContainContent(content, i, true);
        Content content2 = this.elementData[i];
        removeParent(content2);
        content.setParent(this.parent);
        this.elementData[i] = content;
        incDataModOnly();
        return content2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<Content> iterator() {
        return new CLIterator();
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<Content> listIterator() {
        return new CLListIterator(0);
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<Content> listIterator(int i) {
        return new CLListIterator(i);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        return super.toString();
    }

    private void sortInPlace(int[] iArr) {
        int[] iArrCopyOf = ArrayCopy.copyOf(iArr, iArr.length);
        Arrays.sort(iArrCopyOf);
        int length = iArrCopyOf.length;
        Content[] contentArr = new Content[length];
        for (int i = 0; i < length; i++) {
            contentArr[i] = this.elementData[iArr[i]];
        }
        for (int i2 = 0; i2 < iArr.length; i2++) {
            this.elementData[iArrCopyOf[i2]] = contentArr[i2];
        }
    }

    private final int binarySearch(int[] iArr, int i, int i2, Comparator<? super Content> comparator) {
        int i3 = i - 1;
        Content content = this.elementData[i2];
        int i4 = 0;
        while (i4 <= i3) {
            int i5 = (i4 + i3) >>> 1;
            int iCompare = comparator.compare(content, this.elementData[iArr[i5]]);
            if (iCompare == 0) {
                while (iCompare == 0 && i5 < i3) {
                    int i6 = i5 + 1;
                    if (comparator.compare(content, this.elementData[iArr[i6]]) != 0) {
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

    @Override // java.util.List
    public final void sort(Comparator<? super Content> comparator) {
        if (comparator == null) {
            return;
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

    private final class CLIterator implements Iterator<Content> {
        private boolean canremove;
        private int cursor;
        private int expect;

        private CLIterator() {
            this.expect = -1;
            this.cursor = 0;
            this.canremove = false;
            this.expect = ContentList.this.getModCount();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.cursor < ContentList.this.size;
        }

        @Override // java.util.Iterator
        public Content next() {
            if (ContentList.this.getModCount() == this.expect) {
                if (this.cursor >= ContentList.this.size) {
                    throw new NoSuchElementException("Iterated beyond the end of the ContentList.");
                }
                this.canremove = true;
                Content[] contentArr = ContentList.this.elementData;
                int i = this.cursor;
                this.cursor = i + 1;
                return contentArr[i];
            }
            throw new ConcurrentModificationException("ContentList was modified outside of this Iterator");
        }

        @Override // java.util.Iterator
        public void remove() {
            if (ContentList.this.getModCount() != this.expect) {
                throw new ConcurrentModificationException("ContentList was modified outside of this Iterator");
            }
            if (!this.canremove) {
                throw new IllegalStateException("Can only remove() content after a call to next()");
            }
            this.canremove = false;
            ContentList contentList = ContentList.this;
            int i = this.cursor - 1;
            this.cursor = i;
            contentList.remove(i);
            this.expect = ContentList.this.getModCount();
        }
    }

    private final class CLListIterator implements ListIterator<Content> {
        private boolean canremove = false;
        private boolean canset = false;
        private int cursor;
        private int expectedmod;
        private boolean forward;

        CLListIterator(int i) {
            this.forward = false;
            this.expectedmod = -1;
            this.cursor = -1;
            this.expectedmod = ContentList.this.getModCount();
            this.forward = false;
            ContentList.this.checkIndex(i, false);
            this.cursor = i;
        }

        private void checkConcurrent() {
            if (this.expectedmod != ContentList.this.getModCount()) {
                throw new ConcurrentModificationException("The ContentList supporting this iterator has been modified bysomething other than this Iterator.");
            }
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            return (this.forward ? this.cursor + 1 : this.cursor) < ContentList.this.size;
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return (this.forward ? this.cursor : this.cursor - 1) >= 0;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.forward ? this.cursor + 1 : this.cursor;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.forward ? this.cursor : this.cursor - 1;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public Content next() {
            checkConcurrent();
            int i = this.forward ? this.cursor + 1 : this.cursor;
            if (i >= ContentList.this.size) {
                throw new NoSuchElementException("next() is beyond the end of the Iterator");
            }
            this.cursor = i;
            this.forward = true;
            this.canremove = true;
            this.canset = true;
            return ContentList.this.elementData[this.cursor];
        }

        @Override // java.util.ListIterator
        public Content previous() {
            checkConcurrent();
            int i = this.forward ? this.cursor : this.cursor - 1;
            if (i < 0) {
                throw new NoSuchElementException("previous() is beyond the beginning of the Iterator");
            }
            this.cursor = i;
            this.forward = false;
            this.canremove = true;
            this.canset = true;
            return ContentList.this.elementData[this.cursor];
        }

        @Override // java.util.ListIterator
        public void add(Content content) {
            checkConcurrent();
            int i = this.forward ? this.cursor + 1 : this.cursor;
            ContentList.this.add(i, content);
            this.expectedmod = ContentList.this.getModCount();
            this.canset = false;
            this.canremove = false;
            this.cursor = i;
            this.forward = true;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public void remove() {
            checkConcurrent();
            if (!this.canremove) {
                throw new IllegalStateException("Can not remove an element unless either next() or previous() has been called since the last remove()");
            }
            ContentList.this.remove(this.cursor);
            this.forward = false;
            this.expectedmod = ContentList.this.getModCount();
            this.canremove = false;
            this.canset = false;
        }

        @Override // java.util.ListIterator
        public void set(Content content) {
            checkConcurrent();
            if (!this.canset) {
                throw new IllegalStateException("Can not set an element unless either next() or previous() has been called since the last remove() or set()");
            }
            ContentList.this.set(this.cursor, content);
            this.expectedmod = ContentList.this.getModCount();
        }
    }
}
