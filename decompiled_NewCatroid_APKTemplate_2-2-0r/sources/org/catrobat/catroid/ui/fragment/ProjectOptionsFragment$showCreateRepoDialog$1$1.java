package org.catrobat.catroid.ui.fragment;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.git.GitController;
import org.catrobat.catroid.utils.git.GitResult;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$showCreateRepoDialog$1$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectOptionsFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$showCreateRepoDialog$1$1", f = "ProjectOptionsFragment.kt", i = {}, l = {1608}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class ProjectOptionsFragment$showCreateRepoDialog$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ boolean $isPrivate;
    final /* synthetic */ String $repoName;
    final /* synthetic */ String $token;
    int label;
    final /* synthetic */ ProjectOptionsFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ProjectOptionsFragment$showCreateRepoDialog$1$1(ProjectOptionsFragment projectOptionsFragment, String str, String str2, boolean z, Continuation<? super ProjectOptionsFragment$showCreateRepoDialog$1$1> continuation) {
        super(2, continuation);
        this.this$0 = projectOptionsFragment;
        this.$token = str;
        this.$repoName = str2;
        this.$isPrivate = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ProjectOptionsFragment$showCreateRepoDialog$1$1(this.this$0, this.$token, this.$repoName, this.$isPrivate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ProjectOptionsFragment$showCreateRepoDialog$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

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
            GitResult<String> gitResultInitializeAndPushNewRepository = gitController.initializeAndPushNewRepository(this.$token, this.$repoName, this.$isPrivate);
            this.label = 1;
            if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass1(this.this$0, gitResultInitializeAndPushNewRepository, null), this) == coroutine_suspended) {
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
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectOptionsFragment$showCreateRepoDialog$1$1$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$showCreateRepoDialog$1$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: ProjectOptionsFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.fragment.ProjectOptionsFragment$showCreateRepoDialog$1$1$1", f = "ProjectOptionsFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ GitResult<String> $result;
        int label;
        final /* synthetic */ ProjectOptionsFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(ProjectOptionsFragment projectOptionsFragment, GitResult<String> gitResult, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = projectOptionsFragment;
            this.$result = gitResult;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.this$0, this.$result, continuation);
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
                GitResult<String> gitResult = this.$result;
                if (gitResult instanceof GitResult.Success) {
                    ToastUtil.showSuccess(this.this$0.requireContext(), "Проект успешно опубликован!");
                    Project project = this.this$0.project;
                    XmlHeader xmlHeader = project != null ? project.getXmlHeader() : null;
                    if (xmlHeader != null) {
                        xmlHeader.setGitRemoteUrl((String) ((GitResult.Success) this.$result).getData());
                    }
                    this.this$0.saveProject();
                    this.this$0.updateGitButtonsVisibility();
                } else if (gitResult instanceof GitResult.Error) {
                    ToastUtil.showError(this.this$0.requireContext(), "Ошибка: " + ((GitResult.Error) this.$result).getMessage());
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
    }
}
