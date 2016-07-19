package com.wellness.eva.procedures;

import java.util.ArrayList;

/**
 * Medical Procedure contains list of
 * procedures associated with medical
 * emergency
 */
public class MedicalProcedure
{
    private String emergencyName;
    private String procedureName;
    private ArrayList<String> instructions;
    private boolean needsFeedback;

    public ArrayList<String> getInstructions() {

        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    /**
     *
     * @return
     */
    public String getProcedureName() {
        return procedureName;
    }

    /**
     * Set the procedure name for selected
     * medical emergency situation
     * @param procedureName
     */
    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    /**
     * Return true if the procedure is capable
     * to measure and collect feedback while user operation
     * @return
     */
    public boolean NeedsFeedback() {
        return needsFeedback;
    }

    /**
     * Set true if procedure can provide feedback using
     * device on board sensors
     * @param needsFeedback
     */
    public void setNeedsFeedback(boolean needsFeedback) {
        this.needsFeedback = needsFeedback;
    }

    /**
     * Invariant to test if file was extracted
     * for internal memory and it exist
     * @return
     */
    public int procedureExist()
    {
        return instructions.size();
    }
}
