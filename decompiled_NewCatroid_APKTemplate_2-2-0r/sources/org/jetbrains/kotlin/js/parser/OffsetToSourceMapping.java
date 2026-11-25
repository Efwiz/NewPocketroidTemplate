package org.jetbrains.kotlin.js.parser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.google.gwt.dev.js.rhino.CodePosition;

/* JADX INFO: compiled from: OffsetToSourceMapping.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0011\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/OffsetToSourceMapping;", "", "text", "", "(Ljava/lang/String;)V", "data", "", "get", "Lorg/jetbrains/kotlin/com/google/gwt/dev/js/rhino/CodePosition;", TypedValues.CycleType.S_WAVE_OFFSET, "", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OffsetToSourceMapping {
    private final int[] data;

    public OffsetToSourceMapping(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        ArrayList arrayList = new ArrayList();
        arrayList.add(0);
        int i = 0;
        while (i < text.length()) {
            int i2 = i + 1;
            char cCharAt = text.charAt(i);
            boolean z = true;
            if (cCharAt == '\r') {
                if (i2 < text.length() && text.charAt(i2) == '\n') {
                    i2 = i + 2;
                }
            } else if (cCharAt != '\n') {
                z = false;
            }
            i = i2;
            if (z) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        this.data = CollectionsKt.toIntArray(arrayList);
    }

    public final CodePosition get(int offset) {
        int iBinarySearch$default = ArraysKt.binarySearch$default(this.data, offset, 0, 0, 6, (Object) null);
        if (iBinarySearch$default < 0) {
            iBinarySearch$default = (-iBinarySearch$default) - 2;
        }
        return new CodePosition(iBinarySearch$default, offset - this.data[iBinarySearch$default]);
    }
}
