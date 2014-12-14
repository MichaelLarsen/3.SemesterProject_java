/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Michael, Sebastian, Emil og Andreas
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Profile.getProfileAll", query = "SELECT p FROM Profile p"),
    @NamedQuery(name = "Profile.authenticate", query = "SELECT p.id, p.username, p.role FROM Profile p WHERE p.username = :username AND p.password = :password"),
    @NamedQuery(name = "Profile.findProfile", query = "SELECT p FROM Profile p WHERE p.username = :username"),
})

public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Expose
    @Column(name = "user_name", length = 40)
    private String username;
    
    
    @Column(length = 40)
    private String email;
    
    @Expose
    @Column(name = "pw", length = 40)
    private String password;
    
    
    @Column(length = 40)
    private Date created;
    
    @Expose
    @Column(length = 40)
    private String role;
    
    public Profile() {
    }

    public Profile(String userName, String email, String password, Date created, String role) {
        this.username = userName;
        this.email = email;
        this.password = password;
        this.created = created;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return password;
    }

    public void setPw(String password) {
        this.password = password;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profile)) {
            return false;
        }
        Profile other = (Profile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{\"user_name\":" + "\"" + username + "\"" + ",\"email\":" + "\"" + email + "\"" + ",\"password\":" + "\"" + password + "\"" + ",\"created\":" + "\"" + created + "\"" + ",\"role\":" + "\"" + role + "\"" +"}";
    }
    
}
