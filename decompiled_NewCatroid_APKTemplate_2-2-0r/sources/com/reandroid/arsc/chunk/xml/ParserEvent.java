package com.reandroid.arsc.chunk.xml;

/* JADX INFO: loaded from: classes10.dex */
public class ParserEvent {
    public static final int CDSECT = 5;
    public static final int COMMENT = 9;
    public static final int DOCDECL = 10;
    public static final int END_DOCUMENT = 1;
    public static final int END_TAG = 3;
    public static final int ENTITY_REF = 6;
    public static final int IGNORABLE_WHITESPACE = 7;
    public static final int PROCESSING_INSTRUCTION = 8;
    public static final int START_DOCUMENT = 0;
    public static final int START_TAG = 2;
    public static final int TEXT = 4;
    private final String comment;
    private final boolean endComment;
    private final int event;
    private final ResXmlNode xmlNode;

    public ParserEvent(int i, ResXmlNode resXmlNode, String str, boolean z) {
        this.event = i;
        this.xmlNode = resXmlNode;
        this.comment = str;
        this.endComment = z;
    }

    public ParserEvent(int i, ResXmlNode resXmlNode) {
        this(i, resXmlNode, null, false);
    }

    public int getEvent() {
        return this.event;
    }

    public ResXmlNode getXmlNode() {
        return this.xmlNode;
    }

    public String getComment() {
        return this.comment;
    }

    public boolean isEndComment() {
        return this.endComment;
    }
}
