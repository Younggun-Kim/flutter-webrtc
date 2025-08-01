package com.cloudwebrtc.webrtc.custom;

import android.util.Log;

import org.webrtc.VideoFrame;
import org.webrtc.VideoSink;
import org.webrtc.VideoTrack;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class FrameEvent {
    private final int width;
    private final int height;
    private final byte[] y;
    private final byte[] u;
    private final byte[] v;

    public FrameEvent(int width, int height, byte[] y, byte[] u, byte[] v) {
        this.width = width;
        this.height = height;
        this.y = y;
        this.u = u;
        this.v = v;
    }
