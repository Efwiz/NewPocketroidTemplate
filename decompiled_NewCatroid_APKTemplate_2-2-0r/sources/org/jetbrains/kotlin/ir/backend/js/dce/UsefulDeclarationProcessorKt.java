package org.jetbrains.kotlin.ir.backend.js.dce;

import com.google.android.gms.cast.MediaTrack;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: UsefulDeclarationProcessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u001e\u0010\u0007\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u001e\u0010\b\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0088\u0001\u0010\t\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\n\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062`\u0010\u000b\u001a\\\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\u00010\fH\u0002¨\u0006\u0014"}, d2 = {"transformToDotLikeString", "", "reachabilityInfos", "", "Lorg/jetbrains/kotlin/ir/backend/js/dce/ReachabilityInfo;", "dceDumpNameCache", "Lorg/jetbrains/kotlin/ir/backend/js/dce/DceDumpNameCache;", "transformToJsConstDeclaration", "transformToJsonString", "transformToStringBy", "separator", "transformer", "Lkotlin/Function4;", "Lkotlin/ParameterName;", "name", "sourceFqn", "targetFqn", MediaTrack.ROLE_DESCRIPTION, "", "isTargetContagious", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UsefulDeclarationProcessorKt {
    private static final String transformToStringBy(List<ReachabilityInfo> list, String str, DceDumpNameCache dceDumpNameCache, Function4<? super String, ? super String, ? super String, ? super Boolean, String> function4) {
        List<ReachabilityInfo> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (ReachabilityInfo reachabilityInfo : list2) {
            arrayList.add(function4.invoke(dceDumpNameCache.getOrPut(reachabilityInfo.getSource()), dceDumpNameCache.getOrPut(reachabilityInfo.getTarget()), reachabilityInfo.getDescription(), Boolean.valueOf(reachabilityInfo.isTargetContagious())));
        }
        return CollectionsKt.joinToString$default(CollectionsKt.distinct(arrayList), str, null, null, 0, null, null, 62, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String transformToDotLikeString(List<ReachabilityInfo> list, DceDumpNameCache dceDumpNameCache) {
        return transformToStringBy(list, "\n", dceDumpNameCache, new Function4<String, String, String, Boolean, String>() { // from class: org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessorKt.transformToDotLikeString.1
            @Override // kotlin.jvm.functions.Function4
            public /* bridge */ /* synthetic */ String invoke(String str, String str2, String str3, Boolean bool) {
                return invoke(str, str2, str3, bool.booleanValue());
            }

            public final String invoke(String sourceFqn, String targetFqn, String description, boolean z) {
                Intrinsics.checkNotNullParameter(sourceFqn, "sourceFqn");
                Intrinsics.checkNotNullParameter(targetFqn, "targetFqn");
                Intrinsics.checkNotNullParameter(description, "description");
                String str = description + (z ? "[CONTAGIOUS!]" : "");
                return "\"" + sourceFqn + "\" -> \"" + targetFqn + '\"' + (StringsKt.isBlank(str) ? "" : " // " + str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String transformToJsonString(List<ReachabilityInfo> list, DceDumpNameCache dceDumpNameCache) {
        return "[\n" + transformToStringBy(list, ",\n", dceDumpNameCache, new Function4<String, String, String, Boolean, String>() { // from class: org.jetbrains.kotlin.ir.backend.js.dce.UsefulDeclarationProcessorKt.transformToJsonString.1
            @Override // kotlin.jvm.functions.Function4
            public /* bridge */ /* synthetic */ String invoke(String str, String str2, String str3, Boolean bool) {
                return invoke(str, str2, str3, bool.booleanValue());
            }

            public final String invoke(String sourceFqn, String targetFqn, String description, boolean z) {
                Intrinsics.checkNotNullParameter(sourceFqn, "sourceFqn");
                Intrinsics.checkNotNullParameter(targetFqn, "targetFqn");
                Intrinsics.checkNotNullParameter(description, "description");
                return StringsKt.trimMargin$default("\n        |    {\n        |        \"source\" : \"" + UtilsKt.removeQuotes(sourceFqn) + "\",\n        |        \"target\" : \"" + UtilsKt.removeQuotes(targetFqn) + "\",\n        |        \"description\" : \"" + UtilsKt.removeQuotes(description) + "\",\n        |        \"isTargetContagious\" : " + z + "\n        |    }", null, 1, null);
            }
        }) + "\n]";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String transformToJsConstDeclaration(List<ReachabilityInfo> list, DceDumpNameCache dceDumpNameCache) {
        return "export const kotlinReachabilityInfos = " + transformToJsonString(list, dceDumpNameCache) + ';';
    }
}
