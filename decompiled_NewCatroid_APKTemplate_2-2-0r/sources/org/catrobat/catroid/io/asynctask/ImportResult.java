package org.catrobat.catroid.io.asynctask;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ui.ProjectUploadActivityKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ImportResult.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectUnZipperAndImporter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/io/asynctask/ImportResult;", "", "()V", "BakedProject", "Failure", "Success", "Lorg/catrobat/catroid/io/asynctask/ImportResult$BakedProject;", "Lorg/catrobat/catroid/io/asynctask/ImportResult$Failure;", "Lorg/catrobat/catroid/io/asynctask/ImportResult$Success;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ImportResult {
    public /* synthetic */ ImportResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ImportResult$Success.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectUnZipperAndImporter.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/catrobat/catroid/io/asynctask/ImportResult$Success;", "Lorg/catrobat/catroid/io/asynctask/ImportResult;", "()V", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Success extends ImportResult {
        public static final Success INSTANCE = new Success();

        private Success() {
            super(null);
        }
    }

    private ImportResult() {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ImportResult$Failure.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectUnZipperAndImporter.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/catrobat/catroid/io/asynctask/ImportResult$Failure;", "Lorg/catrobat/catroid/io/asynctask/ImportResult;", "()V", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Failure extends ImportResult {
        public static final Failure INSTANCE = new Failure();

        private Failure() {
            super(null);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ImportResult$BakedProject.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectUnZipperAndImporter.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/io/asynctask/ImportResult$BakedProject;", "Lorg/catrobat/catroid/io/asynctask/ImportResult;", ProjectUploadActivityKt.PROJECT_DIR, "Ljava/io/File;", "(Ljava/io/File;)V", "getProjectDir", "()Ljava/io/File;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class BakedProject extends ImportResult {
        private final File projectDir;

        public static /* synthetic */ BakedProject copy$default(BakedProject bakedProject, File file, int i, Object obj) {
            if ((i & 1) != 0) {
                file = bakedProject.projectDir;
            }
            return bakedProject.copy(file);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final File getProjectDir() {
            return this.projectDir;
        }

        public final BakedProject copy(File projectDir) {
            Intrinsics.checkNotNullParameter(projectDir, "projectDir");
            return new BakedProject(projectDir);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof BakedProject) && Intrinsics.areEqual(this.projectDir, ((BakedProject) other).projectDir);
        }

        public int hashCode() {
            return this.projectDir.hashCode();
        }

        public String toString() {
            return "BakedProject(projectDir=" + this.projectDir + Constant.AFTER_QUTO;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BakedProject(File projectDir) {
            super(null);
            Intrinsics.checkNotNullParameter(projectDir, "projectDir");
            this.projectDir = projectDir;
        }

        public final File getProjectDir() {
            return this.projectDir;
        }
    }
}
