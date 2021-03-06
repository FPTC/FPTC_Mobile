package org.pfccap.education.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "SECOND_ANSWER".
 */
public class SecondAnswer {

    private Long id;
    private String idAnswer;
    private String idSecondAnswer;
    private String description;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public SecondAnswer() {
    }

    public SecondAnswer(Long id) {
        this.id = id;
    }

    public SecondAnswer(Long id, String idAnswer, String idSecondAnswer, String description) {
        this.id = id;
        this.idAnswer = idAnswer;
        this.idSecondAnswer = idSecondAnswer;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(String idAnswer) {
        this.idAnswer = idAnswer;
    }

    public String getIdSecondAnswer() {
        return idSecondAnswer;
    }

    public void setIdSecondAnswer(String idSecondAnswer) {
        this.idSecondAnswer = idSecondAnswer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
