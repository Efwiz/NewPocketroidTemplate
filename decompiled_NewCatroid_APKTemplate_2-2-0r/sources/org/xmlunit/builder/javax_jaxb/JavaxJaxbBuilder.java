package org.xmlunit.builder.javax_jaxb;

import java.beans.Introspector;
import java.lang.reflect.Method;
import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.util.JAXBSource;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import org.xml.sax.InputSource;
import org.xmlunit.XMLUnitException;
import org.xmlunit.builder.JaxbBuilder;

/* JADX INFO: loaded from: classes3.dex */
public class JavaxJaxbBuilder extends JaxbBuilder {
    protected JavaxJaxbBuilder(Object obj) {
        super(obj);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.xml.bind.DataBindingException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.xml.bind.JAXBException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.xml.bind.PropertyException */
    @Override // org.xmlunit.builder.Input.Builder
    public Source build() throws PropertyException, DataBindingException {
        Marshaller marshallerCreateDefaultMarshaller;
        try {
            Object marshaller = getMarshaller();
            if (marshaller != null) {
                if (marshaller instanceof Marshaller) {
                    marshallerCreateDefaultMarshaller = (Marshaller) marshaller;
                } else {
                    throw new XMLUnitException("provided Marshaller must be a " + Marshaller.class.getName());
                }
            } else {
                marshallerCreateDefaultMarshaller = createDefaultMarshaller();
            }
            JAXBSource jAXBSource = new JAXBSource(marshallerCreateDefaultMarshaller, getPreparedJaxbObject());
            jAXBSource.setInputSource((InputSource) null);
            return jAXBSource;
        } catch (JAXBException e) {
            throw new DataBindingException(e);
        }
    }

    private Object getPreparedJaxbObject() {
        Object object = getObject();
        if ((object instanceof JAXBElement) || object.getClass().getAnnotation(XmlRootElement.class) != null) {
            return object;
        }
        if (getUseObjectFactory()) {
            return createJAXBElement(object);
        }
        return createInferredJAXBElement(object);
    }

    private Marshaller createDefaultMarshaller() throws PropertyException, JAXBException {
        JAXBContext jAXBContextNewInstance;
        Object object = getObject();
        if (object instanceof JAXBElement) {
            jAXBContextNewInstance = JAXBContext.newInstance(new Class[]{((JAXBElement) object).getDeclaredType()});
        } else {
            jAXBContextNewInstance = JAXBContext.newInstance(new Class[]{object.getClass()});
        }
        Marshaller marshallerCreateMarshaller = jAXBContextNewInstance.createMarshaller();
        marshallerCreateMarshaller.setProperty("jaxb.formatted.output", true);
        return marshallerCreateMarshaller;
    }

    private static <T> JAXBElement<T> createInferredJAXBElement(T t) {
        Class<?> cls = t.getClass();
        return new JAXBElement<>(new QName(inferName(cls)), cls, t);
    }

    private static <T> JAXBElement<T> createJAXBElement(T t) {
        JAXBElement<T> jAXBElementCreateJaxbElementFromObjectFactory = createJaxbElementFromObjectFactory(t);
        return jAXBElementCreateJaxbElementFromObjectFactory == null ? createInferredJAXBElement(t) : jAXBElementCreateJaxbElementFromObjectFactory;
    }

    private static <T> JAXBElement<T> createJaxbElementFromObjectFactory(T t) {
        Object objInvoke;
        try {
            Class<?> objectFactoryClass = getObjectFactoryClass(t);
            Object objNewInstance = objectFactoryClass.newInstance();
            for (Method method : objectFactoryClass.getMethods()) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1 && parameterTypes[0] == t.getClass() && method.getReturnType().isAssignableFrom(JAXBElement.class)) {
                    objInvoke = method.invoke(objNewInstance, t);
                    return (JAXBElement) objInvoke;
                }
            }
            objInvoke = null;
            return (JAXBElement) objInvoke;
        } catch (Exception unused) {
            return null;
        }
    }

    private static <T> Class<?> getObjectFactoryClass(T t) throws ClassNotFoundException {
        return Thread.currentThread().getContextClassLoader().loadClass(t.getClass().getPackage().getName() + ".ObjectFactory");
    }

    private static String inferName(Class cls) {
        return Introspector.decapitalize(cls.getSimpleName());
    }
}
