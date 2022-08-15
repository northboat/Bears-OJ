package com.oj.neuqoj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Detail {
    int num;
    String desc;
    long memory_limit;
    long time_limit;
    String example_input;
    String example_output;
    String tips;
    int customized;
}
