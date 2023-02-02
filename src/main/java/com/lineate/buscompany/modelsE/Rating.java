package com.lineate.buscompany.modelsE;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode()
public class Rating {
    private int id;
    private int clientId;
    private int mark;
    private String message;

    public Rating(int clientId,int mark,String message){
        this.clientId=clientId;
        this.mark=mark;
        this.message=message;
    }

}
