package com.esotericsoftware.reflectasm;

import com.esotericsoftware.asm.ClassWriter;
import com.esotericsoftware.asm.MethodVisitor;
import java.lang.reflect.Modifier;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: loaded from: classes8.dex */
public abstract class ConstructorAccess<T> {
    boolean isNonStaticMemberClass;

    public abstract T newInstance();

    public abstract T newInstance(Object obj);

    public boolean isNonStaticMemberClass() {
        return this.isNonStaticMemberClass;
    }

    /* JADX WARN: Code duplicated, block: B:36:0x00cf  */
    /* JADX WARN: Code duplicated, block: B:37:0x00d2  */
    public static <T> ConstructorAccess<T> get(Class<T> cls) {
        Class clsLoadAccessClass;
        int modifiers;
        String str;
        String str2;
        Class<?> enclosingClass = cls.getEnclosingClass();
        boolean z = (enclosingClass == null || !cls.isMemberClass() || Modifier.isStatic(cls.getModifiers())) ? false : true;
        String name2 = cls.getName();
        String str3 = name2 + "ConstructorAccess";
        if (str3.startsWith("java.")) {
            str3 = "reflectasm." + str3;
        }
        AccessClassLoader accessClassLoader = AccessClassLoader.get(cls);
        synchronized (accessClassLoader) {
            clsLoadAccessClass = accessClassLoader.loadAccessClass(str3);
            if (clsLoadAccessClass == null) {
                String strReplace = str3.replace('.', '/');
                String strReplace2 = name2.replace('.', '/');
                if (!z) {
                    str = null;
                    try {
                        modifiers = cls.getDeclaredConstructor(null).getModifiers();
                        if (Modifier.isPrivate(modifiers)) {
                            throw new RuntimeException("Class cannot be created (the no-arg constructor is private): " + cls.getName());
                        }
                        if (Modifier.isPublic(modifiers)) {
                            str2 = "com/esotericsoftware/reflectasm/PublicConstructorAccess";
                        } else {
                            str2 = "com/esotericsoftware/reflectasm/ConstructorAccess";
                        }
                        ClassWriter classWriter = new ClassWriter(0);
                        classWriter.visit(196653, 33, strReplace, null, str2, null);
                        insertConstructor(classWriter, str2);
                        insertNewInstance(classWriter, strReplace2);
                        insertNewInstanceInner(classWriter, strReplace2, str);
                        classWriter.visitEnd();
                        clsLoadAccessClass = accessClassLoader.defineAccessClass(str3, classWriter.toByteArray());
                    } catch (Exception e) {
                        throw new RuntimeException("Class cannot be created (missing no-arg constructor): " + cls.getName(), e);
                    }
                } else {
                    String strReplace3 = enclosingClass.getName().replace('.', '/');
                    try {
                        modifiers = cls.getDeclaredConstructor(enclosingClass).getModifiers();
                        if (Modifier.isPrivate(modifiers)) {
                            throw new RuntimeException("Non-static member class cannot be created (the enclosing class constructor is private): " + cls.getName());
                        }
                        str = strReplace3;
                        if (Modifier.isPublic(modifiers)) {
                            str2 = "com/esotericsoftware/reflectasm/PublicConstructorAccess";
                        } else {
                            str2 = "com/esotericsoftware/reflectasm/ConstructorAccess";
                        }
                        ClassWriter classWriter2 = new ClassWriter(0);
                        classWriter2.visit(196653, 33, strReplace, null, str2, null);
                        insertConstructor(classWriter2, str2);
                        insertNewInstance(classWriter2, strReplace2);
                        insertNewInstanceInner(classWriter2, strReplace2, str);
                        classWriter2.visitEnd();
                        clsLoadAccessClass = accessClassLoader.defineAccessClass(str3, classWriter2.toByteArray());
                    } catch (Exception e2) {
                        throw new RuntimeException("Non-static member class cannot be created (missing enclosing class constructor): " + cls.getName(), e2);
                    }
                }
            }
        }
        try {
            ConstructorAccess<T> constructorAccess = (ConstructorAccess) clsLoadAccessClass.newInstance();
            if (!(constructorAccess instanceof PublicConstructorAccess) && !AccessClassLoader.areInSameRuntimeClassLoader(cls, clsLoadAccessClass)) {
                throw new RuntimeException((!z ? "Class cannot be created (the no-arg constructor is protected or package-protected, and its ConstructorAccess could not be defined in the same class loader): " : "Non-static member class cannot be created (the enclosing class constructor is protected or package-protected, and its ConstructorAccess could not be defined in the same class loader): ") + cls.getName());
            }
            constructorAccess.isNonStaticMemberClass = z;
            return constructorAccess;
        } catch (Throwable th) {
            throw new RuntimeException("Exception constructing constructor access class: " + str3, th);
        }
    }

