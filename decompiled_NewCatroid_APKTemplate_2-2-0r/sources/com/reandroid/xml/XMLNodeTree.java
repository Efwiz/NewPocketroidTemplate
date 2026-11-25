package com.reandroid.xml;

import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.IndexIterator;
import com.reandroid.utils.collection.RecursiveIterator;
import com.reandroid.utils.collection.SizedSupplier;
import com.reandroid.xml.base.NodeTree;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes11.dex */
public abstract class XMLNodeTree extends XMLNode implements NodeTree<XMLNode>, Iterable<XMLNode>, SizedSupplier<XMLNode> {
    private static final ArrayCollection<XMLNode> EMPTY = ArrayCollection.empty();
    private static final int TRIM_INTERVAL = 1000;
    private int lastTrimSize;
    private ArrayCollection<XMLNode> mChildes = EMPTY;

    abstract void endSerialize(XmlSerializer xmlSerializer) throws IOException;

    abstract void startSerialize(XmlSerializer xmlSerializer) throws IOException;

    public XMLNode getLast() {
        int size = size();
        if (size == 0) {
            return null;
        }
        return this.mChildes.get(size - 1);
    }

    public void clear() {
        if (size() == 0) {
            return;
        }
        synchronized (this) {
            this.mChildes.clear();
            this.mChildes.trimToSize();
            this.lastTrimSize = 0;
        }
    }

    public Iterator<XMLNode> iterator(Predicate<? super XMLNode> predicate) {
        return new IndexIterator(this, predicate);
    }

    @Override // com.reandroid.xml.XMLNode, com.reandroid.xml.base.NodeTree, java.lang.Iterable
    public Iterator<XMLNode> iterator() {
        return new IndexIterator(this);
    }

    public Iterator<XMLNode> recursiveNodes() {
        return RecursiveIterator.of(this, new Function() { // from class: com.reandroid.xml.XMLNodeTree$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((XMLNode) obj).iterator();
            }
        });
    }

    @Override // com.reandroid.xml.base.NodeTree, com.reandroid.utils.collection.SizedItem
    public int size() {
        return this.mChildes.size();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.reandroid.utils.collection.SizedSupplier
    public XMLNode get(int i) {
        return this.mChildes.get(i);
    }

    public void addAll(Iterable<? extends XMLNode> iterable) {
        Iterator<? extends XMLNode> it = iterable.iterator();
        while (it.hasNext()) {
            add(it.next());
        }
    }

    @Override // com.reandroid.xml.base.NodeTree
    public boolean add(XMLNode xMLNode) {
        boolean zAdd;
        if (xMLNode == null || xMLNode == this) {
            return false;
        }
        synchronized (this) {
            if (this.mChildes == EMPTY) {
                this.mChildes = new ArrayCollection<>();
            }
            zAdd = this.mChildes.add(xMLNode);
            xMLNode.setParentNode(this);
            if (this.mChildes.size() - this.lastTrimSize > 1000) {
                this.mChildes.trimToSize();
                this.lastTrimSize = this.mChildes.size();
            }
        }
        return zAdd;
    }

    @Override // com.reandroid.xml.base.NodeTree
    public void add(int i, XMLNode xMLNode) {
        if (xMLNode == null || xMLNode == this) {
            return;
        }
        synchronized (this) {
            if (this.mChildes == EMPTY) {
                this.mChildes = new ArrayCollection<>();
            }
            this.mChildes.add(i, xMLNode);
            xMLNode.setParentNode(this);
            if (this.mChildes.size() - this.lastTrimSize > 1000) {
                this.mChildes.trimToSize();
                this.lastTrimSize = this.mChildes.size();
            }
        }
    }

    public int indexOf(XMLNode xMLNode) {
        return this.mChildes.indexOf(xMLNode);
    }

    @Override // com.reandroid.xml.base.NodeTree
    public boolean remove(XMLNode xMLNode) {
        synchronized (this) {
            if (xMLNode != null) {
                if (this.mChildes.remove(xMLNode)) {
                    xMLNode.setParentNode(null);
                    return true;
                }
            }
            return false;
        }
    }

    @Override // com.reandroid.xml.base.NodeTree
    public XMLNode remove(int i) {
        XMLNode xMLNodeRemove;
        synchronized (this) {
            xMLNodeRemove = this.mChildes.remove(i);
            if (xMLNodeRemove != null) {
                xMLNodeRemove.setParentNode(null);
            }
        }
        return xMLNodeRemove;
    }

    public boolean remove(Predicate<? extends XMLNode> predicate) {
        boolean zRemove;
        synchronized (this) {
            zRemove = this.mChildes.remove(predicate);
        }
        return zRemove;
    }

    public boolean sort(Comparator<? super XMLNode> comparator) {
        boolean zSortItems;
        synchronized (this) {
            zSortItems = this.mChildes.sortItems(comparator);
        }
        return zSortItems;
    }

    @Override // com.reandroid.xml.base.XmlSerializable
    public void serialize(XmlSerializer xmlSerializer) throws IOException {
        startSerialize(xmlSerializer);
        serializeChildes(xmlSerializer);
        endSerialize(xmlSerializer);
    }

    private void serializeChildes(XmlSerializer xmlSerializer) throws IOException {
        Iterator<XMLNode> it = iterator();
        while (it.hasNext()) {
            it.next().serialize(xmlSerializer);
        }
    }

    public XMLElement newElement() {
        return new XMLElement();
    }

    public XMLText newText() {
        return new XMLText();
    }

    public XMLComment newComment() {
        return new XMLComment();
    }

    public XMLAttribute newAttribute() {
        return new XMLAttribute();
    }

    public XMLNamespace newNamespace(String str, String str2) {
        return new XMLNamespace(str, str2);
    }
}
