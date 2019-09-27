package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserSet implements Serializable {
    @Column(name = "set_key")
    private String set_key;

    @Column(name = "set_value")
    private String set_value;

    /**
     * Default constructor.
     */
    public UserSet() {

    }

    /**
     * Overloaded constructor.
     * @param key The Key of the Key/Value pair.
     * @param value The Value of the Key/Value pair.
     */
    UserSet(String key, String value) {
        this.setKey(key);
        this.setValue(value);
    }

    /**
     * {@code key} getter.
     * @return the pair's Key.
     */
    public String getKey() {
        return this.set_key;
    }

    /**
     * {@code value} getter.
     * @return the pair's Value.
     */
    public String getValue() {
        return this.set_value;
    }

    /**
     * {@code key}
     * @param key The Key of the Key/Value pair.
     */
    private void setKey(String key) {
        this.set_key = key;
    }

    /**
     * {@code value}
     * @param value The Value of the Key/Value pair.
     */
    private void setValue(String value) {
        this.set_value = value;
    }

    /**
     * Overrided {@code equals()} function.
     * @param o object to be compared.
     * @return {@code true} if objects match, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSet)) return false;
        UserSet that = (UserSet) o;
        return Objects.equals(getKey(), that.getKey()) && Objects.equals(getValue(), that.getValue());
    }

    /**
     * Overrided {@code hashCode()} function. Accompanies the {@code equals()} function.
     * @return the object's hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getValue());
    }
}
