package me.alpha432.oyvey.event.events;

import io.netty.channel.Channel;
import me.alpha432.oyvey.event.EventStage;

public class NettyChannelEvent
extends EventStage {
    private final Channel channel;

    public NettyChannelEvent(Channel channel) {
        super(0);
        this.channel = channel;
    }

    public Channel getChannel() {
        return this.channel;
    }
}

