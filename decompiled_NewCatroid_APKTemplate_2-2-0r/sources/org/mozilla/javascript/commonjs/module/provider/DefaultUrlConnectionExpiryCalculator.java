package org.mozilla.javascript.commonjs.module.provider;

import java.io.Serializable;
import java.net.URLConnection;
import org.apache.commons.lang3.time.DateUtils;

/* JADX INFO: loaded from: classes4.dex */
public class DefaultUrlConnectionExpiryCalculator implements UrlConnectionExpiryCalculator, Serializable {
    private static final long serialVersionUID = 1;
    private final long relativeExpiry;

    public DefaultUrlConnectionExpiryCalculator() {
        this(DateUtils.MILLIS_PER_MINUTE);
    }

    public DefaultUrlConnectionExpiryCalculator(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("relativeExpiry < 0");
        }
        this.relativeExpiry = j;
    }

    @Override // org.mozilla.javascript.commonjs.module.provider.UrlConnectionExpiryCalculator
    public long calculateExpiry(URLConnection uRLConnection) {
        return System.currentTimeMillis() + this.relativeExpiry;
    }
}
