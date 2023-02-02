package com.lineate.buscompany.dtoE.responseE;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode()
public class RatingResponseE {
    private int id;
    private int clientId;
    private int mark;
    private String message;

    public RatingResponseE(int id, int clientId,int mark,String message){
        this.id=id;
        this.clientId=clientId;
        this.mark=mark;
        this.message=message;
    }
}
