package org.jetbrains.kotlin.incremental;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.build.GeneratedJvmClass;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.util.io.BooleanDataDescriptor;
import org.jetbrains.kotlin.com.intellij.util.io.EnumeratorStringDescriptor;
import org.jetbrains.kotlin.incremental.storage.AbstractDirtyClassesMap;
import org.jetbrains.kotlin.incremental.storage.AbstractSourceToOutputMap;
import org.jetbrains.kotlin.incremental.storage.BasicStringMap;
import org.jetbrains.kotlin.incremental.storage.ClassAttributesMap;
import org.jetbrains.kotlin.incremental.storage.DirtyClassesJvmNameMap;
import org.jetbrains.kotlin.incremental.storage.FileToPathConverter;
import org.jetbrains.kotlin.incremental.storage.FileToPathConverterKt;
import org.jetbrains.kotlin.incremental.storage.InlineFunctionOrAccessorExternalizer;
import org.jetbrains.kotlin.incremental.storage.LazyStorage;
import org.jetbrains.kotlin.incremental.storage.LongExternalizer;
import org.jetbrains.kotlin.incremental.storage.MapExternalizer;
import org.jetbrains.kotlin.incremental.storage.ProtoMapValue;
import org.jetbrains.kotlin.incremental.storage.ProtoMapValueExternalizer;
import org.jetbrains.kotlin.incremental.storage.SourceToJvmNameMap;
import org.jetbrains.kotlin.incremental.storage.StringCollectionExternalizer;
import org.jetbrains.kotlin.incremental.storage.StringExternalizer;
import org.jetbrains.kotlin.inline.InlineFunction;
import org.jetbrains.kotlin.inline.InlineFunctionOrAccessor;
import org.jetbrains.kotlin.inline.InlinePropertyAccessor;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache;
import org.jetbrains.kotlin.load.kotlin.incremental.components.JvmPackagePartProto;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;

