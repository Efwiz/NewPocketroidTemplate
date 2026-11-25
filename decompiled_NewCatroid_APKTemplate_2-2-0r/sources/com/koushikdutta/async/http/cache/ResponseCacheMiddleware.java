package com.koushikdutta.async.http.cache;

import android.net.Uri;
import android.util.Base64;
import com.koushikdutta.async.AsyncSSLSocket;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.FilteredDataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.SimpleCancellable;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.SimpleMiddleware;
import com.koushikdutta.async.util.Allocator;
import com.koushikdutta.async.util.Charsets;
import com.koushikdutta.async.util.FileCache;
import com.koushikdutta.async.util.StreamUtility;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.CacheResponse;
import java.nio.ByteBuffer;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.SSLEngine;

/* JADX INFO: loaded from: classes10.dex */
public class ResponseCacheMiddleware extends SimpleMiddleware {
    public static final String CACHE = "cache";
    public static final String CONDITIONAL_CACHE = "conditional-cache";
    public static final int ENTRY_BODY = 1;
    public static final int ENTRY_COUNT = 2;
    public static final int ENTRY_METADATA = 0;
    private static final String LOGTAG = "AsyncHttpCache";
    public static final String SERVED_FROM = "X-Served-From";
    private FileCache cache;
    private int cacheHitCount;
    private int cacheStoreCount;
    private boolean caching = true;
    private int conditionalCacheHitCount;
    private int networkCount;
    private AsyncServer server;
    private int writeAbortCount;
    private int writeSuccessCount;

    public static class CacheData {
        ResponseHeaders cachedResponseHeaders;
        EntryCacheResponse candidate;
        long contentLength;
        FileInputStream[] snapshot;
    }

