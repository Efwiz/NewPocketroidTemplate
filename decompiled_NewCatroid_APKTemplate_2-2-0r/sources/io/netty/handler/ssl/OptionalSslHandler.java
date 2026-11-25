package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.ObjectUtil;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public class OptionalSslHandler extends ByteToMessageDecoder {
    private final SslContext sslContext;

    protected ChannelHandler newNonSslHandler(ChannelHandlerContext channelHandlerContext) {
        return null;
    }

    protected String newNonSslHandlerName() {
        return null;
    }

    protected String newSslHandlerName() {
        return null;
    }

    public OptionalSslHandler(SslContext sslContext) {
        this.sslContext = (SslContext) ObjectUtil.checkNotNull(sslContext, "sslContext");
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 5) {
            return;
        }
        if (SslHandler.isEncrypted(byteBuf)) {
            handleSsl(channelHandlerContext);
        } else {
            handleNonSsl(channelHandlerContext);
        }
    }

    private void handleSsl(ChannelHandlerContext channelHandlerContext) {
        SslHandler sslHandlerNewSslHandler = null;
        try {
            sslHandlerNewSslHandler = newSslHandler(channelHandlerContext, this.sslContext);
            channelHandlerContext.pipeline().replace(this, newSslHandlerName(), sslHandlerNewSslHandler);
        } catch (Throwable th) {
            if (sslHandlerNewSslHandler != null) {
                ReferenceCountUtil.safeRelease(sslHandlerNewSslHandler.engine());
            }
            throw th;
        }
    }

    private void handleNonSsl(ChannelHandlerContext channelHandlerContext) {
        ChannelHandler channelHandlerNewNonSslHandler = newNonSslHandler(channelHandlerContext);
        if (channelHandlerNewNonSslHandler != null) {
            channelHandlerContext.pipeline().replace(this, newNonSslHandlerName(), channelHandlerNewNonSslHandler);
        } else {
            channelHandlerContext.pipeline().remove(this);
        }
    }

    protected SslHandler newSslHandler(ChannelHandlerContext channelHandlerContext, SslContext sslContext) {
        return sslContext.newHandler(channelHandlerContext.alloc());
    }
}
