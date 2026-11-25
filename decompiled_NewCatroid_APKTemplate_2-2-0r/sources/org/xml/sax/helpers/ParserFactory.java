package org.xml.sax.helpers;

import org.xml.sax.Parser;

/* JADX INFO: loaded from: classes3.dex */
public class ParserFactory {
    private ParserFactory() {
    }

    public static Parser makeParser() throws IllegalAccessException, InstantiationException, ClassNotFoundException, ClassCastException, NullPointerException {
        String systemProperty = SecuritySupport.getSystemProperty("org.xml.sax.parser");
        if (systemProperty != null) {
            return makeParser(systemProperty);
        }
        throw new NullPointerException("No value for sax.parser property");
    }

    public static Parser makeParser(String str) throws IllegalAccessException, InstantiationException, ClassNotFoundException, ClassCastException {
        return (Parser) NewInstance.newInstance(NewInstance.getClassLoader(), str);
    }
}
