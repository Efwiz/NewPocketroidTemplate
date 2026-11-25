package org.jetbrains.kotlin.codegen.extensions;

import com.reandroid.dex.sections.Marker;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.ClassBuilderFactory;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: ClassBuilderInterceptorExtension.kt */
/* JADX INFO: loaded from: classes3.dex */
@Deprecated(level = DeprecationLevel.ERROR, message = "This extension is only supported in K1 and will not work properly in K2. Please migrate to org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension or org.jetbrains.kotlin.backend.jvm.extensions.ClassGeneratorExtension.")
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u0000 \t2\u00020\u0001:\u0001\tJ \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/extensions/ClassBuilderInterceptorExtension;", "", "interceptClassBuilderFactory", "Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;", "interceptedFactory", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "diagnostics", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ClassBuilderInterceptorExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    ClassBuilderFactory interceptClassBuilderFactory(ClassBuilderFactory interceptedFactory, BindingContext bindingContext, DiagnosticSink diagnostics);

    /* JADX INFO: compiled from: ClassBuilderInterceptorExtension.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/codegen/extensions/ClassBuilderInterceptorExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/codegen/extensions/ClassBuilderInterceptorExtension;", "()V", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<ClassBuilderInterceptorExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.classBuilderFactoryInterceptorExtension", ClassBuilderInterceptorExtension.class);
        }
    }
}
