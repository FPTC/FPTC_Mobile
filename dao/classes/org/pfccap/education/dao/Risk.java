package org.pfccap.education.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "RISK".
 */
public class Risk {

    private Long id;
    private String code;
    private String question;
    private Integer typeCancer;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Risk() {
    }

    public Risk(Long id) {
        this.id = id;
    }

    public Risk(Long id, String code, String question, Integer typeCancer) {
        this.id = id;
        this.code = code;
        this.question = question;
        this.typeCancer = typeCancer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getTypeCancer() {
        return typeCancer;
    }

    public void setTypeCancer(Integer typeCancer) {
        this.typeCancer = typeCancer;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
