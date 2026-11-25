package com.reandroid.apkeditor.info;

import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.archive.block.CertificateBlock;
import com.reandroid.arsc.array.ResValueMapArray;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.xml.ResXmlAttribute;
import com.reandroid.arsc.chunk.xml.ResXmlDocument;
import com.reandroid.arsc.chunk.xml.ResXmlElement;
import com.reandroid.arsc.chunk.xml.ResXmlNamespace;
import com.reandroid.arsc.chunk.xml.ResXmlNode;
import com.reandroid.arsc.chunk.xml.ResXmlTextNode;
import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.header.StringPoolHeader;
import com.reandroid.arsc.item.StringItem;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.pool.StringPool;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResTableMapEntry;
import com.reandroid.arsc.value.ResValue;
import com.reandroid.arsc.value.ResValueMap;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.dex.model.DexFile;
import com.reandroid.dex.sections.MapItem;
import com.reandroid.dex.sections.MapList;
import com.reandroid.dex.sections.Marker;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.StringsUtil;
import com.reandroid.utils.collection.CollectionUtil;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes10.dex */
public class InfoWriterText extends InfoWriter {
    private static final String ARRAY_TAB = "     ";

    public InfoWriterText(Writer writer) {
        super(writer);
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeStringPool(String str, StringPool<?> stringPool) throws IOException {
        Writer writer = getWriter();
        writer.write(str);
        writer.write("\n");
        writer.write("String pool of ");
        writer.write(Integer.toString(stringPool.size()));
        writer.write(" unique ");
        if (stringPool.isUtf8()) {
            writer.write("UTF-8 ");
        } else {
            writer.write("UTF-16 ");
        }
        StringPoolHeader headerBlock = stringPool.getHeaderBlock();
        if (!headerBlock.isSorted()) {
            writer.write("non-");
        }
        writer.write("sorted strings, ");
        writer.write(Integer.toString(stringPool.size()));
        writer.write(" entries and ");
        writer.write(Integer.toString(stringPool.countStyles()));
        writer.write(" styles using ");
        writer.write(Integer.toString(headerBlock.getChunkSize()));
        writer.write(" bytes:");
        writer.write("\n");
        int size = stringPool.size();
        for (int i = 0; i < size; i++) {
            StringItem stringItem = stringPool.get(i);
            writer.write("String #");
            writer.write(Integer.toString(i));
            writer.write(": ");
            writer.write(stringItem.get());
            writer.write("\n");
        }
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeXmlDocument(String str, ResXmlDocument resXmlDocument) throws IOException {
        writeNameValue("source-path", str);
        for (ResXmlNode resXmlNode : resXmlDocument) {
            if (resXmlNode instanceof ResXmlElement) {
                writeElement((ResXmlElement) resXmlNode);
            } else if (resXmlNode instanceof ResXmlTextNode) {
                writeTextNode(0, (ResXmlTextNode) resXmlNode);
            }
        }
        getWriter().flush();
    }

    private void writeElement(ResXmlElement resXmlElement) throws IOException {
        int depth = resXmlElement.getDepth() * 2;
        int namespaceCount = resXmlElement.getNamespaceCount();
        for (int i = 0; i < namespaceCount; i++) {
            writeNamespace(depth, resXmlElement.getNamespaceAt(i));
        }
        int i2 = depth + 2;
        Writer writer = getWriter();
        writeIndent(i2);
        writer.write("E: ");
        writer.write(resXmlElement.getName(true));
        writer.write(" (line=");
        writer.write(Integer.toString(resXmlElement.getLineNumber()));
        writer.write(Constant.AFTER_QUTO);
        writer.write("\n");
        Iterator<ResXmlAttribute> attributes = resXmlElement.getAttributes();
        while (attributes.hasNext()) {
            writeAttribute(i2, attributes.next());
        }
        flush();
        for (ResXmlNode resXmlNode : resXmlElement) {
            if (resXmlNode instanceof ResXmlElement) {
                writeElement((ResXmlElement) resXmlNode);
            } else if (resXmlNode instanceof ResXmlTextNode) {
                writeTextNode(i2, (ResXmlTextNode) resXmlNode);
            }
        }
    }

    private void writeTextNode(int i, ResXmlTextNode resXmlTextNode) throws IOException {
        Writer writer = getWriter();
        writeIndent(i + 2);
        writer.write("T: \"");
        writer.write(resXmlTextNode.getText());
        writer.write("\"");
        writer.write("\n");
    }

    private void writeNamespace(int i, ResXmlNamespace resXmlNamespace) throws IOException {
        Writer writer = getWriter();
        writeIndent(i);
        writer.write("N: ");
        writer.write(resXmlNamespace.getPrefix());
        writer.write(ContainerUtils.KEY_VALUE_DELIMITER);
        writer.write(resXmlNamespace.getUri());
        writer.write("\n");
    }

    private void writeAttribute(int i, ResXmlAttribute resXmlAttribute) throws IOException {
        Writer writer = getWriter();
        writeIndent(i + 2);
        writer.write("A: ");
        ResXmlNamespace namespace = resXmlAttribute.getNamespace();
        if (namespace != null) {
            writer.write(namespace.getPrefix());
            writer.append(':');
        }
        writer.write(resXmlAttribute.getName());
        int nameId = resXmlAttribute.getNameId();
        if (nameId != 0) {
            writer.append('(');
            writer.write(HexUtil.toHex8(nameId));
            writer.append(')');
        }
        writer.append('=');
        ValueType valueType = resXmlAttribute.getValueType();
        if (valueType == ValueType.STRING) {
            writer.append('\"');
            writer.write(resXmlAttribute.getDataAsPoolString().getXml());
            writer.append('\"');
            writer.write(" (Raw: \"");
            writer.write(resXmlAttribute.getValueString());
            writer.write("\")");
        } else if (valueType == ValueType.BOOLEAN) {
            writer.append('\"');
            writer.write(resXmlAttribute.getValueAsBoolean() ? "true" : "false");
            writer.append('\"');
        } else {
            writer.write("(type ");
            writer.write(HexUtil.toHex(valueType.getByte() & 255, 1));
            writer.write(Constant.AFTER_QUTO);
            writer.write(HexUtil.toHex(resXmlAttribute.getData(), 1));
        }
        writer.write("\n");
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeCertificates(List<CertificateBlock> list, boolean z) throws IOException {
        Writer writer = getWriter();
        writer.write("\n");
        writeNameValue("Certificates", Integer.valueOf(list.size()));
        for (CertificateBlock certificateBlock : list) {
            writeWithTab(writer, ARRAY_TAB, certificateBlock.printCertificate());
            if (z) {
                writer.write(ARRAY_TAB);
                writer.write("Base64: ");
                writer.write(toBase64(certificateBlock.getCertificateBytes()));
                writer.write("\n");
            }
        }
        flush();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeDexInfo(DexFile dexFile, boolean z) throws IOException {
        Writer writer = getWriter();
        writer.write("\n");
        writeNameValue(SchemaSymbols.ATTVAL_NAME, dexFile.getFileName());
        writeNameValue("Version", Integer.valueOf(dexFile.getVersion()));
        List<Marker> list = CollectionUtil.toList(dexFile.getMarkers());
        if (list.size() != 0) {
            writer.write("Markers:");
            for (Marker marker : list) {
                writer.write("\n");
                writer.write(ARRAY_TAB);
                writer.write(marker.toString());
            }
        }
        writer.write("\n");
        MapList mapList = dexFile.getDexLayout().getMapList();
        writer.write("Sections:");
        for (MapItem mapItem : mapList) {
            writer.write("\n");
            writer.write(ARRAY_TAB);
            writer.write(mapItem.toString());
        }
        writer.write("\n");
        writer.flush();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeResources(PackageBlock packageBlock, List<String> list, boolean z) throws IOException {
        Writer writer = getWriter();
        writer.write("Package name=");
        writer.write(packageBlock.getName());
        writer.write(" id=");
        writer.write(HexUtil.toHex2((byte) packageBlock.getId()));
        writer.write("\n");
        packageBlock.sortTypes();
        for (SpecTypePair specTypePair : packageBlock.listSpecTypePairs()) {
            if (contains(specTypePair, list)) {
                writeResources(specTypePair, z);
            }
        }
    }

    public void writeResources(SpecTypePair specTypePair, boolean z) throws IOException {
        Writer writer = getWriter();
        writer.write("  type ");
        writer.write(specTypePair.getTypeName());
        writer.write(" id=");
        writer.write(Integer.toString(specTypePair.getId()));
        writer.write(" entryCount=");
        writer.write(Integer.toString(specTypePair.getHighestEntryCount()));
        writer.write("\n");
        Iterator<ResourceEntry> resources = specTypePair.getResources();
        while (resources.hasNext()) {
            writeResources(resources.next(), z);
        }
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeResources(ResourceEntry resourceEntry, boolean z) throws IOException {
        if (resourceEntry.isEmpty()) {
            return;
        }
        Writer writer = getWriter();
        writer.write("    ");
        writer.write("resource");
        writer.write(" ");
        writer.write(HexUtil.toHex8(resourceEntry.getResourceId()));
        writer.write(" ");
        writer.write(resourceEntry.getType());
        writer.write("/");
        writer.write(resourceEntry.getName());
        writer.write("\n");
        if (z) {
            writeEntries(resourceEntry);
        }
        writer.flush();
    }

    public void writeEntries(ResourceEntry resourceEntry) throws IOException {
        Iterator<Entry> it = resourceEntry.iterator();
        while (it.hasNext()) {
            writeEntry(it.next());
        }
    }

    public void writeEntry(Entry entry) throws IOException {
        Writer writer = getWriter();
        writer.write("      (");
        writer.write(entry.getResConfig().getQualifiers());
        writer.write(") ");
        if (entry.isComplex()) {
            writeBagEntry(entry);
        } else {
            writeResEntry(entry);
        }
        writer.flush();
    }

    private void writeResEntry(Entry entry) throws IOException {
        ResValue resValue = entry.getResValue();
        if (resValue == null) {
            return;
        }
        Writer writer = getWriter();
        writer.write(getValueAsString(resValue));
        writer.write("\n");
    }

    private void writeBagEntry(Entry entry) throws IOException {
        Writer writer = getWriter();
        ResValueMapArray resValueMapArray = entry.getResValueMapArray();
        writer.write(" size=");
        writer.write(Integer.toString(resValueMapArray.getCount()));
        writer.write(" parent=");
        writer.write(HexUtil.toHex8(((ResTableMapEntry) entry.getTableEntry()).getParentId()));
        writer.write("\n");
        Iterator it = resValueMapArray.getChildes().iterator();
        while (it.hasNext()) {
            writeValueMap((ResValueMap) it.next());
        }
    }

    private void writeValueMap(ResValueMap resValueMap) throws IOException {
        Writer writer = getWriter();
        writer.write("        ");
        String strDecodeName = resValueMap.decodeName();
        if (strDecodeName != null) {
            writer.write(strDecodeName);
            writer.write("(");
        }
        writer.write(HexUtil.toHex8(resValueMap.getNameId()));
        if (strDecodeName != null) {
            writer.write(Constant.AFTER_QUTO);
        }
        writer.write(ContainerUtils.KEY_VALUE_DELIMITER);
        writer.write(getValueAsString(resValueMap));
        writer.write("\n");
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writePackageNames(Collection<PackageBlock> collection) throws IOException {
        if (collection == null || collection.size() == 0) {
            return;
        }
        Writer writer = getWriter();
        writer.write("resource-packages");
        writer.write("  [ count ");
        writer.write(Integer.toString(collection.size()));
        writer.write("]");
        writer.write("\n");
        for (PackageBlock packageBlock : collection) {
            writer.write(ARRAY_TAB);
            writer.write(HexUtil.toHex2((byte) packageBlock.getId()));
            writer.write("  \"");
            writer.write(packageBlock.getName());
            writer.write("\"");
            writer.write("\n");
        }
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeEntries(String str, List<Entry> list) throws IOException {
        if (list == null || list.size() == 0) {
            return;
        }
        int i = 0;
        Entry entry = list.get(0);
        Writer writer = getWriter();
        writer.write(str);
        writer.write("  [ configs = ");
        writer.write(Integer.toString(list.size()));
        writer.write(", id=");
        writer.write(HexUtil.toHex8(entry.getResourceId()));
        writer.write(", type=");
        writer.write(entry.getTypeName());
        writer.write(", name=");
        writer.write(entry.getName());
        writer.write(" ]");
        writer.write("\n");
        for (Entry entry2 : list) {
            i++;
            writer.write(ARRAY_TAB);
            writeEntry(entry2);
            writer.write("\n");
            if (i % 3 == 0) {
                writer.flush();
            }
        }
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeArray(String str, Object[] objArr) throws IOException {
        if (objArr == null) {
            return;
        }
        Writer writer = getWriter();
        writer.write(str);
        writer.write("  [ count ");
        writer.write(Integer.toString(objArr.length));
        writer.write("]");
        writer.write("\n");
        String str2 = "%0" + getDecimalPlaces(objArr.length) + "d) ";
        int i = 0;
        for (Object obj : objArr) {
            i++;
            writer.write(ARRAY_TAB);
            writer.write(String.format(str2, Integer.valueOf(i)));
            String string = toString(obj);
            if (string == null) {
                string = "null";
            }
            writer.write(string);
            writer.write("\n");
            if (i % 3 == 0) {
                writer.flush();
            }
        }
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeNameValue(String str, Object obj) throws IOException {
        String string = toString(obj);
        if (string == null) {
            return;
        }
        Writer writer = getWriter();
        writer.write(str);
        writer.write("=\"");
        writer.write(string);
        writer.write("\"");
        writer.write("\n");
        writer.flush();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void flush() throws IOException {
        getWriter().flush();
    }

    private void writeIndent(int i) throws IOException {
        writeSpaces(getWriter(), i);
    }

    private void writeWithTab(Writer writer, String str, String str2) throws IOException {
        for (String str3 : StringsUtil.split(str2, '\n')) {
            writer.write(str);
            writer.write(str3.trim());
            writer.write("\n");
        }
    }

    private static int getDecimalPlaces(int i) {
        int i2 = 0;
        while (i != 0) {
            i2++;
            i /= 10;
        }
        return i2;
    }
}
