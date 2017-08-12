package com.cdj.ends.base.util;

public enum PreferenceKey {
    USER_ID("USER_ID", String.class),
    USER_PASSWORD("USER_PASSWORD", String.class);

    String key;
    Class valueType;

    PreferenceKey(String key, Class valueType) {
        this.key = key;
        this.valueType = valueType;
    }

    public String getKey() {
        return key;
    }

    public Class getValueType() {
        return valueType;
    }
}
