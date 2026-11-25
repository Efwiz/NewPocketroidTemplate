package com.thoughtworks.xstream.converters.time;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import io.reactivex.annotations.SchedulerSupport;
import java.time.temporal.ValueRange;
import java.util.HashMap;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/time/ValueRangeConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ValueRangeConverter implements Converter {
    private final Mapper mapper;

    public ValueRangeConverter(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) {
        return cls == ValueRange.class;
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        ValueRange valueRange = (ValueRange) obj;
        write("maxLargest", valueRange.getMaximum(), hierarchicalStreamWriter);
        write("maxSmallest", valueRange.getSmallestMaximum(), hierarchicalStreamWriter);
        write("minLargest", valueRange.getLargestMinimum(), hierarchicalStreamWriter);
        write("minSmallest", valueRange.getMinimum(), hierarchicalStreamWriter);
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        boolean zEquals = SchedulerSupport.CUSTOM.equals(hierarchicalStreamReader.getAttribute(this.mapper.aliasForSystemAttribute("serialization")));
        if (zEquals) {
            hierarchicalStreamReader.moveDown();
            hierarchicalStreamReader.moveDown();
        }
        HashMap map = new HashMap();
        while (hierarchicalStreamReader.hasMoreChildren()) {
            hierarchicalStreamReader.moveDown();
            String nodeName = hierarchicalStreamReader.getNodeName();
            if (!zEquals) {
                nodeName = this.mapper.realMember(ValueRange.class, nodeName);
            }
            map.put(nodeName, Long.valueOf(hierarchicalStreamReader.getValue()));
            hierarchicalStreamReader.moveUp();
        }
        if (zEquals) {
            hierarchicalStreamReader.moveUp();
            hierarchicalStreamReader.moveUp();
        }
        return ValueRange.of(((Long) map.get("minSmallest")).longValue(), ((Long) map.get("minLargest")).longValue(), ((Long) map.get("maxSmallest")).longValue(), ((Long) map.get("maxLargest")).longValue());
    }

    private void write(String str, long j, HierarchicalStreamWriter hierarchicalStreamWriter) {
        ExtendedHierarchicalStreamWriterHelper.startNode(hierarchicalStreamWriter, this.mapper.serializedMember(ValueRange.class, str), Long.TYPE);
        hierarchicalStreamWriter.setValue(String.valueOf(j));
        hierarchicalStreamWriter.endNode();
    }
}
