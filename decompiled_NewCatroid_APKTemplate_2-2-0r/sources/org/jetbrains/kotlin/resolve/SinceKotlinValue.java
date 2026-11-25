package org.jetbrains.kotlin.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: sinceKotlinUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/SinceKotlinValue;", "", "apiVersion", "Lorg/jetbrains/kotlin/config/ApiVersion;", "wasExperimentalMarkerClasses", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/config/ApiVersion;Ljava/util/List;)V", "getApiVersion", "()Lorg/jetbrains/kotlin/config/ApiVersion;", "getWasExperimentalMarkerClasses", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class SinceKotlinValue {
    private final ApiVersion apiVersion;
    private final List<ClassDescriptor> wasExperimentalMarkerClasses;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SinceKotlinValue copy$default(SinceKotlinValue sinceKotlinValue, ApiVersion apiVersion, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            apiVersion = sinceKotlinValue.apiVersion;
        }
        if ((i & 2) != 0) {
            list = sinceKotlinValue.wasExperimentalMarkerClasses;
        }
        return sinceKotlinValue.copy(apiVersion, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ApiVersion getApiVersion() {
        return this.apiVersion;
    }

    public final List<ClassDescriptor> component2() {
        return this.wasExperimentalMarkerClasses;
    }

    public final SinceKotlinValue copy(ApiVersion apiVersion, List<? extends ClassDescriptor> wasExperimentalMarkerClasses) {
        Intrinsics.checkNotNullParameter(apiVersion, "apiVersion");
        Intrinsics.checkNotNullParameter(wasExperimentalMarkerClasses, "wasExperimentalMarkerClasses");
        return new SinceKotlinValue(apiVersion, wasExperimentalMarkerClasses);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SinceKotlinValue)) {
            return false;
        }
        SinceKotlinValue sinceKotlinValue = (SinceKotlinValue) other;
        return Intrinsics.areEqual(this.apiVersion, sinceKotlinValue.apiVersion) && Intrinsics.areEqual(this.wasExperimentalMarkerClasses, sinceKotlinValue.wasExperimentalMarkerClasses);
    }

    public int hashCode() {
        return (this.apiVersion.hashCode() * 31) + this.wasExperimentalMarkerClasses.hashCode();
    }

    public String toString() {
        return "SinceKotlinValue(apiVersion=" + this.apiVersion + ", wasExperimentalMarkerClasses=" + this.wasExperimentalMarkerClasses + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SinceKotlinValue(ApiVersion apiVersion, List<? extends ClassDescriptor> wasExperimentalMarkerClasses) {
        Intrinsics.checkNotNullParameter(apiVersion, "apiVersion");
        Intrinsics.checkNotNullParameter(wasExperimentalMarkerClasses, "wasExperimentalMarkerClasses");
        this.apiVersion = apiVersion;
        this.wasExperimentalMarkerClasses = wasExperimentalMarkerClasses;
    }

    public final ApiVersion getApiVersion() {
        return this.apiVersion;
    }

    public final List<ClassDescriptor> getWasExperimentalMarkerClasses() {
        return this.wasExperimentalMarkerClasses;
    }
}
