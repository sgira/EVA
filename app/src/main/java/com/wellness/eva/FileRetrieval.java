package com.wellness.eva;

import com.wellness.eva.procedures.MedicalProcedure;

import java.util.ArrayList;

/**
 * Created by SinJa on 7/9/16.
 */

public class FileRetrieval {

    //private static String heart_attack = "heartAttack";

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
        if (emergencyType.equals("heartAttack")) {

            ArrayList<String> instructions = new ArrayList<>();
            instructions.add("1. Have the person sit down, rest, and try to keep calm.\n\n");
            instructions.add("2. Loosen any tight clothing.\n\n");
            instructions.add("3. Ask if the person takes any chest pain medication " +
                    "for a known heart condition, and help them take it.\n\n");
            instructions.add("4. If the pain does not go away promptly with rest or within " +
                    "3 minutes of taking medication, call for emergency medical help.\n\n");
            instructions.add("5. If the person is unconscious and unresponsive, call 911, then begin CPR.\n\n");
            instructions.add("6. If an infant or child is unconscious and unresponsive, " +
                    "perform 1 minute of CPR, then call 911.\n\n");

            medicalProcedure.setInstructions(instructions);
            medicalProcedure.setNeedsFeedback(true);
        }


        //If file does not exist return empty medical procedure

        return medicalProcedure;
    }
}
