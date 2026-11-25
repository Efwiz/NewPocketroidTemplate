package com.badlogic.gdx.utils;

import com.badlogic.gdx.files.FileHandle;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import org.apache.xerces.dom3.as.ASDataType;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/JsonReader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class JsonReader implements BaseJsonReader {
    static final int json_en_array = 23;
    static final int json_en_main = 1;
    static final int json_en_object = 5;
    static final int json_error = 0;
    static final int json_first_final = 35;
    static final int json_start = 1;
    private JsonValue current;
    private final Array<JsonValue> elements = new Array<>(8);
    private final Array<JsonValue> lastChild = new Array<>(8);
    private JsonValue root;
    private boolean stop;
    private static final byte[] _json_actions = init__json_actions_0();
    private static final short[] _json_key_offsets = init__json_key_offsets_0();
    private static final char[] _json_trans_keys = init__json_trans_keys_0();
    private static final byte[] _json_single_lengths = init__json_single_lengths_0();
    private static final byte[] _json_range_lengths = init__json_range_lengths_0();
    private static final short[] _json_index_offsets = init__json_index_offsets_0();
    private static final byte[] _json_indicies = init__json_indicies_0();
    private static final byte[] _json_trans_targs = init__json_trans_targs_0();
    private static final byte[] _json_trans_actions = init__json_trans_actions_0();
    private static final byte[] _json_eof_actions = init__json_eof_actions_0();

    public JsonValue parse(String str) {
        char[] charArray = str.toCharArray();
        return parse(charArray, 0, charArray.length);
    }

    public JsonValue parse(Reader reader) {
        char[] cArr = new char[1024];
        int i = 0;
        while (true) {
            try {
                try {
                    int i2 = reader.read(cArr, i, cArr.length - i);
                    if (i2 == -1) {
                        StreamUtils.closeQuietly(reader);
                        return parse(cArr, 0, i);
                    }
                    if (i2 == 0) {
                        char[] cArr2 = new char[cArr.length * 2];
                        System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
                        cArr = cArr2;
                    } else {
                        i += i2;
                    }
                } catch (IOException e) {
                    throw new SerializationException("Error reading input.", e);
                }
            } catch (Throwable th) {
                StreamUtils.closeQuietly(reader);
                throw th;
            }
            StreamUtils.closeQuietly(reader);
            throw th;
        }
    }

    @Override // com.badlogic.gdx.utils.BaseJsonReader
    public JsonValue parse(InputStream inputStream) {
        try {
            return parse(new InputStreamReader(inputStream, "UTF-8"));
        } catch (Exception e) {
            throw new SerializationException("Error reading stream.", e);
        }
    }

    @Override // com.badlogic.gdx.utils.BaseJsonReader
    public JsonValue parse(FileHandle fileHandle) {
        try {
            try {
                return parse(fileHandle.reader("UTF-8"));
            } catch (Exception e) {
                throw new SerializationException("Error parsing file: " + fileHandle, e);
            }
        } catch (Exception e2) {
            throw new SerializationException("Error reading file: " + fileHandle, e2);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:104:0x016f  */
    /* JADX WARN: Code duplicated, block: B:107:0x0178 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:132:0x01a9 A[LOOP:7: B:105:0x0170->B:132:0x01a9, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:136:0x01b9 A[Catch: RuntimeException -> 0x0096, LOOP:5: B:134:0x01b1->B:136:0x01b9, LOOP_END, TryCatch #1 {RuntimeException -> 0x0096, blocks: (B:257:0x0364, B:264:0x037e, B:270:0x0392, B:275:0x039d, B:278:0x03a6, B:27:0x007b, B:63:0x010b, B:134:0x01b1, B:136:0x01b9, B:138:0x01c5, B:141:0x01cf), top: B:354:0x007b }] */
    /* JADX WARN: Code duplicated, block: B:138:0x01c5 A[Catch: RuntimeException -> 0x0096, TryCatch #1 {RuntimeException -> 0x0096, blocks: (B:257:0x0364, B:264:0x037e, B:270:0x0392, B:275:0x039d, B:278:0x03a6, B:27:0x007b, B:63:0x010b, B:134:0x01b1, B:136:0x01b9, B:138:0x01c5, B:141:0x01cf), top: B:354:0x007b }] */
    /* JADX WARN: Code duplicated, block: B:140:0x01cd A[ADDED_TO_REGION, LOOP:8: B:140:0x01cd->B:144:0x01d5, LOOP_START, PHI: r5
      0x01cd: PHI (r5v82 int) = (r5v75 int), (r5v84 int) binds: [B:139:0x01cb, B:144:0x01d5] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:141:0x01cf A[Catch: RuntimeException -> 0x0096, TRY_LEAVE, TryCatch #1 {RuntimeException -> 0x0096, blocks: (B:257:0x0364, B:264:0x037e, B:270:0x0392, B:275:0x039d, B:278:0x03a6, B:27:0x007b, B:63:0x010b, B:134:0x01b1, B:136:0x01b9, B:138:0x01c5, B:141:0x01cf), top: B:354:0x007b }] */
    /* JADX WARN: Code duplicated, block: B:146:0x01dd A[LOOP:9: B:146:0x01dd->B:158:0x01f4, LOOP_START, PHI: r5
      0x01dd: PHI (r5v76 int) = (r5v75 int), (r5v80 int) binds: [B:139:0x01cb, B:158:0x01f4] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:157:0x01f2  */
    /* JADX WARN: Code duplicated, block: B:165:0x0207 A[Catch: RuntimeException -> 0x030e, TryCatch #4 {RuntimeException -> 0x030e, blocks: (B:58:0x00f9, B:162:0x01fe, B:165:0x0207, B:166:0x020c, B:169:0x0215, B:171:0x0218, B:172:0x0220, B:173:0x0230, B:176:0x0239, B:178:0x0244, B:181:0x024d, B:183:0x0250, B:185:0x025a, B:186:0x0269, B:188:0x0273, B:231:0x02f7, B:192:0x0282, B:194:0x0288, B:196:0x0291, B:198:0x0297, B:199:0x029c, B:201:0x02a2, B:204:0x02ab, B:223:0x02dd, B:229:0x02f3, B:226:0x02e8), top: B:360:0x00f9 }] */
    /* JADX WARN: Code duplicated, block: B:169:0x0215 A[Catch: RuntimeException -> 0x030e, TryCatch #4 {RuntimeException -> 0x030e, blocks: (B:58:0x00f9, B:162:0x01fe, B:165:0x0207, B:166:0x020c, B:169:0x0215, B:171:0x0218, B:172:0x0220, B:173:0x0230, B:176:0x0239, B:178:0x0244, B:181:0x024d, B:183:0x0250, B:185:0x025a, B:186:0x0269, B:188:0x0273, B:231:0x02f7, B:192:0x0282, B:194:0x0288, B:196:0x0291, B:198:0x0297, B:199:0x029c, B:201:0x02a2, B:204:0x02ab, B:223:0x02dd, B:229:0x02f3, B:226:0x02e8), top: B:360:0x00f9 }] */
    /* JADX WARN: Code duplicated, block: B:171:0x0218 A[Catch: RuntimeException -> 0x030e, TryCatch #4 {RuntimeException -> 0x030e, blocks: (B:58:0x00f9, B:162:0x01fe, B:165:0x0207, B:166:0x020c, B:169:0x0215, B:171:0x0218, B:172:0x0220, B:173:0x0230, B:176:0x0239, B:178:0x0244, B:181:0x024d, B:183:0x0250, B:185:0x025a, B:186:0x0269, B:188:0x0273, B:231:0x02f7, B:192:0x0282, B:194:0x0288, B:196:0x0291, B:198:0x0297, B:199:0x029c, B:201:0x02a2, B:204:0x02ab, B:223:0x02dd, B:229:0x02f3, B:226:0x02e8), top: B:360:0x00f9 }] */
    /* JADX WARN: Code duplicated, block: B:176:0x0239 A[Catch: RuntimeException -> 0x030e, TryCatch #4 {RuntimeException -> 0x030e, blocks: (B:58:0x00f9, B:162:0x01fe, B:165:0x0207, B:166:0x020c, B:169:0x0215, B:171:0x0218, B:172:0x0220, B:173:0x0230, B:176:0x0239, B:178:0x0244, B:181:0x024d, B:183:0x0250, B:185:0x025a, B:186:0x0269, B:188:0x0273, B:231:0x02f7, B:192:0x0282, B:194:0x0288, B:196:0x0291, B:198:0x0297, B:199:0x029c, B:201:0x02a2, B:204:0x02ab, B:223:0x02dd, B:229:0x02f3, B:226:0x02e8), top: B:360:0x00f9 }] */
    /* JADX WARN: Code duplicated, block: B:181:0x024d A[Catch: RuntimeException -> 0x030e, TryCatch #4 {RuntimeException -> 0x030e, blocks: (B:58:0x00f9, B:162:0x01fe, B:165:0x0207, B:166:0x020c, B:169:0x0215, B:171:0x0218, B:172:0x0220, B:173:0x0230, B:176:0x0239, B:178:0x0244, B:181:0x024d, B:183:0x0250, B:185:0x025a, B:186:0x0269, B:188:0x0273, B:231:0x02f7, B:192:0x0282, B:194:0x0288, B:196:0x0291, B:198:0x0297, B:199:0x029c, B:201:0x02a2, B:204:0x02ab, B:223:0x02dd, B:229:0x02f3, B:226:0x02e8), top: B:360:0x00f9 }] */
    /* JADX WARN: Code duplicated, block: B:183:0x0250 A[Catch: RuntimeException -> 0x030e, TryCatch #4 {RuntimeException -> 0x030e, blocks: (B:58:0x00f9, B:162:0x01fe, B:165:0x0207, B:166:0x020c, B:169:0x0215, B:171:0x0218, B:172:0x0220, B:173:0x0230, B:176:0x0239, B:178:0x0244, B:181:0x024d, B:183:0x0250, B:185:0x025a, B:186:0x0269, B:188:0x0273, B:231:0x02f7, B:192:0x0282, B:194:0x0288, B:196:0x0291, B:198:0x0297, B:199:0x029c, B:201:0x02a2, B:204:0x02ab, B:223:0x02dd, B:229:0x02f3, B:226:0x02e8), top: B:360:0x00f9 }] */
    /* JADX WARN: Code duplicated, block: B:184:0x0259  */
    /* JADX WARN: Code duplicated, block: B:186:0x0269 A[Catch: RuntimeException -> 0x030e, TryCatch #4 {RuntimeException -> 0x030e, blocks: (B:58:0x00f9, B:162:0x01fe, B:165:0x0207, B:166:0x020c, B:169:0x0215, B:171:0x0218, B:172:0x0220, B:173:0x0230, B:176:0x0239, B:178:0x0244, B:181:0x024d, B:183:0x0250, B:185:0x025a, B:186:0x0269, B:188:0x0273, B:231:0x02f7, B:192:0x0282, B:194:0x0288, B:196:0x0291, B:198:0x0297, B:199:0x029c, B:201:0x02a2, B:204:0x02ab, B:223:0x02dd, B:229:0x02f3, B:226:0x02e8), top: B:360:0x00f9 }] */
    /* JADX WARN: Code duplicated, block: B:188:0x0273 A[Catch: RuntimeException -> 0x030e, TryCatch #4 {RuntimeException -> 0x030e, blocks: (B:58:0x00f9, B:162:0x01fe, B:165:0x0207, B:166:0x020c, B:169:0x0215, B:171:0x0218, B:172:0x0220, B:173:0x0230, B:176:0x0239, B:178:0x0244, B:181:0x024d, B:183:0x0250, B:185:0x025a, B:186:0x0269, B:188:0x0273, B:231:0x02f7, B:192:0x0282, B:194:0x0288, B:196:0x0291, B:198:0x0297, B:199:0x029c, B:201:0x02a2, B:204:0x02ab, B:223:0x02dd, B:229:0x02f3, B:226:0x02e8), top: B:360:0x00f9 }] */
    /* JADX WARN: Code duplicated, block: B:190:0x027a  */
    /* JADX WARN: Code duplicated, block: B:191:0x0280 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:192:0x0282 A[Catch: RuntimeException -> 0x030e, TryCatch #4 {RuntimeException -> 0x030e, blocks: (B:58:0x00f9, B:162:0x01fe, B:165:0x0207, B:166:0x020c, B:169:0x0215, B:171:0x0218, B:172:0x0220, B:173:0x0230, B:176:0x0239, B:178:0x0244, B:181:0x024d, B:183:0x0250, B:185:0x025a, B:186:0x0269, B:188:0x0273, B:231:0x02f7, B:192:0x0282, B:194:0x0288, B:196:0x0291, B:198:0x0297, B:199:0x029c, B:201:0x02a2, B:204:0x02ab, B:223:0x02dd, B:229:0x02f3, B:226:0x02e8), top: B:360:0x00f9 }] */
    /* JADX WARN: Code duplicated, block: B:194:0x0288 A[Catch: RuntimeException -> 0x030e, TryCatch #4 {RuntimeException -> 0x030e, blocks: (B:58:0x00f9, B:162:0x01fe, B:165:0x0207, B:166:0x020c, B:169:0x0215, B:171:0x0218, B:172:0x0220, B:173:0x0230, B:176:0x0239, B:178:0x0244, B:181:0x024d, B:183:0x0250, B:185:0x025a, B:186:0x0269, B:188:0x0273, B:231:0x02f7, B:192:0x0282, B:194:0x0288, B:196:0x0291, B:198:0x0297, B:199:0x029c, B:201:0x02a2, B:204:0x02ab, B:223:0x02dd, B:229:0x02f3, B:226:0x02e8), top: B:360:0x00f9 }] */
    /* JADX WARN: Code duplicated, block: B:196:0x0291 A[Catch: RuntimeException -> 0x030e, TryCatch #4 {RuntimeException -> 0x030e, blocks: (B:58:0x00f9, B:162:0x01fe, B:165:0x0207, B:166:0x020c, B:169:0x0215, B:171:0x0218, B:172:0x0220, B:173:0x0230, B:176:0x0239, B:178:0x0244, B:181:0x024d, B:183:0x0250, B:185:0x025a, B:186:0x0269, B:188:0x0273, B:231:0x02f7, B:192:0x0282, B:194:0x0288, B:196:0x0291, B:198:0x0297, B:199:0x029c, B:201:0x02a2, B:204:0x02ab, B:223:0x02dd, B:229:0x02f3, B:226:0x02e8), top: B:360:0x00f9 }] */
    /* JADX WARN: Code duplicated, block: B:198:0x0297 A[Catch: RuntimeException -> 0x030e, TryCatch #4 {RuntimeException -> 0x030e, blocks: (B:58:0x00f9, B:162:0x01fe, B:165:0x0207, B:166:0x020c, B:169:0x0215, B:171:0x0218, B:172:0x0220, B:173:0x0230, B:176:0x0239, B:178:0x0244, B:181:0x024d, B:183:0x0250, B:185:0x025a, B:186:0x0269, B:188:0x0273, B:231:0x02f7, B:192:0x0282, B:194:0x0288, B:196:0x0291, B:198:0x0297, B:199:0x029c, B:201:0x02a2, B:204:0x02ab, B:223:0x02dd, B:229:0x02f3, B:226:0x02e8), top: B:360:0x00f9 }] */
    /* JADX WARN: Code duplicated, block: B:199:0x029c A[Catch: RuntimeException -> 0x030e, TryCatch #4 {RuntimeException -> 0x030e, blocks: (B:58:0x00f9, B:162:0x01fe, B:165:0x0207, B:166:0x020c, B:169:0x0215, B:171:0x0218, B:172:0x0220, B:173:0x0230, B:176:0x0239, B:178:0x0244, B:181:0x024d, B:183:0x0250, B:185:0x025a, B:186:0x0269, B:188:0x0273, B:231:0x02f7, B:192:0x0282, B:194:0x0288, B:196:0x0291, B:198:0x0297, B:199:0x029c, B:201:0x02a2, B:204:0x02ab, B:223:0x02dd, B:229:0x02f3, B:226:0x02e8), top: B:360:0x00f9 }] */
    /* JADX WARN: Code duplicated, block: B:201:0x02a2 A[Catch: RuntimeException -> 0x030e, TryCatch #4 {RuntimeException -> 0x030e, blocks: (B:58:0x00f9, B:162:0x01fe, B:165:0x0207, B:166:0x020c, B:169:0x0215, B:171:0x0218, B:172:0x0220, B:173:0x0230, B:176:0x0239, B:178:0x0244, B:181:0x024d, B:183:0x0250, B:185:0x025a, B:186:0x0269, B:188:0x0273, B:231:0x02f7, B:192:0x0282, B:194:0x0288, B:196:0x0291, B:198:0x0297, B:199:0x029c, B:201:0x02a2, B:204:0x02ab, B:223:0x02dd, B:229:0x02f3, B:226:0x02e8), top: B:360:0x00f9 }] */
    /* JADX WARN: Code duplicated, block: B:202:0x02a7  */
    /* JADX WARN: Code duplicated, block: B:204:0x02ab A[Catch: RuntimeException -> 0x030e, TRY_LEAVE, TryCatch #4 {RuntimeException -> 0x030e, blocks: (B:58:0x00f9, B:162:0x01fe, B:165:0x0207, B:166:0x020c, B:169:0x0215, B:171:0x0218, B:172:0x0220, B:173:0x0230, B:176:0x0239, B:178:0x0244, B:181:0x024d, B:183:0x0250, B:185:0x025a, B:186:0x0269, B:188:0x0273, B:231:0x02f7, B:192:0x0282, B:194:0x0288, B:196:0x0291, B:198:0x0297, B:199:0x029c, B:201:0x02a2, B:204:0x02ab, B:223:0x02dd, B:229:0x02f3, B:226:0x02e8), top: B:360:0x00f9 }] */
    /* JADX WARN: Code duplicated, block: B:207:0x02b5  */
    /* JADX WARN: Code duplicated, block: B:209:0x02b9  */
    /* JADX WARN: Code duplicated, block: B:213:0x02c1  */
    /* JADX WARN: Code duplicated, block: B:215:0x02c5  */
    /* JADX WARN: Code duplicated, block: B:218:0x02cf  */
    /* JADX WARN: Code duplicated, block: B:222:0x02dc  */
    /* JADX WARN: Code duplicated, block: B:224:0x02e5  */
    /* JADX WARN: Code duplicated, block: B:226:0x02e8 A[Catch: NumberFormatException -> 0x02f3, RuntimeException -> 0x030e, TRY_LEAVE, TryCatch #3 {NumberFormatException -> 0x02f3, blocks: (B:223:0x02dd, B:226:0x02e8), top: B:358:0x02dd }] */
    /* JADX WARN: Code duplicated, block: B:228:0x02f0  */
    /* JADX WARN: Code duplicated, block: B:234:0x02fd  */
    /* JADX WARN: Code duplicated, block: B:235:0x0301  */
    /* JADX WARN: Code duplicated, block: B:246:0x0330  */
    /* JADX WARN: Code duplicated, block: B:252:0x0349 A[Catch: RuntimeException -> 0x0410, TryCatch #7 {RuntimeException -> 0x0410, blocks: (B:249:0x033b, B:250:0x0345, B:252:0x0349, B:255:0x035b, B:261:0x0377, B:267:0x038b, B:272:0x0396), top: B:366:0x033b }] */
    /* JADX WARN: Code duplicated, block: B:254:0x0352  */
    /* JADX WARN: Code duplicated, block: B:255:0x035b A[Catch: RuntimeException -> 0x0410, TRY_LEAVE, TryCatch #7 {RuntimeException -> 0x0410, blocks: (B:249:0x033b, B:250:0x0345, B:252:0x0349, B:255:0x035b, B:261:0x0377, B:267:0x038b, B:272:0x0396), top: B:366:0x033b }] */
    /* JADX WARN: Code duplicated, block: B:257:0x0364 A[Catch: RuntimeException -> 0x0096, TRY_ENTER, TRY_LEAVE, TryCatch #1 {RuntimeException -> 0x0096, blocks: (B:257:0x0364, B:264:0x037e, B:270:0x0392, B:275:0x039d, B:278:0x03a6, B:27:0x007b, B:63:0x010b, B:134:0x01b1, B:136:0x01b9, B:138:0x01c5, B:141:0x01cf), top: B:354:0x007b }] */
    /* JADX WARN: Code duplicated, block: B:259:0x036a  */
    /* JADX WARN: Code duplicated, block: B:260:0x0375 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:261:0x0377 A[Catch: RuntimeException -> 0x0410, TRY_ENTER, TRY_LEAVE, TryCatch #7 {RuntimeException -> 0x0410, blocks: (B:249:0x033b, B:250:0x0345, B:252:0x0349, B:255:0x035b, B:261:0x0377, B:267:0x038b, B:272:0x0396), top: B:366:0x033b }] */
    /* JADX WARN: Code duplicated, block: B:263:0x037d  */
    /* JADX WARN: Code duplicated, block: B:266:0x038a  */
    /* JADX WARN: Code duplicated, block: B:269:0x0391  */
    /* JADX WARN: Code duplicated, block: B:272:0x0396 A[Catch: RuntimeException -> 0x0410, TRY_ENTER, TRY_LEAVE, TryCatch #7 {RuntimeException -> 0x0410, blocks: (B:249:0x033b, B:250:0x0345, B:252:0x0349, B:255:0x035b, B:261:0x0377, B:267:0x038b, B:272:0x0396), top: B:366:0x033b }] */
    /* JADX WARN: Code duplicated, block: B:274:0x039c  */
    /* JADX WARN: Code duplicated, block: B:276:0x03a1  */
    /* JADX WARN: Code duplicated, block: B:278:0x03a6 A[Catch: RuntimeException -> 0x0096, TRY_LEAVE, TryCatch #1 {RuntimeException -> 0x0096, blocks: (B:257:0x0364, B:264:0x037e, B:270:0x0392, B:275:0x039d, B:278:0x03a6, B:27:0x007b, B:63:0x010b, B:134:0x01b1, B:136:0x01b9, B:138:0x01c5, B:141:0x01cf), top: B:354:0x007b }] */
    /* JADX WARN: Code duplicated, block: B:281:0x03ae  */
    /* JADX WARN: Code duplicated, block: B:283:0x03b2  */
    /* JADX WARN: Code duplicated, block: B:291:0x03c5  */
    /* JADX WARN: Code duplicated, block: B:292:0x03c8  */
    /* JADX WARN: Code duplicated, block: B:297:0x03d9  */
    /* JADX WARN: Code duplicated, block: B:299:0x03e4  */
    /* JADX WARN: Code duplicated, block: B:301:0x03e9 A[Catch: NumberFormatException -> 0x03f8, RuntimeException -> 0x040e, TRY_LEAVE, TryCatch #2 {NumberFormatException -> 0x03f8, blocks: (B:298:0x03dc, B:301:0x03e9), top: B:356:0x03dc }] */
    /* JADX WARN: Code duplicated, block: B:303:0x03f1  */
    /* JADX WARN: Code duplicated, block: B:309:0x0402  */
    /* JADX WARN: Code duplicated, block: B:326:0x0432 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:327:0x0434  */
    /* JADX WARN: Code duplicated, block: B:329:0x0438  */
    /* JADX WARN: Code duplicated, block: B:331:0x043e  */
    /* JADX WARN: Code duplicated, block: B:335:0x0486  */
    /* JADX WARN: Code duplicated, block: B:337:0x048c  */
    /* JADX WARN: Code duplicated, block: B:339:0x049b  */
    /* JADX WARN: Code duplicated, block: B:345:0x04b1 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:347:0x04b4  */
    /* JADX WARN: Code duplicated, block: B:352:0x01e1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:360:0x00f9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:364:0x01e9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:366:0x033b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:377:0x020c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:378:0x0244 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:379:0x0205 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:380:0x0237 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:381:0x0418 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:382:0x032c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:383:0x01fe A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:384:0x0230 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:385:0x0322 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:386:0x0338 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:388:0x031a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:389:0x0311 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:38:0x00a1 A[Catch: RuntimeException -> 0x041c, TryCatch #10 {RuntimeException -> 0x041c, blocks: (B:21:0x0055, B:36:0x009b, B:38:0x00a1, B:40:0x00a9, B:41:0x00ac), top: B:372:0x0055 }] */
    /* JADX WARN: Code duplicated, block: B:390:0x0213 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:391:0x024b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:410:0x0124 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:411:0x0124 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:413:0x0167 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:41:0x00ac A[Catch: RuntimeException -> 0x041c, TRY_LEAVE, TryCatch #10 {RuntimeException -> 0x041c, blocks: (B:21:0x0055, B:36:0x009b, B:38:0x00a1, B:40:0x00a9, B:41:0x00ac), top: B:372:0x0055 }] */
    /* JADX WARN: Code duplicated, block: B:422:0x0440 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:423:0x0167 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:424:0x0167 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:433:0x0102 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:434:0x0102 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:436:0x01f4 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:437:0x02d6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:438:0x02c8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:441:0x0400 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:442:0x0415 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:446:0x03d3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:450:0x00d2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:451:0x00a9 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:454:? A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:455:? A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:45:0x00c2 A[Catch: RuntimeException -> 0x041a, TryCatch #9 {RuntimeException -> 0x041a, blocks: (B:52:0x00dc, B:54:0x00ea, B:43:0x00be, B:45:0x00c2, B:47:0x00c9, B:49:0x00cf, B:50:0x00d2), top: B:370:0x00dc }] */
    /* JADX WARN: Code duplicated, block: B:47:0x00c9 A[Catch: RuntimeException -> 0x041a, TryCatch #9 {RuntimeException -> 0x041a, blocks: (B:52:0x00dc, B:54:0x00ea, B:43:0x00be, B:45:0x00c2, B:47:0x00c9, B:49:0x00cf, B:50:0x00d2), top: B:370:0x00dc }] */
    /* JADX WARN: Code duplicated, block: B:49:0x00cf A[Catch: RuntimeException -> 0x041a, TryCatch #9 {RuntimeException -> 0x041a, blocks: (B:52:0x00dc, B:54:0x00ea, B:43:0x00be, B:45:0x00c2, B:47:0x00c9, B:49:0x00cf, B:50:0x00d2), top: B:370:0x00dc }] */
    /* JADX WARN: Code duplicated, block: B:51:0x00da A[PHI: r5 r16
      0x00da: PHI (r5v105 int) = (r5v2 int), (r5v27 int), (r5v2 int) binds: [B:37:0x009f, B:40:0x00a9, B:32:0x0091] A[DONT_GENERATE, DONT_INLINE]
      0x00da: PHI (r16v13 int) = (r16v16 int), (r16v17 int), (r16v18 int) binds: [B:37:0x009f, B:40:0x00a9, B:32:0x0091] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:54:0x00ea A[Catch: RuntimeException -> 0x041a, TRY_LEAVE, TryCatch #9 {RuntimeException -> 0x041a, blocks: (B:52:0x00dc, B:54:0x00ea, B:43:0x00be, B:45:0x00c2, B:47:0x00c9, B:49:0x00cf, B:50:0x00d2), top: B:370:0x00dc }] */
    /* JADX WARN: Code duplicated, block: B:60:0x0102 A[PHI: r10
      0x0102: PHI (r10v47 int) = (r10v24 int), (r10v30 int), (r10v30 int), (r10v32 int) binds: [B:59:0x00ff, B:433:0x0102, B:434:0x0102, B:145:0x01d8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:62:0x0107  */
    /* JADX WARN: Code duplicated, block: B:66:0x0113  */
    /* JADX WARN: Code duplicated, block: B:68:0x0117  */
    /* JADX WARN: Code duplicated, block: B:69:0x0119  */
    /* JADX WARN: Code duplicated, block: B:73:0x0121 A[LOOP:3: B:63:0x010b->B:73:0x0121, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:75:0x0131  */
    /* JADX WARN: Code duplicated, block: B:77:0x0136  */
    /* JADX WARN: Code duplicated, block: B:80:0x013f A[ADDED_TO_REGION] */
    public JsonValue parse(char[] cArr, int i, int i2) {
        boolean z;
        int i3;
        int i4;
        int i5;
        RuntimeException runtimeException;
        int i6;
        int i7;
        int i8;
        String str;
        String str2;
        String str3;
        String str4;
        boolean z2;
        boolean z3;
        char c;
        int i9;
        JsonValue jsonValuePeek;
        int i10;
        int i11;
        byte b;
        int i12;
        int i13;
        int i14;
        char c2;
        char[] cArr2;
        int i15;
        byte b2;
        byte b3;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        char c3;
        int[] iArr;
        String str5;
        boolean z4;
        boolean z5;
        boolean z6;
        char c4;
        int i21;
        char c5;
        int i22;
        boolean z7;
        char c6;
        boolean z8;
        boolean z9;
        int i23;
        char c7;
        char c8;
        int i24;
        int i25;
        boolean z10;
        char c9;
        int i26;
        int i27;
        int i28;
        this.stop = false;
        int i29 = 0;
        char c10 = 0;
        int i30 = 0;
        boolean z11 = false;
        boolean z12 = false;
        boolean z13 = false;
        int[] iArrCopyOf = new int[4];
        int i31 = 1;
        String str6 = null;
        int i32 = i;
        while (true) {
            String str7 = "null";
            String str8 = "false";
            int i33 = i30;
            try {
                if (c10 != 0) {
                    z = z11;
                    if (c10 != 1) {
                        if (c10 != 2) {
                            if (c10 == 4) {
                                i30 = i33;
                                z11 = z;
                                if (i32 == i2) {
                                    try {
                                        byte b4 = _json_eof_actions[i31];
                                        i6 = b4 + 1;
                                        i7 = _json_actions[b4];
                                        while (true) {
                                            i8 = i7 - 1;
                                            if (i7 > 0) {
                                                int i34 = i6 + 1;
                                                if (_json_actions[i6] != 1) {
                                                    str2 = str8;
                                                    str3 = str7;
                                                    i5 = i32;
                                                } else {
                                                    str = new String(cArr, i30, i32 - i30);
                                                    if (z11) {
                                                        str = unescape(str);
                                                    }
                                                    if (z12) {
                                                        str2 = str8;
                                                        str3 = str7;
                                                        i5 = i32;
                                                        str4 = str;
                                                        z12 = false;
                                                    } else {
                                                        if (z13) {
                                                            if (str.equals("true")) {
                                                                bool(str6, true);
                                                            } else if (str.equals(str8)) {
                                                                bool(str6, false);
                                                            } else {
                                                                if (str.equals(str7)) {
                                                                    string(str6, null);
                                                                } else {
                                                                    z2 = true;
                                                                    z3 = false;
                                                                    while (true) {
                                                                        if (i30 < i32) {
                                                                            c = cArr[i30];
                                                                            str2 = str8;
                                                                            if (c == '+') {
                                                                                if (c == 'E' && c != 'e') {
                                                                                    if (c != '-') {
                                                                                        if (c != '.') {
                                                                                            switch (c) {
                                                                                                case '0':
                                                                                                case '1':
                                                                                                case '2':
                                                                                                case '3':
                                                                                                case '4':
                                                                                                case '5':
                                                                                                case '6':
                                                                                                case '7':
                                                                                                case '8':
                                                                                                case '9':
                                                                                                    break;
                                                                                                default:
                                                                                                    z3 = false;
                                                                                                    z2 = false;
                                                                                                    break;
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                                z3 = true;
                                                                                z2 = false;
                                                                            }
                                                                            i30++;
                                                                            str8 = str2;
                                                                        } else {
                                                                            str2 = str8;
                                                                        }
                                                                    }
                                                                    if (z3) {
                                                                        str3 = str7;
                                                                        i5 = i32;
                                                                        try {
                                                                            number(str6, Double.parseDouble(str), str);
                                                                        } catch (NumberFormatException unused) {
                                                                            string(str6, str);
                                                                        }
                                                                    } else {
                                                                        str3 = str7;
                                                                        i5 = i32;
                                                                        if (z2) {
                                                                            number(str6, Long.parseLong(str), str);
                                                                        }
                                                                    }
                                                                }
                                                                str4 = null;
                                                            }
                                                            str2 = str8;
                                                            str3 = str7;
                                                            i5 = i32;
                                                            str4 = null;
                                                        } else {
                                                            str2 = str8;
                                                            str3 = str7;
                                                            i5 = i32;
                                                        }
                                                        string(str6, str);
                                                        str4 = null;
                                                    }
                                                    try {
                                                        if (this.stop) {
                                                            i4 = i5;
                                                        } else {
                                                            str6 = str4;
                                                            i30 = i5;
                                                            z13 = false;
                                                        }
                                                    } catch (RuntimeException e) {
                                                        e = e;
                                                        runtimeException = e;
                                                        i4 = i5;
                                                    }
                                                }
                                                str7 = str3;
                                                i7 = i8;
                                                i32 = i5;
                                                i6 = i34;
                                                str8 = str2;
                                            }
                                        }
                                    } catch (RuntimeException e2) {
                                        e = e2;
                                        i5 = i32;
                                    }
                                }
                                i32 = i32;
                            }
                            runtimeException = null;
                            JsonValue jsonValue = this.root;
                            this.root = null;
                            this.current = null;
                            this.lastChild.clear();
                            if (!this.stop) {
                                if (i4 < i2) {
                                    i11 = 1;
                                    for (i10 = 0; i10 < i4; i10++) {
                                        if (cArr[i10] == '\n') {
                                            i11++;
                                        }
                                    }
                                    int iMax = Math.max(0, i4 - 32);
                                    throw new SerializationException("Error parsing JSON on line " + i11 + " near: " + new String(cArr, iMax, i4 - iMax) + "*ERROR*" + new String(cArr, i4, Math.min(64, i2 - i4)), runtimeException);
                                }
                                if (this.elements.size != 0) {
                                    jsonValuePeek = this.elements.peek();
                                    this.elements.clear();
                                    if (jsonValuePeek == null && jsonValuePeek.isObject()) {
                                        throw new SerializationException("Error parsing JSON, unmatched brace.");
                                    }
                                    throw new SerializationException("Error parsing JSON, unmatched bracket.");
                                }
                                if (runtimeException != null) {
                                    throw new SerializationException("Error parsing JSON: ".concat(new String(cArr)), runtimeException);
                                }
                            }
                            return jsonValue;
                        }
                        i3 = i32;
                        i30 = i33;
                        z11 = z;
                        if (i31 == 0) {
                            iArrCopyOf = iArrCopyOf;
                            c10 = 5;
                            i29 = i29;
                            i32 = i3;
                        } else {
                            i9 = i3 + 1;
                            if (i9 != i2) {
                                iArrCopyOf = iArrCopyOf;
                                i32 = i9;
                                i29 = i29;
                                c10 = 1;
                            } else {
                                i32 = i9;
                                if (i32 == i2) {
                                    byte b5 = _json_eof_actions[i31];
                                    i6 = b5 + 1;
                                    i7 = _json_actions[b5];
                                    while (true) {
                                        i8 = i7 - 1;
                                        if (i7 > 0) {
                                            int i35 = i6 + 1;
                                            if (_json_actions[i6] != 1) {
                                                str2 = str8;
                                                str3 = str7;
                                                i5 = i32;
                                            } else {
                                                str = new String(cArr, i30, i32 - i30);
                                                if (z11) {
                                                    str = unescape(str);
                                                }
                                                if (z12) {
                                                    str2 = str8;
                                                    str3 = str7;
                                                    i5 = i32;
                                                    str4 = str;
                                                    z12 = false;
                                                } else {
                                                    if (z13) {
                                                        if (str.equals("true")) {
                                                            bool(str6, true);
                                                        } else if (str.equals(str8)) {
                                                            bool(str6, false);
                                                        } else {
                                                            if (str.equals(str7)) {
                                                                string(str6, null);
                                                            } else {
                                                                z2 = true;
                                                                z3 = false;
                                                                while (true) {
                                                                    if (i30 < i32) {
                                                                        c = cArr[i30];
                                                                        str2 = str8;
                                                                        if (c == '+') {
                                                                            if (c == 'E') {
                                                                                z3 = true;
                                                                                z2 = false;
                                                                            } else {
                                                                                z3 = true;
                                                                                z2 = false;
                                                                            }
                                                                        }
                                                                        i30++;
                                                                        str8 = str2;
                                                                    } else {
                                                                        str2 = str8;
                                                                    }
                                                                }
                                                                if (z3) {
                                                                    str3 = str7;
                                                                    i5 = i32;
                                                                    number(str6, Double.parseDouble(str), str);
                                                                } else {
                                                                    str3 = str7;
                                                                    i5 = i32;
                                                                    if (z2) {
                                                                        number(str6, Long.parseLong(str), str);
                                                                    }
                                                                }
                                                            }
                                                            str4 = null;
                                                        }
                                                        str2 = str8;
                                                        str3 = str7;
                                                        i5 = i32;
                                                        str4 = null;
                                                    } else {
                                                        str2 = str8;
                                                        str3 = str7;
                                                        i5 = i32;
                                                    }
                                                    string(str6, str);
                                                    str4 = null;
                                                }
                                                if (this.stop) {
                                                    i4 = i5;
                                                } else {
                                                    str6 = str4;
                                                    i30 = i5;
                                                    z13 = false;
                                                }
                                            }
                                            str7 = str3;
                                            i7 = i8;
                                            i32 = i5;
                                            i6 = i35;
                                            str8 = str2;
                                        }
                                        runtimeException = null;
                                        JsonValue jsonValue2 = this.root;
                                        this.root = null;
                                        this.current = null;
                                        this.lastChild.clear();
                                        if (!this.stop) {
                                            if (i4 < i2) {
                                                i11 = 1;
                                                while (i10 < i4) {
                                                    if (cArr[i10] == '\n') {
                                                        i11++;
                                                    }
                                                }
                                                int iMax2 = Math.max(0, i4 - 32);
                                                throw new SerializationException("Error parsing JSON on line " + i11 + " near: " + new String(cArr, iMax2, i4 - iMax2) + "*ERROR*" + new String(cArr, i4, Math.min(64, i2 - i4)), runtimeException);
                                            }
                                            if (this.elements.size != 0) {
                                                jsonValuePeek = this.elements.peek();
                                                this.elements.clear();
                                                if (jsonValuePeek == null) {
                                                }
                                                throw new SerializationException("Error parsing JSON, unmatched bracket.");
                                            }
                                            if (runtimeException != null) {
                                                throw new SerializationException("Error parsing JSON: ".concat(new String(cArr)), runtimeException);
                                            }
                                        }
                                        return jsonValue2;
                                    }
                                }
                                i32 = i32;
                            }
                        }
                    }
                    i4 = i32;
                    runtimeException = null;
                    JsonValue jsonValue3 = this.root;
                    this.root = null;
                    this.current = null;
                    this.lastChild.clear();
                    if (!this.stop) {
                        if (i4 < i2) {
                            i11 = 1;
                            while (i10 < i4) {
                                if (cArr[i10] == '\n') {
                                    i11++;
                                }
                            }
                            int iMax3 = Math.max(0, i4 - 32);
                            throw new SerializationException("Error parsing JSON on line " + i11 + " near: " + new String(cArr, iMax3, i4 - iMax3) + "*ERROR*" + new String(cArr, i4, Math.min(64, i2 - i4)), runtimeException);
                        }
                        if (this.elements.size != 0) {
                            jsonValuePeek = this.elements.peek();
                            this.elements.clear();
                            if (jsonValuePeek == null) {
                            }
                            throw new SerializationException("Error parsing JSON, unmatched bracket.");
                        }
                        if (runtimeException != null) {
                            throw new SerializationException("Error parsing JSON: ".concat(new String(cArr)), runtimeException);
                        }
                    }
                    return jsonValue3;
                }
                z = z11;
                if (i32 == i2) {
                    c10 = 4;
                } else if (i31 == 0) {
                    c10 = 5;
                }
                i30 = i33;
                z11 = z;
                short s = _json_key_offsets[i31];
                short s2 = _json_index_offsets[i31];
                byte b6 = _json_single_lengths[i31];
                int i36 = s;
                int i37 = s2;
                if (b6 > 0) {
                    int i38 = s + b6;
                    int i39 = s;
                    int i40 = i38 - 1;
                    while (true) {
                        if (i40 >= i39) {
                            int i41 = i39 + ((i40 - i39) >> 1);
                            int i42 = i39;
                            try {
                                char c11 = cArr[i32];
                                int i43 = i40;
                                char c12 = _json_trans_keys[i41];
                                if (c11 < c12) {
                                    i40 = i41 - 1;
                                    i39 = i42 == true ? 1 : 0;
                                } else if (c11 > c12) {
                                    i39 = i41 + 1;
                                    i40 = i43;
                                } else {
                                    i28 = s2 + (i41 - s);
                                    i3 = i32;
                                    i15 = i28;
                                    byte b7 = _json_indicies[i15];
                                    b2 = _json_trans_targs[b7];
                                    b3 = _json_trans_actions[b7];
                                    if (b3 != 0) {
                                        int i44 = b3 + 1;
                                        i16 = _json_actions[b3];
                                        i17 = i44;
                                        i30 = i33;
                                        i4 = i3;
                                        while (true) {
                                            i18 = i16 - 1;
                                            if (i16 > 0) {
                                                i19 = i17 + 1;
                                                switch (_json_actions[i17]) {
                                                    case 0:
                                                        i29 = i29;
                                                        iArr = iArrCopyOf;
                                                        z12 = true;
                                                        iArrCopyOf = iArr;
                                                        i16 = i18;
                                                        i17 = i19;
                                                        i29 = i29;
                                                        break;
                                                    case 1:
                                                        str5 = new String(cArr, i30, i4 - i30);
                                                        if (z) {
                                                            str5 = unescape(str5);
                                                        }
                                                        if (z12) {
                                                            i29 = i29;
                                                            iArr = iArrCopyOf;
                                                            z12 = false;
                                                        } else {
                                                            if (z13) {
                                                                if (str5.equals("true")) {
                                                                    bool(str6, true);
                                                                } else if (str5.equals("false")) {
                                                                    bool(str6, false);
                                                                } else {
                                                                    if (str5.equals("null")) {
                                                                        string(str6, null);
                                                                    } else {
                                                                        z4 = false;
                                                                        z5 = true;
                                                                        while (true) {
                                                                            if (i30 < i4) {
                                                                                z4 = z4;
                                                                                c4 = cArr[i30];
                                                                                i29 = i29;
                                                                                if (c4 != '+') {
                                                                                    if (c4 != 'E') {
                                                                                        if (c4 != '-') {
                                                                                            if (c4 != '.') {
                                                                                                switch (c4) {
                                                                                                    case '0':
                                                                                                    case '1':
                                                                                                    case '2':
                                                                                                    case '3':
                                                                                                    case '4':
                                                                                                    case '5':
                                                                                                    case '6':
                                                                                                    case '7':
                                                                                                    case '8':
                                                                                                    case '9':
                                                                                                        break;
                                                                                                    default:
                                                                                                        z5 = false;
                                                                                                        z6 = false;
                                                                                                        break;
                                                                                                }
                                                                                            }
                                                                                            i30++;
                                                                                            i29 = i29;
                                                                                        }
                                                                                    }
                                                                                    z4 = true;
                                                                                    z5 = false;
                                                                                    i30++;
                                                                                    i29 = i29;
                                                                                }
                                                                                i30++;
                                                                                i29 = i29;
                                                                            } else {
                                                                                z6 = z4;
                                                                                i29 = i29;
                                                                            }
                                                                        }
                                                                        if (z6) {
                                                                            iArr = iArrCopyOf;
                                                                            number(str6, Double.parseDouble(str5), str5);
                                                                        } else {
                                                                            iArr = iArrCopyOf;
                                                                            if (z5) {
                                                                                number(str6, Long.parseLong(str5), str5);
                                                                            }
                                                                        }
                                                                    }
                                                                    str5 = null;
                                                                }
                                                                i29 = i29;
                                                                iArr = iArrCopyOf;
                                                                str5 = null;
                                                            } else {
                                                                i29 = i29;
                                                                iArr = iArrCopyOf;
                                                            }
                                                            string(str6, str5);
                                                            str5 = null;
                                                        }
                                                        if (this.stop) {
                                                            runtimeException = null;
                                                        } else {
                                                            i30 = i4;
                                                            str6 = str5;
                                                            z13 = false;
                                                            iArrCopyOf = iArr;
                                                            i16 = i18;
                                                            i17 = i19;
                                                            i29 = i29;
                                                        }
                                                        break;
                                                    case 2:
                                                        startObject(str6);
                                                        if (this.stop) {
                                                            runtimeException = null;
                                                        } else {
                                                            if (i29 == iArrCopyOf.length) {
                                                                c3 = 2;
                                                                iArrCopyOf = Arrays.copyOf(iArrCopyOf, iArrCopyOf.length * 2);
                                                            } else {
                                                                c3 = 2;
                                                            }
                                                            iArrCopyOf[i29] = b2;
                                                            i29++;
                                                            c10 = c3;
                                                            i32 = i4;
                                                            i31 = 5;
                                                            z11 = z;
                                                            str6 = null;
                                                        }
                                                        break;
                                                    case 3:
                                                        pop();
                                                        if (this.stop) {
                                                            runtimeException = null;
                                                        } else {
                                                            i29--;
                                                            i20 = iArrCopyOf[i29];
                                                            i32 = i4;
                                                            z11 = z;
                                                            c10 = 2;
                                                            i31 = i20;
                                                        }
                                                        break;
                                                    case 4:
                                                        startArray(str6);
                                                        if (this.stop) {
                                                            runtimeException = null;
                                                        } else {
                                                            if (i29 == iArrCopyOf.length) {
                                                                iArrCopyOf = Arrays.copyOf(iArrCopyOf, iArrCopyOf.length * 2);
                                                            }
                                                            iArrCopyOf[i29] = b2;
                                                            i29++;
                                                            i32 = i4;
                                                            z11 = z;
                                                            c10 = 2;
                                                            str6 = null;
                                                            i31 = 23;
                                                        }
                                                        break;
                                                    case 5:
                                                        pop();
                                                        if (this.stop) {
                                                            runtimeException = null;
                                                        } else {
                                                            i29--;
                                                            i20 = iArrCopyOf[i29];
                                                            i32 = i4;
                                                            z11 = z;
                                                            c10 = 2;
                                                            i31 = i20;
                                                        }
                                                        break;
                                                    case 6:
                                                        i21 = i4 + 1;
                                                        if (cArr[i4] == '/') {
                                                            while (i21 != i2) {
                                                                i21++;
                                                            }
                                                            i4 = i21 - 1;
                                                        } else {
                                                            while (true) {
                                                                i4 = i21 + 1;
                                                                if (i4 < i2) {
                                                                    if (cArr[i21] == '*') {
                                                                        if (cArr[i4] != '/') {
                                                                        }
                                                                    }
                                                                    i21 = i4;
                                                                }
                                                            }
                                                        }
                                                        iArr = iArrCopyOf;
                                                        iArrCopyOf = iArr;
                                                        i16 = i18;
                                                        i17 = i19;
                                                        i29 = i29;
                                                        break;
                                                    case 7:
                                                        c5 = '\r';
                                                        i22 = i4;
                                                        if (z12) {
                                                            z9 = false;
                                                            do {
                                                                c7 = cArr[i22];
                                                                z8 = z9;
                                                                if (c7 == '\n') {
                                                                }
                                                            } while (i22 != i2);
                                                            i23 = i22 - 1;
                                                            while (Character.isSpace(cArr[i23])) {
                                                                i23--;
                                                            }
                                                            i30 = i4;
                                                            z = z9;
                                                            z13 = true;
                                                            i4 = i23;
                                                            iArr = iArrCopyOf;
                                                            iArrCopyOf = iArr;
                                                            i16 = i18;
                                                            i17 = i19;
                                                            i29 = i29;
                                                        } else {
                                                            z7 = false;
                                                            while (true) {
                                                                c6 = cArr[i22];
                                                                z8 = z7;
                                                                if (c6 == '\n') {
                                                                }
                                                                z7 = z8;
                                                                c5 = '\r';
                                                            }
                                                        }
                                                        z9 = z8;
                                                        i23 = i22 - 1;
                                                        while (Character.isSpace(cArr[i23])) {
                                                            i23--;
                                                        }
                                                        i30 = i4;
                                                        z = z9;
                                                        z13 = true;
                                                        i4 = i23;
                                                        iArr = iArrCopyOf;
                                                        iArrCopyOf = iArr;
                                                        i16 = i18;
                                                        i17 = i19;
                                                        i29 = i29;
                                                        break;
                                                    case 8:
                                                        i24 = i4 + 1;
                                                        i25 = i24;
                                                        z10 = false;
                                                        while (true) {
                                                            c9 = cArr[i25];
                                                            i26 = i24;
                                                            if (c9 != '\"') {
                                                                if (c9 != '\\') {
                                                                    i27 = 1;
                                                                } else {
                                                                    i25++;
                                                                    i27 = 1;
                                                                    z10 = true;
                                                                }
                                                                i25 += i27;
                                                                if (i25 != i2) {
                                                                    i24 = i26;
                                                                }
                                                            }
                                                        }
                                                        i4 = i25 - 1;
                                                        i29 = i29;
                                                        iArr = iArrCopyOf;
                                                        z = z10;
                                                        i30 = i26;
                                                        iArrCopyOf = iArr;
                                                        i16 = i18;
                                                        i17 = i19;
                                                        i29 = i29;
                                                        break;
                                                    default:
                                                        iArr = iArrCopyOf;
                                                        iArrCopyOf = iArr;
                                                        i16 = i18;
                                                        i17 = i19;
                                                        i29 = i29;
                                                        break;
                                                }
                                            } else {
                                                i29 = i29;
                                                iArrCopyOf = iArrCopyOf;
                                                i3 = i4;
                                                z11 = z;
                                                i31 = b2;
                                                if (i31 == 0) {
                                                    iArrCopyOf = iArrCopyOf;
                                                    c10 = 5;
                                                    i29 = i29;
                                                    i32 = i3;
                                                } else {
                                                    i9 = i3 + 1;
                                                    if (i9 != i2) {
                                                        iArrCopyOf = iArrCopyOf;
                                                        i32 = i9;
                                                        i29 = i29;
                                                        c10 = 1;
                                                    } else {
                                                        i32 = i9;
                                                        if (i32 == i2) {
                                                            byte b8 = _json_eof_actions[i31];
                                                            i6 = b8 + 1;
                                                            i7 = _json_actions[b8];
                                                            while (true) {
                                                                i8 = i7 - 1;
                                                                if (i7 > 0) {
                                                                    int i310 = i6 + 1;
                                                                    if (_json_actions[i6] != 1) {
                                                                        str2 = str8;
                                                                        str3 = str7;
                                                                        i5 = i32;
                                                                    } else {
                                                                        str = new String(cArr, i30, i32 - i30);
                                                                        if (z11) {
                                                                            str = unescape(str);
                                                                        }
                                                                        if (z12) {
                                                                            str2 = str8;
                                                                            str3 = str7;
                                                                            i5 = i32;
                                                                            str4 = str;
                                                                            z12 = false;
                                                                        } else {
                                                                            if (z13) {
                                                                                if (str.equals("true")) {
                                                                                    bool(str6, true);
                                                                                } else if (str.equals(str8)) {
                                                                                    bool(str6, false);
                                                                                } else {
                                                                                    if (str.equals(str7)) {
                                                                                        string(str6, null);
                                                                                    } else {
                                                                                        z2 = true;
                                                                                        z3 = false;
                                                                                        while (true) {
                                                                                            if (i30 < i32) {
                                                                                                c = cArr[i30];
                                                                                                str2 = str8;
                                                                                                if (c == '+') {
                                                                                                    if (c == 'E') {
                                                                                                        z3 = true;
                                                                                                        z2 = false;
                                                                                                    } else {
                                                                                                        z3 = true;
                                                                                                        z2 = false;
                                                                                                    }
                                                                                                }
                                                                                                i30++;
                                                                                                str8 = str2;
                                                                                            } else {
                                                                                                str2 = str8;
                                                                                            }
                                                                                        }
                                                                                        if (z3) {
                                                                                            str3 = str7;
                                                                                            i5 = i32;
                                                                                            number(str6, Double.parseDouble(str), str);
                                                                                        } else {
                                                                                            str3 = str7;
                                                                                            i5 = i32;
                                                                                            if (z2) {
                                                                                                number(str6, Long.parseLong(str), str);
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    str4 = null;
                                                                                }
                                                                                str2 = str8;
                                                                                str3 = str7;
                                                                                i5 = i32;
                                                                                str4 = null;
                                                                            } else {
                                                                                str2 = str8;
                                                                                str3 = str7;
                                                                                i5 = i32;
                                                                            }
                                                                            string(str6, str);
                                                                            str4 = null;
                                                                        }
                                                                        if (this.stop) {
                                                                            i4 = i5;
                                                                        } else {
                                                                            str6 = str4;
                                                                            i30 = i5;
                                                                            z13 = false;
                                                                        }
                                                                    }
                                                                    str7 = str3;
                                                                    i7 = i8;
                                                                    i32 = i5;
                                                                    i6 = i310;
                                                                    str8 = str2;
                                                                }
                                                                runtimeException = null;
                                                            }
                                                        }
                                                        i32 = i32;
                                                        i4 = i32;
                                                        runtimeException = null;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        i31 = b2;
                                        i30 = i33;
                                        z11 = z;
                                        if (i31 == 0) {
                                            iArrCopyOf = iArrCopyOf;
                                            c10 = 5;
                                            i29 = i29;
                                            i32 = i3;
                                        } else {
                                            i9 = i3 + 1;
                                            if (i9 != i2) {
                                                iArrCopyOf = iArrCopyOf;
                                                i32 = i9;
                                                i29 = i29;
                                                c10 = 1;
                                            } else {
                                                i32 = i9;
                                                if (i32 == i2) {
                                                    byte b9 = _json_eof_actions[i31];
                                                    i6 = b9 + 1;
                                                    i7 = _json_actions[b9];
                                                    while (true) {
                                                        i8 = i7 - 1;
                                                        if (i7 > 0) {
                                                            int i311 = i6 + 1;
                                                            if (_json_actions[i6] != 1) {
                                                                str2 = str8;
                                                                str3 = str7;
                                                                i5 = i32;
                                                            } else {
                                                                str = new String(cArr, i30, i32 - i30);
                                                                if (z11) {
                                                                    str = unescape(str);
                                                                }
                                                                if (z12) {
                                                                    str2 = str8;
                                                                    str3 = str7;
                                                                    i5 = i32;
                                                                    str4 = str;
                                                                    z12 = false;
                                                                } else {
                                                                    if (z13) {
                                                                        if (str.equals("true")) {
                                                                            bool(str6, true);
                                                                        } else if (str.equals(str8)) {
                                                                            bool(str6, false);
                                                                        } else {
                                                                            if (str.equals(str7)) {
                                                                                string(str6, null);
                                                                            } else {
                                                                                z2 = true;
                                                                                z3 = false;
                                                                                while (true) {
                                                                                    if (i30 < i32) {
                                                                                        c = cArr[i30];
                                                                                        str2 = str8;
                                                                                        if (c == '+') {
                                                                                            if (c == 'E') {
                                                                                                z3 = true;
                                                                                                z2 = false;
                                                                                            } else {
                                                                                                z3 = true;
                                                                                                z2 = false;
                                                                                            }
                                                                                        }
                                                                                        i30++;
                                                                                        str8 = str2;
                                                                                    } else {
                                                                                        str2 = str8;
                                                                                    }
                                                                                }
                                                                                if (z3) {
                                                                                    str3 = str7;
                                                                                    i5 = i32;
                                                                                    number(str6, Double.parseDouble(str), str);
                                                                                } else {
                                                                                    str3 = str7;
                                                                                    i5 = i32;
                                                                                    if (z2) {
                                                                                        number(str6, Long.parseLong(str), str);
                                                                                    }
                                                                                }
                                                                            }
                                                                            str4 = null;
                                                                        }
                                                                        str2 = str8;
                                                                        str3 = str7;
                                                                        i5 = i32;
                                                                        str4 = null;
                                                                    } else {
                                                                        str2 = str8;
                                                                        str3 = str7;
                                                                        i5 = i32;
                                                                    }
                                                                    string(str6, str);
                                                                    str4 = null;
                                                                }
                                                                if (this.stop) {
                                                                    i4 = i5;
                                                                } else {
                                                                    str6 = str4;
                                                                    i30 = i5;
                                                                    z13 = false;
                                                                }
                                                            }
                                                            str7 = str3;
                                                            i7 = i8;
                                                            i32 = i5;
                                                            i6 = i311;
                                                            str8 = str2;
                                                        }
                                                        runtimeException = null;
                                                    }
                                                }
                                                i32 = i32;
                                                i4 = i32;
                                                runtimeException = null;
                                            }
                                        }
                                    }
                                }
                            } catch (RuntimeException e3) {
                                e = e3;
                                i4 = i32;
                                runtimeException = e;
                                JsonValue jsonValue4 = this.root;
                                this.root = null;
                                this.current = null;
                                this.lastChild.clear();
                                if (!this.stop) {
                                    if (i4 < i2) {
                                        i11 = 1;
                                        while (i10 < i4) {
                                            if (cArr[i10] == '\n') {
                                                i11++;
                                            }
                                        }
                                        int iMax4 = Math.max(0, i4 - 32);
                                        throw new SerializationException("Error parsing JSON on line " + i11 + " near: " + new String(cArr, iMax4, i4 - iMax4) + "*ERROR*" + new String(cArr, i4, Math.min(64, i2 - i4)), runtimeException);
                                    }
                                    if (this.elements.size != 0) {
                                        jsonValuePeek = this.elements.peek();
                                        this.elements.clear();
                                        if (jsonValuePeek == null) {
                                        }
                                        throw new SerializationException("Error parsing JSON, unmatched bracket.");
                                    }
                                    if (runtimeException != null) {
                                        throw new SerializationException("Error parsing JSON: ".concat(new String(cArr)), runtimeException);
                                    }
                                }
                                return jsonValue4;
                            }
                        } else {
                            i36 = i38;
                            i37 = s2 + b6;
                            b = _json_range_lengths[i31];
                            i28 = i37;
                            if (b > 0) {
                                i12 = ((b << 1) + i36) - 2;
                                i13 = i36;
                                while (true) {
                                    if (i12 < i13) {
                                        i28 = i37 + b;
                                        i3 = i32;
                                        i15 = i28;
                                    } else {
                                        i14 = i13 + (((i12 - i13) >> 1) & (-2));
                                        byte b10 = b;
                                        c2 = cArr[i32];
                                        cArr2 = _json_trans_keys;
                                        i3 = i32;
                                        if (c2 < cArr2[i14]) {
                                            i12 = i14 - 2;
                                        } else if (c2 > cArr2[i14 + 1]) {
                                            i13 = i14 + 2;
                                        } else {
                                            i15 = i37 + ((i14 - i36) >> 1);
                                        }
                                        b = b10;
                                        i32 = i3;
                                        i13 = i13;
                                    }
                                }
                            } else {
                                i3 = i32;
                                i15 = i28;
                            }
                            try {
                                byte b11 = _json_indicies[i15];
                                b2 = _json_trans_targs[b11];
                                b3 = _json_trans_actions[b11];
                                if (b3 != 0) {
                                    int i45 = b3 + 1;
                                    i16 = _json_actions[b3];
                                    i17 = i45;
                                    i30 = i33;
                                    i4 = i3;
                                    while (true) {
                                        i18 = i16 - 1;
                                        if (i16 > 0) {
                                            try {
                                                i19 = i17 + 1;
                                                switch (_json_actions[i17]) {
                                                    case 0:
                                                        i29 = i29;
                                                        iArr = iArrCopyOf;
                                                        z12 = true;
                                                        iArrCopyOf = iArr;
                                                        i16 = i18;
                                                        i17 = i19;
                                                        i29 = i29;
                                                        break;
                                                    case 1:
                                                        str5 = new String(cArr, i30, i4 - i30);
                                                        if (z) {
                                                            str5 = unescape(str5);
                                                        }
                                                        if (z12) {
                                                            i29 = i29;
                                                            iArr = iArrCopyOf;
                                                            z12 = false;
                                                        } else {
                                                            if (z13) {
                                                                if (str5.equals("true")) {
                                                                    bool(str6, true);
                                                                } else if (str5.equals("false")) {
                                                                    bool(str6, false);
                                                                } else {
                                                                    if (str5.equals("null")) {
                                                                        string(str6, null);
                                                                    } else {
                                                                        z4 = false;
                                                                        z5 = true;
                                                                        while (true) {
                                                                            if (i30 < i4) {
                                                                                z4 = z4;
                                                                                c4 = cArr[i30];
                                                                                i29 = i29;
                                                                                if (c4 != '+') {
                                                                                    if (c4 != 'E' && c4 != 'e') {
                                                                                        if (c4 != '-') {
                                                                                            if (c4 != '.') {
                                                                                                switch (c4) {
                                                                                                    case '0':
                                                                                                    case '1':
                                                                                                    case '2':
                                                                                                    case '3':
                                                                                                    case '4':
                                                                                                    case '5':
                                                                                                    case '6':
                                                                                                    case '7':
                                                                                                    case '8':
                                                                                                    case '9':
                                                                                                        break;
                                                                                                    default:
                                                                                                        z5 = false;
                                                                                                        z6 = false;
                                                                                                        break;
                                                                                                }
                                                                                            }
                                                                                            i30++;
                                                                                            i29 = i29;
                                                                                        }
                                                                                    }
                                                                                    z4 = true;
                                                                                    z5 = false;
                                                                                    i30++;
                                                                                    i29 = i29;
                                                                                }
                                                                                i30++;
                                                                                i29 = i29;
                                                                            } else {
                                                                                z6 = z4;
                                                                                i29 = i29;
                                                                            }
                                                                        }
                                                                        if (z6) {
                                                                            iArr = iArrCopyOf;
                                                                            try {
                                                                                number(str6, Double.parseDouble(str5), str5);
                                                                            } catch (NumberFormatException unused2) {
                                                                                string(str6, str5);
                                                                            }
                                                                        } else {
                                                                            iArr = iArrCopyOf;
                                                                            if (z5) {
                                                                                number(str6, Long.parseLong(str5), str5);
                                                                            }
                                                                        }
                                                                    }
                                                                    str5 = null;
                                                                }
                                                                i29 = i29;
                                                                iArr = iArrCopyOf;
                                                                str5 = null;
                                                            } else {
                                                                i29 = i29;
                                                                iArr = iArrCopyOf;
                                                            }
                                                            string(str6, str5);
                                                            str5 = null;
                                                        }
                                                        if (this.stop) {
                                                            runtimeException = null;
                                                        } else {
                                                            i30 = i4;
                                                            str6 = str5;
                                                            z13 = false;
                                                            iArrCopyOf = iArr;
                                                            i16 = i18;
                                                            i17 = i19;
                                                            i29 = i29;
                                                        }
                                                        break;
                                                    case 2:
                                                        startObject(str6);
                                                        if (this.stop) {
                                                            runtimeException = null;
                                                        } else {
                                                            if (i29 == iArrCopyOf.length) {
                                                                c3 = 2;
                                                                iArrCopyOf = Arrays.copyOf(iArrCopyOf, iArrCopyOf.length * 2);
                                                            } else {
                                                                c3 = 2;
                                                            }
                                                            iArrCopyOf[i29] = b2;
                                                            i29++;
                                                            c10 = c3;
                                                            i32 = i4;
                                                            i31 = 5;
                                                            z11 = z;
                                                            str6 = null;
                                                        }
                                                        break;
                                                    case 3:
                                                        pop();
                                                        if (this.stop) {
                                                            runtimeException = null;
                                                        } else {
                                                            i29--;
                                                            i20 = iArrCopyOf[i29];
                                                            i32 = i4;
                                                            z11 = z;
                                                            c10 = 2;
                                                            i31 = i20;
                                                        }
                                                        break;
                                                    case 4:
                                                        startArray(str6);
                                                        if (this.stop) {
                                                            runtimeException = null;
                                                        } else {
                                                            if (i29 == iArrCopyOf.length) {
                                                                iArrCopyOf = Arrays.copyOf(iArrCopyOf, iArrCopyOf.length * 2);
                                                            }
                                                            iArrCopyOf[i29] = b2;
                                                            i29++;
                                                            i32 = i4;
                                                            z11 = z;
                                                            c10 = 2;
                                                            str6 = null;
                                                            i31 = 23;
                                                        }
                                                        break;
                                                    case 5:
                                                        pop();
                                                        if (this.stop) {
                                                            runtimeException = null;
                                                        } else {
                                                            i29--;
                                                            i20 = iArrCopyOf[i29];
                                                            i32 = i4;
                                                            z11 = z;
                                                            c10 = 2;
                                                            i31 = i20;
                                                        }
                                                        break;
                                                    case 6:
                                                        i21 = i4 + 1;
                                                        if (cArr[i4] == '/') {
                                                            while (i21 != i2 && cArr[i21] != '\n') {
                                                                i21++;
                                                            }
                                                            i4 = i21 - 1;
                                                        } else {
                                                            while (true) {
                                                                i4 = i21 + 1;
                                                                if (i4 < i2) {
                                                                    try {
                                                                        if (cArr[i21] == '*') {
                                                                            try {
                                                                                if (cArr[i4] != '/') {
                                                                                }
                                                                            } catch (RuntimeException e4) {
                                                                                e = e4;
                                                                                runtimeException = e;
                                                                                i4 = i21;
                                                                                JsonValue jsonValue5 = this.root;
                                                                                this.root = null;
                                                                                this.current = null;
                                                                                this.lastChild.clear();
                                                                                if (!this.stop) {
                                                                                    if (i4 < i2) {
                                                                                        i11 = 1;
                                                                                        while (i10 < i4) {
                                                                                            if (cArr[i10] == '\n') {
                                                                                                i11++;
                                                                                            }
                                                                                        }
                                                                                        int iMax5 = Math.max(0, i4 - 32);
                                                                                        throw new SerializationException("Error parsing JSON on line " + i11 + " near: " + new String(cArr, iMax5, i4 - iMax5) + "*ERROR*" + new String(cArr, i4, Math.min(64, i2 - i4)), runtimeException);
                                                                                    }
                                                                                    if (this.elements.size != 0) {
                                                                                        jsonValuePeek = this.elements.peek();
                                                                                        this.elements.clear();
                                                                                        if (jsonValuePeek == null) {
                                                                                            break;
                                                                                        }
                                                                                        throw new SerializationException("Error parsing JSON, unmatched bracket.");
                                                                                    }
                                                                                    if (runtimeException != null) {
                                                                                        throw new SerializationException("Error parsing JSON: ".concat(new String(cArr)), runtimeException);
                                                                                    }
                                                                                }
                                                                                return jsonValue5;
                                                                            }
                                                                        }
                                                                        i21 = i4;
                                                                    } catch (RuntimeException e5) {
                                                                        e = e5;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        iArr = iArrCopyOf;
                                                        iArrCopyOf = iArr;
                                                        i16 = i18;
                                                        i17 = i19;
                                                        i29 = i29;
                                                        break;
                                                    case 7:
                                                        c5 = '\r';
                                                        i22 = i4;
                                                        if (z12) {
                                                            z9 = false;
                                                            do {
                                                                try {
                                                                    c7 = cArr[i22];
                                                                    z8 = z9;
                                                                    if (c7 == '\n' && c7 != '\r') {
                                                                        if (c7 == '/') {
                                                                            int i46 = i22 + 1;
                                                                            if (i46 == i2 || ((c8 = cArr[i46]) != '/' && c8 != '*')) {
                                                                                i22++;
                                                                            }
                                                                            i23 = i22 - 1;
                                                                            while (Character.isSpace(cArr[i23])) {
                                                                                i23--;
                                                                            }
                                                                            i30 = i4;
                                                                            z = z9;
                                                                            z13 = true;
                                                                            i4 = i23;
                                                                            iArr = iArrCopyOf;
                                                                        } else if (c7 != ':') {
                                                                            z9 = c7 != '\\' ? z8 : true;
                                                                            i22++;
                                                                        }
                                                                        iArrCopyOf = iArr;
                                                                        i16 = i18;
                                                                        i17 = i19;
                                                                        i29 = i29;
                                                                        break;
                                                                    }
                                                                } catch (RuntimeException e6) {
                                                                    runtimeException = e6;
                                                                    i4 = i22;
                                                                    break;
                                                                }
                                                            } while (i22 != i2);
                                                            i23 = i22 - 1;
                                                            while (Character.isSpace(cArr[i23])) {
                                                                i23--;
                                                            }
                                                            i30 = i4;
                                                            z = z9;
                                                            z13 = true;
                                                            i4 = i23;
                                                            iArr = iArrCopyOf;
                                                            iArrCopyOf = iArr;
                                                            i16 = i18;
                                                            i17 = i19;
                                                            i29 = i29;
                                                            break;
                                                        } else {
                                                            z7 = false;
                                                            while (true) {
                                                                c6 = cArr[i22];
                                                                z8 = z7;
                                                                if (c6 == '\n' && c6 != c5 && c6 != ',') {
                                                                    if (c6 == '/') {
                                                                        int i47 = i22 + 1;
                                                                        if (i47 != i2) {
                                                                            char c13 = cArr[i47];
                                                                            if (c13 == '/' || c13 == '*') {
                                                                            }
                                                                        }
                                                                        i22++;
                                                                        if (i22 == i2) {
                                                                            z7 = z8;
                                                                            c5 = '\r';
                                                                        }
                                                                    } else if (c6 != '}') {
                                                                        if (c6 == '\\') {
                                                                            z8 = true;
                                                                        } else if (c6 != ']') {
                                                                        }
                                                                        i22++;
                                                                        if (i22 == i2) {
                                                                            z7 = z8;
                                                                            c5 = '\r';
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        z9 = z8;
                                                        i23 = i22 - 1;
                                                        while (Character.isSpace(cArr[i23])) {
                                                            i23--;
                                                        }
                                                        i30 = i4;
                                                        z = z9;
                                                        z13 = true;
                                                        i4 = i23;
                                                        iArr = iArrCopyOf;
                                                        iArrCopyOf = iArr;
                                                        i16 = i18;
                                                        i17 = i19;
                                                        i29 = i29;
                                                        break;
                                                    case 8:
                                                        i24 = i4 + 1;
                                                        i25 = i24;
                                                        z10 = false;
                                                        while (true) {
                                                            c9 = cArr[i25];
                                                            i26 = i24;
                                                            if (c9 != '\"') {
                                                                if (c9 != '\\') {
                                                                    i27 = 1;
                                                                } else {
                                                                    i25++;
                                                                    i27 = 1;
                                                                    z10 = true;
                                                                }
                                                                i25 += i27;
                                                                if (i25 != i2) {
                                                                    i24 = i26;
                                                                }
                                                            }
                                                        }
                                                        i4 = i25 - 1;
                                                        i29 = i29;
                                                        iArr = iArrCopyOf;
                                                        z = z10;
                                                        i30 = i26;
                                                        iArrCopyOf = iArr;
                                                        i16 = i18;
                                                        i17 = i19;
                                                        i29 = i29;
                                                        break;
                                                    default:
                                                        iArr = iArrCopyOf;
                                                        iArrCopyOf = iArr;
                                                        i16 = i18;
                                                        i17 = i19;
                                                        i29 = i29;
                                                        break;
                                                }
                                            } catch (RuntimeException e7) {
                                                e = e7;
                                                runtimeException = e;
                                            }
                                        } else {
                                            i29 = i29;
                                            iArrCopyOf = iArrCopyOf;
                                            i3 = i4;
                                            z11 = z;
                                            i31 = b2;
                                            if (i31 == 0) {
                                                iArrCopyOf = iArrCopyOf;
                                                c10 = 5;
                                                i29 = i29;
                                                i32 = i3;
                                            } else {
                                                i9 = i3 + 1;
                                                if (i9 != i2) {
                                                    iArrCopyOf = iArrCopyOf;
                                                    i32 = i9;
                                                    i29 = i29;
                                                    c10 = 1;
                                                } else {
                                                    i32 = i9;
                                                    if (i32 == i2) {
                                                        byte b12 = _json_eof_actions[i31];
                                                        i6 = b12 + 1;
                                                        i7 = _json_actions[b12];
                                                        while (true) {
                                                            i8 = i7 - 1;
                                                            if (i7 > 0) {
                                                                int i312 = i6 + 1;
                                                                if (_json_actions[i6] != 1) {
                                                                    str2 = str8;
                                                                    str3 = str7;
                                                                    i5 = i32;
                                                                } else {
                                                                    str = new String(cArr, i30, i32 - i30);
                                                                    if (z11) {
                                                                        str = unescape(str);
                                                                    }
                                                                    if (z12) {
                                                                        str2 = str8;
                                                                        str3 = str7;
                                                                        i5 = i32;
                                                                        str4 = str;
                                                                        z12 = false;
                                                                    } else {
                                                                        if (z13) {
                                                                            if (str.equals("true")) {
                                                                                bool(str6, true);
                                                                            } else if (str.equals(str8)) {
                                                                                bool(str6, false);
                                                                            } else {
                                                                                if (str.equals(str7)) {
                                                                                    string(str6, null);
                                                                                } else {
                                                                                    z2 = true;
                                                                                    z3 = false;
                                                                                    while (true) {
                                                                                        if (i30 < i32) {
                                                                                            c = cArr[i30];
                                                                                            str2 = str8;
                                                                                            if (c == '+') {
                                                                                                if (c == 'E') {
                                                                                                    z3 = true;
                                                                                                    z2 = false;
                                                                                                } else {
                                                                                                    z3 = true;
                                                                                                    z2 = false;
                                                                                                }
                                                                                            }
                                                                                            i30++;
                                                                                            str8 = str2;
                                                                                        } else {
                                                                                            str2 = str8;
                                                                                        }
                                                                                    }
                                                                                    if (z3) {
                                                                                        str3 = str7;
                                                                                        i5 = i32;
                                                                                        number(str6, Double.parseDouble(str), str);
                                                                                    } else {
                                                                                        str3 = str7;
                                                                                        i5 = i32;
                                                                                        if (z2) {
                                                                                            number(str6, Long.parseLong(str), str);
                                                                                        }
                                                                                    }
                                                                                }
                                                                                str4 = null;
                                                                            }
                                                                            str2 = str8;
                                                                            str3 = str7;
                                                                            i5 = i32;
                                                                            str4 = null;
                                                                        } else {
                                                                            str2 = str8;
                                                                            str3 = str7;
                                                                            i5 = i32;
                                                                        }
                                                                        string(str6, str);
                                                                        str4 = null;
                                                                    }
                                                                    if (this.stop) {
                                                                        i4 = i5;
                                                                    } else {
                                                                        str6 = str4;
                                                                        i30 = i5;
                                                                        z13 = false;
                                                                    }
                                                                }
                                                                str7 = str3;
                                                                i7 = i8;
                                                                i32 = i5;
                                                                i6 = i312;
                                                                str8 = str2;
                                                            }
                                                            runtimeException = null;
                                                        }
                                                    }
                                                    i32 = i32;
                                                    i4 = i32;
                                                    runtimeException = null;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    i31 = b2;
                                    i30 = i33;
                                    z11 = z;
                                    if (i31 == 0) {
                                        iArrCopyOf = iArrCopyOf;
                                        c10 = 5;
                                        i29 = i29;
                                        i32 = i3;
                                    } else {
                                        i9 = i3 + 1;
                                        if (i9 != i2) {
                                            iArrCopyOf = iArrCopyOf;
                                            i32 = i9;
                                            i29 = i29;
                                            c10 = 1;
                                        } else {
                                            i32 = i9;
                                            if (i32 == i2) {
                                                byte b13 = _json_eof_actions[i31];
                                                i6 = b13 + 1;
                                                i7 = _json_actions[b13];
                                                while (true) {
                                                    i8 = i7 - 1;
                                                    if (i7 > 0) {
                                                        int i313 = i6 + 1;
                                                        if (_json_actions[i6] != 1) {
                                                            str2 = str8;
                                                            str3 = str7;
                                                            i5 = i32;
                                                        } else {
                                                            str = new String(cArr, i30, i32 - i30);
                                                            if (z11) {
                                                                str = unescape(str);
                                                            }
                                                            if (z12) {
                                                                str2 = str8;
                                                                str3 = str7;
                                                                i5 = i32;
                                                                str4 = str;
                                                                z12 = false;
                                                            } else {
                                                                if (z13) {
                                                                    if (str.equals("true")) {
                                                                        bool(str6, true);
                                                                    } else if (str.equals(str8)) {
                                                                        bool(str6, false);
                                                                    } else {
                                                                        if (str.equals(str7)) {
                                                                            string(str6, null);
                                                                        } else {
                                                                            z2 = true;
                                                                            z3 = false;
                                                                            while (true) {
                                                                                if (i30 < i32) {
                                                                                    c = cArr[i30];
                                                                                    str2 = str8;
                                                                                    if (c == '+') {
                                                                                        if (c == 'E') {
                                                                                            z3 = true;
                                                                                            z2 = false;
                                                                                        } else {
                                                                                            z3 = true;
                                                                                            z2 = false;
                                                                                        }
                                                                                    }
                                                                                    i30++;
                                                                                    str8 = str2;
                                                                                } else {
                                                                                    str2 = str8;
                                                                                }
                                                                            }
                                                                            if (z3) {
                                                                                str3 = str7;
                                                                                i5 = i32;
                                                                                number(str6, Double.parseDouble(str), str);
                                                                            } else {
                                                                                str3 = str7;
                                                                                i5 = i32;
                                                                                if (z2) {
                                                                                    number(str6, Long.parseLong(str), str);
                                                                                }
                                                                            }
                                                                        }
                                                                        str4 = null;
                                                                    }
                                                                    str2 = str8;
                                                                    str3 = str7;
                                                                    i5 = i32;
                                                                    str4 = null;
                                                                } else {
                                                                    str2 = str8;
                                                                    str3 = str7;
                                                                    i5 = i32;
                                                                }
                                                                string(str6, str);
                                                                str4 = null;
                                                            }
                                                            if (this.stop) {
                                                                i4 = i5;
                                                            } else {
                                                                str6 = str4;
                                                                i30 = i5;
                                                                z13 = false;
                                                            }
                                                        }
                                                        str7 = str3;
                                                        i7 = i8;
                                                        i32 = i5;
                                                        i6 = i313;
                                                        str8 = str2;
                                                    }
                                                    runtimeException = null;
                                                }
                                            }
                                            i32 = i32;
                                            i4 = i32;
                                            runtimeException = null;
                                        }
                                    }
                                }
                            } catch (RuntimeException e8) {
                                e = e8;
                                runtimeException = e;
                                i4 = i3;
                            }
                        }
                    }
                } else {
                    b = _json_range_lengths[i31];
                    i28 = i37;
                    if (b > 0) {
                        i12 = ((b << 1) + i36) - 2;
                        i13 = i36;
                        while (true) {
                            if (i12 < i13) {
                                i28 = i37 + b;
                                i3 = i32;
                                i15 = i28;
                            } else {
                                i14 = i13 + (((i12 - i13) >> 1) & (-2));
                                byte b14 = b;
                                c2 = cArr[i32];
                                cArr2 = _json_trans_keys;
                                i3 = i32;
                                if (c2 < cArr2[i14]) {
                                    i12 = i14 - 2;
                                } else if (c2 > cArr2[i14 + 1]) {
                                    i13 = i14 + 2;
                                } else {
                                    i15 = i37 + ((i14 - i36) >> 1);
                                }
                                b = b14;
                                i32 = i3;
                                i13 = i13;
                            }
                        }
                    } else {
                        i3 = i32;
                        i15 = i28;
                    }
                    byte b15 = _json_indicies[i15];
                    b2 = _json_trans_targs[b15];
                    b3 = _json_trans_actions[b15];
                    if (b3 != 0) {
                        int i48 = b3 + 1;
                        i16 = _json_actions[b3];
                        i17 = i48;
                        i30 = i33;
                        i4 = i3;
                        while (true) {
                            i18 = i16 - 1;
                            if (i16 > 0) {
                                i19 = i17 + 1;
                                switch (_json_actions[i17]) {
                                    case 0:
                                        i29 = i29;
                                        iArr = iArrCopyOf;
                                        z12 = true;
                                        iArrCopyOf = iArr;
                                        i16 = i18;
                                        i17 = i19;
                                        i29 = i29;
                                        break;
                                    case 1:
                                        str5 = new String(cArr, i30, i4 - i30);
                                        if (z) {
                                            str5 = unescape(str5);
                                        }
                                        if (z12) {
                                            i29 = i29;
                                            iArr = iArrCopyOf;
                                            z12 = false;
                                        } else {
                                            if (z13) {
                                                if (str5.equals("true")) {
                                                    bool(str6, true);
                                                } else if (str5.equals("false")) {
                                                    bool(str6, false);
                                                } else {
                                                    if (str5.equals("null")) {
                                                        string(str6, null);
                                                    } else {
                                                        z4 = false;
                                                        z5 = true;
                                                        while (true) {
                                                            if (i30 < i4) {
                                                                z4 = z4;
                                                                c4 = cArr[i30];
                                                                i29 = i29;
                                                                if (c4 != '+') {
                                                                    if (c4 != 'E') {
                                                                        if (c4 != '-') {
                                                                            if (c4 != '.') {
                                                                                switch (c4) {
                                                                                    case '0':
                                                                                    case '1':
                                                                                    case '2':
                                                                                    case '3':
                                                                                    case '4':
                                                                                    case '5':
                                                                                    case '6':
                                                                                    case '7':
                                                                                    case '8':
                                                                                    case '9':
                                                                                        break;
                                                                                    default:
                                                                                        z5 = false;
                                                                                        z6 = false;
                                                                                        break;
                                                                                }
                                                                            }
                                                                            i30++;
                                                                            i29 = i29;
                                                                        }
                                                                    }
                                                                    z4 = true;
                                                                    z5 = false;
                                                                    i30++;
                                                                    i29 = i29;
                                                                }
                                                                i30++;
                                                                i29 = i29;
                                                            } else {
                                                                z6 = z4;
                                                                i29 = i29;
                                                            }
                                                        }
                                                        if (z6) {
                                                            iArr = iArrCopyOf;
                                                            number(str6, Double.parseDouble(str5), str5);
                                                        } else {
                                                            iArr = iArrCopyOf;
                                                            if (z5) {
                                                                number(str6, Long.parseLong(str5), str5);
                                                            }
                                                        }
                                                    }
                                                    str5 = null;
                                                }
                                                i29 = i29;
                                                iArr = iArrCopyOf;
                                                str5 = null;
                                            } else {
                                                i29 = i29;
                                                iArr = iArrCopyOf;
                                            }
                                            string(str6, str5);
                                            str5 = null;
                                        }
                                        if (this.stop) {
                                            runtimeException = null;
                                        } else {
                                            i30 = i4;
                                            str6 = str5;
                                            z13 = false;
                                            iArrCopyOf = iArr;
                                            i16 = i18;
                                            i17 = i19;
                                            i29 = i29;
                                        }
                                        break;
                                    case 2:
                                        startObject(str6);
                                        if (this.stop) {
                                            runtimeException = null;
                                        } else {
                                            if (i29 == iArrCopyOf.length) {
                                                c3 = 2;
                                                iArrCopyOf = Arrays.copyOf(iArrCopyOf, iArrCopyOf.length * 2);
                                            } else {
                                                c3 = 2;
                                            }
                                            iArrCopyOf[i29] = b2;
                                            i29++;
                                            c10 = c3;
                                            i32 = i4;
                                            i31 = 5;
                                            z11 = z;
                                            str6 = null;
                                        }
                                        break;
                                    case 3:
                                        pop();
                                        if (this.stop) {
                                            runtimeException = null;
                                        } else {
                                            i29--;
                                            i20 = iArrCopyOf[i29];
                                            i32 = i4;
                                            z11 = z;
                                            c10 = 2;
                                            i31 = i20;
                                        }
                                        break;
                                    case 4:
                                        startArray(str6);
                                        if (this.stop) {
                                            runtimeException = null;
                                        } else {
                                            if (i29 == iArrCopyOf.length) {
                                                iArrCopyOf = Arrays.copyOf(iArrCopyOf, iArrCopyOf.length * 2);
                                            }
                                            iArrCopyOf[i29] = b2;
                                            i29++;
                                            i32 = i4;
                                            z11 = z;
                                            c10 = 2;
                                            str6 = null;
                                            i31 = 23;
                                        }
                                        break;
                                    case 5:
                                        pop();
                                        if (this.stop) {
                                            runtimeException = null;
                                        } else {
                                            i29--;
                                            i20 = iArrCopyOf[i29];
                                            i32 = i4;
                                            z11 = z;
                                            c10 = 2;
                                            i31 = i20;
                                        }
                                        break;
                                    case 6:
                                        i21 = i4 + 1;
                                        if (cArr[i4] == '/') {
                                            while (i21 != i2) {
                                                i21++;
                                            }
                                            i4 = i21 - 1;
                                        } else {
                                            while (true) {
                                                i4 = i21 + 1;
                                                if (i4 < i2) {
                                                    if (cArr[i21] == '*') {
                                                        if (cArr[i4] != '/') {
                                                        }
                                                    }
                                                    i21 = i4;
                                                }
                                            }
                                        }
                                        iArr = iArrCopyOf;
                                        iArrCopyOf = iArr;
                                        i16 = i18;
                                        i17 = i19;
                                        i29 = i29;
                                        break;
                                    case 7:
                                        c5 = '\r';
                                        i22 = i4;
                                        if (z12) {
                                            z9 = false;
                                            do {
                                                c7 = cArr[i22];
                                                z8 = z9;
                                                if (c7 == '\n') {
                                                }
                                            } while (i22 != i2);
                                            i23 = i22 - 1;
                                            while (Character.isSpace(cArr[i23])) {
                                                i23--;
                                            }
                                            i30 = i4;
                                            z = z9;
                                            z13 = true;
                                            i4 = i23;
                                            iArr = iArrCopyOf;
                                            iArrCopyOf = iArr;
                                            i16 = i18;
                                            i17 = i19;
                                            i29 = i29;
                                        } else {
                                            z7 = false;
                                            while (true) {
                                                c6 = cArr[i22];
                                                z8 = z7;
                                                if (c6 == '\n') {
                                                }
                                                z7 = z8;
                                                c5 = '\r';
                                            }
                                        }
                                        z9 = z8;
                                        i23 = i22 - 1;
                                        while (Character.isSpace(cArr[i23])) {
                                            i23--;
                                        }
                                        i30 = i4;
                                        z = z9;
                                        z13 = true;
                                        i4 = i23;
                                        iArr = iArrCopyOf;
                                        iArrCopyOf = iArr;
                                        i16 = i18;
                                        i17 = i19;
                                        i29 = i29;
                                        break;
                                    case 8:
                                        i24 = i4 + 1;
                                        i25 = i24;
                                        z10 = false;
                                        while (true) {
                                            c9 = cArr[i25];
                                            i26 = i24;
                                            if (c9 != '\"') {
                                                if (c9 != '\\') {
                                                    i27 = 1;
                                                } else {
                                                    i25++;
                                                    i27 = 1;
                                                    z10 = true;
                                                }
                                                i25 += i27;
                                                if (i25 != i2) {
                                                    i24 = i26;
                                                }
                                            }
                                        }
                                        i4 = i25 - 1;
                                        i29 = i29;
                                        iArr = iArrCopyOf;
                                        z = z10;
                                        i30 = i26;
                                        iArrCopyOf = iArr;
                                        i16 = i18;
                                        i17 = i19;
                                        i29 = i29;
                                        break;
                                    default:
                                        iArr = iArrCopyOf;
                                        iArrCopyOf = iArr;
                                        i16 = i18;
                                        i17 = i19;
                                        i29 = i29;
                                        break;
                                }
                            } else {
                                i29 = i29;
                                iArrCopyOf = iArrCopyOf;
                                i3 = i4;
                                z11 = z;
                                i31 = b2;
                                if (i31 == 0) {
                                    iArrCopyOf = iArrCopyOf;
                                    c10 = 5;
                                    i29 = i29;
                                    i32 = i3;
                                } else {
                                    i9 = i3 + 1;
                                    if (i9 != i2) {
                                        iArrCopyOf = iArrCopyOf;
                                        i32 = i9;
                                        i29 = i29;
                                        c10 = 1;
                                    } else {
                                        i32 = i9;
                                        if (i32 == i2) {
                                            byte b16 = _json_eof_actions[i31];
                                            i6 = b16 + 1;
                                            i7 = _json_actions[b16];
                                            while (true) {
                                                i8 = i7 - 1;
                                                if (i7 > 0) {
                                                    int i314 = i6 + 1;
                                                    if (_json_actions[i6] != 1) {
                                                        str2 = str8;
                                                        str3 = str7;
                                                        i5 = i32;
                                                    } else {
                                                        str = new String(cArr, i30, i32 - i30);
                                                        if (z11) {
                                                            str = unescape(str);
                                                        }
                                                        if (z12) {
                                                            str2 = str8;
                                                            str3 = str7;
                                                            i5 = i32;
                                                            str4 = str;
                                                            z12 = false;
                                                        } else {
                                                            if (z13) {
                                                                if (str.equals("true")) {
                                                                    bool(str6, true);
                                                                } else if (str.equals(str8)) {
                                                                    bool(str6, false);
                                                                } else {
                                                                    if (str.equals(str7)) {
                                                                        string(str6, null);
                                                                    } else {
                                                                        z2 = true;
                                                                        z3 = false;
                                                                        while (true) {
                                                                            if (i30 < i32) {
                                                                                c = cArr[i30];
                                                                                str2 = str8;
                                                                                if (c == '+') {
                                                                                    if (c == 'E') {
                                                                                        z3 = true;
                                                                                        z2 = false;
                                                                                    } else {
                                                                                        z3 = true;
                                                                                        z2 = false;
                                                                                    }
                                                                                }
                                                                                i30++;
                                                                                str8 = str2;
                                                                            } else {
                                                                                str2 = str8;
                                                                            }
                                                                        }
                                                                        if (z3) {
                                                                            str3 = str7;
                                                                            i5 = i32;
                                                                            number(str6, Double.parseDouble(str), str);
                                                                        } else {
                                                                            str3 = str7;
                                                                            i5 = i32;
                                                                            if (z2) {
                                                                                number(str6, Long.parseLong(str), str);
                                                                            }
                                                                        }
                                                                    }
                                                                    str4 = null;
                                                                }
                                                                str2 = str8;
                                                                str3 = str7;
                                                                i5 = i32;
                                                                str4 = null;
                                                            } else {
                                                                str2 = str8;
                                                                str3 = str7;
                                                                i5 = i32;
                                                            }
                                                            string(str6, str);
                                                            str4 = null;
                                                        }
                                                        if (this.stop) {
                                                            i4 = i5;
                                                        } else {
                                                            str6 = str4;
                                                            i30 = i5;
                                                            z13 = false;
                                                        }
                                                    }
                                                    str7 = str3;
                                                    i7 = i8;
                                                    i32 = i5;
                                                    i6 = i314;
                                                    str8 = str2;
                                                }
                                                runtimeException = null;
                                            }
                                        }
                                        i32 = i32;
                                        i4 = i32;
                                        runtimeException = null;
                                    }
                                }
                            }
                        }
                    } else {
                        i31 = b2;
                        i30 = i33;
                        z11 = z;
                        if (i31 == 0) {
                            iArrCopyOf = iArrCopyOf;
                            c10 = 5;
                            i29 = i29;
                            i32 = i3;
                        } else {
                            i9 = i3 + 1;
                            if (i9 != i2) {
                                iArrCopyOf = iArrCopyOf;
                                i32 = i9;
                                i29 = i29;
                                c10 = 1;
                            } else {
                                i32 = i9;
                                if (i32 == i2) {
                                    byte b17 = _json_eof_actions[i31];
                                    i6 = b17 + 1;
                                    i7 = _json_actions[b17];
                                    while (true) {
                                        i8 = i7 - 1;
                                        if (i7 > 0) {
                                            int i315 = i6 + 1;
                                            if (_json_actions[i6] != 1) {
                                                str2 = str8;
                                                str3 = str7;
                                                i5 = i32;
                                            } else {
                                                str = new String(cArr, i30, i32 - i30);
                                                if (z11) {
                                                    str = unescape(str);
                                                }
                                                if (z12) {
                                                    str2 = str8;
                                                    str3 = str7;
                                                    i5 = i32;
                                                    str4 = str;
                                                    z12 = false;
                                                } else {
                                                    if (z13) {
                                                        if (str.equals("true")) {
                                                            bool(str6, true);
                                                        } else if (str.equals(str8)) {
                                                            bool(str6, false);
                                                        } else {
                                                            if (str.equals(str7)) {
                                                                string(str6, null);
                                                            } else {
                                                                z2 = true;
                                                                z3 = false;
                                                                while (true) {
                                                                    if (i30 < i32) {
                                                                        c = cArr[i30];
                                                                        str2 = str8;
                                                                        if (c == '+') {
                                                                            if (c == 'E') {
                                                                                z3 = true;
                                                                                z2 = false;
                                                                            } else {
                                                                                z3 = true;
                                                                                z2 = false;
                                                                            }
                                                                        }
                                                                        i30++;
                                                                        str8 = str2;
                                                                    } else {
                                                                        str2 = str8;
                                                                    }
                                                                }
                                                                if (z3) {
                                                                    str3 = str7;
                                                                    i5 = i32;
                                                                    number(str6, Double.parseDouble(str), str);
                                                                } else {
                                                                    str3 = str7;
                                                                    i5 = i32;
                                                                    if (z2) {
                                                                        number(str6, Long.parseLong(str), str);
                                                                    }
                                                                }
                                                            }
                                                            str4 = null;
                                                        }
                                                        str2 = str8;
                                                        str3 = str7;
                                                        i5 = i32;
                                                        str4 = null;
                                                    } else {
                                                        str2 = str8;
                                                        str3 = str7;
                                                        i5 = i32;
                                                    }
                                                    string(str6, str);
                                                    str4 = null;
                                                }
                                                if (this.stop) {
                                                    i4 = i5;
                                                } else {
                                                    str6 = str4;
                                                    i30 = i5;
                                                    z13 = false;
                                                }
                                            }
                                            str7 = str3;
                                            i7 = i8;
                                            i32 = i5;
                                            i6 = i315;
                                            str8 = str2;
                                        }
                                        runtimeException = null;
                                    }
                                }
                                i32 = i32;
                                i4 = i32;
                                runtimeException = null;
                            }
                        }
                    }
                }
            } catch (RuntimeException e9) {
                e = e9;
                i3 = i32;
            }
            JsonValue jsonValue6 = this.root;
            this.root = null;
            this.current = null;
            this.lastChild.clear();
            if (!this.stop) {
                if (i4 < i2) {
                    i11 = 1;
                    while (i10 < i4) {
                        if (cArr[i10] == '\n') {
                            i11++;
                        }
                    }
                    int iMax6 = Math.max(0, i4 - 32);
                    throw new SerializationException("Error parsing JSON on line " + i11 + " near: " + new String(cArr, iMax6, i4 - iMax6) + "*ERROR*" + new String(cArr, i4, Math.min(64, i2 - i4)), runtimeException);
                }
                if (this.elements.size != 0) {
                    jsonValuePeek = this.elements.peek();
                    this.elements.clear();
                    if (jsonValuePeek == null) {
                    }
                    throw new SerializationException("Error parsing JSON, unmatched bracket.");
                }
                if (runtimeException != null) {
                    throw new SerializationException("Error parsing JSON: ".concat(new String(cArr)), runtimeException);
                }
            }
            return jsonValue6;
        }
    }

    private static byte[] init__json_actions_0() {
        return new byte[]{0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 1, 6, 1, 7, 1, 8, 2, 0, 7, 2, 0, 8, 2, 1, 3, 2, 1, 5};
    }

    private static short[] init__json_key_offsets_0() {
        return new short[]{0, 0, 11, 13, 14, 16, 25, 31, 37, 39, 50, 57, 64, 73, 74, 83, 85, 87, 96, 98, 100, 101, 103, ASDataType.BASE64BINARY_DATATYPE, ASDataType.GMONTH_DATATYPE, 123, 130, 141, 142, 153, 155, 157, 168, 170, 172, 174, 179, 184, 184};
    }

    private static char[] init__json_trans_keys_0() {
        return new char[]{'\r', StringUtil.SPACE, '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '*', '/', '\"', '*', '/', '\r', StringUtil.SPACE, '\"', ',', '/', ':', '}', '\t', '\n', '\r', StringUtil.SPACE, '/', ':', '\t', '\n', '\r', StringUtil.SPACE, '/', ':', '\t', '\n', '*', '/', '\r', StringUtil.SPACE, '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '\t', '\n', '\r', StringUtil.SPACE, ',', '/', '}', '\t', '\n', '\r', StringUtil.SPACE, ',', '/', '}', '\r', StringUtil.SPACE, '\"', ',', '/', ':', '}', '\t', '\n', '\"', '\r', StringUtil.SPACE, '\"', ',', '/', ':', '}', '\t', '\n', '*', '/', '*', '/', '\r', StringUtil.SPACE, '\"', ',', '/', ':', '}', '\t', '\n', '*', '/', '*', '/', '\"', '*', '/', '*', '/', '\r', StringUtil.SPACE, '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '\t', '\n', '\r', StringUtil.SPACE, ',', '/', ']', '\t', '\n', '\r', StringUtil.SPACE, ',', '/', ']', '\r', StringUtil.SPACE, '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '\"', '\r', StringUtil.SPACE, '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '*', '/', '*', '/', '\r', StringUtil.SPACE, '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '*', '/', '*', '/', '*', '/', '\r', StringUtil.SPACE, '/', '\t', '\n', '\r', StringUtil.SPACE, '/', '\t', '\n', 0};
    }

    private static byte[] init__json_single_lengths_0() {
        return new byte[]{0, 9, 2, 1, 2, 7, 4, 4, 2, 9, 7, 7, 7, 1, 7, 2, 2, 7, 2, 2, 1, 2, 2, 9, 7, 7, 9, 1, 9, 2, 2, 9, 2, 2, 2, 3, 3, 0, 0};
    }

    private static byte[] init__json_range_lengths_0() {
        return new byte[]{0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0};
    }

    private static short[] init__json_index_offsets_0() {
        return new short[]{0, 0, 11, 14, 16, 19, 28, 34, 40, 43, 54, 62, 70, 79, 81, 90, 93, 96, ASDataType.BASE64BINARY_DATATYPE, ASDataType.DURATION_DATATYPE, 111, 113, ASDataType.GMONTH_DATATYPE, 119, 130, 138, 146, 157, 159, 170, 173, 176, 187, 190, 193, 196, ASDataType.NCNAME_DATATYPE, ASDataType.NEGATIVEINTEGER_DATATYPE, ASDataType.LONG_DATATYPE};
    }

    private static byte[] init__json_indicies_0() {
        return new byte[]{1, 1, 2, 3, 4, 3, 5, 3, 6, 1, 0, 7, 7, 3, 8, 3, 9, 9, 3, 11, 11, 12, 13, 14, 3, 15, 11, 10, 16, 16, 17, 18, 16, 3, 19, 19, 20, 21, 19, 3, 22, 22, 3, 21, 21, 24, 3, 25, 3, 26, 3, 27, 21, 23, 28, 29, 29, 28, 30, 31, 32, 3, 33, 34, 34, 33, 13, 35, 15, 3, 34, 34, 12, 36, 37, 3, 15, 34, 10, 16, 3, 36, 36, 12, 3, 38, 3, 3, 36, 10, 39, 39, 3, 40, 40, 3, 13, 13, 12, 3, 41, 3, 15, 13, 10, 42, 42, 3, 43, 43, 3, 28, 3, 44, 44, 3, 45, 45, 3, 47, 47, 48, 49, 50, 3, 51, 52, 53, 47, 46, 54, 55, 55, 54, 56, 57, 58, 3, 59, 60, 60, 59, 49, 61, 52, 3, 60, 60, 48, 62, 63, 3, 51, 52, 53, 60, 46, 54, 3, 62, 62, 48, 3, 64, 3, 51, 3, 53, 62, 46, 65, 65, 3, 66, 66, 3, 49, 49, 48, 3, 67, 3, 51, 52, 53, 49, 46, 68, 68, 3, 69, 69, 3, 70, 70, 3, 8, 8, 71, 8, 3, 72, 72, 73, 72, 3, 3, 3, 0};
    }

    private static byte[] init__json_trans_targs_0() {
        return new byte[]{35, 1, 3, 0, 4, 36, 36, 36, 36, 1, 6, 5, 13, 17, 22, 37, 7, 8, 9, 7, 8, 9, 7, 10, 20, 21, 11, 11, 11, 12, 17, 19, 37, 11, 12, 19, 14, 16, 15, 14, 12, 18, 17, 11, 9, 5, 24, 23, 27, 31, 34, 25, 38, 25, 25, 26, 31, 33, 38, 25, 26, 33, 28, 30, 29, 28, 26, 32, 31, 25, 23, 2, 36, 2};
    }

    private static byte[] init__json_trans_actions_0() {
        return new byte[]{13, 0, 15, 0, 0, 7, 3, 11, 1, 11, 17, 0, 20, 0, 0, 5, 1, 1, 1, 0, 0, 0, 11, 13, 15, 0, 7, 3, 1, 1, 1, 1, 23, 0, 0, 0, 0, 0, 0, 11, 11, 0, 11, 11, 11, 11, 13, 0, 15, 0, 0, 7, 9, 3, 1, 1, 1, 1, 26, 0, 0, 0, 0, 0, 0, 11, 11, 0, 11, 11, 11, 1, 0, 0};
    }

    private static byte[] init__json_eof_actions_0() {
        return new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0};
    }

    public void stop() {
        this.stop = true;
    }

    public boolean isStopped() {
        return this.stop;
    }

    private void addChild(String str, JsonValue jsonValue) {
        jsonValue.setName(str);
        JsonValue jsonValue2 = this.current;
        if (jsonValue2 == null) {
            this.current = jsonValue;
            this.root = jsonValue;
            return;
        }
        if (jsonValue2.isArray() || this.current.isObject()) {
            jsonValue.parent = this.current;
            if (this.current.size == 0) {
                this.current.child = jsonValue;
            } else {
                JsonValue jsonValuePop = this.lastChild.pop();
                jsonValuePop.next = jsonValue;
                jsonValue.prev = jsonValuePop;
            }
            this.lastChild.add(jsonValue);
            this.current.size++;
            return;
        }
        this.root = this.current;
    }

    protected void startObject(String str) {
        JsonValue jsonValue = new JsonValue(JsonValue.ValueType.object);
        if (this.current != null) {
            addChild(str, jsonValue);
        }
        this.elements.add(jsonValue);
        this.current = jsonValue;
    }

    protected void startArray(String str) {
        JsonValue jsonValue = new JsonValue(JsonValue.ValueType.array);
        if (this.current != null) {
            addChild(str, jsonValue);
        }
        this.elements.add(jsonValue);
        this.current = jsonValue;
    }

    protected void pop() {
        this.root = this.elements.pop();
        if (this.current.size > 0) {
            this.lastChild.pop();
        }
        this.current = this.elements.size > 0 ? this.elements.peek() : null;
    }

    protected void string(String str, String str2) {
        addChild(str, new JsonValue(str2));
    }

    protected void number(String str, double d, String str2) {
        addChild(str, new JsonValue(d, str2));
    }

    protected void number(String str, long j, String str2) {
        addChild(str, new JsonValue(j, str2));
    }

    protected void bool(String str, boolean z) {
        addChild(str, new JsonValue(z));
    }

    protected String unescape(String str) {
        int length = str.length();
        StringBuilder stringBuilder = new StringBuilder(length + 16);
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            char cCharAt = str.charAt(i);
            if (cCharAt != '\\') {
                stringBuilder.append(cCharAt);
                i = i2;
            } else {
                if (i2 == length) {
                    break;
                }
                int i3 = i + 2;
                char cCharAt2 = str.charAt(i2);
                if (cCharAt2 == 'u') {
                    i += 6;
                    stringBuilder.append(Character.toChars(Integer.parseInt(str.substring(i3, i), 16)));
                } else {
                    if (cCharAt2 != '\"' && cCharAt2 != '/' && cCharAt2 != '\\') {
                        if (cCharAt2 == 'b') {
                            cCharAt2 = '\b';
                        } else if (cCharAt2 == 'f') {
                            cCharAt2 = '\f';
                        } else if (cCharAt2 == 'n') {
                            cCharAt2 = '\n';
                        } else if (cCharAt2 == 'r') {
                            cCharAt2 = '\r';
                        } else {
                            if (cCharAt2 != 't') {
                                throw new SerializationException("Illegal escaped character: \\" + cCharAt2);
                            }
                            cCharAt2 = '\t';
                        }
                    }
                    stringBuilder.append(cCharAt2);
                    i = i3;
                }
            }
        }
        return stringBuilder.toString();
    }
}
