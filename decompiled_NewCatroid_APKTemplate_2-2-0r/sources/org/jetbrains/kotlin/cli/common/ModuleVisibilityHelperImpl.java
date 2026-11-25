package org.jetbrains.kotlin.cli.common;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.load.kotlin.ModuleVisibilityManager;
import org.jetbrains.kotlin.load.kotlin.ModuleVisibilityUtilsKt;
import org.jetbrains.kotlin.modules.Module;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyPackageDescriptor;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement;
import org.jetbrains.kotlin.util.ModuleVisibilityHelper;

/* JADX INFO: compiled from: moduleVisibilityImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\bH\u0002J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006H\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/ModuleVisibilityHelperImpl;", "Lorg/jetbrains/kotlin/util/ModuleVisibilityHelper;", "()V", "findModule", "Lorg/jetbrains/kotlin/modules/Module;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "modules", "", "isInFriendModule", "", "what", TypedValues.TransitionType.S_FROM, "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModuleVisibilityHelperImpl implements ModuleVisibilityHelper {
    @Override // org.jetbrains.kotlin.util.ModuleVisibilityHelper
    public boolean isInFriendModule(DeclarationDescriptor what, DeclarationDescriptor from) {
        PackageMemberDeclarationProvider declarationProvider;
        Collection<KtFile> packageFiles;
        KtFile ktFile;
        Project project;
        Intrinsics.checkNotNullParameter(what, "what");
        Intrinsics.checkNotNullParameter(from, "from");
        SourceElement sourceElement = ModuleVisibilityUtilsKt.getSourceElement(from);
        boolean z = sourceElement instanceof KotlinSourceElement;
        if (z) {
            project = ((KotlinSourceElement) sourceElement).getPsi().getProject();
            Intrinsics.checkNotNull(project);
        } else {
            LazyPackageDescriptor lazyPackageDescriptor = from instanceof LazyPackageDescriptor ? (LazyPackageDescriptor) from : null;
            if (lazyPackageDescriptor == null || (declarationProvider = lazyPackageDescriptor.getDeclarationProvider()) == null || (packageFiles = declarationProvider.getPackageFiles()) == null || (ktFile = (KtFile) CollectionsKt.firstOrNull(packageFiles)) == null || (project = ktFile.getProject()) == null) {
                return true;
            }
        }
        ModuleVisibilityManager service = ModuleVisibilityManager.SERVICE.getInstance(project);
        if (!service.getEnabled()) {
            return true;
        }
        Iterator<T> it = service.getFriendPaths().iterator();
        while (it.hasNext()) {
            if (ModuleVisibilityUtilsKt.isContainedByCompiledPartOfOurModule(what, new File((String) it.next()))) {
                return true;
            }
        }
        Collection<Module> chunk = service.getChunk();
        if (ModuleVisibilityUtilsKt.getSourceElement(what) instanceof KotlinSourceElement) {
            return chunk.size() <= 1 || !z || findModule(what, chunk) == findModule(from, chunk);
        }
        if (chunk.isEmpty()) {
            return false;
        }
        return (chunk.size() == 1 && ModuleVisibilityUtilsKt.isContainedByCompiledPartOfOurModule(what, new File(((Module) CollectionsKt.single(chunk)).getOutputDir()))) || findModule(from, chunk) == findModule(what, chunk);
    }

    private final Module findModule(DeclarationDescriptor descriptor, Collection<? extends Module> modules) {
        SourceElement sourceElement = ModuleVisibilityUtilsKt.getSourceElement(descriptor);
        Object obj = null;
        if (sourceElement instanceof KotlinSourceElement) {
            Collection<? extends Module> collection = modules;
            Module module = (Module) CollectionsKt.singleOrNull(collection);
            if (module != null) {
                return module;
            }
            for (Object obj2 : collection) {
                if (((Module) obj2).getSourceFiles().contains(((KotlinSourceElement) sourceElement).getPsi().getContainingKtFile().getVirtualFile().getPath())) {
                    obj = obj2;
                    break;
                }
            }
            return (Module) obj;
        }
        loop1: for (Object obj3 : modules) {
            Module module2 = (Module) obj3;
            if (!ModuleVisibilityUtilsKt.isContainedByCompiledPartOfOurModule(descriptor, new File(module2.getOutputDir()))) {
                List<String> friendPaths = module2.getFriendPaths();
                if (!(friendPaths instanceof Collection) || !friendPaths.isEmpty()) {
                    Iterator<T> it = friendPaths.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            continue;
                        } else if (ModuleVisibilityUtilsKt.isContainedByCompiledPartOfOurModule(descriptor, new File((String) it.next()))) {
                        }
                    }
                }
            }
            obj = obj3;
        }
        return (Module) obj;
    }
}