    static /* synthetic */ int access$508(ResponseCacheMiddleware responseCacheMiddleware) {
        int i = responseCacheMiddleware.writeSuccessCount;
        responseCacheMiddleware.writeSuccessCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$608(ResponseCacheMiddleware responseCacheMiddleware) {
        int i = responseCacheMiddleware.writeAbortCount;
        responseCacheMiddleware.writeAbortCount = i + 1;
        return i;
    }

    private ResponseCacheMiddleware() {
    }

    public static ResponseCacheMiddleware addCache(AsyncHttpClient asyncHttpClient, File file, long j) throws IOException {
        Iterator<AsyncHttpClientMiddleware> it = asyncHttpClient.getMiddleware().iterator();
        while (it.hasNext()) {
            if (it.next() instanceof ResponseCacheMiddleware) {
                throw new IOException("Response cache already added to http client");
            }
        }
        ResponseCacheMiddleware responseCacheMiddleware = new ResponseCacheMiddleware();
        responseCacheMiddleware.server = asyncHttpClient.getServer();
        responseCacheMiddleware.cache = new FileCache(file, j, false);
        asyncHttpClient.insertMiddleware(responseCacheMiddleware);
        return responseCacheMiddleware;
    }

    public FileCache getFileCache() {
        return this.cache;
    }

    public boolean getCaching() {
        return this.caching;
    }

    public void setCaching(boolean z) {
        this.caching = z;
    }

    public void removeFromCache(Uri uri) {
        getFileCache().remove(FileCache.toKeyString(uri));
    }

    @Override // com.koushikdutta.async.http.SimpleMiddleware, com.koushikdutta.async.http.AsyncHttpClientMiddleware
    public Cancellable getSocket(final AsyncHttpClientMiddleware.GetSocketData getSocketData) {
        FileInputStream[] fileInputStreamArr;
        RequestHeaders requestHeaders = new RequestHeaders(getSocketData.request.getUri(), RawHeaders.fromMultimap(getSocketData.request.getHeaders().getMultiMap()));
        getSocketData.state.put("request-headers", requestHeaders);
        if (this.cache == null || !this.caching || requestHeaders.isNoCache()) {
            this.networkCount++;
            return null;
        }
        try {
            fileInputStreamArr = this.cache.get(FileCache.toKeyString(getSocketData.request.getUri()), 2);
            try {
                if (fileInputStreamArr == null) {
                    this.networkCount++;
                    return null;
                }
                long jAvailable = fileInputStreamArr[1].available();
                Entry entry = new Entry(fileInputStreamArr[0]);
                if (!entry.matches(getSocketData.request.getUri(), getSocketData.request.getMethod(), getSocketData.request.getHeaders().getMultiMap())) {
                    this.networkCount++;
                    StreamUtility.closeQuietly(fileInputStreamArr);
                    return null;
                }
                EntryCacheResponse entryCacheResponse = new EntryCacheResponse(entry, fileInputStreamArr[1]);
                try {
                    Map<String, List<String>> headers = entryCacheResponse.getHeaders();
                    FileInputStream body = entryCacheResponse.getBody();
                    if (headers == null || body == null) {
                        this.networkCount++;
                        StreamUtility.closeQuietly(fileInputStreamArr);
                        return null;
                    }
                    RawHeaders rawHeadersFromMultimap = RawHeaders.fromMultimap(headers);
                    ResponseHeaders responseHeaders = new ResponseHeaders(getSocketData.request.getUri(), rawHeadersFromMultimap);
                    rawHeadersFromMultimap.set("Content-Length", String.valueOf(jAvailable));
                    rawHeadersFromMultimap.removeAll("Content-Encoding");
                    rawHeadersFromMultimap.removeAll("Transfer-Encoding");
                    responseHeaders.setLocalTimestamps(System.currentTimeMillis(), System.currentTimeMillis());
                    ResponseSource responseSourceChooseResponseSource = responseHeaders.chooseResponseSource(System.currentTimeMillis(), requestHeaders);
                    if (responseSourceChooseResponseSource == ResponseSource.CACHE) {
                        getSocketData.request.logi("Response retrieved from cache");
                        final CachedSocket cachedSSLSocket = entry.isHttps() ? new CachedSSLSocket(entryCacheResponse, jAvailable) : new CachedSocket(entryCacheResponse, jAvailable);
                        cachedSSLSocket.pending.add(ByteBuffer.wrap(rawHeadersFromMultimap.toHeaderString().getBytes()));
                        this.server.post(new Runnable() { // from class: com.koushikdutta.async.http.cache.ResponseCacheMiddleware.1
                            @Override // java.lang.Runnable
                            public void run() {
                                getSocketData.connectCallback.onConnectCompleted(null, cachedSSLSocket);
                                cachedSSLSocket.sendCachedDataOnNetworkThread();
                            }
                        });
                        this.cacheHitCount++;
                        getSocketData.state.put("socket-owner", this);
                        SimpleCancellable simpleCancellable = new SimpleCancellable();
                        simpleCancellable.setComplete();
                        return simpleCancellable;
                    }
                    if (responseSourceChooseResponseSource == ResponseSource.CONDITIONAL_CACHE) {
                        getSocketData.request.logi("Response may be served from conditional cache");
                        CacheData cacheData = new CacheData();
                        cacheData.snapshot = fileInputStreamArr;
                        cacheData.contentLength = jAvailable;
                        cacheData.cachedResponseHeaders = responseHeaders;
                        cacheData.candidate = entryCacheResponse;
                        getSocketData.state.put("cache-data", cacheData);
                        return null;
                    }
                    getSocketData.request.logd("Response can not be served from cache");
                    this.networkCount++;
                    StreamUtility.closeQuietly(fileInputStreamArr);
                    return null;
                } catch (Exception unused) {
                    this.networkCount++;
                    StreamUtility.closeQuietly(fileInputStreamArr);
                    return null;
                }
            } catch (IOException unused2) {
                this.networkCount++;
                StreamUtility.closeQuietly(fileInputStreamArr);
                return null;
            }
        } catch (IOException unused3) {
            fileInputStreamArr = null;
        }
    }

    public int getConditionalCacheHitCount() {
        return this.conditionalCacheHitCount;
    }

    public int getCacheHitCount() {
        return this.cacheHitCount;
    }

    public int getNetworkCount() {
        return this.networkCount;
    }

    public int getCacheStoreCount() {
        return this.cacheStoreCount;
    }

    @Override // com.koushikdutta.async.http.SimpleMiddleware, com.koushikdutta.async.http.AsyncHttpClientMiddleware
    public void onBodyDecoder(AsyncHttpClientMiddleware.OnBodyDataOnRequestSentData onBodyDataOnRequestSentData) {
        if (((CachedSocket) Util.getWrappedSocket(onBodyDataOnRequestSentData.socket, CachedSocket.class)) != null) {
            onBodyDataOnRequestSentData.response.headers().set(SERVED_FROM, CACHE);
            return;
        }
        CacheData cacheData = (CacheData) onBodyDataOnRequestSentData.state.get("cache-data");
        RawHeaders rawHeadersFromMultimap = RawHeaders.fromMultimap(onBodyDataOnRequestSentData.response.headers().getMultiMap());
        rawHeadersFromMultimap.removeAll("Content-Length");
        rawHeadersFromMultimap.setStatusLine(String.format(Locale.ENGLISH, "%s %s %s", onBodyDataOnRequestSentData.response.protocol(), Integer.valueOf(onBodyDataOnRequestSentData.response.code()), onBodyDataOnRequestSentData.response.message()));
        ResponseHeaders responseHeaders = new ResponseHeaders(onBodyDataOnRequestSentData.request.getUri(), rawHeadersFromMultimap);
        onBodyDataOnRequestSentData.state.put("response-headers", responseHeaders);
        if (cacheData != null) {
            if (cacheData.cachedResponseHeaders.validate(responseHeaders)) {
                onBodyDataOnRequestSentData.request.logi("Serving response from conditional cache");
                ResponseHeaders responseHeadersCombine = cacheData.cachedResponseHeaders.combine(responseHeaders);
                onBodyDataOnRequestSentData.response.headers(new Headers(responseHeadersCombine.getHeaders().toMultimap()));
                onBodyDataOnRequestSentData.response.code(responseHeadersCombine.getHeaders().getResponseCode());
                onBodyDataOnRequestSentData.response.message(responseHeadersCombine.getHeaders().getResponseMessage());
                onBodyDataOnRequestSentData.response.headers().set(SERVED_FROM, CONDITIONAL_CACHE);
                this.conditionalCacheHitCount++;
                CachedBodyEmitter cachedBodyEmitter = new CachedBodyEmitter(cacheData.candidate, cacheData.contentLength);
                cachedBodyEmitter.setDataEmitter(onBodyDataOnRequestSentData.bodyEmitter);
                onBodyDataOnRequestSentData.bodyEmitter = cachedBodyEmitter;
                cachedBodyEmitter.sendCachedData();
                return;
            }
            onBodyDataOnRequestSentData.state.remove("cache-data");
            StreamUtility.closeQuietly(cacheData.snapshot);
        }
        if (this.caching) {
            RequestHeaders requestHeaders = (RequestHeaders) onBodyDataOnRequestSentData.state.get("request-headers");
            if (requestHeaders == null || !responseHeaders.isCacheable(requestHeaders) || !onBodyDataOnRequestSentData.request.getMethod().equals("GET")) {
                this.networkCount++;
                onBodyDataOnRequestSentData.request.logd("Response is not cacheable");
                return;
            }
            String keyString = FileCache.toKeyString(onBodyDataOnRequestSentData.request.getUri());
            Entry entry = new Entry(onBodyDataOnRequestSentData.request.getUri(), requestHeaders.getHeaders().getAll(responseHeaders.getVaryFields()), onBodyDataOnRequestSentData.request, responseHeaders.getHeaders());
            BodyCacher bodyCacher = new BodyCacher();
            EntryEditor entryEditor = new EntryEditor(keyString);
            try {
                entry.writeTo(entryEditor);
                entryEditor.newOutputStream(1);
                bodyCacher.editor = entryEditor;
                bodyCacher.setDataEmitter(onBodyDataOnRequestSentData.bodyEmitter);
                onBodyDataOnRequestSentData.bodyEmitter = bodyCacher;
                onBodyDataOnRequestSentData.state.put("body-cacher", bodyCacher);
                onBodyDataOnRequestSentData.request.logd("Caching response");
                this.cacheStoreCount++;
            } catch (Exception unused) {
                entryEditor.abort();
                this.networkCount++;
            }
        }
    }

    @Override // com.koushikdutta.async.http.SimpleMiddleware, com.koushikdutta.async.http.AsyncHttpClientMiddleware
    public void onResponseComplete(AsyncHttpClientMiddleware.OnResponseCompleteDataOnRequestSentData onResponseCompleteDataOnRequestSentData) {
        CacheData cacheData = (CacheData) onResponseCompleteDataOnRequestSentData.state.get("cache-data");
        if (cacheData != null && cacheData.snapshot != null) {
            StreamUtility.closeQuietly(cacheData.snapshot);
        }
        CachedSocket cachedSocket = (CachedSocket) Util.getWrappedSocket(onResponseCompleteDataOnRequestSentData.socket, CachedSocket.class);
        if (cachedSocket != null) {
            StreamUtility.closeQuietly(cachedSocket.cacheResponse.getBody());
        }
        BodyCacher bodyCacher = (BodyCacher) onResponseCompleteDataOnRequestSentData.state.get("body-cacher");
        if (bodyCacher != null) {
            if (onResponseCompleteDataOnRequestSentData.exception != null) {
                bodyCacher.abort();
            } else {
                bodyCacher.commit();
            }
        }
    }

    public void clear() {
        FileCache fileCache = this.cache;
        if (fileCache != null) {
            fileCache.clear();
        }
    }

    private static class BodyCacher extends FilteredDataEmitter {
        ByteBufferList cached;
        EntryEditor editor;

        private BodyCacher() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.koushikdutta.async.DataEmitterBase
        public void report(Exception exc) {
            super.report(exc);
            if (exc != null) {
                abort();
            }
        }

        @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.callback.DataCallback
        public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
            ByteBufferList byteBufferList2 = this.cached;
            if (byteBufferList2 != null) {
                super.onDataAvailable(dataEmitter, byteBufferList2);
                if (this.cached.remaining() > 0) {
                    return;
                } else {
                    this.cached = null;
                }
            }
            ByteBufferList byteBufferList3 = new ByteBufferList();
            try {
                try {
                    EntryEditor entryEditor = this.editor;
                    if (entryEditor != null) {
                        FileOutputStream fileOutputStreamNewOutputStream = entryEditor.newOutputStream(1);
                        if (fileOutputStreamNewOutputStream != null) {
                            while (!byteBufferList.isEmpty()) {
                                ByteBuffer byteBufferRemove = byteBufferList.remove();
                                try {
                                    ByteBufferList.writeOutputStream(fileOutputStreamNewOutputStream, byteBufferRemove);
                                    byteBufferList3.add(byteBufferRemove);
                                } catch (Throwable th) {
                                    byteBufferList3.add(byteBufferRemove);
                                    throw th;
                                }
                            }
                        } else {
                            abort();
                        }
                    }
                } catch (Throwable th2) {
                    byteBufferList.get(byteBufferList3);
                    byteBufferList3.get(byteBufferList);
                    throw th2;
                }
            } catch (Exception unused) {
                abort();
            }
            byteBufferList.get(byteBufferList3);
            byteBufferList3.get(byteBufferList);
            super.onDataAvailable(dataEmitter, byteBufferList);
            if (this.editor == null || byteBufferList.remaining() <= 0) {
                return;
            }
            ByteBufferList byteBufferList4 = new ByteBufferList();
            this.cached = byteBufferList4;
            byteBufferList.get(byteBufferList4);
        }

        @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.DataEmitter
        public void close() {
            abort();
            super.close();
        }

        public void abort() {
            EntryEditor entryEditor = this.editor;
            if (entryEditor != null) {
                entryEditor.abort();
                this.editor = null;
            }
        }

        public void commit() {
            EntryEditor entryEditor = this.editor;
            if (entryEditor != null) {
                entryEditor.commit();
                this.editor = null;
            }
        }
    }

