/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.jianxia.bouncer.entity;

import java.io.Serializable;
import java.util.HashMap;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zhangjianxia
 */
@Entity
@Table(name = "APPUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appuser.findAll", query = "SELECT a FROM Appuser a"),
    @NamedQuery(name = "Appuser.findById", query = "SELECT a FROM Appuser a WHERE a.id = :id"),
    @NamedQuery(name = "Appuser.findByGroupid", query = "SELECT a FROM Appuser a WHERE a.groupid = :groupid"),
    @NamedQuery(name = "Appuser.findByPassword", query = "SELECT a FROM Appuser a WHERE a.password = :password"),
    @NamedQuery(name = "Appuser.findByUserid", query = "SELECT a FROM Appuser a WHERE a.userid = :userid")})
public class Appuser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Column(name = "USERID")
    private String userid;
    @Size(max = 255)
    
    @NotNull
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 255)
    
    @NotNull
    @Column(name = "GROUPID")
    private String groupid;
    @Size(max = 255)
    

    public Appuser() {
    }

    public Appuser(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getPassword() {
        return "";
    }

    //public void setPassword(String password) {
        //this.password = password;
    //}
     public void setPassword(String password) {
        if (password != null && !password.isEmpty()) {
            // Initialize PasswordHash object
            Instance<? extends PasswordHash> instance = CDI.current().select(Pbkdf2PasswordHash.class);
            PasswordHash passwordHash = instance.get();
            passwordHash.initialize(new HashMap<String,String>());                                                                      

            // Hash and salt the password
            this.password = passwordHash.generate(password.toCharArray());
        }
        // If password is empty or null, do nothing to avoid changing the password
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appuser)) {
            return false;
        }
        Appuser other = (Appuser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.jianxia.bouncer.entity.Appuser[ id=" + id + " ]";
    }
    
}
