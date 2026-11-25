package org.jetbrains.kotlin.resolve.lazy.data;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtTypeParameterList;

/* JADX INFO: compiled from: KtScriptInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\n\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\nH\u0016J\u0016\u0010\u0012\u001a\u0010\u0012\f\u0012\n \u0014*\u0004\u0018\u00010\u00130\u00130\nH\u0016J\n\u0010\u0015\u001a\u0004\u0018\u00010\u000fH\u0016J\u000e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\nH\u0016J\b\u0010\u0018\u001a\u00020\u0003H\u0016J\n\u0010\u0019\u001a\u0004\u0018\u00010\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/data/KtScriptInfo;", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtClassLikeInfo;", "script", "Lorg/jetbrains/kotlin/psi/KtScript;", "(Lorg/jetbrains/kotlin/psi/KtScript;)V", "getScript", "()Lorg/jetbrains/kotlin/psi/KtScript;", "getClassKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getCompanionObjects", "", "Lorg/jetbrains/kotlin/psi/KtObjectDeclaration;", "getContainingPackageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getCorrespondingClassOrObject", "", "getDanglingAnnotations", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "getDeclarations", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getModifierList", "getPrimaryConstructorParameters", "Lorg/jetbrains/kotlin/psi/KtParameter;", "getScopeAnchor", "getTypeParameterList", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtScriptInfo implements KtClassLikeInfo {
    private final KtScript script;

    public Void getCorrespondingClassOrObject() {
        return null;
    }

    public Void getModifierList() {
        return null;
    }

    public Void getTypeParameterList() {
        return null;
    }

    public KtScriptInfo(KtScript script) {
        Intrinsics.checkNotNullParameter(script, "script");
        this.script = script;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    /* JADX INFO: renamed from: getCorrespondingClassOrObject, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ KtClassOrObject mo7451getCorrespondingClassOrObject() {
        return (KtClassOrObject) getCorrespondingClassOrObject();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    /* JADX INFO: renamed from: getModifierList, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ KtModifierList mo7452getModifierList() {
        return (KtModifierList) getModifierList();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    /* JADX INFO: renamed from: getTypeParameterList, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ KtTypeParameterList mo7453getTypeParameterList() {
        return (KtTypeParameterList) getTypeParameterList();
    }

    public final KtScript getScript() {
        return this.script;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    public FqName getContainingPackageFqName() {
        FqName fqNameParent = this.script.mo7391getFqName().parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        return fqNameParent;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    public List<KtObjectDeclaration> getCompanionObjects() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    /* JADX INFO: renamed from: getScopeAnchor, reason: from getter */
    public KtScript getScript() {
        return this.script;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    public List<KtParameter> getPrimaryConstructorParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    public ClassKind getClassKind() {
        return ClassKind.CLASS;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationContainer
    public List<KtDeclaration> getDeclarations() {
        List<KtDeclaration> declarations = this.script.getDeclarations();
        Intrinsics.checkNotNullExpressionValue(declarations, "getDeclarations(...)");
        return declarations;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    public List<KtAnnotationEntry> getDanglingAnnotations() {
        return CollectionsKt.emptyList();
    }
}
