package org.jetbrains.kotlin.com.intellij.openapi.components;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionDescriptor;
import org.jetbrains.kotlin.com.intellij.util.xmlb.annotations.Attribute;

/* JADX INFO: loaded from: classes3.dex */
public final class ServiceDescriptor {

    @Attribute
    public final ClientKind client;

    @Attribute
    public final String configurationSchemaKey;

    @Attribute
    public final String headlessImplementation;

    @Attribute
    public final ExtensionDescriptor.Os os;

    @Attribute
    public final boolean overrides;

    @Attribute
    public final PreloadMode preload;

    @Attribute
    public final String serviceImplementation;

    @Attribute
    public final String serviceInterface;

    @Attribute
    public final String testServiceImplementation;

    public enum ClientKind {
        ALL,
        GUEST,
        LOCAL
    }

    public enum PreloadMode {
        TRUE,
        FALSE,
        AWAIT,
        NOT_HEADLESS,
        NOT_LIGHT_EDIT
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "preload", "org/jetbrains/kotlin/com/intellij/openapi/components/ServiceDescriptor", "<init>"));
    }

    public ServiceDescriptor(String str, String str2, String str3, String str4, boolean z, String str5, PreloadMode preloadMode, ClientKind clientKind, ExtensionDescriptor.Os os) {
        if (preloadMode == null) {
            $$$reportNull$$$0(0);
        }
        this.serviceInterface = str;
        this.serviceImplementation = str2;
        this.testServiceImplementation = str3;
        this.headlessImplementation = str4;
        this.overrides = z;
        this.configurationSchemaKey = str5;
        this.preload = preloadMode;
        this.client = clientKind;
        this.os = os;
    }

    public String toString() {
        return "ServiceDescriptor(interface='" + this.serviceInterface + "', serviceImplementation='" + this.serviceImplementation + "', testServiceImplementation='" + this.testServiceImplementation + "', headlessImplementation='" + this.headlessImplementation + "', overrides=" + this.overrides + ", configurationSchemaKey='" + this.configurationSchemaKey + "', preload=" + this.preload + ", client=" + this.client + ')';
    }
}
