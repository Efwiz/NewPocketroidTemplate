package com.google.android.gms.internal.mlkit_vision_internal_vkp;

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

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaph {
    static String zza(zzapf zzapfVar, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("# ");
        sb.append(str);
        zzd(zzapfVar, sb, 0);
        return sb.toString();
    }

    static final void zzb(StringBuilder sb, int i, String str, Object obj) {
        if (obj instanceof List) {
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                zzb(sb, i, str, it.next());
            }
            return;
        }
        if (obj instanceof Map) {
            Iterator it2 = ((Map) obj).entrySet().iterator();
            while (it2.hasNext()) {
                zzb(sb, i, str, (Map.Entry) it2.next());
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
            sb.append(": \"");
            sb.append(zzaqo.zza(zzamk.zzt((String) obj)));
            sb.append('\"');
            return;
        }
        if (obj instanceof zzamk) {
            sb.append(": \"");
            sb.append(zzaqo.zza((zzamk) obj));
            sb.append('\"');
            return;
        }
        if (obj instanceof zzans) {
            sb.append(" {");
            zzd((zzans) obj, sb, i + 2);
            sb.append("\n");
            while (i2 < i) {
                sb.append(StringUtil.SPACE);
                i2++;
            }
            sb.append("}");
            return;
        }
        if (!(obj instanceof Map.Entry)) {
            sb.append(": ");
            sb.append(obj.toString());
            return;
        }
        sb.append(" {");
        Map.Entry entry = (Map.Entry) obj;
        int i4 = i + 2;
        zzb(sb, i4, ASTExpr.DEFAULT_MAP_KEY_NAME, entry.getKey());
        zzb(sb, i4, "value", entry.getValue());
        sb.append("\n");
        while (i2 < i) {
            sb.append(StringUtil.SPACE);
            i2++;
        }
        sb.append("}");
    }

    private static final String zzc(String str) {
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

    private static void zzd(zzapf zzapfVar, StringBuilder sb, int i) {
        boolean zEquals;
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        TreeSet<String> treeSet = new TreeSet();
        for (Method method : zzapfVar.getClass().getDeclaredMethods()) {
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
            if (strSubstring.endsWith("List") && !strSubstring.endsWith("OrBuilderList") && !strSubstring.equals("List")) {
                String strValueOf = String.valueOf(strSubstring.substring(0, 1).toLowerCase());
                String strValueOf2 = String.valueOf(strSubstring.substring(1, strSubstring.length() - 4));
                String strConcat = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
                Method method2 = (Method) map.get(str);
                if (method2 != null && method2.getReturnType().equals(List.class)) {
                    zzb(sb, i, zzc(strConcat), zzans.zzK(method2, zzapfVar, new Object[0]));
                }
            }
            if (strSubstring.endsWith("Map") && !strSubstring.equals("Map")) {
                String strValueOf3 = String.valueOf(strSubstring.substring(0, 1).toLowerCase());
                String strValueOf4 = String.valueOf(strSubstring.substring(1, strSubstring.length() - 3));
                String strConcat2 = strValueOf4.length() != 0 ? strValueOf3.concat(strValueOf4) : new String(strValueOf3);
                Method method3 = (Method) map.get(str);
                if (method3 != null && method3.getReturnType().equals(Map.class) && !method3.isAnnotationPresent(Deprecated.class) && Modifier.isPublic(method3.getModifiers())) {
                    zzb(sb, i, zzc(strConcat2), zzans.zzK(method3, zzapfVar, new Object[0]));
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
                    Object objZzK = zzans.zzK(method4, zzapfVar, new Object[0]);
                    if (method5 == null) {
                        if (objZzK instanceof Boolean) {
                            if (((Boolean) objZzK).booleanValue()) {
                                zzb(sb, i, zzc(strConcat3), objZzK);
                            }
                        } else if (objZzK instanceof Integer) {
                            if (((Integer) objZzK).intValue() != 0) {
                                zzb(sb, i, zzc(strConcat3), objZzK);
                            }
                        } else if (objZzK instanceof Float) {
                            if (((Float) objZzK).floatValue() != 0.0f) {
                                zzb(sb, i, zzc(strConcat3), objZzK);
                            }
                        } else if (!(objZzK instanceof Double)) {
                            if (objZzK instanceof String) {
                                zEquals = objZzK.equals("");
                            } else if (objZzK instanceof zzamk) {
                                zEquals = objZzK.equals(zzamk.zzb);
                            } else if (objZzK instanceof zzapf) {
                                if (objZzK != ((zzapf) objZzK).zzv()) {
                                    zzb(sb, i, zzc(strConcat3), objZzK);
                                }
                            } else if (!(objZzK instanceof Enum) || ((Enum) objZzK).ordinal() != 0) {
                                zzb(sb, i, zzc(strConcat3), objZzK);
                            }
                            if (!zEquals) {
                                zzb(sb, i, zzc(strConcat3), objZzK);
                            }
                        } else if (((Double) objZzK).doubleValue() != 0.0d) {
                            zzb(sb, i, zzc(strConcat3), objZzK);
                        }
                    } else if (((Boolean) zzans.zzK(method5, zzapfVar, new Object[0])).booleanValue()) {
                        zzb(sb, i, zzc(strConcat3), objZzK);
                    }
                }
            }
        }
        if (zzapfVar instanceof zzano) {
            Iterator<Map.Entry<T, Object>> itZzh = ((zzano) zzapfVar).zzb.zzh();
            if (itZzh.hasNext()) {
                throw null;
            }
        }
        zzaqu zzaquVar = ((zzans) zzapfVar).zzc;
        if (zzaquVar != null) {
            zzaquVar.zzg(sb, i);
        }
    }
}
