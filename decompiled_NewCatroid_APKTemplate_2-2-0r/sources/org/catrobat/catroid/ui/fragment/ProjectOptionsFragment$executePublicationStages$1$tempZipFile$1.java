package org.catrobat.catroid.ui.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$executePublicationStages$1$tempZipFile$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectOptionsFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Ljava/io/File;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$executePublicationStages$1$tempZipFile$1", f = "ProjectOptionsFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class ProjectOptionsFragment$executePublicationStages$1$tempZipFile$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super File>, Object> {
    final /* synthetic */ Project $currentProject;
    int label;
    final /* synthetic */ ProjectOptionsFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ProjectOptionsFragment$executePublicationStages$1$tempZipFile$1(ProjectOptionsFragment projectOptionsFragment, Project project, Continuation<? super ProjectOptionsFragment$executePublicationStages$1$tempZipFile$1> continuation) {
        super(2, continuation);
        this.this$0 = projectOptionsFragment;
        this.$currentProject = project;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ProjectOptionsFragment$executePublicationStages$1$tempZipFile$1(this.this$0, this.$currentProject, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super File> continuation) {
        return ((ProjectOptionsFragment$executePublicationStages$1$tempZipFile$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
        ResultKt.throwOnFailure(obj);
        File file = new File(this.this$0.requireContext().getCacheDir(), "publish_temp_" + System.currentTimeMillis() + Constants.ZIP_EXTENSION);
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
        ProjectOptionsFragment projectOptionsFragment = this.this$0;
        try {
            File directory = this.$currentProject.getDirectory();
            Intrinsics.checkNotNullExpressionValue(directory, "getDirectory(...)");
            ProjectOptionsFragment.zipDirectory3$default(projectOptionsFragment, directory, zipOutputStream, null, 4, null);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(zipOutputStream, null);
            return file;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(zipOutputStream, th);
                throw th2;
            }
        }
    }
}
