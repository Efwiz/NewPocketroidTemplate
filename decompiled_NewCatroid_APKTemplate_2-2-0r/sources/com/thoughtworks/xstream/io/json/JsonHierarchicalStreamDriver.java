package com.thoughtworks.xstream.io.json;

import com.thoughtworks.xstream.io.AbstractDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.naming.NameCoder;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/json/JsonHierarchicalStreamDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class JsonHierarchicalStreamDriver extends AbstractDriver {
    public JsonHierarchicalStreamDriver() {
    }

    public JsonHierarchicalStreamDriver(NameCoder nameCoder) {
        super(nameCoder);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(Reader reader) {
        throw new UnsupportedOperationException("The JsonHierarchicalStreamDriver can only write JSON");
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(InputStream inputStream) {
        throw new UnsupportedOperationException("The JsonHierarchicalStreamDriver can only write JSON");
    }

    @Override // com.thoughtworks.xstream.io.AbstractDriver, com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(URL url) {
        throw new UnsupportedOperationException("The JsonHierarchicalStreamDriver can only write JSON");
    }

    @Override // com.thoughtworks.xstream.io.AbstractDriver, com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(File file) {
        throw new UnsupportedOperationException("The JsonHierarchicalStreamDriver can only write JSON");
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(Writer writer) {
        return new JsonWriter(writer);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(OutputStream outputStream) {
        try {
            return createWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new StreamException(e);
        }
    }
}
