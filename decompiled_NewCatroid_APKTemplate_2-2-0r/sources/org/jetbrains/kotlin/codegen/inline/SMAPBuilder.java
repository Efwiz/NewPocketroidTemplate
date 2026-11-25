package org.jetbrains.kotlin.codegen.inline;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.reandroid.dex.sections.Marker;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SMAP.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tJ\"\u0010\n\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00070\u000b2\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\tH\u0002J\u001c\u0010\n\u001a\u00020\u0004*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\tH\u0002J\u0014\u0010\u0012\u001a\u00020\u0004*\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0010H\u0002J\u001c\u0010\u0014\u001a\u00020\u0004*\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\tH\u0002¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/SMAPBuilder;", "", "()V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "", "fileMappings", "", "Lorg/jetbrains/kotlin/codegen/inline/FileMapping;", "backwardsCompatibleSyntax", "", "toSMAP", "", "stratumName", "mapToFirstLine", "Lorg/jetbrains/kotlin/codegen/inline/RangeMapping;", "fileId", "", "oneLine", "toSMAPFile", "id", "toSMAPMapping", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SMAPBuilder {
    public static final SMAPBuilder INSTANCE = new SMAPBuilder();

    private SMAPBuilder() {
    }

    public final String build(List<FileMapping> fileMappings, boolean backwardsCompatibleSyntax) {
        Intrinsics.checkNotNullParameter(fileMappings, "fileMappings");
        if (fileMappings.isEmpty()) {
            return null;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<FileMapping> it = fileMappings.iterator();
        while (it.hasNext()) {
            for (RangeMapping rangeMapping : it.next().getLineMappings()) {
                int dest = rangeMapping.getDest();
                int range = rangeMapping.getRange();
                SourcePosition callSite = rangeMapping.getCallSite();
                if (callSite != null) {
                    int line = callSite.getLine();
                    String file = callSite.getFile();
                    String path = callSite.getPath();
                    LinkedHashMap linkedHashMap2 = linkedHashMap;
                    Pair pair = TuplesKt.to(file, path);
                    Object fileMapping = linkedHashMap2.get(pair);
                    if (fileMapping == null) {
                        fileMapping = new FileMapping(file, path);
                        linkedHashMap2.put(pair, fileMapping);
                    }
                    FileMapping.mapNewInterval$default((FileMapping) fileMapping, line, dest, range, null, 8, null);
                }
            }
        }
        String smap = toSMAP((Collection<FileMapping>) fileMappings, "Kotlin", false);
        Collection<FileMapping> collectionValues = linkedHashMap.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        String smap2 = toSMAP(collectionValues, SMAPKt.KOTLIN_DEBUG_STRATA_NAME, !backwardsCompatibleSyntax);
        return (!backwardsCompatibleSyntax || smap.length() <= 0 || smap2.length() <= 0) ? "SMAP\n" + fileMappings.get(0).getName() + "\nKotlin\n" + smap + smap2 + "*E\n" : "SMAP\n" + fileMappings.get(0).getName() + "\nKotlin\n" + smap + "*E\n" + smap2 + "*E\n";
    }

    private final String toSMAP(Collection<FileMapping> collection, String str, boolean z) {
        if (collection.isEmpty()) {
            return "";
        }
        StringBuilder sbAppend = new StringBuilder("*S ").append(str).append("\n*F\n");
        Collection<FileMapping> collection2 = collection;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        int i = 0;
        int i2 = 0;
        for (Object obj : collection2) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList.add(INSTANCE.toSMAPFile((FileMapping) obj, i3));
            i2 = i3;
        }
        StringBuilder sbAppend2 = sbAppend.append(CollectionsKt.joinToString$default(arrayList, "", null, null, 0, null, null, 62, null)).append("*L\n");
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        for (Object obj2 : collection2) {
            int i4 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList2.add(INSTANCE.toSMAPMapping((FileMapping) obj2, i4, z));
            i = i4;
        }
        return sbAppend2.append(CollectionsKt.joinToString$default(arrayList2, "", null, null, 0, null, null, 62, null)).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String toSMAP(RangeMapping rangeMapping, int i, boolean z) {
        StringBuilder sbAppend;
        StringBuilder sbAppend2;
        int range;
        if (rangeMapping.getRange() != 1) {
            if (z) {
                sbAppend2 = new StringBuilder().append(rangeMapping.getSource()).append('#').append(i).append(':').append(rangeMapping.getDest()).append(',');
                range = rangeMapping.getRange();
            } else {
                sbAppend = new StringBuilder().append(rangeMapping.getSource()).append('#').append(i).append(',').append(rangeMapping.getRange());
            }
            return sbAppend2.append(range).append('\n').toString();
        }
        sbAppend = new StringBuilder().append(rangeMapping.getSource()).append('#').append(i);
        sbAppend2 = sbAppend.append(':');
        range = rangeMapping.getDest();
        return sbAppend2.append(range).append('\n').toString();
    }

    private final String toSMAPFile(FileMapping fileMapping, int i) {
        return "+ " + i + StringUtil.SPACE + fileMapping.getName() + '\n' + fileMapping.getPath() + '\n';
    }

    private final String toSMAPMapping(FileMapping fileMapping, final int i, final boolean z) {
        return CollectionsKt.joinToString$default(fileMapping.getLineMappings(), "", null, null, 0, null, new Function1<RangeMapping, CharSequence>() { // from class: org.jetbrains.kotlin.codegen.inline.SMAPBuilder.toSMAPMapping.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(RangeMapping it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return SMAPBuilder.INSTANCE.toSMAP(it, i, z);
            }
        }, 30, null);
    }
}
