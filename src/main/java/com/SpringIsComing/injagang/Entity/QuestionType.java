package com.SpringIsComing.injagang.Entity;

public enum QuestionType {

    CS("컴퓨터과학"), SITUATION("상황"), JOB("직무"), PERSONALITY("인성");

    private final String description;

    QuestionType(String description){
        this.description = description;}

    public String getDescription() {
        return description;}
}
