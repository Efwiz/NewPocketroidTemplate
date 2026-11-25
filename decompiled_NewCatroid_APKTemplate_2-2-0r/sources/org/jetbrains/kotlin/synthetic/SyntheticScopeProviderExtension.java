package org.jetbrains.kotlin.synthetic;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.extensions.ProjectExtensionDescriptor;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScope;

/* JADX INFO: compiled from: JavaSyntheticScopes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \t2\u00020\u0001:\u0001\tJ\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/synthetic/SyntheticScopeProviderExtension;", "", "getScopes", "", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScope;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "javaSyntheticPropertiesScope", "Lorg/jetbrains/kotlin/synthetic/JavaSyntheticPropertiesScope;", "Companion", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SyntheticScopeProviderExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    List<SyntheticScope> getScopes(ModuleDescriptor moduleDescriptor, JavaSyntheticPropertiesScope javaSyntheticPropertiesScope);

    /* JADX INFO: compiled from: JavaSyntheticScopes.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/synthetic/SyntheticScopeProviderExtension$Companion;", "Lorg/jetbrains/kotlin/extensions/ProjectExtensionDescriptor;", "Lorg/jetbrains/kotlin/synthetic/SyntheticScopeProviderExtension;", "()V", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ProjectExtensionDescriptor<SyntheticScopeProviderExtension> {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
            super("org.jetbrains.kotlin.syntheticScopeProviderExtension", SyntheticScopeProviderExtension.class);
        }
    }
}
