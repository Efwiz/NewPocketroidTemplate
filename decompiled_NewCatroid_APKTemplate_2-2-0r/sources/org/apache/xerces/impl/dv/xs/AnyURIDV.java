package org.apache.xerces.impl.dv.xs;

import io.netty.util.internal.StringUtil;
import java.io.UnsupportedEncodingException;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.util.URI;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes13.dex */
public class AnyURIDV extends TypeValidator {
    private static final URI BASE_URI;
    private static char[] gAfterEscaping1;
    private static char[] gAfterEscaping2;
    private static char[] gHexChs;
    private static boolean[] gNeedEscaping;

    static {
        URI uri;
        try {
            uri = new URI("abc://def.ghi.jkl");
        } catch (URI.MalformedURIException unused) {
            uri = null;
        }
        BASE_URI = uri;
        gNeedEscaping = new boolean[128];
        gAfterEscaping1 = new char[128];
        gAfterEscaping2 = new char[128];
        gHexChs = new char[]{ExternalAnnotationProvider.NULLABLE, ExternalAnnotationProvider.NONNULL, '2', '3', '4', '5', '6', '7', '8', '9', 'A', Util.C_BYTE, Util.C_CHAR, Util.C_DOUBLE, 'E', Util.C_FLOAT};
        for (int i = 0; i <= 31; i++) {
            gNeedEscaping[i] = true;
            char[] cArr = gAfterEscaping1;
            char[] cArr2 = gHexChs;
            cArr[i] = cArr2[i >> 4];
            gAfterEscaping2[i] = cArr2[i & 15];
        }
        gNeedEscaping[127] = true;
        gAfterEscaping1[127] = '7';
        gAfterEscaping2[127] = Util.C_FLOAT;
        char[] cArr3 = {StringUtil.SPACE, '<', '>', '\"', '{', '}', '|', '\\', Util.C_EXCEPTION_START, '~', '`'};
        for (int i2 = 0; i2 < 11; i2++) {
            char c = cArr3[i2];
            gNeedEscaping[c] = true;
            char[] cArr4 = gAfterEscaping1;
            char[] cArr5 = gHexChs;
            cArr4[c] = cArr5[c >> 4];
            gAfterEscaping2[c] = cArr5[c & 15];
        }
    }

    private static String encode(String str) {
        char c;
        char cCharAt;
        char c2;
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length * 3);
        int i = 0;
        while (i < length && (cCharAt = str.charAt(i)) < 128) {
            if (gNeedEscaping[cCharAt]) {
                stringBuffer.append(MangleConstant.PLATFORM_FUNCTION_MARKER);
                stringBuffer.append(gAfterEscaping1[cCharAt]);
                c2 = gAfterEscaping2[cCharAt];
            } else {
                c2 = cCharAt;
            }
            stringBuffer.append(c2);
            i++;
        }
        if (i < length) {
            try {
                byte[] bytes = str.substring(i).getBytes("UTF-8");
                int length2 = bytes.length;
                for (byte b : bytes) {
                    if (b < 0) {
                        int i2 = b + 256;
                        stringBuffer.append(MangleConstant.PLATFORM_FUNCTION_MARKER);
                        stringBuffer.append(gHexChs[i2 >> 4]);
                        c = gHexChs[i2 & 15];
                    } else if (gNeedEscaping[b]) {
                        stringBuffer.append(MangleConstant.PLATFORM_FUNCTION_MARKER);
                        stringBuffer.append(gAfterEscaping1[b]);
                        c = gAfterEscaping2[b];
                    } else {
                        c = (char) b;
                    }
                    stringBuffer.append(c);
                }
                length = length2;
            } catch (UnsupportedEncodingException unused) {
                return str;
            }
        }
        return stringBuffer.length() != length ? stringBuffer.toString() : str;
    }

    @Override // org.apache.xerces.impl.dv.xs.TypeValidator
    public Object getActualValue(String str, ValidationContext validationContext) throws InvalidDatatypeValueException {
        try {
            if (str.length() != 0) {
                new URI(BASE_URI, encode(str));
            }
            return str;
        } catch (URI.MalformedURIException unused) {
            throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{str, SchemaSymbols.ATTVAL_ANYURI});
        }
    }

    @Override // org.apache.xerces.impl.dv.xs.TypeValidator
    public short getAllowedFacets() {
        return (short) 2079;
    }
}
