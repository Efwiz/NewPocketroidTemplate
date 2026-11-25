package org.bouncycastle.pqc.crypto.xmss;

import com.beust.jcommander.Parameters;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.eclipse.jdt.core.compiler.IProblem;

/* JADX INFO: loaded from: classes13.dex */
final class WOTSPlusOid implements XMSSOid {
    private static final Map<String, WOTSPlusOid> oidLookupTable;
    private final int oid;
    private final String stringRepresentation;

    static {
        HashMap map = new HashMap();
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA256, 32, 16, 67), new WOTSPlusOid(IProblem.ObjectHasNoSuperclass, "WOTSP_SHA2-256_W16"));
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA512, 64, 16, 131), new WOTSPlusOid(33554434, "WOTSP_SHA2-512_W16"));
        map.put(createKey("SHAKE128", 32, 16, 67), new WOTSPlusOid(50331651, "WOTSP_SHAKE128_W16"));
        map.put(createKey("SHAKE256", 64, 16, 131), new WOTSPlusOid(67108868, "WOTSP_SHAKE256_W16"));
        oidLookupTable = Collections.unmodifiableMap(map);
    }

    private WOTSPlusOid(int i, String str) {
        this.oid = i;
        this.stringRepresentation = str;
    }

    private static String createKey(String str, int i, int i2, int i3) {
        if (str != null) {
            return str + Parameters.DEFAULT_OPTION_PREFIXES + i + Parameters.DEFAULT_OPTION_PREFIXES + i2 + Parameters.DEFAULT_OPTION_PREFIXES + i3;
        }
        throw new NullPointerException("algorithmName == null");
    }

    protected static WOTSPlusOid lookup(String str, int i, int i2, int i3) {
        if (str != null) {
            return oidLookupTable.get(createKey(str, i, i2, i3));
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
