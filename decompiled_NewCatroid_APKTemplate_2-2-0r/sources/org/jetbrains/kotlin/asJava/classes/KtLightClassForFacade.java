package org.jetbrains.kotlin.asJava.classes;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: KtLightClassForFacade.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForFacade;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "facadeClassFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFacadeClassFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "getFiles", "()Ljava/util/Collection;", "multiFileClass", "", "getMultiFileClass", "()Z", "getName", "", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtLightClassForFacade extends KtLightClass {
    FqName getFacadeClassFqName();

    Collection<KtFile> getFiles();

    boolean getMultiFileClass();

    @Override // org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    default String getName() {
        String strAsString = getFacadeClassFqName().shortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }
}
