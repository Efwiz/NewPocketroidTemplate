package com.opencsv.bean;

import com.opencsv.bean.function.AccessorInvoker;
import com.opencsv.bean.function.AssignmentInvoker;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.commons.lang3.reflect.FieldUtils;

/* JADX INFO: loaded from: classes10.dex */
public class FieldAccess<T> {
    private final AccessorInvoker<Object, T> accessor = determineAccessorMethod();
    private final AssignmentInvoker<Object, T> assignment = determineAssignmentMethod();
    private final Field field;

    public FieldAccess(Field field) {
        this.field = field;
    }

    private AccessorInvoker<Object, T> determineAccessorMethod() {
        try {
            final Method method = this.field.getDeclaringClass().getMethod("get" + Character.toUpperCase(this.field.getName().charAt(0)) + this.field.getName().substring(1), new Class[0]);
            return new AccessorInvoker() { // from class: com.opencsv.bean.FieldAccess$$ExternalSyntheticLambda2
                @Override // com.opencsv.bean.function.AccessorInvoker
                public final Object invoke(Object obj) {
                    return method.invoke(obj, new Object[0]);
                }
            };
        } catch (NoSuchMethodException unused) {
            return new AccessorInvoker() { // from class: com.opencsv.bean.FieldAccess$$ExternalSyntheticLambda3
                @Override // com.opencsv.bean.function.AccessorInvoker
                public final Object invoke(Object obj) {
                    return this.f$0.m2748lambda$determineAccessorMethod$1$comopencsvbeanFieldAccess(obj);
                }
            };
        }
    }

    /* JADX INFO: renamed from: lambda$determineAccessorMethod$1$com-opencsv-bean-FieldAccess, reason: not valid java name */
    /* synthetic */ Object m2748lambda$determineAccessorMethod$1$comopencsvbeanFieldAccess(Object obj) throws IllegalAccessException, InvocationTargetException {
        return FieldUtils.readField(this.field, obj, true);
    }

    private AssignmentInvoker<Object, T> determineAssignmentMethod() {
        try {
            final Method method = this.field.getDeclaringClass().getMethod(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX + Character.toUpperCase(this.field.getName().charAt(0)) + this.field.getName().substring(1), this.field.getType());
            method.getClass();
            return new AssignmentInvoker() { // from class: com.opencsv.bean.FieldAccess$$ExternalSyntheticLambda0
                @Override // com.opencsv.bean.function.AssignmentInvoker
                public final void invoke(Object obj, Object obj2) throws IllegalAccessException, InvocationTargetException {
                    method.invoke(obj, obj2);
                }
            };
        } catch (NoSuchMethodException unused) {
            return new AssignmentInvoker() { // from class: com.opencsv.bean.FieldAccess$$ExternalSyntheticLambda1
                @Override // com.opencsv.bean.function.AssignmentInvoker
                public final void invoke(Object obj, Object obj2) throws IllegalAccessException, InvocationTargetException {
                    this.f$0.m2749lambda$determineAssignmentMethod$3$comopencsvbeanFieldAccess(obj, obj2);
                }
            };
        }
    }

    /* JADX INFO: renamed from: lambda$determineAssignmentMethod$3$com-opencsv-bean-FieldAccess, reason: not valid java name */
    /* synthetic */ void m2749lambda$determineAssignmentMethod$3$comopencsvbeanFieldAccess(Object obj, Object obj2) throws IllegalAccessException, InvocationTargetException {
        FieldUtils.writeField(this.field, obj, obj2, true);
    }

    public T getField(Object obj) throws IllegalAccessException, InvocationTargetException {
        return this.accessor.invoke(obj);
    }

    public void setField(Object obj, T t) throws IllegalAccessException, InvocationTargetException {
        this.assignment.invoke(obj, t);
    }

    public int hashCode() {
        return this.field.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof FieldAccess) {
            return this.field.equals(((FieldAccess) obj).field);
        }
        return false;
    }

    public String toString() {
        return this.field.toString();
    }
}
