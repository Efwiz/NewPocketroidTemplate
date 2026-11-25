package org.apache.xerces.impl.xs;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Vector;
import org.apache.xerces.impl.xs.util.StringListImpl;
import org.apache.xerces.impl.xs.util.XSNamedMap4Types;
import org.apache.xerces.impl.xs.util.XSNamedMapImpl;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xs.StringList;
import org.apache.xerces.xs.XSAttributeDeclaration;
import org.apache.xerces.xs.XSAttributeGroupDefinition;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSIDCDefinition;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSModelGroupDefinition;
import org.apache.xerces.xs.XSNamedMap;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSNamespaceItemList;
import org.apache.xerces.xs.XSNotationDeclaration;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSTypeDefinition;

/* JADX INFO: loaded from: classes13.dex */
public final class XSModelImpl extends AbstractList implements XSModel, XSNamespaceItemList {
    private static final boolean[] GLOBAL_COMP = {false, true, true, true, false, true, true, false, false, false, true, true, false, false, false, true, true};
    private static final short MAX_COMP_IDX = 16;
    private XSObjectList fAnnotations;
    private final XSNamedMap[] fGlobalComponents;
    private final int fGrammarCount;
    private final SchemaGrammar[] fGrammarList;
    private final SymbolHash fGrammarMap;
    private final boolean fHasIDC;
    private final XSNamedMap[][] fNSComponents;
    private final String[] fNamespaces;
    private final StringList fNamespacesList;
    private final SymbolHash fSubGroupMap;

    private final class XSNamespaceItemListIterator implements ListIterator {
        private int index;

        public XSNamespaceItemListIterator(int i) {
            this.index = i;
        }

