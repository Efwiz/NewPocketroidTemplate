package org.apache.xerces.xinclude;

import com.koushikdutta.async.http.body.DocumentBody;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.CharEncoding;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.io.ASCIIReader;
import org.apache.xerces.impl.io.Latin1Reader;
import org.apache.xerces.impl.io.UTF16Reader;
import org.apache.xerces.impl.io.UTF8Reader;
import org.apache.xerces.util.EncodingMap;
import org.apache.xerces.util.HTTPInputSource;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.parser.XMLInputSource;

/* JADX INFO: loaded from: classes13.dex */
public class XIncludeTextReader {
    private XMLErrorReporter fErrorReporter;
    private final XIncludeHandler fHandler;
    private Reader fReader;
    private XMLInputSource fSource;
    private XMLString fTempString;

    public XIncludeTextReader(XMLInputSource xMLInputSource, XIncludeHandler xIncludeHandler, int i) throws IOException {
        this.fTempString = new XMLString();
        this.fHandler = xIncludeHandler;
        this.fSource = xMLInputSource;
        this.fTempString = new XMLString(new char[i + 1], 0, 0);
    }

    private Reader createASCIIReader(InputStream inputStream) {
        return new ASCIIReader(inputStream, this.fTempString.ch.length, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
    }

    private Reader createLatin1Reader(InputStream inputStream) {
        return new Latin1Reader(inputStream, this.fTempString.ch.length);
    }

    private Reader createUTF16Reader(InputStream inputStream, boolean z) {
        return new UTF16Reader(inputStream, this.fTempString.ch.length << 1, z, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
    }

    private Reader createUTF8Reader(InputStream inputStream) {
        return new UTF8Reader(inputStream, this.fTempString.ch.length, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
    }

    public void close() throws IOException {
        Reader reader = this.fReader;
        if (reader != null) {
            reader.close();
            this.fReader = null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:12:0x002e  */
    protected String consumeBOM(InputStream inputStream, String str) throws IOException {
        byte[] bArr = new byte[3];
        inputStream.mark(3);
        if (str.equals("UTF-8")) {
            if (inputStream.read(bArr, 0, 3) == 3) {
                int i = bArr[0] & 255;
                int i2 = bArr[1] & 255;
                int i3 = bArr[2] & 255;
                if (i != 239 || i2 != 187 || i3 != 191) {
                    inputStream.reset();
                }
            } else {
                inputStream.reset();
            }
        } else if (str.startsWith(CharEncoding.UTF_16)) {
            if (inputStream.read(bArr, 0, 2) == 2) {
                int i4 = bArr[0] & 255;
                int i5 = bArr[1] & 255;
                if (i4 == 254 && i5 == 255) {
                    return CharEncoding.UTF_16BE;
                }
                if (i4 == 255 && i5 == 254) {
                    return CharEncoding.UTF_16LE;
                }
            }
            inputStream.reset();
        }
        return str;
    }

    protected String getEncodingName(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[4];
        inputStream.mark(4);
        int i = inputStream.read(bArr, 0, 4);
        inputStream.reset();
        if (i == 4) {
            return getEncodingName(bArr);
        }
        return null;
    }

    protected String getEncodingName(byte[] bArr) {
        int i = bArr[0] & 255;
        int i2 = bArr[1] & 255;
        if (i == 254 && i2 == 255) {
            return CharEncoding.UTF_16BE;
        }
        if (i == 255 && i2 == 254) {
            return CharEncoding.UTF_16LE;
        }
        int i3 = bArr[2] & 255;
        if (i == 239 && i2 == 187 && i3 == 191) {
            return "UTF-8";
        }
        int i4 = bArr[3] & 255;
        if (i == 0 && i2 == 0 && i3 == 0 && i4 == 60) {
            return "ISO-10646-UCS-4";
        }
        if (i == 60 && i2 == 0 && i3 == 0 && i4 == 0) {
            return "ISO-10646-UCS-4";
        }
        if (i == 0 && i2 == 0 && i3 == 60 && i4 == 0) {
            return "ISO-10646-UCS-4";
        }
        if (i == 0 && i2 == 60 && i3 == 0 && i4 == 0) {
            return "ISO-10646-UCS-4";
        }
        if (i == 0 && i2 == 60 && i3 == 0 && i4 == 63) {
            return CharEncoding.UTF_16BE;
        }
        if (i == 60 && i2 == 0 && i3 == 63 && i4 == 0) {
            return CharEncoding.UTF_16LE;
        }
        if (i == 76 && i2 == 111 && i3 == 167 && i4 == 148) {
            return "CP037";
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00ff A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:51:0x0102  */
    /* JADX WARN: Code duplicated, block: B:52:0x0105  */
    /* JADX WARN: Code duplicated, block: B:54:0x010d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:55:0x010f  */
    /* JADX WARN: Code duplicated, block: B:56:0x0111  */
    /* JADX WARN: Code duplicated, block: B:60:0x011f  */
    protected Reader getReader(XMLInputSource xMLInputSource) throws IOException {
        InputStream bufferedInputStream;
        String strTrim;
        String strTrim2;
        if (xMLInputSource.getCharacterStream() != null) {
            return xMLInputSource.getCharacterStream();
        }
        String encoding = xMLInputSource.getEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }
        if (xMLInputSource.getByteStream() != null) {
            bufferedInputStream = xMLInputSource.getByteStream();
            if (!(bufferedInputStream instanceof BufferedInputStream)) {
                bufferedInputStream = new BufferedInputStream(bufferedInputStream, this.fTempString.ch.length);
            }
        } else {
            URLConnection uRLConnectionOpenConnection = new URL(XMLEntityManager.expandSystemId(xMLInputSource.getSystemId(), xMLInputSource.getBaseSystemId(), false)).openConnection();
            if ((uRLConnectionOpenConnection instanceof HttpURLConnection) && (xMLInputSource instanceof HTTPInputSource)) {
                HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                HTTPInputSource hTTPInputSource = (HTTPInputSource) xMLInputSource;
                Iterator hTTPRequestProperties = hTTPInputSource.getHTTPRequestProperties();
                while (hTTPRequestProperties.hasNext()) {
                    Map.Entry entry = (Map.Entry) hTTPRequestProperties.next();
                    httpURLConnection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
                }
                boolean followHTTPRedirects = hTTPInputSource.getFollowHTTPRedirects();
                if (!followHTTPRedirects) {
                    httpURLConnection.setInstanceFollowRedirects(followHTTPRedirects);
                }
            }
            bufferedInputStream = new BufferedInputStream(uRLConnectionOpenConnection.getInputStream());
            String contentType = uRLConnectionOpenConnection.getContentType();
            int iIndexOf = contentType != null ? contentType.indexOf(59) : -1;
            String encodingName = null;
            if (iIndexOf != -1) {
                strTrim = contentType.substring(0, iIndexOf).trim();
                String strTrim3 = contentType.substring(iIndexOf + 1).trim();
                if (strTrim3.startsWith("charset=")) {
                    strTrim2 = strTrim3.substring(8).trim();
                    if ((strTrim2.charAt(0) == '\"' && strTrim2.charAt(strTrim2.length() - 1) == '\"') || (strTrim2.charAt(0) == '\'' && strTrim2.charAt(strTrim2.length() - 1) == '\'')) {
                        strTrim2 = strTrim2.substring(1, strTrim2.length() - 1);
                    }
                }
                if (strTrim.equals("text/xml")) {
                    if (strTrim.equals(DocumentBody.CONTENT_TYPE)) {
                        if (strTrim2 != null) {
                            encodingName = strTrim2;
                        }
                    } else if (strTrim.endsWith("+xml")) {
                    }
                    encodingName = getEncodingName(bufferedInputStream);
                } else if (strTrim2 != null) {
                    encodingName = strTrim2;
                } else {
                    encodingName = CharEncoding.US_ASCII;
                }
                if (encodingName != null) {
                    encoding = encodingName;
                }
            } else {
                strTrim = contentType != null ? contentType.trim() : "";
            }
            strTrim2 = null;
            if (strTrim.equals("text/xml")) {
                if (strTrim.equals(DocumentBody.CONTENT_TYPE)) {
                    if (strTrim2 != null) {
                        encodingName = strTrim2;
                    }
                } else if (strTrim.endsWith("+xml")) {
                }
                encodingName = getEncodingName(bufferedInputStream);
            } else if (strTrim2 != null) {
                encodingName = strTrim2;
            } else {
                encodingName = CharEncoding.US_ASCII;
            }
            if (encodingName != null) {
                encoding = encodingName;
            }
        }
        String strConsumeBOM = consumeBOM(bufferedInputStream, encoding.toUpperCase(Locale.ENGLISH));
        if (strConsumeBOM.equals("UTF-8")) {
            return createUTF8Reader(bufferedInputStream);
        }
        if (strConsumeBOM.equals(CharEncoding.UTF_16BE)) {
            return createUTF16Reader(bufferedInputStream, true);
        }
        if (strConsumeBOM.equals(CharEncoding.UTF_16LE)) {
            return createUTF16Reader(bufferedInputStream, false);
        }
        String iANA2JavaMapping = EncodingMap.getIANA2JavaMapping(strConsumeBOM);
        if (iANA2JavaMapping == null) {
            throw new IOException(this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210").formatMessage(this.fErrorReporter.getLocale(), "EncodingDeclInvalid", new Object[]{strConsumeBOM}));
        }
        if (iANA2JavaMapping.equals("ASCII")) {
            return createASCIIReader(bufferedInputStream);
        }
        return iANA2JavaMapping.equals("ISO8859_1") ? createLatin1Reader(bufferedInputStream) : new InputStreamReader(bufferedInputStream, iANA2JavaMapping);
    }

    protected boolean isValid(int i) {
        return XMLChar.isValid(i);
    }

    public void parse() throws IOException {
        int i;
        int i2;
        Reader reader = getReader(this.fSource);
        this.fReader = reader;
        this.fSource = null;
        int i3 = reader.read(this.fTempString.ch, 0, this.fTempString.ch.length - 1);
        this.fHandler.fHasIncludeReportedContent = true;
        while (i3 != -1) {
            int i4 = 0;
            while (i4 < i3) {
                char c = this.fTempString.ch[i4];
                if (!isValid(c)) {
                    if (XMLChar.isHighSurrogate(c)) {
                        i4++;
                        if (i4 < i3) {
                            i2 = this.fTempString.ch[i4];
                        } else {
                            i = this.fReader.read();
                            if (i != -1) {
                                i2 = i;
                                this.fTempString.ch[i3] = (char) i;
                                i3++;
                                i2 = i;
                            }
                        }
                        i2 = i;
                        if (XMLChar.isLowSurrogate(i2)) {
                            int iSupplemental = XMLChar.supplemental(c, (char) i2);
                            if (!isValid(iSupplemental)) {
                                this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInContent", new Object[]{Integer.toString(iSupplemental, 16)}, (short) 2);
                            }
                        } else {
                            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInContent", new Object[]{Integer.toString(i2, 16)}, (short) 2);
                        }
                    } else {
                        this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInContent", new Object[]{Integer.toString(c, 16)}, (short) 2);
                    }
                }
                i4++;
            }
            if (this.fHandler != null && i3 > 0) {
                this.fTempString.offset = 0;
                this.fTempString.length = i3;
                XIncludeHandler xIncludeHandler = this.fHandler;
                xIncludeHandler.characters(this.fTempString, xIncludeHandler.modifyAugmentations(null, true));
            }
            i3 = this.fReader.read(this.fTempString.ch, 0, this.fTempString.ch.length - 1);
        }
    }

    protected void setBufferSize(int i) {
        int i2 = i + 1;
        if (this.fTempString.ch.length != i2) {
            this.fTempString.ch = new char[i2];
        }
    }

    public void setErrorReporter(XMLErrorReporter xMLErrorReporter) {
        this.fErrorReporter = xMLErrorReporter;
    }

    public void setInputSource(XMLInputSource xMLInputSource) {
        this.fSource = xMLInputSource;
    }
}
