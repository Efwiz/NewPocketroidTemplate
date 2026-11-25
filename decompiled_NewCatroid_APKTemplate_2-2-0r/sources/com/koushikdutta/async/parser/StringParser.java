package com.koushikdutta.async.parser;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.TransformFuture;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes10.dex */
public class StringParser implements AsyncParser<String> {
    Charset forcedCharset;

    public StringParser() {
    }

    public StringParser(Charset charset) {
        this.forcedCharset = charset;
    }

    @Override // com.koushikdutta.async.parser.AsyncParser
    public Future<String> parse(DataEmitter dataEmitter) {
        final String strCharset = dataEmitter.charset();
        return (Future) new ByteBufferListParser().parse(dataEmitter).then(new TransformFuture<String, ByteBufferList>() { // from class: com.koushikdutta.async.parser.StringParser.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.koushikdutta.async.future.TransformFuture
            public void transform(ByteBufferList byteBufferList) throws Exception {
                String str;
                Charset charsetForName = StringParser.this.forcedCharset;
                if (charsetForName == null && (str = strCharset) != null) {
                    charsetForName = Charset.forName(str);
                }
                setComplete(byteBufferList.readString(charsetForName));
            }
        });
    }

    @Override // com.koushikdutta.async.parser.AsyncParser
    public void write(DataSink dataSink, String str, CompletedCallback completedCallback) {
        new ByteBufferListParser().write(dataSink, new ByteBufferList(str.getBytes()), completedCallback);
    }

    @Override // com.koushikdutta.async.parser.AsyncParser
    public Type getType() {
        return String.class;
    }
}
