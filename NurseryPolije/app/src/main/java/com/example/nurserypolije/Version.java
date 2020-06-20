package com.example.nurserypolije;

public class Version {

    private String pertanyaan, jawaban;
    private boolean expandable;

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public Version(String pertanyaan, String jawaban) {
        this.pertanyaan = pertanyaan;
        this.jawaban = jawaban;
        this.expandable = false;

    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }


    @Override
    public String toString() {
        return "Version{" +
                "codenama='" + pertanyaan + '\'' +
                ", version='" + jawaban + '\'' +
                '}';
    }
}

