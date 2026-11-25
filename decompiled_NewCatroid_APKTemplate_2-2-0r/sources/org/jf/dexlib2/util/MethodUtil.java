package org.jf.dexlib2.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import org.jf.dexlib2.AccessFlags;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.util.CharSequenceUtils;

/* JADX INFO: loaded from: classes6.dex */
public final class MethodUtil {
    private static int directMask = (AccessFlags.STATIC.getValue() | AccessFlags.PRIVATE.getValue()) | AccessFlags.CONSTRUCTOR.getValue();
    public static Predicate<Method> METHOD_IS_DIRECT = new Predicate<Method>() { // from class: org.jf.dexlib2.util.MethodUtil.1
        @Override // java.util.function.Predicate
        public boolean test(Method method) {
            return method != null && MethodUtil.isDirect(method);
        }
    };
    public static Predicate<Method> METHOD_IS_VIRTUAL = new Predicate<Method>() { // from class: org.jf.dexlib2.util.MethodUtil.2
        @Override // java.util.function.Predicate
        public boolean test(Method method) {
            return (method == null || MethodUtil.isDirect(method)) ? false : true;
        }
    };

    public static boolean isDirect(Method method) {
        return (method.getAccessFlags() & directMask) != 0;
    }

    public static boolean isStatic(Method method) {
        return AccessFlags.STATIC.isSet(method.getAccessFlags());
    }

    public static boolean isConstructor(MethodReference methodReference) {
        return methodReference.getName().equals("<init>");
    }

    public static boolean isPackagePrivate(Method method) {
        return (method.getAccessFlags() & ((AccessFlags.PRIVATE.getValue() | AccessFlags.PROTECTED.getValue()) | AccessFlags.PUBLIC.getValue())) == 0;
    }

    public static int getParameterRegisterCount(Method method) {
        return getParameterRegisterCount(method, isStatic(method));
    }

    public static int getParameterRegisterCount(MethodReference methodReference, boolean z) {
        return getParameterRegisterCount(methodReference.getParameterTypes(), z);
    }

    public static int getParameterRegisterCount(Collection<? extends CharSequence> collection, boolean z) {
        Iterator<? extends CharSequence> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            char cCharAt = it.next().charAt(0);
            i = (cCharAt == 'J' || cCharAt == 'D') ? i + 2 : i + 1;
        }
        return !z ? i + 1 : i;
    }

    private static char getShortyType(CharSequence charSequence) {
        if (charSequence.length() > 1) {
            return 'L';
        }
        return charSequence.charAt(0);
    }

    public static String getShorty(Collection<? extends CharSequence> collection, String str) {
        StringBuilder sb = new StringBuilder(collection.size() + 1);
        sb.append(getShortyType(str));
        Iterator<? extends CharSequence> it = collection.iterator();
        while (it.hasNext()) {
            sb.append(getShortyType(it.next()));
        }
        return sb.toString();
    }

    public static boolean methodSignaturesMatch(MethodReference methodReference, MethodReference methodReference2) {
        return methodReference.getName().equals(methodReference2.getName()) && methodReference.getReturnType().equals(methodReference2.getReturnType()) && CharSequenceUtils.listEquals(methodReference.getParameterTypes(), methodReference2.getParameterTypes());
    }

    private MethodUtil() {
    }
}
