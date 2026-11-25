package com.hivemq.client.internal.util;

import io.netty.util.NetUtil;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/* JADX INFO: loaded from: classes10.dex */
public final class InetSocketAddressUtil {
    public static InetSocketAddress create(String str, int i) {
        byte[] bArrCreateByteArrayFromIpAddressString = NetUtil.createByteArrayFromIpAddressString(str);
        if (bArrCreateByteArrayFromIpAddressString != null) {
            try {
                return new InetSocketAddress(InetAddress.getByAddress(bArrCreateByteArrayFromIpAddressString), i);
            } catch (UnknownHostException unused) {
            }
        }
        return InetSocketAddress.createUnresolved(str, i);
    }

    private InetSocketAddressUtil() {
    }
}