    private static class CachedBodyEmitter extends FilteredDataEmitter {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        boolean allowEnd;
        EntryCacheResponse cacheResponse;
        private boolean paused;
        ByteBufferList pending = new ByteBufferList();
        private Allocator allocator = new Allocator();
        Runnable sendCachedDataRunnable = new Runnable() { // from class: com.koushikdutta.async.http.cache.ResponseCacheMiddleware.CachedBodyEmitter.1
            @Override // java.lang.Runnable
            public void run() {
                CachedBodyEmitter.this.sendCachedDataOnNetworkThread();
            }
        };

        public CachedBodyEmitter(EntryCacheResponse entryCacheResponse, long j) {
            this.cacheResponse = entryCacheResponse;
            this.allocator.setCurrentAlloc((int) j);
        }

        void sendCachedDataOnNetworkThread() {
            if (this.pending.remaining() > 0) {
                super.onDataAvailable(this, this.pending);
                if (this.pending.remaining() > 0) {
                    return;
                }
            }
            try {
                ByteBuffer byteBufferAllocate = this.allocator.allocate();
                int i = this.cacheResponse.getBody().read(byteBufferAllocate.array(), byteBufferAllocate.arrayOffset(), byteBufferAllocate.capacity());
                if (i == -1) {
                    ByteBufferList.reclaim(byteBufferAllocate);
                    this.allowEnd = true;
                    report(null);
                    return;
                }
                this.allocator.track(i);
                byteBufferAllocate.limit(i);
                this.pending.add(byteBufferAllocate);
                super.onDataAvailable(this, this.pending);
                if (this.pending.remaining() > 0) {
                    return;
                }
                getServer().postDelayed(this.sendCachedDataRunnable, 10L);
            } catch (IOException e) {
                this.allowEnd = true;
                report(e);
            }
        }

