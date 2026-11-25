package com.reandroid.apkeditor.info;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.archive.block.CertificateBlock;
import com.reandroid.arsc.array.ResValueMapArray;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.chunk.xml.ResXmlDocument;
import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.pool.StringPool;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResTableMapEntry;
import com.reandroid.arsc.value.ResValue;
import com.reandroid.arsc.value.ResValueMap;
import com.reandroid.dex.model.DexFile;
import com.reandroid.dex.sections.Marker;
import com.reandroid.json.JSONObject;
import com.reandroid.json.JSONWriter;
import com.reandroid.utils.collection.CollectionUtil;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: loaded from: classes10.dex */
public class InfoWriterJson extends InfoWriter {
    private final JSONWriter mJsonWriter;

    public InfoWriterJson(Writer writer) {
        super(writer);
        this.mJsonWriter = new JSONWriter(writer).array();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeStringPool(String str, StringPool<?> stringPool) throws IOException {
        JSONWriter jSONWriterArray = this.mJsonWriter.object().key("string_pool").object().key(FirebaseAnalytics.Param.SOURCE).value(str).key("count").value(stringPool.size()).key("styles").value(stringPool.countStyles()).key("sorted").value(stringPool.getHeaderBlock().isSorted()).key("utf8").value(stringPool.isUtf8()).key("bytes").value(stringPool.getHeaderBlock().getChunkSize()).key("strings").array();
        int size = stringPool.size();
        for (int i = 0; i < size; i++) {
            jSONWriterArray.value(stringPool.get(i).get());
        }
        jSONWriterArray.endArray().endObject().endObject();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeXmlDocument(String str, ResXmlDocument resXmlDocument) throws IOException {
        JSONWriter jSONWriterObject = this.mJsonWriter.object();
        jSONWriterObject.key("source_path").value(str);
        jSONWriterObject.key("document").value(resXmlDocument.toJson());
        jSONWriterObject.endObject();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeCertificates(List<CertificateBlock> list, boolean z) throws IOException {
        JSONWriter jSONWriterArray = this.mJsonWriter.object().key("certificates").array();
        for (CertificateBlock certificateBlock : list) {
            JSONObject json = certificateBlock.toJson();
            if (z) {
                json.put("base64", toBase64(certificateBlock.getCertificateBytes()));
            }
            jSONWriterArray.value(json);
        }
        jSONWriterArray.endArray().endObject();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeDexInfo(DexFile dexFile, boolean z) throws IOException {
        JSONWriter jSONWriterArray = this.mJsonWriter.object().key("name").value(dexFile.getFileName()).key("version").value(dexFile.getVersion()).key("markers").array();
        Iterator it = CollectionUtil.toList(dexFile.getMarkers()).iterator();
        while (it.hasNext()) {
            jSONWriterArray.value(((Marker) it.next()).getJsonObject());
        }
        jSONWriterArray.endArray().endObject();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeResources(PackageBlock packageBlock, List<String> list, boolean z) throws IOException {
        packageBlock.sortTypes();
        JSONWriter jSONWriterArray = this.mJsonWriter.object().key("id").value(packageBlock.getId()).key("package").value(packageBlock.getName()).key(SpecTypePair.NAME_types).array();
        for (SpecTypePair specTypePair : packageBlock.listSpecTypePairs()) {
            if (contains(specTypePair, list)) {
                writeResources(specTypePair, z);
            }
        }
        jSONWriterArray.endArray().endObject();
    }

    public void writeResources(SpecTypePair specTypePair, boolean z) throws IOException {
        JSONWriter jSONWriterArray = this.mJsonWriter.object().key("id").value(specTypePair.getId()).key(ModuleXmlParser.TYPE).value(specTypePair.getTypeName()).key(TypeBlock.NAME_entries).array();
        Iterator<ResourceEntry> resources = specTypePair.getResources();
        while (resources.hasNext()) {
            writeResources(resources.next(), z);
        }
        jSONWriterArray.endArray().endObject();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeResources(ResourceEntry resourceEntry, boolean z) throws IOException {
        if (resourceEntry.isEmpty()) {
            return;
        }
        JSONWriter jSONWriterValue = this.mJsonWriter.object().key("id").value(resourceEntry.getResourceId()).key(ModuleXmlParser.TYPE).value(resourceEntry.getType()).key("name").value(resourceEntry.getName());
        if (z) {
            jSONWriterValue.key("configs");
            writeEntries(resourceEntry);
        }
        jSONWriterValue.endObject();
    }

    public void writeEntries(ResourceEntry resourceEntry) throws IOException {
        JSONWriter jSONWriterArray = this.mJsonWriter.array();
        Iterator<Entry> it = resourceEntry.iterator();
        while (it.hasNext()) {
            writeEntry(it.next());
        }
        jSONWriterArray.endArray();
    }

    public void writeEntry(Entry entry) throws IOException {
        if (entry.isComplex()) {
            writeBagEntry(entry);
        } else {
            writeResEntry(entry);
        }
    }

    private void writeResEntry(Entry entry) throws IOException {
        ResValue resValue = entry.getResValue();
        if (resValue == null) {
            return;
        }
        this.mJsonWriter.object().key("qualifiers").value(entry.getResConfig().getQualifiers()).key("value").value(getValueAsString(resValue)).endObject();
    }

    private void writeBagEntry(Entry entry) {
        ResValueMapArray resValueMapArray = entry.getResValueMapArray();
        JSONWriter jSONWriterArray = this.mJsonWriter.object().key("qualifiers").value(entry.getResConfig().getQualifiers()).key("size").value(resValueMapArray.size()).key("parent").value(((ResTableMapEntry) entry.getTableEntry()).getParentId()).key("bag").array();
        Iterator it = resValueMapArray.getChildes().iterator();
        while (it.hasNext()) {
            writeValueMap((ResValueMap) it.next());
        }
        jSONWriterArray.endArray().endObject();
    }

    private void writeValueMap(ResValueMap resValueMap) {
        this.mJsonWriter.object().key("name").value(resValueMap.decodeName()).key("id").value(resValueMap.getNameId()).key("value").value(getValueAsString(resValueMap)).endObject();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writePackageNames(Collection<PackageBlock> collection) throws IOException {
        if (collection == null || collection.size() == 0) {
            return;
        }
        JSONWriter jSONWriterArray = this.mJsonWriter.object().key("resource-packages").array();
        for (PackageBlock packageBlock : collection) {
            jSONWriterArray.object().key("id").value(packageBlock.getId()).key("name").value(packageBlock.getName()).endObject();
        }
        jSONWriterArray.endArray().endObject();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeEntries(String str, List<Entry> list) throws IOException {
        if (list == null || list.size() == 0) {
            return;
        }
        Entry entry = list.get(0);
        JSONWriter jSONWriterArray = this.mJsonWriter.object().key("id").value(entry.getResourceId()).key(ModuleXmlParser.TYPE).value(entry.getTypeName()).key("name").value(entry.getName()).key(TypeBlock.NAME_entries).array();
        Iterator<Entry> it = list.iterator();
        while (it.hasNext()) {
            writeEntry(it.next());
        }
        jSONWriterArray.endArray().endObject();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeArray(String str, Object[] objArr) throws IOException {
        JSONWriter jSONWriterArray = this.mJsonWriter.object().key(str).array();
        for (Object obj : objArr) {
            jSONWriterArray.value(obj);
        }
        jSONWriterArray.endArray().endObject();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void writeNameValue(String str, Object obj) throws IOException {
        this.mJsonWriter.object().key(str).value(obj).endObject();
        getWriter().flush();
    }

    @Override // com.reandroid.apkeditor.info.InfoWriter
    public void flush() throws IOException {
        Writer writer = getWriter();
        this.mJsonWriter.endArray();
        writer.write("\n");
        writer.flush();
    }
}
