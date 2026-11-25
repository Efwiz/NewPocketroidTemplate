package org.jetbrains.kotlin.psi.stubs;

import kotlin.Metadata;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;

/* JADX INFO: compiled from: StubInterfaces.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/KotlinCallableStubBase;", "TDeclaration", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinStubWithFqName;", "isExtension", "", "isTopLevel", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinCallableStubBase<TDeclaration extends KtCallableDeclaration> extends KotlinStubWithFqName<TDeclaration> {
    boolean isExtension();

    boolean isTopLevel();
}
