package com.badlogic.gdx.utils;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;
import org.eclipse.jgit.transport.RefSpec;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/JsonWriter.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class JsonWriter extends Writer {
    private JsonObject current;
    private boolean named;
    final Writer writer;
    private final Array<JsonObject> stack = new Array<>();
    private OutputType outputType = OutputType.json;
    private boolean quoteLongValues = false;

    public JsonWriter(Writer writer) {
        this.writer = writer;
    }

    public Writer getWriter() {
        return this.writer;
    }

    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }

    public void setQuoteLongValues(boolean z) {
        this.quoteLongValues = z;
    }

    public JsonWriter name(String str) throws IOException {
        JsonObject jsonObject = this.current;
        if (jsonObject == null || jsonObject.array) {
            throw new IllegalStateException("Current item must be an object.");
        }
        if (!this.current.needsComma) {
            this.current.needsComma = true;
        } else {
            this.writer.write(44);
        }
        this.writer.write(this.outputType.quoteName(str));
        this.writer.write(58);
        this.named = true;
        return this;
    }

    public JsonWriter object() throws IOException {
        requireCommaOrName();
        Array<JsonObject> array = this.stack;
        JsonObject jsonObject = new JsonObject(false);
        this.current = jsonObject;
        array.add(jsonObject);
        return this;
    }

    public JsonWriter array() throws IOException {
        requireCommaOrName();
        Array<JsonObject> array = this.stack;
        JsonObject jsonObject = new JsonObject(true);
        this.current = jsonObject;
        array.add(jsonObject);
        return this;
    }

    public JsonWriter value(Object obj) throws IOException {
        if (this.quoteLongValues && ((obj instanceof Long) || (obj instanceof Double) || (obj instanceof BigDecimal) || (obj instanceof BigInteger))) {
            obj = obj.toString();
        } else if (obj instanceof Number) {
            Number number = (Number) obj;
            long jLongValue = number.longValue();
            if (number.doubleValue() == jLongValue) {
                obj = Long.valueOf(jLongValue);
            }
        }
        requireCommaOrName();
        this.writer.write(this.outputType.quoteValue(obj));
        return this;
    }

    public JsonWriter json(String str) throws IOException {
        requireCommaOrName();
        this.writer.write(str);
        return this;
    }

    private void requireCommaOrName() throws IOException {
        JsonObject jsonObject = this.current;
        if (jsonObject == null) {
            return;
        }
        if (jsonObject.array) {
            if (!this.current.needsComma) {
                this.current.needsComma = true;
                return;
            } else {
                this.writer.write(44);
                return;
            }
        }
        if (!this.named) {
            throw new IllegalStateException("Name must be set.");
        }
        this.named = false;
    }

    public JsonWriter object(String str) throws IOException {
        return name(str).object();
    }

    public JsonWriter array(String str) throws IOException {
        return name(str).array();
    }

    public JsonWriter set(String str, Object obj) throws IOException {
        return name(str).value(obj);
    }

    public JsonWriter json(String str, String str2) throws IOException {
        return name(str).json(str2);
    }

    public JsonWriter pop() throws IOException {
        if (this.named) {
            throw new IllegalStateException("Expected an object, array, or value since a name was set.");
        }
        this.stack.pop().close();
        this.current = this.stack.size == 0 ? null : this.stack.peek();
        return this;
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) throws IOException {
        this.writer.write(cArr, i, i2);
    }

    @Override // java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        this.writer.flush();
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        while (this.stack.size > 0) {
            pop();
        }
        this.writer.close();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/JsonWriter$JsonObject.class
      classes8.dex
     */
    private class JsonObject {
        final boolean array;
        boolean needsComma;

        JsonObject(boolean z) throws IOException {
            this.array = z;
            JsonWriter.this.writer.write(z ? 91 : 123);
        }

        void close() throws IOException {
            JsonWriter.this.writer.write(this.array ? 93 : 125);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/JsonWriter$OutputType.class
      classes8.dex
     */
    public enum OutputType {
        json,
        javascript,
        minimal;

        private static Pattern javascriptPattern = Pattern.compile("^[a-zA-Z_$][a-zA-Z_$0-9]*$");
        private static Pattern minimalNamePattern = Pattern.compile("^[^\":,}/ ][^:]*$");
        private static Pattern minimalValuePattern = Pattern.compile("^[^\":,{\\[\\]/ ][^}\\],]*$");

        public String quoteValue(Object obj) {
            int length;
            if (obj == null) {
                return "null";
            }
            String string = obj.toString();
            if ((obj instanceof Number) || (obj instanceof Boolean)) {
                return string;
            }
            StringBuilder stringBuilder = new StringBuilder(string);
            stringBuilder.replace('\\', "\\\\").replace('\r', "\\r").replace('\n', "\\n").replace('\t', "\\t");
            if (this == minimal && !string.equals("true") && !string.equals("false") && !string.equals("null") && !string.contains("//") && !string.contains(RefSpec.WILDCARD_SUFFIX) && (length = stringBuilder.length()) > 0 && stringBuilder.charAt(length - 1) != ' ' && minimalValuePattern.matcher(stringBuilder).matches()) {
                return stringBuilder.toString();
            }
            return "\"" + stringBuilder.replace('\"', "\\\"").toString() + '\"';
        }

        public String quoteName(String str) {
            StringBuilder stringBuilder = new StringBuilder(str);
            stringBuilder.replace('\\', "\\\\").replace('\r', "\\r").replace('\n', "\\n").replace('\t', "\\t");
            int i = AnonymousClass1.$SwitchMap$com$badlogic$gdx$utils$JsonWriter$OutputType[ordinal()];
            if (i == 1) {
                if (!str.contains("//") && !str.contains(RefSpec.WILDCARD_SUFFIX) && minimalNamePattern.matcher(stringBuilder).matches()) {
                    return stringBuilder.toString();
                }
            } else {
                if (i == 2) {
                }
                return "\"" + stringBuilder.replace('\"', "\\\"").toString() + '\"';
            }
            if (javascriptPattern.matcher(stringBuilder).matches()) {
                return stringBuilder.toString();
            }
            return "\"" + stringBuilder.replace('\"', "\\\"").toString() + '\"';
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/JsonWriter$1.class
      classes8.dex
     */
    /* JADX INFO: renamed from: com.badlogic.gdx.utils.JsonWriter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$badlogic$gdx$utils$JsonWriter$OutputType;

        static {
            int[] iArr = new int[OutputType.values().length];
            $SwitchMap$com$badlogic$gdx$utils$JsonWriter$OutputType = iArr;
            try {
                iArr[OutputType.minimal.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$badlogic$gdx$utils$JsonWriter$OutputType[OutputType.javascript.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
