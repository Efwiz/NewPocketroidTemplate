package org.apache.xerces.util;

import com.beust.jcommander.Parameters;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes13.dex */
public class URI implements Serializable {
    private static final int ASCII_ALPHA_CHARACTERS = 16;
    private static final int ASCII_DIGIT_CHARACTERS = 32;
    private static final int ASCII_HEX_CHARACTERS = 64;
    private static final int MARK_CHARACTERS = 2;
    private static final int MASK_ALPHA_NUMERIC = 48;
    private static final int MASK_PATH_CHARACTER = 178;
    private static final int MASK_SCHEME_CHARACTER = 52;
    private static final int MASK_UNRESERVED_MASK = 50;
    private static final int MASK_URI_CHARACTER = 51;
    private static final int MASK_USERINFO_CHARACTER = 58;
    private static final int PATH_CHARACTERS = 128;
    private static final int RESERVED_CHARACTERS = 1;
    private static final int SCHEME_CHARACTERS = 4;
    private static final int USERINFO_CHARACTERS = 8;
    private static final byte[] fgLookupTable = new byte[128];
    static final long serialVersionUID = 1601921774685357214L;
    private String m_fragment;
    private String m_host;
    private String m_path;
    private int m_port;
    private String m_queryString;
    private String m_regAuthority;
    private String m_scheme;
    private String m_userinfo;

    public static class MalformedURIException extends IOException {
        static final long serialVersionUID = -6695054834342951930L;

        public MalformedURIException() {
        }

        public MalformedURIException(String str) {
            super(str);
        }
    }

    static {
        for (int i = 48; i <= 57; i++) {
            byte[] bArr = fgLookupTable;
            bArr[i] = (byte) (bArr[i] | 96);
        }
        for (int i2 = 65; i2 <= 70; i2++) {
            byte[] bArr2 = fgLookupTable;
            bArr2[i2] = (byte) (bArr2[i2] | 80);
            int i3 = i2 + 32;
            bArr2[i3] = (byte) (bArr2[i3] | 80);
        }
        for (int i4 = 71; i4 <= 90; i4++) {
            byte[] bArr3 = fgLookupTable;
            bArr3[i4] = (byte) (bArr3[i4] | 16);
            int i5 = i4 + 32;
            bArr3[i5] = (byte) (bArr3[i5] | 16);
        }
        byte[] bArr4 = fgLookupTable;
        byte b = (byte) (bArr4[59] | 1);
        bArr4[59] = b;
        byte b2 = (byte) (bArr4[47] | 1);
        bArr4[47] = b2;
        bArr4[63] = (byte) (bArr4[63] | 1);
        byte b3 = (byte) (bArr4[58] | 1);
        bArr4[58] = b3;
        byte b4 = (byte) (bArr4[64] | 1);
        bArr4[64] = b4;
        byte b5 = (byte) (bArr4[38] | 1);
        bArr4[38] = b5;
        byte b6 = (byte) (bArr4[61] | 1);
        bArr4[61] = b6;
        byte b7 = (byte) (bArr4[43] | 1);
        bArr4[43] = b7;
        byte b8 = (byte) (bArr4[36] | 1);
        bArr4[36] = b8;
        byte b9 = (byte) (bArr4[44] | 1);
        bArr4[44] = b9;
        bArr4[91] = (byte) (bArr4[91] | 1);
        bArr4[93] = (byte) (bArr4[93] | 1);
        byte b10 = (byte) (bArr4[45] | 2);
        bArr4[45] = b10;
        bArr4[95] = (byte) (bArr4[95] | 2);
        byte b11 = (byte) (bArr4[46] | 2);
        bArr4[46] = b11;
        bArr4[33] = (byte) (bArr4[33] | 2);
        bArr4[126] = (byte) (bArr4[126] | 2);
        bArr4[42] = (byte) (bArr4[42] | 2);
        bArr4[39] = (byte) (bArr4[39] | 2);
        bArr4[40] = (byte) (bArr4[40] | 2);
        bArr4[41] = (byte) (bArr4[41] | 2);
        byte b12 = (byte) (b7 | 4);
        bArr4[43] = b12;
        bArr4[45] = (byte) (b10 | 4);
        bArr4[46] = (byte) (b11 | 4);
        byte b13 = (byte) (b | 8);
        bArr4[59] = b13;
        byte b14 = (byte) (b3 | 8);
        bArr4[58] = b14;
        byte b15 = (byte) (b5 | 8);
        bArr4[38] = b15;
        byte b16 = (byte) (b6 | 8);
        bArr4[61] = b16;
        byte b17 = (byte) (b12 | 8);
        bArr4[43] = b17;
        byte b18 = (byte) (b8 | 8);
        bArr4[36] = b18;
        byte b19 = (byte) (b9 | 8);
        bArr4[44] = b19;
        bArr4[59] = (byte) (b13 | 128);
        bArr4[47] = (byte) (b2 | 128);
        bArr4[58] = (byte) (b14 | 128);
        bArr4[64] = (byte) (b4 | 128);
        bArr4[38] = (byte) (b15 | 128);
        bArr4[61] = (byte) (b16 | 128);
        bArr4[43] = (byte) (b17 | 128);
        bArr4[36] = (byte) (b18 | 128);
        bArr4[44] = (byte) (b19 | 128);
    }

