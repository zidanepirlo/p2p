package com.newland.financial.p2p.domain.entity;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class Student extends BaseEntity{

    private String id;
    private String name;
    private BigDecimal avgScore;
    private Date createTime;

}
