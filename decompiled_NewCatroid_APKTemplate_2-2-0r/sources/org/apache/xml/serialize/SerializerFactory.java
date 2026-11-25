package org.apache.xml.serialize;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Hashtable;
import java.util.StringTokenizer;

/* JADX INFO: loaded from: classes13.dex */
public abstract class SerializerFactory {
    public static final String FactoriesProperty = "org.apache.xml.serialize.factories";
    private static Hashtable _factories = new Hashtable();

    static {
        registerSerializerFactory(new SerializerFactoryImpl("xml"));
        registerSerializerFactory(new SerializerFactoryImpl(Method.HTML));
        registerSerializerFactory(new SerializerFactoryImpl(Method.XHTML));
        registerSerializerFactory(new SerializerFactoryImpl("text"));
        String systemProperty = SecuritySupport.getSystemProperty(FactoriesProperty);
        if (systemProperty != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(systemProperty, " ;,:");
            while (stringTokenizer.hasMoreTokens()) {
                try {
                    SerializerFactory serializerFactory = (SerializerFactory) ObjectFactory.newInstance(stringTokenizer.nextToken(), SerializerFactory.class.getClassLoader(), true);
                    if (_factories.containsKey(serializerFactory.getSupportedMethod())) {
                        _factories.put(serializerFactory.getSupportedMethod(), serializerFactory);
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    public static SerializerFactory getSerializerFactory(String str) {
        return (SerializerFactory) _factories.get(str);
    }

    public static void registerSerializerFactory(SerializerFactory serializerFactory) {
        synchronized (_factories) {
            _factories.put(serializerFactory.getSupportedMethod(), serializerFactory);
        }
    }

    protected abstract String getSupportedMethod();

    public abstract Serializer makeSerializer(OutputStream outputStream, OutputFormat outputFormat) throws UnsupportedEncodingException;

    public abstract Serializer makeSerializer(Writer writer, OutputFormat outputFormat);

    public abstract Serializer makeSerializer(OutputFormat outputFormat);
}
