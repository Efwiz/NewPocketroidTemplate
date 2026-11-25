package org.jetbrains.kotlin.com.intellij.ide.plugins;

import com.google.android.gms.cast.MediaTrack;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;
import org.jetbrains.kotlin.com.intellij.util.XmlElement;
import org.jetbrains.kotlin.library.KotlinLibraryKt;

/* JADX INFO: compiled from: RawPluginDescriptor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001:\u00011B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00048\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u0004\u0018\u00010\t8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u0004\u0018\u00010\t8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u00048\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\r\u001a\u00020\u000e8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u00048\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0019\u0010\u0011\u001a\t\u0018\u00010\t¢\u0006\u0002\b\u00128\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R&\u0010\u0013\u001a\u0016\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u0004\u0018\u00010\u00148\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u0004\u0018\u00010\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0017\u001a\u00020\u00188\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001b\u001a\u00020\u00188\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001c\u001a\u00020\u00188\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001d\u001a\u00020\u00188\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001e\u001a\u00020\u00188\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001a\u0010 \u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u00048\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\u0004\u0018\u00010\t8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\u0004\u0018\u00010\t8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010#\u001a\u0004\u0018\u00010\t8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0014\u0010%\u001a\u0004\u0018\u00010&8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0012\u0010'\u001a\u00020(8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010)\u001a\u0004\u0018\u00010\t8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010*\u001a\u0004\u0018\u00010\t8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010+\u001a\u0004\u0018\u00010\t8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010,\u001a\u0004\u0018\u00010\t8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010-\u001a\u0004\u0018\u00010\t8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010.\u001a\u0004\u0018\u00010\t8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u0010/\u001a\u0004\u0018\u00010\t8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u0014\u00100\u001a\u0004\u0018\u00010\t8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/RawPluginDescriptor;", "", "()V", "actions", "", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/RawPluginDescriptor$ActionDescriptor;", "appContainerDescriptor", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ContainerDescriptor;", "category", "", "changeNotes", "contentModules", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginContentDescriptor$ModuleItem;", "dependencies", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ModuleDependenciesDescriptor;", KotlinLibraryKt.KLIB_PROPERTY_DEPENDS, "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginDependency;", MediaTrack.ROLE_DESCRIPTION, "Lorg/jetbrains/kotlin/com/intellij/openapi/util/NlsSafe;", "epNameToExtensions", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionDescriptor;", "id", "implementationDetail", "", "incompatibilities", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/PluginId;", "isBundledUpdateAllowed", "isLicenseOptional", "isRestartRequired", "isUseIdeaClassLoader", "moduleContainerDescriptor", "modules", "name", "package", "productCode", "projectContainerDescriptor", "releaseDate", "Ljava/time/LocalDate;", "releaseVersion", "", "resourceBundleBaseName", "sinceBuild", "untilBuild", "url", "vendor", "vendorEmail", "vendorUrl", "version", "ActionDescriptor", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
public final class RawPluginDescriptor {
    public List<ActionDescriptor> actions;
    public String category;
    public String changeNotes;
    public List<PluginContentDescriptor.ModuleItem> contentModules;
    public List<PluginDependency> depends;
    public String description;
    public Map<String, List<ExtensionDescriptor>> epNameToExtensions;
    public String id;
    public boolean implementationDetail;
    public List<PluginId> incompatibilities;
    public boolean isBundledUpdateAllowed;
    public boolean isLicenseOptional;
    public boolean isRestartRequired;
    public boolean isUseIdeaClassLoader;
    public List<PluginId> modules;
    public String name;
    public String package;
    public String productCode;
    public LocalDate releaseDate;
    public int releaseVersion;
    public String resourceBundleBaseName;
    public String sinceBuild;
    public String untilBuild;
    public String url;
    public String vendor;
    public String vendorEmail;
    public String vendorUrl;
    public String version;
    public final ContainerDescriptor appContainerDescriptor = new ContainerDescriptor();
    public final ContainerDescriptor projectContainerDescriptor = new ContainerDescriptor();
    public final ContainerDescriptor moduleContainerDescriptor = new ContainerDescriptor();
    public ModuleDependenciesDescriptor dependencies = ModuleDependenciesDescriptor.EMPTY;

    /* JADX INFO: compiled from: RawPluginDescriptor.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007R\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/RawPluginDescriptor$ActionDescriptor;", "", "name", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/util/XmlElement;", "resourceBundle", "(Ljava/lang/String;Lcom/intellij/util/XmlElement;Ljava/lang/String;)V", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
    public static final class ActionDescriptor {
        public final XmlElement element;
        public final String name;
        public final String resourceBundle;

        public ActionDescriptor(String name2, XmlElement element, String str) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(element, "element");
            this.name = name2;
            this.element = element;
            this.resourceBundle = str;
        }
    }
}
