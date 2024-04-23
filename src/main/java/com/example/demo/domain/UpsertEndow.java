package com.example.demo.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class UpsertEndow {

    private String endowId;

    private String endowContent;

    private MultipartFile endowImage;
    private String endowTitle;

    public String getEndowId() {
        return endowId;
    }

    public void setEndowId(String endowId) {
        this.endowId = endowId;
    }

    public String getEndowContent() {
        return endowContent;
    }

    public void setEndowContent(String endowContent) {
        this.endowContent = endowContent;
    }

    public MultipartFile getEndowImage() {
        return endowImage;
    }

    public void setEndowImage(MultipartFile endowImage) {
        this.endowImage = endowImage;
    }

    public String getEndowTitle() {
        return endowTitle;
    }

    public void setEndowTitle(String endowTitle) {
        this.endowTitle = endowTitle;
    }
}