        void sendCachedData() {
            getServer().post(this.sendCachedDataRunnable);
        }

        @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.DataEmitter
        public void resume() {
            this.paused = false;
            sendCachedData();
        }

        @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.DataEmitter
        public boolean isPaused() {
            return this.paused;
        }

        @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.DataEmitter
        public void close() {
            if (getServer().getAffinity() != Thread.currentThread()) {
                getServer().post(new Runnable() { // from class: com.koushikdutta.async.http.cache.ResponseCacheMiddleware.CachedBodyEmitter.2
                    @Override // java.lang.Runnable
                    public void run() {
                        CachedBodyEmitter.this.close();
                    }
                });
                return;
            }
            this.pending.recycle();
            StreamUtility.closeQuietly(this.cacheResponse.getBody());
            super.close();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.koushikdutta.async.DataEmitterBase
        public void report(Exception exc) {
            if (this.allowEnd) {
                StreamUtility.closeQuietly(this.cacheResponse.getBody());
                super.report(exc);
            }
        }
    }

    private static final class Entry {
        private final String cipherSuite;
        private final Certificate[] localCertificates;
        private final Certificate[] peerCertificates;
        private final String requestMethod;
        private final RawHeaders responseHeaders;
        private final String uri;
        private final RawHeaders varyHeaders;

