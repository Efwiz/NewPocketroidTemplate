package org.jetbrains.kotlin.psi.stubs;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtConstantExpression;

/* JADX INFO: compiled from: StubInterfaces.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/KotlinConstantExpressionStub;", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/psi/KtConstantExpression;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/psi/stubs/ConstantValueKind;", "value", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinConstantExpressionStub extends StubElement<KtConstantExpression> {
    ConstantValueKind kind();

    String value();
}
