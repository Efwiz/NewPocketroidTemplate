package org.catrobat.catroid.content.actions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.catrobat.catroid.stage.StageActivity;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateImageViewAction$runAsyncCreate$1$2.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateImageViewAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class CreateImageViewAction$runAsyncCreate$1$2 extends Lambda implements Function0<Unit> {
    final /* synthetic */ ImageView $imageView;
    final /* synthetic */ String $srcStr;
    final /* synthetic */ StageActivity $stage;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CreateImageViewAction$runAsyncCreate$1$2(String str, StageActivity stageActivity, ImageView imageView) {
        super(0);
        this.$srcStr = str;
        this.$stage = stageActivity;
        this.$imageView = imageView;
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ /* synthetic */ Unit invoke() {
        invoke2();
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2() {
        try {
            URLConnection uRLConnectionOpenConnection = new URL(this.$srcStr).openConnection();
            Intrinsics.checkNotNull(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            final Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            StageActivity stageActivity = this.$stage;
            final ImageView imageView = this.$imageView;
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.CreateImageViewAction$runAsyncCreate$1$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CreateImageViewAction$runAsyncCreate$1$2.invoke$lambda$0(imageView, bitmapDecodeStream);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(ImageView imageView, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(imageView, "$imageView");
        imageView.setImageBitmap(bitmap);
    }
}
