package org.jetbrains.kotlin.cli.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.load.kotlin.ModuleVisibilityManager;
import org.jetbrains.kotlin.modules.Module;

/* JADX INFO: compiled from: moduleVisibilityImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000eH\u0016J\u0010\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\bH\u0016J\b\u0010\u0015\u001a\u00020\u0011H\u0016R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/CliModuleVisibilityManagerImpl;", "Lorg/jetbrains/kotlin/load/kotlin/ModuleVisibilityManager;", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", CompilerOptions.ENABLED, "", "(Z)V", "chunk", "", "Lorg/jetbrains/kotlin/modules/Module;", "getChunk", "()Ljava/util/List;", "getEnabled", "()Z", "friendPaths", "", "getFriendPaths", "addFriendPath", "", "path", "addModule", "module", "dispose", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CliModuleVisibilityManagerImpl implements Disposable, ModuleVisibilityManager {
    private final boolean enabled;
    private final List<Module> chunk = new ArrayList();
    private final List<String> friendPaths = new ArrayList();

    public CliModuleVisibilityManagerImpl(boolean z) {
        this.enabled = z;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.ModuleVisibilityManager
    public boolean getEnabled() {
        return this.enabled;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.ModuleVisibilityManager
    public List<Module> getChunk() {
        return this.chunk;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.ModuleVisibilityManager
    public List<String> getFriendPaths() {
        return this.friendPaths;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.ModuleVisibilityManager
    public void addModule(Module module) {
        Intrinsics.checkNotNullParameter(module, "module");
        getChunk().add(module);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.ModuleVisibilityManager
    public void addFriendPath(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        List<String> friendPaths = getFriendPaths();
        String absolutePath = new File(path).getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        friendPaths.add(absolutePath);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
        getChunk().clear();
    }
}
