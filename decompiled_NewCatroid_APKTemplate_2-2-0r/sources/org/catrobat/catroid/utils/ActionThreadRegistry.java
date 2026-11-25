package org.catrobat.catroid.utils;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/ActionThreadRegistry.class
  classes14.dex
 */
/* JADX INFO: compiled from: ActionThreadRegistry.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0005H\u0007J\u0010\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007J\u0018\u0010\u0010\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0007H\u0007J\u0010\u0010\u0012\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u0005H\u0007J\u0018\u0010\u0013\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0007H\u0007R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/utils/ActionThreadRegistry;", "", "()V", "activeThreads", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Ljava/util/ArrayList;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "Lkotlin/collections/ArrayList;", LineReader.CLEAR, "", "isThreadRunning", "", "threadId", "normalizeId", "id", "register", "action", "stopThread", "unregister", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ActionThreadRegistry {
    public static final ActionThreadRegistry INSTANCE = new ActionThreadRegistry();
    private static final ConcurrentHashMap<String, ArrayList<Action>> activeThreads = new ConcurrentHashMap<>();

    private ActionThreadRegistry() {
    }

    @JvmStatic
    public static final String normalizeId(String id2) {
        Intrinsics.checkNotNullParameter(id2, "id");
        return StringsKt.replace$default(StringsKt.replace$default(StringsKt.trim((CharSequence) id2).toString(), "\"", "", false, 4, (Object) null), FormatHelper.QUOTE, "", false, 4, (Object) null);
    }

    @JvmStatic
    public static final void register(String threadId, Action action) {
        ArrayList<Action> arrayListPutIfAbsent;
        Intrinsics.checkNotNullParameter(threadId, "threadId");
        Intrinsics.checkNotNullParameter(action, "action");
        String strNormalizeId = normalizeId(threadId);
        ConcurrentHashMap<String, ArrayList<Action>> concurrentHashMap = activeThreads;
        ArrayList<Action> arrayList = concurrentHashMap.get(strNormalizeId);
        if (arrayList == null && (arrayListPutIfAbsent = concurrentHashMap.putIfAbsent(strNormalizeId, (arrayList = new ArrayList<>()))) != null) {
            arrayList = arrayListPutIfAbsent;
        }
        arrayList.add(action);
    }

    @JvmStatic
    public static final void unregister(String threadId, Action action) {
        Intrinsics.checkNotNullParameter(threadId, "threadId");
        Intrinsics.checkNotNullParameter(action, "action");
        String strNormalizeId = normalizeId(threadId);
        ConcurrentHashMap<String, ArrayList<Action>> concurrentHashMap = activeThreads;
        ArrayList<Action> arrayList = concurrentHashMap.get(strNormalizeId);
        if (arrayList == null) {
            return;
        }
        arrayList.remove(action);
        if (arrayList.isEmpty()) {
            concurrentHashMap.remove(strNormalizeId);
        }
    }

    @JvmStatic
    public static final boolean isThreadRunning(String threadId) {
        Intrinsics.checkNotNullParameter(threadId, "threadId");
        ArrayList<Action> arrayList = activeThreads.get(normalizeId(threadId));
        return (arrayList == null || arrayList.isEmpty()) ? false : true;
    }

    @JvmStatic
    public static final void stopThread(String threadId) {
        Intrinsics.checkNotNullParameter(threadId, "threadId");
        String strNormalizeId = normalizeId(threadId);
        ArrayList<Action> arrayList = activeThreads.get(strNormalizeId);
        if (arrayList == null) {
            return;
        }
        for (Action action : arrayList) {
            Actor actor = action.getActor();
            if (actor != null) {
                actor.removeAction(action);
            }
        }
        activeThreads.remove(strNormalizeId);
    }

    @JvmStatic
    public static final void clear() {
        activeThreads.clear();
    }
}
