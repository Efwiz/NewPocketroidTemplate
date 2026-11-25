package org.jetbrains.kotlin.psi.stubs;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtImportDirective;

/* JADX INFO: compiled from: StubInterfaces.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\n\u0010\u0003\u001a\u0004\u0018\u00010\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/KotlinImportDirectiveStub;", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/psi/KtImportDirective;", "getImportedFqName", "Lorg/jetbrains/kotlin/name/FqName;", "isAllUnder", "", "isValid", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinImportDirectiveStub extends StubElement<KtImportDirective> {
    FqName getImportedFqName();

    boolean isAllUnder();

    boolean isValid();
}