        public Entry(InputStream inputStream) throws Throwable {
            StrictLineReader strictLineReader;
            Throwable th;
            try {
                strictLineReader = new StrictLineReader(inputStream, Charsets.US_ASCII);
                try {
                    this.uri = strictLineReader.readLine();
                    this.requestMethod = strictLineReader.readLine();
                    this.varyHeaders = new RawHeaders();
                    int i = strictLineReader.readInt();
                    for (int i2 = 0; i2 < i; i2++) {
                        this.varyHeaders.addLine(strictLineReader.readLine());
                    }
                    RawHeaders rawHeaders = new RawHeaders();
                    this.responseHeaders = rawHeaders;
                    rawHeaders.setStatusLine(strictLineReader.readLine());
                    int i3 = strictLineReader.readInt();
                    for (int i4 = 0; i4 < i3; i4++) {
                        this.responseHeaders.addLine(strictLineReader.readLine());
                    }
                    this.cipherSuite = null;
                    this.peerCertificates = null;
                    this.localCertificates = null;
                    StreamUtility.closeQuietly(strictLineReader, inputStream);
                } catch (Throwable th2) {
                    th = th2;
                    StreamUtility.closeQuietly(strictLineReader, inputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                strictLineReader = null;
                th = th3;
            }
        }

        public Entry(Uri uri, RawHeaders rawHeaders, AsyncHttpRequest asyncHttpRequest, RawHeaders rawHeaders2) {
            this.uri = uri.toString();
            this.varyHeaders = rawHeaders;
            this.requestMethod = asyncHttpRequest.getMethod();
            this.responseHeaders = rawHeaders2;
            this.cipherSuite = null;
            this.peerCertificates = null;
            this.localCertificates = null;
        }

        public void writeTo(EntryEditor entryEditor) throws IOException {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(entryEditor.newOutputStream(0), Charsets.UTF_8));
            bufferedWriter.write(this.uri + '\n');
            bufferedWriter.write(this.requestMethod + '\n');
            bufferedWriter.write(Integer.toString(this.varyHeaders.length()) + '\n');
            for (int i = 0; i < this.varyHeaders.length(); i++) {
                bufferedWriter.write(this.varyHeaders.getFieldName(i) + ": " + this.varyHeaders.getValue(i) + '\n');
            }
            bufferedWriter.write(this.responseHeaders.getStatusLine() + '\n');
            bufferedWriter.write(Integer.toString(this.responseHeaders.length()) + '\n');
            for (int i2 = 0; i2 < this.responseHeaders.length(); i2++) {
                bufferedWriter.write(this.responseHeaders.getFieldName(i2) + ": " + this.responseHeaders.getValue(i2) + '\n');
            }
            if (isHttps()) {
                bufferedWriter.write(10);
                bufferedWriter.write(this.cipherSuite + '\n');
                writeCertArray(bufferedWriter, this.peerCertificates);
                writeCertArray(bufferedWriter, this.localCertificates);
            }
            bufferedWriter.close();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isHttps() {
            return this.uri.startsWith("https://");
        }

        private Certificate[] readCertArray(StrictLineReader strictLineReader) throws IOException {
            int i = strictLineReader.readInt();
            if (i == -1) {
                return null;
            }
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                Certificate[] certificateArr = new Certificate[i];
                for (int i2 = 0; i2 < i; i2++) {
                    certificateArr[i2] = certificateFactory.generateCertificate(new ByteArrayInputStream(Base64.decode(strictLineReader.readLine(), 0)));
                }
                return certificateArr;
            } catch (CertificateException e) {
                throw new IOException(e.getMessage());
            }
        }

        private void writeCertArray(Writer writer, Certificate[] certificateArr) throws IOException {
            if (certificateArr == null) {
                writer.write("-1\n");
                return;
            }
            try {
                writer.write(Integer.toString(certificateArr.length) + '\n');
                for (Certificate certificate : certificateArr) {
                    writer.write(Base64.encodeToString(certificate.getEncoded(), 0) + '\n');
                }
            } catch (CertificateEncodingException e) {
                throw new IOException(e.getMessage());
            }
        }

        public boolean matches(Uri uri, String str, Map<String, List<String>> map) {
            return this.uri.equals(uri.toString()) && this.requestMethod.equals(str) && new ResponseHeaders(uri, this.responseHeaders).varyMatches(this.varyHeaders.toMultimap(), map);
        }
    }

