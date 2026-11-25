package org.jetbrains.kotlin.cli.jvm.index;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JvmDependenciesDynamicCompoundIndex.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0001J\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u00012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u0012JK\u0010\u0013\u001a\u0004\u0018\u0001H\u0014\"\b\b\u0000\u0010\u0014*\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\u001a\u0010\u001b\u001a\u0016\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001a\u0012\u0006\u0012\u0004\u0018\u0001H\u00140\u001cH\u0016¢\u0006\u0002\u0010\u001eJ8\u0010\u001f\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020!2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00192\u0018\u0010\"\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020#0\u001cH\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u00010\tj\b\u0012\u0004\u0012\u00020\u0001`\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesDynamicCompoundIndex;", "Lorg/jetbrains/kotlin/cli/jvm/index/JvmDependenciesIndex;", "()V", "indexedRoots", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot;", "getIndexedRoots", "()Lkotlin/sequences/Sequence;", "indices", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "addIndex", "", "index", "addNewIndexForRoots", PsiTreeChangeEvent.PROP_ROOTS, "", "findClass", ExifInterface.GPS_DIRECTION_TRUE, "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "acceptedRootTypes", "", "Lorg/jetbrains/kotlin/cli/jvm/index/JavaRoot$RootType;", "findClassGivenDirectory", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "(Lorg/jetbrains/kotlin/name/ClassId;Ljava/util/Set;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "traverseDirectoriesInPackage", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "continueSearch", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmDependenciesDynamicCompoundIndex implements JvmDependenciesIndex {
    private final ArrayList<JvmDependenciesIndex> indices = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public final void addIndex(JvmDependenciesIndex index) {
        Intrinsics.checkNotNullParameter(index, "index");
        ReentrantReadWriteLock reentrantReadWriteLock = this.lock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            this.indices.add(index);
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
        } finally {
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
            writeLock.unlock();
        }
    }

    public final JvmDependenciesIndex addNewIndexForRoots(Iterable<JavaRoot> roots) {
        JvmDependenciesIndexImpl jvmDependenciesIndexImpl;
        Intrinsics.checkNotNullParameter(roots, "roots");
        ReentrantReadWriteLock.ReadLock lock = this.lock.readLock();
        lock.lock();
        try {
            HashSet hashSet = SequencesKt.toHashSet(getIndexedRoots());
            ArrayList arrayList = new ArrayList();
            for (JavaRoot javaRoot : roots) {
                if (!hashSet.contains(javaRoot)) {
                    arrayList.add(javaRoot);
                }
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.isEmpty()) {
                jvmDependenciesIndexImpl = null;
            } else {
                jvmDependenciesIndexImpl = new JvmDependenciesIndexImpl(arrayList2);
                addIndex(jvmDependenciesIndexImpl);
            }
            return jvmDependenciesIndexImpl;
        } finally {
            lock.unlock();
        }
    }

    @Override // org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndex
    public Sequence<JavaRoot> getIndexedRoots() {
        return SequencesKt.flatMap(CollectionsKt.asSequence(this.indices), new Function1<JvmDependenciesIndex, Sequence<? extends JavaRoot>>() { // from class: org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesDynamicCompoundIndex$indexedRoots$1
            @Override // kotlin.jvm.functions.Function1
            public final Sequence<JavaRoot> invoke(JvmDependenciesIndex it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getIndexedRoots();
            }
        });
    }

    @Override // org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndex
    public <T> T findClass(final ClassId classId, final Set<? extends JavaRoot.RootType> acceptedRootTypes, final Function2<? super VirtualFile, ? super JavaRoot.RootType, ? extends T> findClassGivenDirectory) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(acceptedRootTypes, "acceptedRootTypes");
        Intrinsics.checkNotNullParameter(findClassGivenDirectory, "findClassGivenDirectory");
        ReentrantReadWriteLock.ReadLock lock = this.lock.readLock();
        lock.lock();
        try {
            return (T) SequencesKt.firstOrNull(SequencesKt.mapNotNull(CollectionsKt.asSequence(this.indices), new Function1<JvmDependenciesIndex, T>() { // from class: org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesDynamicCompoundIndex$findClass$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final T invoke(JvmDependenciesIndex it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return (T) it.findClass(classId, acceptedRootTypes, findClassGivenDirectory);
                }
            }));
        } finally {
            lock.unlock();
        }
    }

    @Override // org.jetbrains.kotlin.cli.jvm.index.JvmDependenciesIndex
    public void traverseDirectoriesInPackage(FqName packageFqName, Set<? extends JavaRoot.RootType> acceptedRootTypes, Function2<? super VirtualFile, ? super JavaRoot.RootType, Boolean> continueSearch) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(acceptedRootTypes, "acceptedRootTypes");
        Intrinsics.checkNotNullParameter(continueSearch, "continueSearch");
        ReentrantReadWriteLock.ReadLock lock = this.lock.readLock();
        lock.lock();
        try {
            Iterator<T> it = this.indices.iterator();
            while (it.hasNext()) {
                ((JvmDependenciesIndex) it.next()).traverseDirectoriesInPackage(packageFqName, acceptedRootTypes, continueSearch);
            }
            Unit unit = Unit.INSTANCE;
        } finally {
            lock.unlock();
        }
    }
}
