package org.jetbrains.kotlin.cli.jvm.compiler;

import com.reandroid.archive.block.ApkSignatureBlock;
import gnu.trove.THashSet;
import java.io.InputStream;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.cli.jvm.index.JavaRoot;
import org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndex;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.kotlin.VirtualFileFinder;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.serialization.deserialization.MetadataPackageFragment;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: CliVirtualFileFinder.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001a\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0017\u0010\u000f\u001a\t\u0018\u00010\n¢\u0006\u0002\b\u00102\u0006\u0010\u000b\u001a\u00020\fH\u0002J-\u0010\u000f\u001a\t\u0018\u00010\n¢\u0006\u0002\b\u00102\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u000e2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0002J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J(\u0010\u0019\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0002J\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0016\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u001f\u0010\u001c\u001a\t\u0018\u00010\n¢\u0006\u0002\b\u00102\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u000eH\u0002J\u001a\u0010\u001e\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0017\u0010\u001f\u001a\t\u0018\u00010\n¢\u0006\u0002\b\u00102\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0012\u0010 \u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020\u0018H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/CliVirtualFileFinder;", "Lorg/jetbrains/kotlin/load/kotlin/VirtualFileFinder;", "index", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndex;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "enableSearchInCtSym", "", "(Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndex;Lcom/intellij/psi/search/GlobalSearchScope;Z)V", "findBinaryClass", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", PsiTreeChangeEvent.PROP_FILE_NAME, "", "findBinaryOrSigClass", "Lorg/jetbrains/annotations/Nullable;", Namer.METADATA_SIMPLE_NAME, "rootType", "", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot$RootType;", "findBuiltInsData", "Ljava/io/InputStream;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "findClass", "findMetadata", "findMetadataTopLevelClassesInPackage", "findSigFileIfEnabled", "dir", "findSourceClass", "findSourceOrBinaryVirtualFile", "findVirtualFileWithHeader", "hasMetadataPackage", "fqName", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CliVirtualFileFinder extends VirtualFileFinder {
    private final boolean enableSearchInCtSym;
    private final JvmDependenciesIndex index;
    private final GlobalSearchScope scope;

    public CliVirtualFileFinder(JvmDependenciesIndex index, GlobalSearchScope scope, boolean z) {
        Intrinsics.checkNotNullParameter(index, "index");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.index = index;
        this.scope = scope;
        this.enableSearchInCtSym = z;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.VirtualFileFinder
    public VirtualFile findVirtualFileWithHeader(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return findBinaryOrSigClass(classId);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.VirtualFileFinder
    public VirtualFile findSourceOrBinaryVirtualFile(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        VirtualFile virtualFileFindBinaryOrSigClass = findBinaryOrSigClass(classId);
        return virtualFileFindBinaryOrSigClass == null ? findSourceClass(classId, classId.getRelativeClassName().asString() + SuffixConstants.SUFFIX_STRING_java) : virtualFileFindBinaryOrSigClass;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.KotlinMetadataFinder
    public InputStream findMetadata(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        classId.isNestedClass();
        VirtualFile virtualFileFindBinaryClass = findBinaryClass(classId, classId.getShortClassName().asString() + MetadataPackageFragment.DOT_METADATA_FILE_EXTENSION);
        if (virtualFileFindBinaryClass != null) {
            return virtualFileFindBinaryClass.getInputStream();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.KotlinMetadataFinder
    public Set<String> findMetadataTopLevelClassesInPackage(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        final THashSet tHashSet = new THashSet();
        JvmDependenciesIndex.traverseDirectoriesInPackage$default(this.index, packageFqName, null, new Function2<VirtualFile, JavaRoot.RootType, Boolean>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.CliVirtualFileFinder.findMetadataTopLevelClassesInPackage.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(VirtualFile dir, JavaRoot.RootType rootType) {
                Intrinsics.checkNotNullParameter(dir, "dir");
                Intrinsics.checkNotNullParameter(rootType, "<anonymous parameter 1>");
                VirtualFile[] children = dir.getChildren();
                Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
                for (VirtualFile virtualFile : children) {
                    if (Intrinsics.areEqual(virtualFile.getExtension(), MetadataPackageFragment.METADATA_FILE_EXTENSION)) {
                        tHashSet.add(virtualFile.getNameWithoutExtension());
                    }
                }
                return true;
            }
        }, 2, null);
        return tHashSet;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.KotlinMetadataFinder
    public boolean hasMetadataPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        JvmDependenciesIndex.traverseDirectoriesInPackage$default(this.index, fqName, null, new Function2<VirtualFile, JavaRoot.RootType, Boolean>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.CliVirtualFileFinder.hasMetadataPackage.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(VirtualFile dir, JavaRoot.RootType rootType) {
                Intrinsics.checkNotNullParameter(dir, "dir");
                Intrinsics.checkNotNullParameter(rootType, "<anonymous parameter 1>");
                Ref.BooleanRef booleanRef2 = booleanRef;
                boolean z = booleanRef2.element;
                VirtualFile[] children = dir.getChildren();
                Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
                boolean z2 = false;
                for (VirtualFile virtualFile : children) {
                    if (Intrinsics.areEqual(virtualFile.getExtension(), MetadataPackageFragment.METADATA_FILE_EXTENSION)) {
                        z2 = true;
                        break;
                    }
                }
                booleanRef2.element = z | z2;
                return Boolean.valueOf(!booleanRef.element);
            }
        }, 2, null);
        return booleanRef.element;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.KotlinMetadataFinder
    public InputStream findBuiltInsData(FqName packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        VirtualFile virtualFileFindBinaryClass = findBinaryClass(new ClassId(packageFqName, Name.special("<builtins-metadata>")), BuiltInSerializerProtocol.INSTANCE.getBuiltInsFileName(packageFqName));
        if (virtualFileFindBinaryClass != null) {
            return virtualFileFindBinaryClass.getInputStream();
        }
        return null;
    }

    private final VirtualFile findClass(ClassId classId, final String fileName, Set<? extends JavaRoot.RootType> rootType) {
        VirtualFile virtualFile = (VirtualFile) this.index.findClass(classId, rootType, new Function2<VirtualFile, JavaRoot.RootType, VirtualFile>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.CliVirtualFileFinder.findClass.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final VirtualFile invoke(VirtualFile dir, JavaRoot.RootType rootType2) {
                Intrinsics.checkNotNullParameter(dir, "dir");
                Intrinsics.checkNotNullParameter(rootType2, "<anonymous parameter 1>");
                VirtualFile virtualFileFindChild = dir.findChild(fileName);
                if (virtualFileFindChild == null || !virtualFileFindChild.isValid()) {
                    return null;
                }
                return virtualFileFindChild;
            }
        });
        if (virtualFile == null || !this.scope.contains(virtualFile)) {
            return null;
        }
        return virtualFile;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final VirtualFile findSigFileIfEnabled(VirtualFile dir, String simpleName) {
        if (this.enableSearchInCtSym) {
            return dir.findChild(simpleName + ApkSignatureBlock.FILE_EXT);
        }
        return null;
    }

    private final VirtualFile findBinaryOrSigClass(ClassId classId, final String simpleName, Set<? extends JavaRoot.RootType> rootType) {
        VirtualFile virtualFile = (VirtualFile) this.index.findClass(classId, rootType, new Function2<VirtualFile, JavaRoot.RootType, VirtualFile>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.CliVirtualFileFinder.findBinaryOrSigClass.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final VirtualFile invoke(VirtualFile dir, JavaRoot.RootType rootType2) {
                Intrinsics.checkNotNullParameter(dir, "dir");
                Intrinsics.checkNotNullParameter(rootType2, "<anonymous parameter 1>");
                VirtualFile virtualFileFindChild = dir.findChild(simpleName + ".class");
                if (virtualFileFindChild == null) {
                    virtualFileFindChild = this.findSigFileIfEnabled(dir, simpleName);
                }
                if (virtualFileFindChild == null || !virtualFileFindChild.isValid()) {
                    return null;
                }
                return virtualFileFindChild;
            }
        });
        if (virtualFile == null || !this.scope.contains(virtualFile)) {
            return null;
        }
        return virtualFile;
    }

    private final VirtualFile findBinaryOrSigClass(ClassId classId) {
        String strAsString = classId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return findBinaryOrSigClass(classId, StringsKt.replace$default(strAsString, '.', '$', false, 4, (Object) null), JavaRoot.INSTANCE.getOnlyBinary());
    }

    private final VirtualFile findBinaryClass(ClassId classId, String fileName) {
        return findClass(classId, fileName, JavaRoot.INSTANCE.getOnlyBinary());
    }

    private final VirtualFile findSourceClass(ClassId classId, String fileName) {
        return findClass(classId, fileName, JavaRoot.INSTANCE.getOnlySource());
    }
}
