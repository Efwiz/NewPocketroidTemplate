package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/GregorianCalendarConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class GregorianCalendarConverter implements Converter {
    static /* synthetic */ Class class$java$lang$String;
    static /* synthetic */ Class class$java$util$GregorianCalendar;

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        Class clsClass$ = class$java$util$GregorianCalendar;
        if (clsClass$ == null) {
            clsClass$ = class$("java.util.GregorianCalendar");
            class$java$util$GregorianCalendar = clsClass$;
        }
        return cls == clsClass$;
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) throws Throwable {
        GregorianCalendar gregorianCalendar = (GregorianCalendar) obj;
        ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter, "time", Long.TYPE);
        hierarchicalStreamWriter.setValue(String.valueOf(gregorianCalendar.getTime().getTime()));
        hierarchicalStreamWriter.endNode();
        Class clsClass$ = class$java$lang$String;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_LANG_STRING);
            class$java$lang$String = clsClass$;
        }
        ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter, "timezone", clsClass$);
        hierarchicalStreamWriter.setValue(gregorianCalendar.getTimeZone().getID());
        hierarchicalStreamWriter.endNode();
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        String id2;
        hierarchicalStreamReader.moveDown();
        long j = Long.parseLong(hierarchicalStreamReader.getValue());
        hierarchicalStreamReader.moveUp();
        if (hierarchicalStreamReader.hasMoreChildren()) {
            hierarchicalStreamReader.moveDown();
            id2 = hierarchicalStreamReader.getValue();
            hierarchicalStreamReader.moveUp();
        } else {
            id2 = TimeZone.getDefault().getID();
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone(id2));
        gregorianCalendar.setTime(new Date(j));
        return gregorianCalendar;
    }
}
