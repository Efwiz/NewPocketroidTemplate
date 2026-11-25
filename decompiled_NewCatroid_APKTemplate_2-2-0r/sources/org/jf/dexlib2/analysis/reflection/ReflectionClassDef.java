package org.jf.dexlib2.analysis.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jf.dexlib2.analysis.reflection.util.ReflectionUtils;
import org.jf.dexlib2.base.reference.BaseTypeReference;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Field;
import org.jf.dexlib2.iface.Method;
import org.jf.util.collection.CombiningIterator;
import org.jf.util.collection.EmptySet;
import org.jf.util.collection.Iterables;
import org.jf.util.collection.ListUtil;

/* JADX INFO: loaded from: classes6.dex */
public class ReflectionClassDef extends BaseTypeReference implements ClassDef {
    private static final int DIRECT_MODIFIERS = 10;
    private final Class cls;

    @Override // org.jf.dexlib2.iface.ClassDef
    public String getSourceFile() {
        return null;
    }

    public ReflectionClassDef(Class cls) {
        this.cls = cls;
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public int getAccessFlags() {
        return this.cls.getModifiers();
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public String getSuperclass() {
        if (Modifier.isInterface(this.cls.getModifiers())) {
            return TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR;
        }
        Class superclass = this.cls.getSuperclass();
        if (superclass == null) {
            return null;
        }
        return ReflectionUtils.javaToDexName(superclass.getName());
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public List<String> getInterfaces() {
        return ListUtil.copyOf(Iterables.transform(Iterables.forArray(this.cls.getInterfaces()), new Function<Class, String>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.1
            @Override // java.util.function.Function
            public String apply(Class cls) {
                if (cls == null) {
                    return null;
                }
                return ReflectionUtils.javaToDexName(cls.getName());
            }
        }));
    }

    @Override // org.jf.dexlib2.iface.ClassDef, org.jf.dexlib2.iface.Annotatable
    public Set<? extends Annotation> getAnnotations() {
        return EmptySet.of();
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Iterable<? extends Field> getStaticFields() {
        return new Iterable<Field>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.2
            @Override // java.lang.Iterable
            public Iterator<Field> iterator() {
                return Iterables.transform(Iterables.filter(Iterables.forArray(ReflectionClassDef.this.cls.getDeclaredFields()), new Predicate<java.lang.reflect.Field>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.2.1
                    @Override // java.util.function.Predicate
                    public boolean test(java.lang.reflect.Field field) {
                        return field != null && Modifier.isStatic(field.getModifiers());
                    }
                }), new Function<java.lang.reflect.Field, Field>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.2.2
                    @Override // java.util.function.Function
                    public Field apply(java.lang.reflect.Field field) {
                        return new ReflectionField(field);
                    }
                });
            }
        };
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Iterable<? extends Field> getInstanceFields() {
        return new Iterable<Field>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.3
            @Override // java.lang.Iterable
            public Iterator<Field> iterator() {
                return Iterables.transform(Iterables.filter(Iterables.forArray(ReflectionClassDef.this.cls.getDeclaredFields()), new Predicate<java.lang.reflect.Field>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.3.1
                    @Override // java.util.function.Predicate
                    public boolean test(java.lang.reflect.Field field) {
                        return (field == null || Modifier.isStatic(field.getModifiers())) ? false : true;
                    }
                }), new Function<java.lang.reflect.Field, Field>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.3.2
                    @Override // java.util.function.Function
                    public Field apply(java.lang.reflect.Field field) {
                        return new ReflectionField(field);
                    }
                });
            }
        };
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<? extends Field> getFields() {
        return new AbstractSet<Field>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.4
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Field> iterator() {
                return Iterables.transform(Iterables.forArray(ReflectionClassDef.this.cls.getDeclaredFields()), new Function<java.lang.reflect.Field, Field>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.4.1
                    @Override // java.util.function.Function
                    public Field apply(java.lang.reflect.Field field) {
                        return new ReflectionField(field);
                    }
                });
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return ReflectionClassDef.this.cls.getDeclaredFields().length;
            }
        };
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Iterable<? extends Method> getDirectMethods() {
        return new Iterable<Method>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.5
            @Override // java.lang.Iterable
            public Iterator<Method> iterator() {
                return new CombiningIterator(Iterables.transform(Iterables.forArray(ReflectionClassDef.this.cls.getDeclaredConstructors()), new Function<Constructor, Method>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.5.1
                    @Override // java.util.function.Function
                    public Method apply(Constructor constructor) {
                        return new ReflectionConstructor(constructor);
                    }
                }), Iterables.transform(Iterables.filter(Iterables.forArray(ReflectionClassDef.this.cls.getDeclaredMethods()), new Predicate<java.lang.reflect.Method>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.5.2
                    @Override // java.util.function.Predicate
                    public boolean test(java.lang.reflect.Method method) {
                        return (method == null || (method.getModifiers() & 10) == 0) ? false : true;
                    }
                }), new Function<java.lang.reflect.Method, Method>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.5.3
                    @Override // java.util.function.Function
                    public Method apply(java.lang.reflect.Method method) {
                        return new ReflectionMethod(method);
                    }
                }));
            }
        };
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Iterable<? extends Method> getVirtualMethods() {
        return new Iterable<Method>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.6
            @Override // java.lang.Iterable
            public Iterator<Method> iterator() {
                return Iterables.transform(Iterables.filter(Iterables.forArray(ReflectionClassDef.this.cls.getDeclaredMethods()), new Predicate<java.lang.reflect.Method>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.6.1
                    @Override // java.util.function.Predicate
                    public boolean test(java.lang.reflect.Method method) {
                        return method != null && (method.getModifiers() & 10) == 0;
                    }
                }), new Function<java.lang.reflect.Method, Method>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.6.2
                    @Override // java.util.function.Function
                    public Method apply(java.lang.reflect.Method method) {
                        return new ReflectionMethod(method);
                    }
                });
            }
        };
    }

    @Override // org.jf.dexlib2.iface.ClassDef
    public Set<? extends Method> getMethods() {
        return new AbstractSet<Method>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.7
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<Method> iterator() {
                return new CombiningIterator(Iterables.transform(Iterables.forArray(ReflectionClassDef.this.cls.getDeclaredConstructors()), new Function<Constructor, Method>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.7.1
                    @Override // java.util.function.Function
                    public Method apply(Constructor constructor) {
                        return new ReflectionConstructor(constructor);
                    }
                }), Iterables.transform(Iterables.forArray(ReflectionClassDef.this.cls.getDeclaredMethods()), new Function<java.lang.reflect.Method, Method>() { // from class: org.jf.dexlib2.analysis.reflection.ReflectionClassDef.7.2
                    @Override // java.util.function.Function
                    public Method apply(java.lang.reflect.Method method) {
                        return new ReflectionMethod(method);
                    }
                }));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return ReflectionClassDef.this.cls.getDeclaredMethods().length + ReflectionClassDef.this.cls.getDeclaredConstructors().length;
            }
        };
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference, org.jf.dexlib2.iface.ClassDef
    public String getType() {
        return ReflectionUtils.javaToDexName(this.cls.getName());
    }
}
