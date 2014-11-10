package com.tmatvienko.chat.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tatyana on 11/10/14.
 */
@Entity
@Table(name="user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "login")})
@NamedQueries({
        @NamedQuery(name = User.Names.FIND_BY_NAME, query = User.Values.FIND_BY_NAME),
        @NamedQuery(name = User.Names.DELETE_BY_ID, query = User.Values.DELETE_BY_ID)
})
@Cacheable
public class User implements Serializable {
    private static final long serialVersionUID = -89804915024345352L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull(message = "login field cannot be null.")
    private String login;

    @Column(name = "password_hash")
    @NotNull(message = "passwordHash field cannot be null.")
    @Size(min = 1, max = 64, message = "Field cannot be empty. The length of passwordHash should be 64 symbols.")
    private String passwordHash;

    @Column(name = "password_salt")
    @NotNull(message = "passwordSalt field cannot be null.")
    @Size(min = 1, max = 24, message = "Field cannot be empty. The length of passwordSalt should not be more than " +
            "24 symbols.")
    private String passwordSalt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    private Set<Message> messages = new HashSet<Message>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    public static interface Names {
        static final String FIND_BY_NAME = "User.findByName";
        static final String DELETE_BY_ID = "User.deleteById";
    }

    public static interface Values {
        static final String FIND_BY_NAME = "select u from User u where u.login = :login";
        static final String DELETE_BY_ID = "delete from User u where u.id = :id";
    }

    public static interface Parameters {
        static final String USER = "user";
        static final String ID = "id";
        static final String LOGIN = "login";
    }
}
