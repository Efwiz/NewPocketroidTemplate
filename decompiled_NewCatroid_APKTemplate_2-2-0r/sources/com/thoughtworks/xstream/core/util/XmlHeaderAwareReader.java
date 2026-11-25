package com.thoughtworks.xstream.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import org.catrobat.catroid.BuildConfig;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/XmlHeaderAwareReader.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public final class XmlHeaderAwareReader extends Reader {
    private static final String KEY_ENCODING = "encoding";
    private static final String KEY_VERSION = "version";
    private static final int STATE_ATTR_NAME = 3;
    private static final int STATE_ATTR_VALUE = 4;
    private static final int STATE_AWAIT_XML_HEADER = 2;
    private static final int STATE_BOM = 0;
    private static final int STATE_START = 1;
    private static final String XML_TOKEN = "?xml";
    private final InputStreamReader reader;
    private final double version;

    public XmlHeaderAwareReader(InputStream inputStream) throws IOException {
        PushbackInputStream[] pushbackInputStreamArr = new PushbackInputStream[1];
        pushbackInputStreamArr[0] = inputStream instanceof PushbackInputStream ? (PushbackInputStream) inputStream : new PushbackInputStream(inputStream, 64);
        Map header = getHeader(pushbackInputStreamArr);
        this.version = Double.parseDouble((String) header.get("version"));
        this.reader = new InputStreamReader(pushbackInputStreamArr[0], (String) header.get("encoding"));
    }

    /* JADX WARN: Code duplicated, block: B:103:0x0102 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:118:0x0105 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:125:0x00fe A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:134:0x0029 A[SYNTHETIC] */
    private Map getHeader(PushbackInputStream[] pushbackInputStreamArr) throws IOException {
        HashMap map = new HashMap();
        map.put("encoding", "utf-8");
        map.put("version", BuildConfig.VERSION_NAME);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(64);
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        int i = 0;
        char c = 0;
        char c2 = 0;
        boolean z = false;
        while (i != -1 && (i = pushbackInputStreamArr[0].read()) != -1) {
            byteArrayOutputStream.write(i);
            char c3 = (char) i;
            char c4 = 2;
            if (c != 0) {
                if (c != 1) {
                    if (c != 2) {
                        c4 = 4;
                        if (c != 3) {
                            if (c == 4) {
                                if (c2 == 0) {
                                    if (c3 == '\"' || c3 == '\'') {
                                        c2 = c3;
                                    } else {
                                        i = -1;
                                    }
                                } else if (c3 == '\\' && !z) {
                                    z = true;
                                } else if (c3 == c2 && !z) {
                                    map.put(stringBuffer.toString(), stringBuffer2.toString());
                                    stringBuffer.setLength(0);
                                    stringBuffer2.setLength(0);
                                    c2 = 0;
                                    c = 3;
                                } else if (c3 != '\n') {
                                    stringBuffer2.append(c3);
                                    z = false;
                                } else {
                                    z = false;
                                    i = -1;
                                }
                            }
                        } else if (Character.isWhitespace(c3)) {
                            if (stringBuffer.length() > 0) {
                                i = -1;
                            }
                        } else if (c3 == '=') {
                            c = c4;
                        } else {
                            char lowerCase = Character.toLowerCase(c3);
                            if (Character.isLetter(lowerCase)) {
                                stringBuffer.append(lowerCase);
                            } else {
                                i = -1;
                            }
                        }
                    } else {
                        if (!Character.isWhitespace(c3)) {
                            stringBuffer.append(Character.toLowerCase(c3));
                            if (!XML_TOKEN.startsWith(stringBuffer.substring(0))) {
                            }
                        } else if (stringBuffer.toString().equals(XML_TOKEN)) {
                            stringBuffer.setLength(0);
                            c = 3;
                        }
                        i = -1;
                    }
                } else if (Character.isWhitespace(c3)) {
                    if (c3 == '<') {
                        c = c4;
                    } else {
                        i = -1;
                    }
                }
            } else if ((c3 != 239 || byteArrayOutputStream.size() != 1) && ((c3 != 187 || byteArrayOutputStream.size() != 2) && (c3 != 191 || byteArrayOutputStream.size() != 3))) {
                if (byteArrayOutputStream.size() <= 1) {
                    c = 1;
                    if (Character.isWhitespace(c3)) {
                        if (c3 == '<') {
                            c = c4;
                        }
                    }
                }
                i = -1;
            } else if (c3 == 191) {
                byteArrayOutputStream.reset();
                c = 1;
            }
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        int length = byteArray.length;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return map;
            }
            try {
                pushbackInputStreamArr[0].unread(byteArray[i2]);
                length = i2;
            } catch (IOException unused) {
                pushbackInputStreamArr[0] = new PushbackInputStream(pushbackInputStreamArr[0], length);
            }
        }
    }

    public String getEncoding() {
        return this.reader.getEncoding();
    }

    public double getVersion() {
        return this.version;
    }

    @Override // java.io.Reader
    public void mark(int i) throws IOException {
        this.reader.mark(i);
    }

    @Override // java.io.Reader
    public boolean markSupported() {
        return this.reader.markSupported();
    }

    @Override // java.io.Reader
    public int read() throws IOException {
        return this.reader.read();
    }

    @Override // java.io.Reader
    public int read(char[] cArr, int i, int i2) throws IOException {
        return this.reader.read(cArr, i, i2);
    }

    @Override // java.io.Reader
    public int read(char[] cArr) throws IOException {
        return this.reader.read(cArr);
    }

    @Override // java.io.Reader
    public boolean ready() throws IOException {
        return this.reader.ready();
    }

    @Override // java.io.Reader
    public void reset() throws IOException {
        this.reader.reset();
    }

    @Override // java.io.Reader
    public long skip(long j) throws IOException {
        return this.reader.skip(j);
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.reader.close();
    }

    public boolean equals(Object obj) {
        return this.reader.equals(obj);
    }

    public int hashCode() {
        return this.reader.hashCode();
    }

    public String toString() {
        return this.reader.toString();
    }
}
