package com.hivemq.client.internal.mqtt.handler.websocket;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.MqttClientTransportConfigImpl;
import com.hivemq.client.internal.mqtt.MqttWebSocketConfigImpl;
import com.hivemq.client.internal.mqtt.ioc.ConnectionScope;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.inject.Inject;

/* JADX INFO: loaded from: classes10.dex */
@ConnectionScope
public class MqttWebSocketInitializer {
    private static final String HTTP_AGGREGATOR_NAME = "http.aggregator";
    private static final String HTTP_CODEC_NAME = "http.codec";
    private final MqttWebSocketCodec mqttWebSocketCodec;

    @Inject
    MqttWebSocketInitializer(MqttWebSocketCodec mqttWebSocketCodec) {
        this.mqttWebSocketCodec = mqttWebSocketCodec;
    }

    public void initChannel(Channel channel, MqttClientConfig mqttClientConfig, MqttWebSocketConfigImpl mqttWebSocketConfigImpl, Consumer<Channel> consumer, BiConsumer<Channel, Throwable> biConsumer) {
        try {
            MqttClientTransportConfigImpl currentTransportConfig = mqttClientConfig.getCurrentTransportConfig();
            InetSocketAddress serverAddress = currentTransportConfig.getServerAddress();
            channel.pipeline().addLast(HTTP_CODEC_NAME, (ChannelHandler) new HttpClientCodec()).addLast(HTTP_AGGREGATOR_NAME, (ChannelHandler) new HttpObjectAggregator(65535)).addLast(MqttWebsocketHandshakeHandler.NAME, new MqttWebsocketHandshakeHandler(WebSocketClientHandshakerFactory.newHandshaker(new URI(currentTransportConfig.getRawSslConfig() == null ? "ws" : "wss", null, serverAddress.getHostString(), serverAddress.getPort(), "/" + mqttWebSocketConfigImpl.getServerPath(), mqttWebSocketConfigImpl.getQueryString(), null), WebSocketVersion.V13, mqttWebSocketConfigImpl.getSubprotocol(), true, (HttpHeaders) null, 268435460, true, false), mqttWebSocketConfigImpl.getHandshakeTimeoutMs(), consumer, biConsumer)).addLast(MqttWebSocketCodec.NAME, this.mqttWebSocketCodec);
        } catch (URISyntaxException e) {
            biConsumer.accept(channel, e);
        }
    }
}