/* JADX INFO: compiled from: IncrementalJvmCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ú\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u0000 c2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\tcdefghijkB\u001f\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\tJ\u001a\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00020)2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00050)J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.H\u0016J&\u0010/\u001a\u00020,2\u0006\u00100\u001a\u00020\u00022\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u0010-\u001a\u00020.J\u0010\u00105\u001a\u00020,2\u0006\u00106\u001a\u000207H\u0014J\u0016\u00108\u001a\n\u0012\u0004\u0012\u000207\u0018\u0001092\u0006\u0010:\u001a\u00020\u0002J\u0010\u0010;\u001a\u0002072\u0006\u0010<\u001a\u000207H\u0016J\n\u0010=\u001a\u0004\u0018\u00010>H\u0016J\f\u0010?\u001a\b\u0012\u0004\u0012\u00020@09J\u000e\u0010A\u001a\b\u0012\u0004\u0012\u00020709H\u0016J\u000e\u0010B\u001a\b\u0012\u0004\u0012\u00020709H\u0016J\u0012\u0010C\u001a\u0004\u0018\u00010D2\u0006\u0010E\u001a\u000207H\u0016J\u0018\u0010F\u001a\n\u0012\u0004\u0012\u000207\u0018\u0001092\u0006\u0010G\u001a\u000207H\u0016J\u000e\u0010H\u001a\u00020I2\u0006\u0010J\u001a\u00020@J\u000e\u0010K\u001a\u00020I2\u0006\u0010J\u001a\u00020@J\u000e\u0010L\u001a\u00020I2\u0006\u00100\u001a\u00020\u0002J\u000e\u0010M\u001a\u00020I2\u0006\u0010N\u001a\u00020\u0005J&\u0010O\u001a\u00020,2\u0006\u0010P\u001a\u00020Q2\u000e\u0010R\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010S2\u0006\u0010-\u001a\u00020.J\u0018\u0010T\u001a\u00020,2\u0006\u0010U\u001a\u00020V2\u0006\u0010-\u001a\u00020.H\u0016J6\u0010W\u001a\u00020,2\u0006\u0010J\u001a\u00020@2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u000e\u0010R\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010S2\u0006\u0010-\u001a\u00020.J \u0010X\u001a\u00020,2\b\u0010Y\u001a\u0004\u0018\u00010\u00052\u0006\u0010Z\u001a\u00020[2\u0006\u0010\\\u001a\u00020.J\u001c\u0010]\u001a\u00020,2\f\u0010R\u001a\b\u0012\u0004\u0012\u00020\u0005092\u0006\u0010N\u001a\u00020\u0005J\u000e\u0010^\u001a\u00020I2\u0006\u0010N\u001a\u00020\u0005J\u0014\u0010_\u001a\b\u0012\u0004\u0012\u00020\u0005092\u0006\u0010`\u001a\u000207J\f\u0010a\u001a\u00020b*\u00020QH\u0002R\u0012\u0010\n\u001a\u00060\u000bR\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\rX\u0090\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00060\u0011R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0012\u001a\u00060\u0013R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0014\u001a\u00060\u0015R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0016\u001a\u00060\u0017R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0018\u001a\u00060\u0019R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001a\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001b\u0010\u001cR\u0012\u0010\u001f\u001a\u00060 R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010!\u001a\u00060\"R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010#\u001a\u00060\u0011R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010$\u001a\u00020%X\u0090\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'¨\u0006l"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache;", "Lorg/jetbrains/kotlin/incremental/AbstractIncrementalCache;", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/IncrementalCache;", "targetDataRoot", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "targetOutputDir", "(Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;Ljava/io/File;)V", "constantsMap", "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$ConstantsMap;", "dirtyOutputClassesMap", "Lorg/jetbrains/kotlin/incremental/storage/DirtyClassesJvmNameMap;", "getDirtyOutputClassesMap$kotlin_build_common", "()Lorg/jetbrains/kotlin/incremental/storage/DirtyClassesJvmNameMap;", "feProtoMap", "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$ProtoMap;", "inlineFunctionsMap", "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$InlineFunctionsMap;", "internalNameToSource", "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$InternalNameToSourcesMap;", "javaSourcesProtoMap", "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$JavaSourcesProtoMap;", "multifileFacadeToParts", "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$MultifileClassFacadeMap;", ModuleXmlParser.OUTPUT_DIR, "getOutputDir", "()Ljava/io/File;", "outputDir$delegate", "Lkotlin/Lazy;", "packagePartMap", "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$PackagePartMap;", "partToMultifileFacade", "Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$MultifileClassPartMap;", "protoMap", "sourceToClassesMap", "Lorg/jetbrains/kotlin/incremental/storage/SourceToJvmNameMap;", "getSourceToClassesMap$kotlin_build_common", "()Lorg/jetbrains/kotlin/incremental/storage/SourceToJvmNameMap;", "classesBySources", "", "sources", "clearCacheForRemovedClasses", "", "changesCollector", "Lorg/jetbrains/kotlin/incremental/ChangesCollector;", "collectClassChangesByFeMetadata", "className", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "stringTable", "Lorg/jetbrains/kotlin/metadata/jvm/serialization/JvmStringTable;", "debugLog", CrashHianalyticsData.MESSAGE, "", "getAllPartsOfMultifileFacade", "", "facade", "getClassFilePath", "internalClassName", "getModuleMappingData", "", "getObsoleteJavaClasses", "Lorg/jetbrains/kotlin/name/ClassId;", "getObsoleteMultifileClasses", "getObsoletePackageParts", "getPackagePartData", "Lorg/jetbrains/kotlin/load/kotlin/incremental/components/JvmPackagePartProto;", "partInternalName", "getStableMultifileFacadeParts", "facadeInternalName", "isJavaClassAlreadyInCache", "", "classId", "isJavaClassToTrack", "isMultifileFacade", "isTrackedFile", "file", "saveClassToCache", "kotlinClassInfo", "Lorg/jetbrains/kotlin/incremental/KotlinClassInfo;", "sourceFiles", "", "saveFileToCache", "generatedClass", "Lorg/jetbrains/kotlin/build/GeneratedJvmClass;", "saveFrontendClassToCache", "saveJavaClassProto", FirebaseAnalytics.Param.SOURCE, "serializedJavaClass", "Lorg/jetbrains/kotlin/incremental/SerializedJavaClass;", "collector", "saveModuleMappingToCache", "sourceInCache", "sourcesByInternalName", "internalName", "scopeFqName", "Lorg/jetbrains/kotlin/name/FqName;", "Companion", "ConstantsMap", "InlineFunctionsMap", "InternalNameToSourcesMap", "JavaSourcesProtoMap", "MultifileClassFacadeMap", "MultifileClassPartMap", "PackagePartMap", "ProtoMap", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IncrementalJvmCache extends AbstractIncrementalCache<JvmClassName> implements IncrementalCache {
    private final ConstantsMap constantsMap;
    private final DirtyClassesJvmNameMap dirtyOutputClassesMap;
    private final ProtoMap feProtoMap;
    private final InlineFunctionsMap inlineFunctionsMap;
    private final InternalNameToSourcesMap internalNameToSource;
    private final JavaSourcesProtoMap javaSourcesProtoMap;
    private final MultifileClassFacadeMap multifileFacadeToParts;

    /* JADX INFO: renamed from: outputDir$delegate, reason: from kotlin metadata */
    private final Lazy outputDir;
    private final PackagePartMap packagePartMap;
    private final MultifileClassPartMap partToMultifileFacade;
    private final ProtoMap protoMap;
    private final SourceToJvmNameMap sourceToClassesMap;

    /* JADX INFO: compiled from: IncrementalJvmCache.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KotlinClassHeader.Kind.values().length];
            try {
                iArr[KotlinClassHeader.Kind.FILE_FACADE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KotlinClassHeader.Kind.MULTIFILE_CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[KotlinClassHeader.Kind.MULTIFILE_CLASS_PART.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[KotlinClassHeader.Kind.CLASS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[KotlinClassHeader.Kind.UNKNOWN.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[KotlinClassHeader.Kind.SYNTHETIC_CLASS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    protected void debugLog(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IncrementalJvmCache(File targetDataRoot, IncrementalCompilationContext icContext, final File file) {
        super(new File(targetDataRoot, IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME), icContext);
        Intrinsics.checkNotNullParameter(targetDataRoot, "targetDataRoot");
        Intrinsics.checkNotNullParameter(icContext, "icContext");
        AbstractIncrementalCache.Companion companion = AbstractIncrementalCache.INSTANCE;
        this.sourceToClassesMap = (SourceToJvmNameMap) registerMap(new SourceToJvmNameMap(getStorageFile(AbstractIncrementalCache.getSOURCE_TO_CLASSES()), icContext));
        AbstractIncrementalCache.Companion companion2 = AbstractIncrementalCache.INSTANCE;
        this.dirtyOutputClassesMap = (DirtyClassesJvmNameMap) registerMap(new DirtyClassesJvmNameMap(getStorageFile(AbstractIncrementalCache.getDIRTY_OUTPUT_CLASSES()), icContext));
        this.protoMap = (ProtoMap) registerMap(new ProtoMap(this, getStorageFile("proto"), icContext));
        this.feProtoMap = (ProtoMap) registerMap(new ProtoMap(this, getStorageFile("fe-proto"), icContext));
        this.constantsMap = (ConstantsMap) registerMap(new ConstantsMap(this, getStorageFile("constants"), icContext));
        this.packagePartMap = (PackagePartMap) registerMap(new PackagePartMap(this, getStorageFile("package-parts"), icContext));
        this.multifileFacadeToParts = (MultifileClassFacadeMap) registerMap(new MultifileClassFacadeMap(this, getStorageFile("multifile-class-facades"), icContext));
        this.partToMultifileFacade = (MultifileClassPartMap) registerMap(new MultifileClassPartMap(this, getStorageFile("multifile-class-parts"), icContext));
        this.inlineFunctionsMap = (InlineFunctionsMap) registerMap(new InlineFunctionsMap(this, getStorageFile("inline-functions"), icContext));
        this.internalNameToSource = (InternalNameToSourcesMap) registerMap(new InternalNameToSourcesMap(this, getStorageFile("internal-name-to-source"), icContext));
        this.javaSourcesProtoMap = (JavaSourcesProtoMap) registerMap(new JavaSourcesProtoMap(this, getStorageFile("java-sources-proto-map"), icContext));
        this.outputDir = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<File>() { // from class: org.jetbrains.kotlin.incremental.IncrementalJvmCache$outputDir$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final File invoke() {
                File file2 = file;
                if (file2 != null) {
                    return file2;
                }
                throw new IllegalArgumentException("Target is expected to have output directory".toString());
            }
        });
    }

    @Override // org.jetbrains.kotlin.incremental.AbstractIncrementalCache
    public AbstractSourceToOutputMap<JvmClassName> getSourceToClassesMap$kotlin_build_common() {
        return this.sourceToClassesMap;
    }

    @Override // org.jetbrains.kotlin.incremental.AbstractIncrementalCache
    public AbstractDirtyClassesMap<JvmClassName> getDirtyOutputClassesMap$kotlin_build_common() {
        return this.dirtyOutputClassesMap;
    }

    private final File getOutputDir() {
        return (File) this.outputDir.getValue();
    }

    public final boolean isTrackedFile(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return getSourceToClassesMap$kotlin_build_common().contains(file);
    }

    public final boolean sourceInCache(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return getSourceToClassesMap$kotlin_build_common().contains(file);
    }

    public final Collection<File> sourcesByInternalName(String internalName) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        return this.internalNameToSource.get(internalName);
    }

    public final Collection<String> getAllPartsOfMultifileFacade(JvmClassName facade) {
        Intrinsics.checkNotNullParameter(facade, "facade");
        return this.multifileFacadeToParts.get(facade);
    }

    public final boolean isMultifileFacade(JvmClassName className) {
        Intrinsics.checkNotNullParameter(className, "className");
        return this.multifileFacadeToParts.contains(className);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache
    public String getClassFilePath(String internalClassName) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        String systemIndependentName = FileUtil.toSystemIndependentName(FilesKt.normalize(new File(getOutputDir(), internalClassName + ".class")).getAbsolutePath());
        Intrinsics.checkNotNullExpressionValue(systemIndependentName, "toSystemIndependentName(...)");
        return systemIndependentName;
    }

    public final void saveModuleMappingToCache(Collection<? extends File> sourceFiles, File file) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(file, "file");
        JvmClassName jvmClassNameByInternalName = JvmClassName.byInternalName(".kotlin_module");
        Intrinsics.checkNotNullExpressionValue(jvmClassNameByInternalName, "byInternalName(...)");
        this.protoMap.storeModuleMapping(jvmClassNameByInternalName, FilesKt.readBytes(file));
        getDirtyOutputClassesMap$kotlin_build_common().notDirty(jvmClassNameByInternalName);
        Iterator<T> it = sourceFiles.iterator();
        while (it.hasNext()) {
            getSourceToClassesMap$kotlin_build_common().add((File) it.next(), jvmClassNameByInternalName);
        }
    }

    public void saveFileToCache(GeneratedJvmClass generatedClass, ChangesCollector changesCollector) {
        Intrinsics.checkNotNullParameter(generatedClass, "generatedClass");
        Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
        saveClassToCache(KotlinClassInfo.INSTANCE.createFrom(generatedClass.getOutputClass()), generatedClass.getSourceFiles(), changesCollector);
    }

    public final void saveClassToCache(KotlinClassInfo kotlinClassInfo, List<? extends File> sourceFiles, ChangesCollector changesCollector) {
        Intrinsics.checkNotNullParameter(kotlinClassInfo, "kotlinClassInfo");
        Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
        JvmClassName className = kotlinClassInfo.getClassName();
        getDirtyOutputClassesMap$kotlin_build_common().notDirty(className);
        if (sourceFiles != null) {
            Iterator<T> it = sourceFiles.iterator();
            while (it.hasNext()) {
                getSourceToClassesMap$kotlin_build_common().add((File) it.next(), className);
            }
            InternalNameToSourcesMap internalNameToSourcesMap = this.internalNameToSource;
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            internalNameToSourcesMap.set(internalName, sourceFiles);
        }
        if (kotlinClassInfo.getClassId().isLocal()) {
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[kotlinClassInfo.getClassKind().ordinal()];
        if (i == 1) {
            if (sourceFiles != null) {
                sourceFiles.size();
            }
            this.packagePartMap.addPackagePart(className);
            this.protoMap.process(kotlinClassInfo, changesCollector);
            this.constantsMap.process(kotlinClassInfo, changesCollector);
            this.inlineFunctionsMap.process(kotlinClassInfo, changesCollector);
            return;
        }
        if (i != 2) {
            if (i != 3) {
                if (i != 4) {
                    return;
                }
                ProtoData protoData = kotlinClassInfo.getProtoData();
                Intrinsics.checkNotNull(protoData, "null cannot be cast to non-null type org.jetbrains.kotlin.incremental.ClassProtoData");
                addToClassStorage((ClassProtoData) protoData, sourceFiles != null ? (File) CollectionsKt.single((List) sourceFiles) : null);
                this.protoMap.process(kotlinClassInfo, changesCollector);
                this.constantsMap.process(kotlinClassInfo, changesCollector);
                this.inlineFunctionsMap.process(kotlinClassInfo, changesCollector);
                return;
            }
            if (sourceFiles != null) {
                sourceFiles.size();
            }
            this.packagePartMap.addPackagePart(className);
            MultifileClassPartMap multifileClassPartMap = this.partToMultifileFacade;
            String internalName2 = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName2, "getInternalName(...)");
            String multifileClassName = kotlinClassInfo.getMultifileClassName();
            Intrinsics.checkNotNull(multifileClassName);
            multifileClassPartMap.set(internalName2, multifileClassName);
            this.protoMap.process(kotlinClassInfo, changesCollector);
            this.constantsMap.process(kotlinClassInfo, changesCollector);
            this.inlineFunctionsMap.process(kotlinClassInfo, changesCollector);
            return;
        }
        List list = ArraysKt.toList(kotlinClassInfo.getClassHeaderData());
        if (list.isEmpty()) {
            throw new IllegalStateException(("Multifile class has no parts: " + className).toString());
        }
        this.multifileFacadeToParts.set(className, list);
        if (this.protoMap.contains(className)) {
            FqName fqNameForClassNameWithoutDollars = className.getFqNameForClassNameWithoutDollars();
            Intrinsics.checkNotNullExpressionValue(fqNameForClassNameWithoutDollars, "getFqNameForClassNameWithoutDollars(...)");
            changesCollector.collectSignature(fqNameForClassNameWithoutDollars, true);
        }
        this.protoMap.remove(className, changesCollector);
        AbstractIncrementalCache.ClassFqNameToSourceMap classFqNameToSourceMap = getClassFqNameToSourceMap();
        FqName fqNameForClassNameWithoutDollars2 = className.getFqNameForClassNameWithoutDollars();
        Intrinsics.checkNotNullExpressionValue(fqNameForClassNameWithoutDollars2, "getFqNameForClassNameWithoutDollars(...)");
        classFqNameToSourceMap.remove(fqNameForClassNameWithoutDollars2);
        ClassAttributesMap classAttributesMap$kotlin_build_common = getClassAttributesMap();
        FqName fqNameForClassNameWithoutDollars3 = className.getFqNameForClassNameWithoutDollars();
        Intrinsics.checkNotNullExpressionValue(fqNameForClassNameWithoutDollars3, "getFqNameForClassNameWithoutDollars(...)");
        classAttributesMap$kotlin_build_common.remove(fqNameForClassNameWithoutDollars3);
        InternalNameToSourcesMap internalNameToSourcesMap2 = this.internalNameToSource;
        String internalName3 = className.getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName3, "getInternalName(...)");
        internalNameToSourcesMap2.remove(internalName3);
        this.constantsMap.process(kotlinClassInfo, changesCollector);
        this.inlineFunctionsMap.process(kotlinClassInfo, changesCollector);
    }

    public final void saveFrontendClassToCache(ClassId classId, ProtoBuf.Class classProto, JvmStringTable stringTable, List<? extends File> sourceFiles, ChangesCollector changesCollector) throws IOException {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        Intrinsics.checkNotNullParameter(stringTable, "stringTable");
        Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
        JvmClassName jvmClassNameByClassId = JvmClassName.byClassId(classId);
        Intrinsics.checkNotNullExpressionValue(jvmClassNameByClassId, "byClassId(...)");
        if (sourceFiles != null) {
            InternalNameToSourcesMap internalNameToSourcesMap = this.internalNameToSource;
            String internalName = jvmClassNameByClassId.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            internalNameToSourcesMap.set(internalName, sourceFiles);
        }
        if (classId.isLocal()) {
            return;
        }
        ClassProtoData classProtoData = new ClassProtoData(classProto, stringTable.toNameResolver());
        addToClassStorage(classProtoData, sourceFiles != null ? (File) CollectionsKt.single((List) sourceFiles) : null);
        ProtoMap protoMap = this.feProtoMap;
        byte[] bArrWriteDataBytes = JvmProtoBufUtil.writeDataBytes(stringTable, classProto);
        Intrinsics.checkNotNullExpressionValue(bArrWriteDataBytes, "writeDataBytes(...)");
        protoMap.putAndCollect(jvmClassNameByClassId, new ProtoMapValue(false, bArrWriteDataBytes, (String[]) stringTable.getStrings().toArray(new String[0])), classProtoData, changesCollector);
    }

    public final void collectClassChangesByFeMetadata(JvmClassName className, ProtoBuf.Class classProto, JvmStringTable stringTable, ChangesCollector changesCollector) {
        Intrinsics.checkNotNullParameter(className, "className");
        Intrinsics.checkNotNullParameter(classProto, "classProto");
        Intrinsics.checkNotNullParameter(stringTable, "stringTable");
        Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
        this.feProtoMap.check(className, classProto, stringTable, changesCollector);
    }

    public final void saveJavaClassProto(File source, SerializedJavaClass serializedJavaClass, ChangesCollector collector) {
        Intrinsics.checkNotNullParameter(serializedJavaClass, "serializedJavaClass");
        Intrinsics.checkNotNullParameter(collector, "collector");
        JvmClassName jvmClassNameByClassId = JvmClassName.byClassId(serializedJavaClass.getClassId());
        Intrinsics.checkNotNullExpressionValue(jvmClassNameByClassId, "byClassId(...)");
        this.javaSourcesProtoMap.process(jvmClassNameByClassId, serializedJavaClass, collector);
        if (source != null) {
            getSourceToClassesMap$kotlin_build_common().add(source, jvmClassNameByClassId);
        }
        addToClassStorage(JavaClassesTrackerImplKt.toProtoData(serializedJavaClass), source);
        getDirtyOutputClassesMap$kotlin_build_common().notDirty(jvmClassNameByClassId);
    }

    public final Collection<ClassId> getObsoleteJavaClasses() {
        Collection<JvmClassName> dirtyOutputClasses = getDirtyOutputClassesMap$kotlin_build_common().getDirtyOutputClasses();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = dirtyOutputClasses.iterator();
        while (it.hasNext()) {
            SerializedJavaClass serializedJavaClass = this.javaSourcesProtoMap.get((JvmClassName) it.next());
            ClassId classId = serializedJavaClass != null ? serializedJavaClass.getClassId() : null;
            if (classId != null) {
                arrayList.add(classId);
            }
        }
        return arrayList;
    }

    public final boolean isJavaClassToTrack(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        JvmClassName jvmClassNameByClassId = JvmClassName.byClassId(classId);
        Intrinsics.checkNotNullExpressionValue(jvmClassNameByClassId, "byClassId(...)");
        return getDirtyOutputClassesMap$kotlin_build_common().isDirty(jvmClassNameByClassId) || !this.javaSourcesProtoMap.contains(jvmClassNameByClassId);
    }

    public final boolean isJavaClassAlreadyInCache(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        JvmClassName jvmClassNameByClassId = JvmClassName.byClassId(classId);
        Intrinsics.checkNotNullExpressionValue(jvmClassNameByClassId, "byClassId(...)");
        return this.javaSourcesProtoMap.contains(jvmClassNameByClassId);
    }

    @Override // org.jetbrains.kotlin.incremental.IncrementalCacheCommon
    public void clearCacheForRemovedClasses(ChangesCollector changesCollector) {
        Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
        Collection<JvmClassName> dirtyOutputClasses = getDirtyOutputClassesMap$kotlin_build_common().getDirtyOutputClasses();
        HashMap map = new HashMap();
        for (JvmClassName jvmClassName : dirtyOutputClasses) {
            String str = this.partToMultifileFacade.get(jvmClassName);
            if (str != null) {
                JvmClassName jvmClassNameByInternalName = JvmClassName.byInternalName(str);
                Intrinsics.checkNotNullExpressionValue(jvmClassNameByInternalName, "byInternalName(...)");
                HashMap map2 = map;
                Object obj = map2.get(jvmClassNameByInternalName);
                if (obj == null) {
                    obj = (Set) new HashSet();
                    map2.put(jvmClassNameByInternalName, obj);
                }
                String internalName = jvmClassName.getInternalName();
                Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
                ((Set) obj).add(internalName);
            }
        }
        for (Map.Entry entry : map.entrySet()) {
            Intrinsics.checkNotNull(entry);
            JvmClassName jvmClassName2 = (JvmClassName) entry.getKey();
            Set set = (Set) entry.getValue();
            MultifileClassFacadeMap multifileClassFacadeMap = this.multifileFacadeToParts;
            Intrinsics.checkNotNull(jvmClassName2);
            Collection<String> collection = multifileClassFacadeMap.get(jvmClassName2);
            if (collection != null) {
                ArrayList arrayList = new ArrayList();
                for (Object obj2 : collection) {
                    if (!set.contains((String) obj2)) {
                        arrayList.add(obj2);
                    }
                }
                ArrayList arrayList2 = arrayList;
                if (arrayList2.isEmpty()) {
                    this.multifileFacadeToParts.remove(jvmClassName2);
                } else {
                    this.multifileFacadeToParts.set(jvmClassName2, arrayList2);
                }
            }
        }
        Collection<JvmClassName> collection2 = dirtyOutputClasses;
        for (JvmClassName jvmClassName3 : collection2) {
            this.protoMap.remove(jvmClassName3, changesCollector);
            this.feProtoMap.remove(jvmClassName3, changesCollector);
            this.packagePartMap.remove(jvmClassName3);
            this.multifileFacadeToParts.remove(jvmClassName3);
            this.partToMultifileFacade.remove(jvmClassName3);
            this.constantsMap.remove(jvmClassName3);
            this.inlineFunctionsMap.remove(jvmClassName3);
            InternalNameToSourcesMap internalNameToSourcesMap = this.internalNameToSource;
            String internalName2 = jvmClassName3.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName2, "getInternalName(...)");
            internalNameToSourcesMap.remove(internalName2);
            this.javaSourcesProtoMap.remove(jvmClassName3, changesCollector);
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            FqName fqNameForClassNameWithoutDollars = ((JvmClassName) it.next()).getFqNameForClassNameWithoutDollars();
            Intrinsics.checkNotNullExpressionValue(fqNameForClassNameWithoutDollars, "getFqNameForClassNameWithoutDollars(...)");
            arrayList3.add(fqNameForClassNameWithoutDollars);
        }
        removeAllFromClassStorage(arrayList3, changesCollector);
        getDirtyOutputClassesMap$kotlin_build_common().clean();
    }

    @Override // org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache
    public Collection<String> getObsoletePackageParts() {
        Collection<JvmClassName> dirtyOutputClasses = getDirtyOutputClassesMap$kotlin_build_common().getDirtyOutputClasses();
        PackagePartMap packagePartMap = this.packagePartMap;
        ArrayList arrayList = new ArrayList();
        for (Object obj : dirtyOutputClasses) {
            if (packagePartMap.isPackagePart((JvmClassName) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        debugLog("Obsolete package parts: " + arrayList2);
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it = arrayList3.iterator();
        while (it.hasNext()) {
            arrayList4.add(((JvmClassName) it.next()).getInternalName());
        }
        return arrayList4;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache
    public JvmPackagePartProto getPackagePartData(String partInternalName) {
        Intrinsics.checkNotNullParameter(partInternalName, "partInternalName");
        ProtoMap protoMap = this.protoMap;
        JvmClassName jvmClassNameByInternalName = JvmClassName.byInternalName(partInternalName);
        Intrinsics.checkNotNullExpressionValue(jvmClassNameByInternalName, "byInternalName(...)");
        ProtoMapValue protoMapValue = protoMap.get(jvmClassNameByInternalName);
        if (protoMapValue != null) {
            return new JvmPackagePartProto(protoMapValue.getBytes(), protoMapValue.getStrings());
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache
    public Collection<String> getObsoleteMultifileClasses() {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<JvmClassName> it = getDirtyOutputClassesMap$kotlin_build_common().getDirtyOutputClasses().iterator();
        while (it.hasNext()) {
            String str = this.partToMultifileFacade.get(it.next());
            if (str != null) {
                linkedHashSet.add(str);
            }
        }
        debugLog("Obsolete multifile class facades: " + linkedHashSet);
        return linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache
    public Collection<String> getStableMultifileFacadeParts(String facadeInternalName) {
        Intrinsics.checkNotNullParameter(facadeInternalName, "facadeInternalName");
        JvmClassName jvmClassNameByInternalName = JvmClassName.byInternalName(facadeInternalName);
        Intrinsics.checkNotNullExpressionValue(jvmClassNameByInternalName, "byInternalName(...)");
        Collection<String> collection = this.multifileFacadeToParts.get(jvmClassNameByInternalName);
        if (collection == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            AbstractDirtyClassesMap<JvmClassName> dirtyOutputClassesMap$kotlin_build_common = getDirtyOutputClassesMap$kotlin_build_common();
            JvmClassName jvmClassNameByInternalName2 = JvmClassName.byInternalName((String) obj);
            Intrinsics.checkNotNullExpressionValue(jvmClassNameByInternalName2, "byInternalName(...)");
            if (!dirtyOutputClassesMap$kotlin_build_common.isDirty(jvmClassNameByInternalName2)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.incremental.components.IncrementalCache
    public byte[] getModuleMappingData() {
        ProtoMap protoMap = this.protoMap;
        JvmClassName jvmClassNameByInternalName = JvmClassName.byInternalName(".kotlin_module");
        Intrinsics.checkNotNullExpressionValue(jvmClassNameByInternalName, "byInternalName(...)");
        ProtoMapValue protoMapValue = protoMap.get(jvmClassNameByInternalName);
        if (protoMapValue != null) {
            return protoMapValue.getBytes();
        }
        return null;
    }

    /* JADX INFO: compiled from: IncrementalJvmCache.kt */
    @Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0000\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J&\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0011\u0010\u0012\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000bH\u0086\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0002H\u0014J\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0086\u0002J\u0016\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00020\u0011J&\u0010\u001b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u001f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010 \u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\"¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$ProtoMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "Lorg/jetbrains/kotlin/incremental/storage/ProtoMapValue;", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache;Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "check", "", "className", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "classProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "stringTable", "Lorg/jetbrains/kotlin/metadata/jvm/serialization/JvmStringTable;", "changesCollector", "Lorg/jetbrains/kotlin/incremental/ChangesCollector;", "contains", "", "dumpValue", "", "value", "get", "process", "kotlinClassInfo", "Lorg/jetbrains/kotlin/incremental/KotlinClassInfo;", "putAndCollect", "newMapValue", "newProtoData", "Lorg/jetbrains/kotlin/incremental/ProtoData;", ProductAction.ACTION_REMOVE, "storeModuleMapping", "bytes", "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class ProtoMap extends BasicStringMap<ProtoMapValue> {
        final /* synthetic */ IncrementalJvmCache this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ProtoMap(IncrementalJvmCache incrementalJvmCache, File storageFile, IncrementalCompilationContext icContext) {
            super(storageFile, ProtoMapValueExternalizer.INSTANCE, icContext);
            Intrinsics.checkNotNullParameter(storageFile, "storageFile");
            Intrinsics.checkNotNullParameter(icContext, "icContext");
            this.this$0 = incrementalJvmCache;
        }

        public final synchronized void process(KotlinClassInfo kotlinClassInfo, ChangesCollector changesCollector) {
            Intrinsics.checkNotNullParameter(kotlinClassInfo, "kotlinClassInfo");
            Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
            putAndCollect(kotlinClassInfo.getClassName(), kotlinClassInfo.getProtoMapValue(), kotlinClassInfo.getProtoData(), changesCollector);
        }

        public final synchronized void storeModuleMapping(JvmClassName className, byte[] bytes) {
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(bytes, "bytes");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            storage.set(internalName, new ProtoMapValue(false, bytes, new String[0]));
        }

        public final synchronized void putAndCollect(JvmClassName className, ProtoMapValue newMapValue, ProtoData newProtoData, ChangesCollector changesCollector) {
            ProtoData protoData;
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(newMapValue, "newMapValue");
            Intrinsics.checkNotNullParameter(newProtoData, "newProtoData");
            Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            ProtoMapValue protoMapValue = (ProtoMapValue) getStorage().get(internalName);
            getStorage().set(internalName, newMapValue);
            if (protoMapValue != null) {
                FqName packageFqName = className.getPackageFqName();
                Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                protoData = ProtoDifferenceUtilsKt.toProtoData(protoMapValue, packageFqName);
            } else {
                protoData = null;
            }
            ChangesCollector.collectProtoChanges$default(changesCollector, protoData, newProtoData, false, internalName, 4, null);
        }

        public final void check(JvmClassName className, ProtoBuf.Class classProto, JvmStringTable stringTable, ChangesCollector changesCollector) {
            ProtoData protoData;
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(classProto, "classProto");
            Intrinsics.checkNotNullParameter(stringTable, "stringTable");
            Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            ProtoMapValue protoMapValue = (ProtoMapValue) getStorage().get(internalName);
            if (protoMapValue != null) {
                FqName packageFqName = className.getPackageFqName();
                Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                protoData = ProtoDifferenceUtilsKt.toProtoData(protoMapValue, packageFqName);
            } else {
                protoData = null;
            }
            ChangesCollector.collectProtoChanges$default(changesCollector, protoData, new ClassProtoData(classProto, stringTable.toNameResolver()), false, internalName, 4, null);
        }

        public final boolean contains(JvmClassName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            return storage.contains(internalName);
        }

        public final ProtoMapValue get(JvmClassName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            return (ProtoMapValue) storage.get(internalName);
        }

        public final synchronized void remove(JvmClassName className, ChangesCollector changesCollector) {
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            ProtoMapValue protoMapValue = (ProtoMapValue) getStorage().get(internalName);
            if (protoMapValue == null) {
                return;
            }
            if (!Intrinsics.areEqual(internalName, ".kotlin_module")) {
                FqName packageFqName = className.getPackageFqName();
                Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                ChangesCollector.collectProtoChanges$default(changesCollector, ProtoDifferenceUtilsKt.toProtoData(protoMapValue, packageFqName), null, false, null, 12, null);
            }
            getStorage().remove(internalName);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
        public String dumpValue(ProtoMapValue value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return (value.isPackageFacade() ? SchemaSymbols.ATTVAL_TRUE_1 : "0") + Long.toHexString(IncrementalJvmCacheKt.md5(value.getBytes()));
        }
    }

    /* JADX INFO: compiled from: IncrementalJvmCache.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0011\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0086\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0002H\u0014J\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0086\u0002J\u001e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u0016\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u0015¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$JavaSourcesProtoMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "Lorg/jetbrains/kotlin/incremental/SerializedJavaClass;", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache;Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "contains", "", "className", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "dumpValue", "", "value", "get", "process", "", "jvmClassName", "newData", "changesCollector", "Lorg/jetbrains/kotlin/incremental/ChangesCollector;", ProductAction.ACTION_REMOVE, "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class JavaSourcesProtoMap extends BasicStringMap<SerializedJavaClass> {
        final /* synthetic */ IncrementalJvmCache this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public JavaSourcesProtoMap(IncrementalJvmCache incrementalJvmCache, File storageFile, IncrementalCompilationContext icContext) {
            super(storageFile, JavaClassProtoMapValueExternalizer.INSTANCE, icContext);
            Intrinsics.checkNotNullParameter(storageFile, "storageFile");
            Intrinsics.checkNotNullParameter(icContext, "icContext");
            this.this$0 = incrementalJvmCache;
        }

        public final synchronized void process(JvmClassName jvmClassName, SerializedJavaClass newData, ChangesCollector changesCollector) {
            Intrinsics.checkNotNullParameter(jvmClassName, "jvmClassName");
            Intrinsics.checkNotNullParameter(newData, "newData");
            Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
            String internalName = jvmClassName.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            SerializedJavaClass serializedJavaClass = (SerializedJavaClass) getStorage().get(internalName);
            getStorage().set(internalName, newData);
            ChangesCollector.collectProtoChanges$default(changesCollector, serializedJavaClass != null ? JavaClassesTrackerImplKt.toProtoData(serializedJavaClass) : null, JavaClassesTrackerImplKt.toProtoData(newData), true, null, 8, null);
        }

        public final synchronized void remove(JvmClassName className, ChangesCollector changesCollector) {
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            SerializedJavaClass serializedJavaClass = (SerializedJavaClass) getStorage().get(internalName);
            if (serializedJavaClass == null) {
                return;
            }
            getStorage().remove(internalName);
            ChangesCollector.collectProtoChanges$default(changesCollector, JavaClassesTrackerImplKt.toProtoData(serializedJavaClass), null, false, null, 12, null);
        }

        public final SerializedJavaClass get(JvmClassName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            return (SerializedJavaClass) storage.get(internalName);
        }

        public final boolean contains(JvmClassName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            return storage.contains(internalName);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
        public String dumpValue(SerializedJavaClass value) {
            Intrinsics.checkNotNullParameter(value, "value");
            byte[] byteArray = value.getProto().toByteArray();
            Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
            String hexString = Long.toHexString(IncrementalJvmCacheKt.md5(byteArray));
            Intrinsics.checkNotNullExpressionValue(hexString, "toHexString(...)");
            return hexString;
        }
    }

    /* JADX INFO: compiled from: IncrementalJvmCache.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00020\u0001B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0011\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0086\u0002J\u001c\u0010\u000e\u001a\u00020\u00032\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002H\u0014J\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\f\u001a\u00020\r¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$ConstantsMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "", "", "", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache;Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "contains", "", "className", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "dumpValue", "value", "process", "", "kotlinClassInfo", "Lorg/jetbrains/kotlin/incremental/KotlinClassInfo;", "changesCollector", "Lorg/jetbrains/kotlin/incremental/ChangesCollector;", ProductAction.ACTION_REMOVE, "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class ConstantsMap extends BasicStringMap<Map<String, ? extends Long>> {
        final /* synthetic */ IncrementalJvmCache this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ConstantsMap(IncrementalJvmCache incrementalJvmCache, File storageFile, IncrementalCompilationContext icContext) {
            super(storageFile, new MapExternalizer(StringExternalizer.INSTANCE, LongExternalizer.INSTANCE, null, 4, null), icContext);
            Intrinsics.checkNotNullParameter(storageFile, "storageFile");
            Intrinsics.checkNotNullParameter(icContext, "icContext");
            this.this$0 = incrementalJvmCache;
        }

        public final boolean contains(JvmClassName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            return storage.contains(internalName);
        }

        public final synchronized void process(KotlinClassInfo kotlinClassInfo, ChangesCollector changesCollector) {
            Intrinsics.checkNotNullParameter(kotlinClassInfo, "kotlinClassInfo");
            Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
            String internalName = kotlinClassInfo.getClassName().getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            Map mapEmptyMap = (Map) getStorage().get(internalName);
            if (mapEmptyMap == null) {
                mapEmptyMap = MapsKt.emptyMap();
            }
            Map<String, Long> constantSnapshots = kotlinClassInfo.getExtraInfo().getConstantSnapshots();
            if (!constantSnapshots.isEmpty()) {
                getStorage().set(internalName, constantSnapshots);
            } else {
                getStorage().remove(internalName);
            }
            Set<String> setPlus = SetsKt.plus(mapEmptyMap.keySet(), (Iterable) constantSnapshots.keySet());
            if (setPlus.isEmpty()) {
                return;
            }
            FqName fqNameScopeFqName = this.this$0.scopeFqName(kotlinClassInfo);
            for (String str : setPlus) {
                changesCollector.collectMemberIfValueWasChanged(fqNameScopeFqName, str, mapEmptyMap.get(str), constantSnapshots.get(str));
            }
            ClassId companionObject = kotlinClassInfo.getCompanionObject();
            if (companionObject != null) {
                FqName fqNameAsSingleFqName = companionObject.asSingleFqName();
                Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
                for (String str2 : setPlus) {
                    changesCollector.collectMemberIfValueWasChanged(fqNameAsSingleFqName, str2, mapEmptyMap.get(str2), constantSnapshots.get(str2));
                }
            }
        }

        public final synchronized void remove(JvmClassName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            storage.remove(internalName);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
        public String dumpValue(Map<String, Long> value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return IncrementalJvmCacheKt.dumpMap(value, IncrementalJvmCache$ConstantsMap$dumpValue$1.INSTANCE);
        }
    }

    /* JADX INFO: compiled from: IncrementalJvmCache.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0002H\u0014J\u000e\u0010\u000f\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$PackagePartMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache;Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "addPackagePart", "", "className", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "dumpValue", "", "value", "isPackagePart", ProductAction.ACTION_REMOVE, "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class PackagePartMap extends BasicStringMap<Boolean> {
        final /* synthetic */ IncrementalJvmCache this$0;

        /* JADX WARN: Illegal instructions before constructor call */
        public PackagePartMap(IncrementalJvmCache incrementalJvmCache, File storageFile, IncrementalCompilationContext icContext) {
            Intrinsics.checkNotNullParameter(storageFile, "storageFile");
            Intrinsics.checkNotNullParameter(icContext, "icContext");
            this.this$0 = incrementalJvmCache;
            BooleanDataDescriptor INSTANCE = BooleanDataDescriptor.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(INSTANCE, "INSTANCE");
            super(storageFile, INSTANCE, icContext);
        }

        @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
        public /* bridge */ /* synthetic */ String dumpValue(Object obj) {
            return dumpValue(((Boolean) obj).booleanValue());
        }

        public final void addPackagePart(JvmClassName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            storage.set(internalName, true);
        }

        public final void remove(JvmClassName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            storage.remove(internalName);
        }

        public final boolean isPackagePart(JvmClassName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            return storage.contains(internalName);
        }

        protected String dumpValue(boolean value) {
            return "";
        }
    }

    /* JADX INFO: compiled from: IncrementalJvmCache.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086\u0002J\u0016\u0010\r\u001a\u00020\u00032\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0014J\u0019\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00022\u0006\u0010\u000b\u001a\u00020\fH\u0086\u0002J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\fJ\u001f\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0086\u0002¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$MultifileClassFacadeMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "", "", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache;Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "contains", "", "className", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "dumpValue", "value", "get", ProductAction.ACTION_REMOVE, "", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "partNames", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class MultifileClassFacadeMap extends BasicStringMap<Collection<? extends String>> {
        final /* synthetic */ IncrementalJvmCache this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MultifileClassFacadeMap(IncrementalJvmCache incrementalJvmCache, File storageFile, IncrementalCompilationContext icContext) {
            super(storageFile, StringCollectionExternalizer.INSTANCE, icContext);
            Intrinsics.checkNotNullParameter(storageFile, "storageFile");
            Intrinsics.checkNotNullParameter(icContext, "icContext");
            this.this$0 = incrementalJvmCache;
        }

        public final synchronized void set(JvmClassName className, Collection<String> partNames) {
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(partNames, "partNames");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            storage.set(internalName, partNames);
        }

        public final Collection<String> get(JvmClassName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            return (Collection) storage.get(internalName);
        }

        public final boolean contains(JvmClassName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            return storage.contains(internalName);
        }

        public final synchronized void remove(JvmClassName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            storage.remove(internalName);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
        public String dumpValue(Collection<String> value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return IncrementalJvmCacheKt.dumpCollection(value);
        }
    }

    /* JADX INFO: compiled from: IncrementalJvmCache.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u0002H\u0014J\u0010\u0010\n\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\fJ\u0016\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$MultifileClassPartMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache;Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "dumpValue", "value", "get", "partName", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", ProductAction.ACTION_REMOVE, "", "className", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "facadeName", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class MultifileClassPartMap extends BasicStringMap<String> {
        final /* synthetic */ IncrementalJvmCache this$0;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
        public String dumpValue(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return value;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public MultifileClassPartMap(IncrementalJvmCache incrementalJvmCache, File storageFile, IncrementalCompilationContext icContext) {
            Intrinsics.checkNotNullParameter(storageFile, "storageFile");
            Intrinsics.checkNotNullParameter(icContext, "icContext");
            this.this$0 = incrementalJvmCache;
            EnumeratorStringDescriptor INSTANCE = EnumeratorStringDescriptor.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(INSTANCE, "INSTANCE");
            super(storageFile, INSTANCE, icContext);
        }

        public final synchronized void set(String partName, String facadeName) {
            Intrinsics.checkNotNullParameter(partName, "partName");
            Intrinsics.checkNotNullParameter(facadeName, "facadeName");
            getStorage().set(partName, facadeName);
        }

        public final String get(JvmClassName partName) {
            Intrinsics.checkNotNullParameter(partName, "partName");
            LazyStorage<K, V> storage = getStorage();
            String internalName = partName.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            return (String) storage.get(internalName);
        }

        public final synchronized void remove(JvmClassName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            storage.remove(internalName);
        }
    }

    /* JADX INFO: compiled from: IncrementalJvmCache.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0014J\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u00022\u0006\u0010\f\u001a\u00020\u0003H\u0086\u0002J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u0003J\u001f\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\u00032\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0002H\u0086\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$InternalNameToSourcesMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "", "", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache;Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "dumpValue", "value", "get", "internalName", ProductAction.ACTION_REMOVE, "", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "sourceFiles", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class InternalNameToSourcesMap extends BasicStringMap<Collection<? extends String>> {
        final /* synthetic */ IncrementalJvmCache this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public InternalNameToSourcesMap(IncrementalJvmCache incrementalJvmCache, File storageFile, IncrementalCompilationContext icContext) {
            super(storageFile, new EnumeratorStringDescriptor(), PathCollectionExternalizer.INSTANCE, icContext);
            Intrinsics.checkNotNullParameter(storageFile, "storageFile");
            Intrinsics.checkNotNullParameter(icContext, "icContext");
            this.this$0 = incrementalJvmCache;
        }

        public final void set(String internalName, Collection<? extends File> sourceFiles) {
            Intrinsics.checkNotNullParameter(internalName, "internalName");
            Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
            getStorage().set(internalName, FileToPathConverterKt.toPaths(getPathConverter(), sourceFiles));
        }

        public final Collection<File> get(String internalName) {
            Intrinsics.checkNotNullParameter(internalName, "internalName");
            FileToPathConverter pathConverter = getPathConverter();
            List listEmptyList = (Collection) getStorage().get(internalName);
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            return FileToPathConverterKt.toFiles(pathConverter, listEmptyList);
        }

        public final void remove(String internalName) {
            Intrinsics.checkNotNullParameter(internalName, "internalName");
            getStorage().remove(internalName);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
        public String dumpValue(Collection<String> value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return IncrementalJvmCacheKt.dumpCollection(value);
        }
    }

    /* JADX INFO: compiled from: IncrementalJvmCache.kt */
    @Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00020\u0001B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u001c\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002H\u0014J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache$InlineFunctionsMap;", "Lorg/jetbrains/kotlin/incremental/storage/BasicStringMap;", "", "Lorg/jetbrains/kotlin/inline/InlineFunctionOrAccessor;", "", "storageFile", "Ljava/io/File;", "icContext", "Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;", "(Lorg/jetbrains/kotlin/incremental/IncrementalJvmCache;Ljava/io/File;Lorg/jetbrains/kotlin/incremental/IncrementalCompilationContext;)V", "dumpValue", "", "value", "process", "", "kotlinClassInfo", "Lorg/jetbrains/kotlin/incremental/KotlinClassInfo;", "changesCollector", "Lorg/jetbrains/kotlin/incremental/ChangesCollector;", ProductAction.ACTION_REMOVE, "className", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class InlineFunctionsMap extends BasicStringMap<Map<InlineFunctionOrAccessor, ? extends Long>> {
        final /* synthetic */ IncrementalJvmCache this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public InlineFunctionsMap(IncrementalJvmCache incrementalJvmCache, File storageFile, IncrementalCompilationContext icContext) {
            super(storageFile, new MapExternalizer(InlineFunctionOrAccessorExternalizer.INSTANCE, LongExternalizer.INSTANCE, null, 4, null), icContext);
            Intrinsics.checkNotNullParameter(storageFile, "storageFile");
            Intrinsics.checkNotNullParameter(icContext, "icContext");
            this.this$0 = incrementalJvmCache;
        }

        public final synchronized void process(KotlinClassInfo kotlinClassInfo, ChangesCollector changesCollector) {
            String propertyName;
            Intrinsics.checkNotNullParameter(kotlinClassInfo, "kotlinClassInfo");
            Intrinsics.checkNotNullParameter(changesCollector, "changesCollector");
            String internalName = kotlinClassInfo.getClassName().getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            Map mapEmptyMap = (Map) getStorage().get(internalName);
            if (mapEmptyMap == null) {
                mapEmptyMap = MapsKt.emptyMap();
            }
            Map<InlineFunctionOrAccessor, Long> inlineFunctionOrAccessorSnapshots = kotlinClassInfo.getExtraInfo().getInlineFunctionOrAccessorSnapshots();
            if (!inlineFunctionOrAccessorSnapshots.isEmpty()) {
                getStorage().set(internalName, inlineFunctionOrAccessorSnapshots);
            } else {
                getStorage().remove(internalName);
            }
            FqName fqNameScopeFqName = this.this$0.scopeFqName(kotlinClassInfo);
            for (InlineFunctionOrAccessor inlineFunctionOrAccessor : SetsKt.plus(mapEmptyMap.keySet(), (Iterable) inlineFunctionOrAccessorSnapshots.keySet())) {
                if (inlineFunctionOrAccessor instanceof InlineFunction) {
                    propertyName = ((InlineFunction) inlineFunctionOrAccessor).getKotlinFunctionName();
                } else {
                    if (!(inlineFunctionOrAccessor instanceof InlinePropertyAccessor)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    propertyName = ((InlinePropertyAccessor) inlineFunctionOrAccessor).getPropertyName();
                }
                changesCollector.collectMemberIfValueWasChanged(fqNameScopeFqName, propertyName, mapEmptyMap.get(inlineFunctionOrAccessor), inlineFunctionOrAccessorSnapshots.get(inlineFunctionOrAccessor));
            }
        }

        public final synchronized void remove(JvmClassName className) {
            Intrinsics.checkNotNullParameter(className, "className");
            LazyStorage<K, V> storage = getStorage();
            String internalName = className.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            storage.remove(internalName);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.incremental.storage.BasicMap
        public String dumpValue(Map<InlineFunctionOrAccessor, Long> value) {
            Intrinsics.checkNotNullParameter(value, "value");
            LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(value.size()));
            Iterator<T> it = value.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                linkedHashMap.put(((InlineFunctionOrAccessor) entry.getKey()).getJvmMethodSignature().asString(), entry.getValue());
            }
            return IncrementalJvmCacheKt.dumpMap(linkedHashMap, new Function1<Long, String>() { // from class: org.jetbrains.kotlin.incremental.IncrementalJvmCache$InlineFunctionsMap$dumpValue$2
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ String invoke(Long l) {
                    return invoke(l.longValue());
                }

                public final String invoke(long j) {
                    String hexString = Long.toHexString(j);
                    Intrinsics.checkNotNullExpressionValue(hexString, "toHexString(...)");
                    return hexString;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FqName scopeFqName(KotlinClassInfo kotlinClassInfo) {
        FqName packageFqName;
        if (WhenMappings.$EnumSwitchMapping$0[kotlinClassInfo.getClassKind().ordinal()] == 4) {
            packageFqName = kotlinClassInfo.getClassId().asSingleFqName();
        } else {
            packageFqName = kotlinClassInfo.getClassId().getPackageFqName();
        }
        Intrinsics.checkNotNull(packageFqName);
        return packageFqName;
    }

    public final Iterable<JvmClassName> classesBySources(Iterable<? extends File> sources) {
        Intrinsics.checkNotNullParameter(sources, "sources");
        ArrayList arrayList = new ArrayList();
        Iterator<? extends File> it = sources.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, getSourceToClassesMap$kotlin_build_common().get(it.next()));
        }
        return arrayList;
    }
}
