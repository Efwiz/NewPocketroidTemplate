package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import com.thoughtworks.xstream.mapper.DefaultMapper;
import com.thoughtworks.xstream.mapper.Mapper;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/JavaClassConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class JavaClassConverter extends AbstractSingleValueConverter {
    static /* synthetic */ Class class$java$lang$Class;
    private Mapper mapper;

    public JavaClassConverter(ClassLoaderReference classLoaderReference) {
        this(new DefaultMapper(classLoaderReference));
    }

    public JavaClassConverter(ClassLoader classLoader) {
        this(new ClassLoaderReference(classLoader));
    }

    protected JavaClassConverter(Mapper mapper) {
        this.mapper = mapper;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        Class clsClass$ = class$java$lang$Class;
        if (clsClass$ == null) {
            clsClass$ = class$(CommonClassNames.JAVA_LANG_CLASS);
            class$java$lang$Class = clsClass$;
        }
        return clsClass$.equals(cls);
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public String toString(Object obj) {
        return this.mapper.serializedClass((Class) obj);
    }

    @Override // com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter, com.thoughtworks.xstream.converters.SingleValueConverter
    public Object fromString(String str) {
        try {
            return this.mapper.realClass(str);
        } catch (CannotResolveClassException e) {
            throw new ConversionException(new StringBuffer("Cannot load java class ").append(str).toString(), e.getCause());
        }
    }
}
