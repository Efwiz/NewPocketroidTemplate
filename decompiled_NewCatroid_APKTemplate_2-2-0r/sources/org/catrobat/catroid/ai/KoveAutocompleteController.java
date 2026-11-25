package org.catrobat.catroid.ai;

import android.util.Log;
import com.google.android.gms.cast.MediaError;
import com.reandroid.apkeditor.Options;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.ui.recyclerview.adapter.BrickAdapter;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/KoveAutocompleteController.class
  classes9.dex
 */
/* JADX INFO: compiled from: KoveAutocompleteController.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0013J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0002J\u0006\u0010\u0018\u001a\u00020\u0013J&\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u001d\u001a\u00020\u0013H\u0082@¢\u0006\u0002\u0010\u001eR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/ai/KoveAutocompleteController;", "", "()V", "activeScript", "Lorg/catrobat/catroid/content/Script;", "activeSprite", "Lorg/catrobat/catroid/content/Sprite;", "adapter", "Lorg/catrobat/catroid/ui/recyclerview/adapter/BrickAdapter;", "autocompleteJob", "Lkotlinx/coroutines/Job;", "currentPhantomBricks", "", "Lorg/catrobat/catroid/content/bricks/Brick;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "selectedIndex", "", "acceptSuggestions", "", "cancelActiveJob", "cleanResponse", "", Options.TYPE_RAW, "dismissSuggestions", "onUserActivity", "sprite", "script", "brickIndex", "triggerInference", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KoveAutocompleteController {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static KoveAutocompleteController instance;
    private Script activeScript;
    private Sprite activeSprite;
    private BrickAdapter adapter;
    private Job autocompleteJob;
    private final List<Brick> currentPhantomBricks;
    private final CoroutineScope scope;
    private int selectedIndex;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/KoveAutocompleteController$triggerInference$1.class
      classes9.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ai.KoveAutocompleteController$triggerInference$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: KoveAutocompleteController.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ai.KoveAutocompleteController", f = "KoveAutocompleteController.kt", i = {0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2}, l = {59, 66, 78}, m = "triggerInference", n = {"this", "sprite", "script", "currentAdapter", "this", "sprite", "script", "currentAdapter", "this", "sprite", "script", "currentAdapter"}, s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3"})
    static final class C41641 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        C41641(Continuation<? super C41641> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KoveAutocompleteController.this.triggerInference(this);
        }
    }

    public /* synthetic */ KoveAutocompleteController(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @JvmStatic
    public static final KoveAutocompleteController getInstance() {
        return INSTANCE.getInstance();
    }

    private KoveAutocompleteController() {
        this.selectedIndex = -1;
        this.scope = CoroutineScopeKt.CoroutineScope(Dispatchers.getMain().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null)));
        this.currentPhantomBricks = new ArrayList();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/KoveAutocompleteController$Companion.class
      classes9.dex
     */
    /* JADX INFO: compiled from: KoveAutocompleteController.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0004H\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/ai/KoveAutocompleteController$Companion;", "", "()V", "instance", "Lorg/catrobat/catroid/ai/KoveAutocompleteController;", "getInstance", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final KoveAutocompleteController getInstance() {
            if (KoveAutocompleteController.instance == null) {
                KoveAutocompleteController.instance = new KoveAutocompleteController(null);
            }
            KoveAutocompleteController koveAutocompleteController = KoveAutocompleteController.instance;
            Intrinsics.checkNotNull(koveAutocompleteController);
            return koveAutocompleteController;
        }
    }

    public final void onUserActivity(Sprite sprite, Script script, int brickIndex, BrickAdapter adapter) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        cancelActiveJob();
        this.activeSprite = sprite;
        this.activeScript = script;
        this.adapter = adapter;
        this.selectedIndex = brickIndex;
        if (!this.currentPhantomBricks.isEmpty()) {
            dismissSuggestions();
        }
        this.autocompleteJob = BuildersKt__Builders_commonKt.launch$default(this.scope, null, null, new AnonymousClass1(null), 3, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/KoveAutocompleteController$onUserActivity$1.class
      classes9.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ai.KoveAutocompleteController$onUserActivity$1, reason: invalid class name */
    /* JADX INFO: compiled from: KoveAutocompleteController.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ai.KoveAutocompleteController$onUserActivity$1", f = "KoveAutocompleteController.kt", i = {}, l = {48, 49}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return KoveAutocompleteController.this.new AnonymousClass1(continuation);
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
                this.label = 1;
                if (DelayKt.delay(2500L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                } else {
                    if (i != 2) {
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
            this.label = 2;
            if (KoveAutocompleteController.this.triggerInference(this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:55:0x0159 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:56:0x015a  */
    /* JADX WARN: Code duplicated, block: B:59:0x0164 A[Catch: Exception -> 0x0082, TryCatch #0 {Exception -> 0x0082, blocks: (B:14:0x004b, B:57:0x015c, B:59:0x0164, B:61:0x0167, B:62:0x016e, B:64:0x0174, B:65:0x017f, B:19:0x0068, B:46:0x010e, B:51:0x0138, B:49:0x012e, B:53:0x013b, B:22:0x007e, B:42:0x00bc, B:38:0x009d), top: B:70:0x0033 }] */
    /* JADX WARN: Code duplicated, block: B:61:0x0167 A[Catch: Exception -> 0x0082, TryCatch #0 {Exception -> 0x0082, blocks: (B:14:0x004b, B:57:0x015c, B:59:0x0164, B:61:0x0167, B:62:0x016e, B:64:0x0174, B:65:0x017f, B:19:0x0068, B:46:0x010e, B:51:0x0138, B:49:0x012e, B:53:0x013b, B:22:0x007e, B:42:0x00bc, B:38:0x009d), top: B:70:0x0033 }] */
    /* JADX WARN: Code duplicated, block: B:64:0x0174 A[Catch: Exception -> 0x0082, LOOP:0: B:62:0x016e->B:64:0x0174, LOOP_END, TryCatch #0 {Exception -> 0x0082, blocks: (B:14:0x004b, B:57:0x015c, B:59:0x0164, B:61:0x0167, B:62:0x016e, B:64:0x0174, B:65:0x017f, B:19:0x0068, B:46:0x010e, B:51:0x0138, B:49:0x012e, B:53:0x013b, B:22:0x007e, B:42:0x00bc, B:38:0x009d), top: B:70:0x0033 }] */
    /* JADX WARN: Code duplicated, block: B:7:0x001e  */
    public final Object triggerInference(Continuation<? super Unit> continuation) throws Throwable {
        C41641 c41641;
        Sprite sprite;
        Script script;
        BrickAdapter brickAdapter;
        KoveAutocompleteController koveAutocompleteController;
        BrickAdapter brickAdapter2;
        Script script2;
        KoveAutocompleteController koveAutocompleteController2;
        String strCleanResponse;
        KoveAutocompleteController koveAutocompleteController3;
        Sprite sprite2;
        List list;
        Iterator it;
        if (continuation instanceof C41641) {
            c41641 = (C41641) continuation;
            if ((c41641.label & Integer.MIN_VALUE) != 0) {
                c41641.label -= Integer.MIN_VALUE;
            } else {
                c41641 = new C41641(continuation);
            }
        } else {
            c41641 = new C41641(continuation);
        }
        Object objWithContext = c41641.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c41641.label;
        try {
            if (i != 0) {
                if (i == 1) {
                    brickAdapter = (BrickAdapter) c41641.L$3;
                    script = (Script) c41641.L$2;
                    sprite = (Sprite) c41641.L$1;
                    koveAutocompleteController = (KoveAutocompleteController) c41641.L$0;
                    ResultKt.throwOnFailure(objWithContext);
                } else {
                    if (i == 2) {
                        brickAdapter2 = (BrickAdapter) c41641.L$3;
                        script2 = (Script) c41641.L$2;
                        Sprite sprite3 = (Sprite) c41641.L$1;
                        koveAutocompleteController2 = (KoveAutocompleteController) c41641.L$0;
                        ResultKt.throwOnFailure(objWithContext);
                        sprite = sprite3;
                        strCleanResponse = koveAutocompleteController2.cleanResponse((String) objWithContext);
                        Log.d("KOVE_DEBUG", "==================== KOVE RAW OUTPUT ====================\n" + strCleanResponse);
                        if (strCleanResponse.length() != 0 && !StringsKt.startsWith$default(strCleanResponse, MediaError.ERROR_TYPE_ERROR, false, 2, (Object) null)) {
                            CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
                            KoveAutocompleteController$triggerInference$newBricks$1 koveAutocompleteController$triggerInference$newBricks$1 = new KoveAutocompleteController$triggerInference$newBricks$1(strCleanResponse, sprite, null);
                            c41641.L$0 = koveAutocompleteController2;
                            c41641.L$1 = sprite;
                            c41641.L$2 = script2;
                            c41641.L$3 = brickAdapter2;
                            c41641.label = 3;
                            objWithContext = BuildersKt.withContext(coroutineDispatcher, koveAutocompleteController$triggerInference$newBricks$1, c41641);
                            if (objWithContext == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                            koveAutocompleteController3 = koveAutocompleteController2;
                            sprite2 = sprite;
                        }
                        return Unit.INSTANCE;
                    }
                    if (i != 3) {
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    brickAdapter2 = (BrickAdapter) c41641.L$3;
                    script2 = (Script) c41641.L$2;
                    sprite2 = (Sprite) c41641.L$1;
                    koveAutocompleteController3 = (KoveAutocompleteController) c41641.L$0;
                    ResultKt.throwOnFailure(objWithContext);
                }
                list = (List) objWithContext;
                if (list.isEmpty()) {
                    return Unit.INSTANCE;
                }
                it = list.iterator();
                while (it.hasNext()) {
                    ((Brick) it.next()).setPhantom(true);
                }
                int i2 = koveAutocompleteController3.selectedIndex + 1;
                script2.getBrickList().addAll(i2, list);
                koveAutocompleteController3.currentPhantomBricks.addAll(list);
                brickAdapter2.updateItems(sprite2);
                Log.i("KOVE_AUTOCOMPLETE", "Inserted " + list.size() + " phantom bricks at index " + i2);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(objWithContext);
            sprite = this.activeSprite;
            if (sprite != null && (script = this.activeScript) != null && (brickAdapter = this.adapter) != null) {
                CoroutineDispatcher coroutineDispatcher2 = Dispatchers.getDefault();
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(sprite, script, this, null);
                c41641.L$0 = this;
                c41641.L$1 = sprite;
                c41641.L$2 = script;
                c41641.L$3 = brickAdapter;
                c41641.label = 1;
                objWithContext = BuildersKt.withContext(coroutineDispatcher2, anonymousClass2, c41641);
                if (objWithContext == coroutine_suspended) {
                    return coroutine_suspended;
                }
                koveAutocompleteController = this;
            }
            return Unit.INSTANCE;
            Pair pair = (Pair) objWithContext;
            String str = (String) pair.component1();
            String str2 = (String) pair.component2();
            Log.d("KOVE_DEBUG", "==================== KOVE INPUT PREFIX ====================\n" + str);
            Log.d("KOVE_DEBUG", "==================== KOVE INPUT SUFFIX ====================\n" + str2);
            CoroutineDispatcher coroutineDispatcher3 = Dispatchers.getDefault();
            KoveAutocompleteController$triggerInference$resultRaw$1 koveAutocompleteController$triggerInference$resultRaw$1 = new KoveAutocompleteController$triggerInference$resultRaw$1(str, str2, null);
            c41641.L$0 = koveAutocompleteController;
            c41641.L$1 = sprite;
            c41641.L$2 = script;
            c41641.L$3 = brickAdapter;
            c41641.label = 2;
            objWithContext = BuildersKt.withContext(coroutineDispatcher3, koveAutocompleteController$triggerInference$resultRaw$1, c41641);
            if (objWithContext == coroutine_suspended) {
                return coroutine_suspended;
            }
            brickAdapter2 = brickAdapter;
            script2 = script;
            koveAutocompleteController2 = koveAutocompleteController;
            strCleanResponse = koveAutocompleteController2.cleanResponse((String) objWithContext);
            Log.d("KOVE_DEBUG", "==================== KOVE RAW OUTPUT ====================\n" + strCleanResponse);
            if (strCleanResponse.length() != 0) {
                CoroutineDispatcher coroutineDispatcher4 = Dispatchers.getDefault();
                KoveAutocompleteController$triggerInference$newBricks$1 koveAutocompleteController$triggerInference$newBricks$2 = new KoveAutocompleteController$triggerInference$newBricks$1(strCleanResponse, sprite, null);
                c41641.L$0 = koveAutocompleteController2;
                c41641.L$1 = sprite;
                c41641.L$2 = script2;
                c41641.L$3 = brickAdapter2;
                c41641.label = 3;
                objWithContext = BuildersKt.withContext(coroutineDispatcher4, koveAutocompleteController$triggerInference$newBricks$2, c41641);
                if (objWithContext == coroutine_suspended) {
                    return coroutine_suspended;
                }
                koveAutocompleteController3 = koveAutocompleteController2;
                sprite2 = sprite;
                list = (List) objWithContext;
                if (list.isEmpty()) {
                    return Unit.INSTANCE;
                }
                it = list.iterator();
                while (it.hasNext()) {
                    ((Brick) it.next()).setPhantom(true);
                }
                int i3 = koveAutocompleteController3.selectedIndex + 1;
                script2.getBrickList().addAll(i3, list);
                koveAutocompleteController3.currentPhantomBricks.addAll(list);
                brickAdapter2.updateItems(sprite2);
                Log.i("KOVE_AUTOCOMPLETE", "Inserted " + list.size() + " phantom bricks at index " + i3);
                return Unit.INSTANCE;
            }
            return Unit.INSTANCE;
        } catch (Exception e) {
            Log.e("KOVE_AUTOCOMPLETE", "Error in autocomplete pipeline", e);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ai/KoveAutocompleteController$triggerInference$2.class
      classes9.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ai.KoveAutocompleteController$triggerInference$2, reason: invalid class name */
    /* JADX INFO: compiled from: KoveAutocompleteController.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lkotlin/Pair;", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ai.KoveAutocompleteController$triggerInference$2", f = "KoveAutocompleteController.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Pair<? extends String, ? extends String>>, Object> {
        final /* synthetic */ Script $script;
        final /* synthetic */ Sprite $sprite;
        int label;
        final /* synthetic */ KoveAutocompleteController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Sprite sprite, Script script, KoveAutocompleteController koveAutocompleteController, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$sprite = sprite;
            this.$script = script;
            this.this$0 = koveAutocompleteController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(this.$sprite, this.$script, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super Pair<? extends String, ? extends String>> continuation) {
            return invoke2(coroutineScope, (Continuation<? super Pair<String, String>>) continuation);
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(CoroutineScope coroutineScope, Continuation<? super Pair<String, String>> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                return KoveContextGenerator.INSTANCE.generateContext(this.$sprite, this.$script, this.this$0.selectedIndex);
            }
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
    }

    public final void acceptSuggestions() {
        Sprite sprite = this.activeSprite;
        if (sprite == null || this.currentPhantomBricks.isEmpty()) {
            return;
        }
        Iterator<T> it = this.currentPhantomBricks.iterator();
        while (it.hasNext()) {
            ((Brick) it.next()).setPhantom(false);
        }
        this.currentPhantomBricks.clear();
        BrickAdapter brickAdapter = this.adapter;
        if (brickAdapter != null) {
            brickAdapter.updateItems(sprite);
        }
        cancelActiveJob();
        Log.i("KOVE_AUTOCOMPLETE", "Suggestions accepted and materialized.");
    }

    public final void dismissSuggestions() {
        Script script;
        Sprite sprite = this.activeSprite;
        if (sprite == null || (script = this.activeScript) == null || this.currentPhantomBricks.isEmpty()) {
            return;
        }
        script.getBrickList().removeAll(this.currentPhantomBricks);
        this.currentPhantomBricks.clear();
        BrickAdapter brickAdapter = this.adapter;
        if (brickAdapter != null) {
            brickAdapter.updateItems(sprite);
        }
        Log.i("KOVE_AUTOCOMPLETE", "Suggestions dismissed and removed.");
    }

    public final void cancelActiveJob() {
        Job job = this.autocompleteJob;
        if (job != null) {
            Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
        }
        this.autocompleteJob = null;
    }

    private final String cleanResponse(String raw) {
        for (String str : CollectionsKt.listOf((Object[]) new String[]{"<|fim_prefix|>", "<|fim_suffix|>", "<|fim_middle|>", "<|endoftext|>", "<|file_sep|>"})) {
            if (StringsKt.contains$default((CharSequence) raw, (CharSequence) str, false, 2, (Object) null)) {
                raw = StringsKt.substringBefore$default(raw, str, (String) null, 2, (Object) null);
            }
        }
        return StringsKt.trim((CharSequence) raw).toString();
    }
}
