package io.netty.channel.embedded;

import java.net.SocketAddress;

/* JADX INFO: loaded from: classes11.dex */
final class EmbeddedSocketAddress extends SocketAddress {
    private static final long serialVersionUID = 1400788804624980619L;

    EmbeddedSocketAddress() {
    }

    public String toString() {
        return "embedded";
    }
}
