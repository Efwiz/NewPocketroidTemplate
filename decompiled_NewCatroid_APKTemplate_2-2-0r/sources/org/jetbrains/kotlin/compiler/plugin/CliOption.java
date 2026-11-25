package org.jetbrains.kotlin.compiler.plugin;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CliOptions.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tR\u0014\u0010\b\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0005\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/compiler/plugin/CliOption;", "Lorg/jetbrains/kotlin/compiler/plugin/AbstractCliOption;", "optionName", "", "valueDescription", MediaTrack.ROLE_DESCRIPTION, "required", "", "allowMultipleOccurrences", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZ)V", "getAllowMultipleOccurrences", "()Z", "getDescription", "()Ljava/lang/String;", "getOptionName", "getRequired", "getValueDescription", "plugin-api"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CliOption implements AbstractCliOption {
    private final boolean allowMultipleOccurrences;
    private final String description;
    private final String optionName;
    private final boolean required;
    private final String valueDescription;

    public CliOption(String optionName, String valueDescription, String description, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(optionName, "optionName");
        Intrinsics.checkNotNullParameter(valueDescription, "valueDescription");
        Intrinsics.checkNotNullParameter(description, "description");
        this.optionName = optionName;
        this.valueDescription = valueDescription;
        this.description = description;
        this.required = z;
        this.allowMultipleOccurrences = z2;
    }

    public /* synthetic */ CliOption(String str, String str2, String str3, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, (i & 8) != 0 ? true : z, (i & 16) != 0 ? false : z2);
    }

    @Override // org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
    public String getOptionName() {
        return this.optionName;
    }

    @Override // org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
    public String getValueDescription() {
        return this.valueDescription;
    }

    @Override // org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
    public String getDescription() {
        return this.description;
    }

    @Override // org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
    public boolean getRequired() {
        return this.required;
    }

    @Override // org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
    public boolean getAllowMultipleOccurrences() {
        return this.allowMultipleOccurrences;
    }
}
