package com.example.asus.strokeanalyzer.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;
import com.example.asus.strokeanalyzer.Entities.Patient;

/**
 * Interfejs definiujący operację na bazie danych związane z tabelą zawierającą dane pacjentów
 *
 * @author Stanisław Wasilkowski
 */

@Dao
public interface PatientDao {
    /**
     * Metoda zapisująca dane dotyczące nowego pacjenta w bazie danych
     *
     * @param patient obiekt klasy Patient, który ma zostać dodany do bazy
     * @return (long) Id pacjenta w bazie danych
     */
    @Insert
    long insert(Patient patient);

    /**
     * Metoda aktualizująca w bazie danyhc dane odnoszące się do pacjenta patient
     *
     * @param patient obiekt klasy Patient, którego dane mają zostać zaktualizowane w bazie danych
     */
    @Update
    void update(Patient patient);

    /**
     * Metoda zwracająca listę wszystkich pacjentów zapisanych w bazie danych aplikacji
     *
     * @return
     * {@code List<Patient>} lista wszystkich pacjentów zapisanych w bazie danych
     */
    @Query("SELECT * FROM patient")
    List<Patient> selectAll();

    /**
     * Metoda zwracająca dane pacjenta przechowywane w bazie danych
     *
     * @param id Id pacjenta, którego dane chcemy pozyskać z bazy danych
     * @return (Patient) obiekt zawierający dane pacjenta pozyskane z bazy danych
     */
    @Query("SELECT * FROM patient WHERE id = :id")
    Patient selectById(int id);

    /**
     * Metoda zwracająca liczbę pacjentów o numerze identyfikacyjnym patientNumber
     * zapisanych w bazie danych
     *
     * @param patientNumber numer pacjenta, który chcemy wyszukać w bazie
     * @return (int) liczba pacjentów o podanym numerze zapisanych w bazie danych
     */
    @Query("SELECT COUNT(*) FROM patient WHERE patient_number = :patientNumber")
    int checkIfPatientExistsByNumber(long patientNumber);

    /**
     * Metoda usuwająca z bazy danych informacje o pacjencie patient
     *
     * @param patient obiekt pacjenta, którego chcemy usunąć z bazy danych
     */
    @Delete
    void delete(Patient patient);
}
