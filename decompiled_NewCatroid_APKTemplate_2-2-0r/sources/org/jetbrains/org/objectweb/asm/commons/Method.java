package org.jetbrains.org.objectweb.asm.commons;

import androidx.exifinterface.media.ExifInterface;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes6.dex */
public class Method {
    private static final Map<String, String> PRIMITIVE_TYPE_DESCRIPTORS;
    private final String descriptor;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f1008name;

    static {
        HashMap map = new HashMap();
        map.put(PsiKeyword.VOID, ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        map.put("byte", "B");
        map.put(PsiKeyword.CHAR, "C");
        map.put("double", "D");
        map.put("float", "F");
        map.put("int", "I");
        map.put("long", "J");
        map.put("short", ExifInterface.LATITUDE_SOUTH);
        map.put("boolean", "Z");
        PRIMITIVE_TYPE_DESCRIPTORS = map;
    }

    public Method(String str, String str2) {
        this.f1008name = str;
        this.descriptor = str2;
    }

    public Method(String str, Type type, Type[] typeArr) {
        this(str, Type.getMethodDescriptor(type, typeArr));
    }

    public String getName() {
        return this.f1008name;
    }

    public String getDescriptor() {
        return this.descriptor;
    }

    public Type getReturnType() {
        return Type.getReturnType(this.descriptor);
    }

    public Type[] getArgumentTypes() {
        return Type.getArgumentTypes(this.descriptor);
    }

    public String toString() {
        return this.f1008name + this.descriptor;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Method)) {
            return false;
        }
        Method method = (Method) obj;
        return this.f1008name.equals(method.f1008name) && this.descriptor.equals(method.descriptor);
    }

    public int hashCode() {
        return this.f1008name.hashCode() ^ this.descriptor.hashCode();
    }
}
