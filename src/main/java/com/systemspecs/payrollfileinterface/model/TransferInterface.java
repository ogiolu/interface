package com.systemspecs.payrollfileinterface.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransferInterface {
    private Long id;
    private String companyid;
    private String effectivedate;
    private String staffnumber;
    private String status = "A";
    private String analysis1;
    private String analysis2;
    private String analysis3;
    private String analysis4;
    private String analysis5;
    private String analysis6;




}
