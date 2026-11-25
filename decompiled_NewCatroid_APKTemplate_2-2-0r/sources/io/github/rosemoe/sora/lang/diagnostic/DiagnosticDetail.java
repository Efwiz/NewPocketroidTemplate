package io.github.rosemoe.sora.lang.diagnostic;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: DiagnosticDetail.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0011\u0010\u0013\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÆ\u0003J=\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\u0010\b\u0002\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0001HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0019\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001d"}, d2 = {"Lio/github/rosemoe/sora/lang/diagnostic/DiagnosticDetail;", "", "briefMessage", "", "detailedMessage", "quickfixes", "", "Lio/github/rosemoe/sora/lang/diagnostic/Quickfix;", "extraData", "(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/util/List;Ljava/lang/Object;)V", "getBriefMessage", "()Ljava/lang/CharSequence;", "getDetailedMessage", "getExtraData", "()Ljava/lang/Object;", "getQuickfixes", "()Ljava/util/List;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DiagnosticDetail {
    private final CharSequence briefMessage;
    private final CharSequence detailedMessage;
    private final Object extraData;
    private final List<Quickfix> quickfixes;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ DiagnosticDetail copy$default(DiagnosticDetail diagnosticDetail, CharSequence charSequence, CharSequence charSequence2, List list, Object obj, int i, Object obj2) {
        if ((i & 1) != 0) {
            charSequence = diagnosticDetail.briefMessage;
        }
        if ((i & 2) != 0) {
            charSequence2 = diagnosticDetail.detailedMessage;
        }
        if ((i & 4) != 0) {
            list = diagnosticDetail.quickfixes;
        }
        if ((i & 8) != 0) {
            obj = diagnosticDetail.extraData;
        }
        return diagnosticDetail.copy(charSequence, charSequence2, list, obj);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final CharSequence getBriefMessage() {
        return this.briefMessage;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final CharSequence getDetailedMessage() {
        return this.detailedMessage;
    }

    public final List<Quickfix> component3() {
        return this.quickfixes;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final Object getExtraData() {
        return this.extraData;
    }

    public final DiagnosticDetail copy(CharSequence briefMessage, CharSequence detailedMessage, List<? extends Quickfix> quickfixes, Object extraData) {
        Intrinsics.checkNotNullParameter(briefMessage, "briefMessage");
        return new DiagnosticDetail(briefMessage, detailedMessage, quickfixes, extraData);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DiagnosticDetail)) {
            return false;
        }
        DiagnosticDetail diagnosticDetail = (DiagnosticDetail) other;
        return Intrinsics.areEqual(this.briefMessage, diagnosticDetail.briefMessage) && Intrinsics.areEqual(this.detailedMessage, diagnosticDetail.detailedMessage) && Intrinsics.areEqual(this.quickfixes, diagnosticDetail.quickfixes) && Intrinsics.areEqual(this.extraData, diagnosticDetail.extraData);
    }

    public int hashCode() {
        int iHashCode = this.briefMessage.hashCode() * 31;
        CharSequence charSequence = this.detailedMessage;
        int iHashCode2 = (iHashCode + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        List<Quickfix> list = this.quickfixes;
        int iHashCode3 = (iHashCode2 + (list == null ? 0 : list.hashCode())) * 31;
        Object obj = this.extraData;
        return iHashCode3 + (obj != null ? obj.hashCode() : 0);
    }

    public String toString() {
        CharSequence charSequence = this.briefMessage;
        CharSequence charSequence2 = this.detailedMessage;
        return "DiagnosticDetail(briefMessage=" + ((Object) charSequence) + ", detailedMessage=" + ((Object) charSequence2) + ", quickfixes=" + this.quickfixes + ", extraData=" + this.extraData + Constant.AFTER_QUTO;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public DiagnosticDetail(CharSequence briefMessage, CharSequence charSequence, List<? extends Quickfix> list, Object obj) {
        Intrinsics.checkNotNullParameter(briefMessage, "briefMessage");
        this.briefMessage = briefMessage;
        this.detailedMessage = charSequence;
        this.quickfixes = list;
        this.extraData = obj;
    }

    public /* synthetic */ DiagnosticDetail(CharSequence charSequence, CharSequence charSequence2, List list, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(charSequence, (i & 2) != 0 ? null : charSequence2, (i & 4) != 0 ? null : list, (i & 8) != 0 ? null : obj);
    }

    public final CharSequence getBriefMessage() {
        return this.briefMessage;
    }

    public final CharSequence getDetailedMessage() {
        return this.detailedMessage;
    }

    public final List<Quickfix> getQuickfixes() {
        return this.quickfixes;
    }

    public final Object getExtraData() {
        return this.extraData;
    }
}
