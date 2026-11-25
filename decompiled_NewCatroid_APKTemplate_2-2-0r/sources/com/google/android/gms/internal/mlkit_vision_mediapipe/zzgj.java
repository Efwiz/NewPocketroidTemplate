package com.google.android.gms.internal.mlkit_vision_mediapipe;

import io.netty.util.internal.StringUtil;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzgj {
    static String zza(zzgi zzgiVar, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("# ").append(str);
        zza(zzgiVar, sb, 0);
        return sb.toString();
    }

    /* JADX WARN: Code duplicated, block: B:84:0x01f2  */
    /* JADX WARN: Code duplicated, block: B:85:0x01f5  */
    private static void zza(zzgi zzgiVar, StringBuilder sb, int i) {
        boolean zEquals;
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        TreeSet<String> treeSet = new TreeSet();
        for (Method method : zzgiVar.getClass().getDeclaredMethods()) {
            map2.put(method.getName(), method);
            if (method.getParameterTypes().length == 0) {
                map.put(method.getName(), method);
                if (method.getName().startsWith("get")) {
                    treeSet.add(method.getName());
                }
            }
        }
        for (String str : treeSet) {
            String strSubstring = str.startsWith("get") ? str.substring(3) : str;
            boolean zBooleanValue = true;
            if (strSubstring.endsWith("List") && !strSubstring.endsWith("OrBuilderList") && !strSubstring.equals("List")) {
                String strValueOf = String.valueOf(strSubstring.substring(0, 1).toLowerCase());
                String strValueOf2 = String.valueOf(strSubstring.substring(1, strSubstring.length() - 4));
                String strConcat = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
                Method method2 = (Method) map.get(str);
                if (method2 != null && method2.getReturnType().equals(List.class)) {
                    zza(sb, i, zza(strConcat), zzfb.zza(method2, zzgiVar, new Object[0]));
                }
            }
            if (strSubstring.endsWith("Map") && !strSubstring.equals("Map")) {
                String strValueOf3 = String.valueOf(strSubstring.substring(0, 1).toLowerCase());
                String strValueOf4 = String.valueOf(strSubstring.substring(1, strSubstring.length() - 3));
                String strConcat2 = strValueOf4.length() != 0 ? strValueOf3.concat(strValueOf4) : new String(strValueOf3);
                Method method3 = (Method) map.get(str);
                if (method3 != null && method3.getReturnType().equals(Map.class) && !method3.isAnnotationPresent(Deprecated.class) && Modifier.isPublic(method3.getModifiers())) {
                    zza(sb, i, zza(strConcat2), zzfb.zza(method3, zzgiVar, new Object[0]));
                }
            }
            String strValueOf5 = String.valueOf(strSubstring);
            if (((Method) map2.get(strValueOf5.length() != 0 ? FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX.concat(strValueOf5) : new String(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX))) != null) {
                if (strSubstring.endsWith("Bytes")) {
                    String strValueOf6 = String.valueOf(strSubstring.substring(0, strSubstring.length() - 5));
                    if (!map.containsKey(strValueOf6.length() != 0 ? "get".concat(strValueOf6) : new String("get"))) {
                    }
                }
                String strValueOf7 = String.valueOf(strSubstring.substring(0, 1).toLowerCase());
                String strValueOf8 = String.valueOf(strSubstring.substring(1));
                String strConcat3 = strValueOf8.length() != 0 ? strValueOf7.concat(strValueOf8) : new String(strValueOf7);
                String strValueOf9 = String.valueOf(strSubstring);
                Method method4 = (Method) map.get(strValueOf9.length() != 0 ? "get".concat(strValueOf9) : new String("get"));
                String strValueOf10 = String.valueOf(strSubstring);
                Method method5 = (Method) map.get(strValueOf10.length() != 0 ? "has".concat(strValueOf10) : new String("has"));
                if (method4 != null) {
                    Object objZza = zzfb.zza(method4, zzgiVar, new Object[0]);
                    if (method5 == null) {
                        if (objZza instanceof Boolean) {
                            if (((Boolean) objZza).booleanValue()) {
                                zEquals = false;
                            } else {
                                zEquals = true;
                            }
                        } else if (objZza instanceof Integer) {
                            if (((Integer) objZza).intValue() == 0) {
                                zEquals = true;
                            } else {
                                zEquals = false;
                            }
                        } else if (objZza instanceof Float) {
                            if (((Float) objZza).floatValue() == 0.0f) {
                                zEquals = true;
                            } else {
                                zEquals = false;
                            }
                        } else if (objZza instanceof Double) {
                            if (((Double) objZza).doubleValue() == 0.0d) {
                                zEquals = true;
                            } else {
                                zEquals = false;
                            }
                        } else if (objZza instanceof String) {
                            zEquals = objZza.equals("");
                        } else if (objZza instanceof zzdu) {
                            zEquals = objZza.equals(zzdu.zza);
                        } else if (!(objZza instanceof zzgi) ? !((objZza instanceof Enum) && ((Enum) objZza).ordinal() == 0) : objZza != ((zzgi) objZza).zzh()) {
                            zEquals = false;
                        } else {
                            zEquals = true;
                        }
                        if (zEquals) {
                            zBooleanValue = false;
                        }
                    } else {
                        zBooleanValue = ((Boolean) zzfb.zza(method5, zzgiVar, new Object[0])).booleanValue();
                    }
                    if (zBooleanValue) {
                        zza(sb, i, zza(strConcat3), objZza);
                    }
                }
            }
        }
        if (zzgiVar instanceof zzfb.zzb) {
            Iterator<Map.Entry<T, Object>> itZzd = ((zzfb.zzb) zzgiVar).zzc.zzd();
            if (itZzd.hasNext()) {
                throw new NoSuchMethodError();
            }
        }
        zzfb zzfbVar = (zzfb) zzgiVar;
        if (zzfbVar.zzb != null) {
            zzfbVar.zzb.zza(sb, i);
        }
    }

    static final void zza(StringBuilder sb, int i, String str, Object obj) {
        if (obj instanceof List) {
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                zza(sb, i, str, it.next());
            }
            return;
        }
        if (obj instanceof Map) {
            Iterator it2 = ((Map) obj).entrySet().iterator();
            while (it2.hasNext()) {
                zza(sb, i, str, (Map.Entry) it2.next());
            }
            return;
        }
        sb.append('\n');
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            sb.append(StringUtil.SPACE);
        }
        sb.append(str);
        if (obj instanceof String) {
            sb.append(": \"").append(zzhl.zza(zzdu.zza((String) obj))).append('\"');
            return;
        }
        if (obj instanceof zzdu) {
            sb.append(": \"").append(zzhl.zza((zzdu) obj)).append('\"');
            return;
        }
        if (obj instanceof zzfb) {
            sb.append(" {");
            zza((zzfb) obj, sb, i + 2);
            sb.append("\n");
            while (i2 < i) {
                sb.append(StringUtil.SPACE);
                i2++;
            }
            sb.append("}");
            return;
        }
        if (obj instanceof Map.Entry) {
            sb.append(" {");
            Map.Entry entry = (Map.Entry) obj;
            int i4 = i + 2;
            zza(sb, i4, ASTExpr.DEFAULT_MAP_KEY_NAME, entry.getKey());
            zza(sb, i4, "value", entry.getValue());
            sb.append("\n");
            while (i2 < i) {
                sb.append(StringUtil.SPACE);
                i2++;
            }
            sb.append("}");
            return;
        }
        sb.append(": ").append(obj.toString());
    }

    private static final String zza(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (Character.isUpperCase(cCharAt)) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(cCharAt));
        }
        return sb.toString();
    }
}
