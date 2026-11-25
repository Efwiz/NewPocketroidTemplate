package org.jetbrains.kotlin.psi.stubs;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: StubInterfaces.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003J\n\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/KotlinStubWithFqName;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiNamedElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/NamedStub;", "getFqName", "Lorg/jetbrains/kotlin/name/FqName;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinStubWithFqName<T extends PsiNamedElement> extends NamedStub<T> {
    /* JADX INFO: renamed from: getFqName */
    FqName mo7402getFqName();
}
