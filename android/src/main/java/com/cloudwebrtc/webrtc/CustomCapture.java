package com.cloudwebrtc.webrtc;

import android.util.Log;

import org.webrtc.VideoFrame;
import org.webrtc.VideoSink;
import org.webrtc.VideoTrack;

import io.flutter.plugin.common.MethodChannel;

public class CustomCapture implements VideoSink {

    private final String tag = "CustomCapture";
    private final VideoTrack videoTrack;
    private final MethodChannel.Result callback;

    public CustomCapture(VideoTrack videoTrack, MethodChannel.Result callback) {
        this.videoTrack = videoTrack;
        this.callback = callback;

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

            callback.success(null);
        } catch (Exception e) {
            callback.error("CustomCaptureException", e.getLocalizedMessage(), e);
        }

    }
}
