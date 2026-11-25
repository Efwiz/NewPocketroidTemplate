package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import java.lang.ref.WeakReference;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.application.Application;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;

/* JADX INFO: compiled from: MappedExtensionProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\u0006\b\u0001\u0010\u0003 \u00012\u00020\u0002B/\b\u0004\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0018\u0010\u0006\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\b\u0012\u0004\u0012\u00028\u00010\u0007¢\u0006\u0002\u0010\tJ\u000b\u0010\u000e\u001a\u00028\u0001¢\u0006\u0002\u0010\u000fJ\r\u0010\u0010\u001a\u00028\u0001H\u0002¢\u0006\u0002\u0010\u000fR \u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00028\u00010\f0\u000bX\u0088\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0006\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\b\u0012\u0004\u0012\u00028\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/util/MappedExtensionProvider;", ExifInterface.GPS_DIRECTION_TRUE, "", "R", "epName", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointName;", "map", "Lkotlin/Function1;", "", "(Lcom/intellij/openapi/extensions/ExtensionPointName;Lkotlin/jvm/functions/Function1;)V", "cached", "Ljava/lang/ref/WeakReference;", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/com/intellij/openapi/application/Application;", "get", "()Ljava/lang/Object;", ConfigConstants.CONFIG_KEY_UPDATE, "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class MappedExtensionProvider<T, R> {
    private WeakReference<Pair<Application, R>> cached;
    private final ExtensionPointName<T> epName;
    private final Function1<List<? extends T>, R> map;

    /* JADX WARN: Multi-variable type inference failed */
    protected MappedExtensionProvider(ExtensionPointName<T> epName, Function1<? super List<? extends T>, ? extends R> map) {
        Intrinsics.checkNotNullParameter(epName, "epName");
        Intrinsics.checkNotNullParameter(map, "map");
        this.epName = epName;
        this.map = map;
        this.cached = new WeakReference<>(null);
    }

    public final R get() {
        Pair<Application, R> pair = this.cached.get();
        if (pair == null) {
            return update();
        }
        return Intrinsics.areEqual(pair.component1(), ApplicationManager.getApplication()) ? pair.component2() : update();
    }

    private final R update() {
        Application application = ApplicationManager.getApplication();
        Function1<List<? extends T>, R> function1 = this.map;
        List<T> extensionList = application.getExtensionArea().getExtensionPoint(this.epName).getExtensionList();
        Intrinsics.checkNotNullExpressionValue(extensionList, "getExtensionList(...)");
        Pair pair = new Pair(application, function1.invoke(extensionList));
        this.cached = new WeakReference<>(pair);
        return (R) pair.getSecond();
    }
}
