package com.injun.quiz.domain;

public enum Factor {
    MINIMUN, MAXIMUM;

    @Override
    public String toString() {
        String value = "";
        switch (this){
            case MINIMUN:value = String.valueOf(11); break;
            case MAXIMUM:value = String.valueOf(99); break;
        }
        return value;
    }
}
