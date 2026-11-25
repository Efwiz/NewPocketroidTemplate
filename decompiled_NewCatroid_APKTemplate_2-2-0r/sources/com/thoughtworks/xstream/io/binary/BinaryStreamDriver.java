package com.thoughtworks.xstream.io.binary;

import com.thoughtworks.xstream.io.AbstractDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/binary/BinaryStreamDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class BinaryStreamDriver extends AbstractDriver {
    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(Reader reader) {
        throw new UnsupportedOperationException("The BinaryDriver cannot use character-oriented input streams.");
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(InputStream inputStream) {
        return new BinaryStreamReader(inputStream);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(Writer writer) {
        throw new UnsupportedOperationException("The BinaryDriver cannot use character-oriented output streams.");
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamWriter createWriter(OutputStream outputStream) {
        return new BinaryStreamWriter(outputStream);
    }
}
