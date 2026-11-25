package io.netty.handler.ssl;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import io.netty.handler.ssl.util.SimpleKeyManagerFactory;
import io.netty.util.internal.ObjectUtil;
import java.security.KeyStore;
import javax.net.ssl.KeyManager;
import javax.net.ssl.ManagerFactoryParameters;

/* JADX INFO: loaded from: classes11.dex */
final class KeyManagerFactoryWrapper extends SimpleKeyManagerFactory {
    private final KeyManager km;

    @Override // io.netty.handler.ssl.util.SimpleKeyManagerFactory
    protected void engineInit(KeyStore keyStore, char[] cArr) throws Exception {
    }

    @Override // io.netty.handler.ssl.util.SimpleKeyManagerFactory
    protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws Exception {
    }

    KeyManagerFactoryWrapper(KeyManager keyManager) {
        this.km = (KeyManager) ObjectUtil.checkNotNull(keyManager, LanguageCodeUtil.KM);
    }

    @Override // io.netty.handler.ssl.util.SimpleKeyManagerFactory
    protected KeyManager[] engineGetKeyManagers() {
        return new KeyManager[]{this.km};
    }
}