    public URI() {
        this.m_scheme = null;
        this.m_userinfo = null;
        this.m_host = null;
        this.m_port = -1;
        this.m_regAuthority = null;
        this.m_path = null;
        this.m_queryString = null;
        this.m_fragment = null;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public URI(String str) throws MalformedURIException {
        this((URI) null, str);
    }

    public URI(String str, String str2) throws MalformedURIException {
        this.m_scheme = null;
        this.m_userinfo = null;
        this.m_host = null;
        this.m_port = -1;
        this.m_regAuthority = null;
        this.m_path = null;
        this.m_queryString = null;
        this.m_fragment = null;
        if (str == null || str.trim().length() == 0) {
            throw new MalformedURIException("Cannot construct URI with null/empty scheme!");
        }
        if (str2 == null || str2.trim().length() == 0) {
            throw new MalformedURIException("Cannot construct URI with null/empty scheme-specific part!");
        }
        setScheme(str);
        setPath(str2);
    }

    public URI(String str, String str2, String str3, int i, String str4, String str5, String str6) throws MalformedURIException {
        this.m_scheme = null;
        this.m_userinfo = null;
        this.m_host = null;
        this.m_port = -1;
        this.m_regAuthority = null;
        this.m_path = null;
        this.m_queryString = null;
        this.m_fragment = null;
        if (str == null || str.trim().length() == 0) {
            throw new MalformedURIException("Scheme is required!");
        }
        if (str3 == null) {
            if (str2 != null) {
                throw new MalformedURIException("Userinfo may not be specified if host is not specified!");
            }
            if (i != -1) {
                throw new MalformedURIException("Port may not be specified if host is not specified!");
            }
        }
        if (str4 != null) {
            if (str4.indexOf(63) != -1 && str5 != null) {
                throw new MalformedURIException("Query string cannot be specified in path and query string!");
            }
            if (str4.indexOf(35) != -1 && str6 != null) {
                throw new MalformedURIException("Fragment cannot be specified in both the path and fragment!");
            }
        }
        setScheme(str);
        setHost(str3);
        setPort(i);
        setUserinfo(str2);
        setPath(str4);
        setQueryString(str5);
        setFragment(str6);
    }

    public URI(String str, String str2, String str3, String str4, String str5) throws MalformedURIException {
        this(str, null, str2, -1, str3, str4, str5);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public URI(String str, boolean z) throws MalformedURIException {
        this(null, str, z);
    }

    public URI(URI uri) {
        this.m_scheme = null;
        this.m_userinfo = null;
        this.m_host = null;
        this.m_port = -1;
        this.m_regAuthority = null;
        this.m_path = null;
        this.m_queryString = null;
        this.m_fragment = null;
        initialize(uri);
    }

    public URI(URI uri, String str) throws MalformedURIException {
        this.m_scheme = null;
        this.m_userinfo = null;
        this.m_host = null;
        this.m_port = -1;
        this.m_regAuthority = null;
        this.m_path = null;
        this.m_queryString = null;
        this.m_fragment = null;
        initialize(uri, str);
    }

    public URI(URI uri, String str, boolean z) throws MalformedURIException {
        this.m_scheme = null;
        this.m_userinfo = null;
        this.m_host = null;
        this.m_port = -1;
        this.m_regAuthority = null;
        this.m_path = null;
        this.m_queryString = null;
        this.m_fragment = null;
        initialize(uri, str, z);
    }

    private void initialize(URI uri) {
        this.m_scheme = uri.getScheme();
        this.m_userinfo = uri.getUserinfo();
        this.m_host = uri.getHost();
        this.m_port = uri.getPort();
        this.m_regAuthority = uri.getRegBasedAuthority();
        this.m_path = uri.getPath();
        this.m_queryString = uri.getQueryString();
        this.m_fragment = uri.getFragment();
    }

    private void initialize(URI uri, String str) throws MalformedURIException {
        int length = 0;
        int length2 = str != null ? str.length() : 0;
        if (uri == null && length2 == 0) {
            throw new MalformedURIException("Cannot initialize URI with empty parameters.");
        }
        if (length2 == 0) {
            initialize(uri);
            return;
        }
        int iIndexOf = str.indexOf(58);
        if (iIndexOf != -1) {
            int i = iIndexOf - 1;
            int iLastIndexOf = str.lastIndexOf(47, i);
            int iLastIndexOf2 = str.lastIndexOf(63, i);
            int iLastIndexOf3 = str.lastIndexOf(35, i);
            if (iIndexOf != 0 && iLastIndexOf == -1 && iLastIndexOf2 == -1 && iLastIndexOf3 == -1) {
                initializeScheme(str);
                length = this.m_scheme.length() + 1;
                if (iIndexOf == length2 - 1 || str.charAt(iIndexOf + 1) == '#') {
                    throw new MalformedURIException("Scheme specific part cannot be empty.");
                }
            } else if (iIndexOf == 0 || (uri == null && iLastIndexOf3 != 0)) {
                throw new MalformedURIException("No scheme found in URI.");
            }
        } else if (uri == null && str.indexOf(35) != 0) {
            throw new MalformedURIException("No scheme found in URI.");
        }
        int i2 = length + 1;
        if (i2 < length2 && str.charAt(length) == '/' && str.charAt(i2) == '/') {
            int i3 = length + 2;
            int i4 = i3;
            while (i4 < length2) {
                char cCharAt = str.charAt(i4);
                if (cCharAt == '/' || cCharAt == '?' || cCharAt == '#') {
                    break;
                } else {
                    i4++;
                }
            }
            if (i4 <= i3) {
                this.m_host = "";
            } else if (initializeAuthority(str.substring(i3, i4))) {
            }
            length = i4;
        }
        initializePath(str, length);
        if (uri != null) {
            absolutize(uri);
        }
    }

    private void initialize(URI uri, String str, boolean z) throws MalformedURIException {
        int i = 0;
        int length = str != null ? str.length() : 0;
        if (uri == null && length == 0) {
            if (!z) {
                throw new MalformedURIException("Cannot initialize URI with empty parameters.");
            }
            this.m_path = "";
            return;
        }
        if (length == 0) {
            initialize(uri);
            return;
        }
        int iIndexOf = str.indexOf(58);
        if (iIndexOf != -1) {
            int i2 = iIndexOf - 1;
            int iLastIndexOf = str.lastIndexOf(47, i2);
            int iLastIndexOf2 = str.lastIndexOf(63, i2);
            int iLastIndexOf3 = str.lastIndexOf(35, i2);
            if (iIndexOf != 0 && iLastIndexOf == -1 && iLastIndexOf2 == -1 && iLastIndexOf3 == -1) {
                initializeScheme(str);
                int length2 = this.m_scheme.length() + 1;
                if (iIndexOf == length - 1 || str.charAt(iIndexOf + 1) == '#') {
                    throw new MalformedURIException("Scheme specific part cannot be empty.");
                }
                i = length2;
            } else if (iIndexOf == 0 || (uri == null && iLastIndexOf3 != 0 && !z)) {
                throw new MalformedURIException("No scheme found in URI.");
            }
        } else if (uri == null && str.indexOf(35) != 0 && !z) {
            throw new MalformedURIException("No scheme found in URI.");
        }
        int i3 = i + 1;
        if (i3 < length && str.charAt(i) == '/' && str.charAt(i3) == '/') {
            int i4 = i + 2;
            int i5 = i4;
            while (i5 < length) {
                char cCharAt = str.charAt(i5);
                if (cCharAt == '/' || cCharAt == '?' || cCharAt == '#') {
                    break;
                } else {
                    i5++;
                }
            }
            if (i5 <= i4) {
                this.m_host = "";
            } else if (initializeAuthority(str.substring(i4, i5))) {
            }
            i = i5;
        }
        initializePath(str, i);
        if (uri != null) {
            absolutize(uri);
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0043 A[PHI: r6
      0x0043: PHI (r6v9 int) = (r6v6 int), (r6v13 int) binds: [B:30:0x0050, B:22:0x0041] A[DONT_GENERATE, DONT_INLINE]] */
    private boolean initializeAuthority(String str) {
        String strSubstring;
        int i;
        boolean z;
        int iLastIndexOf;
        int length = str.length();
        int i2 = -1;
        if (str.indexOf(64, 0) != -1) {
            int i3 = 0;
            while (i3 < length && str.charAt(i3) != '@') {
                i3++;
            }
            strSubstring = str.substring(0, i3);
            i = i3 + 1;
        } else {
            strSubstring = null;
            i = 0;
        }
        if (i >= length) {
            z = false;
            iLastIndexOf = i;
        } else if (str.charAt(i) == '[') {
            int iIndexOf = str.indexOf(93, i);
            if (iIndexOf == -1) {
                iIndexOf = length;
            }
            iLastIndexOf = iIndexOf + 1;
            if (iLastIndexOf >= length || str.charAt(iLastIndexOf) != ':') {
                iLastIndexOf = length;
                z = false;
            } else {
                z = true;
            }
        } else {
            iLastIndexOf = str.lastIndexOf(58, length);
            if (iLastIndexOf <= i) {
                iLastIndexOf = length;
            }
            if (iLastIndexOf != length) {
                z = true;
            } else {
                z = false;
            }
        }
        String strSubstring2 = str.substring(i, iLastIndexOf);
        if (strSubstring2.length() > 0 && z) {
            int i4 = iLastIndexOf + 1;
            int i5 = i4;
            while (i5 < length) {
                i5++;
            }
            String strSubstring3 = str.substring(i4, i5);
            if (strSubstring3.length() > 0) {
                try {
                    int i6 = Integer.parseInt(strSubstring3);
                    if (i6 == -1) {
                        i6--;
                    }
                    i2 = i6;
                } catch (NumberFormatException unused) {
                    i2 = -2;
                }
            }
        }
        if (isValidServerBasedAuthority(strSubstring2, i2, strSubstring)) {
            this.m_host = strSubstring2;
            this.m_port = i2;
            this.m_userinfo = strSubstring;
            return true;
        }
        if (!isValidRegistryBasedAuthority(str)) {
            return false;
        }
        this.m_regAuthority = str;
        return true;
    }

    private void initializePath(String str, int i) throws MalformedURIException {
        int i2;
        if (str == null) {
            throw new MalformedURIException("Cannot initialize path from null string!");
        }
        int length = str.length();
        char cCharAt = 0;
        if (i >= length) {
            i2 = i;
        } else if (getScheme() == null || str.charAt(i) == '/') {
            i2 = i;
            while (i2 < length) {
                cCharAt = str.charAt(i2);
                if (cCharAt == '%') {
                    int i3 = i2 + 2;
                    if (i3 >= length || !isHex(str.charAt(i2 + 1)) || !isHex(str.charAt(i3))) {
                        throw new MalformedURIException("Path contains invalid escape sequence!");
                    }
                    i2 = i3;
                } else if (!isPathCharacter(cCharAt)) {
                    if (cCharAt != '?' && cCharAt != '#') {
                        throw new MalformedURIException("Path contains invalid character: " + cCharAt);
                    }
                    break;
                    break;
                }
                i2++;
            }
        } else {
            i2 = i;
            while (i2 < length) {
                cCharAt = str.charAt(i2);
                if (cCharAt == '?' || cCharAt == '#') {
                    break;
                }
                if (cCharAt == '%') {
                    int i4 = i2 + 2;
                    if (i4 >= length || !isHex(str.charAt(i2 + 1)) || !isHex(str.charAt(i4))) {
                        throw new MalformedURIException("Opaque part contains invalid escape sequence!");
                    }
                    i2 = i4;
                } else if (!isURICharacter(cCharAt)) {
                    throw new MalformedURIException("Opaque part contains invalid character: " + cCharAt);
                }
                i2++;
            }
        }
        this.m_path = str.substring(i, i2);
        if (cCharAt == '?') {
            int i5 = i2 + 1;
            int i6 = i5;
            while (i6 < length) {
                cCharAt = str.charAt(i6);
                if (cCharAt == '#') {
                    break;
                }
                if (cCharAt == '%') {
                    int i7 = i6 + 2;
                    if (i7 >= length || !isHex(str.charAt(i6 + 1)) || !isHex(str.charAt(i7))) {
                        throw new MalformedURIException("Query string contains invalid escape sequence!");
                    }
                    i6 = i7;
                } else if (!isURICharacter(cCharAt)) {
                    throw new MalformedURIException("Query string contains invalid character: " + cCharAt);
                }
                i6++;
            }
            this.m_queryString = str.substring(i5, i6);
            i2 = i6;
        }
        if (cCharAt == '#') {
            int i8 = i2 + 1;
            int i9 = i8;
            while (i9 < length) {
                char cCharAt2 = str.charAt(i9);
                if (cCharAt2 == '%') {
                    int i10 = i9 + 2;
                    if (i10 >= length || !isHex(str.charAt(i9 + 1)) || !isHex(str.charAt(i10))) {
                        throw new MalformedURIException("Fragment contains invalid escape sequence!");
                    }
                    i9 = i10;
                } else if (!isURICharacter(cCharAt2)) {
                    throw new MalformedURIException("Fragment contains invalid character: " + cCharAt2);
                }
                i9++;
            }
            this.m_fragment = str.substring(i8, i9);
        }
    }

    private void initializeScheme(String str) throws MalformedURIException {
        int length = str.length();
        int i = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt == ':' || cCharAt == '/' || cCharAt == '?' || cCharAt == '#') {
                break;
            } else {
                i++;
            }
        }
        String strSubstring = str.substring(0, i);
        if (strSubstring.length() == 0) {
            throw new MalformedURIException("No scheme found in URI.");
        }
        setScheme(strSubstring);
    }

    private static boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private static boolean isAlphanum(char c) {
        return c <= 'z' && (fgLookupTable[c] & 48) != 0;
    }

    public static boolean isConformantSchemeName(String str) {
        if (str == null || str.trim().length() == 0 || !isAlpha(str.charAt(0))) {
            return false;
        }
        int length = str.length();
        for (int i = 1; i < length; i++) {
            if (!isSchemeCharacter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isHex(char c) {
        return c <= 'f' && (fgLookupTable[c] & 64) != 0;
    }

    private static boolean isPathCharacter(char c) {
        return c <= '~' && (fgLookupTable[c] & Opcodes.OPC_getstatic) != 0;
    }

    private static boolean isReservedCharacter(char c) {
        return c <= ']' && (fgLookupTable[c] & 1) != 0;
    }

    private static boolean isSchemeCharacter(char c) {
        return c <= 'z' && (fgLookupTable[c] & 52) != 0;
    }

    private static boolean isURICharacter(char c) {
        return c <= '~' && (fgLookupTable[c] & 51) != 0;
    }

    private static boolean isURIString(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        int i = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '%') {
                int i2 = i + 2;
                if (i2 >= length || !isHex(str.charAt(i + 1)) || !isHex(str.charAt(i2))) {
                    return false;
                }
                i = i2;
            } else if (!isURICharacter(cCharAt)) {
                return false;
            }
            i++;
        }
        return true;
    }

    private static boolean isUnreservedCharacter(char c) {
        return c <= '~' && (fgLookupTable[c] & 50) != 0;
    }

    private static boolean isUserinfoCharacter(char c) {
        return c <= 'z' && (fgLookupTable[c] & 58) != 0;
    }

    private boolean isValidRegistryBasedAuthority(String str) {
        int length = str.length();
        int i = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '%') {
                int i2 = i + 2;
                if (i2 >= length || !isHex(str.charAt(i + 1)) || !isHex(str.charAt(i2))) {
                    return false;
                }
                i = i2;
            } else if (!isPathCharacter(cCharAt)) {
                return false;
            }
            i++;
        }
        return true;
    }

    private boolean isValidServerBasedAuthority(String str, int i, String str2) {
        if (!isWellFormedAddress(str) || i < -1 || i > 65535) {
            return false;
        }
        if (str2 != null) {
            int length = str2.length();
            int i2 = 0;
            while (i2 < length) {
                char cCharAt = str2.charAt(i2);
                if (cCharAt == '%') {
                    int i3 = i2 + 2;
                    if (i3 >= length || !isHex(str2.charAt(i2 + 1)) || !isHex(str2.charAt(i3))) {
                        return false;
                    }
                    i2 = i3;
                } else if (!isUserinfoCharacter(cCharAt)) {
                    return false;
                }
                i2++;
            }
        }
        return true;
    }

    public static boolean isWellFormedAddress(String str) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return false;
        }
        if (str.startsWith("[")) {
            return isWellFormedIPv6Reference(str);
        }
        if (str.startsWith(".") || str.startsWith(Parameters.DEFAULT_OPTION_PREFIXES) || str.endsWith(Parameters.DEFAULT_OPTION_PREFIXES)) {
            return false;
        }
        int iLastIndexOf = str.lastIndexOf(46);
        if (str.endsWith(".")) {
            iLastIndexOf = str.substring(0, iLastIndexOf).lastIndexOf(46);
        }
        int i = iLastIndexOf + 1;
        if (i < length && isDigit(str.charAt(i))) {
            return isWellFormedIPv4Address(str);
        }
        if (length > 255) {
            return false;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            if (cCharAt == '.') {
                if (!isAlphanum(str.charAt(i3 - 1))) {
                    return false;
                }
                int i4 = i3 + 1;
                if (i4 < length && !isAlphanum(str.charAt(i4))) {
                    return false;
                }
                i2 = 0;
            } else if ((!isAlphanum(cCharAt) && cCharAt != '-') || (i2 = i2 + 1) > 63) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWellFormedIPv4Address(String str) {
        int i;
        int length = str.length();
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            char cCharAt = str.charAt(i4);
            if (cCharAt == '.') {
                if ((i4 > 0 && !isDigit(str.charAt(i4 - 1))) || (((i = i4 + 1) < length && !isDigit(str.charAt(i))) || (i2 = i2 + 1) > 3)) {
                    return false;
                }
                i3 = 0;
            } else {
                if (!isDigit(cCharAt) || (i3 = i3 + 1) > 3) {
                    return false;
                }
                if (i3 == 3) {
                    char cCharAt2 = str.charAt(i4 - 2);
                    char cCharAt3 = str.charAt(i4 - 1);
                    if (cCharAt2 >= '2' && (cCharAt2 != '2' || (cCharAt3 >= '5' && (cCharAt3 != '5' || cCharAt > '5')))) {
                        return false;
                    }
                } else {
                    continue;
                }
            }
        }
        return i2 == 3;
    }

