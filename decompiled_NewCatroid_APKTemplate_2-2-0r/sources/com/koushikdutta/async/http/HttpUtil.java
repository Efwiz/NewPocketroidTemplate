package com.koushikdutta.async.http;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.FilteredDataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import com.koushikdutta.async.http.body.JSONObjectBody;
import com.koushikdutta.async.http.body.MultipartFormDataBody;
import com.koushikdutta.async.http.body.StringBody;
import com.koushikdutta.async.http.body.UrlEncodedFormBody;
import com.koushikdutta.async.http.filter.ChunkedInputFilter;
import com.koushikdutta.async.http.filter.ContentLengthFilter;
import com.koushikdutta.async.http.filter.GZIPInputFilter;
import com.koushikdutta.async.http.filter.InflaterInputFilter;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.eclipse.jgit.util.HttpSupport;

/* JADX INFO: loaded from: classes10.dex */
public class HttpUtil {
    public static AsyncHttpRequestBody getBody(DataEmitter dataEmitter, CompletedCallback completedCallback, Headers headers) {
        String str = headers.get("Content-Type");
        if (str == null) {
            return null;
        }
        String[] strArrSplit = str.split(";");
        for (int i = 0; i < strArrSplit.length; i++) {
            strArrSplit[i] = strArrSplit[i].trim();
        }
        for (String str2 : strArrSplit) {
            if ("application/x-www-form-urlencoded".equals(str2)) {
                return new UrlEncodedFormBody();
            }
            if ("application/json".equals(str2)) {
                return new JSONObjectBody();
            }
            if ("text/plain".equals(str2)) {
                return new StringBody();
            }
            if ("multipart/form-data".equals(str2)) {
                return new MultipartFormDataBody(strArrSplit);
            }
        }
        return null;
    }

    static class EndEmitter extends FilteredDataEmitter {
        private EndEmitter() {
        }

        public static EndEmitter create(AsyncServer asyncServer, final Exception exc) {
            EndEmitter endEmitter = new EndEmitter();
            asyncServer.post(new Runnable() { // from class: com.koushikdutta.async.http.HttpUtil.EndEmitter.1
                @Override // java.lang.Runnable
                public void run() {
                    EndEmitter.this.report(exc);
                }
            });
            return endEmitter;
        }
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0089  */
    /* JADX WARN: Code duplicated, block: B:31:0x0093  */
    /* JADX WARN: Code duplicated, block: B:33:0x009f  */
    /* JADX WARN: Code duplicated, block: B:37:? A[RETURN, SYNTHETIC] */
    public static DataEmitter getBodyDecoder(DataEmitter dataEmitter, Protocol protocol, Headers headers, boolean z) {
        long j;
        FilteredDataEmitter chunkedInputFilter;
        FilteredDataEmitter inflaterInputFilter;
        try {
            j = Long.parseLong(headers.get("Content-Length"));
        } catch (Exception unused) {
            j = -1;
        }
        if (-1 == j) {
            if ("chunked".equalsIgnoreCase(headers.get("Transfer-Encoding"))) {
                chunkedInputFilter = new ChunkedInputFilter();
                chunkedInputFilter.setDataEmitter(dataEmitter);
            } else if ((z || protocol == Protocol.HTTP_1_1) && !"close".equalsIgnoreCase(headers.get("Connection"))) {
                EndEmitter endEmitterCreate = EndEmitter.create(dataEmitter.getServer(), null);
                endEmitterCreate.setDataEmitter(dataEmitter);
                return endEmitterCreate;
            }
            if (HttpSupport.ENCODING_GZIP.equals(headers.get("Content-Encoding"))) {
                inflaterInputFilter = new GZIPInputFilter();
                inflaterInputFilter.setDataEmitter(dataEmitter);
            } else {
                if (CompressorStreamFactory.DEFLATE.equals(headers.get("Content-Encoding"))) {
                    return dataEmitter;
                }
                inflaterInputFilter = new InflaterInputFilter();
                inflaterInputFilter.setDataEmitter(dataEmitter);
            }
            return inflaterInputFilter;
        }
        if (j < 0) {
            EndEmitter endEmitterCreate2 = EndEmitter.create(dataEmitter.getServer(), new BodyDecoderException("not using chunked encoding, and no content-length found."));
            endEmitterCreate2.setDataEmitter(dataEmitter);
            return endEmitterCreate2;
        }
        if (j == 0) {
            EndEmitter endEmitterCreate3 = EndEmitter.create(dataEmitter.getServer(), null);
            endEmitterCreate3.setDataEmitter(dataEmitter);
            return endEmitterCreate3;
        }
        chunkedInputFilter = new ContentLengthFilter(j);
        chunkedInputFilter.setDataEmitter(dataEmitter);
        dataEmitter = chunkedInputFilter;
        if (HttpSupport.ENCODING_GZIP.equals(headers.get("Content-Encoding"))) {
            inflaterInputFilter = new GZIPInputFilter();
            inflaterInputFilter.setDataEmitter(dataEmitter);
        } else {
            if (CompressorStreamFactory.DEFLATE.equals(headers.get("Content-Encoding"))) {
                return dataEmitter;
            }
            inflaterInputFilter = new InflaterInputFilter();
            inflaterInputFilter.setDataEmitter(dataEmitter);
        }
        return inflaterInputFilter;
    }

    public static boolean isKeepAlive(Protocol protocol, Headers headers) {
        String str = headers.get("Connection");
        if (str == null) {
            return protocol == Protocol.HTTP_1_1;
        }
        return "keep-alive".equalsIgnoreCase(str);
    }

    public static boolean isKeepAlive(String str, Headers headers) {
        String str2 = headers.get("Connection");
        if (str2 == null) {
            return Protocol.get(str) == Protocol.HTTP_1_1;
        }
        return "keep-alive".equalsIgnoreCase(str2);
    }

    public static int contentLength(Headers headers) {
        String str = headers.get("Content-Length");
        if (str == null) {
            return -1;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }
}
