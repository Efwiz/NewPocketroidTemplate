package org.jetbrains.kotlin.codegen.inline;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.dex.sections.Marker;
import io.netty.util.internal.StringUtil;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: SMAPParser.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0007J\u0010\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J$\u0010\r\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\b\u0010\u000f\u001a\u0004\u0018\u00010\u0004H\u0002¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/SMAPParser;", "", "()V", "parseOrCreateDefault", "Lorg/jetbrains/kotlin/codegen/inline/SMAP;", "mappingInfo", "", FirebaseAnalytics.Param.SOURCE, "path", "methodStartLine", "", "methodEndLine", "parseOrNull", "parseStratum", "stratum", "callSites", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SMAPParser {
    public static final SMAPParser INSTANCE = new SMAPParser();

    private SMAPParser() {
    }

    @JvmStatic
    public static final SMAP parseOrCreateDefault(String mappingInfo, String source, String path, int methodStartLine, int methodEndLine) {
        SMAP orNull;
        Intrinsics.checkNotNullParameter(path, "path");
        if (mappingInfo != null && mappingInfo.length() > 0 && (orNull = INSTANCE.parseOrNull(mappingInfo)) != null) {
            return orNull;
        }
        if (source == null || source.length() == 0 || methodStartLine > methodEndLine) {
            return new SMAP(CollectionsKt.emptyList());
        }
        FileMapping fileMapping = new FileMapping(source, path);
        FileMapping.mapNewInterval$default(fileMapping, methodStartLine, methodStartLine, (methodEndLine - methodStartLine) + 1, null, 8, null);
        return new SMAP(CollectionsKt.listOf(fileMapping));
    }

    public final SMAP parseOrNull(String mappingInfo) {
        Intrinsics.checkNotNullParameter(mappingInfo, "mappingInfo");
        return parseStratum(mappingInfo, "Kotlin", parseStratum(mappingInfo, SMAPKt.KOTLIN_DEBUG_STRATA_NAME, null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r10v5 */
    private final SMAP parseStratum(String mappingInfo, final String stratum, SMAP callSites) {
        ?? r10;
        int i;
        int iIndexOf$default;
        int i2;
        RangeMapping rangeMappingFindRange;
        String strSubstring;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = SequencesKt.drop(SequencesKt.dropWhile(StringsKt.lineSequence(mappingInfo), new Function1<String, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.SMAPParser$parseStratum$iterator$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(String it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(!Intrinsics.areEqual(it2, "*S " + stratum));
            }
        }), 1).iterator();
        SMAP smap = null;
        if (!it.hasNext() || !Intrinsics.areEqual(it.next(), SMAP.FILE_SECTION)) {
            return null;
        }
        while (true) {
            r10 = 0;
            i = 2;
            if (!it.hasNext()) {
                break;
            }
            String str = (String) it.next();
            if (Intrinsics.areEqual(str, SMAP.LINE_SECTION)) {
                break;
            }
            if (Intrinsics.areEqual(str, SMAP.FILE_SECTION) || Intrinsics.areEqual(str, SMAP.END) || StringsKt.startsWith$default(str, SMAP.STRATA_SECTION, false, 2, (Object) null)) {
                return null;
            }
            if (StringsKt.startsWith$default(str, "+ ", false, 2, (Object) null)) {
                strSubstring = str.substring(2);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            } else {
                strSubstring = str;
            }
            int i3 = Integer.parseInt(StringsKt.substringBefore$default(strSubstring, StringUtil.SPACE, (String) null, 2, (Object) null));
            String strSubstringAfter$default = StringsKt.substringAfter$default(strSubstring, StringUtil.SPACE, (String) null, 2, (Object) null);
            linkedHashMap.put(Integer.valueOf(i3), new FileMapping(strSubstringAfter$default, StringsKt.startsWith$default(str, "+ ", false, 2, (Object) null) ? (String) it.next() : strSubstringAfter$default));
        }
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (Intrinsics.areEqual(str2, SMAP.LINE_SECTION) || Intrinsics.areEqual(str2, SMAP.FILE_SECTION)) {
                return smap;
            }
            if (Intrinsics.areEqual(str2, SMAP.END) || StringsKt.startsWith$default(str2, SMAP.STRATA_SECTION, (boolean) r10, i, (Object) smap)) {
                break;
            }
            String str3 = str2;
            int iIndexOf$default2 = StringsKt.indexOf$default((CharSequence) str3, '#', 0, false, 6, (Object) null);
            if (iIndexOf$default2 < 0 || (iIndexOf$default = StringsKt.indexOf$default((CharSequence) str3, ':', iIndexOf$default2, false, 4, (Object) null)) < 0) {
                return smap;
            }
            int iIndexOf$default3 = StringsKt.indexOf$default((CharSequence) str3, ',', 0, false, 6, (Object) null);
            if (iIndexOf$default2 > iIndexOf$default3 || iIndexOf$default3 > iIndexOf$default) {
                iIndexOf$default3 = iIndexOf$default;
            }
            int i4 = iIndexOf$default3;
            int iIndexOf$default4 = StringsKt.indexOf$default((CharSequence) str3, ',', iIndexOf$default, false, 4, (Object) null);
            if (iIndexOf$default4 < 0) {
                iIndexOf$default4 = str2.length();
            }
            String strSubstring2 = str2.substring(iIndexOf$default2 + 1, i4);
            Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
            FileMapping fileMapping = (FileMapping) linkedHashMap.get(Integer.valueOf(Integer.parseInt(strSubstring2)));
            if (fileMapping == null) {
                return smap;
            }
            String strSubstring3 = str2.substring(r10, iIndexOf$default2);
            Intrinsics.checkNotNullExpressionValue(strSubstring3, "substring(...)");
            int i5 = Integer.parseInt(strSubstring3);
            String strSubstring4 = str2.substring(iIndexOf$default + 1, iIndexOf$default4);
            Intrinsics.checkNotNullExpressionValue(strSubstring4, "substring(...)");
            int i6 = Integer.parseInt(strSubstring4);
            if (iIndexOf$default4 != str2.length()) {
                String strSubstring5 = str2.substring(iIndexOf$default4 + 1);
                Intrinsics.checkNotNullExpressionValue(strSubstring5, "substring(...)");
                i2 = Integer.parseInt(strSubstring5);
            } else if (i4 != iIndexOf$default) {
                String strSubstring6 = str2.substring(i4 + 1, iIndexOf$default);
                Intrinsics.checkNotNullExpressionValue(strSubstring6, "substring(...)");
                i2 = Integer.parseInt(strSubstring6);
            } else {
                i2 = 1;
            }
            fileMapping.mapNewInterval(i5, i6, i2, (callSites == null || (rangeMappingFindRange = callSites.findRange(i6)) == null) ? null : rangeMappingFindRange.mapDestToSource(rangeMappingFindRange.getDest()));
            smap = null;
            r10 = 0;
            i = 2;
        }
        Collection collectionValues = linkedHashMap.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        return new SMAP(CollectionsKt.toList(collectionValues));
    }
}
