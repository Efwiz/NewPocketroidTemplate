package org.jetbrains.kotlin.cli.common.arguments;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: InternalCompilerArgument.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0007HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/ManualLanguageFeatureSetting;", "Lorg/jetbrains/kotlin/cli/common/arguments/InternalArgument;", "languageFeature", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "state", "Lorg/jetbrains/kotlin/config/LanguageFeature$State;", "stringRepresentation", "", "(Lorg/jetbrains/kotlin/config/LanguageFeature;Lorg/jetbrains/kotlin/config/LanguageFeature$State;Ljava/lang/String;)V", "getLanguageFeature", "()Lorg/jetbrains/kotlin/config/LanguageFeature;", "getState", "()Lorg/jetbrains/kotlin/config/LanguageFeature$State;", "getStringRepresentation", "()Ljava/lang/String;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ManualLanguageFeatureSetting implements InternalArgument {
    private final LanguageFeature languageFeature;
    private final LanguageFeature.State state;
    private final String stringRepresentation;

    public static /* synthetic */ ManualLanguageFeatureSetting copy$default(ManualLanguageFeatureSetting manualLanguageFeatureSetting, LanguageFeature languageFeature, LanguageFeature.State state, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            languageFeature = manualLanguageFeatureSetting.languageFeature;
        }
        if ((i & 2) != 0) {
            state = manualLanguageFeatureSetting.state;
        }
        if ((i & 4) != 0) {
            str = manualLanguageFeatureSetting.stringRepresentation;
        }
        return manualLanguageFeatureSetting.copy(languageFeature, state, str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final LanguageFeature getLanguageFeature() {
        return this.languageFeature;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final LanguageFeature.State getState() {
        return this.state;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getStringRepresentation() {
        return this.stringRepresentation;
    }

    public final ManualLanguageFeatureSetting copy(LanguageFeature languageFeature, LanguageFeature.State state, String stringRepresentation) {
        Intrinsics.checkNotNullParameter(languageFeature, "languageFeature");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(stringRepresentation, "stringRepresentation");
        return new ManualLanguageFeatureSetting(languageFeature, state, stringRepresentation);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ManualLanguageFeatureSetting)) {
            return false;
        }
        ManualLanguageFeatureSetting manualLanguageFeatureSetting = (ManualLanguageFeatureSetting) other;
        return this.languageFeature == manualLanguageFeatureSetting.languageFeature && this.state == manualLanguageFeatureSetting.state && Intrinsics.areEqual(this.stringRepresentation, manualLanguageFeatureSetting.stringRepresentation);
    }

    public int hashCode() {
        return (((this.languageFeature.hashCode() * 31) + this.state.hashCode()) * 31) + this.stringRepresentation.hashCode();
    }

    public String toString() {
        return "ManualLanguageFeatureSetting(languageFeature=" + this.languageFeature + ", state=" + this.state + ", stringRepresentation=" + this.stringRepresentation + ')';
    }

    public ManualLanguageFeatureSetting(LanguageFeature languageFeature, LanguageFeature.State state, String stringRepresentation) {
        Intrinsics.checkNotNullParameter(languageFeature, "languageFeature");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(stringRepresentation, "stringRepresentation");
        this.languageFeature = languageFeature;
        this.state = state;
        this.stringRepresentation = stringRepresentation;
    }

    public final LanguageFeature getLanguageFeature() {
        return this.languageFeature;
    }

    public final LanguageFeature.State getState() {
        return this.state;
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.InternalArgument
    public String getStringRepresentation() {
        return this.stringRepresentation;
    }
}