    public static boolean isWellFormedIPv6Reference(String str) {
        int[] iArr;
        int iScanHexSequence;
        int length = str.length();
        int i = length - 1;
        if (length <= 2 || str.charAt(0) != '[' || str.charAt(i) != ']' || (iScanHexSequence = scanHexSequence(str, 1, i, (iArr = new int[1]))) == -1) {
            return false;
        }
        if (iScanHexSequence == i) {
            return iArr[0] == 8;
        }
        int i2 = iScanHexSequence + 1;
        if (i2 >= i || str.charAt(iScanHexSequence) != ':') {
            return false;
        }
        if (str.charAt(i2) != ':') {
            return iArr[0] == 6 && isWellFormedIPv4Address(str.substring(i2, i));
        }
        int i3 = iArr[0] + 1;
        iArr[0] = i3;
        if (i3 > 8) {
            return false;
        }
        int i4 = iScanHexSequence + 2;
        if (i4 == i) {
            return true;
        }
        int iScanHexSequence2 = scanHexSequence(str, i4, i, iArr);
        if (iScanHexSequence2 != i) {
            if (iScanHexSequence2 == -1) {
                return false;
            }
            if (iArr[0] > i3) {
                iScanHexSequence2++;
            }
            if (!isWellFormedIPv4Address(str.substring(iScanHexSequence2, i))) {
                return false;
            }
        }
        return true;
    }

