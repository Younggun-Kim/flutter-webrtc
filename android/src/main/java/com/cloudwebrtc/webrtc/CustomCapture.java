package com.cloudwebrtc.webrtc;

import android.util.Log;

import org.webrtc.VideoFrame;
import org.webrtc.VideoSink;
import org.webrtc.VideoTrack;

import io.flutter.plugin.common.MethodChannel;

public class CustomCapture implements VideoSink {

    private final String tag = "CustomCapture";
    private final VideoTrack videoTrack;

    public CustomCapture(VideoTrack videoTrack) {
        this.videoTrack = videoTrack;

        videoTrack.addSink(this);
    }

    @Override
    public void onFrame(VideoFrame videoFrame) {
        try {
            videoFrame.retain();

            VideoFrame.Buffer buffer = videoFrame.getBuffer();
            VideoFrame.I420Buffer i420Buffer = buffer.toI420();

            Log.i(tag, "420Buffer" + i420Buffer.toString());

            i420Buffer.release();
            videoFrame.release();

        } catch (Exception e) {
            dispose();
            Log.e(tag, "onFrame Error: ", e);
        }
    }

    public void dispose() {
        videoTrack.removeSink(this);
    }
}
