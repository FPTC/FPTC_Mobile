package org.pfccap.education.dao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "USER".
 */
public class User {

    private Long id;
    private String name;
    private java.util.Date datelogin;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String name, java.util.Date datelogin) {
        this.id = id;
        this.name = name;
        this.datelogin = datelogin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.Date getDatelogin() {
        return datelogin;
    }

    public void setDatelogin(java.util.Date datelogin) {
        this.datelogin = datelogin;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
