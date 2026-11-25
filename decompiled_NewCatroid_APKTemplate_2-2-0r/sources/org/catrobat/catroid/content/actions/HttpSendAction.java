package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import org.catrobat.catroid.common.NewCatroidHttpManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/HttpSendAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: HttpSendAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0016H\u0016J\b\u0010\u0018\u001a\u00020\u0016H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/content/actions/HttpSendAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "finished", "", "requestId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getRequestId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setRequestId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "act", "delta", "", "reset", "", "restart", "runAsyncSend", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HttpSendAction extends Action {
    private volatile boolean finished;
    private Formula requestId;
    private Scope scope;
    private boolean started;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getRequestId() {
        return this.requestId;
    }

    public final void setRequestId(Formula formula) {
        this.requestId = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.started) {
            this.started = true;
            runAsyncSend();
        }
        return this.finished;
    }

    private final void runAsyncSend() {
        ThreadsKt.thread$default(true, false, null, null, 0, new Function0<Unit>() { // from class: org.catrobat.catroid.content.actions.HttpSendAction.runAsyncSend.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                try {
                    Formula requestId = HttpSendAction.this.getRequestId();
                    String strInterpretString = requestId != null ? requestId.interpretString(HttpSendAction.this.getScope()) : null;
                    if (strInterpretString == null) {
                        strInterpretString = "";
                    }
                    if (strInterpretString.length() <= 0) {
                        HttpSendAction.this.finished = true;
                        return;
                    }
                    NewCatroidHttpManager newCatroidHttpManager = NewCatroidHttpManager.INSTANCE;
                    final HttpSendAction httpSendAction = HttpSendAction.this;
                    newCatroidHttpManager.executeRequest(strInterpretString, new Function0<Unit>() { // from class: org.catrobat.catroid.content.actions.HttpSendAction.runAsyncSend.1.1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                            httpSendAction.finished = true;
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    HttpSendAction.this.finished = true;
                }
            }
        }, 30, null);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.started = false;
        this.finished = false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.started = false;
        this.finished = false;
        this.scope = null;
        this.requestId = null;
    }
}
