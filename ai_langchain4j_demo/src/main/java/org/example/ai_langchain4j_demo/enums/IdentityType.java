package org.example.ai_langchain4j_demo.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum IdentityType {
    STUDENT("STUDENT", "学生"),
    TEACHER("TEACHER", "教师");

    private final String code;
    private final String desc;

    IdentityType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}