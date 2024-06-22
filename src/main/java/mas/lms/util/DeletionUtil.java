package mas.lms.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import mas.lms.model.Borrow;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

/**
 * Utility class for handling entity deletion operations.
 */
public class DeletionUtil {

    /**
     * Deletes an entity from the database.
     *
     * @param entityClass The class type of the entity to delete.
     * @param entityId    The ID of the entity to delete.
     * @param entityName  The name of the entity type (used for display purposes).
     * @param <T>         The type parameter of the entity.
     */
    public static <T> void deleteEntity(Class<T> entityClass, Long entityId, String entityName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            T entity = session.get(entityClass, entityId);
            if (entity == null) {
                showAlert("Error", entityName + " not found.");
                return;
            }

            session.delete(entity);
            transaction.commit();
            showAlert("Success", entityName + " deleted successfully!");
        } catch (Exception e) {
            if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                showAlert("Error", "The " + entityName + " cannot be deleted due to existing references.");
                throw (org.hibernate.exception.ConstraintViolationException) e.getCause();
            } else {
                showAlert("Error", "An error occurred while deleting the " + entityName + ".");
                e.printStackTrace();
            }
        }
    }

    /**
     * Forcefully deletes an entity from the database, including all its references.
     *
     * @param entityClass The class type of the entity to delete.
     * @param entityId    The ID of the entity to delete.
     * @param entityName  The name of the entity type (used for display purposes).
     * @param <T>         The type parameter of the entity.
     */
    public static <T> void forceDeleteEntity(Class<T> entityClass, Long entityId, String entityName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            T entity = session.get(entityClass, entityId);
            if (entity == null) {
                showAlert("Error", entityName + " not found.");
                return;
            }

            if (entityClass == mas.lms.model.Member.class) {
                // Delete all associated borrows for the member
                Query<Borrow> query = session.createQuery("from Borrow where member.id = :memberId", Borrow.class);
                query.setParameter("memberId", entityId);
                List<Borrow> borrows = query.list();
                for (Borrow borrow : borrows) {
                    session.delete(borrow);
                }
            }

            session.delete(entity);
            transaction.commit();
            showAlert("Success", entityName + " forcefully deleted successfully!");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while forcefully deleting the " + entityName + ".");
            e.printStackTrace();
        }
    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title   The title of the alert dialog.
     * @param message The message to be displayed in the alert dialog.
     */
    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation dialog with the specified title and message.
     *
     * @param title   The title of the confirmation dialog.
     * @param message The message to be displayed in the confirmation dialog.
     * @return true if the user confirms the action, false otherwise.
     */
    public static boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
