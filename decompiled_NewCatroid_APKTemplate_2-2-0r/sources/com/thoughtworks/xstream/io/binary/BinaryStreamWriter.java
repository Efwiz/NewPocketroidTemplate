package com.thoughtworks.xstream.io.binary;

import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.StreamException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/binary/BinaryStreamWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class BinaryStreamWriter implements ExtendedHierarchicalStreamWriter {
    private final DataOutputStream out;
    private final IdRegistry idRegistry = new IdRegistry();
    private final Token.Formatter tokenFormatter = new Token.Formatter();

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public HierarchicalStreamWriter underlyingWriter() {
        return this;
    }

    public BinaryStreamWriter(OutputStream outputStream) {
        this.out = new DataOutputStream(outputStream);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void startNode(String str) {
        write(new Token.StartNode(this.idRegistry.getId(str)));
    }

    @Override // com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriter
    public void startNode(String str, Class cls) {
        startNode(str);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void addAttribute(String str, String str2) {
        write(new Token.Attribute(this.idRegistry.getId(str), str2));
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void setValue(String str) {
        write(new Token.Value(str));
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void endNode() {
        write(new Token.EndNode());
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void flush() {
        try {
            this.out.flush();
        } catch (IOException e) {
            throw new StreamException(e);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void close() {
        try {
            this.out.close();
        } catch (IOException e) {
            throw new StreamException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void write(Token token) {
        try {
            this.tokenFormatter.write(this.out, token);
        } catch (IOException e) {
            throw new StreamException(e);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/binary/BinaryStreamWriter$IdRegistry.class
      classes11.dex
     */
    private class IdRegistry {
        private Map ids;
        private long nextId;

        private IdRegistry() {
            this.nextId = 0L;
            this.ids = new HashMap();
        }

        public long getId(String str) {
            Long l = (Long) this.ids.get(str);
            if (l == null) {
                long j = this.nextId + 1;
                this.nextId = j;
                l = new Long(j);
                this.ids.put(str, l);
                BinaryStreamWriter.this.write(new Token.MapIdToValue(l.longValue(), str));
            }
            return l.longValue();
        }
    }
}
