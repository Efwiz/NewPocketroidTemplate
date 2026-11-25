package com.thoughtworks.xstream.core.util;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.ErrorWritingException;
import com.thoughtworks.xstream.converters.reflection.ObjectAccessException;
import java.beans.PropertyEditor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/core/util/ThreadSafePropertyEditor.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class ThreadSafePropertyEditor {
    static /* synthetic */ Class class$java$beans$PropertyEditor;
    private final Class editorType;
    private final Pool pool;

    public ThreadSafePropertyEditor(Class cls, int i, int i2) throws Throwable {
        Class clsClass$ = class$java$beans$PropertyEditor;
        if (clsClass$ == null) {
            clsClass$ = class$("java.beans.PropertyEditor");
            class$java$beans$PropertyEditor = clsClass$;
        }
        if (!clsClass$.isAssignableFrom(cls)) {
            StringBuffer stringBufferAppend = new StringBuffer().append(cls.getName()).append(" is not a ");
            Class clsClass$2 = class$java$beans$PropertyEditor;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.beans.PropertyEditor");
                class$java$beans$PropertyEditor = clsClass$2;
            }
            throw new IllegalArgumentException(stringBufferAppend.append(clsClass$2.getName()).toString());
        }
        this.editorType = cls;
        this.pool = new Pool(i, i2, new Pool.Factory() { // from class: com.thoughtworks.xstream.core.util.ThreadSafePropertyEditor.1
            @Override // com.thoughtworks.xstream.core.util.Pool.Factory
            public Object newInstance() {
                ErrorWritingException conversionException;
                try {
                    return ThreadSafePropertyEditor.this.editorType.newInstance();
                } catch (IllegalAccessException e) {
                    conversionException = new ObjectAccessException("Cannot call default constructor", e);
                    conversionException.add("construction-type", ThreadSafePropertyEditor.this.editorType.getName());
                    throw conversionException;
                } catch (InstantiationException e2) {
                    conversionException = new ConversionException("Faild to call default constructor", e2);
                    conversionException.add("construction-type", ThreadSafePropertyEditor.this.editorType.getName());
                    throw conversionException;
                }
            }
        });
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public String getAsText(Object obj) {
        PropertyEditor propertyEditorFetchFromPool = fetchFromPool();
        try {
            propertyEditorFetchFromPool.setValue(obj);
            return propertyEditorFetchFromPool.getAsText();
        } finally {
            this.pool.putInPool(propertyEditorFetchFromPool);
        }
    }

    public Object setAsText(String str) {
        PropertyEditor propertyEditorFetchFromPool = fetchFromPool();
        try {
            propertyEditorFetchFromPool.setAsText(str);
            return propertyEditorFetchFromPool.getValue();
        } finally {
            this.pool.putInPool(propertyEditorFetchFromPool);
        }
    }

    private PropertyEditor fetchFromPool() {
        return (PropertyEditor) this.pool.fetchFromPool();
    }
}
