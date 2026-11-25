package com.reandroid.archive.block;

import com.android.apksig.internal.apk.ApkSigningBlockUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.coder.CoderHex;
import com.reandroid.arsc.coder.EncodeResult;
import com.reandroid.utils.HexUtil;
import java.util.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class SignatureId implements Comparable<SignatureId> {
    public static final String FILE_EXT_RAW = ".signature.info.bin";
    public static final SignatureId NULL;
    public static final SignatureId PADDING;
    public static final SignatureId STAMP_V1;
    public static final SignatureId STAMP_V2;
    public static final SignatureId V2;
    public static final SignatureId V3;
    public static final SignatureId V31;
    private static final SignatureId[] VALUES;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final int f222id;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f223name;
    private final int sort;

    private SignatureId(String str, int i, int i2) {
        this.f223name = str;
        this.f222id = i;
        this.sort = i2;
    }

    public String name() {
        return this.f223name;
    }

    public int getId() {
        return this.f222id;
    }

    public String toFileName() {
        if (this.f223name != null) {
            return this.f223name + FILE_EXT_RAW;
        }
        return HexUtil.toHex8(this.f222id) + FILE_EXT_RAW;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.f222id == ((SignatureId) obj).f222id;
    }

    @Override // java.lang.Comparable
    public int compareTo(SignatureId signatureId) {
        return Integer.compare(this.sort, signatureId.sort);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.f222id));
    }

    public String toString() {
        String str = this.f223name;
        return str != null ? str : "UNKNOWN(" + HexUtil.toHex8(this.f222id) + Constant.AFTER_QUTO;
    }

    public static SignatureId valueOf(String str) {
        if (str == null) {
            return null;
        }
        if (str.endsWith(FILE_EXT_RAW)) {
            str = str.substring(0, str.length() - FILE_EXT_RAW.length());
        }
        for (SignatureId signatureId : VALUES) {
            if (str.equalsIgnoreCase(signatureId.name())) {
                return signatureId;
            }
        }
        EncodeResult encodeResultEncode = CoderHex.INS.encode(str);
        if (encodeResultEncode != null) {
            return new SignatureId(null, encodeResultEncode.value, 99);
        }
        return null;
    }

    public static SignatureId valueOf(int i) {
        for (SignatureId signatureId : VALUES) {
            if (i == signatureId.getId()) {
                return signatureId;
            }
        }
        return new SignatureId(null, i, 99);
    }

    public static SignatureId[] values() {
        return (SignatureId[]) VALUES.clone();
    }

    static {
        SignatureId signatureId = new SignatureId("V2", 1896449818, 0);
        V2 = signatureId;
        SignatureId signatureId2 = new SignatureId("V3", -262969152, 1);
        V3 = signatureId2;
        SignatureId signatureId3 = new SignatureId("V31", 462663009, 2);
        V31 = signatureId3;
        SignatureId signatureId4 = new SignatureId("STAMP_V1", 722016414, 3);
        STAMP_V1 = signatureId4;
        SignatureId signatureId5 = new SignatureId("STAMP_V2", 1845461005, 4);
        STAMP_V2 = signatureId5;
        SignatureId signatureId6 = new SignatureId("PADDING", ApkSigningBlockUtils.VERITY_PADDING_BLOCK_ID, 9999);
        PADDING = signatureId6;
        SignatureId signatureId7 = new SignatureId("NULL", 0, 999);
        NULL = signatureId7;
        VALUES = new SignatureId[]{signatureId, signatureId2, signatureId3, signatureId4, signatureId5, signatureId6, signatureId7};
    }
}
