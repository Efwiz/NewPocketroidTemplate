package org.catrobat.catroid.common;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/ParameterizedData.class
  classes12.dex
 */
/* JADX INFO: compiled from: ParameterizedData.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B?\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\f\b\u0002\u0010\u0005\u001a\u00060\u0006j\u0002`\u0007\u0012\f\b\u0002\u0010\b\u001a\u00060\u0006j\u0002`\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0006\u0010\u001c\u001a\u00020\u001dR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001e\u0010\u0005\u001a\u00060\u0006j\u0002`\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0011\"\u0004\b\u0019\u0010\u0013R\u001e\u0010\b\u001a\u00060\u0006j\u0002`\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0015\"\u0004\b\u001b\u0010\u0017¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/common/ParameterizedData;", "", "currentPosition", "", "listSize", "failMessages", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "successMessages", "currentParameters", "", "(IILjava/lang/StringBuilder;Ljava/lang/StringBuilder;Ljava/lang/String;)V", "getCurrentParameters", "()Ljava/lang/String;", "setCurrentParameters", "(Ljava/lang/String;)V", "getCurrentPosition", "()I", "setCurrentPosition", "(I)V", "getFailMessages", "()Ljava/lang/StringBuilder;", "setFailMessages", "(Ljava/lang/StringBuilder;)V", "getListSize", "setListSize", "getSuccessMessages", "setSuccessMessages", "reset", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ParameterizedData {
    private String currentParameters;
    private int currentPosition;
    private StringBuilder failMessages;
    private int listSize;
    private StringBuilder successMessages;

    public ParameterizedData() {
        this(0, 0, null, null, null, 31, null);
    }

    public ParameterizedData(int i, int i2, StringBuilder failMessages, StringBuilder successMessages, String currentParameters) {
        Intrinsics.checkNotNullParameter(failMessages, "failMessages");
        Intrinsics.checkNotNullParameter(successMessages, "successMessages");
        Intrinsics.checkNotNullParameter(currentParameters, "currentParameters");
        this.currentPosition = i;
        this.listSize = i2;
        this.failMessages = failMessages;
        this.successMessages = successMessages;
        this.currentParameters = currentParameters;
    }

    public final int getCurrentPosition() {
        return this.currentPosition;
    }

    public final void setCurrentPosition(int i) {
        this.currentPosition = i;
    }

    public final int getListSize() {
        return this.listSize;
    }

    public final void setListSize(int i) {
        this.listSize = i;
    }

    public /* synthetic */ ParameterizedData(int i, int i2, StringBuilder sb, StringBuilder sb2, String str, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 1 : i2, (i3 & 4) != 0 ? new StringBuilder() : sb, (i3 & 8) != 0 ? new StringBuilder() : sb2, (i3 & 16) != 0 ? "" : str);
    }

    public final StringBuilder getFailMessages() {
        return this.failMessages;
    }

    public final void setFailMessages(StringBuilder sb) {
        Intrinsics.checkNotNullParameter(sb, "<set-?>");
        this.failMessages = sb;
    }

    public final StringBuilder getSuccessMessages() {
        return this.successMessages;
    }

    public final void setSuccessMessages(StringBuilder sb) {
        Intrinsics.checkNotNullParameter(sb, "<set-?>");
        this.successMessages = sb;
    }

    public final String getCurrentParameters() {
        return this.currentParameters;
    }

    public final void setCurrentParameters(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.currentParameters = str;
    }

    public final void reset() {
        this.currentPosition = 0;
        this.listSize = 1;
        StringsKt.clear(this.failMessages);
        StringsKt.clear(this.successMessages);
        this.currentParameters = "";
    }
}
