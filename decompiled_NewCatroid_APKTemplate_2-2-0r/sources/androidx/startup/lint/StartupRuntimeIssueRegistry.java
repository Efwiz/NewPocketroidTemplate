package androidx.startup.lint;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.client.api.Vendor;
import com.android.tools.lint.detector.api.Issue;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: StartupRuntimeIssueRegistry.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/startup-runtime-1.1.1.aar:lint.jar:androidx/startup/lint/StartupRuntimeIssueRegistry.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0006R\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Landroidx/startup/lint/StartupRuntimeIssueRegistry;", "Lcom/android/tools/lint/client/api/IssueRegistry;", "()V", "api", "", "getApi", "()I", "issues", "", "Lcom/android/tools/lint/detector/api/Issue;", "getIssues", "()Ljava/util/List;", "minApi", "getMinApi", "vendor", "Lcom/android/tools/lint/client/api/Vendor;", "getVendor", "()Lcom/android/tools/lint/client/api/Vendor;", "startup-runtime-lint"})
public final class StartupRuntimeIssueRegistry extends IssueRegistry {
    private final int minApi = 10;
    private final int api = 11;

    @NotNull
    private final Vendor vendor = new Vendor("Android Open Source Project", "androidx.startup", "https://issuetracker.google.com/issues/new?component=823348", (String) null, 8, (DefaultConstructorMarker) null);

    public int getMinApi() {
        return this.minApi;
    }

    public int getApi() {
        return this.api;
    }

    @NotNull
    public List<Issue> getIssues() {
        return CollectionsKt.listOf((Object[]) new Issue[]{InitializerConstructorDetector.INSTANCE.getISSUE(), EnsureInitializerMetadataDetector.INSTANCE.getISSUE()});
    }

    @NotNull
    public Vendor getVendor() {
        return this.vendor;
    }
}
