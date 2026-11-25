package org.catrobat.catroid.content.actions;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import com.android.dex.DexFormat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.ide.ScriptExecutor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LoadAndRunDexAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: LoadAndRunDexAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u001fH\u0016J\b\u0010!\u001a\u00020\u001fH\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0006\"\u0004\b\u0010\u0010\bR\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0006\"\u0004\b\u0013\u0010\bR\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/catrobat/catroid/content/actions/LoadAndRunDexAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "className", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getClassName", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setClassName", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "dexPath", "getDexPath", "setDexPath", "finished", "", "methodArg", "getMethodArg", "setMethodArg", "methodName", "getMethodName", "setMethodName", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "act", "delta", "", "reset", "", "restart", "runAsyncLoad", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LoadAndRunDexAction extends Action {
    private Formula className;
    private Formula dexPath;
    private volatile boolean finished;
    private Formula methodArg;
    private Formula methodName;
    private Scope scope;
    private boolean started;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getDexPath() {
        return this.dexPath;
    }

    public final void setDexPath(Formula formula) {
        this.dexPath = formula;
    }

    public final Formula getClassName() {
        return this.className;
    }

    public final void setClassName(Formula formula) {
        this.className = formula;
    }

    public final Formula getMethodName() {
        return this.methodName;
    }

    public final void setMethodName(Formula formula) {
        this.methodName = formula;
    }

    public final Formula getMethodArg() {
        return this.methodArg;
    }

    public final void setMethodArg(Formula formula) {
        this.methodArg = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.started) {
            this.started = true;
            runAsyncLoad();
        }
        return this.finished;
    }

    private final void runAsyncLoad() {
        ThreadsKt.thread$default(true, false, null, null, 0, new Function0<Unit>() { // from class: org.catrobat.catroid.content.actions.LoadAndRunDexAction.runAsyncLoad.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() throws Throwable {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX WARN: Code duplicated, block: B:112:0x00f1 A[SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:114:0x00dc A[SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:117:0x014d A[SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:119:0x0136 A[SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:11:0x001e  */
            /* JADX WARN: Code duplicated, block: B:123:0x020b A[SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:124:0x02dd A[SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:126:? A[LOOP:4: B:86:0x02a1->B:126:?, LOOP_END, SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:127:0x02d8 A[SYNTHETIC] */
            /* JADX WARN: Code duplicated, block: B:15:0x0027 A[Catch: all -> 0x02e1, Exception -> 0x02e4, TRY_ENTER, TRY_LEAVE, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:17:0x002f A[Catch: all -> 0x001b, Exception -> 0x02e4, TRY_ENTER, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:18:0x003a  */
            /* JADX WARN: Code duplicated, block: B:20:0x003d  */
            /* JADX WARN: Code duplicated, block: B:23:0x0047 A[Catch: all -> 0x001b, Exception -> 0x02e4, TRY_ENTER, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:24:0x0052  */
            /* JADX WARN: Code duplicated, block: B:26:0x0055  */
            /* JADX WARN: Code duplicated, block: B:30:0x0060 A[Catch: all -> 0x001b, Exception -> 0x02e4, TRY_ENTER, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:31:0x006b  */
            /* JADX WARN: Code duplicated, block: B:33:0x006e  */
            /* JADX WARN: Code duplicated, block: B:37:0x0079 A[Catch: all -> 0x001b, Exception -> 0x02e4, TRY_ENTER, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:38:0x0084  */
            /* JADX WARN: Code duplicated, block: B:40:0x0087  */
            /* JADX WARN: Code duplicated, block: B:45:0x00b9 A[Catch: all -> 0x001b, Exception -> 0x02e4, LOOP:0: B:43:0x00b3->B:45:0x00b9, LOOP_END, TRY_ENTER, TRY_LEAVE, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:50:0x00e2 A[Catch: all -> 0x001b, Exception -> 0x02e4, TRY_ENTER, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:56:0x012f A[Catch: all -> 0x001b, Exception -> 0x02e4, TRY_ENTER, TRY_LEAVE, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:60:0x013c A[Catch: all -> 0x02e1, Exception -> 0x02e4, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:65:0x015b A[Catch: all -> 0x02e1, Exception -> 0x02e4, TRY_ENTER, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:67:0x0177 A[Catch: all -> 0x02e1, Exception -> 0x02e4, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:68:0x0182 A[Catch: all -> 0x02e1, Exception -> 0x02e4, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:72:0x01a1 A[Catch: all -> 0x02e1, Exception -> 0x02e4, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:73:0x01a9 A[Catch: all -> 0x02e1, Exception -> 0x02e4, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:75:0x01f9 A[Catch: all -> 0x02e1, Exception -> 0x02e4, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:77:0x0208 A[Catch: all -> 0x02e1, Exception -> 0x02e4, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:81:0x0219 A[Catch: all -> 0x02e1, Exception -> 0x02e4, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:83:0x021f A[Catch: all -> 0x02e1, Exception -> 0x02e4, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:84:0x0222  */
            /* JADX WARN: Code duplicated, block: B:88:0x02a7 A[Catch: all -> 0x02e1, Exception -> 0x02e4, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Code duplicated, block: B:91:0x02c6 A[Catch: all -> 0x02e1, Exception -> 0x02e4, TryCatch #2 {Exception -> 0x02e4, blocks: (B:3:0x0003, B:5:0x000f, B:7:0x0015, B:15:0x0027, B:17:0x002f, B:21:0x003f, B:23:0x0047, B:28:0x0058, B:30:0x0060, B:35:0x0071, B:37:0x0079, B:42:0x008a, B:43:0x00b3, B:45:0x00b9, B:47:0x00cd, B:48:0x00dc, B:50:0x00e2, B:52:0x00f1, B:54:0x00f5, B:56:0x012f, B:57:0x0132, B:58:0x0136, B:60:0x013c, B:62:0x014d, B:65:0x015b, B:67:0x0177, B:68:0x0182, B:70:0x0197, B:72:0x01a1, B:73:0x01a9, B:75:0x01f9, B:77:0x0208, B:78:0x020b, B:79:0x020e, B:81:0x0219, B:83:0x021f, B:85:0x0223, B:86:0x02a1, B:88:0x02a7, B:89:0x02c0, B:91:0x02c6, B:95:0x02d9, B:97:0x02dd), top: B:107:0x0003, outer: #0 }] */
            /* JADX WARN: Instruction removed from duplicated block: B:73:0x01a9, please report this as an issue */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() throws Throwable {
                File filesDir;
                Formula dexPath;
                String strInterpretString;
                Formula className;
                String strInterpretString2;
                String str;
                Formula methodName;
                String strInterpretString3;
                String str2;
                Formula methodArg;
                String strInterpretString4;
                final String str3;
                ArrayList arrayList;
                Iterator it;
                ArrayList arrayList2;
                ArrayList arrayList3;
                File file;
                File codeCacheDir;
                File file2;
                Iterator it2;
                File file3;
                File file4;
                final Object objNewInstance;
                ArrayList arrayList4;
                int i;
                ArrayList arrayList5;
                Object obj;
                final Activity activity;
                Class[] clsArr;
                Function1 function1;
                Iterator it3;
                Object next;
                Method method;
                File file5;
                File file6;
                File file7;
                File fileProcessLibrary;
                boolean z = true;
                try {
                    try {
                        final Context appContext = CatroidApplication.getAppContext();
                        Scope scope = LoadAndRunDexAction.this.getScope();
                        if (scope != null) {
                            try {
                                Project project = scope.getProject();
                                if (project != null) {
                                    filesDir = project.getFilesDir();
                                } else {
                                    filesDir = null;
                                }
                                if (filesDir != null) {
                                    dexPath = LoadAndRunDexAction.this.getDexPath();
                                    if (dexPath != null) {
                                        strInterpretString = dexPath.interpretString(LoadAndRunDexAction.this.getScope());
                                    } else {
                                        strInterpretString = null;
                                    }
                                    if (strInterpretString == null) {
                                        strInterpretString = DexFormat.DEX_IN_JAR_NAME;
                                    }
                                    className = LoadAndRunDexAction.this.getClassName();
                                    if (className != null) {
                                        strInterpretString2 = className.interpretString(LoadAndRunDexAction.this.getScope());
                                    } else {
                                        strInterpretString2 = null;
                                    }
                                    if (strInterpretString2 == null) {
                                        strInterpretString2 = "game.Main";
                                    }
                                    str = strInterpretString2;
                                    methodName = LoadAndRunDexAction.this.getMethodName();
                                    if (methodName != null) {
                                        strInterpretString3 = methodName.interpretString(LoadAndRunDexAction.this.getScope());
                                    } else {
                                        strInterpretString3 = null;
                                    }
                                    if (strInterpretString3 == null) {
                                        strInterpretString3 = "onStart";
                                    }
                                    str2 = strInterpretString3;
                                    methodArg = LoadAndRunDexAction.this.getMethodArg();
                                    if (methodArg != null) {
                                        strInterpretString4 = methodArg.interpretString(LoadAndRunDexAction.this.getScope());
                                    } else {
                                        strInterpretString4 = null;
                                    }
                                    if (strInterpretString4 == null) {
                                        strInterpretString4 = "";
                                    }
                                    str3 = strInterpretString4;
                                    List listSplit$default = StringsKt.split$default((CharSequence) strInterpretString, new String[]{","}, false, 0, 6, (Object) null);
                                    arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
                                    it = listSplit$default.iterator();
                                    while (it.hasNext()) {
                                        arrayList.add(StringsKt.trim((CharSequence) it.next()).toString());
                                    }
                                    arrayList2 = new ArrayList();
                                    for (Object obj2 : arrayList) {
                                        if (((String) obj2).length() > 0) {
                                            arrayList2.add(obj2);
                                        }
                                    }
                                    ArrayList arrayList6 = arrayList2;
                                    arrayList3 = new ArrayList();
                                    file = new File(filesDir, "libs_processed");
                                    codeCacheDir = appContext.getCodeCacheDir();
                                    file2 = new File(codeCacheDir, "native_libs_" + System.currentTimeMillis());
                                    file2.mkdirs();
                                    if (!file.exists()) {
                                        file.mkdirs();
                                    }
                                    it2 = arrayList6.iterator();
                                    while (it2.hasNext()) {
                                        file5 = new File(filesDir, (String) it2.next());
                                        if (!file5.exists()) {
                                            if (Intrinsics.areEqual(FilesKt.getExtension(file5), "jar")) {
                                                ScriptExecutor scriptExecutor = ScriptExecutor.INSTANCE;
                                                Intrinsics.checkNotNull(appContext);
                                                file6 = file2;
                                                file7 = codeCacheDir;
                                                fileProcessLibrary = scriptExecutor.processLibrary(appContext, file5, file, file6, 34);
                                                if (fileProcessLibrary.exists()) {
                                                    String absolutePath = fileProcessLibrary.getAbsolutePath();
                                                    Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                                                    arrayList3.add(absolutePath);
                                                }
                                            } else {
                                                file6 = file2;
                                                file7 = codeCacheDir;
                                                String absolutePath2 = file5.getAbsolutePath();
                                                Intrinsics.checkNotNullExpressionValue(absolutePath2, "getAbsolutePath(...)");
                                                arrayList3.add(absolutePath2);
                                            }
                                            file2 = file6;
                                            codeCacheDir = file7;
                                        }
                                    }
                                    file3 = file2;
                                    file4 = codeCacheDir;
                                    if (arrayList3.isEmpty()) {
                                        z = true;
                                        LoadAndRunDexAction.this.finished = true;
                                    } else {
                                        String pathSeparator = File.pathSeparator;
                                        Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
                                        Class clsLoadClass = new DexClassLoader(CollectionsKt.joinToString$default(arrayList3, pathSeparator, null, null, 0, null, null, 62, null), file4.getAbsolutePath(), file3.getAbsolutePath(), appContext.getClassLoader()).loadClass(str);
                                        objNewInstance = clsLoadClass.newInstance();
                                        Method[] methods = clsLoadClass.getMethods();
                                        Intrinsics.checkNotNullExpressionValue(methods, "getMethods(...)");
                                        arrayList4 = new ArrayList();
                                        for (Method method2 : methods) {
                                            if (Intrinsics.areEqual(method2.getName(), str2)) {
                                                arrayList4.add(method2);
                                            }
                                        }
                                        arrayList5 = arrayList4;
                                        if (!arrayList5.isEmpty()) {
                                            obj = Gdx.f82app;
                                            if (obj instanceof Activity) {
                                                activity = (Activity) obj;
                                            } else {
                                                activity = null;
                                            }
                                            for (Pair pair : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(new Class[]{Activity.class, ViewGroup.class}, new LoadAndRunDexAction$runAsyncLoad$1$signatures$1(activity, objNewInstance)), TuplesKt.to(new Class[]{Context.class, ViewGroup.class}, new LoadAndRunDexAction$runAsyncLoad$1$signatures$2(activity, objNewInstance, appContext)), TuplesKt.to(new Class[]{Activity.class}, new Function1<Method, Object>() { // from class: org.catrobat.catroid.content.actions.LoadAndRunDexAction$runAsyncLoad$1$signatures$3
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Method method3) {
                                                    Intrinsics.checkNotNullParameter(method3, "method");
                                                    try {
                                                        return method3.invoke(objNewInstance, activity);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                        return Unit.INSTANCE;
                                                    }
                                                }
                                            }), TuplesKt.to(new Class[]{Context.class}, new Function1<Method, Object>() { // from class: org.catrobat.catroid.content.actions.LoadAndRunDexAction$runAsyncLoad$1$signatures$4
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Method method3) {
                                                    Intrinsics.checkNotNullParameter(method3, "method");
                                                    try {
                                                        return method3.invoke(objNewInstance, appContext);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                        return Unit.INSTANCE;
                                                    }
                                                }
                                            }), TuplesKt.to(new Class[]{String.class}, new Function1<Method, Object>() { // from class: org.catrobat.catroid.content.actions.LoadAndRunDexAction$runAsyncLoad$1$signatures$5
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Method method3) {
                                                    Intrinsics.checkNotNullParameter(method3, "method");
                                                    try {
                                                        return method3.invoke(objNewInstance, str3);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                        return Unit.INSTANCE;
                                                    }
                                                }
                                            }), TuplesKt.to(new Class[0], new Function1<Method, Object>() { // from class: org.catrobat.catroid.content.actions.LoadAndRunDexAction$runAsyncLoad$1$signatures$6
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(1);
                                                }

                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Method method3) {
                                                    Intrinsics.checkNotNullParameter(method3, "method");
                                                    try {
                                                        return method3.invoke(objNewInstance, new Object[0]);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                        return Unit.INSTANCE;
                                                    }
                                                }
                                            })})) {
                                                clsArr = (Class[]) pair.component1();
                                                function1 = (Function1) pair.component2();
                                                it3 = arrayList5.iterator();
                                                do {
                                                    if (it3.hasNext()) {
                                                        next = null;
                                                        break;
                                                    }
                                                    next = it3.next();
                                                } while (!Arrays.equals(((Method) next).getParameterTypes(), clsArr));
                                                method = (Method) next;
                                                if (method != null) {
                                                    function1.invoke(method);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                LoadAndRunDexAction.this.finished = z;
                                return;
                            } catch (Throwable th) {
                                th = th;
                                LoadAndRunDexAction.this.finished = z;
                                throw th;
                            }
                        }
                        filesDir = null;
                        if (filesDir != null) {
                            dexPath = LoadAndRunDexAction.this.getDexPath();
                            if (dexPath != null) {
                                strInterpretString = dexPath.interpretString(LoadAndRunDexAction.this.getScope());
                            } else {
                                strInterpretString = null;
                            }
                            if (strInterpretString == null) {
                                strInterpretString = DexFormat.DEX_IN_JAR_NAME;
                            }
                            className = LoadAndRunDexAction.this.getClassName();
                            if (className != null) {
                                strInterpretString2 = className.interpretString(LoadAndRunDexAction.this.getScope());
                            } else {
                                strInterpretString2 = null;
                            }
                            if (strInterpretString2 == null) {
                                strInterpretString2 = "game.Main";
                            }
                            str = strInterpretString2;
                            methodName = LoadAndRunDexAction.this.getMethodName();
                            if (methodName != null) {
                                strInterpretString3 = methodName.interpretString(LoadAndRunDexAction.this.getScope());
                            } else {
                                strInterpretString3 = null;
                            }
                            if (strInterpretString3 == null) {
                                strInterpretString3 = "onStart";
                            }
                            str2 = strInterpretString3;
                            methodArg = LoadAndRunDexAction.this.getMethodArg();
                            if (methodArg != null) {
                                strInterpretString4 = methodArg.interpretString(LoadAndRunDexAction.this.getScope());
                            } else {
                                strInterpretString4 = null;
                            }
                            if (strInterpretString4 == null) {
                                strInterpretString4 = "";
                            }
                            str3 = strInterpretString4;
                            List listSplit$default2 = StringsKt.split$default((CharSequence) strInterpretString, new String[]{","}, false, 0, 6, (Object) null);
                            arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default2, 10));
                            it = listSplit$default2.iterator();
                            while (it.hasNext()) {
                                arrayList.add(StringsKt.trim((CharSequence) it.next()).toString());
                            }
                            arrayList2 = new ArrayList();
                            while (r4.hasNext()) {
                                if (((String) obj2).length() > 0) {
                                    arrayList2.add(obj2);
                                }
                            }
                            ArrayList arrayList7 = arrayList2;
                            arrayList3 = new ArrayList();
                            file = new File(filesDir, "libs_processed");
                            codeCacheDir = appContext.getCodeCacheDir();
                            file2 = new File(codeCacheDir, "native_libs_" + System.currentTimeMillis());
                            file2.mkdirs();
                            if (!file.exists()) {
                                file.mkdirs();
                            }
                            it2 = arrayList7.iterator();
                            while (it2.hasNext()) {
                                file5 = new File(filesDir, (String) it2.next());
                                if (!file5.exists()) {
                                    if (Intrinsics.areEqual(FilesKt.getExtension(file5), "jar")) {
                                        ScriptExecutor scriptExecutor2 = ScriptExecutor.INSTANCE;
                                        Intrinsics.checkNotNull(appContext);
                                        file6 = file2;
                                        file7 = codeCacheDir;
                                        fileProcessLibrary = scriptExecutor2.processLibrary(appContext, file5, file, file6, 34);
                                        if (fileProcessLibrary.exists()) {
                                            String absolutePath3 = fileProcessLibrary.getAbsolutePath();
                                            Intrinsics.checkNotNullExpressionValue(absolutePath3, "getAbsolutePath(...)");
                                            arrayList3.add(absolutePath3);
                                        }
                                    } else {
                                        file6 = file2;
                                        file7 = codeCacheDir;
                                        String absolutePath4 = file5.getAbsolutePath();
                                        Intrinsics.checkNotNullExpressionValue(absolutePath4, "getAbsolutePath(...)");
                                        arrayList3.add(absolutePath4);
                                    }
                                    file2 = file6;
                                    codeCacheDir = file7;
                                }
                            }
                            file3 = file2;
                            file4 = codeCacheDir;
                            if (arrayList3.isEmpty()) {
                                z = true;
                                LoadAndRunDexAction.this.finished = true;
                            } else {
                                String pathSeparator2 = File.pathSeparator;
                                Intrinsics.checkNotNullExpressionValue(pathSeparator2, "pathSeparator");
                                Class clsLoadClass2 = new DexClassLoader(CollectionsKt.joinToString$default(arrayList3, pathSeparator2, null, null, 0, null, null, 62, null), file4.getAbsolutePath(), file3.getAbsolutePath(), appContext.getClassLoader()).loadClass(str);
                                objNewInstance = clsLoadClass2.newInstance();
                                Method[] methods2 = clsLoadClass2.getMethods();
                                Intrinsics.checkNotNullExpressionValue(methods2, "getMethods(...)");
                                arrayList4 = new ArrayList();
                                while (i < r5) {
                                    if (Intrinsics.areEqual(method2.getName(), str2)) {
                                        arrayList4.add(method2);
                                    }
                                }
                                arrayList5 = arrayList4;
                                if (!arrayList5.isEmpty()) {
                                    obj = Gdx.f82app;
                                    if (obj instanceof Activity) {
                                        activity = (Activity) obj;
                                    } else {
                                        activity = null;
                                    }
                                    while (r0.hasNext()) {
                                        clsArr = (Class[]) pair.component1();
                                        function1 = (Function1) pair.component2();
                                        it3 = arrayList5.iterator();
                                        do {
                                            if (it3.hasNext()) {
                                                next = null;
                                                break;
                                            }
                                            next = it3.next();
                                        } while (!Arrays.equals(((Method) next).getParameterTypes(), clsArr));
                                        method = (Method) next;
                                        if (method != null) {
                                            function1.invoke(method);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        LoadAndRunDexAction.this.finished = z;
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                        z = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LoadAndRunDexAction.this.finished = true;
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
        this.dexPath = null;
        this.className = null;
        this.methodName = null;
        this.methodArg = null;
    }
}
