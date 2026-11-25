package com.thoughtworks.xstream;

import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.security.TypeHierarchyPermission;
import com.thoughtworks.xstream.security.TypePermission;
import com.thoughtworks.xstream.security.WildcardTypePermission;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.datatype.DatatypeFactory;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/XStreamer.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class XStreamer {
    private static final TypePermission[] PERMISSIONS;
    static /* synthetic */ Class class$com$thoughtworks$xstream$MarshallingStrategy;
    static /* synthetic */ Class class$com$thoughtworks$xstream$XStream;
    static /* synthetic */ Class class$com$thoughtworks$xstream$converters$ConverterLookup;
    static /* synthetic */ Class class$com$thoughtworks$xstream$converters$ConverterMatcher;
    static /* synthetic */ Class class$com$thoughtworks$xstream$converters$ConverterRegistry;
    static /* synthetic */ Class class$com$thoughtworks$xstream$converters$MarshallingContext;
    static /* synthetic */ Class class$com$thoughtworks$xstream$converters$UnmarshallingContext;
    static /* synthetic */ Class class$com$thoughtworks$xstream$converters$javabean$JavaBeanProvider;
    static /* synthetic */ Class class$com$thoughtworks$xstream$converters$reflection$FieldKeySorter;
    static /* synthetic */ Class class$com$thoughtworks$xstream$converters$reflection$ReflectionProvider;
    static /* synthetic */ Class class$com$thoughtworks$xstream$core$JVM;
    static /* synthetic */ Class class$com$thoughtworks$xstream$io$HierarchicalStreamDriver;
    static /* synthetic */ Class class$com$thoughtworks$xstream$io$naming$NameCoder;
    static /* synthetic */ Class class$com$thoughtworks$xstream$mapper$Mapper;
    static /* synthetic */ Class class$com$thoughtworks$xstream$security$TypePermission;
    static /* synthetic */ Class class$javax$xml$datatype$DatatypeFactory;

    static {
        TypePermission[] typePermissionArr = new TypePermission[16];
        Class clsClass$ = class$com$thoughtworks$xstream$converters$ConverterMatcher;
        if (clsClass$ == null) {
            clsClass$ = class$("com.thoughtworks.xstream.converters.ConverterMatcher");
            class$com$thoughtworks$xstream$converters$ConverterMatcher = clsClass$;
        }
        typePermissionArr[0] = new TypeHierarchyPermission(clsClass$);
        Class clsClass$2 = class$com$thoughtworks$xstream$mapper$Mapper;
        if (clsClass$2 == null) {
            clsClass$2 = class$("com.thoughtworks.xstream.mapper.Mapper");
            class$com$thoughtworks$xstream$mapper$Mapper = clsClass$2;
        }
        typePermissionArr[1] = new TypeHierarchyPermission(clsClass$2);
        Class clsClass$3 = class$com$thoughtworks$xstream$XStream;
        if (clsClass$3 == null) {
            clsClass$3 = class$("com.thoughtworks.xstream.XStream");
            class$com$thoughtworks$xstream$XStream = clsClass$3;
        }
        typePermissionArr[2] = new TypeHierarchyPermission(clsClass$3);
        Class clsClass$4 = class$com$thoughtworks$xstream$converters$reflection$ReflectionProvider;
        if (clsClass$4 == null) {
            clsClass$4 = class$("com.thoughtworks.xstream.converters.reflection.ReflectionProvider");
            class$com$thoughtworks$xstream$converters$reflection$ReflectionProvider = clsClass$4;
        }
        typePermissionArr[3] = new TypeHierarchyPermission(clsClass$4);
        Class clsClass$5 = class$com$thoughtworks$xstream$converters$javabean$JavaBeanProvider;
        if (clsClass$5 == null) {
            clsClass$5 = class$("com.thoughtworks.xstream.converters.javabean.JavaBeanProvider");
            class$com$thoughtworks$xstream$converters$javabean$JavaBeanProvider = clsClass$5;
        }
        typePermissionArr[4] = new TypeHierarchyPermission(clsClass$5);
        Class clsClass$6 = class$com$thoughtworks$xstream$converters$reflection$FieldKeySorter;
        if (clsClass$6 == null) {
            clsClass$6 = class$("com.thoughtworks.xstream.converters.reflection.FieldKeySorter");
            class$com$thoughtworks$xstream$converters$reflection$FieldKeySorter = clsClass$6;
        }
        typePermissionArr[5] = new TypeHierarchyPermission(clsClass$6);
        Class clsClass$7 = class$com$thoughtworks$xstream$converters$ConverterLookup;
        if (clsClass$7 == null) {
            clsClass$7 = class$("com.thoughtworks.xstream.converters.ConverterLookup");
            class$com$thoughtworks$xstream$converters$ConverterLookup = clsClass$7;
        }
        typePermissionArr[6] = new TypeHierarchyPermission(clsClass$7);
        Class clsClass$8 = class$com$thoughtworks$xstream$converters$ConverterRegistry;
        if (clsClass$8 == null) {
            clsClass$8 = class$("com.thoughtworks.xstream.converters.ConverterRegistry");
            class$com$thoughtworks$xstream$converters$ConverterRegistry = clsClass$8;
        }
        typePermissionArr[7] = new TypeHierarchyPermission(clsClass$8);
        Class clsClass$9 = class$com$thoughtworks$xstream$io$HierarchicalStreamDriver;
        if (clsClass$9 == null) {
            clsClass$9 = class$("com.thoughtworks.xstream.io.HierarchicalStreamDriver");
            class$com$thoughtworks$xstream$io$HierarchicalStreamDriver = clsClass$9;
        }
        typePermissionArr[8] = new TypeHierarchyPermission(clsClass$9);
        Class clsClass$10 = class$com$thoughtworks$xstream$MarshallingStrategy;
        if (clsClass$10 == null) {
            clsClass$10 = class$("com.thoughtworks.xstream.MarshallingStrategy");
            class$com$thoughtworks$xstream$MarshallingStrategy = clsClass$10;
        }
        typePermissionArr[9] = new TypeHierarchyPermission(clsClass$10);
        Class clsClass$11 = class$com$thoughtworks$xstream$converters$MarshallingContext;
        if (clsClass$11 == null) {
            clsClass$11 = class$("com.thoughtworks.xstream.converters.MarshallingContext");
            class$com$thoughtworks$xstream$converters$MarshallingContext = clsClass$11;
        }
        typePermissionArr[10] = new TypeHierarchyPermission(clsClass$11);
        Class clsClass$12 = class$com$thoughtworks$xstream$converters$UnmarshallingContext;
        if (clsClass$12 == null) {
            clsClass$12 = class$("com.thoughtworks.xstream.converters.UnmarshallingContext");
            class$com$thoughtworks$xstream$converters$UnmarshallingContext = clsClass$12;
        }
        typePermissionArr[11] = new TypeHierarchyPermission(clsClass$12);
        Class clsClass$13 = class$com$thoughtworks$xstream$io$naming$NameCoder;
        if (clsClass$13 == null) {
            clsClass$13 = class$("com.thoughtworks.xstream.io.naming.NameCoder");
            class$com$thoughtworks$xstream$io$naming$NameCoder = clsClass$13;
        }
        typePermissionArr[12] = new TypeHierarchyPermission(clsClass$13);
        Class clsClass$14 = class$com$thoughtworks$xstream$security$TypePermission;
        if (clsClass$14 == null) {
            clsClass$14 = class$("com.thoughtworks.xstream.security.TypePermission");
            class$com$thoughtworks$xstream$security$TypePermission = clsClass$14;
        }
        typePermissionArr[13] = new TypeHierarchyPermission(clsClass$14);
        String[] strArr = new String[1];
        StringBuffer stringBuffer = new StringBuffer();
        Class clsClass$15 = class$com$thoughtworks$xstream$core$JVM;
        if (clsClass$15 == null) {
            clsClass$15 = class$("com.thoughtworks.xstream.core.JVM");
            class$com$thoughtworks$xstream$core$JVM = clsClass$15;
        }
        strArr[0] = stringBuffer.append(clsClass$15.getPackage().getName()).append(".**").toString();
        typePermissionArr[14] = new WildcardTypePermission(strArr);
        Class clsClass$16 = class$javax$xml$datatype$DatatypeFactory;
        if (clsClass$16 == null) {
            clsClass$16 = class$(DatatypeFactory.DATATYPEFACTORY_PROPERTY);
            class$javax$xml$datatype$DatatypeFactory = clsClass$16;
        }
        typePermissionArr[15] = new TypeHierarchyPermission(clsClass$16);
        PERMISSIONS = typePermissionArr;
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public String toXML(XStream xStream, Object obj) throws Throwable {
        StringWriter stringWriter = new StringWriter();
        try {
            toXML(xStream, obj, stringWriter);
            return stringWriter.toString();
        } catch (ObjectStreamException e) {
            throw e;
        } catch (IOException e2) {
            throw new StreamException("Unexpected IO error from a StringWriter", e2);
        }
    }

    public void toXML(XStream xStream, Object obj, Writer writer) throws Throwable {
        XStream xStream2 = new XStream();
        XStream.setupDefaultSecurity(xStream2);
        ObjectOutputStream objectOutputStreamCreateObjectOutputStream = xStream2.createObjectOutputStream(writer);
        try {
            objectOutputStreamCreateObjectOutputStream.writeObject(xStream);
            objectOutputStreamCreateObjectOutputStream.flush();
            xStream.toXML(obj, writer);
        } finally {
            objectOutputStreamCreateObjectOutputStream.close();
        }
    }

    public Object fromXML(String str) throws ObjectStreamException, ClassNotFoundException {
        try {
            return fromXML(new StringReader(str));
        } catch (ObjectStreamException e) {
            throw e;
        } catch (IOException e2) {
            throw new StreamException("Unexpected IO error from a StringReader", e2);
        }
    }

    public Object fromXML(String str, TypePermission[] typePermissionArr) throws ObjectStreamException, ClassNotFoundException {
        try {
            return fromXML(new StringReader(str), typePermissionArr);
        } catch (ObjectStreamException e) {
            throw e;
        } catch (IOException e2) {
            throw new StreamException("Unexpected IO error from a StringReader", e2);
        }
    }

    public Object fromXML(HierarchicalStreamDriver hierarchicalStreamDriver, String str) throws ObjectStreamException, ClassNotFoundException {
        try {
            return fromXML(hierarchicalStreamDriver, new StringReader(str));
        } catch (ObjectStreamException e) {
            throw e;
        } catch (IOException e2) {
            throw new StreamException("Unexpected IO error from a StringReader", e2);
        }
    }

    public Object fromXML(HierarchicalStreamDriver hierarchicalStreamDriver, String str, TypePermission[] typePermissionArr) throws ObjectStreamException, ClassNotFoundException {
        try {
            return fromXML(hierarchicalStreamDriver, new StringReader(str), typePermissionArr);
        } catch (ObjectStreamException e) {
            throw e;
        } catch (IOException e2) {
            throw new StreamException("Unexpected IO error from a StringReader", e2);
        }
    }

    public Object fromXML(Reader reader) throws IOException, ClassNotFoundException {
        return fromXML(new XppDriver(), reader);
    }

    public Object fromXML(Reader reader, TypePermission[] typePermissionArr) throws IOException, ClassNotFoundException {
        return fromXML(new XppDriver(), reader, typePermissionArr);
    }

    public Object fromXML(HierarchicalStreamDriver hierarchicalStreamDriver, Reader reader) throws IOException, ClassNotFoundException {
        return fromXML(hierarchicalStreamDriver, reader, PERMISSIONS);
    }

    public Object fromXML(HierarchicalStreamDriver hierarchicalStreamDriver, Reader reader, TypePermission[] typePermissionArr) throws Throwable {
        XStream xStream = new XStream(hierarchicalStreamDriver);
        XStream.setupDefaultSecurity(xStream);
        for (TypePermission typePermission : typePermissionArr) {
            xStream.addPermission(typePermission);
        }
        HierarchicalStreamReader hierarchicalStreamReaderCreateReader = hierarchicalStreamDriver.createReader(reader);
        ObjectInputStream objectInputStreamCreateObjectInputStream = xStream.createObjectInputStream(hierarchicalStreamReaderCreateReader);
        try {
            objectInputStreamCreateObjectInputStream = ((XStream) objectInputStreamCreateObjectInputStream.readObject()).createObjectInputStream(hierarchicalStreamReaderCreateReader);
            try {
                Object object = objectInputStreamCreateObjectInputStream.readObject();
                objectInputStreamCreateObjectInputStream.close();
                return object;
            } finally {
                objectInputStreamCreateObjectInputStream.close();
            }
        } catch (Throwable th) {
            objectInputStreamCreateObjectInputStream.close();
            throw th;
        }
    }

    public static TypePermission[] getDefaultPermissions() {
        return (TypePermission[]) PERMISSIONS.clone();
    }
}
