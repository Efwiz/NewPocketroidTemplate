package org.eclipse.jdt.internal.compiler.tool;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import javax.tools.FileObject;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes14.dex */
public final class Util {
    public static String LINE_SEPARATOR = System.getProperty(SystemProperties.LINE_SEPARATOR);

    public static class EncodingError {
        int length;
        int position;

        public EncodingError(int i, int i2) {
            this.position = i;
            this.length = i2;
        }

        public String getSource(char[] cArr) {
            char c;
            char c2;
            int i = this.position;
            int i2 = (this.length + i) - 1;
            if (i <= i2) {
                if ((i < 0 && i2 < 0) || cArr.length == 0) {
                    return "No source available";
                }
                StringBuffer stringBuffer = new StringBuffer("\t");
                int length = cArr.length;
                int i3 = i >= length ? length - 1 : i;
                while (i3 > 0 && (c2 = cArr[i3 - 1]) != '\n' && c2 != '\r') {
                    i3--;
                }
                int i4 = i2 >= length ? length - 1 : i2;
                while (true) {
                    int i5 = i4 + 1;
                    if (i5 >= length || (c = cArr[i5]) == '\r' || c == '\n') {
                        break;
                    }
                    i4 = i5;
                }
                while (true) {
                    char c3 = cArr[i3];
                    if (c3 != ' ' && c3 != '\t') {
                        break;
                    }
                    i3++;
                }
                stringBuffer.append(cArr, i3, (i4 - i3) + 1);
                stringBuffer.append(Util.LINE_SEPARATOR).append("\t");
                while (i3 < i) {
                    stringBuffer.append(cArr[i3] == '\t' ? '\t' : ' ');
                    i3++;
                }
                while (true) {
                    if (i <= (i2 >= length ? length - 1 : i2)) {
                        stringBuffer.append(org.eclipse.jdt.internal.compiler.util.Util.C_EXCEPTION_START);
                        i++;
                    } else {
                        return stringBuffer.toString();
                    }
                }
            } else {
                return "No source available";
            }
        }
    }

    public static class EncodingErrorCollector {
        String encoding;
        ArrayList<EncodingError> encodingErrors = new ArrayList<>();
        FileObject fileObject;

        public EncodingErrorCollector(FileObject fileObject, String str) {
            this.fileObject = fileObject;
            this.encoding = str;
        }

        public void collect(int i, int i2) {
            this.encodingErrors.add(new EncodingError(i, i2));
        }

        public void reportAllEncodingErrors(String str) {
            char[] charArray = str.toCharArray();
            for (EncodingError encodingError : this.encodingErrors) {
                System.err.println(String.valueOf(this.fileObject.getName()) + " Unmappable character for encoding " + this.encoding);
                System.err.println(encodingError.getSource(charArray));
            }
        }
    }

    public static char[] getInputStreamAsCharArray(InputStream inputStream, int i, String str) throws IOException {
        try {
            CharsetDecoder charsetDecoderNewDecoder = Charset.forName(str).newDecoder();
            charsetDecoderNewDecoder.onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
            byte[] inputStreamAsByteArray = org.eclipse.jdt.internal.compiler.util.Util.getInputStreamAsByteArray(inputStream, i);
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(inputStreamAsByteArray.length);
            byteBufferAllocate.put(inputStreamAsByteArray);
            byteBufferAllocate.flip();
            return charsetDecoderNewDecoder.decode(byteBufferAllocate).array();
        } catch (IllegalCharsetNameException unused) {
            System.err.println("Illegal charset name : " + str);
            return null;
        } catch (UnsupportedCharsetException unused2) {
            System.err.println("Unsupported charset : " + str);
            return null;
        }
    }

    public static CharSequence getCharContents(FileObject fileObject, boolean z, byte[] bArr, String str) throws IOException {
        EncodingErrorCollector encodingErrorCollector = null;
        if (bArr == null) {
            return null;
        }
        try {
            CharsetDecoder charsetDecoderNewDecoder = Charset.forName(str).newDecoder();
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArr.length);
            byteBufferAllocate.put(bArr);
            byteBufferAllocate.flip();
            if (z) {
                charsetDecoderNewDecoder.onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
                return charsetDecoderNewDecoder.decode(byteBufferAllocate);
            }
            charsetDecoderNewDecoder.onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
            CharBuffer charBufferAllocate = CharBuffer.allocate(bArr.length);
            String strReplacement = charsetDecoderNewDecoder.replacement();
            int length = strReplacement.length();
            while (true) {
                CoderResult coderResultDecode = charsetDecoderNewDecoder.decode(byteBufferAllocate, charBufferAllocate, true);
                if (coderResultDecode.isMalformed() || coderResultDecode.isUnmappable()) {
                    if (encodingErrorCollector == null) {
                        encodingErrorCollector = new EncodingErrorCollector(fileObject, str);
                    }
                    reportEncodingError(encodingErrorCollector, charBufferAllocate.position(), coderResultDecode.length());
                    if (charBufferAllocate.position() + length >= charBufferAllocate.capacity()) {
                        CharBuffer charBufferAllocate2 = CharBuffer.allocate(charBufferAllocate.capacity() * 2);
                        charBufferAllocate.flip();
                        charBufferAllocate2.put(charBufferAllocate);
                        charBufferAllocate = charBufferAllocate2;
                    }
                    charBufferAllocate.append((CharSequence) strReplacement);
                    byteBufferAllocate.position(byteBufferAllocate.position() + coderResultDecode.length());
                } else {
                    if (!coderResultDecode.isOverflow()) {
                        break;
                    }
                    CharBuffer charBufferAllocate3 = CharBuffer.allocate(charBufferAllocate.capacity() * 2);
                    charBufferAllocate.flip();
                    charBufferAllocate3.put(charBufferAllocate);
                    charBufferAllocate = charBufferAllocate3;
                }
            }
            charBufferAllocate.flip();
            if (encodingErrorCollector != null) {
                encodingErrorCollector.reportAllEncodingErrors(charBufferAllocate.toString());
            }
            return charBufferAllocate;
        } catch (IllegalCharsetNameException unused) {
            System.err.println("Illegal charset name : " + str);
            return null;
        } catch (UnsupportedCharsetException unused2) {
            System.err.println("Unsupported charset : " + str);
            return null;
        }
    }

    private static void reportEncodingError(EncodingErrorCollector encodingErrorCollector, int i, int i2) {
        encodingErrorCollector.collect(i, -i2);
    }
}
