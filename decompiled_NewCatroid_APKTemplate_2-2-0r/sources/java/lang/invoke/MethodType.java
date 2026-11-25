package java.lang.invoke;

import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-lambda-stubs.jar:java/lang/invoke/MethodType.class */
public final class MethodType implements Serializable {
    public static MethodType methodType(Class<?> rtype, Class<?>[] ptypes);

    public static MethodType methodType(Class<?> rtype, List<Class<?>> ptypes);

    public static MethodType methodType(Class<?> rtype, Class<?> ptype0, Class<?>... ptypes);

    public static MethodType methodType(Class<?> rtype);

    public static MethodType methodType(Class<?> rtype, Class<?> ptype0);

    public static MethodType methodType(Class<?> rtype, MethodType ptypes);

    public static MethodType genericMethodType(int objectArgCount, boolean finalArray);

    public static MethodType genericMethodType(int objectArgCount);

    public MethodType changeParameterType(int num, Class<?> nptype);

    public MethodType insertParameterTypes(int num, Class<?>... ptypesToInsert);

    public MethodType appendParameterTypes(Class<?>... ptypesToInsert);

    public MethodType insertParameterTypes(int num, List<Class<?>> ptypesToInsert);

    public MethodType appendParameterTypes(List<Class<?>> ptypesToInsert);

    public MethodType dropParameterTypes(int start, int end);

    public MethodType changeReturnType(Class<?> nrtype);

    public boolean hasPrimitives();

    public boolean hasWrappers();

    public MethodType erase();

    public MethodType generic();

    public MethodType wrap();

    public MethodType unwrap();

    public Class<?> parameterType(int num);

    public int parameterCount();

    public Class<?> returnType();

    public List<Class<?>> parameterList();

    public Class<?>[] parameterArray();

    public static MethodType fromMethodDescriptorString(String descriptor, ClassLoader loader) throws TypeNotPresentException, IllegalArgumentException;

    public String toMethodDescriptorString();
}
