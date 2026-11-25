package com.koushikdutta.async.parser;

import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.TransformFuture;
import java.lang.reflect.Type;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes10.dex */
public class JSONArrayParser implements AsyncParser<JSONArray> {
    @Override // com.koushikdutta.async.parser.AsyncParser
    public Future<JSONArray> parse(DataEmitter dataEmitter) {
        return (Future) new StringParser().parse(dataEmitter).then(new TransformFuture<JSONArray, String>() { // from class: com.koushikdutta.async.parser.JSONArrayParser.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.koushikdutta.async.future.TransformFuture
            public void transform(String str) throws Exception {
                setComplete(new JSONArray(str));
            }
        });
    }

    @Override // com.koushikdutta.async.parser.AsyncParser
    public void write(DataSink dataSink, JSONArray jSONArray, CompletedCallback completedCallback) {
        new StringParser().write(dataSink, jSONArray.toString(), completedCallback);
    }

    @Override // com.koushikdutta.async.parser.AsyncParser
    public Type getType() {
        return JSONArray.class;
    }
}
