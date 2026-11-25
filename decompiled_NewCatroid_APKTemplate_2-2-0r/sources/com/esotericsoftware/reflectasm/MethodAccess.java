package com.esotericsoftware.reflectasm;

import com.esotericsoftware.asm.ClassWriter;
import com.esotericsoftware.asm.Label;
import com.esotericsoftware.asm.MethodVisitor;
import com.esotericsoftware.asm.Type;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: loaded from: classes8.dex */
public abstract class MethodAccess {
    private String[] methodNames;
    private Class[][] parameterTypes;
    private Class[] returnTypes;

    public abstract Object invoke(Object obj, int i, Object... objArr);

    public Object invoke(Object obj, String str, Class[] clsArr, Object... objArr) {
        return invoke(obj, getIndex(str, clsArr), objArr);
    }

    public Object invoke(Object obj, String str, Object... objArr) {
        return invoke(obj, getIndex(str, objArr == null ? 0 : objArr.length), objArr);
    }

    public int getIndex(String str) {
        int length = this.methodNames.length;
        for (int i = 0; i < length; i++) {
            if (this.methodNames[i].equals(str)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unable to find non-private method: " + str);
    }

    public int getIndex(String str, Class... clsArr) {
        int length = this.methodNames.length;
        for (int i = 0; i < length; i++) {
            if (this.methodNames[i].equals(str) && Arrays.equals(clsArr, this.parameterTypes[i])) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unable to find non-private method: " + str + " " + Arrays.toString(clsArr));
    }

    public int getIndex(String str, int i) {
        int length = this.methodNames.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (this.methodNames[i2].equals(str) && this.parameterTypes[i2].length == i) {
                return i2;
            }
        }
        throw new IllegalArgumentException("Unable to find non-private method: " + str + " with " + i + " params.");
    }

    public String[] getMethodNames() {
        return this.methodNames;
    }

    public Class[][] getParameterTypes() {
        return this.parameterTypes;
    }

    public Class[] getReturnTypes() {
        return this.returnTypes;
    }

    public static MethodAccess get(Class cls) {
        Class clsLoadAccessClass;
        Class[][] clsArr;
        Class[] clsArr2;
        String str;
        int i;
        boolean zIsInterface = cls.isInterface();
        if (!zIsInterface && cls.getSuperclass() == null && cls != Object.class) {
            throw new IllegalArgumentException("The type must not be an interface, a primitive type, or void.");
        }
        ArrayList arrayList = new ArrayList();
        if (!zIsInterface) {
            for (Class superclass = cls; superclass != Object.class; superclass = superclass.getSuperclass()) {
                addDeclaredMethodsToList(superclass, arrayList);
            }
        } else {
            recursiveAddInterfaceMethodsToList(cls, arrayList);
        }
        int size = arrayList.size();
        String[] strArr = new String[size];
        Class[][] clsArr3 = new Class[size][];
        Class[] clsArr4 = new Class[size];
        for (int i2 = 0; i2 < size; i2++) {
            Method method = (Method) arrayList.get(i2);
            strArr[i2] = method.getName();
            clsArr3[i2] = method.getParameterTypes();
            clsArr4[i2] = method.getReturnType();
        }
        String name2 = cls.getName();
        String str2 = name2 + "MethodAccess";
        if (str2.startsWith("java.")) {
            str2 = "reflectasm." + str2;
        }
        AccessClassLoader accessClassLoader = AccessClassLoader.get(cls);
        synchronized (accessClassLoader) {
            clsLoadAccessClass = accessClassLoader.loadAccessClass(str2);
            if (clsLoadAccessClass == null) {
                String strReplace = str2.replace('.', '/');
                String strReplace2 = name2.replace('.', '/');
                ClassWriter classWriter = new ClassWriter(1);
                classWriter.visit(196653, 33, strReplace, null, "com/esotericsoftware/reflectasm/MethodAccess", null);
                MethodVisitor methodVisitorVisitMethod = classWriter.visitMethod(1, "<init>", "()V", null, null);
                methodVisitorVisitMethod.visitCode();
                methodVisitorVisitMethod.visitVarInsn(25, 0);
                methodVisitorVisitMethod.visitMethodInsn(183, "com/esotericsoftware/reflectasm/MethodAccess", "<init>", "()V");
                methodVisitorVisitMethod.visitInsn(177);
                methodVisitorVisitMethod.visitMaxs(0, 0);
                methodVisitorVisitMethod.visitEnd();
                MethodVisitor methodVisitorVisitMethod2 = classWriter.visitMethod(129, "invoke", "(Ljava/lang/Object;I[Ljava/lang/Object;)Ljava/lang/Object;", null, null);
                methodVisitorVisitMethod2.visitCode();
                if (arrayList.isEmpty()) {
                    clsArr = clsArr3;
                    clsArr2 = clsArr4;
                    str = str2;
                } else {
                    methodVisitorVisitMethod2.visitVarInsn(25, 1);
                    methodVisitorVisitMethod2.visitTypeInsn(192, strReplace2);
                    methodVisitorVisitMethod2.visitVarInsn(58, 4);
                    methodVisitorVisitMethod2.visitVarInsn(21, 2);
                    Label[] labelArr = new Label[size];
                    for (int i3 = 0; i3 < size; i3++) {
                        labelArr[i3] = new Label();
                    }
                    Label label = new Label();
                    methodVisitorVisitMethod2.visitTableSwitchInsn(0, size - 1, label, labelArr);
                    StringBuilder sb = new StringBuilder(128);
                    int i4 = 0;
                    while (i4 < size) {
                        methodVisitorVisitMethod2.visitLabel(labelArr[i4]);
                        if (i4 == 0) {
                            methodVisitorVisitMethod2.visitFrame(1, 1, new Object[]{strReplace2}, 0, null);
                        } else {
                            methodVisitorVisitMethod2.visitFrame(3, 0, null, 0, null);
                        }
                        int i5 = size;
                        methodVisitorVisitMethod2.visitVarInsn(25, 4);
                        sb.setLength(0);
                        sb.append('(');
                        Class[] clsArr5 = clsArr3[i4];
                        Class cls2 = clsArr4[i4];
                        Label[] labelArr2 = labelArr;
                        int i6 = 0;
                        while (i6 < clsArr5.length) {
                            Class[] clsArr6 = clsArr4;
                            methodVisitorVisitMethod2.visitVarInsn(25, 3);
                            methodVisitorVisitMethod2.visitIntInsn(16, i6);
                            methodVisitorVisitMethod2.visitInsn(50);
                            Type type = Type.getType(clsArr5[i6]);
                            switch (type.getSort()) {
                                case 1:
                                    clsArr5 = clsArr5;
                                    methodVisitorVisitMethod2.visitTypeInsn(192, "java/lang/Boolean");
                                    methodVisitorVisitMethod2.visitMethodInsn(182, "java/lang/Boolean", "booleanValue", "()Z");
                                    break;
                                case 2:
                                    clsArr5 = clsArr5;
                                    methodVisitorVisitMethod2.visitTypeInsn(192, "java/lang/Character");
                                    methodVisitorVisitMethod2.visitMethodInsn(182, "java/lang/Character", "charValue", "()C");
                                    break;
                                case 3:
                                    clsArr5 = clsArr5;
                                    methodVisitorVisitMethod2.visitTypeInsn(192, "java/lang/Byte");
                                    methodVisitorVisitMethod2.visitMethodInsn(182, "java/lang/Byte", "byteValue", "()B");
                                    break;
                                case 4:
                                    clsArr5 = clsArr5;
                                    methodVisitorVisitMethod2.visitTypeInsn(192, "java/lang/Short");
                                    methodVisitorVisitMethod2.visitMethodInsn(182, "java/lang/Short", "shortValue", "()S");
                                    break;
                                case 5:
                                    clsArr5 = clsArr5;
                                    methodVisitorVisitMethod2.visitTypeInsn(192, "java/lang/Integer");
                                    methodVisitorVisitMethod2.visitMethodInsn(182, "java/lang/Integer", "intValue", "()I");
                                    break;
                                case 6:
                                    clsArr5 = clsArr5;
                                    methodVisitorVisitMethod2.visitTypeInsn(192, "java/lang/Float");
                                    methodVisitorVisitMethod2.visitMethodInsn(182, "java/lang/Float", "floatValue", "()F");
                                    break;
                                case 7:
                                    clsArr5 = clsArr5;
                                    methodVisitorVisitMethod2.visitTypeInsn(192, "java/lang/Long");
                                    methodVisitorVisitMethod2.visitMethodInsn(182, "java/lang/Long", "longValue", "()J");
                                    break;
                                case 8:
                                    clsArr5 = clsArr5;
                                    methodVisitorVisitMethod2.visitTypeInsn(192, "java/lang/Double");
                                    methodVisitorVisitMethod2.visitMethodInsn(182, "java/lang/Double", "doubleValue", "()D");
                                    break;
                                case 9:
                                    methodVisitorVisitMethod2.visitTypeInsn(192, type.getDescriptor());
                                    break;
                                case 10:
                                    methodVisitorVisitMethod2.visitTypeInsn(192, type.getInternalName());
                                    break;
                                default:
                                    break;
                            }
                            sb.append(type.getDescriptor());
                            i6++;
                            clsArr5 = clsArr5;
                            clsArr4 = clsArr6;
                            clsArr3 = clsArr3;
                            str2 = str2;
                        }
                        Class[][] clsArr7 = clsArr3;
                        Class[] clsArr8 = clsArr4;
                        String str3 = str2;
                        sb.append(')');
                        sb.append(Type.getDescriptor(cls2));
                        if (zIsInterface) {
                            i = 185;
                        } else {
                            i = Modifier.isStatic(((Method) arrayList.get(i4)).getModifiers()) ? 184 : 182;
                        }
                        methodVisitorVisitMethod2.visitMethodInsn(i, strReplace2, strArr[i4], sb.toString());
                        switch (Type.getType(cls2).getSort()) {
                            case 0:
                                methodVisitorVisitMethod2.visitInsn(1);
                                continue;
                                methodVisitorVisitMethod2.visitInsn(176);
                                i4++;
                                size = i5;
                                labelArr = labelArr2;
                                clsArr4 = clsArr8;
                                clsArr3 = clsArr7;
                                str2 = str3;
                                break;
                            case 1:
                                methodVisitorVisitMethod2.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
                                break;
                            case 2:
                                methodVisitorVisitMethod2.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
                                break;
                            case 3:
                                methodVisitorVisitMethod2.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                                break;
                            case 4:
                                methodVisitorVisitMethod2.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                                break;
                            case 5:
                                methodVisitorVisitMethod2.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                                break;
                            case 6:
                                methodVisitorVisitMethod2.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                                break;
                            case 7:
                                methodVisitorVisitMethod2.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                                break;
                            case 8:
                                methodVisitorVisitMethod2.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                                break;
                        }
                        methodVisitorVisitMethod2.visitInsn(176);
                        i4++;
                        size = i5;
                        labelArr = labelArr2;
                        clsArr4 = clsArr8;
                        clsArr3 = clsArr7;
                        str2 = str3;
                    }
                    clsArr = clsArr3;
                    clsArr2 = clsArr4;
                    str = str2;
                    methodVisitorVisitMethod2.visitLabel(label);
                    methodVisitorVisitMethod2.visitFrame(3, 0, null, 0, null);
                }
                methodVisitorVisitMethod2.visitTypeInsn(187, "java/lang/IllegalArgumentException");
                methodVisitorVisitMethod2.visitInsn(89);
                methodVisitorVisitMethod2.visitTypeInsn(187, "java/lang/StringBuilder");
                methodVisitorVisitMethod2.visitInsn(89);
                methodVisitorVisitMethod2.visitLdcInsn("Method not found: ");
                methodVisitorVisitMethod2.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
                methodVisitorVisitMethod2.visitVarInsn(21, 2);
                methodVisitorVisitMethod2.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
                methodVisitorVisitMethod2.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
                methodVisitorVisitMethod2.visitMethodInsn(183, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V");
                methodVisitorVisitMethod2.visitInsn(191);
                methodVisitorVisitMethod2.visitMaxs(0, 0);
                methodVisitorVisitMethod2.visitEnd();
                classWriter.visitEnd();
                str2 = str;
                clsLoadAccessClass = accessClassLoader.defineAccessClass(str2, classWriter.toByteArray());
            } else {
                clsArr = clsArr3;
                clsArr2 = clsArr4;
            }
        }
        try {
            MethodAccess methodAccess = (MethodAccess) clsLoadAccessClass.newInstance();
            methodAccess.methodNames = strArr;
            methodAccess.parameterTypes = clsArr;
            methodAccess.returnTypes = clsArr2;
            return methodAccess;
        } catch (Throwable th) {
            throw new RuntimeException("Error constructing method access class: " + str2, th);
        }
    }

    private static void addDeclaredMethodsToList(Class cls, ArrayList<Method> arrayList) {
        for (Method method : cls.getDeclaredMethods()) {
            if (!Modifier.isPrivate(method.getModifiers())) {
                arrayList.add(method);
            }
        }
    }

    private static void recursiveAddInterfaceMethodsToList(Class cls, ArrayList<Method> arrayList) {
        addDeclaredMethodsToList(cls, arrayList);
        for (Class<?> cls2 : cls.getInterfaces()) {
            recursiveAddInterfaceMethodsToList(cls2, arrayList);
        }
    }
}
