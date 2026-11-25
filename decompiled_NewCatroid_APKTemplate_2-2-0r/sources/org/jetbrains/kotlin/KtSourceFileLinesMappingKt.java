package org.jetbrains.kotlin;

import java.io.InputStreamReader;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KtSourceFileLinesMapping.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001*\u00020\u0004\u001a\n\u0010\u0005\u001a\u00020\u0003*\u00020\u0002¨\u0006\u0006"}, d2 = {"readSourceFileWithMapping", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/KtSourceFileLinesMapping;", "Ljava/io/InputStreamReader;", "toSourceLinesMapping", "frontend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtSourceFileLinesMappingKt {
    public static final Pair<CharSequence, KtSourceFileLinesMapping> readSourceFileWithMapping(InputStreamReader inputStreamReader) {
        Intrinsics.checkNotNullParameter(inputStreamReader, "<this>");
        char[] cArr = new char[255];
        List listMutableListOf = CollectionsKt.mutableListOf(0);
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int i2 = 0;
        int i3 = 0;
        boolean z = false;
        while (true) {
            if (i2 >= i) {
                i = inputStreamReader.read(cArr);
                if (i < 0) {
                    return TuplesKt.to(sb, new KtSourceFileLinesMappingFromLineStartOffsets(CollectionsKt.toIntArray(listMutableListOf), i3));
                }
                i2 = 0;
            } else {
                int i4 = i2 + 1;
                char c = cArr[i2];
                int i5 = i3 + 1;
                if (c == '\n' && z) {
                    z = false;
                    i2 = i4;
                } else {
                    if (c == '\n' || c == '\r') {
                        sb.append('\n');
                        listMutableListOf.add(Integer.valueOf(i5));
                        if (c == '\r') {
                            z = true;
                        }
                        i2 = i4;
                        i3 = i5;
                    } else {
                        sb.append(c);
                    }
                    z = false;
                    i2 = i4;
                    i3 = i5;
                }
            }
        }
    }

    public static final KtSourceFileLinesMapping toSourceLinesMapping(CharSequence charSequence) {
        Intrinsics.checkNotNullParameter(charSequence, "<this>");
        List listMutableListOf = CollectionsKt.mutableListOf(0);
        int i = 0;
        for (int i2 = 0; i2 < charSequence.length(); i2++) {
            i++;
            if (charSequence.charAt(i2) == '\n') {
                listMutableListOf.add(Integer.valueOf(i));
            }
        }
        return new KtSourceFileLinesMappingFromLineStartOffsets(CollectionsKt.toIntArray(listMutableListOf), i);
    }
}
