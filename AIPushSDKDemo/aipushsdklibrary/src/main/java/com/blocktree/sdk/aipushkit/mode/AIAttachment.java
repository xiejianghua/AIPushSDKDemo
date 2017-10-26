package com.blocktree.sdk.aipushkit.mode;
/**
 * xiezuofei
 * 2017-09-18 09:50
 * 793169940@qq.com
 * 附件对象
 */
public class AIAttachment {
    private String attachmentid="";//附件id
    private String fileKey="";//1232.jpg	文件的key值
    private String format="";//文件格式
    private String fileName="";//1232.jpg	文件名称
    private String fileUrl="";//下载地址
    private String thumbnail="";//缩略图地址
    private String fileSize="";//文件大小
    private String duration="";//播放时长
    private String uploadTime="";//上传时间


    public String getAttachmentid() {
        return attachmentid;
    }

    public void setAttachmentid(String attachmentid) {
        this.attachmentid = attachmentid;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
    @Override
    public String toString() {
        return "AIAttachment{" +
                "attachmentid='" + attachmentid + '\'' +
                ", fileKey='" + fileKey + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", duration='" + duration + '\'' +
                ", format='" + format + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                '}';
    }
}
