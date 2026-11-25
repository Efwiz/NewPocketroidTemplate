package org.luaj.vm2.lib.jse;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.luaj.vm2.LuaValue;

/* JADX INFO: loaded from: classes6.dex */
class JavaClass extends JavaInstance implements CoerceJavaToLua.Coercion {
    Map fields;
    Map innerclasses;
    Map methods;
    static final Map classes = Collections.synchronizedMap(new HashMap());
    static final LuaValue NEW = valueOf(PsiKeyword.NEW);

    JavaClass(Class cls) {
        super(cls);
        this.jclass = this;
    }

    static JavaClass forClass(Class cls) {
        Map map = classes;
        JavaClass javaClass = (JavaClass) map.get(cls);
        if (javaClass != null) {
            return javaClass;
        }
        JavaClass javaClass2 = new JavaClass(cls);
        map.put(cls, javaClass2);
        return javaClass2;
    }

    @Override // org.luaj.vm2.lib.jse.CoerceJavaToLua.Coercion
    public LuaValue coerce(Object obj) {
        return this;
    }

    public LuaValue getConstructor() {
        return getMethod(NEW);
    }

    Field getField(LuaValue luaValue) {
        if (this.fields == null) {
            HashMap map = new HashMap();
            for (Field field : ((Class) this.m_instance).getFields()) {
                if (Modifier.isPublic(field.getModifiers())) {
                    map.put(LuaValue.valueOf(field.getName()), field);
                    try {
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                    } catch (SecurityException unused) {
                    }
                }
            }
            this.fields = map;
        }
        return (Field) this.fields.get(luaValue);
    }

    Class getInnerClass(LuaValue luaValue) {
        if (this.innerclasses == null) {
            HashMap map = new HashMap();
            for (Class<?> cls : ((Class) this.m_instance).getClasses()) {
                String name2 = cls.getName();
                map.put(LuaValue.valueOf(name2.substring(Math.max(name2.lastIndexOf(36), name2.lastIndexOf(46)) + 1)), cls);
            }
            this.innerclasses = map;
        }
        return (Class) this.innerclasses.get(luaValue);
    }

    LuaValue getMethod(LuaValue luaValue) {
        LuaValue luaValue2;
        Object objForConstructors;
        if (this.methods == null) {
            HashMap map = new HashMap();
            for (Method method : ((Class) this.m_instance).getMethods()) {
                if (Modifier.isPublic(method.getModifiers())) {
                    String name2 = method.getName();
                    List arrayList = (List) map.get(name2);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        map.put(name2, arrayList);
                    }
                    arrayList.add(JavaMethod.forMethod(method));
                }
            }
            HashMap map2 = new HashMap();
            Constructor<?>[] constructors = ((Class) this.m_instance).getConstructors();
            ArrayList arrayList2 = new ArrayList();
            for (int i = 0; i < constructors.length; i++) {
                if (Modifier.isPublic(constructors[i].getModifiers())) {
                    arrayList2.add(JavaConstructor.forConstructor(constructors[i]));
                }
            }
            int size = arrayList2.size();
            if (size != 0) {
                if (size != 1) {
                    luaValue2 = NEW;
                    objForConstructors = JavaConstructor.forConstructors((JavaConstructor[]) arrayList2.toArray(new JavaConstructor[arrayList2.size()]));
                } else {
                    luaValue2 = NEW;
                    objForConstructors = arrayList2.get(0);
                }
                map2.put(luaValue2, objForConstructors);
            }
            for (Map.Entry entry : map.entrySet()) {
                String str = (String) entry.getKey();
                List list = (List) entry.getValue();
                map2.put(LuaValue.valueOf(str), list.size() == 1 ? list.get(0) : JavaMethod.forMethods((JavaMethod[]) list.toArray(new JavaMethod[list.size()])));
            }
            this.methods = map2;
        }
        return (LuaValue) this.methods.get(luaValue);
    }
}
