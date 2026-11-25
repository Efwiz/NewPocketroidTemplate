package com.thoughtworks.xstream.converters;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/SingleValueConverterWrapper.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class SingleValueConverterWrapper implements Converter, SingleValueConverter, ErrorReporter {
    private final SingleValueConverter wrapped;

    public SingleValueConverterWrapper(SingleValueConverter singleValueConverter) {
        this.wrapped = singleValueConverter;
    }

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return this.wrapped.canConvert(cls);
    }

    @Override // com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        return this.wrapped.toString(obj);
    }

    @Override // com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        return this.wrapped.fromString(str);
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        hierarchicalStreamWriter.setValue(toString(obj));
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return fromString(hierarchicalStreamReader.getValue());
    }

    @Override // com.thoughtworks.xstream.converters.ErrorReporter
    public void appendErrors(ErrorWriter errorWriter) {
        SingleValueConverter singleValueConverter = this.wrapped;
        errorWriter.add("wrapped-converter", singleValueConverter == null ? "(null)" : singleValueConverter.getClass().getName());
        SingleValueConverter singleValueConverter2 = this.wrapped;
        if (singleValueConverter2 instanceof ErrorReporter) {
            ((ErrorReporter) singleValueConverter2).appendErrors(errorWriter);
        }
    }
}