    static class EntryCacheResponse extends CacheResponse {
        private final Entry entry;
        private final FileInputStream snapshot;

        public EntryCacheResponse(Entry entry, FileInputStream fileInputStream) {
            this.entry = entry;
            this.snapshot = fileInputStream;
        }

        @Override // java.net.CacheResponse
        public Map<String, List<String>> getHeaders() {
            return this.entry.responseHeaders.toMultimap();
        }

        @Override // java.net.CacheResponse
        public FileInputStream getBody() {
            return this.snapshot;
        }
    }

    private class CachedSSLSocket extends CachedSocket implements AsyncSSLSocket {
        @Override // com.koushikdutta.async.AsyncSSLSocket
        public X509Certificate[] getPeerCertificates() {
            return null;
        }

        @Override // com.koushikdutta.async.AsyncSSLSocket
        public SSLEngine getSSLEngine() {
            return null;
        }

        public CachedSSLSocket(EntryCacheResponse entryCacheResponse, long j) {
            super(entryCacheResponse, j);
        }
    }

    private class CachedSocket extends CachedBodyEmitter implements AsyncSocket {
        boolean closed;
        CompletedCallback closedCallback;
        boolean open;

        @Override // com.koushikdutta.async.DataSink
        public void end() {
        }

        @Override // com.koushikdutta.async.DataSink
        public WritableCallback getWriteableCallback() {
            return null;
        }