        @Override // java.util.ListIterator
        public void add(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            return this.index < XSModelImpl.this.fGrammarCount;
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.index > 0;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public Object next() {
            if (this.index >= XSModelImpl.this.fGrammarCount) {
                throw new NoSuchElementException();
            }
            SchemaGrammar[] schemaGrammarArr = XSModelImpl.this.fGrammarList;
            int i = this.index;
            this.index = i + 1;
            return schemaGrammarArr[i];
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.index;
        }

        @Override // java.util.ListIterator
        public Object previous() {
            if (this.index <= 0) {
                throw new NoSuchElementException();
            }
            SchemaGrammar[] schemaGrammarArr = XSModelImpl.this.fGrammarList;
            int i = this.index - 1;
            this.index = i;
            return schemaGrammarArr[i];
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.index - 1;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.ListIterator
        public void set(Object obj) {
            throw new UnsupportedOperationException();
        }
    }

    public XSModelImpl(SchemaGrammar[] schemaGrammarArr) {
        this(schemaGrammarArr, (short) 1);
    }

    public XSModelImpl(SchemaGrammar[] schemaGrammarArr, short s) {
        this.fAnnotations = null;
        int length = schemaGrammarArr.length;
        int i = length + 1;
        int iMax = Math.max(i, 5);
        String[] strArr = new String[iMax];
        SchemaGrammar[] schemaGrammarArr2 = new SchemaGrammar[iMax];
        boolean z = false;
        for (int i2 = 0; i2 < length; i2++) {
            SchemaGrammar schemaGrammar = schemaGrammarArr[i2];
            String targetNamespace = schemaGrammar.getTargetNamespace();
            strArr[i2] = targetNamespace;
            schemaGrammarArr2[i2] = schemaGrammar;
            if (targetNamespace == SchemaSymbols.URI_SCHEMAFORSCHEMA) {
                z = true;
            }
        }
        if (!z) {
            strArr[length] = SchemaSymbols.URI_SCHEMAFORSCHEMA;
            schemaGrammarArr2[length] = SchemaGrammar.getS4SGrammar(s);
            length = i;
        }
        for (int i3 = 0; i3 < length; i3++) {
            Vector importedGrammars = schemaGrammarArr2[i3].getImportedGrammars();
            for (int size = importedGrammars == null ? -1 : importedGrammars.size() - 1; size >= 0; size--) {
                SchemaGrammar schemaGrammar2 = (SchemaGrammar) importedGrammars.elementAt(size);
                int i4 = 0;
                while (i4 < length && schemaGrammar2 != schemaGrammarArr2[i4]) {
                    i4++;
                }
                if (i4 == length) {
                    if (length == schemaGrammarArr2.length) {
                        int i5 = length * 2;
                        String[] strArr2 = new String[i5];
                        System.arraycopy(strArr, 0, strArr2, 0, length);
                        SchemaGrammar[] schemaGrammarArr3 = new SchemaGrammar[i5];
                        System.arraycopy(schemaGrammarArr2, 0, schemaGrammarArr3, 0, length);
                        schemaGrammarArr2 = schemaGrammarArr3;
                        strArr = strArr2;
                    }
                    strArr[length] = schemaGrammar2.getTargetNamespace();
                    schemaGrammarArr2[length] = schemaGrammar2;
                    length++;
                }
            }
        }
        this.fNamespaces = strArr;
        this.fGrammarList = schemaGrammarArr2;
        this.fGrammarMap = new SymbolHash(length * 2);
        boolean z2 = false;
        for (int i6 = 0; i6 < length; i6++) {
            this.fGrammarMap.put(null2EmptyString(this.fNamespaces[i6]), this.fGrammarList[i6]);
            if (this.fGrammarList[i6].hasIDConstraints()) {
                z2 = true;
            }
        }
        this.fHasIDC = z2;
        this.fGrammarCount = length;
        this.fGlobalComponents = new XSNamedMap[17];
        this.fNSComponents = (XSNamedMap[][]) Array.newInstance((Class<?>) XSNamedMap.class, length, 17);
        this.fNamespacesList = new StringListImpl(this.fNamespaces, length);
        this.fSubGroupMap = buildSubGroups();
    }

    private SymbolHash buildSubGroups() {
        SubstitutionGroupHandler substitutionGroupHandler = new SubstitutionGroupHandler(null);
        for (int i = 0; i < this.fGrammarCount; i++) {
            substitutionGroupHandler.addSubstitutionGroup(this.fGrammarList[i].getSubstitutionGroups());
        }
        XSObjectListImpl globalElements = getGlobalElements();
        int length = globalElements.getLength();
        SymbolHash symbolHash = new SymbolHash(length * 2);
        for (int i2 = 0; i2 < length; i2++) {
            XSElementDecl xSElementDecl = (XSElementDecl) globalElements.item(i2);
            XSElementDecl[] substitutionGroup = substitutionGroupHandler.getSubstitutionGroup(xSElementDecl);
            symbolHash.put(xSElementDecl, substitutionGroup.length > 0 ? new XSObjectListImpl(substitutionGroup, substitutionGroup.length) : XSObjectListImpl.EMPTY_LIST);
        }
        return symbolHash;
    }

    private SymbolHash buildSubGroups_Org() {
        SubstitutionGroupHandler substitutionGroupHandler = new SubstitutionGroupHandler(null);
        for (int i = 0; i < this.fGrammarCount; i++) {
            substitutionGroupHandler.addSubstitutionGroup(this.fGrammarList[i].getSubstitutionGroups());
        }
        XSNamedMap components = getComponents((short) 2);
        int length = components.getLength();
        SymbolHash symbolHash = new SymbolHash(length * 2);
        for (int i2 = 0; i2 < length; i2++) {
            XSElementDecl xSElementDecl = (XSElementDecl) components.item(i2);
            XSElementDecl[] substitutionGroup = substitutionGroupHandler.getSubstitutionGroup(xSElementDecl);
            symbolHash.put(xSElementDecl, substitutionGroup.length > 0 ? new XSObjectListImpl(substitutionGroup, substitutionGroup.length) : XSObjectListImpl.EMPTY_LIST);
        }
        return symbolHash;
    }

    private XSObjectListImpl getGlobalElements() {
        SymbolHash[] symbolHashArr = new SymbolHash[this.fGrammarCount];
        int length = 0;
        for (int i = 0; i < this.fGrammarCount; i++) {
            SymbolHash symbolHash = this.fGrammarList[i].fAllGlobalElemDecls;
            symbolHashArr[i] = symbolHash;
            length += symbolHash.getLength();
        }
        if (length == 0) {
            return XSObjectListImpl.EMPTY_LIST;
        }
        XSObject[] xSObjectArr = new XSObject[length];
        int length2 = 0;
        for (int i2 = 0; i2 < this.fGrammarCount; i2++) {
            symbolHashArr[i2].getValues(xSObjectArr, length2);
            length2 += symbolHashArr[i2].getLength();
        }
        return new XSObjectListImpl(xSObjectArr, length);
    }

    private ListIterator listIterator0(int i) {
        return new XSNamespaceItemListIterator(i);
    }

    private static final String null2EmptyString(String str) {
        return str == null ? XMLSymbols.EMPTY_STRING : str;
    }

    private void toArray0(Object[] objArr) {
        int i = this.fGrammarCount;
        if (i > 0) {
            System.arraycopy(this.fGrammarList, 0, objArr, 0, i);
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public Object get(int i) {
        if (i < 0 || i >= this.fGrammarCount) {
            throw new IndexOutOfBoundsException("Index: " + i);
        }
        return this.fGrammarList[i];
    }

    @Override // org.apache.xerces.xs.XSModel
    public synchronized XSObjectList getAnnotations() {
        XSObjectList xSObjectList = this.fAnnotations;
        if (xSObjectList != null) {
            return xSObjectList;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.fGrammarCount; i2++) {
            i += this.fGrammarList[i2].fNumAnnotations;
        }
        if (i == 0) {
            XSObjectListImpl xSObjectListImpl = XSObjectListImpl.EMPTY_LIST;
            this.fAnnotations = xSObjectListImpl;
            return xSObjectListImpl;
        }
        XSAnnotationImpl[] xSAnnotationImplArr = new XSAnnotationImpl[i];
        int i3 = 0;
        for (int i4 = 0; i4 < this.fGrammarCount; i4++) {
            SchemaGrammar schemaGrammar = this.fGrammarList[i4];
            if (schemaGrammar.fNumAnnotations > 0) {
                System.arraycopy(schemaGrammar.fAnnotations, 0, xSAnnotationImplArr, i3, schemaGrammar.fNumAnnotations);
                i3 += schemaGrammar.fNumAnnotations;
            }
        }
        XSObjectListImpl xSObjectListImpl2 = new XSObjectListImpl(xSAnnotationImplArr, i);
        this.fAnnotations = xSObjectListImpl2;
        return xSObjectListImpl2;
    }

    @Override // org.apache.xerces.xs.XSModel
    public XSAttributeDeclaration getAttributeDeclaration(String str, String str2) {
        SchemaGrammar schemaGrammar = (SchemaGrammar) this.fGrammarMap.get(null2EmptyString(str2));
        if (schemaGrammar == null) {
            return null;
        }
        return (XSAttributeDeclaration) schemaGrammar.fGlobalAttrDecls.get(str);
    }

    public XSAttributeDeclaration getAttributeDeclaration(String str, String str2, String str3) {
        SchemaGrammar schemaGrammar = (SchemaGrammar) this.fGrammarMap.get(null2EmptyString(str2));
        if (schemaGrammar == null) {
            return null;
        }
        return schemaGrammar.getGlobalAttributeDecl(str, str3);
    }

    @Override // org.apache.xerces.xs.XSModel
    public XSAttributeGroupDefinition getAttributeGroup(String str, String str2) {
        SchemaGrammar schemaGrammar = (SchemaGrammar) this.fGrammarMap.get(null2EmptyString(str2));
        if (schemaGrammar == null) {
            return null;
        }
        return (XSAttributeGroupDefinition) schemaGrammar.fGlobalAttrGrpDecls.get(str);
    }

    public XSAttributeGroupDefinition getAttributeGroup(String str, String str2, String str3) {
        SchemaGrammar schemaGrammar = (SchemaGrammar) this.fGrammarMap.get(null2EmptyString(str2));
        if (schemaGrammar == null) {
            return null;
        }
        return schemaGrammar.getGlobalAttributeGroupDecl(str, str3);
    }

    /* JADX WARN: Code duplicated, block: B:35:0x0060 A[Catch: all -> 0x00a7, TryCatch #0 {, blocks: (B:6:0x0007, B:9:0x000f, B:12:0x001a, B:38:0x007a, B:31:0x003c, B:32:0x0045, B:33:0x004e, B:34:0x0057, B:35:0x0060, B:36:0x0069, B:37:0x0072, B:42:0x0082, B:43:0x0090, B:44:0x009d, B:47:0x00a3), top: B:53:0x0007 }] */
    @Override // org.apache.xerces.xs.XSModel
    public synchronized XSNamedMap getComponents(short s) {
        if (s > 0 && s <= 16) {
            if (GLOBAL_COMP[s]) {
                SymbolHash[] symbolHashArr = new SymbolHash[this.fGrammarCount];
                if (this.fGlobalComponents[s] == null) {
                    for (int i = 0; i < this.fGrammarCount; i++) {
                        if (s == 1) {
                            symbolHashArr[i] = this.fGrammarList[i].fGlobalAttrDecls;
                        } else if (s == 2) {
                            symbolHashArr[i] = this.fGrammarList[i].fGlobalElemDecls;
                        } else if (s == 3) {
                            symbolHashArr[i] = this.fGrammarList[i].fGlobalTypeDecls;
                        } else if (s == 5) {
                            symbolHashArr[i] = this.fGrammarList[i].fGlobalAttrGrpDecls;
                        } else if (s == 6) {
                            symbolHashArr[i] = this.fGrammarList[i].fGlobalGroupDecls;
                        } else if (s == 10) {
                            symbolHashArr[i] = this.fGrammarList[i].fGlobalIDConstraintDecls;
                        } else if (s == 11) {
                            symbolHashArr[i] = this.fGrammarList[i].fGlobalNotationDecls;
                        } else if (s == 15 || s == 16) {
                            symbolHashArr[i] = this.fGrammarList[i].fGlobalTypeDecls;
                        }
                    }
                    if (s == 15 || s == 16) {
                        this.fGlobalComponents[s] = new XSNamedMap4Types(this.fNamespaces, symbolHashArr, this.fGrammarCount, s);
                    } else {
                        this.fGlobalComponents[s] = new XSNamedMapImpl(this.fNamespaces, symbolHashArr, this.fGrammarCount);
                    }
                }
                return this.fGlobalComponents[s];
            }
        }
        return XSNamedMapImpl.EMPTY_MAP;
    }

    /* JADX WARN: Code duplicated, block: B:51:0x007d A[Catch: all -> 0x00b9, TryCatch #0 {, blocks: (B:6:0x0007, B:11:0x0012, B:13:0x0016, B:16:0x0021, B:23:0x0032, B:25:0x0036, B:28:0x003a, B:57:0x0096, B:58:0x00a2, B:47:0x0061, B:48:0x0068, B:49:0x006f, B:50:0x0076, B:51:0x007d, B:52:0x0084, B:53:0x008b, B:59:0x00ad, B:17:0x0024, B:19:0x0028, B:22:0x002f, B:62:0x00b5), top: B:68:0x0007 }] */
    @Override // org.apache.xerces.xs.XSModel
    public synchronized XSNamedMap getComponentsByNamespace(short s, String str) {
        SymbolHash symbolHash;
        if (s > 0 && s <= 16) {
            if (GLOBAL_COMP[s]) {
                int i = 0;
                if (str != null) {
                    while (i < this.fGrammarCount && !str.equals(this.fNamespaces[i])) {
                        i++;
                    }
                } else {
                    while (i < this.fGrammarCount && this.fNamespaces[i] != null) {
                        i++;
                    }
                }
                if (i == this.fGrammarCount) {
                    return XSNamedMapImpl.EMPTY_MAP;
                }
                if (this.fNSComponents[i][s] == null) {
                    if (s == 1) {
                        symbolHash = this.fGrammarList[i].fGlobalAttrDecls;
                    } else if (s == 2) {
                        symbolHash = this.fGrammarList[i].fGlobalElemDecls;
                    } else if (s == 3) {
                        symbolHash = this.fGrammarList[i].fGlobalTypeDecls;
                    } else if (s == 5) {
                        symbolHash = this.fGrammarList[i].fGlobalAttrGrpDecls;
                    } else if (s == 6) {
                        symbolHash = this.fGrammarList[i].fGlobalGroupDecls;
                    } else if (s == 10) {
                        symbolHash = this.fGrammarList[i].fGlobalIDConstraintDecls;
                    } else if (s == 11) {
                        symbolHash = this.fGrammarList[i].fGlobalNotationDecls;
                    } else if (s == 15 || s == 16) {
                        symbolHash = this.fGrammarList[i].fGlobalTypeDecls;
                    } else {
                        symbolHash = null;
                    }
                    if (s == 15 || s == 16) {
                        this.fNSComponents[i][s] = new XSNamedMap4Types(str, symbolHash, s);
                    } else {
                        this.fNSComponents[i][s] = new XSNamedMapImpl(str, symbolHash);
                    }
                }
                return this.fNSComponents[i][s];
            }
        }
        return XSNamedMapImpl.EMPTY_MAP;
    }

    @Override // org.apache.xerces.xs.XSModel
    public XSElementDeclaration getElementDeclaration(String str, String str2) {
        SchemaGrammar schemaGrammar = (SchemaGrammar) this.fGrammarMap.get(null2EmptyString(str2));
        if (schemaGrammar == null) {
            return null;
        }
        return (XSElementDeclaration) schemaGrammar.fGlobalElemDecls.get(str);
    }

    public XSElementDeclaration getElementDeclaration(String str, String str2, String str3) {
        SchemaGrammar schemaGrammar = (SchemaGrammar) this.fGrammarMap.get(null2EmptyString(str2));
        if (schemaGrammar == null) {
            return null;
        }
        return schemaGrammar.getGlobalElementDecl(str, str3);
    }

    @Override // org.apache.xerces.xs.XSModel
    public XSIDCDefinition getIDCDefinition(String str, String str2) {
        SchemaGrammar schemaGrammar = (SchemaGrammar) this.fGrammarMap.get(null2EmptyString(str2));
        if (schemaGrammar == null) {
            return null;
        }
        return (XSIDCDefinition) schemaGrammar.fGlobalIDConstraintDecls.get(str);
    }

    public XSIDCDefinition getIDCDefinition(String str, String str2, String str3) {
        SchemaGrammar schemaGrammar = (SchemaGrammar) this.fGrammarMap.get(null2EmptyString(str2));
        if (schemaGrammar == null) {
            return null;
        }
        return schemaGrammar.getIDConstraintDecl(str, str3);
    }

    @Override // org.apache.xerces.xs.XSNamespaceItemList
    public int getLength() {
        return this.fGrammarCount;
    }

    @Override // org.apache.xerces.xs.XSModel
    public XSModelGroupDefinition getModelGroupDefinition(String str, String str2) {
        SchemaGrammar schemaGrammar = (SchemaGrammar) this.fGrammarMap.get(null2EmptyString(str2));
        if (schemaGrammar == null) {
            return null;
        }
        return (XSModelGroupDefinition) schemaGrammar.fGlobalGroupDecls.get(str);
    }

    public XSModelGroupDefinition getModelGroupDefinition(String str, String str2, String str3) {
        SchemaGrammar schemaGrammar = (SchemaGrammar) this.fGrammarMap.get(null2EmptyString(str2));
        if (schemaGrammar == null) {
            return null;
        }
        return schemaGrammar.getGlobalGroupDecl(str, str3);
    }

    @Override // org.apache.xerces.xs.XSModel
    public XSNamespaceItemList getNamespaceItems() {
        return this;
    }

    @Override // org.apache.xerces.xs.XSModel
    public StringList getNamespaces() {
        return this.fNamespacesList;
    }

    @Override // org.apache.xerces.xs.XSModel
    public XSNotationDeclaration getNotationDeclaration(String str, String str2) {
        SchemaGrammar schemaGrammar = (SchemaGrammar) this.fGrammarMap.get(null2EmptyString(str2));
        if (schemaGrammar == null) {
            return null;
        }
        return (XSNotationDeclaration) schemaGrammar.fGlobalNotationDecls.get(str);
    }

    public XSNotationDeclaration getNotationDeclaration(String str, String str2, String str3) {
        SchemaGrammar schemaGrammar = (SchemaGrammar) this.fGrammarMap.get(null2EmptyString(str2));
        if (schemaGrammar == null) {
            return null;
        }
        return schemaGrammar.getGlobalNotationDecl(str, str3);
    }

    @Override // org.apache.xerces.xs.XSModel
    public XSObjectList getSubstitutionGroup(XSElementDeclaration xSElementDeclaration) {
        return (XSObjectList) this.fSubGroupMap.get(xSElementDeclaration);
    }

    @Override // org.apache.xerces.xs.XSModel
    public XSTypeDefinition getTypeDefinition(String str, String str2) {
        SchemaGrammar schemaGrammar = (SchemaGrammar) this.fGrammarMap.get(null2EmptyString(str2));
        if (schemaGrammar == null) {
            return null;
        }
        return (XSTypeDefinition) schemaGrammar.fGlobalTypeDecls.get(str);
    }

    public XSTypeDefinition getTypeDefinition(String str, String str2, String str3) {
        SchemaGrammar schemaGrammar = (SchemaGrammar) this.fGrammarMap.get(null2EmptyString(str2));
        if (schemaGrammar == null) {
            return null;
        }
        return schemaGrammar.getGlobalTypeDecl(str, str3);
    }

    public boolean hasIDConstraints() {
        return this.fHasIDC;
    }

    @Override // org.apache.xerces.xs.XSNamespaceItemList
    public XSNamespaceItem item(int i) {
        if (i < 0 || i >= this.fGrammarCount) {
            return null;
        }
        return this.fGrammarList[i];
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator iterator() {
        return listIterator0(0);
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator listIterator() {
        return listIterator0(0);
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator listIterator(int i) {
        if (i < 0 || i >= this.fGrammarCount) {
            throw new IndexOutOfBoundsException("Index: " + i);
        }
        return listIterator0(i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return getLength();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray() {
        Object[] objArr = new Object[this.fGrammarCount];
        toArray0(objArr);
        return objArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray(Object[] objArr) {
        if (objArr.length < this.fGrammarCount) {
            objArr = (Object[]) Array.newInstance(objArr.getClass().getComponentType(), this.fGrammarCount);
        }
        toArray0(objArr);
        int length = objArr.length;
        int i = this.fGrammarCount;
        if (length > i) {
            objArr[i] = null;
        }
        return objArr;
    }
}
