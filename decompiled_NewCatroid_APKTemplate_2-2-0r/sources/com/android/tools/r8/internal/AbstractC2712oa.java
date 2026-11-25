package com.android.tools.r8.internal;

import androidx.exifinterface.media.ExifInterface;
import java.util.LinkedHashMap;
import java.util.List;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oa, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2712oa {
    private static final String a = AbstractC0823Ab.a(AbstractC0823Ab.a('k', 'o', Character.valueOf(Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL), 'l', 'i', 'n'), "", null, null, null, 62);
    private static final LinkedHashMap b;

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        List listA = AbstractC0823Ab.a("Boolean", "Z", "Char", "C", "Byte", "B", "Short", ExifInterface.LATITUDE_SOUTH, "Int", "I", "Float", "F", "Long", "J", "Double", "D");
        int iA = DN.a(0, listA.size() - 1, 2);
        if (iA >= 0) {
            int i = 0;
            while (true) {
                StringBuilder sb = new StringBuilder();
                String str = a;
                int i2 = i + 1;
                linkedHashMap.put(sb.append(str).append('/').append((String) listA.get(i)).toString(), listA.get(i2));
                linkedHashMap.put(str + '/' + ((String) listA.get(i)) + "Array", "[" + ((String) listA.get(i2)));
                if (i == iA) {
                    break;
                } else {
                    i += 2;
                }
            }
        }
        linkedHashMap.put(a + "/Unit", ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        a(linkedHashMap, "Any", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME);
        a(linkedHashMap, "Nothing", "java/lang/Void");
        a(linkedHashMap, "Annotation", "java/lang/annotation/Annotation");
        for (String str2 : AbstractC0823Ab.a(CommonClassNames.JAVA_LANG_STRING_SHORT, "CharSequence", "Throwable", "Cloneable", "Number", "Comparable", "Enum")) {
            a(linkedHashMap, str2, "java/lang/" + str2);
        }
        for (String str3 : AbstractC0823Ab.a("Iterator", "Collection", "List", "Set", "Map", "ListIterator")) {
            a(linkedHashMap, "collections/" + str3, "java/util/" + str3);
            a(linkedHashMap, "collections/Mutable" + str3, "java/util/" + str3);
        }
        a(linkedHashMap, "collections/Iterable", "java/lang/Iterable");
        a(linkedHashMap, "collections/MutableIterable", "java/lang/Iterable");
        a(linkedHashMap, "collections/Map.Entry", "java/util/Map$Entry");
        a(linkedHashMap, "collections/MutableMap.MutableEntry", "java/util/Map$Entry");
        for (int i3 = 0; i3 < 23; i3++) {
            String strA = AbstractC1857dg.a("Function", i3);
            StringBuilder sb2 = new StringBuilder();
            String str4 = a;
            a(linkedHashMap, strA, sb2.append(str4).append("/jvm/functions/Function").append(i3).toString());
            a(linkedHashMap, AbstractC1857dg.a("reflect/KFunction", i3), str4 + "/reflect/KFunction");
        }
        for (String str5 : AbstractC0823Ab.a("Char", "Byte", "Short", "Int", "Float", "Long", "Double", CommonClassNames.JAVA_LANG_STRING_SHORT, "Enum")) {
            a(linkedHashMap, str5 + ".Companion", a + "/jvm/internal/" + str5 + "CompanionObject");
        }
        b = linkedHashMap;
    }

    private static final void a(LinkedHashMap linkedHashMap, String str, String str2) {
        linkedHashMap.put(a + '/' + str, "L" + str2 + ';');
    }

    public static final String a(String str) {
        AbstractC0936Ds.c(str, "classId");
        String str2 = (String) b.get(str);
        return str2 == null ? "L" + AbstractC2710oY.a(str, '.', '$') + ';' : str2;
    }
}
