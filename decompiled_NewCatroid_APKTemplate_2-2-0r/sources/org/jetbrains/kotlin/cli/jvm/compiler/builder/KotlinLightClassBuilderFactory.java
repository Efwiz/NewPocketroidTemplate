package org.jetbrains.kotlin.cli.jvm.compiler.builder;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.ClassBuilderFactory;
import org.jetbrains.kotlin.codegen.ClassBuilderMode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;

/* JADX INFO: compiled from: KotlinLightClassBuilderFactory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0006\u0010\u0016\u001a\u00020\u0003R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/builder/KotlinLightClassBuilderFactory;", "Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;", "javaFileStub", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/PsiJavaFileStub;", "(Lcom/intellij/psi/impl/java/stubs/PsiJavaFileStub;)V", "stubStack", "Lorg/jetbrains/kotlin/com/intellij/util/containers/Stack;", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "asBytes", "", "builder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "asText", "close", "", "getClassBuilderMode", "Lorg/jetbrains/kotlin/codegen/ClassBuilderMode;", "newClassBuilder", "Lorg/jetbrains/kotlin/cli/jvm/compiler/builder/StubClassBuilder;", "origin", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "result", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinLightClassBuilderFactory implements ClassBuilderFactory {
    private final PsiJavaFileStub javaFileStub;
    private final Stack<StubElement<PsiElement>> stubStack;

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    public void close() {
    }

    public KotlinLightClassBuilderFactory(PsiJavaFileStub javaFileStub) {
        Intrinsics.checkNotNullParameter(javaFileStub, "javaFileStub");
        this.javaFileStub = javaFileStub;
        Stack<StubElement<PsiElement>> stack = new Stack<>();
        Intrinsics.checkNotNull(javaFileStub, "null cannot be cast to non-null type com.intellij.psi.stubs.StubElement<com.intellij.psi.PsiElement>");
        stack.push(javaFileStub);
        this.stubStack = stack;
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    /* JADX INFO: renamed from: asBytes, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ byte[] mo6372asBytes(ClassBuilder classBuilder) {
        return (byte[]) asBytes(classBuilder);
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    public /* bridge */ /* synthetic */ String asText(ClassBuilder classBuilder) {
        return (String) m6373asText(classBuilder);
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    public ClassBuilderMode getClassBuilderMode() {
        ClassBuilderMode LIGHT_CLASSES = ClassBuilderMode.LIGHT_CLASSES;
        Intrinsics.checkNotNullExpressionValue(LIGHT_CLASSES, "LIGHT_CLASSES");
        return LIGHT_CLASSES;
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    public StubClassBuilder newClassBuilder(JvmDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        return new StubClassBuilder(this.stubStack, this.javaFileStub);
    }

    /* JADX INFO: renamed from: asText, reason: collision with other method in class */
    public Void m6373asText(ClassBuilder builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        throw new UnsupportedOperationException("asText is not implemented");
    }

    public Void asBytes(ClassBuilder builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        throw new UnsupportedOperationException("asBytes is not implemented");
    }

    public final PsiJavaFileStub result() {
        StubElement<PsiElement> stubElementPop = this.stubStack.pop();
        if (stubElementPop != this.javaFileStub) {
            KotlinLightClassBuilderFactoryKt.LOG.error("Unbalanced stack operations: " + stubElementPop);
        }
        return this.javaFileStub;
    }
}
