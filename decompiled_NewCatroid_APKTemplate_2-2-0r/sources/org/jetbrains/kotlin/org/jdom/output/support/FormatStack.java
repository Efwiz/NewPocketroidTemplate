package org.jetbrains.kotlin.org.jdom.output.support;

import org.jetbrains.kotlin.org.jdom.output.EscapeStrategy;
import org.jetbrains.kotlin.org.jdom.output.Format;

/* JADX INFO: loaded from: classes4.dex */
public final class FormatStack {
    private final Format.TextMode defaultMode;
    private final String encoding;
    private final EscapeStrategy escapeStrategy;
    private final boolean expandEmptyElements;
    private final String indent;
    private final String lineSeparator;
    private final boolean omitDeclaration;
    private final boolean omitEncoding;
    private final boolean specifiedAttributesOnly;
    private int capacity = 16;
    private int depth = 0;
    private String[] levelIndent = new String[16];
    private String[] levelEOL = new String[16];
    private String[] levelEOLIndent = new String[16];
    private String[] termEOLIndent = new String[16];
    private boolean[] ignoreTrAXEscapingPIs = new boolean[16];
    private Format.TextMode[] mode = new Format.TextMode[16];
    private boolean[] escapeOutput = new boolean[16];

    public FormatStack(Format format) {
        this.indent = format.getIndent();
        this.lineSeparator = format.getLineSeparator();
        this.encoding = format.getEncoding();
        this.omitDeclaration = format.getOmitDeclaration();
        this.omitEncoding = format.getOmitEncoding();
        this.expandEmptyElements = format.getExpandEmptyElements();
        this.escapeStrategy = format.getEscapeStrategy();
        this.defaultMode = format.getTextMode();
        this.specifiedAttributesOnly = format.isSpecifiedAttributesOnly();
        this.mode[this.depth] = format.getTextMode();
        if (this.mode[this.depth] == Format.TextMode.PRESERVE) {
            String[] strArr = this.levelIndent;
            int i = this.depth;
            strArr[i] = null;
            this.levelEOL[i] = null;
            this.levelEOLIndent[i] = null;
            this.termEOLIndent[i] = null;
        } else {
            this.levelIndent[this.depth] = format.getIndent() == null ? null : "";
            this.levelEOL[this.depth] = format.getLineSeparator();
            String[] strArr2 = this.levelEOLIndent;
            int i2 = this.depth;
            String str = this.levelIndent[i2] != null ? this.levelEOL[i2] : null;
            strArr2[i2] = str;
            this.termEOLIndent[i2] = str;
        }
        this.ignoreTrAXEscapingPIs[this.depth] = format.getIgnoreTrAXEscapingPIs();
        this.escapeOutput[this.depth] = true;
    }

    public String getLineSeparator() {
        return this.lineSeparator;
    }

    public boolean isIgnoreTrAXEscapingPIs() {
        return this.ignoreTrAXEscapingPIs[this.depth];
    }

    public void setIgnoreTrAXEscapingPIs(boolean z) {
        this.ignoreTrAXEscapingPIs[this.depth] = z;
    }

    public void setEscapeOutput(boolean z) {
        this.escapeOutput[this.depth] = z;
    }
}
