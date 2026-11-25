package net.bytebuddy.utility.dispatcher;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.build.AccessControllerPlugin;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.dynamic.scaffold.TypeWriter;
import net.bytebuddy.jar.asm.ClassWriter;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Type;
import net.bytebuddy.utility.GraalImageCode;
import net.bytebuddy.utility.Invoker;
import net.bytebuddy.utility.MethodComparator;
import net.bytebuddy.utility.nullability.MaybeNull;
import net.bytebuddy.utility.privilege.GetSystemPropertyAction;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class JavaDispatcher<T> implements PrivilegedAction<T> {
    private static final boolean ACCESS_CONTROLLER;
    private static final boolean GENERATE;
    public static final String GENERATE_PROPERTY = "net.bytebuddy.generate";
    private static final Invoker INVOKER;
    private static final DynamicClassLoader.Resolver RESOLVER;

    @MaybeNull
    @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
    private final ClassLoader classLoader;
    private final boolean generate;
    private final Class<T> proxy;

    @Target({ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Container {
    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Defaults {
    }

    @Target({ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Instance {
    }

    @Target({ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface IsConstructor {
    }

    @Target({ElementType.METHOD})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface IsStatic {
    }

    @Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Proxied {
        String value();
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0039 A[RETURN] */
    public boolean equals(@MaybeNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        JavaDispatcher javaDispatcher = (JavaDispatcher) obj;
        if (this.generate != javaDispatcher.generate || !this.proxy.equals(javaDispatcher.proxy)) {
            return false;
        }
        ClassLoader classLoader = this.classLoader;
        ClassLoader classLoader2 = javaDispatcher.classLoader;
        if (classLoader2 == null) {
            if (classLoader != null) {
                return false;
            }
            return true;
        }
        if (classLoader == null || !classLoader.equals(classLoader2)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int iHashCode = ((getClass().hashCode() * 31) + this.proxy.hashCode()) * 31;
        ClassLoader classLoader = this.classLoader;
        if (classLoader != null) {
            iHashCode += classLoader.hashCode();
        }
        return (iHashCode * 31) + (this.generate ? 1 : 0);
    }

    static {
        boolean z = false;
        try {
            Class.forName("java.security.AccessController", false, null);
            ACCESS_CONTROLLER = Boolean.parseBoolean(System.getProperty("net.bytebuddy.securitymanager", "true"));
        } catch (ClassNotFoundException unused) {
            ACCESS_CONTROLLER = z;
        } catch (SecurityException unused2) {
            z = true;
            ACCESS_CONTROLLER = z;
        }
        GENERATE = Boolean.parseBoolean((String) doPrivileged(new GetSystemPropertyAction(GENERATE_PROPERTY)));
        RESOLVER = (DynamicClassLoader.Resolver) doPrivileged(DynamicClassLoader.Resolver.CreationAction.INSTANCE);
        INVOKER = (Invoker) doPrivileged(new InvokerCreationAction());
    }

    protected JavaDispatcher(Class<T> cls, @MaybeNull ClassLoader classLoader, boolean z) {
        this.proxy = cls;
        this.classLoader = classLoader;
        this.generate = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @AccessControllerPlugin.Enhance
    public static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
        return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
    }

    public static <T> PrivilegedAction<T> of(Class<T> cls) {
        return of(cls, null);
    }

    protected static <T> PrivilegedAction<T> of(Class<T> cls, @MaybeNull ClassLoader classLoader) {
        return of(cls, classLoader, GENERATE);
    }

    protected static <T> PrivilegedAction<T> of(Class<T> cls, @MaybeNull ClassLoader classLoader, boolean z) {
        if (!cls.isInterface()) {
            throw new IllegalArgumentException("Expected an interface instead of " + cls);
        }
        if (!cls.isAnnotationPresent(Proxied.class)) {
            throw new IllegalArgumentException("Expected " + cls.getName() + " to be annotated with " + Proxied.class.getName());
        }
        if (((Proxied) cls.getAnnotation(Proxied.class)).value().startsWith("java.security.")) {
            throw new IllegalArgumentException("Classes related to Java security cannot be proxied: " + cls.getName());
        }
        return new JavaDispatcher(cls, classLoader, z);
    }

    /* JADX WARN: Code duplicated, block: B:228:0x04eb A[Catch: all -> 0x0579, NoSuchMethodException -> 0x057e, ClassNotFoundException -> 0x0583, TryCatch #23 {ClassNotFoundException -> 0x0583, NoSuchMethodException -> 0x057e, all -> 0x0579, blocks: (B:131:0x0290, B:133:0x029a, B:134:0x02a1, B:136:0x02a5, B:137:0x02ac, B:170:0x03bd, B:138:0x02d1, B:139:0x02ff, B:152:0x0318, B:153:0x033e, B:154:0x033f, B:156:0x035a, B:157:0x035d, B:158:0x0389, B:159:0x038a, B:171:0x03cd, B:174:0x03e1, B:176:0x03ef, B:178:0x03f7, B:182:0x0402, B:192:0x0419, B:193:0x043f, B:194:0x0440, B:196:0x044a, B:198:0x0453, B:200:0x0465, B:204:0x046f, B:206:0x0479, B:209:0x0482, B:211:0x048a, B:218:0x04c1, B:214:0x0495, B:215:0x049a, B:216:0x04be, B:219:0x04c6, B:221:0x04ce, B:225:0x04dc, B:226:0x04e1, B:228:0x04eb, B:230:0x04f3, B:231:0x04fc, B:232:0x051c, B:233:0x051d, B:235:0x0525, B:237:0x0531, B:238:0x0551, B:239:0x0552, B:240:0x0578, B:197:0x044f), top: B:330:0x0290 }] */
    /* JADX WARN: Code duplicated, block: B:230:0x04f3 A[Catch: all -> 0x0579, NoSuchMethodException -> 0x057e, ClassNotFoundException -> 0x0583, TryCatch #23 {ClassNotFoundException -> 0x0583, NoSuchMethodException -> 0x057e, all -> 0x0579, blocks: (B:131:0x0290, B:133:0x029a, B:134:0x02a1, B:136:0x02a5, B:137:0x02ac, B:170:0x03bd, B:138:0x02d1, B:139:0x02ff, B:152:0x0318, B:153:0x033e, B:154:0x033f, B:156:0x035a, B:157:0x035d, B:158:0x0389, B:159:0x038a, B:171:0x03cd, B:174:0x03e1, B:176:0x03ef, B:178:0x03f7, B:182:0x0402, B:192:0x0419, B:193:0x043f, B:194:0x0440, B:196:0x044a, B:198:0x0453, B:200:0x0465, B:204:0x046f, B:206:0x0479, B:209:0x0482, B:211:0x048a, B:218:0x04c1, B:214:0x0495, B:215:0x049a, B:216:0x04be, B:219:0x04c6, B:221:0x04ce, B:225:0x04dc, B:226:0x04e1, B:228:0x04eb, B:230:0x04f3, B:231:0x04fc, B:232:0x051c, B:233:0x051d, B:235:0x0525, B:237:0x0531, B:238:0x0551, B:239:0x0552, B:240:0x0578, B:197:0x044f), top: B:330:0x0290 }] */
    /* JADX WARN: Code duplicated, block: B:233:0x051d A[Catch: all -> 0x0579, NoSuchMethodException -> 0x057e, ClassNotFoundException -> 0x0583, TryCatch #23 {ClassNotFoundException -> 0x0583, NoSuchMethodException -> 0x057e, all -> 0x0579, blocks: (B:131:0x0290, B:133:0x029a, B:134:0x02a1, B:136:0x02a5, B:137:0x02ac, B:170:0x03bd, B:138:0x02d1, B:139:0x02ff, B:152:0x0318, B:153:0x033e, B:154:0x033f, B:156:0x035a, B:157:0x035d, B:158:0x0389, B:159:0x038a, B:171:0x03cd, B:174:0x03e1, B:176:0x03ef, B:178:0x03f7, B:182:0x0402, B:192:0x0419, B:193:0x043f, B:194:0x0440, B:196:0x044a, B:198:0x0453, B:200:0x0465, B:204:0x046f, B:206:0x0479, B:209:0x0482, B:211:0x048a, B:218:0x04c1, B:214:0x0495, B:215:0x049a, B:216:0x04be, B:219:0x04c6, B:221:0x04ce, B:225:0x04dc, B:226:0x04e1, B:228:0x04eb, B:230:0x04f3, B:231:0x04fc, B:232:0x051c, B:233:0x051d, B:235:0x0525, B:237:0x0531, B:238:0x0551, B:239:0x0552, B:240:0x0578, B:197:0x044f), top: B:330:0x0290 }] */
    /* JADX WARN: Code duplicated, block: B:235:0x0525 A[Catch: all -> 0x0579, NoSuchMethodException -> 0x057e, ClassNotFoundException -> 0x0583, TryCatch #23 {ClassNotFoundException -> 0x0583, NoSuchMethodException -> 0x057e, all -> 0x0579, blocks: (B:131:0x0290, B:133:0x029a, B:134:0x02a1, B:136:0x02a5, B:137:0x02ac, B:170:0x03bd, B:138:0x02d1, B:139:0x02ff, B:152:0x0318, B:153:0x033e, B:154:0x033f, B:156:0x035a, B:157:0x035d, B:158:0x0389, B:159:0x038a, B:171:0x03cd, B:174:0x03e1, B:176:0x03ef, B:178:0x03f7, B:182:0x0402, B:192:0x0419, B:193:0x043f, B:194:0x0440, B:196:0x044a, B:198:0x0453, B:200:0x0465, B:204:0x046f, B:206:0x0479, B:209:0x0482, B:211:0x048a, B:218:0x04c1, B:214:0x0495, B:215:0x049a, B:216:0x04be, B:219:0x04c6, B:221:0x04ce, B:225:0x04dc, B:226:0x04e1, B:228:0x04eb, B:230:0x04f3, B:231:0x04fc, B:232:0x051c, B:233:0x051d, B:235:0x0525, B:237:0x0531, B:238:0x0551, B:239:0x0552, B:240:0x0578, B:197:0x044f), top: B:330:0x0290 }] */
    /* JADX WARN: Code duplicated, block: B:263:0x05d9  */
    /* JADX WARN: Code duplicated, block: B:267:0x05fb  */
    /* JADX WARN: Code duplicated, block: B:274:0x0615  */
    /* JADX WARN: Code duplicated, block: B:278:0x0637  */
    /* JADX WARN: Code duplicated, block: B:367:0x04fc A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:368:0x0531 A[SYNTHETIC] */
    @Override // java.security.PrivilegedAction
    public T run() {
        Method[] methods;
        Method[] methods2;
        String str;
        String str2;
        Method[] methodArr;
        int i;
        Throwable th;
        NoSuchMethodException noSuchMethodException;
        Object objOf;
        ClassNotFoundException classNotFoundException;
        Object objOf2;
        int i2;
        boolean z;
        Class<?>[] clsArr;
        boolean z2;
        Annotation[][] annotationArr;
        String str3 = "Instance check requires a boolean return type: ";
        String str4 = "Instance check requires a single regular-typed argument: ";
        int i3 = 1;
        int i4 = 0;
        try {
            Object objInvoke = System.class.getMethod("getSecurityManager", new Class[0]).invoke(null, new Object[0]);
            if (objInvoke != null) {
                Class.forName("java.lang.SecurityManager").getMethod("checkPermission", Permission.class).invoke(objInvoke, new RuntimePermission("net.bytebuddy.createJavaDispatcher"));
            }
        } catch (ClassNotFoundException | NoSuchMethodException unused) {
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to access security manager", e);
        } catch (InvocationTargetException e2) {
            Throwable targetException = e2.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw ((RuntimeException) targetException);
            }
            throw new IllegalStateException("Failed to assert access rights using security manager", targetException);
        }
        Map linkedHashMap = this.generate ? new LinkedHashMap() : new HashMap();
        boolean zIsAnnotationPresent = this.proxy.isAnnotationPresent(Defaults.class);
        String strValue = ((Proxied) this.proxy.getAnnotation(Proxied.class)).value();
        try {
            Class<?> cls = Class.forName(strValue, false, this.classLoader);
            boolean z3 = this.generate;
            if (z3) {
                methods2 = (Method[]) GraalImageCode.getCurrent().sorted(this.proxy.getMethods(), MethodComparator.INSTANCE);
            } else {
                methods2 = this.proxy.getMethods();
            }
            int length = methods2.length;
            int i5 = 0;
            while (i5 < length) {
                Method method = methods2[i5];
                if (method.getDeclaringClass() != Object.class) {
                    if (method.isAnnotationPresent(Instance.class)) {
                        if (method.getParameterTypes().length != i3 || !method.getParameterTypes()[i4].isAssignableFrom(cls)) {
                            throw new IllegalStateException(str4 + method);
                        }
                        if (method.getReturnType() != Boolean.TYPE) {
                            throw new IllegalStateException(str3 + method);
                        }
                        linkedHashMap.put(method, new Dispatcher.ForInstanceCheck(cls));
                    } else if (method.isAnnotationPresent(Container.class)) {
                        if (method.getParameterTypes().length != i3 || method.getParameterTypes()[i4] != Integer.TYPE) {
                            throw new IllegalStateException("Container creation requires a single int-typed argument: " + method);
                        }
                        if (!method.getReturnType().isArray() || !method.getReturnType().getComponentType().isAssignableFrom(cls)) {
                            throw new IllegalStateException("Container creation requires an assignable array as return value: " + method);
                        }
                        linkedHashMap.put(method, new Dispatcher.ForContainerCreation(cls));
                    } else {
                        if (cls.getName().equals("java.lang.invoke.MethodHandles") && method.getName().equals("lookup")) {
                            throw new UnsupportedOperationException("Cannot resolve Byte Buddy lookup via dispatcher");
                        }
                        try {
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            if (method.isAnnotationPresent(IsStatic.class)) {
                                methodArr = methods2;
                                i2 = i4;
                            } else {
                                try {
                                    if (method.isAnnotationPresent(IsConstructor.class)) {
                                        methodArr = methods2;
                                        i2 = i4;
                                    } else {
                                        if (parameterTypes.length == 0) {
                                            throw new IllegalStateException("Expected self type: " + method);
                                        }
                                        if (!parameterTypes[i4].isAssignableFrom(cls)) {
                                            throw new IllegalStateException("Cannot assign self type: " + cls + " on " + method);
                                        }
                                        int length2 = parameterTypes.length - i3;
                                        methodArr = methods2;
                                        try {
                                            Class<?>[] clsArr2 = new Class[length2];
                                            System.arraycopy(parameterTypes, i3, clsArr2, i4, length2);
                                            parameterTypes = clsArr2;
                                            i2 = i3;
                                        } catch (ClassNotFoundException e3) {
                                            e = e3;
                                            str = str3;
                                            str2 = str4;
                                        } catch (NoSuchMethodException e4) {
                                            e = e4;
                                            str = str3;
                                            str2 = str4;
                                            i = length;
                                            noSuchMethodException = e;
                                            if (!zIsAnnotationPresent) {
                                                objOf = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                            } else {
                                                objOf = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                            }
                                            linkedHashMap.put(method, objOf);
                                            i5++;
                                            methods2 = methodArr;
                                            length = i;
                                            str4 = str2;
                                            str3 = str;
                                            i3 = 1;
                                            i4 = 0;
                                        } catch (Throwable th2) {
                                            th = th2;
                                            str = str3;
                                            str2 = str4;
                                            i = length;
                                            th = th;
                                            linkedHashMap.put(method, new Dispatcher.ForUnresolvedMethod("Unexpected error: " + th.getMessage()));
                                            i5++;
                                            methods2 = methodArr;
                                            length = i;
                                            str4 = str2;
                                            str3 = str;
                                            i3 = 1;
                                            i4 = 0;
                                        }
                                        str = str3;
                                        str2 = str4;
                                        i = length;
                                        classNotFoundException = e;
                                        if (!zIsAnnotationPresent || method.isAnnotationPresent(Defaults.class)) {
                                            objOf2 = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                        } else {
                                            objOf2 = new Dispatcher.ForUnresolvedMethod("Class not available on current VM: " + classNotFoundException.getMessage());
                                        }
                                        linkedHashMap.put(method, objOf2);
                                    }
                                } catch (ClassNotFoundException e5) {
                                    e = e5;
                                    methodArr = methods2;
                                } catch (NoSuchMethodException e6) {
                                    e = e6;
                                    methodArr = methods2;
                                } catch (Throwable th3) {
                                    th = th3;
                                    methodArr = methods2;
                                }
                            }
                            try {
                                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                                int i6 = i4;
                                while (i6 < parameterTypes.length) {
                                    Annotation[] annotationArr2 = parameterAnnotations[i6 + i2];
                                    int i7 = i2;
                                    int length3 = annotationArr2.length;
                                    i = length;
                                    int i8 = 0;
                                    while (true) {
                                        if (i8 >= length3) {
                                            str = str3;
                                            str2 = str4;
                                            z2 = z3;
                                            annotationArr = parameterAnnotations;
                                            break;
                                        }
                                        int i9 = length3;
                                        try {
                                            Annotation annotation = annotationArr2[i8];
                                            Annotation[] annotationArr3 = annotationArr2;
                                            if (annotation instanceof Proxied) {
                                                int i10 = 0;
                                                while (parameterTypes[i6].isArray()) {
                                                    i10++;
                                                    try {
                                                        parameterTypes[i6] = parameterTypes[i6].getComponentType();
                                                    } catch (ClassNotFoundException e7) {
                                                        e = e7;
                                                        str = str3;
                                                        str2 = str4;
                                                    } catch (NoSuchMethodException e8) {
                                                        e = e8;
                                                        str = str3;
                                                        str2 = str4;
                                                        noSuchMethodException = e;
                                                        if (!zIsAnnotationPresent) {
                                                            objOf = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                                        } else {
                                                            objOf = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                                        }
                                                        linkedHashMap.put(method, objOf);
                                                        i5++;
                                                        methods2 = methodArr;
                                                        length = i;
                                                        str4 = str2;
                                                        str3 = str;
                                                        i3 = 1;
                                                        i4 = 0;
                                                    } catch (Throwable th4) {
                                                        th = th4;
                                                        str = str3;
                                                        str2 = str4;
                                                        th = th;
                                                        linkedHashMap.put(method, new Dispatcher.ForUnresolvedMethod("Unexpected error: " + th.getMessage()));
                                                        i5++;
                                                        methods2 = methodArr;
                                                        length = i;
                                                        str4 = str2;
                                                        str3 = str;
                                                        i3 = 1;
                                                        i4 = 0;
                                                    }
                                                }
                                                annotationArr = parameterAnnotations;
                                                if (i10 > 0) {
                                                    if (parameterTypes[i6].isPrimitive()) {
                                                        throw new IllegalStateException("Primitive values are not supposed to be proxied: " + i6 + " of " + method);
                                                    }
                                                    int i11 = i10;
                                                    str2 = str4;
                                                    try {
                                                        str = str3;
                                                        try {
                                                            z2 = z3;
                                                            try {
                                                                if (!parameterTypes[i6].isAssignableFrom(Class.forName(((Proxied) annotation).value(), false, this.classLoader))) {
                                                                    throw new IllegalStateException("Cannot resolve to component type: " + ((Proxied) annotation).value() + " at " + i6 + " of " + method);
                                                                }
                                                                StringBuilder sb = new StringBuilder();
                                                                int i12 = i11;
                                                                while (true) {
                                                                    int i13 = i12 - 1;
                                                                    if (i12 <= 0) {
                                                                        break;
                                                                    }
                                                                    sb.append('[');
                                                                    i12 = i13;
                                                                }
                                                                parameterTypes[i6] = Class.forName(sb.append('L').append(((Proxied) annotation).value()).append(';').toString(), false, this.classLoader);
                                                                break;
                                                            } catch (ClassNotFoundException e9) {
                                                                classNotFoundException = e9;
                                                                z3 = z2;
                                                            } catch (NoSuchMethodException e10) {
                                                                noSuchMethodException = e10;
                                                                z3 = z2;
                                                                if (!zIsAnnotationPresent || method.isAnnotationPresent(Defaults.class)) {
                                                                    objOf = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                                                } else {
                                                                    objOf = new Dispatcher.ForUnresolvedMethod("Method not available on current VM: " + noSuchMethodException.getMessage());
                                                                }
                                                                linkedHashMap.put(method, objOf);
                                                                i5++;
                                                                methods2 = methodArr;
                                                                length = i;
                                                                str4 = str2;
                                                                str3 = str;
                                                                i3 = 1;
                                                                i4 = 0;
                                                            } catch (Throwable th5) {
                                                                th = th5;
                                                                z3 = z2;
                                                                linkedHashMap.put(method, new Dispatcher.ForUnresolvedMethod("Unexpected error: " + th.getMessage()));
                                                                i5++;
                                                                methods2 = methodArr;
                                                                length = i;
                                                                str4 = str2;
                                                                str3 = str;
                                                                i3 = 1;
                                                                i4 = 0;
                                                            }
                                                        } catch (ClassNotFoundException e11) {
                                                            e = e11;
                                                            classNotFoundException = e;
                                                        } catch (NoSuchMethodException e12) {
                                                            e = e12;
                                                            noSuchMethodException = e;
                                                            if (!zIsAnnotationPresent) {
                                                                objOf = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                                            } else {
                                                                objOf = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                                            }
                                                            linkedHashMap.put(method, objOf);
                                                            i5++;
                                                            methods2 = methodArr;
                                                            length = i;
                                                            str4 = str2;
                                                            str3 = str;
                                                            i3 = 1;
                                                            i4 = 0;
                                                        } catch (Throwable th6) {
                                                            th = th6;
                                                            th = th;
                                                            linkedHashMap.put(method, new Dispatcher.ForUnresolvedMethod("Unexpected error: " + th.getMessage()));
                                                            i5++;
                                                            methods2 = methodArr;
                                                            length = i;
                                                            str4 = str2;
                                                            str3 = str;
                                                            i3 = 1;
                                                            i4 = 0;
                                                        }
                                                    } catch (ClassNotFoundException e13) {
                                                        e = e13;
                                                        str = str3;
                                                    } catch (NoSuchMethodException e14) {
                                                        e = e14;
                                                        str = str3;
                                                    } catch (Throwable th7) {
                                                        th = th7;
                                                        str = str3;
                                                    }
                                                } else {
                                                    str = str3;
                                                    str2 = str4;
                                                    z2 = z3;
                                                    Class<?> cls2 = Class.forName(((Proxied) annotation).value(), false, this.classLoader);
                                                    if (!parameterTypes[i6].isAssignableFrom(cls2)) {
                                                        throw new IllegalStateException("Cannot resolve to type: " + cls2.getName() + " at " + i6 + " of " + method);
                                                    }
                                                    parameterTypes[i6] = cls2;
                                                    break;
                                                }
                                            } else {
                                                i8++;
                                                length3 = i9;
                                                annotationArr2 = annotationArr3;
                                            }
                                        } catch (ClassNotFoundException e15) {
                                            e = e15;
                                            str = str3;
                                            str2 = str4;
                                        } catch (NoSuchMethodException e16) {
                                            e = e16;
                                            str = str3;
                                            str2 = str4;
                                        } catch (Throwable th8) {
                                            th = th8;
                                            str = str3;
                                            str2 = str4;
                                        }
                                        classNotFoundException = e;
                                        if (!zIsAnnotationPresent) {
                                            objOf2 = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                        } else {
                                            objOf2 = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                        }
                                        linkedHashMap.put(method, objOf2);
                                    }
                                    i6++;
                                    i2 = i7;
                                    length = i;
                                    parameterAnnotations = annotationArr;
                                    str4 = str2;
                                    str3 = str;
                                    z3 = z2;
                                }
                                str = str3;
                                str2 = str4;
                                boolean z4 = z3;
                                i = length;
                                if (method.isAnnotationPresent(IsConstructor.class)) {
                                    Constructor<?> constructor = cls.getConstructor(parameterTypes);
                                    if (!method.getReturnType().isAssignableFrom(cls)) {
                                        throw new IllegalStateException("Cannot assign " + constructor.getDeclaringClass().getName() + " to " + method);
                                    }
                                    if ((constructor.getModifiers() & 1) == 0 || (cls.getModifiers() & 1) == 0) {
                                        constructor.setAccessible(true);
                                        z3 = false;
                                    } else {
                                        z3 = z4;
                                    }
                                    try {
                                        linkedHashMap.put(method, new Dispatcher.ForConstructor(constructor));
                                    } catch (ClassNotFoundException e17) {
                                        e = e17;
                                        classNotFoundException = e;
                                        if (!zIsAnnotationPresent) {
                                            objOf2 = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                        } else {
                                            objOf2 = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                        }
                                        linkedHashMap.put(method, objOf2);
                                    } catch (NoSuchMethodException e18) {
                                        e = e18;
                                        noSuchMethodException = e;
                                        if (!zIsAnnotationPresent) {
                                            objOf = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                        } else {
                                            objOf = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                        }
                                        linkedHashMap.put(method, objOf);
                                    } catch (Throwable th9) {
                                        th = th9;
                                        th = th;
                                        linkedHashMap.put(method, new Dispatcher.ForUnresolvedMethod("Unexpected error: " + th.getMessage()));
                                    }
                                } else {
                                    Proxied proxied = (Proxied) method.getAnnotation(Proxied.class);
                                    Method method2 = cls.getMethod(proxied == null ? method.getName() : proxied.value(), parameterTypes);
                                    if (!method.getReturnType().isAssignableFrom(method2.getReturnType())) {
                                        throw new IllegalStateException("Cannot assign " + method2.getReturnType().getName() + " to " + method);
                                    }
                                    Class<?>[] exceptionTypes = method2.getExceptionTypes();
                                    int length4 = exceptionTypes.length;
                                    int i14 = 0;
                                    while (i14 < length4) {
                                        Class<?> cls3 = exceptionTypes[i14];
                                        if (RuntimeException.class.isAssignableFrom(cls3) || Error.class.isAssignableFrom(cls3)) {
                                            clsArr = exceptionTypes;
                                        } else {
                                            Class<?>[] exceptionTypes2 = method.getExceptionTypes();
                                            int length5 = exceptionTypes2.length;
                                            int i15 = 0;
                                            while (true) {
                                                if (i15 < length5) {
                                                    clsArr = exceptionTypes;
                                                    if (exceptionTypes2[i15].isAssignableFrom(cls3)) {
                                                        break;
                                                    }
                                                    i15++;
                                                    exceptionTypes = clsArr;
                                                } else {
                                                    throw new IllegalStateException("Resolved method for " + method + " throws undeclared checked exception " + cls3.getName());
                                                }
                                            }
                                        }
                                        i14++;
                                        exceptionTypes = clsArr;
                                    }
                                    if ((method2.getModifiers() & 1) != 0) {
                                        if ((method2.getDeclaringClass().getModifiers() & 1) == 0) {
                                            z = true;
                                        }
                                        if (Modifier.isStatic(method2.getModifiers())) {
                                            if (method.isAnnotationPresent(IsStatic.class)) {
                                                throw new IllegalStateException("Resolved method for " + method + " was expected to be static: " + method2);
                                            }
                                            linkedHashMap.put(method, new Dispatcher.ForStaticMethod(method2));
                                        } else {
                                            if (!method.isAnnotationPresent(IsStatic.class)) {
                                                throw new IllegalStateException("Resolved method for " + method + " was expected to be virtual: " + method2);
                                            }
                                            linkedHashMap.put(method, new Dispatcher.ForNonStaticMethod(method2));
                                        }
                                        z3 = z4;
                                    } else {
                                        z = true;
                                    }
                                    method2.setAccessible(z);
                                    z4 = false;
                                    if (Modifier.isStatic(method2.getModifiers())) {
                                        if (method.isAnnotationPresent(IsStatic.class)) {
                                            throw new IllegalStateException("Resolved method for " + method + " was expected to be static: " + method2);
                                        }
                                        linkedHashMap.put(method, new Dispatcher.ForStaticMethod(method2));
                                    } else {
                                        if (!method.isAnnotationPresent(IsStatic.class)) {
                                            throw new IllegalStateException("Resolved method for " + method + " was expected to be virtual: " + method2);
                                        }
                                        linkedHashMap.put(method, new Dispatcher.ForNonStaticMethod(method2));
                                    }
                                    z3 = z4;
                                }
                            } catch (ClassNotFoundException e19) {
                                e = e19;
                                str = str3;
                                str2 = str4;
                                i = length;
                            } catch (NoSuchMethodException e20) {
                                e = e20;
                                str = str3;
                                str2 = str4;
                                i = length;
                                noSuchMethodException = e;
                                if (!zIsAnnotationPresent) {
                                    objOf = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                } else {
                                    objOf = Dispatcher.ForDefaultValue.of(method.getReturnType());
                                }
                                linkedHashMap.put(method, objOf);
                                i5++;
                                methods2 = methodArr;
                                length = i;
                                str4 = str2;
                                str3 = str;
                                i3 = 1;
                                i4 = 0;
                            } catch (Throwable th10) {
                                th = th10;
                                str = str3;
                                str2 = str4;
                                i = length;
                                th = th;
                                linkedHashMap.put(method, new Dispatcher.ForUnresolvedMethod("Unexpected error: " + th.getMessage()));
                                i5++;
                                methods2 = methodArr;
                                length = i;
                                str4 = str2;
                                str3 = str;
                                i3 = 1;
                                i4 = 0;
                            }
                        } catch (ClassNotFoundException e21) {
                            e = e21;
                            str = str3;
                            str2 = str4;
                            methodArr = methods2;
                        } catch (NoSuchMethodException e22) {
                            e = e22;
                            str = str3;
                            str2 = str4;
                            methodArr = methods2;
                        } catch (Throwable th11) {
                            th = th11;
                            str = str3;
                            str2 = str4;
                            methodArr = methods2;
                        }
                    }
                    str = str3;
                    str2 = str4;
                    methodArr = methods2;
                    i = length;
                } else {
                    str = str3;
                    str2 = str4;
                    methodArr = methods2;
                    i = length;
                }
                i5++;
                methods2 = methodArr;
                length = i;
                str4 = str2;
                str3 = str;
                i3 = 1;
                i4 = 0;
            }
            if (z3) {
                return (T) DynamicClassLoader.proxy(this.proxy, linkedHashMap);
            }
            return (T) Proxy.newProxyInstance(this.proxy.getClassLoader(), new Class[]{this.proxy}, new ProxiedInvocationHandler(cls.getName(), linkedHashMap));
        } catch (ClassNotFoundException e23) {
            String str5 = "Instance check requires a boolean return type: ";
            String str6 = "Instance check requires a single regular-typed argument: ";
            if (this.generate) {
                methods = (Method[]) GraalImageCode.getCurrent().sorted(this.proxy.getMethods(), MethodComparator.INSTANCE);
            } else {
                methods = this.proxy.getMethods();
            }
            int length6 = methods.length;
            int i16 = 0;
            while (i16 < length6) {
                Method method3 = methods[i16];
                if (method3.getDeclaringClass() != Object.class) {
                    if (method3.isAnnotationPresent(Instance.class)) {
                        if (method3.getParameterTypes().length != 1 || method3.getParameterTypes()[0].isPrimitive() || method3.getParameterTypes()[0].isArray()) {
                            throw new IllegalStateException(str6 + method3);
                        }
                        if (method3.getReturnType() != Boolean.TYPE) {
                            throw new IllegalStateException(str5 + method3);
                        }
                        linkedHashMap.put(method3, Dispatcher.ForDefaultValue.BOOLEAN);
                    } else {
                        linkedHashMap.put(method3, (zIsAnnotationPresent || method3.isAnnotationPresent(Defaults.class)) ? Dispatcher.ForDefaultValue.of(method3.getReturnType()) : new Dispatcher.ForUnresolvedMethod("Type not available on current VM: " + e23.getMessage()));
                    }
                }
                i16++;
                str5 = str5;
                str6 = str6;
            }
            return this.generate ? (T) DynamicClassLoader.proxy(this.proxy, linkedHashMap) : (T) Proxy.newProxyInstance(this.proxy.getClassLoader(), new Class[]{this.proxy}, new ProxiedInvocationHandler(strValue, linkedHashMap));
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    private static class InvokerCreationAction implements PrivilegedAction<Invoker> {
        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass();
        }

        public int hashCode() {
            return getClass().hashCode();
        }

        private InvokerCreationAction() {
        }

        @Override // java.security.PrivilegedAction
        public Invoker run() {
            return DynamicClassLoader.invoker();
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    private static class DirectInvoker implements Invoker {
        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass();
        }

        public int hashCode() {
            return getClass().hashCode();
        }

        private DirectInvoker() {
        }

        @Override // net.bytebuddy.utility.Invoker
        public Object newInstance(Constructor<?> constructor, Object[] objArr) throws IllegalAccessException, InstantiationException, InvocationTargetException {
            return constructor.newInstance(objArr);
        }

        @Override // net.bytebuddy.utility.Invoker
        public Object invoke(Method method, @MaybeNull Object obj, @MaybeNull Object[] objArr) throws IllegalAccessException, InvocationTargetException {
            return method.invoke(obj, objArr);
        }
    }

    protected interface Dispatcher {
        int apply(MethodVisitor methodVisitor, Method method);

        @MaybeNull
        Object invoke(Object[] objArr) throws Throwable;

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForInstanceCheck implements Dispatcher {
            private final Class<?> target;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.target.equals(((ForInstanceCheck) obj).target);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.target.hashCode();
            }

            protected ForInstanceCheck(Class<?> cls) {
                this.target = cls;
            }

            @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
            public Object invoke(Object[] objArr) {
                return Boolean.valueOf(this.target.isInstance(objArr[0]));
            }

            @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
            public int apply(MethodVisitor methodVisitor, Method method) {
                methodVisitor.visitVarInsn(25, 1);
                methodVisitor.visitTypeInsn(193, Type.getInternalName(this.target));
                methodVisitor.visitInsn(172);
                return 1;
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForContainerCreation implements Dispatcher {
            private final Class<?> target;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.target.equals(((ForContainerCreation) obj).target);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.target.hashCode();
            }

            protected ForContainerCreation(Class<?> cls) {
                this.target = cls;
            }

            @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
            public Object invoke(Object[] objArr) {
                return Array.newInstance(this.target, ((Integer) objArr[0]).intValue());
            }

            @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
            public int apply(MethodVisitor methodVisitor, Method method) {
                methodVisitor.visitVarInsn(21, 1);
                methodVisitor.visitTypeInsn(189, Type.getInternalName(this.target));
                methodVisitor.visitInsn(176);
                return 1;
            }
        }

        public enum ForDefaultValue implements Dispatcher {
            VOID(null, 0, 177, 0),
            BOOLEAN(false, 3, 172, 1),
            BOOLEAN_REVERSE(true, 4, 172, 1),
            BYTE((byte) 0, 3, 172, 1),
            SHORT((short) 0, 3, 172, 1),
            CHARACTER((char) 0, 3, 172, 1),
            INTEGER(0, 3, 172, 1),
            LONG(0L, 9, 173, 2),
            FLOAT(Float.valueOf(0.0f), 11, 174, 1),
            DOUBLE(Double.valueOf(0.0d), 14, 175, 2),
            REFERENCE(null, 1, 176, 1);

            private final int load;
            private final int returned;
            private final int size;

            @MaybeNull
            private final Object value;

            ForDefaultValue(Object obj, int i, int i2, int i3) {
                this.value = obj;
                this.load = i;
                this.returned = i2;
                this.size = i3;
            }

            protected static Dispatcher of(Class<?> cls) {
                if (cls == Void.TYPE) {
                    return VOID;
                }
                if (cls == Boolean.TYPE) {
                    return BOOLEAN;
                }
                if (cls == Byte.TYPE) {
                    return BYTE;
                }
                if (cls == Short.TYPE) {
                    return SHORT;
                }
                if (cls == Character.TYPE) {
                    return CHARACTER;
                }
                if (cls == Integer.TYPE) {
                    return INTEGER;
                }
                if (cls == Long.TYPE) {
                    return LONG;
                }
                if (cls == Float.TYPE) {
                    return FLOAT;
                }
                if (cls == Double.TYPE) {
                    return DOUBLE;
                }
                if (cls.isArray()) {
                    if (cls.getComponentType() == Boolean.TYPE) {
                        return OfPrimitiveArray.BOOLEAN;
                    }
                    if (cls.getComponentType() == Byte.TYPE) {
                        return OfPrimitiveArray.BYTE;
                    }
                    if (cls.getComponentType() == Short.TYPE) {
                        return OfPrimitiveArray.SHORT;
                    }
                    if (cls.getComponentType() == Character.TYPE) {
                        return OfPrimitiveArray.CHARACTER;
                    }
                    if (cls.getComponentType() == Integer.TYPE) {
                        return OfPrimitiveArray.INTEGER;
                    }
                    if (cls.getComponentType() == Long.TYPE) {
                        return OfPrimitiveArray.LONG;
                    }
                    if (cls.getComponentType() == Float.TYPE) {
                        return OfPrimitiveArray.FLOAT;
                    }
                    if (cls.getComponentType() == Double.TYPE) {
                        return OfPrimitiveArray.DOUBLE;
                    }
                    return OfNonPrimitiveArray.of(cls.getComponentType());
                }
                return REFERENCE;
            }

            @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
            @MaybeNull
            public Object invoke(Object[] objArr) {
                return this.value;
            }

            @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
            public int apply(MethodVisitor methodVisitor, Method method) {
                int i = this.load;
                if (i != 0) {
                    methodVisitor.visitInsn(i);
                }
                methodVisitor.visitInsn(this.returned);
                return this.size;
            }

            protected enum OfPrimitiveArray implements Dispatcher {
                BOOLEAN(new boolean[0], 4),
                BYTE(new byte[0], 8),
                SHORT(new short[0], 9),
                CHARACTER(new char[0], 5),
                INTEGER(new int[0], 10),
                LONG(new long[0], 11),
                FLOAT(new float[0], 6),
                DOUBLE(new double[0], 7);

                private final int operand;
                private final Object value;

                OfPrimitiveArray(Object obj, int i) {
                    this.value = obj;
                    this.operand = i;
                }

                @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
                public Object invoke(Object[] objArr) {
                    return this.value;
                }

                @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
                public int apply(MethodVisitor methodVisitor, Method method) {
                    methodVisitor.visitInsn(3);
                    methodVisitor.visitIntInsn(188, this.operand);
                    methodVisitor.visitInsn(176);
                    return 1;
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            protected static class OfNonPrimitiveArray implements Dispatcher {
                private final Class<?> componentType;

                @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.IGNORE)
                private final Object value;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.componentType.equals(((OfNonPrimitiveArray) obj).componentType);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.componentType.hashCode();
                }

                protected OfNonPrimitiveArray(Object obj, Class<?> cls) {
                    this.value = obj;
                    this.componentType = cls;
                }

                protected static Dispatcher of(Class<?> cls) {
                    return new OfNonPrimitiveArray(Array.newInstance(cls, 0), cls);
                }

                @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
                public Object invoke(Object[] objArr) {
                    return this.value;
                }

                @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
                public int apply(MethodVisitor methodVisitor, Method method) {
                    methodVisitor.visitInsn(3);
                    methodVisitor.visitTypeInsn(189, Type.getInternalName(this.componentType));
                    methodVisitor.visitInsn(176);
                    return 1;
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForConstructor implements Dispatcher {
            private final Constructor<?> constructor;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.constructor.equals(((ForConstructor) obj).constructor);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.constructor.hashCode();
            }

            protected ForConstructor(Constructor<?> constructor) {
                this.constructor = constructor;
            }

            @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
            public Object invoke(Object[] objArr) throws Throwable {
                return JavaDispatcher.INVOKER.newInstance(this.constructor, objArr);
            }

            @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
            public int apply(MethodVisitor methodVisitor, Method method) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Class<?>[] parameterTypes2 = this.constructor.getParameterTypes();
                methodVisitor.visitTypeInsn(187, Type.getInternalName(this.constructor.getDeclaringClass()));
                methodVisitor.visitInsn(89);
                int size = 1;
                for (int i = 0; i < parameterTypes.length; i++) {
                    Type type = Type.getType(parameterTypes[i]);
                    methodVisitor.visitVarInsn(type.getOpcode(21), size);
                    Class<?> cls = parameterTypes[i];
                    Class<?> cls2 = parameterTypes2[i];
                    if (cls != cls2) {
                        methodVisitor.visitTypeInsn(192, Type.getInternalName(cls2));
                    }
                    size += type.getSize();
                }
                methodVisitor.visitMethodInsn(183, Type.getInternalName(this.constructor.getDeclaringClass()), "<init>", Type.getConstructorDescriptor(this.constructor), false);
                methodVisitor.visitInsn(176);
                return size + 1;
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForStaticMethod implements Dispatcher {
            private final Method method;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.method.equals(((ForStaticMethod) obj).method);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.method.hashCode();
            }

            protected ForStaticMethod(Method method) {
                this.method = method;
            }

            @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
            @MaybeNull
            public Object invoke(Object[] objArr) throws Throwable {
                return JavaDispatcher.INVOKER.invoke(this.method, null, objArr);
            }

            @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
            public int apply(MethodVisitor methodVisitor, Method method) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Class<?>[] parameterTypes2 = this.method.getParameterTypes();
                int size = 1;
                for (int i = 0; i < parameterTypes.length; i++) {
                    Type type = Type.getType(parameterTypes[i]);
                    methodVisitor.visitVarInsn(type.getOpcode(21), size);
                    Class<?> cls = parameterTypes[i];
                    Class<?> cls2 = parameterTypes2[i];
                    if (cls != cls2) {
                        methodVisitor.visitTypeInsn(192, Type.getInternalName(cls2));
                    }
                    size += type.getSize();
                }
                methodVisitor.visitMethodInsn(184, Type.getInternalName(this.method.getDeclaringClass()), this.method.getName(), Type.getMethodDescriptor(this.method), this.method.getDeclaringClass().isInterface());
                methodVisitor.visitInsn(Type.getReturnType(this.method).getOpcode(172));
                return Math.max(size - 1, Type.getReturnType(this.method).getSize());
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForNonStaticMethod implements Dispatcher {
            private static final Object[] NO_ARGUMENTS = new Object[0];
            private final Method method;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.method.equals(((ForNonStaticMethod) obj).method);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.method.hashCode();
            }

            protected ForNonStaticMethod(Method method) {
                this.method = method;
            }

            @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
            public Object invoke(Object[] objArr) throws Throwable {
                Object[] objArr2;
                if (objArr.length == 1) {
                    objArr2 = NO_ARGUMENTS;
                } else {
                    int length = objArr.length - 1;
                    Object[] objArr3 = new Object[length];
                    System.arraycopy(objArr, 1, objArr3, 0, length);
                    objArr2 = objArr3;
                }
                return JavaDispatcher.INVOKER.invoke(this.method, objArr[0], objArr2);
            }

            @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
            public int apply(MethodVisitor methodVisitor, Method method) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Class<?>[] parameterTypes2 = this.method.getParameterTypes();
                int i = 0;
                int size = 1;
                while (i < parameterTypes.length) {
                    Type type = Type.getType(parameterTypes[i]);
                    methodVisitor.visitVarInsn(type.getOpcode(21), size);
                    if (parameterTypes[i] != (i == 0 ? this.method.getDeclaringClass() : parameterTypes2[i - 1])) {
                        methodVisitor.visitTypeInsn(192, Type.getInternalName(i == 0 ? this.method.getDeclaringClass() : parameterTypes2[i - 1]));
                    }
                    size += type.getSize();
                    i++;
                }
                methodVisitor.visitMethodInsn(this.method.getDeclaringClass().isInterface() ? 185 : 182, Type.getInternalName(this.method.getDeclaringClass()), this.method.getName(), Type.getMethodDescriptor(this.method), this.method.getDeclaringClass().isInterface());
                methodVisitor.visitInsn(Type.getReturnType(this.method).getOpcode(172));
                return Math.max(size - 1, Type.getReturnType(this.method).getSize());
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForUnresolvedMethod implements Dispatcher {
            private final String message;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.message.equals(((ForUnresolvedMethod) obj).message);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.message.hashCode();
            }

            protected ForUnresolvedMethod(String str) {
                this.message = str;
            }

            @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
            public Object invoke(Object[] objArr) throws Throwable {
                throw new IllegalStateException("Could not invoke proxy: " + this.message);
            }

            @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.Dispatcher
            public int apply(MethodVisitor methodVisitor, Method method) {
                methodVisitor.visitTypeInsn(187, Type.getInternalName(IllegalStateException.class));
                methodVisitor.visitInsn(89);
                methodVisitor.visitLdcInsn(this.message);
                methodVisitor.visitMethodInsn(183, Type.getInternalName(IllegalStateException.class), "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, Type.getType((Class<?>) String.class)), false);
                methodVisitor.visitInsn(191);
                return 3;
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class ProxiedInvocationHandler implements InvocationHandler {
        private static final Object[] NO_ARGUMENTS = new Object[0];

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f406name;
        private final Map<Method, Dispatcher> targets;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ProxiedInvocationHandler proxiedInvocationHandler = (ProxiedInvocationHandler) obj;
            return this.f406name.equals(proxiedInvocationHandler.f406name) && this.targets.equals(proxiedInvocationHandler.targets);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.f406name.hashCode()) * 31) + this.targets.hashCode();
        }

        protected ProxiedInvocationHandler(String str, Map<Method, Dispatcher> map) {
            this.f406name = str;
            this.targets = map;
        }

        @Override // java.lang.reflect.InvocationHandler
        @MaybeNull
        public Object invoke(Object obj, Method method, @MaybeNull Object[] objArr) throws Throwable {
            z = false;
            z = false;
            boolean z = false;
            if (method.getDeclaringClass() == Object.class) {
                if (method.getName().equals("hashCode")) {
                    return Integer.valueOf(hashCode());
                }
                if (method.getName().equals(Namer.EQUALS_METHOD_NAME)) {
                    Object obj2 = objArr[0];
                    if (obj2 != null && Proxy.isProxyClass(obj2.getClass()) && Proxy.getInvocationHandler(objArr[0]).equals(this)) {
                        z = true;
                    }
                    return Boolean.valueOf(z);
                }
                if (method.getName().equals("toString")) {
                    return "Call proxy for " + this.f406name;
                }
                throw new IllegalStateException("Unexpected object method: " + method);
            }
            Dispatcher dispatcher = this.targets.get(method);
            try {
                try {
                    if (dispatcher == null) {
                        throw new IllegalStateException("No proxy target found for " + method);
                    }
                    if (objArr == null) {
                        objArr = NO_ARGUMENTS;
                    }
                    return dispatcher.invoke(objArr);
                } catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            } catch (Error e2) {
                throw e2;
            } catch (RuntimeException e3) {
                throw e3;
            } catch (Throwable th) {
                for (Class<?> cls : method.getExceptionTypes()) {
                    if (cls.isInstance(th)) {
                        throw th;
                    }
                }
                throw new IllegalStateException("Failed to invoke proxy for " + method, th);
            }
        }
    }

    protected static class DynamicClassLoader extends ClassLoader {

        @MaybeNull
        private static final String DUMP_FOLDER;
        private static final Class<?>[] NO_PARAMETER = new Class[0];
        private static final Object[] NO_ARGUMENT = new Object[0];

        static {
            String str;
            try {
                str = (String) JavaDispatcher.doPrivileged(new GetSystemPropertyAction(TypeWriter.DUMP_PROPERTY));
            } catch (Throwable unused) {
                str = null;
            }
            DUMP_FOLDER = str;
        }

        protected DynamicClassLoader(Class<?> cls) {
            super(cls.getClassLoader());
            JavaDispatcher.RESOLVER.accept(this, cls);
        }

        protected static Object proxy(Class<?> cls, Map<Method, Dispatcher> map) {
            ClassWriter classWriter = new ClassWriter(0);
            classWriter.visit(ClassFileVersion.ofThisVm(ClassFileVersion.JAVA_V5).getMinorMajorVersion(), 1, Type.getInternalName(cls) + "$Proxy", null, Type.getInternalName(Object.class), new String[]{Type.getInternalName(cls)});
            for (Map.Entry<Method, Dispatcher> entry : map.entrySet()) {
                Class<?>[] exceptionTypes = entry.getKey().getExceptionTypes();
                String[] strArr = new String[exceptionTypes.length];
                for (int i = 0; i < exceptionTypes.length; i++) {
                    strArr[i] = Type.getInternalName(exceptionTypes[i]);
                }
                MethodVisitor methodVisitorVisitMethod = classWriter.visitMethod(1, entry.getKey().getName(), Type.getMethodDescriptor(entry.getKey()), null, strArr);
                methodVisitorVisitMethod.visitCode();
                int size = (entry.getKey().getModifiers() & 8) == 0 ? 1 : 0;
                for (Class<?> cls2 : entry.getKey().getParameterTypes()) {
                    size += Type.getType(cls2).getSize();
                }
                methodVisitorVisitMethod.visitMaxs(entry.getValue().apply(methodVisitorVisitMethod, entry.getKey()), size);
                methodVisitorVisitMethod.visitEnd();
            }
            MethodVisitor methodVisitorVisitMethod2 = classWriter.visitMethod(1, "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]), null, null);
            methodVisitorVisitMethod2.visitCode();
            methodVisitorVisitMethod2.visitVarInsn(25, 0);
            methodVisitorVisitMethod2.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]), false);
            methodVisitorVisitMethod2.visitInsn(177);
            methodVisitorVisitMethod2.visitMaxs(1, 1);
            methodVisitorVisitMethod2.visitEnd();
            classWriter.visitEnd();
            byte[] byteArray = classWriter.toByteArray();
            String str = DUMP_FOLDER;
            if (str != null) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(str, cls.getName() + "$Proxy.class"));
                    try {
                        fileOutputStream.write(byteArray);
                        fileOutputStream.close();
                    } catch (Throwable th) {
                        fileOutputStream.close();
                        throw th;
                    }
                } catch (Throwable unused) {
                }
            }
            try {
                try {
                    return new DynamicClassLoader(cls).defineClass(cls.getName() + "$Proxy", byteArray, 0, byteArray.length, JavaDispatcher.class.getProtectionDomain()).getConstructor(NO_PARAMETER).newInstance(NO_ARGUMENT);
                } catch (Exception e) {
                    e = e;
                    throw new IllegalStateException("Failed to create proxy for " + cls.getName(), e);
                }
            } catch (Exception e2) {
                e = e2;
            }
        }

        protected static Invoker invoker() {
            ClassWriter classWriter = new ClassWriter(0);
            classWriter.visit(ClassFileVersion.ofThisVm(ClassFileVersion.JAVA_V5).getMinorMajorVersion(), 1, Type.getInternalName(Invoker.class) + "$Dispatcher", null, Type.getInternalName(Object.class), new String[]{Type.getInternalName(Invoker.class)});
            for (Method method : (Method[]) GraalImageCode.getCurrent().sorted(Invoker.class.getMethods(), MethodComparator.INSTANCE)) {
                Class<?>[] exceptionTypes = method.getExceptionTypes();
                String[] strArr = new String[exceptionTypes.length];
                for (int i = 0; i < exceptionTypes.length; i++) {
                    strArr[i] = Type.getInternalName(exceptionTypes[i]);
                }
                MethodVisitor methodVisitorVisitMethod = classWriter.visitMethod(1, method.getName(), Type.getMethodDescriptor(method), null, strArr);
                methodVisitorVisitMethod.visitCode();
                Type[] typeArr = new Type[method.getParameterTypes().length - 1];
                int size = 1;
                for (int i2 = 0; i2 < method.getParameterTypes().length; i2++) {
                    Type type = Type.getType(method.getParameterTypes()[i2]);
                    if (i2 > 0) {
                        typeArr[i2 - 1] = type;
                    }
                    methodVisitorVisitMethod.visitVarInsn(type.getOpcode(21), size);
                    size += type.getSize();
                }
                methodVisitorVisitMethod.visitMethodInsn(182, Type.getInternalName(method.getParameterTypes()[0]), method.getName(), Type.getMethodDescriptor(Type.getReturnType(method), typeArr), false);
                methodVisitorVisitMethod.visitInsn(Type.getReturnType(method).getOpcode(172));
                methodVisitorVisitMethod.visitMaxs(Math.max(size - 1, Type.getReturnType(method).getSize()), size);
                methodVisitorVisitMethod.visitEnd();
            }
            MethodVisitor methodVisitorVisitMethod2 = classWriter.visitMethod(1, "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]), null, null);
            methodVisitorVisitMethod2.visitCode();
            methodVisitorVisitMethod2.visitVarInsn(25, 0);
            methodVisitorVisitMethod2.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]), false);
            methodVisitorVisitMethod2.visitInsn(177);
            methodVisitorVisitMethod2.visitMaxs(1, 1);
            methodVisitorVisitMethod2.visitEnd();
            classWriter.visitEnd();
            byte[] byteArray = classWriter.toByteArray();
            try {
                String property = System.getProperty(TypeWriter.DUMP_PROPERTY);
                if (property != null) {
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(property, Invoker.class.getName() + "$Dispatcher.class"));
                    try {
                        fileOutputStream.write(byteArray);
                        fileOutputStream.close();
                    } catch (Throwable th) {
                        fileOutputStream.close();
                        throw th;
                    }
                }
            } catch (Throwable unused) {
            }
            try {
                return (Invoker) new DynamicClassLoader(Invoker.class).defineClass(Invoker.class.getName() + "$Dispatcher", byteArray, 0, byteArray.length, JavaDispatcher.class.getProtectionDomain()).getConstructor(NO_PARAMETER).newInstance(NO_ARGUMENT);
            } catch (UnsupportedOperationException unused2) {
                return new DirectInvoker();
            } catch (Exception e) {
                throw new IllegalStateException("Failed to create invoker for " + Invoker.class.getName(), e);
            }
        }

        protected interface Resolver {

            public enum NoOp implements Resolver {
                INSTANCE;

                @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.DynamicClassLoader.Resolver
                public void accept(@MaybeNull ClassLoader classLoader, Class<?> cls) {
                }
            }

            void accept(@MaybeNull ClassLoader classLoader, Class<?> cls);

            public enum CreationAction implements PrivilegedAction<Resolver> {
                INSTANCE;

                @Override // java.security.PrivilegedAction
                public Resolver run() {
                    try {
                        Class<?> cls = Class.forName("java.lang.Module", false, null);
                        return new ForModuleSystem(Class.class.getMethod("getModule", new Class[0]), cls.getMethod("isExported", String.class), cls.getMethod("addExports", String.class, cls), ClassLoader.class.getMethod("getUnnamedModule", new Class[0]));
                    } catch (Exception unused) {
                        return NoOp.INSTANCE;
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForModuleSystem implements Resolver {
                private final Method addExports;
                private final Method getModule;
                private final Method getUnnamedModule;
                private final Method isExported;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    ForModuleSystem forModuleSystem = (ForModuleSystem) obj;
                    return this.getModule.equals(forModuleSystem.getModule) && this.isExported.equals(forModuleSystem.isExported) && this.addExports.equals(forModuleSystem.addExports) && this.getUnnamedModule.equals(forModuleSystem.getUnnamedModule);
                }

                public int hashCode() {
                    return (((((((getClass().hashCode() * 31) + this.getModule.hashCode()) * 31) + this.isExported.hashCode()) * 31) + this.addExports.hashCode()) * 31) + this.getUnnamedModule.hashCode();
                }

                protected ForModuleSystem(Method method, Method method2, Method method3, Method method4) {
                    this.getModule = method;
                    this.isExported = method2;
                    this.addExports = method3;
                    this.getUnnamedModule = method4;
                }

                @Override // net.bytebuddy.utility.dispatcher.JavaDispatcher.DynamicClassLoader.Resolver
                public void accept(@MaybeNull ClassLoader classLoader, Class<?> cls) {
                    Package r0 = cls.getPackage();
                    if (r0 != null) {
                        try {
                            Object objInvoke = this.getModule.invoke(cls, new Object[0]);
                            if (((Boolean) this.isExported.invoke(objInvoke, r0.getName())).booleanValue()) {
                                return;
                            }
                            this.addExports.invoke(objInvoke, r0.getName(), this.getUnnamedModule.invoke(classLoader, new Object[0]));
                        } catch (Exception e) {
                            throw new IllegalStateException("Failed to adjust module graph for dispatcher", e);
                        }
                    }
                }
            }
        }
    }
}
