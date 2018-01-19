package com.github.invictum.mei.entity;

/**
 * Defines condition container
 */
public class ConditionEntity {

    private String condition;
    private String value;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConditionEntity)) return false;

        ConditionEntity that = (ConditionEntity) o;

        if (condition != null ? !condition.equals(that.condition) : that.condition != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = condition != null ? condition.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
