package dk.mgarde.wishlist.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.Data;

/**
 * Person data and {@link Entity} definition.
 */
@Entity
@Data
public class Person extends PanacheEntity {

    public String name;
    public LocalDate birthday;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "person_id")
    public List<Wish> wishList;

    // Getters and setters

    /**
     * Attempts to parse a string parameter as a date. Sets the persons birthday as
     * a LocalDate. Use getBirthday getter for converting back to a string.
     */
    public void setBirthday(final String birthday) {
        this.birthday = LocalDate.parse(birthday);
    }

    /**
     * Gets a persons birthday as a string
     */
    public String getBirthday() {
        return this.birthday.toString();

    }

    // Panache helper methods

    /**
     * Finds a {@link Person} based a personId
     * 
     * @throws EntityNotFoundException if no person is found.
     */
    public static Person findPersonBy(final long id) throws EntityNotFoundException {
        final Person person = find("id", id).firstResult();

        if (person != null) {
            return person;
        } else {
            throw new EntityNotFoundException("Person with id; " + id + " not found.");
        }
    }

    /**
     * Searches the database for a person with the name parameter Is case sensitive.
     */
    public static List<Person> findPersonBy(final String searchParam) {
        List<Person> personList = find("lower(name) like lower(:query)",
                Parameters.with("query", "%" + searchParam + "%".toLowerCase())).list();

        if (!personList.isEmpty()) {
            return personList;
        } else {
            throw new EntityNotFoundException("No one found.");
        }
    }

    /**
     * Find a specific person. I.e. based on name and birthday. Name search is case
     * insensitive.
     */
    public static Person findPerson(final Person person) {
        return find("lower(name) = lower(:name) and birthday = :birthday",
                Parameters.with("name", person.getName()).and("birthday", LocalDate.parse(person.getBirthday())))
                        .firstResult();
    }

    /**
     * Checks if a person exists.
     */
    public static boolean personExists(final Person person) {
        return findPerson(person) != null;
    }

    /**
     * Adds a new person to the database unless the person already exists. Case
     * sensitive.
     */
    @Transactional
    public static Person addNewPerson(final Person person) throws EntityExistsException {
        if (personExists(person)) {
            throw new EntityExistsException(person.toString() + " already exists.");
        } else {
            person.persist();
            return person;
        }
    }

    /**
     * Get a list of persons including wishlists.
     */
    public static List<Person> getAllPersonsList() {
        List<Person> personList = findAll(Sort.by("name")).list();

        if (!personList.isEmpty()) {
            return personList;
        } else {
            throw new EntityNotFoundException("No one found.");
        }
    }

    /**
     * Deletes a person.
     * 
     * @return True if successful otherwise false
     */
    @Transactional
    public static boolean deletePerson(final long personId) {
        findPersonBy(personId);
        return delete("id", personId) > 0;
    }
}