package org.jetbrains.kotlin.konan.properties;

import io.netty.util.internal.StringUtil;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.transport.WalkEncryption;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.util.UtilKt;

/* JADX INFO: compiled from: Properties.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010#\n\u0002\b\t\u001a\u0012\u0010\u0000\u001a\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\"\u0010\u0005\u001a\u00020\u0006*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0007\u001a\u00020\u00042\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0004\u001a\u000e\u0010\t\u001a\u00020\n*\u00060\u0001j\u0002`\u0002\u001a\u000e\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002*\u00020\u000b\u001a2\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\r*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0007\u001a\u00020\u00042\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u0006\u001a$\u0010\u000f\u001a\u0004\u0018\u00010\u0004*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0007\u001a\u00020\u00042\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0004\u001aB\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\r*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0007\u001a\u00020\u00042\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u00062\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u0012\u001a4\u0010\u0013\u001a\u0004\u0018\u00010\u0004*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0007\u001a\u00020\u00042\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00042\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u0012\u001a.\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\r*\u00020\u00042\n\u0010\u0015\u001a\u00060\u0001j\u0002`\u00022\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u0012H\u0002\u001a\u0016\u0010\u0016\u001a\u00020\n*\u00020\u000b2\n\u0010\u0015\u001a\u00060\u0001j\u0002`\u0002\u001a\u0016\u0010\u0017\u001a\u00020\n*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0018\u001a\u00020\u000b\u001a\u0014\u0010\b\u001a\u00020\u0004*\u00020\u00042\b\u0010\u0019\u001a\u0004\u0018\u00010\u0004*\n\u0010\u001a\"\u00020\u00012\u00020\u0001¨\u0006\u001b"}, d2 = {"loadProperties", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "path", "", "hasProperty", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "suffix", "keepOnlyDefaultProfiles", "", "Lorg/jetbrains/kotlin/konan/file/File;", "propertyList", "", "escapeInQuotes", "propertyString", "resolvablePropertyList", "visitedProperties", "", "resolvablePropertyString", "resolveValue", "properties", "saveProperties", "saveToFile", "file", "suf", "Properties", "kotlin-util-io"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class PropertiesKt {
    public static final Properties loadProperties(File file) throws Exception {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Properties properties = new Properties();
        BufferedReader bufferedReader = file.bufferedReader();
        try {
            try {
                properties.load(bufferedReader);
                Unit unit = Unit.INSTANCE;
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                return properties;
            } catch (Throwable th) {
                if (0 == 0 && bufferedReader != null) {
                    bufferedReader.close();
                }
                throw th;
            }
        } catch (Exception e) {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception unused) {
                }
            }
            throw e;
        }
    }

    public static final Properties loadProperties(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return loadProperties(new File(path));
    }

    public static final void saveProperties(File file, Properties properties) throws Exception {
        Intrinsics.checkNotNullParameter(file, "<this>");
        Intrinsics.checkNotNullParameter(properties, "properties");
        StringWriter stringWriter = new StringWriter();
        properties.store(stringWriter, (String) null);
        String string = stringWriter.toString();
        Intrinsics.checkNotNullExpressionValue(string, "StringWriter().apply {\n …s, null)\n    }.toString()");
        String strLineSeparator = System.lineSeparator();
        Intrinsics.checkNotNullExpressionValue(strLineSeparator, "lineSeparator()");
        boolean z = false;
        List listSplit$default = StringsKt.split$default((CharSequence) string, new String[]{strLineSeparator}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listSplit$default) {
            String str = (String) obj;
            if (str.length() != 0 && !StringsKt.startsWith$default(str, "#", false, 2, (Object) null)) {
                arrayList.add(obj);
            }
        }
        List listSorted = CollectionsKt.sorted(arrayList);
        OutputStream outputStream = file.outputStream();
        try {
            byte[] bytes = CollectionsKt.joinToString$default(listSorted, "\n", null, "\n", 0, null, null, 58, null).getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            outputStream.write(bytes);
            Unit unit = Unit.INSTANCE;
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (Exception e) {
            if (outputStream != null) {
                try {
                    try {
                        outputStream.close();
                    } catch (Throwable th) {
                        z = true;
                        th = th;
                        if (!z && outputStream != null) {
                            outputStream.close();
                        }
                        throw th;
                    }
                } catch (Exception unused) {
                }
            }
            throw e;
        } catch (Throwable th2) {
            th = th2;
            if (!z) {
                outputStream.close();
            }
            throw th;
        }
    }

    public static final void saveToFile(Properties properties, File file) throws Exception {
        Intrinsics.checkNotNullParameter(properties, "<this>");
        Intrinsics.checkNotNullParameter(file, "file");
        saveProperties(file, properties);
    }

    public static final String propertyString(Properties properties, String key, String str) {
        Intrinsics.checkNotNullParameter(properties, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        String property = properties.getProperty(suffix(key, str));
        return property == null ? properties.getProperty(key) : property;
    }

    public static /* synthetic */ String propertyString$default(Properties properties, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        return propertyString(properties, str, str2);
    }

    public static /* synthetic */ List propertyList$default(Properties properties, String str, String str2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return propertyList(properties, str, str2, z);
    }

    public static final List<String> propertyList(Properties properties, String key, String str, boolean z) {
        String string;
        Intrinsics.checkNotNullParameter(properties, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        String property = properties.getProperty(suffix(key, str));
        if (property == null) {
            property = properties.getProperty(key);
        }
        if (property != null) {
            String str2 = property;
            int length = str2.length() - 1;
            int i = 0;
            boolean z2 = false;
            while (i <= length) {
                boolean zIsWhitespace = CharsKt.isWhitespace(str2.charAt(!z2 ? i : length));
                if (z2) {
                    if (!zIsWhitespace) {
                        break;
                    }
                    length--;
                } else if (zIsWhitespace) {
                    i++;
                } else {
                    z2 = true;
                }
            }
            string = str2.subSequence(i, length + 1).toString();
        } else {
            string = null;
        }
        String str3 = string;
        if (str3 == null || str3.length() == 0) {
            return CollectionsKt.emptyList();
        }
        if (z) {
            return UtilKt.parseSpaceSeparatedArgs(string);
        }
        return new Regex(WalkEncryption.Vals.REGEX_WS).split(str3, 0);
    }

    public static /* synthetic */ boolean hasProperty$default(Properties properties, String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        return hasProperty(properties, str, str2);
    }

    public static final boolean hasProperty(Properties properties, String key, String str) {
        Intrinsics.checkNotNullParameter(properties, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        return properties.getProperty(suffix(key, str)) != null;
    }

    public static final String suffix(String str, String str2) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return str2 == null ? str : str + '.' + str2;
    }

    public static final void keepOnlyDefaultProfiles(Properties properties) {
        Intrinsics.checkNotNullParameter(properties, "<this>");
        String property = properties.getProperty("dependencyProfiles");
        if (Intrinsics.areEqual(property, "default alt")) {
            properties.setProperty("dependencyProfiles", "default");
            return;
        }
        throw new IllegalStateException(("unexpected dependencyProfiles value: expected 'default alt', got '" + property + '\'').toString());
    }

    public static /* synthetic */ List resolvablePropertyList$default(Properties properties, String str, String str2, boolean z, Set set, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            set = new LinkedHashSet();
        }
        return resolvablePropertyList(properties, str, str2, z, set);
    }

    public static final List<String> resolvablePropertyList(Properties properties, String key, String str, boolean z, Set<String> visitedProperties) {
        Intrinsics.checkNotNullParameter(properties, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(visitedProperties, "visitedProperties");
        List<String> listPropertyList = propertyList(properties, key, str, z);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = listPropertyList.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, resolveValue((String) it.next(), properties, CollectionsKt.toMutableSet(visitedProperties)));
        }
        return arrayList;
    }

    public static /* synthetic */ String resolvablePropertyString$default(Properties properties, String str, String str2, Set set, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        if ((i & 4) != 0) {
            set = new LinkedHashSet();
        }
        return resolvablePropertyString(properties, str, str2, set);
    }

    public static final String resolvablePropertyString(Properties properties, String key, String str, Set<String> visitedProperties) {
        List listSplit$default;
        Intrinsics.checkNotNullParameter(properties, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(visitedProperties, "visitedProperties");
        String strPropertyString = propertyString(properties, key, str);
        if (strPropertyString == null || (listSplit$default = StringsKt.split$default((CharSequence) strPropertyString, new char[]{StringUtil.SPACE}, false, 0, 6, (Object) null)) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, resolveValue((String) it.next(), properties, visitedProperties));
        }
        return CollectionsKt.joinToString$default(arrayList, " ", null, null, 0, null, null, 62, null);
    }

    private static final List<String> resolveValue(String str, Properties properties, Set<String> set) {
        String string;
        String strSubstring;
        if (StringsKt.contains$default((CharSequence) str, (CharSequence) "$", false, 2, (Object) null)) {
            String strSubstringBefore = StringsKt.substringBefore(str, '$', "");
            String strSubstringAfter$default = StringsKt.substringAfter$default(str, '$', (String) null, 2, (Object) null);
            String strSubstringBefore$default = StringsKt.substringBefore$default(strSubstringAfter$default, '/', (String) null, 2, (Object) null);
            String strSubstringAfter = StringsKt.substringAfter(strSubstringAfter$default, '/', "");
            if (!set.add(strSubstringBefore$default)) {
                throw new IllegalStateException(("Circular dependency: " + CollectionsKt.joinToString$default(set, null, null, null, 0, null, null, 63, null)).toString());
            }
            List<String> listResolvablePropertyList$default = resolvablePropertyList$default(properties, strSubstringBefore$default, null, false, set, 6, null);
            if (listResolvablePropertyList$default.size() > 1) {
                if (strSubstringAfter.length() > 0) {
                    throw new IllegalStateException(("Cannot append `/" + strSubstringAfter + "` to multiple values: " + CollectionsKt.joinToString$default(listResolvablePropertyList$default, null, null, null, 0, null, null, 63, null)).toString());
                }
                if (strSubstringBefore.length() <= 0) {
                    return listResolvablePropertyList$default;
                }
                throw new IllegalStateException(("Cannot add prefix `" + strSubstringBefore + "` to multiple values: " + CollectionsKt.joinToString$default(listResolvablePropertyList$default, null, null, null, 0, null, null, 63, null)).toString());
            }
            List<String> list = listResolvablePropertyList$default;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (String str2 : list) {
                if (strSubstringAfter.length() > 0) {
                    StringBuilder sbAppend = new StringBuilder().append(strSubstringBefore);
                    int lastIndex = StringsKt.getLastIndex(str2);
                    while (true) {
                        if (-1 >= lastIndex) {
                            strSubstring = "";
                            break;
                        }
                        if (str2.charAt(lastIndex) != '/') {
                            strSubstring = str2.substring(0, lastIndex + 1);
                            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                            break;
                        }
                        lastIndex--;
                    }
                    string = sbAppend.append(strSubstring).append('/').append(strSubstringAfter).toString();
                } else {
                    string = strSubstringBefore + str2;
                }
                arrayList.add(string);
            }
            return arrayList;
        }
        return CollectionsKt.listOf(str);
    }
}
