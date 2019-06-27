package com.lambdaschool.tipsease.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// User is considered the parent entity

@Entity
@Table(name = "users")
public class User extends Auditable
{

    // hides the fields with hidden = isHidden if set to true.
    private final boolean isHidden = false;

    @Id
    @ApiModelProperty(notes = "The database generated product ID", hidden = isHidden)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;
    // 1
    // 2

    @Column(nullable = false)
    @ApiModelProperty(notes = "The whole name of a user")
    private String name;
    // Jacob Tonna
    // Alex Shipplet

    @Column(nullable = false, unique = true)
    @ApiModelProperty(notes = "Username used to login")
    private String username;
    // @Jtonna
    // @Alex

    @Column(nullable = true)
    @ApiModelProperty(notes = "this value is auto set for all new users but can be updated by the user")
    private String profilepicture = "https://twistedsifter.files.wordpress.com/2012/09/trippy-profile-pic-portrait-head-on-and-from-side-angle.jpg";

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(notes = "this is the password users will use to sign in")
    private String password;
    // password123
    // superSecurePassword

    @ApiModelProperty(notes = "The database generated product ID", hidden = isHidden)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<UserRoles> userRoles = new ArrayList<>();

    public User()
    {
    }

    public User(String name, String username, String password, String profilepicture, List<UserRoles> userRoles)
    {
        setName(name);
        setUsername(username);
        setPassword(password);
        setProfilepicture(profilepicture);
        for (UserRoles ur : userRoles)
        {
            ur.setUser(this);
        }
        this.userRoles = userRoles;
    }

    public long getUserid()
    {
        return userid;
    }

    public void setUserid(long userid)
    {
        this.userid = userid;
    }

    public String getProfilepicture() {
        return profilepicture;
    }

    public void setProfilepicture(String profilepicture) {
        this.profilepicture = profilepicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setPasswordNoEncrypt(String password)
    {
        this.password = password;
    }

    public List<UserRoles> getUserRoles()
    {
        return userRoles;
    }

    public void setUserRoles(List<UserRoles> userRoles)
    {
        this.userRoles = userRoles;
    }

    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for (UserRoles r : this.userRoles)
        {
            String myRole = "ROLE_" + r.getRole().getName().toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }

        return rtnList;
    }
}
