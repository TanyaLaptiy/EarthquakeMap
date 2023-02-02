package com.lineate.buscompany.dtoE.requestE;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode()
public class RatingRequestE {
    private int clientId;
    private int mark;
    private String message;

    public RatingRequestE(int clientId,int mark,String message){
        this.clientId=clientId;
        this.mark=mark;
        this.message=message;
    }
}
