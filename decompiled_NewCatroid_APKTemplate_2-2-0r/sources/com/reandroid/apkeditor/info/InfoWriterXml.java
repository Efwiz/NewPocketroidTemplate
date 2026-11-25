package com.reandroid.apkeditor.info;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.archive.block.CertificateBlock;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.xml.ResXmlDocument;
import com.reandroid.arsc.chunk.xml.ResXmlNode;
import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.item.StringItem;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.pool.StringPool;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResTableMapEntry;
import com.reandroid.arsc.value.ResValue;
import com.reandroid.arsc.value.ResValueMap;
import com.reandroid.dex.model.DexFile;
import com.reandroid.dex.sections.MapItem;
import com.reandroid.dex.sections.MapList;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.ComputeList;
import com.reandroid.xml.XMLUtil;
import com.reandroid.xml.kxml2.KXmlSerializer;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes10.dex */
public class InfoWriterXml extends InfoWriter {
    private static final int INDENT = 3;
    private static final String TAG_INFO = "info";
    private int mIndent;
    private KXmlSerializer mSerializer;

    public InfoWriterXml(Writer writer) {
        super(writer);
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeStringPool(String str, StringPool<?> stringPool) throws IOException {
        KXmlSerializer serializer = getSerializer();
        int i = this.mIndent + 2;
        this.mIndent = i;
        writeIndent(serializer, i);
        int i2 = this.mIndent;
        int i3 = i2 + 2;
        this.mIndent = i3;
        serializer.startTag(null, "string-pool");
        serializer.attribute(null, FirebaseAnalytics.Param.SOURCE, str);
        serializer.attribute(null, "count", Integer.toString(stringPool.size()));
        serializer.attribute(null, "styles", Integer.toString(stringPool.countStyles()));
        serializer.attribute(null, "sorted", String.valueOf(stringPool.getHeaderBlock().isSorted()));
        serializer.attribute(null, "utf8", String.valueOf(stringPool.isUtf8()));
        serializer.attribute(null, "bytes", String.valueOf(stringPool.getHeaderBlock().getChunkSize()));
        int size = stringPool.size();
        for (int i4 = 0; i4 < size; i4++) {
            StringItem stringItem = stringPool.get(i4);
            writeIndent(serializer, i3);
            serializer.startTag(null, "item");
            serializer.text(stringItem.get());
            serializer.endTag(null, "item");
        }
        writeIndent(serializer, i2);
        serializer.endTag(null, "string-pool");
        serializer.flush();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeXmlDocument(String str, ResXmlDocument resXmlDocument) throws IOException {
        KXmlSerializer serializer = getSerializer();
        serializer.flush();
        Writer writer = getWriter();
        writer.write("\n");
        serializer.startTag(null, "document");
        serializer.attribute(null, "source-path", str);
        XmlSerializer xmlSerializerNewSerializer = newSerializer();
        Iterator<ResXmlNode> it = resXmlDocument.iterator();
        while (it.hasNext()) {
            it.next().serialize(xmlSerializerNewSerializer, false);
        }
        xmlSerializerNewSerializer.flush();
        writer.write("\n");
        serializer.endTag(null, "document");
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeCertificates(List<CertificateBlock> list, final boolean z) throws IOException {
        writeArray("certificates", new ComputeList(list, new Function() { // from class: com.reandroid.apkeditor.info.InfoWriterXml$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return InfoWriterXml.lambda$writeCertificates$0(z, (CertificateBlock) obj);
            }
        }).toArray());
    }

    static /* synthetic */ String lambda$writeCertificates$0(boolean z, CertificateBlock certificateBlock) {
        String strPrintCertificate = certificateBlock.printCertificate();
        return z ? strPrintCertificate + "\n" + toBase64(certificateBlock.getCertificateBytes()) : strPrintCertificate;
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeDexInfo(DexFile dexFile, boolean z) throws IOException {
        KXmlSerializer serializer = getSerializer();
        int i = this.mIndent + 2;
        this.mIndent = i;
        writeIndent(serializer, i);
        int i2 = this.mIndent + 2;
        this.mIndent = i2;
        serializer.startTag(null, "dex");
        serializer.attribute(null, "name", dexFile.getFileName());
        serializer.attribute(null, "version", Integer.toString(dexFile.getVersion()));
        writeArray("markers", CollectionUtil.toList(dexFile.getMarkers()).toArray());
        MapList mapList = dexFile.getDexLayout().getMapList();
        writeIndent(serializer, i2);
        serializer.startTag(null, "dex-sections");
        int i3 = this.mIndent + 2;
        this.mIndent = i3;
        for (MapItem mapItem : mapList) {
            writeIndent(serializer, i3);
            serializer.startTag(null, "section");
            serializer.attribute(null, "name", mapItem.getSectionType().getName());
            serializer.attribute(null, "count", Integer.toString(mapItem.getCountValue()));
            serializer.attribute(null, TypedValues.CycleType.S_WAVE_OFFSET, Integer.toString(mapItem.getOffsetValue()));
            serializer.endTag(null, "section");
        }
        int i4 = this.mIndent - 2;
        this.mIndent = i4;
        writeIndent(serializer, i4);
        serializer.endTag(null, "dex-sections");
        int i5 = this.mIndent - 2;
        this.mIndent = i5;
        writeIndent(serializer, i5);
        serializer.endTag(null, "dex");
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeResources(PackageBlock packageBlock, List<String> list, boolean z) throws IOException {
        KXmlSerializer serializer = getSerializer();
        int i = this.mIndent;
        int i2 = i + 2;
        this.mIndent = i2;
        writeIndent(serializer, i2);
        serializer.startTag(null, "package");
        serializer.attribute(null, "id", HexUtil.toHex2((byte) packageBlock.getId()));
        serializer.attribute(null, "name", packageBlock.getName());
        packageBlock.sortTypes();
        for (SpecTypePair specTypePair : packageBlock.listSpecTypePairs()) {
            if (contains(specTypePair, list)) {
                writeResources(specTypePair, z);
            }
        }
        writeIndent(serializer, i2);
        this.mIndent = i;
        serializer.endTag(null, "package");
    }

    public void writeResources(SpecTypePair specTypePair, boolean z) throws IOException {
        KXmlSerializer serializer = getSerializer();
        int i = this.mIndent;
        int i2 = i + 2;
        this.mIndent = i2;
        writeIndent(serializer, i2);
        serializer.startTag(null, ModuleXmlParser.TYPE);
        serializer.attribute(null, "name", specTypePair.getTypeName());
        serializer.attribute(null, "id", Integer.toString(specTypePair.getId()));
        serializer.attribute(null, "entryCount", Integer.toString(specTypePair.getHighestEntryCount()));
        Iterator<ResourceEntry> resources = specTypePair.getResources();
        while (resources.hasNext()) {
            writeResources(resources.next(), z);
        }
        writeIndent(serializer, i2);
        this.mIndent = i;
        serializer.endTag(null, ModuleXmlParser.TYPE);
        serializer.flush();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeResources(ResourceEntry resourceEntry, boolean z) throws IOException {
        if (resourceEntry.isEmpty()) {
            return;
        }
        KXmlSerializer serializer = getSerializer();
        int i = this.mIndent;
        int i2 = i + 2;
        this.mIndent = i2;
        writeIndent(serializer, i2);
        serializer.startTag(null, "resource");
        serializer.attribute(null, "id", HexUtil.toHex8(resourceEntry.getResourceId()));
        serializer.attribute(null, ModuleXmlParser.TYPE, resourceEntry.getType());
        serializer.attribute(null, "name", resourceEntry.getName());
        if (z) {
            writeEntries(resourceEntry);
            writeIndent(serializer, i2);
        }
        this.mIndent = i;
        serializer.endTag(null, "resource");
        serializer.flush();
    }

    public void writeEntries(ResourceEntry resourceEntry) throws IOException {
        Iterator<Entry> it = resourceEntry.iterator();
        while (it.hasNext()) {
            writeEntry(it.next());
        }
    }

    public void writeEntry(Entry entry) throws IOException {
        KXmlSerializer serializer = getSerializer();
        int i = this.mIndent;
        int i2 = i + 2;
        this.mIndent = i2;
        writeIndent(serializer, i2);
        serializer.startTag(null, "config");
        serializer.attribute(null, "qualifiers", entry.getResConfig().getQualifiers());
        if (entry.isComplex()) {
            writeBagEntry(entry);
        } else {
            writeResEntry(entry);
        }
        writeIndent(serializer, i2);
        serializer.endTag(null, "config");
        this.mIndent = i;
    }

    private void writeResEntry(Entry entry) throws IOException {
        ResValue resValue = entry.getResValue();
        if (resValue == null) {
            return;
        }
        KXmlSerializer serializer = getSerializer();
        int i = this.mIndent;
        int i2 = i + 2;
        this.mIndent = i2;
        writeIndent(serializer, i2);
        serializer.startTag(null, "value");
        serializer.attribute(null, ModuleXmlParser.TYPE, resValue.getValueType().name());
        serializer.text(getValueAsString(resValue));
        serializer.endTag(null, "value");
        this.mIndent = i;
    }

    private void writeBagEntry(Entry entry) throws IOException {
        KXmlSerializer serializer = getSerializer();
        int i = this.mIndent;
        int i2 = i + 2;
        this.mIndent = i2;
        writeIndent(serializer, i2);
        serializer.startTag(null, "bag");
        serializer.attribute(null, "parent", HexUtil.toHex8(((ResTableMapEntry) entry.getTableEntry()).getParentId()));
        Iterator it = entry.getResValueMapArray().getChildes().iterator();
        while (it.hasNext()) {
            writeValueMap((ResValueMap) it.next());
        }
        writeIndent(serializer, i2);
        serializer.endTag(null, "bag");
        this.mIndent = i;
    }

    private void writeValueMap(ResValueMap resValueMap) throws IOException {
        KXmlSerializer serializer = getSerializer();
        int i = this.mIndent;
        int i2 = i + 2;
        this.mIndent = i2;
        writeIndent(serializer, i2);
        serializer.startTag(null, "value");
        serializer.attribute(null, "name", HexUtil.toHex8(resValueMap.getNameId()));
        serializer.attribute(null, ModuleXmlParser.TYPE, resValueMap.getValueType().name());
        serializer.text(getValueAsString(resValueMap));
        serializer.endTag(null, "value");
        this.mIndent = i;
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writePackageNames(Collection<PackageBlock> collection) throws IOException {
        if (collection == null || collection.size() == 0) {
            return;
        }
        KXmlSerializer serializer = getSerializer();
        writeIndent(serializer, 3);
        serializer.startTag(null, "resource-packages");
        serializer.attribute(null, "count", Integer.toString(collection.size()));
        for (PackageBlock packageBlock : collection) {
            writeIndent(serializer, 5);
            serializer.startTag(null, "package");
            serializer.attribute(null, "id", HexUtil.toHex2((byte) packageBlock.getId()));
            serializer.attribute(null, "name", packageBlock.getName());
            serializer.endTag(null, "package");
        }
        writeIndent(serializer, 3);
        serializer.endTag(null, "resource-packages");
        serializer.flush();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeEntries(String str, List<Entry> list) throws IOException {
        if (list == null || list.size() == 0) {
            return;
        }
        Entry entry = list.get(0);
        KXmlSerializer serializer = getSerializer();
        writeIndent(serializer, 3);
        serializer.startTag(null, str);
        serializer.attribute(null, "count", Integer.toString(list.size()));
        serializer.attribute(null, "id", HexUtil.toHex8(entry.getResourceId()));
        serializer.attribute(null, ModuleXmlParser.TYPE, entry.getTypeName());
        serializer.attribute(null, "name", entry.getName());
        Iterator<Entry> it = list.iterator();
        while (it.hasNext()) {
            writeEntry(it.next());
        }
        writeIndent(serializer, 3);
        serializer.endTag(null, str);
        serializer.flush();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeArray(String str, Object[] objArr) throws IOException {
        if (objArr == null) {
            return;
        }
        int i = this.mIndent;
        if (i == 0) {
            i = 3;
        }
        KXmlSerializer serializer = getSerializer();
        writeIndent(serializer, i);
        serializer.startTag(null, str);
        serializer.attribute(null, "count", Integer.toString(objArr.length));
        int i2 = i + 2;
        for (Object obj : objArr) {
            String string = toString(obj);
            if (string == null) {
                string = "";
            }
            writeIndent(serializer, i2);
            serializer.startTag(null, "item");
            serializer.text(string);
            serializer.endTag(null, "item");
        }
        writeIndent(serializer, i);
        serializer.endTag(null, str);
        serializer.flush();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeNameValue(String str, Object obj) throws IOException {
        String string = toString(obj);
        if (string == null) {
            return;
        }
        KXmlSerializer serializer = getSerializer();
        writeIndent(serializer, 3);
        serializer.startTag(null, str);
        serializer.text(string);
        serializer.endTag(null, str);
        serializer.flush();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void flush() throws IOException {
        KXmlSerializer kXmlSerializer = this.mSerializer;
        if (kXmlSerializer != null) {
            writeIndent(kXmlSerializer, 0);
            kXmlSerializer.endTag(null, "info");
            kXmlSerializer.endDocument();
            writeIndent(kXmlSerializer, 0);
            kXmlSerializer.flush();
        }
    }

    private void writeIndent(KXmlSerializer kXmlSerializer, int i) throws IOException {
        StringBuilder sb = new StringBuilder("\n");
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(StringUtil.SPACE);
        }
        kXmlSerializer.text(sb.toString());
    }

    private KXmlSerializer getSerializer() throws IOException {
        KXmlSerializer kXmlSerializer = this.mSerializer;
        if (kXmlSerializer != null) {
            return kXmlSerializer;
        }
        KXmlSerializer kXmlSerializer2 = new KXmlSerializer();
        kXmlSerializer2.setOutput(getWriter());
        kXmlSerializer2.startDocument("utf-8", null);
        writeIndent(kXmlSerializer2, 0);
        kXmlSerializer2.startTag(null, "info");
        this.mSerializer = kXmlSerializer2;
        return kXmlSerializer2;
    }

    private XmlSerializer newSerializer() throws IOException {
        KXmlSerializer kXmlSerializer = this.mSerializer;
        if (kXmlSerializer != null) {
            kXmlSerializer.flush();
        }
        Writer writer = getWriter();
        writer.flush();
        KXmlSerializer kXmlSerializer2 = new KXmlSerializer();
        kXmlSerializer2.setOutput(writer);
        XMLUtil.setFeatureSafe((XmlSerializer) kXmlSerializer2, XMLUtil.FEATURE_INDENT_OUTPUT, true);
        return kXmlSerializer2;
    }
}
