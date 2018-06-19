package com.mykapps.androidlearners.data;

public class JsonDataList {
    private String topicID;
    private String topicName;
    private String topicImage;
    private String videoUrl;
    private String likesCount;
    private String commentsCount;
    private String placeholderString;

    public JsonDataList(String topicIDCons, String topicNameCons, String topicImageCons, String videoUrlCons, String likesCountCons, String commentsCountCons, String placeholderStringCons) {
        this.topicID = topicIDCons;
        this.topicName = topicNameCons;
        this.topicImage = topicImageCons;
        this.videoUrl = videoUrlCons;
        this.likesCount = likesCountCons;
        this.commentsCount = commentsCountCons;
        this.placeholderString = placeholderStringCons;

    }

    public String getTopicID() {
        return topicID;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getTopicImage() {
        return topicImage;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getLikesCount() {
        return likesCount;
    }

    public String getCommentsCount() {
        return commentsCount;
    }

    public String getPlaceholderString() {
        return placeholderString;
    }
}
