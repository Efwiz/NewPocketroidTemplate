package org.catrobat.catroid.stage;

import android.speech.tts.UtteranceProgressListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/UtteranceProgressListenerContainer.class
  classes14.dex
 */
/* JADX INFO: compiled from: UtteranceProgressListenerContainer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010!\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00012\b\u0010\f\u001a\u0004\u0018\u00010\u0005J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u0005H\u0016J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u0005H\u0016J\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u0005H\u0016R$\u0010\u0003\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/stage/UtteranceProgressListenerContainer;", "Landroid/speech/tts/UtteranceProgressListener;", "()V", "listeners", "", "", "", "addUtteranceProgressListener", "", "speechFile", "Ljava/io/File;", "utteranceProgressListener", "utteranceId", "onDone", "", "onError", "onStart", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UtteranceProgressListenerContainer extends UtteranceProgressListener {
    private final Map<String, List<UtteranceProgressListener>> listeners = new HashMap();

    @Override // android.speech.tts.UtteranceProgressListener
    public void onError(String utteranceId) {
        Intrinsics.checkNotNullParameter(utteranceId, "utteranceId");
    }

    @Override // android.speech.tts.UtteranceProgressListener
    public void onStart(String utteranceId) {
        Intrinsics.checkNotNullParameter(utteranceId, "utteranceId");
    }

    public final synchronized boolean addUtteranceProgressListener(File speechFile, UtteranceProgressListener utteranceProgressListener, String utteranceId) {
        boolean z;
        Intrinsics.checkNotNullParameter(speechFile, "speechFile");
        Intrinsics.checkNotNullParameter(utteranceProgressListener, "utteranceProgressListener");
        List<UtteranceProgressListener> list = this.listeners.get(utteranceId);
        z = false;
        if (list == null) {
            if (speechFile.exists()) {
                utteranceProgressListener.onDone(utteranceId);
            } else {
                ArrayList arrayList = new ArrayList();
                arrayList.add(utteranceProgressListener);
                this.listeners.put(utteranceId, arrayList);
                z = true;
            }
        } else {
            list.add(utteranceProgressListener);
        }
        return z;
    }

    @Override // android.speech.tts.UtteranceProgressListener
    public synchronized void onDone(String utteranceId) {
        Intrinsics.checkNotNullParameter(utteranceId, "utteranceId");
        List<UtteranceProgressListener> listEmptyList = this.listeners.get(utteranceId);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        Iterator<UtteranceProgressListener> it = listEmptyList.iterator();
        while (it.hasNext()) {
            it.next().onDone(utteranceId);
        }
        this.listeners.put(utteranceId, null);
    }
}
