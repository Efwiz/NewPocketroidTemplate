package org.jetbrains.kotlin.extensions;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.core.CoreApplicationEnvironment;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: compiled from: ProjectExtensionDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007J\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u001b\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00028\u0000¢\u0006\u0002\u0010\u0013J\u000e\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", ExifInterface.GPS_DIRECTION_TRUE, "", "name", "", "extensionClass", "Ljava/lang/Class;", "(Ljava/lang/String;Ljava/lang/Class;)V", "extensionPointName", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointName;", "getExtensionPointName", "()Lcom/intellij/openapi/extensions/ExtensionPointName;", "getInstances", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "registerExtension", "", SchemaSymbols.ATTVAL_EXTENSION, "(Lcom/intellij/openapi/project/Project;Ljava/lang/Object;)V", "registerExtensionPoint", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ProjectExtensionDescriptor<T> {
    private final Class<T> extensionClass;
    private final ExtensionPointName<T> extensionPointName;

    public ProjectExtensionDescriptor(String name2, Class<T> extensionClass) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(extensionClass, "extensionClass");
        this.extensionClass = extensionClass;
        ExtensionPointName<T> extensionPointNameCreate = ExtensionPointName.create(name2);
        Intrinsics.checkNotNullExpressionValue(extensionPointNameCreate, "create(...)");
        this.extensionPointName = extensionPointNameCreate;
    }

    public final ExtensionPointName<T> getExtensionPointName() {
        return this.extensionPointName;
    }

    public final void registerExtensionPoint(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        CoreApplicationEnvironment.registerExtensionPoint(project.getExtensionArea(), this.extensionPointName.getName(), this.extensionClass);
    }

    public final void registerExtension(Project project, T extension) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(extension, "extension");
        project.getExtensionArea().getExtensionPoint(this.extensionPointName).registerExtension(extension, project);
    }

    public final List<T> getInstances(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        ExtensionsArea extensionArea = project.getExtensionArea();
        Intrinsics.checkNotNullExpressionValue(extensionArea, "getExtensionArea(...)");
        if (!extensionArea.hasExtensionPoint(this.extensionPointName.getName())) {
            return CollectionsKt.emptyList();
        }
        T[] extensions = extensionArea.getExtensionPoint(this.extensionPointName).getExtensions();
        Intrinsics.checkNotNullExpressionValue(extensions, "getExtensions(...)");
        return ArraysKt.toList(extensions);
    }
}