    private static void insertConstructor(ClassWriter classWriter, String str) {
        MethodVisitor methodVisitorVisitMethod = classWriter.visitMethod(1, "<init>", "()V", null, null);
        methodVisitorVisitMethod.visitCode();
        methodVisitorVisitMethod.visitVarInsn(25, 0);
        methodVisitorVisitMethod.visitMethodInsn(183, str, "<init>", "()V");
        methodVisitorVisitMethod.visitInsn(177);
        methodVisitorVisitMethod.visitMaxs(1, 1);
        methodVisitorVisitMethod.visitEnd();
    }

    static void insertNewInstance(ClassWriter classWriter, String str) {
        MethodVisitor methodVisitorVisitMethod = classWriter.visitMethod(1, TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "()Ljava/lang/Object;", null, null);
        methodVisitorVisitMethod.visitCode();
        methodVisitorVisitMethod.visitTypeInsn(187, str);
        methodVisitorVisitMethod.visitInsn(89);
        methodVisitorVisitMethod.visitMethodInsn(183, str, "<init>", "()V");
        methodVisitorVisitMethod.visitInsn(176);
        methodVisitorVisitMethod.visitMaxs(2, 1);
        methodVisitorVisitMethod.visitEnd();
    }

    static void insertNewInstanceInner(ClassWriter classWriter, String str, String str2) {
        MethodVisitor methodVisitorVisitMethod = classWriter.visitMethod(1, TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "(Ljava/lang/Object;)Ljava/lang/Object;", null, null);
        methodVisitorVisitMethod.visitCode();
        if (str2 != null) {
            methodVisitorVisitMethod.visitTypeInsn(187, str);
            methodVisitorVisitMethod.visitInsn(89);
            methodVisitorVisitMethod.visitVarInsn(25, 1);
            methodVisitorVisitMethod.visitTypeInsn(192, str2);
            methodVisitorVisitMethod.visitInsn(89);
            methodVisitorVisitMethod.visitMethodInsn(182, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, "getClass", "()Ljava/lang/Class;");
            methodVisitorVisitMethod.visitInsn(87);
            methodVisitorVisitMethod.visitMethodInsn(183, str, "<init>", "(L" + str2 + ";)V");
            methodVisitorVisitMethod.visitInsn(176);
            methodVisitorVisitMethod.visitMaxs(4, 2);
        } else {
            methodVisitorVisitMethod.visitTypeInsn(187, "java/lang/UnsupportedOperationException");
            methodVisitorVisitMethod.visitInsn(89);
            methodVisitorVisitMethod.visitLdcInsn("Not an inner class.");
            methodVisitorVisitMethod.visitMethodInsn(183, "java/lang/UnsupportedOperationException", "<init>", "(Ljava/lang/String;)V");
            methodVisitorVisitMethod.visitInsn(191);
            methodVisitorVisitMethod.visitMaxs(3, 2);
        }
        methodVisitorVisitMethod.visitEnd();
    }
}
