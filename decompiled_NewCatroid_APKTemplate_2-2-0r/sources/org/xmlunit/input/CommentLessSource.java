package org.xmlunit.input;

import java.io.StringReader;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import org.xmlunit.transform.Transformation;

/* JADX INFO: loaded from: classes3.dex */
public final class CommentLessSource extends DOMSource {
    private static final String DEFAULT_VERSION = "2.0";
    public static final String STYLE = getStylesheetContent(DEFAULT_VERSION);
    private static final String STYLE_TEMPLATE = "<stylesheet version=\"%1$s\" xmlns=\"http://www.w3.org/1999/XSL/Transform\"><template match=\"node()[not(self::comment())]|@*\"><copy><apply-templates select=\"node()[not(self::comment())]|@*\"/></copy></template></stylesheet>";

    public CommentLessSource(Source source) {
        this(source, DEFAULT_VERSION);
    }

    public CommentLessSource(Source source, String str) {
        if (source == null) {
            throw new IllegalArgumentException("source must not be null");
        }
        if (str == null) {
            throw new IllegalArgumentException("xsltVersion must not be null");
        }
        Transformation transformation = new Transformation(source);
        transformation.setStylesheet(getStylesheet(str));
        setNode(transformation.transformToDocument());
    }

    private static Source getStylesheet(String str) {
        return new StreamSource(new StringReader(getStylesheetContentCached(str)));
    }

    private static String getStylesheetContentCached(String str) {
        return DEFAULT_VERSION.equals(str) ? STYLE : getStylesheetContent(str);
    }

    private static String getStylesheetContent(String str) {
        return String.format(STYLE_TEMPLATE, str);
    }
}
