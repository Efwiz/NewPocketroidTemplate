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
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/JsonSkimmer.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class JsonSkimmer {
    static final int json_en_array = 23;
    static final int json_en_main = 1;
    static final int json_en_object = 5;
    static final int json_error = 0;
    static final int json_first_final = 35;
    static final int json_start = 1;
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

    protected void pop() {
    }

    protected void push(String str, boolean z) {
    }

    protected void value(String str, String str2, boolean z) {
    }

    public void parse(String str) {
        char[] charArray = str.toCharArray();
        parse(charArray, 0, charArray.length);
    }

    public void parse(Reader reader) {
        char[] cArr = new char[1024];
        int i = 0;
        while (true) {
            try {
                try {
                    int i2 = reader.read(cArr, i, cArr.length - i);
                    if (i2 == -1) {
                        StreamUtils.closeQuietly(reader);
                        parse(cArr, 0, i);
                        return;
                    } else if (i2 == 0) {
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

    public void parse(InputStream inputStream) {
        try {
            parse(new InputStreamReader(inputStream, "UTF-8"));
        } catch (Exception e) {
            throw new SerializationException("Error reading stream.", e);
        }
    }

    public void parse(FileHandle fileHandle) {
        try {
            try {
                parse(fileHandle.reader("UTF-8"));
            } catch (Exception e) {
                throw new SerializationException("Error parsing file: " + fileHandle, e);
            }
        } catch (Exception e2) {
            throw new SerializationException("Error reading file: " + fileHandle, e2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:118:0x015d A[LOOP:7: B:91:0x0127->B:118:0x015d, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:124:0x0176  */
    /* JADX WARN: Code duplicated, block: B:128:0x017e  */
    /* JADX WARN: Code duplicated, block: B:130:0x0181 A[Catch: RuntimeException -> 0x006b, TRY_ENTER, TRY_LEAVE, TryCatch #6 {RuntimeException -> 0x006b, blocks: (B:20:0x0051, B:45:0x00bb, B:46:0x00c1, B:48:0x00c5, B:130:0x0181, B:155:0x01be, B:158:0x01c6, B:159:0x01cb, B:162:0x01d4, B:164:0x01d7, B:165:0x01df, B:166:0x01eb, B:169:0x01f3, B:171:0x01fd, B:174:0x0206, B:176:0x0209, B:178:0x0213, B:180:0x0220, B:182:0x022a, B:186:0x0236, B:185:0x0232, B:36:0x008c, B:38:0x0096, B:39:0x009b), top: B:248:0x0051 }] */
    /* JADX WARN: Code duplicated, block: B:135:0x018e  */
    /* JADX WARN: Code duplicated, block: B:142:0x019c  */
    /* JADX WARN: Code duplicated, block: B:145:0x01a4  */
    /* JADX WARN: Code duplicated, block: B:158:0x01c6 A[Catch: RuntimeException -> 0x006b, TryCatch #6 {RuntimeException -> 0x006b, blocks: (B:20:0x0051, B:45:0x00bb, B:46:0x00c1, B:48:0x00c5, B:130:0x0181, B:155:0x01be, B:158:0x01c6, B:159:0x01cb, B:162:0x01d4, B:164:0x01d7, B:165:0x01df, B:166:0x01eb, B:169:0x01f3, B:171:0x01fd, B:174:0x0206, B:176:0x0209, B:178:0x0213, B:180:0x0220, B:182:0x022a, B:186:0x0236, B:185:0x0232, B:36:0x008c, B:38:0x0096, B:39:0x009b), top: B:248:0x0051 }] */
    /* JADX WARN: Code duplicated, block: B:162:0x01d4 A[Catch: RuntimeException -> 0x006b, TryCatch #6 {RuntimeException -> 0x006b, blocks: (B:20:0x0051, B:45:0x00bb, B:46:0x00c1, B:48:0x00c5, B:130:0x0181, B:155:0x01be, B:158:0x01c6, B:159:0x01cb, B:162:0x01d4, B:164:0x01d7, B:165:0x01df, B:166:0x01eb, B:169:0x01f3, B:171:0x01fd, B:174:0x0206, B:176:0x0209, B:178:0x0213, B:180:0x0220, B:182:0x022a, B:186:0x0236, B:185:0x0232, B:36:0x008c, B:38:0x0096, B:39:0x009b), top: B:248:0x0051 }] */
    /* JADX WARN: Code duplicated, block: B:164:0x01d7 A[Catch: RuntimeException -> 0x006b, TryCatch #6 {RuntimeException -> 0x006b, blocks: (B:20:0x0051, B:45:0x00bb, B:46:0x00c1, B:48:0x00c5, B:130:0x0181, B:155:0x01be, B:158:0x01c6, B:159:0x01cb, B:162:0x01d4, B:164:0x01d7, B:165:0x01df, B:166:0x01eb, B:169:0x01f3, B:171:0x01fd, B:174:0x0206, B:176:0x0209, B:178:0x0213, B:180:0x0220, B:182:0x022a, B:186:0x0236, B:185:0x0232, B:36:0x008c, B:38:0x0096, B:39:0x009b), top: B:248:0x0051 }] */
    /* JADX WARN: Code duplicated, block: B:169:0x01f3 A[Catch: RuntimeException -> 0x006b, TryCatch #6 {RuntimeException -> 0x006b, blocks: (B:20:0x0051, B:45:0x00bb, B:46:0x00c1, B:48:0x00c5, B:130:0x0181, B:155:0x01be, B:158:0x01c6, B:159:0x01cb, B:162:0x01d4, B:164:0x01d7, B:165:0x01df, B:166:0x01eb, B:169:0x01f3, B:171:0x01fd, B:174:0x0206, B:176:0x0209, B:178:0x0213, B:180:0x0220, B:182:0x022a, B:186:0x0236, B:185:0x0232, B:36:0x008c, B:38:0x0096, B:39:0x009b), top: B:248:0x0051 }] */
    /* JADX WARN: Code duplicated, block: B:174:0x0206 A[Catch: RuntimeException -> 0x006b, TryCatch #6 {RuntimeException -> 0x006b, blocks: (B:20:0x0051, B:45:0x00bb, B:46:0x00c1, B:48:0x00c5, B:130:0x0181, B:155:0x01be, B:158:0x01c6, B:159:0x01cb, B:162:0x01d4, B:164:0x01d7, B:165:0x01df, B:166:0x01eb, B:169:0x01f3, B:171:0x01fd, B:174:0x0206, B:176:0x0209, B:178:0x0213, B:180:0x0220, B:182:0x022a, B:186:0x0236, B:185:0x0232, B:36:0x008c, B:38:0x0096, B:39:0x009b), top: B:248:0x0051 }] */
    /* JADX WARN: Code duplicated, block: B:176:0x0209 A[Catch: RuntimeException -> 0x006b, TryCatch #6 {RuntimeException -> 0x006b, blocks: (B:20:0x0051, B:45:0x00bb, B:46:0x00c1, B:48:0x00c5, B:130:0x0181, B:155:0x01be, B:158:0x01c6, B:159:0x01cb, B:162:0x01d4, B:164:0x01d7, B:165:0x01df, B:166:0x01eb, B:169:0x01f3, B:171:0x01fd, B:174:0x0206, B:176:0x0209, B:178:0x0213, B:180:0x0220, B:182:0x022a, B:186:0x0236, B:185:0x0232, B:36:0x008c, B:38:0x0096, B:39:0x009b), top: B:248:0x0051 }] */
    /* JADX WARN: Code duplicated, block: B:177:0x0212  */
    /* JADX WARN: Code duplicated, block: B:180:0x0220 A[Catch: RuntimeException -> 0x006b, TryCatch #6 {RuntimeException -> 0x006b, blocks: (B:20:0x0051, B:45:0x00bb, B:46:0x00c1, B:48:0x00c5, B:130:0x0181, B:155:0x01be, B:158:0x01c6, B:159:0x01cb, B:162:0x01d4, B:164:0x01d7, B:165:0x01df, B:166:0x01eb, B:169:0x01f3, B:171:0x01fd, B:174:0x0206, B:176:0x0209, B:178:0x0213, B:180:0x0220, B:182:0x022a, B:186:0x0236, B:185:0x0232, B:36:0x008c, B:38:0x0096, B:39:0x009b), top: B:248:0x0051 }] */
    /* JADX WARN: Code duplicated, block: B:182:0x022a A[Catch: RuntimeException -> 0x006b, TryCatch #6 {RuntimeException -> 0x006b, blocks: (B:20:0x0051, B:45:0x00bb, B:46:0x00c1, B:48:0x00c5, B:130:0x0181, B:155:0x01be, B:158:0x01c6, B:159:0x01cb, B:162:0x01d4, B:164:0x01d7, B:165:0x01df, B:166:0x01eb, B:169:0x01f3, B:171:0x01fd, B:174:0x0206, B:176:0x0209, B:178:0x0213, B:180:0x0220, B:182:0x022a, B:186:0x0236, B:185:0x0232, B:36:0x008c, B:38:0x0096, B:39:0x009b), top: B:248:0x0051 }] */
    /* JADX WARN: Code duplicated, block: B:184:0x0230  */
    /* JADX WARN: Code duplicated, block: B:185:0x0232 A[Catch: RuntimeException -> 0x006b, TryCatch #6 {RuntimeException -> 0x006b, blocks: (B:20:0x0051, B:45:0x00bb, B:46:0x00c1, B:48:0x00c5, B:130:0x0181, B:155:0x01be, B:158:0x01c6, B:159:0x01cb, B:162:0x01d4, B:164:0x01d7, B:165:0x01df, B:166:0x01eb, B:169:0x01f3, B:171:0x01fd, B:174:0x0206, B:176:0x0209, B:178:0x0213, B:180:0x0220, B:182:0x022a, B:186:0x0236, B:185:0x0232, B:36:0x008c, B:38:0x0096, B:39:0x009b), top: B:248:0x0051 }] */
    /* JADX WARN: Code duplicated, block: B:189:0x023b  */
    /* JADX WARN: Code duplicated, block: B:190:0x023f  */
    /* JADX WARN: Code duplicated, block: B:197:0x0252  */
    /* JADX WARN: Code duplicated, block: B:199:0x025a A[Catch: RuntimeException -> 0x0292, TRY_ENTER, TryCatch #1 {RuntimeException -> 0x0292, blocks: (B:199:0x025a, B:200:0x0264, B:202:0x0268, B:43:0x00ad, B:30:0x0070), top: B:238:0x00ad }] */
    /* JADX WARN: Code duplicated, block: B:202:0x0268 A[Catch: RuntimeException -> 0x0292, TRY_LEAVE, TryCatch #1 {RuntimeException -> 0x0292, blocks: (B:199:0x025a, B:200:0x0264, B:202:0x0268, B:43:0x00ad, B:30:0x0070), top: B:238:0x00ad }] */
    /* JADX WARN: Code duplicated, block: B:206:0x0272 A[Catch: RuntimeException -> 0x0298, TRY_ENTER, TryCatch #0 {RuntimeException -> 0x0298, blocks: (B:206:0x0272, B:208:0x027b, B:212:0x0287, B:211:0x0283, B:14:0x002f), top: B:236:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:208:0x027b A[Catch: RuntimeException -> 0x0298, TryCatch #0 {RuntimeException -> 0x0298, blocks: (B:206:0x0272, B:208:0x027b, B:212:0x0287, B:211:0x0283, B:14:0x002f), top: B:236:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:210:0x0281  */
    /* JADX WARN: Code duplicated, block: B:211:0x0283 A[Catch: RuntimeException -> 0x0298, TryCatch #0 {RuntimeException -> 0x0298, blocks: (B:206:0x0272, B:208:0x027b, B:212:0x0287, B:211:0x0283, B:14:0x002f), top: B:236:0x002f }] */
    /* JADX WARN: Code duplicated, block: B:215:0x028c  */
    /* JADX WARN: Code duplicated, block: B:224:0x029c  */
    /* JADX WARN: Code duplicated, block: B:226:0x02a0  */
    /* JADX WARN: Code duplicated, block: B:228:0x02a6  */
    /* JADX WARN: Code duplicated, block: B:232:0x02ee A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:233:0x02f0 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:234:0x02f1  */
    /* JADX WARN: Code duplicated, block: B:244:0x0193 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:253:0x01be A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:254:0x01cb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:255:0x01eb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:256:0x01fd A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:257:0x023a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:258:0x024e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:259:0x0247 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:260:0x0258 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:262:0x0244 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:263:0x0244 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:264:0x01c5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:265:0x01f2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:266:0x01d3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:267:0x0205 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:285:0x0240 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:286:0x00e7 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:291:0x02a8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:292:0x016b A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:302:0x0160 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:309:0x0160 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:312:0x01b0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:314:0x01ae A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:315:0x0295 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:316:0x028b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:318:0x028f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:320:0x009b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:321:0x0096 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:322:0x00a8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:32:0x0076  */
    /* JADX WARN: Code duplicated, block: B:35:0x0081  */
    /* JADX WARN: Code duplicated, block: B:41:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:45:0x00bb A[Catch: RuntimeException -> 0x006b, TRY_ENTER, TryCatch #6 {RuntimeException -> 0x006b, blocks: (B:20:0x0051, B:45:0x00bb, B:46:0x00c1, B:48:0x00c5, B:130:0x0181, B:155:0x01be, B:158:0x01c6, B:159:0x01cb, B:162:0x01d4, B:164:0x01d7, B:165:0x01df, B:166:0x01eb, B:169:0x01f3, B:171:0x01fd, B:174:0x0206, B:176:0x0209, B:178:0x0213, B:180:0x0220, B:182:0x022a, B:186:0x0236, B:185:0x0232, B:36:0x008c, B:38:0x0096, B:39:0x009b), top: B:248:0x0051 }] */
    /* JADX WARN: Code duplicated, block: B:48:0x00c5 A[Catch: RuntimeException -> 0x006b, TRY_LEAVE, TryCatch #6 {RuntimeException -> 0x006b, blocks: (B:20:0x0051, B:45:0x00bb, B:46:0x00c1, B:48:0x00c5, B:130:0x0181, B:155:0x01be, B:158:0x01c6, B:159:0x01cb, B:162:0x01d4, B:164:0x01d7, B:165:0x01df, B:166:0x01eb, B:169:0x01f3, B:171:0x01fd, B:174:0x0206, B:176:0x0209, B:178:0x0213, B:180:0x0220, B:182:0x022a, B:186:0x0236, B:185:0x0232, B:36:0x008c, B:38:0x0096, B:39:0x009b), top: B:248:0x0051 }] */
    /* JADX WARN: Code duplicated, block: B:51:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:55:0x00da  */
    /* JADX WARN: Code duplicated, block: B:58:0x00e0  */
    /* JADX WARN: Code duplicated, block: B:62:0x00ee  */
    /* JADX WARN: Code duplicated, block: B:64:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:67:0x00fa A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:90:0x0125  */
    /* JADX WARN: Code duplicated, block: B:93:0x012d A[ADDED_TO_REGION] */
    public void parse(char[] cArr, int i, int i2) {
        RuntimeException runtimeException;
        int i3;
        int i4;
        int i5;
        byte b;
        String str;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        char c;
        char[] cArr2;
        int i12;
        RuntimeException e;
        int i13;
        byte b2;
        byte b3;
        int i14;
        int i15;
        int i16;
        int i17;
        String str2;
        int i18;
        int i19;
        int i20;
        char c2;
        int i21;
        char c3;
        int i22;
        char c4;
        int i23;
        boolean z;
        char c5;
        this.stop = false;
        int i24 = 4;
        int i25 = 1;
        int i26 = 0;
        int i27 = 0;
        int i28 = 0;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        int[] iArrCopyOf = new int[4];
        int i29 = 1;
        String str3 = null;
        int i30 = i;
        while (true) {
            try {
                if (i27 != 0) {
                    if (i27 != i25) {
                        if (i27 != 2) {
                            if (i27 == i24) {
                            }
                            runtimeException = null;
                            if (i30 >= i2) {
                                if (runtimeException != null) {
                                    throw new SerializationException("Error parsing JSON: ".concat(new String(cArr)), runtimeException);
                                }
                                return;
                            }
                            i6 = i25;
                            for (i7 = 0; i7 < i30; i7++) {
                                if (cArr[i7] == '\n') {
                                    i6++;
                                }
                            }
                            int iMax = Math.max(0, i30 - 32);
                            throw new SerializationException("Error parsing JSON on line " + i6 + " near: " + new String(cArr, iMax, i30 - iMax) + "*ERROR*" + new String(cArr, i30, Math.min(64, i2 - i30)), runtimeException);
                        }
                        if (i30 == i2) {
                            byte b4 = _json_eof_actions[i29];
                            i3 = b4 + 1;
                            i4 = _json_actions[b4];
                            while (true) {
                                i5 = i4 - 1;
                                if (i4 > 0) {
                                    int i31 = i3 + 1;
                                    b = _json_actions[i3];
                                    i25 = 1;
                                    if (b == 1) {
                                        str = new String(cArr, i28, i30 - i28);
                                        if (z2) {
                                            str = unescape(str);
                                        }
                                        if (z3) {
                                            z3 = false;
                                        } else {
                                            value(str3, str, z4);
                                            str = null;
                                        }
                                        if (this.stop) {
                                            return;
                                        }
                                        str3 = str;
                                        i28 = i30;
                                        z4 = false;
                                    }
                                    i4 = i5;
                                    i3 = i31;
                                }
                            }
                        }
                        i25 = 1;
                        runtimeException = null;
                        if (i30 >= i2) {
                            if (runtimeException != null) {
                                throw new SerializationException("Error parsing JSON: ".concat(new String(cArr)), runtimeException);
                            }
                            return;
                        }
                        i6 = i25;
                        while (i7 < i30) {
                            if (cArr[i7] == '\n') {
                                i6++;
                            }
                        }
                        int iMax2 = Math.max(0, i30 - 32);
                        throw new SerializationException("Error parsing JSON on line " + i6 + " near: " + new String(cArr, iMax2, i30 - iMax2) + "*ERROR*" + new String(cArr, i30, Math.min(64, i2 - i30)), runtimeException);
                    }
                    if (i29 == 0) {
                        i27 = 5;
                        i24 = 4;
                        i25 = 1;
                    } else {
                        i30++;
                        if (i30 == i2) {
                            if (i30 == i2) {
                                byte b5 = _json_eof_actions[i29];
                                i3 = b5 + 1;
                                i4 = _json_actions[b5];
                                while (true) {
                                    i5 = i4 - 1;
                                    if (i4 > 0) {
                                        int i32 = i3 + 1;
                                        b = _json_actions[i3];
                                        i25 = 1;
                                        if (b == 1) {
                                            str = new String(cArr, i28, i30 - i28);
                                            if (z2) {
                                                str = unescape(str);
                                            }
                                            if (z3) {
                                                z3 = false;
                                            } else {
                                                value(str3, str, z4);
                                                str = null;
                                            }
                                            if (this.stop) {
                                                return;
                                            }
                                            str3 = str;
                                            i28 = i30;
                                            z4 = false;
                                        }
                                        i4 = i5;
                                        i3 = i32;
                                    }
                                }
                            }
                            i25 = 1;
                            runtimeException = null;
                            if (i30 >= i2) {
                                if (runtimeException != null) {
                                    throw new SerializationException("Error parsing JSON: ".concat(new String(cArr)), runtimeException);
                                }
                                return;
                            }
                            i6 = i25;
                            while (i7 < i30) {
                                if (cArr[i7] == '\n') {
                                    i6++;
                                }
                            }
                            int iMax3 = Math.max(0, i30 - 32);
                            throw new SerializationException("Error parsing JSON on line " + i6 + " near: " + new String(cArr, iMax3, i30 - iMax3) + "*ERROR*" + new String(cArr, i30, Math.min(64, i2 - i30)), runtimeException);
                        }
                        i24 = 4;
                        i25 = 1;
                        i27 = 1;
                    }
                } else if (i30 == i2) {
                    i27 = i24;
                } else if (i29 == 0) {
                    i27 = 5;
                }
                short s = _json_key_offsets[i29];
                short s2 = _json_index_offsets[i29];
                byte b6 = _json_single_lengths[i29];
                int i33 = s;
                int i34 = s2;
                if (b6 > 0) {
                    int i35 = s + b6;
                    int i36 = s;
                    int i37 = i35 - 1;
                    while (true) {
                        if (i37 >= i36) {
                            int i38 = i36 + ((i37 - i36) >> 1);
                            try {
                                char c6 = cArr[i30];
                                int i39 = i37;
                                char c7 = _json_trans_keys[i38];
                                if (c6 < c7) {
                                    i37 = i38 - 1;
                                } else if (c6 > c7) {
                                    i36 = i38 + 1;
                                    i37 = i39;
                                } else {
                                    i13 = s2 + (i38 - s);
                                    byte b7 = _json_indicies[i13];
                                    b2 = _json_trans_targs[b7];
                                    b3 = _json_trans_actions[b7];
                                    if (b3 != 0) {
                                        i14 = b3 + 1;
                                        i15 = _json_actions[b3];
                                        while (true) {
                                            i16 = i15 - 1;
                                            if (i15 > 0) {
                                                i17 = i14 + 1;
                                                switch (_json_actions[i14]) {
                                                    case 0:
                                                        z3 = true;
                                                        i15 = i16;
                                                        i14 = i17;
                                                        break;
                                                    case 1:
                                                        str2 = new String(cArr, i28, i30 - i28);
                                                        if (z2) {
                                                            str2 = unescape(str2);
                                                        }
                                                        if (z3) {
                                                            z3 = false;
                                                        } else {
                                                            value(str3, str2, z4);
                                                            str2 = null;
                                                        }
                                                        if (this.stop) {
                                                            return;
                                                        }
                                                        str3 = str2;
                                                        i28 = i30;
                                                        z4 = false;
                                                        i15 = i16;
                                                        i14 = i17;
                                                        break;
                                                    case 2:
                                                        push(str3, true);
                                                        if (this.stop) {
                                                            return;
                                                        }
                                                        if (i26 == iArrCopyOf.length) {
                                                            i27 = 2;
                                                            iArrCopyOf = Arrays.copyOf(iArrCopyOf, iArrCopyOf.length * 2);
                                                        } else {
                                                            i27 = 2;
                                                        }
                                                        iArrCopyOf[i26] = b2;
                                                        i26++;
                                                        i29 = 5;
                                                        i24 = 4;
                                                        i25 = 1;
                                                        str3 = null;
                                                        break;
                                                    case 3:
                                                        pop();
                                                        if (this.stop) {
                                                            return;
                                                        }
                                                        i26--;
                                                        i29 = iArrCopyOf[i26];
                                                        i24 = 4;
                                                        i25 = 1;
                                                        i27 = 2;
                                                        break;
                                                    case 4:
                                                        push(str3, false);
                                                        if (this.stop) {
                                                            return;
                                                        }
                                                        if (i26 == iArrCopyOf.length) {
                                                            iArrCopyOf = Arrays.copyOf(iArrCopyOf, iArrCopyOf.length * 2);
                                                        }
                                                        iArrCopyOf[i26] = b2;
                                                        i29 = 23;
                                                        i26++;
                                                        i24 = 4;
                                                        i25 = 1;
                                                        i27 = 2;
                                                        str3 = null;
                                                        break;
                                                    case 5:
                                                        pop();
                                                        if (this.stop) {
                                                            return;
                                                        }
                                                        i26--;
                                                        i29 = iArrCopyOf[i26];
                                                        i24 = 4;
                                                        i25 = 1;
                                                        i27 = 2;
                                                        break;
                                                    case 6:
                                                        i18 = i30 + 1;
                                                        if (cArr[i30] == '/') {
                                                            i20 = i18;
                                                            while (i20 != i2) {
                                                                i20++;
                                                            }
                                                            i30 = i20 - 1;
                                                        } else {
                                                            i30 = i18;
                                                            while (true) {
                                                                i19 = i30 + 1;
                                                                if (i19 < i2) {
                                                                    if (cArr[i30] == '*') {
                                                                    }
                                                                    i30 = i19;
                                                                }
                                                                if (cArr[i19] != '/') {
                                                                    i30 = i19;
                                                                } else {
                                                                    i30 = i19;
                                                                }
                                                            }
                                                        }
                                                        i15 = i16;
                                                        i14 = i17;
                                                        break;
                                                    case 7:
                                                        c2 = '\r';
                                                        if (z3) {
                                                            i22 = i30;
                                                            z2 = false;
                                                            do {
                                                                c4 = cArr[i22];
                                                                if (c4 == '\n') {
                                                                }
                                                            } while (i22 != i2);
                                                        } else {
                                                            i21 = i30;
                                                            boolean z5 = false;
                                                            while (true) {
                                                                c3 = cArr[i21];
                                                                if (c3 == '\n') {
                                                                }
                                                                c2 = '\r';
                                                            }
                                                            z2 = z5;
                                                            i22 = i21;
                                                        }
                                                        do {
                                                            i22--;
                                                        } while (Character.isSpace(cArr[i22]));
                                                        z4 = true;
                                                        i28 = i30;
                                                        i30 = i22;
                                                        i15 = i16;
                                                        i14 = i17;
                                                        break;
                                                    case 8:
                                                        int i40 = i30 + 1;
                                                        i23 = i40;
                                                        z = false;
                                                        do {
                                                            c5 = cArr[i23];
                                                            if (c5 != '\"') {
                                                                if (c5 == '\\') {
                                                                    i23++;
                                                                    z = true;
                                                                }
                                                                i23++;
                                                            }
                                                            z2 = z;
                                                            i28 = i40;
                                                            i30 = i23 - 1;
                                                            i15 = i16;
                                                            i14 = i17;
                                                            break;
                                                        } while (i23 != i2);
                                                        z2 = z;
                                                        i28 = i40;
                                                        i30 = i23 - 1;
                                                        i15 = i16;
                                                        i14 = i17;
                                                        break;
                                                    default:
                                                        i15 = i16;
                                                        i14 = i17;
                                                        break;
                                                }
                                            }
                                        }
                                    }
                                    i29 = b2;
                                    if (i29 == 0) {
                                        i27 = 5;
                                        i24 = 4;
                                        i25 = 1;
                                    } else {
                                        i30++;
                                        if (i30 == i2) {
                                            i24 = 4;
                                            i25 = 1;
                                            i27 = 1;
                                        } else {
                                            if (i30 == i2) {
                                                byte b8 = _json_eof_actions[i29];
                                                i3 = b8 + 1;
                                                i4 = _json_actions[b8];
                                                while (true) {
                                                    i5 = i4 - 1;
                                                    if (i4 > 0) {
                                                        int i310 = i3 + 1;
                                                        b = _json_actions[i3];
                                                        i25 = 1;
                                                        if (b == 1) {
                                                            str = new String(cArr, i28, i30 - i28);
                                                            if (z2) {
                                                                str = unescape(str);
                                                            }
                                                            if (z3) {
                                                                z3 = false;
                                                            } else {
                                                                value(str3, str, z4);
                                                                str = null;
                                                            }
                                                            if (this.stop) {
                                                                return;
                                                            }
                                                            str3 = str;
                                                            i28 = i30;
                                                            z4 = false;
                                                        }
                                                        i4 = i5;
                                                        i3 = i310;
                                                    }
                                                }
                                            }
                                            i25 = 1;
                                            runtimeException = null;
                                        }
                                    }
                                }
                                i36 = i36;
                            } catch (RuntimeException e2) {
                                e = e2;
                                runtimeException = e;
                                i25 = 1;
                                if (i30 >= i2) {
                                    if (runtimeException != null) {
                                        throw new SerializationException("Error parsing JSON: ".concat(new String(cArr)), runtimeException);
                                    }
                                    return;
                                }
                                i6 = i25;
                                while (i7 < i30) {
                                    if (cArr[i7] == '\n') {
                                        i6++;
                                    }
                                }
                                int iMax4 = Math.max(0, i30 - 32);
                                throw new SerializationException("Error parsing JSON on line " + i6 + " near: " + new String(cArr, iMax4, i30 - iMax4) + "*ERROR*" + new String(cArr, i30, Math.min(64, i2 - i30)), runtimeException);
                            }
                        } else {
                            i33 = i35;
                            i34 = s2 + b6;
                            i8 = _json_range_lengths[i29];
                            i13 = i34;
                            if (i8 > 0) {
                                i9 = ((i8 << 1) + i33) - 2;
                                i10 = i33;
                                while (i9 >= i10) {
                                    i11 = (((i9 - i10) >> 1) & (-2)) + i10;
                                    i8 = i8;
                                    c = cArr[i30];
                                    cArr2 = _json_trans_keys;
                                    i12 = i9;
                                    if (c < cArr2[i11]) {
                                        i9 = i11 - 2;
                                    } else if (c > cArr2[i11 + 1]) {
                                        i10 = i11 + 2;
                                        i9 = i12;
                                    } else {
                                        i8 = (i11 - i33) >> 1;
                                        i13 = i34 + i8;
                                    }
                                }
                                i13 = i34 + i8;
                            }
                            try {
                                byte b9 = _json_indicies[i13];
                                b2 = _json_trans_targs[b9];
                                b3 = _json_trans_actions[b9];
                                if (b3 != 0) {
                                    i14 = b3 + 1;
                                    i15 = _json_actions[b3];
                                    while (true) {
                                        i16 = i15 - 1;
                                        if (i15 > 0) {
                                            i17 = i14 + 1;
                                            switch (_json_actions[i14]) {
                                                case 0:
                                                    z3 = true;
                                                    i15 = i16;
                                                    i14 = i17;
                                                    break;
                                                case 1:
                                                    str2 = new String(cArr, i28, i30 - i28);
                                                    if (z2) {
                                                        str2 = unescape(str2);
                                                    }
                                                    if (z3) {
                                                        z3 = false;
                                                    } else {
                                                        value(str3, str2, z4);
                                                        str2 = null;
                                                    }
                                                    if (this.stop) {
                                                        return;
                                                    }
                                                    str3 = str2;
                                                    i28 = i30;
                                                    z4 = false;
                                                    i15 = i16;
                                                    i14 = i17;
                                                    break;
                                                case 2:
                                                    push(str3, true);
                                                    if (this.stop) {
                                                        return;
                                                    }
                                                    if (i26 == iArrCopyOf.length) {
                                                        i27 = 2;
                                                        iArrCopyOf = Arrays.copyOf(iArrCopyOf, iArrCopyOf.length * 2);
                                                    } else {
                                                        i27 = 2;
                                                    }
                                                    iArrCopyOf[i26] = b2;
                                                    i26++;
                                                    i29 = 5;
                                                    i24 = 4;
                                                    i25 = 1;
                                                    str3 = null;
                                                    break;
                                                case 3:
                                                    pop();
                                                    if (this.stop) {
                                                        return;
                                                    }
                                                    i26--;
                                                    i29 = iArrCopyOf[i26];
                                                    i24 = 4;
                                                    i25 = 1;
                                                    i27 = 2;
                                                    break;
                                                case 4:
                                                    push(str3, false);
                                                    if (this.stop) {
                                                        return;
                                                    }
                                                    if (i26 == iArrCopyOf.length) {
                                                        iArrCopyOf = Arrays.copyOf(iArrCopyOf, iArrCopyOf.length * 2);
                                                    }
                                                    iArrCopyOf[i26] = b2;
                                                    i29 = 23;
                                                    i26++;
                                                    i24 = 4;
                                                    i25 = 1;
                                                    i27 = 2;
                                                    str3 = null;
                                                    break;
                                                case 5:
                                                    pop();
                                                    if (this.stop) {
                                                        return;
                                                    }
                                                    i26--;
                                                    i29 = iArrCopyOf[i26];
                                                    i24 = 4;
                                                    i25 = 1;
                                                    i27 = 2;
                                                    break;
                                                case 6:
                                                    i18 = i30 + 1;
                                                    if (cArr[i30] == '/') {
                                                        i20 = i18;
                                                        while (i20 != i2 && cArr[i20] != '\n') {
                                                            i20++;
                                                        }
                                                        i30 = i20 - 1;
                                                    } else {
                                                        i30 = i18;
                                                        while (true) {
                                                            i19 = i30 + 1;
                                                            try {
                                                                if (i19 < i2) {
                                                                    try {
                                                                        if (cArr[i30] == '*') {
                                                                        }
                                                                        i30 = i19;
                                                                    } catch (RuntimeException e3) {
                                                                        e = e3;
                                                                        runtimeException = e;
                                                                        i25 = 1;
                                                                        if (i30 >= i2) {
                                                                            if (runtimeException != null) {
                                                                                throw new SerializationException("Error parsing JSON: ".concat(new String(cArr)), runtimeException);
                                                                            }
                                                                            return;
                                                                        }
                                                                        i6 = i25;
                                                                        while (i7 < i30) {
                                                                            if (cArr[i7] == '\n') {
                                                                                i6++;
                                                                            }
                                                                        }
                                                                        int iMax5 = Math.max(0, i30 - 32);
                                                                        throw new SerializationException("Error parsing JSON on line " + i6 + " near: " + new String(cArr, iMax5, i30 - iMax5) + "*ERROR*" + new String(cArr, i30, Math.min(64, i2 - i30)), runtimeException);
                                                                    }
                                                                }
                                                                if (cArr[i19] != '/') {
                                                                    i30 = i19;
                                                                } else {
                                                                    i30 = i19;
                                                                }
                                                            } catch (RuntimeException e4) {
                                                                runtimeException = e4;
                                                                i30 = i30;
                                                                i25 = 1;
                                                                if (i30 >= i2) {
                                                                    if (runtimeException != null) {
                                                                        throw new SerializationException("Error parsing JSON: ".concat(new String(cArr)), runtimeException);
                                                                    }
                                                                    return;
                                                                }
                                                                i6 = i25;
                                                                while (i7 < i30) {
                                                                    if (cArr[i7] == '\n') {
                                                                        i6++;
                                                                    }
                                                                }
                                                                int iMax6 = Math.max(0, i30 - 32);
                                                                throw new SerializationException("Error parsing JSON on line " + i6 + " near: " + new String(cArr, iMax6, i30 - iMax6) + "*ERROR*" + new String(cArr, i30, Math.min(64, i2 - i30)), runtimeException);
                                                            }
                                                        }
                                                    }
                                                    i15 = i16;
                                                    i14 = i17;
                                                    break;
                                                case 7:
                                                    c2 = '\r';
                                                    if (z3) {
                                                        i22 = i30;
                                                        z2 = false;
                                                        do {
                                                            try {
                                                                c4 = cArr[i22];
                                                                if (c4 == '\n' && c4 != '\r') {
                                                                    if (c4 == '/') {
                                                                        int i41 = i22 + 1;
                                                                        if (i41 != i2) {
                                                                            char c8 = cArr[i41];
                                                                            if (c8 == '/' || c8 == '*') {
                                                                            }
                                                                        }
                                                                        i22++;
                                                                    } else if (c4 != ':') {
                                                                        if (c4 == '\\') {
                                                                            z2 = true;
                                                                        }
                                                                        i22++;
                                                                    }
                                                                }
                                                            } catch (RuntimeException e5) {
                                                                runtimeException = e5;
                                                                i30 = i22;
                                                                i25 = 1;
                                                                if (i30 >= i2) {
                                                                    if (runtimeException != null) {
                                                                        throw new SerializationException("Error parsing JSON: ".concat(new String(cArr)), runtimeException);
                                                                    }
                                                                    return;
                                                                }
                                                                i6 = i25;
                                                                while (i7 < i30) {
                                                                    if (cArr[i7] == '\n') {
                                                                        i6++;
                                                                    }
                                                                }
                                                                int iMax7 = Math.max(0, i30 - 32);
                                                                throw new SerializationException("Error parsing JSON on line " + i6 + " near: " + new String(cArr, iMax7, i30 - iMax7) + "*ERROR*" + new String(cArr, i30, Math.min(64, i2 - i30)), runtimeException);
                                                            }
                                                        } while (i22 != i2);
                                                    } else {
                                                        i21 = i30;
                                                        boolean z6 = false;
                                                        while (true) {
                                                            c3 = cArr[i21];
                                                            if (c3 == '\n' && c3 != c2 && c3 != ',') {
                                                                if (c3 == '/') {
                                                                    int i42 = i21 + 1;
                                                                    if (i42 != i2) {
                                                                        char c9 = cArr[i42];
                                                                        if (c9 == '/' || c9 == '*') {
                                                                        }
                                                                    }
                                                                    i21++;
                                                                    if (i21 == i2) {
                                                                        c2 = '\r';
                                                                    }
                                                                } else if (c3 != '}') {
                                                                    if (c3 == '\\') {
                                                                        z6 = true;
                                                                    } else if (c3 != ']') {
                                                                    }
                                                                    i21++;
                                                                    if (i21 == i2) {
                                                                        c2 = '\r';
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        z2 = z6;
                                                        i22 = i21;
                                                    }
                                                    do {
                                                        i22--;
                                                    } while (Character.isSpace(cArr[i22]));
                                                    z4 = true;
                                                    i28 = i30;
                                                    i30 = i22;
                                                    i15 = i16;
                                                    i14 = i17;
                                                    break;
                                                case 8:
                                                    int i43 = i30 + 1;
                                                    i23 = i43;
                                                    z = false;
                                                    do {
                                                        try {
                                                            c5 = cArr[i23];
                                                            if (c5 != '\"') {
                                                                if (c5 == '\\') {
                                                                    i23++;
                                                                    z = true;
                                                                }
                                                                i23++;
                                                            }
                                                            z2 = z;
                                                            i28 = i43;
                                                            i30 = i23 - 1;
                                                            i15 = i16;
                                                            i14 = i17;
                                                        } catch (RuntimeException e6) {
                                                            runtimeException = e6;
                                                            i30 = i23;
                                                            i25 = 1;
                                                            if (i30 >= i2) {
                                                                if (runtimeException != null) {
                                                                    throw new SerializationException("Error parsing JSON: ".concat(new String(cArr)), runtimeException);
                                                                }
                                                                return;
                                                            }
                                                            i6 = i25;
                                                            while (i7 < i30) {
                                                                if (cArr[i7] == '\n') {
                                                                    i6++;
                                                                }
                                                            }
                                                            int iMax8 = Math.max(0, i30 - 32);
                                                            throw new SerializationException("Error parsing JSON on line " + i6 + " near: " + new String(cArr, iMax8, i30 - iMax8) + "*ERROR*" + new String(cArr, i30, Math.min(64, i2 - i30)), runtimeException);
                                                        }
                                                        break;
                                                    } while (i23 != i2);
                                                    z2 = z;
                                                    i28 = i43;
                                                    i30 = i23 - 1;
                                                    i15 = i16;
                                                    i14 = i17;
                                                    break;
                                                default:
                                                    i15 = i16;
                                                    i14 = i17;
                                                    break;
                                            }
                                        }
                                    }
                                }
                                i29 = b2;
                                if (i29 == 0) {
                                    i27 = 5;
                                    i24 = 4;
                                    i25 = 1;
                                } else {
                                    i30++;
                                    if (i30 == i2) {
                                        i24 = 4;
                                        i25 = 1;
                                        i27 = 1;
                                    } else {
                                        if (i30 == i2) {
                                            byte b10 = _json_eof_actions[i29];
                                            i3 = b10 + 1;
                                            i4 = _json_actions[b10];
                                            while (true) {
                                                i5 = i4 - 1;
                                                if (i4 > 0) {
                                                    int i311 = i3 + 1;
                                                    b = _json_actions[i3];
                                                    i25 = 1;
                                                    if (b == 1) {
                                                        str = new String(cArr, i28, i30 - i28);
                                                        if (z2) {
                                                            str = unescape(str);
                                                        }
                                                        if (z3) {
                                                            z3 = false;
                                                        } else {
                                                            value(str3, str, z4);
                                                            str = null;
                                                        }
                                                        if (this.stop) {
                                                            return;
                                                        }
                                                        str3 = str;
                                                        i28 = i30;
                                                        z4 = false;
                                                    }
                                                    i4 = i5;
                                                    i3 = i311;
                                                }
                                            }
                                        }
                                        i25 = 1;
                                        runtimeException = null;
                                    }
                                }
                            } catch (RuntimeException e7) {
                                e = e7;
                                i25 = 1;
                                runtimeException = e;
                            }
                        }
                    }
                } else {
                    i8 = _json_range_lengths[i29];
                    i13 = i34;
                    if (i8 > 0) {
                        i9 = ((i8 << 1) + i33) - 2;
                        i10 = i33;
                        while (i9 >= i10) {
                            i11 = (((i9 - i10) >> 1) & (-2)) + i10;
                            i8 = i8;
                            c = cArr[i30];
                            cArr2 = _json_trans_keys;
                            i12 = i9;
                            if (c < cArr2[i11]) {
                                i9 = i11 - 2;
                            } else if (c > cArr2[i11 + 1]) {
                                i10 = i11 + 2;
                                i9 = i12;
                            } else {
                                i8 = (i11 - i33) >> 1;
                                i13 = i34 + i8;
                            }
                        }
                        i13 = i34 + i8;
                    }
                    byte b11 = _json_indicies[i13];
                    b2 = _json_trans_targs[b11];
                    b3 = _json_trans_actions[b11];
                    if (b3 != 0) {
                        i14 = b3 + 1;
                        i15 = _json_actions[b3];
                        while (true) {
                            i16 = i15 - 1;
                            if (i15 > 0) {
                                i17 = i14 + 1;
                                switch (_json_actions[i14]) {
                                    case 0:
                                        z3 = true;
                                        i15 = i16;
                                        i14 = i17;
                                        break;
                                    case 1:
                                        str2 = new String(cArr, i28, i30 - i28);
                                        if (z2) {
                                            str2 = unescape(str2);
                                        }
                                        if (z3) {
                                            z3 = false;
                                        } else {
                                            value(str3, str2, z4);
                                            str2 = null;
                                        }
                                        if (this.stop) {
                                            return;
                                        }
                                        str3 = str2;
                                        i28 = i30;
                                        z4 = false;
                                        i15 = i16;
                                        i14 = i17;
                                        break;
                                    case 2:
                                        push(str3, true);
                                        if (this.stop) {
                                            return;
                                        }
                                        if (i26 == iArrCopyOf.length) {
                                            i27 = 2;
                                            iArrCopyOf = Arrays.copyOf(iArrCopyOf, iArrCopyOf.length * 2);
                                        } else {
                                            i27 = 2;
                                        }
                                        iArrCopyOf[i26] = b2;
                                        i26++;
                                        i29 = 5;
                                        i24 = 4;
                                        i25 = 1;
                                        str3 = null;
                                        break;
                                    case 3:
                                        pop();
                                        if (this.stop) {
                                            return;
                                        }
                                        i26--;
                                        i29 = iArrCopyOf[i26];
                                        i24 = 4;
                                        i25 = 1;
                                        i27 = 2;
                                        break;
                                    case 4:
                                        push(str3, false);
                                        if (this.stop) {
                                            return;
                                        }
                                        if (i26 == iArrCopyOf.length) {
                                            iArrCopyOf = Arrays.copyOf(iArrCopyOf, iArrCopyOf.length * 2);
                                        }
                                        iArrCopyOf[i26] = b2;
                                        i29 = 23;
                                        i26++;
                                        i24 = 4;
                                        i25 = 1;
                                        i27 = 2;
                                        str3 = null;
                                        break;
                                    case 5:
                                        pop();
                                        if (this.stop) {
                                            return;
                                        }
                                        i26--;
                                        i29 = iArrCopyOf[i26];
                                        i24 = 4;
                                        i25 = 1;
                                        i27 = 2;
                                        break;
                                    case 6:
                                        i18 = i30 + 1;
                                        if (cArr[i30] == '/') {
                                            i20 = i18;
                                            while (i20 != i2) {
                                                i20++;
                                            }
                                            i30 = i20 - 1;
                                        } else {
                                            i30 = i18;
                                            while (true) {
                                                i19 = i30 + 1;
                                                if (i19 < i2) {
                                                    if (cArr[i30] == '*') {
                                                    }
                                                    i30 = i19;
                                                }
                                                if (cArr[i19] != '/') {
                                                    i30 = i19;
                                                } else {
                                                    i30 = i19;
                                                }
                                            }
                                        }
                                        i15 = i16;
                                        i14 = i17;
                                        break;
                                    case 7:
                                        c2 = '\r';
                                        if (z3) {
                                            i22 = i30;
                                            z2 = false;
                                            do {
                                                c4 = cArr[i22];
                                                if (c4 == '\n') {
                                                }
                                            } while (i22 != i2);
                                        } else {
                                            i21 = i30;
                                            boolean z7 = false;
                                            while (true) {
                                                c3 = cArr[i21];
                                                if (c3 == '\n') {
                                                }
                                                c2 = '\r';
                                            }
                                            z2 = z7;
                                            i22 = i21;
                                        }
                                        do {
                                            i22--;
                                        } while (Character.isSpace(cArr[i22]));
                                        z4 = true;
                                        i28 = i30;
                                        i30 = i22;
                                        i15 = i16;
                                        i14 = i17;
                                        break;
                                    case 8:
                                        int i44 = i30 + 1;
                                        i23 = i44;
                                        z = false;
                                        do {
                                            c5 = cArr[i23];
                                            if (c5 != '\"') {
                                                if (c5 == '\\') {
                                                    i23++;
                                                    z = true;
                                                }
                                                i23++;
                                            }
                                            z2 = z;
                                            i28 = i44;
                                            i30 = i23 - 1;
                                            i15 = i16;
                                            i14 = i17;
                                            break;
                                        } while (i23 != i2);
                                        z2 = z;
                                        i28 = i44;
                                        i30 = i23 - 1;
                                        i15 = i16;
                                        i14 = i17;
                                        break;
                                    default:
                                        i15 = i16;
                                        i14 = i17;
                                        break;
                                }
                            }
                        }
                    }
                    i29 = b2;
                    if (i29 == 0) {
                        i27 = 5;
                        i24 = 4;
                        i25 = 1;
                    } else {
                        i30++;
                        if (i30 == i2) {
                            i24 = 4;
                            i25 = 1;
                            i27 = 1;
                        } else {
                            if (i30 == i2) {
                                byte b12 = _json_eof_actions[i29];
                                i3 = b12 + 1;
                                i4 = _json_actions[b12];
                                while (true) {
                                    i5 = i4 - 1;
                                    if (i4 > 0) {
                                        int i312 = i3 + 1;
                                        b = _json_actions[i3];
                                        i25 = 1;
                                        if (b == 1) {
                                            str = new String(cArr, i28, i30 - i28);
                                            if (z2) {
                                                str = unescape(str);
                                            }
                                            if (z3) {
                                                z3 = false;
                                            } else {
                                                value(str3, str, z4);
                                                str = null;
                                            }
                                            if (this.stop) {
                                                return;
                                            }
                                            str3 = str;
                                            i28 = i30;
                                            z4 = false;
                                        }
                                        i4 = i5;
                                        i3 = i312;
                                    }
                                }
                            }
                            i25 = 1;
                            runtimeException = null;
                        }
                    }
                }
            } catch (RuntimeException e8) {
                e = e8;
                runtimeException = e;
            }
            if (i30 >= i2) {
                if (runtimeException != null) {
                    throw new SerializationException("Error parsing JSON: ".concat(new String(cArr)), runtimeException);
                }
                return;
            }
            i6 = i25;
            while (i7 < i30) {
                if (cArr[i7] == '\n') {
                    i6++;
                }
            }
            int iMax9 = Math.max(0, i30 - 32);
            throw new SerializationException("Error parsing JSON on line " + i6 + " near: " + new String(cArr, iMax9, i30 - iMax9) + "*ERROR*" + new String(cArr, i30, Math.min(64, i2 - i30)), runtimeException);
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
