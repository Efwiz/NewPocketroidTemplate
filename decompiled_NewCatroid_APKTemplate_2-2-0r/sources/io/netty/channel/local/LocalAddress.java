package io.netty.channel.local;

import io.netty.channel.Channel;
import io.netty.util.internal.ObjectUtil;
import java.net.SocketAddress;

/* JADX INFO: loaded from: classes11.dex */
public final class LocalAddress extends SocketAddress implements Comparable<LocalAddress> {
    public static final LocalAddress ANY = new LocalAddress("ANY");
    private static final long serialVersionUID = 4644331421130916435L;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final String f278id;
    private final String strVal;

    LocalAddress(Channel channel) {
        StringBuilder sb = new StringBuilder(16);
        sb.append("local:E");
        sb.append(Long.toHexString((((long) channel.hashCode()) & 4294967295L) | 4294967296L));
        sb.setCharAt(7, ':');
        this.f278id = sb.substring(6);
        this.strVal = sb.toString();
    }

    public LocalAddress(String str) {
        ObjectUtil.checkNotNull(str, "id");
        String lowerCase = str.trim().toLowerCase();
        if (lowerCase.isEmpty()) {
            throw new IllegalArgumentException("empty id");
        }
        this.f278id = lowerCase;
        this.strVal = "local:" + lowerCase;
    }

    public String id() {
        return this.f278id;
    }

    public int hashCode() {
        return this.f278id.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof LocalAddress) {
            return this.f278id.equals(((LocalAddress) obj).f278id);
        }
        return false;
    }

    @Override // java.lang.Comparable
    public int compareTo(LocalAddress localAddress) {
        return this.f278id.compareTo(localAddress.f278id);
    }

    public String toString() {
        return this.strVal;
    }
}
