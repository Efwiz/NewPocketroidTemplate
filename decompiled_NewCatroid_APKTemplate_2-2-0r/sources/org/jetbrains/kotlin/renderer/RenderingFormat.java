package org.jetbrains.kotlin.renderer;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: DescriptorRenderer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H&j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/renderer/RenderingFormat;", "", "(Ljava/lang/String;I)V", "escape", "", "string", "PLAIN", "HTML", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum RenderingFormat {
    PLAIN { // from class: org.jetbrains.kotlin.renderer.RenderingFormat.PLAIN
        @Override // org.jetbrains.kotlin.renderer.RenderingFormat
        public String escape(String string) {
            Intrinsics.checkNotNullParameter(string, "string");
            return string;
        }
    },
    HTML { // from class: org.jetbrains.kotlin.renderer.RenderingFormat.HTML
        @Override // org.jetbrains.kotlin.renderer.RenderingFormat
        public String escape(String string) {
            Intrinsics.checkNotNullParameter(string, "string");
            return StringsKt.replace$default(StringsKt.replace$default(string, "<", "&lt;", false, 4, (Object) null), ">", "&gt;", false, 4, (Object) null);
        }
    };

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* synthetic */ RenderingFormat(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public static EnumEntries<RenderingFormat> getEntries() {
        return $ENTRIES;
    }

    public abstract String escape(String string);
}
