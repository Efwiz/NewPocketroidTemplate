package com.thoughtworks.xstream.core.util;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/SelfStreamingInstanceChecker.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class SelfStreamingInstanceChecker implements Converter {
    static /* synthetic */ Class class$java$lang$Object;
    private Converter defaultConverter;
    private final ConverterLookup lookup;
    private final Object self;

    public SelfStreamingInstanceChecker(ConverterLookup converterLookup, Object obj) {
        this.lookup = converterLookup;
        this.self = obj;
    }

    public SelfStreamingInstanceChecker(Converter converter, Object obj) {
        this.defaultConverter = converter;
        this.self = obj;
        this.lookup = null;
    }

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return cls == this.self.getClass();
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        if (obj == this.self) {
            throw new ConversionException("Cannot marshal the XStream instance in action");
        }
        getConverter().marshal(obj, hierarchicalStreamWriter, marshallingContext);
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return getConverter().unmarshal(hierarchicalStreamReader, unmarshallingContext);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private Converter getConverter() throws Throwable {
        Converter converter = this.defaultConverter;
        if (converter != null) {
            return converter;
        }
        ConverterLookup converterLookup = this.lookup;
        Class clsClass$ = class$java$lang$Object;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_LANG_OBJECT);
            class$java$lang$Object = clsClass$;
        }
        return converterLookup.lookupConverterForType(clsClass$);
    }
}
