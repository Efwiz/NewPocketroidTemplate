package com.koushikdutta.async.dns;

import com.koushikdutta.async.AsyncDatagramSocket;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.future.SimpleFuture;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

/* JADX INFO: loaded from: classes10.dex */
public class Dns {
    private static int setFlag(int i, int i2, int i3) {
        return i | (i2 << i3);
    }

    public static Future<DnsResponse> lookup(String str) {
        return lookup(AsyncServer.getDefault(), str, false, null);
    }

    private static int setQuery(int i) {
        return setFlag(i, 0, 0);
    }

    private static int setRecursion(int i) {
        return setFlag(i, 1, 8);
    }

    private static void addName(ByteBuffer byteBuffer, String str) {
        for (String str2 : str.split("\\.")) {
            byteBuffer.put((byte) str2.length());
            byteBuffer.put(str2.getBytes());
        }
        byteBuffer.put((byte) 0);
    }

    public static Future<DnsResponse> lookup(AsyncServer asyncServer, String str) {
        return lookup(asyncServer, str, false, null);
    }

    public static Cancellable multicastLookup(AsyncServer asyncServer, String str, FutureCallback<DnsResponse> futureCallback) {
        return lookup(asyncServer, str, true, futureCallback);
    }

    public static Cancellable multicastLookup(String str, FutureCallback<DnsResponse> futureCallback) {
        return multicastLookup(AsyncServer.getDefault(), str, futureCallback);
    }

    public static Future<DnsResponse> lookup(AsyncServer asyncServer, String str, final boolean z, final FutureCallback<DnsResponse> futureCallback) {
        final AsyncDatagramSocket asyncDatagramSocketOpenDatagram;
        ByteBuffer byteBufferOrder = ByteBufferList.obtain(1024).order(ByteOrder.BIG_ENDIAN);
        short sNextInt = (short) new Random().nextInt();
        short query = (short) setQuery(0);
        if (!z) {
            query = (short) setRecursion(query);
        }
        byteBufferOrder.putShort(sNextInt);
        byteBufferOrder.putShort(query);
        byteBufferOrder.putShort(z ? (short) 1 : (short) 2);
        byteBufferOrder.putShort((short) 0);
        byteBufferOrder.putShort((short) 0);
        byteBufferOrder.putShort((short) 0);
        addName(byteBufferOrder, str);
        byteBufferOrder.putShort(z ? (short) 12 : (short) 1);
        byteBufferOrder.putShort((short) 1);
        if (!z) {
            addName(byteBufferOrder, str);
            byteBufferOrder.putShort((short) 28);
            byteBufferOrder.putShort((short) 1);
        }
        byteBufferOrder.flip();
        try {
            if (!z) {
                asyncDatagramSocketOpenDatagram = asyncServer.connectDatagram(new InetSocketAddress("8.8.8.8", 53));
            } else {
                asyncDatagramSocketOpenDatagram = AsyncServer.getDefault().openDatagram(new InetSocketAddress(0), true);
                Field declaredField = DatagramSocket.class.getDeclaredField("impl");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(asyncDatagramSocketOpenDatagram.getSocket());
                Method declaredMethod = obj.getClass().getDeclaredMethod("join", InetAddress.class);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(obj, InetAddress.getByName("224.0.0.251"));
                ((DatagramSocket) asyncDatagramSocketOpenDatagram.getSocket()).setBroadcast(true);
            }
            final SimpleFuture<DnsResponse> simpleFuture = new SimpleFuture<DnsResponse>() { // from class: com.koushikdutta.async.dns.Dns.1
                @Override // com.koushikdutta.async.future.SimpleCancellable
                protected void cleanup() {
                    super.cleanup();
                    asyncDatagramSocketOpenDatagram.close();
                }
            };
            asyncDatagramSocketOpenDatagram.setDataCallback(new DataCallback() { // from class: com.koushikdutta.async.dns.Dns.2
                @Override // com.koushikdutta.async.callback.DataCallback
                public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
                    try {
                        DnsResponse dnsResponse = DnsResponse.parse(byteBufferList);
                        dnsResponse.source = asyncDatagramSocketOpenDatagram.getRemoteAddress();
                        if (!z) {
                            asyncDatagramSocketOpenDatagram.close();
                            simpleFuture.setComplete(dnsResponse);
                        } else {
                            futureCallback.onCompleted(null, dnsResponse);
                        }
                    } catch (Exception unused) {
                    }
                    byteBufferList.recycle();
                }
            });
            if (!z) {
                asyncDatagramSocketOpenDatagram.write(new ByteBufferList(byteBufferOrder));
            } else {
                asyncDatagramSocketOpenDatagram.send(new InetSocketAddress("224.0.0.251", 5353), byteBufferOrder);
            }
            return simpleFuture;
        } catch (Exception e) {
            SimpleFuture simpleFuture2 = new SimpleFuture();
            simpleFuture2.setComplete(e);
            if (z) {
                futureCallback.onCompleted(e, null);
            }
            return simpleFuture2;
        }
    }
}
