package org.eclipse.jgit.lib;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes13.dex */
public class GpgSignature implements Serializable {
    private static final long serialVersionUID = 1;
    private byte[] signature;

    public GpgSignature(byte[] bArr) {
        this.signature = bArr;
    }

    public String toExternalString() {
        return new String(this.signature, StandardCharsets.US_ASCII);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GpgSignature[");
        sb.append(this.signature != null ? "length " + this.signature.length : "null");
        sb.append("]");
        return sb.toString();
    }
}
