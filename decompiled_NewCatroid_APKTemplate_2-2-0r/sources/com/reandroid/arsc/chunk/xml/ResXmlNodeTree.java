package com.reandroid.arsc.chunk.xml;

import com.reandroid.arsc.container.BlockList;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.FilterIterator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes10.dex */
public interface ResXmlNodeTree extends Iterable<ResXmlNode>, JSONConvert<JSONObject> {
    BlockList<ResXmlNode> getNodeListBlockInternal();

    @Override // java.lang.Iterable
    default Iterator<ResXmlNode> iterator() {
        return getNodeListBlockInternal().clonedIterator();
    }

    default ResXmlNode get(int i) {
        return (ResXmlNode) getNodeListBlockInternal().get(i);
    }

    default int size() {
        return getNodeListBlockInternal().size();
    }

    default void add(ResXmlNode resXmlNode) {
        if (resXmlNode == null || resXmlNode == this) {
            return;
        }
        BlockList<ResXmlNode> nodeListBlockInternal = getNodeListBlockInternal();
        if (nodeListBlockInternal.containsExact(resXmlNode)) {
            return;
        }
        nodeListBlockInternal.add(resXmlNode);
    }

    default void add(int i, ResXmlNode resXmlNode) {
        if (resXmlNode == null || resXmlNode == this) {
            return;
        }
        BlockList<ResXmlNode> nodeListBlockInternal = getNodeListBlockInternal();
        if (nodeListBlockInternal.containsExact(resXmlNode)) {
            return;
        }
        nodeListBlockInternal.add(i, resXmlNode);
    }

    default int removeIf(Predicate<? super ResXmlNode> predicate) {
        Iterator<T> itClonedIterator = getNodeListBlockInternal().clonedIterator();
        int i = 0;
        while (itClonedIterator.hasNext()) {
            ResXmlNode resXmlNode = (ResXmlNode) itClonedIterator.next();
            if (predicate.test(resXmlNode) && remove(resXmlNode)) {
                i++;
            }
        }
        return i;
    }

    default boolean remove(ResXmlNode resXmlNode) {
        if (resXmlNode != null && resXmlNode.getParent() != null) {
            resXmlNode.onRemoved();
        }
        return getNodeListBlockInternal().remove(resXmlNode);
    }

    default void clear() {
        for (ResXmlNode resXmlNode : this) {
            if (resXmlNode.getParent() != null) {
                resXmlNode.onRemoved();
            }
        }
        getNodeListBlockInternal().clearChildes();
    }

    default Iterator<ResXmlNode> iterator(Predicate<? super ResXmlNode> predicate) {
        return getNodeListBlockInternal().iterator(predicate);
    }

    default <T1 extends ResXmlNode> Iterator<T1> iterator(Class<T1> cls) {
        return (Iterator<T1>) getNodeListBlockInternal().iterator(cls);
    }

    default Iterator<ResXmlElement> getElements() {
        return iterator(ResXmlElement.class);
    }

    default Iterator<ResXmlElement> getElements(Predicate<? super ResXmlElement> predicate) {
        return FilterIterator.of(getElements(), predicate);
    }

    default Iterator<ResXmlElement> getElements(final String str) {
        return getElements(new Predicate() { // from class: com.reandroid.arsc.chunk.xml.ResXmlNodeTree$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((ResXmlElement) obj).equalsName(str);
            }
        });
    }

    default ResXmlElement getElement(String str) {
        return (ResXmlElement) CollectionUtil.getFirst(getElements(str));
    }

    default List<ResXmlElement> listElements() {
        return CollectionUtil.toList(getElements());
    }

    default List<ResXmlElement> listElements(String str) {
        return CollectionUtil.toList(getElements(str));
    }
}
