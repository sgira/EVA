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

        if (emergencyType.equals("choking")) {

            ArrayList<String> instructions = new ArrayList<>();

            instructions.add("1. Roll the person onto their back on a hard surface, keeping " +
                    "the back in a straight line while firmly supporting the head and neck.\n\n");
            instructions.add("2. Expose the person's chest.\n\n");
            instructions.add("3. Open the person's mouth with your thumb and index finger, placing " +
                    "your thumb over the tongue and your index finger under the chin.\n\n");
            instructions.add("4. If you can see an object and it is loose, remove it.\n\n");
            instructions.add("5. If you do not see an object, open the person's airway by lifting " +
                    "the chin while tilting the head back.\n\n");
            instructions.add("6. Place your ear close to the person's mouth and watch for chest " +
                    "movement. Look, listen, and feel for breathing for 5 seconds.\n\n");
            instructions.add("7. If the person is breathing, give first aid for unconsciousness.\n\n");
            instructions.add("8. If the person is not breathing, begin rescue breathing. Maintain " +
                    "the head position, close the person's nostrils by pinching them with your " +
                    "thumb and index finger, and cover the person's mouth tightly with your mouth." +
                    " Give two slow, full breaths with a pause in between.\n\n");
            instructions.add("9. If the person's chest does not rise, reposition the head and give" +
                    " two more breaths.\n\n");
            instructions.add("10. If the chest still does not rise, the airway is likely blocked, " +
                    "and you need to start CPR with chest compressions.\n\n");
            instructions.add("11. Do 30 chest compressions, open the person's mouth to look " +
                    "for an object. If you see the object and it is loose, remove it.\n\n");
            instructions.add("12. If the object is removed, but the person has no pulse, " +
                    "begin CPR with chest compressions.\n\n");
            instructions.add("13. If you do not see an object, give two more rescue breaths. If the" +
                    " person's chest still does not rise, keep going with cycles of chest " +
                    "compressions, checking for an object, and rescue breaths until medical help " +
                    "arrives or the person starts breathing on their own.\n\n");
            instructions.add("14. If the person starts having convulsions or seizures, " +
                    "give first aid for this problem.\n\n");
            instructions.add("15. After removing the object that caused the choking, " +
                    "keep the person still and get medical help.\n\n");

            medicalProcedure.setInstructions(instructions);
            medicalProcedure.setNeedsFeedback(true);
        }

        if (emergencyType.equals("burning")) {

            ArrayList<String> instructions = new ArrayList<>();

            instructions.add("1. Soak the burn in cool water for at least 5 minutes.\n\n");
            instructions.add("2. Treat the burn with a skin care product that protects and heals" +
                    " skin, such as aloe vera cream or an antibiotic ointment\n\n");
            instructions.add("3. Wrap a dry gauze bandage loosely around the burn.\n\n");
            instructions.add("4. Take an over-the-counter pain reliever, such as acetaminophen" +
                    " or ibuprofen  to help with the pain.\n\n");
            instructions.add("1. Soak the burn in cool water for 15 minutes.\n\n");
            instructions.add("2. If the burned area is small, put cool, clean, wet cloths on " +
                    "the burn for a few minutes every day.\n\n");
            instructions.add("3. Put on an antibiotic cream, or other creams or ointments " +
                    "prescribed by your doctor.\n\n");
            instructions.add("4. Cover the burn with a dry nonstick dressing" +
                    " held in place with gauze or tape.\n\n");
            instructions.add("5. Check with your doctor's office to make sure you are " +
                    "up-to-date on tetanus shots.\n\n");
            instructions.add("6. Change the dressing every day. First, wash your hands with soap " +
                    "and water. Then gently wash the burn and put antibiotic ointment on it. " +
                    "If the burn area is small, a dressing may not be needed during the day.\n\n");
            instructions.add("7.  Check the burn every day for signs of infection, such as " +
                    "increased pain, redness, swelling or pus. If you see any of these signs, " +
                    "see your doctor right away.\n\n");
            instructions.add("8. To prevent infection, avoid breaking any blisters that form.\n\n");
            instructions.add("9. Keep your fingernails cut short and don't scratch the burned skin.\n\n");
            instructions.add("10. The burned area will be sensitive to sunlight for up to one year, " +
                    "so you should apply sunscreen to the area when you're outside.\n\n");
            instructions.add("1. Go to the hospital right away.\n\n");
            instructions.add("2. Don't take off any clothing that is stuck to the burn.\n\n");
            instructions.add("3. Don't soak the burn in water or apply any ointment.\n\n");
            instructions.add("4. If possible, raise the burned area above the level of the heart.\n\n");
            instructions.add("5. You can cover the burn with a cool, wet sterile bandage or clean " +
                    "cloth until you receive medical assistance.\n\n");

            medicalProcedure.setInstructions(instructions);
        }




        //If file does not exist return empty medical procedure

        return medicalProcedure;
    }
}
