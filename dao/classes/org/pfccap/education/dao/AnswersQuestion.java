package org.pfccap.education.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "ANSWERS_QUESTION".
 */
public class AnswersQuestion {

    private Long id;
    private String idQuestion;
    private String idAnswer;
    private String description;
    private Boolean value;
    private Integer points;
    private Boolean enable;
    private String txtSecondQuestion;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public AnswersQuestion() {
    }

    public AnswersQuestion(Long id) {
        this.id = id;
    }

    public AnswersQuestion(Long id, String idQuestion, String idAnswer, String description, Boolean value, Integer points, Boolean enable, String txtSecondQuestion) {
        this.id = id;
        this.idQuestion = idQuestion;
        this.idAnswer = idAnswer;
        this.description = description;
        this.value = value;
        this.points = points;
        this.enable = enable;
        this.txtSecondQuestion = txtSecondQuestion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(String idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(String idAnswer) {
        this.idAnswer = idAnswer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getTxtSecondQuestion() {
        return txtSecondQuestion;
    }

    public void setTxtSecondQuestion(String txtSecondQuestion) {
        this.txtSecondQuestion = txtSecondQuestion;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
