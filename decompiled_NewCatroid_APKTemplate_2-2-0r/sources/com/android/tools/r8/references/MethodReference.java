package com.android.tools.r8.references;

import androidx.exifinterface.media.ExifInterface;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.UT$$ExternalSyntheticLambda5;
import com.android.tools.r8.position.MethodPosition$$ExternalSyntheticLambda0;
import com.android.tools.r8.utils.Y2;
import java.util.List;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class MethodReference {
    static final /* synthetic */ boolean e = true;
    private final ClassReference a;
    private final String b;
    private final List c;
    private final TypeReference d;

    MethodReference(ClassReference classReference, String str, AbstractC1639am abstractC1639am, TypeReference typeReference) {
        boolean z = e;
        if (!z && classReference == null) {
            throw new AssertionError();
        }
        if (!z && str == null) {
            throw new AssertionError();
        }
        this.a = classReference;
        this.b = str;
        this.c = abstractC1639am;
        this.d = typeReference;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MethodReference)) {
            return false;
        }
        MethodReference methodReference = (MethodReference) obj;
        return this.a.equals(methodReference.a) && this.b.equals(methodReference.b) && this.c.equals(methodReference.c) && Objects.equals(this.d, methodReference.d);
    }

    public List<TypeReference> getFormalTypes() {
        return this.c;
    }

    public ClassReference getHolderClass() {
        return this.a;
    }

    public String getMethodDescriptor() {
        return Y2.a("", getFormalTypes(), new MethodPosition$$ExternalSyntheticLambda0(), Y2.a.PARENS) + (getReturnType() == null ? ExifInterface.GPS_MEASUREMENT_INTERRUPTED : getReturnType().getDescriptor());
    }

    public String getMethodName() {
        return this.b;
    }

    public TypeReference getReturnType() {
        return this.d;
    }

    public int hashCode() {
        return Objects.hash(this.a, this.b, this.c, this.d);
    }

    public String toSourceString() {
        StringBuilder sb = new StringBuilder();
        TypeReference typeReference = this.d;
        sb.append(typeReference == null ? PsiKeyword.VOID : typeReference.getTypeName());
        sb.append(" ");
        sb.append(this.a.getTypeName());
        sb.append(".");
        sb.append(this.b);
        sb.append(Y2.a(", ", getFormalTypes(), new UT$$ExternalSyntheticLambda5(), Y2.a.PARENS));
        return sb.toString();
    }

    public String toString() {
        return getHolderClass() + getMethodName() + getMethodDescriptor();
    }
}
