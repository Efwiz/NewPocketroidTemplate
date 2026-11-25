package org.jetbrains.kotlin.asJava.elements;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: KtLightElements.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u0002*\n\b\u0001\u0010\u0003 \u0001*\u00020\u00042\u00020\u0004R\u001c\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u0004\u0018\u00018\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtElement;", "D", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "givenAnnotations", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "getGivenAnnotations", "()Ljava/util/List;", "kotlinOrigin", "getKotlinOrigin", "()Lorg/jetbrains/kotlin/psi/KtElement;", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtLightElement<T extends KtElement, D extends PsiElement> extends PsiElement {
    default List<KtLightAbstractAnnotation> getGivenAnnotations() {
        return null;
    }

    /* JADX INFO: renamed from: getKotlinOrigin */
    T mo6102getKotlinOrigin();
}
