package com.garagemanagement.carrepairservice.common.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Agg{
    private String repair_id;
    private Long count;

    public Agg(String repair_id, Long count){
        this.repair_id = repair_id;
        this.count = count;
    }


}
