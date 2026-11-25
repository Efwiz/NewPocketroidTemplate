package org.catrobat.catroid.io.asynctask;

import java.io.File;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectRenamer.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectRenamer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J2\u0010\u0007\u001a\u00020\b2\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\b0\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0007R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/io/asynctask/ProjectRenamer;", "", "projectDirectory", "Ljava/io/File;", "destinationName", "", "(Ljava/io/File;Ljava/lang/String;)V", "renameProjectAsync", "", "onRenameProjectComplete", "Lkotlin/Function1;", "", "scope", "Lkotlinx/coroutines/CoroutineScope;", "dispatcherOnFinished", "Lkotlinx/coroutines/CoroutineDispatcher;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectRenamer {
    private final String destinationName;
    private final File projectDirectory;

    public final void renameProjectAsync() {
        renameProjectAsync$default(this, null, null, null, 7, null);
    }

    public final void renameProjectAsync(Function1<? super Boolean, Unit> onRenameProjectComplete) {
        Intrinsics.checkNotNullParameter(onRenameProjectComplete, "onRenameProjectComplete");
        renameProjectAsync$default(this, onRenameProjectComplete, null, null, 6, null);
    }

    public final void renameProjectAsync(Function1<? super Boolean, Unit> onRenameProjectComplete, CoroutineScope scope) {
        Intrinsics.checkNotNullParameter(onRenameProjectComplete, "onRenameProjectComplete");
        Intrinsics.checkNotNullParameter(scope, "scope");
        renameProjectAsync$default(this, onRenameProjectComplete, scope, null, 4, null);
    }

    public ProjectRenamer(File projectDirectory, String destinationName) {
        Intrinsics.checkNotNullParameter(projectDirectory, "projectDirectory");
        Intrinsics.checkNotNullParameter(destinationName, "destinationName");
        this.projectDirectory = projectDirectory;
        this.destinationName = destinationName;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void renameProjectAsync$default(ProjectRenamer projectRenamer, Function1 function1, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = new Function1<Boolean, Unit>() { // from class: org.catrobat.catroid.io.asynctask.ProjectRenamer.renameProjectAsync.1
                public final void invoke(boolean z) {
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                    invoke(bool.booleanValue());
                    return Unit.INSTANCE;
                }
            };
        }
        if ((i & 2) != 0) {
            coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
        }
        if ((i & 4) != 0) {
            coroutineDispatcher = Dispatchers.getMain();
        }
        projectRenamer.renameProjectAsync(function1, coroutineScope, coroutineDispatcher);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectRenamer$renameProjectAsync$2.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.io.asynctask.ProjectRenamer$renameProjectAsync$2, reason: invalid class name */
    /* JADX INFO: compiled from: ProjectRenamer.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.io.asynctask.ProjectRenamer$renameProjectAsync$2", f = "ProjectRenamer.kt", i = {}, l = {52}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ CoroutineDispatcher $dispatcherOnFinished;
        final /* synthetic */ Function1<Boolean, Unit> $onRenameProjectComplete;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass2(CoroutineDispatcher coroutineDispatcher, Function1<? super Boolean, Unit> function1, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$dispatcherOnFinished = coroutineDispatcher;
            this.$onRenameProjectComplete = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ProjectRenamer.this.new AnonymousClass2(this.$dispatcherOnFinished, this.$onRenameProjectComplete, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                boolean z = ProjectRenamerKt.renameProject(ProjectRenamer.this.projectDirectory, ProjectRenamer.this.destinationName) != null;
                this.label = 1;
                if (BuildersKt.withContext(this.$dispatcherOnFinished, new AnonymousClass1(this.$onRenameProjectComplete, z, null), this) == coroutine_suspended) {
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
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectRenamer$renameProjectAsync$2$1.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.io.asynctask.ProjectRenamer$renameProjectAsync$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: ProjectRenamer.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.io.asynctask.ProjectRenamer$renameProjectAsync$2$1", f = "ProjectRenamer.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Function1<Boolean, Unit> $onRenameProjectComplete;
            final /* synthetic */ boolean $success;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            AnonymousClass1(Function1<? super Boolean, Unit> function1, boolean z, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$onRenameProjectComplete = function1;
                this.$success = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.$onRenameProjectComplete, this.$success, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                this.$onRenameProjectComplete.invoke(Boxing.boxBoolean(this.$success));
                return Unit.INSTANCE;
            }
        }
    }

    public final void renameProjectAsync(Function1<? super Boolean, Unit> onRenameProjectComplete, CoroutineScope scope, CoroutineDispatcher dispatcherOnFinished) {
        Intrinsics.checkNotNullParameter(onRenameProjectComplete, "onRenameProjectComplete");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(dispatcherOnFinished, "dispatcherOnFinished");
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new AnonymousClass2(dispatcherOnFinished, onRenameProjectComplete, null), 3, null);
    }
}
