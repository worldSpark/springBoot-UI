
package com.example.demo.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(
    prefix = "pathconfig"
)
public class FtpAttConfig {
    private boolean ftpOpen;
    private String ftpHost;
    private int ftpPort;
    private String ftpUserName;
    private String ftpPassWord;
    private String ftpOperativePath;
    private String ftpCloudPath;
    private String ftpPortalPath;
    private String ftpMobilePath;
    private String ftpPropertyPath;
    private String ftpDefaultPath;

    public FtpAttConfig() {
    }

    public String getFtpDefaultPath() {
        return ftpDefaultPath;
    }

    public void setFtpDefaultPath(String ftpDefaultPath) {
        this.ftpDefaultPath = ftpDefaultPath;
    }

    public String getFtpHost() {
        return this.ftpHost;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public int getFtpPort() {
        return this.ftpPort;
    }

    public void setFtpPort(int ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpUserName() {
        return this.ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPassWord() {
        return this.ftpPassWord;
    }

    public void setFtpPassWord(String ftpPassWord) {
        this.ftpPassWord = ftpPassWord;
    }

    public String getFtpOperativePath() {
        return this.ftpOperativePath;
    }

    public void setFtpOperativePath(String ftpOperativePath) {
        this.ftpOperativePath = ftpOperativePath;
    }

    public String getFtpCloudPath() {
        return this.ftpCloudPath;
    }

    public void setFtpCloudPath(String ftpCloudPath) {
        this.ftpCloudPath = ftpCloudPath;
    }

    public String getFtpPortalPath() {
        return this.ftpPortalPath;
    }

    public void setFtpPortalPath(String ftpPortalPath) {
        this.ftpPortalPath = ftpPortalPath;
    }

    public String getFtpMobilePath() {
        return this.ftpMobilePath;
    }

    public void setFtpMobilePath(String ftpMobilePath) {
        this.ftpMobilePath = ftpMobilePath;
    }

    public String getFtpPropertyPath() {
        return this.ftpPropertyPath;
    }

    public void setFtpPropertyPath(String ftpPropertyPath) {
        this.ftpPropertyPath = ftpPropertyPath;
    }

    public boolean isFtpOpen() {
        return this.ftpOpen;
    }

    public void setFtpOpen(boolean ftpOpen) {
        this.ftpOpen = ftpOpen;
    }
}
