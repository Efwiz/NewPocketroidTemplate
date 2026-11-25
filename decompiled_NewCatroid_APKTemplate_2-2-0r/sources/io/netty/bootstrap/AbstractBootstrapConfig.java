package io.netty.bootstrap;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.util.AttributeKey;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.net.SocketAddress;
import java.util.Map;

/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractBootstrapConfig<B extends AbstractBootstrap<B, C>, C extends Channel> {
    protected final B bootstrap;

    protected AbstractBootstrapConfig(B b) {
        this.bootstrap = (B) ObjectUtil.checkNotNull(b, "bootstrap");
    }

    public final SocketAddress localAddress() {
        return this.bootstrap.localAddress();
    }

    public final ChannelFactory<? extends C> channelFactory() {
        return this.bootstrap.channelFactory();
    }

    public final ChannelHandler handler() {
        return this.bootstrap.handler();
    }

    public final Map<ChannelOption<?>, Object> options() {
        return this.bootstrap.options();
    }

    public final Map<AttributeKey<?>, Object> attrs() {
        return this.bootstrap.attrs();
    }

    public final EventLoopGroup group() {
        return this.bootstrap.group();
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder().append(StringUtil.simpleClassName(this)).append('(');
        EventLoopGroup eventLoopGroupGroup = group();
        if (eventLoopGroupGroup != null) {
            sbAppend.append("group: ").append(StringUtil.simpleClassName(eventLoopGroupGroup)).append(", ");
        }
        ChannelFactory<? extends C> channelFactory = channelFactory();
        if (channelFactory != null) {
            sbAppend.append("channelFactory: ").append(channelFactory).append(", ");
        }
        SocketAddress socketAddressLocalAddress = localAddress();
        if (socketAddressLocalAddress != null) {
            sbAppend.append("localAddress: ").append(socketAddressLocalAddress).append(", ");
        }
        Map<ChannelOption<?>, Object> mapOptions = options();
        if (!mapOptions.isEmpty()) {
            sbAppend.append("options: ").append(mapOptions).append(", ");
        }
        Map<AttributeKey<?>, Object> mapAttrs = attrs();
        if (!mapAttrs.isEmpty()) {
            sbAppend.append("attrs: ").append(mapAttrs).append(", ");
        }
        ChannelHandler channelHandlerHandler = handler();
        if (channelHandlerHandler != null) {
            sbAppend.append("handler: ").append(channelHandlerHandler).append(", ");
        }
        if (sbAppend.charAt(sbAppend.length() - 1) == '(') {
            sbAppend.append(')');
        } else {
            sbAppend.setCharAt(sbAppend.length() - 2, ')');
            sbAppend.setLength(sbAppend.length() - 1);
        }
        return sbAppend.toString();
    }
}
