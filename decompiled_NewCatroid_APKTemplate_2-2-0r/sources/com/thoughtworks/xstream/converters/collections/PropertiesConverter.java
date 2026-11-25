package com.thoughtworks.xstream.converters.collections;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.core.util.Fields;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/collections/PropertiesConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class PropertiesConverter implements Converter {
    static /* synthetic */ Class class$java$util$Properties;
    private static final Field defaultsField;
    private final boolean sort;

    static {
        Class clsClass$ = class$java$util$Properties;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_UTIL_PROPERTIES);
            class$java$util$Properties = clsClass$;
        }
        Class clsClass$2 = class$java$util$Properties;
        if (clsClass$2 == null) {
            clsClass$2 = class$(CommonClassNames.JAVA_UTIL_PROPERTIES);
            class$java$util$Properties = clsClass$2;
        }
        defaultsField = Fields.locate(clsClass$, clsClass$2, false);
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public PropertiesConverter() {
        this(false);
    }

    public PropertiesConverter(boolean z) {
        this.sort = z;
    }

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        Class clsClass$ = class$java$util$Properties;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_UTIL_PROPERTIES);
            class$java$util$Properties = clsClass$;
        }
        return clsClass$ == cls;
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        Properties properties;
        Properties properties2 = (Properties) obj;
        for (Map.Entry entry : (this.sort ? new TreeMap(properties2) : properties2).entrySet()) {
            hierarchicalStreamWriter.startNode("property");
            hierarchicalStreamWriter.addAttribute("name", entry.getKey().toString());
            hierarchicalStreamWriter.addAttribute("value", entry.getValue().toString());
            hierarchicalStreamWriter.endNode();
        }
        Field field = defaultsField;
        if (field == null || (properties = (Properties) Fields.read(field, properties2)) == null) {
            return;
        }
        hierarchicalStreamWriter.startNode("defaults");
        marshal(properties, hierarchicalStreamWriter, marshallingContext);
        hierarchicalStreamWriter.endNode();
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        Properties properties = new Properties();
        Properties properties2 = null;
        while (hierarchicalStreamReader.hasMoreChildren()) {
            hierarchicalStreamReader.moveDown();
            if (hierarchicalStreamReader.getNodeName().equals("defaults")) {
                properties2 = (Properties) unmarshal(hierarchicalStreamReader, unmarshallingContext);
            } else {
                properties.setProperty(hierarchicalStreamReader.getAttribute("name"), hierarchicalStreamReader.getAttribute("value"));
            }
            hierarchicalStreamReader.moveUp();
        }
        if (properties2 == null) {
            return properties;
        }
        Properties properties3 = new Properties(properties2);
        properties3.putAll(properties);
        return properties3;
    }
}
