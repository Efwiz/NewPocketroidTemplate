package org.catrobat.catroid.io.asynctask;

import android.content.Context;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.catrobat.catroid.ui.ProjectUploadActivityKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectLoader.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectLoader.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00112\u00020\u0001:\u0002\u0011\u0012B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007J\u000e\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/io/asynctask/ProjectLoader;", "", ProjectUploadActivityKt.PROJECT_DIR, "Ljava/io/File;", "context", "Landroid/content/Context;", "(Ljava/io/File;Landroid/content/Context;)V", "weakContextReference", "Ljava/lang/ref/WeakReference;", "weakListenerReference", "Lorg/catrobat/catroid/io/asynctask/ProjectLoader$ProjectLoadListener;", "loadProjectAsync", "", "scope", "Lkotlinx/coroutines/CoroutineScope;", "setListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Companion", "ProjectLoadListener", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectLoader {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "ProjectLoader";
    private File projectDir;
    private WeakReference<Context> weakContextReference;
    private WeakReference<ProjectLoadListener> weakListenerReference;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectLoader$ProjectLoadListener.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectLoader.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/io/asynctask/ProjectLoader$ProjectLoadListener;", "", "onLoadFinished", "", FirebaseAnalytics.Param.SUCCESS, "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface ProjectLoadListener {
        void onLoadFinished(boolean success);
    }

    public final void loadProjectAsync() {
        loadProjectAsync$default(this, null, 1, null);
    }

    public ProjectLoader(File projectDir, Context context) {
        Intrinsics.checkNotNullParameter(projectDir, "projectDir");
        Intrinsics.checkNotNullParameter(context, "context");
        this.projectDir = projectDir;
        this.weakContextReference = new WeakReference<>(context);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectLoader$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectLoader.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/io/asynctask/ProjectLoader$Companion;", "", "()V", "TAG", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return ProjectLoader.TAG;
        }
    }

    public final ProjectLoader setListener(ProjectLoadListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.weakListenerReference = new WeakReference<>(listener);
        return this;
    }

    public static /* synthetic */ void loadProjectAsync$default(ProjectLoader projectLoader, CoroutineScope coroutineScope, int i, Object obj) {
        if ((i & 1) != 0) {
            coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
        }
        projectLoader.loadProjectAsync(coroutineScope);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectLoader$loadProjectAsync$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.io.asynctask.ProjectLoader$loadProjectAsync$1, reason: invalid class name */
    /* JADX INFO: compiled from: ProjectLoader.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.io.asynctask.ProjectLoader$loadProjectAsync$1", f = "ProjectLoader.kt", i = {}, l = {58}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ Context $context;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(Context context, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ProjectLoader.this.new AnonymousClass1(this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                boolean zLoadProject = ProjectLoaderKt.loadProject(ProjectLoader.this.projectDir, this.$context);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C02551(ProjectLoader.this, zLoadProject, null), this) == coroutine_suspended) {
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
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/asynctask/ProjectLoader$loadProjectAsync$1$1.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.io.asynctask.ProjectLoader$loadProjectAsync$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ProjectLoader.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.io.asynctask.ProjectLoader$loadProjectAsync$1$1", f = "ProjectLoader.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02551 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ boolean $projectSaved;
            int label;
            final /* synthetic */ ProjectLoader this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02551(ProjectLoader projectLoader, boolean z, Continuation<? super C02551> continuation) {
                super(2, continuation);
                this.this$0 = projectLoader;
                this.$projectSaved = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02551(this.this$0, this.$projectSaved, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02551) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                ProjectLoadListener projectLoadListener;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    WeakReference weakReference = this.this$0.weakListenerReference;
                    if (weakReference == null || (projectLoadListener = (ProjectLoadListener) weakReference.get()) == null) {
                        return Unit.INSTANCE;
                    }
                    projectLoadListener.onLoadFinished(this.$projectSaved);
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
        }
    }

    public final void loadProjectAsync(CoroutineScope scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Context context = this.weakContextReference.get();
        if (context == null) {
            return;
        }
        BuildersKt__Builders_commonKt.launch$default(scope, null, null, new AnonymousClass1(context, null), 3, null);
    }
}
