package dk.mgarde.wishlist.model;

import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.SneakyThrows;

/**
 * Wish data and {@link Entity} definition.
 */
@Entity
public class Wish extends PanacheEntity {

    public String name;
    public String price;
    public String url;
    public Integer priority;

    // Data handling methods

    /**
     * Finds a wish by wishId.
     * 
     * @throws EntityNotFoundException if the wish is not found.
     */
    @Transactional
    public static Wish findWishBy(long wishId) throws EntityNotFoundException {
        Wish wish = find("id", wishId).firstResult();
        if (wish != null) {
            return wish;
        } else {
            throw new EntityNotFoundException("Wish with id; " + wishId + " not found.");
        }
    }

    /**
     * Gets a persons wishlist. Checks if the person with id exists.
     * 
     * @throws {@link SneakyThrows} an {@link EntityNotFoundException} if the wish is not found.
     */
    @SneakyThrows
    public static List<Wish> getWishListByPersonId(final long id) {
        final Person person = Person.findPersonBy(id);

        if (person.wishList != null && !person.wishList.isEmpty()) {
            return person.getWishList();
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Adds a wish to a person with the specified id. Checks for the existence of
     * the person and {@link SneakyThrows} an {@link EntityNotFoundException} if the
     * person does not exist.
     */
    @Transactional
    @SneakyThrows
    public static void addWishToPersonById(final long personId, Wish wish) {
        Person person = Person.findPersonBy(personId);
        person.wishList.add(wish);
        person.persist();
    }

    /**
     * Deletes a wish. The wishList definition in {@link Person} handles foreign key
     * constraints. {@link SneakyThrows} an {@link EntityNotFoundException} if the
     * wish does not exist.
     */
    @Transactional
    @SneakyThrows
    public static boolean deleteWish(final long wishId) {
        findWishBy(wishId);
        return delete("id", wishId) > 0;
    }
}