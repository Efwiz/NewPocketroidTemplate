package org.jetbrains.kotlin.com.intellij.ide.plugins;

import com.google.android.gms.cast.MediaTrack;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.apkeditor.Options;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginId;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl;
import org.jetbrains.kotlin.com.intellij.platform.util.plugins.DataLoader;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IdeaPluginDescriptorImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000À\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b%\b\u0007\u0018\u0000 \u0086\u00012\u00020\u0001:\u0004\u0086\u0001\u0087\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u0007¢\u0006\u0002\u0010\rJ\b\u0010H\u001a\u00020\u0007H\u0016J\u0010\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020LH\u0002J:\u0010M\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u000b2\u0006\u0010N\u001a\u00020O2\u0006\u0010K\u001a\u00020L2\u0006\u0010P\u001a\u00020Q2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0002JJ\u0010R\u001a\u00020=2\u0018\u0010S\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0!0 2\u0016\u0010T\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\b\u0012\u0006\u0012\u0002\b\u00030U0 2\u000e\u0010V\u001a\n\u0012\u0004\u0012\u00020W\u0018\u00010\u0011H\u0002J\u0013\u0010X\u001a\u00020\u00072\b\u0010Y\u001a\u0004\u0018\u00010ZH\u0096\u0002J\n\u0010[\u001a\u0004\u0018\u00010\u000bH\u0016J\n\u0010\\\u001a\u0004\u0018\u00010\u000bH\u0016J\u000e\u0010]\u001a\b\u0012\u0004\u0012\u00020^0\u0011H\u0016J\n\u0010_\u001a\u0004\u0018\u00010\u000bH\u0016J\n\u0010`\u001a\u0004\u0018\u00010\u000bH\u0016J\b\u0010a\u001a\u00020\u000bH\u0016J\u0013\u0010b\u001a\b\u0012\u0004\u0012\u00020\t0cH\u0016¢\u0006\u0002\u0010dJ\n\u0010e\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u0010f\u001a\u00020\tH\u0016J\b\u0010g\u001a\u00020\u0005H\u0016J\n\u0010h\u001a\u0004\u0018\u00010\u000bH\u0016J\n\u0010i\u001a\u0004\u0018\u00010;H\u0016J\b\u0010j\u001a\u00020=H\u0016J\n\u0010k\u001a\u0004\u0018\u00010\u000bH\u0016J\n\u0010l\u001a\u0004\u0018\u00010\u000bH\u0016J\n\u0010m\u001a\u0004\u0018\u00010\u000bH\u0016J\n\u0010n\u001a\u0004\u0018\u00010\u000bH\u0016J\n\u0010o\u001a\u0004\u0018\u00010\u000bH\u0016J\n\u0010p\u001a\u0004\u0018\u00010\u000bH\u0016J\n\u0010q\u001a\u0004\u0018\u00010\u000bH\u0016J\n\u0010r\u001a\u0004\u0018\u00010\u000bH\u0016J\b\u0010s\u001a\u00020=H\u0016J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010,\u001a\u00020\u0007H\u0016J\b\u0010t\u001a\u00020\u0007H\u0016J\b\u0010.\u001a\u00020\u0007H\u0016J\b\u0010u\u001a\u00020\u0007H\u0016J2\u0010v\u001a\u00020J2\u0006\u0010K\u001a\u00020L2\b\u0010w\u001a\u0004\u0018\u00010\t2\n\b\u0001\u0010x\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010y\u001a\u0004\u0018\u00010\tH\u0002J6\u0010z\u001a\u00020J2\u0006\u0010{\u001a\u00020\u00002\u0006\u0010K\u001a\u00020L2\u0006\u0010N\u001a\u00020O2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002070\u00112\u0006\u0010P\u001a\u00020QH\u0002J.\u0010|\u001a\u00020J2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010N\u001a\u00020O2\u0006\u0010K\u001a\u00020L2\u0006\u0010}\u001a\u00020\u00072\u0006\u0010P\u001a\u00020QJ8\u0010~\u001a\u00020J2\u0016\u0010T\u001a\u0012\u0012\u0004\u0012\u00020\u000b\u0012\b\u0012\u0006\u0012\u0002\b\u00030U0 2\u0006\u0010\u007f\u001a\u00020\u00142\u000e\u0010V\u001a\n\u0012\u0004\u0012\u00020W\u0018\u00010\u0011H\u0007J\u0011\u0010\u0080\u0001\u001a\u00020J2\b\u0010\u0015\u001a\u0004\u0018\u00010\u000bJ\u0012\u0010\u0081\u0001\u001a\u00020J2\u0007\u0010\u0082\u0001\u001a\u00020\u0007H\u0016J\u0014\u0010\u0083\u0001\u001a\u00020J2\t\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u000fH\u0007J\t\u0010\u0085\u0001\u001a\u00020\u000bH\u0016R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00118\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u00020\u00148\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u00020\u00188\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u00020\u001a8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0015\u0010\u001c\u001a\t\u0018\u00010\u000b¢\u0006\u0002\b\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u0004\u0018\u00010\u000b8\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R%\u0010\u001f\u001a\u0016\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0!\u0018\u00010 ¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u00020\u00078\u0000X\u0081\u0004¢\u0006\u0002\n\u0000R\u0016\u0010&\u001a\b\u0012\u0004\u0012\u00020\t0\u00118\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010'\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001a\u0010(\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u000e\u0010,\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010-\u001a\u00020\u00078\u0000@\u0000X\u0081\u000e¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u00020\u00078\u0000X\u0081\u0004¢\u0006\u0002\n\u0000R\u0010\u00100\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001a\u00101\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00118\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u00020\u00148\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u00103\u001a\b\u0012\u0004\u0012\u00020\t0\u00118\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00104\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u00105\u001a\u0004\u0018\u00010\u000b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u00106\u001a\b\u0012\u0004\u0012\u0002070\u00118\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00108\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u00109\u001a\u00020\u00148\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010:\u001a\u0004\u0018\u00010;X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020=X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010>\u001a\u0004\u0018\u00010\u000b8\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000R\u0010\u0010?\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010@\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\u00110 8F¢\u0006\u0006\u001a\u0004\bA\u0010$R\u0010\u0010B\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010C\u001a\u0004\u0018\u00010\u000b8\u0000X\u0081\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010D\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010E\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010F\u001a\u0004\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010G\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0088\u0001"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/ide/plugins/IdeaPluginDescriptorImpl;", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/IdeaPluginDescriptor;", Options.TYPE_RAW, "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/RawPluginDescriptor;", "path", "Ljava/nio/file/Path;", "isBundled", "", "id", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/PluginId;", "moduleName", "", "useCoreClassLoader", "(Lcom/intellij/ide/plugins/RawPluginDescriptor;Ljava/nio/file/Path;ZLcom/intellij/openapi/extensions/PluginId;Ljava/lang/String;Z)V", "_pluginClassLoader", "Ljava/lang/ClassLoader;", "actions", "", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/RawPluginDescriptor$ActionDescriptor;", "appContainerDescriptor", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ContainerDescriptor;", "category", "changeNotes", FirebaseAnalytics.Param.CONTENT, "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginContentDescriptor;", "dependencies", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/ModuleDependenciesDescriptor;", MediaTrack.ROLE_DESCRIPTION, "descriptionChildText", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/NlsSafe;", "descriptorPath", "epNameToExtensions", "", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionDescriptor;", "getEpNameToExtensions", "()Ljava/util/Map;", "implementationDetail", "incompatibilities", "isBundledUpdateAllowed", "isDeleted", "()Z", "setDeleted", "(Z)V", "isEnabled", "isIncomplete", "isLicenseOptional", "isRestartRequired", "isUseIdeaClassLoader", "jarFiles", "moduleContainerDescriptor", "modules", "name", ModuleXmlParser.JAVA_SOURCE_PACKAGE_PREFIX, "pluginDependencies", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PluginDependency;", "productCode", "projectContainerDescriptor", "releaseDate", "Ljava/util/Date;", "releaseVersion", "", "resourceBundleBaseName", "sinceBuild", "unsortedEpNameToExtensionElements", "getUnsortedEpNameToExtensionElements", "untilBuild", "url", "vendor", "vendorEmail", "vendorUrl", "version", "allowBundledUpdate", "checkCompatibility", "", "context", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/DescriptorListLoadingContext;", "createSub", "pathResolver", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/PathResolver;", "dataLoader", "Lorg/jetbrains/kotlin/com/intellij/platform/util/plugins/DataLoader;", "doRegisterExtensions", "unsortedMap", "nameToPoint", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/impl/ExtensionPointImpl;", "listenerCallbacks", "Ljava/lang/Runnable;", Namer.EQUALS_METHOD_NAME, "other", "", "getCategory", "getChangeNotes", "getDependencies", "Lorg/jetbrains/kotlin/com/intellij/ide/plugins/IdeaPluginDependency;", "getDescription", "getDescriptorPath", "getName", "getOptionalDependentPluginIds", "", "()[Lcom/intellij/openapi/extensions/PluginId;", "getPluginClassLoader", "getPluginId", "getPluginPath", "getProductCode", "getReleaseDate", "getReleaseVersion", "getResourceBundleBaseName", "getSinceBuild", "getUntilBuild", "getUrl", "getVendor", "getVendorEmail", "getVendorUrl", "getVersion", "hashCode", "isImplementationDetail", "isRequireRestart", "markAsIncomplete", "disabledDependency", "shortMessage", "pluginId", "processOldDependencies", "descriptor", "readExternal", "isSub", "registerExtensions", "containerDescriptor", "setCategory", "setEnabled", CompilerOptions.ENABLED, "setPluginClassLoader", "classLoader", "toString", "Companion", "OS", "intellij.platform.core.impl"}, k = 1, mv = {1, 5, 1})
public final class IdeaPluginDescriptorImpl implements IdeaPluginDescriptor {
    public static boolean disableNonBundledPlugins;
    private ClassLoader _pluginClassLoader;
    public final List<RawPluginDescriptor.ActionDescriptor> actions;
    public final ContainerDescriptor appContainerDescriptor;
    private String category;
    private final String changeNotes;
    public final PluginContentDescriptor content;
    public final ModuleDependenciesDescriptor dependencies;
    private final String descriptionChildText;
    public String descriptorPath;
    private final Map<String, List<ExtensionDescriptor>> epNameToExtensions;
    private final PluginId id;
    public final boolean implementationDetail;
    public final List<PluginId> incompatibilities;
    private final boolean isBundled;
    public final boolean isBundledUpdateAllowed;
    private boolean isEnabled;
    public boolean isIncomplete;
    private final boolean isLicenseOptional;
    public final boolean isRestartRequired;
    public final boolean isUseIdeaClassLoader;
    public transient List<? extends Path> jarFiles;
    public final ContainerDescriptor moduleContainerDescriptor;
    public final String moduleName;
    public final List<PluginId> modules;
    private final String name;
    public final String packagePrefix;
    public final Path path;
    public final List<PluginDependency> pluginDependencies;
    private final String productCode;
    public final ContainerDescriptor projectContainerDescriptor;
    private Date releaseDate;
    private final int releaseVersion;
    private String resourceBundleBaseName;
    private final String sinceBuild;
    private final String untilBuild;
    public final String url;
    public final boolean useCoreClassLoader;
    private String vendor;
    private final String vendorEmail;
    private final String vendorUrl;
    private String version;

    public IdeaPluginDescriptorImpl(RawPluginDescriptor raw, Path path, boolean z, PluginId pluginId, String str, boolean z2) {
        PluginId id2;
        Intrinsics.checkNotNullParameter(raw, "raw");
        Intrinsics.checkNotNullParameter(path, "path");
        this.path = path;
        this.isBundled = z;
        this.moduleName = str;
        this.useCoreClassLoader = z2;
        if (pluginId != null) {
            id2 = pluginId;
        } else {
            String str2 = raw.id;
            str2 = str2 == null ? raw.name : str2;
            if (str2 == null) {
                throw new RuntimeException("Neither id nor name are specified");
            }
            id2 = PluginId.getId(str2);
            Intrinsics.checkNotNullExpressionValue(id2, "PluginId.getId(raw.id ?:…nor name are specified\"))");
        }
        this.id = id2;
        String idString = raw.name;
        idString = idString == null ? pluginId != null ? pluginId.getIdString() : null : idString;
        this.name = idString == null ? raw.id : idString;
        this.productCode = raw.productCode;
        LocalDate localDate = raw.releaseDate;
        this.releaseDate = localDate != null ? Date.from(localDate.atStartOfDay(ZoneOffset.UTC).toInstant()) : null;
        this.releaseVersion = raw.releaseVersion;
        this.isLicenseOptional = raw.isLicenseOptional;
        this.changeNotes = raw.changeNotes;
        this.version = raw.version;
        this.vendor = raw.vendor;
        this.vendorEmail = raw.vendorEmail;
        this.vendorUrl = raw.vendorUrl;
        this.category = raw.category;
        this.url = raw.url;
        List<PluginId> listEmptyList = raw.incompatibilities;
        if (listEmptyList == null) {
            listEmptyList = Collections.emptyList();
            Intrinsics.checkNotNullExpressionValue(listEmptyList, "Collections.emptyList()");
        }
        this.incompatibilities = listEmptyList;
        List<PluginDependency> listEmptyList2 = raw.depends;
        if (listEmptyList2 != null) {
            Iterator<PluginDependency> it = listEmptyList2.iterator();
            while (it.hasNext()) {
                PluginDependency next = it.next();
                if (!next.getIsOptional()) {
                    for (PluginDependency pluginDependency : listEmptyList2) {
                        if (pluginDependency.getIsOptional() && Intrinsics.areEqual(pluginDependency.getPluginId(), next.getPluginId())) {
                            pluginDependency.setOptional$intellij_platform_core_impl(false);
                            it.remove();
                            break;
                        }
                    }
                }
            }
        }
        if (listEmptyList2 == null) {
            listEmptyList2 = Collections.emptyList();
            Intrinsics.checkNotNullExpressionValue(listEmptyList2, "Collections.emptyList()");
        }
        this.pluginDependencies = listEmptyList2;
        List<RawPluginDescriptor.ActionDescriptor> listEmptyList3 = raw.actions;
        if (listEmptyList3 == null) {
            listEmptyList3 = Collections.emptyList();
            Intrinsics.checkNotNullExpressionValue(listEmptyList3, "Collections.emptyList()");
        }
        this.actions = listEmptyList3;
        this.epNameToExtensions = raw.epNameToExtensions;
        this.appContainerDescriptor = raw.appContainerDescriptor;
        this.projectContainerDescriptor = raw.projectContainerDescriptor;
        this.moduleContainerDescriptor = raw.moduleContainerDescriptor;
        List<PluginContentDescriptor.ModuleItem> list = raw.contentModules;
        this.content = list != null ? new PluginContentDescriptor(list) : PluginContentDescriptor.EMPTY;
        this.dependencies = raw.dependencies;
        List<PluginId> listEmptyList4 = raw.modules;
        if (listEmptyList4 == null) {
            listEmptyList4 = Collections.emptyList();
            Intrinsics.checkNotNullExpressionValue(listEmptyList4, "Collections.emptyList()");
        }
        this.modules = listEmptyList4;
        this.descriptionChildText = raw.description;
        this.isUseIdeaClassLoader = raw.isUseIdeaClassLoader;
        this.isBundledUpdateAllowed = raw.isBundledUpdateAllowed;
        this.implementationDetail = raw.implementationDetail;
        this.isRestartRequired = raw.isRestartRequired;
        this.packagePrefix = raw.package;
        this.sinceBuild = raw.sinceBuild;
        this.untilBuild = raw.untilBuild;
        this.isEnabled = true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor
    /* JADX INFO: renamed from: getPluginPath, reason: from getter */
    public Path getPath() {
        return this.path;
    }

    private final IdeaPluginDescriptorImpl createSub(RawPluginDescriptor raw, String descriptorPath, PathResolver pathResolver, DescriptorListLoadingContext context, DataLoader dataLoader, String moduleName) {
        raw.name = this.name;
        IdeaPluginDescriptorImpl ideaPluginDescriptorImpl = new IdeaPluginDescriptorImpl(raw, this.path, this.isBundled, this.id, moduleName, this.useCoreClassLoader);
        ideaPluginDescriptorImpl.descriptorPath = descriptorPath;
        ideaPluginDescriptorImpl.vendor = this.vendor;
        ideaPluginDescriptorImpl.version = this.version;
        ideaPluginDescriptorImpl.resourceBundleBaseName = this.resourceBundleBaseName;
        ideaPluginDescriptorImpl.readExternal(raw, pathResolver, context, true, dataLoader);
        return ideaPluginDescriptorImpl;
    }

    public final void readExternal(RawPluginDescriptor raw, PathResolver pathResolver, DescriptorListLoadingContext context, boolean isSub, DataLoader dataLoader) {
        Intrinsics.checkNotNullParameter(raw, "raw");
        Intrinsics.checkNotNullParameter(pathResolver, "pathResolver");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(dataLoader, "dataLoader");
        if (!isSub) {
            for (PluginContentDescriptor.ModuleItem moduleItem : this.content.modules) {
                String str = moduleItem.configFile;
                if (str == null) {
                    str = moduleItem.name + ".xml";
                }
                String str2 = str;
                moduleItem.descriptor = createSub(pathResolver.resolveModuleFile(context, dataLoader, str2, null), str2, pathResolver, context, dataLoader, moduleItem.name);
            }
        }
        if (raw.resourceBundleBaseName != null) {
            if (Intrinsics.areEqual(this.id, PluginManagerCore.CORE_ID) && !isSub) {
                IdeaPluginDescriptorImplKt.getLOG().warn("<resource-bundle>" + raw.resourceBundleBaseName + "</resource-bundle> tag is found in an xml descriptor included into the platform part of the IDE but the platform part uses predefined bundles (e.g. ActionsBundle for actions) anyway; this tag must be replaced by a corresponding attribute in some inner tags (e.g. by 'resource-bundle' attribute in 'actions' tag)");
            }
            String str3 = this.resourceBundleBaseName;
            if (str3 != null && !Intrinsics.areEqual(str3, raw.resourceBundleBaseName)) {
                IdeaPluginDescriptorImplKt.getLOG().warn("Resource bundle redefinition for plugin " + this.id + ". Old value: " + this.resourceBundleBaseName + ", new value: " + raw.resourceBundleBaseName);
            }
            this.resourceBundleBaseName = raw.resourceBundleBaseName;
        }
        if (this.version == null) {
            this.version = context.getDefaultVersion();
        }
        if (!isSub) {
            if (context.isPluginDisabled(this.id)) {
                markAsIncomplete$default(this, context, null, null, null, 8, null);
            } else {
                checkCompatibility(context);
                if (this.isIncomplete) {
                    return;
                }
                for (ModuleDependenciesDescriptor.PluginReference pluginReference : this.dependencies.plugins) {
                    if (context.isPluginDisabled(pluginReference.id)) {
                        markAsIncomplete$default(this, context, pluginReference.id, "plugin.loading.error.short.depends.on.disabled.plugin", null, 8, null);
                    } else if (context.result.isBroken(pluginReference.id)) {
                        markAsIncomplete(context, null, "plugin.loading.error.short.depends.on.broken.plugin", pluginReference.id);
                    }
                }
            }
        }
        if (this.isIncomplete || this.moduleName != null) {
            return;
        }
        processOldDependencies(this, context, pathResolver, this.pluginDependencies, dataLoader);
    }

    private final void processOldDependencies(IdeaPluginDescriptorImpl descriptor, DescriptorListLoadingContext context, PathResolver pathResolver, List<PluginDependency> dependencies, DataLoader dataLoader) {
        IOException iOException;
        RawPluginDescriptor rawPluginDescriptorResolvePath;
        List<String> visitedFiles = null;
        for (PluginDependency pluginDependency : dependencies) {
            if (context.isPluginDisabled(pluginDependency.getPluginId())) {
                if (!pluginDependency.getIsOptional() && !this.isIncomplete) {
                    markAsIncomplete$default(this, context, pluginDependency.getPluginId(), "plugin.loading.error.short.depends.on.disabled.plugin", null, 8, null);
                }
            } else if (context.result.isBroken(pluginDependency.getPluginId()) && !pluginDependency.getIsOptional() && !this.isIncomplete) {
                markAsIncomplete(context, null, "plugin.loading.error.short.depends.on.broken.plugin", pluginDependency.getPluginId());
            }
            String configFile = pluginDependency.getConfigFile();
            if (configFile != null && (!pathResolver.isFlat() || !context.checkOptionalConfigShortName(configFile, descriptor))) {
                try {
                    try {
                        rawPluginDescriptorResolvePath = pathResolver.resolvePath(context, dataLoader, configFile, null);
                        iOException = null;
                    } catch (IOException e) {
                        e = e;
                        iOException = e;
                        rawPluginDescriptorResolvePath = null;
                    }
                } catch (IOException e2) {
                    e = e2;
                }
                if (rawPluginDescriptorResolvePath != null) {
                    if (visitedFiles == null) {
                        visitedFiles = context.getVisitedFiles();
                    }
                    IdeaPluginDescriptorImplKt.checkCycle(descriptor, configFile, visitedFiles);
                    visitedFiles.add(configFile);
                    pluginDependency.subDescriptor = descriptor.createSub(rawPluginDescriptorResolvePath, configFile, pathResolver, context, dataLoader, null);
                    visitedFiles.clear();
                } else {
                    String str = "Plugin " + descriptor + " misses optional descriptor " + configFile;
                    if (context.isMissingSubDescriptorIgnored) {
                        IdeaPluginDescriptorImplKt.getLOG().info(str);
                        if (iOException != null) {
                            IdeaPluginDescriptorImplKt.getLOG().debug(iOException);
                        }
                    } else {
                        throw new RuntimeException(str, iOException);
                    }
                }
            }
        }
    }

    private final void checkCompatibility(final DescriptorListLoadingContext context) {
        PluginLoadingError it;
        if (this.isBundled) {
            return;
        }
        Function1<PluginLoadingError, Unit> function1 = new Function1<PluginLoadingError, Unit>() { // from class: org.jetbrains.kotlin.com.intellij.ide.plugins.IdeaPluginDescriptorImpl.checkCompatibility.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(PluginLoadingError pluginLoadingError) {
                invoke2(pluginLoadingError);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(PluginLoadingError error) {
                Intrinsics.checkNotNullParameter(error, "error");
                if (IdeaPluginDescriptorImpl.this.isIncomplete) {
                    return;
                }
                IdeaPluginDescriptorImpl.this.isIncomplete = true;
                IdeaPluginDescriptorImpl.this.isEnabled = false;
                context.result.addIncompletePlugin$intellij_platform_core_impl(IdeaPluginDescriptorImpl.this, error);
            }
        };
        if (!disableNonBundledPlugins) {
            if (IdeaPluginDescriptorImplKt.checkCompatibilityFlag && ((this.sinceBuild != null || this.untilBuild != null) && (it = PluginManagerCore.checkBuildNumberCompatibility(this, context.result.productBuildNumber.get())) != null)) {
                Intrinsics.checkNotNullExpressionValue(it, "it");
                function1.invoke2(it);
                return;
            } else {
                if (context.result.isBroken(this)) {
                    function1.invoke2(new PluginLoadingError(this, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.ide.plugins.IdeaPluginDescriptorImpl.checkCompatibility.5
                        @Override // java.util.function.Supplier
                        public final String get() {
                            return CoreBundle.message("plugin.loading.error.long.marked.as.broken", IdeaPluginDescriptorImpl.this.name, IdeaPluginDescriptorImpl.this.version);
                        }
                    }, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.ide.plugins.IdeaPluginDescriptorImpl.checkCompatibility.6
                        @Override // java.util.function.Supplier
                        public final String get() {
                            return CoreBundle.message("plugin.loading.error.short.marked.as.broken", new Object[0]);
                        }
                    }));
                    return;
                }
                return;
            }
        }
        function1.invoke2(new PluginLoadingError(this, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.ide.plugins.IdeaPluginDescriptorImpl.checkCompatibility.2
            @Override // java.util.function.Supplier
            public final String get() {
                return CoreBundle.message("plugin.loading.error.long.custom.plugin.loading.disabled", IdeaPluginDescriptorImpl.this.getName());
            }
        }, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.ide.plugins.IdeaPluginDescriptorImpl.checkCompatibility.3
            @Override // java.util.function.Supplier
            public final String get() {
                return CoreBundle.message("plugin.loading.error.short.custom.plugin.loading.disabled", new Object[0]);
            }
        }, false, null, 16, null));
    }

    static /* synthetic */ void markAsIncomplete$default(IdeaPluginDescriptorImpl ideaPluginDescriptorImpl, DescriptorListLoadingContext descriptorListLoadingContext, PluginId pluginId, String str, PluginId pluginId2, int i, Object obj) {
        if ((i & 8) != 0) {
            pluginId2 = pluginId;
        }
        ideaPluginDescriptorImpl.markAsIncomplete(descriptorListLoadingContext, pluginId, str, pluginId2);
    }

    private final void markAsIncomplete(DescriptorListLoadingContext context, PluginId disabledDependency, final String shortMessage, final PluginId pluginId) {
        if (this.isIncomplete) {
            return;
        }
        this.isIncomplete = true;
        this.isEnabled = false;
        context.result.addIncompletePlugin$intellij_platform_core_impl(this, shortMessage == null ? null : new PluginLoadingError(this, null, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.ide.plugins.IdeaPluginDescriptorImpl$markAsIncomplete$pluginError$1
            @Override // java.util.function.Supplier
            public final String get() {
                String str = shortMessage;
                PluginId pluginId2 = pluginId;
                Intrinsics.checkNotNull(pluginId2);
                return CoreBundle.message(str, pluginId2);
            }
        }, false, disabledDependency));
    }

    public final void registerExtensions(final Map<String, ? extends ExtensionPointImpl<?>> nameToPoint, ContainerDescriptor containerDescriptor, final List<? extends Runnable> listenerCallbacks) {
        Intrinsics.checkNotNullParameter(nameToPoint, "nameToPoint");
        Intrinsics.checkNotNullParameter(containerDescriptor, "containerDescriptor");
        Map<String, ? extends List<ExtensionDescriptor>> map = containerDescriptor.extensions;
        if (map != null) {
            if (map.isEmpty()) {
                return;
            }
            map.forEach(new BiConsumer() { // from class: org.jetbrains.kotlin.com.intellij.ide.plugins.IdeaPluginDescriptorImpl$registerExtensions$$inlined$let$lambda$1
                @Override // java.util.function.BiConsumer
                public final void accept(String name2, List<ExtensionDescriptor> list) {
                    Intrinsics.checkNotNullParameter(name2, "name");
                    Intrinsics.checkNotNullParameter(list, "list");
                    ExtensionPointImpl extensionPointImpl = (ExtensionPointImpl) nameToPoint.get(name2);
                    if (extensionPointImpl != null) {
                        extensionPointImpl.registerExtensions(list, this.this$0, listenerCallbacks);
                    }
                }
            });
            return;
        }
        Map<String, List<ExtensionDescriptor>> map2 = this.epNameToExtensions;
        if (map2 != null) {
            if (Intrinsics.areEqual(containerDescriptor, this.appContainerDescriptor)) {
                int iDoRegisterExtensions = doRegisterExtensions(map2, nameToPoint, listenerCallbacks);
                containerDescriptor.setDistinctExtensionPointCount(iDoRegisterExtensions);
                if (iDoRegisterExtensions == map2.size()) {
                    this.projectContainerDescriptor.extensions = Collections.emptyMap();
                    this.moduleContainerDescriptor.extensions = Collections.emptyMap();
                    return;
                }
                return;
            }
            if (Intrinsics.areEqual(containerDescriptor, this.projectContainerDescriptor)) {
                int iDoRegisterExtensions2 = doRegisterExtensions(map2, nameToPoint, listenerCallbacks);
                containerDescriptor.setDistinctExtensionPointCount(iDoRegisterExtensions2);
                if (iDoRegisterExtensions2 == map2.size()) {
                    containerDescriptor.extensions = map2;
                    this.moduleContainerDescriptor.extensions = Collections.emptyMap();
                    return;
                } else {
                    if (iDoRegisterExtensions2 == map2.size() - this.appContainerDescriptor.getDistinctExtensionPointCount()) {
                        this.moduleContainerDescriptor.extensions = Collections.emptyMap();
                        return;
                    }
                    return;
                }
            }
            if (doRegisterExtensions(map2, nameToPoint, listenerCallbacks) == 0) {
                this.moduleContainerDescriptor.extensions = Collections.emptyMap();
            }
        }
    }

    private final int doRegisterExtensions(Map<String, ? extends List<ExtensionDescriptor>> unsortedMap, Map<String, ? extends ExtensionPointImpl<?>> nameToPoint, List<? extends Runnable> listenerCallbacks) {
        int i = 0;
        for (Map.Entry<String, ? extends List<ExtensionDescriptor>> entry : unsortedMap.entrySet()) {
            ExtensionPointImpl<?> extensionPointImpl = nameToPoint.get(entry.getKey());
            if (extensionPointImpl != null) {
                extensionPointImpl.registerExtensions(entry.getValue(), this, listenerCallbacks);
                i++;
            }
        }
        return i;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor
    public String getName() {
        String str = this.name;
        Intrinsics.checkNotNull(str);
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor
    public String getVersion() {
        return this.version;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor
    /* JADX INFO: renamed from: getPluginId, reason: from getter */
    public PluginId getId() {
        return this.id;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor
    /* JADX INFO: renamed from: getPluginClassLoader, reason: from getter */
    public ClassLoader get_pluginClassLoader() {
        return this._pluginClassLoader;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor
    public String getSinceBuild() {
        return this.sinceBuild;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor
    public String getUntilBuild() {
        return this.untilBuild;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.extensions.PluginDescriptor
    /* JADX INFO: renamed from: isBundled, reason: from getter */
    public boolean getIsBundled() {
        return this.isBundled;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IdeaPluginDescriptorImpl)) {
            return false;
        }
        IdeaPluginDescriptorImpl ideaPluginDescriptorImpl = (IdeaPluginDescriptorImpl) other;
        return Intrinsics.areEqual(this.id, ideaPluginDescriptorImpl.id) && Intrinsics.areEqual(this.descriptorPath, ideaPluginDescriptorImpl.descriptorPath);
    }

    public int hashCode() {
        int iHashCode = this.id.hashCode() * 31;
        String str = this.descriptorPath;
        return iHashCode + (str != null ? str.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder("PluginDescriptor(name=").append(this.name).append(", id=").append(this.id).append(", ").append(this.moduleName == null ? "" : "moduleName=" + this.moduleName + ", ").append("descriptorPath=");
        String str = this.descriptorPath;
        if (str == null) {
            str = "plugin.xml";
        }
        return sbAppend.append(str).append(", path=").append(IdeaPluginDescriptorImplKt.pluginPathToUserString(this.path)).append(", version=").append(this.version).append(", package=").append(this.packagePrefix).append(", isBundled=").append(this.isBundled).append(Constant.AFTER_QUTO).toString();
    }
}
