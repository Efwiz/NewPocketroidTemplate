package kotlin.script.experimental.jvm;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ScriptDependency;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmScriptCompilation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u001b\b\u0016\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0013\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007¢\u0006\u0002\u0010\bJ\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007HÆ\u0003J\u0019\u0010\f\u001a\u00020\u00002\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lkotlin/script/experimental/jvm/JvmDependency;", "Lkotlin/script/experimental/api/ScriptDependency;", "classpathEntries", "", "Ljava/io/File;", "([Ljava/io/File;)V", ModuleXmlParser.CLASSPATH, "", "(Ljava/util/List;)V", "getClasspath", "()Ljava/util/List;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "Companion", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class JvmDependency implements ScriptDependency {
    private static final long serialVersionUID = 1;
    private final List<File> classpath;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ JvmDependency copy$default(JvmDependency jvmDependency, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = jvmDependency.classpath;
        }
        return jvmDependency.copy(list);
    }

    public final List<File> component1() {
        return this.classpath;
    }

    public final JvmDependency copy(List<? extends File> classpath) {
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        return new JvmDependency(classpath);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof JvmDependency) && Intrinsics.areEqual(this.classpath, ((JvmDependency) other).classpath);
    }

    public int hashCode() {
        return this.classpath.hashCode();
    }

    public String toString() {
        return "JvmDependency(classpath=" + this.classpath + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public JvmDependency(List<? extends File> classpath) {
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        this.classpath = classpath;
    }

    public final List<File> getClasspath() {
        return this.classpath;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JvmDependency(File... classpathEntries) {
        this((List<? extends File>) ArraysKt.asList(classpathEntries));
        Intrinsics.checkNotNullParameter(classpathEntries, "classpathEntries");
    }
}
