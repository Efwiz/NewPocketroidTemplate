package org.jetbrains.kotlin.konan.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.konan.target.KonanTarget;
import org.jetbrains.kotlin.util.UtilKt;

/* JADX INFO: compiled from: DefFile.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\b\u0004\u001a\u0018\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a>\u0010\u0006\u001a\u001a\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u00072\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0\fH\u0002\u001a\f\u0010\r\u001a\u00020\b*\u00020\bH\u0002\u001a\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\t*\u00020\b2\u0006\u0010\u000f\u001a\u00020\nH\u0002¨\u0006\u0010"}, d2 = {"DefFile", "Lorg/jetbrains/kotlin/konan/util/DefFile;", "file", "Ljava/io/File;", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "parseDefFile", "Lkotlin/Triple;", "Ljava/util/Properties;", "", "", "substitutions", "", "duplicate", "getSpaceSeparated", "name", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class DefFileKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final List<String> getSpaceSeparated(Properties properties, String str) {
        List<String> spaceSeparatedArgs;
        String property = properties.getProperty(str);
        return (property == null || (spaceSeparatedArgs = UtilKt.parseSpaceSeparatedArgs(property)) == null) ? CollectionsKt.emptyList() : spaceSeparatedArgs;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Triple<Properties, Properties, List<String>> parseDefFile(File file, Map<String, String> map) throws IOException {
        List listEmptyList;
        Properties properties = new Properties();
        if (file == null) {
            return new Triple<>(properties, new Properties(), CollectionsKt.emptyList());
        }
        List lines$default = FilesKt.readLines$default(file, null, 1, null);
        int iIndexOf = lines$default.indexOf("---");
        int i = 0;
        if (iIndexOf != -1) {
            List listSubList = lines$default.subList(0, iIndexOf);
            listEmptyList = lines$default.subList(iIndexOf + 1, lines$default.size());
            lines$default = listSubList;
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        List list = lines$default;
        String strLineSeparator = System.lineSeparator();
        Intrinsics.checkNotNullExpressionValue(strLineSeparator, "lineSeparator()");
        List listSplit$default = StringsKt.split$default((CharSequence) CollectionsKt.joinToString$default(list, strLineSeparator, null, null, 0, null, null, 62, null), new char[]{'\"'}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
        for (Object obj : listSplit$default) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String strReplace = (String) obj;
            if (i % 2 != 0) {
                strReplace = new Regex("\\\\(?=.)").replace(strReplace, Regex.INSTANCE.escapeReplacement("\\\\"));
            }
            arrayList.add(strReplace);
            i = i2;
        }
        properties.load(new StringReader(CollectionsKt.joinToString$default(arrayList, "\"", null, null, 0, null, null, 62, null)));
        Properties propertiesDuplicate = duplicate(properties);
        SubstitutionKt.substitute(properties, map);
        return new Triple<>(properties, propertiesDuplicate, listEmptyList);
    }

    private static final Properties duplicate(Properties properties) {
        Properties properties2 = new Properties();
        properties2.putAll(properties);
        return properties2;
    }

    public static final DefFile DefFile(File file, KonanTarget target) {
        Intrinsics.checkNotNullParameter(target, "target");
        return new DefFile(file, SubstitutionKt.defaultTargetSubstitutions(target));
    }
}
