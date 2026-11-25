package org.jetbrains.kotlin.scripting.extensions;

import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension;
import org.jetbrains.kotlin.resolve.lazy.LazyClassContext;
import org.jetbrains.kotlin.resolve.lazy.ResolveSession;
import org.jetbrains.kotlin.resolve.lazy.data.KtScriptInfo;
import org.jetbrains.kotlin.resolve.lazy.declarations.PackageMemberDeclarationProvider;
import org.jetbrains.kotlin.scripting.resolve.LazyScriptDescriptor;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptingResolveExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J6\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/scripting/extensions/ScriptingResolveExtension;", "Lorg/jetbrains/kotlin/resolve/extensions/SyntheticResolveExtension;", "()V", "generateSyntheticClasses", "", "thisDescriptor", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "ctx", "Lorg/jetbrains/kotlin/resolve/lazy/LazyClassContext;", "declarationProvider", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/PackageMemberDeclarationProvider;", "result", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptingResolveExtension implements SyntheticResolveExtension {
    @Override // org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension
    public void generateSyntheticClasses(PackageFragmentDescriptor thisDescriptor, Name name2, LazyClassContext ctx, PackageMemberDeclarationProvider declarationProvider, Set<ClassDescriptor> result) {
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(declarationProvider, "declarationProvider");
        Intrinsics.checkNotNullParameter(result, "result");
        Iterator<T> it = declarationProvider.getScriptDeclarations(name2).iterator();
        while (it.hasNext()) {
            result.add(new LazyScriptDescriptor((ResolveSession) ctx, thisDescriptor, name2, (KtScriptInfo) it.next()));
        }
        super.generateSyntheticClasses(thisDescriptor, name2, ctx, declarationProvider, result);
    }
}
