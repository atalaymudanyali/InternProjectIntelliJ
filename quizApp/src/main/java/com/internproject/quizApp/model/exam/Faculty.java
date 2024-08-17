package com.internproject.quizApp.model.exam;

public enum Faculty {
    EDEBIYAT_FAK("Edebiyat Fak."),
    MUHENDISLIK_FAK("Mühendislik Fak."),
    SOSYAL_BILIMLER_FAK("Sosyal Bilimler Fak."),
    HUKUK_FAK("Hukuk Fak.");

    private String displayName;

    Faculty(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
