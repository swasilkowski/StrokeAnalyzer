package com.example.asus.strokeanalyzer.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;
import com.example.asus.strokeanalyzer.Entities.NihssExamination;

/**
 * Interfejs definiujący operację na bazie danych związane z tabelą zawierającą badania pacjenta w skali NIHSS
 *
 * @author Stanisław Wasilkowski
 */

@Dao
public interface NihssDao {

    /**
     * Metoda zapisująca badanie nihssExamination w bazie danych
     *
     * @param nihssExamination obiekt zawierający dane pojedynczego badania NIHSS, które ma zostać dodane do bazy
     * @return (long) id badania dodanego do bazy
     */
    @Insert
    long insert(NihssExamination nihssExamination);

    /**
     * Metoda zwracająca wszystkie badania pacjenta dla skali NIHSS
     *
     * @param patientId Id pacjenta, dla którego pobrana ma zostać lista badań dla skali NIHSS
     * @return
     * {@literal List<NihssExamination>} lista badań dla skali NIHSS pacjenta
     */
    @Query("SELECT * FROM nihss_form WHERE patient_id = :patientId")
    List<NihssExamination> SelectByPatientId(int patientId);

    /**
     * Metoda zwracająca najświeższe badanie pacjenta dla skali NIHSS
     *
     * @param patientId Id pacjenta, dla którego pobrane ma zostać badanie dla skali NIHSS
     * @return (NihssExamination) najnowsze badanie pacjenta dla skali NIHSS
     */
    @Query("SELECT * FROM nihss_form WHERE patient_id = :patientId ORDER BY added_on DESC LIMIT 1")
    NihssExamination SelectLatestByPatientId(int patientId);

    /**
     * Metoda zwracająca najstarsze badanie pacjenta dla skali NIHSS
     *
     * @param patientId Id pacjenta, dla którego pobrane ma zostać badanie dla skali NIHSS
     * @return (NihssExamination) najstarsze badanie pacjenta dla skali NIHSS
     */
    @Query("SELECT * FROM nihss_form WHERE patient_id = :patientId ORDER BY added_on LIMIT 1")
    NihssExamination SelectEarliestByPatientId(int patientId);

    /**
     * Metoda usuwająca z bazy danych wszystkie badania dla skali NIHSS danego pacjenta
     *
     * @param patientId Id pacjenta, którego badania dla skali NIHSS mają zostać usunięte z bazy danych
     */
    @Query("DELETE FROM nihss_form WHERE patient_id = :patientId")
    void deleteByPatientId(int patientId);
}
