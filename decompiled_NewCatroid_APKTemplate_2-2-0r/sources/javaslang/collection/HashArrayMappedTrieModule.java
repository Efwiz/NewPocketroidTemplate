package javaslang.collection;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.control.Option;

/* JADX INFO: compiled from: HashArrayMappedTrie.java */
/* JADX INFO: loaded from: classes11.dex */
interface HashArrayMappedTrieModule {

    /* JADX INFO: compiled from: HashArrayMappedTrie.java */
    public enum Action {
        PUT,
        REMOVE
    }

    /* JADX INFO: compiled from: HashArrayMappedTrie.java */
    public static class LeafNodeIterator<K, V> extends AbstractIterator<LeafNode<K, V>> {
        private final int[] indexes;
        private int level;
        private final Object[] nodes;
        private int ptr = 0;
        private final int total;

        LeafNodeIterator(AbstractNode<K, V> abstractNode) {
            Object[] objArr = new Object[8];
            this.nodes = objArr;
            int[] iArr = new int[8];
            this.indexes = iArr;
            this.total = abstractNode.size();
            this.level = downstairs(objArr, iArr, abstractNode, 0);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.ptr < this.total;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // javaslang.collection.AbstractIterator
        public LeafNode<K, V> getNext() {
            Object objFindNextLeaf = this.nodes[this.level];
            while (!(objFindNextLeaf instanceof LeafNode)) {
                objFindNextLeaf = findNextLeaf();
            }
            this.ptr++;
            if (objFindNextLeaf instanceof LeafList) {
                LeafList leafList = (LeafList) objFindNextLeaf;
                this.nodes[this.level] = leafList.tail;
                return leafList;
            }
            this.nodes[this.level] = EmptyNode.instance();
            return (LeafSingleton) objFindNextLeaf;
        }

        private Object findNextLeaf() {
            AbstractNode child = null;
            do {
                int i = this.level;
                if (i <= 0) {
                    break;
                }
                int i2 = i - 1;
                this.level = i2;
                int[] iArr = this.indexes;
                int i3 = iArr[i2] + 1;
                iArr[i2] = i3;
                child = getChild((AbstractNode) this.nodes[i2], i3);
            } while (child == null);
            int iDownstairs = downstairs(this.nodes, this.indexes, child, this.level + 1);
            this.level = iDownstairs;
            return this.nodes[iDownstairs];
        }

        private static <K, V> int downstairs(Object[] objArr, int[] iArr, AbstractNode<K, V> abstractNode, int i) {
            while (true) {
                objArr[i] = abstractNode;
                iArr[i] = 0;
                abstractNode = getChild(abstractNode, 0);
                if (abstractNode == null) {
                    return i;
                }
                i++;
            }
        }

        private static <K, V> AbstractNode<K, V> getChild(AbstractNode<K, V> abstractNode, int i) {
            if (!(abstractNode instanceof IndexedNode)) {
                if (!(abstractNode instanceof ArrayNode)) {
                    return null;
                }
                ArrayNode arrayNode = (ArrayNode) abstractNode;
                if (i < 32) {
                    return (AbstractNode) arrayNode.subNodes[i];
                }
                return null;
            }
            Object[] objArr = ((IndexedNode) abstractNode).subNodes;
            if (i < objArr.length) {
                return (AbstractNode) objArr[i];
            }
            return null;
        }
    }

    /* JADX INFO: compiled from: HashArrayMappedTrie.java */
    public static abstract class AbstractNode<K, V> implements HashArrayMappedTrie<K, V> {
        static int bitCount(int i) {
            int i2 = i - ((i >> 1) & 1431655765);
            int i3 = (i2 & 858993459) + ((i2 >> 2) & 858993459);
            int i4 = 252645135 & (i3 + (i3 >> 4));
            int i5 = i4 + (i4 >> 8);
            return (i5 + (i5 >> 16)) & 127;
        }

        static int hashFragment(int i, int i2) {
            return (i2 >>> i) & 31;
        }

        static int toBitmap(int i) {
            return 1 << i;
        }

        abstract Option<V> lookup(int i, int i2, K k);

        abstract AbstractNode<K, V> modify(int i, int i2, K k, V v, Action action);

        static int fromBitmap(int i, int i2) {
            return bitCount(i & (i2 - 1));
        }

        static Object[] update(Object[] objArr, int i, Object obj) {
            Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
            objArrCopyOf[i] = obj;
            return objArrCopyOf;
        }

