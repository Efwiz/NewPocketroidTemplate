package org.eclipse.jgit.transport;

/* JADX INFO: loaded from: classes13.dex */
public final class CapabilitiesV2Request {
    private CapabilitiesV2Request() {
    }

    /* synthetic */ CapabilitiesV2Request(CapabilitiesV2Request capabilitiesV2Request) {
        this();
    }

    public static Builder builder() {
        return new Builder(null);
    }

    public static final class Builder {
        private Builder() {
        }

        /* synthetic */ Builder(Builder builder) {
            this();
        }

        public CapabilitiesV2Request build() {
            return new CapabilitiesV2Request(null);
        }
    }
}
