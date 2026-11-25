package com.badlogic.gdx.utils;

import com.badlogic.gdx.files.FileHandle;
import com.huawei.hms.framework.common.ContainerUtils;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.xerces.dom3.as.ASDataType;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/XmlReader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class XmlReader {
    static final int xml_en_elementBody = 15;
    static final int xml_en_main = 1;
    static final int xml_error = 0;
    static final int xml_first_final = 34;
    static final int xml_start = 1;
    private Element current;
    private String entitiesText;
    private Element root;
    private static final byte[] _xml_actions = init__xml_actions_0();
    private static final byte[] _xml_key_offsets = init__xml_key_offsets_0();
    private static final char[] _xml_trans_keys = init__xml_trans_keys_0();
    private static final byte[] _xml_single_lengths = init__xml_single_lengths_0();
    private static final byte[] _xml_range_lengths = init__xml_range_lengths_0();
    private static final short[] _xml_index_offsets = init__xml_index_offsets_0();
    private static final byte[] _xml_indicies = init__xml_indicies_0();
    private static final byte[] _xml_trans_targs = init__xml_trans_targs_0();
    private static final byte[] _xml_trans_actions = init__xml_trans_actions_0();
    private final Array<Element> elements = new Array<>(8);
    private final StringBuilder textBuffer = new StringBuilder(64);

    public Element parse(String str) {
        char[] charArray = str.toCharArray();
        return parse(charArray, 0, charArray.length);
    }

    public Element parse(Reader reader) {
        try {
            try {
                char[] cArr = new char[1024];
                int i = 0;
                while (true) {
                    int i2 = reader.read(cArr, i, cArr.length - i);
                    if (i2 == -1) {
                        Element element = parse(cArr, 0, i);
                        StreamUtils.closeQuietly(reader);
                        return element;
                    }
                    if (i2 == 0) {
                        char[] cArr2 = new char[cArr.length * 2];
                        System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
                        cArr = cArr2;
                    } else {
                        i += i2;
                    }
                    StreamUtils.closeQuietly(reader);
                    throw th;
                }
            } catch (IOException e) {
                throw new SerializationException(e);
            }
        } catch (Throwable th) {
            StreamUtils.closeQuietly(reader);
            throw th;
        }
    }

    public Element parse(InputStream inputStream) {
        try {
            try {
                Element element = parse(new InputStreamReader(inputStream, "UTF-8"));
                StreamUtils.closeQuietly(inputStream);
                return element;
            } catch (IOException e) {
                throw new SerializationException(e);
            }
        } catch (Throwable th) {
            StreamUtils.closeQuietly(inputStream);
            throw th;
        }
    }

    public Element parse(FileHandle fileHandle) {
        try {
            return parse(fileHandle.reader("UTF-8"));
        } catch (Exception e) {
            throw new SerializationException("Error parsing file: " + fileHandle, e);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:147:0x0234  */
    /* JADX WARN: Code duplicated, block: B:166:0x0230 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:173:0x022b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:174:0x0019 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:204:0x011d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:205:0x00f8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:219:0x008a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:27:0x0065  */
    /* JADX WARN: Code duplicated, block: B:30:0x006f  */
    /* JADX WARN: Code duplicated, block: B:32:0x007d  */
    /* JADX WARN: Code duplicated, block: B:34:0x0081  */
    /* JADX WARN: Code duplicated, block: B:36:0x0087  */
    /* JADX WARN: Code duplicated, block: B:61:0x00de  */
    /* JADX WARN: Code duplicated, block: B:63:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:65:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:67:0x00ed  */
    /* JADX WARN: Code duplicated, block: B:69:0x00f5 A[LOOP:5: B:66:0x00eb->B:69:0x00f5, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:72:0x0114  */
    /* JADX WARN: Code duplicated, block: B:78:0x0126 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:79:0x0128  */
    /* JADX WARN: Code duplicated, block: B:81:0x013d  */
    /* JADX WARN: Failed to find 'out' block for switch in B:44:0x00ae. Please report as an issue. */
    public Element parse(char[] cArr, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        char c;
        char[] cArr2;
        int i8;
        int i9;
        int i10;
        int i11;
        boolean z;
        int i12;
        int i13;
        int i14;
        String str;
        String strEntity;
        int i15 = 1;
        int i16 = i;
        byte b = 1;
        int i17 = 0;
        int i18 = 0;
        String str2 = null;
        int i19 = 0;
        while (true) {
            char c2 = '\n';
            if (i17 != 0) {
                if (i17 != i15) {
                    if (i17 != 2) {
                        i3 = i15;
                    }
                }
                i3 = i15;
                if (b == 0) {
                    i15 = i3;
                    i17 = 5;
                } else {
                    i16++;
                    if (i16 != i2) {
                        i15 = i3;
                        i17 = i15;
                    }
                }
            } else if (i16 == i2) {
                i17 = 4;
            } else if (b == 0) {
                i17 = 5;
            }
            int i20 = _xml_key_offsets[b];
            short s = _xml_index_offsets[b];
            byte b2 = _xml_single_lengths[b];
            int i21 = s;
            if (b2 > 0) {
                int i22 = i20 + b2;
                int i23 = i20;
                int i24 = i22 - 1;
                while (true) {
                    if (i24 >= i23) {
                        int i25 = i23 + ((i24 - i23) >> 1);
                        char c3 = cArr[i16];
                        char c4 = _xml_trans_keys[i25];
                        if (c3 < c4) {
                            i24 = i25 - 1;
                        } else if (c3 > c4) {
                            i23 = i25 + 1;
                        } else {
                            i8 = s + (i25 - i20);
                        }
                    } else {
                        i20 = i22;
                        i21 = s + b2;
                        i4 = _xml_range_lengths[b];
                        i8 = i21;
                        if (i4 > 0) {
                            i5 = ((i4 << 1) + i20) - 2;
                            i6 = i20;
                            while (i5 >= i6) {
                                i7 = (((i5 - i6) >> i15) & (-2)) + i6;
                                c = cArr[i16];
                                cArr2 = _xml_trans_keys;
                                if (c < cArr2[i7]) {
                                    i5 = i7 - 2;
                                } else if (c > cArr2[i7 + 1]) {
                                    i6 = i7 + 2;
                                } else {
                                    i4 = (i7 - i20) >> 1;
                                    i8 = i21 + i4;
                                }
                            }
                            i8 = i21 + i4;
                        }
                    }
                }
            } else {
                i4 = _xml_range_lengths[b];
                i8 = i21;
                if (i4 > 0) {
                    i5 = ((i4 << 1) + i20) - 2;
                    i6 = i20;
                    while (i5 >= i6) {
                        i7 = (((i5 - i6) >> i15) & (-2)) + i6;
                        c = cArr[i16];
                        cArr2 = _xml_trans_keys;
                        if (c < cArr2[i7]) {
                            i5 = i7 - 2;
                        } else if (c > cArr2[i7 + 1]) {
                            i6 = i7 + 2;
                        } else {
                            i4 = (i7 - i20) >> 1;
                            i8 = i21 + i4;
                        }
                    }
                    i8 = i21 + i4;
                }
            }
            byte b3 = _xml_indicies[i8];
            b = _xml_trans_targs[b3];
            byte b4 = _xml_trans_actions[b3];
            if (b4 != 0) {
                int i26 = b4 + 1;
                int i27 = _xml_actions[b4];
                while (true) {
                    int i28 = i27 - 1;
                    if (i27 > 0) {
                        int i29 = i26 + 1;
                        switch (_xml_actions[i26]) {
                            case 0:
                                i9 = i15;
                                i18 = i16;
                                i27 = i28;
                                i26 = i29;
                                i15 = i9;
                                c2 = '\n';
                                break;
                            case 1:
                                i9 = i15;
                                char c5 = cArr[i18];
                                if (c5 == '?' || c5 == '!') {
                                    char c6 = cArr[i18 + 1];
                                    if (c6 == '[' && cArr[i18 + 2] == 'C' && cArr[i18 + 3] == 'D' && cArr[i18 + 4] == 'A' && cArr[i18 + 5] == 'T' && cArr[i18 + 6] == 'A' && cArr[i18 + 7] == '[') {
                                        int i30 = i18 + 8;
                                        int i31 = i18 + 10;
                                        while (true) {
                                            if (cArr[i31 - 2] == ']' && cArr[i31 - 1] == ']' && cArr[i31] == '>') {
                                                i10 = 2;
                                                text(new String(cArr, i30, (i31 - i30) - 2));
                                                i16 = i31;
                                                i18 = i30;
                                            } else {
                                                i31++;
                                            }
                                        }
                                    } else {
                                        i10 = 2;
                                        if (c5 == '!' && c6 == '-' && cArr[i18 + 2] == '-') {
                                            int i32 = i18 + 3;
                                            while (true) {
                                                if (cArr[i32] == '-' && cArr[i32 + 1] == '-') {
                                                    int i33 = i32 + 2;
                                                    if (cArr[i33] == '>') {
                                                        i16 = i33;
                                                    }
                                                }
                                                i32++;
                                            }
                                        } else {
                                            while (cArr[i16] != '>') {
                                                i16++;
                                            }
                                        }
                                    }
                                    b = 15;
                                    i17 = i10;
                                    i15 = i9;
                                } else {
                                    open(new String(cArr, i18, i16 - i18));
                                    i19 = i9;
                                    i27 = i28;
                                    i26 = i29;
                                    i15 = i9;
                                    c2 = '\n';
                                }
                                break;
                            case 2:
                                i9 = i15;
                                close();
                                i19 = 0;
                                b = 15;
                                i15 = i9;
                                i17 = 2;
                                break;
                            case 3:
                                i9 = i15;
                                close();
                                b = 15;
                                i15 = i9;
                                i17 = 2;
                                break;
                            case 4:
                                i9 = i15;
                                if (i19 != 0) {
                                    b = 15;
                                    i15 = i9;
                                    i17 = 2;
                                }
                                i27 = i28;
                                i26 = i29;
                                i15 = i9;
                                c2 = '\n';
                                break;
                            case 5:
                                i9 = i15;
                                str2 = new String(cArr, i18, i16 - i18);
                                i27 = i28;
                                i26 = i29;
                                i15 = i9;
                                c2 = '\n';
                                break;
                            case 6:
                                for (int i34 = i16; i34 != i18; i34--) {
                                    char c7 = cArr[i34 - 1];
                                    if (c7 != '\t' && c7 != c2 && c7 != '\r' && c7 != ' ') {
                                        i11 = i18;
                                        z = false;
                                        while (i18 != i34) {
                                            i12 = i18 + 1;
                                            if (cArr[i18] != '&') {
                                                i18 = i12;
                                            } else {
                                                i18 = i12;
                                                while (true) {
                                                    if (i18 != i34) {
                                                        i14 = i18 + 1;
                                                        if (cArr[i18] != ';') {
                                                            i18 = i14;
                                                            i15 = 1;
                                                        } else {
                                                            i13 = 1;
                                                            this.textBuffer.append(cArr, i11, (i12 - i11) - 1);
                                                            str = new String(cArr, i12, (i14 - i12) - 1);
                                                            strEntity = entity(str);
                                                            StringBuilder stringBuilder = this.textBuffer;
                                                            if (strEntity != null) {
                                                                str = strEntity;
                                                            }
                                                            stringBuilder.append(str);
                                                            i11 = i14;
                                                            i18 = i11;
                                                            z = true;
                                                        }
                                                    } else {
                                                        i13 = i15;
                                                    }
                                                }
                                                i15 = i13;
                                            }
                                        }
                                        i9 = i15;
                                        if (z) {
                                            if (i11 < i34) {
                                                this.textBuffer.append(cArr, i11, i34 - i11);
                                            }
                                            this.entitiesText = this.textBuffer.toString();
                                            this.textBuffer.setLength(0);
                                        } else {
                                            this.entitiesText = new String(cArr, i11, i34 - i11);
                                        }
                                        i18 = i11;
                                        i27 = i28;
                                        i26 = i29;
                                        i15 = i9;
                                        c2 = '\n';
                                    }
                                    break;
                                }
                                i11 = i18;
                                z = false;
                                while (i18 != i34) {
                                    i12 = i18 + 1;
                                    if (cArr[i18] != '&') {
                                        i18 = i12;
                                    } else {
                                        i18 = i12;
                                        while (true) {
                                            if (i18 != i34) {
                                                i14 = i18 + 1;
                                                if (cArr[i18] != ';') {
                                                    i18 = i14;
                                                    i15 = 1;
                                                } else {
                                                    i13 = 1;
                                                    this.textBuffer.append(cArr, i11, (i12 - i11) - 1);
                                                    str = new String(cArr, i12, (i14 - i12) - 1);
                                                    strEntity = entity(str);
                                                    StringBuilder stringBuilder2 = this.textBuffer;
                                                    if (strEntity != null) {
                                                        str = strEntity;
                                                    }
                                                    stringBuilder2.append(str);
                                                    i11 = i14;
                                                    i18 = i11;
                                                    z = true;
                                                }
                                            } else {
                                                i13 = i15;
                                            }
                                        }
                                        i15 = i13;
                                    }
                                }
                                i9 = i15;
                                if (z) {
                                    if (i11 < i34) {
                                        this.textBuffer.append(cArr, i11, i34 - i11);
                                    }
                                    this.entitiesText = this.textBuffer.toString();
                                    this.textBuffer.setLength(0);
                                } else {
                                    this.entitiesText = new String(cArr, i11, i34 - i11);
                                }
                                i18 = i11;
                                i27 = i28;
                                i26 = i29;
                                i15 = i9;
                                c2 = '\n';
                                break;
                            case 7:
                                attribute(str2, this.entitiesText);
                                i9 = i15;
                                i27 = i28;
                                i26 = i29;
                                i15 = i9;
                                c2 = '\n';
                                break;
                            case 8:
                                text(this.entitiesText);
                                i9 = i15;
                                i27 = i28;
                                i26 = i29;
                                i15 = i9;
                                c2 = '\n';
                                break;
                            default:
                                i9 = i15;
                                i27 = i28;
                                i26 = i29;
                                i15 = i9;
                                c2 = '\n';
                                break;
                        }
                    }
                }
            }
            i3 = i15;
            if (b == 0) {
                i15 = i3;
                i17 = 5;
            } else {
                i16++;
                if (i16 != i2) {
                    i15 = i3;
                    i17 = i15;
                }
            }
        }
        this.entitiesText = null;
        if (i16 < i2) {
            int i35 = i3;
            for (int i36 = 0; i36 < i16; i36++) {
                if (cArr[i36] == '\n') {
                    i35++;
                }
            }
            throw new SerializationException("Error parsing XML on line " + i35 + " near: " + new String(cArr, i16, Math.min(32, i2 - i16)));
        }
        if (this.elements.size != 0) {
            Element elementPeek = this.elements.peek();
            this.elements.clear();
            throw new SerializationException("Error parsing XML, unclosed element: " + elementPeek.getName());
        }
        Element element = this.root;
        this.root = null;
        return element;
    }

    private static byte[] init__xml_actions_0() {
        return new byte[]{0, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 2, 1, 4, 2, 2, 4, 2, 6, 7, 2, 6, 8, 3, 0, 6, 7};
    }

    private static byte[] init__xml_key_offsets_0() {
        return new byte[]{0, 0, 4, 9, 14, 20, 26, 30, 35, 36, 37, 42, 46, 50, 51, 52, 56, 57, 62, 67, 73, 79, 83, 88, 89, 90, 95, 99, 103, 104, 108, 109, 110, 111, 112, 115};
    }

    private static char[] init__xml_trans_keys_0() {
        return new char[]{StringUtil.SPACE, '<', '\t', '\r', StringUtil.SPACE, '/', '>', '\t', '\r', StringUtil.SPACE, '/', '>', '\t', '\r', StringUtil.SPACE, '/', '=', '>', '\t', '\r', StringUtil.SPACE, '/', '=', '>', '\t', '\r', StringUtil.SPACE, '=', '\t', '\r', StringUtil.SPACE, '\"', '\'', '\t', '\r', '\"', '\"', StringUtil.SPACE, '/', '>', '\t', '\r', StringUtil.SPACE, '>', '\t', '\r', StringUtil.SPACE, '>', '\t', '\r', '\'', '\'', StringUtil.SPACE, '<', '\t', '\r', '<', StringUtil.SPACE, '/', '>', '\t', '\r', StringUtil.SPACE, '/', '>', '\t', '\r', StringUtil.SPACE, '/', '=', '>', '\t', '\r', StringUtil.SPACE, '/', '=', '>', '\t', '\r', StringUtil.SPACE, '=', '\t', '\r', StringUtil.SPACE, '\"', '\'', '\t', '\r', '\"', '\"', StringUtil.SPACE, '/', '>', '\t', '\r', StringUtil.SPACE, '>', '\t', '\r', StringUtil.SPACE, '>', '\t', '\r', '<', StringUtil.SPACE, '/', '\t', '\r', '>', '>', '\'', '\'', StringUtil.SPACE, '\t', '\r', 0};
    }

    private static byte[] init__xml_single_lengths_0() {
        return new byte[]{0, 2, 3, 3, 4, 4, 2, 3, 1, 1, 3, 2, 2, 1, 1, 2, 1, 3, 3, 4, 4, 2, 3, 1, 1, 3, 2, 2, 1, 2, 1, 1, 1, 1, 1, 0};
    }

    private static byte[] init__xml_range_lengths_0() {
        return new byte[]{0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0};
    }

    private static short[] init__xml_index_offsets_0() {
        return new short[]{0, 0, 4, 9, 14, 20, 26, 30, 35, 37, 39, 44, 48, 52, 54, 56, 60, 62, 67, 72, 78, 84, 88, 93, 95, 97, 102, ASDataType.ANYURI_DATATYPE, 110, 112, ASDataType.GMONTH_DATATYPE, 118, 120, 122, 124, 127};
    }

    private static byte[] init__xml_indicies_0() {
        return new byte[]{0, 2, 0, 1, 2, 1, 1, 2, 3, 5, 6, 7, 5, 4, 9, 10, 1, 11, 9, 8, 13, 1, 14, 1, 13, 12, 15, 16, 15, 1, 16, 17, 18, 16, 1, 20, 19, 22, 21, 9, 10, 11, 9, 1, 23, 24, 23, 1, 25, 11, 25, 1, 20, 26, 22, 27, 29, 30, 29, 28, 32, 31, 30, 34, 1, 30, 33, 36, 37, 38, 36, 35, 40, 41, 1, 42, 40, 39, 44, 1, 45, 1, 44, 43, 46, 47, 46, 1, 47, 48, 49, 47, 1, 51, 50, 53, 52, 40, 41, 42, 40, 1, 54, 55, 54, 1, 56, 42, 56, 1, 57, 1, 57, 34, 57, 1, 1, 58, 59, 58, 51, 60, 53, 61, 62, 62, 1, 1, 0};
    }

    private static byte[] init__xml_trans_targs_0() {
        return new byte[]{1, 0, 2, 3, 3, 4, 11, 34, 5, 4, 11, 34, 5, 6, 7, 6, 7, 8, 13, 9, 10, 9, 10, 12, 34, 12, 14, 14, 16, 15, 17, 16, 17, 18, 30, 18, 19, 26, 28, 20, 19, 26, 28, 20, 21, 22, 21, 22, 23, 32, 24, 25, 24, 25, 27, 28, 27, 29, 31, 35, 33, 33, 34};
    }

    private static byte[] init__xml_trans_actions_0() {
        return new byte[]{0, 0, 0, 1, 0, 3, 3, 13, 1, 0, 0, 9, 0, 11, 11, 0, 0, 0, 0, 1, 25, 0, 19, 5, 16, 0, 1, 0, 1, 0, 0, 0, 22, 1, 0, 0, 3, 3, 13, 1, 0, 0, 9, 0, 11, 11, 0, 0, 0, 0, 1, 25, 0, 19, 5, 16, 0, 0, 0, 7, 1, 0, 0};
    }

    protected void open(String str) {
        Element element = new Element(str, this.current);
        Element element2 = this.current;
        if (element2 != null) {
            element2.addChild(element);
        }
        this.elements.add(element);
        this.current = element;
    }

    protected void attribute(String str, String str2) {
        this.current.setAttribute(str, str2);
    }

    protected String entity(String str) {
        if (str.equals("lt")) {
            return "<";
        }
        if (str.equals("gt")) {
            return ">";
        }
        if (str.equals("amp")) {
            return ContainerUtils.FIELD_DELIMITER;
        }
        if (str.equals("apos")) {
            return FormatHelper.QUOTE;
        }
        if (str.equals("quot")) {
            return "\"";
        }
        if (str.startsWith("#x")) {
            return Character.toString((char) Integer.parseInt(str.substring(2), 16));
        }
        return null;
    }

    protected void text(String str) {
        String text = this.current.getText();
        Element element = this.current;
        if (text != null) {
            str = text + str;
        }
        element.setText(str);
    }

    protected void close() {
        this.root = this.elements.pop();
        this.current = this.elements.size > 0 ? this.elements.peek() : null;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/XmlReader$Element.class
      classes8.dex
     */
    public static class Element {
        private ObjectMap<String, String> attributes;
        private Array<Element> children;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f129name;
        private Element parent;
        private String text;

        public Element(String str, Element element) {
            this.f129name = str;
            this.parent = element;
        }

        public String getName() {
            return this.f129name;
        }

        public ObjectMap<String, String> getAttributes() {
            return this.attributes;
        }

        public String getAttribute(String str) {
            ObjectMap<String, String> objectMap = this.attributes;
            if (objectMap == null) {
                throw new GdxRuntimeException("Element " + this.f129name + " doesn't have attribute: " + str);
            }
            String str2 = objectMap.get(str);
            if (str2 != null) {
                return str2;
            }
            throw new GdxRuntimeException("Element " + this.f129name + " doesn't have attribute: " + str);
        }

        public String getAttribute(String str, String str2) {
            String str3;
            ObjectMap<String, String> objectMap = this.attributes;
            return (objectMap == null || (str3 = objectMap.get(str)) == null) ? str2 : str3;
        }

        public boolean hasAttribute(String str) {
            ObjectMap<String, String> objectMap = this.attributes;
            if (objectMap == null) {
                return false;
            }
            return objectMap.containsKey(str);
        }

        public void setAttribute(String str, String str2) {
            if (this.attributes == null) {
                this.attributes = new ObjectMap<>(8);
            }
            this.attributes.put(str, str2);
        }

        public int getChildCount() {
            Array<Element> array = this.children;
            if (array == null) {
                return 0;
            }
            return array.size;
        }

        public Array<Element> getChildren() {
            return this.children;
        }

        public Element getChild(int i) {
            Array<Element> array = this.children;
            if (array == null) {
                throw new GdxRuntimeException("Element has no children: " + this.f129name);
            }
            return array.get(i);
        }

        public void addChild(Element element) {
            if (this.children == null) {
                this.children = new Array<>(8);
            }
            this.children.add(element);
        }

        public String getText() {
            return this.text;
        }

        public void setText(String str) {
            this.text = str;
        }

        public void removeChild(int i) {
            Array<Element> array = this.children;
            if (array != null) {
                array.removeIndex(i);
            }
        }

        public void removeChild(Element element) {
            Array<Element> array = this.children;
            if (array != null) {
                array.removeValue(element, true);
            }
        }

        public void remove() {
            this.parent.removeChild(this);
        }

        public void replaceChild(Element element, Element element2) {
            Array<Element> array = this.children;
            if (array == null) {
                throw new GdxRuntimeException("Element has no children: " + this.f129name);
            }
            if (!array.replaceFirst(element, true, element2)) {
                throw new GdxRuntimeException("Element '" + this.f129name + "' does not contain child: " + element);
            }
        }

        public Element getParent() {
            return this.parent;
        }

        public String toString() {
            return toString("");
        }

        /* JADX WARN: Multi-variable type inference failed */
        public String toString(String str) {
            String str2;
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append(str);
            stringBuilder.append('<');
            stringBuilder.append(this.f129name);
            ObjectMap<String, String> objectMap = this.attributes;
            if (objectMap != null) {
                ObjectMap.Entries<String, String> it = objectMap.entries().iterator();
                while (it.hasNext()) {
                    ObjectMap.Entry next = it.next();
                    stringBuilder.append(StringUtil.SPACE);
                    stringBuilder.append((String) next.key);
                    stringBuilder.append("=\"");
                    stringBuilder.append((String) next.value);
                    stringBuilder.append('\"');
                }
            }
            if (this.children == null && ((str2 = this.text) == null || str2.length() == 0)) {
                stringBuilder.append("/>");
            } else {
                stringBuilder.append(">\n");
                String str3 = str + '\t';
                String str4 = this.text;
                if (str4 != null && str4.length() > 0) {
                    stringBuilder.append(str3);
                    stringBuilder.append(this.text);
                    stringBuilder.append('\n');
                }
                Array<Element> array = this.children;
                if (array != null) {
                    Array.ArrayIterator<Element> it2 = array.iterator();
                    while (it2.hasNext()) {
                        stringBuilder.append(it2.next().toString(str3));
                        stringBuilder.append('\n');
                    }
                }
                stringBuilder.append(str);
                stringBuilder.append("</");
                stringBuilder.append(this.f129name);
                stringBuilder.append('>');
            }
            return stringBuilder.toString();
        }

        public Element getChildByName(String str) {
            if (this.children == null) {
                return null;
            }
            for (int i = 0; i < this.children.size; i++) {
                Element element = this.children.get(i);
                if (element.f129name.equals(str)) {
                    return element;
                }
            }
            return null;
        }

        public boolean hasChild(String str) {
            return (this.children == null || getChildByName(str) == null) ? false : true;
        }

        public Element getChildByNameRecursive(String str) {
            if (this.children == null) {
                return null;
            }
            for (int i = 0; i < this.children.size; i++) {
                Element element = this.children.get(i);
                if (element.f129name.equals(str)) {
                    return element;
                }
                Element childByNameRecursive = element.getChildByNameRecursive(str);
                if (childByNameRecursive != null) {
                    return childByNameRecursive;
                }
            }
            return null;
        }

        public boolean hasChildRecursive(String str) {
            return (this.children == null || getChildByNameRecursive(str) == null) ? false : true;
        }

        public Array<Element> getChildrenByName(String str) {
            Array<Element> array = new Array<>();
            if (this.children == null) {
                return array;
            }
            for (int i = 0; i < this.children.size; i++) {
                Element element = this.children.get(i);
                if (element.f129name.equals(str)) {
                    array.add(element);
                }
            }
            return array;
        }

        public Array<Element> getChildrenByNameRecursively(String str) {
            Array<Element> array = new Array<>();
            getChildrenByNameRecursively(str, array);
            return array;
        }

        private void getChildrenByNameRecursively(String str, Array<Element> array) {
            if (this.children == null) {
                return;
            }
            for (int i = 0; i < this.children.size; i++) {
                Element element = this.children.get(i);
                if (element.f129name.equals(str)) {
                    array.add(element);
                }
                element.getChildrenByNameRecursively(str, array);
            }
        }

        public float getFloatAttribute(String str) {
            return Float.parseFloat(getAttribute(str));
        }

        public float getFloatAttribute(String str, float f) {
            String attribute = getAttribute(str, null);
            return attribute == null ? f : Float.parseFloat(attribute);
        }

        public int getIntAttribute(String str) {
            return Integer.parseInt(getAttribute(str));
        }

        public int getIntAttribute(String str, int i) {
            String attribute = getAttribute(str, null);
            return attribute == null ? i : Integer.parseInt(attribute);
        }

        public boolean getBooleanAttribute(String str) {
            return Boolean.parseBoolean(getAttribute(str));
        }

        public boolean getBooleanAttribute(String str, boolean z) {
            String attribute = getAttribute(str, null);
            return attribute == null ? z : Boolean.parseBoolean(attribute);
        }

        public String get(String str) {
            String str2 = get(str, null);
            if (str2 != null) {
                return str2;
            }
            throw new GdxRuntimeException("Element " + this.f129name + " doesn't have attribute or child: " + str);
        }

        public String get(String str, String str2) {
            String text;
            String str3;
            ObjectMap<String, String> objectMap = this.attributes;
            if (objectMap != null && (str3 = objectMap.get(str)) != null) {
                return str3;
            }
            Element childByName = getChildByName(str);
            return (childByName == null || (text = childByName.getText()) == null) ? str2 : text;
        }

        public int getInt(String str) {
            String str2 = get(str, null);
            if (str2 == null) {
                throw new GdxRuntimeException("Element " + this.f129name + " doesn't have attribute or child: " + str);
            }
            return Integer.parseInt(str2);
        }

        public int getInt(String str, int i) {
            String str2 = get(str, null);
            return str2 == null ? i : Integer.parseInt(str2);
        }

        public float getFloat(String str) {
            String str2 = get(str, null);
            if (str2 == null) {
                throw new GdxRuntimeException("Element " + this.f129name + " doesn't have attribute or child: " + str);
            }
            return Float.parseFloat(str2);
        }

        public float getFloat(String str, float f) {
            String str2 = get(str, null);
            return str2 == null ? f : Float.parseFloat(str2);
        }

        public boolean getBoolean(String str) {
            String str2 = get(str, null);
            if (str2 == null) {
                throw new GdxRuntimeException("Element " + this.f129name + " doesn't have attribute or child: " + str);
            }
            return Boolean.parseBoolean(str2);
        }

        public boolean getBoolean(String str, boolean z) {
            String str2 = get(str, null);
            return str2 == null ? z : Boolean.parseBoolean(str2);
        }
    }
}