        static Object[] remove(Object[] objArr, int i) {
            Object[] objArr2 = new Object[objArr.length - 1];
            System.arraycopy(objArr, 0, objArr2, 0, i);
            System.arraycopy(objArr, i + 1, objArr2, i, (objArr.length - i) - 1);
            return objArr2;
        }

        static Object[] insert(Object[] objArr, int i, Object obj) {
            Object[] objArr2 = new Object[objArr.length + 1];
            System.arraycopy(objArr, 0, objArr2, 0, i);
            objArr2[i] = obj;
            System.arraycopy(objArr, i, objArr2, i + 1, objArr.length - i);
            return objArr2;
        }

        Iterator<LeafNode<K, V>> nodes() {
            return new LeafNodeIterator(this);
        }

        @Override // javaslang.collection.HashArrayMappedTrie, java.lang.Iterable
        public Iterator<Tuple2<K, V>> iterator() {
            return (Iterator<Tuple2<K, V>>) nodes().map(new Function() { // from class: javaslang.collection.HashArrayMappedTrieModule$AbstractNode$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    HashArrayMappedTrieModule.LeafNode leafNode = (HashArrayMappedTrieModule.LeafNode) obj;
                    return Tuple.of(leafNode.key(), leafNode.value());
                }
            });
        }

        @Override // javaslang.collection.HashArrayMappedTrie
        public Iterator<K> keysIterator() {
            return (Iterator<K>) nodes().map(new Function() { // from class: javaslang.collection.HashArrayMappedTrieModule$AbstractNode$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((HashArrayMappedTrieModule.LeafNode) obj).key();
                }
            });
        }

        @Override // javaslang.collection.HashArrayMappedTrie
        public Option<V> get(K k) {
            return lookup(0, Objects.hashCode(k), k);
        }

        @Override // javaslang.collection.HashArrayMappedTrie
        public boolean containsKey(K k) {
            return get(k).isDefined();
        }

        @Override // javaslang.collection.HashArrayMappedTrie
        public HashArrayMappedTrie<K, V> put(K k, V v) {
            return modify(0, Objects.hashCode(k), k, v, Action.PUT);
        }

        @Override // javaslang.collection.HashArrayMappedTrie
        public HashArrayMappedTrie<K, V> remove(K k) {
            return modify(0, Objects.hashCode(k), k, null, Action.REMOVE);
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof HashArrayMappedTrie) {
                HashArrayMappedTrie hashArrayMappedTrie = (HashArrayMappedTrie) obj;
                if (size() == hashArrayMappedTrie.size()) {
                    Iterator<Tuple2<K, V>> it = iterator();
                    while (it.hasNext()) {
                        Tuple2<K, V> next = it.next();
                        Option<V> option = hashArrayMappedTrie.get(next._1);
                        if (!option.isDefined() || !option.get().equals(next._2)) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }

        static /* synthetic */ String lambda$toString$1(Tuple2 tuple2) {
            return tuple2._1 + " -> " + tuple2._2;
        }

        public final String toString() {
            return iterator().map(new Function() { // from class: javaslang.collection.HashArrayMappedTrieModule$AbstractNode$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return HashArrayMappedTrieModule.AbstractNode.lambda$toString$1((Tuple2) obj);
                }
            }).mkString("HashArrayMappedTrie(", ", ", Constant.AFTER_QUTO);
        }
    }

    /* JADX INFO: compiled from: HashArrayMappedTrie.java */
    public static final class EmptyNode<K, V> extends AbstractNode<K, V> implements Serializable {
        private static final EmptyNode<?, ?> INSTANCE = new EmptyNode<>();
        private static final long serialVersionUID = 1;

        public int hashCode() {
            return 1;
        }

        @Override // javaslang.collection.HashArrayMappedTrie
        public boolean isEmpty() {
            return true;
        }

        @Override // javaslang.collection.HashArrayMappedTrie
        public int size() {
            return 0;
        }

        private EmptyNode() {
        }

