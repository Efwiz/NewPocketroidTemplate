package org.jetbrains.kotlin.utils;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.CharsKt;
import kotlin.text.MatchResult;
import kotlin.text.Regex;

/* JADX INFO: compiled from: ResolvedDependencies.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JF\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n26\u0010\u000b\u001a2\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00120\fJ\u000e\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/utils/ResolvedDependenciesSupport;", "", "()V", "DEPENDENCY_MODULE_REGEX", "Lkotlin/text/Regex;", "REQUESTED_VERSION_BY_INCOMING_DEPENDENCY_REGEX", "SOURCE_CODE_MODULE_REGEX", "deserialize", "Lorg/jetbrains/kotlin/utils/ResolvedDependencies;", FirebaseAnalytics.Param.SOURCE, "", "onMalformedLine", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "lineNo", Constant.LINE, "", "serialize", "dependencies", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolvedDependenciesSupport {
    public static final ResolvedDependenciesSupport INSTANCE = new ResolvedDependenciesSupport();
    private static final Regex SOURCE_CODE_MODULE_REGEX = new Regex("^0 ([^\\[]+)$");
    private static final Regex DEPENDENCY_MODULE_REGEX = new Regex("^(\\d+) ([^\\[]+)\\[([^]]+)](.*)?$");
    private static final Regex REQUESTED_VERSION_BY_INCOMING_DEPENDENCY_REGEX = new Regex("^#(\\d+)\\[(.*)]$");

    private ResolvedDependenciesSupport() {
    }

    public final String serialize(ResolvedDependencies dependencies) {
        Intrinsics.checkNotNullParameter(dependencies, "dependencies");
        int i = 0;
        final Map mapMutableMapOf = MapsKt.mutableMapOf(TuplesKt.to(dependencies.getSourceCodeModuleId(), 0));
        for (Object obj : dependencies.getModules()) {
            int i2 = i + 1;
            if (i < 0) {
                kotlin.collections.CollectionsKt.throwIndexOverflow();
            }
            mapMutableMapOf.put(((ResolvedDependency) obj).getId(), Integer.valueOf(i2));
            i = i2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("0 ");
        StringBuilder sb2 = sb;
        kotlin.collections.CollectionsKt.joinTo(dependencies.getSourceCodeModuleId().getUniqueNames(), sb2, (112 & 2) != 0 ? ", " : ",", (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : null);
        sb.append('\n');
        for (ResolvedDependency resolvedDependency : dependencies.getModules()) {
            sb.append(String.valueOf(((Number) MapsKt.getValue(mapMutableMapOf, resolvedDependency.getId())).intValue())).append(StringUtil.SPACE);
            kotlin.collections.CollectionsKt.joinTo(resolvedDependency.getId().getUniqueNames(), sb2, (112 & 2) != 0 ? ", " : ",", (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : null);
            sb.append('[').append(resolvedDependency.getSelectedVersion()).append(']');
            kotlin.collections.CollectionsKt.joinTo(resolvedDependency.getRequestedVersionsByIncomingDependencies().entrySet(), sb2, (112 & 2) != 0 ? ", " : "", (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : new Function1<Map.Entry<ResolvedDependencyId, ResolvedDependencyVersion>, CharSequence>() { // from class: org.jetbrains.kotlin.utils.ResolvedDependenciesSupport$serialize$1$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(Map.Entry<ResolvedDependencyId, ResolvedDependencyVersion> entry) {
                    Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                    return " #" + ((Number) MapsKt.getValue(mapMutableMapOf, entry.getKey())).intValue() + '[' + entry.getValue() + ']';
                }
            });
            sb.append('\n');
            kotlin.collections.CollectionsKt.joinTo(resolvedDependency.getArtifactPaths(), sb2, (112 & 2) != 0 ? ", " : "", (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : new Function1<ResolvedDependencyArtifactPath, CharSequence>() { // from class: org.jetbrains.kotlin.utils.ResolvedDependenciesSupport$serialize$1$1$2
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(ResolvedDependencyArtifactPath artifactPath) {
                    Intrinsics.checkNotNullParameter(artifactPath, "artifactPath");
                    return "\t" + artifactPath + '\n';
                }
            });
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public final ResolvedDependencies deserialize(String source, Function2<? super Integer, ? super String, Unit> onMalformedLine) {
        ResolvedDependencyId resolvedDependencyId;
        Iterator it;
        int i;
        String strSubSequence;
        Function2<? super Integer, ? super String, Unit> onMalformedLine2 = onMalformedLine;
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(onMalformedLine2, "onMalformedLine");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        ArrayList<ResolvedDependency> arrayList = new ArrayList();
        Iterator it2 = kotlin.text.StringsKt.lines(source).iterator();
        ResolvedDependencyId resolvedDependencyId2 = null;
        int i2 = 0;
        int i3 = 0;
        while (it2.hasNext()) {
            Object next = it2.next();
            int i4 = i3 + 1;
            if (i3 < 0) {
                kotlin.collections.CollectionsKt.throwIndexOverflow();
            }
            String str = (String) next;
            String str2 = str;
            if (kotlin.text.StringsKt.isBlank(str2)) {
                it = it2;
                i = i2;
            } else if (CharsKt.isWhitespace(str.charAt(i2))) {
                ResolvedDependency resolvedDependency = (ResolvedDependency) kotlin.collections.CollectionsKt.lastOrNull((List) arrayList);
                int length = str2.length();
                int i5 = i2;
                while (true) {
                    if (i5 < length) {
                        if (!CharsKt.isWhitespace(str2.charAt(i5))) {
                            strSubSequence = str2.subSequence(i5, str2.length());
                            break;
                        }
                        i5++;
                    } else {
                        break;
                    }
                }
                String string = strSubSequence.toString();
                if (resolvedDependency != null && !kotlin.text.StringsKt.isBlank(string)) {
                    resolvedDependency.getArtifactPaths().add(new ResolvedDependencyArtifactPath(string));
                    it = it2;
                    i = i2;
                } else {
                    return deserialize$lambda$6$malformedLine(onMalformedLine2, i3, str);
                }
            } else if (str.charAt(i2) == '0') {
                MatchResult matchResultMatchEntire = SOURCE_CODE_MODULE_REGEX.matchEntire(str2);
                if (matchResultMatchEntire == null) {
                    return deserialize$lambda$6$malformedLine(onMalformedLine2, i3, str);
                }
                String str3 = matchResultMatchEntire.getGroupValues().get(1);
                char[] cArr = new char[1];
                cArr[i2] = ',';
                it = it2;
                i = i2;
                resolvedDependencyId2 = new ResolvedDependencyId(kotlin.text.StringsKt.split$default((CharSequence) str3, cArr, false, 0, 6, (Object) null));
            } else {
                if (resolvedDependencyId2 == null) {
                    return deserialize$lambda$6$malformedLine(onMalformedLine2, i3, str);
                }
                MatchResult matchResultMatchEntire2 = DEPENDENCY_MODULE_REGEX.matchEntire(str2);
                if (matchResultMatchEntire2 == null) {
                    return deserialize$lambda$6$malformedLine(onMalformedLine2, i3, str);
                }
                int i6 = Integer.parseInt(matchResultMatchEntire2.getGroupValues().get(1));
                ResolvedDependencyId resolvedDependencyId3 = new ResolvedDependencyId(kotlin.text.StringsKt.split$default((CharSequence) matchResultMatchEntire2.getGroupValues().get(2), new char[]{','}, false, 0, 6, (Object) null));
                ResolvedDependencyVersion resolvedDependencyVersion = new ResolvedDependencyVersion(matchResultMatchEntire2.getGroupValues().get(3));
                if (matchResultMatchEntire2.getGroupValues().size() > 4) {
                    i = 0;
                    List listSplit$default = kotlin.text.StringsKt.split$default((CharSequence) kotlin.text.StringsKt.trimStart((CharSequence) matchResultMatchEntire2.getGroupValues().get(4)).toString(), new char[]{StringUtil.SPACE}, false, 0, 6, (Object) null);
                    LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(kotlin.collections.CollectionsKt.collectionSizeOrDefault(listSplit$default, 10)), 16));
                    Iterator it3 = listSplit$default.iterator();
                    while (it3.hasNext()) {
                        MatchResult matchResultMatchEntire3 = REQUESTED_VERSION_BY_INCOMING_DEPENDENCY_REGEX.matchEntire((String) it3.next());
                        if (matchResultMatchEntire3 == null) {
                            return deserialize$lambda$6$malformedLine(onMalformedLine2, i3, str);
                        }
                        Iterator it4 = it2;
                        Pair pair = TuplesKt.to(Integer.valueOf(Integer.parseInt(matchResultMatchEntire3.getGroupValues().get(1))), new ResolvedDependencyVersion(matchResultMatchEntire3.getGroupValues().get(2)));
                        linkedHashMap3.put(pair.getFirst(), pair.getSecond());
                        onMalformedLine2 = onMalformedLine;
                        it2 = it4;
                    }
                    it = it2;
                    linkedHashMap2.put(resolvedDependencyId3, linkedHashMap3);
                } else {
                    it = it2;
                    i = 0;
                }
                linkedHashMap.put(Integer.valueOf(i6), resolvedDependencyId3);
                arrayList.add(new ResolvedDependency(resolvedDependencyId3, false, resolvedDependencyVersion, new LinkedHashMap(), new LinkedHashSet(), 2, null));
            }
            onMalformedLine2 = onMalformedLine;
            i3 = i4;
            i2 = i;
            it2 = it;
        }
        for (ResolvedDependency resolvedDependency2 : arrayList) {
            Map map = (Map) linkedHashMap2.get(resolvedDependency2.getId());
            if (map != null) {
                for (Map.Entry entry : map.entrySet()) {
                    int iIntValue = ((Number) entry.getKey()).intValue();
                    ResolvedDependencyVersion resolvedDependencyVersion2 = (ResolvedDependencyVersion) entry.getValue();
                    if (iIntValue == 0) {
                        Intrinsics.checkNotNull(resolvedDependencyId2);
                        resolvedDependencyId = resolvedDependencyId2;
                    } else {
                        resolvedDependencyId = (ResolvedDependencyId) MapsKt.getValue(linkedHashMap, Integer.valueOf(iIntValue));
                    }
                    resolvedDependency2.getRequestedVersionsByIncomingDependencies().put(resolvedDependencyId, resolvedDependencyVersion2);
                }
            }
        }
        Intrinsics.checkNotNull(resolvedDependencyId2);
        return new ResolvedDependencies(arrayList, resolvedDependencyId2);
    }

    private static final ResolvedDependencies deserialize$lambda$6$malformedLine(Function2<? super Integer, ? super String, Unit> function2, int i, String str) {
        function2.invoke(Integer.valueOf(i), str);
        return ResolvedDependencies.INSTANCE.getEMPTY();
    }
}
