package org.catrobat.paintroid.ui;

import android.content.Context;
import android.net.Uri;
import androidx.test.espresso.idling.CountingIdlingResource;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.catrobat.paintroid.command.serialization.CommandSerializer;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.contract.MainActivityContracts;
import org.catrobat.paintroid.iotasks.CreateFile;
import org.catrobat.paintroid.iotasks.LoadImage;
import org.catrobat.paintroid.iotasks.SaveImage;

/* JADX INFO: compiled from: MainActivityInteractor.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J:\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J:\u0010\u0019\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u001a2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J:\u0010\u001d\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u001a2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/catrobat/paintroid/ui/MainActivityInteractor;", "Lorg/catrobat/paintroid/contract/MainActivityContracts$Interactor;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "(Landroidx/test/espresso/idling/CountingIdlingResource;)V", "scopeIO", "Lkotlinx/coroutines/CoroutineScope;", "createFile", "", "callback", "Lorg/catrobat/paintroid/iotasks/CreateFile$CreateFileCallback;", "requestCode", "", "filename", "", "loadFile", "Lorg/catrobat/paintroid/iotasks/LoadImage$LoadImageCallback;", "uri", "Landroid/net/Uri;", "context", "Landroid/content/Context;", "scaling", "", "commandSerializer", "Lorg/catrobat/paintroid/command/serialization/CommandSerializer;", "saveCopy", "Lorg/catrobat/paintroid/iotasks/SaveImage$SaveImageCallback;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "saveImage", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class MainActivityInteractor implements MainActivityContracts.Interactor {
    private final CountingIdlingResource idlingResource;
    private final CoroutineScope scopeIO;

    public MainActivityInteractor(CountingIdlingResource idlingResource) {
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        this.idlingResource = idlingResource;
        this.scopeIO = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO());
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Interactor
    public void saveCopy(SaveImage.SaveImageCallback callback, int requestCode, LayerContracts.Model layerModel, CommandSerializer commandSerializer, Uri uri, Context context) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        Intrinsics.checkNotNullParameter(commandSerializer, "commandSerializer");
        Intrinsics.checkNotNullParameter(context, "context");
        new SaveImage(callback, requestCode, layerModel, commandSerializer, uri, true, context, this.scopeIO, this.idlingResource).execute();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Interactor
    public void createFile(CreateFile.CreateFileCallback callback, int requestCode, String filename) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(filename, "filename");
        new CreateFile(callback, requestCode, filename, this.scopeIO).execute();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Interactor
    public void saveImage(SaveImage.SaveImageCallback callback, int requestCode, LayerContracts.Model layerModel, CommandSerializer commandSerializer, Uri uri, Context context) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        Intrinsics.checkNotNullParameter(commandSerializer, "commandSerializer");
        Intrinsics.checkNotNullParameter(context, "context");
        new SaveImage(callback, requestCode, layerModel, commandSerializer, uri, false, context, this.scopeIO, this.idlingResource).execute();
    }

    @Override // org.catrobat.paintroid.contract.MainActivityContracts.Interactor
    public void loadFile(LoadImage.LoadImageCallback callback, int requestCode, Uri uri, Context context, boolean scaling, CommandSerializer commandSerializer) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(commandSerializer, "commandSerializer");
        new LoadImage(callback, requestCode, uri, context, scaling, commandSerializer, this.scopeIO, this.idlingResource).execute();
    }
}
