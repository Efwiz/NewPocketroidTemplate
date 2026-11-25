package org.catrobat.catroid.content.actions;

import android.R;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LoadAndRunDexAction$runAsyncLoad$1$signatures$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: LoadAndRunDexAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "method", "Ljava/lang/reflect/Method;", "invoke", "(Ljava/lang/reflect/Method;)Ljava/lang/Boolean;"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class LoadAndRunDexAction$runAsyncLoad$1$signatures$1 extends Lambda implements Function1<Method, Boolean> {
    final /* synthetic */ Activity $activity;
    final /* synthetic */ Object $instance;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    LoadAndRunDexAction$runAsyncLoad$1$signatures$1(Activity activity, Object obj) {
        super(1);
        this.$activity = activity;
        this.$instance = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(final Method method) {
        Intrinsics.checkNotNullParameter(method, "method");
        Activity activity = this.$activity;
        final ViewGroup viewGroup = activity != null ? (ViewGroup) activity.findViewById(R.id.content) : null;
        Handler handler = new Handler(Looper.getMainLooper());
        final Object obj = this.$instance;
        final Activity activity2 = this.$activity;
        return Boolean.valueOf(handler.post(new Runnable() { // from class: org.catrobat.catroid.content.actions.LoadAndRunDexAction$runAsyncLoad$1$signatures$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LoadAndRunDexAction$runAsyncLoad$1$signatures$1.invoke$lambda$0(method, obj, activity2, viewGroup);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(Method method, Object obj, Activity activity, ViewGroup viewGroup) {
        Intrinsics.checkNotNullParameter(method, "$method");
        try {
            method.invoke(obj, activity, viewGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
