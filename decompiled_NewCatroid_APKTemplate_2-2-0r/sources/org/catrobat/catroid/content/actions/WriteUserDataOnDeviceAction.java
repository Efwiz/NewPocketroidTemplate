package org.catrobat.catroid.content.actions;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import net.bytebuddy.implementation.Implementation;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.io.DeviceUserDataAccessor;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WriteUserDataOnDeviceAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: WriteUserDataOnDeviceAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0014\u001a\u00020\u00152\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0002J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\b\u0010\u0017\u001a\u00020\u0013H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/catrobat/catroid/content/actions/WriteUserDataOnDeviceAction;", "Lorg/catrobat/catroid/content/actions/AsynchronousAction;", "()V", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/catrobat/catroid/io/DeviceUserDataAccessor;", "getAccessor", "()Lorg/catrobat/catroid/io/DeviceUserDataAccessor;", "setAccessor", "(Lorg/catrobat/catroid/io/DeviceUserDataAccessor;)V", "scopeIO", "Lkotlinx/coroutines/CoroutineScope;", "userData", "Lorg/catrobat/catroid/formulaeditor/UserData;", "", "getUserData", "()Lorg/catrobat/catroid/formulaeditor/UserData;", "setUserData", "(Lorg/catrobat/catroid/formulaeditor/UserData;)V", "writeActionFinished", "", "executeWriteTask", "", "initialize", "isFinished", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WriteUserDataOnDeviceAction extends AsynchronousAction {
    private DeviceUserDataAccessor accessor;
    private final CoroutineScope scopeIO = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
    private UserData<Object> userData;
    private boolean writeActionFinished;

    public final UserData<Object> getUserData() {
        return this.userData;
    }

    public final void setUserData(UserData<Object> userData) {
        this.userData = userData;
    }

    public final DeviceUserDataAccessor getAccessor() {
        return this.accessor;
    }

    public final void setAccessor(DeviceUserDataAccessor deviceUserDataAccessor) {
        this.accessor = deviceUserDataAccessor;
    }

    @Override // org.catrobat.catroid.content.actions.AsynchronousAction
    public void initialize() {
        this.writeActionFinished = false;
        UserData<Object> userData = this.userData;
        if (userData != null) {
            executeWriteTask(userData);
        }
    }

    @Override // org.catrobat.catroid.content.actions.AsynchronousAction
    /* JADX INFO: renamed from: isFinished */
    public boolean getIsFinished() {
        return this.writeActionFinished || this.userData == null;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WriteUserDataOnDeviceAction$executeWriteTask$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.WriteUserDataOnDeviceAction$executeWriteTask$1, reason: invalid class name */
    /* JADX INFO: compiled from: WriteUserDataOnDeviceAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.content.actions.WriteUserDataOnDeviceAction$executeWriteTask$1", f = "WriteUserDataOnDeviceAction.kt", i = {}, l = {50}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ UserData<Object> $userData;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(UserData<Object> userData, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$userData = userData;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return WriteUserDataOnDeviceAction.this.new AnonymousClass1(this.$userData, continuation);
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
                DeviceUserDataAccessor accessor = WriteUserDataOnDeviceAction.this.getAccessor();
                if (accessor != null) {
                    accessor.writeUserData(this.$userData);
                }
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getMain(), new C02531(WriteUserDataOnDeviceAction.this, null), this) == coroutine_suspended) {
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
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WriteUserDataOnDeviceAction$executeWriteTask$1$1.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.WriteUserDataOnDeviceAction$executeWriteTask$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: WriteUserDataOnDeviceAction.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.content.actions.WriteUserDataOnDeviceAction$executeWriteTask$1$1", f = "WriteUserDataOnDeviceAction.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02531 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ WriteUserDataOnDeviceAction this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02531(WriteUserDataOnDeviceAction writeUserDataOnDeviceAction, Continuation<? super C02531> continuation) {
                super(2, continuation);
                this.this$0 = writeUserDataOnDeviceAction;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02531(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02531) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.writeActionFinished = true;
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
        }
    }

    private final void executeWriteTask(UserData<Object> userData) {
        BuildersKt__Builders_commonKt.launch$default(this.scopeIO, null, null, new AnonymousClass1(userData, null), 3, null);
    }
}
