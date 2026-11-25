package org.jetbrains.kotlin.psi.stubs.impl;

import com.reandroid.apk.PathMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiClassHolderFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.stubs.KotlinFileStub;
import org.jetbrains.kotlin.psi.stubs.KotlinImportAliasStub;
import org.jetbrains.kotlin.psi.stubs.KotlinImportDirectiveStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtFileElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KotlinFileStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 &2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\b\u0012\u0004\u0012\u00020\u00020\u0004:\u0001&B!\b\u0016\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nBC\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0007\u0012\u000e\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fJ\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u000e2\u0006\u0010\u001c\u001a\u00020\u0007H\u0016J\u0013\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0016¢\u0006\u0002\u0010 J\b\u0010!\u001a\u00020\u0011H\u0016J\u000e\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00030#H\u0016J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010$\u001a\u00020\u0007H\u0016J\f\u0010%\u001a\u00020\u0011*\u00020\u0007H\u0002R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u00118F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u00118F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0013R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinFileStubImpl;", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/PsiFileStubImpl;", "Lorg/jetbrains/kotlin/psi/KtFile;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinFileStub;", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/PsiClassHolderFileStub;", "ktFile", "packageName", "", "isScript", "", "(Lorg/jetbrains/kotlin/psi/KtFile;Ljava/lang/String;Z)V", "facadeFqNameString", "partSimpleName", "facadePartSimpleNames", "", "(Lorg/jetbrains/kotlin/psi/KtFile;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "facadeFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFacadeFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getFacadePartSimpleNames", "()Ljava/util/List;", "partFqName", "getPartFqName", "getPartSimpleName", "()Ljava/lang/String;", "findImportsByAlias", "Lorg/jetbrains/kotlin/psi/stubs/KotlinImportDirectiveStub;", PathMap.NAME_alias, "getClasses", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "()[Lcom/intellij/psi/PsiClass;", "getPackageFqName", "getType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IStubFileElementType;", "toString", "relativeToPackage", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinFileStubImpl extends PsiFileStubImpl<KtFile> implements PsiClassHolderFileStub<KtFile>, KotlinFileStub {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String facadeFqNameString;
    private final List<String> facadePartSimpleNames;
    private final boolean isScript;
    private final String packageName;
    private final String partSimpleName;

    public final String getPartSimpleName() {
        return this.partSimpleName;
    }

    public final List<String> getFacadePartSimpleNames() {
        return this.facadePartSimpleNames;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinFileStubImpl(KtFile ktFile, String packageName, boolean z, String str, String str2, List<String> list) {
        super(ktFile);
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        this.packageName = packageName;
        this.isScript = z;
        this.facadeFqNameString = str;
        this.partSimpleName = str2;
        this.facadePartSimpleNames = list;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KotlinFileStubImpl(KtFile ktFile, String packageName, boolean z) {
        this(ktFile, packageName, z, null, null, null);
        Intrinsics.checkNotNullParameter(packageName, "packageName");
    }

    private final FqName relativeToPackage(String str) {
        FqName fqNameChild = getPackageFqName().child(Name.identifier(str));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        return fqNameChild;
    }

    public final FqName getPartFqName() {
        String str = this.partSimpleName;
        if (str != null) {
            return relativeToPackage(str);
        }
        return null;
    }

    public final FqName getFacadeFqName() {
        String str = this.facadeFqNameString;
        if (str != null) {
            return new FqName(str);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinFileStub
    public FqName getPackageFqName() {
        return new FqName(this.packageName);
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinFileStub
    /* JADX INFO: renamed from: isScript, reason: from getter */
    public boolean getIsScript() {
        return this.isScript;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStubImpl, org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub
    public IStubFileElementType<KotlinFileStub> getType() {
        KtFileElementType INSTANCE2 = KtFileElementType.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(INSTANCE2, "INSTANCE");
        return INSTANCE2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "PsiJetFileStubImpl[package=" + getPackageFqName().asString() + ']';
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.PsiClassHolderFileStub
    public PsiClass[] getClasses() {
        List<StubElement> childrenStubs = getChildrenStubs();
        Intrinsics.checkNotNullExpressionValue(childrenStubs, "getChildrenStubs(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : childrenStubs) {
            if (obj instanceof PsiClassStub) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add((PsiClass) ((PsiClassStub) it.next()).getPsi());
        }
        return (PsiClass[]) arrayList3.toArray(new PsiClass[0]);
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinFileStub
    public List<KotlinImportDirectiveStub> findImportsByAlias(String alias) {
        Object next;
        Object next2;
        Intrinsics.checkNotNullParameter(alias, "alias");
        List<StubElement> childrenStubs = getChildrenStubs();
        Intrinsics.checkNotNullExpressionValue(childrenStubs, "getChildrenStubs(...)");
        Iterator<T> it = childrenStubs.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((StubElement) next).getStubType(), KtStubElementTypes.IMPORT_LIST));
        StubElement stubElement = (StubElement) next;
        if (stubElement == null) {
            return CollectionsKt.emptyList();
        }
        List<StubElement> childrenStubs2 = stubElement.getChildrenStubs();
        Intrinsics.checkNotNullExpressionValue(childrenStubs2, "getChildrenStubs(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : childrenStubs2) {
            if (obj instanceof KotlinImportDirectiveStub) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            List<StubElement> childrenStubs3 = ((KotlinImportDirectiveStub) obj2).getChildrenStubs();
            Intrinsics.checkNotNullExpressionValue(childrenStubs3, "getChildrenStubs(...)");
            Iterator<T> it2 = childrenStubs3.iterator();
            do {
                if (!it2.hasNext()) {
                    next2 = null;
                    break;
                }
                next2 = it2.next();
            } while (!(next2 instanceof KotlinImportAliasStub));
            KotlinImportAliasStub kotlinImportAliasStub = (KotlinImportAliasStub) next2;
            if (Intrinsics.areEqual(kotlinImportAliasStub != null ? kotlinImportAliasStub.getName() : null, alias)) {
                arrayList2.add(obj2);
            }
        }
        return arrayList2;
    }

    /* JADX INFO: compiled from: KotlinFileStubImpl.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0006J&\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\r¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinFileStubImpl$Companion;", "", "()V", "forFile", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinFileStubImpl;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "isScript", "", "forFileFacadeStub", "facadeFqName", "forMultifileClassStub", "partNames", "", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinFileStubImpl forFile(FqName packageFqName, boolean isScript) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            String strAsString = packageFqName.asString();
            Intrinsics.checkNotNull(strAsString);
            return new KotlinFileStubImpl(null, strAsString, isScript, null, null, null);
        }

        public final KotlinFileStubImpl forFileFacadeStub(FqName facadeFqName) {
            Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
            String strAsString = facadeFqName.parent().asString();
            String strAsString2 = facadeFqName.asString();
            String strAsString3 = facadeFqName.shortName().asString();
            Intrinsics.checkNotNull(strAsString);
            return new KotlinFileStubImpl(null, strAsString, false, strAsString2, strAsString3, null);
        }

        public final KotlinFileStubImpl forMultifileClassStub(FqName packageFqName, FqName facadeFqName, List<String> partNames) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(facadeFqName, "facadeFqName");
            String strAsString = packageFqName.asString();
            String strAsString2 = facadeFqName.asString();
            Intrinsics.checkNotNull(strAsString);
            return new KotlinFileStubImpl(null, strAsString, false, strAsString2, null, partNames);
        }
    }
}
