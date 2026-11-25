package com.koushikdutta.async.parser;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.TransformFuture;
import com.koushikdutta.async.http.body.DocumentBody;
import com.koushikdutta.async.stream.ByteBufferListInputStream;
import java.lang.reflect.Type;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/* JADX INFO: loaded from: classes10.dex */
public class DocumentParser implements AsyncParser<Document> {
    @Override // com.koushikdutta.async.parser.AsyncParser
    public Future<Document> parse(DataEmitter dataEmitter) {
        return (Future) new ByteBufferListParser().parse(dataEmitter).then(new TransformFuture<Document, ByteBufferList>() { // from class: com.koushikdutta.async.parser.DocumentParser.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.koushikdutta.async.future.TransformFuture
            public void transform(ByteBufferList byteBufferList) throws Exception {
                setComplete(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteBufferListInputStream(byteBufferList)));
            }
        });
    }

    @Override // com.koushikdutta.async.parser.AsyncParser
    public void write(DataSink dataSink, Document document, CompletedCallback completedCallback) {
        new DocumentBody(document).write(null, dataSink, completedCallback);
    }

    @Override // com.koushikdutta.async.parser.AsyncParser
    public Type getType() {
        return Document.class;
    }
}
