package com.koushikdutta.async.http.body;

import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.parser.DocumentParser;
import com.koushikdutta.async.util.Charsets;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

/* JADX INFO: loaded from: classes10.dex */
public class DocumentBody implements AsyncHttpRequestBody<Document> {
    public static final String CONTENT_TYPE = "application/xml";
    ByteArrayOutputStream bout;
    Document document;

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public boolean readFullyOnRequest() {
        return true;
    }

    public DocumentBody() {
        this(null);
    }

    public DocumentBody(Document document) {
        this.document = document;
    }

    private void prepare() {
        if (this.bout != null) {
            return;
        }
        try {
            DOMSource dOMSource = new DOMSource(this.document);
            Transformer transformerNewTransformer = TransformerFactory.newInstance().newTransformer();
            this.bout = new ByteArrayOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.bout, Charsets.UTF_8);
            transformerNewTransformer.transform(dOMSource, new StreamResult(outputStreamWriter));
            outputStreamWriter.flush();
        } catch (Exception unused) {
        }
    }

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public void write(AsyncHttpRequest asyncHttpRequest, DataSink dataSink, CompletedCallback completedCallback) {
        prepare();
        Util.writeAll(dataSink, this.bout.toByteArray(), completedCallback);
    }

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public void parse(DataEmitter dataEmitter, final CompletedCallback completedCallback) {
        new DocumentParser().parse(dataEmitter).setCallback(new FutureCallback<Document>() { // from class: com.koushikdutta.async.http.body.DocumentBody.1
            @Override // com.koushikdutta.async.future.FutureCallback
            public void onCompleted(Exception exc, Document document) {
                DocumentBody.this.document = document;
                completedCallback.onCompleted(exc);
            }
        });
    }

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public String getContentType() {
        return CONTENT_TYPE;
    }

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public int length() {
        prepare();
        return this.bout.size();
    }

    @Override // com.koushikdutta.async.http.body.AsyncHttpRequestBody
    public Document get() {
        return this.document;
    }
}
