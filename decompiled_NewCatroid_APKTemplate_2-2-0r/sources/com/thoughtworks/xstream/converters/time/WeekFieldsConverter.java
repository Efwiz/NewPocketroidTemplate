package com.thoughtworks.xstream.converters.time;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import io.reactivex.annotations.SchedulerSupport;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/time/WeekFieldsConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class WeekFieldsConverter implements Converter {
    private final Mapper mapper;

    public WeekFieldsConverter(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return cls == WeekFields.class;
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        WeekFields weekFields = (WeekFields) obj;
        ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter, this.mapper.serializedMember(WeekFields.class, "minimalDays"), Integer.TYPE);
        hierarchicalStreamWriter.setValue(String.valueOf(weekFields.getMinimalDaysInFirstWeek()));
        hierarchicalStreamWriter.endNode();
        ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter, this.mapper.serializedMember(WeekFields.class, "firstDayOfWeek"), DayOfWeek.class);
        marshallingContext.convertAnother(weekFields.getFirstDayOfWeek());
        hierarchicalStreamWriter.endNode();
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        String strRealMember;
        boolean zEquals = SchedulerSupport.CUSTOM.equals(hierarchicalStreamReader.getAttribute(this.mapper.aliasForSystemAttribute("serialization")));
        if (zEquals) {
            hierarchicalStreamReader.moveDown();
            hierarchicalStreamReader.moveDown();
        }
        int i = 0;
        DayOfWeek dayOfWeek = null;
        while (hierarchicalStreamReader.hasMoreChildren()) {
            hierarchicalStreamReader.moveDown();
            if (zEquals) {
                strRealMember = hierarchicalStreamReader.getNodeName();
            } else {
                strRealMember = this.mapper.realMember(WeekFields.class, hierarchicalStreamReader.getNodeName());
            }
            if ("minimalDays".equals(strRealMember)) {
                i = Integer.parseInt(hierarchicalStreamReader.getValue());
            } else if ("firstDayOfWeek".equals(strRealMember)) {
                dayOfWeek = (DayOfWeek) unmarshallingContext.convertAnother(null, DayOfWeek.class);
            } else {
                throw new AbstractReflectionConverter.UnknownFieldException(WeekFields.class.getName(), strRealMember);
            }
            hierarchicalStreamReader.moveUp();
        }
        if (zEquals) {
            hierarchicalStreamReader.moveUp();
            hierarchicalStreamReader.moveUp();
        }
        return WeekFields.of(dayOfWeek, i);
    }
}
