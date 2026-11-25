package org.bouncycastle.pqc.crypto.xmss;

import com.beust.jcommander.Parameters;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.eclipse.jdt.core.compiler.IProblem;

/* JADX INFO: loaded from: classes13.dex */
public final class DefaultXMSSMTOid implements XMSSOid {
    private static final Map<String, DefaultXMSSMTOid> oidLookupTable;
    private final int oid;
    private final String stringRepresentation;

    static {
        HashMap map = new HashMap();
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA256, 32, 16, 67, 20, 2), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-256_W16_H20_D2"));
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA256, 32, 16, 67, 20, 4), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-256_W16_H20_D4"));
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA256, 32, 16, 67, 40, 2), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-256_W16_H40_D2"));
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA256, 32, 16, 67, 40, 2), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-256_W16_H40_D4"));
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA256, 32, 16, 67, 40, 4), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-256_W16_H40_D8"));
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA256, 32, 16, 67, 60, 8), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-256_W16_H60_D3"));
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA256, 32, 16, 67, 60, 6), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-256_W16_H60_D6"));
        map.put(createKey(McElieceCCA2KeyGenParameterSpec.SHA256, 32, 16, 67, 60, 12), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-256_W16_H60_D12"));
        map.put(createKey("SHA2-512", 64, 16, 131, 20, 2), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-512_W16_H20_D2"));
        map.put(createKey("SHA2-512", 64, 16, 131, 20, 4), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-512_W16_H20_D4"));
        map.put(createKey("SHA2-512", 64, 16, 131, 40, 2), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-512_W16_H40_D2"));
        map.put(createKey("SHA2-512", 64, 16, 131, 40, 4), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-512_W16_H40_D4"));
        map.put(createKey("SHA2-512", 64, 16, 131, 40, 8), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-512_W16_H40_D8"));
        map.put(createKey("SHA2-512", 64, 16, 131, 60, 3), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-512_W16_H60_D3"));
        map.put(createKey("SHA2-512", 64, 16, 131, 60, 6), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-512_W16_H60_D6"));
        map.put(createKey("SHA2-512", 64, 16, 131, 60, 12), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHA2-512_W16_H60_D12"));
        map.put(createKey("SHAKE128", 32, 16, 67, 20, 2), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE128_W16_H20_D2"));
        map.put(createKey("SHAKE128", 32, 16, 67, 20, 4), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE128_W16_H20_D4"));
        map.put(createKey("SHAKE128", 32, 16, 67, 40, 2), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE128_W16_H40_D2"));
        map.put(createKey("SHAKE128", 32, 16, 67, 40, 4), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE128_W16_H40_D4"));
        map.put(createKey("SHAKE128", 32, 16, 67, 40, 8), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE128_W16_H40_D8"));
        map.put(createKey("SHAKE128", 32, 16, 67, 60, 3), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE128_W16_H60_D3"));
        map.put(createKey("SHAKE128", 32, 16, 67, 60, 6), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE128_W16_H60_D6"));
        map.put(createKey("SHAKE128", 32, 16, 67, 60, 12), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE128_W16_H60_D12"));
        map.put(createKey("SHAKE256", 64, 16, 131, 20, 2), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE256_W16_H20_D2"));
        map.put(createKey("SHAKE256", 64, 16, 131, 20, 4), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE256_W16_H20_D4"));
        map.put(createKey("SHAKE256", 64, 16, 131, 40, 2), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE256_W16_H40_D2"));
        map.put(createKey("SHAKE256", 64, 16, 131, 40, 4), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE256_W16_H40_D4"));
        map.put(createKey("SHAKE256", 64, 16, 131, 40, 8), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE256_W16_H40_D8"));
        map.put(createKey("SHAKE256", 64, 16, 131, 60, 3), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE256_W16_H60_D3"));
        map.put(createKey("SHAKE256", 64, 16, 131, 60, 6), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE256_W16_H60_D6"));
        map.put(createKey("SHAKE256", 64, 16, 131, 60, 12), new DefaultXMSSMTOid(IProblem.ObjectHasNoSuperclass, "XMSSMT_SHAKE256_W16_H60_D12"));
        oidLookupTable = Collections.unmodifiableMap(map);
    }

    private DefaultXMSSMTOid(int i, String str) {
        this.oid = i;
        this.stringRepresentation = str;
    }

    private static String createKey(String str, int i, int i2, int i3, int i4, int i5) {
        if (str != null) {
            return str + Parameters.DEFAULT_OPTION_PREFIXES + i + Parameters.DEFAULT_OPTION_PREFIXES + i2 + Parameters.DEFAULT_OPTION_PREFIXES + i3 + Parameters.DEFAULT_OPTION_PREFIXES + i4 + Parameters.DEFAULT_OPTION_PREFIXES + i5;
        }
        throw new NullPointerException("algorithmName == null");
    }

    public static DefaultXMSSMTOid lookup(String str, int i, int i2, int i3, int i4, int i5) {
        if (str != null) {
            return oidLookupTable.get(createKey(str, i, i2, i3, i4, i5));
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
