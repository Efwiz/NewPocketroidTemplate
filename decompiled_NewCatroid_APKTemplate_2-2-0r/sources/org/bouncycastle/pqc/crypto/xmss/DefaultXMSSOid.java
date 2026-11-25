package org.bouncycastle.pqc.crypto.xmss;

import com.beust.jcommander.Parameters;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.eclipse.jdt.core.compiler.IProblem;

/* JADX INFO: loaded from: classes13.dex */
public final class DefaultXMSSOid implements XMSSOid {
    private static final Map<String, DefaultXMSSOid> oidLookupTable;
    private final int oid;
    private final String stringRepresentation;

    static {
        HashMap map = new HashMap();
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA256, 32, 16, 67, 10), new DefaultXMSSOid(IProblem.ObjectHasNoSuperclass, "XMSS_SHA2-256_W16_H10"));
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA256, 32, 16, 67, 16), new DefaultXMSSOid(33554434, "XMSS_SHA2-256_W16_H16"));
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA256, 32, 16, 67, 20), new DefaultXMSSOid(50331651, "XMSS_SHA2-256_W16_H20"));
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA512, 64, 16, 131, 10), new DefaultXMSSOid(67108868, "XMSS_SHA2-512_W16_H10"));
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA512, 64, 16, 131, 16), new DefaultXMSSOid(83886085, "XMSS_SHA2-512_W16_H16"));
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA512, 64, 16, 131, 20), new DefaultXMSSOid(100663302, "XMSS_SHA2-512_W16_H20"));
        map.put(createKey("SHAKE128", 32, 16, 67, 10), new DefaultXMSSOid(117440519, "XMSS_SHAKE128_W16_H10"));
        map.put(createKey("SHAKE128", 32, 16, 67, 16), new DefaultXMSSOid(134217736, "XMSS_SHAKE128_W16_H16"));
        map.put(createKey("SHAKE128", 32, 16, 67, 20), new DefaultXMSSOid(150994953, "XMSS_SHAKE128_W16_H20"));
        map.put(createKey("SHAKE256", 64, 16, 131, 10), new DefaultXMSSOid(167772170, "XMSS_SHAKE256_W16_H10"));
        map.put(createKey("SHAKE256", 64, 16, 131, 16), new DefaultXMSSOid(184549387, "XMSS_SHAKE256_W16_H16"));
        map.put(createKey("SHAKE256", 64, 16, 131, 20), new DefaultXMSSOid(201326604, "XMSS_SHAKE256_W16_H20"));
        oidLookupTable = Collections.unmodifiableMap(map);
    }

    private DefaultXMSSOid(int i, String str) {
        this.oid = i;
        this.stringRepresentation = str;
    }

    private static String createKey(String str, int i, int i2, int i3, int i4) {
        if (str != null) {
            return str + Parameters.DEFAULT_OPTION_PREFIXES + i + Parameters.DEFAULT_OPTION_PREFIXES + i2 + Parameters.DEFAULT_OPTION_PREFIXES + i3 + Parameters.DEFAULT_OPTION_PREFIXES + i4;
        }
        throw new NullPointerException("algorithmName == null");
    }

    public static DefaultXMSSOid lookup(String str, int i, int i2, int i3, int i4) {
        if (str != null) {
            return oidLookupTable.get(createKey(str, i, i2, i3, i4));
        }
        throw new NullPointerException("algorithmName == null");
    }

    @Override // org.bouncycastle.pqc.crypto.xmss.XMSSOid
    public int getOid() {
        return this.oid;
    }

    @Override // org.bouncycastle.pqc.crypto.xmss.XMSSOid
    public String toString() {
        return this.stringRepresentation;
    }
}
