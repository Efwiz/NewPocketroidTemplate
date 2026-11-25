package com.thoughtworks.xstream.converters.time;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.time.Clock;
import java.time.ZoneId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/time/SystemClockConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class SystemClockConverter implements Converter {
    private final Mapper mapper;
    private final Class<?> type = Clock.systemUTC().getClass();

    public SystemClockConverter(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return cls == this.type;
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter, this.mapper.serializedMember(Clock.class, "zone"), null);
        marshallingContext.convertAnother(((Clock) obj).getZone());
        hierarchicalStreamWriter.endNode();
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        hierarchicalStreamReader.moveDown();
        ZoneId zoneId = (ZoneId) unmarshallingContext.convertAnother(null, ZoneId.class);
        hierarchicalStreamReader.moveUp();
        return Clock.system(zoneId);
    }
}
