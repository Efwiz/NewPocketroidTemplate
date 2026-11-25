package org.jetbrains.kotlin.com.intellij.openapi.diagnostic;

import java.lang.reflect.Method;
import java.util.logging.Level;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LoggerRt {
    private static Factory ourFactory;

    private interface Factory {
        LoggerRt getInstance(String str);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/diagnostic/LoggerRt";
        } else if (i == 2) {
            objArr[0] = "clazz";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[0] = "t";
        } else {
            objArr[0] = "category";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/diagnostic/LoggerRt";
        } else {
            objArr[1] = "getInstance";
        }
        if (i != 1) {
            if (i == 3) {
                objArr[2] = CompilerOptions.INFO;
            } else if (i == 4) {
                objArr[2] = CommonCompilerArguments.WARN;
            } else if (i != 5) {
                objArr[2] = "getInstance";
            } else {
                objArr[2] = "error";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public abstract void error(String str, Throwable th);

    public abstract void info(String str, Throwable th);

    public abstract void warn(String str, Throwable th);

    private static synchronized Factory getFactory() {
        if (ourFactory == null) {
            try {
                try {
                    ourFactory = new IdeaFactory();
                } catch (Throwable unused) {
                    ourFactory = new Slf4JFactory();
                }
            } catch (Throwable unused2) {
                ourFactory = new JavaFactory();
            }
        }
        return ourFactory;
    }

    public static LoggerRt getInstance(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        LoggerRt factory = getFactory().getInstance(str);
        if (factory == null) {
            $$$reportNull$$$0(1);
        }
        return factory;
    }

    public static LoggerRt getInstance(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(2);
        }
        return getInstance("#" + cls.getName());
    }

    public void info(String str) {
        info(str, null);
    }

    public void info(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(3);
        }
        info(th.getMessage(), th);
    }

    public void warn(String str) {
        warn(str, null);
    }

    public void warn(Throwable th) {
        if (th == null) {
            $$$reportNull$$$0(4);
        }
        warn(th.getMessage(), th);
    }

    private static class JavaFactory implements Factory {
        private JavaFactory() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt.Factory
        public LoggerRt getInstance(String str) {
            final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(str);
            return new LoggerRt() { // from class: org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt.JavaFactory.1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt
                public void info(String str2, Throwable th) {
                    logger.log(Level.INFO, str2, th);
                }

                @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt
                public void warn(String str2, Throwable th) {
                    logger.log(Level.WARNING, str2, th);
                }

                @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt
                public void error(String str2, Throwable th) {
                    logger.log(Level.SEVERE, str2, th);
                }
            };
        }
    }

    private static abstract class ReflectionBasedFactory implements Factory {
        protected abstract void error(String str, Throwable th, Object obj) throws Exception;

        protected abstract Object getLogger(String str) throws Exception;

        protected abstract void info(String str, Throwable th, Object obj) throws Exception;

        protected abstract void warn(String str, Throwable th, Object obj) throws Exception;

        private ReflectionBasedFactory() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt.Factory
        public LoggerRt getInstance(String str) {
            try {
                final Object logger = getLogger(str);
                return new LoggerRt() { // from class: org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt.ReflectionBasedFactory.1
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt
                    public void info(String str2, Throwable th) {
                        try {
                            ReflectionBasedFactory.this.info(str2, th, logger);
                        } catch (Exception unused) {
                        }
                    }

                    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt
                    public void warn(String str2, Throwable th) {
                        try {
                            ReflectionBasedFactory.this.warn(str2, th, logger);
                        } catch (Exception unused) {
                        }
                    }

                    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt
                    public void error(String str2, Throwable th) {
                        try {
                            ReflectionBasedFactory.this.error(str2, th, logger);
                        } catch (Exception unused) {
                        }
                    }
                };
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final class IdeaFactory extends ReflectionBasedFactory {
        private final Method myError;
        private final Method myGetInstance;
        private final Method myInfo;
        private final Method myWarn;

        private IdeaFactory() throws Exception {
            super();
            Class<?> cls = Class.forName("org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger");
            Method method = cls.getMethod("getInstance", String.class);
            this.myGetInstance = method;
            method.setAccessible(true);
            Method method2 = cls.getMethod(CompilerOptions.INFO, String.class, Throwable.class);
            this.myInfo = method2;
            method2.setAccessible(true);
            this.myWarn = cls.getMethod(CommonCompilerArguments.WARN, String.class, Throwable.class);
            method2.setAccessible(true);
            Method method3 = cls.getMethod("error", String.class, Throwable.class);
            this.myError = method3;
            method3.setAccessible(true);
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt.ReflectionBasedFactory
        protected void error(String str, Throwable th, Object obj) throws Exception {
            this.myError.invoke(obj, str, th);
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt.ReflectionBasedFactory
        protected void warn(String str, Throwable th, Object obj) throws Exception {
            this.myWarn.invoke(obj, str, th);
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt.ReflectionBasedFactory
        protected void info(String str, Throwable th, Object obj) throws Exception {
            this.myInfo.invoke(obj, str, th);
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt.ReflectionBasedFactory
        protected Object getLogger(String str) throws Exception {
            return this.myGetInstance.invoke(null, str);
        }
    }

    private static final class Slf4JFactory extends ReflectionBasedFactory {
        private final Method myError;
        private final Method myGetLogger;
        private final Method myInfo;
        private final Method myWarn;

        private Slf4JFactory() throws Exception {
            super();
            Method method = Class.forName("org.slf4j.LoggerFactory").getMethod("getLogger", String.class);
            this.myGetLogger = method;
            method.setAccessible(true);
            Class<?> cls = Class.forName("org.slf4j.Logger");
            Method method2 = cls.getMethod(CompilerOptions.INFO, String.class, Throwable.class);
            this.myInfo = method2;
            method2.setAccessible(true);
            this.myWarn = cls.getMethod(CommonCompilerArguments.WARN, String.class, Throwable.class);
            method2.setAccessible(true);
            Method method3 = cls.getMethod("error", String.class, Throwable.class);
            this.myError = method3;
            method3.setAccessible(true);
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt.ReflectionBasedFactory
        protected void error(String str, Throwable th, Object obj) throws Exception {
            this.myError.invoke(obj, str, th);
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt.ReflectionBasedFactory
        protected void warn(String str, Throwable th, Object obj) throws Exception {
            this.myWarn.invoke(obj, str, th);
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt.ReflectionBasedFactory
        protected void info(String str, Throwable th, Object obj) throws Exception {
            this.myInfo.invoke(obj, str, th);
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.LoggerRt.ReflectionBasedFactory
        protected Object getLogger(String str) throws Exception {
            return this.myGetLogger.invoke(null, str);
        }
    }
}
