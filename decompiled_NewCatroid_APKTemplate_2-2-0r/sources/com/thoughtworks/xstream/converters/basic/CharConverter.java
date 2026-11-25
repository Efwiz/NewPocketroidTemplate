package com.thoughtworks.xstream.converters.basic;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/basic/CharConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class CharConverter implements Converter, SingleValueConverter {
    static /* synthetic */ Class class$java$lang$Character;

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        if (cls != Character.TYPE) {
            Class clsClass$ = class$java$lang$Character;
            if (clsClass$ == null) {
                clsClass$ = class$(CommonClassNames.JAVA_LANG_CHARACTER);
                class$java$lang$Character = clsClass$;
            }
            if (cls != clsClass$) {
                return false;
            }
        }
        return true;
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        hierarchicalStreamWriter.setValue(toString(obj));
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        String attribute = hierarchicalStreamReader.getAttribute("null");
        if (attribute != null && attribute.equals("true")) {
            return new Character((char) 0);
        }
        return fromString(hierarchicalStreamReader.getValue());
    }

    @Override // com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        if (str.length() == 0) {
            return new Character((char) 0);
        }
        return new Character(str.charAt(0));
    }

    @Override // com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        return ((Character) obj).charValue() == 0 ? "" : obj.toString();
    }
}
