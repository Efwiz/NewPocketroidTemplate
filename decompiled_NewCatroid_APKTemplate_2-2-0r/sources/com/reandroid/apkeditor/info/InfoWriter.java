package com.reandroid.apkeditor.info;

import com.reandroid.archive.block.ApkSignatureBlock;
import com.reandroid.archive.block.CertificateBlock;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.xml.ResXmlDocument;
import com.reandroid.arsc.coder.ValueCoder;
import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.pool.StringPool;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.Value;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.dex.model.DexDirectory;
import com.reandroid.dex.model.DexFile;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.collection.CollectionUtil;
import io.netty.util.internal.StringUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil;

/* JADX INFO: loaded from: classes10.dex */
public abstract class InfoWriter implements Closeable {
    static final String NAME_QUALIFIERS = "qualifiers";
    static final String NAME_RESOURCE = "resource";
    static final String TAG_BAG = "bag";
    static final String TAG_CONFIG = "config";
    static final String TAG_ITEM = "item";
    static final String TAG_PUBLIC = "public";
    static final String TAG_RESOURCES = "resources";
    static final String TAG_RES_PACKAGES = "resource-packages";
    static final String TAG_VALUE = "value";
    private final Writer writer;

    public abstract void flush() throws IOException;

    public abstract void writeArray(String str, Object[] objArr) throws IOException;

    public abstract void writeCertificates(List<CertificateBlock> list, boolean z) throws IOException;

    public abstract void writeDexInfo(DexFile dexFile, boolean z) throws IOException;

    public abstract void writeEntries(String str, List<Entry> list) throws IOException;

    public abstract void writeNameValue(String str, Object obj) throws IOException;

    public abstract void writePackageNames(Collection<PackageBlock> collection) throws IOException;

    public abstract void writeResources(ResourceEntry resourceEntry, boolean z) throws IOException;

    public abstract void writeStringPool(String str, StringPool<?> stringPool) throws IOException;

    public abstract void writeXmlDocument(String str, ResXmlDocument resXmlDocument) throws IOException;

    public InfoWriter(Writer writer) {
        this.writer = writer;
    }

    public void writeSignatureInfo(ApkSignatureBlock apkSignatureBlock, boolean z) throws IOException {
        if (apkSignatureBlock == null) {
            writeNameValue("certificates", "null");
        } else {
            writeCertificates(CollectionUtil.toList(apkSignatureBlock.getCertificates()), z);
        }
    }

    public void writeResources(PackageBlock packageBlock, List<String> list, boolean z) throws IOException {
        Iterator<ResourceEntry> resources = packageBlock.getResources();
        while (resources.hasNext()) {
            writeResources(resources.next(), z);
        }
    }

    public void writeDexInfo(DexDirectory dexDirectory) throws IOException {
        Iterator<DexFile> it = dexDirectory.iterator();
        while (it.hasNext()) {
            writeDexInfo(it.next(), true);
        }
    }

    boolean contains(SpecTypePair specTypePair, List<String> list) {
        if (list.size() == 0) {
            return true;
        }
        return list.contains(specTypePair.getTypeName());
    }

    public Writer getWriter() {
        return this.writer;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.writer.close();
    }

    static String toString(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    static String getValueAsString(Value value) {
        ValueType valueType = value.getValueType();
        if (valueType == ValueType.STRING) {
            return value.getValueAsString();
        }
        String strDecode = ValueCoder.decode(valueType, value.getData());
        if (strDecode != null) {
            return strDecode;
        }
        if (valueType == ValueType.ATTRIBUTE) {
            return HexUtil.toHex8("?0x", value.getData());
        }
        if (valueType == ValueType.REFERENCE) {
            return HexUtil.toHex8("@0x", value.getData());
        }
        return HexUtil.toHex8(PsiLiteralUtil.HEX_PREFIX, value.getData());
    }

    static String toBase64(byte[] bArr) {
        return Base64.getEncoder().encodeToString(bArr);
    }

    static void writeSpaces(Writer writer, int i) throws IOException {
        for (int i2 = 0; i2 < i; i2++) {
            writer.append(StringUtil.SPACE);
        }
    }
}
