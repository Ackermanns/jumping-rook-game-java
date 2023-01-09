package com.example.jumpingrookgame.model;

@interface IProjectDetails {
    double version();
    String author();
    String lastModified() default "27/04/2022";
}
