package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.core.util.Fields;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/converters/extended/DynamicProxyConverter.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class DynamicProxyConverter implements Converter {
    private static final InvocationHandler DUMMY;
    private static final Field HANDLER;
    static /* synthetic */ Class class$com$thoughtworks$xstream$converters$extended$DynamicProxyConverter;
    static /* synthetic */ Class class$com$thoughtworks$xstream$mapper$DynamicProxyMapper$DynamicProxy;
    static /* synthetic */ Class class$java$lang$reflect$InvocationHandler;
    static /* synthetic */ Class class$java$lang$reflect$Proxy;
    private ClassLoaderReference classLoaderReference;
    private Mapper mapper;

    static {
        Class clsClass$ = class$java$lang$reflect$Proxy;
        if (clsClass$ == null) {
            clsClass$ = class$("java.lang.reflect.Proxy");
            class$java$lang$reflect$Proxy = clsClass$;
        }
        Class clsClass$2 = class$java$lang$reflect$InvocationHandler;
        if (clsClass$2 == null) {
            clsClass$2 = class$("java.lang.reflect.InvocationHandler");
            class$java$lang$reflect$InvocationHandler = clsClass$2;
        }
        HANDLER = Fields.locate(clsClass$, clsClass$2, false);
        DUMMY = new InvocationHandler() { // from class: com.thoughtworks.xstream.converters.extended.DynamicProxyConverter.1
            @Override // java.lang.reflect.InvocationHandler
            public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
                return null;
            }
        };
    }

    static /* synthetic */ Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public DynamicProxyConverter(Mapper mapper) throws Throwable {
        Class clsClass$ = class$com$thoughtworks$xstream$converters$extended$DynamicProxyConverter;
        if (clsClass$ == null) {
            clsClass$ = class$("com.thoughtworks.xstream.converters.extended.DynamicProxyConverter");
            class$com$thoughtworks$xstream$converters$extended$DynamicProxyConverter = clsClass$;
        }
        this(mapper, clsClass$.getClassLoader());
    }

    public DynamicProxyConverter(Mapper mapper, ClassLoaderReference classLoaderReference) {
        this.classLoaderReference = classLoaderReference;
        this.mapper = mapper;
    }

    public DynamicProxyConverter(Mapper mapper, ClassLoader classLoader) {
        this(mapper, new ClassLoaderReference(classLoader));
    }

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class cls) throws Throwable {
        if (cls != null) {
            Class clsClass$ = class$com$thoughtworks$xstream$mapper$DynamicProxyMapper$DynamicProxy;
            if (clsClass$ == null) {
                clsClass$ = class$("com.thoughtworks.xstream.mapper.DynamicProxyMapper$DynamicProxy");
                class$com$thoughtworks$xstream$mapper$DynamicProxyMapper$DynamicProxy = clsClass$;
            }
            if (cls.equals(clsClass$) || Proxy.isProxyClass(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(obj);
        addInterfacesToXml(obj, hierarchicalStreamWriter);
        hierarchicalStreamWriter.startNode("handler");
        String strAliasForSystemAttribute = this.mapper.aliasForSystemAttribute("class");
        if (strAliasForSystemAttribute != null) {
            hierarchicalStreamWriter.addAttribute(strAliasForSystemAttribute, this.mapper.serializedClass(invocationHandler.getClass()));
        }
        marshallingContext.convertAnother(invocationHandler);
        hierarchicalStreamWriter.endNode();
    }

    private void addInterfacesToXml(Object obj, HierarchicalStreamWriter hierarchicalStreamWriter) {
        for (Class<?> cls : obj.getClass().getInterfaces()) {
            hierarchicalStreamWriter.startNode(PsiKeyword.INTERFACE);
            hierarchicalStreamWriter.setValue(this.mapper.serializedClass(cls));
            hierarchicalStreamWriter.endNode();
        }
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        Class clsRealClass;
        String strAliasForSystemAttribute;
        ArrayList arrayList = new ArrayList();
        while (true) {
            if (!hierarchicalStreamReader.hasMoreChildren()) {
                clsRealClass = null;
                break;
            }
            hierarchicalStreamReader.moveDown();
            String nodeName = hierarchicalStreamReader.getNodeName();
            if (nodeName.equals(PsiKeyword.INTERFACE)) {
                arrayList.add(this.mapper.realClass(hierarchicalStreamReader.getValue()));
            } else if (nodeName.equals("handler") && (strAliasForSystemAttribute = this.mapper.aliasForSystemAttribute("class")) != null) {
                clsRealClass = this.mapper.realClass(hierarchicalStreamReader.getAttribute(strAliasForSystemAttribute));
                break;
            }
            hierarchicalStreamReader.moveUp();
        }
        if (clsRealClass == null) {
            throw new ConversionException("No InvocationHandler specified for dynamic proxy");
        }
        Class[] clsArr = new Class[arrayList.size()];
        arrayList.toArray(clsArr);
        Field field = HANDLER;
        Object objNewProxyInstance = field != null ? Proxy.newProxyInstance(this.classLoaderReference.getReference(), clsArr, DUMMY) : null;
        InvocationHandler invocationHandler = (InvocationHandler) unmarshallingContext.convertAnother(objNewProxyInstance, clsRealClass);
        hierarchicalStreamReader.moveUp();
        if (field != null) {
            Fields.write(field, objNewProxyInstance, invocationHandler);
            return objNewProxyInstance;
        }
        return Proxy.newProxyInstance(this.classLoaderReference.getReference(), clsArr, invocationHandler);
    }
}
