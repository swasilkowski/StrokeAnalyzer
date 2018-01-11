package com.example.asus.strokeanalyzer.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.asus.strokeanalyzer.Entities.OtherData;
import java.util.List;

/**
 * Interfejs definiujący operację na bazie danych związane z tabelą zawierającą odpowiedzi na pytania
 * powiązane z danym pacjentem
 *
 * @author Stanisław Wasilkowski
 */

@Dao
public interface OtherDataDao {

    /**
     * Metoda zapisująca odpowiedź na pytanie w bazie danych
     *
     * @param data dane stanowiące odpowiedź na pytanie
     * @return (long) Id wpisu w bazie danych
     */
    @Insert
    long insert(OtherData data);

    /**
     * Metoda pobierająca z bazy danych wszystkie odpowiedzi na pytania powiązane z pacjentem o id patientId
     *
     * @param patientId Id pacjenta, którego odpowiedzi na pytania chcemy pozyskać z bazy danych
     * @return
     * {@code List<OtherData>} lista odpowiedzi na pytania danego pacjenta
     */
    @Query("SELECT * FROM other_data WHERE patient_id = :patientId")
    List<OtherData> SelectByPatientId(int patientId);

    /**
     * Metoda aktualizująca w bazie danych konkretną odpowiedź pacjenta
     *
     * @param data dane odpowiedzi, która ma zostać zaktualizowana
     */
    @Update
    void update(OtherData data);

    /**
     * Metoda zwracająca liczbę odpowiedzi na dane pytanie wybranego pacjenta
     *
     * @param patientId Id pacjenta, dla którego szukamy odpowiedzi na pytanie
     * @param answerId Id pytania, dla którego szukamy odpowiedzi udzielonej przez pacjenta
     * @return (int) liczba odpowiedzi znajdujących się w bazie danych na dane pytanie wybranego pacjenta
     */
    @Query("SELECT COUNT(*) FROM other_data WHERE patient_id = :patientId AND answer_id = :answerId")
    int checkIfExists(int patientId, int answerId);

    /**
     * Metoda usuwająca z bazy danych wszystkie odpowiedzi związane z konkretnym pacjentem
     *
     * @param patientId Id pacjenta, którego odpowiedzi mają zostać usunięte z bazy danych
     */
    @Query("DELETE FROM other_data WHERE patient_id = :patientId")
    void deleteByPatientId(int patientId);
}
