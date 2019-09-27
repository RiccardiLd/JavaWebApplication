package model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "USER")
public class User {

    @Id @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sign_up_date")
    private Date sign_up_date;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_sign_in")
    private Date last_sign_in;

    @ElementCollection
    @CollectionTable(name = "USER_SET", joinColumns = @JoinColumn(name = "user_email"))
    private Set<UserSet> user_set;

    /**
     * Default constructor.
     */
    public User() {

    }

    /**
     * Overloaded constructor.
     * The only way to have a {@code signup_date} on your newly created user is by passing through this constructor.
     * @param email the user's email address. Functions as the ID of said user.
     * @param password the user's password. Will be stored in plain text in this version.
     */
    public User(String email, String password) {
        this.user_set = null;
        setEmail(email);
        setPassword(password);
        setSignUpDate(new Date());
    }

    /**
     * {@code email} getter.
     * @return the user's email.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * {@code password} getter.
     * It is package-private, since it sees no use outside the {@code model} package.
     * @return the user's password.
     */
    String getPassword() {
        return this.password;
    }

    /**
     * {@code signup_date} getter.
     * @return the user's sign up date.
     */
    public Date getSignUpDate() {
        return this.sign_up_date;
    }

    /**
     * {@code last_signin} getter.
     * @return the user's last sign in date.
     */
    public Date getLastSignIn() {
        return this.last_sign_in;
    }

    /**
     * {@code user_set} getter.
     * @return the user's {@code Set} of Key/Value pairs.
     */
    public Set<UserSet> getUserSet() {
        return this.user_set;
    }

    /**
     * {@code email} setter.
     * @param email the new email.
     */
    private void setEmail(String email) {
        this.email = email;
    }

    /**
     * {@code password} setter.
     * @param password the new password.
     */
    private void setPassword(String password) {
        this.password = password;
    }

    /**
     * {@code signup_date} setter. Can only be called upon user creation.
     * @param signup_date the new sign up date.
     */
    private void setSignUpDate( Date signup_date ) {
        this.sign_up_date = signup_date;
    }

    /**
     * {@code last_signin} setter. Upon call will automatically update the parameter with the current time.
     * It is package-private, since it sees no use outside the {@code model} package.
     */
    void setLastSignIn() {
        this.last_sign_in = new Date();
    }

    /**
     * Checks the existence of the Key/Value pair in the user's set and adds it.
     * It is package-private, since it sees no use outside the {@code model} package.
     * @param key The Key of the Key/Value pair.
     * @param value The Value of the Key/Value pair.
     * @return {@code true} if the operation was completed successfully, {@code false} otherwise.
     */
    boolean addUserSet(String key, String value) {
        UserSet u_set = new UserSet(key, value);
        if(!this.user_set.contains(u_set)) {
            this.user_set.add(u_set);
            return true;
        }
        return false;
    }

    /**
     * Checks the existence of the Key/Value pair in the user's set and deletes it.
     * It is package-private, since it sees no use outside the {@code model} package.
     * @param key The Key of the Key/Value pair.
     * @param value The Value of the Key/Value pair.
     * @return {@code true} if the operation was completed successfully, {@code false} otherwise.
     */
    boolean removeUserSet(String key, String value) {
        UserSet u_set = new UserSet(key, value);
        if(this.user_set.contains(u_set)) {
            this.user_set.remove(u_set);
            return true;
        }
        return false;
    }
}