        static <K, V> EmptyNode<K, V> instance() {
            return (EmptyNode<K, V>) INSTANCE;
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.AbstractNode
        Option<V> lookup(int i, int i2, K k) {
            return Option.none();
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.AbstractNode
        AbstractNode<K, V> modify(int i, int i2, K k, V v, Action action) {
            return action == Action.REMOVE ? this : new LeafSingleton(i2, k, v);
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.AbstractNode
        public Iterator<LeafNode<K, V>> nodes() {
            return Iterator.empty();
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    /* JADX INFO: compiled from: HashArrayMappedTrie.java */
    public static abstract class LeafNode<K, V> extends AbstractNode<K, V> {
        abstract int hash();

        @Override // javaslang.collection.HashArrayMappedTrie
        public boolean isEmpty() {
            return false;
        }

        abstract K key();

        abstract V value();

        static <K, V> AbstractNode<K, V> mergeLeaves(int i, LeafNode<K, V> leafNode, LeafSingleton<K, V> leafSingleton) {
            int iHash = leafNode.hash();
            int iHash2 = leafSingleton.hash();
            if (iHash == iHash2) {
                return new LeafList(iHash, leafSingleton.key(), leafSingleton.value(), leafNode);
            }
            int iHashFragment = hashFragment(i, iHash);
            int iHashFragment2 = hashFragment(i, iHash2);
            int bitmap = toBitmap(iHashFragment) | toBitmap(iHashFragment2);
            if (iHashFragment == iHashFragment2) {
                AbstractNode abstractNodeMergeLeaves = mergeLeaves(i + 5, leafNode, leafSingleton);
                return new IndexedNode(bitmap, abstractNodeMergeLeaves.size(), new Object[]{abstractNodeMergeLeaves});
            }
            return new IndexedNode(bitmap, leafNode.size() + leafSingleton.size(), iHashFragment < iHashFragment2 ? new Object[]{leafNode, leafSingleton} : new Object[]{leafSingleton, leafNode});
        }
    }

    /* JADX INFO: compiled from: HashArrayMappedTrie.java */
    public static final class LeafSingleton<K, V> extends LeafNode<K, V> implements Serializable {
        private static final long serialVersionUID = 1;
        private final int hash;
        private final K key;
        private final V value;

        @Override // javaslang.collection.HashArrayMappedTrie
        public int size() {
            return 1;
        }

        LeafSingleton(int i, K k, V v) {
            this.hash = i;
            this.key = k;
            this.value = v;
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.AbstractNode
        Option<V> lookup(int i, int i2, K k) {
            if (i2 == this.hash && Objects.equals(k, this.key)) {
                return Option.some(this.value);
            }
            return Option.none();
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.AbstractNode
        AbstractNode<K, V> modify(int i, int i2, K k, V v, Action action) {
            if (i2 == this.hash && Objects.equals(k, this.key)) {
                return action == Action.REMOVE ? EmptyNode.instance() : new LeafSingleton(this.hash, k, v);
            }
            return action == Action.REMOVE ? this : mergeLeaves(i, this, new LeafSingleton(i2, k, v));
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.AbstractNode
        public Iterator<LeafNode<K, V>> nodes() {
            return Iterator.of(this);
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.hash), this.value);
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.LeafNode
        int hash() {
            return this.hash;
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.LeafNode
        K key() {
            return this.key;
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.LeafNode
        V value() {
            return this.value;
        }
    }

    /* JADX INFO: compiled from: HashArrayMappedTrie.java */
    public static final class LeafList<K, V> extends LeafNode<K, V> implements Serializable {
        private static final long serialVersionUID = 1;
        private final int hash;
        private final K key;
        private final int size;
        private final LeafNode<K, V> tail;
        private final V value;

        LeafList(int i, K k, V v, LeafNode<K, V> leafNode) {
            this.hash = i;
            this.key = k;
            this.value = v;
            this.size = leafNode.size() + 1;
            this.tail = leafNode;
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.AbstractNode
        Option<V> lookup(int i, int i2, final K k) {
            if (this.hash != i2) {
                return Option.none();
            }
            return iterator().find(new Predicate() { // from class: javaslang.collection.HashArrayMappedTrieModule$LeafList$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Objects.equals(((Tuple2) obj)._1, k);
                }
            }).map(new Function() { // from class: javaslang.collection.HashArrayMappedTrieModule$LeafList$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((Tuple2) obj)._2;
                }
            });
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.AbstractNode
        AbstractNode<K, V> modify(int i, int i2, K k, V v, Action action) {
            if (i2 != this.hash) {
                return action == Action.REMOVE ? this : mergeLeaves(i, this, new LeafSingleton(i2, k, v));
            }
            AbstractNode<K, V> abstractNodeRemoveElement = removeElement(k);
            return action == Action.REMOVE ? abstractNodeRemoveElement : new LeafList(this.hash, k, v, (LeafNode) abstractNodeRemoveElement);
        }

        private static <K, V> AbstractNode<K, V> mergeNodes(LeafNode<K, V> leafNode, LeafNode<K, V> leafNode2) {
            if (leafNode2 == null) {
                return leafNode;
            }
            if (leafNode instanceof LeafSingleton) {
                return new LeafList(leafNode.hash(), leafNode.key(), leafNode.value(), leafNode2);
            }
            if (leafNode2 instanceof LeafSingleton) {
                return new LeafList(leafNode2.hash(), leafNode2.key(), leafNode2.value(), leafNode);
            }
            while (leafNode2 instanceof LeafList) {
                LeafList leafList = (LeafList) leafNode2;
                LeafList leafList2 = new LeafList(leafList.hash, leafList.key, leafList.value, leafNode);
                leafNode2 = leafList.tail;
                leafNode = leafList2;
            }
            return new LeafList(leafNode2.hash(), leafNode2.key(), leafNode2.value(), leafNode);
        }

        private AbstractNode<K, V> removeElement(K k) {
            if (Objects.equals(k, this.key)) {
                return this.tail;
            }
            LeafNode leafSingleton = new LeafSingleton(this.hash, this.key, this.value);
            LeafNode<K, V> leafNode = this.tail;
            boolean z = false;
            while (!z && leafNode != null) {
                if (Objects.equals(k, leafNode.key())) {
                    z = true;
                } else {
                    leafSingleton = new LeafList(leafNode.hash(), leafNode.key(), leafNode.value(), leafSingleton);
                }
                leafNode = leafNode instanceof LeafList ? ((LeafList) leafNode).tail : null;
            }
            return mergeNodes(leafSingleton, leafNode);
        }

        @Override // javaslang.collection.HashArrayMappedTrie
        public int size() {
            return this.size;
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.AbstractNode
        public Iterator<LeafNode<K, V>> nodes() {
            return new AbstractIterator<LeafNode<K, V>>() { // from class: javaslang.collection.HashArrayMappedTrieModule.LeafList.1
                LeafNode<K, V> node;

                {
                    this.node = LeafList.this;
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.node != null;
                }

                @Override // javaslang.collection.AbstractIterator
                public LeafNode<K, V> getNext() {
                    LeafNode<K, V> leafNode = this.node;
                    if (!(leafNode instanceof LeafSingleton)) {
                        this.node = ((LeafList) leafNode).tail;
                    } else {
                        this.node = null;
                    }
                    return leafNode;
                }
            };
        }

        public int hashCode() {
            Iterator<LeafNode<K, V>> iteratorNodes = nodes();
            int iHash = 0;
            while (iteratorNodes.hasNext()) {
                LeafNode<K, V> next = iteratorNodes.next();
                iHash += Objects.hash(next.key(), next.value());
            }
            return iHash;
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.LeafNode
        int hash() {
            return this.hash;
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.LeafNode
        K key() {
            return this.key;
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.LeafNode
        V value() {
            return this.value;
        }
    }

    /* JADX INFO: compiled from: HashArrayMappedTrie.java */
    public static final class IndexedNode<K, V> extends AbstractNode<K, V> implements Serializable {
        private static final long serialVersionUID = 1;
        private final int bitmap;
        private final int size;
        private final Object[] subNodes;

        @Override // javaslang.collection.HashArrayMappedTrie
        public boolean isEmpty() {
            return false;
        }

        IndexedNode(int i, int i2, Object[] objArr) {
            this.bitmap = i;
            this.size = i2;
            this.subNodes = objArr;
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.AbstractNode
        Option<V> lookup(int i, int i2, K k) {
            int bitmap = toBitmap(hashFragment(i, i2));
            int i3 = this.bitmap;
            if ((i3 & bitmap) != 0) {
                return ((AbstractNode) this.subNodes[fromBitmap(i3, bitmap)]).lookup(i + 5, i2, k);
            }
            return Option.none();
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.AbstractNode
        AbstractNode<K, V> modify(int i, int i2, K k, V v, Action action) {
            AbstractNode<K, V> abstractNodeModify;
            int i3;
            int iHashFragment = hashFragment(i, i2);
            int bitmap = toBitmap(iHashFragment);
            int iFromBitmap = fromBitmap(this.bitmap, bitmap);
            int i4 = this.bitmap;
            boolean z = false;
            boolean z2 = (i4 & bitmap) != 0;
            AbstractNode abstractNode = z2 ? (AbstractNode) this.subNodes[iFromBitmap] : null;
            if (z2) {
                abstractNodeModify = abstractNode.modify(i + 5, i2, k, v, action);
            } else {
                abstractNodeModify = EmptyNode.instance().modify(i + 5, i2, k, v, action);
            }
            boolean z3 = z2 && abstractNodeModify.isEmpty();
            if (!z2 && !abstractNodeModify.isEmpty()) {
                z = true;
            }
            if (z3) {
                i3 = (~bitmap) & i4;
            } else {
                i3 = z ? bitmap | i4 : i4;
            }
            if (i3 == 0) {
                return EmptyNode.instance();
            }
            if (z3) {
                Object[] objArr = this.subNodes;
                if (objArr.length <= 2) {
                    Object obj = objArr[iFromBitmap ^ 1];
                    if (obj instanceof LeafNode) {
                        return (AbstractNode) obj;
                    }
                }
                return new IndexedNode(i3, this.size - abstractNode.size(), remove(this.subNodes, iFromBitmap));
            }
            if (!z) {
                return !z2 ? this : new IndexedNode(i3, (this.size - abstractNode.size()) + abstractNodeModify.size(), update(this.subNodes, iFromBitmap, abstractNodeModify));
            }
            Object[] objArr2 = this.subNodes;
            if (objArr2.length >= 16) {
                return expand(iHashFragment, abstractNodeModify, i4, objArr2);
            }
            return new IndexedNode(i3, this.size + abstractNodeModify.size(), insert(this.subNodes, iFromBitmap, abstractNodeModify));
        }

        private ArrayNode<K, V> expand(int i, AbstractNode<K, V> abstractNode, int i2, Object[] objArr) {
            Object[] objArr2 = new Object[32];
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < 32; i5++) {
                if ((i2 & 1) != 0) {
                    objArr2[i5] = objArr[i4];
                    i3++;
                    i4++;
                } else if (i5 == i) {
                    objArr2[i5] = abstractNode;
                    i3++;
                } else {
                    objArr2[i5] = EmptyNode.instance();
                }
                i2 >>>= 1;
            }
            return new ArrayNode<>(i3, this.size + abstractNode.size(), objArr2);
        }

        @Override // javaslang.collection.HashArrayMappedTrie
        public int size() {
            return this.size;
        }

        public int hashCode() {
            return Objects.hash(this.subNodes);
        }
    }

    /* JADX INFO: compiled from: HashArrayMappedTrie.java */
    public static final class ArrayNode<K, V> extends AbstractNode<K, V> implements Serializable {
        private static final long serialVersionUID = 1;
        private final int count;
        private final int size;
        private final Object[] subNodes;

        @Override // javaslang.collection.HashArrayMappedTrie
        public boolean isEmpty() {
            return false;
        }

        ArrayNode(int i, int i2, Object[] objArr) {
            this.subNodes = objArr;
            this.count = i;
            this.size = i2;
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.AbstractNode
        Option<V> lookup(int i, int i2, K k) {
            return ((AbstractNode) this.subNodes[hashFragment(i, i2)]).lookup(i + 5, i2, k);
        }

        @Override // javaslang.collection.HashArrayMappedTrieModule.AbstractNode
        AbstractNode<K, V> modify(int i, int i2, K k, V v, Action action) {
            int iHashFragment = hashFragment(i, i2);
            AbstractNode abstractNode = (AbstractNode) this.subNodes[iHashFragment];
            AbstractNode<K, V> abstractNodeModify = abstractNode.modify(i + 5, i2, k, v, action);
            if (abstractNode.isEmpty() && !abstractNodeModify.isEmpty()) {
                return new ArrayNode(this.count + 1, this.size + abstractNodeModify.size(), update(this.subNodes, iHashFragment, abstractNodeModify));
            }
            if (!abstractNode.isEmpty() && abstractNodeModify.isEmpty()) {
                int i3 = this.count;
                if (i3 - 1 <= 8) {
                    return pack(iHashFragment, this.subNodes);
                }
                return new ArrayNode(i3 - 1, this.size - abstractNode.size(), update(this.subNodes, iHashFragment, EmptyNode.instance()));
            }
            return new ArrayNode(this.count, (this.size - abstractNode.size()) + abstractNodeModify.size(), update(this.subNodes, iHashFragment, abstractNodeModify));
        }

        private IndexedNode<K, V> pack(int i, Object[] objArr) {
            Object[] objArr2 = new Object[this.count - 1];
            int i2 = 0;
            int size = 0;
            int i3 = 0;
            for (int i4 = 0; i4 < 32; i4++) {
                AbstractNode abstractNode = (AbstractNode) objArr[i4];
                if (i4 != i && !abstractNode.isEmpty()) {
                    size += abstractNode.size();
                    objArr2[i3] = abstractNode;
                    i2 |= 1 << i4;
                    i3++;
                }
            }
            return new IndexedNode<>(i2, size, objArr2);
        }

        @Override // javaslang.collection.HashArrayMappedTrie
        public int size() {
            return this.size;
        }

        public int hashCode() {
            return Objects.hash(this.subNodes);
        }
    }
}
