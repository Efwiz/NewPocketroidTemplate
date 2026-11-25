package org.jetbrains.kotlin.cli.common.modules;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.modules.JavaRootPath;
import org.jetbrains.kotlin.modules.Module;

/* JADX INFO: compiled from: ModuleBuilder.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\t\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0003J\u000e\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0003J\u000e\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0003J\u000e\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\fJ\u000e\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0003J\u000e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00030\u001dH\u0016J\u000e\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00030\u001dH\u0016J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00030\u001dH\u0016J\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00020\f0\u001dH\u0016J\b\u0010!\u001a\u00020\u0003H\u0016J\b\u0010\"\u001a\u00020\u0003H\u0016J\b\u0010#\u001a\u00020\u0003H\u0016J\u000e\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00030\u001dH\u0016J\b\u0010%\u001a\u00020\u0003H\u0016R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00030\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/modules/ModuleBuilder;", "Lorg/jetbrains/kotlin/modules/Module;", "name", "", ModuleXmlParser.OUTPUT_DIR, ModuleXmlParser.TYPE, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "classpathRoots", "Ljava/util/ArrayList;", "commonSourceFiles", "friendDirs", ModuleXmlParser.JAVA_SOURCE_ROOTS, "Lorg/jetbrains/kotlin/modules/JavaRootPath;", ModuleXmlParser.MODULAR_JDK_ROOT, "getModularJdkRoot", "()Ljava/lang/String;", "setModularJdkRoot", "(Ljava/lang/String;)V", "sourceFiles", "addClasspathEntry", "", "path", "addCommonSourceFiles", "addFriendDir", ModuleXmlParser.FRIEND_DIR, "addJavaSourceRoot", "rootPath", "addSourceFiles", "getClasspathRoots", "", "getCommonSourceFiles", "getFriendPaths", "getJavaSourceRoots", "getModuleName", "getModuleType", "getOutputDirectory", "getSourceFiles", "toString", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModuleBuilder implements Module {
    private final ArrayList<String> classpathRoots;
    private final ArrayList<String> commonSourceFiles;
    private final ArrayList<String> friendDirs;
    private final ArrayList<JavaRootPath> javaSourceRoots;
    private String modularJdkRoot;
    private final String name;
    private final String outputDir;
    private final ArrayList<String> sourceFiles;
    private final String type;

    public ModuleBuilder(String name2, String outputDir, String type) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        Intrinsics.checkNotNullParameter(type, "type");
        this.name = name2;
        this.outputDir = outputDir;
        this.type = type;
        this.sourceFiles = new ArrayList<>();
        this.commonSourceFiles = new ArrayList<>();
        this.classpathRoots = new ArrayList<>();
        this.javaSourceRoots = new ArrayList<>();
        this.friendDirs = new ArrayList<>();
    }

    @Override // org.jetbrains.kotlin.modules.Module
    public String getModularJdkRoot() {
        return this.modularJdkRoot;
    }

    public void setModularJdkRoot(String str) {
        this.modularJdkRoot = str;
    }

    public final void addSourceFiles(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        this.sourceFiles.add(path);
    }

    public final void addCommonSourceFiles(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        this.commonSourceFiles.add(path);
    }

    public final void addClasspathEntry(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        this.classpathRoots.add(path);
    }

    public final void addJavaSourceRoot(JavaRootPath rootPath) {
        Intrinsics.checkNotNullParameter(rootPath, "rootPath");
        this.javaSourceRoots.add(rootPath);
    }

    public final void addFriendDir(String friendDir) {
        Intrinsics.checkNotNullParameter(friendDir, "friendDir");
        this.friendDirs.add(friendDir);
    }

    @Override // org.jetbrains.kotlin.modules.Module
    /* JADX INFO: renamed from: getOutputDirectory, reason: from getter */
    public String getOutputDir() {
        return this.outputDir;
    }

    @Override // org.jetbrains.kotlin.modules.Module
    public List<String> getFriendPaths() {
        return this.friendDirs;
    }

    @Override // org.jetbrains.kotlin.modules.Module
    public List<JavaRootPath> getJavaSourceRoots() {
        return this.javaSourceRoots;
    }

    @Override // org.jetbrains.kotlin.modules.Module
    public List<String> getSourceFiles() {
        return this.sourceFiles;
    }

    @Override // org.jetbrains.kotlin.modules.Module
    public List<String> getCommonSourceFiles() {
        return this.commonSourceFiles;
    }

    @Override // org.jetbrains.kotlin.modules.Module
    public List<String> getClasspathRoots() {
        return this.classpathRoots;
    }

    @Override // org.jetbrains.kotlin.modules.Module
    /* JADX INFO: renamed from: getModuleName, reason: from getter */
    public String getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.modules.Module
    /* JADX INFO: renamed from: getModuleType, reason: from getter */
    public String getType() {
        return this.type;
    }

    public String toString() {
        return this.name + " (" + this.type + ')';
    }
}
