package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RunLuaAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: RunLuaAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001aH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/RunLuaAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getCode", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setCode", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "isFinished", "", "isRunning", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "userVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "getUserVariable", "()Lorg/catrobat/catroid/formulaeditor/UserVariable;", "setUserVariable", "(Lorg/catrobat/catroid/formulaeditor/UserVariable;)V", "act", "delta", "", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RunLuaAction extends TemporalAction {
    private Formula code;
    private boolean isFinished;
    private boolean isRunning;
    private Scope scope;
    private UserVariable userVariable;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<Globals> sharedGlobals$delegate = LazyKt.lazy(new Function0<Globals>() { // from class: org.catrobat.catroid.content.actions.RunLuaAction$Companion$sharedGlobals$2
        @Override // kotlin.jvm.functions.Function0
        public final Globals invoke() {
            return JsePlatform.standardGlobals();
        }
    });
    private static final ConcurrentHashMap<String, LuaValue> compiledChunks = new ConcurrentHashMap<>();

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
    }

    public static final /* synthetic */ void access$setFinished$p(RunLuaAction runLuaAction, boolean z) {
        runLuaAction.isFinished = z;
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getCode() {
        return this.code;
    }

    public final void setCode(Formula formula) {
        this.code = formula;
    }

    public final UserVariable getUserVariable() {
        return this.userVariable;
    }

    public final void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RunLuaAction$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: RunLuaAction.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/catrobat/catroid/content/actions/RunLuaAction$Companion;", "", "()V", "compiledChunks", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lorg/luaj/vm2/LuaValue;", "sharedGlobals", "Lorg/luaj/vm2/Globals;", "getSharedGlobals", "()Lorg/luaj/vm2/Globals;", "sharedGlobals$delegate", "Lkotlin/Lazy;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Globals getSharedGlobals() {
            Object value = RunLuaAction.sharedGlobals$delegate.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            return (Globals) value;
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.isRunning) {
            this.isRunning = true;
            Formula formula = this.code;
            String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
            if (strInterpretString == null) {
                strInterpretString = "return 0";
            }
            BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getDefault()), null, null, new AnonymousClass1(strInterpretString, this, null), 3, null);
        }
        return this.isFinished;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RunLuaAction$act$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.RunLuaAction$act$1, reason: invalid class name */
    /* JADX INFO: compiled from: RunLuaAction.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.content.actions.RunLuaAction$act$1", f = "RunLuaAction.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $codeStr;
        int label;
        final /* synthetic */ RunLuaAction this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(String str, RunLuaAction runLuaAction, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$codeStr = str;
            this.this$0 = runLuaAction;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$codeStr, this.this$0, continuation);
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
                try {
                    LuaValue luaValueLoad = (LuaValue) RunLuaAction.compiledChunks.get(this.$codeStr);
                    if (luaValueLoad == null) {
                        luaValueLoad = RunLuaAction.INSTANCE.getSharedGlobals().load(this.$codeStr);
                        String str = this.$codeStr;
                        ConcurrentHashMap concurrentHashMap = RunLuaAction.compiledChunks;
                        Intrinsics.checkNotNull(luaValueLoad);
                        concurrentHashMap.put(str, luaValueLoad);
                        Intrinsics.checkNotNullExpressionValue(luaValueLoad, "also(...)");
                    }
                    final LuaValue luaValueCall = luaValueLoad.call();
                    Intrinsics.checkNotNullExpressionValue(luaValueCall, "call(...)");
                    Application application = Gdx.f82app;
                    final RunLuaAction runLuaAction = this.this$0;
                    application.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.actions.RunLuaAction$act$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            RunLuaAction.AnonymousClass1.invokeSuspend$lambda$1(runLuaAction, luaValueCall);
                        }
                    });
                } catch (Throwable th) {
                    Log.e("RunLuaAction", "FATAL LUA ERROR DURING GENERATION: ", th);
                    Application application2 = Gdx.f82app;
                    final RunLuaAction runLuaAction2 = this.this$0;
                    application2.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.actions.RunLuaAction$act$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            RunLuaAction.access$setFinished$p(runLuaAction2, true);
                        }
                    });
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invokeSuspend$lambda$1(RunLuaAction runLuaAction, LuaValue luaValue) {
            UserVariable userVariable = runLuaAction.getUserVariable();
            if (userVariable != null) {
                userVariable.setValue(luaValue.tojstring());
            }
            runLuaAction.isFinished = true;
        }
    }
}
