package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;

/* JADX INFO: compiled from: DelegatingClassBuilderFactory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016J\u0014\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0016J\t\u0010\f\u001a\u00020\rH\u0096\u0001J\t\u0010\u000e\u001a\u00020\u000fH\u0097\u0001J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H&R\u0014\u0010\u0002\u001a\u00020\u0001X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/codegen/DelegatingClassBuilderFactory;", "Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;)V", "getDelegate", "()Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;", "asBytes", "", "builder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "asText", "", "close", "", "getClassBuilderMode", "Lorg/jetbrains/kotlin/codegen/ClassBuilderMode;", "newClassBuilder", "Lorg/jetbrains/kotlin/codegen/DelegatingClassBuilder;", "origin", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DelegatingClassBuilderFactory implements ClassBuilderFactory {
    private final ClassBuilderFactory delegate;

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    public void close() {
        this.delegate.close();
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    public ClassBuilderMode getClassBuilderMode() {
        return this.delegate.getClassBuilderMode();
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    public abstract DelegatingClassBuilder newClassBuilder(JvmDeclarationOrigin origin);

    public DelegatingClassBuilderFactory(ClassBuilderFactory delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    protected final ClassBuilderFactory getDelegate() {
        return this.delegate;
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    /* JADX INFO: renamed from: asBytes */
    public byte[] mo6372asBytes(ClassBuilder builder) {
        ClassBuilderFactory classBuilderFactory = this.delegate;
        Intrinsics.checkNotNull(builder, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.DelegatingClassBuilder");
        return classBuilderFactory.mo6372asBytes(((DelegatingClassBuilder) builder).getDelegate());
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    public String asText(ClassBuilder builder) {
        ClassBuilderFactory classBuilderFactory = this.delegate;
        Intrinsics.checkNotNull(builder, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.DelegatingClassBuilder");
        return classBuilderFactory.asText(((DelegatingClassBuilder) builder).getDelegate());
    }
}
