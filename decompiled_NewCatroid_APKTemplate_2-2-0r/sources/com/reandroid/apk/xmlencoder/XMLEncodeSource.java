package com.reandroid.apk.xmlencoder;

import com.reandroid.apk.APKLogger;
import com.reandroid.archive.ByteInputSource;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.xml.ResXmlDocument;
import com.reandroid.utils.CRCDigest;
import com.reandroid.utils.io.IOUtil;
import com.reandroid.xml.source.XMLParserSource;
import java.io.IOException;
import java.io.OutputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes10.dex */
public class XMLEncodeSource extends ByteInputSource {
    private static final byte[] DISPOSED = new byte[0];
    private byte[] array;
    private APKLogger mLogger;
    private final PackageBlock packageBlock;
    private final XMLParserSource parserSource;

    public XMLEncodeSource(PackageBlock packageBlock, XMLParserSource xMLParserSource) {
        super(DISPOSED, xMLParserSource.getPath());
        this.packageBlock = packageBlock;
        this.parserSource = xMLParserSource;
    }

    @Override // com.reandroid.archive.InputSource
    public long getLength() throws IOException {
        return getArray().length;
    }

    @Override // com.reandroid.archive.InputSource
    public long getCrc() throws IOException {
        CRCDigest cRCDigest = new CRCDigest();
        byte[] array = getArray();
        cRCDigest.update(array, 0, array.length);
        return cRCDigest.getValue();
    }

    @Override // com.reandroid.archive.ByteInputSource, com.reandroid.archive.InputSource
    public long write(OutputStream outputStream) throws IOException {
        byte[] array = getArray();
        if (array == DISPOSED) {
            throw new IOException("Disposed source: " + getAlias());
        }
        outputStream.write(array, 0, array.length);
        return array.length;
    }

    @Override // com.reandroid.archive.ByteInputSource
    public byte[] getBytes() {
        try {
            return getArray();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override // com.reandroid.archive.ByteInputSource, com.reandroid.archive.InputSource
    public void disposeInputSource() {
        this.array = DISPOSED;
    }

    private byte[] getArray() throws IOException {
        byte[] bArr = this.array;
        if (bArr != null) {
            return bArr;
        }
        try {
            byte[] bytes = encode().getBytes();
            this.array = bytes;
            return bytes;
        } catch (XmlPullParserException e) {
            throw new IOException(e);
        }
    }

    private ResXmlDocument encode() throws XmlPullParserException, IOException {
        XMLParserSource xMLParserSource = this.parserSource;
        logVerbose("Encoding: " + xMLParserSource.getPath());
        XmlPullParser parser = xMLParserSource.getParser();
        ResXmlDocument resXmlDocument = new ResXmlDocument();
        resXmlDocument.setPackageBlock(this.packageBlock);
        resXmlDocument.parse(parser);
        IOUtil.close(parser);
        return resXmlDocument;
    }

    public void setApkLogger(APKLogger aPKLogger) {
        this.mLogger = aPKLogger;
    }

    private void logVerbose(String str) {
        APKLogger aPKLogger = this.mLogger;
        if (aPKLogger != null) {
            aPKLogger.logVerbose(str);
        }
    }
}
