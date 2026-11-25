package org.jetbrains.kotlin.org.codehaus.stax2.typed;

import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;

/* JADX INFO: loaded from: classes4.dex */
public class TypedXMLStreamException extends XMLStreamException {
    private static final long serialVersionUID = 1;
    protected String mLexical;

    public TypedXMLStreamException(String str, String str2, Location location, IllegalArgumentException illegalArgumentException) {
        super(str2, location, illegalArgumentException);
        this.mLexical = str;
    }
}