    private static int scanHexSequence(String str, int i, int i2, int[] iArr) {
        int i3;
        int i4 = 0;
        for (int i5 = i; i5 < i2; i5++) {
            char cCharAt = str.charAt(i5);
            if (cCharAt == ':') {
                if (i4 > 0) {
                    int i6 = iArr[0] + 1;
                    iArr[0] = i6;
                    if (i6 > 8) {
                        return -1;
                    }
                }
                if (i4 == 0 || ((i3 = i5 + 1) < i2 && str.charAt(i3) == ':')) {
                    return i5;
                }
                i4 = 0;
            } else {
                if (!isHex(cCharAt)) {
                    if (cCharAt != '.' || i4 >= 4 || i4 <= 0 || iArr[0] > 6) {
                        return -1;
                    }
                    int i7 = i5 - i4;
                    int i8 = i7 - 1;
                    return i8 >= i ? i8 : i7;
                }
                i4++;
                if (i4 > 4) {
                    return -1;
                }
            }
        }
        if (i4 > 0) {
            int i9 = iArr[0] + 1;
            iArr[0] = i9;
            if (i9 <= 8) {
                return i2;
            }
        }
        return -1;
    }

    /* JADX WARN: Code duplicated, block: B:37:0x00b3  */
    public void absolutize(URI uri) {
        int iLastIndexOf;
        if (this.m_path.length() == 0 && this.m_scheme == null && this.m_host == null && this.m_regAuthority == null) {
            this.m_scheme = uri.getScheme();
            this.m_userinfo = uri.getUserinfo();
            this.m_host = uri.getHost();
            this.m_port = uri.getPort();
            this.m_regAuthority = uri.getRegBasedAuthority();
            this.m_path = uri.getPath();
            if (this.m_queryString == null) {
                this.m_queryString = uri.getQueryString();
                if (this.m_fragment == null) {
                    this.m_fragment = uri.getFragment();
                    return;
                }
                return;
            }
            return;
        }
        if (this.m_scheme == null) {
            this.m_scheme = uri.getScheme();
            if (this.m_host == null && this.m_regAuthority == null) {
                this.m_userinfo = uri.getUserinfo();
                this.m_host = uri.getHost();
                this.m_port = uri.getPort();
                this.m_regAuthority = uri.getRegBasedAuthority();
                String strSubstring = "/";
                if (this.m_path.length() <= 0 || !this.m_path.startsWith("/")) {
                    String path = uri.getPath();
                    if (path != null && path.length() > 0) {
                        int iLastIndexOf2 = path.lastIndexOf(47);
                        if (iLastIndexOf2 != -1) {
                            strSubstring = path.substring(0, iLastIndexOf2 + 1);
                        } else {
                            strSubstring = "";
                        }
                    } else if (this.m_path.length() <= 0) {
                        strSubstring = "";
                    }
                    String strConcat = strSubstring.concat(this.m_path);
                    while (true) {
                        int iIndexOf = strConcat.indexOf("/./");
                        if (iIndexOf == -1) {
                            break;
                        } else {
                            strConcat = strConcat.substring(0, iIndexOf + 1).concat(strConcat.substring(iIndexOf + 3));
                        }
                    }
                    if (strConcat.endsWith("/.")) {
                        strConcat = strConcat.substring(0, strConcat.length() - 1);
                    }
                    int i = 1;
                    while (true) {
                        int iIndexOf2 = strConcat.indexOf("/../", i);
                        if (iIndexOf2 <= 0) {
                            break;
                        }
                        String strSubstring2 = strConcat.substring(0, strConcat.indexOf("/../"));
                        int iLastIndexOf3 = strSubstring2.lastIndexOf(47);
                        if (iLastIndexOf3 == -1 || strSubstring2.substring(iLastIndexOf3).equals("..")) {
                            i = iIndexOf2 + 4;
                        } else {
                            strConcat = strConcat.substring(0, iLastIndexOf3 + 1).concat(strConcat.substring(iIndexOf2 + 4));
                            i = iLastIndexOf3;
                        }
                    }
                    if (strConcat.endsWith("/..") && (iLastIndexOf = strConcat.substring(0, strConcat.length() - 3).lastIndexOf(47)) != -1) {
                        strConcat = strConcat.substring(0, iLastIndexOf + 1);
                    }
                    this.m_path = strConcat;
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0040  */
    public void appendPath(String str) throws MalformedURIException {
        String str2;
        if (str == null || str.trim().length() == 0) {
            return;
        }
        if (!isURIString(str)) {
            throw new MalformedURIException("Path contains invalid character!");
        }
        String str3 = this.m_path;
        if (str3 != null && str3.trim().length() != 0) {
            if (this.m_path.endsWith("/")) {
                if (str.startsWith("/")) {
                    str2 = this.m_path;
                    str = str.substring(1);
                } else {
                    str2 = this.m_path;
                }
            } else if (str.startsWith("/")) {
                str2 = this.m_path;
            } else {
                str2 = this.m_path;
                str = "/" + str;
            }
            str = str2.concat(str);
        } else if (!str.startsWith("/")) {
            str = "/" + str;
        }
        this.m_path = str;
    }

    public boolean equals(Object obj) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        if (!(obj instanceof URI)) {
            return false;
        }
        URI uri = (URI) obj;
        String str7 = this.m_scheme;
        if (!(str7 == null && uri.m_scheme == null) && (str7 == null || (str = uri.m_scheme) == null || !str7.equals(str))) {
            return false;
        }
        String str8 = this.m_userinfo;
        if (!(str8 == null && uri.m_userinfo == null) && (str8 == null || (str2 = uri.m_userinfo) == null || !str8.equals(str2))) {
            return false;
        }
        String str9 = this.m_host;
        if ((!(str9 == null && uri.m_host == null) && (str9 == null || (str3 = uri.m_host) == null || !str9.equals(str3))) || this.m_port != uri.m_port) {
            return false;
        }
        String str10 = this.m_path;
        if (!(str10 == null && uri.m_path == null) && (str10 == null || (str4 = uri.m_path) == null || !str10.equals(str4))) {
            return false;
        }
        String str11 = this.m_queryString;
        if (!(str11 == null && uri.m_queryString == null) && (str11 == null || (str5 = uri.m_queryString) == null || !str11.equals(str5))) {
            return false;
        }
        String str12 = this.m_fragment;
        if (str12 == null && uri.m_fragment == null) {
            return true;
        }
        return (str12 == null || (str6 = uri.m_fragment) == null || !str12.equals(str6)) ? false : true;
    }

    public String getAuthority() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.m_host != null || this.m_regAuthority != null) {
            stringBuffer.append("//");
            if (this.m_host != null) {
                String str = this.m_userinfo;
                if (str != null) {
                    stringBuffer.append(str);
                    stringBuffer.append('@');
                }
                stringBuffer.append(this.m_host);
                if (this.m_port != -1) {
                    stringBuffer.append(':');
                    stringBuffer.append(this.m_port);
                }
            } else {
                stringBuffer.append(this.m_regAuthority);
            }
        }
        return stringBuffer.toString();
    }

    public String getFragment() {
        return this.m_fragment;
    }

    public String getHost() {
        return this.m_host;
    }

    public String getPath() {
        return this.m_path;
    }

    public String getPath(boolean z, boolean z2) {
        StringBuffer stringBuffer = new StringBuffer(this.m_path);
        if (z && this.m_queryString != null) {
            stringBuffer.append(MangleConstant.Q_MARK);
            stringBuffer.append(this.m_queryString);
        }
        if (z2 && this.m_fragment != null) {
            stringBuffer.append('#');
            stringBuffer.append(this.m_fragment);
        }
        return stringBuffer.toString();
    }

    public int getPort() {
        return this.m_port;
    }

    public String getQueryString() {
        return this.m_queryString;
    }

    public String getRegBasedAuthority() {
        return this.m_regAuthority;
    }

    public String getScheme() {
        return this.m_scheme;
    }

    public String getSchemeSpecificPart() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.m_host != null || this.m_regAuthority != null) {
            stringBuffer.append("//");
            if (this.m_host != null) {
                String str = this.m_userinfo;
                if (str != null) {
                    stringBuffer.append(str);
                    stringBuffer.append('@');
                }
                stringBuffer.append(this.m_host);
                if (this.m_port != -1) {
                    stringBuffer.append(':');
                    stringBuffer.append(this.m_port);
                }
            } else {
                stringBuffer.append(this.m_regAuthority);
            }
        }
        String str2 = this.m_path;
        if (str2 != null) {
            stringBuffer.append(str2);
        }
        if (this.m_queryString != null) {
            stringBuffer.append(MangleConstant.Q_MARK);
            stringBuffer.append(this.m_queryString);
        }
        if (this.m_fragment != null) {
            stringBuffer.append('#');
            stringBuffer.append(this.m_fragment);
        }
        return stringBuffer.toString();
    }

    public String getUserinfo() {
        return this.m_userinfo;
    }

    public boolean isAbsoluteURI() {
        return this.m_scheme != null;
    }

    public boolean isGenericURI() {
        return this.m_host != null;
    }

    public void setFragment(String str) throws MalformedURIException {
        if (str == null) {
            str = null;
        } else {
            if (!isGenericURI()) {
                throw new MalformedURIException("Fragment can only be set for a generic URI!");
            }
            if (getPath() == null) {
                throw new MalformedURIException("Fragment cannot be set when path is null!");
            }
            if (!isURIString(str)) {
                throw new MalformedURIException("Fragment contains invalid character!");
            }
        }
        this.m_fragment = str;
    }

    public void setHost(String str) throws MalformedURIException {
        if (str != null && str.length() != 0) {
            if (!isWellFormedAddress(str)) {
                throw new MalformedURIException("Host is not a well formed address!");
            }
            this.m_host = str;
            this.m_regAuthority = null;
            return;
        }
        if (str != null) {
            this.m_regAuthority = null;
        }
        this.m_host = str;
        this.m_userinfo = null;
        this.m_port = -1;
    }

    public void setPath(String str) throws MalformedURIException {
        if (str != null) {
            initializePath(str, 0);
            return;
        }
        this.m_path = null;
        this.m_queryString = null;
        this.m_fragment = null;
    }

    public void setPort(int i) throws MalformedURIException {
        if (i < 0 || i > 65535) {
            if (i != -1) {
                throw new MalformedURIException("Invalid port number!");
            }
        } else if (this.m_host == null) {
            throw new MalformedURIException("Port cannot be set when host is null!");
        }
        this.m_port = i;
    }

    public void setQueryString(String str) throws MalformedURIException {
        if (str == null) {
            str = null;
        } else {
            if (!isGenericURI()) {
                throw new MalformedURIException("Query string can only be set for a generic URI!");
            }
            if (getPath() == null) {
                throw new MalformedURIException("Query string cannot be set when path is null!");
            }
            if (!isURIString(str)) {
                throw new MalformedURIException("Query string contains invalid character!");
            }
        }
        this.m_queryString = str;
    }

    public void setRegBasedAuthority(String str) throws MalformedURIException {
        if (str == null) {
            this.m_regAuthority = null;
            return;
        }
        if (str.length() < 1 || !isValidRegistryBasedAuthority(str) || str.indexOf(47) != -1) {
            throw new MalformedURIException("Registry based authority is not well formed.");
        }
        this.m_regAuthority = str;
        this.m_host = null;
        this.m_userinfo = null;
        this.m_port = -1;
    }

    public void setScheme(String str) throws MalformedURIException {
        if (str == null) {
            throw new MalformedURIException("Cannot set scheme from null string!");
        }
        if (!isConformantSchemeName(str)) {
            throw new MalformedURIException("The scheme is not conformant.");
        }
        this.m_scheme = str.toLowerCase(Locale.ENGLISH);
    }

    public void setUserinfo(String str) throws MalformedURIException {
        if (str == null) {
            this.m_userinfo = null;
            return;
        }
        if (this.m_host == null) {
            throw new MalformedURIException("Userinfo cannot be set when host is null!");
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '%') {
                int i2 = i + 2;
                if (i2 >= length || !isHex(str.charAt(i + 1)) || !isHex(str.charAt(i2))) {
                    throw new MalformedURIException("Userinfo contains invalid escape sequence!");
                }
            } else if (!isUserinfoCharacter(cCharAt)) {
                throw new MalformedURIException("Userinfo contains invalid character:" + cCharAt);
            }
        }
        this.m_userinfo = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String str = this.m_scheme;
        if (str != null) {
            stringBuffer.append(str);
            stringBuffer.append(':');
        }
        stringBuffer.append(getSchemeSpecificPart());
        return stringBuffer.toString();
    }
}
