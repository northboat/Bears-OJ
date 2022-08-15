package com.oj.neuqoj.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    String name;
    String account;
    int finished;
    int simple_finished;
    int middle_finished;
    int hard_finished;
    String finished_index;
    String register_time;
    String grade;
    String skillful_lang;
    int java_finished;
    int c_finished;
    int python_finished;

    public Info(String account, String name, String register_time, int ques_num){
        this.account = account;
        this.name = name;
        this.register_time = register_time;

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < ques_num; i++){ stringBuilder.append('0'); }
        this.finished_index = stringBuilder.toString();
    }

    public boolean hasPassed(int index){
        if(index >= this.finished_index.length()){
            StringBuilder stringBuilder = new StringBuilder(this.finished_index);
            int n = this.finished_index.length();
            for(int i = 0; i < index-n; i++){
                stringBuilder.append('0');
            }
            this.finished_index = stringBuilder.toString();
            return false;
        }
        return this.finished_index.charAt(index) == '1';
    }

    public void pass(int index, int lang, String level){
        StringBuilder stringBuilder = new StringBuilder(this.finished_index);
        if(index >= this.finished_index.length()){
            int n = this.finished_index.length();
            for(int i = 0; i < index-n; i++){
                stringBuilder.append('0');
            }
        }
        switch (lang){
            case 20800:
            case 21100: this.java_finished += 1; break;
            case 10730: this.c_finished += 1; break;
            case 30114: this.python_finished += 1; break;
        }
        if(level.equals("易")){
            this.simple_finished++;
        }else if(level.equals("中")){
            this.middle_finished++;
        }else{
            this.hard_finished++;
        }
        this.finished += 1;
        this.finished_index = stringBuilder.replace(index, index+1, "1").toString();
    }

    public void goodAt(){
        this.skillful_lang = "C/C++";
        if(this.java_finished > this.c_finished){ this.skillful_lang = "Java"; }
        if(this.python_finished > java_finished){ this.skillful_lang = "GoLang"; }
    }


}
