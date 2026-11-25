package org.jetbrains.kotlin.psi;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.psiUtil.PsiChildRange;

/* JADX INFO: compiled from: createByPattern.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\b\"\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\b\u0012\u0004\u0012\u0002H\u00010\u0005B!\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007¢\u0006\u0002\u0010\tJ%\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u00012\u0006\u0010\u000f\u001a\u00028\u00002\u0006\u0010\u0010\u001a\u00020\u0011H&¢\u0006\u0002\u0010\u0012R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/psi/PsiElementPlaceholderArgumentType;", ExifInterface.GPS_DIRECTION_TRUE, "", "TPlaceholder", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/psi/ArgumentType;", "klass", "Ljava/lang/Class;", "placeholderClass", "(Ljava/lang/Class;Ljava/lang/Class;)V", "getPlaceholderClass", "()Ljava/lang/Class;", "replacePlaceholderElement", "Lorg/jetbrains/kotlin/psi/psiUtil/PsiChildRange;", "placeholder", "argument", "reformat", "", "(Lcom/intellij/psi/PsiElement;Ljava/lang/Object;Z)Lorg/jetbrains/kotlin/psi/psiUtil/PsiChildRange;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
abstract class PsiElementPlaceholderArgumentType<T, TPlaceholder extends PsiElement> extends ArgumentType<T> {
    private final Class<TPlaceholder> placeholderClass;

    public abstract PsiChildRange replacePlaceholderElement(TPlaceholder tplaceholder, T t, boolean z);

    public final Class<TPlaceholder> getPlaceholderClass() {
        return this.placeholderClass;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiElementPlaceholderArgumentType(Class<T> klass, Class<TPlaceholder> placeholderClass) {
        super(klass);
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(placeholderClass, "placeholderClass");
        this.placeholderClass = placeholderClass;
    }
}
