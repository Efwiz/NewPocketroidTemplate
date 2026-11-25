package com.thoughtworks.xstream.io.json;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import io.netty.util.internal.StringUtil;
import java.io.Writer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/json/JsonWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class JsonWriter extends AbstractJsonWriter {
    private int depth;
    protected final Format format;
    private boolean newLineProposed;
    protected final QuickWriter writer;

    @Override // com.thoughtworks.xstream.io.AbstractWriter, com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public HierarchicalStreamWriter underlyingWriter() {
        return this;
    }

    public JsonWriter(Writer writer, char[] cArr, String str) {
        this(writer, 0, new Format(cArr, str.toCharArray(), Format.SPACE_AFTER_LABEL | Format.COMPACT_EMPTY_ELEMENT));
    }

    public JsonWriter(Writer writer, char[] cArr) {
        this(writer, 0, new Format(cArr, new char[]{'\n'}, Format.SPACE_AFTER_LABEL | Format.COMPACT_EMPTY_ELEMENT));
    }

    public JsonWriter(Writer writer, String str, String str2) {
        this(writer, 0, new Format(str.toCharArray(), str2.toCharArray(), Format.SPACE_AFTER_LABEL | Format.COMPACT_EMPTY_ELEMENT));
    }

    public JsonWriter(Writer writer, String str) {
        this(writer, 0, new Format(str.toCharArray(), new char[]{'\n'}, Format.SPACE_AFTER_LABEL | Format.COMPACT_EMPTY_ELEMENT));
    }

    public JsonWriter(Writer writer) {
        this(writer, 0, new Format(new char[]{StringUtil.SPACE, StringUtil.SPACE}, new char[]{'\n'}, Format.SPACE_AFTER_LABEL | Format.COMPACT_EMPTY_ELEMENT));
    }

    public JsonWriter(Writer writer, char[] cArr, String str, int i) {
        this(writer, i, new Format(cArr, str.toCharArray(), Format.SPACE_AFTER_LABEL | Format.COMPACT_EMPTY_ELEMENT));
    }

    public JsonWriter(Writer writer, int i) {
        this(writer, i, new Format());
    }

    public JsonWriter(Writer writer, Format format) {
        this(writer, 0, format);
    }

    public JsonWriter(Writer writer, int i, Format format) {
        this(writer, i, format, 1024);
    }

    public JsonWriter(Writer writer, int i, Format format, int i2) {
        super(i, format.getNameCoder());
        this.writer = new QuickWriter(writer, i2);
        this.format = format;
        this.depth = (i & 1) == 0 ? -1 : 0;
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void flush() {
        this.writer.flush();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void close() {
        this.writer.close();
    }

    @Override // com.thoughtworks.xstream.io.json.AbstractJsonWriter
    protected void startObject() {
        if (this.newLineProposed) {
            writeNewLine();
        }
        this.writer.write('{');
        startNewLine();
    }

    @Override // com.thoughtworks.xstream.io.json.AbstractJsonWriter
    protected void addLabel(String str) {
        if (this.newLineProposed) {
            writeNewLine();
        }
        this.writer.write('\"');
        writeText(str);
        this.writer.write("\":");
        if ((this.format.mode() & Format.SPACE_AFTER_LABEL) != 0) {
            this.writer.write(StringUtil.SPACE);
        }
    }

    @Override // com.thoughtworks.xstream.io.json.AbstractJsonWriter
    protected void addValue(String str, AbstractJsonWriter.Type type) {
        if (this.newLineProposed) {
            writeNewLine();
        }
        if (type == AbstractJsonWriter.Type.STRING) {
            this.writer.write('\"');
        }
        writeText(str);
        if (type == AbstractJsonWriter.Type.STRING) {
            this.writer.write('\"');
        }
    }

    @Override // com.thoughtworks.xstream.io.json.AbstractJsonWriter
    protected void startArray() {
        if (this.newLineProposed) {
            writeNewLine();
        }
        this.writer.write("[");
        startNewLine();
    }

    @Override // com.thoughtworks.xstream.io.json.AbstractJsonWriter
    protected void nextElement() {
        this.writer.write(",");
        writeNewLine();
    }

    @Override // com.thoughtworks.xstream.io.json.AbstractJsonWriter
    protected void endArray() {
        endNewLine();
        this.writer.write("]");
    }

    @Override // com.thoughtworks.xstream.io.json.AbstractJsonWriter
    protected void endObject() {
        endNewLine();
        this.writer.write("}");
    }

    private void startNewLine() {
        int i = this.depth + 1;
        this.depth = i;
        if (i > 0) {
            this.newLineProposed = true;
        }
    }

    private void endNewLine() {
        int i = this.depth;
        this.depth = i - 1;
        if (i > 0) {
            if ((this.format.mode() & Format.COMPACT_EMPTY_ELEMENT) != 0 && this.newLineProposed) {
                this.newLineProposed = false;
            } else {
                writeNewLine();
            }
        }
    }

    private void writeNewLine() {
        int i = this.depth;
        this.writer.write(this.format.getNewLine());
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                this.writer.write(this.format.getLineIndenter());
                i = i2;
            } else {
                this.newLineProposed = false;
                return;
            }
        }
    }

    private void writeText(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\f') {
                this.writer.write("\\f");
            } else if (cCharAt == '\r') {
                this.writer.write("\\r");
            } else if (cCharAt == '\"') {
                this.writer.write("\\\"");
            } else if (cCharAt == '\\') {
                this.writer.write("\\\\");
            } else {
                switch (cCharAt) {
                    case '\b':
                        this.writer.write("\\b");
                        break;
                    case '\t':
                        this.writer.write("\\t");
                        break;
                    case '\n':
                        this.writer.write("\\n");
                        break;
                    default:
                        if (cCharAt > 31) {
                            this.writer.write(cCharAt);
                        } else {
                            this.writer.write("\\u");
                            String string = new StringBuffer("000").append(Integer.toHexString(cCharAt)).toString();
                            this.writer.write(string.substring(string.length() - 4));
                        }
                        break;
                }
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/json/JsonWriter$Format.class
      classes11.dex
     */
    public static class Format {
        public static int COMPACT_EMPTY_ELEMENT = 2;
        public static int SPACE_AFTER_LABEL = 1;
        private char[] lineIndenter;
        private final int mode;
        private final NameCoder nameCoder;
        private char[] newLine;

        public Format() {
            this(new char[]{StringUtil.SPACE, StringUtil.SPACE}, new char[]{'\n'}, SPACE_AFTER_LABEL | COMPACT_EMPTY_ELEMENT);
        }

        public Format(char[] cArr, char[] cArr2, int i) {
            this(cArr, cArr2, i, new NoNameCoder());
        }

        public Format(char[] cArr, char[] cArr2, int i, NameCoder nameCoder) {
            this.lineIndenter = cArr;
            this.newLine = cArr2;
            this.mode = i;
            this.nameCoder = nameCoder;
        }

        public char[] getLineIndenter() {
            return this.lineIndenter;
        }

        public char[] getNewLine() {
            return this.newLine;
        }

        public int mode() {
            return this.mode;
        }

        public NameCoder getNameCoder() {
            return this.nameCoder;
        }
    }
}
