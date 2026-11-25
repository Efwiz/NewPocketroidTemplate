package org.catrobat.catroid.NN;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import org.catrobat.catroid.utils.NativeLibraryManager;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/NN/OnnxSessionManager.class
  classes7.dex
 */
/* JADX INFO: compiled from: OnnxSessionManager.kt */
/* JADX INFO: loaded from: classes7.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0012J\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u00142\u0006\u0010\u0011\u001a\u00020\u0012J\u0011\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u0012H\u0082 J3\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2#\u0010\u001b\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u001a¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\u00180\u001cJ\u0018\u0010 \u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u001a\u0018\u00010\u00142\u0006\u0010\u0019\u001a\u00020\u001aJ\u0013\u0010!\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u0019\u001a\u00020\u001aH\u0082 J\u0006\u0010\"\u001a\u00020\u0018J\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00180\u0014J\t\u0010$\u001a\u00020\u0018H\u0082 R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/NN/OnnxSessionManager;", "", "()V", "executor", "Ljava/util/concurrent/ExecutorService;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "isModelLoaded", "", "isWorking", "()Z", "setWorking", "(Z)V", "job", "Lkotlinx/coroutines/CompletableJob;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "loadModel", "modelPath", "", "loadModelAsync", "Ljava/util/concurrent/Future;", "loadModelJNI", "", "predict", "", "inputData", "", "onResult", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "result", "predictAsync", "runInferenceJNI", "unloadModel", "unloadModelAsync", "unloadModelJNI", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OnnxSessionManager {
    public static final OnnxSessionManager INSTANCE = new OnnxSessionManager();
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static boolean isModelLoaded;
    private static boolean isWorking;
    private static final CompletableJob job;
    private static final CoroutineScope scope;

    private final native int loadModelJNI(String modelPath);

    /* JADX INFO: Access modifiers changed from: private */
    public final native float[] runInferenceJNI(float[] inputData);

    private final native void unloadModelJNI();

    private OnnxSessionManager() {
    }

    static {
        isWorking = true;
        CompletableJob completableJobSupervisorJob$default = SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null);
        job = completableJobSupervisorJob$default;
        scope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus(completableJobSupervisorJob$default));
        if (NativeLibraryManager.INSTANCE.isLoaded(NativeLibraryManager.Feature.CORE)) {
            return;
        }
        isWorking = false;
    }

    public final boolean isWorking() {
        return isWorking;
    }

    public final void setWorking(boolean z) {
        isWorking = z;
    }

    public final boolean loadModel(String modelPath) {
        Intrinsics.checkNotNullParameter(modelPath, "modelPath");
        unloadModel();
        boolean z = loadModelJNI(modelPath) == 0;
        isModelLoaded = z;
        return z;
    }

    public final void predict(float[] inputData, Function1<? super float[], Unit> onResult) {
        Intrinsics.checkNotNullParameter(inputData, "inputData");
        Intrinsics.checkNotNullParameter(onResult, "onResult");
        if (isModelLoaded) {
            BuildersKt__Builders_commonKt.launch$default(scope, null, null, new AnonymousClass1(inputData, onResult, null), 3, null);
        } else {
            onResult.invoke(null);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/NN/OnnxSessionManager$predict$1.class
      classes7.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.NN.OnnxSessionManager$predict$1, reason: invalid class name */
    /* JADX INFO: compiled from: OnnxSessionManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.NN.OnnxSessionManager$predict$1", f = "OnnxSessionManager.kt", i = {}, l = {44}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ float[] $inputData;
        final /* synthetic */ Function1<float[], Unit> $onResult;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass1(float[] fArr, Function1<? super float[], Unit> function1, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$inputData = fArr;
            this.$onResult = function1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$inputData, this.$onResult, continuation);
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
                float[] fArrRunInferenceJNI = OnnxSessionManager.INSTANCE.runInferenceJNI(this.$inputData);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C02491(this.$onResult, fArrRunInferenceJNI, null), this) == coroutine_suspended) {
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
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/NN/OnnxSessionManager$predict$1$1.class
          classes7.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.NN.OnnxSessionManager$predict$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: OnnxSessionManager.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.NN.OnnxSessionManager$predict$1$1", f = "OnnxSessionManager.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02491 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Function1<float[], Unit> $onResult;
            final /* synthetic */ float[] $result;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            C02491(Function1<? super float[], Unit> function1, float[] fArr, Continuation<? super C02491> continuation) {
                super(2, continuation);
                this.$onResult = function1;
                this.$result = fArr;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02491(this.$onResult, this.$result, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02491) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                this.$onResult.invoke(this.$result);
                return Unit.INSTANCE;
            }
        }
    }

    public final void unloadModel() {
        if (isModelLoaded) {
            unloadModelJNI();
            isModelLoaded = false;
        }
    }

    public final Future<Boolean> loadModelAsync(final String modelPath) {
        Intrinsics.checkNotNullParameter(modelPath, "modelPath");
        Future<Boolean> futureSubmit = executor.submit(new Callable() { // from class: org.catrobat.catroid.NN.OnnxSessionManager$$ExternalSyntheticLambda2
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return OnnxSessionManager.loadModelAsync$lambda$0(modelPath);
            }
        });
        Intrinsics.checkNotNullExpressionValue(futureSubmit, "submit(...)");
        return futureSubmit;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Boolean loadModelAsync$lambda$0(String modelPath) {
        Intrinsics.checkNotNullParameter(modelPath, "$modelPath");
        OnnxSessionManager onnxSessionManager = INSTANCE;
        onnxSessionManager.unloadModel();
        boolean z = onnxSessionManager.loadModelJNI(modelPath) == 0;
        isModelLoaded = z;
        return Boolean.valueOf(z);
    }

    public final Future<float[]> predictAsync(final float[] inputData) {
        Intrinsics.checkNotNullParameter(inputData, "inputData");
        if (isModelLoaded) {
            return executor.submit(new Callable() { // from class: org.catrobat.catroid.NN.OnnxSessionManager$$ExternalSyntheticLambda1
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return OnnxSessionManager.predictAsync$lambda$1(inputData);
                }
            });
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float[] predictAsync$lambda$1(float[] inputData) {
        Intrinsics.checkNotNullParameter(inputData, "$inputData");
        return INSTANCE.runInferenceJNI(inputData);
    }

    public final Future<Unit> unloadModelAsync() {
        Future<Unit> futureSubmit = executor.submit(new Callable() { // from class: org.catrobat.catroid.NN.OnnxSessionManager$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return OnnxSessionManager.unloadModelAsync$lambda$2();
            }
        });
        Intrinsics.checkNotNullExpressionValue(futureSubmit, "submit(...)");
        return futureSubmit;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit unloadModelAsync$lambda$2() {
        if (isModelLoaded) {
            INSTANCE.unloadModelJNI();
            isModelLoaded = false;
        }
        return Unit.INSTANCE;
    }
}
