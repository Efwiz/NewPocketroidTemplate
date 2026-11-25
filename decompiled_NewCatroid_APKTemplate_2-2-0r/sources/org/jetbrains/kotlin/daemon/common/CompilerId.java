package org.jetbrains.kotlin.daemon.common;

import com.reandroid.apk.UncompressedFiles;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.text.StringsKt;
import org.bouncycastle.cms.CMSAttributeTableGenerator;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: DaemonParams.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u001f\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0004HÆ\u0003J#\u0010\u0014\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0004HÆ\u0001J\u0006\u0010\u0015\u001a\u00020\u0004J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u0004HÖ\u0001R \u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR&\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00100\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\b¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompilerId;", "Lorg/jetbrains/kotlin/daemon/common/OptionsGroup;", "compilerClasspath", "", "", "compilerVersion", "(Ljava/util/List;Ljava/lang/String;)V", "getCompilerClasspath", "()Ljava/util/List;", "setCompilerClasspath", "(Ljava/util/List;)V", "getCompilerVersion", "()Ljava/lang/String;", "setCompilerVersion", "(Ljava/lang/String;)V", "mappers", "Lorg/jetbrains/kotlin/daemon/common/PropMapper;", "getMappers", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, CMSAttributeTableGenerator.DIGEST, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "Companion", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CompilerId implements OptionsGroup {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private List<String> compilerClasspath;
    private String compilerVersion;

    /* JADX WARN: Multi-variable type inference failed */
    public CompilerId() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CompilerId copy$default(CompilerId compilerId, List list, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            list = compilerId.compilerClasspath;
        }
        if ((i & 2) != 0) {
            str = compilerId.compilerVersion;
        }
        return compilerId.copy(list, str);
    }

    @JvmStatic
    public static final CompilerId makeCompilerId(Iterable<? extends File> iterable) {
        return INSTANCE.makeCompilerId(iterable);
    }

    @JvmStatic
    public static final CompilerId makeCompilerId(File... fileArr) {
        return INSTANCE.makeCompilerId(fileArr);
    }

    public final List<String> component1() {
        return this.compilerClasspath;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getCompilerVersion() {
        return this.compilerVersion;
    }

    public final CompilerId copy(List<String> compilerClasspath, String compilerVersion) {
        Intrinsics.checkNotNullParameter(compilerClasspath, "compilerClasspath");
        Intrinsics.checkNotNullParameter(compilerVersion, "compilerVersion");
        return new CompilerId(compilerClasspath, compilerVersion);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CompilerId)) {
            return false;
        }
        CompilerId compilerId = (CompilerId) other;
        return Intrinsics.areEqual(this.compilerClasspath, compilerId.compilerClasspath) && Intrinsics.areEqual(this.compilerVersion, compilerId.compilerVersion);
    }

    public int hashCode() {
        return (this.compilerClasspath.hashCode() * 31) + this.compilerVersion.hashCode();
    }

    public String toString() {
        return "CompilerId(compilerClasspath=" + this.compilerClasspath + ", compilerVersion=" + this.compilerVersion + ')';
    }

    public CompilerId(List<String> compilerClasspath, String compilerVersion) {
        Intrinsics.checkNotNullParameter(compilerClasspath, "compilerClasspath");
        Intrinsics.checkNotNullParameter(compilerVersion, "compilerVersion");
        this.compilerClasspath = compilerClasspath;
        this.compilerVersion = compilerVersion;
    }

    public /* synthetic */ CompilerId(List list, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CollectionsKt.emptyList() : list, (i & 2) != 0 ? "" : str);
    }

    public final List<String> getCompilerClasspath() {
        return this.compilerClasspath;
    }

    public final void setCompilerClasspath(List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.compilerClasspath = list;
    }

    public final String getCompilerVersion() {
        return this.compilerVersion;
    }

    public final void setCompilerVersion(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.compilerVersion = str;
    }

    @Override // org.jetbrains.kotlin.daemon.common.OptionsGroup
    public List<PropMapper<?, ?, ?>> getMappers() {
        return CollectionsKt.listOf((Object[]) new PropMapper[]{new PropMapper(this, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.CompilerId$mappers$1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getCompilerClasspath()Ljava/util/List;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((CompilerId) obj).getCompilerClasspath();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "compilerClasspath";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(CompilerId.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((CompilerId) obj).setCompilerClasspath((List) obj2);
            }
        }, null, new Function1<String, List<? extends String>>() { // from class: org.jetbrains.kotlin.daemon.common.CompilerId$mappers$2
            @Override // kotlin.jvm.functions.Function1
            public final List<String> invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String strTrimQuotes = DaemonParamsKt.trimQuotes(it);
                String pathSeparator = File.pathSeparator;
                Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
                return StringsKt.split$default((CharSequence) strTrimQuotes, new String[]{pathSeparator}, false, 0, 6, (Object) null);
            }
        }, new Function1<List<? extends String>, String>() { // from class: org.jetbrains.kotlin.daemon.common.CompilerId$mappers$3
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ String invoke(List<? extends String> list) {
                return invoke2((List<String>) list);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final String invoke2(List<String> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                List<String> list = it;
                String pathSeparator = File.pathSeparator;
                Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
                return CollectionsKt.joinToString$default(list, pathSeparator, null, null, 0, null, null, 62, null);
            }
        }, null, null, 100, null), new StringPropMapper(this, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.CompilerId$mappers$4
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getCompilerVersion()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((CompilerId) obj).getCompilerVersion();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "compilerVersion";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(CompilerId.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((CompilerId) obj).setCompilerVersion((String) obj2);
            }
        }, null, null, null, null, null, 124, null)});
    }

    public final String digest() {
        List<String> list = this.compilerClasspath;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new File((String) it.next()).getAbsolutePath());
        }
        return DaemonParamsKt.toHexString(DaemonParamsKt.distinctStringsDigest(arrayList));
    }

    /* JADX INFO: compiled from: DaemonParams.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J!\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006\"\u00020\u0007H\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\tH\u0007¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/CompilerId$Companion;", "", "()V", "makeCompilerId", "Lorg/jetbrains/kotlin/daemon/common/CompilerId;", UncompressedFiles.NAME_paths, "", "Ljava/io/File;", "([Ljava/io/File;)Lorg/jetbrains/kotlin/daemon/common/CompilerId;", "", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final CompilerId makeCompilerId(File... paths) {
            Intrinsics.checkNotNullParameter(paths, "paths");
            return makeCompilerId(ArraysKt.asIterable(paths));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @JvmStatic
        public final CompilerId makeCompilerId(Iterable<? extends File> paths) {
            Intrinsics.checkNotNullParameter(paths, "paths");
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(paths, 10));
            Iterator<? extends File> it = paths.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getAbsolutePath());
            }
            return new CompilerId(arrayList, null, 2, 0 == true ? 1 : 0);
        }
    }
}
