package org.catrobat.catroid.io;

import android.util.Log;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import org.catrobat.catroid.content.Setting;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/XStreamSettingConverter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class XStreamSettingConverter extends ReflectionConverter {
    private static final String BRICKS_PACKAGE_NAME = "org.catrobat.catroid.content";
    private static final String TAG = "XStreamSettingConverter";
    private static final String TYPE = "type";

    public XStreamSettingConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
        super(mapper, reflectionProvider);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class type) {
        return Setting.class.isAssignableFrom(type);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter
    protected void doMarshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        writer.addAttribute("type", source.getClass().getSimpleName());
        super.doMarshal(source, writer, context);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter
    public Object doUnmarshal(Object result, HierarchicalStreamReader reader, UnmarshallingContext context) {
        try {
            return super.doUnmarshal((Setting) this.reflectionProvider.newInstance(Class.forName("org.catrobat.catroid.content." + reader.getAttribute("type"))), reader, context);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "Setting class not found : " + result.toString(), e);
            return super.doUnmarshal(result, reader, context);
        }
    }
}
