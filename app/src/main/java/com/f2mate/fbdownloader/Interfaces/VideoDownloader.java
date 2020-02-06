package com.f2mate.fbdownloader.Interfaces;

public interface VideoDownloader {

    String createDirectory();

    String getVideoId(String link);

    void DownloadVideo();
}
