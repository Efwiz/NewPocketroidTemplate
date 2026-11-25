package kotlin.script.experimental.api;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.annotation.Annotation;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptData.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003B\u0017\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\r\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\tJ\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0006HÆ\u0003J*\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0004\u001a\u00028\u00002\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÆ\u0001¢\u0006\u0002\u0010\u0010J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0003HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0018"}, d2 = {"Lkotlin/script/experimental/api/ScriptSourceAnnotation;", "A", "", "", "annotation", FirebaseAnalytics.Param.LOCATION, "Lkotlin/script/experimental/api/SourceCode$LocationWithId;", "(Ljava/lang/annotation/Annotation;Lkotlin/script/experimental/api/SourceCode$LocationWithId;)V", "getAnnotation", "()Ljava/lang/annotation/Annotation;", "Ljava/lang/annotation/Annotation;", "getLocation", "()Lkotlin/script/experimental/api/SourceCode$LocationWithId;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Ljava/lang/annotation/Annotation;Lkotlin/script/experimental/api/SourceCode$LocationWithId;)Lkotlin/script/experimental/api/ScriptSourceAnnotation;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ScriptSourceAnnotation<A extends Annotation> {
    private final A annotation;
    private final SourceCode.LocationWithId location;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ScriptSourceAnnotation copy$default(ScriptSourceAnnotation scriptSourceAnnotation, Annotation annotation, SourceCode.LocationWithId locationWithId, int i, Object obj) {
        if ((i & 1) != 0) {
            annotation = scriptSourceAnnotation.annotation;
        }
        if ((i & 2) != 0) {
            locationWithId = scriptSourceAnnotation.location;
        }
        return scriptSourceAnnotation.copy(annotation, locationWithId);
    }

    public final A component1() {
        return this.annotation;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final SourceCode.LocationWithId getLocation() {
        return this.location;
    }

    public final ScriptSourceAnnotation<A> copy(A annotation, SourceCode.LocationWithId location) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        return new ScriptSourceAnnotation<>(annotation, location);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ScriptSourceAnnotation)) {
            return false;
        }
        ScriptSourceAnnotation scriptSourceAnnotation = (ScriptSourceAnnotation) other;
        return Intrinsics.areEqual(this.annotation, scriptSourceAnnotation.annotation) && Intrinsics.areEqual(this.location, scriptSourceAnnotation.location);
    }

    public int hashCode() {
        int iHashCode = this.annotation.hashCode() * 31;
        SourceCode.LocationWithId locationWithId = this.location;
        return iHashCode + (locationWithId == null ? 0 : locationWithId.hashCode());
    }

    public String toString() {
        return "ScriptSourceAnnotation(annotation=" + this.annotation + ", location=" + this.location + ')';
    }

    public ScriptSourceAnnotation(A annotation, SourceCode.LocationWithId locationWithId) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        this.annotation = annotation;
        this.location = locationWithId;
    }

    public final A getAnnotation() {
        return this.annotation;
    }

    public final SourceCode.LocationWithId getLocation() {
        return this.location;
    }
}
