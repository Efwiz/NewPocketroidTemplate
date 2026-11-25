package com.thoughtworks.xstream.persistence;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/persistence/FilePersistenceStrategy.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class FilePersistenceStrategy extends AbstractFilePersistenceStrategy {
    private final String illegalChars;

    public FilePersistenceStrategy(File file) {
        this(file, new XStream(new DomDriver()));
    }

    public FilePersistenceStrategy(File file, XStream xStream) {
        this(file, xStream, "utf-8", "<>?:/\\\"|*%");
    }

    public FilePersistenceStrategy(File file, XStream xStream, String str, String str2) {
        super(file, xStream, str);
        this.illegalChars = str2;
    }

    @Override // com.thoughtworks.xstream.persistence.AbstractFilePersistenceStrategy
    protected boolean isValid(File file, String str) {
        return super.isValid(file, str) && str.indexOf(64) > 0;
    }

    @Override // com.thoughtworks.xstream.persistence.AbstractFilePersistenceStrategy
    protected Object extractKey(String str) {
        String strUnescape = unescape(str.substring(0, str.length() - 4));
        if ("null@null".equals(strUnescape)) {
            return null;
        }
        int iIndexOf = strUnescape.indexOf(64);
        if (iIndexOf < 0) {
            ConversionException conversionException = new ConversionException("No valid key");
            conversionException.add(ASTExpr.DEFAULT_MAP_KEY_NAME, strUnescape);
            throw conversionException;
        }
        Class clsRealClass = getMapper().realClass(strUnescape.substring(0, iIndexOf));
        Converter converterLookupConverterForType = getConverterLookup().lookupConverterForType(clsRealClass);
        if (converterLookupConverterForType instanceof SingleValueConverter) {
            return ((SingleValueConverter) converterLookupConverterForType).fromString(strUnescape.substring(iIndexOf + 1));
        }
        ConversionException conversionException2 = new ConversionException("No SingleValueConverter available for key type");
        conversionException2.add("key-type", clsRealClass.getName());
        throw conversionException2;
    }

    protected String unescape(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            int iIndexOf = str.indexOf(37);
            if (iIndexOf >= 0) {
                stringBuffer.append(str.substring(0, iIndexOf));
                int i = iIndexOf + 1;
                int i2 = iIndexOf + 3;
                stringBuffer.append((char) Integer.parseInt(str.substring(i, i2), 16));
                str = str.substring(i2);
            } else {
                stringBuffer.append(str);
                return stringBuffer.toString();
            }
        }
    }

    @Override // com.thoughtworks.xstream.persistence.AbstractFilePersistenceStrategy
    protected String getName(Object obj) {
        if (obj == null) {
            return "null@null.xml";
        }
        Class<?> cls = obj.getClass();
        Converter converterLookupConverterForType = getConverterLookup().lookupConverterForType(cls);
        if (converterLookupConverterForType instanceof SingleValueConverter) {
            return new StringBuffer().append(getMapper().serializedClass(cls)).append('@').append(escape(((SingleValueConverter) converterLookupConverterForType).toString(obj))).append(".xml").toString();
        }
        ConversionException conversionException = new ConversionException("No SingleValueConverter available for key type");
        conversionException.add("key-type", cls.getName());
        throw conversionException;
    }

    protected String escape(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (char c : str.toCharArray()) {
            if (c >= ' ' && this.illegalChars.indexOf(c) < 0) {
                stringBuffer.append(c);
            } else {
                stringBuffer.append(new StringBuffer("%").append(Integer.toHexString(c).toUpperCase()).toString());
            }
        }
        return stringBuffer.toString();
    }
}
