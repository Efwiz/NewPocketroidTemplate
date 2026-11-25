package org.eclipse.jgit.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import org.ietf.jgss.GSSManager;

/* JADX INFO: loaded from: classes14.dex */
public abstract class GSSManagerFactory {
    public abstract GSSManager newInstance(URL url);

    /* JADX WARN: Multi-variable type inference failed */
    public static GSSManagerFactory detect() {
        SunGSSManagerFactory sunGSSManagerFactory = null;
        Object[] objArr = 0;
        if (SunGSSManagerFactory.isSupported()) {
            return new SunGSSManagerFactory(sunGSSManagerFactory);
        }
        return new DefaultGSSManagerFactory(objArr == true ? 1 : 0);
    }

    private static class DefaultGSSManagerFactory extends GSSManagerFactory {
        private static final GSSManager INSTANCE = GSSManager.getInstance();

        private DefaultGSSManagerFactory() {
        }

        /* synthetic */ DefaultGSSManagerFactory(DefaultGSSManagerFactory defaultGSSManagerFactory) {
            this();
        }

        @Override // org.eclipse.jgit.util.GSSManagerFactory
        public GSSManager newInstance(URL url) {
            return INSTANCE;
        }
    }

    private static class SunGSSManagerFactory extends GSSManagerFactory {
        private static Constructor<?> GSS_MANAGER_IMPL_CONSTRUCTOR;
        private static Constructor<?> HTTP_CALLER_CONSTRUCTOR;
        private static Constructor<?> HTTP_CALLER_INFO_CONSTRUCTOR;
        private static boolean IS_SUPPORTED;

        private SunGSSManagerFactory() {
        }

        /* synthetic */ SunGSSManagerFactory(SunGSSManagerFactory sunGSSManagerFactory) {
            this();
        }

        static {
            try {
                init();
                IS_SUPPORTED = true;
            } catch (Exception unused) {
                IS_SUPPORTED = false;
            }
        }

        private static void init() throws NoSuchMethodException, ClassNotFoundException {
            Class<?> cls = Class.forName("sun.net.www.protocol.http.HttpCallerInfo");
            HTTP_CALLER_INFO_CONSTRUCTOR = cls.getConstructor(URL.class);
            HTTP_CALLER_CONSTRUCTOR = Class.forName("sun.security.jgss.HttpCaller").getConstructor(cls);
            GSS_MANAGER_IMPL_CONSTRUCTOR = Class.forName("sun.security.jgss.GSSManagerImpl").getConstructor(Class.forName("sun.security.jgss.GSSCaller"));
        }

        public static boolean isSupported() {
            return IS_SUPPORTED;
        }

        @Override // org.eclipse.jgit.util.GSSManagerFactory
        public GSSManager newInstance(URL url) {
            try {
                return (GSSManager) GSS_MANAGER_IMPL_CONSTRUCTOR.newInstance(HTTP_CALLER_CONSTRUCTOR.newInstance(HTTP_CALLER_INFO_CONSTRUCTOR.newInstance(url)));
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException e) {
                throw new Error(e);
            }
        }
    }
}
