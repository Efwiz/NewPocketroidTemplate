package java.lang.invoke;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;

/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-lambda-stubs.jar:java/lang/invoke/MethodHandles.class */
public class MethodHandles {

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-lambda-stubs.jar:java/lang/invoke/MethodHandles$Lookup.class */
    public static final class Lookup {
        public static final int PUBLIC = 0;
        public static final int PRIVATE = 0;
        public static final int PROTECTED = 0;
        public static final int PACKAGE = 0;

        public Class<?> lookupClass();

        public int lookupModes();

        public Lookup in(Class<?> requestedLookupClass);

        public MethodHandle findStatic(Class<?> refc, String name2, MethodType type) throws IllegalAccessException, NoSuchMethodException;

        public MethodHandle findVirtual(Class<?> refc, String name2, MethodType type) throws IllegalAccessException, NoSuchMethodException;

        public MethodHandle findConstructor(Class<?> refc, MethodType type) throws IllegalAccessException, NoSuchMethodException;

        public MethodHandle findSpecial(Class<?> refc, String name2, MethodType type, Class<?> specialCaller) throws IllegalAccessException, NoSuchMethodException;

        public MethodHandle findGetter(Class<?> refc, String name2, Class<?> type) throws IllegalAccessException, NoSuchFieldException;

        public MethodHandle findSetter(Class<?> refc, String name2, Class<?> type) throws IllegalAccessException, NoSuchFieldException;

        public MethodHandle findStaticGetter(Class<?> refc, String name2, Class<?> type) throws IllegalAccessException, NoSuchFieldException;

        public MethodHandle findStaticSetter(Class<?> refc, String name2, Class<?> type) throws IllegalAccessException, NoSuchFieldException;

        public MethodHandle bind(Object receiver, String name2, MethodType type) throws IllegalAccessException, NoSuchMethodException;

        public MethodHandle unreflect(Method m) throws IllegalAccessException;

        public MethodHandle unreflectSpecial(Method m, Class<?> specialCaller) throws IllegalAccessException;

        public MethodHandle unreflectConstructor(Constructor<?> c) throws IllegalAccessException;

        public MethodHandle unreflectGetter(Field f) throws IllegalAccessException;

        public MethodHandle unreflectSetter(Field f) throws IllegalAccessException;

        public MethodHandleInfo revealDirect(MethodHandle target);
    }

    public static Lookup lookup();

    public static Lookup publicLookup();

    public static <T extends Member> T reflectAs(Class<T> expected, MethodHandle target);

    public static MethodHandle arrayElementGetter(Class<?> arrayClass) throws IllegalArgumentException;

    public static MethodHandle arrayElementSetter(Class<?> arrayClass) throws IllegalArgumentException;

    public static MethodHandle spreadInvoker(MethodType type, int leadingArgCount);

    public static MethodHandle exactInvoker(MethodType type);

    public static MethodHandle invoker(MethodType type);

    public static MethodHandle explicitCastArguments(MethodHandle target, MethodType newType);

    public static MethodHandle permuteArguments(MethodHandle target, MethodType newType, int... reorder);

    public static MethodHandle constant(Class<?> type, Object value);

    public static MethodHandle identity(Class<?> type);

    public static MethodHandle insertArguments(MethodHandle target, int pos, Object... values);

    public static MethodHandle dropArguments(MethodHandle target, int pos, List<Class<?>> valueTypes);

    public static MethodHandle dropArguments(MethodHandle target, int pos, Class<?>... valueTypes);

    public static MethodHandle filterArguments(MethodHandle target, int pos, MethodHandle... filters);

    public static MethodHandle collectArguments(MethodHandle target, int pos, MethodHandle filter);

    public static MethodHandle filterReturnValue(MethodHandle target, MethodHandle filter);

    public static MethodHandle foldArguments(MethodHandle target, MethodHandle combiner);

    public static MethodHandle guardWithTest(MethodHandle test, MethodHandle target, MethodHandle fallback);

    public static MethodHandle catchException(MethodHandle target, Class<? extends Throwable> exType, MethodHandle handler);

    public static MethodHandle throwException(Class<?> returnType, Class<? extends Throwable> exType);
}
