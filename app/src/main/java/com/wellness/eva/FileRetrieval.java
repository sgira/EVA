package com.wellness.eva;

import com.wellness.eva.procedures.MedicalProcedure;

import java.util.ArrayList;

/**
 * Created by SinJa on 7/9/16.
 */

public class FileRetrieval {

    /**
     * Retrieve medical procedure for medical emergency. If file
     * does not exist it returns an empty medical procedure.
     * @param emergencyType
     * @return emergency procedure
     */
    public static MedicalProcedure retrieveMedicalEmergency(String emergencyType)
    {
        //If file exist
        MedicalProcedure medicalProcedure = new MedicalProcedure();

        //Retrieve medical procedure from internal file
        ArrayList<String> instructions = new ArrayList<>();
        instructions.add("Check pulse");
        instructions.add("Peform compressions");
        medicalProcedure.setInstructions(instructions);

        //If file does not exist return empty medical procedure

        return medicalProcedure;
    }
}
