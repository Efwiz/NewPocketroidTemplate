package net.bytebuddy;

import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.utility.RandomString;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public interface NamingStrategy {
    public static final String BYTE_BUDDY_RENAME_PACKAGE = "net.bytebuddy.renamed";
    public static final String NO_PREFIX = "";

    String rebase(TypeDescription typeDescription);

    String redefine(TypeDescription typeDescription);

    String subclass(TypeDescription.Generic generic);

    public static abstract class AbstractBase implements NamingStrategy {
        protected abstract String name(TypeDescription typeDescription);

        @Override // net.bytebuddy.NamingStrategy
        public String subclass(TypeDescription.Generic generic) {
            return name(generic.asErasure());
        }

        @Override // net.bytebuddy.NamingStrategy
        public String redefine(TypeDescription typeDescription) {
            return typeDescription.getName();
        }

        @Override // net.bytebuddy.NamingStrategy
        public String rebase(TypeDescription typeDescription) {
            return typeDescription.getName();
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class Suffixing extends AbstractBase {
        private static final String JAVA_PACKAGE = "java.";
        private final BaseNameResolver baseNameResolver;
        private final String javaLangPackagePrefix;
        private final String suffix;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Suffixing suffixing = (Suffixing) obj;
            return this.suffix.equals(suffixing.suffix) && this.javaLangPackagePrefix.equals(suffixing.javaLangPackagePrefix) && this.baseNameResolver.equals(suffixing.baseNameResolver);
        }

        public int hashCode() {
            return (((((getClass().hashCode() * 31) + this.suffix.hashCode()) * 31) + this.javaLangPackagePrefix.hashCode()) * 31) + this.baseNameResolver.hashCode();
        }

        public Suffixing(String str) {
            this(str, BaseNameResolver.ForUnnamedType.INSTANCE);
        }

        public Suffixing(String str, String str2) {
            this(str, BaseNameResolver.ForUnnamedType.INSTANCE, str2);
        }

        public Suffixing(String str, BaseNameResolver baseNameResolver) {
            this(str, baseNameResolver, NamingStrategy.BYTE_BUDDY_RENAME_PACKAGE);
        }

        public Suffixing(String str, BaseNameResolver baseNameResolver, String str2) {
            this.suffix = str;
            this.baseNameResolver = baseNameResolver;
            this.javaLangPackagePrefix = str2;
        }

        @Override // net.bytebuddy.NamingStrategy.AbstractBase
        protected String name(TypeDescription typeDescription) {
            String strResolve = this.baseNameResolver.resolve(typeDescription);
            if (strResolve.startsWith(JAVA_PACKAGE) && !this.javaLangPackagePrefix.equals("")) {
                strResolve = this.javaLangPackagePrefix + "." + strResolve;
            }
            return strResolve + "$" + this.suffix;
        }

        public interface BaseNameResolver {
            String resolve(TypeDescription typeDescription);

            public enum ForUnnamedType implements BaseNameResolver {
                INSTANCE;

                @Override // net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver
                public String resolve(TypeDescription typeDescription) {
                    return typeDescription.getName();
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForGivenType implements BaseNameResolver {
                private final TypeDescription typeDescription;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.typeDescription.equals(((ForGivenType) obj).typeDescription);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.typeDescription.hashCode();
                }

                public ForGivenType(TypeDescription typeDescription) {
                    this.typeDescription = typeDescription;
                }

                @Override // net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver
                public String resolve(TypeDescription typeDescription) {
                    return this.typeDescription.getName();
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForFixedValue implements BaseNameResolver {

                /* JADX INFO: renamed from: name, reason: collision with root package name */
                private final String f332name;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.f332name.equals(((ForFixedValue) obj).f332name);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.f332name.hashCode();
                }

                public ForFixedValue(String str) {
                    this.f332name = str;
                }

                @Override // net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver
                public String resolve(TypeDescription typeDescription) {
                    return this.f332name;
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class WithCallerSuffix implements BaseNameResolver {
                private final BaseNameResolver delegate;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.delegate.equals(((WithCallerSuffix) obj).delegate);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.delegate.hashCode();
                }

                public WithCallerSuffix(BaseNameResolver baseNameResolver) {
                    this.delegate = baseNameResolver;
                }

                /* JADX WARN: Code duplicated, block: B:12:0x004a  */
                /* JADX WARN: Code duplicated, block: B:14:0x0070  */
                @Override // net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver
                public String resolve(TypeDescription typeDescription) {
                    String str;
                    boolean z = false;
                    for (StackTraceElement stackTraceElement : new Throwable().getStackTrace()) {
                        if (stackTraceElement.getClassName().equals(ByteBuddy.class.getName())) {
                            z = true;
                        } else if (z) {
                            str = stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName();
                            if (str != null) {
                                throw new IllegalStateException("Base name resolver not invoked via " + ByteBuddy.class);
                            }
                            return this.delegate.resolve(typeDescription) + "$" + str.replace('.', '$');
                        }
                    }
                    str = null;
                    if (str != null) {
                        throw new IllegalStateException("Base name resolver not invoked via " + ByteBuddy.class);
                    }
                    return this.delegate.resolve(typeDescription) + "$" + str.replace('.', '$');
                }
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class SuffixingRandom extends Suffixing {

        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.IGNORE)
        private final RandomString randomString;

        @Override // net.bytebuddy.NamingStrategy.Suffixing
        public boolean equals(@MaybeNull Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass();
        }

        @Override // net.bytebuddy.NamingStrategy.Suffixing
        public int hashCode() {
            return super.hashCode();
        }

        public SuffixingRandom(String str) {
            this(str, Suffixing.BaseNameResolver.ForUnnamedType.INSTANCE);
        }

        public SuffixingRandom(String str, String str2) {
            this(str, Suffixing.BaseNameResolver.ForUnnamedType.INSTANCE, str2);
        }

        @Deprecated
        public SuffixingRandom(String str, BaseNameResolver baseNameResolver) {
            this(str, (Suffixing.BaseNameResolver) baseNameResolver);
        }

        public SuffixingRandom(String str, Suffixing.BaseNameResolver baseNameResolver) {
            this(str, baseNameResolver, NamingStrategy.BYTE_BUDDY_RENAME_PACKAGE);
        }

        @Deprecated
        public SuffixingRandom(String str, BaseNameResolver baseNameResolver, String str2) {
            this(str, (Suffixing.BaseNameResolver) baseNameResolver, str2);
        }

        public SuffixingRandom(String str, Suffixing.BaseNameResolver baseNameResolver, String str2) {
            this(str, baseNameResolver, str2, new RandomString());
        }

        @Deprecated
        public SuffixingRandom(String str, BaseNameResolver baseNameResolver, String str2, RandomString randomString) {
            this(str, (Suffixing.BaseNameResolver) baseNameResolver, str2, randomString);
        }

        public SuffixingRandom(String str, Suffixing.BaseNameResolver baseNameResolver, String str2, RandomString randomString) {
            super(str, baseNameResolver, str2);
            this.randomString = randomString;
        }

        @Override // net.bytebuddy.NamingStrategy.Suffixing, net.bytebuddy.NamingStrategy.AbstractBase
        protected String name(TypeDescription typeDescription) {
            return super.name(typeDescription) + "$" + this.randomString.nextString();
        }

        @Deprecated
        public interface BaseNameResolver extends Suffixing.BaseNameResolver {

            @Deprecated
            public enum ForUnnamedType implements BaseNameResolver {
                INSTANCE;

                @Override // net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver
                public String resolve(TypeDescription typeDescription) {
                    return typeDescription.getName();
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            @Deprecated
            public static class ForGivenType extends Suffixing.BaseNameResolver.ForGivenType implements BaseNameResolver {
                @Override // net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver.ForGivenType
                public boolean equals(@MaybeNull Object obj) {
                    if (!super.equals(obj)) {
                        return false;
                    }
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass();
                }

                @Override // net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver.ForGivenType
                public int hashCode() {
                    return super.hashCode();
                }

                public ForGivenType(TypeDescription typeDescription) {
                    super(typeDescription);
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            @Deprecated
            public static class ForFixedValue extends Suffixing.BaseNameResolver.ForFixedValue implements BaseNameResolver {
                @Override // net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver.ForFixedValue
                public boolean equals(@MaybeNull Object obj) {
                    if (!super.equals(obj)) {
                        return false;
                    }
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass();
                }

                @Override // net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver.ForFixedValue
                public int hashCode() {
                    return super.hashCode();
                }

                public ForFixedValue(String str) {
                    super(str);
                }
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class PrefixingRandom extends AbstractBase {
        private final String prefix;

        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.IGNORE)
        private final RandomString randomString = new RandomString();

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.prefix.equals(((PrefixingRandom) obj).prefix);
        }

        public int hashCode() {
            return (getClass().hashCode() * 31) + this.prefix.hashCode();
        }

        public PrefixingRandom(String str) {
            this.prefix = str;
        }

        @Override // net.bytebuddy.NamingStrategy.AbstractBase
        protected String name(TypeDescription typeDescription) {
            return this.prefix + "." + typeDescription.getName() + "$" + this.randomString.nextString();
        }
    }
}
