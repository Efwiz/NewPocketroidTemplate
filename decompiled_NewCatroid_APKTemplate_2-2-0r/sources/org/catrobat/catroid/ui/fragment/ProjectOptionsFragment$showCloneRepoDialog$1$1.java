package org.catrobat.catroid.ui.fragment;

import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.git.GitController;
import org.catrobat.catroid.utils.git.GitResult;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$showCloneRepoDialog$1$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectOptionsFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$showCloneRepoDialog$1$1", f = "ProjectOptionsFragment.kt", i = {}, l = {1687}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class ProjectOptionsFragment$showCloneRepoDialog$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ File $originalProjectDir;
    final /* synthetic */ String $repoUrl;
    final /* synthetic */ File $tempDir;
    final /* synthetic */ String $token;
    int label;
    final /* synthetic */ ProjectOptionsFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ProjectOptionsFragment$showCloneRepoDialog$1$1(ProjectOptionsFragment projectOptionsFragment, String str, String str2, File file, File file2, Continuation<? super ProjectOptionsFragment$showCloneRepoDialog$1$1> continuation) {
        super(2, continuation);
        this.this$0 = projectOptionsFragment;
        this.$repoUrl = str;
        this.$token = str2;
        this.$tempDir = file;
        this.$originalProjectDir = file2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ProjectOptionsFragment$showCloneRepoDialog$1$1(this.this$0, this.$repoUrl, this.$token, this.$tempDir, this.$originalProjectDir, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ProjectOptionsFragment$showCloneRepoDialog$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r11v16, types: [T, org.catrobat.catroid.utils.git.GitResult$Success] */
    /* JADX WARN: Type inference failed for: r11v4, types: [T, org.catrobat.catroid.utils.git.GitResult] */
    /* JADX WARN: Type inference failed for: r3v1, types: [T, org.catrobat.catroid.utils.git.GitResult$Error] */
    /* JADX WARN: Type inference failed for: r3v4, types: [T, org.catrobat.catroid.utils.git.GitResult$Error] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            GitController gitController = this.this$0.gitController;
            if (gitController == null) {
                Intrinsics.throwUninitializedPropertyAccessException("gitController");
                gitController = null;
            }
            ?? CloneRepository = gitController.cloneRepository(this.$repoUrl, this.$token, this.$tempDir);
            Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = new GitResult.Error("Operation failed before replacement.", null, 2, null);
            if (CloneRepository instanceof GitResult.Success) {
                try {
                    FilesKt.deleteRecursively(this.$originalProjectDir);
                    if (!this.$tempDir.renameTo(this.$originalProjectDir)) {
                        throw new IOException("Failed to replace project directory.");
                    }
                    Project projectLoadProject = XstreamSerializer.getInstance().loadProject(this.$originalProjectDir, this.this$0.requireContext());
                    if (projectLoadProject == null) {
                        throw new IOException("Failed to load cloned project from disk.");
                    }
                    projectLoadProject.getXmlHeader().setGitRemoteUrl(this.$repoUrl);
                    XstreamSerializer.getInstance().saveProject(projectLoadProject);
                    this.this$0.getProjectManager().setCurrentProject(projectLoadProject);
                    this.this$0.project = projectLoadProject;
                    projectLoadProject.getXmlHeader().setGitRemoteUrl(this.$repoUrl);
                    XstreamSerializer.getInstance().saveProject(projectLoadProject);
                    objectRef.element = new GitResult.Success(Unit.INSTANCE);
                } catch (Exception e) {
                    e.printStackTrace();
                    FilesKt.deleteRecursively(this.$tempDir);
                    objectRef.element = new GitResult.Error("Failed to replace or load project: " + e.getMessage(), null, 2, null);
                }
            } else {
                objectRef.element = CloneRepository;
            }
            this.label = 1;
            if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass1(this.this$0, objectRef, this.$originalProjectDir, this.$repoUrl, this.$tempDir, null), this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$showCloneRepoDialog$1$1$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$showCloneRepoDialog$1$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$showCloneRepoDialog$1$1$1", f = "ProjectOptionsFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Ref.ObjectRef<GitResult<Unit>> $finalResult;
        final /* synthetic */ File $originalProjectDir;
        final /* synthetic */ String $repoUrl;
        final /* synthetic */ File $tempDir;
        int label;
        final /* synthetic */ ProjectOptionsFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(ProjectOptionsFragment projectOptionsFragment, Ref.ObjectRef<GitResult<Unit>> objectRef, File file, String str, File file2, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = projectOptionsFragment;
            this.$finalResult = objectRef;
            this.$originalProjectDir = file;
            this.$repoUrl = str;
            this.$tempDir = file2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.this$0, this.$finalResult, this.$originalProjectDir, this.$repoUrl, this.$tempDir, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                this.this$0.hideProgressDialog();
                GitResult<Unit> gitResult = this.$finalResult.element;
                if (gitResult instanceof GitResult.Success) {
                    ToastUtil.showSuccess(this.this$0.requireContext(), "Проект успешно склонирован!");
                    this.this$0.getProjectManager().loadProject(this.$originalProjectDir);
                    this.this$0.getProjectManager().getCurrentProject().getXmlHeader().setGitRemoteUrl(this.$repoUrl);
                    ProjectOptionsFragment projectOptionsFragment = this.this$0;
                    projectOptionsFragment.project = projectOptionsFragment.getProjectManager().getCurrentProject();
                    this.this$0.shouldSaveOnPause = true;
                    this.this$0.showProjectReloadDialog();
                } else if (gitResult instanceof GitResult.Error) {
                    FilesKt.deleteRecursively(this.$tempDir);
                    ToastUtil.showError(this.this$0.requireContext(), "Ошибка: " + ((GitResult.Error) this.$finalResult.element).getMessage());
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
    }
}
