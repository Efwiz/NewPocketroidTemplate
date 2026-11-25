package org.catrobat.catroid.content.actions;

import android.content.Context;
import java.io.File;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.io.SoundManager;
import org.catrobat.catroid.stage.SpeechSynthesizer;
import org.catrobat.catroid.utils.MobileServiceAvailability;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SpeakAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SpeakAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u0019\u001a\u00020\u0018H\u0002J\b\u0010\u001a\u001a\u00020\u0018H\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016¨\u0006\u001b"}, d2 = {"Lorg/catrobat/catroid/content/actions/SpeakAction;", "Lorg/catrobat/catroid/content/actions/AsynchronousAction;", "()V", "context", "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "isFinished", "", "mobileServiceAvailability", "Lorg/catrobat/catroid/utils/MobileServiceAvailability;", "getMobileServiceAvailability", "()Lorg/catrobat/catroid/utils/MobileServiceAvailability;", "setMobileServiceAvailability", "(Lorg/catrobat/catroid/utils/MobileServiceAvailability;)V", "speechSynthesizer", "Lorg/catrobat/catroid/stage/SpeechSynthesizer;", "getSpeechSynthesizer", "()Lorg/catrobat/catroid/stage/SpeechSynthesizer;", "setSpeechSynthesizer", "(Lorg/catrobat/catroid/stage/SpeechSynthesizer;)V", "initialize", "", "onDone", "onError", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SpeakAction extends AsynchronousAction {
    public Context context;
    private boolean isFinished;
    public MobileServiceAvailability mobileServiceAvailability;
    public SpeechSynthesizer speechSynthesizer;

    public final MobileServiceAvailability getMobileServiceAvailability() {
        MobileServiceAvailability mobileServiceAvailability = this.mobileServiceAvailability;
        if (mobileServiceAvailability != null) {
            return mobileServiceAvailability;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mobileServiceAvailability");
        return null;
    }

    public final void setMobileServiceAvailability(MobileServiceAvailability mobileServiceAvailability) {
        Intrinsics.checkNotNullParameter(mobileServiceAvailability, "<set-?>");
        this.mobileServiceAvailability = mobileServiceAvailability;
    }

    public final Context getContext() {
        Context context = this.context;
        if (context != null) {
            return context;
        }
        Intrinsics.throwUninitializedPropertyAccessException("context");
        return null;
    }

    public final void setContext(Context context) {
        Intrinsics.checkNotNullParameter(context, "<set-?>");
        this.context = context;
    }

    public final SpeechSynthesizer getSpeechSynthesizer() {
        SpeechSynthesizer speechSynthesizer = this.speechSynthesizer;
        if (speechSynthesizer != null) {
            return speechSynthesizer;
        }
        Intrinsics.throwUninitializedPropertyAccessException("speechSynthesizer");
        return null;
    }

    public final void setSpeechSynthesizer(SpeechSynthesizer speechSynthesizer) {
        Intrinsics.checkNotNullParameter(speechSynthesizer, "<set-?>");
        this.speechSynthesizer = speechSynthesizer;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SpeakAction$initialize$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.SpeakAction$initialize$1, reason: invalid class name */
    /* JADX INFO: compiled from: SpeakAction.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function0<Unit> {
        AnonymousClass1(Object obj) {
            super(0, obj, SpeakAction.class, "onError", "onError()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((SpeakAction) this.receiver).onError();
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SpeakAction$initialize$2.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.SpeakAction$initialize$2, reason: invalid class name */
    /* JADX INFO: compiled from: SpeakAction.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function0<Unit> {
        AnonymousClass2(Object obj) {
            super(0, obj, SpeakAction.class, "onDone", "onDone()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((SpeakAction) this.receiver).onDone();
        }
    }

    @Override // org.catrobat.catroid.content.actions.AsynchronousAction
    public void initialize() {
        if (getMobileServiceAvailability().isGmsAvailable(getContext())) {
            getSpeechSynthesizer().setUtteranceProgressListener(new AnonymousClass1(this), new AnonymousClass2(this));
        } else if (!getMobileServiceAvailability().isHmsAvailable(getContext())) {
            return;
        } else {
            getSpeechSynthesizer().setHuaweiTextToSpeechListener(new AnonymousClass3(this), new AnonymousClass4(this));
        }
        getSpeechSynthesizer().synthesize(new ShowTextUtils.AndroidStringProvider(getContext()));
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SpeakAction$initialize$3.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.SpeakAction$initialize$3, reason: invalid class name */
    /* JADX INFO: compiled from: SpeakAction.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass3 extends FunctionReferenceImpl implements Function0<Unit> {
        AnonymousClass3(Object obj) {
            super(0, obj, SpeakAction.class, "onError", "onError()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((SpeakAction) this.receiver).onError();
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SpeakAction$initialize$4.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.SpeakAction$initialize$4, reason: invalid class name */
    /* JADX INFO: compiled from: SpeakAction.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass4 extends FunctionReferenceImpl implements Function0<Unit> {
        AnonymousClass4(Object obj) {
            super(0, obj, SpeakAction.class, "onDone", "onDone()V", 0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ((SpeakAction) this.receiver).onDone();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onError() {
        this.isFinished = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onDone() {
        SoundManager soundManager = SoundManager.getInstance();
        File speechFile = getSpeechSynthesizer().getSpeechFile();
        String absolutePath = speechFile != null ? speechFile.getAbsolutePath() : null;
        Scope scope = getSpeechSynthesizer().getScope();
        soundManager.playSoundFile(absolutePath, scope != null ? scope.getSprite() : null);
        this.isFinished = true;
    }

    @Override // org.catrobat.catroid.content.actions.AsynchronousAction
    /* JADX INFO: renamed from: isFinished, reason: from getter */
    public boolean getIsFinished() {
        return this.isFinished;
    }
}
