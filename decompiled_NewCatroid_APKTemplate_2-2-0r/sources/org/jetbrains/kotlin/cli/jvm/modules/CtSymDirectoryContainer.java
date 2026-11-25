package org.jetbrains.kotlin.cli.jvm.modules;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;

/* JADX INFO: compiled from: CtSymDirectoryContainer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BI\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\u000b\u001a\u00020\b¢\u0006\u0002\u0010\fJ\b\u0010!\u001a\u00020\"H\u0016J\u0015\u0010#\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000eH\u0016¢\u0006\u0002\u0010\u0010J\r\u0010$\u001a\u00070\u0014¢\u0006\u0002\b\u0015H\u0016J\b\u0010%\u001a\u00020&H\u0016J\b\u0010'\u001a\u00020(H\u0016J\b\u0010)\u001a\u00020\u0007H\u0016J\"\u0010*\u001a\u00020(2\b\u0010+\u001a\u0004\u0018\u00010,2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020.H\u0016J\n\u00100\u001a\u0004\u0018\u00010\u0001H\u0016J\b\u00101\u001a\u00020\u0007H\u0016J\b\u00102\u001a\u00020(H\u0016J\b\u00103\u001a\u00020\bH\u0016J\b\u00104\u001a\u00020\bH\u0016J\b\u00105\u001a\u00020\bH\u0016J\"\u00106\u001a\u0002072\u0006\u0010+\u001a\u00020\b2\u0006\u0010-\u001a\u00020\b2\b\u0010/\u001a\u0004\u0018\u000108H\u0016R!\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R \u0010\u0013\u001a\u00070\u0014¢\u0006\u0002\b\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0012\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0019\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u0012\u001a\u0004\b\u001a\u0010\u001bR\u001b\u0010\u001d\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010\u0012\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010\t\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/modules/CtSymDirectoryContainer;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "parent", "rootOrPackageParts", "", "packages", "", "", "", "currentPackage", "moduleRoot", "skipPackageCheck", "(Lcom/intellij/openapi/vfs/VirtualFile;Ljava/util/List;Ljava/util/Map;Ljava/lang/String;Ljava/lang/String;Z)V", "_children", "", "get_children", "()[Lcom/intellij/openapi/vfs/VirtualFile;", "_children$delegate", "Lkotlin/Lazy;", "_fileSystem", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem;", "Lorg/jetbrains/annotations/NotNull;", "get_fileSystem", "()Lcom/intellij/openapi/vfs/VirtualFileSystem;", "_fileSystem$delegate", "_isValid", "get_isValid", "()Z", "_isValid$delegate", "_path", "get_path", "()Ljava/lang/String;", "_path$delegate", "contentsToByteArray", "", "getChildren", "getFileSystem", "getInputStream", "Ljava/io/InputStream;", "getLength", "", "getName", "getOutputStream", "p0", "", "p1", "", "p2", "getParent", "getPath", "getTimeStamp", "isDirectory", "isValid", "isWritable", "refresh", "", "Ljava/lang/Runnable;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CtSymDirectoryContainer extends VirtualFile {

    /* JADX INFO: renamed from: _children$delegate, reason: from kotlin metadata */
    private final Lazy _children;

    /* JADX INFO: renamed from: _fileSystem$delegate, reason: from kotlin metadata */
    private final Lazy _fileSystem;

    /* JADX INFO: renamed from: _isValid$delegate, reason: from kotlin metadata */
    private final Lazy _isValid;

    /* JADX INFO: renamed from: _path$delegate, reason: from kotlin metadata */
    private final Lazy _path;
    private final String currentPackage;
    private final String moduleRoot;
    private final Map<String, Boolean> packages;
    private final VirtualFile parent;
    private final List<VirtualFile> rootOrPackageParts;
    private final boolean skipPackageCheck;

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isDirectory() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isWritable() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public void refresh(boolean p0, boolean p1, Runnable p2) {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public /* bridge */ /* synthetic */ long getLength() {
        return ((Number) m6374getLength()).longValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public /* bridge */ /* synthetic */ OutputStream getOutputStream(Object obj, long j, long j2) {
        return (OutputStream) m6375getOutputStream(obj, j, j2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public /* bridge */ /* synthetic */ long getTimeStamp() {
        return ((Number) m6376getTimeStamp()).longValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CtSymDirectoryContainer(VirtualFile virtualFile, List<? extends VirtualFile> rootOrPackageParts, Map<String, Boolean> packages, String currentPackage, String moduleRoot, boolean z) {
        Intrinsics.checkNotNullParameter(rootOrPackageParts, "rootOrPackageParts");
        Intrinsics.checkNotNullParameter(packages, "packages");
        Intrinsics.checkNotNullParameter(currentPackage, "currentPackage");
        Intrinsics.checkNotNullParameter(moduleRoot, "moduleRoot");
        this.parent = virtualFile;
        this.rootOrPackageParts = rootOrPackageParts;
        this.packages = packages;
        this.currentPackage = currentPackage;
        this.moduleRoot = moduleRoot;
        this.skipPackageCheck = z;
        this._path = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CtSymDirectoryContainer$_path$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                StringBuilder sb = new StringBuilder();
                VirtualFile virtualFile2 = this.this$0.parent;
                return sb.append(virtualFile2 != null ? virtualFile2.getPath() : null).append(this.this$0.currentPackage).append('/').toString();
            }
        });
        this._fileSystem = LazyKt.lazy(new Function0<VirtualFileSystem>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CtSymDirectoryContainer$_fileSystem$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final VirtualFileSystem invoke() {
                VirtualFile virtualFile2 = this.this$0.parent;
                Intrinsics.checkNotNull(virtualFile2);
                return virtualFile2.getFileSystem();
            }
        });
        this._isValid = LazyKt.lazy(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CtSymDirectoryContainer$_isValid$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                List list = this.this$0.rootOrPackageParts;
                boolean z2 = true;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        if (!((VirtualFile) it.next()).isValid()) {
                            z2 = false;
                            break;
                        }
                    }
                }
                return Boolean.valueOf(z2);
            }
        });
        this._children = LazyKt.lazy(new Function0<VirtualFile[]>() { // from class: org.jetbrains.kotlin.cli.jvm.modules.CtSymDirectoryContainer$_children$2
            {
                super(0);
            }

            /* JADX WARN: Code duplicated, block: B:9:0x0030  */
            @Override // kotlin.jvm.functions.Function0
            public final VirtualFile[] invoke() {
                List listEmptyList;
                List list = this.this$0.rootOrPackageParts;
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    VirtualFile[] children = ((VirtualFile) it.next()).getChildren();
                    if (children != null) {
                        Intrinsics.checkNotNull(children);
                        listEmptyList = ArraysKt.toList(children);
                        if (listEmptyList == null) {
                            listEmptyList = CollectionsKt.emptyList();
                        }
                    } else {
                        listEmptyList = CollectionsKt.emptyList();
                    }
                    CollectionsKt.addAll(arrayList, listEmptyList);
                }
                ArrayList<VirtualFile> arrayList2 = arrayList;
                if (!arrayList2.isEmpty()) {
                    boolean z2 = this.this$0.skipPackageCheck || ((Boolean) this.this$0.packages.getOrDefault(this.this$0.currentPackage, false)).booleanValue();
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    CtSymDirectoryContainer ctSymDirectoryContainer = this.this$0;
                    ArrayList arrayList3 = new ArrayList();
                    for (VirtualFile virtualFile2 : arrayList2) {
                        CtSymClassVirtualFile ctSymClassVirtualFile = null;
                        if (virtualFile2.isDirectory()) {
                            String name2 = ctSymDirectoryContainer.currentPackage.length() == 0 ? virtualFile2.getName() : ctSymDirectoryContainer.currentPackage + '.' + virtualFile2.getName();
                            Intrinsics.checkNotNull(name2);
                            if (ctSymDirectoryContainer.skipPackageCheck || ctSymDirectoryContainer.packages.containsKey(name2)) {
                                Object obj = linkedHashMap.get(name2);
                                if (obj == null) {
                                    ArrayList arrayList4 = new ArrayList();
                                    CtSymDirectoryContainer ctSymDirectoryContainer2 = new CtSymDirectoryContainer(ctSymDirectoryContainer, arrayList4, ctSymDirectoryContainer.packages, name2, ctSymDirectoryContainer.moduleRoot, ctSymDirectoryContainer.skipPackageCheck);
                                    obj = TuplesKt.to(ctSymDirectoryContainer2, arrayList4);
                                    linkedHashMap.put(name2, obj);
                                    ctSymClassVirtualFile = ctSymDirectoryContainer2;
                                }
                                List list2 = (List) ((Pair) obj).getSecond();
                                Intrinsics.checkNotNull(virtualFile2);
                                list2.add(virtualFile2);
                            }
                            ctSymClassVirtualFile = ctSymClassVirtualFile;
                        } else if (z2) {
                            Intrinsics.checkNotNull(virtualFile2);
                            ctSymClassVirtualFile = new CtSymClassVirtualFile(ctSymDirectoryContainer, virtualFile2);
                        }
                        if (ctSymClassVirtualFile != null) {
                            arrayList3.add(ctSymClassVirtualFile);
                        }
                    }
                    return (VirtualFile[]) arrayList3.toArray(new VirtualFile[0]);
                }
                return new VirtualFile[0];
            }
        });
    }

    private final String get_path() {
        return (String) this._path.getValue();
    }

    private final VirtualFileSystem get_fileSystem() {
        return (VirtualFileSystem) this._fileSystem.getValue();
    }

    private final boolean get_isValid() {
        return ((Boolean) this._isValid.getValue()).booleanValue();
    }

    private final VirtualFile[] get_children() {
        return (VirtualFile[]) this._children.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getName() {
        return StringsKt.substringAfterLast$default(this.currentPackage, ".", (String) null, 2, (Object) null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFileSystem getFileSystem() {
        VirtualFileSystem virtualFileSystem = get_fileSystem();
        Intrinsics.checkNotNullExpressionValue(virtualFileSystem, "<get-_fileSystem>(...)");
        return virtualFileSystem;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getPath() {
        return get_path();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isValid() {
        return get_isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile getParent() {
        return this.parent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile[] getChildren() {
        return get_children();
    }

    /* JADX INFO: renamed from: getOutputStream, reason: collision with other method in class */
    public Void m6375getOutputStream(Object p0, long p1, long p2) {
        throw new IllegalStateException("not supported".toString());
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public byte[] contentsToByteArray() {
        throw new IllegalStateException("not supported".toString());
    }

    /* JADX INFO: renamed from: getTimeStamp, reason: collision with other method in class */
    public Void m6376getTimeStamp() {
        throw new IllegalStateException("not supported".toString());
    }

    /* JADX INFO: renamed from: getLength, reason: collision with other method in class */
    public Void m6374getLength() {
        throw new IllegalStateException("not supported".toString());
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public InputStream getInputStream() {
        throw new IllegalStateException("not supported".toString());
    }
}
