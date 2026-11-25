package org.antlr.stringtemplate;

import java.io.IOException;

/* JADX INFO: loaded from: classes12.dex */
public interface StringTemplateWriter {
    public static final int NO_WRAP = -1;

    void popAnchorPoint();

    String popIndentation();

    void pushAnchorPoint();

    void pushIndentation(String str);

    void setLineWidth(int i);

    int write(String str) throws IOException;

    int write(String str, String str2) throws IOException;

    int writeSeparator(String str) throws IOException;

    int writeWrapSeparator(String str) throws IOException;
}
