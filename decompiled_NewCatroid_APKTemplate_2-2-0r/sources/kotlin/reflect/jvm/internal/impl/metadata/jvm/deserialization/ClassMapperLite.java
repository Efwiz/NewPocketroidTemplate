package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import androidx.exifinterface.media.ExifInterface;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: compiled from: ClassMapperLite.kt */
/* JADX INFO: loaded from: classes12.dex */
public final class ClassMapperLite {
    public static final ClassMapperLite INSTANCE = new ClassMapperLite();

    /* JADX INFO: renamed from: kotlin, reason: collision with root package name */
    private static final String f308kotlin = CollectionsKt.joinToString$default(CollectionsKt.listOf((Object[]) new Character[]{'k', 'o', Character.valueOf(Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL), 'l', 'i', 'n'}), "", null, null, 0, null, null, 62, null);
    private static final Map<String, String> map;

    private ClassMapperLite() {
    }

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        List listListOf = CollectionsKt.listOf((Object[]) new String[]{"Boolean", "Z", "Char", "C", "Byte", "B", "Short", ExifInterface.LATITUDE_SOUTH, "Int", "I", "Float", "F", "Long", "J", "Double", "D"});
        int progressionLastElement = ProgressionUtilKt.getProgressionLastElement(0, listListOf.size() - 1, 2);
        if (progressionLastElement >= 0) {
            int i = 0;
            while (true) {
                int i2 = i + 2;
                StringBuilder sb = new StringBuilder();
                String str = f308kotlin;
                int i3 = i + 1;
                linkedHashMap.put(sb.append(str).append('/').append((String) listListOf.get(i)).toString(), listListOf.get(i3));
                linkedHashMap.put(str + '/' + ((String) listListOf.get(i)) + "Array", Intrinsics.stringPlus("[", listListOf.get(i3)));
                if (i == progressionLastElement) {
                    break;
                } else {
                    i = i2;
                }
            }
        }
        linkedHashMap.put(Intrinsics.stringPlus(f308kotlin, "/Unit"), ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        m4182map$lambda0$add(linkedHashMap, "Any", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME);
        m4182map$lambda0$add(linkedHashMap, "Nothing", "java/lang/Void");
        m4182map$lambda0$add(linkedHashMap, "Annotation", "java/lang/annotation/Annotation");
        for (String str2 : CollectionsKt.listOf((Object[]) new String[]{CommonClassNames.JAVA_LANG_STRING_SHORT, "CharSequence", "Throwable", "Cloneable", "Number", "Comparable", "Enum"})) {
            m4182map$lambda0$add(linkedHashMap, str2, Intrinsics.stringPlus("java/lang/", str2));
        }
        for (String str3 : CollectionsKt.listOf((Object[]) new String[]{"Iterator", "Collection", "List", "Set", "Map", "ListIterator"})) {
            m4182map$lambda0$add(linkedHashMap, Intrinsics.stringPlus("collections/", str3), Intrinsics.stringPlus("java/util/", str3));
            m4182map$lambda0$add(linkedHashMap, Intrinsics.stringPlus("collections/Mutable", str3), Intrinsics.stringPlus("java/util/", str3));
        }
        m4182map$lambda0$add(linkedHashMap, "collections/Iterable", "java/lang/Iterable");
        m4182map$lambda0$add(linkedHashMap, "collections/MutableIterable", "java/lang/Iterable");
        m4182map$lambda0$add(linkedHashMap, "collections/Map.Entry", "java/util/Map$Entry");
        m4182map$lambda0$add(linkedHashMap, "collections/MutableMap.MutableEntry", "java/util/Map$Entry");
        for (int i4 = 0; i4 < 23; i4++) {
            String strStringPlus = Intrinsics.stringPlus("Function", Integer.valueOf(i4));
            StringBuilder sb2 = new StringBuilder();
            String str4 = f308kotlin;
            m4182map$lambda0$add(linkedHashMap, strStringPlus, sb2.append(str4).append("/jvm/functions/Function").append(i4).toString());
            m4182map$lambda0$add(linkedHashMap, Intrinsics.stringPlus("reflect/KFunction", Integer.valueOf(i4)), Intrinsics.stringPlus(str4, "/reflect/KFunction"));
        }
        for (String str5 : CollectionsKt.listOf((Object[]) new String[]{"Char", "Byte", "Short", "Int", "Float", "Long", "Double", CommonClassNames.JAVA_LANG_STRING_SHORT, "Enum"})) {
            m4182map$lambda0$add(linkedHashMap, Intrinsics.stringPlus(str5, ".Companion"), f308kotlin + "/jvm/internal/" + str5 + "CompanionObject");
        }
        map = linkedHashMap;
    }

    /* JADX INFO: renamed from: map$lambda-0$add, reason: not valid java name */
    private static final void m4182map$lambda0$add(Map<String, String> map2, String str, String str2) {
        map2.put(f308kotlin + '/' + str, "L" + str2 + ';');
    }

    @JvmStatic
    public static final String mapClass(String classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        String str = map.get(classId);
        return str == null ? "L" + StringsKt.replace$default(classId, '.', '$', false, 4, (Object) null) + ';' : str;
    }
}
