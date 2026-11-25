package com.hivemq.client.internal.mqtt;

import com.hivemq.client.internal.mqtt.MqttProxyConfigImplBuilder;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.internal.util.InetSocketAddressUtil;
import com.hivemq.client.mqtt.MqttProxyConfig;
import com.hivemq.client.mqtt.MqttProxyConfigBuilder;
import com.hivemq.client.mqtt.MqttProxyConfigBuilderBase;
import com.hivemq.client.mqtt.MqttProxyProtocol;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttProxyConfigImplBuilder<B extends MqttProxyConfigImplBuilder<B>> {
    private InetSocketAddress address;
    private int handshakeTimeoutMs;
    private Object host;
    private String password;
    private int port;
    private MqttProxyProtocol protocol;
    private String username;

    abstract B self();

    MqttProxyConfigImplBuilder() {
        this.protocol = MqttProxyConfigImpl.DEFAULT_PROXY_PROTOCOL;
        this.host = "localhost";
        this.port = -1;
        this.handshakeTimeoutMs = 10000;
    }

    MqttProxyConfigImplBuilder(MqttProxyConfigImpl mqttProxyConfigImpl) {
        this.protocol = MqttProxyConfigImpl.DEFAULT_PROXY_PROTOCOL;
        this.host = "localhost";
        this.port = -1;
        this.handshakeTimeoutMs = 10000;
        if (mqttProxyConfigImpl != null) {
            this.protocol = mqttProxyConfigImpl.getProtocol();
            this.address = mqttProxyConfigImpl.getAddress();
            this.username = mqttProxyConfigImpl.getRawUsername();
            this.password = mqttProxyConfigImpl.getRawPassword();
            this.handshakeTimeoutMs = mqttProxyConfigImpl.getHandshakeTimeoutMs();
        }
    }

    public B protocol(MqttProxyProtocol mqttProxyProtocol) {
        this.protocol = (MqttProxyProtocol) Checks.notNull(mqttProxyProtocol, "Proxy protocol");
        return (B) self();
    }

    public B address(InetSocketAddress inetSocketAddress) {
        this.address = (InetSocketAddress) Checks.notNull(inetSocketAddress, "Proxy address");
        return (B) self();
    }

    public B host(String str) {
        setProxyHost(Checks.notEmpty(str, "Proxy host"));
        return (B) self();
    }

    public B host(InetAddress inetAddress) {
        setProxyHost(Checks.notNull(inetAddress, "Proxy host"));
        return (B) self();
    }

    private void setProxyHost(Object obj) {
        this.host = obj;
        InetSocketAddress inetSocketAddress = this.address;
        if (inetSocketAddress != null) {
            this.port = inetSocketAddress.getPort();
            this.address = null;
        }
    }

    public B port(int i) {
        this.port = Checks.unsignedShort(i, "Proxy port");
        InetSocketAddress inetSocketAddress = this.address;
        if (inetSocketAddress != null) {
            InetAddress address = inetSocketAddress.getAddress();
            if (address != null) {
                this.host = address;
            } else {
                this.host = this.address.getHostString();
            }
            this.address = null;
        }
        return (B) self();
    }

    public B username(String str) {
        this.username = str;
        return (B) self();
    }

    public B password(String str) {
        this.password = str;
        return (B) self();
    }

    public B handshakeTimeout(long j, TimeUnit timeUnit) {
        Checks.notNull(timeUnit, "Time unit");
        this.handshakeTimeoutMs = (int) Checks.range(timeUnit.toMillis(j), 0L, 2147483647L, "Handshake timeout in milliseconds");
        return (B) self();
    }

    private InetSocketAddress getAddress() {
        InetSocketAddress inetSocketAddress = this.address;
        if (inetSocketAddress != null) {
            return inetSocketAddress;
        }
        Object obj = this.host;
        if (obj instanceof InetAddress) {
            return new InetSocketAddress((InetAddress) this.host, getPort());
        }
        return InetSocketAddressUtil.create((String) obj, getPort());
    }

    private int getPort() {
        int i = this.port;
        if (i != -1) {
            return i;
        }
        int i2 = AnonymousClass1.$SwitchMap$com$hivemq$client$mqtt$MqttProxyProtocol[this.protocol.ordinal()];
        if (i2 == 1 || i2 == 2) {
            return MqttProxyConfig.DEFAULT_SOCKS_PROXY_PORT;
        }
        return 80;
    }

    /* JADX INFO: renamed from: com.hivemq.client.internal.mqtt.MqttProxyConfigImplBuilder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$hivemq$client$mqtt$MqttProxyProtocol;

        static {
            int[] iArr = new int[MqttProxyProtocol.values().length];
            $SwitchMap$com$hivemq$client$mqtt$MqttProxyProtocol = iArr;
            try {
                iArr[MqttProxyProtocol.SOCKS_4.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$MqttProxyProtocol[MqttProxyProtocol.SOCKS_5.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$MqttProxyProtocol[MqttProxyProtocol.HTTP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public MqttProxyConfigImpl build() {
        return new MqttProxyConfigImpl(this.protocol, getAddress(), this.username, this.password, this.handshakeTimeoutMs);
    }

    public static class Default extends MqttProxyConfigImplBuilder<Default> implements MqttProxyConfigBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.MqttProxyConfigImplBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase address(InetSocketAddress inetSocketAddress) {
            return (MqttProxyConfigBuilderBase) super.address(inetSocketAddress);
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilder
        public /* bridge */ /* synthetic */ MqttProxyConfig build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase handshakeTimeout(long j, TimeUnit timeUnit) {
            return (MqttProxyConfigBuilderBase) super.handshakeTimeout(j, timeUnit);
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase host(String str) {
            return (MqttProxyConfigBuilderBase) super.host(str);
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase host(InetAddress inetAddress) {
            return (MqttProxyConfigBuilderBase) super.host(inetAddress);
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase password(String str) {
            return (MqttProxyConfigBuilderBase) super.password(str);
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase port(int i) {
            return (MqttProxyConfigBuilderBase) super.port(i);
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase protocol(MqttProxyProtocol mqttProxyProtocol) {
            return (MqttProxyConfigBuilderBase) super.protocol(mqttProxyProtocol);
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase username(String str) {
            return (MqttProxyConfigBuilderBase) super.username(str);
        }

        public Default() {
        }

        Default(MqttProxyConfigImpl mqttProxyConfigImpl) {
            super(mqttProxyConfigImpl);
        }
    }

    public static class Nested<P> extends MqttProxyConfigImplBuilder<Nested<P>> implements MqttProxyConfigBuilder.Nested<P> {
        private final Function<? super MqttProxyConfigImpl, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.MqttProxyConfigImplBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase address(InetSocketAddress inetSocketAddress) {
            return (MqttProxyConfigBuilderBase) super.address(inetSocketAddress);
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase handshakeTimeout(long j, TimeUnit timeUnit) {
            return (MqttProxyConfigBuilderBase) super.handshakeTimeout(j, timeUnit);
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase host(String str) {
            return (MqttProxyConfigBuilderBase) super.host(str);
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase host(InetAddress inetAddress) {
            return (MqttProxyConfigBuilderBase) super.host(inetAddress);
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase password(String str) {
            return (MqttProxyConfigBuilderBase) super.password(str);
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase port(int i) {
            return (MqttProxyConfigBuilderBase) super.port(i);
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase protocol(MqttProxyProtocol mqttProxyProtocol) {
            return (MqttProxyConfigBuilderBase) super.protocol(mqttProxyProtocol);
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilderBase username(String str) {
            return (MqttProxyConfigBuilderBase) super.username(str);
        }

        Nested(MqttProxyConfigImpl mqttProxyConfigImpl, Function<? super MqttProxyConfigImpl, P> function) {
            super(mqttProxyConfigImpl);
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.MqttProxyConfigBuilder.Nested
        public P applyProxyConfig() {
            return this.parentConsumer.apply(build());
        }
    }
}
