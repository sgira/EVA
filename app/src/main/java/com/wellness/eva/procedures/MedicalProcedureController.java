package com.wellness.eva.procedures;

/**
 * Controller in MVC design pattern
 * capable of manaing a medical emergency object
 */
public class MedicalProcedureController {

    private MedicalEmergency emergency;

    public MedicalProcedureController(MedicalEmergency emergency) {
        this.emergency = emergency;
    }
}
