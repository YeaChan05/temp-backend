package com.semtleWebGroup.youtubeclone.domain.video.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VideoEditDto {
    private UUID videoId;
    private String title;
    private String description;
    private Blob thumbImg;

    @Builder
    public VideoEditDto(UUID videoId, String title, String description, Blob thumbImg) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.thumbImg = thumbImg;
    }
}
