package org.apache.xerces.impl.xs.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.xml.namespace.QName;
import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.xs.XSNamedMap;
import org.apache.xerces.xs.XSObject;

/* JADX INFO: loaded from: classes13.dex */
public class XSNamedMapImpl extends AbstractMap implements XSNamedMap {
    public static final XSNamedMapImpl EMPTY_MAP = new XSNamedMapImpl(new XSObject[0], 0);
    XSObject[] fArray;
    private Set fEntrySet;
    int fLength;
    final SymbolHash[] fMaps;
    final int fNSNum;
    final String[] fNamespaces;

    private static final class XSNamedMapEntry implements Map.Entry {
        private final QName key;
        private final XSObject value;

        public XSNamedMapEntry(QName qName, XSObject xSObject) {
            this.key = qName;
            this.value = xSObject;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            QName qName = this.key;
            if (qName == null) {
                if (key != null) {
                    return false;
                }
            } else if (!qName.equals(key)) {
                return false;
            }
            XSObject xSObject = this.value;
            if (xSObject == null) {
                if (value != null) {
                    return false;
                }
            } else if (!xSObject.equals(value)) {
                return false;
            }
            return true;
        }

        @Override // java.util.Map.Entry
        public Object getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public Object getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            QName qName = this.key;
            int iHashCode = qName == null ? 0 : qName.hashCode();
            XSObject xSObject = this.value;
            return iHashCode ^ (xSObject != null ? xSObject.hashCode() : 0);
        }

        @Override // java.util.Map.Entry
        public Object setValue(Object obj) {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(String.valueOf(this.key));
            stringBuffer.append('=');
            stringBuffer.append(String.valueOf(this.value));
            return stringBuffer.toString();
        }
    }

    public XSNamedMapImpl(String str, SymbolHash symbolHash) {
        this.fArray = null;
        this.fLength = -1;
        this.fEntrySet = null;
        this.fNamespaces = new String[]{str};
        this.fMaps = new SymbolHash[]{symbolHash};
        this.fNSNum = 1;
    }

    public XSNamedMapImpl(String[] strArr, SymbolHash[] symbolHashArr, int i) {
        this.fArray = null;
        this.fLength = -1;
        this.fEntrySet = null;
        this.fNamespaces = strArr;
        this.fMaps = symbolHashArr;
        this.fNSNum = i;
    }

    public XSNamedMapImpl(XSObject[] xSObjectArr, int i) {
        this.fArray = null;
        this.fLength = -1;
        this.fEntrySet = null;
        if (i == 0) {
            this.fNamespaces = null;
            this.fMaps = null;
            this.fNSNum = 0;
            this.fArray = xSObjectArr;
            this.fLength = 0;
            return;
        }
        this.fNamespaces = new String[]{xSObjectArr[0].getNamespace()};
        this.fMaps = null;
        this.fNSNum = 1;
        this.fArray = xSObjectArr;
        this.fLength = i;
    }

    static boolean isEqual(String str, String str2) {
        if (str != null) {
            return str.equals(str2);
        }
        return str2 == null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        return get(obj) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public synchronized Set entrySet() {
        if (this.fEntrySet == null) {
            final int length = getLength();
            final XSNamedMapEntry[] xSNamedMapEntryArr = new XSNamedMapEntry[length];
            for (int i = 0; i < length; i++) {
                XSObject xSObjectItem = item(i);
                xSNamedMapEntryArr[i] = new XSNamedMapEntry(new QName(xSObjectItem.getNamespace(), xSObjectItem.getName()), xSObjectItem);
            }
            this.fEntrySet = new AbstractSet() { // from class: org.apache.xerces.impl.xs.util.XSNamedMapImpl.1
                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public Iterator iterator() {
                    return new Iterator() { // from class: org.apache.xerces.impl.xs.util.XSNamedMapImpl.1.1
                        private int index = 0;

                        @Override // java.util.Iterator
                        public boolean hasNext() {
                            return this.index < length;
                        }

                        @Override // java.util.Iterator
                        public Object next() {
                            if (this.index >= length) {
                                throw new NoSuchElementException();
                            }
                            XSNamedMapEntry[] xSNamedMapEntryArr2 = xSNamedMapEntryArr;
                            int i2 = this.index;
                            this.index = i2 + 1;
                            return xSNamedMapEntryArr2[i2];
                        }

                        @Override // java.util.Iterator
                        public void remove() {
                            throw new UnsupportedOperationException();
                        }
                    };
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
                public int size() {
                    return length;
                }
            };
        }
        return this.fEntrySet;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object get(Object obj) {
        if (!(obj instanceof QName)) {
            return null;
        }
        QName qName = (QName) obj;
        String namespaceURI = qName.getNamespaceURI();
        return itemByName("".equals(namespaceURI) ? null : namespaceURI, qName.getLocalPart());
    }

    public synchronized int getLength() {
        if (this.fLength == -1) {
            this.fLength = 0;
            for (int i = 0; i < this.fNSNum; i++) {
                this.fLength += this.fMaps[i].getLength();
            }
        }
        return this.fLength;
    }

    public synchronized XSObject item(int i) {
        if (this.fArray == null) {
            getLength();
            this.fArray = new XSObject[this.fLength];
            int values = 0;
            for (int i2 = 0; i2 < this.fNSNum; i2++) {
                values += this.fMaps[i2].getValues(this.fArray, values);
            }
        }
        if (i >= 0 && i < this.fLength) {
            return this.fArray[i];
        }
        return null;
    }

    public XSObject itemByName(String str, String str2) {
        for (int i = 0; i < this.fNSNum; i++) {
            if (isEqual(str, this.fNamespaces[i])) {
                SymbolHash[] symbolHashArr = this.fMaps;
                if (symbolHashArr != null) {
                    return (XSObject) symbolHashArr[i].get(str2);
                }
                for (int i2 = 0; i2 < this.fLength; i2++) {
                    XSObject xSObject = this.fArray[i2];
                    if (xSObject.getName().equals(str2)) {
                        return xSObject;
                    }
                }
                return null;
            }
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return getLength();
    }
}
