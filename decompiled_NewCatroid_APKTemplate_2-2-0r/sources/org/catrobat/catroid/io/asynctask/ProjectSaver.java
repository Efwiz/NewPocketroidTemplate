package org.catrobat.catroid.io.asynctask;

import android.content.Context;
import java.lang.ref.WeakReference;
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
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.catrobat.catroid.content.Project;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectSaver.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectSaver.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J(\u0010\t\u001a\u00020\n2\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\n0\f2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/io/asynctask/ProjectSaver;", "", "project", "Lorg/catrobat/catroid/content/Project;", "context", "Landroid/content/Context;", "(Lorg/catrobat/catroid/content/Project;Landroid/content/Context;)V", "weakContextReference", "Ljava/lang/ref/WeakReference;", "saveProjectAsync", "", "onSaveProjectComplete", "Lkotlin/Function1;", "", "scope", "Lkotlinx/coroutines/CoroutineScope;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectSaver {
    private final Project project;
    private final WeakReference<Context> weakContextReference;

    public final void saveProjectAsync() {
        saveProjectAsync$default(this, null, null, 3, null);
    }

    public final void saveProjectAsync(Function1<? super Boolean, Unit> onSaveProjectComplete) {
        Intrinsics.checkNotNullParameter(onSaveProjectComplete, "onSaveProjectComplete");
        saveProjectAsync$default(this, onSaveProjectComplete, null, 2, null);
    }

    public ProjectSaver(Project project, Context context) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(context, "context");
        this.project = project;
        this.weakContextReference = new WeakReference<>(context);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void saveProjectAsync$default(ProjectSaver projectSaver, Function1 function1, CoroutineScope coroutineScope, int i, Object obj) {
        if ((i & 1) != 0) {
            function1 = new Function1<Boolean, Unit>() { // from class: org.catrobat.catroid.io.asynctask.ProjectSaver.saveProjectAsync.1
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
        projectSaver.saveProjectAsync(function1, coroutineScope);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectSaver$saveProjectAsync$2.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.io.asynctask.ProjectSaver$saveProjectAsync$2, reason: invalid class name */
    /* JADX INFO: compiled from: ProjectSaver.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.io.asynctask.ProjectSaver$saveProjectAsync$2", f = "ProjectSaver.kt", i = {}, l = {50}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Context $context;
        final /* synthetic */ Function1<Boolean, Unit> $onSaveProjectComplete;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass2(Context context, Function1<? super Boolean, Unit> function1, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$context = context;
            this.$onSaveProjectComplete = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ProjectSaver.this.new AnonymousClass2(this.$context, this.$onSaveProjectComplete, continuation);
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
                boolean zSaveProjectSerial = ProjectSaverKt.saveProjectSerial(ProjectSaver.this.project, this.$context);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass1(this.$onSaveProjectComplete, zSaveProjectSerial, null), this) == coroutine_suspended) {
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
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectSaver$saveProjectAsync$2$1.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.io.asynctask.ProjectSaver$saveProjectAsync$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: ProjectSaver.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.io.asynctask.ProjectSaver$saveProjectAsync$2$1", f = "ProjectSaver.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Function1<Boolean, Unit> $onSaveProjectComplete;
            final /* synthetic */ boolean $projectSaved;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            AnonymousClass1(Function1<? super Boolean, Unit> function1, boolean z, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$onSaveProjectComplete = function1;
                this.$projectSaved = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.$onSaveProjectComplete, this.$projectSaved, continuation);
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
                this.$onSaveProjectComplete.invoke(Boxing.boxBoolean(this.$projectSaved));
                return Unit.INSTANCE;
            }
        }
    }

    public final void saveProjectAsync(Function1<? super Boolean, Unit> onSaveProjectComplete, CoroutineScope scope) {
        Intrinsics.checkNotNullParameter(onSaveProjectComplete, "onSaveProjectComplete");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Context context = this.weakContextReference.get();
        if (context == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new AnonymousClass2(context, onSaveProjectComplete, null), 3, null);
    }
}
