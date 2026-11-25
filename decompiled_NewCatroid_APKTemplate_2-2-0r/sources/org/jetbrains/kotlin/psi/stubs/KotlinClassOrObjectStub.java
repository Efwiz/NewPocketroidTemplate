package org.jetbrains.kotlin.psi.stubs;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.psi.KtClassOrObject;

/* JADX INFO: compiled from: StubInterfaces.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00010\u0004J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H&J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\tH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/KotlinClassOrObjectStub;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinClassifierStub;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinStubWithFqName;", "getSuperNames", "", "", "isLocal", "", "isTopLevel", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinClassOrObjectStub<T extends KtClassOrObject> extends KotlinClassifierStub, KotlinStubWithFqName<T> {
    List<String> getSuperNames();

    boolean isLocal();

    boolean isTopLevel();
}
