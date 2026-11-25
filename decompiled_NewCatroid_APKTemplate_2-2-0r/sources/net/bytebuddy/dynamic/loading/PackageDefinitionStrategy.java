package net.bytebuddy.dynamic.loading;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public interface PackageDefinitionStrategy {
    Definition define(ClassLoader classLoader, String str, String str2);

    public enum NoOp implements PackageDefinitionStrategy {
        INSTANCE;

        @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy
        public Definition define(ClassLoader classLoader, String str, String str2) {
            return Definition.Undefined.INSTANCE;
        }
    }

    public enum Trivial implements PackageDefinitionStrategy {
        INSTANCE;

        @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy
        public Definition define(ClassLoader classLoader, String str, String str2) {
            return Definition.Trivial.INSTANCE;
        }
    }

    public interface Definition {
        @MaybeNull
        String getImplementationTitle();

        @MaybeNull
        String getImplementationVendor();

        @MaybeNull
        String getImplementationVersion();

        @MaybeNull
        URL getSealBase();

        @MaybeNull
        String getSpecificationTitle();

        @MaybeNull
        String getSpecificationVendor();

        @MaybeNull
        String getSpecificationVersion();

        boolean isCompatibleTo(Package r1);

        boolean isDefined();

        public enum Undefined implements Definition {
            INSTANCE;

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            public boolean isDefined() {
                return false;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            public String getSpecificationTitle() {
                throw new IllegalStateException("Cannot read property of undefined package");
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            public String getSpecificationVersion() {
                throw new IllegalStateException("Cannot read property of undefined package");
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            public String getSpecificationVendor() {
                throw new IllegalStateException("Cannot read property of undefined package");
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            public String getImplementationTitle() {
                throw new IllegalStateException("Cannot read property of undefined package");
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            public String getImplementationVersion() {
                throw new IllegalStateException("Cannot read property of undefined package");
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            public String getImplementationVendor() {
                throw new IllegalStateException("Cannot read property of undefined package");
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            public URL getSealBase() {
                throw new IllegalStateException("Cannot read property of undefined package");
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            public boolean isCompatibleTo(Package r2) {
                throw new IllegalStateException("Cannot check compatibility to undefined package");
            }
        }

        public enum Trivial implements Definition {
            INSTANCE;


            @AlwaysNull
            private static final String NO_VALUE = null;

            @AlwaysNull
            private static final URL NOT_SEALED = null;

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            public boolean isCompatibleTo(Package r1) {
                return true;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            public boolean isDefined() {
                return true;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            @MaybeNull
            public String getSpecificationTitle() {
                return NO_VALUE;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            @MaybeNull
            public String getSpecificationVersion() {
                return NO_VALUE;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            @MaybeNull
            public String getSpecificationVendor() {
                return NO_VALUE;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            @MaybeNull
            public String getImplementationTitle() {
                return NO_VALUE;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            @MaybeNull
            public String getImplementationVersion() {
                return NO_VALUE;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            public String getImplementationVendor() {
                return NO_VALUE;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            @MaybeNull
            public URL getSealBase() {
                return NOT_SEALED;
            }
        }

        public static class Simple implements Definition {

            @MaybeNull
            private final String implementationTitle;

            @MaybeNull
            private final String implementationVendor;

            @MaybeNull
            private final String implementationVersion;

            @MaybeNull
            protected final URL sealBase;

            @MaybeNull
            private final String specificationTitle;

            @MaybeNull
            private final String specificationVendor;

            @MaybeNull
            private final String specificationVersion;

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            public boolean isDefined() {
                return true;
            }

            public Simple(@MaybeNull String str, @MaybeNull String str2, @MaybeNull String str3, @MaybeNull String str4, @MaybeNull String str5, @MaybeNull String str6, @MaybeNull URL url) {
                this.specificationTitle = str;
                this.specificationVersion = str2;
                this.specificationVendor = str3;
                this.implementationTitle = str4;
                this.implementationVersion = str5;
                this.implementationVendor = str6;
                this.sealBase = url;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            @MaybeNull
            public String getSpecificationTitle() {
                return this.specificationTitle;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            @MaybeNull
            public String getSpecificationVersion() {
                return this.specificationVersion;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            @MaybeNull
            public String getSpecificationVendor() {
                return this.specificationVendor;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            @MaybeNull
            public String getImplementationTitle() {
                return this.implementationTitle;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            @MaybeNull
            public String getImplementationVersion() {
                return this.implementationVersion;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            @MaybeNull
            public String getImplementationVendor() {
                return this.implementationVendor;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            @MaybeNull
            public URL getSealBase() {
                return this.sealBase;
            }

            @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition
            public boolean isCompatibleTo(Package r2) {
                URL url = this.sealBase;
                if (url == null) {
                    return !r2.isSealed();
                }
                return r2.isSealed(url);
            }

            public int hashCode() {
                String str = this.specificationTitle;
                int iHashCode = (str != null ? str.hashCode() : 0) * 31;
                String str2 = this.specificationVersion;
                int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
                String str3 = this.specificationVendor;
                int iHashCode3 = (iHashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
                String str4 = this.implementationTitle;
                int iHashCode4 = (iHashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31;
                String str5 = this.implementationVersion;
                int iHashCode5 = (iHashCode4 + (str5 != null ? str5.hashCode() : 0)) * 31;
                String str6 = this.implementationVendor;
                int iHashCode6 = (iHashCode5 + (str6 != null ? str6.hashCode() : 0)) * 31;
                URL url = this.sealBase;
                return iHashCode6 + (url != null ? url.hashCode() : 0);
            }

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                Simple simple = (Simple) obj;
                String str = this.specificationTitle;
                if (str == null ? simple.specificationTitle == null : str.equals(simple.specificationTitle)) {
                    String str2 = this.specificationVersion;
                    if (str2 == null ? simple.specificationVersion == null : str2.equals(simple.specificationVersion)) {
                        String str3 = this.specificationVendor;
                        if (str3 == null ? simple.specificationVendor == null : str3.equals(simple.specificationVendor)) {
                            String str4 = this.implementationTitle;
                            if (str4 == null ? simple.implementationTitle == null : str4.equals(simple.implementationTitle)) {
                                String str5 = this.implementationVersion;
                                if (str5 == null ? simple.implementationVersion == null : str5.equals(simple.implementationVersion)) {
                                    String str6 = this.implementationVendor;
                                    if (str6 == null ? simple.implementationVendor == null : str6.equals(simple.implementationVendor)) {
                                        URL url = this.sealBase;
                                        if (url != null) {
                                            if (url.equals(simple.sealBase)) {
                                                return true;
                                            }
                                        } else if (simple.sealBase == null) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return false;
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class ManifestReading implements PackageDefinitionStrategy {
        private static final Attributes.Name[] ATTRIBUTE_NAMES = {Attributes.Name.SPECIFICATION_TITLE, Attributes.Name.SPECIFICATION_VERSION, Attributes.Name.SPECIFICATION_VENDOR, Attributes.Name.IMPLEMENTATION_TITLE, Attributes.Name.IMPLEMENTATION_VERSION, Attributes.Name.IMPLEMENTATION_VENDOR, Attributes.Name.SEALED};

        @AlwaysNull
        private static final URL NOT_SEALED = null;
        private final SealBaseLocator sealBaseLocator;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.sealBaseLocator.equals(((ManifestReading) obj).sealBaseLocator);
        }

        public int hashCode() {
            return (getClass().hashCode() * 31) + this.sealBaseLocator.hashCode();
        }

        public ManifestReading() {
            this(new SealBaseLocator.ForTypeResourceUrl());
        }

        public ManifestReading(SealBaseLocator sealBaseLocator) {
            this.sealBaseLocator = sealBaseLocator;
        }

        @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy
        public Definition define(ClassLoader classLoader, String str, String str2) {
            InputStream resourceAsStream = classLoader.getResourceAsStream("META-INF/MANIFEST.MF");
            if (resourceAsStream != null) {
                try {
                    try {
                        Manifest manifest = new Manifest(resourceAsStream);
                        HashMap map = new HashMap();
                        Attributes mainAttributes = manifest.getMainAttributes();
                        if (mainAttributes != null) {
                            for (Attributes.Name name2 : ATTRIBUTE_NAMES) {
                                map.put(name2, mainAttributes.getValue(name2));
                            }
                        }
                        Attributes attributes = manifest.getAttributes(str.replace('.', '/').concat("/"));
                        if (attributes != null) {
                            for (Attributes.Name name3 : ATTRIBUTE_NAMES) {
                                String value = attributes.getValue(name3);
                                if (value != null) {
                                    map.put(name3, value);
                                }
                            }
                        }
                        return new Definition.Simple((String) map.get(Attributes.Name.SPECIFICATION_TITLE), (String) map.get(Attributes.Name.SPECIFICATION_VERSION), (String) map.get(Attributes.Name.SPECIFICATION_VENDOR), (String) map.get(Attributes.Name.IMPLEMENTATION_TITLE), (String) map.get(Attributes.Name.IMPLEMENTATION_VERSION), (String) map.get(Attributes.Name.IMPLEMENTATION_VENDOR), Boolean.parseBoolean((String) map.get(Attributes.Name.SEALED)) ? this.sealBaseLocator.findSealBase(classLoader, str2) : NOT_SEALED);
                    } finally {
                        resourceAsStream.close();
                    }
                } catch (IOException e) {
                    throw new IllegalStateException("Error while reading manifest file", e);
                }
            }
            return Definition.Trivial.INSTANCE;
        }

        public interface SealBaseLocator {
            @MaybeNull
            URL findSealBase(ClassLoader classLoader, String str);

            public enum NonSealing implements SealBaseLocator {
                INSTANCE;

                @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.ManifestReading.SealBaseLocator
                @MaybeNull
                public URL findSealBase(ClassLoader classLoader, String str) {
                    return ManifestReading.NOT_SEALED;
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForFixedValue implements SealBaseLocator {

                @MaybeNull
                @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
                private final URL sealBase;

                public ForFixedValue(@MaybeNull URL url) {
                    this.sealBase = url;
                }

                @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.ManifestReading.SealBaseLocator
                @MaybeNull
                public URL findSealBase(ClassLoader classLoader, String str) {
                    return this.sealBase;
                }

                public int hashCode() {
                    URL url = this.sealBase;
                    if (url == null) {
                        return 17;
                    }
                    return url.hashCode();
                }

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    URL url = this.sealBase;
                    URL url2 = ((ForFixedValue) obj).sealBase;
                    if (url == null) {
                        return url2 == null;
                    }
                    return url.equals(url2);
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForTypeResourceUrl implements SealBaseLocator {
                private static final String CLASS_FILE_EXTENSION = ".class";
                private static final int EXCLUDE_INITIAL_SLASH = 1;
                private static final String FILE_SYSTEM = "file";
                private static final String JAR_FILE = "jar";
                private static final String RUNTIME_IMAGE = "jrt";
                private final SealBaseLocator fallback;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.fallback.equals(((ForTypeResourceUrl) obj).fallback);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.fallback.hashCode();
                }

                public ForTypeResourceUrl() {
                    this(NonSealing.INSTANCE);
                }

                public ForTypeResourceUrl(SealBaseLocator sealBaseLocator) {
                    this.fallback = sealBaseLocator;
                }

                @Override // net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.ManifestReading.SealBaseLocator
                @MaybeNull
                public URL findSealBase(ClassLoader classLoader, String str) {
                    URL resource = classLoader.getResource(str.replace('.', '/') + ".class");
                    if (resource != null) {
                        try {
                            if (resource.getProtocol().equals("jar")) {
                                return URI.create(resource.getPath().substring(0, resource.getPath().indexOf(33))).toURL();
                            }
                            if (resource.getProtocol().equals("file")) {
                                return resource;
                            }
                            if (resource.getProtocol().equals("jrt")) {
                                String path = resource.getPath();
                                int iIndexOf = path.indexOf(47, 1);
                                return iIndexOf == -1 ? resource : URI.create("jrt:" + path.substring(0, iIndexOf)).toURL();
                            }
                        } catch (MalformedURLException e) {
                            throw new IllegalStateException("Unexpected URL: " + resource, e);
                        }
                    }
                    return this.fallback.findSealBase(classLoader, str);
                }
            }
        }
    }
}
