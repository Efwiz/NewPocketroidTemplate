package com.badlogic.gdx.utils;

import com.android.dex.DexFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/PropertiesUtils.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public final class PropertiesUtils {
    private static final int CONTINUE = 3;
    private static final int IGNORE = 5;
    private static final int KEY_DONE = 4;
    private static final String LINE_SEPARATOR = "\n";
    private static final int NONE = 0;
    private static final int SLASH = 1;
    private static final int UNICODE = 2;

    private PropertiesUtils() {
    }

    /* JADX WARN: Code duplicated, block: B:103:0x011e  */
    /* JADX WARN: Code duplicated, block: B:107:0x0124  */
    /* JADX WARN: Code duplicated, block: B:118:0x013f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:119:0x0141  */
    /* JADX WARN: Code duplicated, block: B:147:0x0134 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:153:0x011c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:61:0x00c4 A[PHI: r12
      0x00c4: PHI (r12v8 char) = (r12v2 char), (r12v3 char), (r12v4 char), (r12v5 char), (r12v6 char), (r12v1 char), (r12v1 char), (r12v1 char) binds: [B:60:0x00c2, B:59:0x00bf, B:58:0x00bd, B:57:0x00bb, B:56:0x00b8, B:140:0x00c4, B:102:0x011c, B:104:0x011f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:93:0x010d  */
    /* JADX WARN: Code duplicated, block: B:95:0x0110  */
    /* JADX WARN: Code duplicated, block: B:97:0x0113 A[ADDED_TO_REGION] */
    public static void load(ObjectMap<String, String> objectMap, Reader reader) throws IOException {
        char c;
        if (objectMap == null) {
            throw new NullPointerException("properties cannot be null");
        }
        if (reader == null) {
            throw new NullPointerException("reader cannot be null");
        }
        char[] cArr = new char[40];
        BufferedReader bufferedReader = new BufferedReader(reader);
        int i = 1;
        int i2 = 0;
        boolean z = true;
        int i3 = -1;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (true) {
            int i8 = bufferedReader.read();
            if (i8 == -1) {
                if (i5 == 2 && i6 <= 4) {
                    throw new IllegalArgumentException("Invalid Unicode sequence: expected format \\uxxxx");
                }
                if (i3 == -1 && i4 > 0) {
                    i3 = i4;
                }
                if (i3 >= 0) {
                    String str = new String(cArr, i2, i4);
                    String strSubstring = str.substring(i2, i3);
                    String strSubstring2 = str.substring(i3);
                    if (i5 == i) {
                        strSubstring2 = strSubstring2 + DexFormat.MAGIC_SUFFIX;
                    }
                    objectMap.put(strSubstring, strSubstring2);
                    return;
                }
                return;
            }
            char c2 = (char) i8;
            if (i4 == cArr.length) {
                char[] cArr2 = new char[cArr.length * 2];
                System.arraycopy(cArr, i2, cArr2, i2, i4);
                cArr = cArr2;
            }
            if (i5 == 2) {
                int iDigit = Character.digit(c2, 16);
                if (iDigit >= 0) {
                    i7 = (i7 << 4) + iDigit;
                    i6++;
                    if (i6 < 4) {
                    }
                } else if (i6 <= 4) {
                    throw new IllegalArgumentException("Invalid Unicode sequence: illegal character");
                }
                cArr[i4] = (char) i7;
                i4++;
                i5 = i2;
                if (c2 != '\n') {
                }
            }
            if (i5 == i) {
                if (c2 == '\n') {
                    i5 = 5;
                    i2 = 0;
                } else if (c2 != '\r') {
                    if (c2 == 'b') {
                        c2 = '\b';
                    } else if (c2 == 'f') {
                        c2 = '\f';
                    } else if (c2 == 'n') {
                        c2 = '\n';
                    } else if (c2 == 'r') {
                        c2 = '\r';
                    } else if (c2 == 't') {
                        c2 = '\t';
                    } else if (c2 == 'u') {
                        i2 = 0;
                        i5 = 2;
                        i6 = 0;
                        i7 = 0;
                    }
                    i5 = 0;
                    if (i5 == 4) {
                        i3 = i4;
                        i5 = 0;
                    }
                    cArr[i4] = c2;
                    i4++;
                    i = 1;
                    i2 = 0;
                    z = false;
                } else {
                    i2 = 0;
                    i5 = 3;
                }
            } else if (c2 != '\n') {
                if (c2 == '\r') {
                    if (i4 <= 0 || (i4 == 0 && i3 == 0)) {
                        if (i3 == -1) {
                            i3 = i4;
                        }
                        i2 = 0;
                        String str2 = new String(cArr, 0, i4);
                        objectMap.put(str2.substring(0, i3), str2.substring(i3));
                    } else {
                        i2 = 0;
                    }
                    i3 = -1;
                    i4 = i2;
                    i5 = i4;
                    i = 1;
                    z = true;
                } else if (c2 == '!' || c2 == '#') {
                    if (z) {
                        do {
                            int i9 = bufferedReader.read();
                            if (i9 == -1 || (c = (char) i9) == '\r') {
                                break;
                            }
                        } while (c != '\n');
                    } else {
                        if (Character.isSpace(c2)) {
                            if (i5 == 3) {
                                i5 = 5;
                            }
                            if (i4 == 0 && i4 != i3 && i5 != 5) {
                                if (i3 == -1) {
                                    i5 = 4;
                                }
                            }
                        }
                        if (i5 != 5 || i5 == 3) {
                            i5 = 0;
                        }
                        if (i5 == 4) {
                            i3 = i4;
                            i5 = 0;
                        }
                        cArr[i4] = c2;
                        i4++;
                        i = 1;
                        i2 = 0;
                        z = false;
                    }
                    i = 1;
                    i2 = 0;
                } else if (c2 == ':' || c2 == '=') {
                    if (i3 == -1) {
                        i3 = i4;
                        i = 1;
                        i2 = 0;
                        i5 = 0;
                    } else {
                        if (Character.isSpace(c2)) {
                            if (i5 == 3) {
                                i5 = 5;
                            }
                            if (i4 == 0) {
                            }
                            i = 1;
                            i2 = 0;
                        }
                        if (i5 != 5) {
                            i5 = 0;
                        } else {
                            i5 = 0;
                        }
                        if (i5 == 4) {
                            i3 = i4;
                            i5 = 0;
                        }
                        cArr[i4] = c2;
                        i4++;
                        i = 1;
                        i2 = 0;
                        z = false;
                    }
                } else if (c2 != '\\') {
                    if (Character.isSpace(c2)) {
                        if (i5 == 3) {
                            i5 = 5;
                        }
                        if (i4 == 0) {
                        }
                        i = 1;
                        i2 = 0;
                    }
                    if (i5 != 5) {
                        i5 = 0;
                    } else {
                        i5 = 0;
                    }
                    if (i5 == 4) {
                        i3 = i4;
                        i5 = 0;
                    }
                    cArr[i4] = c2;
                    i4++;
                    i = 1;
                    i2 = 0;
                    z = false;
                } else {
                    if (i5 == 4) {
                        i3 = i4;
                    }
                    i = 1;
                    i2 = 0;
                    i5 = 1;
                }
            } else if (i5 == 3) {
                i5 = 5;
                i = 1;
                i2 = 0;
            } else {
                if (i4 <= 0) {
                    if (i3 == -1) {
                        i3 = i4;
                    }
                    i2 = 0;
                    String str3 = new String(cArr, 0, i4);
                    objectMap.put(str3.substring(0, i3), str3.substring(i3));
                } else {
                    if (i3 == -1) {
                        i3 = i4;
                    }
                    i2 = 0;
                    String str4 = new String(cArr, 0, i4);
                    objectMap.put(str4.substring(0, i3), str4.substring(i3));
                }
                i3 = -1;
                i4 = i2;
                i5 = i4;
                i = 1;
                z = true;
            }
        }
    }

    public static void store(ObjectMap<String, String> objectMap, Writer writer, String str) throws IOException {
        storeImpl(objectMap, writer, str, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void storeImpl(ObjectMap<String, String> objectMap, Writer writer, String str, boolean z) throws IOException {
        if (str != null) {
            writeComment(writer, str);
        }
        writer.write("#");
        writer.write(new Date().toString());
        writer.write("\n");
        StringBuilder stringBuilder = new StringBuilder(200);
        ObjectMap.Entries<String, String> it = objectMap.entries().iterator();
        while (it.hasNext()) {
            ObjectMap.Entry next = it.next();
            dumpString(stringBuilder, (String) next.key, true, z);
            stringBuilder.append('=');
            dumpString(stringBuilder, (String) next.value, false, z);
            writer.write("\n");
            writer.write(stringBuilder.toString());
            stringBuilder.setLength(0);
        }
        writer.flush();
    }

    private static void dumpString(StringBuilder stringBuilder, String str, boolean z, boolean z2) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt > '=' && cCharAt < 127) {
                stringBuilder.append(cCharAt == '\\' ? "\\\\" : Character.valueOf(cCharAt));
            } else if (cCharAt == '\t') {
                stringBuilder.append("\\t");
            } else if (cCharAt == '\n') {
                stringBuilder.append("\\n");
            } else if (cCharAt == '\f') {
                stringBuilder.append("\\f");
            } else if (cCharAt == '\r') {
                stringBuilder.append("\\r");
            } else if (cCharAt != ' ') {
                if (cCharAt == '!' || cCharAt == '#' || cCharAt == ':' || cCharAt == '=') {
                    stringBuilder.append('\\').append(cCharAt);
                } else if ((cCharAt < ' ' || cCharAt > '~') & z2) {
                    String hexString = Integer.toHexString(cCharAt);
                    stringBuilder.append("\\u");
                    for (int i2 = 0; i2 < 4 - hexString.length(); i2++) {
                        stringBuilder.append(ExternalAnnotationProvider.NULLABLE);
                    }
                    stringBuilder.append(hexString);
                } else {
                    stringBuilder.append(cCharAt);
                }
            } else if (i == 0 || z) {
                stringBuilder.append("\\ ");
            } else {
                stringBuilder.append(cCharAt);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0071  */
    private static void writeComment(Writer writer, String str) throws IOException {
        writer.write("#");
        int length = str.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt > 255 || cCharAt == '\n' || cCharAt == '\r') {
                if (i2 != i) {
                    writer.write(str.substring(i2, i));
                }
                if (cCharAt > 255) {
                    String hexString = Integer.toHexString(cCharAt);
                    writer.write("\\u");
                    for (int i3 = 0; i3 < 4 - hexString.length(); i3++) {
                        writer.write(48);
                    }
                    writer.write(hexString);
                } else {
                    writer.write("\n");
                    if (cCharAt == '\r' && i != length - 1) {
                        int i4 = i + 1;
                        if (str.charAt(i4) == '\n') {
                            i = i4;
                        }
                    }
                    if (i != length - 1) {
                        int i5 = i + 1;
                        if (str.charAt(i5) != '#' && str.charAt(i5) != '!') {
                            writer.write("#");
                        }
                    } else {
                        writer.write("#");
                    }
                }
                i2 = i + 1;
            }
            i++;
        }
        if (i2 != i) {
            writer.write(str.substring(i2, i));
        }
        writer.write("\n");
    }
}
