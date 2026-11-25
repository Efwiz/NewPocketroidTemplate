package com.thoughtworks.xstream.io.xml;

import com.thoughtworks.xstream.core.util.FastStack;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.naming.NameCoder;
import io.netty.util.internal.StringUtil;
import java.io.Writer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/xml/PrettyPrintWriter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class PrettyPrintWriter extends AbstractXmlWriter {
    public static int XML_1_0 = 0;
    public static int XML_1_1 = 1;
    public static int XML_QUIRKS = -1;
    protected int depth;
    private final FastStack elementStack;
    private final char[] lineIndenter;
    private final int mode;
    private String newLine;
    private boolean readyForNewLine;
    private boolean tagInProgress;
    private boolean tagIsEmpty;
    private final QuickWriter writer;
    private static final char[] NULL = "&#x0;".toCharArray();
    private static final char[] AMP = "&amp;".toCharArray();
    private static final char[] LT = "&lt;".toCharArray();
    private static final char[] GT = "&gt;".toCharArray();
    private static final char[] CR = "&#xd;".toCharArray();
    private static final char[] QUOT = "&quot;".toCharArray();
    private static final char[] APOS = "&apos;".toCharArray();
    private static final char[] CLOSE = "</".toCharArray();

    private PrettyPrintWriter(Writer writer, int i, char[] cArr, NameCoder nameCoder, String str) {
        super(nameCoder);
        this.elementStack = new FastStack(16);
        this.writer = new QuickWriter(writer);
        this.lineIndenter = cArr;
        this.newLine = str;
        this.mode = i;
        if (i < XML_QUIRKS || i > XML_1_1) {
            throw new IllegalArgumentException("Not a valid XML mode");
        }
    }

    public PrettyPrintWriter(Writer writer, char[] cArr, String str, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(writer, XML_QUIRKS, cArr, xmlFriendlyReplacer, str);
    }

    public PrettyPrintWriter(Writer writer, int i, char[] cArr, NameCoder nameCoder) {
        this(writer, i, cArr, nameCoder, "\n");
    }

    public PrettyPrintWriter(Writer writer, int i, char[] cArr, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(writer, i, cArr, xmlFriendlyReplacer, "\n");
    }

    public PrettyPrintWriter(Writer writer, char[] cArr, String str) {
        this(writer, cArr, str, new XmlFriendlyReplacer());
    }

    public PrettyPrintWriter(Writer writer, int i, char[] cArr) {
        this(writer, i, cArr, new XmlFriendlyNameCoder());
    }

    public PrettyPrintWriter(Writer writer, char[] cArr) {
        this(writer, XML_QUIRKS, cArr);
    }

    public PrettyPrintWriter(Writer writer, String str, String str2) {
        this(writer, str.toCharArray(), str2);
    }

    public PrettyPrintWriter(Writer writer, int i, String str) {
        this(writer, i, str.toCharArray());
    }

    public PrettyPrintWriter(Writer writer, String str) {
        this(writer, str.toCharArray());
    }

    public PrettyPrintWriter(Writer writer, int i, NameCoder nameCoder) {
        this(writer, i, new char[]{StringUtil.SPACE, StringUtil.SPACE}, nameCoder);
    }

    public PrettyPrintWriter(Writer writer, int i, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(writer, i, new char[]{StringUtil.SPACE, StringUtil.SPACE}, xmlFriendlyReplacer);
    }

    public PrettyPrintWriter(Writer writer, NameCoder nameCoder) {
        this(writer, XML_QUIRKS, new char[]{StringUtil.SPACE, StringUtil.SPACE}, nameCoder, "\n");
    }

    public PrettyPrintWriter(Writer writer, XmlFriendlyReplacer xmlFriendlyReplacer) {
        this(writer, new char[]{StringUtil.SPACE, StringUtil.SPACE}, "\n", xmlFriendlyReplacer);
    }

    public PrettyPrintWriter(Writer writer, int i) {
        this(writer, i, new char[]{StringUtil.SPACE, StringUtil.SPACE});
    }

    public PrettyPrintWriter(Writer writer) {
        this(writer, new char[]{StringUtil.SPACE, StringUtil.SPACE});
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void startNode(String str) {
        String strEncodeNode = encodeNode(str);
        this.tagIsEmpty = false;
        finishTag();
        this.writer.write('<');
        this.writer.write(strEncodeNode);
        this.elementStack.push(strEncodeNode);
        this.tagInProgress = true;
        this.depth++;
        this.readyForNewLine = true;
        this.tagIsEmpty = true;
    }

    @Override // com.thoughtworks.xstream.io.AbstractWriter, com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriter
    public void startNode(String str, Class cls) {
        startNode(str);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void setValue(String str) {
        this.readyForNewLine = false;
        this.tagIsEmpty = false;
        finishTag();
        writeText(this.writer, str);
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void addAttribute(String str, String str2) {
        this.writer.write(StringUtil.SPACE);
        this.writer.write(encodeAttribute(str));
        this.writer.write('=');
        this.writer.write('\"');
        writeAttributeValue(this.writer, str2);
        this.writer.write('\"');
    }

    protected void writeAttributeValue(QuickWriter quickWriter, String str) {
        writeText(str, true);
    }

    protected void writeText(QuickWriter quickWriter, String str) {
        writeText(str, false);
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0049  */
    /* JADX WARN: Code duplicated, block: B:41:0x008c  */
    /* JADX WARN: Code duplicated, block: B:43:0x0092 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:59:0x00ca  */
    private void writeText(String str, boolean z) {
        int i;
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt != 0) {
                if (cCharAt == '\r') {
                    this.writer.write(CR);
                } else if (cCharAt == '\"') {
                    this.writer.write(QUOT);
                } else if (cCharAt == '<') {
                    this.writer.write(LT);
                } else if (cCharAt == '>') {
                    this.writer.write(GT);
                } else if (cCharAt == '\t' || cCharAt == '\n') {
                    if (!z) {
                        this.writer.write(cCharAt);
                    } else if (!Character.isDefined(cCharAt) && !Character.isISOControl(cCharAt)) {
                        if (this.mode != XML_QUIRKS && cCharAt > 55295 && cCharAt < 57344) {
                            throw new StreamException(new StringBuffer("Invalid character 0x").append(Integer.toHexString(cCharAt)).append(" in XML stream").toString());
                        }
                        this.writer.write(cCharAt);
                    } else {
                        i = this.mode;
                        if (i != XML_1_0 && (cCharAt < '\t' || cCharAt == 11 || cCharAt == '\f' || cCharAt == 14 || (cCharAt >= 15 && cCharAt <= 31))) {
                            throw new StreamException(new StringBuffer("Invalid character 0x").append(Integer.toHexString(cCharAt)).append(" in XML 1.0 stream").toString());
                        }
                        if (i == XML_QUIRKS && (cCharAt == 65534 || cCharAt == 65535)) {
                            throw new StreamException(new StringBuffer("Invalid character 0x").append(Integer.toHexString(cCharAt)).append(" in XML stream").toString());
                        }
                        this.writer.write("&#x");
                        this.writer.write(Integer.toHexString(cCharAt));
                        this.writer.write(';');
                    }
                } else if (cCharAt == '&') {
                    this.writer.write(AMP);
                } else if (cCharAt == '\'') {
                    this.writer.write(APOS);
                } else if (!Character.isDefined(cCharAt)) {
                    i = this.mode;
                    if (i != XML_1_0) {
                    }
                    if (i == XML_QUIRKS) {
                    }
                    this.writer.write("&#x");
                    this.writer.write(Integer.toHexString(cCharAt));
                    this.writer.write(';');
                } else {
                    i = this.mode;
                    if (i != XML_1_0) {
                    }
                    if (i == XML_QUIRKS) {
                    }
                    this.writer.write("&#x");
                    this.writer.write(Integer.toHexString(cCharAt));
                    this.writer.write(';');
                }
            } else if (this.mode == XML_QUIRKS) {
                this.writer.write(NULL);
            } else {
                throw new StreamException("Invalid character 0x0 in XML stream");
            }
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void endNode() {
        this.depth--;
        if (this.tagIsEmpty) {
            this.writer.write('/');
            this.readyForNewLine = false;
            finishTag();
            this.elementStack.popSilently();
        } else {
            finishTag();
            this.writer.write(CLOSE);
            this.writer.write((String) this.elementStack.pop());
            this.writer.write('>');
        }
        this.readyForNewLine = true;
        if (this.depth == 0) {
            this.writer.flush();
        }
    }

    private void finishTag() {
        if (this.tagInProgress) {
            this.writer.write('>');
        }
        this.tagInProgress = false;
        if (this.readyForNewLine) {
            endOfLine();
        }
        this.readyForNewLine = false;
        this.tagIsEmpty = false;
    }

    protected void endOfLine() {
        this.writer.write(getNewLine());
        for (int i = 0; i < this.depth; i++) {
            this.writer.write(this.lineIndenter);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void flush() {
        this.writer.flush();
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamWriter
    public void close() {
        this.writer.close();
    }

    protected String getNewLine() {
        return this.newLine;
    }
}
