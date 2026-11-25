package org.apache.commons.beanutils.converters;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/* JADX INFO: loaded from: classes12.dex */
@Deprecated
public abstract class AbstractArrayConverter implements Converter {
    public static final Object NO_DEFAULT = new Object();
    protected static String[] strings = new String[0];
    protected Object defaultValue;
    protected boolean useDefault;

    @Override // org.apache.commons.beanutils.Converter
    public abstract Object convert(Class cls, Object obj);

    public AbstractArrayConverter() {
        this.defaultValue = null;
        this.useDefault = false;
    }

    public AbstractArrayConverter(Object obj) {
        this.defaultValue = null;
        this.useDefault = true;
        if (obj == NO_DEFAULT) {
            this.useDefault = false;
        } else {
            this.defaultValue = obj;
            this.useDefault = true;
        }
    }

    protected List parseElements(String str) {
        int iNextToken;
        str.getClass();
        String strTrim = str.trim();
        if (strTrim.startsWith("{") && strTrim.endsWith("}")) {
            strTrim = strTrim.substring(1, strTrim.length() - 1);
        }
        try {
            StreamTokenizer streamTokenizer = new StreamTokenizer(new StringReader(strTrim));
            streamTokenizer.whitespaceChars(44, 44);
            streamTokenizer.ordinaryChars(48, 57);
            streamTokenizer.ordinaryChars(46, 46);
            streamTokenizer.ordinaryChars(45, 45);
            streamTokenizer.wordChars(48, 57);
            streamTokenizer.wordChars(46, 46);
            streamTokenizer.wordChars(45, 45);
            ArrayList arrayList = new ArrayList();
            while (true) {
                iNextToken = streamTokenizer.nextToken();
                if (iNextToken != -3 && iNextToken <= 0) {
                    break;
                }
                arrayList.add(streamTokenizer.sval);
            }
            if (iNextToken == -1) {
                return arrayList;
            }
            throw new ConversionException("Encountered token of type " + iNextToken);
        } catch (IOException e) {
            throw new ConversionException(e);
        }
    }
}
