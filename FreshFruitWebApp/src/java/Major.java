/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Greg
 */
public enum Major {
    AE("Aerospace Engineering"),
    ARCH("Architecture"),
    BME("Biomedical Engineering"),
    ChemE("Chemical Engineering"),
    Chem("Chemistry"),
    CM("Computational Media"),
    CS("Computer Science"),
    EE("Electrical Engineering"),
    ISYE("Industrial and Systems Engineering"),
    Math("Mathematics"),
    MGT("Management"),
    ME("Mechanical Engineering"),
    Phys("Physics"),
    Un("Undecided");

    public String fullName;

/**
 * set major method.
 * @param name is the name of the major 
 */
    private Major(String name) {
        fullName = name;
    }
    /**
 * set major method.
 * @param fullname get the name of major
 */

    public static Major getMajorFromString(String fullName) {
        for (Major m : Major.values()) {
            if (m.fullName.equals(fullName)) {
                return m;
            }
        }
        return null;
    }
}