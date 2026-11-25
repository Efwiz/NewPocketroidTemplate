package org.jetbrains.kotlin.cli.jvm.index;

import androidx.exifinterface.media.ExifInterface;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JvmDependenciesIndex.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001JM\u0010\u0007\u001a\u0004\u0018\u0001H\b\"\b\b\u0000\u0010\b*\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u001a\u0010\u000e\u001a\u0016\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u0001H\b0\u000fH&¢\u0006\u0002\u0010\u0011J:\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0018\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00170\u000fH&R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0018À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndex;", "", "indexedRoots", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot;", "getIndexedRoots", "()Lkotlin/sequences/Sequence;", "findClass", ExifInterface.GPS_DIRECTION_TRUE, "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "acceptedRootTypes", "", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot$RootType;", "findClassGivenDirectory", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "(Lorg/jetbrains/kotlin/name/ClassId;Ljava/util/Set;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "traverseDirectoriesInPackage", "", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "continueSearch", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JvmDependenciesIndex {
    <T> T findClass(ClassId classId, Set<? extends JavaRoot.RootType> acceptedRootTypes, Function2<? super VirtualFile, ? super JavaRoot.RootType, ? extends T> findClassGivenDirectory);

    Sequence<JavaRoot> getIndexedRoots();

    void traverseDirectoriesInPackage(FqName packageFqName, Set<? extends JavaRoot.RootType> acceptedRootTypes, Function2<? super VirtualFile, ? super JavaRoot.RootType, Boolean> continueSearch);

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ Object findClass$default(JvmDependenciesIndex jvmDependenciesIndex, ClassId classId, Set set, Function2 function2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: findClass");
        }
        if ((i & 2) != 0) {
            set = JavaRoot.INSTANCE.getSourceAndBinary();
        }
        return jvmDependenciesIndex.findClass(classId, set, function2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void traverseDirectoriesInPackage$default(JvmDependenciesIndex jvmDependenciesIndex, FqName fqName, Set set, Function2 function2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: traverseDirectoriesInPackage");
        }
        if ((i & 2) != 0) {
            set = JavaRoot.INSTANCE.getSourceAndBinary();
        }
        jvmDependenciesIndex.traverseDirectoriesInPackage(fqName, set, function2);
    }
}
