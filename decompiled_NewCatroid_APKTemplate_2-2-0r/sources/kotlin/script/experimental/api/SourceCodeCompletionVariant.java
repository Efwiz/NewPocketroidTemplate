package kotlin.script.experimental.api;

import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: replAnalysis.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\bHÆ\u0003J=\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bHÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r¨\u0006\u001d"}, d2 = {"Lkotlin/script/experimental/api/SourceCodeCompletionVariant;", "", "text", "", "displayText", "tail", "icon", "deprecationLevel", "Lkotlin/DeprecationLevel;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/DeprecationLevel;)V", "getDeprecationLevel", "()Lkotlin/DeprecationLevel;", "getDisplayText", "()Ljava/lang/String;", "getIcon", "getTail", "getText", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SourceCodeCompletionVariant {
    private final DeprecationLevel deprecationLevel;
    private final String displayText;
    private final String icon;
    private final String tail;
    private final String text;

    public static /* synthetic */ SourceCodeCompletionVariant copy$default(SourceCodeCompletionVariant sourceCodeCompletionVariant, String str, String str2, String str3, String str4, DeprecationLevel deprecationLevel, int i, Object obj) {
        if ((i & 1) != 0) {
            str = sourceCodeCompletionVariant.text;
        }
        if ((i & 2) != 0) {
            str2 = sourceCodeCompletionVariant.displayText;
        }
        String str5 = str2;
        if ((i & 4) != 0) {
            str3 = sourceCodeCompletionVariant.tail;
        }
        String str6 = str3;
        if ((i & 8) != 0) {
            str4 = sourceCodeCompletionVariant.icon;
        }
        String str7 = str4;
        if ((i & 16) != 0) {
            deprecationLevel = sourceCodeCompletionVariant.deprecationLevel;
        }
        return sourceCodeCompletionVariant.copy(str, str5, str6, str7, deprecationLevel);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getText() {
        return this.text;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getDisplayText() {
        return this.displayText;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getTail() {
        return this.tail;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getIcon() {
        return this.icon;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final DeprecationLevel getDeprecationLevel() {
        return this.deprecationLevel;
    }

    public final SourceCodeCompletionVariant copy(String text, String displayText, String tail, String icon, DeprecationLevel deprecationLevel) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(displayText, "displayText");
        Intrinsics.checkNotNullParameter(tail, "tail");
        Intrinsics.checkNotNullParameter(icon, "icon");
        return new SourceCodeCompletionVariant(text, displayText, tail, icon, deprecationLevel);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SourceCodeCompletionVariant)) {
            return false;
        }
        SourceCodeCompletionVariant sourceCodeCompletionVariant = (SourceCodeCompletionVariant) other;
        return Intrinsics.areEqual(this.text, sourceCodeCompletionVariant.text) && Intrinsics.areEqual(this.displayText, sourceCodeCompletionVariant.displayText) && Intrinsics.areEqual(this.tail, sourceCodeCompletionVariant.tail) && Intrinsics.areEqual(this.icon, sourceCodeCompletionVariant.icon) && this.deprecationLevel == sourceCodeCompletionVariant.deprecationLevel;
    }

    public int hashCode() {
        int iHashCode = ((((((this.text.hashCode() * 31) + this.displayText.hashCode()) * 31) + this.tail.hashCode()) * 31) + this.icon.hashCode()) * 31;
        DeprecationLevel deprecationLevel = this.deprecationLevel;
        return iHashCode + (deprecationLevel == null ? 0 : deprecationLevel.hashCode());
    }

    public String toString() {
        return "SourceCodeCompletionVariant(text=" + this.text + ", displayText=" + this.displayText + ", tail=" + this.tail + ", icon=" + this.icon + ", deprecationLevel=" + this.deprecationLevel + ')';
    }

    public SourceCodeCompletionVariant(String text, String displayText, String tail, String icon, DeprecationLevel deprecationLevel) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(displayText, "displayText");
        Intrinsics.checkNotNullParameter(tail, "tail");
        Intrinsics.checkNotNullParameter(icon, "icon");
        this.text = text;
        this.displayText = displayText;
        this.tail = tail;
        this.icon = icon;
        this.deprecationLevel = deprecationLevel;
    }

    public /* synthetic */ SourceCodeCompletionVariant(String str, String str2, String str3, String str4, DeprecationLevel deprecationLevel, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, str4, (i & 16) != 0 ? null : deprecationLevel);
    }

    public final String getText() {
        return this.text;
    }

    public final String getDisplayText() {
        return this.displayText;
    }

    public final String getTail() {
        return this.tail;
    }

    public final String getIcon() {
        return this.icon;
    }

    public final DeprecationLevel getDeprecationLevel() {
        return this.deprecationLevel;
    }
}
