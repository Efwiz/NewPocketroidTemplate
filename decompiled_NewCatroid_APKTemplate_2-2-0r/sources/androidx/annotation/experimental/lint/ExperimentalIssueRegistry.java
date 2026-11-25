package androidx.annotation.experimental.lint;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: ExperimentalIssueRegistry.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/annotation-experimental-1.1.0.aar:lint.jar:androidx/annotation/experimental/lint/ExperimentalIssueRegistry.class */
@Metadata(mv = {1, 4, 2}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0006¨\u0006\u000e"}, d2 = {"Landroidx/annotation/experimental/lint/ExperimentalIssueRegistry;", "Lcom/android/tools/lint/client/api/IssueRegistry;", "()V", "api", "", "getApi", "()I", "issues", "", "Lcom/android/tools/lint/detector/api/Issue;", "getIssues", "()Ljava/util/List;", "minApi", "getMinApi", "annotation-experimental-lint"})
public final class ExperimentalIssueRegistry extends IssueRegistry {
    private final int minApi = 8;
    private final int api = 8;

    public int getMinApi() {
        return this.minApi;
    }

    public int getApi() {
        return this.api;
    }

    @NotNull
    public List<Issue> getIssues() {
        return ExperimentalDetector.INSTANCE.getISSUES();
    }
}
