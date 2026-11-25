package com.reandroid.arsc.chunk.xml;

import com.reandroid.archive.InputSource;
import com.reandroid.arsc.ApkFile;
import com.reandroid.arsc.chunk.Chunk;
import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.chunk.MainChunk;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.ParentChunk;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.header.HeaderBlock;
import com.reandroid.arsc.header.InfoHeader;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.pool.ResXmlStringPool;
import com.reandroid.arsc.pool.StringPool;
import com.reandroid.arsc.refactor.ResourceMergeOption;
import com.reandroid.common.BytesOutputStream;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.IterableIterator;
import com.reandroid.utils.collection.SingleIterator;
import com.reandroid.xml.XMLDocument;
import com.reandroid.xml.XMLFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlDocument extends Chunk<HeaderBlock> implements ResXmlNodeTree, MainChunk, ParentChunk, JSONConvert<JSONObject> {
    private static final String NAME_element = "element";
    private ApkFile mApkFile;
    private boolean mDestroyed;
    private final BlockList<ResXmlNode> mNodeList;
    private PackageBlock mPackageBlock;
    private final ResXmlIDMap mResXmlIDMap;
    private final ResXmlStringPool mResXmlStringPool;

    @Override // com.reandroid.arsc.chunk.ParentChunk
    public MainChunk getMainChunk() {
        return this;
    }

    @Override // com.reandroid.arsc.chunk.ParentChunk
    public StringPool<?> getSpecStringPool() {
        return null;
    }

    @Override // com.reandroid.arsc.chunk.Chunk
    protected void onChunkRefreshed() {
    }

    public ResXmlDocument() {
        super(new HeaderBlock(ChunkType.XML), 3);
        ResXmlStringPool resXmlStringPool = new ResXmlStringPool(true);
        this.mResXmlStringPool = resXmlStringPool;
        ResXmlIDMap resXmlIDMap = new ResXmlIDMap();
        this.mResXmlIDMap = resXmlIDMap;
        BlockList<ResXmlNode> blockList = new BlockList<>();
        this.mNodeList = blockList;
        addChild(resXmlStringPool);
        addChild(resXmlIDMap);
        addChild(blockList);
        blockList.add(new ResXmlElement());
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNodeTree
    public BlockList<ResXmlNode> getNodeListBlockInternal() {
        return this.mNodeList;
    }

    public Iterator<ResXmlAttribute> recursiveAttributes() throws ConcurrentModificationException {
        return new IterableIterator<ResXmlElement, ResXmlAttribute>(getElements()) { // from class: com.reandroid.arsc.chunk.xml.ResXmlDocument.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<ResXmlAttribute> iterator(ResXmlElement resXmlElement) {
                return resXmlElement.recursiveAttributes();
            }
        };
    }

    public Iterator<ResXmlNode> recursiveXmlNodes() throws ConcurrentModificationException {
        return new IterableIterator<ResXmlNode, ResXmlNode>(iterator()) { // from class: com.reandroid.arsc.chunk.xml.ResXmlDocument.2
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<ResXmlNode> iterator(ResXmlNode resXmlNode) {
                if (resXmlNode instanceof ResXmlElement) {
                    return ((ResXmlElement) resXmlNode).recursiveXmlNodes();
                }
                return SingleIterator.of(resXmlNode);
            }
        };
    }

    public Iterator<ResXmlElement> recursiveElements() throws ConcurrentModificationException {
        return new IterableIterator<ResXmlElement, ResXmlElement>(getElements()) { // from class: com.reandroid.arsc.chunk.xml.ResXmlDocument.3
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<ResXmlElement> iterator(ResXmlElement resXmlElement) {
                return resXmlElement.recursiveElements();
            }
        };
    }

    public int autoSetAttributeNamespaces() {
        return autoSetAttributeNamespaces(true);
    }

    public int autoSetAttributeNamespaces(boolean z) {
        Iterator<ResXmlElement> elements = getElements();
        int iAutoSetAttributeNamespaces = 0;
        while (elements.hasNext()) {
            iAutoSetAttributeNamespaces += elements.next().autoSetAttributeNamespaces(z);
        }
        if (iAutoSetAttributeNamespaces > 0) {
            removeUnusedNamespaces();
            getStringPool().removeUnusedStrings();
        }
        return iAutoSetAttributeNamespaces;
    }

    public int autoSetAttributeNames() {
        return autoSetAttributeNames(true);
    }

    public int autoSetAttributeNames(boolean z) {
        Iterator<ResXmlElement> elements = getElements();
        int iAutoSetAttributeNames = 0;
        while (elements.hasNext()) {
            iAutoSetAttributeNames += elements.next().autoSetAttributeNames(z);
        }
        if (iAutoSetAttributeNames > 0) {
            removeUnusedNamespaces();
            getStringPool().removeUnusedStrings();
        }
        return iAutoSetAttributeNames;
    }

    public void autoSetLineNumber() {
        Iterator<ResXmlElement> elements = getElements();
        while (elements.hasNext()) {
            elements.next().autoSetLineNumber();
        }
    }

    public int removeUnusedNamespaces() {
        Iterator<ResXmlElement> elements = getElements();
        int iRemoveUnusedNamespaces = 0;
        while (elements.hasNext()) {
            iRemoveUnusedNamespaces += elements.next().removeUnusedNamespaces();
        }
        return iRemoveUnusedNamespaces;
    }

    public String refreshFull() {
        boolean z;
        int chunkSize = getHeaderBlock().getChunkSize();
        StringBuilder sb = new StringBuilder();
        getStringPool().compressDuplicates();
        Iterator<ResXmlElement> elements = getElements();
        boolean z2 = false;
        while (true) {
            z = true;
            if (!elements.hasNext()) {
                break;
            }
            int iRemoveUndefinedAttributes = elements.next().removeUndefinedAttributes();
            if (iRemoveUndefinedAttributes != 0) {
                sb.append("Removed undefined attributes = ");
                sb.append(iRemoveUndefinedAttributes);
                z2 = true;
            }
        }
        int iRemoveUnusedNamespaces = removeUnusedNamespaces();
        if (iRemoveUnusedNamespaces != 0) {
            if (z2) {
                sb.append("\n");
            }
            sb.append("Removed unused namespaces = ");
            sb.append(iRemoveUnusedNamespaces);
            z2 = true;
        }
        if (getStringPool().removeUnusedStrings()) {
            if (z2) {
                sb.append("\n");
            }
            sb.append("Removed unused xml strings");
            z2 = true;
        }
        refresh();
        int chunkSize2 = getHeaderBlock().getChunkSize();
        if (chunkSize != chunkSize2) {
            if (z2) {
                sb.append("\n");
            }
            sb.append("Xml size changed = ");
            sb.append(chunkSize);
            sb.append(", ");
            sb.append(chunkSize2);
        } else {
            z = z2;
        }
        if (z) {
            return sb.toString();
        }
        return null;
    }

    public void destroy() {
        synchronized (this) {
            if (this.mDestroyed) {
                return;
            }
            this.mDestroyed = true;
            this.mNodeList.clearChildes();
            getResXmlIDMap().destroy();
            getStringPool().clear();
            refresh();
        }
    }

    public void setAttributesUnitSize(int i, boolean z) {
        Iterator<ResXmlElement> elements = getElements();
        while (elements.hasNext()) {
            elements.next().setAttributesUnitSize(i, z);
        }
    }

    public ResXmlElement getOrCreateElement(String str) {
        ResXmlElement element = getElement(str);
        if (element == null) {
            return createRootElement(str);
        }
        if (str == null) {
            return element;
        }
        element.setName(str);
        return element;
    }

    public ResXmlElement createRootElement(String str) {
        ResXmlElement resXmlElementNewElement = newElement();
        resXmlElementNewElement.newStartElement(1);
        if (str != null) {
            resXmlElementNewElement.setName(str);
        }
        return resXmlElementNewElement;
    }

    void linkStringReferences() {
        Iterator<ResXmlElement> elements = getElements();
        while (elements.hasNext()) {
            elements.next().linkStringReferences();
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

    @Override // com.reandroid.arsc.chunk.Chunk, com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        InfoHeader headerBlock = blockReader.readHeaderBlock();
        if (headerBlock == null) {
            throw new IOException("Not bin xml: " + blockReader);
        }
        int chunkSize = headerBlock.getChunkSize();
        if (chunkSize < 0) {
            throw new IOException("Negative chunk size: " + chunkSize);
        }
        if (chunkSize > blockReader.available()) {
            throw new IOException("Higher chunk size: " + chunkSize + ", available = " + blockReader.available());
        }
        if (chunkSize < headerBlock.getHeaderSize()) {
            throw new IOException("Higher header size: " + headerBlock);
        }
        BlockReader blockReaderCreate = blockReader.create(chunkSize);
        HeaderBlock headerBlock2 = getHeaderBlock();
        headerBlock2.readBytes(blockReaderCreate);
        headerBlock2.setType(ChunkType.XML);
        clear();
        while (blockReaderCreate.isAvailable() && readNext(blockReaderCreate)) {
        }
        blockReader.offset(headerBlock2.getChunkSize());
        blockReaderCreate.close();
        onChunkLoaded();
    }

    @Override // com.reandroid.arsc.chunk.Chunk
    public void onChunkLoaded() {
        super.onChunkLoaded();
        linkStringReferences();
    }

    private boolean readNext(BlockReader blockReader) throws IOException {
        if (!blockReader.isAvailable()) {
            return false;
        }
        int position = blockReader.getPosition();
        InfoHeader headerBlock = blockReader.readHeaderBlock();
        if (headerBlock == null) {
            return false;
        }
        ChunkType chunkType = headerBlock.getChunkType();
        if (chunkType == ChunkType.STRING) {
            this.mResXmlStringPool.readBytes(blockReader);
        } else if (chunkType == ChunkType.XML_RESOURCE_MAP) {
            this.mResXmlIDMap.readBytes(blockReader);
        } else {
            if (isElementChunk(chunkType)) {
                newElement().readBytes(blockReader);
                return blockReader.isAvailable();
            }
            throw new IOException("Unexpected chunk " + headerBlock);
        }
        return blockReader.isAvailable() && position != blockReader.getPosition();
    }

    private boolean isElementChunk(ChunkType chunkType) {
        return chunkType == ChunkType.XML_START_ELEMENT || chunkType == ChunkType.XML_END_ELEMENT || chunkType == ChunkType.XML_START_NAMESPACE || chunkType == ChunkType.XML_END_NAMESPACE || chunkType == ChunkType.XML_CDATA || chunkType == ChunkType.XML_LAST_CHUNK;
    }

    @Override // com.reandroid.arsc.chunk.MainChunk
    public ResXmlStringPool getStringPool() {
        return this.mResXmlStringPool;
    }

    @Override // com.reandroid.arsc.chunk.MainChunk
    public ApkFile getApkFile() {
        return this.mApkFile;
    }

    @Override // com.reandroid.arsc.chunk.MainChunk
    public void setApkFile(ApkFile apkFile) {
        this.mApkFile = apkFile;
    }

    @Override // com.reandroid.arsc.chunk.ParentChunk
    public PackageBlock getPackageBlock() {
        TableBlock loadedTableBlock;
        ApkFile apkFile = this.mApkFile;
        PackageBlock packageBlock = this.mPackageBlock;
        if (apkFile == null || packageBlock != null || (loadedTableBlock = apkFile.getLoadedTableBlock()) == null) {
            return packageBlock;
        }
        PackageBlock packageBlockSelectPackageBlock = selectPackageBlock(loadedTableBlock);
        this.mPackageBlock = packageBlockSelectPackageBlock;
        return packageBlockSelectPackageBlock;
    }

    public void setPackageBlock(PackageBlock packageBlock) {
        this.mPackageBlock = packageBlock;
    }

    PackageBlock selectPackageBlock(TableBlock tableBlock) {
        PackageBlock packageBlockPickOne = tableBlock.pickOne();
        return packageBlockPickOne == null ? tableBlock.pickOrEmptyPackage() : packageBlockPickOne;
    }

    @Override // com.reandroid.arsc.chunk.MainChunk
    public TableBlock getTableBlock() {
        TableBlock tableBlock;
        PackageBlock packageBlock = getPackageBlock();
        if (packageBlock != null && (tableBlock = packageBlock.getTableBlock()) != null) {
            return tableBlock;
        }
        ApkFile apkFile = getApkFile();
        if (apkFile != null) {
            return apkFile.getLoadedTableBlock();
        }
        return null;
    }

    public ResXmlIDMap getResXmlIDMap() {
        return this.mResXmlIDMap;
    }

    public ResXmlElement getDocumentElement() {
        return (ResXmlElement) CollectionUtil.getFirst(getElements());
    }

    public ResXmlElement newElement() {
        clearEmptyElements();
        ResXmlElement resXmlElement = new ResXmlElement();
        add(resXmlElement);
        return resXmlElement;
    }

    public void addElement(int i, ResXmlElement resXmlElement) {
        clearEmptyElements();
        add(i, resXmlElement);
    }

    public void clearEmptyElements() {
        removeIf(new Predicate() { // from class: com.reandroid.arsc.chunk.xml.ResXmlDocument$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ResXmlDocument.lambda$clearEmptyElements$0((ResXmlNode) obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$clearEmptyElements$0(ResXmlNode resXmlNode) {
        if (resXmlNode instanceof ResXmlElement) {
            return ((ResXmlElement) resXmlNode).isUndefined();
        }
        return false;
    }

    @Override // com.reandroid.arsc.base.BlockContainer
    protected void onPreRefresh() {
        clearEmptyElements();
        getNodeListBlockInternal().refresh();
        super.onPreRefresh();
    }

    public void readBytes(File file) throws IOException {
        super.readBytes(new BlockReader(file));
    }

    public void readBytes(InputStream inputStream) throws IOException {
        super.readBytes(new BlockReader(inputStream));
    }

    public final int writeBytes(File file) throws IOException {
        if (isNull()) {
            throw new IOException("Can NOT save null block");
        }
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        int iWriteBytes = super.writeBytes(fileOutputStream);
        fileOutputStream.close();
        return iWriteBytes;
    }

    public void mergeWithName(ResourceMergeOption resourceMergeOption, ResXmlDocument resXmlDocument) {
        if (resXmlDocument == this) {
            return;
        }
        Iterator<ResXmlElement> elements = resXmlDocument.getElements();
        while (elements.hasNext()) {
            ResXmlElement next = elements.next();
            getOrCreateElement(next.getName()).mergeWithName(resourceMergeOption, next);
        }
    }

    public void parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        if (this.mDestroyed) {
            throw new IOException("Destroyed document");
        }
        PackageBlock packageBlock = getPackageBlock();
        if (packageBlock == null) {
            throw new IOException("Can not decode without package");
        }
        setPackageBlock(packageBlock);
        if (xmlPullParser.getEventType() == 0) {
            clear();
            xmlPullParser.next();
        }
        while (parseNext(xmlPullParser)) {
            xmlPullParser.next();
        }
        refreshFull();
    }

    private boolean parseNext(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int eventType = xmlPullParser.getEventType();
        while (eventType != 2 && eventType != 1) {
            eventType = xmlPullParser.next();
        }
        if (eventType != 2) {
            return false;
        }
        newElement().parse(xmlPullParser);
        return true;
    }

    public String serializeToXml() throws IOException {
        StringWriter stringWriter = new StringWriter();
        XmlSerializer xmlSerializerNewSerializer = XMLFactory.newSerializer(stringWriter);
        serialize(xmlSerializerNewSerializer);
        xmlSerializerNewSerializer.flush();
        stringWriter.flush();
        stringWriter.close();
        return stringWriter.toString();
    }

    public void serialize(XmlSerializer xmlSerializer) throws IOException {
        serialize(xmlSerializer, true);
    }

    public void serialize(XmlSerializer xmlSerializer, boolean z) throws IOException {
        if (this.mDestroyed) {
            throw new IOException("Destroyed document");
        }
        PackageBlock packageBlock = getPackageBlock();
        if (z && packageBlock == null) {
            throw new IOException("Can not decode without package");
        }
        ResXmlElement.setIndent(xmlSerializer, true);
        xmlSerializer.startDocument("utf-8", null);
        autoSetAttributeNamespaces();
        Iterator<ResXmlNode> it = iterator();
        while (it.hasNext()) {
            it.next().serialize(xmlSerializer, z);
        }
        xmlSerializer.endDocument();
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("element", getDocumentElement().toJson());
        return jSONObject;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        getDocumentElement().fromJson(jSONObject.optJSONObject("element"));
        refresh();
    }

    public XMLDocument decodeToXml() {
        return toXml(true);
    }

    public XMLDocument toXml() {
        return toXml(false);
    }

    public XMLDocument toXml(boolean z) {
        XMLDocument xMLDocument = new XMLDocument();
        xMLDocument.setEncoding("utf-8");
        Iterator<ResXmlNode> it = iterator();
        while (it.hasNext()) {
            xMLDocument.add(it.next().toXml(z));
        }
        return xMLDocument;
    }

    void addEvents(ParserEventList parserEventList) {
        ResXmlElement documentElement = getDocumentElement();
        parserEventList.add(new ParserEvent(0, documentElement));
        Iterator<ResXmlElement> elements = getElements();
        while (elements.hasNext()) {
            elements.next().addEvents(parserEventList);
        }
        parserEventList.add(new ParserEvent(1, documentElement));
    }

    public static boolean isResXmlBlock(File file) {
        if (file == null) {
            return false;
        }
        try {
            return isResXmlBlock(InfoHeader.readHeaderBlock(file));
        } catch (IOException unused) {
            return false;
        }
    }

    public static boolean isResXmlBlock(InputSource inputSource) {
        boolean zIsResXmlBlock = false;
        try {
            InputStream inputStreamOpenStream = inputSource.openStream();
            zIsResXmlBlock = isResXmlBlock(inputStreamOpenStream);
            inputStreamOpenStream.close();
            return zIsResXmlBlock;
        } catch (IOException unused) {
            return zIsResXmlBlock;
        }
    }

    public static boolean isResXmlBlock(InputStream inputStream) {
        try {
            return isResXmlBlock(BlockReader.readHeaderBlock(inputStream));
        } catch (IOException unused) {
            return false;
        }
    }

    public static boolean isResXmlBlock(byte[] bArr) {
        try {
            return isResXmlBlock(BlockReader.readHeaderBlock(bArr));
        } catch (IOException unused) {
            return false;
        }
    }

    public static boolean isResXmlBlock(BlockReader blockReader) {
        if (blockReader == null) {
            return false;
        }
        try {
            return isResXmlBlock(blockReader.readHeaderBlock());
        } catch (IOException unused) {
            return false;
        }
    }

    public static boolean isResXmlBlock(HeaderBlock headerBlock) {
        return headerBlock != null && headerBlock.getChunkType() == ChunkType.XML;
    }
}
