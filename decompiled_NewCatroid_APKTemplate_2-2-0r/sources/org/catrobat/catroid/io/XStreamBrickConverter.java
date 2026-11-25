package org.catrobat.catroid.io;

import android.util.Log;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import org.catrobat.catroid.content.bricks.Brick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/XStreamBrickConverter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class XStreamBrickConverter extends ReflectionConverter {
    private static final String[] BRICKS_PACKAGE_NAMES = {"org.catrobat.catroid.content.bricks", "org.catrobat.catroid.physics.content.bricks"};
    private static final String TAG = "XStreamBrickConverter";
    private static final String TYPE = "type";

    public XStreamBrickConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
        super(mapper, reflectionProvider);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class type) {
        return Brick.class.isAssignableFrom(type);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter
    protected void doMarshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        writer.addAttribute("type", source.getClass().getSimpleName());
        super.doMarshal(source, writer, context);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter
    public Object doUnmarshal(Object result, HierarchicalStreamReader reader, UnmarshallingContext context) {
        String attribute = reader.getAttribute("type");
        int i = 0;
        while (true) {
            String[] strArr = BRICKS_PACKAGE_NAMES;
            if (i < strArr.length) {
                try {
                    return super.doUnmarshal((Brick) this.reflectionProvider.newInstance(Class.forName(strArr[i] + "." + attribute)), reader, context);
                } catch (ClassNotFoundException unused) {
                    Log.d(TAG, "Brick " + attribute + " not found in " + BRICKS_PACKAGE_NAMES[i]);
                    i++;
                }
            } else {
                Log.e(TAG, "Brick " + attribute + " not found in packages");
                return super.doUnmarshal(result, reader, context);
            }
        }
    }
}