        @Override // com.koushikdutta.async.DataSink
        public void setWriteableCallback(WritableCallback writableCallback) {
        }

        public CachedSocket(EntryCacheResponse entryCacheResponse, long j) {
            super(entryCacheResponse, j);
            this.allowEnd = true;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.koushikdutta.async.http.cache.ResponseCacheMiddleware.CachedBodyEmitter, com.koushikdutta.async.DataEmitterBase
        public void report(Exception exc) {
            super.report(exc);
            if (this.closed) {
                return;
            }
            this.closed = true;
            CompletedCallback completedCallback = this.closedCallback;
            if (completedCallback != null) {
                completedCallback.onCompleted(exc);
            }
        }

        @Override // com.koushikdutta.async.DataSink
        public void write(ByteBufferList byteBufferList) {
            byteBufferList.recycle();
        }

        @Override // com.koushikdutta.async.DataSink
        public boolean isOpen() {
            return this.open;
        }

        @Override // com.koushikdutta.async.http.cache.ResponseCacheMiddleware.CachedBodyEmitter, com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.DataEmitter
        public void close() {
            this.open = false;
        }

        @Override // com.koushikdutta.async.DataSink
        public CompletedCallback getClosedCallback() {
            return this.closedCallback;
        }

        @Override // com.koushikdutta.async.DataSink
        public void setClosedCallback(CompletedCallback completedCallback) {
            this.closedCallback = completedCallback;
        }

        @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.DataEmitter, com.koushikdutta.async.DataSink
        public AsyncServer getServer() {
            return ResponseCacheMiddleware.this.server;
        }
    }

    class EntryEditor {
        boolean done;
        String key;
        FileOutputStream[] outs = new FileOutputStream[2];
        File[] temps;

        public EntryEditor(String str) {
            this.key = str;
            this.temps = ResponseCacheMiddleware.this.cache.getTempFiles(2);
        }

        void commit() {
            StreamUtility.closeQuietly(this.outs);
            if (this.done) {
                return;
            }
            ResponseCacheMiddleware.this.cache.commitTempFiles(this.key, this.temps);
            ResponseCacheMiddleware.access$508(ResponseCacheMiddleware.this);
            this.done = true;
        }

        FileOutputStream newOutputStream(int i) throws IOException {
            FileOutputStream[] fileOutputStreamArr = this.outs;
            if (fileOutputStreamArr[i] == null) {
                fileOutputStreamArr[i] = new FileOutputStream(this.temps[i]);
            }
            return this.outs[i];
        }

        void abort() {
            StreamUtility.closeQuietly(this.outs);
            FileCache.removeFiles(this.temps);
            if (this.done) {
                return;
            }
            ResponseCacheMiddleware.access$608(ResponseCacheMiddleware.this);
            this.done = true;
        }
    }
}
