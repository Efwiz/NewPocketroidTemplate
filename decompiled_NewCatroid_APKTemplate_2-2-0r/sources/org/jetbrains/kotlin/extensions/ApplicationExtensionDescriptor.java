package org.jetbrains.kotlin.extensions;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.core.CoreApplicationEnvironment;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea;

/* JADX INFO: compiled from: ApplicationExtensionDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007J\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u000f\u001a\u00020\u0010¢\u0006\u0002\u0010\u0011J\u0006\u0010\u0012\u001a\u00020\rR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/extensions/ApplicationExtensionDescriptor;", ExifInterface.GPS_DIRECTION_TRUE, "", "name", "", "extensionClass", "Ljava/lang/Class;", "(Ljava/lang/String;Ljava/lang/Class;)V", "extensionPointName", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointName;", "getInstances", "", "registerExtension", "", SchemaSymbols.ATTVAL_EXTENSION, "disposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "(Ljava/lang/Object;Lcom/intellij/openapi/Disposable;)V", "registerExtensionPoint", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ApplicationExtensionDescriptor<T> {
    private final Class<T> extensionClass;
    private final ExtensionPointName<T> extensionPointName;

    public ApplicationExtensionDescriptor(String name2, Class<T> extensionClass) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(extensionClass, "extensionClass");
        this.extensionClass = extensionClass;
        ExtensionPointName<T> extensionPointNameCreate = ExtensionPointName.create(name2);
        Intrinsics.checkNotNullExpressionValue(extensionPointNameCreate, "create(...)");
        this.extensionPointName = extensionPointNameCreate;
    }

    public final void registerExtensionPoint() {
        CoreApplicationEnvironment.registerExtensionPoint(ApplicationManager.getApplication().getExtensionArea(), this.extensionPointName.getName(), this.extensionClass);
    }

    public final void registerExtension(T extension, Disposable disposable) {
        Intrinsics.checkNotNullParameter(extension, "extension");
        Intrinsics.checkNotNullParameter(disposable, "disposable");
        ApplicationManager.getApplication().getExtensionArea().getExtensionPoint(this.extensionPointName).registerExtension(extension, disposable);
    }

    public final List<T> getInstances() {
        ExtensionsArea extensionArea = ApplicationManager.getApplication().getExtensionArea();
        Intrinsics.checkNotNullExpressionValue(extensionArea, "getExtensionArea(...)");
        if (!extensionArea.hasExtensionPoint(this.extensionPointName.getName())) {
            return CollectionsKt.emptyList();
        }
        T[] extensions = extensionArea.getExtensionPoint(this.extensionPointName).getExtensions();
        Intrinsics.checkNotNullExpressionValue(extensions, "getExtensions(...)");
        return ArraysKt.toList(extensions);
    }
}
