package org.jetbrains.kotlin.modules;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: Module.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007H&J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007H&J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007H&J\u000e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0007H&J\b\u0010\f\u001a\u00020\u0003H&J\b\u0010\r\u001a\u00020\u0003H&J\b\u0010\u000e\u001a\u00020\u0003H&J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007H&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/modules/Module;", "", ModuleXmlParser.MODULAR_JDK_ROOT, "", "getModularJdkRoot", "()Ljava/lang/String;", "getClasspathRoots", "", "getCommonSourceFiles", "getFriendPaths", "getJavaSourceRoots", "Lorg/jetbrains/kotlin/modules/JavaRootPath;", "getModuleName", "getModuleType", "getOutputDirectory", "getSourceFiles", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Module {
    List<String> getClasspathRoots();

    List<String> getCommonSourceFiles();

    List<String> getFriendPaths();

    List<JavaRootPath> getJavaSourceRoots();

    String getModularJdkRoot();

    String getModuleName();

    String getModuleType();

    String getOutputDirectory();

    List<String> getSourceFiles();
}
