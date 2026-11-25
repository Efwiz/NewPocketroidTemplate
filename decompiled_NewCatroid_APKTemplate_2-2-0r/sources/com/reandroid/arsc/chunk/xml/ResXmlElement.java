package com.reandroid.arsc.chunk.xml;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.array.ResXmlAttributeArray;
import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.container.SingleBlockContainer;
import com.reandroid.arsc.header.HeaderBlock;
import com.reandroid.arsc.header.InfoHeader;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.model.ResourceLibrary;
import com.reandroid.arsc.pool.ResXmlStringPool;
import com.reandroid.arsc.refactor.ResourceMergeOption;
import com.reandroid.common.Namespace;
import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.StringsUtil;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.RecursiveIterator;
import com.reandroid.utils.collection.SingleIterator;
import com.reandroid.xml.XMLAttribute;
import com.reandroid.xml.XMLComment;
import com.reandroid.xml.XMLElement;
import com.reandroid.xml.XMLNode;
import com.reandroid.xml.XMLUtil;
import java.io.IOException;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.xml.XMLConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlElement extends ResXmlNode implements ResXmlNodeTree, JSONConvert<JSONObject>, Comparator<ResXmlNode> {
    private static final String FEATURE_INDENT_OUTPUT = "http://xmlpull.org/v1/doc/features.html#indent-output";
    private static final int MAX_INDENT_DEPTH = 25;
    private final BlockList<ResXmlNode> mBody;
    private final SingleBlockContainer<ResXmlEndElement> mEndElementContainer;
    private final BlockList<ResXmlEndNamespace> mEndNamespaceList;
    private final SingleBlockContainer<ResXmlStartElement> mStartElementContainer;
    private final BlockList<ResXmlStartNamespace> mStartNamespaceList;
    public static final String NAME_element = ObjectsUtil.of(CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD);
    public static final String NAME_name = ObjectsUtil.of("name");
    public static final String NAME_comment = ObjectsUtil.of("comment");
    public static final String NAME_text = ObjectsUtil.of("text");
    public static final String NAME_namespaces = ObjectsUtil.of("namespaces");
    public static final String NAME_namespace_uri = ObjectsUtil.of(ResXmlAttribute.NAME_namespace_uri);
    public static final String NAME_namespace_prefix = ObjectsUtil.of("namespace_prefix");
    public static final String NAME_line = ObjectsUtil.of(Constant.LINE);
    public static final String NAME_line_end = ObjectsUtil.of("line_end");
    public static final String NAME_attributes = ObjectsUtil.of("attributes");
    public static final String NAME_childes = ObjectsUtil.of("childes");

    public ResXmlElement() {
        super(5);
        BlockList<ResXmlStartNamespace> blockList = new BlockList<>();
        this.mStartNamespaceList = blockList;
        SingleBlockContainer<ResXmlStartElement> singleBlockContainer = new SingleBlockContainer<>();
        this.mStartElementContainer = singleBlockContainer;
        BlockList<ResXmlNode> blockList2 = new BlockList<>();
        this.mBody = blockList2;
        SingleBlockContainer<ResXmlEndElement> singleBlockContainer2 = new SingleBlockContainer<>();
        this.mEndElementContainer = singleBlockContainer2;
        BlockList<ResXmlEndNamespace> blockList3 = new BlockList<>();
        this.mEndNamespaceList = blockList3;
        addChild(0, blockList);
        addChild(1, singleBlockContainer);
        addChild(2, blockList2);
        addChild(3, singleBlockContainer2);
        addChild(4, blockList3);
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNodeTree
    public BlockList<ResXmlNode> getNodeListBlockInternal() {
        return this.mBody;
    }

    public boolean isUndefined() {
        return size() == 0 && getAttributeCount() == 0 && getNamespaceCount() == 0 && StringsUtil.isEmpty(getName());
    }

    public ResXmlElement getParentElement() {
        return (ResXmlElement) getParentInstance(ResXmlElement.class);
    }

    public ResXmlDocument getParentDocument() {
        return (ResXmlDocument) getParentInstance(ResXmlDocument.class);
    }

    public ResXmlElement getRootElement() {
        ResXmlElement parentElement = getParentElement();
        return parentElement != null ? parentElement.getRootElement() : this;
    }

    public ResXmlNamespace getNamespace() {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            return startElement.getResXmlStartNamespace();
        }
        return null;
    }

    public void addIndent(int i) {
        if (hasText()) {
            return;
        }
        int depth = getDepth();
        if (depth > 25) {
            depth = 25;
        }
        int i2 = (depth + 1) * i;
        ResXmlTextNode resXmlTextNode = null;
        for (ResXmlElement resXmlElement : listElements()) {
            ResXmlTextNode resXmlTextNodeCreateResXmlTextNode = createResXmlTextNode(resXmlElement.getIndex());
            resXmlTextNodeCreateResXmlTextNode.makeIndent(i2);
            resXmlElement.addIndent(i);
            resXmlTextNode = resXmlTextNodeCreateResXmlTextNode;
        }
        if (resXmlTextNode != null) {
            int i3 = depth * i;
            ResXmlTextNode resXmlTextNode2 = new ResXmlTextNode();
            addNode(resXmlTextNode2);
            resXmlTextNode2.makeIndent(i3);
        }
    }

    public int clearIndents() {
        return removeIf(new Predicate() { // from class: com.reandroid.arsc.chunk.xml.ResXmlElement$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ResXmlElement.lambda$clearIndents$0((ResXmlNode) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$clearIndents$0(ResXmlNode resXmlNode) {
        if (resXmlNode instanceof ResXmlTextNode) {
            return ((ResXmlTextNode) resXmlNode).isIndent();
        }
        return false;
    }

    public Iterator<ResXmlNode> recursiveXmlNodes() throws ConcurrentModificationException {
        return CombiningIterator.of(SingleIterator.of(this), ComputeIterator.of(iterator(), new Function() { // from class: com.reandroid.arsc.chunk.xml.ResXmlElement$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ResXmlElement.lambda$recursiveXmlNodes$1((ResXmlNode) obj);
            }
        }));
    }

    static /* synthetic */ Iterator lambda$recursiveXmlNodes$1(ResXmlNode resXmlNode) {
        if (resXmlNode instanceof ResXmlElement) {
            return ((ResXmlElement) resXmlNode).recursiveXmlNodes();
        }
        return SingleIterator.of(resXmlNode);
    }

    public Iterator<ResXmlAttribute> recursiveAttributes() throws ConcurrentModificationException {
        return RecursiveIterator.compute(this, new ResXmlElement$$ExternalSyntheticLambda0(), new Function() { // from class: com.reandroid.arsc.chunk.xml.ResXmlElement$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ResXmlElement) obj).getAttributes();
            }
        });
    }

    public Iterator<ResXmlElement> recursiveElements() {
        return RecursiveIterator.of(this, new ResXmlElement$$ExternalSyntheticLambda0());
    }

    public ResXmlAttribute getIdAttribute() {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            return startElement.getIdAttribute();
        }
        return null;
    }

    public ResXmlAttribute getClassAttribute() {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            return startElement.getClassAttribute();
        }
        return null;
    }

    public ResXmlAttribute getStyleAttribute() {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            return startElement.getStyleAttribute();
        }
        return null;
    }

    public ResXmlNamespace getNamespaceAt(int i) {
        return (ResXmlNamespace) this.mStartNamespaceList.get(i);
    }

    public int getNamespaceCount() {
        return this.mStartNamespaceList.size();
    }

    public ResXmlNamespace getNamespace(String str, String str2) {
        return getXmlStartNamespace(str, str2);
    }

    public ResXmlNamespace getOrCreateNamespace(String str, String str2) {
        return getOrCreateXmlStartNamespace(str, str2);
    }

    public ResXmlNamespace newNamespace(String str, String str2) {
        return createXmlStartNamespace(str, str2);
    }

    public ResXmlNamespace getNamespaceByUri(String str) {
        return getStartNamespaceByUri(str);
    }

    public ResXmlNamespace getNamespaceByPrefix(String str) {
        return getStartNamespaceByPrefix(str, null);
    }

    public ResXmlNamespace getOrCreateNamespaceByPrefix(String str) {
        String str2;
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        ResXmlNamespace namespaceByPrefix = getNamespaceByPrefix(str);
        if (namespaceByPrefix != null) {
            return namespaceByPrefix;
        }
        if (ResourceLibrary.PREFIX_ANDROID.equals(str)) {
            str2 = ResourceLibrary.URI_ANDROID;
        } else {
            str2 = ResourceLibrary.URI_RES_AUTO;
        }
        return getOrCreateNamespace(str2, str);
    }

    public int autoSetAttributeNamespaces() {
        return autoSetAttributeNamespaces(true);
    }

    public int autoSetAttributeNamespaces(boolean z) {
        Iterator<ResXmlAttribute> attributes = getAttributes();
        int iAutoSetAttributeNamespaces = 0;
        while (attributes.hasNext()) {
            if (attributes.next().autoSetNamespace(z)) {
                iAutoSetAttributeNamespaces++;
            }
        }
        Iterator<ResXmlElement> elements = getElements();
        while (elements.hasNext()) {
            iAutoSetAttributeNamespaces += elements.next().autoSetAttributeNamespaces(z);
        }
        return (z && fixEmptyNamespaces()) ? iAutoSetAttributeNamespaces + 1 : iAutoSetAttributeNamespaces;
    }

    public boolean fixEmptyNamespaces() {
        Iterator<ResXmlStartNamespace> it = getStartNamespaceList().iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (it.next().fixEmpty()) {
                z = true;
            }
        }
        return z;
    }

    public int autoSetAttributeNames() {
        return autoSetAttributeNames(true);
    }

    public int autoSetAttributeNames(boolean z) {
        Iterator<ResXmlAttribute> attributes = getAttributes();
        int iAutoSetAttributeNames = 0;
        while (attributes.hasNext()) {
            if (attributes.next().autoSetName(z)) {
                iAutoSetAttributeNames++;
            }
        }
        Iterator<ResXmlElement> elements = getElements();
        while (elements.hasNext()) {
            iAutoSetAttributeNames += elements.next().autoSetAttributeNames(z);
        }
        return iAutoSetAttributeNames;
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    int autoSetLineNumber(int i) {
        int iAutoSetLineNumber = i + 1;
        setLineNumber(iAutoSetLineNumber);
        int attributeCount = getAttributeCount();
        if (attributeCount != 0) {
            iAutoSetLineNumber += attributeCount - 1;
        }
        boolean z = false;
        for (ResXmlNode resXmlNode : this) {
            iAutoSetLineNumber = resXmlNode.autoSetLineNumber(iAutoSetLineNumber);
            if (!z && (resXmlNode instanceof ResXmlElement)) {
                z = true;
            }
        }
        return z ? iAutoSetLineNumber + 1 : iAutoSetLineNumber;
    }

    public void clearNullNodes() {
        clearNullNodes(true);
    }

    private void clearNullNodes(boolean z) {
        for (ResXmlNode resXmlNode : listXmlNodes()) {
            if (resXmlNode.isNull()) {
                remove(resXmlNode);
            }
            if (z && (resXmlNode instanceof ResXmlElement)) {
                ((ResXmlElement) resXmlNode).clearNullNodes(true);
            }
        }
    }

    int removeUnusedNamespaces() {
        Iterator<T> it = new ArrayCollection(getStartNamespaceList()).iterator();
        int iRemoveUnusedNamespaces = 0;
        while (it.hasNext()) {
            if (((ResXmlStartNamespace) it.next()).removeIfNoReference()) {
                iRemoveUnusedNamespaces++;
            }
        }
        Iterator<ResXmlElement> elements = getElements();
        while (elements.hasNext()) {
            iRemoveUnusedNamespaces += elements.next().removeUnusedNamespaces();
        }
        return iRemoveUnusedNamespaces;
    }

    public int removeUndefinedAttributes() {
        ResXmlStartElement startElement = getStartElement();
        int iRemoveUndefinedAttributes = startElement != null ? startElement.removeUndefinedAttributes() : 0;
        Iterator<ResXmlElement> elements = getElements();
        while (elements.hasNext()) {
            iRemoveUndefinedAttributes += elements.next().removeUndefinedAttributes();
        }
        return iRemoveUndefinedAttributes;
    }

    public void changeIndex(ResXmlElement resXmlElement, int i) {
        getNodeListBlockInternal().moveTo(resXmlElement, i);
    }

    public int indexOf(String str) {
        ResXmlElement element = getElement(str);
        if (element != null) {
            return element.getIndex();
        }
        return -1;
    }

    public int lastIndexOf(String str) {
        return lastIndexOf(str, -1);
    }

    public int lastIndexOf(String str, int i) {
        ResXmlElement resXmlElement = (ResXmlElement) CollectionUtil.getLast(getElements(str));
        return resXmlElement != null ? indexOf(resXmlElement, i) : i;
    }

    public int indexOf(ResXmlElement resXmlElement) {
        return indexOf(resXmlElement, -1);
    }

    public int indexOf(ResXmlNode resXmlNode, int i) {
        Iterator<ResXmlNode> it = iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next() == resXmlNode) {
                return i2;
            }
            i2++;
        }
        return i;
    }

    public void setAttributesUnitSize(int i, boolean z) {
        getStartElement().setAttributesUnitSize(i);
        if (z) {
            Iterator<ResXmlElement> it = listElements().iterator();
            while (it.hasNext()) {
                it.next().setAttributesUnitSize(i, z);
            }
        }
    }

    public String getStartComment() {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            return startElement.getComment();
        }
        return null;
    }

    String getEndComment() {
        ResXmlEndElement endElement = getEndElement();
        if (endElement != null) {
            return endElement.getComment();
        }
        return null;
    }

    public int getLineNumber() {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            return startElement.getLineNumber();
        }
        return 0;
    }

    public void setLineNumber(int i) {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            startElement.setLineNumber(i);
            startElement.getResXmlEndElement().setLineNumber(i);
        }
        int namespaceCount = getNamespaceCount();
        for (int i2 = 0; i2 < namespaceCount; i2++) {
            getNamespaceAt(i2).setLineNumber(i);
        }
    }

    public int getStartLineNumber() {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            return startElement.getLineNumber();
        }
        return 0;
    }

    public int getEndLineNumber() {
        ResXmlEndElement endElement = getEndElement();
        if (endElement != null) {
            return endElement.getLineNumber();
        }
        return 0;
    }

    public void setStartLineNumber(int i) {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            startElement.setLineNumber(i);
        }
    }

    public void setEndLineNumber(int i) {
        ResXmlEndElement endElement = getEndElement();
        if (endElement != null) {
            endElement.setLineNumber(i);
        }
    }

    public void setComment(String str) {
        getStartElement().setComment(str);
    }

    public void calculateAttributesOrder() {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            startElement.calculatePositions();
        }
    }

    public ResXmlAttribute newAttribute() {
        return getStartElement().newAttribute();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    void onRemoved() {
        Iterator<ResXmlStartNamespace> it = getStartNamespaceList().iterator();
        while (it.hasNext()) {
            it.next().onRemoved();
        }
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            startElement.onRemoved();
        }
        Iterator<ResXmlNode> it2 = listXmlNodes().iterator();
        while (it2.hasNext()) {
            it2.next().onRemoved();
        }
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    void linkStringReferences() {
        Iterator<ResXmlStartNamespace> it = getStartNamespaceList().iterator();
        while (it.hasNext()) {
            it.next().linkStringReferences();
        }
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            startElement.linkStringReferences();
        }
        Iterator<ResXmlElement> elements = getElements();
        while (elements.hasNext()) {
            elements.next().linkStringReferences();
        }
    }

    public ResXmlElement createChildElement() {
        return createChildElement(-1, null);
    }

    public ResXmlElement createChildElement(int i) {
        return createChildElement(i, null);
    }

    public ResXmlElement createChildElement(String str) {
        return createChildElement(-1, str);
    }

    public ResXmlElement createChildElement(int i, String str) {
        int lineNumber = getStartElement().getLineNumber() + 1;
        ResXmlElement resXmlElement = new ResXmlElement();
        resXmlElement.newStartElement(lineNumber);
        if (i >= 0) {
            addNode(i, resXmlElement);
        } else {
            addNode(resXmlElement);
        }
        if (str != null) {
            resXmlElement.setName(str);
        }
        return resXmlElement;
    }

    public ResXmlTextNode createResXmlTextNode() {
        return createResXmlTextNode(-1, null);
    }

    public ResXmlTextNode createResXmlTextNode(int i) {
        return createResXmlTextNode(i, null);
    }

    public ResXmlTextNode createResXmlTextNode(String str) {
        return createResXmlTextNode(-1, str);
    }

    public ResXmlTextNode createResXmlTextNode(int i, String str) {
        ResXmlTextNode resXmlTextNode = new ResXmlTextNode();
        if (i >= 0) {
            addNode(i, resXmlTextNode);
        } else {
            addNode(resXmlTextNode);
        }
        if (str != null) {
            resXmlTextNode.setText(str);
        }
        return resXmlTextNode;
    }

    public void addResXmlText(String str) {
        if (str == null) {
            return;
        }
        createResXmlTextNode(str);
    }

    public ResXmlAttribute getOrCreateAndroidAttribute(String str, int i) {
        return getOrCreateAttribute(ResourceLibrary.URI_ANDROID, ResourceLibrary.PREFIX_ANDROID, str, i);
    }

    public ResXmlAttribute getOrCreateAttribute(String str, String str2, String str3, int i) {
        ResXmlAttribute resXmlAttributeSearchAttribute = searchAttribute(str3, i);
        if (resXmlAttributeSearchAttribute != null) {
            return resXmlAttributeSearchAttribute;
        }
        ResXmlAttribute resXmlAttributeCreateAttribute = createAttribute(str3, i);
        resXmlAttributeCreateAttribute.setNamespace(str, str2);
        return resXmlAttributeCreateAttribute;
    }

    public ResXmlAttribute getOrCreateAttribute(String str, int i) {
        ResXmlAttribute resXmlAttributeSearchAttribute = searchAttribute(str, i);
        return resXmlAttributeSearchAttribute == null ? createAttribute(str, i) : resXmlAttributeSearchAttribute;
    }

    public ResXmlAttribute createAndroidAttribute(String str, int i) {
        ResXmlAttribute resXmlAttributeCreateAttribute = createAttribute(str, i);
        resXmlAttributeCreateAttribute.setNamespaceReference(getOrCreateXmlStartNamespace(ResourceLibrary.URI_ANDROID, ResourceLibrary.PREFIX_ANDROID).getUriReference());
        return resXmlAttributeCreateAttribute;
    }

    public ResXmlAttribute createAttribute(String str, int i) {
        ResXmlAttribute resXmlAttribute = new ResXmlAttribute();
        addAttribute(resXmlAttribute);
        resXmlAttribute.setName(str, i);
        return resXmlAttribute;
    }

    public void addAttribute(ResXmlAttribute resXmlAttribute) {
        getStartElement().getResXmlAttributeArray().add(resXmlAttribute);
    }

    private ResXmlAttribute searchAttribute(String str, int i) {
        if (i == 0) {
            return searchAttributeByName(str);
        }
        return searchAttributeByResourceId(i);
    }

    public ResXmlAttribute searchAttributeByName(String str) {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            return startElement.searchAttributeByName(str);
        }
        return null;
    }

    public ResXmlAttribute searchAttributeByResourceId(int i) {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            return startElement.searchAttributeByResourceId(i);
        }
        return null;
    }

    public String getName() {
        return getName(false);
    }

    public String getName(boolean z) {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            return startElement.getTagName(z);
        }
        return null;
    }

    public void setName(String str, String str2, String str3) {
        setName(str3);
        setNamespace(str, str2);
    }

    public void setName(String str) {
        ResXmlNamespace orCreateNamespaceByPrefix;
        if (getStringPool() == null) {
            return;
        }
        ensureStartEndElement();
        ResXmlStartElement startElement = getStartElement();
        String strSubstring = null;
        if (str == null) {
            startElement.setName(null);
            return;
        }
        int iLastIndexOf = str.lastIndexOf(58);
        if (iLastIndexOf >= 0) {
            strSubstring = str.substring(0, iLastIndexOf);
            str = str.substring(iLastIndexOf + 1);
        }
        startElement.setName(str);
        if (strSubstring == null || (orCreateNamespaceByPrefix = getOrCreateNamespaceByPrefix(strSubstring)) == null) {
            return;
        }
        startElement.setNamespaceReference(orCreateNamespaceByPrefix.getUriReference());
    }

    public boolean equalsName(String str) {
        if (str == null) {
            return getName() == null;
        }
        return XMLUtil.splitName(str).equals(getName(false));
    }

    public String getUri() {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            return startElement.getUri();
        }
        return null;
    }

    public String getPrefix() {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            return startElement.getPrefix();
        }
        return null;
    }

    public void setNamespace(Namespace namespace) {
        if (namespace != null) {
            setNamespace(namespace.getUri(), namespace.getPrefix());
        } else {
            setNamespace(null, null);
        }
    }

    public void setNamespace(String str, String str2) {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            startElement.setTagNamespace(str, str2);
        }
    }

    public int removeAttributesWithId(int i) {
        return removeAttributes(getAttributesWithId(i));
    }

    public int removeAttributesWithName(String str) {
        return removeAttributes(getAttributesWithName(str));
    }

    public int removeAttributes(Predicate<? super ResXmlAttribute> predicate) {
        return removeAttributes(getAttributes(predicate));
    }

    public int removeAttributes(Iterator<? extends ResXmlAttribute> it) {
        Iterator itCopyOf = CollectionUtil.copyOf(it);
        int i = 0;
        while (itCopyOf.hasNext()) {
            if (removeAttribute((ResXmlAttribute) itCopyOf.next())) {
                i++;
            }
        }
        return i;
    }

    public Iterator<ResXmlAttribute> getAttributes() {
        ResXmlAttributeArray attributeArray = getAttributeArray();
        if (attributeArray != null) {
            if (attributeArray.size() == 0) {
                return EmptyIterator.of();
            }
            return attributeArray.iterator();
        }
        return EmptyIterator.of();
    }

    public Iterator<ResXmlAttribute> getAttributes(Predicate<? super ResXmlAttribute> predicate) {
        ResXmlAttributeArray attributeArray = getAttributeArray();
        if (attributeArray != null) {
            if (attributeArray.size() == 0) {
                return EmptyIterator.of();
            }
            return attributeArray.iterator(predicate);
        }
        return EmptyIterator.of();
    }

    static /* synthetic */ boolean lambda$getAttributesWithId$2(int i, ResXmlAttribute resXmlAttribute) {
        return resXmlAttribute.getNameId() == i;
    }

    public Iterator<ResXmlAttribute> getAttributesWithId(final int i) {
        return getAttributes(new Predicate() { // from class: com.reandroid.arsc.chunk.xml.ResXmlElement$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ResXmlElement.lambda$getAttributesWithId$2(i, (ResXmlAttribute) obj);
            }
        });
    }

    public Iterator<ResXmlAttribute> getAttributesWithName(final String str) {
        return getAttributes(new Predicate() { // from class: com.reandroid.arsc.chunk.xml.ResXmlElement$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ResXmlElement.lambda$getAttributesWithName$3(str, (ResXmlAttribute) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$getAttributesWithName$3(String str, ResXmlAttribute resXmlAttribute) {
        return resXmlAttribute.getNameId() == 0 && resXmlAttribute.equalsName(str);
    }

    public int getAttributeCount() {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            return startElement.getResXmlAttributeArray().size();
        }
        return 0;
    }

    public ResXmlAttribute getAttributeAt(int i) {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            return (ResXmlAttribute) startElement.getResXmlAttributeArray().get(i);
        }
        return null;
    }

    public ResXmlStringPool getStringPool() {
        for (Block parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof ResXmlDocument) {
                return ((ResXmlDocument) parent).getStringPool();
            }
        }
        return null;
    }

    public ResXmlIDMap getResXmlIDMap() {
        ResXmlDocument parentDocument = getParentDocument();
        if (parentDocument != null) {
            return parentDocument.getResXmlIDMap();
        }
        return null;
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    public int getDepth() {
        ResXmlElement parentElement = getParentElement();
        if (parentElement != null) {
            return parentElement.getDepth() + 1;
        }
        return 0;
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    void addEvents(ParserEventList parserEventList) {
        String startComment = getStartComment();
        if (startComment != null) {
            parserEventList.add(new ParserEvent(9, this, startComment, false));
        }
        parserEventList.add(new ParserEvent(2, this));
        Iterator<ResXmlNode> it = iterator();
        while (it.hasNext()) {
            it.next().addEvents(parserEventList);
        }
        String endComment = getEndComment();
        if (endComment != null) {
            parserEventList.add(new ParserEvent(9, this, endComment, true));
        }
        parserEventList.add(new ParserEvent(3, this));
    }

    public void addElement(ResXmlElement resXmlElement) {
        addNode(resXmlElement);
    }

    public boolean removeAttribute(ResXmlAttribute resXmlAttribute) {
        if (resXmlAttribute != null) {
            resXmlAttribute.onRemoved();
        }
        return getStartElement().getResXmlAttributeArray().remove(resXmlAttribute);
    }

    public boolean removeSelf() {
        ResXmlElement parentElement = getParentElement();
        if (parentElement != null) {
            return parentElement.remove(this);
        }
        return false;
    }

    public int countElements() {
        return CollectionUtil.count(getElements());
    }

    public boolean hasText() {
        return iterator(ResXmlTextNode.class).hasNext();
    }

    public boolean hasElement() {
        return iterator(ResXmlElement.class).hasNext();
    }

    public List<ResXmlNode> listXmlNodes() {
        return CollectionUtil.toList(iterator());
    }

    public Iterator<ResXmlTextNode> getTextNodes() {
        return iterator(ResXmlTextNode.class);
    }

    public List<ResXmlTextNode> listXmlTextNodes() {
        return CollectionUtil.toList(getTextNodes());
    }

    public void removeElements(final Predicate<? super ResXmlElement> predicate) {
        removeIf(new Predicate() { // from class: com.reandroid.arsc.chunk.xml.ResXmlElement$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ResXmlElement.lambda$removeElements$4(predicate, (ResXmlNode) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$removeElements$4(Predicate predicate, ResXmlNode resXmlNode) {
        if (resXmlNode instanceof ResXmlElement) {
            return predicate.test((ResXmlElement) resXmlNode);
        }
        return false;
    }

    ResXmlStartNamespace getStartNamespaceByUriRef(int i) {
        if (i < 0) {
            return null;
        }
        for (ResXmlStartNamespace resXmlStartNamespace : this.mStartNamespaceList) {
            if (i == resXmlStartNamespace.getUriReference()) {
                return resXmlStartNamespace;
            }
        }
        ResXmlElement parentElement = getParentElement();
        if (parentElement != null) {
            return parentElement.getStartNamespaceByUriRef(i);
        }
        return null;
    }

    ResXmlStartNamespace getXmlStartNamespace(String str, String str2) {
        if (str != null && str2 != null) {
            for (T t : this.mStartNamespaceList.getChildes()) {
                if (str.equals(t.getUri()) && str2.equals(t.getPrefix())) {
                    return t;
                }
            }
            ResXmlElement parentElement = getParentElement();
            if (parentElement != null) {
                return parentElement.getXmlStartNamespace(str, str2);
            }
        }
        return null;
    }

    private ResXmlStartNamespace getOrCreateXmlStartNamespace(String str, String str2) {
        return getOrCreateXmlStartNamespace(str, str2, 0);
    }

    private ResXmlStartNamespace getOrCreateXmlStartNamespace(String str, String str2, int i) {
        ResXmlStartNamespace xmlStartNamespace = getXmlStartNamespace(str, str2);
        if (xmlStartNamespace != null) {
            return xmlStartNamespace;
        }
        ResXmlStartNamespace resXmlStartNamespaceCreateXmlStartNamespace = getRootElement().createXmlStartNamespace(str, str2);
        resXmlStartNamespaceCreateXmlStartNamespace.setLineNumber(i);
        return resXmlStartNamespaceCreateXmlStartNamespace;
    }

    private ResXmlStartNamespace createXmlStartNamespace(String str, String str2) {
        ResXmlStartNamespace resXmlStartNamespace = new ResXmlStartNamespace();
        ResXmlEndNamespace resXmlEndNamespace = new ResXmlEndNamespace();
        resXmlStartNamespace.setEnd(resXmlEndNamespace);
        addStartNamespace(resXmlStartNamespace);
        addEndNamespace(resXmlEndNamespace, true);
        resXmlStartNamespace.setUriReference(getStringPool().getOrCreate(str).getIndex());
        resXmlStartNamespace.setPrefix(str2);
        return resXmlStartNamespace;
    }

    ResXmlStartNamespace getStartNamespaceByUri(String str) {
        if (str == null) {
            return null;
        }
        for (T t : this.mStartNamespaceList.getChildes()) {
            if (str.equals(t.getUri())) {
                return t;
            }
        }
        ResXmlElement parentElement = getParentElement();
        if (parentElement != null) {
            return parentElement.getStartNamespaceByUri(str);
        }
        return null;
    }

    private ResXmlStartNamespace getStartNamespaceByPrefix(String str, ResXmlStartNamespace resXmlStartNamespace) {
        if (str == null) {
            return resXmlStartNamespace;
        }
        for (ResXmlStartNamespace resXmlStartNamespace2 : getStartNamespaceList()) {
            if (str.equals(resXmlStartNamespace2.getPrefix())) {
                String uri = resXmlStartNamespace2.getUri();
                if (uri != null && uri.length() != 0) {
                    return resXmlStartNamespace2;
                }
                resXmlStartNamespace = resXmlStartNamespace2;
            }
        }
        ResXmlElement parentElement = getParentElement();
        return parentElement != null ? parentElement.getStartNamespaceByPrefix(str, resXmlStartNamespace) : resXmlStartNamespace;
    }

    private List<ResXmlStartNamespace> getStartNamespaceList() {
        return this.mStartNamespaceList.getChildes();
    }

    private void addStartNamespace(ResXmlStartNamespace resXmlStartNamespace) {
        this.mStartNamespaceList.add(resXmlStartNamespace);
    }

    private void addEndNamespace(ResXmlEndNamespace resXmlEndNamespace, boolean z) {
        if (z) {
            this.mEndNamespaceList.add(0, resXmlEndNamespace);
        } else {
            this.mEndNamespaceList.add(resXmlEndNamespace);
        }
    }

    void removeNamespace(ResXmlStartNamespace resXmlStartNamespace) {
        if (resXmlStartNamespace == null) {
            return;
        }
        resXmlStartNamespace.onRemoved();
        this.mStartNamespaceList.remove(resXmlStartNamespace);
        this.mEndNamespaceList.remove(resXmlStartNamespace.getEnd());
    }

    ResXmlStartElement newStartElement(int i) {
        ResXmlStartElement resXmlStartElement = new ResXmlStartElement();
        setStartElement(resXmlStartElement);
        ResXmlEndElement resXmlEndElement = new ResXmlEndElement();
        resXmlStartElement.setResXmlEndElement(resXmlEndElement);
        setEndElement(resXmlEndElement);
        resXmlEndElement.setResXmlStartElement(resXmlStartElement);
        resXmlStartElement.setLineNumber(i);
        resXmlEndElement.setLineNumber(i);
        return resXmlStartElement;
    }

    private ResXmlAttributeArray getAttributeArray() {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            return startElement.getResXmlAttributeArray();
        }
        return null;
    }

    private ResXmlStartElement getStartElement() {
        return (ResXmlStartElement) this.mStartElementContainer.getItem();
    }

    private void setStartElement(ResXmlStartElement resXmlStartElement) {
        this.mStartElementContainer.setItem(resXmlStartElement);
    }

    private ResXmlEndElement getEndElement() {
        return (ResXmlEndElement) this.mEndElementContainer.getItem();
    }

    private void setEndElement(ResXmlEndElement resXmlEndElement) {
        this.mEndElementContainer.setItem(resXmlEndElement);
    }

    private ResXmlTextNode getOrCreateResXmlText() {
        ResXmlNode resXmlNode = get(size() - 1);
        if (resXmlNode instanceof ResXmlTextNode) {
            return (ResXmlTextNode) resXmlNode;
        }
        return createResXmlTextNode();
    }

    public void addNode(ResXmlNode resXmlNode) {
        this.mBody.add(resXmlNode);
    }

    public void addNode(int i, ResXmlNode resXmlNode) {
        this.mBody.add(i, resXmlNode);
    }

    private boolean isBalanced() {
        return isElementBalanced() && isNamespaceBalanced();
    }

    private boolean isNamespaceBalanced() {
        return this.mStartNamespaceList.size() == this.mEndNamespaceList.size();
    }

    private boolean isElementBalanced() {
        return hasStartElement() && hasEndElement();
    }

    private boolean hasStartElement() {
        return this.mStartElementContainer.hasItem();
    }

    private boolean hasEndElement() {
        return this.mEndElementContainer.hasItem();
    }

    private void linkStartEnd() {
        linkStartEndElement();
        linkStartEndNameSpaces();
    }

    private void linkStartEndElement() {
        ResXmlStartElement startElement = getStartElement();
        ResXmlEndElement endElement = getEndElement();
        if (startElement == null || endElement == null) {
            return;
        }
        startElement.setResXmlEndElement(endElement);
        endElement.setResXmlStartElement(startElement);
    }

    private void ensureStartEndElement() {
        ResXmlStartElement startElement = getStartElement();
        ResXmlEndElement endElement = getEndElement();
        if (startElement == null || endElement == null) {
            if (startElement == null) {
                setStartElement(new ResXmlStartElement());
            }
            if (endElement == null) {
                setEndElement(new ResXmlEndElement());
            }
            linkStartEndElement();
        }
    }

    private void linkStartEndNameSpaces() {
        if (isNamespaceBalanced()) {
            int size = this.mStartNamespaceList.size();
            for (int i = 0; i < size; i++) {
                ((ResXmlStartNamespace) this.mStartNamespaceList.get(i)).setEnd((ResXmlEndNamespace) this.mEndNamespaceList.get((size - i) - 1));
            }
        }
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        int position = blockReader.getPosition();
        while (readNext(blockReader) && position != blockReader.getPosition()) {
            position = blockReader.getPosition();
        }
    }

    private boolean readNext(BlockReader blockReader) throws IOException {
        InfoHeader headerBlock;
        int position = blockReader.getPosition();
        if (isBalanced() || (headerBlock = blockReader.readHeaderBlock()) == null) {
            return false;
        }
        ChunkType chunkType = headerBlock.getChunkType();
        if (chunkType == null) {
            unknownChunk(headerBlock);
            return false;
        }
        if (chunkType == ChunkType.XML_START_ELEMENT) {
            onStartElement(blockReader);
        } else if (chunkType == ChunkType.XML_END_ELEMENT) {
            onEndElement(blockReader);
        } else if (chunkType == ChunkType.XML_START_NAMESPACE) {
            onStartNamespace(blockReader);
        } else if (chunkType == ChunkType.XML_END_NAMESPACE) {
            onEndNamespace(blockReader);
        } else if (chunkType == ChunkType.XML_CDATA) {
            onXmlText(blockReader);
        } else {
            unexpectedChunk(headerBlock);
        }
        if (!isBalanced()) {
            if (!blockReader.isAvailable()) {
                unBalancedFinish();
            } else if (position != blockReader.getPosition()) {
                return true;
            }
        }
        linkStartEnd();
        return false;
    }

    private void onStartElement(BlockReader blockReader) throws IOException {
        if (hasStartElement()) {
            ResXmlElement resXmlElement = new ResXmlElement();
            addElement(resXmlElement);
            resXmlElement.readBytes(blockReader);
        } else {
            ResXmlStartElement resXmlStartElement = new ResXmlStartElement();
            setStartElement(resXmlStartElement);
            resXmlStartElement.readBytes(blockReader);
        }
    }

    private void onEndElement(BlockReader blockReader) throws IOException {
        if (hasEndElement()) {
            multipleEndElement(blockReader);
            return;
        }
        ResXmlEndElement resXmlEndElement = new ResXmlEndElement();
        setEndElement(resXmlEndElement);
        resXmlEndElement.readBytes(blockReader);
    }

    private void onStartNamespace(BlockReader blockReader) throws IOException {
        ResXmlStartNamespace resXmlStartNamespace = new ResXmlStartNamespace();
        addStartNamespace(resXmlStartNamespace);
        resXmlStartNamespace.readBytes(blockReader);
    }

    private void onEndNamespace(BlockReader blockReader) throws IOException {
        ResXmlEndNamespace resXmlEndNamespace = new ResXmlEndNamespace();
        addEndNamespace(resXmlEndNamespace, false);
        resXmlEndNamespace.readBytes(blockReader);
    }

    private void onXmlText(BlockReader blockReader) throws IOException {
        createResXmlTextNode().getResXmlText().readBytes(blockReader);
    }

    private void unknownChunk(HeaderBlock headerBlock) throws IOException {
        throw new IOException("Unknown chunk: " + headerBlock.toString());
    }

    private void multipleEndElement(BlockReader blockReader) throws IOException {
        throw new IOException("Multiple end element: " + blockReader.toString());
    }

    private void unexpectedChunk(HeaderBlock headerBlock) throws IOException {
        throw new IOException("Unexpected chunk: " + headerBlock.toString());
    }

    private void unBalancedFinish() throws IOException {
        if (!isNamespaceBalanced()) {
            throw new IOException("Unbalanced namespace: start=" + this.mStartNamespaceList.size() + ", end=" + this.mEndNamespaceList.size());
        }
        if (isElementBalanced()) {
            return;
        }
        StringBuilder sb = new StringBuilder("Unbalanced element: start=");
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            sb.append(startElement);
        } else {
            sb.append("null");
        }
        sb.append(", end=");
        ResXmlEndElement endElement = getEndElement();
        if (endElement != null) {
            sb.append(endElement);
        } else {
            sb.append("null");
        }
        throw new IOException(sb.toString());
    }

    public void mergeWithName(ResourceMergeOption resourceMergeOption, ResXmlElement resXmlElement) {
        setName(resXmlElement.getName(false));
        setNamespace(resXmlElement.getNamespace());
        Iterator<ResXmlAttribute> attributes = resXmlElement.getAttributes();
        while (attributes.hasNext()) {
            newAttribute().mergeWithName(resourceMergeOption, attributes.next());
        }
        for (ResXmlNode resXmlNode : resXmlElement) {
            if (resXmlNode instanceof ResXmlElement) {
                createChildElement().mergeWithName(resourceMergeOption, (ResXmlElement) resXmlNode);
            } else if (resXmlNode instanceof ResXmlTextNode) {
                createResXmlTextNode().mergeWithName(resourceMergeOption, (ResXmlTextNode) resXmlNode);
            }
        }
        getStartElement().setComment(resXmlElement.getStartComment());
        getEndElement().setComment(resXmlElement.getEndComment());
        getStartElement().setLineNumber(resXmlElement.getStartElement().getLineNumber());
        getEndElement().setLineNumber(resXmlElement.getEndElement().getLineNumber());
        clearNullNodes(false);
        calculateAttributesOrder();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    public void serialize(XmlSerializer xmlSerializer, boolean z) throws IOException {
        int namespaceCount = getNamespaceCount();
        for (int i = 0; i < namespaceCount; i++) {
            ResXmlNamespace namespaceAt = getNamespaceAt(i);
            xmlSerializer.setPrefix(namespaceAt.getPrefix(), namespaceAt.getUri());
        }
        String startComment = getStartComment();
        if (startComment != null) {
            xmlSerializer.comment(startComment);
        }
        boolean featureSafe = getFeatureSafe(xmlSerializer, "http://xmlpull.org/v1/doc/features.html#indent-output");
        setIndent(xmlSerializer, featureSafe);
        xmlSerializer.startTag(getUri(), getName());
        int attributeCount = getAttributeCount();
        for (int i2 = 0; i2 < attributeCount; i2++) {
            getAttributeAt(i2).serialize(xmlSerializer, z);
        }
        boolean z2 = featureSafe;
        for (ResXmlNode resXmlNode : this) {
            if (z2 && (resXmlNode instanceof ResXmlTextNode)) {
                setIndent(xmlSerializer, false);
                z2 = false;
            }
            resXmlNode.serialize(xmlSerializer, z);
        }
        xmlSerializer.endTag(getUri(), getName());
        if (featureSafe != z2) {
            setIndent(xmlSerializer, true);
        }
        xmlSerializer.flush();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    public void parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        ResXmlNamespace namespaceByPrefix;
        if (xmlPullParser.getEventType() != 2) {
            throw new XmlPullParserException("Invalid state START_TAG != " + xmlPullParser.getEventType());
        }
        String name2 = xmlPullParser.getName();
        String strSplitPrefix = splitPrefix(name2);
        setName(splitName(name2));
        String namespace = xmlPullParser.getNamespace();
        if (strSplitPrefix == null) {
            strSplitPrefix = xmlPullParser.getPrefix();
        }
        parseNamespaces(xmlPullParser);
        setLineNumber(xmlPullParser.getLineNumber());
        parseAttributes(xmlPullParser);
        parseChildes(xmlPullParser);
        if (strSplitPrefix != null) {
            if ((namespace == null || namespace.length() == 0) && (namespaceByPrefix = getNamespaceByPrefix(strSplitPrefix)) != null) {
                namespace = namespaceByPrefix.getUri();
            }
            setNamespace(namespace, strSplitPrefix);
        }
        clearNullNodes(false);
        calculateAttributesOrder();
    }

    private void parseChildes(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int next = xmlPullParser.next();
        ResXmlElement resXmlElementCreateChildElement = this;
        while (next != 3 && next != 1) {
            if (next == 2) {
                resXmlElementCreateChildElement = createChildElement();
                resXmlElementCreateChildElement.parse(xmlPullParser);
            } else if (ResXmlTextNode.isTextEvent(next)) {
                getOrCreateResXmlText().parse(xmlPullParser);
            } else if (next == 9) {
                resXmlElementCreateChildElement.setComment(xmlPullParser.getText());
            }
            next = xmlPullParser.next();
        }
    }

    private void parseNamespaces(XmlPullParser xmlPullParser) throws XmlPullParserException {
        int namespaceCount = xmlPullParser.getNamespaceCount(xmlPullParser.getDepth());
        for (int i = 0; i < namespaceCount; i++) {
            getOrCreateXmlStartNamespace(xmlPullParser.getNamespaceUri(i), xmlPullParser.getNamespacePrefix(i), xmlPullParser.getLineNumber());
        }
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i2 = 0; i2 < attributeCount; i2++) {
            String attributeName = xmlPullParser.getAttributeName(i2);
            String strSplitPrefix = splitPrefix(attributeName);
            String strSplitName = splitName(attributeName);
            String attributeValue = xmlPullParser.getAttributeValue(i2);
            if (looksNamespace(attributeValue, strSplitPrefix)) {
                getOrCreateNamespace(attributeValue, strSplitName);
            }
        }
    }

    private void parseAttributes(XmlPullParser xmlPullParser) throws IOException {
        ResXmlNamespace namespaceByPrefix;
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            String strSplitPrefix = splitPrefix(attributeName);
            String strSplitName = splitName(attributeName);
            String attributeValue = xmlPullParser.getAttributeValue(i);
            if (!looksNamespace(attributeValue, strSplitPrefix)) {
                String attributeNamespace = null;
                String str = (strSplitPrefix == null && (strSplitPrefix = xmlPullParser.getAttributePrefix(i)) != null && strSplitPrefix.length() == 0) ? null : strSplitPrefix;
                if (str != null) {
                    attributeNamespace = xmlPullParser.getAttributeNamespace(i);
                    if (attributeNamespace.length() == 0 && (namespaceByPrefix = getNamespaceByPrefix(str)) != null) {
                        attributeNamespace = namespaceByPrefix.getUri();
                    }
                }
                newAttribute().encode(false, attributeNamespace, str, strSplitName, attributeValue);
            }
        }
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(ResXmlNode.NAME_node_type, NAME_element);
        jSONObject.put(NAME_name, getName(false));
        jSONObject.put(NAME_namespace_uri, getUri());
        jSONObject.put(NAME_namespace_prefix, getPrefix());
        int startLineNumber = getStartLineNumber();
        int endLineNumber = getEndLineNumber();
        jSONObject.put(NAME_line, startLineNumber);
        if (startLineNumber != endLineNumber) {
            jSONObject.put(NAME_line_end, endLineNumber);
        }
        JSONArray jSONArray = new JSONArray();
        for (ResXmlStartNamespace resXmlStartNamespace : getStartNamespaceList()) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(NAME_namespace_uri, resXmlStartNamespace.getUri());
            jSONObject2.put(NAME_namespace_prefix, resXmlStartNamespace.getPrefix());
            jSONArray.put(jSONObject2);
        }
        if (!jSONArray.isEmpty()) {
            jSONObject.put(NAME_namespaces, jSONArray);
        }
        jSONObject.put(NAME_comment, getStartComment());
        ResXmlAttributeArray attributeArray = getAttributeArray();
        if (attributeArray != null) {
            JSONArray json = attributeArray.toJson();
            if (!json.isEmpty()) {
                jSONObject.put(NAME_attributes, json);
            }
        }
        JSONArray jSONArray2 = new JSONArray();
        Iterator<ResXmlNode> it = iterator();
        while (it.hasNext()) {
            jSONArray2.put(it.next().toJson());
        }
        if (!jSONArray2.isEmpty()) {
            jSONObject.put(NAME_childes, jSONArray2);
        }
        return jSONObject;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        ensureStartEndElement();
        int iOptInt = jSONObject.optInt(NAME_line, 0);
        int iOptInt2 = jSONObject.optInt(NAME_line_end, 0);
        if (iOptInt2 == 0 && iOptInt != 0) {
            iOptInt2 = iOptInt;
        }
        setStartLineNumber(iOptInt);
        setEndLineNumber(iOptInt2);
        Iterator<ResXmlStartNamespace> it = getStartNamespaceList().iterator();
        while (it.hasNext()) {
            it.next().setLineNumber(iOptInt);
        }
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(NAME_namespaces);
        if (jSONArrayOptJSONArray != null) {
            int length = jSONArrayOptJSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i);
                newNamespace(jSONObject2.optString(NAME_namespace_uri, ""), jSONObject2.optString(NAME_namespace_prefix, ""));
            }
        }
        setName(jSONObject.getString(NAME_name));
        setNamespace(jSONObject.optString(NAME_namespace_uri, null), jSONObject.optString(NAME_namespace_prefix, null));
        setComment(jSONObject.optString(NAME_comment));
        addResXmlText(jSONObject.optString(NAME_text, null));
        ResXmlAttributeArray attributeArray = getAttributeArray();
        if (attributeArray != null) {
            attributeArray.fromJson(jSONObject.optJSONArray(NAME_attributes));
        }
        JSONArray jSONArrayOptJSONArray2 = jSONObject.optJSONArray(NAME_childes);
        if (jSONArrayOptJSONArray2 != null) {
            int length2 = jSONArrayOptJSONArray2.length();
            for (int i2 = 0; i2 < length2; i2++) {
                JSONObject jSONObject3 = jSONArrayOptJSONArray2.getJSONObject(i2);
                if (isTextNode(jSONObject3)) {
                    createResXmlTextNode().fromJson(jSONObject3);
                } else {
                    createChildElement().fromJson(jSONObject3);
                }
            }
        }
        getStartElement().calculatePositions();
    }

    private boolean isTextNode(JSONObject jSONObject) {
        String strOptString = jSONObject.optString(ResXmlNode.NAME_node_type, null);
        if ("text".equals(strOptString)) {
            return true;
        }
        if (NAME_element.equals(strOptString)) {
            return false;
        }
        return jSONObject.has(NAME_text);
    }

    public XMLElement toXml() {
        return toXml(null, false);
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNode
    public XMLElement toXml(boolean z) {
        return toXml(null, z);
    }

    public XMLElement decodeToXml() {
        return toXml(null, true);
    }

    private XMLElement toXml(XMLElement xMLElement, boolean z) {
        XMLElement xMLElementNewElement;
        if (xMLElement == null) {
            xMLElementNewElement = new XMLElement();
        } else {
            xMLElementNewElement = xMLElement.newElement();
        }
        xMLElementNewElement.setName(getName(false));
        xMLElementNewElement.setLineNumber(getStartElement().getLineNumber());
        Iterator<ResXmlStartNamespace> it = getStartNamespaceList().iterator();
        while (it.hasNext()) {
            xMLElementNewElement.addNamespace(it.next().decodeToXml());
        }
        xMLElementNewElement.setNamespace(getNamespace());
        Iterator<ResXmlAttribute> attributes = getAttributes();
        while (attributes.hasNext()) {
            ResXmlAttribute next = attributes.next();
            XMLAttribute xml = next.toXml(z);
            xMLElementNewElement.addAttribute(xml);
            if (z) {
                xml.setNamespace(next.decodeUri(), next.decodePrefix());
            } else {
                xml.setNamespace(next.getUri(), next.decodePrefix());
            }
        }
        String startComment = getStartComment();
        if (startComment != null) {
            xMLElementNewElement.add((XMLNode) new XMLComment(startComment));
        }
        String endComment = getEndComment();
        if (endComment != null) {
            xMLElementNewElement.add((XMLNode) new XMLComment(endComment));
        }
        for (ResXmlNode resXmlNode : this) {
            if (resXmlNode instanceof ResXmlElement) {
                ((ResXmlElement) resXmlNode).toXml(xMLElementNewElement, z);
            } else {
                xMLElementNewElement.add(resXmlNode.toXml(z));
            }
        }
        return xMLElementNewElement;
    }

    @Override // java.util.Comparator
    public int compare(ResXmlNode resXmlNode, ResXmlNode resXmlNode2) {
        return CompareUtil.compare(resXmlNode.getIndex(), resXmlNode2.getIndex());
    }

    public String toString() {
        ResXmlStartElement startElement = getStartElement();
        if (startElement != null) {
            StringBuilder sb = new StringBuilder("(");
            sb.append(getStartLineNumber());
            sb.append(":");
            sb.append(getEndLineNumber());
            sb.append(") <");
            sb.append(startElement.toString());
            if (hasText() && !hasElement()) {
                sb.append(">");
                Iterator<ResXmlTextNode> it = listXmlTextNodes().iterator();
                while (it.hasNext()) {
                    sb.append(it.next().getText());
                }
                sb.append("</");
                sb.append(startElement.getTagName());
                sb.append(">");
            } else {
                sb.append("/>");
            }
            return sb.toString();
        }
        return "NULL";
    }

    private static boolean looksNamespace(String str, String str2) {
        return str.length() != 0 && XMLConstants.XMLNS_ATTRIBUTE.equals(str2);
    }

    private static boolean getFeatureSafe(XmlSerializer xmlSerializer, String str) {
        try {
            return xmlSerializer.getFeature(str);
        } catch (Throwable unused) {
            return false;
        }
    }

    private static String splitPrefix(String str) {
        int iIndexOf = str.indexOf(58);
        if (iIndexOf >= 0) {
            return str.substring(0, iIndexOf);
        }
        return null;
    }

    private static String splitName(String str) {
        int iIndexOf = str.indexOf(58);
        return iIndexOf >= 0 ? str.substring(iIndexOf + 1) : str;
    }

    static void setIndent(XmlSerializer xmlSerializer, boolean z) {
        setFeatureSafe(xmlSerializer, "http://xmlpull.org/v1/doc/features.html#indent-output", z);
    }

    private static void setFeatureSafe(XmlSerializer xmlSerializer, String str, boolean z) {
        try {
            xmlSerializer.setFeature(str, z);
        } catch (Throwable unused) {
        }
    }
}
