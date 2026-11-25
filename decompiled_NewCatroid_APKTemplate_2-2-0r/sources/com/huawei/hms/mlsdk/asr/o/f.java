package com.huawei.hms.mlsdk.asr.o;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import androidx.lifecycle.CoroutineLiveDataKt;
import com.huawei.hms.mlsdk.asr.engine.utils.SmartLogger;
import com.huawei.secure.android.common.ssl.SecureSSLSocketFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/* JADX INFO: compiled from: WebSocketManager.java */
/* JADX INFO: loaded from: classes10.dex */
public class f implements com.huawei.hms.mlsdk.asr.o.d {
    private static HandlerThread u;
    private String a;
    private List<String> b;
    private e d;
    private OkHttpClient f;
    private Request g;
    private SSLSocketFactory i;
    private X509TrustManager j;
    private Map<String, String> k;
    private Handler l;
    private Lock n;
    private long q;
    private boolean r;
    private int c = 0;
    private WebSocket e = null;
    private volatile int h = -1;
    private volatile boolean m = false;
    private boolean o = true;
    private Runnable p = new a();
    private Runnable s = new b();
    private WebSocketListener t = new c();

    /* JADX INFO: compiled from: WebSocketManager.java */
    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (f.this.d != null) {
                f.this.d.a();
            }
            f.this.f();
        }
    }

    /* JADX INFO: compiled from: WebSocketManager.java */
    class b implements Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (f.this.h == 1) {
                f.this.e.send("{\"command\":\"HEART\"}");
                Log.d("WebSocketManager", "mHeartMessage: {\"command\":\"HEART\"}");
                f.this.l.postDelayed(this, f.this.q);
            }
        }
    }

    /* JADX INFO: compiled from: WebSocketManager.java */
    public static final class d {
        private OkHttpClient d;
        private Request e;
        private SSLSocketFactory f;
        private X509TrustManager g;
        private Map<String, String> h;
        private boolean m;
        private String a = "";
        private List<String> b = new ArrayList();
        private boolean c = true;
        private int i = Integer.MAX_VALUE;
        private boolean j = true;
        private String k = "{\"command\":\"HEART\"}";
        private long l = CoroutineLiveDataKt.DEFAULT_TIMEOUT;

        public d(Context context) {
        }

        public d a(List<String> list) {
            this.b.clear();
            this.b.addAll(list);
            return this;
        }

        public d b(boolean z) {
            this.m = z;
            return this;
        }

        public d a(boolean z) {
            this.c = z;
            return this;
        }

        public d a(SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager) {
            this.f = sSLSocketFactory;
            this.g = x509TrustManager;
            return this;
        }

        public d a(Map<String, String> map) {
            this.h = map;
            return this;
        }
    }

    static {
        HandlerThread handlerThread = new HandlerThread("dispatch Message");
        u = handlerThread;
        handlerThread.start();
    }

    public f(d dVar) {
        this.a = "";
        this.b = null;
        this.q = CoroutineLiveDataKt.DEFAULT_TIMEOUT;
        this.f = dVar.d;
        this.g = dVar.e;
        this.a = dVar.a;
        if (!dVar.b.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            this.b = arrayList;
            arrayList.addAll(dVar.b);
        }
        boolean unused = dVar.c;
        this.i = dVar.f;
        this.j = dVar.g;
        this.k = dVar.h;
        int unused2 = dVar.i;
        this.n = new ReentrantLock();
        this.l = new Handler(u.getLooper());
        boolean unused3 = dVar.j;
        String unused4 = dVar.k;
        this.q = dVar.l;
        this.r = dVar.m;
    }

    static /* synthetic */ int f(f fVar) {
        int i = fVar.c;
        fVar.c = i + 1;
        return i;
    }

    private void g() {
        X509TrustManager x509TrustManager;
        if (this.f == null) {
            Log.d("WebSocketManager", "createWebSocket()");
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            SSLSocketFactory sSLSocketFactory = this.i;
            if (sSLSocketFactory == null || (x509TrustManager = this.j) == null) {
                Log.d("WebSocketManager", "SslSocketFactory or TrustManger is null");
            } else {
                builder.sslSocketFactory(sSLSocketFactory, x509TrustManager);
                builder.connectTimeout(20L, TimeUnit.SECONDS).callTimeout(20L, TimeUnit.SECONDS).readTimeout(20L, TimeUnit.SECONDS).hostnameVerifier(SecureSSLSocketFactory.STRICT_HOSTNAME_VERIFIER).connectionPool(new ConnectionPool(5, 5L, TimeUnit.SECONDS));
                this.f = builder.build();
            }
        }
        if (this.b == null && this.a.isEmpty()) {
            Log.d("WebSocketManager", "not config url");
        } else {
            try {
                Map<String, String> map = this.k;
                Request.Builder builder2 = new Request.Builder();
                String str = this.a.isEmpty() ? this.b.get(0) : this.a;
                this.a = str;
                Request.Builder builderUrl = builder2.url(str);
                if (map != null) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        builderUrl.header(entry.getKey(), entry.getValue());
                    }
                }
                this.g = builderUrl.build();
            } catch (IllegalArgumentException e) {
                Log.e("WebSocketManager", com.huawei.hms.mlsdk.asr.o.a.a("createRequest(): IllegalArgumentException").append(e.getMessage()).toString());
            } catch (Exception e2) {
                Log.e("WebSocketManager", com.huawei.hms.mlsdk.asr.o.a.a("createRequest(): Exception: ").append(e2.getMessage()).toString());
            }
        }
        OkHttpClient okHttpClient = this.f;
        if (okHttpClient == null || this.g == null) {
            return;
        }
        okHttpClient.dispatcher().cancelAll();
        try {
            this.n.lockInterruptibly();
            try {
                this.f.newWebSocket(this.g, this.t);
            } finally {
                this.n.unlock();
            }
        } catch (InterruptedException unused) {
            Log.d("WebSocketManager", "createWebSocket() InterruptedException");
        }
    }

    static /* synthetic */ void k(f fVar) {
        fVar.l.removeCallbacks(fVar.s);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void f() {
        int iA = a();
        if (iA != 0 && iA != 1) {
            a(0);
            g();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void h() {
        e eVar;
        if (this.h == -1) {
            Log.d("WebSocketManager", "webSocket is already disconnected");
            return;
        }
        this.l.removeCallbacks(this.p);
        this.l.removeCallbacks(this.s);
        OkHttpClient okHttpClient = this.f;
        if (okHttpClient != null) {
            okHttpClient.dispatcher().cancelAll();
        }
        WebSocket webSocket = this.e;
        if (webSocket != null) {
            boolean zClose = webSocket.close(1000, "NORMAL CLOSE");
            Log.d("WebSocketManager", "disconnect(), isClosed is " + zClose);
            this.e = null;
            if (!zClose && (eVar = this.d) != null) {
                eVar.b(1001, "ABNORMAL CLOSE");
            }
        }
        a(-1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        SmartLogger.i("WebSocketManager", "using other url to connect server");
        a(2);
        this.l.post(this.p);
    }

    public boolean b() {
        return this.m;
    }

    public boolean c() {
        return this.h == 1;
    }

    public void d() {
        this.m = false;
        f();
    }

    public void e() {
        this.m = true;
        h();
    }

    /* JADX INFO: compiled from: WebSocketManager.java */
    class c extends WebSocketListener {
        c() {
        }

        @Override // okhttp3.WebSocketListener
        public void onClosed(WebSocket webSocket, int i, String str) {
            f.k(f.this);
            Log.d("WebSocketManager", "onClosed: code: " + i + "reason: " + str);
            if (f.this.d != null) {
                f.this.d.b(i, str);
            }
        }

        @Override // okhttp3.WebSocketListener
        public void onClosing(WebSocket webSocket, int i, String str) {
            f.this.a(3);
            SmartLogger.i("WebSocketManager", "onClosing: code: " + i + ", reason: " + str);
            f.k(f.this);
            if (f.this.d != null) {
                f.this.d.a(i, str);
            }
        }

        @Override // okhttp3.WebSocketListener
        public void onFailure(WebSocket webSocket, Throwable th, Response response) {
            f.this.a(-1);
            SmartLogger.i("WebSocketManager", "onFailure: " + th.getMessage());
            if (f.this.r) {
                f.a(f.this, th, response);
                return;
            }
            if (!f.this.o || f.this.b == null) {
                f.a(f.this, th, response);
                return;
            }
            f.f(f.this);
            if (f.this.c >= f.this.b.size()) {
                f.a(f.this, th, response);
                return;
            }
            f fVar = f.this;
            fVar.a = (String) fVar.b.get(f.this.c);
            f.this.i();
        }

        @Override // okhttp3.WebSocketListener
        public void onMessage(WebSocket webSocket, String str) {
            SmartLogger.d("WebSocketManager", "onMessage: " + str);
            if (f.this.d != null) {
                f.this.d.a(str);
            }
        }

        @Override // okhttp3.WebSocketListener
        public void onOpen(WebSocket webSocket, Response response) {
            f.this.a(1);
            SmartLogger.i("WebSocketManager", "onOpen()");
            f.this.o = false;
            f.this.c = 0;
            f.this.e = webSocket;
            f.g(f.this);
            if (f.this.d != null) {
                f.this.d.a(response);
            }
            f.this.l.postDelayed(f.this.s, f.this.q);
            if (f.this.m) {
                f.this.h();
            }
        }

        @Override // okhttp3.WebSocketListener
        public void onMessage(WebSocket webSocket, ByteString byteString) {
            if (f.this.d != null) {
                f.this.d.a(byteString);
            }
        }
    }

    public void a(e eVar) {
        this.d = eVar;
    }

    public synchronized int a() {
        return this.h;
    }

    public synchronized void a(int i) {
        this.h = i;
    }

    public boolean a(String str) {
        boolean zSend = (this.e == null || this.h != 1) ? false : this.e.send(str);
        if (!zSend) {
            a(0);
            g();
        }
        return zSend;
    }

    public boolean a(ByteString byteString) {
        if (this.e == null || this.h != 1) {
            return false;
        }
        return this.e.send(byteString);
    }

    static /* synthetic */ void a(f fVar, Throwable th, Response response) {
        e eVar = fVar.d;
        if (eVar != null) {
            eVar.a(th, response);
        }
    }

    static /* synthetic */ void g(f fVar) {
        fVar.l.removeCallbacks(fVar.p);
    }
}
