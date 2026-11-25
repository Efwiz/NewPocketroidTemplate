package com.hivemq.client.internal.mqtt.handler.ssl;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.MqttClientSslConfigImpl;
import com.hivemq.client.internal.util.collections.ImmutableList;
import io.netty.channel.Channel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.SupportedCipherSuiteFilter;
import java.net.InetSocketAddress;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttSslInitializer {
    private static final String SSL_HANDLER_NAME = "ssl";

    public static void initChannel(Channel channel, MqttClientConfig mqttClientConfig, MqttClientSslConfigImpl mqttClientSslConfigImpl, Consumer<Channel> consumer, BiConsumer<Channel, Throwable> biConsumer) {
        InetSocketAddress serverAddress = mqttClientConfig.getCurrentTransportConfig().getServerAddress();
        try {
            SslContext currentSslContext = mqttClientConfig.getCurrentSslContext();
            if (currentSslContext == null) {
                currentSslContext = createSslContext(mqttClientSslConfigImpl);
                mqttClientConfig.setCurrentSslContext(currentSslContext);
            }
            SslHandler sslHandlerNewHandler = currentSslContext.newHandler(channel.alloc(), serverAddress.getHostString(), serverAddress.getPort());
            sslHandlerNewHandler.setHandshakeTimeoutMillis(mqttClientSslConfigImpl.getHandshakeTimeoutMs());
            HostnameVerifier rawHostnameVerifier = mqttClientSslConfigImpl.getRawHostnameVerifier();
            if (rawHostnameVerifier == null) {
                SSLParameters sSLParameters = sslHandlerNewHandler.engine().getSSLParameters();
                sSLParameters.setEndpointIdentificationAlgorithm("HTTPS");
                sslHandlerNewHandler.engine().setSSLParameters(sSLParameters);
            }
            channel.pipeline().addLast(SSL_HANDLER_NAME, sslHandlerNewHandler).addLast(MqttSslAdapterHandler.NAME, new MqttSslAdapterHandler(sslHandlerNewHandler, serverAddress.getHostString(), rawHostnameVerifier, consumer, biConsumer));
        } catch (Throwable th) {
            biConsumer.accept(channel, th);
        }
    }

    static SslContext createSslContext(MqttClientSslConfigImpl mqttClientSslConfigImpl) throws SSLException {
        ImmutableList<String> rawProtocols = mqttClientSslConfigImpl.getRawProtocols();
        return SslContextBuilder.forClient().trustManager(mqttClientSslConfigImpl.getRawTrustManagerFactory()).keyManager(mqttClientSslConfigImpl.getRawKeyManagerFactory()).protocols(rawProtocols == null ? null : (String[]) rawProtocols.toArray(new String[0])).ciphers(mqttClientSslConfigImpl.getRawCipherSuites(), SupportedCipherSuiteFilter.INSTANCE).build();
    }

    private MqttSslInitializer() {
    }
}
