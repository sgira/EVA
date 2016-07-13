package com.wellness.eva.procedures;

/**
 * Medical emergency composed of name and
 * the procedures associated with it.
 */
public class MedicalEmergency {

    private String emergencyName;
    private MedicalProcedure procedure;

    public MedicalEmergency(String emergencyName, MedicalProcedure procedure) {
        this.emergencyName = emergencyName;
        this.procedure = procedure;
    }

    /**
     * Name of the medical emergency
     * @return
     */
    public String getEmergencyName() {
        return emergencyName;
    }

    /**
     * Set the name of the active medical emergency
     * @param emergencyName
     */
    public void setEmergencyName(String emergencyName) {
        this.emergencyName = emergencyName;
    }

    /**
     * Returns medical procedure associated with type of
     * medical emergency
     * @return medical procedure
     */
    public MedicalProcedure getProcedure() {
        return procedure;
    }

    /**
     * Set the procedure associated with this
     * medical emergency
     * @param procedure
     */
    public void setProcedure(MedicalProcedure procedure) {
        this.procedure = procedure;
    }
}
